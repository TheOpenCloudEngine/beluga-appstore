
<!DOCTYPE html>
<html>
<head>
    <title>Garuda Service Portal</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="/resources/favicon.ico">
    <link rel="stylesheet" media="screen" href="/resources/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" media="screen" href="/resources/css/login.css" />
    <script src="/resources/jquery/jquery-1.11.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>

    <meta content='width=device-width' name='viewport'>
    <script>
    $(document).ready(function() {
        $("#signup-form").validate();
        $("#orgId").on("focusout", checkOrganization);

        function checkOrganization() {
            var orgId = $("#orgId").val();
            console.log("checkOrganization : ", orgId);
            $.ajax({
                url : "/api/organization",
                async: true,
                dataType : "json",
                type : "POST",
                data : JSON.stringify({ "orgId" : orgId + "" }),
                success : function(response) {
                    var orgName = response.name;
                    $("#orgName").val(orgName);
                    $("#orgName").attr("readonly", "readonly");
                    console.log("org success : ", orgName);
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    console.log("org error : ", textStatus, errorThrown);
                    $("#orgName").val("");
                    $("#orgName").removeAttrs("readonly");
                }
            });
        }
    });


    </script>
</head>
<body class='login'>

    <img class="logo" src="/resources/css/oce.png" alt="Garuda logo" />
    <form class="vertical-form" id="signup-form" action="/signUp" accept-charset="UTF-8" method="post">
        <legend>Sign Up</legend>
        <h4>Organization</h4>
        <input placeholder="Organization ID" label="false" type="text" value="" name="orgId" id="orgId" />
        <input placeholder="Organization Name" label="false" type="text" value="" name="orgName" id="orgName" />
        <h4>Personal</h4>
        <input placeholder="Email Address" label="false" type="text" value="" name="userId" id="userId" />
        <input placeholder="Password" label="false" autocomplete="off" type="password" name="password" id="password" />
        <input type="submit" class="btn btn-success" name="commit" value="Create Account" />
        <%--<p><a href="/forgot_password/new">Forgot password?</a></p>--%>
    </form>
    <div class='footer'>
        <p>
            Don't have an account?
            <a href="/login">Log In</a>
        </p>
    </div>

</body>
</html>
