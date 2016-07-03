package com.diogosimoes.mekaccount.services;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.put;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

public class MekaAPI_2_0 implements IRouterStrategy {
	
	protected GsonBuilder gsonBuilder = new GsonBuilder();
	protected Gson gson;
	protected JsonParser parser = new JsonParser();
	private Object writeLock = new Object();
	
	protected static enum UpdateType {
		ADD,
		REMOVE
	}
	
	@Override
	public String getId() {
		return "api2.0";
	}
	
	public MekaAPI_2_0() {
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
	}

	@Override
	public void publish() {
		/* Print all model*/
        get("/" + getId(), (req, res) -> {
        	return response(Model.dump());
        });
        
        /* Account CRUDs*/
        
        get("/"+ getId() + "/account/:oid", (req, res) -> {
        	Account account = getAccount(req.params(":oid"));
        	return response(account);
        });
        
        /* New endpoint introduced in 2.0 */
        get("/"+ getId() + "/account", (req, res) -> {
        	return response(Account.findAll());
        });
        
        post("/"+ getId() + "/account", (req, res) -> {
        	synchronized (writeLock) {
	        	final Account account = createAccount((JsonObject)parser.parse(req.body()));  
	        	return response(account);
        	}
        });
        
        put("/"+ getId() + "/account/:oid", (req, res) -> {
        	synchronized (writeLock) {
	        	final Account account = getAccount(req.params(":oid"));
	        	updateAccount(account, (JsonObject)parser.parse(req.body()));
	        	return response(account);
        	}
        });
        
        put("/"+ getId() + "/account/:acc_oid/setmeka/:meka_oid", (req, res) -> {
        	synchronized (writeLock) {
	        	final Account account = getAccount(req.params(":acc_oid"));
	        	final Mekamon mekamon = getMekamon(req.params(":meka_oid"));
	        	updateAccount(account, mekamon);
	        	return response(account);
        	}
        });
        
        put("/"+ getId() + "/account/:acc_oid/setnomeka", (req, res) -> {
        	synchronized (writeLock) {
	        	final Account account = getAccount(req.params(":acc_oid"));
	        	updateAccount(account);
	        	return response(account);
        	}
        });
        
        /* Endpoint removed in 2.0 */
        /*
        delete("/"+ getId() + "/account/:oid", (req, res) -> {
        	synchronized (writeLock) {
	        	String oid = req.params(":oid");
	        	final Account account = getAccount(oid);
	        	account.delete();
	        	return String.format("Account #%s was deleted.\n", oid);
        	}
        });
        */
        
        /* Mekamon CRUDs*/
        
        get("/"+ getId() + "/mekamon/:oid", (req, res) -> {
        	Mekamon mekamon = getMekamon(req.params(":oid"));
        	return response(mekamon);
        });
        
        post("/"+ getId() + "/mekamon", (req, res) -> {
        	synchronized (writeLock) {
	        	final Mekamon mekamon = createMekamon((JsonObject)parser.parse(req.body()));
	        	return response(mekamon);
        	}
        });
        
        put("/"+ getId() + "/mekamon/:oid", (req, res) -> {
        	synchronized (writeLock) {
	        	final Mekamon mekamon = getMekamon(req.params(":oid"));
	        	updateMekamon(mekamon, (JsonObject)parser.parse(req.body()));
	        	return response(mekamon);
        	}
        });
        
        delete("/"+ getId() + "/mekamon/:oid", (req, res) -> {
        	synchronized (writeLock) {
	        	String oid = req.params(":oid");
	        	final Mekamon mekamon = getMekamon(oid);
	        	mekamon.delete();
	        	return String.format("Mekamon #%s was deleted.\n", oid);
        	}
        });
        
        /* Battle CRUDs*/
        
        get("/"+ getId() + "/battle/:oid", (req, res) -> {
        	Battle battle = getBattle(req.params(":oid"));
        	return response(battle);
        });
        
        post("/"+ getId() + "/battle", (req, res) -> {
        	synchronized (writeLock) {
	        	final Battle battle = createBattle((JsonObject)parser.parse(req.body()));
	        	return response(battle);
        	}
        });
        
        put("/"+ getId() + "/battle/:oid", (req, res) -> {
        	synchronized (writeLock) {
	        	final Battle battle = getBattle(req.params(":oid"));
	        	updateBattle(battle, (JsonObject)parser.parse(req.body()));
	        	return response(battle);
        	}
        });
        
        put("/"+ getId() + "/battle/:battle_oid/add/:meka_oid", (req, res) -> {
        	synchronized (writeLock) {
	        	final Battle battle = getBattle(req.params(":battle_oid"));
	        	final Mekamon mekamon = getMekamon(req.params(":meka_oid"));
	        	updateBattle(battle, mekamon, UpdateType.ADD);
	        	return response(battle);
        	}
        });
        
        put("/"+ getId() + "/battle/:battle_oid/remove/:meka_oid", (req, res) -> {
        	synchronized (writeLock) {
	        	final Battle battle = getBattle(req.params(":battle_oid"));
	        	final Mekamon mekamon = getMekamon(req.params(":meka_oid"));
	        	updateBattle(battle, mekamon, UpdateType.REMOVE);
	        	return response(battle);
        	}
        });
        
        delete("/"+ getId() + "/battle/:oid", (req, res) -> {
        	synchronized (writeLock) {
	        	String oid = req.params(":oid");
	        	final Battle battle = getBattle(oid);
	        	battle.delete();
	        	return String.format("Battle #%s was deleted.\n", oid);
        	}
        });
	}
	
