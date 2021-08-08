package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;

public class GoToActivationPageCommand implements Command {

    UserService service = UserService.getInstance();
    String login = request.getParameter(RequestParameter.LOGIN);
        try {
        User user = service.query(new FindByLoginSpecification(login)).get(0);
        request.setAttribute(RequestAttribute.USER, user);
    } catch (ServiceException e) {
        e.printStackTrace();
    }
        return new Router(PagePath.ACTIVATION_PAGE, Router.RouterType.FORWARD);
}

