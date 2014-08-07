<#import "header_footer.ftl" as header_footer/>

<@header_footer.main_page>
<link href="group/css/jquery-ui.css" rel="stylesheet">
<link href="group/css/applicants.css" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.js"></script>
<script src="group/js/handler.js"></script>
<script src="group/js/boarding-handler.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>

<div class="outter container">
    <h2 class="viewName">Sort applicants</h2>
    <a href="/ui/groups" class="done">Done</a>
    <button id="sort" class="scheduleView">Sort</button>
    <button id="submitList" class="scheduleView">Submit</button>
    <button id="notifications" class="scheduleView">Notifications</button>
    <div class="clearfix"></div>

    <div class="nocontent">
        <h3>Sorry, no content yet.</h3></div>
    <div class="accordion interviewed"></div>
</div>
<div id="dialog" title="Information">
    <p></p>
</div>
</@header_footer.main_page>