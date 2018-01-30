package com.xy.wmall.common.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * MD5加密（只能用于加密对比，不能解密）
 * 
 * @author xiongyan
 * @date 2017年11月29日 下午5:34:22
 */
public class Md5Utils {
	
	private Md5Utils() {
		
	}

	/**
     * Used building output as Hex
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     * @return 密文
     */
	public static String md5(String text) {
		MessageDigest msgDigest;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
			// 注意改接口是按照utf-8编码形式加密
			msgDigest.update(text.getBytes(Charset.forName("UTF-8")));
			byte[] bytes = msgDigest.digest();
			return new String(encodeHex(bytes));
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
	}

	/**
     * 十六进制编码
     * 
     * @param data
     * @return
     */
    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }
    
}
