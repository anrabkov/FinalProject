package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        Router router = new Router(PagePath.INDEX, Router.RouterType.REDIRECT);
        return router;
    }
}
