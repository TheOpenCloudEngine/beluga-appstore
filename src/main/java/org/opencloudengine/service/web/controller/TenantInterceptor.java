package org.opencloudengine.service.web.controller;

import org.opencloudengine.service.web.db.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TenantInterceptor extends HandlerInterceptorAdapter {

    @Value("#{systemProperties['bss.appId']}")
	private String appId;

    @Value("#{systemProperties['bss.domain']}")
	private String domain;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
	public boolean preHandle(HttpServletRequest request,
	                         HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession(true);

    	User user = (User) session.getAttribute("user");

		if (session.getAttribute("appId") == null)
			session.setAttribute("appId", appId);

		if (session.getAttribute("domain") == null)
			session.setAttribute("domain", domain);

		return super.preHandle(request, response, handler);
	}

}
