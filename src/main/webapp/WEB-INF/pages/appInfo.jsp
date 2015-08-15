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
    <link rel="stylesheet" href="../../resources/css/enhance.css" >
    <script src="../../resources/jquery/jquery-1.11.3.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
    <script>
        function scaleApp(appId) {
            var scaleSize = prompt("Scale to how many instances?");
            //TODO

        }

        function destroyApp(appId) {
            confirm("Destroy app '"+appId+"'?\nThis is irreversible.");
        }

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
                    <a href="monitoring">Monitoring</a>
                </li>
                <li>
                    <a href="docs">Docs</a>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">user@email.com <span class="caret"></span></a>
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
            <div class="sub-title"><a href="javascript:history.back(-1)"><i class="glyphicon glyphicon-arrow-left"></i></a> /oce-processcodi-php</div>
            <hr>
            <div class="col-md-6">
                <dl class="dl-horizontal">
                    <dt>Each CPUs: </dt>
                    <dd>0.5 </dd>
                    <dt>Each Memory: </dt>
                    <dd>50MB</dd>
                    <dt>Total CPUs: </dt>
                    <dd>1.5</dd>
                    <dt>Total Memory: </dt>
                    <dd>150MB</dd>
                    <dt>Version</dt>
                    <dd>2014-09-12 23:28:21</dd>
                </dl>
            </div>
            <div class="col-md-6">
                <dl class="dl-horizontal">
                    <dt>Scales: </dt>
                    <dd>3</dd>
                    <dt>Running</dt>
                    <dd>3</dd>
                    <dt>Healthy</dt>
                    <dd>2</dd>
                    <dt>Unhealthy</dt>
                    <dd>1</dd>
                    <dt>Staged</dt>
                    <dd>0</dd>
                </dl>
            </div>
            <div class="col-md-12">
                <div class="pull-right" style="margin-bottom: 10px;">
                    <a href="javascript:destroyApp('/oce-processcodi-php')" class="btn btn-info">Modify App</a>
                </div>
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Status</th>
                        <th>Started</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>oce-processcodi-php.d2e904bb-f8ab-11e4-a9cb-22dab5464f33
                        <br>
                        <a href="#" class="app-link">10.132.37.104:31000</a></td>
                        <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                        <td>2015. 5. 12.<br>22:35:59</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr class="warning">
                        <td>2e72dbf1-2b2a-4204-b628-e8bd160945dd</td>
                        <td><span class="label label-default">Deploying</span></td>
                        <td>2015. 5. 12.<br>22:35:59</td>
                        <td><a href="btn btn-danger"><i class="glyphicon glyphicon-trash text-danger"></i></a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-md-12">
                <a href="javascript:destroyApp('/oce-processcodi-php')" class="btn btn-default">Restart App</a>
                <div class="pull-right">
                    <a href="javascript:destroyApp('/oce-processcodi-php')" class="btn btn-danger outline">Destroy App</a>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>