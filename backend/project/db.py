from flask_pymongo import PyMongo
from flask_hashing import Hashing
from flask_jwt_extended import JWTManager

hashing = Hashing()
mongo = PyMongo()
jwt = JWTManager()
salt = 'abcdefgh'