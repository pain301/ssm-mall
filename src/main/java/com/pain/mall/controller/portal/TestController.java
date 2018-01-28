package com.pain.mall.controller.portal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/10/8.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/demo")
    @ResponseBody
    public String demo(String str) {
        logger.info("demo");
        logger.warn("demo");
        logger.error("demo");
        return "demo value: " + str;
    }
}
