<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.Resource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "image"; %>
<%@include file="top.jsp" %>

<script>
    $(function () {
        $("#launch").click(function () {
            $("#editorfraim").remove();
            $("#loading").show();
            $("#loadingtext").text("Deploying Docker Terminal");
            var os = $("#os").val();
            $.ajax({
                url: "/api/image/boot",
                type: "post",
                dataType: 'json',
                data: JSON.stringify({os: os}),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $("#container").val(data.container);
                    $("#editorfraim").remove();
                    var iframe = $("<iframe></iframe>");
                    iframe.css({
                        width: "100%",
                        height: "600px",
                        border: "none"
                    });
                    iframe.attr("id", "editorfraim");
                    iframe.attr("src", "http://" + data.host + ":" + data.port);

                    setTimeout(function(){
                        $("#editor").append(iframe);
                    },2000);
                },
                error: function (xhr, status, e) {
                    $.notify("cannot delete app : " + e, "error");
                },
                complete: function(){
                    $("#loading").hide();
                }
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
                },
                tag: {
                    idExists: true,
                    lowercase: true,
                    onkeyup: false
                }
            },
            submitHandler: function (form) {
                if($("#container").val().length < 1){
                    $.notify("Need Launch Editor", {autoHide: false, className: 'error'});
                    return false;
                }

                var os;
                $("#os").find("option").each(function(){
                    if($(this).prop("selected")){
                        os = $(this).text();
                    }
                });
                $("#app-new-form").find("[name=os]").val(os);
                $("#editorfraim").remove();
                $("#loading").show();
                $("#loadingtext").text("Committing Docker Container..");
                form.submit();
            }
        });

        $.validator.addMethod("idExists", function (value, element) {
            var id = $("#app-new-form").find("[name=id]").val();
            var tag = $("#app-new-form").find("[name=tag]").val();
            var ret = true;
            $.ajax({
                url: "/api/image/" + id + "/" + tag,
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
        }, "This image id:tag already exists.");

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
                <h1 id="tables">Create Liberty Docker</h1>
            </div>

            <form id="app-new-form" action="/o/image" method="POST" enctype="multipart/form-data">
                <div class="row col-md-12">
                    <a href="/o/manage" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                    &nbsp;
                    <button type="submit" class="btn btn-primary outline">Save all changes</button>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">General Information</h4>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">OS:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="hidden" name="os">
                                <select id="os" class="form-control">
                                    <c:forEach var="provided" items="${providedList}">
                                        <option value="${provided.image}">${provided.name}</option>
                                    </c:forEach>
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
                            <label class="col-md-3 col-sm-3 control-label">Tag:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="text" name="tag" class="form-control col-150 pull-left required"
                                       minlength="1" maxlength="15"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Launch:</label>

                            <div class="col-md-9 col-sm-9">
                                <button id="launch" type="button" class="btn btn-primary outline mleft-10">Launch
                                    Editor
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Edit Docker</h4>

                    <input type="hidden" name="container" id="container">

                    <div id="editor" class="col-md-12">

                    </div>
                    <div class="col-md-12 form-horizontal" id="loading" style="display: none">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Loading: </label>
                            <div class="col-md-9 col-sm-9 row">
                                <img style="margin-top: 10px" src="/resources/img/ajax-loader.gif"/>
                                <p id="loadingtext"></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Operating Plan</h4>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Boot Command:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="text" name="cmd" class="form-control col-150 pull-left required"
                                       minlength="3" maxlength="300"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Expose Port:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="number" name="port" class="form-control col-150 pull-left required"/>
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