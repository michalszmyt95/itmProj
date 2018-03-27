from bson import ObjectId
from flask import Blueprint
from bson.json_util import dumps
from project.db import mongo

ranks_blueprint = Blueprint(
    'ranks',
    __name__
)

@ranks_blueprint.route('/buttonTest', methods=["GET"])
def podajRankingButtonTestu():
    user = mongo.db.uzytkownicy.find_one({"_id": ObjectId('5abab2745b58012b784c8a31')})
    return dumps(user)