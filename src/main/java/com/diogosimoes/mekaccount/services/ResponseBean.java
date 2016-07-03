package com.diogosimoes.mekaccount.services;

import java.util.Collection;

import com.diogosimoes.mekaccount.domain.DomainObject;

public class ResponseBean {
	
	private String apiVersion;
	private int modelSize;
	private Collection<? extends DomainObject> model;
	
	ResponseBean(String apiVersion, Collection<? extends DomainObject> model) {
		this.apiVersion = apiVersion;
		modelSize = model.size();
		this.model = model;
	}
	
	public String getApiVersion() {
		return apiVersion;
	}
	
	public int getModelSize() {
		return modelSize;
	}
	
	public Collection<? extends DomainObject> getDomain() {
		return model;
	}
	
}
