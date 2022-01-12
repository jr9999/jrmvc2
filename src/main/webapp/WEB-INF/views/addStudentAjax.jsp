<%--
  Created by IntelliJ IDEA.
  User: j.regan
  Date: 1/12/22
  Time: 8:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- @page contentType="text/html;charset=UTF-8" language="java" --%>

<%@page session="false"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <c:url var="home" value="/" scope="request" />
    <spring:url value="/resources/jquery.1.10.2.min.js"
                var="jqueryJs" />
    <script src="${jqueryJs}"></script>

    <title>AJAX based page for adding a student</title>
</head>
<body>

<div style="min-height: 500px">

    <div>
        <h1>Add Student AJAX Form</h1>
        <br>

        <div id="feedback"></div>

        <form id="add-student-form">
            <div>
                <label >Username</label>
                <div>
                    <input type=text id="studentName">
                </div>
            </div>

            <div>
                <div>
                    <button type="submit" id="bth-add-student">Add</button>
                </div>
            </div>
        </form>

        <div>
            <form id="search-student-form">
                <div>
                    <label >search criteria</label>
                    <div>
                        <input type=text id="studentNameSearchCriteria">
                    </div>
                </div>
                <div>
                    <div>
                        <button type="submit" id="bth-search-student">Search</button>
                    </div>
                </div>
            </form>
            <div><h1>Student List</h1></div>
            <div id="search-results"></div>
            <table>
                <tr><th>Id</th><th>Name</th></tr>
            </table>
        </div>

    </div>

</div>

<script>

    jQuery(document).ready(

        function($) {
            $("#search-student-form").submit(
                function(event) {
                    // Prevent the form from submitting via the browser.
                    event.preventDefault();
                    studentSearchViaAjax();
                }
            );

            $("#add-student-form").submit(
                function(event) {
                    // Prevent the form from submitting via the browser.
                    event.preventDefault();
                    addStudentViaAjax();
                }
            );
        }
    );

    function studentSearchViaAjax() {

        var search = {};

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "${home}ajaxstudent/api/search",
            data : JSON.stringify(search),
            dataType : 'json',
            timeout : 100000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                displaySearchResults(data);
            },
            error : function(e) {
                console.log("ERROR: ", e);
                displaySearchResults(e);
            },
            done : function(e) {
                console.log("DONE");
            }
        });
    }

    function addStudentViaAjax() {
        var data = {}
        //data["student"] = $("#query").val();
        data = {
            name: "jeremy",
            id: 12345
        };

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "${home}ajaxstudent/api/add",
            data : JSON.stringify(data),
            dataType : 'json',
            timeout : 100000,
            success : function(successData) {
                console.log("SUCCESS: ", successData);
                studentSearchViaAjax();
            },
            error : function(e) {
                console.log("ERROR adding student: ", e);
            },
            done : function(e) {
                console.log("DONE");
            }
        });
    }

    function enableSearchButton(flag) {
        $("#btn-search-student").prop("disabled", flag);
    }

    function displaySearchResults(data) {
        var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
            + JSON.stringify(data, null, 4) + "&lt;/pre&gt;";
        $('#search-results').html(json);
    }
</script>

</body>
</html>
