package com.zhaohg.kisso.oauth2.controller;

import com.alibaba.fastjson.JSON;
import com.zhaohg.kisso.oauth2.Constants;
import com.zhaohg.kisso.oauth2.entity.Status;
import com.zhaohg.kisso.oauth2.entity.User;
import com.zhaohg.kisso.oauth2.service.OAuthService;
import com.zhaohg.kisso.oauth2.service.UserService;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/openapi")
public class UserInfoController {

	@Autowired
	private OAuthService oAuthService;

	@Autowired
	private UserService userService;


	@RequestMapping("/userInfo")
	public HttpEntity<String> userInfo(HttpServletRequest request ) throws OAuthSystemException, OAuthProblemException {
		return checkAccessToken(request);
	}


	/**
	 * 不校验accessToken
	 * @param request
	 * @return
	 * @throws OAuthSystemException
	 * @throws OAuthProblemException
	 */
	private HttpEntity<String> nocheckAccessToken(HttpServletRequest request )
		throws OAuthSystemException, OAuthProblemException {

		//构建OAuth资源请求
		OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

		//获取Access Token
		String accessToken = oauthRequest.getAccessToken();

		//获取用户名
		String username = oAuthService.getUsernameByAccessToken(accessToken);
		User user = userService.findByUsername(username);

		return new ResponseEntity<>(JSON.toJSONString(user), HttpStatus.OK);
	}


	/**
	 * 校验accessToken
	 * @param request
	 * @return
	 * @throws OAuthSystemException
	 */
	private HttpEntity<String> checkAccessToken(HttpServletRequest request ) throws OAuthSystemException {
		try {
			//构建OAuth资源请求
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
			//获取Access Token
			String accessToken = oauthRequest.getAccessToken();
			//验证Access Token
			if ( !oAuthService.checkAccessToken(accessToken) ) {
				// 如果不存在/过期了，返回未验证错误，需重新验证
				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(Constants.RESOURCE_SERVER_NAME).setError(OAuthError.ResourceResponse.INVALID_TOKEN)
						.buildHeaderMessage();
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add("Content-Type", "application/json; charset=utf-8");
				Status status = new Status();
				status.setCode(HttpStatus.UNAUTHORIZED.value());
				status.setMsg(Constants.INVALID_ACCESS_TOKEN);
				return new ResponseEntity<>(JSON.toJSONString(status), responseHeaders, HttpStatus.UNAUTHORIZED);
			}
			//获取用户名
			String username = oAuthService.getUsernameByAccessToken(accessToken);
			User user = userService.findByUsername(username);
			return new ResponseEntity<>(JSON.toJSONString(user), HttpStatus.OK);
		} catch ( OAuthProblemException e ) {
			//检查是否设置了错误码
			String errorCode = e.getError();
			if ( OAuthUtils.isEmpty(errorCode) ) {
				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(Constants.RESOURCE_SERVER_NAME).buildHeaderMessage();
				HttpHeaders headers = new HttpHeaders();
				headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
					oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
				return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
			}
			OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setRealm(Constants.RESOURCE_SERVER_NAME).setError(e.getError())
					.setErrorDescription(e.getDescription()).setErrorUri(e.getUri()).buildHeaderMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}