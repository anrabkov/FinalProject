package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.*;
import com.rabkov.musictracks.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class StartPageCommand implements Command {

@Override
public Router execute(HttpServletRequest request) {
    Router router;
    ProductService service = ProductService.getInstance();
    HttpSession session = request.getSession(true);
    boolean notAuthenticated = (boolean) session.getAttribute(SessionAttribute.NOT_AUTHENTICATED);
    request.setAttribute(RequestAttribute.NOT_AUTHENTICATED, notAuthenticated);
    List<Product> productList = null;
    try {
        productList = service.query(new FindAllProductsSpecification());
        request.setAttribute(PRODUCT_LIST, productList);
        router = new Router(PagePath.START_PAGE, FORWARD);
    } catch (ServiceException e) {
        //log
        request.setAttribute(RequestAttribute.EXCEPTION, e);
        router = new Router(PagePath.ERROR_PAGE, FORWARD);
    }

    return router;
}
}
