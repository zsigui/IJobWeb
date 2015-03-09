package com.ijob.server.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import com.ijob.server.constants.GlobalConfig;
import com.ijob.server.utils.TextUtils;
import com.ijob.server.utils.log.LM;

/**
 * 
 * @description DH非对称密钥操作类
 * 
 * @author Jackie Zhuang
 * @date 2014年12月14日
 */
public class DH {

	/**
	 * 默认加密算法
	 */
	private static final String DEFAULT_ALGORITHM = "DH";
	/**
	 * 默认生成键值对的位数
	 */
	private static final int DEFAULT_INIT_SIZE = 1024;
	/**
	 * 默认DH下对称加密算法的加密密钥，可选其它加密方式
	 */
	private static final String DEFAULT_SECRET_ALGORITHM = "DES";

	/**
	 * 加解密实际操作方法
	 * 
	 * @param data
	 * @param pubKey
	 * @param prvKey
	 * @param mode
	 * @return
	 */
	private static byte[] doMethod(byte[] data, byte[] pubKey, byte[] prvKey,
			int mode) {
		try {
			SecretKey sKey = getSecretKey(pubKey, prvKey);
			Cipher cipher = Cipher.getInstance(sKey.getAlgorithm());
			cipher.init(mode, sKey);
			return cipher.doFinal(data);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			LM.ins().te(DH.class, "doMethod", e.getMessage());
		} catch (InvalidKeyException e) {
			LM.ins().te(DH.class, "doMethod", e.getMessage());
		} catch (Exception e) {
			LM.ins().te(DH.class, "doMethod", e.getMessage());
		}
		return null;
	}

	/**
	 * 根据传入公私玥构建SecretKey对象
	 * 
	 * @param pubKey
	 * @param prvKey
	 * @return
	 */
	private static SecretKey getSecretKey(byte[] pubKey, byte[] prvKey) {

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(DEFAULT_ALGORITHM);
			// 初始化公玥
			X509EncodedKeySpec pKeySpec = new X509EncodedKeySpec(pubKey);
			PublicKey pubK = keyFactory.generatePublic(pKeySpec);
			// 初始化私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(prvKey);
			PrivateKey prvK = keyFactory.generatePrivate(pkcs8KeySpec);

			KeyAgreement kAgree = KeyAgreement.getInstance(keyFactory
					.getAlgorithm());
			kAgree.init(prvK);
			kAgree.doPhase(pubK, true);
			return kAgree.generateSecret(DEFAULT_SECRET_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			LM.ins().te(DH.class, "getSecretKey", e.getMessage());
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			LM.ins().te(DH.class, "getSecretKey", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LM.ins().te(DH.class, "getSecretKey", e.getMessage());
		}
		return null;
	}

	/**
	 * 生成指定大小的公私玥键值对(甲方)
	 * 
	 * @param initSize
	 *            指定大小
	 * @return 字节二维数组，0下标为私钥字节数组，1下标为公玥字节数组
	 */
	public static byte[][] genAKeys(int initSize) {
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator
					.getInstance(DEFAULT_ALGORITHM);
			keyGenerator.initialize(initSize);
			KeyPair keyPair = keyGenerator.generateKeyPair();
			return new byte[][] {
					((DHPrivateKey) keyPair.getPrivate()).getEncoded(),
					((DHPublicKey) keyPair.getPublic()).getEncoded() };
		} catch (NoSuchAlgorithmException e) {
			LM.ins().te(DH.class, "genAKeys", e.getMessage());
		}
		return null;
	}

