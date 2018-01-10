package com.icaopan.util;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebUtil {
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
        if ((line = reader.readLine()) != null) {
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
        if ((line = reader.readLine()) != null) {
            lines+=line;
        }
        reader.close();
        connection.disconnect();
        return lines;
    }
    public static String readContentFromPostUseHttps(String url, String params) throws IOException {
    	HttpsConnection hc=new HttpsConnection();
    	String rsp="";
		try {
			rsp = hc.doPost(url, params, "utf-8", 30000, 30000, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rsp;  
    }
    public static String makeAllUrl(HttpServletRequest request){
    	HttpServletRequest httpRequest=(HttpServletRequest)request;
        String strBackUrl = request.getScheme()+"://" + request.getServerName() //服务器地址
                            + ":"   
                            + request.getServerPort()           //端口号  
                            + httpRequest.getContextPath()      //项目名称  
                            + httpRequest.getServletPath()      //请求页面或其他地址  
                        + "?" + (httpRequest.getQueryString()); //参数  
        return strBackUrl;
    }
    public static String makeUrl(HttpServletRequest request){
    	HttpServletRequest httpRequest=(HttpServletRequest)request;
        String strBackUrl = request.getScheme()+"://" + request.getServerName() //服务器地址
                            + ":"   
                            + request.getServerPort()           //端口号  
                            + httpRequest.getContextPath();      //项目名称  
        return strBackUrl;
    }
    
    public static String parseUrlParamMap(Map<String,String> parmMap){
    	String urlParmasStr="";
    	for ( String parmName : parmMap.keySet()) {
			String parmValue=parmMap.get(parmName);
			urlParmasStr+=parmName+"="+parmValue+"&";
		}
    	return urlParmasStr;
    }
     /*
      * 对应javascript的escape()函数, 加码后的串可直接使用javascript的unescape()进行解码
      */
     public static String escape(String src) {
      int i;
      char j;
      StringBuffer tmp = new StringBuffer();
      tmp.ensureCapacity(src.length() * 6);
      for (i = 0; i < src.length(); i++) {
       j = src.charAt(i);
       if (Character.isDigit(j) || Character.isLowerCase(j)
         || Character.isUpperCase(j))
        tmp.append(j);
       else if (j < 256) {
        tmp.append("%");
        if (j < 16)
         tmp.append("0");
        tmp.append(Integer.toString(j, 16));
       } else {
        tmp.append("%u");
        tmp.append(Integer.toString(j, 16));
       }
      }
      return tmp.toString();
     }
    public static String unescape(String src) {
    	  StringBuffer tmp = new StringBuffer();
    	  tmp.ensureCapacity(src.length());
    	  int lastPos = 0, pos = 0;
    	  char ch;
    	  while (lastPos < src.length()) {
    	   pos = src.indexOf("%", lastPos);
    	   if (pos == lastPos) {
    	    if (src.charAt(pos + 1) == 'u') {
    	     ch = (char) Integer.parseInt(src
    	       .substring(pos + 2, pos + 6), 16);
    	     tmp.append(ch);
    	     lastPos = pos + 6;
    	    } else {
    	     ch = (char) Integer.parseInt(src
    	       .substring(pos + 1, pos + 3), 16);
    	     tmp.append(ch);
    	     lastPos = pos + 3;
    	    }
    	   } else {
    	    if (pos == -1) {
    	     tmp.append(src.substring(lastPos));
    	     lastPos = src.length();
    	    } else {
    	     tmp.append(src.substring(lastPos, pos));
    	     lastPos = pos;
    	    }
    	   }
    	  }
    	  return tmp.toString();
    	 }
    private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}  
    	  
    }  
    private static HttpsURLConnection getConnection(URL url, String method, String ctype)
            throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(method);  
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");  
        conn.setRequestProperty("User-Agent", "stargate");  
        conn.setRequestProperty("Content-Type", ctype);  
        conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
        return conn;  
    }  
}
