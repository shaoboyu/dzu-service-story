/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package story.web.controller.desk.auth;

import story.base.lang.Consts;
import story.core.data.BadgesCount;
import story.core.persist.service.NotifyService;
import story.shiro.authc.AccountSubject;
import story.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import story.web.controller.desk.Views;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends BaseController {
    @Autowired
    private NotifyService notifyService;

    /**
     * 跳转登录页
     * @return
     */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String view() {
		return view(Views.LOGIN);
	}

    /**
     * 提交登录
     * @param username
     * @param password
     * @param model
     * @return
     */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password,@RequestParam(value = "rememberMe",defaultValue = "0") int rememberMe, ModelMap model) {
		String ret = view(Views.LOGIN);
		
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ret;
        }
		
		AuthenticationToken token = createToken(username, password);
        if (token == null) {
        	model.put("message", "用户名或密码错误");
            return ret;
        }

        if (rememberMe == 1) {
            ((UsernamePasswordToken) token).setRememberMe(true);
        }

        try {
            SecurityUtils.getSubject().login(token);

            ret = Views.REDIRECT_USER;
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
            	model.put("message", "用户不存在");
            } else if (e instanceof LockedAccountException) {
            	model.put("message", "用户被禁用");
            } else {
            	model.put("message", "用户认证失败");
            }
        }

        return ret;
	}

}