	/**
	 * 生成用Base64编码的指定大小的公私玥键值对(甲方)
	 * 
	 * @param initSize
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genAKeysInBase64(int initSize) {
		byte[][] key = genAKeys(initSize);
		return new String[] { Base64.encoder(key[0]), Base64.encoder(key[1]) };
	}

	/**
	 * 生成用Base64编码的默认大小的公私玥键值对(甲方)
	 * 
	 * @param initSize
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genAKeysInBase64() {
		return genAKeysInBase64(DEFAULT_INIT_SIZE);
	}

	/**
	 * 生成进行了十六进制转换的指定大小的公私玥键值对(甲方)
	 * 
	 * @param initSize
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genAKeysInHex(int initSize) {
		byte[][] key = genAKeys(initSize);
		return new String[] { TextUtils.bytes2Hex(key[0]),
				TextUtils.bytes2Hex(key[1]) };
	}

	/**
	 * 生成进行了十六进制转换的默认大小的公私玥键值对(甲方)
	 * 
	 * @param initSize
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genAKeysInHex() {
		return genAKeysInHex(DEFAULT_INIT_SIZE);
	}

	/**
	 * 基于甲方的公玥生成乙方的公私玥键值对(乙方)
	 * 
	 * @param ApubKey
	 * @return 字节二维数组，0下标为私钥字节数组，1下标为公玥字节数组
	 */
	public static byte[][] genBKeys(byte[] ApubKey) {
		try {
			// 转换甲方的公玥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(ApubKey);
			PublicKey Apub = KeyFactory.getInstance(DEFAULT_ALGORITHM)
					.generatePublic(x509KeySpec);

			// 构建乙方公私玥
			DHParameterSpec dhSpec = ((DHPublicKey) Apub).getParams();
			KeyPairGenerator kGenerator = KeyPairGenerator
					.getInstance(DEFAULT_ALGORITHM);
			kGenerator.initialize(dhSpec);
			KeyPair keyPair = kGenerator.generateKeyPair();
			// 注意点：以下Key用getEncoded方法转为byte数组之前需要先显示转换为DHKey类型才行,
			// 否则后面另一方以该公玥进行再构建会出现定义错误
			return new byte[][] {
					((DHPrivateKey) keyPair.getPrivate()).getEncoded(),
					((DHPublicKey) keyPair.getPublic()).getEncoded() };
		} catch (NoSuchAlgorithmException e) {
			LM.ins().te(DH.class, "genBKeys", e.getMessage());
		} catch (InvalidKeySpecException e) {
			LM.ins().te(DH.class, "genBKeys", e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
			LM.ins().te(DH.class, "genBKeys", e.getMessage());
		}

		return null;
	}

	/**
	 * 基于甲方的公玥生成乙方的公私玥键值对，结果用Base64编码(乙方)
	 * 
	 * @param ApubKey
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genBKeysInBase64(String ApubKey) {
		byte[][] keys = genBKeys(Base64.decoder2Byte(ApubKey));
		return new String[] { Base64.encoder(keys[0]), Base64.encoder(keys[1]) };
	}

	/**
	 * 基于甲方的公玥生成乙方的公私玥键值对，结果用Base64编码(乙方)
	 * 
	 * @param ApubKey
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genBKeysInBase64(byte[] ApubKey) {
		byte[][] keys = genBKeys(ApubKey);
		return new String[] { Base64.encoder(keys[0]), Base64.encoder(keys[1]) };
	}

	/**
	 * 基于甲方的公玥生成乙方的公私玥键值对，结果转换为十六进制字符串(乙方)
	 * 
	 * @param ApubKey
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genBKeysInHex(String ApubKey) {
		byte[][] keys = genBKeys(TextUtils.hex2Bytes(ApubKey));
		return new String[] { TextUtils.bytes2Hex(keys[0]),
				TextUtils.bytes2Hex(keys[1]) };
	}

	/**
	 * 基于甲方的公玥生成乙方的公私玥键值对，结果转换十六进制字符串(乙方)
	 * 
	 * @param ApubKey
	 * @return 字符串数组，0下标为私钥，1下标为公玥
	 */
	public static String[] genBKeysInHex(byte[] ApubKey) {
		byte[][] keys = genBKeys(ApubKey);
		return new String[] { TextUtils.bytes2Hex(keys[0]),
				TextUtils.bytes2Hex(keys[1]) };
	}

	/**
	 * 使用甲方公玥和乙方私钥进行加密
	 * 
	 * @param data
	 * @param ApubKey
	 *            甲方公玥
	 * @param BprvKey
	 *            乙方私钥
	 * @return
	 */
	public static byte[] encrypt(byte[] data, byte[] ApubKey, byte[] BprvKey) {
		return doMethod(data, ApubKey, BprvKey, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用甲方公玥和乙方私钥进行加密，并使用Base64进行解码和编码(乙方操作)
	 * 
	 * @param data
	 * @param ApubKey
	 *            甲方公玥
	 * @param BprvKey
	 *            乙方私钥
	 * @param charset
	 * @return
	 */
	public static String encryptInBase64(String data, String ApubKey,
			String BprvKey, String charset) {
		try {
			return Base64.encoder(encrypt(data.getBytes(charset),
					Base64.decoder2Byte(ApubKey), Base64.decoder2Byte(BprvKey)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DH.class, "encryptInBase64", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 使用甲方公玥和乙方私钥进行加密，并使用Base64进行解码和编码(乙方操作)
	 * 
	 * @param data 待加密数据
	 * @param ApubKey
	 *            甲方公玥
	 * @param BprvKey
	 *            乙方私钥
	 * @return
	 */
	public static String encryptInBase64(String data, String ApubKey,
			String BprvKey) {
			return encryptInBase64(data, ApubKey, BprvKey, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 使用甲方公玥和乙方私钥进行加密，并进行字节数组和十六进制字符串相互转换(乙方操作)
	 * 
	 * @param data
	 * @param ApubKey
	 *            甲方公玥
	 * @param BprvKey
	 *            乙方私钥
	 * @param charset
	 * @return
	 */
	public static String encryptInHex(String data, String ApubKey,
			String BprvKey, String charset) {
		try {
			return TextUtils.bytes2Hex(encrypt(data.getBytes(charset),
					TextUtils.hex2Bytes(ApubKey), TextUtils.hex2Bytes(BprvKey)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DH.class, "encryptInHex", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 使用甲方公玥和乙方私钥进行加密，并进行字节数组和十六进制字符串相互转换(乙方操作)
	 * 
	 * @param data
	 * @param ApubKey
	 *            甲方公玥
	 * @param BprvKey
	 *            乙方私钥
	 * @return
	 */
	public static String encryptInHex(String data, String ApubKey,
			String BprvKey) {
		return encryptInHex(data, ApubKey, BprvKey, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 使用乙方公玥和甲方私钥进行解密（甲方操作）
	 * 
	 * @param data
	 * @param BpubKey
	 *            乙方公钥
	 * @param AprvKey
	 *            甲方私钥
	 * @return
	 */
	public static byte[] decrypt(byte[] data, byte[] BpubKey, byte[] AprvKey) {
		return doMethod(data, BpubKey, AprvKey, Cipher.DECRYPT_MODE);
	}

	/**
	 * 使用乙方公玥和甲方私钥进行解密，并对字符串和字节数组进行Base64编码和解码(甲方操作)
	 * 
	 * @param data
	 * @param BpubKey
	 *            乙方公钥
	 * @param AprvKey
	 *            甲方私钥
	 * @param charset
	 * @return
	 */
	public static String decryptInBase64(String data, String BpubKey,
			String AprvKey, String charset) {
		try {
			return new String(decrypt(Base64.decoder2Byte(data),
					Base64.decoder2Byte(BpubKey), Base64.decoder2Byte(AprvKey)), charset);
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DH.class, "decryptInBase64", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 使用乙方公玥和甲方私钥进行解密，并对字符串和字节数组进行Base64编码和解码(甲方操作)
	 * 
	 * @param data
	 * @param BpubKey
	 *            乙方公钥
	 * @param AprvKey
	 *            甲方私钥
	 * @return
	 */
	public static String decryptInBase64(String data, String BpubKey,
			String AprvKey) {
		return decryptInBase64(data, BpubKey, AprvKey, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 使用乙方公玥和甲方私钥进行解密，并进行字节数组和十六进制字符串相互转换(甲方操作)
	 * 
	 * @param data
	 * @param BpubKey
	 *            乙方公钥
	 * @param AprvKey
	 *            甲方私钥
	 * @param charset
	 * @return
	 */
	public static String decryptInHex(String data, String BpubKey,
			String AprvKey, String charset) {
		try {
			return new String(decrypt(TextUtils.hex2Bytes(data),
					TextUtils.hex2Bytes(BpubKey), TextUtils.hex2Bytes(AprvKey)), charset);
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(DH.class, "decryptInHex", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 使用乙方公玥和甲方私钥进行解密，并进行字节数组和十六进制字符串相互转换(甲方操作)
	 * 
	 * @param data
	 * @param BpubKey
	 *            乙方公钥
	 * @param AprvKey
	 *            甲方私钥
	 * @return
	 */
	public static String decryptInHex(String data, String BpubKey,
			String AprvKey) {
		return decryptInHex(data, BpubKey, AprvKey, GlobalConfig.DEFAULT_CHARSET);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[][] Akeys = DH.genAKeys(DEFAULT_INIT_SIZE);
		byte[][] Bkeys = DH.genBKeys(Akeys[1]);
		String data = "akaiaoladkfjadif124455.}{;/+45da6578gfhsdfg245adf";
		LM.ins().td("A的公玥：\n" + Base64.encoder(Akeys[1]));
		LM.ins().td("A的私钥：\n" + Base64.encoder(Akeys[0]));
		LM.ins().td("B的公玥：\n" + Base64.encoder(Bkeys[1]));
		LM.ins().td("B的私钥：\n" + Base64.encoder(Bkeys[0]));
		byte[] enc = DH.encrypt(data.getBytes(GlobalConfig.DEFAULT_CHARSET), Akeys[1],
				Bkeys[0]);
		byte[] dec = DH.decrypt(enc, Bkeys[1], Akeys[0]);
		byte[] enc1 = DH.encrypt(data.getBytes(GlobalConfig.DEFAULT_CHARSET), Bkeys[1],
				Akeys[0]);
		byte[] dec1 = DH.decrypt(enc, Akeys[1], Bkeys[0]);
		LM.ins().td("数据：" + data);
		LM.ins().td("密文：" + Base64.encoder(enc));
		LM.ins().td("原文：" + new String(dec, GlobalConfig.DEFAULT_CHARSET));
		LM.ins().td("密文：" + Base64.encoder(enc1));
		LM.ins().td("原文：" + new String(dec1, GlobalConfig.DEFAULT_CHARSET));
		LM.ins().td("isEqual=" + (data.equals(new String(dec, GlobalConfig.DEFAULT_CHARSET))));
		
		String[] AkeysStr = DH.genAKeysInHex();
		String[] BkeysStr = DH.genBKeysInHex(AkeysStr[1]);
		LM.ins().td("A1的公玥：\n" + AkeysStr[1]);
		LM.ins().td("A1的私钥：\n" + AkeysStr[0]);
		LM.ins().td("B1的公玥：\n" + BkeysStr[1]);
		LM.ins().td("B1的私钥：\n" + BkeysStr[0]);
		String encStr = DH.encryptInHex(data, AkeysStr[1], BkeysStr[0], GlobalConfig.DEFAULT_CHARSET);
		String decStr = DH.decryptInHex(encStr, BkeysStr[1], AkeysStr[0], GlobalConfig.DEFAULT_CHARSET);
		String encStr1 = DH.encryptInHex(data, BkeysStr[1], AkeysStr[0], GlobalConfig.DEFAULT_CHARSET);
		String decStr1 = DH.decryptInHex(encStr, AkeysStr[1], BkeysStr[0], GlobalConfig.DEFAULT_CHARSET);
		LM.ins().td("数据：" + data);
		LM.ins().td("密文：" + encStr);
		LM.ins().td("原文：" + decStr);
		LM.ins().td("密文：" + encStr1);
		LM.ins().td("原文：" + decStr1);
		LM.ins().td("isEqual=" + (decStr1.equals(data)));
	}
}
