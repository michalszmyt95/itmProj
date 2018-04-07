from flask import request, Blueprint
from bson.objectid import ObjectId
from project.db import mongo
from flask_jwt_extended import jwt_required
from project.utils import response

tests_blueprint = Blueprint(
    'tests',
    __name__
)

@jwt_required
@tests_blueprint.route('/buttonTest', methods=["POST"])
def dodajWynikButtontestu():
    res = request.json
    return response(res)

@jwt_required
@tests_blueprint.route('/ranking/buttonTest', methods=["GET"])
def podajRankingButtonTestu():
    res = mongo.db.uzytkownicy.find_one({"_id": ObjectId()})
    return response(res)