package com.alibaba.jvm.sandbox.repater.plugin.http;

import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;
import org.mozilla.universalchardet.UniversalDetector;

public class GetByteEncode {

    /**
     * 获取字节数组编码，没有编码默认为转成”UTF-8“
     *
     * @param bytes
     * @return
     */
    public static String guessEncoding(byte[] bytes) {
        try {
            UniversalDetector detector = new UniversalDetector(null);
            detector.handleData(bytes, 0, bytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            detector.reset();
            LogUtil.info("编码为：{}", encoding);
            return encoding;
        } catch (Exception e) {
            LogUtil.error("获取编码失败，message={}", e.getMessage());
            e.printStackTrace();
            return "UTF-8";
        }
    }
}
