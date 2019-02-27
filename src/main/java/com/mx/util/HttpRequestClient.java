package com.mx.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类解释
 */
public class HttpRequestClient {

    /**
     * post方式处理文件和图片上传
     * @param url  服务器地址
     * @return
     * @throws Exception
     */
    public static Result postMultipartData(String url, Map<String,Object> paramMap) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        List<Part> partList = new ArrayList<Part>();
        for(String key : paramMap.keySet()){
            Object value = paramMap.get(key);
            if(value instanceof String){
                partList.add(new StringPart(key,(String)value,"utf-8"));
            }else if(value instanceof File){
                partList.add(new FilePart(key,(File)value));
            }
        }
        Part[] parts = partList.toArray(new Part[0]);
        method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
        client.executeMethod(method);
        byte[] bytes = method.getResponseBody();
        String result = new String(bytes,"utf-8");
        int status = method.getStatusCode();
        return new Result(status,result);
    }

    public static class Result {
        private HttpStatus status;
        private String responseBody;

        public Result(int status,String responseBody){
            this.status = HttpStatus.valueOf(status);
            this.responseBody = responseBody;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }

    }

}
