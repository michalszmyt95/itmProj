from flask import request, Blueprint
from flask_jwt_extended import jwt_required, get_jwt_identity
from project.db import mongo
from project.models.dto import WynikOperacji
from project.utils import response, podajUzytkownikaPoId

tests_blueprint = Blueprint(
    'tests',
    __name__
)


@tests_blueprint.route('/buttonTest', methods=["POST"])
@jwt_required
def dodajWynikButtontestu():
    wynikTestu = request.json #pobranie danych zadania od androida - pobrane dane maja strukture slownikowa (mapa)
    print('headers: ' + str(request.headers)) #wyswietlenie w konsoli danych wejeciowych z zadania
    print('REQUEST ' + str(wynikTestu)) #wyswietlenie w konsoli danych wejsciowych z zadania

    # skoro jest to slownik, mozemy odwolac sie do wartosci poprzez mechanizm slownik['klucz'] -> wartosc.
    # w tym przypadku przypisujemy do klucza 'test' wartosc 'buttonTest', by w bazie danych bylo wiadomo ktory to byl test.
    wynikTestu['test'] = 'buttonTest'

    user_id = get_jwt_identity() #dzieki uzyciu malpki @jwt_required mozemy uzyc tej metody by wyluskac id uzytkownika ktory wyslal zadanie.
    user = podajUzytkownikaPoId(user_id) #metoda w project/utils zwracajaca obiekt uzytkownika po jego id.

    wynikTestu['user_id'] = user_id # przypisanie do wyniku testu id uzytkownika

    print('Dane uzytkownika pobranego z bazy przy pomocy jwt tokena: ' + str(user)) #wyswietlenie kontrolne danych w konsoli

    #przypisania aktualnych danych uzytkownika do wyniku testu:
    wynikTestu['waga'] = user['waga']
    wynikTestu['wiek'] = user['wiek']
    wynikTestu['wzrost'] = user['wzrost']

    mongo.db.buttonTest.insert_one(wynikTestu) #wykonanie insertu do bazy danych

    # zwracamy dane uzywajac metody response() z project/utils. Metoda ta zamienia kazdy obiekt z pythona na JSON.
    # jest to potrzebne, by android mogl w poprawny sposob otrzymac dane.
    # obiekt ktory zwracamy, to WynikOperacji z project/models/dto.py ktory pozwala na zdefiniowanie identyfikatora oraz informacji boolowskiej.
    # po stronie androida musi byc utworzona klasa implementujaca w analogiczny sposob obiekt jak ten po stronie pythona.
    return response(WynikOperacji("buttonTestWynik", True))

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
