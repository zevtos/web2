<%--@elvariable id="x" type="java.lang.Double"--%>
<%--@elvariable id="y" type="java.lang.Double"--%>
<%--@elvariable id="r" type="java.lang.Double"--%>
<%--@elvariable id="isHit" type="java.lang.Boolean"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результаты проверки точки</title>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Результат проверки точки</h1>
    <p>Координаты X: ${x}, Y: ${y}, Радиус: ${r}</p>

    <c:choose>
        <c:when test="${isHit}">
            <p>Точка попала в область!</p>
        </c:when>
        <c:otherwise>
            <p>Точка не попала в область.</p>
        </c:otherwise>
    </c:choose>

    <h2>Все предыдущие результаты:</h2>
    <table id="resultsTable">
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Попадание</th>
    </tr>
    <c:forEach var="result" items="${sessionScope.results}">
        <tr>
            <td>${result.x}</td>
            <td>${result.y}</td>
            <td>${result.r}</td>
            <td>${result.hit ? 'Да' : 'Нет'}</td>
        </tr>
    </c:forEach>
</table>


    <form action="${pageContext.request.contextPath}">
        <button type="submit">Вернуться к форме</button>
    </form>
</div>
</body>
</html>
