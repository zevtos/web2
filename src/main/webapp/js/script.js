(function () {
    let selectedX = null;
    let selectedR = null;
    let selectedY = null;
    let points = [];

    function setXValue(value) {
        selectedX = parseInt(value, 10);
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
        hideError();
        updatePointOnGraph();
    }

    function setYValue(value) {
        if (isNaN(value) || value < -5 || value > 5) {
            showError({message: 'Значение Y должно быть в диапазоне от -5 до 5.'});
            if (!isNaN(value)) selectedY = parseFloat(value);
            return;
        }
        selectedY = parseFloat(value);
        hideError();
        updatePointOnGraph();
    }

    function updateSelectedRValue(value) {
        const rSelectedElement = document.getElementById("r-selected");
        rSelectedElement.textContent = `Выбранный R: ${value}`;
    }

    function setRValue(value) {
        selectedR = parseFloat(value);
        document.getElementById("r").value = value;
        updateButtonState('r-buttons', value);
        updateSelectedRValue(value);
        hideError();
        drawCoordinateSystem();
        drawAllPoints();
    }

    function updatePointOnGraph() {
        const x = selectedX;
        const y = selectedY;
        const r = selectedR;

        if (!isNaN(x) && !isNaN(y) && !isNaN(r)) {
            const canvas = document.getElementById("plotCanvas");
            const ctx = canvas.getContext("2d");

            drawCoordinateSystem();
            drawAllPoints();

            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const scale = canvas.width / 4;

            const adjustedX = centerX + (x / r) * scale;
            const adjustedY = centerY - (y / r) * scale;

            drawPoint(ctx, adjustedX, adjustedY, "black");
        }
    }

    function updateButtonState(containerId, selectedValue) {
        const buttons = document.getElementById(containerId).getElementsByTagName('button');
        for (let button of buttons) {
            if (parseFloat(button.innerHTML.trim()) === selectedValue) {
                button.classList.add('active');
            } else {
                button.classList.remove('active');
            }
        }
    }

    function findClosestX(graphX) {
        const possibleXValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
        return possibleXValues.reduce((prev, curr) => {
            return (Math.abs(curr - graphX) < Math.abs(prev - graphX) ? curr : prev);
        });
    }

    function handleCanvasClick(event) {
        if (!selectedR) {
            alert("Выберите значение R перед кликом на график!");
            return;
        }

        const canvas = document.getElementById("plotCanvas");
        const rect = canvas.getBoundingClientRect();

        const scaleX = canvas.width / rect.width;
        const scaleY = canvas.height / rect.height;

        const x = (event.clientX - rect.left) * scaleX;
        const y = (event.clientY - rect.top) * scaleY;

        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const scale = canvas.width / 4;

        const graphX = ((x - centerX) / scale) * selectedR;
        const graphY = ((centerY - y) / scale) * selectedR;

        const closestX = findClosestX(graphX);
        setXValue(closestX);

        const roundedY = parseFloat(graphY.toFixed(2));
        document.getElementById("y").value = roundedY;
        setYValue(roundedY);

        sendData();
    }

    function drawPoint(ctx, x, y, color) {
        ctx.fillStyle = color;
        ctx.beginPath();
        ctx.arc(x, y, 5, 0, 2 * Math.PI);
        ctx.fill();
    }

    function sendData() {
        const x = selectedX;
        const y = selectedY;
        const r = selectedR;

        if (!validateInputs(x, y, r)) {
            showError({message: 'Некорректные данные! Убедитесь, что X, Y и R заданы правильно.'});
            return;
        }

        const url = `${window.location.origin}${pageContextPath}/controller`;

        superagent
            .get(url)
            .query({x: x, y: y, r: r})
            .end((err, res) => {
                if (err) {
                    showError({message: err.message || 'Ошибка при отправке данных на сервер'});
                    return;
                }
                document.open();
                document.write(res.text);
                document.close();
            });
    }

    function validateInputs(x, y, r) {
        const validXValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
        const isXValid = validXValues.includes(x);
        const isYValid = !isNaN(y) && y >= -5 && y <= 5;
        const validRValues = [1, 1.5, 2, 2.5, 3];
        const isRValid = validRValues.includes(r);

        return isXValid && isYValid && isRValid;
    }

    function showError(error) {
        const errorMessage = document.getElementById("error-message");
        errorMessage.innerHTML = error.message;
        errorMessage.style.display = "block";
    }

    function hideError() {
        const errorMessage = document.getElementById("error-message");
        if (errorMessage) {
            errorMessage.style.display = "none";
        }
    }

    function drawCoordinateSystem() {
        const canvas = document.getElementById("plotCanvas");
        const ctx = canvas.getContext("2d");

        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const scale = canvas.width / 4;

        ctx.clearRect(0, 0, canvas.width, canvas.height);

        ctx.fillStyle = "rgba(74, 144, 226, 0.5)";
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

        ctx.strokeStyle = "#000";
        ctx.lineWidth = 2;
        ctx.beginPath();
        ctx.moveTo(0, centerY);
        ctx.lineTo(canvas.width, centerY);
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX, canvas.height);
        ctx.stroke();

        ctx.font = "14px Arial";
        ctx.fillStyle = "#000";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";

        ctx.fillText("R", centerX + scale, centerY + 20);
        ctx.fillText("R/2", centerX + scale / 2, centerY + 20);
        ctx.fillText("-R", centerX - scale, centerY + 20);
        ctx.fillText("-R/2", centerX - scale / 2, centerY + 20);

        ctx.fillText("R", centerX - 20, centerY - scale);
        ctx.fillText("R/2", centerX - 20, centerY - scale / 2);
        ctx.fillText("-R/2", centerX - 20, centerY + scale / 2);
        ctx.fillText("-R", centerX - 20, centerY + scale);
    }

    function drawAllPoints() {
        const canvas = document.getElementById("plotCanvas");
        const ctx = canvas.getContext("2d");
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const scale = canvas.width / 4;

        points.forEach(point => {
            const x = centerX + (point.x / point.r) * scale;
            const y = centerY - (point.y / point.r) * scale;
            drawPoint(ctx, x, y, point.isHit ? "green" : "red");
        });
    }

    function updateResults() {
        const resultsTable = document.getElementById("resultsTable");
        if (!resultsTable) {
            console.error("Таблица результатов не найдена");
            return;
        }

        points = [];

        const rowsArray = Array.from(resultsTable.getElementsByTagName("tr")).slice(1);

        for (const row of rowsArray) {
            const cells = row.getElementsByTagName("td");

            if (cells.length >= 4) {
                const x = parseFloat(cells[0].textContent);
                const y = parseFloat(cells[1].textContent);
                const r = parseFloat(cells[2].textContent);
                const isHit = cells[3].textContent.trim().toLowerCase() === 'попадание';

                points.push({
                    x: x,
                    y: y,
                    r: r,
                    isHit: isHit
                });
            }
        }
    }

    window.onload = function () {
        updateResults();
        drawCoordinateSystem();
        drawAllPoints();
        const canvas = document.getElementById("plotCanvas");
        canvas.addEventListener("click", handleCanvasClick);
        setRValue(3);
    }
    window.setXValue = setXValue;
    window.setYValue = setYValue;
    window.setRValue = setRValue;
    window.sendData = sendData;
})();
