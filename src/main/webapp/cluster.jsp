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
                    <a href="cluster.jsp" class="current">Cluster</a>
                </li>
                <li>
                    <a href="apps.jsp">Apps</a>
                </li>
                <li>
                    <a href="monitoring.jsp">Monitoring</a>
                </li>
                <li>
                    <a href="https://github.com/TheOpenCloudEngine/garuda/blob/master/README.md" target="_blank">Docs</a>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">개발클러스터 <span class="caret"></span></a>
                    <ul class="dropdown-menu" aria-labelledby="themes">
                        <li><a href="#">개발클러스터</a></li>
                        <li><a href="#">운영클러스터</a></li>
                        <li class="divider"></li>
                        <li><a href="login.jsp">New Cluster</a></li>
                    </ul>
                </li>
                <li><a href="settings.jsp"><i class="glyphicon glyphicon-cog"></i></a></li>
            </ul>

        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Cluster</h1>
            </div>
            <hr>

            <div class="col-md-6">
                <dl class="dl-horizontal">
                    <dt>ID: </dt>
                    <dd>dev-cluster </dd>
                    <dt>Domain: </dt>
                    <dd>dev.mydomain.com </dd>
                    <dt>Iaas Provider: </dt>
                    <dd>ec2 </dd>
                    <dt>Iaas Definition : </dt>
                    <dd>AWS EC2 Asia Pacific</dd>
                    <dt>Iaas Profile : </dt>
                    <dd>ec2-dev</dd>
                </dl>
            </div>
            <div class="col-md-6">
                <dl class="dl-horizontal">
                    <dt>Key Pair : </dt>
                    <dd>aws-garuda</dd>
                    <dt>State: </dt>
                    <dd>Started </dd>
                    <dt>Started : </dt>
                    <dd>2015.06.29 04:01:45</dd>
                    <dt>Stopped : </dt>
                    <dd></dd>
                    <dt>Created : </dt>
                    <dd>2015.06.29 04:01:45</dd>
                </dl>
            </div>
            <div>
                <a href="javascript:refreshList()" class=""><i class="glyphicon glyphicon-refresh"></i> Refresh</a>
            </div>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Instance Type</th>
                    <th>IP Address</th>
                    <th>Memory</th>
                    <th>Disk</th>
                    <th>Group</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>proxy</td>
                    <td>t2.micro</td>
                    <td>192.168.0.15</td>
                    <td>2GB</td>
                    <td>10GB</td>
                    <td>default</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                </tr>
                <tr>
                    <td>management</td>
                    <td>t2.micro</td>
                    <td>192.168.0.16</td>
                    <td>2GB</td>
                    <td>10GB</td>
                    <td>default</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                </tr>
                <tr>
                    <td>master</td>
                    <td>t2.micro</td>
                    <td>192.168.0.20</td>
                    <td>2GB</td>
                    <td>10GB</td>
                    <td>default</td>
                    <td><span class="label label-default">Pending</span></td>
                </tr>
                <tr>
                    <td>worker</td>
                    <td>t2.micro</td>
                    <td>192.168.0.25</td>
                    <td>4GB</td>
                    <td>10GB</td>
                    <td>default</td>
                    <td><span class="glyphicon glyphicon-minus-sign stop-status"></span></td>
                </tr>
                <tr>
                    <td>service-db</td>
                    <td>t2.micro</td>
                    <td>192.168.0.26</td>
                    <td>2GB</td>
                    <td>10GB</td>
                    <td>default</td>
                    <td><span class="glyphicon glyphicon-ok-sign running-status"></span></td>
                </tr>
                </tbody>
            </table>
            <div>
                <div class="pull-left">
                    <a href="javascript:refreshList()" class="btn btn-default"> Add Instance</a>
                    &nbsp;<a href="javascript:refreshList()" class="btn btn-danger outline"> Remove Instance</a>
                </div>
                <div class="pull-right">
                    <a href="javascript:refreshList()" class="btn btn-default"> Stop Cluster</a>
                    &nbsp;<a href="javascript:refreshList()" class="btn btn-danger outline">Destroy Cluster</a>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>