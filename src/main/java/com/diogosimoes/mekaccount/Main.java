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

    public static void main(String[] args) {
    	init();
        get("/", (req, res) -> {
            return serialize(Model.dump().collect(Collectors.toSet()));
        });
        
        get("/account/:oid", (req, res) -> {
        	Account account = null;
        	String oid = req.params(":oid");
        	try {
        		account = Model.find(oid);
        	} catch (NullPointerException | ClassCastException e) {
        		halt(403, "Invalid request!\n");
        	}
        	if (account == null) {
        		halt(403, "Invalid request!\n");
        	}
        	return gson.toJson(account);
        });
        
        post("/account", (req, res) -> {
        	final Account account = createAccount((JsonObject)parser.parse(req.body()));  
        	return gson.toJson(account);
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
    	
    	new Battle(meka00, meka01);
    	
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
    
    private static String serialize(Set<DomainObject> model) {
    	return gson.toJson(model) + "\n";
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
}