package com.pccth.pccsdmgrservice.model;

public class RegisterResponse {
	private boolean using2FA;
	private String qrCodeImage;
	public RegisterResponse(boolean using2fa, String qrCodeImage) {
		super();
		using2FA = using2fa;
		this.qrCodeImage = qrCodeImage;
	}
	public boolean isUsing2FA() {
		return using2FA;
	}
	public void setUsing2FA(boolean using2fa) {
		using2FA = using2fa;
	}
	public String getQrCodeImage() {
		return qrCodeImage;
	}
	public void setQrCodeImage(String qrCodeImage) {
		this.qrCodeImage = qrCodeImage;
	}
	
	
}