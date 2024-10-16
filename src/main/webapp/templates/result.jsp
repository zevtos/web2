<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результаты проверки точки</title>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <header>
        <h1>Результаты проверки точки</h1>
        <p>Владислав | Группа: P3234 | Вариант: 24155</p>
    </header>

    <main>
        <div class="result-info">
            <p><strong>Проверенные значения:</strong></p>
            <p>X: ${x}, Y: ${y}, R: ${r}</p>

            <c:choose>
                <c:when test="${isHit}">
                    <p style="color: green; font-weight: bold;">Точка попала в область!</p>
                </c:when>
                <c:otherwise>
                    <p style="color: red; font-weight: bold;">Точка не попала в область.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
    <div id="results" class="results-container">
        <h2>Предыдущие результаты:</h2>
        <table id="resultsTable">
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Результат</th>
            </tr>
            <c:forEach var="result" items="${sessionScope.results}">
                <tr>
                    <td>${result.x}</td>
                    <td>${result.y}</td>
                    <td>${result.r}</td>
                    <td>${result.hit ? 'Попадание' : 'Промах'}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class=container>
        <form action="${pageContext.request.contextPath}">
            <button type="submit" class="submit-btn">Вернуться к проверке</button>
        </form>
    </div>
</div>

<script>
    const pageContextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
