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
package com.baomidou.kisso.my.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;

/**
 * <p>
 * 
 * @author hubin
 * @date 2015年3月25日
 * @version 1.0.0
 */
@Controller
public class IndexController extends BaseController {

	@RequestMapping("/index")
	public String index(Model model) {
		SSOToken token = SSOHelper.getToken(request);
		if (token == null) {
			/**
			 * 重定向至代理跨域地址页
			 */
			return redirectTo("http://sso.test.com:8080/login.html?ReturnURL=http%3A%2F%2Fmy.web.com%3A8090%2Fproxylogin.html");
		} else {
			model.addAttribute("userId", token.getUid());
		}
		return "index";
	}

}