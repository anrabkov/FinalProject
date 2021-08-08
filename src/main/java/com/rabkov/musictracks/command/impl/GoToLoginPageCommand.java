package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;

public class GoToLoginPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.LOGIN_PAGE, REDIRECT);
    }
}