<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Проверка попадания точки</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/superagent/superagent.min.js"></script>
</head>
<body>
<div id="header">
    Student Name: Владислав | Group: P3234 | Variant: 24155
</div>

<div class="main-wrapper">
    <div class="left-container">
        <!-- Главная область для расположения контейнеров слева -->
        <div class="container">
            <h1>Проверка попадания точки</h1>

            <!-- Форма для ввода координат и радиуса -->
            <form id="pointForm" action="${pageContext.request.contextPath}/controller" method="get">
                <div class="input-field">
                    <!-- Выбор X через чекбоксы -->
                    <label>Выберите X:</label>
                    <div id="x-buttons" class="checkbox-group">
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="-3" onclick="setXValue(-3)"> -3
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="-2" onclick="setXValue(-2)"> -2
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="-1" onclick="setXValue(-1)"> -1
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="0" onclick="setXValue(0)"> 0
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="1" onclick="setXValue(1)"> 1
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="2" onclick="setXValue(2)"> 2
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="3" onclick="setXValue(3)"> 3
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="4" onclick="setXValue(4)"> 4
                        </label>
                        <label class="checkbox-option">
                            <input type="checkbox" name="x" value="5" onclick="setXValue(5)"> 5
                        </label>
                    </div>
                    <!-- Скрытое поле для хранения выбранного X -->
                    <input type="hidden" id="x" name="x" value="">
                </div>

                <div class="input-field">
                    <!-- Ввод Y через текстовое поле -->
                    <label for="y">Введите Y (от -5 до 5):</label>
                    <input type="number" onclick="updatePointOnGraph()" id="y" name="y" min="-5" max="5" required
                           placeholder="Введите значение Y">
                </div>

                <div class="input-field">
                    <!-- Выбор R через кнопки -->
                    <label for="r">Выберите R:</label>
                    <div id="r-buttons" class="button-container">
                        <button type="button" onclick="setRValue(1)">1</button>
                        <button type="button" onclick="setRValue(1.5)">1.5</button>
                        <button type="button" onclick="setRValue(2)">2</button>
                        <button type="button" onclick="setRValue(2.5)">2.5</button>
                        <button type="button" onclick="setRValue(3)">3</button>
                    </div>
                    <div id="r-selected" class="selected-value">Selected R: None</div>
                    <input type="hidden" id="r" name="r" value="">
                </div>

                <button type="button" onclick="sendData()">Проверить</button>
            </form>

            <!-- Контейнер для отображения ошибок -->
            <div id="error-message" style="display:none; color: red;"></div>
        </div>
    </div>

    <div class="right-container">
        <!-- Дополнительный контейнер справа -->
        <div class="container">
            <canvas id="plotCanvas"></canvas>
        </div>
    </div>
</div>
<script type="text/javascript">
    const pageContextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>