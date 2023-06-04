package com.alibaba.repeater.console.start.controller.vo;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import org.springframework.ui.Model;

public class ResultAdapter {
    public static void transform(RepeaterResult<ModuleInfo> result, Model model, String modultInfo) {
        model.addAttribute(modultInfo, result);
    }
}
