<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.Resource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "manage"; %>
<%@include file="top.jsp" %>

<script src="/resources/js/appEdit.js"></script>
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
            },

            appFile1: {
                required: true
            },
            context1: {
                required: true
            },
            appFile2 : {
                required: function(element){
                    return $("#context2").val().length > 0;
                }
            },
            context2 : {
                required: function(element){
                    return $("#appFile2").val().length > 0;
                }
            }
        },
        messages: {
            appFile1: "Upload file is required.",
            appFile2: "Upload file is required."
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
                <h1 id="tables">Create New App</h1>
            </div>

            <form id="app-new-form" action="/o/apps" method="POST">
                <div class="row col-md-12">
                    <a href="/o/manage" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                    &nbsp;<button type="submit" class="btn btn-primary outline">Save all changes</button>
                </div>
                <div class="row col-md-12">
                    <h4 class="bottom-line">General Information</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Host:</label>
                            <div class="col-md-9 col-sm-9"><input type="text" name="id" id="appId" class="form-control col-150 pull-left required" minlength="3"/>
                                <p class="form-control-static">&nbsp;.${domain}</p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Name:</label>
                            <div class="col-md-9 col-sm-9"><input type="text" name="name" id="appName" class="form-control required" minlength="3"/></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Description :</label>
                            <div class="col-md-9 col-sm-9"><textarea name="description" class="form-control" rows="3"></textarea></div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Operating Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">App file:</label>
                            <div class="col-md-9 col-sm-9">
                                <!--file1-->
                                <p class="form-control-static pull-left">Context</p>
                                <input type="text" id="context1" name="context1" class="form-control col-150 pull-left mleft-10" placeholder="/context" value="/">
                                <input type="file" id="appFile1" name="appFile1" class="form-control-static required col-100 pleft-10 simple-file-btn"/>
                                <div class="progress" id="progressbar1" style="display:none">
                                    <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                                        <span class="sr-only">100% Complete</span>
                                    </div>
                                </div>
                                <p class="form-control-static app-file-detail1 maybe-hide"><span id="fileInfo1"></span>
                                    <br><span class="file-date" id="fileDate1"></span>
                                </p>
                                <!--// file1-->
                                <p/>
                                <!--file2-->
                                <p class="form-control-static pull-left">Context</p>
                                <input type="text" id="context2" name="context2" class="form-control col-150 pull-left mleft-10" placeholder="/context">
                                <input type="file" id="appFile2" name="appFile2" class="form-control-static required col-100 pleft-10 simple-file-btn"/>
                                <div class="progress" id="progressbar2" style="display:none">
                                    <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                                        <span class="sr-only">100% Complete</span>
                                    </div>
                                </div>
                                <p class="form-control-static app-file-detail2 maybe-hide"><span id="fileInfo2"></span>
                                    <br><span class="file-date" id="fileDate2"></span>
                                </p>
                                <!--hidden info-->
                                <input type="hidden" name="fileName1"/>
                                <input type="hidden" name="filePath1"/>
                                <input type="hidden" name="fileLength1"/>
                                <input type="hidden" name="fileDate1"/>
                                <input type="hidden" name="fileChecksum1"/>
                                <input type="hidden" name="fileName2"/>
                                <input type="hidden" name="filePath2"/>
                                <input type="hidden" name="fileLength2"/>
                                <input type="hidden" name="fileDate2"/>
                                <input type="hidden" name="fileChecksum2"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Environment:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="environment" class="form-control required">
                                    <option value="">:: Select ::</option>
                                    <option value="java7_tomcat7">java7_tomcat7</option>
                                    <option value="java7_wildfly8.2">java7_wildfly8.2</option>
                                    <option value="php5_apache2">php5_apache2</option>
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
                                    <%--<option value="50">50MB</option>--%>
                                    <%--<option value="100">100MB</option>--%>
                                    <%--<option value="200">200MB</option>--%>
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
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Scale:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="scale"class="form-control col-100 required">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Resource Plan</h4>
                    <div class="col-md-12 form-horizontal compact">
                        <c:forEach var="resource" items="${resources}" varStatus="status">
                            <label class="col-md-3 col-sm-3 control-label">${resource.resourceName}:</label>
                            <div class="col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="res_${status.index}" value="${resource.id}"> ${resource.name} ( ${resource.id} )
                                    </label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Auto Scaling Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Enable Auto Scale:</label>
                            <div class="col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="autoScaleUse"> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Scale-Out When:</label>
                            <div class="col-md-9 col-sm-9">
                                <div style="float:left">
                                    Work Load
                                    <select class="form-control display-inline col-100" name="scaleOutLoad">
                                        <option value="50">&ge; 50%</option>
                                        <option value="60">&ge; 60%</option>
                                        <option value="70">&ge; 70%</option>
                                        <option value="80">&ge; 80%</option>
                                        <option value="90">&ge; 90%</option>
                                        <option value="100">&ge; 100%</option>
                                        <option value="110">&ge; 110%</option>
                                        <option value="120">&ge; 120%</option>
                                        <option value="130">&ge; 130%</option>
                                        <option value="140">&ge; 140%</option>
                                        <option value="150">&ge; 150%</option>
                                        <option value="200">&ge; 200%</option>
                                        <option value="250">&ge; 250%</option>
                                        <option value="300">&ge; 300%</option>
                                    </select>
                                </div>
                                <div>
                                    &nbsp;&nbsp;for
                                    <select class="form-control display-inline col-100" name="scaleOutTimeInMin">
                                        <option value="1">&ge; 1 Min</option>
                                        <option value="2">&ge; 2 Min</option>
                                        <option value="3">&ge; 3 Min</option>
                                        <option value="4">&ge; 4 Min</option>
                                        <option value="5">&ge; 5 Min</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Scale-In When:</label>
                            <div class="col-md-9 col-sm-9">
                                <div style="float:left">
                                    Work Load
                                    <select class="form-control display-inline col-100" name="scaleInLoad">
                                        <option value="10">&lt; 10%</option>
                                        <option value="20">&lt; 20%</option>
                                        <option value="30">&lt; 30%</option>
                                        <option value="40">&lt; 40%</option>
                                        <option value="50">&lt; 50%</option>
                                        <option value="60">&lt; 60%</option>
                                        <option value="70">&lt; 70%</option>
                                        <option value="80">&lt; 80%</option>
                                        <option value="90">&lt; 90%</option>
                                        <option value="100">&lt; 100%</option>
                                    </select>
                                </div>
                                <div>
                                    &nbsp;&nbsp;for
                                    <select class="form-control display-inline col-100" name="scaleInTimeInMin">
                                        <option value="1">&ge; 1 Min</option>
                                        <option value="2">&ge; 2 Min</option>
                                        <option value="3">&ge; 3 Min</option>
                                        <option value="4">&ge; 4 Min</option>
                                        <option value="5">&ge; 5 Min</option>
                                        <option value="6">&ge; 6 Min</option>
                                        <option value="7">&ge; 7 Min</option>
                                        <option value="8">&ge; 8 Min</option>
                                        <option value="9">&ge; 9 Min</option>
                                        <option value="10">&ge; 10 Min</option>
                                    </select>
                                </div>
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