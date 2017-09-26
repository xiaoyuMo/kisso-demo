/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.zhaohg.kisso.controller;

import com.baomidou.kisso.SSOHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 退出登录
 * </p>
 * 
 * @author hubin
 * @date 2015年3月25日
 * @version 1.0.0
 */
@Controller
public class LogoutController {
	/*
	 * 
	 * 如果实现 SSOCache 缓存， kisso 自动缓存 token 退出只需要 SSOHelper.clearLogin(request, response);
	 * 
	 * 自动清理 token 缓存信息， 同时各个系统都会自动退出。 建议这么！！退出更优雅。。。
	 * 
	 * --------------- 悲剧的开启 ---------------
	 * 
	 * 如果你不这么干那么您只能挨个不同域退出一遍，最终全站退出。
	 * 
	 */

	/**
	 * 统一退出，调用对外提供退出的所有接口
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		SSOHelper.clearLogin(request, response);
		return "logout";
	}

	/**
	 * 退出 SSO 登录
	 */
	@RequestMapping("/logsso")
	public String logsso(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * <p>
		 * SSO 退出，清空退出状态即可
		 * </p>
		 * 
		 * <p>
		 * 子系统退出 SSOHelper.logout(request, response); 注意 sso.properties 包含 退出到
		 * SSO 的地址 ， 属性 sso.logout.url 的配置
		 * </p>
		 */
		SSOHelper.clearLogin(request, response);
		return "redirect:/login.html";
	}
}
