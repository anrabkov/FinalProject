package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;

public class GoToUserPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService service = UserService.getInstance();
        List<User> productList = null;
        try {
            productList = service.query(new FindAllUsersSpecification());
            request.setAttribute(USER_LIST,productList);
            router = new Router(PagePath.USERS_PAGE, FORWARD );
        } catch (ServiceException e) {
            //log
            request.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }

        return router;
    }
}
