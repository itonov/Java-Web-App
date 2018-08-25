package org.softuni.webapp.interceptors;

import org.softuni.webapp.constants.TitleConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TitleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }
        String viewName = modelAndView.getViewName();

        if (viewName.startsWith("redirect:")) {
            return;
        }
        Object titleCandidate = modelAndView.getModelMap().get(TitleConstants.TITLE_NAME);
        if (titleCandidate == null) {
            modelAndView.getModelMap().addAttribute(TitleConstants.TITLE_NAME, TitleConstants.TITLE_PREFIX);
        } else {
            String title = titleCandidate.toString();
            modelAndView.getModelMap().addAttribute(TitleConstants.TITLE_NAME,
                    TitleConstants.TITLE_PREFIX + " - " + title);

        }
    }
}
