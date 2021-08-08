package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;
import com.rabkov.musictracks.command.PagePath;
import com.rabkov.musictracks.command.Router;

import javax.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req) {
        return new Router(PagePath.ERROR_404_PAGE, Router.RouterType.REDIRECT);
    }

}