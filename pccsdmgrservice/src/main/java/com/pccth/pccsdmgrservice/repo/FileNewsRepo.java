package com.pccth.pccsdmgrservice.repo;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.pccth.pccsdmgrservice.model.Employee;
import com.pccth.pccsdmgrservice.model.FileNews;

public interface FileNewsRepo extends JpaRepository<FileNews, Long>{
	
	 @Query(value = "SELECT filenews_name FROM employeemanager.file_news where idnews = ?", nativeQuery = true)
	 List<String> findNewsById(Long idnews);
	 
}
