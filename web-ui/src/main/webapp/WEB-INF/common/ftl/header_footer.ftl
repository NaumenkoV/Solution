<#macro main_page>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
            <title>interviewer-home</title>
            <link rel="stylesheet" href="/common/css/icons/entypo/css/entypo.css" />
            <link rel="stylesheet" href="/common/css/flexslider.css" />
            <link rel="stylesheet" href="/css/header.css" />
            <link rel="stylesheet" href="/common/css/style.css" />
        </head>

        <body>
            <div id="boxedWrapper">
                <div class="header">
                    <div class="navbar navbar-static-top">
                        <div class="navbar-inner">
                            <div class="container">

                                <a class="brand" href="/">IT Interviewer</a>

                                <div class="nav-collapse">
                                    <ul class="nav pull-left">
                                        <li class="active dropdown">
                                            <a href="/ui/groups" class="dropdown-toggle">Groups</a>
                                        </li>
                                        <li class="dropdown central">
                                            <a href="/ui/questions" class="dropdown-toggle">My questions</a>
                                        </li>
                                        <li class="dropdown">
                                            <a href="/ui/users" class="dropdown-toggle">Users</a>
                                        </li>
                                    </ul>
                                </div>
                                <ul class="menuIcon pull-right">
                                    <li class="user" title="${user.getName() + " " + user.getSurname()}">
                                    <a href="#">currentUser</a></li>
                                    <li class="logout" title="Log out"><a href="j_spring_security_logout">logOut</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <!--use this as an anchor for your specific page content-->
                <#nested/>

                <div class="push80"></div>
                <a href="#" id="toTop" class="entypo up-open"><i></i> Back to top</a>
            </div>

            <div id="footer">
                <div class="footNotes">
                    <div class="container">
                        <div class="row-fluid">
                            <div class="span4">
                                <p>03057 Dehtyarivska Street 21G, KIEV</p>
                            </div>
                            <div class="span4">
                                <p>.</p>
                            </div>
                            <div class="span4 doRight">
                                <p>2014 ITA Java group  All Rights Reserved</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    </html>
</#macro>