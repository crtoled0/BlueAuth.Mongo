package com.bz.blueauth.dto.pojo;

import java.util.Map;

import com.bz.blueauth.dto.DataTransferObject;

public class UserPO extends DataTransferObject {
	private String userid;
	private String email;
	private String firstName;
	private String lastName;
    private String password;   

	public UserPO(){

	}

    public UserPO(String userid, String email, String password) {
		this.userid = userid;
		this.email = email;
		this.password = password;
	}
	public UserPO(String userid, String email, String password, String firstName, String lastName) {
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UserPO(String userid, String password) {
		this.userid = userid;
		this.password = password;
	}
	
	public UserPO(Map<String,?> usrMap) {
		this.userid = (String)usrMap.get("userid");
		this.email = (String)usrMap.get("email");
		this.password = (String)usrMap.get("password");
		this.firstName = (String)usrMap.get("firstName");
		this.lastName = (String)usrMap.get("lastName");
    }
    
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	

}

