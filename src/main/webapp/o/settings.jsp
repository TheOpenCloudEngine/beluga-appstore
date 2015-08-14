<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Organization Settings</h1>
            </div>

            <div>
                <%--<div class="pull-right">--%>
                    <%--<a href="#" class="btn btn-lg btn-primary outline">Edit</a>--%>
                <%--</div>--%>
                <h3>토탈소프트뱅크 (tsb)</h3>
                <p>Member Since 2015.8.7</p>
                <p><a href="#">5 Users</a></p>
            </div>

            <br>

            <div class="box" >
                <div class="pull-right">
                    <a href="#" class="btn btn-lg btn-danger outline">Delete Organization</a>
                </div>
                <h2>Delete Organization</h2>
                <p>This will permanently delete all users, apps and organization information.</p>
            </div>

        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>
