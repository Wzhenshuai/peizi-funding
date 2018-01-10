package com.icaopan.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {
	private static final String METHOD_POST = "POST";
    private static final String DEFAULT_CHARSET = "utf-8";
	public static String getServerAddress(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		return basePath;
	}
	
	public static String readContentFromGet(String url) throws IOException {
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
        URL getUrl = new URL(url);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器
        connection.setRequestProperty("Charset", DEFAULT_CHARSET);
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines="";
        String line="";
        while ((line = reader.readLine()) != null) {
            lines+=line;
        }
        reader.close();
        // 断开连接
        connection.disconnect();
        return lines;
    }
	
	public static String readContentFromJQKGet(String url) throws IOException {
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
        URL getUrl = new URL(url);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器
        connection.setRequestProperty("Charset", DEFAULT_CHARSET);
        connection.setRequestProperty("Referer","http://x.10jqka.com.cn/stockpick/search?typed=0&preParams=&ts=1&f=1&qs=result_original&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w=%E9%99%A4%E6%9D%83%E9%99%A4%E6%81%AF%E6%97%A5%3D%E6%98%8E%E6%97%A5&queryarea=");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        connection.setRequestProperty("Cookie","sp_search_last_right_status=hide; spversion=20130314; PHPSESSID=0avn70jg8lpvi0pd47g903r5r7; user=MDpYaG0xMDI3OjpOb25lOjUwMDoxMDE3NDkzNzg6NywxMTExMTExMTExMSw0MDs0NCwxMSw0MDs2LDEsNDA7NSwxLDQwOjo6OjkxNzQ5Mzc4OjE1MDg4MTM5Mjk6OjoxMjg3NjI4NDQwOjYwNDgwMDowOjEzZWY5YjUwM2JhYzU5MzU0NmVmY2I0ZjA3ZTYyMDRjZDpkZWZhdWx0XzI6MA%3D%3D; userid=91749378; u_name=Xhm1027; escapename=Xhm1027; ticket=a4d7436091864d5f5b874621acf2558a; historystock=600502%7C*%7C601519; Hm_lvt_78c58f01938e4d85eaf619eae71b4ed1=1506585827,1508812767,1508822438,1508822479; Hm_lpvt_78c58f01938e4d85eaf619eae71b4ed1=1508822568; cid=0avn70jg8lpvi0pd47g903r5r71508813293; ComputerID=0avn70jg8lpvi0pd47g903r5r71508813293; v=Af9Xv2y2mtj6tZ7EZ4fOCMFzjtiN5FWSbStXepHMmXP17BGOmbTj1n0I58Wh");
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines="";
        String line="";
        while ((line = reader.readLine()) != null) {
            lines+=line;
        }
        reader.close();
        // 断开连接
        connection.disconnect();
        return lines;
    }

    public static String readContentFromPost(String url, String params) throws IOException {
        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL(url);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // Set the post method. Default is GET
        connection.setRequestMethod(METHOD_POST);
        connection.setRequestProperty("contentType", DEFAULT_CHARSET);
        // Post cannot use caches
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        // This method takes effects to
        // every instances of this class.
        // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
        // connection.setFollowRedirects(true);

        // This methods only
        // takes effacts to this
        // instance.
        // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
        connection.setInstanceFollowRedirects(true);
        // Set the content type to urlencoded,
        // because we will write
        // some URL-encoded content to the
        // connection. Settings above must be set before connect!
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
        String content = params;
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
        out.writeBytes(content); 

        out.flush();
        out.close(); // flush and close
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(),"UTF-8"));
        String lines="";
        String line="";
        while ((line = reader.readLine()) != null) {
            lines+=line;
        }
        reader.close();
        connection.disconnect();
        return lines;
    }
}
