package com.rabkov.musictracks.command;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request);
}

