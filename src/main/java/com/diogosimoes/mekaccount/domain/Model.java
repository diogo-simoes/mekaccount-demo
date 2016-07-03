package com.diogosimoes.mekaccount.domain;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
	
	public static Collection<DomainObject> dump() {
		final Set<DomainObject> orderedEntities = new TreeSet<DomainObject>(new Comparator<DomainObject>() {
			@Override
			public int compare(DomainObject o1, DomainObject o2) {
				return o1.getOid().compareTo(o2.getOid());
			}
		});
		orderedEntities.addAll(domain.values());
		return orderedEntities;
	}
	
}
