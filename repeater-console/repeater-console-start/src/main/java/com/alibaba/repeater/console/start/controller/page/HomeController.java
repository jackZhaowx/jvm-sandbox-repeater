package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.HomeBO;
import com.alibaba.repeater.console.common.domain.HomeSearchBO;
import com.alibaba.repeater.console.common.params.HomeParams;
import com.alibaba.repeater.console.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        return "index";
    }

    @RequestMapping("/home.htm")
    public String indexJson(Model model) {
        model.addAttribute("homeBo", homeService.dataJson());
        return "home/home";
    }

    @GetMapping("/search.json")
    @ResponseBody
    public HomeSearchBO searchJson(@ModelAttribute("requestParams") HomeParams params) {
        return homeService.searchJson(params);
    }
}
