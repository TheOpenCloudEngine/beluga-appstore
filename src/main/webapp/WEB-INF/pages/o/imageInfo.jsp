<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "image"; %>
<%@include file="top.jsp" %>
<script>

</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">${libertyImage.id}:${libertyImage.tag}</h1>
            </div>

            <div class="row col-md-12">
                <a href="/o/image" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                &nbsp;<a href="${libertyImage.tag}/edit" class="btn btn-default">Edit</a>
            </div>

            <div class="row col-md-12">
                <h4 class="bottom-line">General Information</h4>

                <div class="col-md-12 form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">ID and Tag:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${libertyImage.id}:${libertyImage.tag}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">OS:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${libertyImage.os}</p></div>
                    </div>
                </div>
            </div>

            <div class="row col-md-12">
                <h4 class="bottom-line">Operating Plan</h4>

                <div class="col-md-12 form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Image:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${libertyImage.image}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Boot Command:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${libertyImage.cmd}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Expose Port:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${libertyImage.port}</p></div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-sm-3 control-label">Description:</label>

                        <div class="col-md-9 col-sm-9"><p class="form-control-static">${libertyImage.desc}</p></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>


<%@include file="bottom.jsp" %>