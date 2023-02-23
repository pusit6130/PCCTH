package com.pccth.pccsdmgrservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pccth.pccsdmgrservice.model.Employee;
import com.pccth.pccsdmgrservice.model.News;

import java.util.Optional;

public interface NewsRepo extends JpaRepository<News, Long> {
//	Optional<News> findNewsById(Long id);
}

