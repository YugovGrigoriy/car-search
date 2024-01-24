package ru.edu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.edu.controller.AuthenticationController;
import ru.edu.entity.UserSite;


public class NameNotAvailableException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    public NameNotAvailableException(UserSite user) {
        // todo: использовать одинаковые названия для одних и тех же сущностей
        // например почему username и login ?
        String login = user.getUsername();
        logger.info("trying to register with an existing login: " + login);

    }




}
