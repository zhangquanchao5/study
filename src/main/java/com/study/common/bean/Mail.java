package com.study.common.bean;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


/**
 * The type Mail.
 */
public class Mail {

    /**
     * 21-30行把本程序所用变量进行定义。 具体在main中对它们赋植。
     * MIME邮件对象 　
     */
    private MimeMessage mimeMsg;
    /**
     * 邮件会话对象
     */
    private Session session;
    /**
     * 系统属性
     */
    private Properties props;
    /**
     * smtp认证用户名
     */
    private String username = "";
    /**
     * smtp认证密码
     */
    private String password = "";
    /**
     * Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成//MimeMessage对象
     */
    private Multipart mp;

    /**
     * Instantiates a new Mail.
     *
     * @param smtp the smtp
     */
    public Mail(String smtp) {
        setSmtpHost(smtp);
        createMimeMessage();
    }

    /**
     * Sets smtp host.
     *
     * @param hostName String
     */
    public void setSmtpHost(String hostName) {
        if (props == null) {
            // 获得系统属性对象
            props = System.getProperties();
        }
        // 设置SMTP主机 　
        props.put("mail.smtp.host", hostName);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.starttls.enable","true");
    }

    /**
     * Create mime message.
     *
     * @return boolean boolean
     */
    public boolean createMimeMessage() {
        try {
            // 获得邮件会话对象
            session = Session.getDefaultInstance(props, null);
        } catch (Exception e) {
            return false;
        }
        try {
            // 创建MIME邮件对象 　
            mimeMsg = new MimeMessage(session);
            // mp 一个multipart对象 　
            mp = new MimeMultipart();
            // Multipart is a container that holds multiple body parts. 　　
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets need auth.
     *
     * @param need boolean
     */
    public void setNeedAuth(boolean need) {
        if (props == null) {
            props = System.getProperties();
        }
        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
    }

    /**
     * Sets name pass.
     *
     * @param name String
     * @param pass String
     */
    public void setNamePass(String name, String pass) {
        username = name;
        password = pass;
    }

    /**
     * Sets subject.
     *
     * @param mailSubject String
     * @throws Exception the exception
     */
    public void setSubject(String mailSubject)  throws Exception  {
        try {
            mimeMsg.setSubject(mailSubject);
        } catch (MessagingException e) {
            throw e;
        }
    }

    /**
     * Sets body.
     *
     * @param mailBody String
     * @throws Exception the exception
     */
    public void setBody(String mailBody) throws Exception {
        if(mailBody==null){
            throw new Exception(" mailBody is null");
        }
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>" + mailBody, "text/html;charset=GB2312");
            mp.addBodyPart(bp);
        } catch (MessagingException e) {
            throw e;
        }
    }

    /**
     * Add file affix.
     *
     * @param filename String
     * @return the boolean
     */
    public boolean addFileAffix(String filename) {
        try {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(fileds.getName());
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置发送者及昵称
     *
     * @param from the from
     * @param nick the nick
     * @throws Exception the exception
     * @return
     */
    public void setFrom(String from, String nick) throws Exception {
        if (from == null){
            throw new Exception(" from is null");
        }
        if (nick == null){
            throw new Exception(" nick is null");
        }
        try {
            // 设置发信人
            mimeMsg.setFrom(new InternetAddress(from, MimeUtility.encodeText(nick)));
        } catch (MessagingException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
    }

    /**
     * Sets to.
     *
     * @param to String
     * @throws Exception the exception
     */
    public void setTo(String to)  throws Exception {
        if (to == null){
            throw new Exception(" to is null");
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        } catch (MessagingException e) {
            throw e;
        }
    }

    /**
     * Sets copy to.
     *
     * @param copyto String
     * @return the copy to
     */
    public boolean setCopyTo(String copyto) {
        if (copyto == null){
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Send void.
     *
     * @throws Exception the exception
     */
    public void send()  throws Exception {
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), username, password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            // transport.send(mimeMsg); 　　
            transport.close();
        } catch (MessagingException e) {
            throw e;
        }
    }

}
