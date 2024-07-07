package com.SalGuMarket.www;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.ProductDTO;
import com.SalGuMarket.www.domain.ProductVO;
import com.SalGuMarket.www.handler.FileHandler;
import com.SalGuMarket.www.service.ProductService;

@SpringBootTest
public class productCreateTest {

	@Autowired
	private ProductService productService;
	@Autowired
	private FileHandler fileHandler;
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Test
	public void productCreate() {
		
		long price = 10;
		
		for(int i=0; i<23; i++) {
			ProductVO pvo = new ProductVO();
			pvo.setTitle("beautyTestTitle" + i);
			pvo.setCategory("beauty");
			pvo.setContent("beautyTestContent" + i);
			pvo.setPrice(price);
			pvo.setSell("y");
			
			testImageFileCreate(pvo);
		}
		
		for(int i=0; i<23; i++) {
			ProductVO pvo = new ProductVO();
			pvo.setTitle("clothesTestTitle" + i);
			pvo.setCategory("clothes");
			pvo.setContent("clothesTestContent" + i);
			pvo.setPrice(price);
			pvo.setSell("y");
			
			testImageFileCreate(pvo);
		}
		
		for(int i=0; i<23; i++) {
			ProductVO pvo = new ProductVO();
			pvo.setTitle("elecTestTitle" + i);
			pvo.setCategory("elec");
			pvo.setContent("elecTestContent" + i);
			pvo.setPrice(price);
			pvo.setSell("y");
			
			testImageFileCreate(pvo);
		}
		
		for(int i=0; i<23; i++) {
			ProductVO pvo = new ProductVO();
			pvo.setTitle("ticketTestTitle" + i);
			pvo.setCategory("ticket");
			pvo.setContent("ticketTestContent" + i);
			pvo.setPrice(price);
			pvo.setSell("y");
			
			testImageFileCreate(pvo);
		}
		
		for(int i=0; i<23; i++) {
			ProductVO pvo = new ProductVO();
			pvo.setTitle("animalTestTitle" + i);
			pvo.setCategory("animal");
			pvo.setContent("animalTestContent" + i);
			pvo.setPrice(price);
			pvo.setSell("y");
			
			testImageFileCreate(pvo);
		}
		
		for(int i=0; i<123; i++) {
			ProductVO pvo = new ProductVO();
			pvo.setTitle("freeTestTitle" + i);
			pvo.setCategory("free");
			pvo.setContent("freeTestContent" + i);
			pvo.setPrice(price);
			pvo.setSell("n");
			
			testImageFileCreate(pvo);
		}
	}
	
	private void testImageFileCreate(ProductVO pvo) {
		
		Resource resource = resourceLoader.getResource("classpath:testImage.jpg");
	    MultipartFile multipartFile = null;

	    try (InputStream inputStream = resource.getInputStream()) {
	        multipartFile = new MockMultipartFile("file", "testImage.jpg", "image/jpeg", inputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	        fail("Failed to load test image file");
	    }
		
		MultipartFile[] files = new MultipartFile[] {multipartFile};
		
		List<FileVO> flist1 = null;
		List<FileVO> flist2 = null;
		if (files[0].getSize() > 0) {
            flist1 = fileHandler.uploadMainIamgeFile(files);
        }
		if (files[0].getSize() > 0) {
            flist2 = fileHandler.uploadMinorImageFile(files);
        }
		
		productService.saveProduct(new ProductDTO(pvo, flist1, flist2));
	}
}
