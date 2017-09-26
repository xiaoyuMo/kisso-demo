package com.zhaohg.kisso.oauth2;

/**
 * 封装OAuth Server端认证需要的参数
 */
public class ClientParams {

	public static final String CLIENT_ID = "3c65d9602a6a44daaa80e3609c4a2be6"; // 应用id CLIENT_ID

	public static final String CLIENT_SECRET = "28f190d727d645a890a36258c405afd1"; // 应用secret CLIENT_SECRET

	public static final String USERNAME = "admin"; // 用户名

	public static final String PASSWORD = "123"; // 密码

	public static final String OAUTH_SERVER_URL = "http://localhost:8080/authorize"; // 授权地址

	public static final String OAUTH_SERVER_TOKEN_URL = "http://localhost:8080/accessToken"; // ACCESS_TOKEN换取地址

	public static final String OAUTH_SERVER_REDIRECT_URI = "http://baomidou.com"; // 回调地址

	public static final String OAUTH_SERVICE_API = "http://localhost:8080/v1/openapi/userInfo"; // 测试开放数据api

}
