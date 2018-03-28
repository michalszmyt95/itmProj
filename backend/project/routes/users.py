from flask import request, Blueprint, abort
from bson.objectid import ObjectId
from bson.json_util import dumps
from project.db import mongo, hashing, salt
from project.utils import response
from project.models.dto import WynikOperacji

users_blueprint = Blueprint(
    'users',
    __name__
)

@users_blueprint.route('/', methods=["POST"])
def dodajUzytkownika():
    user = request.json # users to nie zwykly obiekt, a slownik (dictionary)
    # email jest wymagany:
    if 'email' not in user:
        abort(400)
    if 'id' in user: # Jeśli wprowadzono ID, chcemy zeby nie bylo ono ustawione w bazie:
        del user['id']

    #sprawdzanie czy uzytkownik o takim emailu juz istnieje w bazie:
    hashed_new_email = hashing.hash_value(user['email'], salt) # porownywanie
    res = mongo.db.uzytkownicy.find_one({"email": hashed_new_email})

    if res is not None:
        return response(WynikOperacji(str(res['_id']), False)) # zwracamy do klienta id istniejacego uzytkownika i false - bo uzytkownik juz istnieje w bazie

    #jeśli uzytkownik pojawił się pierwszy raz, dodaj go do bazy:
    user['email'] = hashed_new_email
    userId = mongo.db.uzytkownicy.insert_one(user).inserted_id # dodajemy nowy rekord do bazy oraz otrzymujemy spowrotem id nowego uzytkownika
    return response(WynikOperacji(userId, True)) # zwracamy do klienta id nowego uzytkownika i true - bo uzytkownik zostal wlasnie dodany

@users_blueprint.route('/<string:user_id>', methods=["GET"])
def podajUzytkownika(user_id):
    user = mongo.db.uzytkownicy.find_one({"_id": ObjectId(user_id)})
    return dumps(user)

@users_blueprint.route('/<string:user_id>', methods=["PUT"])
def edytujUzytkownika(user_id):
    return 'test'