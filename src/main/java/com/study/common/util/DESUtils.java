package com.study.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 3DES���ܹ�����
 */
public class DESUtils {

	public static final String secretKey="qweryuio1234578ubnut2etr";
	/**
	 * ����
	 * @param inStr ��Ҫ���ܵ�����
	 * @param secretKey ��Կ
	 * @return ���ܺ������
	 */
	public static String encrypt(String inStr, String secretKey) {
		SecretKey deskey = new SecretKeySpec(secretKey.getBytes(), "DESede");
		Cipher cipher;
		String outStr = null;
		try {
			cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, deskey);
			outStr = byte2hex(cipher.doFinal(inStr.getBytes()));
		} catch (Exception e) {
			outStr = "default";
			System.err.println("3DES����ʧ�ܣ���������[" + inStr + "]");
			e.printStackTrace();
		}
		return outStr;
	}

	/**
	 * ����
	 * @param inStr ��Ҫ���ܵ�����
	 * @param secretKey ��Կ
	 * @return ���ܺ������
	 */
	public static String decrypt(String inStr, String secretKey) {
		SecretKey deskey = new SecretKeySpec(secretKey.getBytes(), "DESede");
		Cipher cipher;
		String outStr = null;
		try {
			cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			outStr = new String(cipher.doFinal(hex2byte(inStr)));
		} catch (Exception e) {
			outStr = "default";
			System.err.println("3DES����ʧ�ܣ���������[" + inStr + "]");
		//	e.printStackTrace();
		}
		return outStr;
	}

	/**
	 * ת��Ϊ16�����ַ�������
	 * @param digest ��Ҫת�����ֽ���
	 * @return ת������ַ���
	 */
	public static String byte2hex(byte[] digest) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < digest.length; n++) {
			stmp = Integer.toHexString(digest[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append("0" + stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * ʮ����ת������
	 * @param hexStr ��ת��16�����ַ���
	 * @return �������ֽ���
	 */
	public static byte[] hex2byte(String hexStr){
		if (hexStr == null)
			return null;
		hexStr = hexStr.trim();
		int len = hexStr.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] digest = new byte[len / 2];
		try {
			for (int i = 0; i < hexStr.length(); i += 2) {
				digest[i / 2] = (byte) Integer.decode("0x" + hexStr.substring(i, i + 2)).intValue();
			}
			return digest;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
//		String secretKey = "111111112222222233333333";
		System.out.println(encrypt("15201175465", secretKey));
		System.out.println(encrypt("2", secretKey));
		System.out.println(EncryptUtil.encrypt("15201175465"+"2", EncryptUtil.MD5));
//		System.out.println(decrypt("2E65BE5BE3A34B7F7C33FD7D3B725D068BDF923EA4998BFF08DB75A911826144C6E8474C56BBCD96", secretKey));
		//System.out.println(encrypt("{patternID : '0', username : 'zhangsan', password : '2'}", secretKey));
	}

}
