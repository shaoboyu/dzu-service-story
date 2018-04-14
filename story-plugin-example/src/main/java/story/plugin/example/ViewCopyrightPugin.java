package story.plugin.example;

import story.core.data.Post;
import story.core.hook.interceptor.desk.ChannelControllerHook;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Beldon 2015/10/31
 */
@Component
public class ViewCopyrightPugin implements ChannelControllerHook.ChannelControllerInterceptorListener {
    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        Post ret = (Post) modelAndView.getModelMap().get("view");
        if (ret != null) {
            String content = ret.getContent();
            if (!content.contains("可在评论区进行接力创作")) {
                content += "<br/>注：可在评论区进行接力创作";
                ret.setContent(content);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }
}
