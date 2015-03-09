package com.ijob.server.model;


import java.io.Serializable;

/**
 * Created by JackieZhuang on 2015/1/18.
 */
public class Recruitment implements Serializable{

	private static final long serialVersionUID = -6226466116717904102L;
	
	private int mId;
	private int mOrgId;
	private String mType;
	private String mInfoProvider;
	private String mName;
	private String mHoldLocation;
	private String mHoldTime;
	private String mTargetEduc;
	private String mTargetMajor;
	private String mDescriPtion;
	private String mContacts;
	private String mContactType;
	private String mFaxCode;
	private String mEmail;
	private String mRelateLink;
	private int mScanNum;

	public Recruitment() {
	}

	public Recruitment(int id, int orgId, String type, String infoProvider, String name, String holdLocation,
	                   String holdTime, String targetEduc, String targetMajor, String descriPtion, String contacts,
	                   String contactType, String faxCode, String email, String relateLink, int scanNum) {
		mId = id;
		mOrgId = orgId;
		mType = type;
		mInfoProvider = infoProvider;
		mName = name;
		mHoldLocation = holdLocation;
		mHoldTime = holdTime;
		mTargetEduc = targetEduc;
		mTargetMajor = targetMajor;
		mDescriPtion = descriPtion;
		mContacts = contacts;
		mContactType = contactType;
		mFaxCode = faxCode;
		mEmail = email;
		mRelateLink = relateLink;
		mScanNum = scanNum;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public int getOrgId() {
		return mOrgId;
	}

	public void setOrgId(int orgId) {
		mOrgId = orgId;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		mType = type;
	}

	public String getInfoProvider() {
		return mInfoProvider;
	}

	public void setInfoProvider(String infoProvider) {
		mInfoProvider = infoProvider;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getHoldLocation() {
		return mHoldLocation;
	}

	public void setHoldLocation(String holdLocation) {
		mHoldLocation = holdLocation;
	}

	public String getHoldTime() {
		return mHoldTime;
	}

	public void setHoldTime(String holdTime) {
		mHoldTime = holdTime;
	}

	public String getTargetEduc() {
		return mTargetEduc;
	}

	public void setTargetEduc(String targetEduc) {
		mTargetEduc = targetEduc;
	}

	public String getTargetMajor() {
		return mTargetMajor;
	}

	public void setTargetMajor(String targetMajor) {
		mTargetMajor = targetMajor;
	}

	public String getDescriPtion() {
		return mDescriPtion;
	}

	public void setDescriPtion(String descriPtion) {
		mDescriPtion = descriPtion;
	}

	public String getContacts() {
		return mContacts;
	}

	public void setContacts(String contacts) {
		mContacts = contacts;
	}

	public String getContactType() {
		return mContactType;
	}

	public void setContactType(String contactType) {
		mContactType = contactType;
	}

	public String getFaxCode() {
		return mFaxCode;
	}

	public void setFaxCode(String faxCode) {
		mFaxCode = faxCode;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public String getRelateLink() {
		return mRelateLink;
	}

	public void setRelateLink(String relateLink) {
		mRelateLink = relateLink;
	}

	public int getScanNum() {
		return mScanNum;
	}

	public void setScanNum(int scanNum) {
		mScanNum = scanNum;
	}

}
