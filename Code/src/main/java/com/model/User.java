package com.model;

import java.sql.Date;
import java.time.LocalDate;

public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String password;
	private String role;//Customer or Admin
	private String customerType;//Silver or Gold or Platinum
	private String phone;
	private String emailId;
	private String address;
	private Date dateOfBirth;
	public User(int userId, String firstName, String lastName, String password, String role, String customerType,
			String phone, String emailId, String address, Date dateOfBirth) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.customerType = customerType;
		this.phone = phone;
		this.emailId = emailId;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", role=" + role + ", customerType=" + customerType + ", phone=" + phone + ", emailId="
				+ emailId + ", address=" + address + ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	
}

