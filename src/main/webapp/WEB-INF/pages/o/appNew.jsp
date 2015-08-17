<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Create New App</h1>
            </div>

            <div class="row col-md-12">
                <a href="manage" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                &nbsp;<a href="appInfo" class="btn btn-primary outline">Save all changes</a>
            </div>
            <form action="appNew" method="POST">
                <div class="row col-md-12">
                    <input type="hidden" name="" value="" />
                    <h4 class="bottom-line">General Information</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">ID:</label>
                            <div class="col-md-9 col-sm-9"><input type="text" name="id" class="form-control col-150" /></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Name:</label>
                            <div class="col-md-9 col-sm-9"><input type="text" name="name" class="form-control" /></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Description :</label>
                            <div class="col-md-9 col-sm-9"><textarea name="description" class="form-control" rows="3"></textarea></div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Operating Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">App file:</label>
                            <div class="col-md-9 col-sm-9">
                                <%--<p class="form-control-static">edi.war (1.2 MB) <br><i class="file-date">2015-07-08 14:11:35</i></p>--%>
                                <input type="file"  name="appFile" class="form-control-static"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPUs:</label>
                            <div class="col-md-9 col-sm-9">
                                <select  name="cpus" class="form-control col-100">
                                    <option value="">0.1</option>
                                    <option>0.2</option>
                                    <option>0.3</option>
                                    <option>0.4</option>
                                    <option>0.5</option>
                                    <option>0.6</option>
                                    <option>0.7</option>
                                    <option>0.8</option>
                                    <option>0.9</option>
                                    <option>1.0</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Memory:</label>
                            <div class="col-md-9 col-sm-9">
                                <select  name="memory" class="form-control col-100">
                                    <option value="50">50MB</option>
                                    <option>100MB</option>
                                    <option>200MB</option>
                                    <option>300MB</option>
                                    <option>400MB</option>
                                    <option>500MB</option>
                                    <option>600MB</option>
                                    <option>700MB</option>
                                    <option>800MB</option>
                                    <option>900MB</option>
                                    <option>1GB</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Scale:</label>
                            <div class="col-md-9 col-sm-9">
                                <select  name="scale"class="form-control col-100">
                                    <option value="">1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Resource Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Database:</label>
                            <div class="col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="db01"> DB01 ( MySql 5.6.26 )
                                    </label>
                                </div>
                                <div class="sub-options">
                                    <label class="radio-inline">
                                        <input type="radio" name="db1IsolationOptions" value="separateDB"> Separate DB
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="db1IsolationOptions" value="SharedDB" disabled> Shared
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-offset-3 col-sm-offset-3 col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox"  name="db02" disabled> DB02 ( Oracle 11g )
                                    </label>
                                </div>
                                <div class="sub-options">
                                    <label class="radio-inline">
                                        <input type="radio" name="db2IsolationOptions" value="separateDB"> Separate DB
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="db2IsolationOptions" value="separateSchema"> Separate Schema
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="db2IsolationOptions" value="sharedSchema"> Shared
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-offset-3 col-sm-offset-3 col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="db03" disabled> DB03 ( Postgres 9.4.4 )
                                    </label>
                                </div>
                                <div class="sub-options">
                                    <label class="radio-inline">
                                        <input type="radio" name="db3IsolationOptions" value="separateDB"> Separate DB
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="db3IsolationOptions" value="separateSchema"> Separate Schema
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="db3IsolationOptions" value="sharedSchema"> Shared
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">FTP:</label>
                            <div class="col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="ftp01"> FTP01 ( Ftp 3.2 )
                                    </label>
                                </div>
                                <div class="sub-options">
                                    <label class="radio-inline">
                                        <input type="radio" name="ftp1IsolationOptions" value="private"> Private
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="ftp1IsolationOptions" value="sharedByUser"> Shared
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Auto Scaling Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Enable Auto Scale-out:</label>
                            <div class="col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="autoScaleOutUse" disabled> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPU Usage higher than:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="cpuHigh">
                                    <option value="50">50%</option>
                                    <option value="60">60%</option>
                                    <option value="70">70%</option>
                                    <option value="80">80%</option>
                                    <option value="90">90%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">During:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="cpuHighDuring">
                                    <option value="1">1 Min</option>
                                    <option value="2">2 Min</option>
                                    <option value="3">3 Min</option>
                                    <option value="4">4 Min</option>
                                    <option value="5">5 Min</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Add Scale:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="autoOutScale">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Enable Auto Scale-in:</label>
                            <div class="col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="autoScaleInUse" disabled> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPU Usage lower than:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="cpuLow">
                                    <option value="10">10%</option>
                                    <option value="20">20%</option>
                                    <option value="30">30%</option>
                                    <option value="40">40%</option>
                                    <option value="50">50%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">During:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100" name="cpuLowDuring">
                                    <option value="1">1 Min</option>
                                    <option value="2">2 Min</option>
                                    <option value="3">3 Min</option>
                                    <option value="4">4 Min</option>
                                    <option value="5">5 Min</option>
                                    <option value="6">6 Min</option>
                                    <option value="7">7 Min</option>
                                    <option value="8">8 Min</option>
                                    <option value="9">9 Min</option>
                                    <option value="10">10 Min</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row col-md-12">
                    <hr>
                    <div>
                        <button type="submit" class="btn btn-primary outline">Save all changes</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>