from flask import Blueprint
from bson.objectid import ObjectId
from project.db import mongo
from flask_jwt_extended import jwt_required
from project.utils import response

users_blueprint = Blueprint(
    'users',
    __name__
)

@jwt_required
@users_blueprint.route('/<string:user_id>', methods=["GET"])
def podajUzytkownika(user_id):
    user = mongo.db.uzytkownicy.find_one({"_id": ObjectId(user_id)})
    return response(user)

@jwt_required
@users_blueprint.route('/<string:user_id>', methods=["PUT"])
def edytujUzytkownika(user_id):
    return 'test'