package cn.techsofts.faceRecognition.utils;

import cn.techsofts.faceRecognition.pojo.Email;
import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Properties;

/**
 * @author livingwater
 * @title: EmailUtil
 * @projectName smarthome
 * @description: TODO
 * @date 2019/4/2922:36
 */
public class EmailUtil {
    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private JavaMailSenderImpl javaMailSender;

    /**
     * 发送邮件
     */
    public Email sendEmail(Email email) {
        initJavaEmailSender();
        try {
            checkMail(email); //1.检测邮件
            sendMimeEmail(email); //2.发送邮件
            log.info("邮件发送成功");
            return saveMail(email); //3.保存邮件//暂时没做
        } catch (Exception e) {
            log.error("邮件发送错误：" + e.getMessage());
            email.setStatus("fail");
            email.setError(e.getMessage());
            return email;
        }
    }

    //检测邮件信息类
    private void checkMail(Email EmailVO) {
        if (StringUtils.isEmpty(EmailVO.getTo())) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(EmailVO.getSubject())) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(EmailVO.getText())) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    //构建复杂邮件信息类
    private void sendMimeEmail(Email emailVO) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);//true表示支持复杂类型
            emailVO.setFrom("cbh@techsofts.cn");//邮件发信人从配置项读取
            messageHelper.setFrom(emailVO.getFrom());//邮件发信人
            messageHelper.setTo(emailVO.getTo().split(","));//邮件收信人
            messageHelper.setSubject(emailVO.getSubject());//邮件主题
            messageHelper.setText(emailVO.getText());//邮件内容
            if (!StringUtils.isEmpty(emailVO.getCc())) {//抄送
                messageHelper.setCc(emailVO.getCc().split(","));
            }
            if (!StringUtils.isEmpty(emailVO.getBcc())) {//密送
                messageHelper.setCc(emailVO.getBcc().split(","));
            }
            if (emailVO.getMultipartFiles() != null) {//添加邮件附件
                for (MultipartFile multipartFile : emailVO.getMultipartFiles()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }
//            if (StringUtils.isEmpty(emailVO.getSentDate() + "")) {
            emailVO.setSentDate(new Date());
//            }
            messageHelper.setSentDate(emailVO.getSentDate());
            javaMailSender.send(messageHelper.getMimeMessage());//正式发送邮件
            emailVO.setStatus("ok");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);//发送失败
        }
    }

    //保存邮件
    private Email saveMail(Email email) {
        //将邮件保存到数据库..
        email.setStatus("ok");
        return email;
    }

    private void initJavaEmailSender() {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setUsername("863203434@qq.com");
        javaMailSender.setPassword("vhxjnlnectwrbdaj");
        javaMailSender.setPort(587);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");
        javaMailSender.setJavaMailProperties(properties);
    }

    public static void main(String[] args) {
        Email email = new Email();
        email.setTo("863203434@qq.com");
        email.setSubject("你好");
        email.setText("我也好");
        new EmailUtil().sendEmail(email);
    }

    public static void sendSysInfoEmail(String message) {
        Email email = new Email();
        email.setTo("863203434@qq.com");
        email.setSubject("服务器信息紧急通知");
        email.setText(message);
        new EmailUtil().sendEmail(email);
    }
}