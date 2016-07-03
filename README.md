# mekaccount-demo

## Installation ##

The mekaccount back-end is fairly easy to deploy if you have docker installed.
After checking out the project, just open the terminal, go to the mekaccount directory and type

```
  docker build -t diogosimoes/mekaccount .
```

This will create the docker image. The first time you run it, it will take a bit because maven will download all the dependencies it needs and build the project.

Once this is done, simply type in

```
  docker run -d -p 4567:4567 diogosimoes/mekaccount
```

and you will have a container running mekaccount.


To see if everything is fine, list all running containers

```
  docker ps
```

and check if **diogosimoes/mekaccount** is listed.

Now you can query the server. Try listing the whole model by typing

```
  curl -L localhost:4567
```


## Domain Entities ##

```
  	+---------------+  1		 1	+---------------+  *		  *	+---------------+
	|    ACCOUNT	|_______________|    MEKAMON	|_______________|     BATTLE	|
	+---------------+				+---------------+				+---------------+
	|				|				|				|				|				|
	|	-email		|				|	-mekaId		|				|	-battleId	|
	|	-name		|				|	-evoLvl		|				|				|
	|	-phone		|				|	-addons		|				|				|
	|	-aliases	|				|				|				|				|
	|				|				|				|				|				|
	|				|				|				|				|				|
	+---------------+				+---------------+				+---------------+
```

## API Endpoints ##

### API v1.0 ###

1. List all domain objects: `curl localhost:4567/api1.0`  
   Returns a json object representing the entire model.

2. Get specific account: `curl localhost:4567/api1.0/account/<account_oid>`  
   Returns a json object representing the requested account.

3. Create a new account: `curl -X POST -H "Content-Type: application/json" -d '{"email": "dummy@domain.tld","name": "dummy","phone": "555-9876","aliases": ["dumy", "dummie"]}' localhost:4567/api1.0/account`  
   Returns a json object representing the new account.

4. Update an account: `curl -X PUT -H "Content-Type: application/json" -d '{"email": "dummy2@domain.tld","name": "dummy2","phone": "555-9876","aliases": ["dumy2", "dummie2"]}' localhost:4567/api1.0/account/<account_oid>`  
   Returns a json object representing the updated account.

5. Set a new mekamon for a given account: `curl -X PUT localhost:4567/api1.0/account/<account_oid>/setmeka/<mekamon>`  
   Returns a json object representing the updated account.

6. Remove the mekamon of a given account: `curl -X PUT localhost:4567/api1.0/account/<account_oid>/setnomeka`  
   Returns a json object representing the updated account.

7. Delete an account: `curl -X DELETE localhost:4567/api1.0/account/<account_oid>`  
   Returns a status message.

8. Get specific mekamon: `curl localhost:4567/api1.0/mekamon/<mekamon_oid>`  
   Returns a json object representing the requested mekamon.

9. Create a new mekamon: `curl -X POST -H "Content-Type: application/json" -d '{"mekaId": "Mark02","evoLvl": 2}' localhost:4567/api1.0/mekamon`  
   Returns a json object representing the new mekamon.

10. Update a mekamon: `curl -X PUT -H "Content-Type: application/json" -d '{"mekaId": "Mark02","evoLvl": 5}' localhost:4567/api1.0/mekamon/<mekamon_oid>`  
   Returns a json object representing the updated mekamon.

11. Delete a mekamon: `curl -X DELETE localhost:4567/api1.0/mekamon/<mekamon_oid>`  
   Returns a status message.

12. Get specific battle: `curl localhost:4567/api1.0/battle/<battle_oid>`  
   Returns a json object representing the requested battle.

13. Create a new battle: `curl -X POST -H "Content-Type: application/json" -d '{"battleId": "UltimateChallenge#REDBLUE"}' localhost:4567/api1.0/battle`  
   Returns a json object representing the new battle.

14. Update a battle: `curl -X PUT -H "Content-Type: application/json" -d '{"battleId": "RegionalFinals"}' localhost:4567/api1.0/battle/<battle_oid>`  
   Returns a json object representing the updated battle.

15. Add a new mekamon to a battle: `curl -X PUT localhost:4567/api1.0/battle/<battle_oid>/add/<mekamon_oid>`  
   Returns a json object representing the updated battle.

16. Remove a mekamon from a battle: `curl -X PUT localhost:4567/api1.0/battle/<battle_oid>/remove/<mekamon_oid>`  
   Returns a json object representing the updated battle.

