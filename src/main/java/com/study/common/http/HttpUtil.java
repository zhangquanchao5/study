package com.study.common.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright lr.
 * User: Daocren
 * Date: 2008-6-24
 * Time: 10:09:10
 * 读取Http上URl数据
 */
public final class HttpUtil {
    private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    /**
     * 读取超时时间 值为值为{@value}
     */
    private static int readtimeout = 3 * 1000;
    /**
     * 连接超时时间 值为值为{@value}
     */
    private static int connectiontimeout = 10 * 1000;

    private static HttpClientParams clientParams = new HttpClientParams();
    private static final String default_string = "utf-8";
    private static final String REQUEST_HEADER_CONNECTION = "Connection";
    private static final String REQUEST_HEADER_PROXYCONNECTION = "Proxy-Connection";
    private static final String REQUEST_HEADER_CONTENTTYPE = "Content-type";
    private static final String REQUEST_HEADER_VALUE_CLOSE = "close";

    /**
     * Instantiates a new Http util.
     */
    private HttpUtil() {
    }

    /**
     * 使用GetMethod方式读取URL数据，读完后自动断开请求
     *
     * @param url String 请求读取的URL
     * @return HttpSendResult http send result
     */
    public static HttpSendResult executeGet(String url) {
        HttpSendResult sendResult = new HttpSendResult();
        HttpConnectionManagerParams params = connectionManager.getParams();
        params.setConnectionTimeout(connectiontimeout);
        HttpClient hc = new HttpClient();
        hc.setHttpConnectionManager(connectionManager);
        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader(REQUEST_HEADER_CONNECTION, REQUEST_HEADER_VALUE_CLOSE);
        getMethod.setRequestHeader(REQUEST_HEADER_PROXYCONNECTION, REQUEST_HEADER_VALUE_CLOSE);
        HttpMethodParams httpMethodParams = getMethod.getParams();
        httpMethodParams.setParameter(HttpMethodParams.SO_TIMEOUT, readtimeout);
        try {
            int responseCode = hc.executeMethod(getMethod);
            InputStream inputStream = getMethod.getResponseBodyAsStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read = -1;
            byte[] buffer = new byte[512];
            while ((read = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
            String response = new String(out.toByteArray());
            sendResult.setStatusCode(responseCode);
            sendResult.setResponse(response);
        } catch (Exception e) {
            sendResult.setException(e);
        }
        getMethod.releaseConnection();
        return sendResult;
    }

    /**
     * 使用PostMethod方式发送数据到指定URL，读完后自动断开请求
     *
     * @param url String 请求读取的URL
     * @param xmlBytes byte[] 请求所需数据
     * @param encode the encode
     * @return HttpSendResult http send result
     */
    public static HttpSendResult executePostXml(String url, byte[] xmlBytes, String encode) {
        HttpSendResult sendResult = new HttpSendResult();
        HttpConnectionManagerParams params = connectionManager.getParams();
        params.setConnectionTimeout(connectiontimeout);
        HttpClient hc = new HttpClient();
        hc.setHttpConnectionManager(connectionManager);
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader(REQUEST_HEADER_CONNECTION, REQUEST_HEADER_VALUE_CLOSE);
        postMethod.setRequestHeader(REQUEST_HEADER_PROXYCONNECTION, REQUEST_HEADER_VALUE_CLOSE);
        postMethod.setRequestEntity(new ByteArrayRequestEntity(xmlBytes));
        postMethod.setRequestHeader(REQUEST_HEADER_CONTENTTYPE, "text/xml; charset=" + encode);
        //HttpMethodParams httpMethodParams = postMethod.getParams();
        // httpMethodParams.setParameter(HttpMethodParams.SO_TIMEOUT, readtimeout);
        try {
            int responseCode = hc.executeMethod(postMethod);
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read = -1;
            while ((read = inputStream.read()) != -1) {
                out.write(read);
            }
            String response = new String(out.toByteArray(), encode);
            sendResult.setStatusCode(responseCode);
            sendResult.setResponse(response);
        } catch (Exception e) {
            sendResult.setException(e);
        } finally {
            postMethod.releaseConnection();
        }
        return sendResult;
    }

    /**
     * Execute post name value.
     *
     * @param url the url
     * @param nameValuePair the name value pair
     * @param encode the encode
     * @return the http send result
     */
    public static HttpSendResult executePostNameValue(String url, NameValuePair[] nameValuePair, String encode) {
        HttpSendResult sendResult = new HttpSendResult();

        HttpConnectionManagerParams params = connectionManager.getParams();
        params.setConnectionTimeout(connectiontimeout);
        HttpClient httpClient = new HttpClient();
        httpClient.setHttpConnectionManager(connectionManager);
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader(REQUEST_HEADER_CONNECTION, REQUEST_HEADER_VALUE_CLOSE);
        postMethod.setRequestHeader(REQUEST_HEADER_PROXYCONNECTION, REQUEST_HEADER_VALUE_CLOSE);
//        postMethod.setRequestHeader("Content-type", "text/xml; charset=" + encode);
        postMethod.setRequestBody(nameValuePair);
        try {
            int responseCode = httpClient.executeMethod(postMethod);
            String response = postMethod.getResponseBodyAsString();
            sendResult.setStatusCode(responseCode);
            sendResult.setResponse(response);
        } catch (Exception e) {
            sendResult.setException(e);
        } finally {
            postMethod.releaseConnection();
        }
        return sendResult;

    }


    /**
     * Execute post.
     *
     * @param url the url
     * @param str the str
     * @param contentType the content type
     * @return the http send result
     */
    public static HttpSendResult executePost(String url, String str, String contentType) {
        return executePost(url, str, default_string, contentType);
    }

    /**
     * Execute post.
     *
     * @param url the url
     * @param str the str
     * @param charset the charset
     * @param contentType the content type
     * @return the http send result
     */
    @SuppressWarnings("deprecation")
    public static HttpSendResult executePost(String url, String str, String charset, String contentType) {
        HttpSendResult sendResult = new HttpSendResult();
        HttpClient hc = new HttpClient(clientParams);
        hc.setConnectionTimeout(connectiontimeout);
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader(REQUEST_HEADER_CONNECTION, REQUEST_HEADER_VALUE_CLOSE);
        postMethod.setRequestHeader(REQUEST_HEADER_PROXYCONNECTION, REQUEST_HEADER_VALUE_CLOSE);
        try {
            postMethod.setRequestEntity(new ByteArrayRequestEntity(str.getBytes(charset)));

        } catch (UnsupportedEncodingException e1) {
            //LifeccpLogger.recSysLog(Level.ERROR, e1.getMessage(), e1);
        }
        postMethod.setRequestHeader(REQUEST_HEADER_CONTENTTYPE, contentType + "; charset=" + charset);
        try {
            int responseCode = hc.executeMethod(postMethod);
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read = -1;
            while ((read = inputStream.read()) != -1) {
                out.write(read);
            }
            String response = new String(out.toByteArray(), charset);
            sendResult.setStatusCode(responseCode);
            sendResult.setResponse(response);
        } catch (Exception e) {
            sendResult.setException(e);
        }
        postMethod.releaseConnection();
        return sendResult;
    }

    /**
     * 文件上传
     * @param URL_STR the uRL _ sTR
     * @param file the file
     * @return the http send result
     */

    public static HttpSendResult uploadFileBody(String URL_STR, File file) {

        HttpSendResult sendResult = new HttpSendResult();
        PostMethod filePost = new PostMethod(URL_STR);
        HttpClient client = new HttpClient();
        try {
            // 通过以下方法可以模拟页面参数提交
//                        filePost.setParameter("userName", userName);
//                        filePost.setParameter("passwd", passwd);

            Part[] parts = {new FilePart("file", file)};
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);
            InputStream inputStream = filePost.getResponseBodyAsStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read = -1;
            while ((read = inputStream.read()) != -1) {
                out.write(read);
            }
            String response = new String(out.toByteArray(), "utf-8");
            sendResult.setStatusCode(status);
            sendResult.setResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            filePost.releaseConnection();
        }
        return sendResult;
    }


    public static HttpSendResult formPostUrl(String url,  Map<String, String> params) {
        HttpSendResult sendResult = new HttpSendResult();
        try{
            NameValuePair[] formparams = new NameValuePair[params.size()];


            HttpClient hc = new HttpClient(clientParams);
            hc.setConnectionTimeout(connectiontimeout);
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestHeader(REQUEST_HEADER_CONNECTION, REQUEST_HEADER_VALUE_CLOSE);
            postMethod.setRequestHeader(REQUEST_HEADER_PROXYCONNECTION, REQUEST_HEADER_VALUE_CLOSE);
           // postMethod.setRequestHeader("Authorization",auth);
            postMethod.setRequestHeader("platform", "web");

            // 构建请求参数

            if (params != null) {
                int i=0;
                for (Map.Entry<String, String> e : params.entrySet()) {
                    formparams[i]=new NameValuePair(e.getKey(),e.getValue());
                    i++;
                }
            }
            postMethod.setRequestBody(formparams);
            postMethod.setRequestHeader(REQUEST_HEADER_CONTENTTYPE, "application/x-www-form-urlencoded" + "; charset=utf-8");

            int responseCode = hc.executeMethod(postMethod);
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read = -1;
            while ((read = inputStream.read()) != -1) {
                out.write(read);
            }
            String response = new String(out.toByteArray(), "utf-8");
            sendResult.setStatusCode(responseCode);
            sendResult.setResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  sendResult;
    }


//    //多个上传
//    public static HttpSendResult postFile(String url, File file) throws IOException {
//        HttpPost httpPost = new HttpPost(url);
//
//        HttpParams myParams = new BasicHttpParams();
//
//        HttpConnectionParams.setConnectionTimeout(myParams, 30000);
//        HttpConnectionParams.setSoTimeout(myParams, 5000);
//
//        DefaultHttpClient httpClient = new DefaultHttpClient(myParams);
////        String BOUNDARY = java.util.UUID.randomUUID().toString();
////        String MULTIPART_FORM_DATA = "multipart/form-data";
////        httpPost.setHeader("Content-Type", MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY);
//        FileEntity entity = new FileEntity(file, "binary/octet-stream");
//        httpPost.setEntity(entity);
//        httpPost.addHeader("fileName", file.getName());
////        httpPost.addHeader("hasStream", "1");
//        BasicHttpContext localContext = new BasicHttpContext();
//        HttpResponse response = httpClient.execute(httpPost, localContext);
//        InputStream inputStream = response.getEntity().getContent();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int read = -1;
//        while ((read = inputStream.read()) != -1) {
//            out.write(read);
//        }
//        HttpSendResult httpSendResult = new HttpSendResult();
//        String response2 = new String(out.toByteArray(), "utf-8");
//        httpSendResult.setStatusCode(response.getStatusLine().getStatusCode());
//        httpSendResult.setResponse(response2);
//
//        return httpSendResult;
//    }

//    public static void main(String[] args) {
//        try {
//            HttpSendResult httpSendResult = uploadFileBody("http://a.lifeccp.com:8888/pub/up", new File("E:\\ps\\DSC_0003.JPG"));
//            System.out.println(httpSendResult.getResponse());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}