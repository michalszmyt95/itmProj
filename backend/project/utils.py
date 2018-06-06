from flask import make_response
from jsonpickle import encode
from project.db import mongo
from flask_pymongo import ObjectId
import datetime

def response(data):
    r = make_response(encode(data))
    r.headers['Content-type'] = 'application/json'
    return r

def podajUzytkownikaPoId(id):
    return mongo.db.uzytkownicy.find_one({"_id": ObjectId(str(id))})

def now():
    return datetime.datetime.now()
