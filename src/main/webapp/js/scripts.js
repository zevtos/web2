// Переменные для хранения выбранных значений X и R
let selectedX = null;
let selectedR = null;

// Функция для установки выбранного значения X
function setXValue(value) {
    selectedX = value;
    const xInput = document.getElementById("x");

    if (xInput !== null) {
        xInput.value = value;
        const checkboxes = document.querySelectorAll('input[name="x"]');
        checkboxes.forEach((checkbox) => {
            checkbox.checked = (parseInt(checkbox.value) === value);
        });
    } else {
        console.error("Element with id 'x' not found in the DOM.");
    }
    updatePointOnGraph();
}

// Функция для установки выбранного значения Y
function setYValue(value) {
    selectedY = value;
    // Обновляем точку на графике
    updatePointOnGraph();
}

// Функция для установки выбранного значения R
function setRValue(value) {
    selectedR = value;
    document.getElementById("r").value = value;
    updateButtonState('r-buttons', value);
    document.getElementById("r-selected").textContent = `Selected R: ${value}`;
    updatePointOnGraph();
}

// Обновление точки на графике в соответствии с текущими значениями X, Y и R
function updatePointOnGraph() {
    const x = parseFloat(document.getElementById("x").value);
    const y = parseFloat(document.getElementById("y").value);
    const r = parseFloat(document.getElementById("r").value);

    if (!isNaN(x) && !isNaN(y) && !isNaN(r)) {
        const canvas = document.getElementById("plotCanvas");
        const ctx = canvas.getContext("2d");

        // Получаем размеры канваса и масштабируем координаты
        const rect = canvas.getBoundingClientRect();
        const centerX = rect.width / 2;
        const centerY = rect.height / 2;
        const scale = rect.width / 4;

        // Рассчитываем положение точки на графике
        const adjustedX = centerX + (x / r) * scale;
        const adjustedY = centerY - (y / r) * scale;

        // Рисуем точку
        drawPoint(ctx, adjustedX, adjustedY);
    }
}

// Обновление состояния кнопок
function updateButtonState(containerId, selectedValue) {
    const buttons = document.getElementById(containerId).getElementsByTagName('button');
    for (let button of buttons) {
        if (button.innerHTML.trim() === String(selectedValue)) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }
    }
}

// Функция нахождения ближайшего значения X
function findClosestX(graphX) {
    const possibleXValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
    return possibleXValues.reduce((prev, curr) => {
        return (Math.abs(curr - graphX) < Math.abs(prev - graphX) ? curr : prev);
    });
}

// Функция обработки клика по графику с учётом масштаба страницы и корректной центровки
function handleCanvasClick(event) {
    if (!selectedR) {
        alert("Выберите значение R перед кликом на график!");
        return;
    }

    const canvas = document.getElementById("plotCanvas");
    const ctx = canvas.getContext("2d");

    // Получаем размеры отображаемого канваса
    const rect = canvas.getBoundingClientRect();

    // Коэффициенты масштабирования для точных преобразований
    const scaleX = canvas.width / rect.width;
    const scaleY = canvas.height / rect.height;

    // Координаты клика на канвасе с учётом масштаба страницы
    const x = (event.clientX - rect.left) * scaleX;
    const y = (event.clientY - rect.top) * scaleY;

    // Центр координатного пространства графика (исходный центр канваса)
    const centerX = canvas.width / 2; // Не rect.width, а реальные размеры канваса
    const centerY = canvas.height / 2;

    // Масштаб графика относительно выбранного R (в пикселях на каждую единицу R)
    const scale = canvas.width / 4; // Масштаб по оси X и Y одинаковый, так как график квадратный

    // Преобразование координат в систему графика
    const graphX = ((x - centerX) / scale) * selectedR; // Преобразование x-координаты клика
    const graphY = ((centerY - y) / scale) * selectedR; // Преобразование y-координаты клика

    // Находим ближайшее значение X из допустимых
    const closestX = findClosestX(graphX);
    setXValue(closestX); // Устанавливаем ближайший X

    // Рассчитываем новое положение X для рисования точки с учетом динамических размеров
    const adjustedX = centerX + (closestX / selectedR) * scale;

    // Обновляем поле Y с округлением до двух знаков после запятой
    document.getElementById("y").value = graphY.toFixed(2);

    // Рисуем точку на графике
    drawPoint(ctx, adjustedX, y);
}



