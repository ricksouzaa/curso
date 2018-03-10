package com.core.model.entity;

import javax.persistence.MappedSuperclass;

import com.core.model.AbstractModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractEntity extends AbstractModel {

	@JsonIgnore
	public abstract Long getId();

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : super.hashCode();
	}

}