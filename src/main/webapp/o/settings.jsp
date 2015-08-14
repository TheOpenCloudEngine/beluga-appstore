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
                <div class="pull-right">
                    <a href="#" class="btn btn-lg btn-primary outline">Edit</a>
                </div>
                <h3>토탈소프트뱅크</h3>
                <p>tsb.co.kr</p>
                <p>Since 2015.8.7</p>
            </div>

            <br>

            <div class="row">
                <div class="col-md-4">
                    <div class="stat-box">
                        <p class="text-primary">13</p>
                        <h4>Users</h4>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stat-box">
                        <p class="text-info">2</p>
                        <h4>Apps</h4>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stat-box">
                        <p class="text-warning">1</p>
                        <h4>Outer Apps</h4>
                    </div>
                </div>
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
