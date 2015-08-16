package org.opencloudengine.serviceportal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by swsong on 2015. 8. 13..
 */
public class ClusterController extends AbstractController {
    @RequestMapping("/cluster")
    public ModelAndView index() throws Exception {

        //TODO clusterId 는 세션에 넣어둔다.


        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:main/cluster.html");
        return mav;
    }
}
