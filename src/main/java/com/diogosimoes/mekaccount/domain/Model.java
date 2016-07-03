package com.diogosimoes.mekaccount.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Model {
	
	private static Map<String, DomainObject> domain = new HashMap<String, DomainObject>();
	
	public static <T extends DomainObject> T find(String oid) {
		return (T) domain.get(oid);
	}
	
	public static void add(DomainObject o) {
		domain.put(o.getOid(), o);
	}
	
	public static void remove(DomainObject o) {
		domain.remove(o.getOid());
	}
	
	public static ModelBean dump() {
		return new ModelBean(domain.values());
	}
	
	public static class ModelBean {
		private int modelSize;
		private Collection<DomainObject> model;
		ModelBean(Collection<DomainObject> model) {
			modelSize = model.size();
			this.model = model;
		}
		public int getModelSize() {
			return modelSize;
		}
		public Collection<DomainObject> getDomain() {
			return model;
		}
	}
}
