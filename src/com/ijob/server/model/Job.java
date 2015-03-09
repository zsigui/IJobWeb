package com.ijob.server.model;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * Created by JackieZhuang on 2015/1/18.
 */
public class Job implements Serializable{

	private static final long serialVersionUID = -7986843381796504247L;
	
	private int mId;
	private int mOrgId;
	private String mName;
	private String mLocation;
	private String mPubTime;
	private String mDeadline;
	private String mRecruitNum;
	private String mMonthlyPay;
	private String mCategory;
	private String mType;
	private String mGenderRequire;
	private String mLanguageRequire;
	private String mProficiency;
	private String mMinQualification;
	private String mMajorRequire;
	private String mDescription;
	private String mContactType;
	private int mScanNum = 0;

	public Job() {
	}

	public Job(int id, int orgId, String name, String location, String pubTime, String deadline, String recruitNum,
	           String monthlyPay, String category, String type, String genderRequire, String languageRequire,
	           String proficiency, String minQualification, String majorRequire, String description,
	           String contactType, int scanNum) {
		mId = id;
		mOrgId = orgId;
		mName = name;
		mLocation = location;
		mPubTime = pubTime;
		mDeadline = deadline;
		mRecruitNum = recruitNum;
		mMonthlyPay = monthlyPay;
		mCategory = category;
		mType = type;
		mGenderRequire = genderRequire;
		mLanguageRequire = languageRequire;
		mProficiency = proficiency;
		mMinQualification = minQualification;
		mMajorRequire = majorRequire;
		mDescription = description;
		mContactType = contactType;
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

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		mLocation = location;
	}

	public String getPubTime() {
		return mPubTime;
	}

	public void setPubTime(String pubTime) {
		mPubTime = pubTime;
	}

	public String getDeadline() {
		return mDeadline;
	}

	public void setDeadline(String deadline) {
		mDeadline = deadline;
	}

	public String getRecruitNum() {
		return mRecruitNum;
	}

	public void setRecruitNum(String recruitNum) {
		mRecruitNum = recruitNum;
	}

	public String getMonthlyPay() {
		return mMonthlyPay;
	}

	public void setMonthlyPay(String monthlyPay) {
		mMonthlyPay = monthlyPay;
	}

	public String getCategory() {
		return mCategory;
	}

	public void setCategory(String category) {
		mCategory = category;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		mType = type;
	}

	public String getGenderRequire() {
		return mGenderRequire;
	}

	public void setGenderRequire(String genderRequire) {
		mGenderRequire = genderRequire;
	}

	public String getLanguageRequire() {
		return mLanguageRequire;
	}

	public void setLanguageRequire(String languageRequire) {
		mLanguageRequire = languageRequire;
	}

	public String getProficiency() {
		return mProficiency;
	}

	public void setProficiency(String proficiency) {
		mProficiency = proficiency;
	}

	public String getMinQualification() {
		return mMinQualification;
	}

	public void setMinQualification(String minQualification) {
		mMinQualification = minQualification;
	}

	public String getMajorRequire() {
		return mMajorRequire;
	}

	public void setMajorRequire(String majorRequire) {
		mMajorRequire = majorRequire;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public String getContactType() {
		return mContactType;
	}

	public void setContactType(String contactType) {
		mContactType = contactType;
	}

	public int getScanNum() {
		return mScanNum;
	}

	public void setScanNum(int scanNum) {
		mScanNum = scanNum;
	}

	/**
	 * 数据库参数字段
	 */
	public static abstract class ParamName {

		public static final String DB_NAME = "jobs";

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/cp.jobs";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/cp.jobs";

		public static final String ID = "job_id";
		public static final String ORG_ID = "job_org_id";
		public static final String NAME = "job_name";
		public static final String LOCATION = "job_location";
		public static final String PUB_TIME = "job_pub_time";
		public static final String DEADLINE = "job_deadline";
		public static final String RECRUIT_NUM = "job_recruit_num";
		public static final String MONTHLY_PAY = "job_monthly_pay";
		public static final String CATEGORY = "job_category";
		public static final String TYPE = "job_type";
		public static final String GENDER_REQUIRE = "job_gender_require";
		public static final String LANGUAGE_REQUIRE = "job_language_require";
		public static final String PROFICIENCY = "job_proficiency";
		public static final String MIN_QUALIFICATION = "job_min_qualification";
		public static final String MAJOR_REQUIRE = "job_major_require";
		public static final String DESCRIPTION = "job_description";
		public static final String CONTACT_TYPE = "job_contactType";
		public static final String SCAN_NUM = "job_scan_num";

		public static ArrayList<String> getRequiredColumns() {
			ArrayList<String> columns = new ArrayList<>();
			columns.add(ParamName.ID);
			columns.add(ParamName.ORG_ID);
			columns.add(ParamName.NAME);
			columns.add(ParamName.LOCATION);
			columns.add(ParamName.PUB_TIME);
			columns.add(ParamName.DEADLINE);
			columns.add(ParamName.RECRUIT_NUM);
			columns.add(ParamName.MONTHLY_PAY);
			columns.add(ParamName.CATEGORY);
			columns.add(ParamName.TYPE);
			columns.add(ParamName.GENDER_REQUIRE);
			columns.add(ParamName.LANGUAGE_REQUIRE);
			columns.add(ParamName.PROFICIENCY);
			columns.add(ParamName.MIN_QUALIFICATION);
			columns.add(ParamName.MAJOR_REQUIRE);
			columns.add(ParamName.DESCRIPTION);
			columns.add(ParamName.CONTACT_TYPE);
			columns.add(ParamName.SCAN_NUM);
			return columns;
		}

	}
}
