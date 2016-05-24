<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "service"; %>
<%@include file="top.jsp" %>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Manage Services</h1>
            </div>

            <div class="pull-right">
                <a href="resourcetype/new" class="btn btn-primary outline">New Service</a></td>
            </div>
            <h2>Services</h2>
            <c:if test="${not empty allResourceTypes}">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Catalog</th>
                        <th>Image</th>
                        <th>Port</th>
                        <th>Apply Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="resourceType" items="${allResourceTypes}">
                        <tr>
                            <td><a href="resourcetype/${resourceType.id}">${resourceType.id}</a></td>
                            <td><a href="resourcetype/${resourceType.id}">${resourceType.name}</a></td>
                            <td>${resourceType.catalog}</td>
                            <td>${resourceType.image}MB</td>
                            <td>${resourceType.port}</td>
                            <td>${resourceType.createDateDisplay}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty allResourceTypes}">
                <div class="well col-md-12 empty-apps">
                    <h3>No Service</h3>
                </div>
            </c:if>
            <br>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>