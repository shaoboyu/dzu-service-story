/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package story.web.controller.desk;

import javax.servlet.http.HttpServletRequest;

import story.core.data.Channel;
import story.core.data.Post;
import story.core.persist.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import story.base.lang.Consts;
import story.core.persist.service.ChannelService;
import story.web.controller.BaseController;
import story.web.controller.desk.Views;


@Controller
public class ChannelController extends BaseController {
	@Autowired
	private ChannelService channelService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("/channel/{id}")
	public String channel(@PathVariable Integer id, ModelMap model,
			HttpServletRequest request) {
		// init params
		String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
		int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);

		Channel channel = channelService.getById(id);
		// callback params
		model.put("channel", channel);
		model.put("order", order);
		model.put("pn", pn);
		return view(Views.ROUTE_POST_INDEX);
	}

	@RequestMapping("/view/{id}")
	public String view(@PathVariable Long id, ModelMap model) {
		Post view = postService.get(id);

		Assert.notNull(view, "该文章已被删除");

		postService.identityViews(id);
		model.put("view", view);
		return view(Views.ROUTE_POST_VIEW);
	}
}
