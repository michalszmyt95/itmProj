from flask import request, Blueprint
from flask_pymongo import ObjectId
from project.db import mongo
from project.models.dto import WynikOperacji
from flask_jwt_extended import jwt_required, get_jwt_identity
from project.utils import response, now

users_blueprint = Blueprint(
    'users',
    __name__
)


@users_blueprint.route('/profil', methods=["POST"])
@jwt_required
def aktualizujProfil():
    daneProfilu = request.json #dane typu waga, wzrost itp
    print('dane profilu' + str(daneProfilu))
    daneProfilu['data_aktualizacji'] = now()
    print('dane profilu' + str(daneProfilu))
    res = mongo.db.uzytkownicy.update_one({'_id': ObjectId(get_jwt_identity())}, {'$set': daneProfilu})
    print('wynik update: ' + str(res))
    return response(WynikOperacji("ustawProfil", True))


@users_blueprint.route('/profil', methods=["GET"])
@jwt_required
def podajDaneProfilu():
    user_id = ObjectId(get_jwt_identity())
    dane_z_bazy = mongo.db.uzytkownicy.find_one({'_id': user_id})
    return response(dane_z_bazy)
