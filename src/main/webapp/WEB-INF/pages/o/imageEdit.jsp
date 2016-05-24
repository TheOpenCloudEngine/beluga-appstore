<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "image"; %>
<%@include file="top.jsp" %>

<script>
    $(function () {
        var originalOS = '${libertyImage.os}';

        $("#launchContinue").click(function () {
            $("#editorfraim").remove();
            $("#loading").show();
            $("#loadingtext").text("Deploying Docker Terminal");
            $.ajax({
                url: "/api/image/${libertyImage.id}/${libertyImage.tag}/boot",
                type: "post",
                dataType: 'json',
                data: JSON.stringify({}),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $("#app-edit-form").find("[name=os]").val(originalOS);

                    $("#container").val(data.container);
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
                    $.notify("cannot launch docker : " + e, "error");
                },
                complete: function(){
                    $("#loading").hide();
                }
            });
        });

        $("#launchNew").click(function () {
            $("#editorfraim").remove();
            $("#loading").show();
            $("#loadingtext").text("Deploying Docker Terminal");

            var os = $("#os").val();
            $.ajax({
                url: "/api/image/${libertyImage.id}/${libertyImage.tag}/new",
                type: "post",
                dataType: 'json',
                data: JSON.stringify({os:os}),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    var osname;
                    $("#os").find("option").each(function(){
                        if($(this).prop("selected")){
                            osname = $(this).text();
                        }
                    });
                    $("#app-edit-form").find("[name=os]").val(osname);

                    $("#container").val(data.container);
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
                    $.notify("cannot launch docker : " + e, "error");
                },
                complete: function(){
                    $("#loading").hide();
                }
            });
        });

        $("#app-edit-form").validate({
            rules: {},
            submitHandler: function (form) {
                if($("#container").val().length < 1){
                    $.notify("Need Launch Editor", {autoHide: false, className: 'error'});
                    return false;
                }

                $("#editorfraim").remove();
                $("#loading").show();
                $("#loadingtext").text("Committing Docker Container..");
                form.submit();
            }
        });

        $("#deleteAppButton").on("click", function () {
            $.ajax({
                url: "/api/image/${libertyImage.id}/${libertyImage.tag}",
                type: "DELETE",
                success: function () {
                    location.href = "/o/image";
                },
                error: function (xhr, status, e) {
                    $.notify("cannot delete image : " + e, "error");
                }
            })
        });
    });


</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">${libertyImage.id}:${libertyImage.tag}</h1>
            </div>

            <form id="app-edit-form" action="/o/image/${libertyImage.id}/${libertyImage.tag}/edit" method="POST">
                <div class="row col-md-12">
                    <a href="/o/image/${libertyImage.id}/${libertyImage.tag}" class="btn btn-default">Cancel</a>
                    &nbsp;
                    <button type="submit" class="btn btn-primary outline">Save all changes</button>
                </div>

                <input type="hidden" name="os" value="${libertyImage.os}">
                <input type="hidden" name="image" value="${libertyImage.image}">
                <input type="hidden" name="container" id="container">

                <div class="row col-md-12">
                    <h4 class="bottom-line">General Information</h4>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">OS:</label>

                            <div class="col-md-9 col-sm-9"><p class="form-control-static">${libertyImage.os}</p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Launch:</label>

                            <div class="col-md-9 col-sm-9 row">
                                <div class="row">
                                    <div class="col-md-4 col-sm-4">
                                        <button id="launchNew" type="button" class="btn btn-primary outline mleft-10">
                                            Launch Editor With New OS
                                        </button>
                                    </div>
                                    <div class="col-md-8 col-sm-8">
                                        <select id="os" class="form-control">
                                            <c:forEach var="provided" items="${providedList}">
                                                <option value="${provided.image}">${provided.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <br>

                                <div class="row">
                                    <div class="col-md-12 col-sm-12">
                                        <button id="launchContinue" type="button"
                                                class="btn btn-primary outline mleft-10">Launch
                                            Editor From Previous Docker Image, ${libertyImage.image}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Edit Image</h4>

                    <div id="editor" class="col-md-12 row">

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
                                       minlength="3" maxlength="300" value="${libertyImage.cmd}"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Expose Port:</label>

                            <div class="col-md-9 col-sm-9">
                                <input type="number" name="port" class="form-control col-150 pull-left required"
                                       value="${libertyImage.port}"/>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Description:</label>

                            <div class="col-md-9 col-sm-9">
                                <textarea name="desc" class="form-control" rows="12">${libertyImage.desc}</textarea>
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

            <div class="row col-md-12">
                <br>
                <br>

                <div class="box">
                    <div class="pull-right">
                        <button type="button" class="btn btn-lg btn-danger outline" data-toggle="modal"
                                data-target="#deleteModal"><i class="glyphicon glyphicon-trash"></i> Delete Liberty
                            Image
                        </button>
                    </div>
                    <h2>Delete Liberty Image</h2>

                    <p>This will delete liberty image.</p>
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
                <p>This will delete liberty image.</p>

                <p><strong class="text-danger">Delete Liberty Image "${libertyImage.id}:${libertyImage.tag}".</strong>
                </p>
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