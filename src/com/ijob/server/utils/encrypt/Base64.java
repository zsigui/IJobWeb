package com.ijob.server.utils.encrypt;

import java.io.IOException;
import java.io.OutputStream;

import com.ijob.server.constants.GlobalConfig;
import com.ijob.server.utils.log.LM;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @description Base64操作类
 * 
 * @author Jackie Zhuang
 * @date 2014年12月11日
 */
public class Base64 {

	public static String encoder(byte[] content) {
		return new BASE64Encoder().encode(content);
	}

	public static void encoder2Stream(byte[] content, OutputStream out) {
		try {
			new BASE64Encoder().encode(content, out);
		} catch (IOException e) {
			LM.ins().te(Base64.class, "encoder2Stream", e.getMessage());
		}
	}

	public static String decoder2String(String content) {
		return decoder2String(content, GlobalConfig.DEFAULT_CHARSET);
	}

	public static String decoder2String(String content, String charset) {
		try {
			return new String(new BASE64Decoder().decodeBuffer(content),
					charset);
		} catch (IOException e) {
			LM.ins().te(Base64.class, "decoder2String", e.getMessage());
		}
		return null;
	}

	public static byte[] decoder2Byte(String content) {
		try {
			return new BASE64Decoder().decodeBuffer(content);
		} catch (IOException e) {
			LM.ins().te(Base64.class, "decoder2Byte", e.getMessage());
		}
		return null;
	}
}
