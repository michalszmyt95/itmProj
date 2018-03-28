from flask_pymongo import PyMongo
from flask_hashing import Hashing

hashing = Hashing()
mongo = PyMongo()
salt = 'abcdefgh'