<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "apps"; %>
<%@include file="top.jsp" %>
<script>
    function applyApp() {
        $.ajax({
            url:"/api/apps/${app.id}/apply",
            type: "POST",
            success:function() {
                alert("[${app.id}] app apply started.");
            },
            error:function(xhr, status, e) {
                alert("Apply app fails : [" + status + "] " + e);
            }
        });
    }

</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">${app.name}</h1>
            </div>

            <div class="row col-md-12">
                <a href="/o/manage" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                &nbsp;<a href="${app.id}/edit" class="btn btn-default">Edit</a>
            </div>

            <div class="row col-md-12">
                <br>
                <div class="box" >
                    <div class="pull-right">
                        <a href="javascript:applyApp()" class="btn btn-lg btn-primary outline">Apply App</a>
                        &nbsp; <a href="${app.id}" class="btn btn-lg btn-default"><i class="glyphicon glyphicon-refresh"></i></a>
                    </div>
                    <h2>Running Status</h2>
                    <br>
                    <div class="row">
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-success">OK</p>
                                <h4>Status</h4>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-warning">${elapsed}</p>
                                <h4>Elapsed</h4>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-primary">${app.scale}</p>
                                <h4>Scale</h4>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-success">ON</p>
                                <h4>Auto Scale</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">General Information</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Name:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${app.name}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Description :</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${app.description}</p></div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">Operating Plan</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">App file:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">${app.appFile} ( ${app.appFileLengthDisplay} ) <br><i class="file-date">${app.appFileDate}</i></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">CPUs:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">${app.cpus}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Memory:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">${app.memoryDisplay}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Scale:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">${app.scale}</p>
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
                                <li><p class="form-control-static">FTP01 ( Ftp 3.2 ) - Private</p></li>
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