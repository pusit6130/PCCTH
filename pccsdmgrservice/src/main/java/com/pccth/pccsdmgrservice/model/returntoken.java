package com.pccth.pccsdmgrservice.model;

public class returntoken {
	private String username;
	private String email;
	private String accessToken;
	private String secret;
	private boolean authenticated;
	
	public returntoken(String username, String email, String accessToken, boolean authenticated, String secret) {
		super();
		this.username = username;
		this.email = email;
		this.accessToken = accessToken;
		this.authenticated = authenticated;
		this.secret = secret;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
}

