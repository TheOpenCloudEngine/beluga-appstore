<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.Resource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "manage"; %>
<%@include file="top.jsp" %>

<script>
$(function(){
    $("#app-new-form").validate({
        onkeyup: function(element) {
            var element_id = $(element).attr('id');
            if (this.settings.rules[element_id] && this.settings.rules[element_id].onkeyup != false) {
                $.validator.defaults.onkeyup.apply(this, arguments);
            }
        },
        rules: {
            id: {
                idExists: true,
                lowercase : true,
                onkeyup: false
            }
        }
    });

    $.validator.addMethod("idExists", function(value, element) {
        var ret = true;
        $.ajax({
            url : "/api/apps/" + value,
            async: false,
            type : "HEAD",
            success : function(response) {
                ret = false;
            },
            error : function() {
                ret = true;
            }
        });
        return ret;
    }, "This app id already exists.");

    $.validator.addMethod("lowercase", function(value) {
        // Marathon documentation에서 가져온 정규식.
        // https://mesosphere.github.io/marathon/docs/rest-api.html#post-v2-apps
        return value.match(/^(([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])\\.)*([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])$/);
    }, 'You must use only lowercase letters, dot and dash in app id');
})

</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Create New Resource</h1>
            </div>

            <form id="app-new-form" action="/o/resources" method="POST">
                <div class="row col-md-12">
                    <a href="/o/manage" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                    &nbsp;<button type="submit" class="btn btn-primary outline">Save all changes</button>
                </div>
                <div class="row col-md-12">
                    <h4 class="bottom-line">General Information</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">ID:</label>
                            <div class="col-md-9 col-sm-9">
                                <input type="text" name="id" id="appId" class="form-control col-150 pull-left required" minlength="3"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Name:</label>
                            <div class="col-md-9 col-sm-9"><input type="text" name="name" id="appName" class="form-control required" minlength="3"/></div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Operating Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Resource Type:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="type" class="form-control required">
                                    <option value="">:: Select ::</option>
                                    <c:forEach var="resource" items="${allResources}">
                                        <option value="${resource.id}">${resource.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPUs:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="cpus" class="form-control col-100 required">
                                    <option value="0.1">0.1</option>
                                    <option value="0.2">0.2</option>
                                    <option value="0.3">0.3</option>
                                    <option value="0.4">0.4</option>
                                    <option value="0.5">0.5</option>
                                    <option value="0.6">0.6</option>
                                    <option value="0.7">0.7</option>
                                    <option value="0.8">0.8</option>
                                    <option value="0.9">0.9</option>
                                    <option value="1.0">1.0</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Memory:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="memory" class="form-control col-100 required">
                                    <option value="50">50MB</option>
                                    <option value="100">100MB</option>
                                    <option value="200">200MB</option>
                                    <option value="300">300MB</option>
                                    <option value="400">400MB</option>
                                    <option value="500">500MB</option>
                                    <option value="600">600MB</option>
                                    <option value="700">700MB</option>
                                    <option value="800">800MB</option>
                                    <option value="900">900MB</option>
                                    <option value="1000">1GB</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row col-md-12">
                    <hr>
                    <div>
                        <button type="submit" class="btn btn-primary outline">Save all changes</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>