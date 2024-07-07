package com.SalGuMarket.www.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SalGuMarket.www.domain.CommentVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping("/post")
	@ResponseBody
	public String post(@RequestBody CommentVO cvo) {
		int isOk=commentService.post(cvo);
		return isOk>0? "1":"0";
	}
	
	@GetMapping("/{bno}/{page}")
	@ResponseBody
	public PagingHandler list(@PathVariable("bno")long bno, @PathVariable("page")int page) {
		log.info(">>>>>>>bno>>>>"+bno+"/ page>>"+page);
		PagingVO pgvo = new PagingVO(page,5);
		PagingHandler ph = commentService.getList(bno,pgvo);
		return ph;
	}
	
	@PutMapping("/edit")
	@ResponseBody
	public String edit(@RequestBody CommentVO cvo) {
		log.info(">>>>>cvo>>>>>"+cvo);
		int isOk=commentService.edit(cvo);
		return isOk>0?"1":"0";
	}
	
	@DeleteMapping("/{cno}")
	@ResponseBody
	public String remove(@PathVariable("cno")long cno) {
		log.info(">>> cno >>"+ cno);
		int isOK = commentService.remove(cno);
		return isOK>0? "1":"0";
	}
}
