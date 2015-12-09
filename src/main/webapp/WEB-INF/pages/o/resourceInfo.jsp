<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                alert("[${resource.id}] Starting resource succeed.");
                location.reload(true);
            },
            error:function(xhr) {
                alert("Starting service fails : " + xhr.responseText);
                $("#deployButton").button("reset");
                updateResourceStatus();
            }
        });
    }

    function deleteResource() {
        $.ajax({
            url: "/api/resources/${resource.id}",
            type: "DELETE",
            success: function() {
                location.href = "/o/manage";
            },
            error: function(xhr, status, e) {
                alert("cannot delete resource : " + e);
                updateResourceStatus();
            }
        })
    }

    $(function(){
        $("#deployButton").on("click", function(){
            $(this).button('loading');
            deployResource();
        });
        $("#deleteButton").on("click", function(){
            $(this).button('loading');
            deleteResource();
        });
        updateResourceStatus();

    });

    function updateResourceStatus() {
        $.ajax({
            url: "/api/apps/${resource.id}/status",
            type: "GET",
            dataType: "json",
            success: function(data) {
                $("#appStatus").text(data.status);
                if(data.status == "-") {
                    //시작하지 않음.start가능.
                    $("#deployButton").removeAttr('disabled');
                } else {
                    if (data.status == "OK") {
                        $("#appStatus").removeClass("text-danger");
                        $("#appStatus").addClass("text-success");
                    } else {
                        $("#appStatus").addClass("text-danger");
                        $("#appStatus").removeClass("text-success");
                    }
                    //delete 가능.
                    $("#deployButton").attr('disabled', true);
                }
                $("#appElapsed").text(data.elapsed);
                $("#appScale").text(data.scale);
            },
            error: function(xhr) {
                $("#appStatus").addClass("text-danger");
                $("#appStatus").removeClass("text-success");
                $("#deployButton").removeAttr('disabled');
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
                                <p class="text-primary">${resource.memoryDisplay}</p>
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
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resource.resourceName}</p></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Created:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resource.createDateDisplay}</p></div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <div><h4 class="bottom-line">Environment Variables</h4></div>
                <div class="col-md-12 form-horizontal compact">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Host:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">$${fn:toUpperCase(resource.id)}_HOST</p></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Port:</label>
                        <div class="col-md-9 col-sm-9"><p class="form-control-static">$${fn:toUpperCase(resource.id)}_PORT</p></div>
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

            <div class="row col-md-12">
                <br>
                <br>
                <div class="box" >
                    <div class="pull-right">
                        <button type="button" class="btn btn-lg btn-danger outline" data-toggle="modal" data-target="#deleteModal"><i class="glyphicon glyphicon-trash"></i> Delete Resource</button>
                    </div>
                    <h2>Delete Resource</h2>
                    <p>This will terminate running resource and permanently delete all resource information.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Are you sure?</h4>
            </div>
            <div class="modal-body">
                <p>This will terminate running resource and permanently delete all resource information.</p>
                <p><strong class="text-danger">Delete resource "${resource.id}".</strong></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-danger" id="deleteButton">Yes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%@include file="bottom.jsp" %>