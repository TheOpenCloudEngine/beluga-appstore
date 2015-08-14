<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">My Profile</h1>
            </div>

            <div>
                <%--<div class="pull-right">--%>
                    <%--<a href="#" class="btn btn-primary outline">Edit</a>--%>
                <%--</div>--%>
                <%--<i class="label label-default">User</i>--%>
                <p>songaal@gmail.com <i class="label label-primary">Administrator</i></p>
                <p>fastcat.co</p>
                <p>Member Since 2015.8.7</p>
            </div>

            <br>

            <div class="box" >
                <div class="pull-right">
                    <a href="#" class="btn btn-danger outline">Delete Account</a>
                </div>
                <h2>Delete Account</h2>
                <p>This will permanently delete all account information.</p>
            </div>

        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>
