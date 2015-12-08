<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.Resources" %>
<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.App" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "manage"; %>
<%@include file="top.jsp" %>

<script src="/resources/js/appEdit.js"></script>
<script>
$(function() {

    $("#app-edit-form").validate({
        onkeyup: function(element) {
            var element_id = $(element).attr('id');
            if (this.settings.rules[element_id] && this.settings.rules[element_id].onkeyup != false) {
                $.validator.defaults.onkeyup.apply(this, arguments);
            }
        },
        rules: {
            appFile1: {
                required: function(element){
                    return $("#fileName1").val().length == 0;
                }
            },
            context1: {
                required: true
            },
            appFile2 : {
                required: function(element){
                    return $("#context2").val().length > 0 && $("#fileName2").val().length == 0;
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

    $("#deleteAppButton").on("click", function(){
        $.ajax({
            url: "/api/apps/${app.id}",
            type: "DELETE",
            success: function() {
                location.href = "/o/manage";
            },
            error: function(xhr, status, e) {
                alert("cannot delete app : " + e);
            }
        })
    });
});


</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">${app.name}</h1>
            </div>

            <form id="app-edit-form" action="/o/apps/${app.id}/edit" method="POST">
                <div class="row col-md-12">
                    <a href="/o/apps/${app.id}" class="btn btn-default">Cancel</a>
                    &nbsp;<button type="submit" class="btn btn-primary outline">Save all changes</button>
                </div>
                <div class="row col-md-12">
                    <input type="hidden" name="" value="" />
                    <h4 class="bottom-line">General Information</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Domain:</label>
                            <div class="col-md-9 col-sm-9"><p class="form-control-static">${app.id}.${domain}</p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Name:</label>
                            <div class="col-md-9 col-sm-9"><input type="text" name="name" class="form-control required" minlength="3" value="${app.name}" /></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Description :</label>
                            <div class="col-md-9 col-sm-9"><textarea name="description" class="form-control" rows="3">${app.description}</textarea></div>
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
                                <input type="text" id="context1" name="context1" class="form-control col-150 pull-left mleft-10" placeholder="/context" value="${app.appContext}">
                                <input type="file" id="appFile1" name="appFile1" class="form-control-static required col-100 pleft-10 simple-file-btn"/>
                                <div class="progress" id="progressbar1" style="display:none">
                                    <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                                        <span class="sr-only">45% Complete</span>
                                    </div>
                                </div>
                                <p class="form-control-static app-file-detail1 <c:if test="${empty app.appFile}">maybe-hide</c:if>"><span id="fileInfo1">${app.appFile} ( ${app.appFileLengthDisplay} )</span>
                                    <br><span class="file-date" id="fileDate1">${app.appFileDate}</span>
                                </p>
                                <!--// file1-->
                                <p/>
                                <!--file2-->
                                <p class="form-control-static pull-left">Context</p>
                                <input type="text" id="context2" name="context2" class="form-control col-150 pull-left mleft-10" placeholder="/context" value="${app.appContext2}">
                                <input type="file" id="appFile2" name="appFile2" class="form-control-static required col-100 pleft-10 simple-file-btn"/>
                                <div class="progress" id="progressbar2" style="display:none">
                                    <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                                        <span class="sr-only">45% Complete</span>
                                    </div>
                                </div>
                                <p class="form-control-static app-file-detail2 <c:if test="${empty app.appFile2}">maybe-hide</c:if>"><span id="fileInfo2">${app.appFile2} ( ${app.appFileLengthDisplay2} )</span>
                                    <br><span class="file-date" id="fileDate2">${app.appFileDate2}</span>
                                </p>
                                <!--// file2-->
                                <!--hidden info-->
                                <input type="hidden" id="fileName1" name="fileName1" value="${app.appFile}"/>
                                <input type="hidden" name="filePath1" value="${app.appFilePath}"/>
                                <input type="hidden" name="fileLength1" value="${app.appFileLength}"/>
                                <input type="hidden" name="fileDate1" value="${app.appFileDate}"/>
                                <input type="hidden" name="fileChecksum1" value="${app.appFileChecksum}"/>
                                <input type="hidden" id="fileName2" name="fileName2" value="${app.appFile2}"/>
                                <input type="hidden" name="filePath2" value="${app.appFilePath2}"/>
                                <input type="hidden" name="fileLength2" value="${app.appFileLength2}"/>
                                <input type="hidden" name="fileDate2" value="${app.appFileDate2}"/>
                                <input type="hidden" name="fileChecksum2" value="${app.appFileChecksum2}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Environment:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="environment" class="form-control required">
                                    <option value="">:: Select ::</option>
                                    <option value="java7_tomcat7" <c:if test="${app.environment == 'java7_tomcat7'}">selected</c:if>>java7_tomcat7</option>
                                    <option value="java7_wildfly8.2" <c:if test="${app.environment == 'java7_wildfly8.2'}">selected</c:if>>java7_wildfly8.2</option>
                                    <option value="php5_apache2" <c:if test="${app.environment == 'php5_apache2'}">selected</c:if>>php5_apache2</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPUs:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="cpus" class="form-control col-100 required">
                                    <option value="0.1" <c:if test="${(app.cpus * 10).intValue() == 1}">selected</c:if>>0.1</option>
                                    <option value="0.2" <c:if test="${(app.cpus * 10).intValue() == 2}">selected</c:if>>0.2</option>
                                    <option value="0.3" <c:if test="${(app.cpus * 10).intValue() == 3}">selected</c:if>>0.3</option>
                                    <option value="0.4" <c:if test="${(app.cpus * 10).intValue() == 4}">selected</c:if>>0.4</option>
                                    <option value="0.5" <c:if test="${(app.cpus * 10).intValue() == 5}">selected</c:if>>0.5</option>
                                    <option value="0.6" <c:if test="${(app.cpus * 10).intValue() == 6}">selected</c:if>>0.6</option>
                                    <option value="0.7" <c:if test="${(app.cpus * 10).intValue() == 7}">selected</c:if>>0.7</option>
                                    <option value="0.8" <c:if test="${(app.cpus * 10).intValue() == 8}">selected</c:if>>0.8</option>
                                    <option value="0.9" <c:if test="${(app.cpus * 10).intValue() == 9}">selected</c:if>>0.9</option>
                                    <option value="1.0" <c:if test="${(app.cpus * 10).intValue() == 10}">selected</c:if>>1.0</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Memory:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="memory" class="form-control col-100 required">
                                    <option value="300" <c:if test="${app.memory == 300}">selected</c:if>>300MB</option>
                                    <option value="400" <c:if test="${app.memory == 400}">selected</c:if>>400MB</option>
                                    <option value="500" <c:if test="${app.memory == 500}">selected</c:if>>500MB</option>
                                    <option value="600" <c:if test="${app.memory == 600}">selected</c:if>>600MB</option>
                                    <option value="700" <c:if test="${app.memory == 700}">selected</c:if>>700MB</option>
                                    <option value="800" <c:if test="${app.memory == 800}">selected</c:if>>800MB</option>
                                    <option value="900" <c:if test="${app.memory == 900}">selected</c:if>>900MB</option>
                                    <option value="1000" <c:if test="${app.memory == 1000}">selected</c:if>>1GB</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Scale:</label>
                            <div class="col-md-9 col-sm-9">
                                <select name="scale"class="form-control col-100 required">
                                    <option value="1" <c:if test="${app.scale == 1}">selected</c:if>>1</option>
                                    <option value="2" <c:if test="${app.scale == 2}">selected</c:if>>2</option>
                                    <option value="3" <c:if test="${app.scale == 3}">selected</c:if>>3</option>
                                    <option value="4" <c:if test="${app.scale == 4}">selected</c:if>>4</option>
                                    <option value="5" <c:if test="${app.scale == 5}">selected</c:if>>5</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <%
                    App app = (App) request.getAttribute("app");
                    List<String> appResourceList = app.getResourceList();

                    Map<String, String[]> resourceInfoMap = (Map<String, String[]>) request.getAttribute("resourceInfoMap");
                %>
                <div class="row col-md-12">
                    <h4 class="bottom-line">Resource Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Database:</label>
                            <%
                                String[] resourceList = new String[]{"mysql5", "postgresql9", "oraclexe11g"};
                                for (int i = 0; i < resourceList.length; i++) {
                                    String isChecked = "";
                                    String resourceKey = resourceList[i];
                                    String ip = "";
                                    String port = "";
                                    Resources.Resource resource = Resources.get(resourceKey);
                                    if(appResourceList.contains(resourceKey)) {
                                        isChecked = "checked";
                                        if (resourceInfoMap != null) {
                                            String[] ipPort = resourceInfoMap.get(resourceKey);
                                            if (ipPort != null) {
                                                ip = ipPort[0];
                                                port = ipPort[1];
                                            }
                                        }
                                    }
                            %>
                            <div class="<%=(i > 0) ? "col-md-offset-3 col-sm-offset-3" : "" %> col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="resource_<%=resourceKey %>" value="true" <%=isChecked %> > <%=resource.getName() %>
                                    </label>
                                </div>
                                <div class="sub-options">
                                    $<%=resource.getIPPropertyKey()%>=<%=ip %>
                                    <br>$<%=resource.getPortPropertyKey()%>=<%=port%>
                                </div>
                            </div>
                            <%
                                }
                            %>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">NoSQL:</label>
                            <%
                                resourceList = new String[]{"mongodb3", "redis3"};
                                for (int i = 0; i < resourceList.length; i++) {
                                    String isChecked = "";
                                    String resourceKey = resourceList[i];
                                    String ip = "";
                                    String port = "";
                                    Resources.Resource resource = Resources.get(resourceKey);
                                    if(appResourceList.contains(resourceKey)) {
                                        isChecked = "checked";
                                        if (resourceInfoMap != null) {
                                            String[] ipPort = resourceInfoMap.get(resourceKey);
                                            if (ipPort != null) {
                                                ip = ipPort[0];
                                                port = ipPort[1];
                                            }
                                        }
                                    }
                            %>
                            <div class="<%=(i > 0) ? "col-md-offset-3 col-sm-offset-3" : "" %> col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="resource_<%=resourceKey %>" value="true" <%=isChecked %> > <%=resource.getName() %>
                                    </label>
                                </div>
                                <div class="sub-options">
                                    $<%=resource.getIPPropertyKey()%>=<%=ip %>
                                    <br>$<%=resource.getPortPropertyKey()%>=<%=port%>
                                </div>
                            </div>
                            <%
                                }
                            %>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">FTP:</label>
                            <%
                                resourceList = new String[]{"sftp", "ftp"};
                                for (int i = 0; i < resourceList.length; i++) {
                                    String isChecked = "";
                                    String resourceKey = resourceList[i];
                                    String ip = "";
                                    String port = "";
                                    Resources.Resource resource = Resources.get(resourceKey);
                                    if(appResourceList.contains(resourceKey)) {
                                        isChecked = "checked";
                                        if (resourceInfoMap != null) {
                                            String[] ipPort = resourceInfoMap.get(resourceKey);
                                            if (ipPort != null) {
                                                ip = ipPort[0];
                                                port = ipPort[1];
                                            }
                                        }
                                    }
                            %>
                            <div class="<%=(i > 0) ? "col-md-offset-3 col-sm-offset-3" : "" %> col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="resource_<%=resourceKey %>" value="true" <%=isChecked %> > <%=resource.getName() %>
                                    </label>
                                </div>
                                <div class="sub-options">
                                    $<%=resource.getIPPropertyKey()%>=<%=ip %>
                                    <br>$<%=resource.getPortPropertyKey()%>=<%=port%>
                                </div>
                            </div>
                            <%
                                }
                            %>
                        </div>
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
                                        <input type="checkbox" name="autoScaleOutUse"> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Scale-Out When:</label>
                            <div class="col-md-9 col-sm-9">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Server Load:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="loadHigher">
                                    <option value="50">&ge; 50%</option>
                                    <option value="60">&ge; 60%</option>
                                    <option value="70">&ge; 70%</option>
                                    <option value="80">&ge; 80%</option>
                                    <option value="90">&ge; 90%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">During:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="higherDuring">
                                    <option>1 Min</option>
                                    <option>2 Min</option>
                                    <option>3 Min</option>
                                    <option>4 Min</option>
                                    <option>5 Min</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Scale-In When:</label>
                            <div class="col-md-9 col-sm-9">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Server Load: </label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="loadLower">
                                    <option value="10">&lt; 10%</option>
                                    <option value="20">&lt; 20%</option>
                                    <option value="30">&lt; 30%</option>
                                    <option value="40">&lt; 40%</option>
                                    <option value="50">&lt; 50%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">During:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="lowerDuring">
                                    <option value="1">1 Min</option>
                                    <option value="2">2 Min</option>
                                    <option value="3">3 Min</option>
                                    <option value="4">4 Min</option>
                                    <option value="5">5 Min</option>
                                    <option value="6">6 Min</option>
                                    <option value="7">7 Min</option>
                                    <option value="8">8 Min</option>
                                    <option value="9">9 Min</option>
                                    <option value="10">10 Min</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row col-md-12">
                    <hr>
                    <div>
                        <a href="/o/apps/${app.id}" class="btn btn-default">Cancel</a>
                        &nbsp;
                        <button type="submit" class="btn btn-primary outline">Save all changes</button>
                    </div>
                </div>
            </form>

            <div class="row col-md-12">
                <br>
                <br>
                <div class="box" >
                    <div class="pull-right">
                        <button type="button" class="btn btn-lg btn-danger outline" data-toggle="modal" data-target="#deleteModal"><i class="glyphicon glyphicon-trash"></i> Delete App</button>
                    </div>
                    <h2>Delete App</h2>
                    <p>This will terminate running app and permanently delete all app information.</p>
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
                <p>This will terminate running app and permanently delete all app information.</p>
                <p><strong class="text-danger">Delete app "${app.id}".</strong></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-danger" id="deleteAppButton">Yes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%@include file="bottom.jsp" %>