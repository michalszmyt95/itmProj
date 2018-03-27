from flask import request, Blueprint
from bson.objectid import ObjectId
from bson.json_util import dumps
from project.db import mongo

tests_blueprint = Blueprint(
    'tests',
    __name__
)

@tests_blueprint.route('/buttonTest', methods=["POST"])
def dodajWynikButtontestu():
    user = request.json # users to nie zwykly obiekt, a slownik (dictionary)
    print("----------")
    print(user)
    if '_id' in user: # Jeśli wprowadzono ID, chcemy zeby nie bylo ono ustawione w bazie:
        del user['_id']
    print(user) # <- teraz slownik users nie ma id.
    userId = mongo.db.uzytkownicy.insert_one(user).inserted_id # dodajemy nowy rekord do bazy oraz otrzymujemy spowrotem id nowego rekordu
    user.update({'_id': dumps(userId)}) # metoda update dodaje do slownika nowa wartosc - id nowego rekordu z bazy
    print(user)
    print("----------")
    return dumps(user) # dumps zamienia slownik na obiekt json.

@tests_blueprint.route('/ranking/buttonTest', methods=["GET"])
def podajRankingButtonTestu():
    user = mongo.db.uzytkownicy.find_one({"_id": ObjectId()})
    return dumps(user)