package com.baomidou.kisso;

import com.baomidou.kisso.security.token.SSOToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * KISSO 演示
 * </p>
 *
 * @author 青苗
 * @since 2017-08-08
 */
@RestController
public class SampleController {

    @RequestMapping("/")
    public String home() {
        return "Hello Kisso!";
    }

    // 授权登录
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 设置登录 COOKIE
        SSOHelper.setCookie(request, response, SSOToken.create().setIp(request).setId(1000).setIssuer("kisso"), false);
        return "login success!";
    }

    // 查看登录信息
    @RequestMapping("/token")
    public String token(HttpServletRequest request, HttpServletResponse response) {
        String msg = "暂未登录";
        SSOToken ssoToken = SSOHelper.attrToken(request);
        if (null != ssoToken) {
            msg = "登录信息 ip=" + ssoToken.getIp();
            msg += "， id=" + ssoToken.getId();
            msg += "， issuer=" + ssoToken.getIssuer();
        }
        return msg;
    }

    // 退出登录
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SSOHelper.clearLogin(request, response);
        return "Logout Kisso!";
    }
}