17. Delete a battle: `curl -X DELETE localhost:4567/api1.0/battle/<battle_oid>`  
   Returns a status message.


### API v2.0 ###

1. List all domain objects: `curl localhost:4567/api2.0`  
   Returns a json object representing the entire model.

2. **NEW** List all accounts: `curl localhost:4567/api2.0/acccount`  
   Return a json object containing all the accounts.

3. Get specific account: `curl localhost:4567/api2.0/account/<account_oid>`  
   Returns a json object representing the requested account.

4. Create a new account: `curl -X POST -H "Content-Type: application/json" -d '{"email": "dummy@domain.tld","name": "dummy","phone": "555-9876","aliases": ["dumy", "dummie"]}' localhost:4567/api2.0/account`  
   Returns a json object representing the new account.

5. Update an account: `curl -X PUT -H "Content-Type: application/json" -d '{"email": "dummy2@domain.tld","name": "dummy2","phone": "555-9876","aliases": ["dumy2", "dummie2"]}' localhost:4567/api2.0/account/<account_oid>`  
   Returns a json object representing the updated account.

6. Set a new mekamon for a given account: `curl -X PUT localhost:4567/api2.0/account/<account_oid>/setmeka/<mekamon>`  
   Returns a json object representing the updated account.

7. Remove the mekamon of a given account: `curl -X PUT localhost:4567/api2.0/account/<account_oid>/setnomeka`  
   Returns a json object representing the updated account.

7. **REMOVED** ~~Delete an account: `curl -X DELETE localhost:4567/api2.0/account/<account_oid>`  
   Returns a status message.~~

8. Get specific mekamon: `curl localhost:4567/api2.0/mekamon/<mekamon_oid>`  
   Returns a json object representing the requested mekamon.

9. Create a new mekamon: `curl -X POST -H "Content-Type: application/json" -d '{"mekaId": "Mark02","evoLvl": 2}' localhost:4567/api2.0/mekamon`  
   Returns a json object representing the new mekamon.

10. Update a mekamon: `curl -X PUT -H "Content-Type: application/json" -d '{"mekaId": "Mark02","evoLvl": 5}' localhost:4567/api2.0/mekamon/<mekamon_oid>`  
   Returns a json object representing the updated mekamon.

11. Delete a mekamon: `curl -X DELETE localhost:4567/api2.0/mekamon/<mekamon_oid>`  
   Returns a status message.

12. Get specific battle: `curl localhost:4567/api2.0/battle/<battle_oid>`  
   Returns a json object representing the requested battle.

13. Create a new battle: `curl -X POST -H "Content-Type: application/json" -d '{"battleId": "UltimateChallenge#REDBLUE"}' localhost:4567/api2.0/battle`  
   Returns a json object representing the new battle.

14. Update a battle: `curl -X PUT -H "Content-Type: application/json" -d '{"battleId": "RegionalFinals"}' localhost:4567/api2.0/battle/<battle_oid>`  
   Returns a json object representing the updated battle.

15. Add a new mekamon to a battle: `curl -X PUT localhost:4567/api2.0/battle/<battle_oid>/add/<mekamon_oid>`  
   Returns a json object representing the updated battle.

16. Remove a mekamon from a battle: `curl -X PUT localhost:4567/api2.0/battle/<battle_oid>/remove/<mekamon_oid>`  
   Returns a json object representing the updated battle.

17. Delete a battle: `curl -X DELETE localhost:4567/api2.0/battle/<battle_oid>`  
   Returns a status message.


### API - Agnostic mode ###

You don't have to specify an API version when formulating your request. If the version is omitted, the server will answer with the most recent version. For this to happen, however, make sure you tell cURL to answer redirects by using the flag **_-L_**, as in
```
  curl -L localhost:4567
```


## Technical considerations ##

Concurrency is dealt with in a very simple manner. Writing operations share a lock that gives them access to change the model state. Reading operations are allowed in at all times to avoid a higher overhead, with the trade-off being no guaranteed consistency on model views. In between writes though, the model is consistent. The only exception being if something goes wrong during a writing action. It basically lacks a rollback mechanism.

In resume, writes are not ACID, lacking a rollback mechanism. That mechanism could help solve all the other concurrency issues identified, leading on to the implementation of a _Read-copy-update_. Nevertheless, developing a Transactional Engine would be a whole project on its own, which would have clearly exceeded the aim of this demo.
