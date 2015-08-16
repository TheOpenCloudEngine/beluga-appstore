<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@include file="top.jsp" %>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">EDI</h1>
            </div>

            <div class="row col-md-12">
                <a href="manage.jsp" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> List</a>
                &nbsp;<a href="appInfo.jsp" class="btn btn-primary outline">Save all changes</a>
            </div>
            <form>
                <div class="row col-md-12">
                    <input type="hidden" name="" value="" />
                    <h4 class="bottom-line">General Information</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Name:</label>
                            <div class="col-md-9 col-sm-9"><input type="text" class="form-control" value="EDI" /></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Description :</label>
                            <div class="col-md-9 col-sm-9"><textarea class="form-control" rows="3"></textarea></div>
                        </div>
                    </div>
                </div>

                <div class="row col-md-12">
                    <h4 class="bottom-line">Operating Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">App file:</label>
                            <div class="col-md-9 col-sm-9">
                                <p class="form-control-static">edi.war (1.2 MB) <br><i class="file-date">2015-07-08 14:11:35</i></p>
                                <input type="file" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPUs:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control col-100">
                                    <option>0.1</option>
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
                                <select class="form-control col-100">
                                    <option>50MB</option>
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
                                <select class="form-control col-100">
                                    <option>1</option>
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
                                        <input type="checkbox" checked> DB01 ( MySql 5.6.26 )
                                    </label>
                                </div>
                                <div class="sub-options">
                                    <label class="radio-inline">
                                        <input type="radio" name="db1IsolationOptions" value="separateDB" checked> Separate DB
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="db1IsolationOptions" value="SharedDB" disabled> Shared
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-offset-3 col-sm-offset-3 col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" disabled> DB02 ( Oracle 11g )
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
                                        <input type="checkbox" disabled> DB03 ( Postgres 9.4.4 )
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
                                        <input type="checkbox"> FTP01 ( Ftp 3.2 )
                                    </label>
                                </div>
                                <div class="sub-options">
                                    <label class="radio-inline">
                                        <input type="radio" name="ftp1IsolationOptions" value="private"> Private
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="ftp1IsolationOptions" value="sharedByUser" checked> Shared
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
                                        <input type="checkbox" disabled> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPU Usage higher than:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100">
                                    <option>50%</option>
                                    <option>60%</option>
                                    <option>70%</option>
                                    <option>80%</option>
                                    <option>90%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">During:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100">
                                    <option>1 Min</option>
                                    <option>2 Min</option>
                                    <option>3 Min</option>
                                    <option>4 Min</option>
                                    <option>5 Min</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Add Scale:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                    <option>5</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">Enable Auto Scale-in:</label>
                            <div class="col-md-9 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" disabled> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">CPU Usage lower than:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100">
                                    <option>10%</option>
                                    <option>20%</option>
                                    <option>30%</option>
                                    <option>40%</option>
                                    <option>50%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-3 control-label">During:</label>
                            <div class="col-md-9 col-sm-9">
                                <select class="form-control display-inline col-100">
                                    <option>1 Min</option>
                                    <option>2 Min</option>
                                    <option>3 Min</option>
                                    <option>4 Min</option>
                                    <option>5 Min</option>
                                    <option>6 Min</option>
                                    <option>7 Min</option>
                                    <option>8 Min</option>
                                    <option>9 Min</option>
                                    <option>10 Min</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <div class="row col-md-12">
                <hr>
                <div>
                    <a href="appInfo.jsp" class="btn btn-primary outline">Save all changes</a>
                </div>
            </div>

            <div class="row col-md-12">
                <br>
                <br>
                <div class="box" >
                    <div class="pull-right">
                        <a href="#" class="btn btn-lg btn-danger outline"><i class="glyphicon glyphicon-trash"></i> Delete App</a>
                    </div>
                    <h2>Delete App</h2>
                    <p>This will termination running app and permanently delete all app information.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>