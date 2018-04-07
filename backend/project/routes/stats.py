from bson import ObjectId
from flask import Blueprint
from project.db import mongo
from flask_jwt_extended import jwt_required
from project.utils import response

stats_blueprint = Blueprint(
    'stats',
    __name__
)

@jwt_required
@stats_blueprint.route('/buttonTest', methods=["GET"])
def podajRankingButtonTestu():
    res = mongo.db.uzytkownicy.find_one({"_id": ObjectId('5abab2745b58012b784c8a31')})
    return response(res)