package com.SalGuMarket.www.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.SalGuMarket.www.repository.MemberMapper;
import com.SalGuMarket.www.security.MemberVO;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
	public final MemberMapper memberMapper;
	public final JavaMailSender javaMailSender;
	
	@Override
	public Map<String, String> check(MemberVO mvo) {
	    Map<String, String> resultMap = new HashMap<>();

	    MemberVO mvo2 = memberMapper.check(mvo);
	    
	    if (mvo2 == null) {
	        log.info("해당 회원 없음");
	        resultMap.put("status", "0");
	        return resultMap;
	    }

	    if (mvo2.getEmail() != null && !mvo2.getEmail().isEmpty()) {
	        try {
	        	MimeMessage message = javaMailSender.createMimeMessage();
	            String authKey = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));
	            message.setSubject("살구마켓 인증번호 발송");
	            message.setRecipients(MimeMessage.RecipientType.TO,mvo2.getRegEmail());
	            String body = "<div style='font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;'>";
	            body += "<div style='background-color: #fff; border-radius: 10px; padding: 20px;'>";
	            body += "<h1 style='color: #333; font-size: 24px; margin-bottom: 40px;'>살구마켓</h1>";
	            body += "<p style='color: #555; font-size: 18px;'>요청하신 인증 번호입니다:</p>";
	            body += "<h2 style='color: #007bff; font-size: 36px; margin: 20px 0;'>" + authKey + "</h2>";
	            body += "<p style='color: #555; font-size: 18px;'>감사합니다.</p>";
	            body += "</div></div>";
	            message.setText(body, "utf-8", "html");
	            log.info("인증번호 전송성공");
	            javaMailSender.send(message);

	            // resultMap에 데이터 추가
	            resultMap.put("email", mvo2.getEmail());
	            resultMap.put("authkey", authKey);
	            resultMap.put("status", "1");
	            return resultMap;
	        } catch (Exception e) {
	            log.info("인증번호 전송실패");
	        }
	    }

	    resultMap.put("status", "0");
	    return resultMap;
	}

}
