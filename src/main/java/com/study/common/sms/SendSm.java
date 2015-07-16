package com.study.common.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

/**
 * Created by huichao on 2015/7/16.
 */
public class SendSm {
    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
    public static final String SUCCE_CODE="2";

    public synchronized static SmsResponse sendSms(String mobile,String content){
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);
        client.getParams().setContentCharset("gbk");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
        SmsResponse smsResponse=new SmsResponse();

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "cf_songzhe"),
                new NameValuePair("password", "soooner123"), //密码可以使用明文密码或使用32位MD5加密
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", content),
        };

        method.setRequestBody(data);


        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();
            smsResponse.setCode(root.elementText("code"));
            smsResponse.setMsg(root.elementText("msg"));
            smsResponse.setSmsid(root.elementText("smsid"));
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return smsResponse;
    }

    public  static void main(String [] args){
        SmsResponse smsResponse=sendSms("15201175465","您的校验码是：【123456】。请不要把校验码泄露给其他人。如非本人操作，可不用理会！");
        System.out.println(smsResponse.getCode()+":"+smsResponse.getMsg()+":"+smsResponse.getSmsid());
    }


}
