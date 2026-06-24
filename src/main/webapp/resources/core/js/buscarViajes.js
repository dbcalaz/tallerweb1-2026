document.addEventListener("DOMContentLoaded", function() {
    function enlazar(visibleId, ocultoId) {
        const inputVisible = document.getElementById(visibleId);
        const inputOculto = document.getElementById(ocultoId);
        const opciones = document.getElementById('listaParadas').options;

        inputVisible.addEventListener('input', function() {
            inputOculto.value = '';
            for (let i = 0; i < opciones.length; i++) {
                if (opciones[i].value === inputVisible.value) {
                    inputOculto.value = opciones[i].getAttribute('data-id');
                    break;
                }
            }
        });
    }
    enlazar('origenTexto', 'idOrigenOculto');
    enlazar('destinoTexto', 'idDestinoOculto');

    const hoy = new Date();
    let mes = hoy.getMonth() + 1;
    let dia = hoy.getDate();
    if (mes < 10) mes = '0' + mes;
    if (dia < 10) dia = '0' + dia;
    document.getElementById('fecha').setAttribute('min', hoy.getFullYear() + '-' + mes + '-' + dia);
});