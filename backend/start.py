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
    user = request.json # user to nie zwykly obiekt, a slownik (dictionary)
    print("----------")
    print(user)
    if '_id' in user: # Jeśli wprowadzono ID, chcemy zeby nie bylo ono ustawione w bazie:
        del user['_id']
    print(user) # <- teraz slownik user nie ma id.
    userId = mongo.db.uzytkownicy.insert_one(user).inserted_id # dodajemy nowy rekord do bazy oraz otrzymujemy spowrotem id nowego rekordu
    user.update({'_id': dumps(userId)}) # metoda update dodaje do slownika nowa wartosc - id nowego rekordu z bazy
    print(user)
    print("----------")
    return dumps(user) # dumps zamienia slownik na obiekt json.

@app.route('/uzytkownik/<string:user_id>', methods=["GET"])
def podajUzytkownika(user_id):
    user = mongo.db.uzytkownicy.find_one({"_id": ObjectId(user_id)})
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
