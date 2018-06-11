package com.example.demo.web;

import com.example.demo.dao.ImageRepository;
import com.example.demo.domain.Book;
import com.example.demo.domain.Image;
import com.example.demo.service.BookService;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import org.apache.commons.io.FileUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ManagerController {
	@Autowired
	BookService bookService;

	@Autowired
	ImageRepository imageRepository;

	@RequestMapping("/addbook")
	public void bookadd(@RequestParam(name="name") String name,
	                    @RequestParam(name="writer") String writer,
	                    @RequestParam(name="price")Integer price,
	                    @RequestParam(name="storage")Integer storage,
	                    @RequestParam(name="description",defaultValue = "")String description,
	                    @RequestParam(name="publishTime") String publishTime,
	                    @RequestParam(name="img")MultipartFile file
	                    ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dpublishTime=simpleDateFormat.parse(publishTime);
			String classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/static/image/";
			FileUtils.writeByteArrayToFile(new File(classPath+name+".jpg"),file.getBytes());
			Image image = new Image(name, file.getBytes());
			imageRepository.save(image);
			bookService.addBook(name,writer,price,storage,description,dpublishTime);
		}
		catch (Exception ex){
			return;
		}
	}

	@RequestMapping("/removebook")
	public void delete(@RequestParam(name="id") Long id){
		bookService.removeBook(id);
	}

	@RequestMapping("/updatebook")
	public Book updatebook(@RequestParam(name="id")Long id,
	                       @RequestParam(name = "name") String name,
	                    @RequestParam(name="writer") String writer,
	                    @RequestParam(name="price")Integer price,
	                    @RequestParam(name="storage")Integer storage,
	                    @RequestParam(name="description")String description,
					    @RequestParam(name="publishTime")String publishTime,
	                    @RequestParam(name="img")MultipartFile file
	){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dpublishTime=simpleDateFormat.parse(publishTime);
			String classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/static/image/";
			FileUtils.writeByteArrayToFile(new File(classPath+name+".jpg"),file.getBytes());
			return bookService.updateBook(id,name,writer,price,storage,description,dpublishTime);
		}
		catch (Exception ex){
			return null;
		}
	}


}

