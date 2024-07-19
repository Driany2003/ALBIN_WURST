// Espera a que el documento esté completamente cargado antes de ejecutar el código JavaScript
document.addEventListener("DOMContentLoaded", function () {
    // Selecciona el enlace "Nuevo"
    var nuevoBtn = document.getElementById("btnNuevo");

    // Agrega un evento de clic al enlace "Nuevo"
    nuevoBtn.addEventListener("click", function () {
        // Abre el modal de registro de épica/HU
        $('#modalRegistroEpica').modal('show');
    });
});

$(document).ready(function () {
    obtenerEpicas();
    $('#EpicaNuevo').click(function () {

        var formData = {
            epiNombre: $('#nombreEpica').val(),
            epiHisUsuario: $('#historiasUsuario').val(),
            epiPrioridad: $('input[name="prioridadEpica"]:checked').val(),
            keyId: parseInt($('#KeyResult').val()),
            sprintId: 1,
            epiEstado: 'Activo',
            epiIsActive: '1'
        };
        $.ajax({
            type: 'POST',
            url: '/okr/requerimientos/epicas-hu/create',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function (response) {
                console.log(response);
                obtenerEpicas();
                $('#modalRegistro').modal('hide');
                toastr.success('Proyecto registrado exitosamente.');
            },
            error: function (error) {
                toastr.error('Error al registrar el proyecto.');
            }
        });
    });

    /* AJAX PARA PODER OBTENER LOS PROYECTOS CREADOS Y SET AL COMBO BOX */
    $.ajax({
        url: '/okr/key-results/list',
        type: 'GET',
        success: function (data) {
            var keyResultCombo = $('#KeyResult');
            keyResultCombo.empty();
            keyResultCombo.append($('<option>', {value: '', text: 'Seleccionar key result'}));
            data.forEach(function (keyResult) {
                keyResultCombo.append($('<option>', {
                    value: keyResult.keyId,
                    text: keyResult.keyNombre
                }));
            });
        },
        error: function (xhr, status, error) {
            console.error('Error al obtener proyecto:', error);
        }
    });


    /* FUNCION AJAX PARA PODER LISTAR LAS EPICAS */
    function obtenerEpicas() {
        $.ajax({
            type: 'GET',
            url: '/okr/requerimientos/epicas-hu/list',
            success: function (response) {
                mostrarEpicas(response);
            },
            error: function (error) {
                console.error('Error al obtener los proyectos:', error);
            }
        });
    }

    function actualizarNombresKeyResult() {
        var tarjetasEpicas = $('.nombre-keyresult');

        tarjetasEpicas.each(function () {
            var spanKeyResult = $(this);
            var keyResultId = spanKeyResult.data('data-keyResult-id');

            console.log('Obteniendo nombre para el Key Result con ID:', keyResultId);

            // Realiza una petición AJAX para obtener el nombre del Key Result
            $.ajax({
                type: 'GET',
                url: '/okr/key-results/' + keyResultId,
                success: function (response) {
                    console.log('Respuesta del servidor para Key Result ID', keyResultId, ':', response);
                    spanKeyResult.text(response);
                },
                error: function (error) {
                    console.error('Error al obtener el nombre del Key Result:', error);
                }
            });
        });
    }

    function mostrarEpicas(epicas) {
        var listaEpicas = $('#project-list');
        listaEpicas.empty();

        epicas.forEach(function (epica) {
            var tarjeta = `
        <div class="col-md-6">
            <div class="card p-3 mb-2">
                <div class="d-flex justify-content-between">
                    <div class="d-flex flex-row align-items-start">
                        <div class="icon-container">
                            <div class="icon">
                                <i class="mdi mdi-folder-outline"></i>
                            </div>
                        </div>
                        <div class="details-container ms-2 c-details">
                            <div class="row">
                                <div class="col-12">
                                    <h6 class="mb-0"><strong>${epica.epiNombre}</strong></h6>
                                </div>
                                <br>
                                <div class="col-12 ms-2">
                                     <h6><strong>Key Result:</strong> <span class="nombre-keyresult" data-keyResult-id="${epica.keyId}">Cargando...</span></h6>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="badge"> <span><strong>Épica</strong></span> </div>
                </div>
                <div class="mt-4">
                    <div class="row">
                        <div class="col-12">
                            <h6 class="mb-0"><strong>Historias de Usuario</strong></h6>
                            <p>${epica.epiHisUsuario}</p>
                        </div>
                        <br>
                        <div class="col-5">
                            <h6 class="heading"><strong>Prioridad</strong></h6>
                            <div class="badges prioridad-${epica.epiPrioridad.toLowerCase()}"> 
                                <span><strong>${epica.epiPrioridad}</strong></span> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`;
            listaEpicas.append(tarjeta);
        });

        actualizarNombresKeyResult();
    }

    obtenerEpicas();
});


