from flask import request, Blueprint, abort
from bson.objectid import ObjectId
from bson.json_util import dumps
from project.db import mongo, hashing, salt
from project.utils import response
from project.models.dto import WynikOperacji
import requests

users_blueprint = Blueprint(
    'users',
    __name__
)

@users_blueprint.route('/', methods=["POST"])
def dodajUzytkownika():
    user = request.json # request.json to nie zwykly obiekt, a slownik (dictionary)

    # email oraz token (z serwisu społecznościowego) jest wymagany:
    if ('email' and 'token' and 'metodaLogowania') not in user:
        abort(400)
    if 'id' in user: # Jeśli wprowadzono ID, chcemy zeby nie bylo ono ustawione w bazie:
        del user['id']

    print('user na wejsciu' + str(user))

    if user['metodaLogowania'] == 'Facebook':
        r = requests.get('https://graph.facebook.com/' + user['socialId'] + '?fields=email&access_token=' + user['token'])
        fbEmail = r.json()['email']
        print(fbEmail + ' =? ' + user['email'])
        if fbEmail != user['email']:
            abort(401)
    elif user['metodaLogowania'] == 'Google':
        r = requests.get(
            'https://www.googleapis.com/plus/v1/people/' + user['socialId'] + '?access_token=' + user['token'])
        print(str(r.json()))
        emaile = r.json()['emails']
        emailZgodny = False
        for e in emaile: # w google moze byc wiele emaili, wiec dla kazdego emaila z googla:
            print('email: ' + e['value'])
            if e['value'] == user['email']: #jesli email z googla jest taki jak uzytkownika
                emailZgodny = True
                break #wychodzimy z petli - uzytkownik zwalidowany poprawnie.
        if not emailZgodny:
            abort(401) # email nie byl zgodny po przeiterowaniu w petli - nie zautoryzowano uzytkownika wiec abort(401)

    #sprawdzanie czy uzytkownik o takim emailu juz istnieje w bazie:
    hashed_new_email = hashing.hash_value(user['email'], salt) # porownywanie
    res = mongo.db.uzytkownicy.find_one({"email": hashed_new_email})

    print(str(res))

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