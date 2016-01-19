package com.ymca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * This class represents the basic "Membership" object in YMCA Portal that allows for Membership management.  
 *
 */
@Entity
@Table(name = "related_terms_and_condition")
public class RelatedTermsAndCondition extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;
    
    @Id
    private String id;

    @Column(name="RecordName")
    private String recordName;

    @Column(name="Account_c")
    private String account_c;
    
    @Column(name="Contact_c")
    private String contact_c;
    
    @Column(name="SignUp_c")
    private String signUp_c;
    
    @Column(name="TAndC_c")
    private String tAndC_c;
    
    @Column(name="Status_c")
    private String status_c;
    
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

    
}
