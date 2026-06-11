const horaSelect = document.getElementById("horaRetirada");
const minutoSelect = document.getElementById("minutoRetirada");

horaSelect.addEventListener("change", function () {
    const hora = parseInt(this.value);

    minutoSelect.innerHTML = '<option value="">Min</option>';

    let limite = 59;

    if (hora === 21) {
        limite = 30;
    }

    for (let i = 0; i <= limite; i++) {
        const option = document.createElement("option");

        const valor = i.toString().padStart(2, "0");

        option.value = valor;
        option.textContent = valor;

        minutoSelect.appendChild(option);
    }
});