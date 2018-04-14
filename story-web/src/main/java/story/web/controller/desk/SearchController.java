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

import story.core.data.Post;
import story.core.persist.service.PostService;
import story.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController extends BaseController {
	@Autowired
	private PostService postService;

	@RequestMapping("/search")
	public String search(String kw, ModelMap model) {
		Pageable pageable = wrapPageable();
		try {
			if (StringUtils.isNotEmpty(kw)) {
				Page<Post> page = postService.search(pageable, kw);
				model.put("page", page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("kw", kw);
		return view(Views.BROWSE_SEARCH);
	}
	
}
