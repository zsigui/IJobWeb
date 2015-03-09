package com.ijob.server.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.ijob.server.constants.GlobalConfig;
import com.ijob.server.utils.TextUtils;
import com.ijob.server.utils.log.LM;

/**
 * 
 * @description 3DES加密解密算法
 * 
 * @author Jackie Zhuang
 * @date 2014年12月12日
 */
public class DESede {

	// 有DES、DESede(3DES)等
	private static final String DEFAULT_ALGORITHM = "DESede";
	// 算法/模式(CBC、EBC等)/填充(PKCS5Padding、NoPadding)
	private static final String DEFAULT_TRANSFORMATION = "DESede/CBC/PKCS5Padding";
	// 密钥字节长度，3DES为24字节，每次8字节
	private static final int DEFAULT_KEY_SIZE = 24;
	// 默认向量长度，iv为8字节
	private static final int DEFAULT_IV_SIZE = 8;
	// 用于默认密钥
	private static final String DEFAULT_KEY = "123456aderqkqeincz5ae5";
	// 用于生成向量
	public static final String DEFAULT_IV = "keoz+2f}";

	/**
	 * 进行3DES加密并
	 * 
	 * @param msg
	 *            待加密字符数组
	 * @param key
	 *            加密密钥字节数组
	 * @param bIv
	 *            8位向量字节数组
	 * @return
	 */
	public static byte[] encrypt(byte[] msg, byte[] key, byte[] bIv) {
		try {
			if (msg == null)
				return null;
			if (key == null || key.length == 0)
				key = genKeyByte(DEFAULT_KEY.getBytes(GlobalConfig.DEFAULT_CHARSET),
						DEFAULT_KEY_SIZE);
			if (bIv == null || bIv.length == 0)
				bIv = genKeyByte(DEFAULT_IV.getBytes(GlobalConfig.DEFAULT_CHARSET),
						DEFAULT_IV_SIZE);
			if (bIv.length < DEFAULT_IV_SIZE)
				bIv = genKeyByte(bIv, DEFAULT_IV_SIZE);

			SecretKey desKey = genKey(MD5.genDigest(key));
			IvParameterSpec iv = new IvParameterSpec(bIv);
			Cipher cipher = Cipher.getInstance(DEFAULT_TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, desKey, iv);
			return cipher.doFinal(msg);
		} catch (NoSuchAlgorithmException e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		} catch (NoSuchPaddingException e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		} catch (InvalidKeyException e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		} catch (Exception e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		}
		return null;
	}

