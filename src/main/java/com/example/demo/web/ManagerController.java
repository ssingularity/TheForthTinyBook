package com.example.demo.web;

		import com.example.demo.domain.Book;
		import com.example.demo.service.BookService;
		import com.sun.org.apache.regexp.internal.RE;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RequestParam;
		import org.springframework.web.bind.annotation.RestController;

		import java.text.ParseException;
		import java.text.SimpleDateFormat;
		import java.util.Date;

@RestController
public class ManagerController {
	@Autowired
	BookService bookService;

	@RequestMapping("/addbook")
	public Book bookadd(@RequestParam(name="name") String name,
	                    @RequestParam(name="writer") String writer,
	                    @RequestParam(name="price")Integer price,
	                    @RequestParam(name="storage")Integer storage,
	                    @RequestParam(name="description",defaultValue = "")String description,
                        @RequestParam(name="publishTime") String publishTime
	){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dpublishTime=simpleDateFormat.parse(publishTime);
			return bookService.addBook(name,writer,price,storage,description,dpublishTime);
		}
		catch (ParseException ex){
			return null;
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
					    @RequestParam(name="publishTime")String publishTime
	){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dpublishTime=simpleDateFormat.parse(publishTime);
			return bookService.updateBook(id,name,writer,price,storage,description,dpublishTime);
		}
		catch (ParseException ex){
			return null;
		}
	}

}

