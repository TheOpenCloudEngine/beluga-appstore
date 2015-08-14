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
                &nbsp;<a href="appInfo.jsp" class="btn btn-primary outline">View</a>
            </div>
            <div class="row col-md-12">
                <form>
                    <input type="hidden" name="" value="" />
                    <h4 class="bottom-line">General Information</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">Name:</label>
                            <div class="col-md-9"><input type="text" class="form-control" value="EDI" /></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">Description :</label>
                            <div class="col-md-9"><textarea class="form-control" rows="3"></textarea></div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn btn-primary outline">Save</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="row col-md-12">
                <form>
                    <h4 class="bottom-line">Operating Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">App file:</label>
                            <div class="col-md-9">
                                <p class="form-control-static">edi.war (1.2 MB) <br><i class="file-date">2015-07-08 14:11:35</i></p>
                                <input type="file" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">CPUs:</label>
                            <div class="col-md-9">
                                <select class="form-control">
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
                            <label class="col-md-3 control-label">Memory:</label>
                            <div class="col-md-9">
                                <select class="form-control">
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
                            <label class="col-md-3 control-label">Scale:</label>
                            <div class="col-md-9">
                                <select class="form-control">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn btn-primary outline">Save</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="row col-md-12">
                <form>
                    <h4 class="bottom-line">Auto Scaling Plan</h4>
                    <div class="col-md-12 form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">Enable Auto Scale-out:</label>
                            <div class="col-md-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" disabled> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">CPU Usage higher than:</label>
                            <div class="col-md-9">
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
                            <label class="col-md-3 control-label">During:</label>
                            <div class="col-md-9">
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
                            <label class="col-md-3 control-label">Add Scale:</label>
                            <div class="col-md-9">
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
                            <label class="col-md-3 control-label">Enable Auto Scale-in:</label>
                            <div class="col-md-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" disabled> Yes
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">CPU Usage lower than:</label>
                            <div class="col-md-9">
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
                            <label class="col-md-3 control-label">During:</label>
                            <div class="col-md-9">
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
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn btn-primary outline">Save</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="row col-md-12">
                <div class="pull-right">
                    <a href="#" class="btn btn-danger outline"><i class="glyphicon glyphicon-trash"></i> Delete App</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>