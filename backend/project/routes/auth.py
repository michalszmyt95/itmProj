from flask import request, Blueprint, abort
from project.db import mongo, hashing, salt
from project.utils import response
from project.models.dto import WynikRejestracji, WynikOdswiezeniaTokena
from flask_jwt_extended import create_access_token, create_refresh_token, jwt_refresh_token_required, get_jwt_identity

import requests

auth_blueprint = Blueprint(
    'auth',
    __name__
)

@auth_blueprint.route('/rejestruj-lub-zaloguj', methods=["POST"])
def rejestrujLubZaloguj():
    user = request.json # request.json to nie zwykly obiekt, a slownik (dictionary)
    print('user na wejsciu' + str(user))

    walidujZgodnoscTokenaOrazMailaZFacebookiemLubGoogle(user)

    #sprawdzanie czy uzytkownik o takim emailu juz istnieje w bazie:
    hashed_new_email = hashing.hash_value(user['email'], salt) # porownywanie
    res = mongo.db.uzytkownicy.find_one({"email": hashed_new_email})

    print(str(res))

    if res is not None: #LOGOWANIE:
        id = str(res['_id'])
        print('id użytkownika w naszej bazie: ' + str(id))
        access_token = str(create_access_token(identity=id))
        refresh_token = str(create_refresh_token(identity=id))
        return response(WynikRejestracji(access_token, refresh_token, False)) # zwracamy do klienta id istniejacego uzytkownika i false - bo uzytkownik juz istnieje w bazie

    #REJESTRACJA:
    #jeśli uzytkownik pojawił się pierwszy raz, dodaj go do bazy:
    user['email'] = hashed_new_email
    usunNiepotrzebneDaneUzytkownika(user)
    user_id = str(mongo.db.uzytkownicy.insert_one(user).inserted_id) # dodajemy nowy rekord do bazy oraz otrzymujemy spowrotem id nowego uzytkownika jako string
    print('id nowego użytkownika w naszej bazie: ' + str(user_id))
    access_token = str(create_access_token(identity=user_id))
    refresh_token = str(create_refresh_token(identity=user_id))
    return response(WynikRejestracji(access_token, refresh_token, True))# zwracamy do klienta token nowego uzytkownika i true - bo uzytkownik zostal wlasnie dodany

def usunNiepotrzebneDaneUzytkownika(user):
    del user['socialId']
    del user['metodaLogowania']
    del user['socialAccessToken']

def walidujZgodnoscTokenaOrazMailaZFacebookiemLubGoogle(user):
    # email, socialAccessToken (z serwisu społecznościowego), metoda logowania i socialId są wymagane:
    if ('email' and 'socialAccessToken' and 'metodaLogowania' and 'socialId') not in user:
        abort(400)
    if 'id' in user: # Jeśli wprowadzono ID, chcemy zeby nie bylo ono ustawione w bazie:
        del user['id']

    if user['metodaLogowania'] == 'Facebook':
        r = requests.get('https://graph.facebook.com/' + user['socialId'] + '?fields=email&access_token=' + user['socialAccessToken'])
        fbEmail = r.json()['email']
        print(fbEmail + ' =? ' + user['email'])
        if fbEmail != user['email']:
            abort(401)
    elif user['metodaLogowania'] == 'Google':
        r = requests.get('https://www.googleapis.com/plus/v1/people/' + user['socialId'] + '?access_token=' + user['socialAccessToken'])
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

@auth_blueprint.route('/odswiez-token')
@jwt_refresh_token_required
def odswiezToken():
    id_uzytkownika = get_jwt_identity() #funkcja podaje id uzytkownika z tokena.
    print('otrzymane id uzytkownika z tokena: ' + str(id_uzytkownika))
    access_token = str(create_access_token(identity=id_uzytkownika))
    refresh_token = str(create_refresh_token(identity=id_uzytkownika))
    return response(WynikOdswiezeniaTokena(access_token, refresh_token, True))

@auth_blueprint.route('/hello', methods=["POST"])
def hello():
    daneWejsciowe = request.json # imię
    print(daneWejsciowe)
    return response("hello " + daneWejsciowe)
