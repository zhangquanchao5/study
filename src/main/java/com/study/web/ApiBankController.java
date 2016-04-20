package com.study.web;

import com.study.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by huichao on 2016/4/20.
 */
@Controller
@RequestMapping("/api")
public class ApiBankController {

    @Autowired
    private IBankService iBankService;

}
