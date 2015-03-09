package com.ijob.server.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
	public static byte[] readBytesFromStream(InputStream data) {
		if (data != null) {
			try {
				ByteArrayOutputStream result = new ByteArrayOutputStream();
				BufferedInputStream bis = new BufferedInputStream(data);
				byte[] bs = new byte[1024];
				int len = -1;
				while ((len = bis.read(bs)) != -1) {
					result.write(bs, 0, len);
				}
				bis.close();
				data.close();
				result.close();
				bis = null;
				return result.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
