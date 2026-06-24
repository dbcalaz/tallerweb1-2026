document.addEventListener("DOMContentLoaded", function() {
    const maxPasajeros = parseInt(document.getElementById('max-pasajeros').innerText);
    let asientosSeleccionados = [];

    const inputAsientos = document.getElementById('asientosSeleccionados');
    const feedback = document.getElementById('feedback-asientos');

    document.querySelectorAll('.asiento.disponible').forEach(asiento => {
        asiento.addEventListener('click', function() {
            const numero = parseInt(this.getAttribute('data-numero'));

            if (this.classList.contains('seleccionado')) {
                this.classList.remove('seleccionado');
                asientosSeleccionados = asientosSeleccionados.filter(a => a !== numero);
            } else {
                if (asientosSeleccionados.length < maxPasajeros) {
                    this.classList.add('seleccionado');
                    asientosSeleccionados.push(numero);
                } else {
                    alert("Solo puedes seleccionar " + maxPasajeros + " asiento(s).");
                }
            }
            inputAsientos.value = asientosSeleccionados.join(',');

            for (let i = 1; i <= maxPasajeros; i++) {
                const badgeAsiento = document.getElementById('asiento-elegido-' + i);
                if (badgeAsiento) {
                    if (asientosSeleccionados[i - 1] !== undefined) {
                        badgeAsiento.innerText = "Nº " + asientosSeleccionados[i - 1];
                    } else {
                        badgeAsiento.innerText = "Aún no elegido";
                    }
                }
            }

            if (asientosSeleccionados.length > 0) {
                feedback.innerText = "Asientos marcados: " + asientosSeleccionados.join(', ');
            } else {
                feedback.innerText = "Deberás seleccionar " + maxPasajeros + " asiento(s).";
            }
        });
    });

    document.getElementById('form-confirmar').addEventListener('submit', function(e) {
        if (asientosSeleccionados.length !== maxPasajeros) {
            e.preventDefault();
            alert("Debes seleccionar exactamente " + maxPasajeros + " asiento(s) antes de confirmar.");
        }
    });
});