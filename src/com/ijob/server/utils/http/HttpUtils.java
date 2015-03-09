package com.ijob.server.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ijob.server.constants.GlobalConfig;
import com.ijob.server.utils.log.LM;

public class HttpUtils {

	private static final int DEFAULT_SO_TIMEOUT = 10000;
	private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;

	private static class SingletonHolder {
		private static final HttpUtils mInstance = new HttpUtils();
	}

	public static HttpUtils getInstance() {
		return SingletonHolder.mInstance;
	}

	private String initGetRequestUrl(String reqUrl, List<NameValuePair> params) {
		if (params == null || params.size() == 0)
			return reqUrl;
		StringBuilder builder = new StringBuilder();
		builder.append(reqUrl).append("?");
		for (NameValuePair pair : params) {
			builder.append(pair.getName()).append("=").append(pair.getValue())
					.append("&");
		}
		// 去掉最后的 &
		builder.deleteCharAt(builder.length() - 1);
		String realRequestUrl = builder.toString();
		return realRequestUrl;
	}

	private void setHeaders(HttpUriRequest request, List<Header> headers) {
		if (headers != null) {
			for (Header header : headers) {
				if (request.containsHeader(header.getName())) {
					request.setHeader(header.getName(), header.getValue());
				} else {
					request.addHeader(header);
				}
			}
		}
	}

