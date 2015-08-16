<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<script>
    function showUsers() {
        $('#user-list').modal();
    }
</script>
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
                <p><a href="javascript:showUsers()">5 Users</a></p>
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

<div class="modal" id="user-list" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Users</h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Join Time</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>user@tsb.co.kr</td>
                        <td>2015-07-15 17:56:20</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>user2@tsb.co.kr</td>
                        <td>2015-08-13 10:11:45</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>
