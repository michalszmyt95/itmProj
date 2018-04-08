class WynikOperacji:
    def __init__(self, id=None, wynik=False):
        self.id = str(id)
        self.wynik = wynik

class WynikRejestracji:
    def __init__(self, accessToken=None, refreshToken=None, dodanoUzytkownika=False):
        self.accessToken = accessToken
        self.refreshToken = refreshToken
        self.dodanoUzytkownika = dodanoUzytkownika

class WynikOdswiezeniaTokena:
    def __init__(self, accessToken=None, refreshToken=None, odswiezonoPoprawnie=False):
        self.accessToken = accessToken
        self.refreshToken = refreshToken
        self.odswiezonoPoprawnie = odswiezonoPoprawnie