package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tandc")
public class TandC extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TandC(){

    }

	@Id
	private Long scId;
	
	@Column(name="tandc", columnDefinition="TEXT")
	private String tandc;

	private String type;
	
	public Long getScId() {
		return scId;
	}

	public void setScId(Long scId) {
		this.scId = scId;
	}

	public String getTandc() {
		return tandc;
	}

	public void setTandc(String tandc) {
		this.tandc = tandc;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tandc == null) ? 0 : tandc.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TandC other = (TandC) obj;
		if (tandc == null) {
			if (other.tandc != null)
				return false;
		} else if (!tandc.equals(other.tandc))
			return false;
		
		return true;
	}
}
