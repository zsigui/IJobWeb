package com.ijob.server.model;

import java.io.Serializable;



/**
 * Created by JackieZhuang on 2015/1/18.
 */
public class Orgnization implements Serializable{

	private static final long serialVersionUID = -2906741481952172523L;
	
	private int mId;
	private String mName;
	private String mIndustry;
	private String mNature;
	private String mScale;
	private String mRegisteredCapital;
	private String mRealCapital;
	private String mType;
	private String mWebsite;
	private String mDescription;
	private String mAddress;
	private String mPostalCode;
	private String mContacts;
	private String mEmail;
	private String mPic;
	private String mScanNum;

	public Orgnization() {
	}

	public Orgnization(int id, String name, String industry, String nature, String scale, String registeredCapital,
	                   String realCapital, String type, String website, String description, String address,
	                   String postalCode, String contacts, String email, String pic, String scanNum) {
		mId = id;
		mName = name;
		mIndustry = industry;
		mNature = nature;
		mScale = scale;
		mRegisteredCapital = registeredCapital;
		mRealCapital = realCapital;
		mType = type;
		mWebsite = website;
		mDescription = description;
		mAddress = address;
		mPostalCode = postalCode;
		mContacts = contacts;
		mEmail = email;
		mPic = pic;
		mScanNum = scanNum;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getIndustry() {
		return mIndustry;
	}

	public void setIndustry(String industry) {
		mIndustry = industry;
	}

	public String getNature() {
		return mNature;
	}

	public void setNature(String nature) {
		mNature = nature;
	}

	public String getScale() {
		return mScale;
	}

	public void setScale(String scale) {
		mScale = scale;
	}

	public String getRegisteredCapital() {
		return mRegisteredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		mRegisteredCapital = registeredCapital;
	}

	public String getRealCapital() {
		return mRealCapital;
	}

	public void setRealCapital(String realCapital) {
		mRealCapital = realCapital;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		mType = type;
	}

	public String getWebsite() {
		return mWebsite;
	}

	public void setWebsite(String website) {
		mWebsite = website;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public String getPostalCode() {
		return mPostalCode;
	}

	public void setPostalCode(String postalCode) {
		mPostalCode = postalCode;
	}

	public String getContacts() {
		return mContacts;
	}

	public void setContacts(String contacts) {
		mContacts = contacts;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public String getPic() {
		return mPic;
	}

	public void setPic(String pic) {
		mPic = pic;
	}

	public String getScanNum() {
		return mScanNum;
	}

	public void setScanNum(String scanNum) {
		mScanNum = scanNum;
	}

}
