package com.example.demo.web;

import com.example.demo.dao.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
public class ImageController {
	@Autowired
	ImageRepository imageRepository;

	@RequestMapping("/getBookImg/{name}")
	public void getBookImg(@PathVariable(name="name")String name, HttpServletResponse response){
		try{
			byte[] image = imageRepository.findByName(name).getImage();
			/*ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			File file = new File(name);
			ImageIO.write(bufferedImage,"jpg",file);
			response.setHeader("Content-Type","image/jped");
			return image;*/
			response.setContentType("image/jpeg");
			//response.setHeader("Content-Disposition", null);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(image);
			outputStream.flush();
			outputStream.close();
		}
		catch (Exception ex){
		}
	}
}
