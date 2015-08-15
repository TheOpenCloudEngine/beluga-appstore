package org.opencloudengine.service.web.controller;

import org.opencloudengine.service.web.http.IllegalOperationException;
import org.opencloudengine.service.web.http.ResponseHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public class AbstractController {
	
	protected static Logger logger = LoggerFactory.getLogger(AbstractController.class);
	
	protected static final String HTTPCLIENT_ID = "httpclient";
	protected static final String USERNAME_ID = "_username";
	/*
	 * exception페이지로 이동한다.
	 * */
	@ExceptionHandler(Throwable.class)
	public ModelAndView handleAllException(Exception ex) {
 
		logger.error("",ex);
		
		ModelAndView model = null;
		
		if(ex instanceof IllegalOperationException) {
			model = new ModelAndView("illegalOperationError");
			model.addObject("message", ex.getMessage());
		} else if(ex instanceof InvalidAuthenticationException) {
			logger.debug("not authorized!!");
			model = new ModelAndView("error");
			model.addObject("message", "Not Authorized User");
		} else {
			logger.debug("normal error!!");
			model = new ModelAndView("error");
			model.addObject("message", ex.getMessage());
		}
		
		return model;
	}
	
	protected ResponseHttpClient.PostMethod httpPost(HttpSession session, String uri) {
		ResponseHttpClient httpClient = (ResponseHttpClient) session.getAttribute(HTTPCLIENT_ID);
		return httpClient.httpPost(uri);
	}
	
	protected ResponseHttpClient.GetMethod httpGet(HttpSession session, String uri) {
		ResponseHttpClient httpClient = (ResponseHttpClient) session.getAttribute(HTTPCLIENT_ID);
		return httpClient.httpGet(uri);
	}
}
