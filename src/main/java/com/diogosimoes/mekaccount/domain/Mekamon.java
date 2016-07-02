package com.diogosimoes.mekaccount.domain;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class Mekamon extends DomainObject {
	
	private String mekaId;
	private int evoLvl;
	private Set<String> addons;
	
	private Account account;
	private Set<Battle> battles;
	
	public Mekamon() {
		super();
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
	public void setAddons(Set<String> addons) {
		this.addons = addons;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		if (this.account != null) {
			this.account.$set$Mekamon(null);
		}
		this.account = account;
		if (account != null) {
			account.$set$Mekamon(this);
		}
	}
	
	void $set$Account(Account account) {
		if (this.account != null) {
			this.account.$set$Mekamon(null);
		}
		this.account = account;
	}
	
	public Set<Battle> getBattles() {
		return battles;
	}
	
	public void addBattle(Battle battle) {
		battles.add(battle);
		battle.$add$Mekamon(this);
	}
	
	void $add$Battle(Battle battle) {
		battles.add(battle);
	}
	
	public void removeBattle(Battle battle) {
		battles.remove(battle);
		battle.$remove$Mekamon(this);
	}
	
	void $remove$Battle(Battle battle) {
		battles.remove(battle);
	}
	
	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(getOid());
	}
}
