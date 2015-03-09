package com.ijob.server.ui.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ijob.server.constants.GlobalConfig;
import com.ijob.server.constants.HttpFeedbackConstants;
import com.ijob.server.constants.ParamConstants;
import com.ijob.server.utils.DateUtils;
import com.ijob.server.utils.TextUtils;
import com.ijob.server.utils.encrypt.MD5;

/**
 * Servlet implementation class getDeseKey
 */
@WebServlet("/getDeseKey")
public class GetDeseKey extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetDeseKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置防止页面中文乱码
		response.setContentType("text/html;charset="
				+ GlobalConfig.DEFAULT_CHARSET);
		request.setCharacterEncoding(GlobalConfig.DEFAULT_CHARSET);

		String keyno = request.getParameter(ParamConstants.PARAM_DESE_KEY_NO);
		System.err.println("getKeyNo = " + keyno);
		try {
			JSONObject obj = null;
			if (!TextUtils.isEmpty(keyno)) {
				String key = GlobalConfig.mapDeseKeys.get(keyno);
				if (key == null) {
					key = initDeseKey(keyno);
				}
				System.err.println("initKey = " + key);
				if (!TextUtils.isEmpty(key)) {
					// 成功返回
					obj = TextUtils.initJSONObj(HttpFeedbackConstants.TAG_CODE,
							HttpFeedbackConstants.CODE_OK,
							HttpFeedbackConstants.TAG_DATA, key);
				} else {
					// 构造key失败
					obj = TextUtils.initJSONObj(HttpFeedbackConstants.TAG_CODE,
							HttpFeedbackConstants.CODE_FAILED,
							HttpFeedbackConstants.TAG_ERROR_CODE,
							HttpFeedbackConstants.FAILED_SERVER_ERR,
							HttpFeedbackConstants.TAG_MSG,
							HttpFeedbackConstants.FAILED_STR_SERVER_ERR);
				}
			} else {
				// get传递参数错误
				obj = TextUtils.initJSONObj(HttpFeedbackConstants.TAG_CODE,
						HttpFeedbackConstants.CODE_FAILED,
						HttpFeedbackConstants.TAG_ERROR_CODE,
						HttpFeedbackConstants.FAILED_GET_INFO,
						HttpFeedbackConstants.TAG_MSG,
						HttpFeedbackConstants.FAILED_STR_GET_INFO);
			}
			Writer wr = response.getWriter();
			wr.write(obj.toString());
			wr.flush();
			wr.close();
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				// 解析JSON出错
				String err = TextUtils.initJSONObj(
						HttpFeedbackConstants.TAG_CODE,
						HttpFeedbackConstants.CODE_FAILED,
						HttpFeedbackConstants.TAG_ERROR_CODE,
						HttpFeedbackConstants.FAILED_SERVER_ERR,
						HttpFeedbackConstants.TAG_MSG,
						HttpFeedbackConstants.FAILED_STR_SERVER_ERR).toString();
				response.getWriter().write(err);
				response.getWriter().close();
			} catch (JSONException excepted) {
			}
		}
	}

	/**
	 * 根据传入的Keyno结合当前日期字符串进行MD5后形成3DES密钥
	 * 
	 * @param keyno
	 * @return
	 */
	private String initDeseKey(String keyno) {
		return MD5.digestInHex(
				keyno + String.valueOf(DateUtils.getVal("yyyyMMddHHmmss")),
				GlobalConfig.DEFAULT_CHARSET);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write(
				"<p>The post method is not supported here</p>");
		response.getWriter().close();
	}

}
