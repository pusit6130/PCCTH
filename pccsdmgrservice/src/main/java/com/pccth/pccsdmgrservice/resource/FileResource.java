package com.pccth.pccsdmgrservice.resource;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.pccth.pccsdmgrservice.model.Employee;
import com.pccth.pccsdmgrservice.model.FileNews;
import com.pccth.pccsdmgrservice.model.News;
import com.pccth.pccsdmgrservice.repo.NewsRepo;
import com.pccth.pccsdmgrservice.service.FileNewsService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;



@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:4200")
public class FileResource {
	private final FileNewsService fileNewsService;

	public FileResource(FileNewsService fileNewsService) {
		super();
		this.fileNewsService = fileNewsService;
	}

	// define a location
    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/upload/";

    // Define a method to upload files
    @PostMapping("/upload/{idnews}")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles,@PathVariable("idnews") String idnews) throws IOException {
       System.out.println("///////////////////////Back-end: "+idnews+multipartFiles);
    	List<String> filenames = new ArrayList<>();
        for(MultipartFile file : multipartFiles) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
            FileNews afn = new FileNews(null,filename,idnews);
            FileNews addfilenews = fileNewsService.addFileNews(afn);
           
        }
        return ResponseEntity.ok().body(filenames);
    }

    // Define a method to download files
    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        System.out.println(filePath.toString());
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }
    
    @GetMapping("/findNews/{id}")
    public ResponseEntity<List<String>> getNewsById (@PathVariable("id") Long id) {
      List<String> filenews = fileNewsService.findNewsById(id);
      return new ResponseEntity<>(filenews, HttpStatus.OK);
  }
    
}
