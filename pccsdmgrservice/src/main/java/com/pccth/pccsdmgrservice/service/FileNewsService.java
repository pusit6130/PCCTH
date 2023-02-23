package com.pccth.pccsdmgrservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pccth.pccsdmgrservice.model.FileNews;
import com.pccth.pccsdmgrservice.repo.FileNewsRepo;

@Service
@Transactional
public class FileNewsService {
	 private final FileNewsRepo filenewsRepo;
	 
	 @Autowired
	 public FileNewsService(FileNewsRepo filenewsRepo) {
	   this.filenewsRepo = filenewsRepo;
	 }
	 
	 public FileNews addFileNews(FileNews filenews) {
		   return filenewsRepo.save(filenews);
	 }
	 
	 public List<String> findNewsById(Long id) {
		 List<String> vv= filenewsRepo.findNewsById(id);
//		 System.out.println("------------------Back end--------------------:"+vv.size());
		 return filenewsRepo.findNewsById(id);
	 }

}
