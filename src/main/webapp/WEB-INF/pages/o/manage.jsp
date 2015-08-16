<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Manage Apps</h1>
            </div>

            <h2>Overview</h2>
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-info">2</p>
                        <h4>Apps</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-warning">1</p>
                        <h4>Outer Apps</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-info">2.6</p>
                        <h4>CPUs</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-3">
                    <div class="stat-box">
                        <p class="text-warning">2400MB</p>
                        <h4>Memory</h4>
                    </div>
                </div>
            </div>

            <br>

            <div class="pull-right">
                <a href="appNew" class="btn btn-primary outline">New App</a></td>
            </div>
            <h2>Providing Apps</h2>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>ID</th>
                    <th>CPUs</th>
                    <th>Memory</th>
                    <th>Scale</th>
                    <th>Version</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><a href="appInfo">EDI</a></td>
                    <td><a href="appInfo">erp</a></td>
                    <td>0.3</td>
                    <td>700MB</td>
                    <td>2</td>
                    <td>2015.8.14 12:23:50</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                    <td><a href="appEdit" class="btn btn-primary outline">Edit</a></td>
                </tr>
                <tr>
                    <td><a href="appInfo">CASP</a></td>
                    <td><a href="appInfo">erp</a></td>
                    <td>0.1</td>
                    <td>500MB</td>
                    <td>3</td>
                    <td>2015.8.14 12:23:50</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                    <td><a href="appEdit" class="btn btn-primary outline">Edit</a></td>
                </tr>
                </tbody>
            </table>

            <br>

            <h2>Outer Apps</h2>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>ID</th>
                    <th>Provider</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>ERP</td>
                    <td>erp</td>
                    <td>ITMA</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                    <td><a href="#" class="btn btn-danger outline">Cancel</a></td>
                </tr>
                <tr>
                    <td>Good Solution</td>
                    <td>goods</td>
                    <td>ITMA</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                    <td><a href="#" class="btn btn-danger outline">Cancel</a></td>
                </tr>
                <tr>
                    <td>Some Solution</td>
                    <td>somes</td>
                    <td>ITMA</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                    <td><a href="#" class="btn btn-danger outline">Cancel</a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>