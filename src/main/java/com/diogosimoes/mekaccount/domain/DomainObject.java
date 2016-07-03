package com.diogosimoes.mekaccount.domain;

import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.JsonElement;

public abstract class DomainObject {
	
	private static AtomicLong objCounter = new AtomicLong();
	
	private final String oid;
	
	DomainObject() {
		oid = this.getClass().getSimpleName().toLowerCase() + ":" + String.format("%08d", objCounter.getAndIncrement());
		Model.add(this);
	}

	public String getOid() {
		return oid;
	}
	
	public void delete() {
		Model.remove(this);
	}
	
	public abstract JsonElement serialize();
}
