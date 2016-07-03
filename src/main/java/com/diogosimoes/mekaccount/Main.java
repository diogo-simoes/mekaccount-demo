package com.diogosimoes.mekaccount;

import static spark.Spark.*;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.diogosimoes.mekaccount.domain.Account;
import com.diogosimoes.mekaccount.domain.Battle;
import com.diogosimoes.mekaccount.domain.Mekamon;

public class Main {
	/* Instanciated routers ordered from latest version to earliest */
	private static Set<IRouterStrategy> routers = new TreeSet<IRouterStrategy>(new Comparator<IRouterStrategy>() {
		@Override
		public int compare(IRouterStrategy r1, IRouterStrategy r2) {
			return r2.getId().compareToIgnoreCase(r1.getId());
		}
	});

    public static void main(String[] args) {
    	loadRouters();
    	loadInitialModel();
    	
    	/* Wildcards for all requests not specifying api version -> redirects to latest api version */
        get("/*", (req, res) -> {
        	String apiVersion = routers.iterator().next().getId();
        	res.redirect("/" + apiVersion + "/" + String.join("/", req.splat()));
        	return null;
        });
        
        post("/*", (req, res) -> {
        	String apiVersion = routers.iterator().next().getId();
        	res.redirect("/" + apiVersion + "/" + String.join("/", req.splat()));
        	return null;
        });
        
        put("/*", (req, res) -> {
        	String apiVersion = routers.iterator().next().getId();
        	res.redirect("/" + apiVersion + "/" + String.join("/", req.splat()));
        	return null;
        });
        
        delete("/*", (req, res) -> {
        	String apiVersion = routers.iterator().next().getId();
        	res.redirect("/" + apiVersion + "/" + String.join("/", req.splat()));
        	return null;
        });    	
    }
    
    private static void loadInitialModel() {
    	final Account diogo = new Account("diogo@domain.tld", "diogo", "555-0123");
    	final Mekamon meka00 = new Mekamon("Mark00", 3);
    	diogo.setMekamon(meka00);
    	
    	final Account jonathan = new Account("jonathan@domain.tld", "jonathan", "555-9999");
    	final Mekamon meka01 = new Mekamon("Mark01", 4);
    	jonathan.setMekamon(meka01);
    	
    	new Battle("battle#DIOJON", meka00, meka01);
    }
    
    private static void loadRouters() {
    	MekaAPI_1_0 api = new MekaAPI_1_0();
    	routers.add(api);
    	api.publish();
    }
    
    
}