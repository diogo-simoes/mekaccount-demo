package com.diogosimoes.mekaccount;

import static spark.Spark.*;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import spark.Request;
import spark.Route;

import com.diogosimoes.mekaccount.domain.Account;
import com.diogosimoes.mekaccount.domain.Battle;
import com.diogosimoes.mekaccount.domain.Mekamon;
import com.diogosimoes.mekaccount.services.IRouterStrategy;
import com.diogosimoes.mekaccount.services.MekaAPI_1_0;
import com.diogosimoes.mekaccount.services.MekaAPI_2_0;

public class Main {
	
	/* Instantiated routers ordered from latest version to earliest */
	private static Set<IRouterStrategy> routers = new TreeSet<IRouterStrategy>(new Comparator<IRouterStrategy>() {
		@Override
		public int compare(IRouterStrategy r1, IRouterStrategy r2) {
			return r2.getId().compareToIgnoreCase(r1.getId());
		}
	});
	
	private static Route defaultRoute = (req, res) -> {
		filterRecurringRequest(req);
    	String redirectUri = "/" + routers.iterator().next().getId();
    	String uri = String.join("/", req.splat());
    	if (uri != null && uri.length() > 0) {
    		redirectUri += "/" + uri;
    	}
    	res.redirect(redirectUri, 308);
    	return null;
	};

    public static void main(String[] args) {
    	loadRouters();
    	loadInitialModel();
    	
    	/* Wildcards for all requests not specifying api version -> redirects to latest api version */
        get("/*", defaultRoute);        
        post("/*", defaultRoute);        
        put("/*", defaultRoute);        
        delete("/*", defaultRoute);    	
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
    	IRouterStrategy api = new MekaAPI_1_0();
    	routers.add(api);
    	api.publish();
    	
    	api = new MekaAPI_2_0();
    	routers.add(api);
    	api.publish();
    }
    
    private static void filterRecurringRequest(Request req) {
    	if (req.splat() == null || req.splat().length == 0) {
    		return;
    	}
    	String[] path = req.splat()[0].split("/");
    	if (path[0].equals(routers.iterator().next().getId())) {
    		halt(403, "Invalid request!\n");
    	}
    }
    
}