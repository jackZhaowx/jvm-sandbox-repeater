package com.alibaba.repeater.console.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.Serializer;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializerProvider;
import com.alibaba.repeater.console.common.Constants;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.RecordWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link }
 * <p>
 *
 * @author zhaowanxin
 */
public class ConvertUtil {

  private static final Logger log = LoggerFactory.getLogger(ConvertUtil.class);

  public static Record convertWrapper(RecordWrapper wrapper, String body) {
    Record record = new Record();
    record.setAppName(wrapper.getAppName());
    record.setEnvironment(wrapper.getEnvironment());
    record.setGmtCreate(new Date());
    record.setGmtRecord(new Date(wrapper.getTimestamp()));
    record.setClientHost(wrapper.getClientHost());
    record.setUrl(wrapper.getUrl());
    record.setHost(wrapper.getHost());
    record.setTraceId(wrapper.getTraceId());
    Serializer hessian = SerializerProvider.instance().provide(Serializer.Type.HESSIAN);
    try {
      Object response = hessian.deserialize(wrapper.getEntranceInvocation().getResponseSerialized(),
          Object.class);
      if (response instanceof String) {
        record.setResponse(convert2Json((String) response));
      } else {
        record.setResponse(JacksonUtil.serialize(response));
      }
      record.setRequest(JacksonUtil.serialize(
          hessian.deserialize(wrapper.getEntranceInvocation().getRequestSerialized(),
              Object[].class)));
      String source = "";
      try {
        JSONArray json = (JSONArray) JSON.parse(record.getRequest());
        log.info("json:{}",json);
        if (json != null && json.size() > 0) {
          Map<String, String> o = (Map<String, String>) json.get(0);
          log.info("o:{}",o);
          Map<String, Object> o2 = (Map<String, Object>) JSON.parse(o.get("body"));
          log.info("o2:{}",o2);
          JSONObject jo = (JSONObject) o2.get("head");
          log.info("jo:{}",jo);
          source = (String) jo.get("source_system");
          log.info("source:{}",source);
        }
      } catch (Exception e) {

      }
      record.setSource(source);
    } catch (SerializeException e) {
      // ignore
    }
    record.setEntranceDesc(wrapper.getEntranceDesc());
    record.setWrapperRecord(body);
    record.setCost(wrapper.getCost());
    record.setReplayType(Constants.REPLAY_TYPE_EVENT);
    return record;
  }

  public static String convert2Json(String json) {
    try {
      return JacksonUtil.serialize(JacksonUtil.deserialize(json, HashMap.class));
    } catch (SerializeException e) {
      return json;
    }
  }
}