	/**
	 * 进行3DES加密并对结果进行Base64编码
	 * 
	 * @param msg
	 *            待加密字符串
	 * @param key
	 *            加密密钥
	 * @param iv
	 *            8位向量字节数组
	 * @param charset
	 *            用于获取字节数组的编码
	 * @return
	 */
	public static String encryptInBase64(String msg, String key, String iv,
			String charset) {
		try {
			return Base64.encoder(encrypt(msg.getBytes(charset),
					genKeyByte(key.getBytes(charset), DEFAULT_KEY_SIZE),
					genKeyByte(iv.getBytes(charset), DEFAULT_IV_SIZE)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		} catch (Exception e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		}
		return null;
	}

	/**
	 * 进行3DES加密并对结果进行Base64编码
	 * 
	 * @param msg
	 *            待加密字符串
	 * @param key
	 *            加密密钥
	 * @param charset
	 *            用于获取字节数组的编码
	 * @return
	 */
	public static String encryptInBase64(String msg, String key, String charset) {
		return encryptInBase64(msg, key, DEFAULT_IV, charset);
	}

	/**
	 * 进行3DES加密并对结果进行Base64编码
	 * 
	 * @param msg
	 *            待加密字符串
	 * @param key
	 *            加密密钥
	 * @return
	 */
	public static String encryptInBase64(String msg, String key) {
		return encryptInBase64(msg, key, DEFAULT_IV, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 进行3DES加密并对结果进行十六进制转换
	 * 
	 * @param msg
	 *            待加密字符串
	 * @param key
	 *            加密密钥
	 * @param iv
	 *            8位向量字节数组
	 * @param charset
	 *            用于获取字节数组的编码
	 * @return
	 */
	public static String encryptInHex(String msg, String key, String iv,
			String charset) {
		try {
			return TextUtils.bytes2Hex(encrypt(msg.getBytes(charset),
					genKeyByte(key.getBytes(charset), DEFAULT_KEY_SIZE),
					genKeyByte(iv.getBytes(charset), DEFAULT_IV_SIZE)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		} catch (Exception e) {
			LM.ins().te(DESede.class, "encrypt", e.getMessage());
		}
		return null;
	}

	/**
	 * 进行3DES加密并对结果进行十六进制转换
	 * 
	 * @param msg
	 *            待加密字符串
	 * @param key
	 *            加密密钥
	 * @param charset
	 *            用于获取字节数组的编码
	 * @return
	 */
	public static String encryptInHex(String msg, String key, String charset) {
		return encryptInHex(msg, key, DEFAULT_IV, charset);
	}

	/**
	 * 进行3DES加密并对结果进行十六进制转换
	 * 
	 * @param msg
	 *            待加密字符串
	 * @param key
	 *            加密密钥
	 * @return
	 */
	public static String encryptInHex(String msg, String key) {
		return encryptInHex(msg, key, DEFAULT_IV, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 进行3DES解密
	 * 
	 * @param msg
	 *            待解密字节数组
	 * @param key
	 *            解密密钥字节数组
	 * @param bIv
	 *            8位向量字节数组
	 * @return
	 */
	public static String decrypt(byte[] msg, byte[] key, byte[] bIv) {
		try {
			if (msg == null)
				return null;
			if (key == null || key.length == 0)
				key = genKeyByte(DEFAULT_KEY.getBytes(GlobalConfig.DEFAULT_CHARSET),
						DEFAULT_KEY_SIZE);
			if (bIv == null || bIv.length == 0)
				bIv = genKeyByte(DEFAULT_IV.getBytes(GlobalConfig.DEFAULT_CHARSET),
						DEFAULT_IV_SIZE);
			if (bIv.length < DEFAULT_IV_SIZE)
				bIv = genKeyByte(bIv, DEFAULT_IV_SIZE);

			SecretKey desKey = genKey(MD5.genDigest(key));
			IvParameterSpec iv = new IvParameterSpec(bIv);
			Cipher cipher = Cipher.getInstance(DEFAULT_TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, desKey, iv);
			return new String(cipher.doFinal(msg), GlobalConfig.DEFAULT_CHARSET);
		} catch (InvalidKeyException e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		} catch (NoSuchPaddingException e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		} catch (Exception e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		}

		return null;
	}

	/**
	 * 对解密内容先进行Base64解码再进行3DES解密
	 * 
	 * @param msg
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @param charset
	 *            用于获取字节数组的编码
	 * @param iv
	 * @return
	 */
	public static String decryptInBase64(String msg, String key, String iv,
			String charset) {
		try {
			return decrypt(Base64.decoder2Byte(msg),
					genKeyByte(key.getBytes(charset), DEFAULT_KEY_SIZE),
					genKeyByte(iv.getBytes(charset), DEFAULT_IV_SIZE));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		} catch (Exception e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		}
		return null;
	}

	/**
	 * 对解密内容先进行Base64解码再进行3DES解密
	 * 
	 * @param msg
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @param charset
	 *            用于获取字节数组的编码
	 * @return
	 */
	public static String decryptInBase64(String msg, String key, String charset) {
		return decryptInBase64(msg, key, DEFAULT_IV, charset);
	}

	/**
	 * 对解密内容先进行Base64解码再进行3DES解密
	 * 
	 * @param msg
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @return
	 */
	public static String decryptInBase64(String msg, String key) {
		return decryptInBase64(msg, key, DEFAULT_IV, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 对解密内容先进行十六进制转换再进行3DES解密
	 * 
	 * @param msg
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @param charset
	 *            用于获取字节数组的编码
	 * @param iv
	 * @return
	 */
	public static String decryptInHex(String msg, String key, String iv,
			String charset) {
		try {
			return decrypt(TextUtils.hex2Bytes(msg),
					genKeyByte(key.getBytes(charset), DEFAULT_KEY_SIZE),
					genKeyByte(iv.getBytes(charset), DEFAULT_IV_SIZE));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		} catch (Exception e) {
			LM.ins().te(DESede.class, "decrypt", e.getMessage());
		}
		return null;
	}

	/**
	 * 对解密内容先进行十六进制转换再进行3DES解密
	 * 
	 * @param msg
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @param charset
	 *            用于获取字节数组的编码
	 * @return
	 */
	public static String decryptInHex(String msg, String key, String charset) {
		return decryptInHex(msg, key, DEFAULT_IV, charset);
	}

	/**
	 * 对解密内容先进行十六进制转换再进行3DES解密
	 * 
	 * @param msg
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @return
	 */
	public static String decryptInHex(String msg, String key) {
		return decryptInHex(msg, key, DEFAULT_IV, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 根据所给字节数组生成限长字节密钥
	 * 
	 * @param key
	 * @param limitSize
	 * @return
	 */
	private static byte[] genKeyByte(byte[] key, int limitSize) {
		byte[] result = new byte[limitSize];
		for (int i = 0, j = 0; i < result.length; i++) {
			result[i] = key[j++];
			// 循环使用
			if (j == key.length)
				j = 0;
		}
		return result;
	}

	/**
	 * 生成3DES密钥
	 * 
	 * @param bKey
	 * @return
	 */
	private static SecretKey genKey(byte[] bKey) {
		try {
			DESedeKeySpec keySpec = new DESedeKeySpec(genKeyByte(bKey,
					DEFAULT_KEY_SIZE));
			SecretKey sKey = SecretKeyFactory.getInstance(DEFAULT_ALGORITHM)
					.generateSecret(keySpec);
			return sKey;
		} catch (NoSuchAlgorithmException e) {
			LM.ins().te(DESede.class, "genKey", e.getMessage());
		} catch (InvalidKeyException e) {
			LM.ins().te(DESede.class, "genKey", e.getMessage());
		} catch (InvalidKeySpecException e) {
			LM.ins().te(DESede.class, "genKey", e.getMessage());
		}
		return null;
	}

	/*public static void main(String[] args) throws UnsupportedEncodingException {
		String key = "kfidlwad12qr:2871dfadfaerqzdqe}+";
		String msg = "English Test To Show";
		byte[] d = TDESEncrpyt.encrypt(msg.getBytes(Ct.DEFAULT_CHARSET),
				key.getBytes(Ct.DEFAULT_CHARSET),
				DEFAULT_IV.getBytes(Ct.DEFAULT_CHARSET));
		String d2 = TDESEncrpyt.encryptInHex(msg, key);
		LM.ins().td(TDESEncrpyt.class, "main", msg);
		LM.ins().td(TDESEncrpyt.class, "main", "密文：" + d2);
		String e = TDESEncrpyt.decryptInHex(d2, key);
		LM.ins().td(TDESEncrpyt.class, "main",
				"明文：" + e + ", isEqual = " + (msg.equals(e)));

	}*/
}
