package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.http.IllegalOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class AbstractController {
	
	protected static Logger logger = LoggerFactory.getLogger(AbstractController.class);
	
	protected static final String HTTPCLIENT_ID = "httpclient";
	protected static final String USERNAME_ID = "_username";

	/*
	 * exception페이지로 이동한다.
	 * */
	@ExceptionHandler(Throwable.class)
	public ModelAndView handleAllException(Exception ex) {
 
		logger.error("controller exception",ex);
		
		ModelAndView model = null;
		
		if(ex instanceof IllegalOperationException) {
            logger.error("illegal operation error : {}", ex.getMessage());
			model = new ModelAndView("error");
			model.addObject("message", ex.getMessage());
		} else if(ex instanceof InvalidAuthenticationException) {
			logger.error("not authorized!! : {}", ex.getMessage());
			model = new ModelAndView("error");
			model.addObject("message", "Not Authorized User");
        } else {
            logger.error("normal error!! : {}", ex.getMessage());
			model = new ModelAndView("error");
			model.addObject("message", ex.getMessage());
		}
		
		return model;
	}
	
//	protected ResponseHttpClient.PostMethod httpPost(HttpSession session, String uri) {
//		ResponseHttpClient httpClient = (ResponseHttpClient) session.getAttribute(HTTPCLIENT_ID);
//		return httpClient.httpPost(uri);
//	}
//
//	protected ResponseHttpClient.GetMethod httpGet(HttpSession session, String uri) {
//		ResponseHttpClient httpClient = (ResponseHttpClient) session.getAttribute(HTTPCLIENT_ID);
//		return httpClient.httpGet(uri);
//	}
}
