<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "service"; %>
<%@include file="top.jsp" %>
<script>
    function deleteResourceType() {
        $.ajax({
            url: "/api/resourcetype/${resourceType.id}",
            type: "DELETE",
            success: function () {
                location.href = "/o/manage";
            },
            error: function (xhr, status, e) {
                $.notify("cannot delete resource type : " + e, {autoHide: false, className: 'error'});
                updateResourceStatus();
            }
        })
    }

    $(function () {
        $("#deleteButton").on("click", function () {
            $(this).button('loading');
            deleteResourceType();
        });

        var filetype = '${resourceType.filetype}';
        if(!filetype || filetype.length < 1){
            $('#file').append('<img src="/resources/assets/img/main/img2.jpg">')
        }else{
            $('#file').append('<img src="/api/resourcetype/${resourceType.id}/image">')
        }
    });
</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">${resourceType.name}</h1>
            </div>

            <div class="row col-md-12">
                <a href="/o/service" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                &nbsp;<a href="${resourceType.id}/edit" class="btn btn-default">Edit</a>
            </div>

            <div class="row col-md-12">
                <h4 class="bottom-line">General Information</h4>

                <div class="col-md-12 form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Catalog:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resourceType.catalog}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">ID:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resourceType.id}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Name:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resourceType.name}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Thumbnail:</label>

                        <div class="col-md-9 col-sm-9" id="file"></div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <h4 class="bottom-line">Operating Plan</h4>

                <div class="col-md-12 form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Image:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resourceType.image}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Port:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resourceType.port}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Environments:</label>

                        <div class="col-md-9 col-sm-9">
                            <c:forEach var="env" items="${resourceType.envMap}">
                                <p class="form-control-static">${env.key}: ${env.value}</p>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Description:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${resourceType.desc}</p></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>


<%@include file="bottom.jsp" %>