// Функция для рисования точки
function drawPoint(ctx, x, y) {
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height); // Очищаем канвас
    drawCoordinateSystem(); // Перерисовываем систему координат

    // Рисуем точку
    ctx.fillStyle = "#ff0000"; // Красная точка
    ctx.beginPath();
    ctx.arc(x, y, 5, 0, 2 * Math.PI); // Радиус точки 5 пикселей
    ctx.fill();
}

// Функция для отправки данных на сервер
function sendData() {
    // Получаем выбранные значения X, Y и R
    const x = parseInt(document.getElementById("x").value);  // Получаем значение из скрытого поля напрямую
    const y = document.getElementById("y").value;
    const r = document.getElementById("r").value;

    // Проверка корректности данных перед отправкой
    if (!validateInputs(x, y, r)) {
        showError({message: 'Некорректные данные! Убедитесь, что X, Y и R заданы правильно.'});
        return;
    }

    // Формируем URL для отправки данных через GET-запрос
    const url = `${window.location.origin}${pageContextPath}/controller`;

    // Используем SuperAgent для GET-запроса
    superagent
        .get(url)
        .query({x: x, y: y, r: r})  // Добавляем параметры x, y и r к запросу
        .end((err, res) => {
            if (err) {
                // Обработка ошибки
                showError({message: err.message || 'Ошибка при отправке данных на сервер'});
                return;
            }

            // Обработка успешного ответа
            document.body.innerHTML = res.text; // Обновляем страницу с полученным HTML
        });
}


// Валидация данных (проверка, что X и Y в пределах допустимых значений, а R — положительное)
function validateInputs(x, y, r) {
    const validXValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
    const isXValid = validXValues.includes(x);
    const parsedY = parseFloat(y);
    const isYValid = !isNaN(parsedY) && parsedY >= -5 && parsedY <= 5;

    const validRValues = [1, 1.5, 2, 2.5, 3];
    const isRValid = validRValues.includes(r);

    return isXValid && isYValid && isRValid;
}


// Обработка ответа от сервера
function handleResponse(data) {
    const resultContainer = document.getElementById("results");

    // Очищаем контейнер результатов
    resultContainer.innerHTML = '';

    // Формируем и выводим сообщение с результатом
    const resultMessage = `Точка с координатами (${data.x}, ${data.y}) ${data.isHit ? 'попала' : 'не попала'} в область радиуса ${data.r}.`;
    resultContainer.innerHTML = `<p>${resultMessage}</p>`;
}

// Отображение ошибки
function showError(error) {
    const errorMessage = document.getElementById("error-message");
    errorMessage.innerHTML = error.message;
    errorMessage.style.display = "block";
}

// Функция для получения выбранного значения X из чекбоксов
function getSelectedX() {
    const checkboxes = document.querySelectorAll('input[name="x"]:checked');
    if (checkboxes.length === 0) {
        return null;
    }
    return parseInt(checkboxes[0].value); // Берём первое выбранное значение
}

// Функция для рисования системы координат
function drawCoordinateSystem() {
    const canvas = document.getElementById("plotCanvas");
    const ctx = canvas.getContext("2d");

    canvas.width = 500;
    canvas.height = 500;

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const scale = canvas.width / 4;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Рисуем фигуры как в предыдущем коде
    ctx.fillStyle = "#4a90e2";
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX - scale, centerY);
    ctx.lineTo(centerX, centerY - scale);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.arc(centerX, centerY, scale, Math.PI, 0.5 * Math.PI, true);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.rect(centerX, centerY, scale / 2, scale);
    ctx.closePath();
    ctx.fill();

    // Рисуем оси координат
    ctx.strokeStyle = "#000";
    ctx.lineWidth = 2;

    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(canvas.width, centerY);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, canvas.height);
    ctx.stroke();

    // Отметки осей
    ctx.font = "14px Arial";
    ctx.fillStyle = "#000";
    ctx.fillText("R", centerX + scale - 10, centerY + 20);
    ctx.fillText("R/2", centerX + scale / 2 - 20, centerY + 20);
    ctx.fillText("-R", centerX - scale - 20, centerY + 20);
    ctx.fillText("-R/2", centerX - scale / 2 - 30, centerY + 20);
    ctx.fillText("R", centerX - 20, centerY - scale + 10);
    ctx.fillText("R/2", centerX - 30, centerY - scale / 2 + 10);
    ctx.fillText("-R/2", centerX - 30, centerY + scale / 2 + 10);
    ctx.fillText("-R/2", centerX - 30, centerY + scale / 2 + 10);
}


// Обработчик загрузки страницы
window.onload = function () {
    drawCoordinateSystem();
    const canvas = document.getElementById("plotCanvas");
    canvas.addEventListener("click", handleCanvasClick);
}
