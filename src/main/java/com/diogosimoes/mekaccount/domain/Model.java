package com.diogosimoes.mekaccount.domain;

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
		domain.remove(o);
	}
	
	public static Stream<DomainObject> dump() {
		return domain.values().stream();
	}
}
