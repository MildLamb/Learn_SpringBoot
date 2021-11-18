package com.mildlamb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class Springboot09TaskApplicationTests {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    void contextLoads() {

        //发送简单的邮件

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置邮件主题
        simpleMailMessage.setSubject("我尊重挺身而出的人，而非黯然陨落的人");
        //设置邮件内容
        simpleMailMessage.setText("屏住呼吸才能稳定，心不乱自然平静");
        //从谁发出 发给谁
        simpleMailMessage.setTo("1902524390@qq.com");
        simpleMailMessage.setFrom("1216982545@qq.com");


        mailSender.send(simpleMailMessage);
    }

    @Test
    void contextLoads2() throws MessagingException, IOException {
        //发送复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        helper.setSubject("这是邮件主题");
        //设置邮件文本
        helper.setText("<p style='color:blue'>我尊重挺身而出的人，而非黯然陨落的人</p>",true);

        //添加附件
        File file = new File("src/main/resources/static/images/kind.jpg");
//        InputStream inputStream = new FileInputStream(file);
//        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        helper.addAttachment("kindred.jpg",file);

        //从谁发出 发给谁
        helper.setTo("1902524390@qq.com");
        helper.setFrom("1216982545@qq.com");

        mailSender.send(mimeMessage);
    }

}
