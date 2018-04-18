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
    wynikTestu = request.json #pobranie danych żądania od androida - pobrane dane mają strukturę słownikową (mapa)
    print('headers: ' + str(request.headers)) #wyświetlenie w konsoli danych wejściowych z żadania
    print('REQUEST ' + str(wynikTestu)) #wyświetlenie w konsoli danych wejściowych z żadania

    # skoro jest to słownik, możemy odwołać się do wartości poprzez mechanizm slownik['klucz'] -> wartość.
    # w tym przypadku przypisujemy do klucza 'test' wartość 'buttonTest', by w bazie danych było wiadomo który to był test.
    wynikTestu['test'] = 'buttonTest'

    user_id = get_jwt_identity() #dzięki użyciu małpki @jwt_required możemy użyć tej metody by wyłuskać id użytkownika który wysłał żądanie.
    user = podajUzytkownikaPoId(user_id) #metoda w project/utils zwracająca obiekt użytkownika po jego id.

    wynikTestu['user_id'] = user_id # przypisanie do wyniku testu id użytkownika

    print('Dane uzytkownika pobranego z bazy przy pomocy jwt tokena: ' + str(user)) #wyswietlenie kontrolne danych w konsoli

    #przypisania aktualnych danych uzytkownika do wyniku testu:
    wynikTestu['waga'] = user['waga']
    wynikTestu['wiek'] = user['wiek']
    wynikTestu['wzrost'] = user['wzrost']

    mongo.db.buttonTest.insert_one(wynikTestu) #wykonanie insertu do bazy danych

    # zwracamy dane uzywajac metody response() z project/utils. Metoda ta zamienia każdy obiekt z pythona na JSON.
    # jest to potrzebne, by android mógł w poprawny sposób otrzymać dane.
    # obiekt który zwracamy, to WynikOperacji z project/models/dto.py który pozwala na zdefiniowanie identyfikatora oraz informacji boolowskiej.
    # po stronie androida musi być utworzona klasa implementująca w analogiczny sposób obiekt jak ten po stronie pythona.
    return response(WynikOperacji("buttonTestWynik", True))
