package org.opencloudengine.console.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by swsong on 2015. 5. 11..
 */
public class AuthMainInterceptor extends HandlerInterceptorAdapter {
	protected static Logger logger = LoggerFactory.getLogger(AuthMainInterceptor.class);
}
