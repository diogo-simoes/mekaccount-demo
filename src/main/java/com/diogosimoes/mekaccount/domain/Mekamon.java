package com.diogosimoes.mekaccount.domain;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Mekamon extends DomainObject {
	
	private String mekaId;
	private int evoLvl;
	private Set<String> addons;
	
	private Account account;
	private Set<Battle> battles;
	
	public Mekamon() {
		super();
		evoLvl = 1;
		addons = new HashSet<String>();
		battles = new HashSet<Battle>();
	}
	
	public Mekamon(String mekaId, int evoLvl) {
		this();
		setMekaId(mekaId);
		setEvoLvl(evoLvl);
	}
	
	public String getMekaId() {
		return mekaId;
	}
	public void setMekaId(String mekaId) {
		this.mekaId = mekaId;
	}
	public int getEvoLvl() {
		return evoLvl;
	}
	public void setEvoLvl(int evoLvl) {
		this.evoLvl = evoLvl;
	}
	public Set<String> getAddons() {
		return addons;
	}
	
	public void addAddon(String addon) {
		addons.add(addon);
	}
	
	public void removeAdoon(String addon) {
		addons.remove(addon);
	}
	
	public void clearAddons() {
		addons.clear();
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		final Account oldAcc = this.account;
		final Account newAcc = account;
		if (oldAcc == newAcc) {
			return;
		}
		if (oldAcc != null) {
			this.account = null;
			oldAcc.$set$Mekamon(null);
		}
		this.account = newAcc;
		if (newAcc != null) {
			newAcc.$set$Mekamon(this);
		}
	}
	
	void $set$Account(Account account) {
		if (this.account != null) {
			final Account oldAcc = this.account;
			this.account = null;
			oldAcc.$set$Mekamon(null);
		}
		this.account = account;
	}
	
	public Set<Battle> getBattles() {
		return battles;
	}
	
	public void addBattle(Battle battle) {
		if (battles.contains(battle)) {
			return;
		}
		battles.add(battle);
		battle.$add$Mekamon(this);
	}
	
	void $add$Battle(Battle battle) {
		battles.add(battle);
	}
	
	public void removeBattle(Battle battle) {
		if (!battles.contains(battle)) {
			return;
		}
		battles.remove(battle);
		battle.$remove$Mekamon(this);
	}
	
	void $remove$Battle(Battle battle) {
		battles.remove(battle);
	}
	
	@Override
	public JsonElement serialize() {
		final JsonObject mekamon = new JsonObject();
		mekamon.add("oid", new JsonPrimitive(getOid()));
		mekamon.add("mekaId", new JsonPrimitive(getMekaId() != null ? getMekaId() : ""));
		mekamon.add("evoLvl", new JsonPrimitive(getEvoLvl()));
		
		final JsonArray addons = new JsonArray();
		mekamon.add("addons", addons);
		for (String addon : getAddons()) {
			addons.add(new JsonPrimitive(addon));
		}
		
		mekamon.add("account", new JsonPrimitive(getAccount() != null ? getAccount().getOid(): ""));
		
		final JsonArray battles = new JsonArray();
		mekamon.add("battles", battles);
		for (Battle battle : getBattles()) {
			battles.add(new JsonPrimitive(battle.getOid()));
		}
		
		return mekamon;
	}
	
	@Override
	public void delete() {
		setAccount(null);
		for (Battle battle : getBattles()) {
			removeBattle(battle);
		}
		super.delete();
	}
}
