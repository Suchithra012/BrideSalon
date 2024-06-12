package com.example.HairSalon.mail;

import com.example.HairSalon.Entity.User;
import com.example.HairSalon.Repository.UserRepo;
import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MailService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    Configuration configuration;

    @Async
    public void emailAboutCustomer(User user) {

            Map<String, Object> model = new HashMap<>();
            model.put("username", user.getUsername() );
            model.put("email", user.getEmail());
            model.put("branch", user.getSalonBranch());
            simpleEmail("A new user  has added", model, "userAdded");


    }
    @Async
    public void simpleEmail(String subject, Map<String, Object> model, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();


        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MMMMM, dd yyyy 'at' hh:mm:ss aa z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
        model.put("datetime", dateFormat.format(date));

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo("moneshkumarvelavan@gmail.com");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(mailFrom);
            String emailContent = getEmailContent(model, content);
            mimeMessageHelper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String getEmailContent(Map<String, Object> model, String templateName) {
        StringBuffer content = new StringBuffer();
        try {
            content.append( FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(templateName + ".ftlh"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
