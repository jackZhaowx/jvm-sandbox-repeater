package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.repeater.console.common.domain.CreenBO;
import com.alibaba.repeater.console.common.domain.SearchTop5BO;
import com.alibaba.repeater.console.service.CreenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CreenController {
    @Resource
    private CreenService creenService;

    @RequestMapping("/creen.htm")
    public String creen(Model model) {
        return "creen/creen";
    }

    @RequestMapping("/creenSearch.json")
    @ResponseBody
    public CreenBO creenSearch() {
        return creenService.creenSearch();
    }
}
