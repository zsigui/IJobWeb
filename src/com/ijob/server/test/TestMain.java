package com.ijob.server.test;

import org.json.JSONException;
import org.json.JSONObject;

import com.ijob.server.utils.TextUtils;
import com.ijob.server.utils.log.LM;

public class TestMain {

	public static void main(String[] args) {
		/*String url = "http://test.com/a?ijob_type=21";
		String sType = TextUtils.findFirst(url, "ijob_type=([^&]*)", 1);
		System.out.println(sType);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(
				".p",
				"Znxjb20ud2lzY29tLnBvcnRhbC5jb250YWluZXIuY29yZS5pbXBsLlBvcnRsZXRFbnRpdHlXaW5kb3d8eXJkd2NrfHZpZXd8bm9ybWFsfGphdmF4LmZhY2VzLnBvcnRsZXRicmlkZ2UuU1RBVEVfSUQ9endnbF95cmR3SW5mb05Mb05sclpoZk1QdC10S1QzRC1KLXAyanktcG9ydGxldDp2aWV3OjMyYWJjNTczLTFlZmUtNDQxMi05YzJiLTYzOThmYmRjMTQ2NA__"));
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Connection", "keep-alive"));
		String msg = HttpUtils.getInstance().getStringWithMethodAndDealer("http://jy.gdufs.edu.cn/detach.portal", params, "get", headers, new IResultDeal() {
			
			@Override
			public byte[] doDeal(CloseableHttpResponse respone) {
				// TODO Auto-generated method stub
				try {
					return EntityUtils.toByteArray(respone.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
		System.out.println(StringEscapeUtils.unescapeHtml3(msg));*/
		try {
			JSONObject jsonObject = TextUtils.initJSONObj("p1", "val1", "p2", "val2", "p3", 1);
			String s = jsonObject.toString();
			JSONObject j = TextUtils.initJSONObj("p1", s, "p2", "val2");
			System.out.println(s);
			System.out.println(j.toString());
			JSONObject ss = new JSONObject(j.toString());
			String t = ss.optString("p1");
			System.out.println("p1 = " + t + ", p2 = " + ss.opt("p2"));
			JSONObject l = new JSONObject(t);
			System.out.println("p1 = " + l.opt("p1") + ", p2 = " + l.opt("p2") + ", p3 = " + l.opt("p3"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LM.ins().shutdownExecutor();
	}
}
