/**
 *
 */
package story.template.directive;

import story.core.data.Post;
import story.core.persist.service.PostService;
import story.template.DirectiveHandler;
import story.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 推荐内容查询
 *
 * @author langhsu
 *
 */
@Component
public class BannerDirective extends TemplateDirective {
	@Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "banner";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        List<Post> result = postService.findAllFeatured();
        handler.put(RESULTS, result).render();
    }
}
