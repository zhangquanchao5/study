package com.study.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by Star on 2015/7/14.
 */
@Component
public class MessageUtil {
    private static Locale locale = Locale.CHINA;

    @Autowired
    private SpringContextHelper springContext;

    /**
     * Gets message.
     *
     * @param messageId the message id
     * @param args the args
     * @return the message
     */
    public String getMessage(String messageId, Object... args) {
        String message = springContext.getApplicationContext().getMessage(messageId, args, messageId, locale);
        return message == null ? messageId : message;
    }

    /**
     * Gets spring app context.
     *
     * @return the spring app context
     */
    public SpringContextHelper getSpringAppContext() {
        return springContext;
    }

}
