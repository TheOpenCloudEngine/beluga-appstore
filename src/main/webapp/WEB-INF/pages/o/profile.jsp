<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "profile"; %>
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
                <p>${user.id} <i class="label label-primary">${userType}</i></p>
                <p>${orgName}</p>
                <p>Member Since ${joinDate}</p>
            </div>

            <br>

            <div class="box" >
                <div class="pull-right">
                    <a href="#${user.id}" class="btn btn-lg btn-danger outline">Delete Account</a>
                </div>
                <h2>Delete Account</h2>
                <p>This will permanently delete all account information.</p>
            </div>

        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>
