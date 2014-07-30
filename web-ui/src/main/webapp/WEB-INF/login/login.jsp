<!DOCTYPE html>
<html>

<head>

  <meta charset="UTF-8">

  <title>IT Interviewer - Login</title>

  <link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>

    <link rel="stylesheet" href="/login/css/style.css" media="screen" type="text/css" />

</head>

<body>

  <div class="login-card">
   <div class="login-header">IT Interviewer</div>
   <div class="login-body">
   <div class="error">${error}</div>
   <div class="msg">${msg}</div>
   <h1>Log-in</h1><br>
  <form action="j_spring_security_check" method='POST'>
    <input type="text" name="username" placeholder="Username">
    <input type="password" name="password" placeholder="Password">
    <input type="submit" name="login" class="login login-submit" value="login">
  </form>
</div>

<!--
  <div class="login-help">
    <a href="#">Register</a> • <a href="#">Forgot Password</a>
  </div>
-->
</div>

<!-- <div id="error"><img src="https://dl.dropboxusercontent.com/u/23299152/Delete-icon.png" /> Your caps-lock is on.</div> -->

  <script src='http://codepen.io/assets/libs/fullpage/jquery_and_jqueryui.js'></script>

</body>

</html>