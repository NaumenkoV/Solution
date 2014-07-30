<html>
<head>
    <meta charset="utf-8"/>
    <title>THIS IS FILE UPLOADDDDDDDD!!!!!!!!!!!11</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
    <style>
        .wrap {
            width: 700px;
            margin: 10px auto;
            padding: 10px 15px;
            background: white;
            border: 2px solid #DBDBDB;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            text-align: center;
            overflow: hidden;
        }

        body {
            font: 14px Arial,Tahoma,Helvetica,FreeSans,sans-serif;
            text-transform: inherit;
            color: #582A00;
            background: #E7EDEE;
            width: 100%;
            margin: 0;
            padding: 0;
        }

        .buttonstyle, input[type="submit"] {
            border-radius: 10px;
            background-color: #61B3DE;
            border: 0;
            color: white;
            font-weight: bold;
            font-style: italic;
            padding: 6px 15px 5px;
            cursor: pointer;
        }

        h1 {
            font-family: Georgia, "Times New Roman", Times, serif;
            font-size: 170%;
            color: #645348;
            font-style: italic;
            text-decoration: none;
            font-weight: 100;
            padding: 10px;
        }

        p {
            display: block;
            -webkit-margin-before: 1em;
            -webkit-margin-after: 1em;
            -webkit-margin-start: 0px;
            -webkit-margin-end: 0px;
        }
    </style>

</head>
<body>
<div class="wrap">
	<h1>${message}</h1>

    <p>Valid formats: jpeg, gif, png, Max upload: %unknown% kb</p>
    <div>
        <form name="uploader" method="POST" enctype="multipart/form-data" action="...">
            File to upload: <input type="file" name="file"> ID: <input type="text" id="text_field1"><br /> <br /> <input id="button1" type="submit" value="Upload" > Press here to upload the file!
        </form>
        <script >
//            $('#button1').click(function(){
//                var bla = $('#text_field1').val();
//                $('form').get(0).setAttribute('action', 'http://localhost:8080/repository/imgfile/applicant/' + bla);
//            });

            $("form[name='uploader']").submit(function(e) {
                var formData = new FormData($(this)[0]);
                var bla = $('#text_field1').val();
                $.ajax({
                    url: location.origin + "/repository/imgfile/applicant/" + bla,
                    type: "POST",
                    data: formData,
                    async: false,
                    dataType : 'json',
                    success: function (msg) {
                        var status = msg.status;
                        alert(status)
                    },
                    error: function (msg) {
                        var op_status = msg.status;
                        alert(op_status)
                    },
                    cache: false,
                    contentType: false,
                    processData: false
                });

                e.preventDefault();
            });

//            $(document).ready(function(){
//                 /* your code */
//            });
        </script>
    </div>

    <%--<script type="text/javascript">--%>
        <%--$.get("http://localhost:8080/repo/img/applicant/testController100?height=200&width=200", onAjaxSuccess--%>
        <%--);--%>

        <%--function onAjaxSuccess(data)--%>
        <%--{--%>
            <%--alert(data);--%>
        <%--}--%>
    <%--</script>--%>

    <div>
        ID: <input type="text" id="text_field2">
        WIDTH: <input type="text" id="text_field5">
        <input id="button2" type="submit" value="GET" class="buttonstyle"> Press here to get the image as file!
        <script>
            $('#button2').click(function(){
                var bla = $('#text_field2').val();
                var width = $('#text_field5').val();

                var img = $("<img />").attr('src', location.origin + '/repository/imgfile/applicant/' + bla + '?height=' + width + '&width=' + width)
                        .load(function() {
                            if (!this.complete || typeof this.naturalWidth == "undefined" || this.naturalWidth == 0) {
                                alert('broken image!');
                            } else {
                                $("#something").append(img);
                            }
                        });

            });
        </script>
    </div>
    <div id="something">

    </div>

    <div>
        ID: <input type="text" id="text_field3">
        WIDTH: <input type="text" id="text_field4">
        <input id="button3" type="submit" value="GET" class="buttonstyle"> Press here to get the base64 image!
        <script>
            $('#button3').click(function(){
                var blablabla = $('#text_field3').val();
                var width = $('#text_field4').val();

                $.get(location.origin + "/repository/img/applicant/" + blablabla + "?height=" + width + "&width=" + width,
                            function(data,status){
                                if (this.error) {
                                    alert("Data: " + data + "\nStatus: " + status);
                                } else {
                                    $("#image").attr('src', data);
                                    $('#hidden_div').css('display','block');
                                }
                });
            });
        </script>
    </div>
    <div id="hidden_div" style="display: none;">
        <img src="#" id="image">
    </div>
</div>
</body>
</html>