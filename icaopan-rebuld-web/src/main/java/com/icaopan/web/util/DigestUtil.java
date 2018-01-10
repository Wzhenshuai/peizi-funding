package com.icaopan.web.util;

import java.security.MessageDigest;

public class DigestUtil {

    public static String MD5(String str) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            byte[] b = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(MD5("huangjf@123"));
    }
}
