package com.ijob.server.utils.http;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface IResultDeal {
	public byte[] doDeal(CloseableHttpResponse respone);
}
