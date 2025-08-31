package com.tibafit.user.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "account_status", nullable = false)
	private Integer accountStatus = 1;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, updatable = false)
	private Date createTime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false)
	private Date updateTime;

	@Column(name = "forgot_password_url", nullable = false)
	private String forgotPasswordURL;

	@Column(name = "name")
	private String name;

	@Column(name = "nick_name")
	private String nickName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "profile_picture")
	private String profilePicture;

	@Column(name = "gender")
	private Integer gender;

	@Column(name = "height_cm", precision = 5, scale = 2)
	private BigDecimal heightCm;

	@Column(name = "weight_kg", precision = 5, scale = 2)
	private BigDecimal weightKg;

	@Column(name = "bmi", precision = 4, scale = 2)
	private BigDecimal bmi;

	@Column(name = "points_balance", nullable = false)
	private Integer pointsBalance = 0;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer userId, String email, String password, Integer accountStatus, Date createTime, Date updateTime,
			String forgotPasswordURL, String name, String nickName, String phone, String profilePicture, Integer gender,
			BigDecimal heightCm, BigDecimal weightKg, BigDecimal bmi, Integer pointsBalance) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.accountStatus = accountStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.forgotPasswordURL = forgotPasswordURL;
		this.name = name;
		this.nickName = nickName;
		this.phone = phone;
		this.profilePicture = profilePicture;
		this.gender = gender;
		this.heightCm = heightCm;
		this.weightKg = weightKg;
		this.bmi = bmi;
		this.pointsBalance = pointsBalance;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getForgotPasswordURL() {
		return forgotPasswordURL;
	}

	public void setForgotPasswordURL(String forgotPasswordURL) {
		this.forgotPasswordURL = forgotPasswordURL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public BigDecimal getHeightCm() {
		return heightCm;
	}

	public void setHeightCm(BigDecimal heightCm) {
		this.heightCm = heightCm;
	}

	public BigDecimal getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(BigDecimal weightKg) {
		this.weightKg = weightKg;
	}

	public BigDecimal getBmi() {
		return bmi;
	}

	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	public Integer getPointsBalance() {
		return pointsBalance;
	}

	public void setPointsBalance(Integer pointsBalance) {
		this.pointsBalance = pointsBalance;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", accountStatus="
				+ accountStatus + ", createTime=" + createTime + ", updateTime=" + updateTime + ", forgotPasswordURL="
				+ forgotPasswordURL + ", name=" + name + ", nickName=" + nickName + ", phone=" + phone
				+ ", profilePicture=" + profilePicture + ", gender=" + gender + ", heightCm=" + heightCm + ", weightKg="
				+ weightKg + ", bmi=" + bmi + ", pointsBalance=" + pointsBalance + "]";
	}

}