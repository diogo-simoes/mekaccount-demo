package com.diogosimoes.mekaccount;

import static spark.Spark.*;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

import com.diogosimoes.mekaccount.domain.Account;
import com.diogosimoes.mekaccount.domain.Battle;
import com.diogosimoes.mekaccount.domain.DomainObject;
import com.diogosimoes.mekaccount.domain.Mekamon;
import com.diogosimoes.mekaccount.domain.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class Main {
	
	private static GsonBuilder gsonBuilder = new GsonBuilder();
	private static Gson gson;
	private static JsonParser parser = new JsonParser();
	private static Object writeLock = new Object();
	
	protected static enum UpdateType {
		ADD,
		REMOVE
	}

    public static void main(String[] args) {
    	init();
    	
    	/* Print all model*/
        get("/", (req, res) -> {
        	return gson.toJson(Model.dump()) + "\n";
        });
        
        /* Account CRUDs*/
        
        get("/account/:oid", (req, res) -> {
        	Account account = getAccount(req.params(":oid"));
        	return gson.toJson(account) + "\n";
        });
        
        post("/account", (req, res) -> {
        	final Account account = createAccount((JsonObject)parser.parse(req.body()));  
        	return gson.toJson(account) + "\n";
        });
        
        put("/account/:oid", (req, res) -> {
        	final Account account = getAccount(req.params(":oid"));
        	updateAccount(account, (JsonObject)parser.parse(req.body()));
        	return gson.toJson(account) + "\n";
        });
        
        put("/account/:acc_oid/setmeka/:meka_oid", (req, res) -> {
        	final Account account = getAccount(req.params(":acc_oid"));
        	final Mekamon mekamon = getMekamon(req.params(":meka_oid"));
        	updateAccount(account, mekamon);
        	return gson.toJson(account) + "\n";
        });
        
        put("/account/:acc_oid/setnomeka", (req, res) -> {
        	final Account account = getAccount(req.params(":acc_oid"));
        	updateAccount(account);
        	return gson.toJson(account) + "\n";
        });
        
        delete("/account/:oid", (req, res) -> {
        	String oid = req.params(":oid");
        	final Account account = getAccount(oid);
        	account.delete();
        	return String.format("Account #%s was deleted.\n", oid);
        });
        
        /* Mekamon CRUDs*/
        
        get("/mekamon/:oid", (req, res) -> {
        	Mekamon mekamon = getMekamon(req.params(":oid"));
        	return gson.toJson(mekamon) + "\n";
        });
        
        post("/mekamon", (req, res) -> {
        	final Mekamon mekamon = createMekamon((JsonObject)parser.parse(req.body()));  
        	return gson.toJson(mekamon) + "\n";
        });
        
        put("/mekamon/:oid", (req, res) -> {
        	final Mekamon mekamon = getMekamon(req.params(":oid"));
        	updateMekamon(mekamon, (JsonObject)parser.parse(req.body()));
        	return gson.toJson(mekamon) + "\n";
        });
        
        delete("/mekamon/:oid", (req, res) -> {
        	String oid = req.params(":oid");
        	final Mekamon mekamon = getMekamon(oid);
        	mekamon.delete();
        	return String.format("Mekamon #%s was deleted.\n", oid);
        });
        
        /* Battle CRUDs*/
        
        get("/battle/:oid", (req, res) -> {
        	Battle battle = getBattle(req.params(":oid"));
        	return gson.toJson(battle) + "\n";
        });
        
        post("/battle", (req, res) -> {
        	final Battle battle = createBattle((JsonObject)parser.parse(req.body()));  
        	return gson.toJson(battle) + "\n";
        });
        
        put("/battle/:oid", (req, res) -> {
        	final Battle battle = getBattle(req.params(":oid"));
        	updateBattle(battle, (JsonObject)parser.parse(req.body()));
        	return gson.toJson(battle) + "\n";
        });
        
        put("/battle/:battle_oid/add/:meka_oid", (req, res) -> {
        	final Battle battle = getBattle(req.params(":battle_oid"));
        	final Mekamon mekamon = getMekamon(req.params(":meka_oid"));
        	updateBattle(battle, mekamon, UpdateType.ADD);
        	return gson.toJson(battle) + "\n";
        });
        
        put("/battle/:battle_oid/remove/:meka_oid", (req, res) -> {
        	final Battle battle = getBattle(req.params(":battle_oid"));
        	final Mekamon mekamon = getMekamon(req.params(":meka_oid"));
        	updateBattle(battle, mekamon, UpdateType.REMOVE);
        	return gson.toJson(mekamon) + "\n";
        });
        
        delete("/battle/:oid", (req, res) -> {
        	String oid = req.params(":oid");
        	final Battle battle = getBattle(oid);
        	battle.delete();
        	return String.format("Battle #%s was deleted.\n", oid);
        });
    }
    
    protected static void init() {
    	gsonBuilder.registerTypeHierarchyAdapter(DomainObject.class, new JsonSerializer<DomainObject>() {
			@Override
			public JsonElement serialize(DomainObject src, Type typeOfSrc,
					JsonSerializationContext context) {
				return src.serialize();
			}
		});
    	gsonBuilder.registerTypeHierarchyAdapter(DomainObject.class, new JsonDeserializer<DomainObject>() {
			@Override
			public DomainObject deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context)
					throws JsonParseException {
				return Model.find(json.getAsJsonPrimitive().getAsString());
			}
		});
    	gsonBuilder.setPrettyPrinting();
    	gson = gsonBuilder.create();
    	
    	final Account diogo = new Account("diogo@domain.tld", "diogo", "555-0123");
    	final Mekamon meka00 = new Mekamon("Mark00", 3);
    	diogo.setMekamon(meka00);
    	
    	final Account jonathan = new Account("jonathan@domain.tld", "jonathan", "555-9999");
    	final Mekamon meka01 = new Mekamon("Mark01", 4);
    	jonathan.setMekamon(meka01);
    	
    	new Battle("battle#DIOJON", meka00, meka01);
    	
    	/*account = new Account();
    	account.setName("John Doe");
    	Thread[] threads = new Thread[100];
    	
    	for (int i = 0; i < 100; i++) {
    		Runnable worker = () -> {
    			final Mekamon mekamon = new Mekamon();
    			mekamon.setAccount(account);
    		};
    		final Thread t = new Thread(worker);
    		t.start();
    		threads[i] = t;
    	}
    	
    	for (int i = 0; i < 100; i++) {
    		try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	*/
    }
    
    private static Account getAccount(String oid) {
    	Account account = null;
    	try {
    		account = Model.find(oid);
    	} catch (NullPointerException | ClassCastException e) {
    		halt(403, "Invalid request!\n");
    	}
    	if (account == null) {
    		halt(403, "Invalid request!\n");
    	}
    	return account;
    }
    
    private static Account createAccount(JsonObject accountConf) {
    	synchronized (writeLock) {
    		final Account account = new Account(accountConf.get("email").getAsString(),
    				accountConf.get("name").getAsString(),
    				accountConf.get("phone").getAsString());
    		accountConf.get("aliases").getAsJsonArray().forEach(el -> account.addAlias(el.getAsString()));
    		return account;
		}
    }
    
    private static Account updateAccount(Account account, JsonObject accountConf) {
    	synchronized (writeLock) {
    		account.setEmail(accountConf.get("email").getAsString());
    		account.setName(accountConf.get("name").getAsString());
    		account.setPhone(accountConf.get("phone").getAsString());
    		account.clearAliases();
    		accountConf.get("aliases").getAsJsonArray().forEach(el -> account.addAlias(el.getAsString()));
    		return account;
		}
    }
    
    private static Account updateAccount(Account account, Mekamon mekamon) {
    	synchronized (writeLock) {
    		account.setMekamon(mekamon);
    		return account;
		}
    }
    
    private static Account updateAccount(Account account) {
    	synchronized (writeLock) {
    		account.setMekamon(null);
    		return account;
		}
    }
    
    private static Mekamon getMekamon(String oid) {
    	Mekamon mekamon = null;
    	try {
    		mekamon = Model.find(oid);
    	} catch (NullPointerException | ClassCastException e) {
    		halt(403, "Invalid request!\n");
    	}
    	if (mekamon == null) {
    		halt(403, "Invalid request!\n");
    	}
    	return mekamon;
    }
    
    private static Mekamon createMekamon(JsonObject mekaConf) {
    	synchronized (writeLock) {
    		final Mekamon mekamon = new Mekamon(mekaConf.get("mekaId").getAsString(),
    				mekaConf.get("evoLvl").getAsInt());
    		return mekamon;
		}
    }
    
    private static Mekamon updateMekamon(Mekamon mekamon, JsonObject mekaConf) {
    	synchronized (writeLock) {
    		mekamon.setMekaId(mekaConf.get("mekaId").getAsString());
    		mekamon.setEvoLvl(mekaConf.get("evoLvl").getAsInt());
    		return mekamon;
		}
    }
    
    private static Battle getBattle(String oid) {
    	Battle battle = null;
    	try {
    		battle = Model.find(oid);
    	} catch (NullPointerException | ClassCastException e) {
    		halt(403, "Invalid request!\n");
    	}
    	if (battle == null) {
    		halt(403, "Invalid request!\n");
    	}
    	return battle;
    }
    
    private static Battle createBattle(JsonObject battleConf) {
    	synchronized (writeLock) {
    		final Battle battle = new Battle(battleConf.get("battleId").getAsString());
    		return battle;
		}
    }
    
    private static Battle updateBattle(Battle battle, JsonObject battleConf) {
    	synchronized (writeLock) {
    		battle.setBattleId(battleConf.get("battleId").getAsString());
    		return battle;
		}
    }
    
    private static Battle updateBattle(Battle battle, Mekamon mekamon, UpdateType op) {
    	synchronized (writeLock) {
    		switch(op) {
    		case ADD:
    			battle.addMekamon(mekamon);
    			break;
    		case REMOVE:
    			battle.removeMekamon(mekamon);
    			break;
    		}    		
    		return battle;
		}
    }
}