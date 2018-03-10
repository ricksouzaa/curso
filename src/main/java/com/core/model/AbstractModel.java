package com.core.model;

public abstract class AbstractModel {

	@Override
	public boolean equals(Object obj) {
		boolean equal = obj != null;
		equal = equal && getClass().isInstance(obj);
		equal = equal && hashCode() > 0;
		equal = equal && obj.hashCode() > 0;
		equal = equal && hashCode() == obj.hashCode();
		return equal;
	}

}