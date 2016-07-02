package com.diogosimoes.mekaccount.domain;

import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class Account extends DomainObject {
	
	private String email;
	private String name;
	private String phone;
	private Set<String> aliases;
	
	private Mekamon mekamon;
	
	public Account() {
		super();
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
	
	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}
	
	public Mekamon getMekamon() {
		return mekamon;
	}
	
	public void setMekamon(Mekamon mekamon) {
		if (this.mekamon != null) {
			this.mekamon.$set$Account(null);
		}
		this.mekamon = mekamon;
		if (mekamon != null) {
			mekamon.$set$Account(this);
		}
	}
	
	void $set$Mekamon(Mekamon mekamon) {
		if (this.mekamon != null) {
			this.mekamon.$set$Account(null);
		}
		this.mekamon = mekamon;
	}

	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(getOid());
	}
}
