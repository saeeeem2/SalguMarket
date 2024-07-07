package com.SalGuMarket.www.controller;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.service.SellBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

	private final SellBoardService sellBoardService;
	
	@GetMapping("/")
	public String index(Model model, PagingVO pgvo) {
		
		// categoryImageList의 개수는 8개
		pgvo.setQty(8);
		
		// 첫 화면 뿌릴때, 페이지네이션의 type과 keyword는 공백
		if(pgvo.getType() == "") {
			pgvo.setType(null);
		}
		if(pgvo.getKeyword() == "") {
			pgvo.setKeyword(null);
		}
	    
	    List<FileVO> categoryImageList = sellBoardService.get8MainImage(pgvo);
	    for(FileVO file : categoryImageList) {
	    	file.setSaveDir(file.getSaveDir().replace(File.separator, "/"));
	    }
	    
	    int totalCount = sellBoardService.getTotalCount(pgvo);
	    if(totalCount == 0) {
	    	totalCount = 1;
	    }
	    
	    PagingHandler ph = new PagingHandler(pgvo, totalCount);
		
		model.addAttribute("categoryImageList", categoryImageList);
		model.addAttribute("ph", ph);
		return "index";
	}
}
