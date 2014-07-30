<!doctype html>
<html lang="us">
<head>
    <meta charset="utf-8">
    <title>Group status: planned</title>
    <link href="http://code.jquery.com/ui/1.11.0/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
    <link href="group/css/applicants.css" rel="stylesheet">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.js"></script>
    <script src="group/js/handler.js"></script>
    <script src="group/js/planned-handler.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
    <%--<script src="group/js/jquery.nicefileinput.min.js"></script>--%>
    <script src="group/js/fileinput.js"></script>
</head>
<body>
<a href="/ui/groups" class="done">Done</a>
<br>

<div class="accordion newapp"></div>
<div class="accordion not_scheduled"></div>
<div class="accordion scheduled"></div>
<div id="dialog" title="Information">
    <p></p>
</div>
</body>
</html>