	protected String response(Collection<? extends DomainObject> model) {
		final ResponseBean bean = new ResponseBean(getId(), model);
		return gson.toJson(bean) + "\n";
	}
	
	protected String response(DomainObject entity) {
		final List<DomainObject> model = new ArrayList<DomainObject>();
		model.add(entity);
		return response(model);
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
    
    private Account createAccount(JsonObject accountConf) {
		final Account account = new Account(accountConf.get("email").getAsString(),
				accountConf.get("name").getAsString(),
				accountConf.get("phone").getAsString());
		accountConf.get("aliases").getAsJsonArray().forEach(el -> account.addAlias(el.getAsString()));
		return account;
    }
    
    private Account updateAccount(Account account, JsonObject accountConf) {
		account.setEmail(accountConf.get("email").getAsString());
		account.setName(accountConf.get("name").getAsString());
		account.setPhone(accountConf.get("phone").getAsString());
		account.clearAliases();
		accountConf.get("aliases").getAsJsonArray().forEach(el -> account.addAlias(el.getAsString()));
		return account;
    }
    
    private Account updateAccount(Account account, Mekamon mekamon) {
		account.setMekamon(mekamon);
		return account;
    }
    
    private Account updateAccount(Account account) {
		account.setMekamon(null);
		return account;
    }
    
    private Mekamon getMekamon(String oid) {
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
    
    private Mekamon createMekamon(JsonObject mekaConf) {
		final Mekamon mekamon = new Mekamon(mekaConf.get("mekaId").getAsString(),
				mekaConf.get("evoLvl").getAsInt());
		return mekamon;
    }
    
    private Mekamon updateMekamon(Mekamon mekamon, JsonObject mekaConf) {
		mekamon.setMekaId(mekaConf.get("mekaId").getAsString());
		mekamon.setEvoLvl(mekaConf.get("evoLvl").getAsInt());
		return mekamon;
    }
    
    private Battle getBattle(String oid) {
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
    
    private Battle createBattle(JsonObject battleConf) {
		final Battle battle = new Battle(battleConf.get("battleId").getAsString());
		return battle;
    }
    
    private Battle updateBattle(Battle battle, JsonObject battleConf) {
		battle.setBattleId(battleConf.get("battleId").getAsString());
		return battle;
    }
    
    private Battle updateBattle(Battle battle, Mekamon mekamon, UpdateType op) {
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
