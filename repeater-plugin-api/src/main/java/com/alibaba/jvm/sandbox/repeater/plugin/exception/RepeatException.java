package com.alibaba.jvm.sandbox.repeater.plugin.exception;

/**
 * {@link RepeatException} 回放异常
 * <p>
 *
 * @author zhaowanxin
 */
public class RepeatException extends NormalException {

    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }
}
