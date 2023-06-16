package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.Constants;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.domain.ReplayBO;
import com.alibaba.repeater.console.common.domain.ReplayListBO;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.dal.model.Replay;
import com.alibaba.repeater.console.service.ReplayService;
import com.alibaba.repeater.console.start.controller.vo.PagerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link ReplayController}
 * <p>
 *
 * @author zhaowanxin
 */
@Controller
@RequestMapping("/replay")
public class ReplayController {

    @Resource
    private ReplayService replayService;

    @RequestMapping("detail.htm")
    public String detail(@ModelAttribute("requestParams") ReplayParams params, Model model) {
        RepeaterResult<ReplayBO> result = replayService.query(params);
        if (!result.isSuccess()) {
            return "/error/404";
        }
        model.addAttribute("replay", result.getData());
        model.addAttribute("record", result.getData().getRecord());
        return "replay/detail";
    }

    @RequestMapping("list.htm")
    public String list(@ModelAttribute("requestParams") ReplayParams params, Model model) {
        PageResult<ReplayListBO> result = replayService.list(params);
        PagerAdapter.transform0(result, model);
        return "replay/list";
    }

    @RequestMapping("execute.json")
    @ResponseBody
    public RepeaterResult<String> replay(@ModelAttribute("requestParams") ReplayParams params) {
        if (params != null) {
            params.setReplayType(Constants.REPLAY_TYPE_AUTO + "");
        }
        return replayService.replay(params);
    }
}