	private byte[] doGet(String reqUrl, List<NameValuePair> params) {
		CloseableHttpClient client = null;
		CloseableHttpResponse respone = null;
		byte[] result = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(DEFAULT_SO_TIMEOUT)
					.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
			String realUrl = initGetRequestUrl(reqUrl, params);
			HttpGet httpGet = new HttpGet(realUrl);
			client = HttpClients.createDefault();
			httpGet.setConfig(requestConfig);
			respone = (CloseableHttpResponse) client.execute(httpGet);
			if (respone.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toByteArray(respone.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
			LM.ins().te(HttpUtils.class, "doGet", e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (respone != null)
					respone.close();
			} catch (IOException e) {
				e.printStackTrace();
				LM.ins().te(HttpUtils.class, "doGet", e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 执行post操作，然后对结果进行回调处理
	 * 
	 * @param reqUrl
	 * @param params
	 * @param headers
	 * @param dealer
	 *            回调处理类
	 * @return
	 */
	private byte[] doGetToDeal(String reqUrl, List<NameValuePair> params,
			List<Header> headers, IResultDeal dealer) {
		CloseableHttpClient client = null;
		CloseableHttpResponse respone = null;
		byte[] result = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(DEFAULT_SO_TIMEOUT)
					.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
			String realUrl = initGetRequestUrl(reqUrl, params);
			HttpGet httpGet = new HttpGet(realUrl);
			client = HttpClients.createDefault();
			setHeaders(httpGet, headers);
			httpGet.setConfig(requestConfig);
			respone = (CloseableHttpResponse) client.execute(httpGet);
			result = dealer.doDeal(respone);
		} catch (IOException e) {
			LM.ins().te(HttpUtils.class, "doGetToDeal[IOException]1",
					e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (respone != null)
					respone.close();
			} catch (IOException e) {
				LM.ins().te(HttpUtils.class, "doGetToDeal[IOException]2",
						e.getMessage());
			}
		}
		return result;
	}

	private Header[] doGetToMatchForHeaders(String reqUrl,
			List<NameValuePair> params, List<Header> headers, IMatch matcher) {
		CloseableHttpClient client = null;
		CloseableHttpResponse respone = null;
		Header[] result = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(DEFAULT_SO_TIMEOUT)
					.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
			String realUrl = initGetRequestUrl(reqUrl, params);
			HttpGet httpGet = new HttpGet(realUrl);
			client = HttpClients.createDefault();
			setHeaders(httpGet, headers);
			httpGet.setConfig(requestConfig);
			respone = (CloseableHttpResponse) client.execute(httpGet);
			Header[] tmp = respone.getAllHeaders();
			byte[] msg = EntityUtils.toByteArray(respone.getEntity());
			if (matcher.match(msg)) {
				result = tmp;
				tmp = null;
			}
		} catch (IOException e) {
			LM.ins().te(HttpUtils.class,
					"doPostGetHeaderOnMatcher[IOException]1", e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (respone != null)
					respone.close();
			} catch (IOException e) {
				LM.ins().te(HttpUtils.class,
						"doPostGetHeaderOnMatcher[IOException]2",
						e.getMessage());
			}
		}
		return result;
	}
	
	private byte[] doPost(String reqUrl, List<NameValuePair> params) {
		CloseableHttpClient client = null;
		CloseableHttpResponse respone = null;
		byte[] result = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(DEFAULT_SO_TIMEOUT)
					.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
			HttpPost httpPost = new HttpPost(reqUrl);
			client = HttpClients.createDefault();
			httpPost.setConfig(requestConfig);
			System.out.println("post entity : " + new UrlEncodedFormEntity(params));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			respone = (CloseableHttpResponse) client.execute(httpPost);
			if (respone.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toByteArray(respone.getEntity());
			}
			LM.ins().td(HttpUtils.class, "doPost",
					"HttpStatus = " + respone.getStatusLine().getStatusCode());
		} catch (IOException e) {
			LM.ins()
					.te(HttpUtils.class, "doPost[IOException]1", e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (respone != null)
					respone.close();
			} catch (IOException e) {
				LM.ins().te(HttpUtils.class, "doPost[IOException]2",
						e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 执行post操作，然后对结果进行回调处理
	 * 
	 * @param reqUrl
	 * @param params
	 * @param dealer
	 *            回调处理类
	 * @return
	 */
	private byte[] doPostToDeal(String reqUrl, List<NameValuePair> params,
			List<Header> headers, IResultDeal dealer) {
		CloseableHttpClient client = null;
		CloseableHttpResponse respone = null;
		byte[] result = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(DEFAULT_SO_TIMEOUT)
					.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
			HttpPost httpPost = new HttpPost(reqUrl);
			client = HttpClients.createDefault();
			httpPost.setConfig(requestConfig);
			System.out.println("post entity : " + new UrlEncodedFormEntity(params));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			setHeaders(httpPost, headers);
			respone = (CloseableHttpResponse) client.execute(httpPost);
			result = dealer.doDeal(respone);
		} catch (IOException e) {
			LM.ins().te(HttpUtils.class, "doPostDeal[IOException]1",
					e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (respone != null)
					respone.close();
			} catch (IOException e) {
				LM.ins().te(HttpUtils.class, "doPostDeal[IOException]2",
						e.getMessage());
			}
		}
		return result;
	}

	private Header[] doPostToMatchForHeaders(String reqUrl,
			List<NameValuePair> params, List<Header> headers, IMatch matcher) {
		CloseableHttpClient client = null;
		CloseableHttpResponse respone = null;
		Header[] result = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(DEFAULT_SO_TIMEOUT)
					.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
			HttpPost httpPost = new HttpPost(reqUrl);
			client = HttpClients.createDefault();
			httpPost.setConfig(requestConfig);
			System.out.println("post entity : " + new UrlEncodedFormEntity(params));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			setHeaders(httpPost, headers);
			respone = (CloseableHttpResponse) client.execute(httpPost);
			Header[] tmp = respone.getAllHeaders();
			byte[] msg = EntityUtils.toByteArray(respone.getEntity());
			if (matcher.match(msg)) {
				result = tmp;
				tmp = null;
			}
		} catch (IOException e) {
			LM.ins().te(HttpUtils.class,
					"doPostGetHeaderOnMatcher[IOException]1", e.getMessage());
		} finally {
			try {
				if (client != null)
					client.close();
				if (respone != null)
					respone.close();
			} catch (IOException e) {
				LM.ins().te(HttpUtils.class,
						"doPostGetHeaderOnMatcher[IOException]2",
						e.getMessage());
			}
		}
		return result;
	}

	public String getStringWithMethod(String reqUrl,
			List<NameValuePair> params, String method) {
		return getStringWithMethod(reqUrl, params, method, GlobalConfig.DEFAULT_CHARSET);
	}

	public String getStringWithMethod(String reqUrl,
			List<NameValuePair> params, String method, String charset) {
		byte[] datas = null;
		if (method.toLowerCase().equals("post")) {
			datas = doPost(reqUrl, params);
		} else if (method.toLowerCase().equals("get")) {
			datas = doGet(reqUrl, params);
		}
		try {
			return (datas != null ? new String(datas, charset) : null);
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(HttpUtils.class,
					"getStringWithMethod[UnsupportedEncodingException]",
					e.getMessage());
		}
		return null;
	}

	public String getStringWithMethodAndDealer(String reqUrl,
			List<NameValuePair> params, String method, IResultDeal dealer) {
		return getStringWithMethodAndDealer(reqUrl, params, method,
				GlobalConfig.DEFAULT_CHARSET, null, dealer);
	}

	public String getStringWithMethodAndDealer(String reqUrl,
			List<NameValuePair> params, String method, List<Header> headers,
			IResultDeal dealer) {
		return getStringWithMethodAndDealer(reqUrl, params, method,
				GlobalConfig.DEFAULT_CHARSET, headers, dealer);
	}

	public String getStringWithMethodAndDealer(String reqUrl,
			List<NameValuePair> params, String method, String charset,
			List<Header> headers, IResultDeal dealer) {
		byte[] datas = null;
		if (method.toLowerCase().equals("post")) {
			datas = doPostToDeal(reqUrl, params, headers, dealer);
		} else if (method.toLowerCase().equals("get")) {
			datas = doGetToDeal(reqUrl, params, headers, dealer);
		}
		try {
			return (datas != null ? new String(datas, charset) : null);
		} catch (UnsupportedEncodingException e) {
			LM.ins().te(HttpUtils.class,
					"getStringWithMethod[UnsupportedEncodingException]",
					e.getMessage());
		}
		return null;
	}

	public byte[] getBytesWithMethod(String reqUrl, List<NameValuePair> params,
			String method) {
		byte[] datas = null;
		if (method.toLowerCase().equals("post")) {
			datas = doPost(reqUrl, params);
		} else if (method.toLowerCase().equals("get")) {
			datas = doGet(reqUrl, params);
		}
		return datas;
	}

	public byte[] getBytesWithMethodAndDealer(String reqUrl,
			List<NameValuePair> params, String method, IResultDeal dealer) {
		return getBytesWithMethodAndDealer(reqUrl, params, method, null, dealer);
	}

	public byte[] getBytesWithMethodAndDealer(String reqUrl,
			List<NameValuePair> params, String method, List<Header> headers,
			IResultDeal dealer) {
		byte[] datas = null;
		if (method.toLowerCase().equals("post")) {
			datas = doPostToDeal(reqUrl, params, headers, dealer);
		} else if (method.toLowerCase().equals("get")) {
			datas = doGetToDeal(reqUrl, params, headers, dealer);
		}
		return datas;
	}
	
	public Header[] getHeaderWithMethodAndMatcher(String reqUrl,
			List<NameValuePair> params, String method, List<Header> headers, IMatch matcher) {
		Header[] datas = null;
		if (method.toLowerCase().equals("post")) {
			datas = doPostToMatchForHeaders(reqUrl, params, headers, matcher);
		} else if (method.toLowerCase().equals("get")) {
			datas = doGetToMatchForHeaders(reqUrl, params, headers, matcher);
		}
		return datas;
	}

}
