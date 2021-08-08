package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserService service = UserService.getInstance();
        try {
            Optional<User> optionalUser = service.authenticate(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                HttpSession session = request.getSession(true);
                session.setAttribute(USER, user);
                session.setAttribute(NOT_AUTHENTICATED, false);
                session.setAttribute(AUTHENTICATED,true);
                Role role = user.getRole();
                switch (role) {
                    case ADMIN: {
                        router = new Router(ADMIN_PAGE, REDIRECT);
                        break;
                    }
                    case USER: {
                        session.setAttribute(CART,new Cart());
                        router = new Router(GO_TO_START_PAGE, REDIRECT);
                        break;
                    }
                    case MANAGER: {
                        router = new Router(MANAGER_PAGE, REDIRECT);
                        break;
                    }
                    default: {
                        request.setAttribute(WRONG_LOGIN_OR_PASSWORD, true);
                        router = new Router(LOGIN_PAGE, FORWARD);
                    }
                }
            } else {
                request.setAttribute(WRONG_LOGIN_OR_PASSWORD, true);
                router = new Router(LOGIN_PAGE, FORWARD);
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
