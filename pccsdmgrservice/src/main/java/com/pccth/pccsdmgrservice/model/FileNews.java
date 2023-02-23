package com.pccth.pccsdmgrservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileNews {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(nullable = false, updatable = false)
	 private Long idfilenews;
	 private String filenews_name;
	 private String idnews;
	 
	public FileNews() {}

	public FileNews(Long idfilenews, String filenews_name, String idnews) {
		super();
		this.idfilenews = idfilenews;
		this.filenews_name = filenews_name;
		this.idnews = idnews;
	}

	public Long getIdfilenews() {
		return idfilenews;
	}

	public void setIdfilenews(Long idfilenews) {
		this.idfilenews = idfilenews;
	}

	public String getFilenews_name() {
		return filenews_name;
	}

	public void setFilenews_name(String filenews_name) {
		this.filenews_name = filenews_name;
	}

	public String getIdnews() {
		return idnews;
	}

	public void setIdnews(String idnews) {
		this.idnews = idnews;
	}
	
	
	 
	 
}
