package com.alibaba.jvm.sandbox.repeater.plugin.exception;

/**
 * {@link RecordException} 录制异常
 * <p>
 *
 * @author zhaowanxin
 */
public class RecordException extends NormalException {

    public RecordException(String message) {
        super(message);
    }

    public RecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
