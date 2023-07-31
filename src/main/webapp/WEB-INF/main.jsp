<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DATABASE</title>
    <style>
        body {
            background-image: url('/images/WELCOME.jpg');
            background-repeat: no-repeat;
            background-size: 100%;
        }

        .button-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .button {
            display: block;
            margin: 1px auto;
            padding: 20px 40px;
            width: 154px; /* фіксована ширина */
            height: 12px; /* фіксована висота */
            font-size: 20px;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 12px;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #3e8e41;
        }
    </style>
</head>
<body>
<br>
<br>
<br>
<c:forEach items="${allTables}" var="table" varStatus="status">
    <a href="${allUrls[status.index]}" class="button">${table}</a><br>
</c:forEach>
</body>
</html>

