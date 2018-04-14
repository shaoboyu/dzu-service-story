/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package story.web.controller.desk.user;

import story.base.context.AppContext;
import story.base.data.Data;
import story.base.utils.FilePathUtils;
import story.base.utils.ImageUtils;
import story.core.data.AccountProfile;
import story.core.persist.service.UserService;
import story.web.controller.BaseController;
import story.web.controller.desk.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;


@Controller
@RequestMapping("/user")
public class AvatarController extends BaseController {
	@Autowired
	private AppContext appContext;
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AvatarController.class);

	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public String view() {
		return view(Views.USER_AVATAR);
	}
	
	@RequestMapping(value = "/avatar", method = RequestMethod.POST)
	public String post(String path, Float x, Float y, Float width, Float height, ModelMap model) {

		LOGGER.info("AvatarController start path is :{}",path);
		AccountProfile profile = getSubject().getProfile();
		
		if (StringUtils.isEmpty(path)) {
			model.put("data", Data.failure("请选择图片"));
			return view(Views.USER_AVATAR);
		}
		
		if (width != null && height != null) {
			String root = fileRepoFactory.select().getRoot();
			LOGGER.info("AvatarController start root is :{}",root);
			File temp = new File(root + path);
			File scale = null;
			
			// 目标目录
			String ava100 = appContext.getAvaDir() + getAvaPath(profile.getId(), 100);
			String dest = root + ava100;
			try {
				// 判断父目录是否存在
				File f = new File(dest);
		        if(!f.getParentFile().exists()){
		            f.getParentFile().mkdirs();
		        }
		        // 在目标目录下生成截图
		        String scalePath = f.getParent() + "/" + profile.getId() + ".jpg";

				LOGGER.info("AvatarController start scalePath is :{}",scalePath);
				ImageUtils.truncateImage(temp.getAbsolutePath(), scalePath, x.intValue(), y.intValue(), width.intValue());
		        
				// 对结果图片进行压缩
				ImageUtils.scaleImage(scalePath, dest, 100);

				AccountProfile user = userService.updateAvatar(profile.getId(), ava100);
				putProfile(user);
				
				scale = new File(scalePath);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				temp.delete();
				if (scale != null) {
					scale.delete();
				}
			}
		}
		return "redirect:/user/profile";
	}
	
	public String getAvaPath(long uid, int size) {
		String base = FilePathUtils.getAvatar(uid);
		return String.format("/%s_%d.jpg", base, size);
	}
}
