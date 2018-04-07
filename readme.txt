Żeby backend chodził, trzeba najpierw odpowiednio przygotować pythona.
Zakładam, że wszyscy działamy na windowsie, dlatego:
Ściągamy z https://www.python.org/ wersje pythona 3.6+ na windowsa i instalujemy.

Możliwe, że nie ustawiła się odpowiednia zmienna środowiskowa PATH. Dla pewności lepiej wejść na zmienne środowiskowe i do zmiennej PATH dodać ścieżkę do folderu z pythonem i do folderu ze skryptami z folderu pythona.

Aby to zrobić, należy wcisnąć WIN + R. Wyskoczy okienko "Uruchom".
Należy wpisać w okienku "rundll32 sysdm.cpl,EditEnvironmentVariables" i kliknąć OK.
Pojawi się okno edycji zmiennych środowiskowych. Wybrać zmienną PATH i ustawić tam odpowiednie ścieżki w których są pliki pythona. W moim przypadku było to C:\Users\user\AppData\Local\Programs\Python\Python36 oraz C:\Users\user\AppData\Local\Programs\Python\Python36\Scripts.

Następnie musimy pobrać potrzebne biblioteki. Będzie to "mikroframework" Flask, oraz pymongo z flaska.

Aby to zrobić, otwieramy wiersz poleceń (najlepiej jako administrator).
Wstukujemy w nim:
	pip install Flask
	pip install Flask-PyMongo
	pip install flask-hashing
	pip install -U jsonpickle
	pip install dnspython
	pip install requests
	pip install flask-jwt-extended

Aby odpalić serwer, należy uruchomić plik run.bat znajdujący się w folderze backend.
