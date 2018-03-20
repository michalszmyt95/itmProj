Żeby backend chodził, trzeba najpierw odpowiednio przygotować pythona.
Zakładam, że wszyscy działamy na windowsie, dlatego:
Ściągamy z https://www.python.org/ wersje pythona 3.6+ na windowsa i instalujemy.

Następnie musimy pobrać potrzebne biblioteki. Będzie to "mikroframework" Flask, oraz pymongo z flaska.

Aby to zrobić, otwieramy wiersz poleceń (najlepiej jako administrator).
Wstukujemy w nim:
	pip install Flask

Później:
	pip install Flask-PyMongo

I na koniec:
	pip install dnspython

Aby odpalić serwer, należy znajdując się w folderze backend wpisać w wierszu poleceń "python start.py".
