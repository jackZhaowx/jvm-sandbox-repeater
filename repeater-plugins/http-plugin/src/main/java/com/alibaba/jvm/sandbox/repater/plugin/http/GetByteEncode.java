package com.alibaba.jvm.sandbox.repater.plugin.http;

import org.mozilla.universalchardet.UniversalDetector;

public class GetByteEncode {
    /**
     * 获取字节数组编码，没有编码默认为转成”UTF-8“
     *
     * @param bytes
     * @return
     */
    public static String guessEncoding(byte[] bytes) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        return encoding;
    }
}
