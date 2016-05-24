<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "service"; %>
<%@include file="top.jsp" %>

<script src="/resources/js/appEdit.js"></script>
<script>
    $(function () {
        $("#from").change(function () {
            var val = $(this).val();
            var split = val.split(",");

            $("[name=image]").val(split[0]);
            $("[name=port]").val(split[1]);

            if (split[0].length > 0) {
                $("[name=image]").attr("readonly", true);
                $("[name=port]").attr("readonly", true);
            }else{
                $("[name=image]").attr("readonly", false);
                $("[name=port]").attr("readonly", false);
            }
        });

        $("#uploadFile").fileinput({
            showUpload: false
        });

        $("#env_add").click(function () {
            var template = $("#env_template");
            var clone = template.clone();
            clone.removeAttr("id");
            template.after(clone);
            clone.show();

            clone.find("[name=env_del]").click(function () {
                clone.remove();
            });
        });

        $("[name=env_del]").click(function(){
            $(this).parent().parent().remove();
        });

        $("#app-edit-form").validate({
            rules: {

            },
            submitHandler: function (form) {
                var envs = {};
                var envTemplates = $("[name=env_template]");
                envTemplates.each(function () {
                    var key = $(this).find("[name=env_key]").val().trim();
                    var value = $(this).find("[name=env_value]").val();
                    if (key.length > 0) {
                        envs[key] = value;
                    }
                });
                $("#env").val(JSON.stringify(envs));
                form.submit();
            }
        });

        $("#deleteAppButton").on("click", function () {
            $.ajax({
                url: "/api/resourcetype/${resourceType.id}",
                type: "DELETE",
                success: function () {
                    location.href = "/o/manage";
                },
                error: function (xhr, status, e) {
                    $.notify("cannot delete resourcetype : " + e, "error");
                }
            })
        });
    });


</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">${resourceType.name}</h1>
            </div>

            <form id="app-edit-form" action="/o/resourcetype/${resourceType.id}/edit" method="POST" enctype="multipart/form-data">
                <div class="row col-md-12">
                    <a href="/o/resourcetype/${resourceType.id}" class="btn btn-default">Cancel</a>
                    &nbsp;
                    <button type="submit" class="btn btn-primary outline">Save all changes</button>
                </div>

                <input type="hidden" name="liberty" value="${resourceType.liberty}">

                <div class="row col-md-12">
                    <h4 class="bottom-line">General Information</h4>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Catalog:</label>

                            <div class="col-md-9 col-sm-9">
                                <select name="catalog" class="form-control required">
                                    <option value="database" <c:if test="${resourceType.catalog == 'database'}">selected</c:if>>database</option>
                                    <option value="devops" <c:if test="${resourceType.catalog == 'devops'}">selected</c:if>>devops</option>
                                    <option value="network" <c:if test="${resourceType.catalog == 'network'}">selected</c:if>>network</option>
                                    <option value="analysis" <c:if test="${resourceType.catalog == 'analysis'}">selected</c:if>>analysis</option>
                                    <option value="business" <c:if test="${resourceType.catalog == 'business'}">selected</c:if>>business</option>
                                </select>
                            </div>
                        </div>

                        <input type="hidden" value="${resourceType.id}">

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Name:</label>

                            <div class="col-md-9 col-sm-9"><input type="text" name="name" class="form-control required"
                                                                  minlength="3" maxlength="40" value="${resourceType.name}"/></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Thumbnail:</label>

                            <div class="col-md-9 col-sm-9">
                                <input id="uploadFile" name="uploadFile" type="file" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Operating Plan</h4>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">From:</label>

                            <div class="col-md-9 col-sm-9">
                                <select id="from" class="form-control">
                                    <option value=",">Docker Hub / Docker Registry</option>
                                    <c:forEach var="image" items="${libertyImages}">
                                        <option value="${image.image},${image.port}">${image.id}:${image.tag}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Image:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="text" name="image" class="form-control col-150 pull-left required"
                                       minlength="3" maxlength="100" value="${resourceType.image}"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Port:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="number" name="port" class="form-control col-150 pull-left required" value="${resourceType.port}"/>
                            </div>
                        </div>
                    </div>

                    <%--Environment 값--%>
                    <input type="hidden" name="env" id="env">

                    <div class="col-md-12 form-horizontal">
                        <label class="col-md-3 col-sm-3 control-label">Environments:</label>

                        <div class="col-md-9 col-sm-9">
                            <div class="row" style="margin-bottom: 10px;display: none" id="env_template"
                                 name="env_template">
                                <div class="col-md-3 col-sm-3">
                                    <input type="text" name="env_key" class="form-control col-150 mright-10"
                                           placeholder="key">
                                </div>
                                <div class="col-md-8 col-sm-8">
                                    <input type="text" name="env_value" class="form-control"
                                           placeholder="value">
                                </div>
                                <div class="col-md-1 col-sm-1">
                                    <button type="button" name="env_del" class="btn btn-primary outline">Del</button>
                                </div>
                            </div>

                            <c:forEach var="env" items="${resourceType.envMap}">
                                <div class="row" style="margin-bottom: 10px;" name="env_template">
                                    <div class="col-md-3 col-sm-3">
                                        <input type="text" name="env_key" class="form-control col-150 mright-10"
                                               placeholder="key" value="${env.key}">
                                    </div>
                                    <div class="col-md-8 col-sm-8">
                                        <input type="text" name="env_value" class="form-control"
                                               placeholder="value" value="${env.value}">
                                    </div>
                                    <div class="col-md-1 col-sm-1">
                                        <button type="button" name="env_del" class="btn btn-primary outline">Del
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>

                            <button id="env_add" type="button" class="btn btn-primary outline mleft-10">Add</button>
                            <br><br>
                        </div>
                    </div>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Description:</label>

                            <div class="col-md-9 col-sm-9">
                                <textarea name="desc" class="form-control" rows="12">${resourceType.desc}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row col-md-12">
                    <hr>
                    <div>
                        <a href="/o/resourcetype/${resourceType.id}" class="btn btn-default">Cancel</a>
                        &nbsp;
                        <button type="submit" class="btn btn-primary outline">Save all changes</button>
                    </div>
                </div>
            </form>

            <div class="row col-md-12">
                <br>
                <br>

                <div class="box">
                    <div class="pull-right">
                        <button type="button" class="btn btn-lg btn-danger outline" data-toggle="modal"
                                data-target="#deleteModal"><i class="glyphicon glyphicon-trash"></i> Delete Resource Type
                        </button>
                    </div>
                    <h2>Delete Resource Type</h2>

                    <p>This will delete resouce type.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Are you sure?</h4>
            </div>
            <div class="modal-body">
                <p>This will delete service.</p>

                <p><strong class="text-danger">Delete Service "${resourceType.id}".</strong></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-danger" id="deleteAppButton">Yes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<%@include file="bottom.jsp" %>