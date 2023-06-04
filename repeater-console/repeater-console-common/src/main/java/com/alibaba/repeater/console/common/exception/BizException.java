package com.alibaba.repeater.console.common.exception;

/**
 * {@link BizException}
 * <p>
 *
 * @author zhaowanxin
 */
public class BizException extends Exception {

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}
