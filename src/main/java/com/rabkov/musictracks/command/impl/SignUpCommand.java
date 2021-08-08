package com.rabkov.musictracks.command.impl;

import com.rabkov.musictracks.command.Command;
import com.rabkov.musictracks.command.Router;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService service = UserService.getInstance();
        String login = request.getParameter(LOGIN);
        try {
            List<User> userList = service.query(new FindByLoginSpecification(login));
            if (userList.isEmpty()) {
                String email = request.getParameter(EMAIL);
                if (UserEmailValidator.isValid(email)) {
                    String password = request.getParameter(RequestParameter.PASSWORD);
                    String confirmPassword = request.getParameter(RequestParameter.CONFIRM_PASSWORD);
                    if (password.equals(confirmPassword)) {
                        HttpSession session = request.getSession(true);
                        User user = (User) session.getAttribute(SessionAttribute.USER);
                        user.setLogin(login);
                        user.setEmail(email);
                        user.setPassword(PasswordCodec.getInstance().codeString(password,login));
                        user.setStatus(Status.NOT_ACTIVATED);
                        service.insert(user);
                        MailSenderService mailSenderService = MailSenderServiceImpl.getInstance();
                        String linkForActivation = "<a href=\"" +
                                request.getRequestURL() +
                                GO_TO_ACTIVATION_PAGE +
                                "&" +
                                LOGIN +
                                "=" +
                                login +
                                "\">" +
                                "Your link for activation.</a>";
                        mailSenderService.send(email, "Link for activation", linkForActivation);
                        router = new Router(INFORMATION_PAGE, RouterType.REDIRECT);
                    }else{
                        request.setAttribute(INVALID_PASSWORDS, true);
                        router = new Router(SIGN_UP_PAGE, FORWARD);
                    }
                } else {
                    request.setAttribute(INVALID_EMAIL, true);
                    router = new Router(SIGN_UP_PAGE, FORWARD);
                }
            } else {
                request.setAttribute(BOOKED_LOGIN, true);
                router = new Router(SIGN_UP_PAGE, FORWARD);
            }
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
}
