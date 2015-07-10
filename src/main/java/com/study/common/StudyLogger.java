package com.study.common;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * The type Lifeccp logger.
 */
public final class StudyLogger {

    private static final Logger BUSINESS = LogManager.getLogger("businessLog");
    private static final Logger SYSTEM = LogManager.getLogger("systemLog");

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


}