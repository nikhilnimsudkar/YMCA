package com.ymca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * This class represents the basic "user" object in YMCA Portal that allows for authentication
 * and user management.  It implements Acegi Security's UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Acegi UserDetails interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "contact")
public class User extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
    private Long contactId;					// required  PK

	@Column(name="PartyId")
	private Long partyId ; // Sales Cloud Party Id
    
    @Column(length = 50)
    private String username;                    // required
    
    @Column()
    private String password;                    // required
    
    @Transient
    private String confirmPassword;
    
    @Column(name = "password_hint")
    @XmlTransient
    private String passwordHint;

    @Column(name ="FirstName")
    private String firstName;                   // required
    
    @Column(name ="LastName")
    private String lastName;                    // required
    
    @Column(name="EmailAddress")
    private String emailAddress;                       // required; unique
    
    @Column(name="FormattedPhoneNumber")
    private String formattedPhoneNumber;
    
    @Column(name="ContactPointPurpose")
    private String contactPointPurpose;
    
    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    private String primaryURL;
    @Column(name = "is_active", columnDefinition="boolean default true")
   	private boolean isActive;  
    
    @Embedded
    private Address address = new Address();
    
    @Version
    private Integer version;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<Role>();
    
    /*@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id")
    private List<Activity> interactions = new ArrayList<>();*/
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "contact")
    private List<Activity> interactions = new ArrayList<>();
    
    @Column(name = "account_enabled")
    private boolean enabled;
    
    @Column(name = "account_expired")
    private boolean accountExpired;
    
    @Column(name = "account_locked")
    private boolean accountLocked;
    
    @Column(name = "credentials_expired")
    private boolean credentialsExpired;
    
    @Column(name = "profile_image")
    private String profileImage;
    
    @Column(name = "is_primary", nullable = false, columnDefinition="boolean default false")
    private boolean isPrimary;
    
    @Column(name = "is_member", nullable = false, columnDefinition="boolean default false")
    private boolean isMember;
    
    @Column(name = "is_adult")
    private String isAdult;
    
    @Column(name = "usr_sequence")
    private String usrSequence;
    
    @Column(name = "Gender")
    private String gender;  
    
    @Column(name = "referrerContactId")
    private Long referrerContactId;     
    
    @Transient
    private String referrerEmail;
    
    @Transient
    private String signupId;
    
    @Transient
    private String isKid;

	//@OneToOne(cascade={CascadeType.ALL})  
    //@JoinColumn(name="waivers_and_tc_id_FK")
    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.ALL})
	@JoinColumn(name="id")
    private Set<ContactWaiversAndTC> contactWaiversAndTC = new HashSet<ContactWaiversAndTC>(0);
    
    @OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="location")
	private Location location;  
    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "referrerContactId")
    private List<User> referrerContact = new ArrayList<User>();
    
    @OneToOne(cascade={CascadeType.PERSIST})
    @JoinColumn(name="referrerContactId")
    private User referrerContact;*/
   

    @Column(name = "PrimaryCustomerId")
    private String primaryCustomerId;   

    @Column(name = "PartyStatus")
    private String partyStatus;  
    
    @Column(name = "Donor_c")
    private String donor;  
    
    @Column(name = "FacilityAccessNumber_c")
    private String facilityAccessNumber;  
    

    @Column(name = "FacilityAccessStatus_c")
    private String FacilityAccessStatus;  

    @Column(name = "FamilyAccountNumber_c")
    private String familyAccountNumber;  

    @Column(name = "Location_c")
    private String scLocation;  

    @Column(name = "Type_c")
    private String type;  

    @Column(name = "SexOffender_c")
    private String sexOffender;  
    
    @Column(name = "UnauthorizedAccessCount_c")
    private Integer unauthorizedAccessCount;  
    
    @Column(name = "fName_and_lname", nullable = false)
    private String fNameAndLName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "relationships")
    private String relationships;

    @Column(name = "area_of_interest")
    private String areaOfInterest;
    
    @Column(name="lastUpdated")
    private Calendar lastUpdated;

    @Column(name="AccountPartyId")
    private String accountPartyId;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account customer;
    
	@Column(name="accountId",insertable=false,updatable=false)
	private Long accountId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="healthHistoryId")
    private HealthHistory healthHistory;
    
    @Column(name = "DoNotCall_c", columnDefinition="boolean default false")
   	private Boolean doNotCall;
    
    @Column(name = "DoNotEmail_c", columnDefinition="boolean default false")
   	private Boolean doNotEmail;
    
    @Column(name = "SendUpdatesInclNewsletter_c", columnDefinition="boolean default false")
   	private boolean sendUpdatesInclNewsletter;
    
    @Column(name ="Employer_c")
    private String employer;
    
    @Column(name ="Ethnicity_c")
    private String ethnicity;    
    
    @Column(name ="Comment_c")
    private String comment;
    @Column(name ="OffenderIds_c")
    private String OffenderIds;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "contactId")
    private List<Notes> notes = new ArrayList<Notes>();
    
    
    private Long averageCheck_inPerWeek_c;
    
    
    private Long averageCheck_inPerMonth_c;
    
    @Column(name="LastCheckInDate_c")
    private Calendar lastCheckInDate_c;
    
    @Transient
    private String membershipAgeCategory;    
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "checkInPerson")
    private List<Activity> checkinPersonList = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "checkOutPerson")
    private List<Activity> checkOutPersonList = new ArrayList<>();
    
    public Long getAverageCheck_inPerWeek_c() {
		return averageCheck_inPerWeek_c;
	}

	public void setAverageCheck_inPerWeek_c(Long averageCheck_inPerWeek_c) {
		this.averageCheck_inPerWeek_c = averageCheck_inPerWeek_c;
	}

	public Long getAverageCheck_inPerMonth_c() {
		return averageCheck_inPerMonth_c;
	}

	public void setAverageCheck_inPerMonth_c(Long averageCheck_inPerMonth_c) {
		this.averageCheck_inPerMonth_c = averageCheck_inPerMonth_c;
	}

	public Calendar getLastCheckInDate_c() {
		return lastCheckInDate_c;
	}

	public void setLastCheckInDate_c(Calendar lastCheckInDate_c) {
		this.lastCheckInDate_c = lastCheckInDate_c;
	}

	public List<Activity> getCheckinPersonList() {
		return checkinPersonList;
	}

	public void setCheckinPersonList(List<Activity> checkinPersonList) {
		this.checkinPersonList = checkinPersonList;
	}

	public List<Activity> getCheckOutPersonList() {
		return checkOutPersonList;
	}

	public void setCheckOutPersonList(List<Activity> checkOutPersonList) {
		this.checkOutPersonList = checkOutPersonList;
	}

	@Transient
    private int age;
    
    @Transient
    private String dateOfBirthStr;
    
    public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public User() {
    }

    /**
     * Create a new instance and set the username.
     *
     * @param username login name for user.
     */
    public User(final String username) {
        this.username = username;
    }

    
    public Long getPartyId() {
        return partyId;
    }

   
    public String getUsername() {
        return username;
    }

    
    public String getPassword() {
        return password;
    }

    
    public String getConfirmPassword() {
        return confirmPassword;
    }

   
    public String getPasswordHint() {
        return passwordHint;
    }

    
    public String getPersonFirstName() {
        return firstName;
    }

   
    public String getPersonLastName() {
        return lastName;
    }

    
    public String getPrimaryEmailAddress() {
        return emailAddress;
    }

    
    public String getPrimaryFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

   
    public String getFormattedPhoneNumber() {
		return formattedPhoneNumber;
	}

	public void setFormattedPhoneNumber(String formattedPhoneNumber) {
		this.formattedPhoneNumber = formattedPhoneNumber;
	}

	public String getContactPointPurpose() {
		return contactPointPurpose;
	}

	public void setContactPointPurpose(String contactPointPurpose) {
		this.contactPointPurpose = contactPointPurpose;
	}

	public List<Activity> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<Activity> interactions) {
		this.interactions = interactions;
	}

	public String getPrimaryURL() {
        return primaryURL;
    }

    public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
     * Returns the full name.
     *
     * @return firstName + ' ' + lastName
     */
    @Transient
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

   
    public Address getAddress() {
        return address;
    }

   
    public Set<Role> getRoles() {
        return roles;
    }
    

    /**
     * Convert user roles to LabelValue objects for convenience.
     *
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
            for (Role role : roles) {
                // convert the user's roles to LabelValue Objects
                userRoles.add(new LabelValue(role.getName(), role.getName()));
            }
        }

        return userRoles;
    }

    /**
     * Adds a role for the user
     *
     * @param role the fully instantiated role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

   
	/**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Transient
    public Set<Role> getAuthorities() {
        Set<Role> authorities = new LinkedHashSet<Role>();
        authorities.addAll(roles);
        return authorities;
    }

    
    public Integer getVersion() {
        return version;
    }

   
    public Set<ContactWaiversAndTC> getContactWaiversAndTC() {
		return contactWaiversAndTC;
	}

	public void setContactWaiversAndTC(Set<ContactWaiversAndTC> contactWaiversAndTC) {
		this.contactWaiversAndTC = contactWaiversAndTC;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRelationships() {
		return relationships;
	}

	public void setRelationships(String relationships) {
		this.relationships = relationships;
	}

	public String getAreaOfInterest() {
		return areaOfInterest;
	}

	public void setAreaOfInterest(String areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}

	public boolean isEnabled() {
        return enabled;
    }

   
    public boolean isAccountExpired() {
        return accountExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

   
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public void setPersonFirstName(String personFirstName) {
        this.firstName = personFirstName;
    }

    public void setPersonLastName(String personLastName) {
        this.lastName = personLastName;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.emailAddress = primaryEmailAddress;
    }

    public void setPrimaryFormattedPhoneNumber(String primaryFormattedPhoneNumber) {
        this.formattedPhoneNumber = primaryFormattedPhoneNumber;
    }

    public void setPrimaryURL(String primaryURL) {
        this.primaryURL = primaryURL;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getUsrSequence() {
		return usrSequence;
	}

	public void setUsrSequence(String usrSequence) {
		this.usrSequence = usrSequence;
	}

	public String getIsAdult() {
		return isAdult;
	}

	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}

	public String getReferrerEmail() {
		return referrerEmail;
	}

	public void setReferrerEmail(String referrerEmail) {
		this.referrerEmail = referrerEmail;
	}

	/*public User getReferrerContact() {
		return referrerContact;
	}

	public void setReferrerContact(User referrerContact) {
		this.referrerContact = referrerContact;
	}*/

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPrimaryCustomerId() {
		return primaryCustomerId;
	}

	public void setPrimaryCustomerId(String primaryCustomerId) {
		this.primaryCustomerId = primaryCustomerId;
	}

	public String getPartyStatus() {
		return partyStatus;
	}

	public void setPartyStatus(String partyStatus) {
		this.partyStatus = partyStatus;
	}

	public String getDonor() {
		return donor;
	}

	public void setDonor(String donor) {
		this.donor = donor;
	}

	public String getFacilityAccessNumber() {
		return facilityAccessNumber;
	}

	public void setFacilityAccessNumber(String facilityAccessNumber) {
		this.facilityAccessNumber = facilityAccessNumber;
	}

	public String getFacilityAccessStatus() {
		return FacilityAccessStatus;
	}

	public void setFacilityAccessStatus(String facilityAccessStatus) {
		FacilityAccessStatus = facilityAccessStatus;
	}

	public String getFamilyAccountNumber() {
		return familyAccountNumber;
	}

	public void setFamilyAccountNumber(String familyAccountNumber) {
		this.familyAccountNumber = familyAccountNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSexOffender() {
		return sexOffender;
	}

	public void setSexOffender(String sexOffender) {
		this.sexOffender = sexOffender;
	}

	public Integer getUnauthorizedAccessCount() {
		return unauthorizedAccessCount;
	}

	public void setUnauthorizedAccessCount(Integer unauthorizedAccessCount) {
		this.unauthorizedAccessCount = unauthorizedAccessCount;
	}

	public String getfNameAndLName() {
		return fNameAndLName;
	}

	public void setfNameAndLName(String fNameAndLName) {
		this.fNameAndLName = fNameAndLName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getAccountPartyId() {
		return accountPartyId;
	}

	public void setAccountPartyId(String accountPartyId) {
		this.accountPartyId = accountPartyId;
	}


	public String getScLocation() {
		return scLocation;
	}

	public void setScLocation(String scLocation) {
		this.scLocation = scLocation;
	}

	public Account getCustomer() {
		return customer;
	}

	public void setCustomer(Account customer) {
		this.customer = customer;
	}

	/*public List<User> getReferrerContact() {
		return referrerContact;
	}

	public void setReferrerContact(List<User> referrerContact) {
		this.referrerContact = referrerContact;
	}*/	

	public Long getReferrerContactId() {
		return referrerContactId;
	}

	public void setReferrerContactId(Long referrerContactId) {
		this.referrerContactId = referrerContactId;
	}

	public String getIsKid() {
		return isKid;
	}

	public void setIsKid(String isKid) {
		this.isKid = isKid;
	}

	public String getSignupId() {
		return signupId;
	}

	public void setSignupId(String signupId) {
		this.signupId = signupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactId == null) ? 0 : contactId.hashCode());
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
		User other = (User) obj;
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName
				+ ", emailAddress=" + emailAddress + "]";
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public HealthHistory getHealthHistory() {
		return healthHistory;
	}

	public void setHealthHistory(HealthHistory healthHistory) {
		this.healthHistory = healthHistory;
	}

	public Boolean getDoNotCall() {
		return doNotCall;
	}

	public void setDoNotCall(Boolean doNotCall) {
		this.doNotCall = doNotCall;
	}

	public Boolean getDoNotEmail() {
		return doNotEmail;
	}

	public void setDoNotEmail(Boolean doNotEmail) {
		this.doNotEmail = doNotEmail;
	}

	public boolean isSendUpdatesInclNewsletter() {
		return sendUpdatesInclNewsletter;
	}

	public void setSendUpdatesInclNewsletter(boolean sendUpdatesInclNewsletter) {
		this.sendUpdatesInclNewsletter = sendUpdatesInclNewsletter;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOffenderIds() {
		return OffenderIds;
	}

	public void setOffenderIds(String offenderIds) {
		OffenderIds = offenderIds;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public String getDateOfBirthStr() {
		return dateOfBirthStr;
	}

	public void setDateOfBirthStr(String dateOfBirthStr) {
		this.dateOfBirthStr = dateOfBirthStr;
	}

	public String getMembershipAgeCategory() {
		return membershipAgeCategory;
	}

	public void setMembershipAgeCategory(String membershipAgeCategory) {
		this.membershipAgeCategory = membershipAgeCategory;
	}
}
