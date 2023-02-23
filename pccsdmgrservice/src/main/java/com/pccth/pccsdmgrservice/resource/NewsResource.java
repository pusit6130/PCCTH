package com.pccth.pccsdmgrservice.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pccth.pccsdmgrservice.model.Employee;
import com.pccth.pccsdmgrservice.model.News;
import com.pccth.pccsdmgrservice.service.NewsService;

@RestController
@RequestMapping("/news")
public class NewsResource {
	 private final NewsService newsService;

	public NewsResource(NewsService newsService) {
		this.newsService = newsService;
	}
	 
	@PostMapping("/addnews")
    public ResponseEntity<?> addnews(@RequestBody News news) {
		System.out.println("-----------------------Back-end: "+news.getNews_name());
		Date newsDate = new Date();
		news.setNews_date(newsDate);
		News addNewsForm = newsService.addNews(news);
		return new ResponseEntity<>(addNewsForm, HttpStatus.OK);
	}
	
	@GetMapping("/allnews")
    public ResponseEntity<List<News>> getAllNews () {
		 List<News> news = newsService.findAllNews();
		 return new ResponseEntity<>(news, HttpStatus.OK);
    } 
	
//	@PutMapping("/update")
//	public ResponseEntity<News> updateEmployee(@RequestBody News news) {
//	      News updateNews= newsService.updateNews(news);
//	      return new ResponseEntity<>(updateNews, HttpStatus.OK);
//	 }
	    
//    @GetMapping("/findNews/{id}")
//    public ResponseEntity<News> getNewsById (@PathVariable("id") Long id) {
//        News news = newsService.findNewsById(id);
//        return new ResponseEntity<>(news, HttpStatus.OK);
//    }
	
}
