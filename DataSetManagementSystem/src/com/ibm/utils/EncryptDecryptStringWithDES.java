package com.ibm.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class EncryptDecryptStringWithDES {

	private Cipher ecipher;
	private Cipher dcipher;
	private SecretKey key;

	public static void main(String[] args) {

		try {
			new EncryptDecryptStringWithDES().encryptData("MYname_3880");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String encryptData(String data) throws Exception {
		key = KeyGenerator.getInstance("DES").generateKey();
		ecipher = Cipher.getInstance("DES");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		String encrypted = encrypt(data);
		System.out.println(encrypted);
		return encrypted;
	}

	public String decryptData(String enData) throws Exception {
		key = KeyGenerator.getInstance("DES").generateKey();
		dcipher = Cipher.getInstance("DES");
		dcipher.init(Cipher.DECRYPT_MODE, key);
		String decrypted = decrypt(enData);
		return decrypted;
	}

	private String encrypt(String str) {
		try {
			// encode the string into a sequence of bytes using the named
			// charset
			// storing the result into a new byte array.
			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			// encode to base64
			enc = BASE64EncoderStream.encode(enc);
			return new String(enc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String decrypt(String str) {
		try {
			// decode with base64 to get bytes
			byte[] dec = BASE64DecoderStream.decode(str.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);
			// create new string based on the specified charset
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}