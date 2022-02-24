<%@tag description="Page layout" pageEncoding="UTF-8" %>
<%@attribute name="scripts" fragment="true" %>
<%@attribute name="body" fragment="true" %>
<%@attribute name="styles" fragment="true" %>
<%@attribute name="title" fragment="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Lana">
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <jsp:invoke fragment="styles"/>
</head>
<body style="min-height: 100vh" class="d-flex flex-column align-items-center pt-5">
<jsp:invoke fragment="body"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<jsp:invoke fragment="scripts"/>
</body>
</html>
