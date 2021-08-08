package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;

public class PurchaseCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("Cart");
        if (cart != null) {
            LocalDate date = LocalDate.now();
            String orderName = "FishingShop-" + date + "-" + System.currentTimeMillis();
            Order order = new Order.OrderBuilder()
                    .setOrderName(orderName)
                    .setDate(date)
                    .setOrderStatus(OrderStatus.IN_PROCESS)
                    .build();
            OrderService service = OrderService.getInstance();
            try {
                service.insert(order);
                long orderId = order.getId();
                if (orderId != 0) {
                    User user = (User) session.getAttribute(SessionAttribute.USER);
                    Purchase purchase = new Purchase(order.getId(), user.getId(), cart);
                    PurchaseService purchaseService = PurchaseService.getInstance();
                    purchaseService.insert(purchase);
                    router = new Router(GO_TO_START_PAGE, REDIRECT);
                } else {
                    throw new ServiceException("Can not create order");
                }
            } catch (ServiceException e) {
                request.setAttribute(RequestAttribute.EXCEPTION, e);
                router = new Router(ERROR_PAGE, FORWARD);
            }
        } else {
            router = new Router(GO_TO_START_PAGE, REDIRECT);
        }
        return router;
    }
}

