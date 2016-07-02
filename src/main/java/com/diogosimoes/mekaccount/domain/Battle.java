package com.diogosimoes.mekaccount.domain;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class Battle extends DomainObject {
	
	private String battleId;
	
	private Set<Mekamon> mekamons;
	
	public Battle() {
		super();
		mekamons = new HashSet<Mekamon>();
	}
	
	public Battle(Mekamon red, Mekamon blue) {
		this();
		addMekamon(red);
		addMekamon(blue);
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}
	
	public Set<Mekamon> getMekamons() {
		return mekamons;
	}
	
	public void addMekamon(Mekamon mekamon) {
		mekamons.add(mekamon);
		mekamon.$add$Battle(this);
	}
	
	void $add$Mekamon(Mekamon mekamon) {
		mekamons.add(mekamon);		
	}
	
	public void removeMekamon(Mekamon mekamon) {
		mekamons.remove(mekamon);
		mekamon.$remove$Battle(this);
	}
	
	void $remove$Mekamon(Mekamon mekamon) {
		mekamons.remove(mekamon);		
	}
	
	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(getOid());
	}
}
