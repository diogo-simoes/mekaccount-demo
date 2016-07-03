package com.diogosimoes.mekaccount.domain;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Account extends DomainObject {
	
	private String email;
	private String name;
	private String phone;
	private Set<String> aliases;
	
	private Mekamon mekamon;
	
	public Account() {
		super();
		aliases = new HashSet<String>();
	}
	
	public Account(String email, String name, String phone) {
		this();
		setEmail(email);
		setName(name);
		setPhone(phone);
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Set<String> getAliases() {
		return aliases;
	}
	
	public void addAlias(String alias) {
		aliases.add(alias);
	}
	
	public void removeAlias(String alias) {
		aliases.remove(alias);
	}
	
	public void clearAliases() {
		aliases.clear();
	}
	
	public Mekamon getMekamon() {
		return mekamon;
	}
	
	public void setMekamon(Mekamon mekamon) {
		final Mekamon oldMeka = this.mekamon;
		final Mekamon newMeka = mekamon;
		if (oldMeka == newMeka) {
			return;
		}
		if (oldMeka != null) {
			this.mekamon = null;
			oldMeka.$set$Account(null);
		}
		this.mekamon = newMeka;
		if (newMeka != null) {
			newMeka.$set$Account(this);
		}
	}
	
	void $set$Mekamon(Mekamon mekamon) {
		if (this.mekamon != null) {
			final Mekamon oldMeka = this.mekamon;
			this.mekamon = null;
			oldMeka.$set$Account(null);
		}
		this.mekamon = mekamon;
	}

	@Override
	public JsonElement serialize() {
		final JsonObject account = new JsonObject();
		account.add("oid", new JsonPrimitive(getOid()));
		account.add("email", new JsonPrimitive(getEmail() != null ? getEmail() : ""));
		account.add("name", new JsonPrimitive(getName() != null ? getName() : ""));
		account.add("phone", new JsonPrimitive(getPhone() != null ? getPhone() : ""));
		
		final JsonArray aliases = new JsonArray();
		account.add("aliases", aliases);
		for (String alias : getAliases()) {
			aliases.add(new JsonPrimitive(alias));
		}
		
		account.add("mekamon", new JsonPrimitive(getMekamon() != null ? getMekamon().getOid() : ""));
		
		return account;
	}

	@Override
	public void delete() {
		setMekamon(null);
		super.delete();
	}
}
