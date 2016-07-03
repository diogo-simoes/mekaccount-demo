# mekaccount-demo

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