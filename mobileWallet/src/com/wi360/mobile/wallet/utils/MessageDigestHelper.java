package com.wi360.mobile.wallet.utils;


import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * MessageDigest(SHA-1,MD5加密)
 * 
 * @Project: youoil
 * @File: MessageDigestHelper.java
 * @See:
 * @author: LIJUNJIE
 * @modified:
 * @Email:569528701@qq.com
 * @Date: 2014-5-12 10:30:26
 * @CopyEdition: 中经汇通有限责任公司2014版权所有
 * @version: V1.0
 */
public class MessageDigestHelper {

	private static final String ALGORITHM = "MD5";
	 
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
 
    /**
     * 先使用algorithm加密，后使用Base64编码返回字符串
     * 
     * 加密字符串默认使用编码utf-8国际编码
     *
     * @param algorithm 加密类型 （例如：SHA-1，MD5）
     * @param str 需要加密的字符串
     * @param chasetName 需要加密的字符串的编码（例如：utf-8）
     * 
     * @return String 
     */
    public static String encode(String algorithm, String str,String chasetName) throws RuntimeException {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(chasetName)) {
			chasetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes(chasetName));
            return Base64Helper.encodeToString(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * 使用MessageDigest指定的algorithm类型进行摘要
     * 
     * @param algorithm 加密类型 （例如：SHA-1，MD5）
     * @param str 需要加密的字符串
     * @param chasetName 需要加密的字符串的编码（例如：utf-8）
     * @return MessageDigest.digest()
     * @throws RuntimeException
     */
    public static byte[] digest(String algorithm, String str,String chasetName) throws RuntimeException {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(chasetName)) {
			chasetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes(chasetName));
            return messageDigest.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * MD5加密
     *
     * @param str
     * @return String
     */
    public static String encodeByMD5(String str,String chasetName) throws RuntimeException {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(chasetName)) {
			chasetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(str.getBytes(chasetName));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 
     * @param str
     * @param chasetName
     * @return
     */
    public static String encodeBySHA1(String str,String chasetName){
    	if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(chasetName)) {
			chasetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes(chasetName));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 返回MD5密文的十六进制的字符串
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
        	buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

	/**
	 * base64加密
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Encode(byte[] b) {
		return new String(Base64.encodeBase64(b));
	}

	/**
	 * base64解密
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Decode(byte[] b) {
		return new String(Base64.decodeBase64(b));
	}

}
