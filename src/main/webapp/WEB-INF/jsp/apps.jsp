<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Garuda</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Favicon -->
    <link rel="shortcut icon" href="/resources/favicon.ico">
    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="../../resources/css/main.css" media="screen">
    <link rel="stylesheet" href="../../resources/css/enhance.css" media="screen">
    <script src="../../resources/jquery/jquery-1.11.3.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
    <script>
        $('#myModal').on('shown.bs.modal', function () {
            $('#myInput').focus()
        })

    </script>
</head>
<body>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a href="http://garuda.com/" class="navbar-brand">Garuda</a>
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
            <ul class="nav navbar-nav">
                <li>
                    <a href="cluster.jsp">Cluster</a>
                </li>
                <li>
                    <a href="apps.jsp" class="current">Apps</a>
                </li>
                <li>
                    <a href="monitoring.jsp">Monitoring</a>
                </li>
                <li>
                    <a href="https://github.com/TheOpenCloudEngine/garuda/blob/master/README.md"
                       target="_blank">Docs</a>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">user@email.com <span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" aria-labelledby="themes">
                        <li><a href="#">홍길동</a></li>
                        <li class="divider"></li>
                        <li><a href="login.jsp">로그아웃</a></li>
                    </ul>
                </li>
                <li><a href="settings"><i class="glyphicon glyphicon-cog"></i></a></li>
            </ul>

        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Apps</h1>
            </div>

            <hr>
            <div class="col-md-12">
                <dl class="dl-horizontal">
                    <dt>App Count:</dt>
                    <dd>3</dd>
                    <dt>Total Scales:</dt>
                    <dd>25</dd>
                    <dt>Used Memory:</dt>
                    <dd>
                        <div class="progress info">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="30"
                                 aria-valuemin="0" aria-valuemax="100" style="width:1%;min-width:20%">
                                850MB / 4800MB ( 30% )
                            </div>
                        </div>
                    </dd>
                    <dt>Used CPUs:</dt>
                    <dd>
                        <div class="progress">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="30"
                                 aria-valuemin="0" aria-valuemax="100" style="width:60%;min-width:20%">
                                2.5 / 8 ( 60% )
                            </div>
                        </div>
                    </dd>
                </dl>
            </div>

            <div class="pull-right" style="margin-bottom: 10px;">
                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#newApp">New App</button>
            </div>
            <div style="padding-top: 20px;">
                <a href="javascript:refreshList()"><i class="glyphicon glyphicon-refresh"></i> Refresh</a>
            </div>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>URL</th>
                    <th>Memory</th>
                    <th>CPUs</th>
                    <th>Scales</th>
                    <th>Status</th>
                    <th>Version</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><a href="appInfo.jsp">/webapp-java</a>
                    </td>
                    <td><a href="http://webapp-java.dev.mydoamin.com" target="_blank"><i
                            class="glyphicon glyphicon-new-window"></i> http://webapp-java.dev.mydoamin.com</a></td>
                    <td>100MB</td>
                    <td>1</td>
                    <td>2/2</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                    <td>2014-09-12<br>23:28:21</td>
                </tr>
                <tr>
                    <td><a href="appInfo.jsp">/erp</a>
                    </td>
                    <td><a href="http://erp.dev.mydoamin.com" target="_blank"><i
                            class="glyphicon glyphicon-new-window"></i> http://erp.dev.mydoamin.com</a></td>
                    <td>750MB</td>
                    <td>1.5</td>
                    <td>2/3</td>
                    <td><span class="label label-default">Deploying</span></td>
                    <td>2014-09-12<br>23:28:21</td>
                </tr>
                <tr>
                    <td colspan="2">TOTAL</td>
                    <td>850MB</td>
                    <td>2.5</td>
                    <td>5</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>


    </div>

</div>

</body>
</html>

<div class="modal fade" id="newApp" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">New Application</h4>
            </div>
            <div class="modal-body">
                <form>
                    <fieldset>
                        <div class="form-group">
                            <label for="appID" class="col-lg-2 control-label">App ID</label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="appID" placeholder="appID">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="platform" class="col-md-2 control-label">Platform</label>

                            <div class="col-md-10">
                                <select class="form-control" id="platform">
                                    <option>Java - Tomcat</option>
                                    <option>PHP - Apache</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="appFile" class="col-md-2 control-label">App File</label>

                            <div class="col-md-9">
                                <input type="file" name="appFile" id="appFile">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="scale" class="col-md-2 control-label">Scale</label>

                            <div class="col-md-9">
                                <select class="form-control" id="scale">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                    <option>5</option>
                                    <option>6</option>
                                    <option>7</option>
                                    <option>8</option>
                                    <option>9</option>
                                    <option>10</option>
                                </select>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Create</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->