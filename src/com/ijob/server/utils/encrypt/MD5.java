package com.ijob.server.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.ijob.server.constants.GlobalConfig;
import com.ijob.server.utils.FileUtils;
import com.ijob.server.utils.TextUtils;
import com.ijob.server.utils.log.LM;

/**
 * 
 * @description MD5摘要加密算法工具类
 * 
 * @author Jackie Zhuang
 * @date 2014年12月11日
 */
public class MD5 {

	/**
	 * 生成摘要字节数组
	 * 
	 * @param content
	 * @return
	 */
	public static byte[] genDigest(String content) {
		return genDigest(content, GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * 生成摘要字节数组
	 * 
	 * @param content
	 *            原文本内容字符串
	 * @param charset
	 *            获取字节数组编码
	 * @return
	 */
	public static byte[] genDigest(String content, String charset) {
		try {
			return genDigest(content.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(MD5.class, "genDigest", e.getMessage());
		}
		return null;
	}

	/**
	 * 生成摘要字节数组
	 * 
	 * @param content
	 *            原文本内容字节数组
	 * @return
	 */
	public static byte[] genDigest(byte[] content) {
		if (content != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(content);
				return md.digest();
			} catch (Exception e) {
				LM.ins().te(MD5.class, "genDigest", e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 使用默认编码UTF-8获取输入内容字节数组，并对其进行MD5摘要加密
	 * 
	 * @param content
	 * @return 十六进制格式的字符串
	 */
	public static String digestInHex(byte[] content) {
		return TextUtils.bytes2Hex(genDigest(content));
	}

	/**
	 * 对输入字节数组内容进行MD5摘要加密
	 * 
	 * @param content
	 * @return 经Base64编码后的字符串
	 */
	public static String digestInBase64(byte[] content) {
		return Base64.encoder(genDigest(content));
	}

	/**
	 * 使用默认编码UTF-8获取输入内容字节数组，并对其进行MD5摘要加密
	 * 
	 * @param content
	 * @param charset
	 * @return 十六进制格式的字符串
	 */
	public static String digestInHex(String content, String charset) {
		return TextUtils.bytes2Hex(genDigest(content, charset));
	}

	/**
	 * 对输入字节数组内容进行MD5摘要加密
	 * 
	 * @param content
	 * @param charset
	 * @return 经Base64编码后的字符串
	 */
	public static String digestInBase64(String content, String charset) {
		try {
			return Base64.encoder(genDigest(content.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(MD5.class, "digestInBase64", e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static String digestFileInBase64(String filePath) {
		try {
			return digestInBase64((FileUtils.readContent(filePath)
					.getBytes(GlobalConfig.DEFAULT_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(MD5.class, "digestFileInBase64", e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static String digestFileInHex(String filePath) {
		try {
			return digestInHex((FileUtils.readContent(filePath)
					.getBytes(GlobalConfig.DEFAULT_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(MD5.class, "digestFileInHex", e.getMessage());
		}
		return null;
	}

	/**
	 * 检验输入内容与所给MD5摘要是否想等
	 * 
	 * @param content
	 * @param md5Val
	 *            十六进制MD5摘要字符串
	 * @return
	 */
	public static boolean checkValidHex(byte[] content, String md5Val) {
		return md5Val.toLowerCase().equals(digestInHex(content));
	}

	/**
	 * 检验输入内容与所给MD5摘要是否想等
	 * 
	 * @param content
	 * @param md5Val
	 *            Base64编码的MD5摘要字符串
	 * @return
	 */
	public static boolean checkValidBase64(byte[] content, String md5Val) {
		return md5Val.toLowerCase().equals(digestInBase64(content));
	}

	/**
	 * 检验输入内容与所给MD5摘要是否想等
	 * 
	 * @param content
	 * @param md5Val
	 *            十六进制MD5摘要字符串
	 * @return
	 */
	public static boolean checkValidHex(String content, String md5Val) {
		try {
			return md5Val.toLowerCase().equals(
					digestInHex(content.getBytes(GlobalConfig.DEFAULT_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(MD5.class, "checkValidHex", e.getMessage());
		}
		return false;
	}

	/**
	 * 检验输入内容与所给MD5摘要是否想等
	 * 
	 * @param content
	 * @param md5Val
	 *            Base64编码的MD5摘要字符串
	 * @return
	 */
	public static boolean checkValidBase64(String content, String md5Val) {
		try {
			return md5Val.toLowerCase().equals(
					digestInBase64(content.getBytes(GlobalConfig.DEFAULT_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(MD5.class, "checkValidBase64", e.getMessage());
		}
		return false;
	}

	/**
	 * 检验文件的MD5摘要与所给MD5摘要是否想等
	 * 
	 * @param fileName
	 *            文件名
	 * @param md5Val
	 *            Base64编码的MD5摘要字符串
	 * @return
	 */
	public static boolean checkFileValid(String fileName, String md5Val) {
		return md5Val.toLowerCase().equals(
				digestInHex(FileUtils.readContentBytesInStandard(fileName)));
	}

	
/*	public static void main(String[] args) {
		String msg = "这仅仅只是个测试而已";
		String s = MD5Digest.digestInHex(msg, Ct.DEFAULT_CHARSET);
		LM.ins().td(MD5Digest.class, "原文：" + msg);
		LM.ins().td(MD5Digest.class, "摘要：" + s + ", 长度:" + s.length());
		String msg1 = "这仅仅只是个测试而已";
		String s1 = MD5Digest.digestInHex(msg1, Ct.DEFAULT_CHARSET);
		LM.ins().td(MD5Digest.class, "原文：" + msg1);
		LM.ins().td(MD5Digest.class, "摘要：" + s1 + ", 长度:" + s1.length());
		String s2 = FileUtils.readContent("IJOB_LOG\\t.txt");
		LM.ins().td(MD5Digest.class, "原文：" + s2);
		LM.ins().td(MD5Digest.class,
				MD5Digest.checkFileValid("IJOB_LOG\\t.txt", s1) + "");
	}*/
	 
}
