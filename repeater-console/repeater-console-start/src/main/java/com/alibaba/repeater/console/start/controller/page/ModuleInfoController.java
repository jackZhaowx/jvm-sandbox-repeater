package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import com.alibaba.repeater.console.service.ModuleInfoService;
import com.alibaba.repeater.console.start.controller.vo.PagerAdapter;
import com.alibaba.repeater.console.start.controller.vo.ResultAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link ModuleInfoController}
 * <p>
 * 在线模块页面
 *
 * @author zhaowanxin
 */
@RequestMapping("/module")
@Controller
public class ModuleInfoController {

    @Resource
    private ModuleInfoService moduleInfoService;

    @RequestMapping("list.htm")
    public String list(@ModelAttribute("requestParams") ModuleInfoParams params, Model model) {
        PageResult<ModuleInfoBO> result = moduleInfoService.query(params);
        PagerAdapter.transform0(result, model);
        return "module/list";
    }

    @RequestMapping("detail.htm")
    public String detail(@ModelAttribute("requestParams") ModuleInfoParams params, Model model) {
        ModuleInfo result = moduleInfoService.getOne(params.getId());
        model.addAttribute("moduleInfo", result);
        return "module/detail";
    }

    @ResponseBody
    @RequestMapping("/byName.json")
    public RepeaterResult<List<ModuleInfoBO>> list(@RequestParam("appName") String appName, @RequestParam("ip") String ip, @RequestParam("environment") String environment) {
        return moduleInfoService.queryNotIpNotEnvironment(appName, ip,environment);
    }

    @ResponseBody
    @RequestMapping("/updateIngoreKeys.json")
    public RepeaterResult<ModuleInfo> updateIngoreKeys(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.updateIngoreKeys(params);
    }

    @ResponseBody
    @RequestMapping("/report.json")
    public RepeaterResult<ModuleInfoBO> list(@ModelAttribute("requestParams") ModuleInfoBO params) {
        return moduleInfoService.report(params);
    }

    @ResponseBody
    @RequestMapping("/active.json")
    public RepeaterResult<ModuleInfoBO> active(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.active(params);
    }

    @ResponseBody
    @RequestMapping("/frozen.json")
    public RepeaterResult<ModuleInfoBO> frozen(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.frozen(params);
    }

    @ResponseBody
    @RequestMapping("/install.json")
    public RepeaterResult<String> install(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.install(params);
    }

    @ResponseBody
    @RequestMapping("/reload.json")
    public RepeaterResult<String> reload(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.reload(params);
    }
}
