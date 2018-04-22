from flask import request, Blueprint
from flask_jwt_extended import jwt_required, get_jwt_identity
from project.db import mongo
from project.utils import response, podajUzytkownikaPoId

tests_blueprint = Blueprint(
    'tests',
    __name__
)


@tests_blueprint.route('/buttonTest', methods=["POST"])
@jwt_required
def dodajWynikButtontestu():
    wynikTestu = request.json
    print('headers: ' + str(request.headers))
    print('REQUEST ' + str(wynikTestu))

    wynikTestu['test'] = 'buttonTest'

    user_id = get_jwt_identity()
    user = podajUzytkownikaPoId(user_id)

    wynikTestu['user_id'] = user_id

    print('Dane uzytkownika pobranego z bazy przy pomocy jwt tokena: ' + str(user))

    wynikTestu['waga'] = user['waga']
    wynikTestu['wiek'] = user['wiek']
    wynikTestu['wzrost'] = user['wzrost']

    mongo.db.buttonTest.insert_one(wynikTestu)

    wynikTestu['jakasWartosc'] = 5

    mongo.db.testowaTabela.insert_one(wynikTestu)

    return response('OK')


@tests_blueprint.route('/ninjaTest', methods=["POST"])
@jwt_required
def dodajWynikNinjatestu():
    wynikTestu = request.json
    print('headers: ' + str(request.headers))
    print('REQUEST ' + str(wynikTestu))

    wynikTestu['test'] = 'ninjaTest'

    user_id = get_jwt_identity()
    user = podajUzytkownikaPoId(user_id)

    wynikTestu['user_id'] = user_id

    print('Dane uzytkownika pobranego z bazy przy pomocy jwt tokena: ' + str(user))

    wynikTestu['waga'] = user['waga']
    wynikTestu['wiek'] = user['wiek']
    wynikTestu['wzrost'] = user['wzrost']

    mongo.db.buttonTest.insert_one(wynikTestu)

    wynikTestu['jakasWartosc'] = 5

    mongo.db.testowaTabela.insert_one(wynikTestu)

    return response('OK')