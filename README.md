<h1> Medical Clinic Project </h1>

<<<<<<< HEAD
Projekt przychodni medycznej. Jest utrzymany w formie strony 
internetowej, na którą można się zalogować, umówić wizytę albo 
zmienić swoje dane.

Projekt ma osobną strukturę dla backendu i frontendu.
Link do backendu: https://github.com/krzysztofplociennik/medical-clinic-backend

**Stack**

Przy projekcie korzystałem z:
- java 8
- hibernate
- mysql
- spring boot
- spring security
- vaadin
=======
This medical clinic project has a form of a website where you can login, setup an appointment or change your credentials. 
>>>>>>> d5f668b81410a6edc27d522b9772272d57e17d7c

The project has a separate backend and frontend. Backend:
https://github.com/krzysztofplociennik/medical-clinic-backend

<<<<<<< HEAD
Na chwilę obecną jest 3 użytkowników z następującymi danymi = login | hasło: 
1. jamilyn | jaha
2. mariof | mafe
3. ryahn | ryhi

Dodatkowo można też zalogować się jako admin: 
admin | 123

**Jak wygląda strona?**

Login:

![login](src/main/resources/screenshots/login.png)


Dash użytkownika: 

![login](src/main/resources/screenshots/user%20-%20dashboard.png)

Rezerwowanie wizyt:

![login](src/main/resources/screenshots/user%20-%20appointments1.png)

Można też konkretne wizyty usunąć z bazy, zaznaczając konkretny 
rekord:

![login](src/main/resources/screenshots/user%20-%20appointments2.png)

Strona dostępnych lekarzy (dodatkowo można sprawdzić, jak zostali 
ocenieni oraz sami możemy ocenić ich):

![login](src/main/resources/screenshots/user%20-%20doctors.png)

Na samym końcu mamy możliwość podejrzenia danych zalogowanego
użytkownika oraz ich edycji (oprócz loginu):

![login](src/main/resources/screenshots/user%20-%20settings.png)

Logując się jako admin mamy dodatkowo dostęp do jeszcze jednej 
zakładki na stronie: 

![login](src/main/resources/screenshots/admin%20-%20dashboard.png)

Można w niej zobaczyć listę pacjentów, lekarzy, rezerwacji oraz ocen.
Przy wybraniu konkretnego obiektu pokazują się jego dane:

![login](src/main/resources/screenshots/admin%20-%20settings.png)

oraz możliwość usunięcia z bazy danego obiektu (co też poprzedzone jest
wymaganym potwierdzeniem):

![login](src/main/resources/screenshots/admin%20-%20prompt.png)

**Deploy**

W trakcie...
=======
**Stack**

- java 8
- hibernate
- mysql
- spring boot
- spring security
- vaadin

**How to use?***

For a time being there are 3 users with credentials (login | password): 
1. jamilyn | jaha
2. mariof | mafe
3. ryahn | ryhi

Additionally it is possible to login as an administrator: 
admin | 123

*Currently the website is not deployed.

**How does the website look like?**

Login view:

![login](https://i.imgur.com/XTP1Q5F.png)

User's dash: 

![dash](https://i.imgur.com/fBvBddp.png)

Appointments page:

![reserving1](https://i.imgur.com/CMoKSBb.png)

You can also delete certain records that are highlighted:

![reserving2](https://i.imgur.com/vnc7z37.png)

Available doctors page (you can check their rating as well or rate them yourself):

![doctors](https://i.imgur.com/2MhHQYu.png)

The last tab gives you the ability to see the credentials of the logged user. There's also an option to edit them (username can't be edited):

![settings](https://i.imgur.com/T2sQC82.png)

When logging in as an admin you can spot another tab:

![admin](https://i.imgur.com/gsMYZlC.png)

Here you can check the patients, doctors, appointments and ratings. While selecting a desired record you can see various information about them:

![admin-delete](https://i.imgur.com/le9Udgg.png)

and if you feel like it you can delete the object as well (and then confirm or cancel the decision):

![admin-prompt](https://i.imgur.com/NrymPJa.png)

**Deploy**

In progress...
>>>>>>> d5f668b81410a6edc27d522b9772272d57e17d7c
