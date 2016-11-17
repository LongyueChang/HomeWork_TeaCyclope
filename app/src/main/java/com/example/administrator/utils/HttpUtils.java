package com.example.administrator.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/13.
 */
public class HttpUtils {
    public static byte[] getBytes(String param) {
        InputStream is=null;
        ByteArrayOutputStream baos=null;
        try {
            URL url=new URL(param);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            if(con.getResponseCode()==200){
                is=con.getInputStream();
                baos=new ByteArrayOutputStream();
                int len=0;
                byte[] buf=new byte[1024*8];
                while ((len=is.read(buf))!=-1){
                    baos.write(buf,0,len);
                }
                byte[] bytes = baos.toByteArray();
                return bytes;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
