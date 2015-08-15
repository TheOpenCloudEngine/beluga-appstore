<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">EDI</h1>
            </div>

            <div class="row col-md-12">
                <a href="manage.jsp" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                &nbsp;<a href="appEdit.jsp" class="btn btn-default">Edit</a>
                &nbsp;<a href="#" class="btn btn-primary outline">Apply</a>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">General Information</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Name:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">EDI</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Description :</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">8월 14, 2015 2:54:28 오후 org.springframework.web.servlet.handler.SimpleUrlHandlerMapping registerHandler
                            정보: Mapped URL path [/resources/**] onto handler 'org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0'
                            8월 14, 2015 2:54:28 오후 org.springframework.web.context.support.XmlWebApplicationContext prepareRefresh</p></div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">Operating Plan</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">App file:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">edi.war (1.2 MB) <br><i class="file-date">2015-07-08 14:11:35</i></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">CPUs:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">0.1</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Memory:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">500MB</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Scale:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">2</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <h4 class="bottom-line">Resource Plan</h4>
                <div class="col-md-12 form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Database:</label>
                        <div class="col-md-9 col-sm-9">
                            <ul class="no-padding-left">
                                <li><p class="form-control-static">DB01 ( MySql 5.6.26 ) - Separate DB</p></li>
                                <li><p class="form-control-static">DB02 ( Oracle 11g ) - Separate DB</p></li>
                            </ul>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">FTP: </label>
                        <div class="col-md-9 col-sm-9">
                            <ul class="no-padding-left">
                                <li><p class="form-control-static">FTP01 ( Ftp 3.2 ) - Shared by User</p></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">Auto Scaling Plan</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Enable Auto Scale-out:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">Yes</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">CPU Usage higher than:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">70%</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">During:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">3 Min</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Add Scale:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">2</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Enable Auto Scale-in:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">Yes</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">CPU Usage lower than:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">30%</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">During:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">3 Min</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>