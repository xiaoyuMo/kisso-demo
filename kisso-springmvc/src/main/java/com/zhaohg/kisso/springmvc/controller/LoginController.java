/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.zhaohg.kisso.springmvc.controller;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.kisso.web.waf.request.WafRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 */
@Controller
public class LoginController{

    /**
     * 登录 （注解跳过权限验证）
     */
    @Login(action = Action.Skip)
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        SSOToken st = SSOHelper.getSSOToken(request);
        if (st != null) {
            return "redirect:/index.html";
        }
        return "login";
    }

    /**
     * 登录 （注解跳过权限验证）
     */
    @Login(action = Action.Skip)
    @RequestMapping("/loginpost")
    public String loginpost(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 生产环境需要过滤sql注入
         */
        WafRequestWrapper req = new WafRequestWrapper(request);
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if ("kisso".equals(username) && "123".equals(password)) {

            //记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
            //String rememberMe = req.getParameter("rememberMe");
            //if ( "on".equals(rememberMe) ) {
            //	request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 604800);
            //}
            SSOHelper.setCookie(request, response, SSOToken.create().setId(12306L).setIssuer("12306"), true);
            // true 会销毁当前 JsessionId 如果用到了 session 相关改为 false

			/*
             * 登录需要跳转登录前页面，自己处理 ReturnURL 使用
			 * HttpUtil.decodeURL(xx) 解码后重定向
			 */
            return "redirect:/index.html";
        }
        return "login";
    }
}
