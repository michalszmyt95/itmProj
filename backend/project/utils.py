from flask import make_response
from jsonpickle import encode

def response(data):
    r = make_response(encode(data))
    r.headers['Content-type'] = 'application/json'
    return r