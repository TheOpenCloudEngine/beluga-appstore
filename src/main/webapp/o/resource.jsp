<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Resource</h1>
            </div>

            <h2>Usage</h2>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>App Name</th>
                    <th>App ID</th>
                    <th>CPUs</th>
                    <th>Memory</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>EDI</td>
                    <td>edi</td>
                    <td>0.6</td>
                    <td>600MB</td>
                </tr>
                <tr>
                    <td>CASP</td>
                    <td>casp</td>
                    <td>2</td>
                    <td>1800MB</td>
                </tr>
                <tr>
                    <td colspan="2"><strong>TOTAL</strong></td>
                    <td><strong>2.6</strong></td>
                    <td><strong>2400MB</strong></td>
                </tr>
                </tbody>
            </table>

            <br>

            <div class="pull-right">
                <a href="#" class="btn btn-primary outline">Request Database</a></td>
            </div>
            <h2>Database</h2>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Vendor</th>
                    <th>Version</th>
                    <th>IP Address</th>
                    <th>User</th>
                    <th>Password</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>MySql</td>
                    <td>5.6.12</td>
                    <td>51.16.123.4</td>
                    <td>tsb</td>
                    <td>tsb123:)</td>
                    <td><a href="#" class="btn btn-danger outline">Cancel</a></td>
                </tr>
                </tbody>
            </table>

            <br>

            <div class="pull-right">
                <a href="#" class="btn btn-primary outline">Request Storage</a></td>
            </div>
            <h2>Storage</h2>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Protocol</th>
                    <th>Version</th>
                    <th>IP Address</th>
                    <th>User</th>
                    <th>Password</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>FTP</td>
                    <td>3.2</td>
                    <td>51.16.65.10</td>
                    <td>tsb</td>
                    <td>tsb123:)</td>
                    <td><a href="#" class="btn btn-danger outline">Cancel</a></td>
                </tr>
                </tbody>
            </table>


        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>