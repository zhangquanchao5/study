package com.study.common;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/**
 * The type Lifeccp logger.
 */
public final class StudyLogger {

    private static final org.apache.logging.log4j.Logger BUSINESS = LogManager.getLogger("businessLog");
    private static final org.apache.logging.log4j.Logger SYSTEM = LogManager.getLogger("systemLog");

    private StudyLogger() {
    }

    /**
     * Rec business log.
     *
     * @param level the level
     * @param msg the msg
     * @param throwable the throwable
     */
    public static void recBusinessLog(Level level, String msg, Throwable throwable) {
        BUSINESS.log(level, msg, throwable);
    }

    /**
     * Rec business log.
     *
     * @param level the level
     * @param msg the msg
     */
    public static void recBusinessLog(Level level, String msg) {
        BUSINESS.log(level, msg);
    }

    /**
     * Rec business log.
     *
     * @param msg the msg
     */
    public static void recBusinessLog(String msg) {
        recBusinessLog(Level.INFO, msg);
    }

    /**
     * Rec sys log.
     *
     * @param level the level
     * @param msg the msg
     * @param throwable the throwable
     */
    public static void recSysLog(Level level, String msg, Throwable throwable) {
        SYSTEM.log(level, msg, throwable);
    }

    /**
     * Rec sys log.
     *
     * @param level the level
     * @param msg the msg
     */
    public static void recSysLog(Level level, String msg) {
        SYSTEM.log(level, msg);
    }

    /**
     * Rec sys log.
     *
     * @param msg the msg
     */
    public static void recSysLog(String msg) {
        recSysLog(Level.INFO, msg);
    }

    /*public static void recBusinessLog(String operateId, String actionId, String msg) {
        BUSINESS.info("operateId  " + operateId + " actionId  " + actionId + " msg  " + msg);
    }

    public static void recBusinessLog(String msg, String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(" {} ");
        }
        BUSINESS.info(msg + sb.toString(), args);
    }

    public static void recSysLog(Level level, String msg, Throwable throwable) {
        if (level == Level.DEBUG) {
            SYSTEM.debug(msg, throwable);
            return;
        } else if (level == Level.ERROR) {
            SYSTEM.error(msg);
            return;
        }
        SYSTEM.info(msg, throwable);
    }

    public static void recSysLog(Level level, String msg) {
        if (level == Level.DEBUG) {
            SYSTEM.debug(msg);
            return;
        } else if (level == Level.ERROR) {
            SYSTEM.error(msg);
            return;
        }
        SYSTEM.info(msg);
    }

    public static void recSysLog(String msg) {
        recSysLog(Level.INFO, msg);
    }*/

}