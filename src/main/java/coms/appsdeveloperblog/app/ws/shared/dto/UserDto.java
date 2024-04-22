package coms.appsdeveloperblog.app.ws.shared.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

	private static final long serialVersionUID = -4469139699692664144L; 
	private String userId;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String encryptedpassword;
	private String emailverificationToken;
	private boolean emailverificationStatus = false;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public String getEncryptedpassword() {
		return encryptedpassword;
	}

	public void setEncryptedpassword(String encryptedpassword) {
		this.encryptedpassword = encryptedpassword;
	}

	public String getEmailverificationToken() {
		return emailverificationToken;
	}

	public void setEmailverificationToken(String emailverificationToken) {
		this.emailverificationToken = emailverificationToken;
	}

	public boolean isEmailverificationStatus() {
		return emailverificationStatus;
	}

	public void setEmailverificationStatus(boolean emailverificationStatus) {
		this.emailverificationStatus = emailverificationStatus;
	}

}
