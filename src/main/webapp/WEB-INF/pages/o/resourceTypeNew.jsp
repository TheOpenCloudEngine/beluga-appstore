<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.Resource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "service"; %>
<%@include file="top.jsp" %>

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

        $("#app-new-form").validate({
            onkeyup: function (element) {
                var element_id = $(element).attr('id');
                if (this.settings.rules[element_id] && this.settings.rules[element_id].onkeyup != false) {
                    $.validator.defaults.onkeyup.apply(this, arguments);
                }
            },
            rules: {
                id: {
                    idExists: true,
                    lowercase: true,
                    onkeyup: false
                }
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

        $.validator.addMethod("idExists", function (value, element) {
            var ret = true;
            $.ajax({
                url: "/api/resourcetype/" + value,
                async: false,
                type: "HEAD",
                success: function (response) {
                    ret = false;
                },
                error: function () {
                    ret = true;
                }
            });
            return ret;
        }, "This service id already exists.");

        $.validator.addMethod("lowercase", function (value) {
            // Marathon documentation에서 가져온 정규식.
            // https://mesosphere.github.io/marathon/docs/rest-api.html#post-v2-apps
            return value.match(/^(([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])\\.)*([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])$/);
        }, 'You must use only lowercase letters and numbers. Dot and dash are also allowed, but cannot be used as first or last letter.');
    })

</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Create New Service</h1>
            </div>

            <form id="app-new-form" action="/o/resourcetype" method="POST" enctype="multipart/form-data">
                <div class="row col-md-12">
                    <a href="/o/service" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                    &nbsp;
                    <button type="submit" class="btn btn-primary outline">Save all changes</button>
                </div>

                <input type="hidden" name="liberty" value="N">

                <div class="row col-md-12">
                    <h4 class="bottom-line">General Information</h4>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Catalog:</label>

                            <div class="col-md-9 col-sm-9">
                                <select name="catalog" class="form-control required">
                                    <option value="database" selected>database</option>
                                    <option value="devops">devops</option>
                                    <option value="network">network</option>
                                    <option value="analysis">analysis</option>
                                    <option value="business">business</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">ID:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="text" name="id" class="form-control col-150 pull-left required"
                                       minlength="3" maxlength="15"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Name:</label>

                            <div class="col-md-9 col-sm-9"><input type="text" name="name" class="form-control required"
                                                                  minlength="3" maxlength="40"/></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Thumbnail:</label>

                            <div class="col-md-9 col-sm-9">
                                <input id="uploadFile" name="uploadFile" type="file" class="form-control required">
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
                            <label class="col-md-3 col-sm-3 control-label">Docker:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="text" name="image" class="form-control col-150 pull-left required"
                                       minlength="3" maxlength="100"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Port:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="number" name="port" class="form-control col-150 pull-left required"/>
                            </div>
                        </div>
                    </div>

                    <%--Environment 값--%>
                    <input type="hidden" name="env" id="env">

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
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
                                        <button type="button" name="env_del" class="btn btn-primary outline">Del
                                        </button>
                                    </div>
                                </div>

                                <button id="env_add" type="button" class="btn btn-primary outline mleft-10">Add</button>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Description:</label>

                            <div class="col-md-9 col-sm-9">
                                <textarea name="desc" class="form-control" rows="12">

                                </textarea>
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