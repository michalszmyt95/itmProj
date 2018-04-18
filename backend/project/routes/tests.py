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
    wynikTestu = request.json #pobranie danych ¿¹dania od androida - pobrane dane maj¹ strukturê s³ownikow¹ (mapa)
    print('headers: ' + str(request.headers)) #wyœwietlenie w konsoli danych wejœciowych z ¿adania
    print('REQUEST ' + str(wynikTestu)) #wyœwietlenie w konsoli danych wejœciowych z ¿adania

    # skoro jest to s³ownik, mo¿emy odwo³aæ siê do wartoœci poprzez mechanizm slownik['klucz'] -> wartoœæ.
    # w tym przypadku przypisujemy do klucza 'test' wartoœæ 'buttonTest', by w bazie danych by³o wiadomo który to by³ test.
    wynikTestu['test'] = 'buttonTest'

    user_id = get_jwt_identity() #dziêki u¿yciu ma³pki @jwt_required mo¿emy u¿yæ tej metody by wy³uskaæ id u¿ytkownika który wys³a³ ¿¹danie.
    user = podajUzytkownikaPoId(user_id) #metoda w project/utils zwracaj¹ca obiekt u¿ytkownika po jego id.

    wynikTestu['user_id'] = user_id # przypisanie do wyniku testu id u¿ytkownika

    print('Dane uzytkownika pobranego z bazy przy pomocy jwt tokena: ' + str(user)) #wyswietlenie kontrolne danych w konsoli

    #przypisania aktualnych danych uzytkownika do wyniku testu:
    wynikTestu['waga'] = user['waga']
    wynikTestu['wiek'] = user['wiek']
    wynikTestu['wzrost'] = user['wzrost']

    mongo.db.buttonTest.insert_one(wynikTestu) #wykonanie insertu do bazy danych

    # zwracamy dane uzywajac metody response() z project/utils. Metoda ta zamienia ka¿dy obiekt z pythona na JSON.
    # jest to potrzebne, by android móg³ w poprawny sposób otrzymaæ dane.
    # obiekt który zwracamy, to WynikOperacji z project/models/dto.py który pozwala na zdefiniowanie identyfikatora oraz informacji boolowskiej.
    # po stronie androida musi byæ utworzona klasa implementuj¹ca w analogiczny sposób obiekt jak ten po stronie pythona.
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