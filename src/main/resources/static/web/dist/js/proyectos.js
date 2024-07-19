// Función para mostrar u ocultar el contenido adicional de la tarjeta
function toggleAdditionalContent(cardId) {
    var card = document.getElementById(cardId);
    var additionalContent = card.querySelector('.additional-content');


    if (additionalContent.style.display === 'block') {
        additionalContent.style.display = 'none';
    } else {
        additionalContent.style.display = 'block';
    }
}

// Agregar un evento de clic a cada tarjeta
document.addEventListener('DOMContentLoaded', function () {
    var cards = document.querySelectorAll('.card');

    cards.forEach(function (card) {
        card.addEventListener('click', function () {
            // Obtener el ID de la tarjeta y llamar a la función toggleAdditionalContent
            var cardId = card.getAttribute('id');
            toggleAdditionalContent(cardId);
        });
    });
});

// Agregar evento de clic al botón "Nuevo"
document.getElementById('btnNuevo').addEventListener('click', function () {
    // Mostrar el modal de registro
    $('#modalRegistro').modal('show');
});

////////////////////////////////////////////////////////////////////////////////////////////////////
/* PARTE JS DE LA PAGINA  */
//////////////////////////////////////////////////////////////////////////////////////////////////////

$(document).ready(function () {
    obtenerProyectos();

    $('#ProyectoNuevo').click(function () {

        var formData = {
            proNombre: $('#NombreProyecto').val(),
            proFechaInicio: $('#FechaInicial').val(),
            proFechaFin: $('#FechaFin').val(),
            proProgreso: $('#ProgresoNuevo').val(),
            proPresupuesto: $('#Presupuesto').val(),
            proEstado: 'En espera',
            proIsActive: 1
        };
        $.ajax({
            type: 'POST',
            url: '/okr/requerimientos/proyectos/create',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function (response) {
                obtenerProyectos();
                $('#modalRegistro').modal('hide');
                toastr.success('Proyecto registrado exitosamente.');
            },
            error: function (error) {
                toastr.error('Error al registrar el proyecto.');
            }
        });
    });

    function obtenerProyectos() {
        $.ajax({
            type: 'GET',
            url: '/okr/requerimientos/proyectos/list',
            success: function (response) {
                mostrarProyectos(response);
            },
            error: function (error) {
                console.error('Error al obtener los proyectos:', error);
            }
        });
    }

    function mostrarProyectos(proyectos) {
        var listaProyectos = $('#project-list');
        listaProyectos.empty();

        proyectos.forEach(function (proyecto) {
            var tarjeta = `
                        <div class="col-md-6">
                            <div class="card p-3 mb-2">
                                <div class="d-flex justify-content-between">
                                    <div class="d-flex flex-row align-items-start">
                                        <div class="icon-container">
                                            <div class="icon">
                                                <i class="mdi mdi-timeline-plus"></i>
                                            </div>                                             
                                        </div>
                                        <div class="details-container ms-2 c-details">
                                            <h6 class="mb-0"><strong>${proyecto.proNombre}</strong></h6>
                                            <span>${proyecto.proDescripcion}</span>
                                        </div>
                                    </div>
                                    <div class="badge"> <span><strong>Proyecto</strong></span> </div>
                                </div>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-7">
                                            <h6 class="heading"><strong>Líder del proyecto:</strong><br>${proyecto.proLider}</h6>
                                        </div>
                                        <div class="col-5">
                                            <h6 class="heading"><strong>Presupuesto:</strong><br>$${proyecto.proPresupuesto}</h6>
                                        </div>
                                    </div>
                                    <div class="mt-5">
                                        <div class="row">
                                            <span class="col-7"><strong>Progreso: </strong>${proyecto.proProgreso}%</span>
                                            <span class="col-5"><strong>Estado: </strong>${proyecto.proEstado}</span>
                                        </div>
                                        <div class="progress mt-2">
                                            <div class="progress-bar" role="progressbar" style="width: ${proyecto.proProgreso}%" aria-valuenow="${proyecto.proProgreso}" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div> 
                                        
                                        <div class="row mt-2">
                                            <div class="col-12">
                                                <strong>Presupuesto restante: </strong>$${proyecto.proPresupuestoRestante}
                                            </div>
                                        </div>
                                    </div>                                         
                                </div>                                   
                                <!-- Íconos de detalles, editar, eliminar -->
                                               
                                                      <div class="card p-3 mb-2" data-project-id="${proyecto.proId}">
                                                                 <!-- Contenido de la tarjeta -->
                                                            <div class="iconos-opciones">
                                                                <i class="fas fa-info-circle" title="Ver detalles"></i>
                                                                <i class="fas fa-edit" title="Editar"></i>
                                                                <i class="fas fa-trash-alt" title="Eliminar"></i>
                                                            </div>
                                                       </div>
                                              
                            </div>                                                      
                        </div>`;
            listaProyectos.append(tarjeta);
        });

        $('.card').hover(function () {
            $(this).find('.iconos-opciones').css('display', 'block');
        }, function () {
            $(this).find('.iconos-opciones').css('display', 'none');
        });
    }


    $(document).on('click', '.fa-trash-alt', function () {
        var projectId = $(this).closest('.card').data('project-id');

        if (confirm('¿Estás seguro de que deseas eliminar este proyecto?')) {
            eliminarProyecto(projectId);
        }
    });

    function eliminarProyecto(projectId) {
        console.log("ID del proyecto a eliminar:", projectId);
        $.ajax({
            type: 'DELETE',
            url: '/okr/requerimientos/proyectos/eliminar/ ' + projectId,
            success: function (response) {
                obtenerProyectos(); // Actualizar la lista de proyectos
                toastr.success('Proyecto eliminado exitosamente.');
            },
            error: function (error) {
                toastr.error('Error al eliminar el proyecto.');
            }
        });
    }
});




