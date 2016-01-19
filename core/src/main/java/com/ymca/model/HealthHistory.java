package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "health_history")
public class HealthHistory extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
    private Long id;					// required  PK

    @Column(name="RecordName")
    private String RecordName;

    @Column(name="ADDADHD_c")
    private String	ADDADHD_c;
    @Column(name="ActivityRestrictions_c")
    private String	ActivityRestrictions_c;
    @Column(name="ApplySunscreen_c")
    private String	ApplySunscreen_c;
    @Column(name="BirthMarks_c")
    private String	BirthMarks_c;
    @Column(name="CurrentMedicationPurpose_c")
    private String	CurrentMedicationPurpose_c;
    @Column(name="CurrentlyinTheraphy_c")
    private String	CurrentlyinTheraphy_c;
    @Column(name="CurrentlyUnderDrCare_c")
    private String	CurrentlyUnderDrCare_c;
    @Column(name="Customer_c")
    private String	Customer_c;
    @Column(name="DentistsAddress_c")
    private String	DentistsAddress_c;
    @Column(name="DentistsPhone_c")
    private String	DentistsPhone_c;
    @Column(name="DoyouhaveInsurance_c")
    private String	DoyouhaveInsurance_c;
    @Column(name="DoyouImmunize_c")
    private String	DoyouImmunize_c;
    @Column(name="DoctorsAddress_c")
    private String	DoctorsAddress_c;
    @Column(name="DoctorsPhone_c")
    private String	DoctorsPhone_c;
    @Column(name="DPT_c")
    private String	DPT_c;
    @Column(name="EmergencyAlternate_c")
    private String	EmergencyAlternate_c;
    @Column(name="EmergencyContactName_c")
    private String	EmergencyContactName_c;
    @Column(name="EmergencyContactNumber_c")
    private String	EmergencyContactNumber_c;
    @Column(name="EthnicOrigin_c")
    private String	EthnicOrigin_c;
    @Column(name="ExplainAccomodations_c")
    private String	ExplainAccomodations_c;
    @Column(name="ExplainRestrictions_c")
    private String	ExplainRestrictions_c;
    @Column(name="ExplainTheraphy_c")
    private String	ExplainTheraphy_c;
    @Column(name="EyeColor_c")
    private String	EyeColor_c;
    @Column(name="FamilyDentist_c")
    private String	FamilyDentist_c;
    @Column(name="FamilyPhysician_c")
    private String	FamilyPhysician_c;
    @Column(name="HairColor_c")
    private String	HairColor_c;
    @Column(name="Height_c")
    private String	Height_c;
    @Column(name="IndependentDismissal_c")
    private String	IndependentDismissal_c;
    @Column(name="IndependentSupervision_c")
    private String	IndependentSupervision_c;
    @Column(name="Instructions_c")
    private String	Instructions_c;
    @Column(name="InsuranceCompany_c")
    private String	InsuranceCompany_c;
    @Column(name="KnowhowtoSwim_c")
    private String	KnowhowtoSwim_c;
    @Column(name="ListCurrentMedications_c")
    private String	ListCurrentMedications_c;
    @Column(name="ListDietaryRestrictions_c")
    private String	ListDietaryRestrictions_c;
    @Column(name="ListRestrictions_c")
    private String	ListRestrictions_c;
    @Column(name="MedicalCondition_c")
    private String	MedicalCondition_c;
    @Column(name="MedicalHistory_c")
    private String	MedicalHistory_c;
    @Column(name="Medication_c")
    private String	Medication_c;
    @Column(name="MMR_c")
    private String	MMR_c;
    @Column(name="OTCAntihistamine_c")
    private String	OTCAntihistamine_c;
    @Column(name="OTCBenadryl_c")
    private String	OTCBenadryl_c;
    @Column(name="OTCCalamine_c")
    private String	OTCCalamine_c;
    @Column(name="OTCChloraseptic_c")
    private String	OTCChloraseptic_c;
    @Column(name="OTCCoughDrops_c")
    private String	OTCCoughDrops_c;
    @Column(name="OTCIbuprofen_c")
    private String	OTCIbuprofen_c;
    @Column(name="OTCNeosporin_c")
    private String	OTCNeosporin_c;
    @Column(name="OTCOther_c")
    private String	OTCOther_c;
    @Column(name="OTCPeptoBismol_c")
    private String	OTCPeptoBismol_c;
    @Column(name="OTCTylenol_c")
    private String	OTCTylenol_c;
    @Column(name="ParticipateinSwim_c")
    private String	ParticipateinSwim_c;
    @Column(name="PermissionwithFloat_c")
    private String	PermissionwithFloat_c;
    @Column(name="PhysicalDescription_c")
    private String	PhysicalDescription_c;
    @Column(name="Policy_c")
    private String	Policy_c;
    @Column(name="PreferredHospital_c")
    private String	PreferredHospital_c;
    @Column(name="Relationship_c")
    private String	Relationship_c;
    @Column(name="SpecialAccomodations_c")
    private String	SpecialAccomodations_c;
    @Column(name="TetanusBooster_c")
    private String	TetanusBooster_c;
    @Column(name="Tuberculin_c")
    private String	Tuberculin_c;
    @Column(name="Weight_c")
    private String	Weight_c;
    @Column(name="When_c")
    private String	When_c;
    @Column(name="lastUpdated")
    private Calendar lastUpdated;
    
    /*@OneToOne(mappedBy="healthHistory")
	private User contact;*/
    
	public String getRecordName() {
		return RecordName;
	}

	public void setRecordName(String recordName) {
		RecordName = recordName;
	}

	public String getADDADHD_c() {
		return ADDADHD_c;
	}

	public void setADDADHD_c(String aDDADHD_c) {
		ADDADHD_c = aDDADHD_c;
	}

	public String getActivityRestrictions_c() {
		return ActivityRestrictions_c;
	}

	public void setActivityRestrictions_c(String activityRestrictions_c) {
		ActivityRestrictions_c = activityRestrictions_c;
	}

	public String getApplySunscreen_c() {
		return ApplySunscreen_c;
	}

	public void setApplySunscreen_c(String applySunscreen_c) {
		ApplySunscreen_c = applySunscreen_c;
	}

	public String getBirthMarks_c() {
		return BirthMarks_c;
	}

	public void setBirthMarks_c(String birthMarks_c) {
		BirthMarks_c = birthMarks_c;
	}

	public String getCurrentMedicationPurpose_c() {
		return CurrentMedicationPurpose_c;
	}

	public void setCurrentMedicationPurpose_c(String currentMedicationPurpose_c) {
		CurrentMedicationPurpose_c = currentMedicationPurpose_c;
	}

	public String getCurrentlyinTheraphy_c() {
		return CurrentlyinTheraphy_c;
	}

	public void setCurrentlyinTheraphy_c(String currentlyinTheraphy_c) {
		CurrentlyinTheraphy_c = currentlyinTheraphy_c;
	}

	public String getCurrentlyUnderDrCare_c() {
		return CurrentlyUnderDrCare_c;
	}

	public void setCurrentlyUnderDrCare_c(String currentlyUnderDrCare_c) {
		CurrentlyUnderDrCare_c = currentlyUnderDrCare_c;
	}

	public String getCustomer_c() {
		return Customer_c;
	}

	public void setCustomer_c(String customer_c) {
		Customer_c = customer_c;
	}

	public String getDentistsAddress_c() {
		return DentistsAddress_c;
	}

	public void setDentistsAddress_c(String dentistsAddress_c) {
		DentistsAddress_c = dentistsAddress_c;
	}

	public String getDentistsPhone_c() {
		return DentistsPhone_c;
	}

	public void setDentistsPhone_c(String dentistsPhone_c) {
		DentistsPhone_c = dentistsPhone_c;
	}

	public String getDoyouhaveInsurance_c() {
		return DoyouhaveInsurance_c;
	}

	public void setDoyouhaveInsurance_c(String doyouhaveInsurance_c) {
		DoyouhaveInsurance_c = doyouhaveInsurance_c;
	}

	public String getDoyouImmunize_c() {
		return DoyouImmunize_c;
	}

	public void setDoyouImmunize_c(String doyouImmunize_c) {
		DoyouImmunize_c = doyouImmunize_c;
	}

	public String getDoctorsAddress_c() {
		return DoctorsAddress_c;
	}

	public void setDoctorsAddress_c(String doctorsAddress_c) {
		DoctorsAddress_c = doctorsAddress_c;
	}

	public String getDoctorsPhone_c() {
		return DoctorsPhone_c;
	}

	public void setDoctorsPhone_c(String doctorsPhone_c) {
		DoctorsPhone_c = doctorsPhone_c;
	}

	public String getDPT_c() {
		return DPT_c;
	}

	public void setDPT_c(String dPT_c) {
		DPT_c = dPT_c;
	}

	public String getEmergencyAlternate_c() {
		return EmergencyAlternate_c;
	}

	public void setEmergencyAlternate_c(String emergencyAlternate_c) {
		EmergencyAlternate_c = emergencyAlternate_c;
	}

	public String getEmergencyContactName_c() {
		return EmergencyContactName_c;
	}

	public void setEmergencyContactName_c(String emergencyContactName_c) {
		EmergencyContactName_c = emergencyContactName_c;
	}

	public String getEmergencyContactNumber_c() {
		return EmergencyContactNumber_c;
	}

	public void setEmergencyContactNumber_c(String emergencyContactNumber_c) {
		EmergencyContactNumber_c = emergencyContactNumber_c;
	}

	public String getEthnicOrigin_c() {
		return EthnicOrigin_c;
	}

	public void setEthnicOrigin_c(String ethnicOrigin_c) {
		EthnicOrigin_c = ethnicOrigin_c;
	}

	public String getExplainAccomodations_c() {
		return ExplainAccomodations_c;
	}

	public void setExplainAccomodations_c(String explainAccomodations_c) {
		ExplainAccomodations_c = explainAccomodations_c;
	}

	public String getExplainRestrictions_c() {
		return ExplainRestrictions_c;
	}

	public void setExplainRestrictions_c(String explainRestrictions_c) {
		ExplainRestrictions_c = explainRestrictions_c;
	}

	public String getExplainTheraphy_c() {
		return ExplainTheraphy_c;
	}

	public void setExplainTheraphy_c(String explainTheraphy_c) {
		ExplainTheraphy_c = explainTheraphy_c;
	}

	public String getEyeColor_c() {
		return EyeColor_c;
	}

	public void setEyeColor_c(String eyeColor_c) {
		EyeColor_c = eyeColor_c;
	}

	public String getFamilyDentist_c() {
		return FamilyDentist_c;
	}

	public void setFamilyDentist_c(String familyDentist_c) {
		FamilyDentist_c = familyDentist_c;
	}

	public String getFamilyPhysician_c() {
		return FamilyPhysician_c;
	}

	public void setFamilyPhysician_c(String familyPhysician_c) {
		FamilyPhysician_c = familyPhysician_c;
	}

	public String getHairColor_c() {
		return HairColor_c;
	}

	public void setHairColor_c(String hairColor_c) {
		HairColor_c = hairColor_c;
	}

	public String getHeight_c() {
		return Height_c;
	}

	public void setHeight_c(String height_c) {
		Height_c = height_c;
	}

	public String getIndependentDismissal_c() {
		return IndependentDismissal_c;
	}

	public void setIndependentDismissal_c(String independentDismissal_c) {
		IndependentDismissal_c = independentDismissal_c;
	}

	public String getIndependentSupervision_c() {
		return IndependentSupervision_c;
	}

	public void setIndependentSupervision_c(String independentSupervision_c) {
		IndependentSupervision_c = independentSupervision_c;
	}

	public String getInstructions_c() {
		return Instructions_c;
	}

	public void setInstructions_c(String instructions_c) {
		Instructions_c = instructions_c;
	}

	public String getInsuranceCompany_c() {
		return InsuranceCompany_c;
	}

	public void setInsuranceCompany_c(String insuranceCompany_c) {
		InsuranceCompany_c = insuranceCompany_c;
	}

	public String getKnowhowtoSwim_c() {
		return KnowhowtoSwim_c;
	}

	public void setKnowhowtoSwim_c(String knowhowtoSwim_c) {
		KnowhowtoSwim_c = knowhowtoSwim_c;
	}

	public String getListCurrentMedications_c() {
		return ListCurrentMedications_c;
	}

	public void setListCurrentMedications_c(String listCurrentMedications_c) {
		ListCurrentMedications_c = listCurrentMedications_c;
	}

	public String getListDietaryRestrictions_c() {
		return ListDietaryRestrictions_c;
	}

	public void setListDietaryRestrictions_c(String listDietaryRestrictions_c) {
		ListDietaryRestrictions_c = listDietaryRestrictions_c;
	}

	public String getListRestrictions_c() {
		return ListRestrictions_c;
	}

	public void setListRestrictions_c(String listRestrictions_c) {
		ListRestrictions_c = listRestrictions_c;
	}

	public String getMedicalCondition_c() {
		return MedicalCondition_c;
	}

	public void setMedicalCondition_c(String medicalCondition_c) {
		MedicalCondition_c = medicalCondition_c;
	}

	public String getMedicalHistory_c() {
		return MedicalHistory_c;
	}

	public void setMedicalHistory_c(String medicalHistory_c) {
		MedicalHistory_c = medicalHistory_c;
	}

	public String getMedication_c() {
		return Medication_c;
	}

	public void setMedication_c(String medication_c) {
		Medication_c = medication_c;
	}

	public String getMMR_c() {
		return MMR_c;
	}

	public void setMMR_c(String mMR_c) {
		MMR_c = mMR_c;
	}

	public String getOTCAntihistamine_c() {
		return OTCAntihistamine_c;
	}

	public void setOTCAntihistamine_c(String oTCAntihistamine_c) {
		OTCAntihistamine_c = oTCAntihistamine_c;
	}

	public String getOTCBenadryl_c() {
		return OTCBenadryl_c;
	}

	public void setOTCBenadryl_c(String oTCBenadryl_c) {
		OTCBenadryl_c = oTCBenadryl_c;
	}

	public String getOTCCalamine_c() {
		return OTCCalamine_c;
	}

	public void setOTCCalamine_c(String oTCCalamine_c) {
		OTCCalamine_c = oTCCalamine_c;
	}

	public String getOTCChloraseptic_c() {
		return OTCChloraseptic_c;
	}

	public void setOTCChloraseptic_c(String oTCChloraseptic_c) {
		OTCChloraseptic_c = oTCChloraseptic_c;
	}

	public String getOTCCoughDrops_c() {
		return OTCCoughDrops_c;
	}

	public void setOTCCoughDrops_c(String oTCCoughDrops_c) {
		OTCCoughDrops_c = oTCCoughDrops_c;
	}

	public String getOTCIbuprofen_c() {
		return OTCIbuprofen_c;
	}

	public void setOTCIbuprofen_c(String oTCIbuprofen_c) {
		OTCIbuprofen_c = oTCIbuprofen_c;
	}

	public String getOTCNeosporin_c() {
		return OTCNeosporin_c;
	}

	public void setOTCNeosporin_c(String oTCNeosporin_c) {
		OTCNeosporin_c = oTCNeosporin_c;
	}

	public String getOTCOther_c() {
		return OTCOther_c;
	}

	public void setOTCOther_c(String oTCOther_c) {
		OTCOther_c = oTCOther_c;
	}

	public String getOTCPeptoBismol_c() {
		return OTCPeptoBismol_c;
	}

	public void setOTCPeptoBismol_c(String oTCPeptoBismol_c) {
		OTCPeptoBismol_c = oTCPeptoBismol_c;
	}

	public String getOTCTylenol_c() {
		return OTCTylenol_c;
	}

	public void setOTCTylenol_c(String oTCTylenol_c) {
		OTCTylenol_c = oTCTylenol_c;
	}

	public String getParticipateinSwim_c() {
		return ParticipateinSwim_c;
	}

	public void setParticipateinSwim_c(String participateinSwim_c) {
		ParticipateinSwim_c = participateinSwim_c;
	}

	public String getPermissionwithFloat_c() {
		return PermissionwithFloat_c;
	}

	public void setPermissionwithFloat_c(String permissionwithFloat_c) {
		PermissionwithFloat_c = permissionwithFloat_c;
	}

	public String getPhysicalDescription_c() {
		return PhysicalDescription_c;
	}

	public void setPhysicalDescription_c(String physicalDescription_c) {
		PhysicalDescription_c = physicalDescription_c;
	}

	public String getPolicy_c() {
		return Policy_c;
	}

	public void setPolicy_c(String policy_c) {
		Policy_c = policy_c;
	}

	public String getPreferredHospital_c() {
		return PreferredHospital_c;
	}

	public void setPreferredHospital_c(String preferredHospital_c) {
		PreferredHospital_c = preferredHospital_c;
	}

	public String getRelationship_c() {
		return Relationship_c;
	}

	public void setRelationship_c(String relationship_c) {
		Relationship_c = relationship_c;
	}

	public String getSpecialAccomodations_c() {
		return SpecialAccomodations_c;
	}

	public void setSpecialAccomodations_c(String specialAccomodations_c) {
		SpecialAccomodations_c = specialAccomodations_c;
	}

	public String getTetanusBooster_c() {
		return TetanusBooster_c;
	}

	public void setTetanusBooster_c(String tetanusBooster_c) {
		TetanusBooster_c = tetanusBooster_c;
	}

	public String getTuberculin_c() {
		return Tuberculin_c;
	}

	public void setTuberculin_c(String tuberculin_c) {
		Tuberculin_c = tuberculin_c;
	}

	public String getWeight_c() {
		return Weight_c;
	}

	public void setWeight_c(String weight_c) {
		Weight_c = weight_c;
	}

	public String getWhen_c() {
		return When_c;
	}

	public void setWhen_c(String when_c) {
		When_c = when_c;
	}

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

	/*public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/*public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}*/

	/*public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}
*/
	/*public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}*/

}
