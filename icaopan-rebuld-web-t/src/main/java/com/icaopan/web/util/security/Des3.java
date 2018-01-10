package com.icaopan.web.util.security;

import com.icaopan.web.util.PropertyReader;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Des3 {

    // 向量
    private final static String iv       = "01234567";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        String retStr = "";
        try {
            DESedeKeySpec spec = new DESedeKeySpec(PropertyReader.getSecretKey().getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            retStr = Base64.encode(encryptData);
        } catch (Exception e) {
            throw new Exception("加密异常", e);
        }
        return retStr;
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws Exception
     */

    public static String decode(String encryptText) throws Exception {
        String reStr = "";
        Key deskey = null;
        byte[] decryptData = null;
        DESedeKeySpec spec = new DESedeKeySpec(PropertyReader.getSecretKey().getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        decryptData = cipher.doFinal(Base64.decode(encryptText));
        reStr = new String(decryptData, encoding);

        return reStr;
    }

    /**
     * 用传入的密钥解密而不是默认密钥
     *
     * @param encryptText
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText, String secretKey) throws Exception {
        String reStr = "";
        Key deskey = null;
        byte[] decryptData = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        decryptData = cipher.doFinal(Base64.decode(encryptText));
        reStr = new String(decryptData, encoding);

        return reStr;
    }

    public static void main(String[] args) {
        String str = "{'userName':'13366995646','passWord':'lfx5510110'}";
        try {
            System.out.println(encode(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
