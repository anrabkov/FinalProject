package com.rabkov.musictracks.controller.filter;

import com.rabkov.musictracks.command.CommandType;
import com.rabkov.musictracks.command.PagePath;
import com.rabkov.musictracks.command.SessionAttribute;
import com.rabkov.musictracks.entity.Role;
import com.rabkov.musictracks.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/ApiController", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class RoleFilter implements Filter {
    private FilterSecurityProvider provider = FilterSecurityProvider.getInstance();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (user == null) {
            user = new User.UserBuilder()
                    .setRole(Role.GUEST)
                    .build();
            session.setAttribute(SessionAttribute.USER, user);
            session.setAttribute(SessionAttribute.NOT_AUTHENTICATED, true);
            session.setAttribute(SessionAttribute.AUTHENTICATED, false);
        }
        CommandType commandType;
        try {
            commandType = valueOf(request.getParameter(COMMAND).toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = DEFAULT;
        }
        if(!provider.isUserCan(user,commandType)){
            servletRequest.getRequestDispatcher(PagePath.GO_TO_START_PAGE).forward(servletRequest,servletResponse);
        }

        if (filterChain != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}
