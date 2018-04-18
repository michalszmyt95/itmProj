from flask import request, Blueprint
from flask_jwt_extended import jwt_required, get_jwt_identity
from project.db import mongo
from project.utils import response, podajUzytkownikaPoId

alkohole_blueprint = Blueprint(
    'alkohole',
    __name__
)


@alkohole_blueprint.route('/buttonAlkohole', methods=["POST"])
@jwt_required
def dodajAlkohole():
    alkohole = request.json
    print('headers: ' + str(request.headers))
    print('REQUEST ' + str(alkohole))

    user_id = get_jwt_identity()
    user = podajUzytkownikaPoId(user_id)

    alkohole['user_id'] = user_id

    print('Dane uzytkownika pobranego z bazy przy pomocy jwt tokena: ' + str(user))


    mongo.db.buttonAlkohole.insert_one(alkohole)

    return response('OK')