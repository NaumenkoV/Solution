<%@ page import="com.softserveinc.ita.utils.JsonUtil" %>
<%@ page import="com.softserveinc.ita.utils.impl.JsonUtilGsonImpl" %>
<%@page import="java.io.*" %>
<%@page import="java.net.*" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.softserveinc.ita.utils.impl.JsonUtilJaxsonImpl" %>
<%@ page import="com.softserveinc.ita.entity.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Interview Results</title>
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
                        <li class="user" title="Your Profile">
                            <a href="#">currentUser</a></li>
                        <li class="logout" title="Log out"><a href="j_spring_security_logout">logOut</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <%!
        public String getJsonByUrl(URL url) throws IOException {
            String recv;
            String recvbuff = "";

            URLConnection urlcon = url.openConnection();
            BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

            while ((recv = buffread.readLine()) != null)
                recvbuff += recv;

            buffread.close();
            return recvbuff;
        }
    %>
    <%
        JsonUtil jsonUtil = new JsonUtilJaxsonImpl();
        Interview interview =
                jsonUtil.fromJson(getJsonByUrl(new URL("http://localhost:8080/interviews/" + request.getParameter("interviewId"))), Interview.class);

    %>

    <div class="applicantTable">
            <%
                Appointment appointment =
                        jsonUtil.fromJson(getJsonByUrl(new URL("http://localhost:8080/appointments/" + request.getParameter("interviewId"))), Appointment.class);
                Applicant applicant = jsonUtil.fromJson(getJsonByUrl(new URL("http://localhost:8080/applicants/" + appointment.getApplicantId())), Applicant.class);
            %>
            <table>
                <tr>
                    <td>Applicant Information</td>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><%=applicant.getName()%></td>
                </tr>
                <tr>
                    <td>Surname:</td>
                    <td><%=applicant.getSurname()%></td>
                </tr>
                <tr>
                    <td>Birthday:</td>
                    <td><%=applicant.getBirthday()%></td>
                </tr>
                <tr>
                    <td>Phone:</td>
                    <td><%=applicant.getPhone()%></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><%=applicant.getEmail()%></td>
                </tr>

            </table>
    </div>

    <div class="questionTable">
        <div align="center">
            <p>&nbsp;</p>

            <%
                Set<QuestionsBlock> allQuestionsBlocksActual = interview.getQuestionsBlocks();
                Iterator<QuestionsBlock> it = allQuestionsBlocksActual.iterator();
                while(it.hasNext()){
                    QuestionsBlock questionsBlockActual = it.next();
                    Set<QuestionInformation> questionInformationSet = questionsBlockActual.getQuestions();
                    Iterator<QuestionInformation> it2 = questionInformationSet.iterator();

            %>
            <p>&nbsp;</p>
            <table>
                <tr>
                    <td>User:</td>
                    <td><%=questionsBlockActual.getUserRole()%></td>
                </tr>

            </table>
            <table width="462" border="1">
                <%
                    while(it2.hasNext()){
                        QuestionInformation questionInformationActual = it2.next();

                %>
                <tr>
                    <td>Question:</td>
                    <td><%= questionInformationActual.getQuestion()%></td>
                </tr>
                <tr>
                    <td>Comment:</td>
                    <td><%= questionInformationActual.getComment()%></td>
                </tr>
                <tr>
                    <td>Points:</td>
                    <td><%= questionInformationActual.getMark()%></td>
                </tr>
                <tr>
                    <td>Weight:</td>
                    <td><%= questionInformationActual.getWeight()%></td>
                </tr>
                <%} %>

            </table>
            <%}%>

            <p>&nbsp;</p>
        </div>
    </div>

    <div class="resultTable">
        <%

            InterviewResults interviewResults = jsonUtil.fromJson(getJsonByUrl(new URL("http://localhost:8080/interviews/" + request.getParameter("interviewId") + "/result")), InterviewResults.class);
        %>
        <table>
            <tr>
                <td>Interview Results</td>
            </tr>
            <tr>
                <td>Total Points:</td>
                <td><%=interviewResults.getTotalPoints()%></td>
            </tr>
            <tr>
                <td>Final Comment:</td>
                <td><%=interviewResults.getTotalPoints()%></td>
            </tr>

        </table>
    </div>

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