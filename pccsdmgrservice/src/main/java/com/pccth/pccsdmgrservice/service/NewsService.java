package com.pccth.pccsdmgrservice.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pccth.pccsdmgrservice.exception.UserNotFoundException;
import com.pccth.pccsdmgrservice.model.Employee;
import com.pccth.pccsdmgrservice.model.News;
import com.pccth.pccsdmgrservice.repo.NewsRepo;

@Service
@Transactional
public class NewsService {
	 private final NewsRepo newsRepo;
	 
	 @Autowired
	 public NewsService(NewsRepo newsRepo) {
	   this.newsRepo = newsRepo;
	 }
	 
	 public News addNews(News news) {
	    return newsRepo.save(news);
	 }
	 
	 public List<News> findAllNews(){
		    return newsRepo.findAll();
	 }
	 
//	 public News findNewsById(Long id) {
//	        return newsRepo.findNewsById(id)
//	                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
//	 }
	 
//	 public News updateNews(News news) {
//	        return newsRepo.save(news);
//	 }
	 

}
