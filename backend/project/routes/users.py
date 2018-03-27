from flask import request, Blueprint
from bson.objectid import ObjectId
from bson.json_util import dumps
from project.db import mongo

users_blueprint = Blueprint(
    'users',
    __name__
)

@users_blueprint.route('/', methods=["POST"])
def dodajUzytkownika():
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

@users_blueprint.route('/<string:user_id>', methods=["GET"])
def podajUzytkownika(user_id):
    user = mongo.db.uzytkownicy.find_one({"_id": ObjectId(user_id)})
    return dumps(user)

@users_blueprint.route('/<string:user_id>', methods=["PUT"])
def edytujUzytkownika(user_id):
    return 'test'