package com.pccth.pccsdmgrservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class News {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(nullable = false, updatable = false)
	 private Long idnews;
	 private String news_name;
	 private String news_detail;
	 private String news_imgeurl;
	 private Date news_date;
	 
	 public News() {}

	public News(Long idnews, String news_name, String news_detail, String news_imgeurl, Date news_date) {
		super();
		this.idnews = idnews;
		this.news_name = news_name;
		this.news_detail = news_detail;
		this.news_imgeurl = news_imgeurl;
		this.news_date = news_date;
	}

	public Long getIdnews() {
		return idnews;
	}

	public void setIdnews(Long idnews) {
		this.idnews = idnews;
	}

	public String getNews_name() {
		return news_name;
	}

	public void setNews_name(String news_name) {
		this.news_name = news_name;
	}

	public String getNews_detail() {
		return news_detail;
	}

	public void setNews_detail(String news_detail) {
		this.news_detail = news_detail;
	}

	public String getNews_imgeurl() {
		return news_imgeurl;
	}

	public void setNews_imgeurl(String news_imgeurl) {
		this.news_imgeurl = news_imgeurl;
	}

	public Date getNews_date() {
		return news_date;
	}

	public void setNews_date(Date news_date) {
		this.news_date = news_date;
	}

	
}
