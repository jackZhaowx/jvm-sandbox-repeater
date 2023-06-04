package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.HomeBO;
import com.alibaba.repeater.console.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HomeController {
    @Resource
    private HomeService homeService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("time", "今天");
        return "index";
    }

    @RequestMapping("/index.json")
    @ResponseBody
    public RepeaterResult<List<HomeBO>> indexJson() {
        return homeService.indexJson();
    }
}
