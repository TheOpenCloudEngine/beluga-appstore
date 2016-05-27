<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "image"; %>
<%@include file="top.jsp" %>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Manage Liberty Docker</h1>
            </div>

            <div class="pull-right">
                <a href="image/new" class="btn btn-primary outline">New Docker</a></td>
            </div>
            <h2>Docker</h2>
            <c:if test="${not empty images}">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tag</th>
                        <th>Docker</th>
                        <th>Port</th>
                        <th>Apply Date</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="image" items="${images}">
                            <tr>
                                <td><a href="image/${image.id}/${image.tag}">${image.id}</a></td>
                                <td><a href="image/${image.id}/${image.tag}">${image.tag}</a></td>
                                <td>${image.image}</td>
                                <td>${image.port}</td>
                                <td>${image.createDateDisplay}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty images}">
                <div class="well col-md-12 empty-apps">
                    <h3>No Docker</h3>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>