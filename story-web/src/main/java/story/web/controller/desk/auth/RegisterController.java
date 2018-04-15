/**
 * 
 */
package story.web.controller.desk.auth;

import story.base.data.Data;
import story.base.lang.Consts;
import story.base.utils.MailHelper;
import story.core.data.AccountProfile;
import story.core.data.User;
import story.core.persist.service.UserService;
import story.core.persist.service.VerifyService;
import story.web.controller.BaseController;
import story.web.controller.desk.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册Controller
 */
@Controller
public class RegisterController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private MailHelper mailHelper;
	
	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getSubject().getProfile();
		if (profile != null) {
			return "redirect:/home";
		}
		return view(Views.REGISTER);
	}
	
	@PostMapping("/register")
	public String register(User post, ModelMap model) {
		Data data;
		String ret = view(Views.REGISTER);
		try {
			post.setAvatar(Consts.AVATAR);
			User user = userService.register(post);
			sendEmail(user);
			data = Data.success("恭喜您! 注册成功!请重新登陆", Data.NOOP);
			data.addLink("login", "重新登陆");
			ret = view(Views.REGISTER_RESULT);
		} catch (Exception e) {
            model.addAttribute("post", post);
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}

	private void sendEmail(User user) {
		String code = verifyService.generateCode(user.getId(), Consts.VERIFY_BIND, user.getEmail());
		Map<String, Object> data = new HashMap<>();
		data.put("userId", user.getId());
		data.put("code", code);
		data.put("type", Consts.VERIFY_BIND);

		mailHelper.sendEmail(Consts.EMAIL_TEMPLATE_BIND, user.getEmail(), "邮箱绑定验证", data);
	}

}