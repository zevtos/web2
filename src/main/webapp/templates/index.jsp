<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!
    double[] rValues = {1, 1.5, 2, 2.5, 3};
%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Проверка попадания точки</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>Проверка попадания точки</h1>
        <p>Владислав | Группа: P3234 | Вариант: 24155</p>
    </header>

    <main>
        <div class="graph-container">
            <canvas id="plotCanvas" width="500" height="500"></canvas>
        </div>

        <div class="form-container">
            <form id="pointForm">
                <div class="input-group">
                    <label>Выберите X:</label>
                    <div id="x-buttons" class="button-group">
                        <% for (int i = -3; i <= 5; i++) { %>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="<%= i %>" onclick="setXValue(<%= i %>)">
                            <%= i %>
                        </label>
                        <% } %>
                    </div>
                    <input type="hidden" id="x" name="x" required>
                </div>

                <div class="input-group">
                    <label for="y">Введите Y (от -5 до 5):</label>
                    <input type="number" id="y" name="y" min="-5" max="5" step="any" required
                           onchange="setYValue(this.value)">
                </div>

                <div class="input-group">
                    <label>Выберите R:</label>
                    <div id="r-buttons" class="button-group">
                        <% for (double value : rValues) { %>
                        <button type="button" onclick="setRValue(<%= value %>)"><%= value %>
                        </button>
                        <% } %>
                    </div>
                    <div id="r-selected" class="selected-value">Выбранный R: Не выбран</div>
                    <input type="hidden" id="r" name="r" required>
                </div>

                <button type="button" class="submit-btn" onclick="sendData()">Проверить</button>
            </form>
            <div id="error-message" class="error-message"></div>
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

</div>

<script>
    const pageContextPath = "${pageContext.request.contextPath}";
</script>
<script src="https://cdn.jsdelivr.net/npm/superagent/superagent.min.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>