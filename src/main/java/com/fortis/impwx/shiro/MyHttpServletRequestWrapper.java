package com.fortis.impwx.shiro;

import org.apache.log4j.Logger;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 *
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger logger = Logger.getLogger(MyHttpServletRequestWrapper.class);

    private String requestBody = null;
    //保存流中的数据
//    private byte[] data;

    public MyHttpServletRequestWrapper(HttpServletRequest request) throws Exception{

        super(request);
        try {
//            data = IOUtils.toByteArray(request.getInputStream());
            requestBody = readBody(request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("MyHttpServletRequestWrapper输入流错误:", e.getCause());
        }
    }

    private static String readBody(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String inputLine;
        BufferedReader br = null;
        try {
            br = request.getReader();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read body.", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    @Override
    public ServletInputStream getInputStream(){
//        return new MyServletInputStream(new ByteArrayInputStream(data));
        return new CustomServletInputStream(requestBody);
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        return new CustomServletInputStream(requestBody);
//    }


    class MyServletInputStream extends ServletInputStream{

        private InputStream inputStream;

        public MyServletInputStream(InputStream inputStream){
            this.inputStream = inputStream;
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        public boolean isFinished() {
            return false;
        }

        public boolean isReady() {
            return false;
        }

        public void setReadListener(ReadListener readListener) {

        }
    }

    private class CustomServletInputStream extends ServletInputStream {

        private ByteArrayInputStream buffer;

        public CustomServletInputStream(String body) {
            body = body == null ? "" : body;
            this.buffer = new ByteArrayInputStream(body.getBytes());
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }

    public String getRequestBody() {
        return requestBody;
    }
}
