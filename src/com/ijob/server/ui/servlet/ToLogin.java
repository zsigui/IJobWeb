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
import com.ijob.server.dao.UserDAO;
import com.ijob.server.model.User;
import com.ijob.server.utils.TextUtils;
import com.ijob.server.utils.encrypt.DESede;

/**
 * Servlet implementation class ToLogin
 */
@WebServlet("/toLogin")
public class ToLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ToLogin() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write(
				"<p>The get method is not supported here</p>");
		response.getWriter().close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置防止页面中文乱码
		response.setContentType("text/html;charset="
				+ GlobalConfig.DEFAULT_CHARSET);
		request.setCharacterEncoding(GlobalConfig.DEFAULT_CHARSET);

		String keyno = request.getParameter(ParamConstants.PARAM_DESE_KEY_NO);
		String postMsg = request
				.getParameter(ParamConstants.PARAM_POST_DECRYPT_DATA);
		try {
			JSONObject obj = null;
			if (!TextUtils.isEmpty(keyno) && !TextUtils.isEmpty(postMsg)) {
				String key = GlobalConfig.mapDeseKeys.get(keyno);
				if (!TextUtils.isEmpty(key)) {
					postMsg = DESede.decryptInHex(postMsg, key);
					JSONObject requestData = new JSONObject(postMsg);
					String uname = requestData
							.optString(ParamConstants.PARAM_UNAME);
					String upwd = requestData
							.optString(ParamConstants.PARAM_UPWD);
					User user = UserDAO.verifyLogin(uname, upwd);
					if (user != null) {
						JSONObject dataJson = new JSONObject();
						dataJson.put(ParamConstants.PARAM_UID, user.getUId());
						dataJson.put(ParamConstants.PARAM_UNAME, user.getName());
						dataJson.put(ParamConstants.PARAM_UNICKNAME,
								user.getNickName());
						dataJson.put(ParamConstants.PARAM_UEMAIL,
								user.getEmail());
						dataJson.put(ParamConstants.PARAM_UGENDER,
								user.getGender());
						dataJson.put(ParamConstants.PARAM_UMOBILEPHONE,
								user.getMobilePhone());
						dataJson.put(ParamConstants.PARAM_USEC_ANSWER,
								user.getSecAnswer());
						dataJson.put(ParamConstants.PARAM_USEC_QUESTION,
								user.getSecQuestion());
						dataJson.put(ParamConstants.PARAM_USTU_NO,
								user.getStudNo());
						dataJson.put(ParamConstants.PARAM_USTU_PWD,
								user.getStudPwd());
						dataJson.put(ParamConstants.PARAM_UDESCRIPTION,
								user.getDescription());
						String encryptMSg = DESede.encryptInHex(
								dataJson.toString(), key);
						if (encryptMSg != null) {
							// 成功返回
							obj = TextUtils.initJSONObj(
									HttpFeedbackConstants.TAG_CODE,
									HttpFeedbackConstants.CODE_OK,
									HttpFeedbackConstants.TAG_DATA, encryptMSg);
						} else {
							// 加密失败
							obj = TextUtils
									.initJSONObj(
											HttpFeedbackConstants.TAG_CODE,
											HttpFeedbackConstants.CODE_FAILED,
											HttpFeedbackConstants.TAG_ERROR_CODE,
											HttpFeedbackConstants.FAILED_SERVER_ERR,
											HttpFeedbackConstants.TAG_MSG,
											HttpFeedbackConstants.FAILED_STR_SERVER_ERR);
						}
					} else {
						// 数据库查找不到对应的信息
						obj = TextUtils.initJSONObj(
								HttpFeedbackConstants.TAG_CODE,
								HttpFeedbackConstants.CODE_FAILED,
								HttpFeedbackConstants.TAG_ERROR_CODE,
								HttpFeedbackConstants.FAILED_GET_INFO,
								HttpFeedbackConstants.TAG_MSG,
								HttpFeedbackConstants.FAILED_STR_GET_INFO);
					}
				} else {
					// 传递获取3DES密钥的keyno不存在
					obj = TextUtils.initJSONObj(HttpFeedbackConstants.TAG_CODE,
							HttpFeedbackConstants.CODE_FAILED,
							HttpFeedbackConstants.TAG_ERROR_CODE,
							HttpFeedbackConstants.FAILED_GET_INFO,
							HttpFeedbackConstants.TAG_MSG,
							HttpFeedbackConstants.FAILED_STR_GET_INFO);
				}
			} else {
				// post传递参数出错
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

}
