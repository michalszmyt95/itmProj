from flask import Flask
from project.db import mongo
from project.routes.ranks import ranks_blueprint
from project.routes.users import users_blueprint
from project.routes.tests import tests_blueprint

def create_app():
    app = Flask(__name__)
    app.config['MONGO_DBNAME'] = 'itmProj'
    app.config['MONGO_URI'] = 'mongodb+srv://itmProj:denaturat100%@itmproj-fddwr.mongodb.net/itmProj'
    mongo.init_app(app)
    rejestruj_blueprinty(app)
    rejestruj_index(app)
    return app

# TUTAJ REJESTRUJEMY DALSZE PLIKI Z REST ENDPOINTAMI I DAJEMY IM PREFIKSY URL:
def rejestruj_blueprinty(app):
    app.register_blueprint(ranks_blueprint, url_prefix='/rankingi')
    app.register_blueprint(users_blueprint, url_prefix='/uzytkownik')
    app.register_blueprint(tests_blueprint, url_prefix='/testy')

def rejestruj_index(app):
    @app.route('/', methods=["GET"])
    def index():
        return 'Oto REST-API aplikacji Chuchnij!'

if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
