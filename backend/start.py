from flask import Flask, request
from flask_pymongo import PyMongo
from bson.objectid import ObjectId
from bson.json_util import dumps

app = Flask(__name__)

app.config['MONGO_DBNAME'] = 'itmProj'
app.config['MONGO_URI'] = 'mongodb+srv://itmProj:denaturat100%@itmproj-fddwr.mongodb.net'

mongo = PyMongo(app)

@app.route('/uzytkownik', methods=["POST"])
def dodajUzytkownika():
    user = request.json
    id = mongo.db.uzytkownicy.insert_one(user).inserted_id
    return dumps(id)

@app.route('/uzytkownik/<string:user_id>', methods=["GET"])
def podajUzytkownika(user_id):
    user = mongo.db.uzytkownicy.find_one({"_id": ObjectId(user_id)})
    print(user)
    return dumps(user)

@app.route('/uzytkownik/<string:user_id>', methods=["PUT"])
def edytujUzytkownika(user_id):
    return 'test'

@app.route('/testy/buttonTest', methods=["POST"])
def dodajWynikButtontestu():
    return 'test'

@app.route('/testy/ranking/buttonTest', methods=["GET"])
def podajRankingButtonTestu():
    return 'test'

if __name__ == '__main__':
    app.run(debug=True)
