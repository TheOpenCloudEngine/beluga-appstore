<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "manage"; %>
<%@include file="top.jsp" %>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Manage Apps</h1>
            </div>

            <h2>Usage Overview</h2>
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-info">${appSize}</p>
                        <h4>Apps</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-warning">${outerAppSize}</p>
                        <h4>Outer Apps</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-info">${totalCpus}</p>
                        <h4>CPUs</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-warning">${totalMemory}</p>
                        <h4>Memory</h4>
                    </div>
                </div>
            </div>

            <br>

            <div class="pull-right">
                <a href="apps/new" class="btn btn-primary outline">New App</a></td>
            </div>
            <h2>Apps</h2>
            <c:if test="${not empty appList}">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>ID</th>
                        <th>CPUs</th>
                        <th>Memory</th>
                        <th>Scale</th>
                        <th>Apply Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="app" items="${appList}">
                            <tr>
                                <td><a href="apps/${app.id}/${app.version}">${app.name}</a></td>
                                <td><a href="apps/${app.id}/${app.version}">${app.id}</a></td>
                                <td>${app.cpus}</td>
                                <td>${app.memory}MB</td>
                                <td>${app.scale}</td>
                                <td>${app.updateDateDisplay}</td>
                                <td><span class="glyphicon ${app.appStatus eq "OK" ? "glyphicon-ok-sign running-status" : "glyphicon-remove-sign stop-status"}"></span></td>
                                <td><a href="http://${app.id}.${domain}" target="_pop_${app.id}" class="btn btn-primary outline">Launch</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty appList}">
                <div class="well col-md-12 empty-apps">
                    <h3>No apps</h3>
                </div>
            </c:if>
            <br>

            <div class="pull-right">
                <a href="catalog" class="btn btn-primary outline">New Resources</a></td>
            </div>
            <h2>Resources</h2>
            <c:if test="${not empty resources}">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>CPUs</th>
                        <th>Memory</th>
                        <th>Apply Date</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="resource" items="${resources}">
                        <tr>
                            <td><a href="resources/${resource.id}">${resource.id}</a></td>
                            <td><a href="resources/${resource.id}">${resource.resourceName}</a></td>
                            <td>${resource.cpus}</td>
                            <td>${resource.memory}MB</td>
                            <td>${resource.createDateDisplay}</td>
                            <td><span class="glyphicon ${resource.appStatus eq "OK" ? "glyphicon-ok-sign running-status" : "glyphicon-remove-sign stop-status"}"></span></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty resources}">
                <div class="well col-md-12 empty-apps">
                    <h3>No resources</h3>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>