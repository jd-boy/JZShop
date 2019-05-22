package com.shop.util;

import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


@Component
public class MyMailSender {
    private static final Logger logger = LoggerFactory.getLogger(MyMailSender.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    /*@Autowired
    private VelocityEngine velocityEngine;*/

    @Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
    

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            String nick = MimeUtility.encodeText("JZ商城");
            InternetAddress from = new InternetAddress(nick + "<1298462930@qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            
            String result = FreeMarkerTemplateUtils.processTemplateIntoString(
            		freeMarkerConfigurer.getConfiguration().getTemplate(template), model);
            /*String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);*/
            mimeMessageHelper.setTo(to); 
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(new String(result), true);
            mailSender.send(mimeMessage);
        	
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
