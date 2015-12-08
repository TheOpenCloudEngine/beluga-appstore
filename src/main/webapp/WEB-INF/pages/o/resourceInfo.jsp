<%@ page import="java.util.Map" %>
<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.App" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "manage"; %>
<%@include file="top.jsp" %>
<script>
    function deployResource() {
        $.ajax({
            url:"/api/resources/${resource.id}/deploy",
            type: "POST",
            success:function() {
                alert("[${app.id}] Starting resource succeed.");
                location.reload(true);
            },
            error:function(xhr) {
                alert("Starting service fails : " + xhr.responseText);
                $("#deployButton").button("reset");
                updateResourceStatus();
            }
        });
    }

    $(function(){
        $("#deployButton").on("click", function(){
            $(this).button('loading');
            deployResource();
        });
        updateResourceStatus();

    });

    function updateResourceStatus() {
        $.ajax({
            url: "/api/resources/${app.id}/status",
            type: "GET",
            dataType: "json",
            success: function(data) {
                $("#appStatus").text(data.status);
                if(data.status == "OK") {
                    $("#appStatus").removeClass("text-danger");
                    $("#appStatus").addClass("text-success");
                } else {
                    $("#appStatus").addClass("text-danger");
                    $("#appStatus").removeClass("text-success");
                }
                $("#appElapsed").text(data.elapsed);
                $("#appScale").text(data.scale);
            },
            error: function(xhr) {
                $("#appStatus").addClass("text-danger");
                $("#appStatus").removeClass("text-success");
                alert("Resource status update error : " + xhr.responseText);
            }
        });
    }
</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">${resource.name}</h1>
            </div>

            <div class="row col-md-12">
                <a href="/o/manage" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                &nbsp;<a href="${resource.id}/edit" class="btn btn-default">Edit</a>
            </div>

            <div class="row col-md-12">
                <br>
                <div class="box" >
                    <div class="pull-right">
                        <a href="javascript:void(0)" id="deployButton" class="btn btn-lg btn-primary outline" data-loading-text="Starting..">Start Service</a>
                        &nbsp; <a href="javascript:updateResourceStatus()" class="btn btn-lg btn-default"><i class="glyphicon glyphicon-refresh"></i></a>
                    </div>
                    <h2>Running Status</h2>
                    <br>
                    <div class="row">
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-danger" id="appStatus"></p>
                                <h4>Status</h4>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-warning" id="appElapsed"></p>
                                <h4>Elapsed</h4>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-primary">${resource.cpus}</p>
                                <h4>Cpus</h4>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-3">
                            <div class="stat-box">
                                <p class="text-primary">${resource.memory}</p>
                                <h4>Memory</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">General Information</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">ID:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resource.id}</p></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Name:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resource.name}</p></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Resource Name:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resource.image}</p></div>
                    </div>

                </div>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">Operating Plan</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">CPUs:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">${resource.cpus}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Memory:</label>
                        <div class="col-md-9 col-sm-9">
                            <p class="form-control-static">${resource.memory}MB</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>