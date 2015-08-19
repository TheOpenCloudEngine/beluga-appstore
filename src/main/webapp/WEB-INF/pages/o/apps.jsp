<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">
            <div class="page-header">
                <h1 id="tables">Apps</h1>
            </div>
        </div>
        <div class="col-md-12">
            <div class="app-grid-list">
                <h2>Providing Apps</h2>
                <c:if test="${not empty appList}">
                    <c:forEach var="app" items="${appList}">
                        <div class="app-entry-wrapper col-md-4 col-sm-6 col-xs-6">
                            <div class="app-entry col-md-4 col-sm-6 col-xs-6">
                                <div class="app-thumbnail">
                                    <%--<image src="resources/img/default-thumbnail.jpg"></image>--%>
                                </div>
                                <div class="app-info">
                                    <div class="app-title">${app.name}</div>
                                    <div class="app-provider">${app.orgName}</div>
                                    <div class="app-date">${app.applyDate}&nbsp;</div>
                                    <div class="app-button" align="right">
                                        <a href="#${app.id}" class="btn btn-primary outline">Launch App</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="col-md-12">
            <div class="app-grid-list">
                <h2>Outer Apps</h2>
                <c:if test="${not empty outerAppList}">
                    <c:forEach var="app" items="${outerAppList}">
                        <div class="app-entry-wrapper col-md-4 col-sm-6 col-xs-6">
                            <div class="app-entry">
                                <div class="app-thumbnail">
                                    <%--<image src="resources/img/default-thumbnail.jpg"></image>--%>
                                </div>
                                <div class="app-info">
                                    <div class="app-title">${app.name}</div>
                                    <div class="app-provider">${app.orgName}</div>
                                    <div class="app-date">${app.applyDate}&nbsp;</div>
                                    <div class="app-button" align="right">
                                        <a href="#${app.id}" class="btn btn-primary outline">Launch App</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>
