from unicodedata import name
from urllib import response
from flask import Flask, request, jsonify
from flask_restful import Resource, Api
from flask_cors import CORS
import base64
import json
from io import BytesIO
from urllib import response
import numpy as np
import requests
import tensorflow as tf
from PIL import Image

# load model
model_path = r"fruitveg0732.h5"
model = tf.keras.models.load_model(model_path)

# input image specification
img_height = 224
img_width = 224
img_size = (img_height, img_width)
img_shape = img_size + (3,)

# classes to predict
class_names = ['f_apple', 'f_banana', 'f_carrot', 'f_greens', 'f_orange', 'f_tomato',
               'm_apple', 'm_banana', 'm_carrot', 'm_greens', 'm_orange', 'm_tomato',
               'r_apple', 'r_banana', 'r_carrot', 'r_greens', 'r_orange', 'r_tomato']


# FLASK WEBB APP PROGRAM
app = Flask(__name__)

# initiate restfull object
api = Api(app)

# initiate object flask_cors
CORS(app)

# initiate empty variable
file = {}


class ResourceExm(Resource):
    def get(self):
        return file

    def post(self):
        # name = request.form["name"] #'b64' adalah key yang akan di post dari user (harus sama)
        pic = np.array(tf.keras.utils.load_img(
            BytesIO(base64.b64decode(request.form['b64'])), target_size=img_shape))
        pic = np.expand_dims(pic, axis=0)
        pic = pic.astype('float16')

        # predict
        pred = model.predict(pic)
        pred_idx = np.argmax(pred, axis=-1)

        file["predict1"] = class_names[int(pred_idx)]
        response = {"msg": "Data telah masuk"}
        return response


api.add_resource(ResourceExm, "/predict", methods=["GET", "POST"],)

if(__name__ == "__main__"):
    app.run(debug=True, port=3000)
