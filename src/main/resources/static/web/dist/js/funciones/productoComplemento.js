var nombreObjeto = "Complemento";
var urlObjeto = "complemento";
var urlCreateComplementoPadre = '/kenpis/' + urlObjeto + '/create-complemento';
var urlCreate = '/kenpis/' + urlObjeto + '/create';
var urlUpdate = '/kenpis/' + urlObjeto + '/update';
var urlUpdatePadre = '/kenpis/' + urlObjeto + '/update-padre';
var urlDelete = '/kenpis/' + urlObjeto + '/delete/';
var urlDeletePadre = '/kenpis/' + urlObjeto + '/delete-padre/';
var urlFindAll = '/kenpis/' + urlObjeto + '/find-all';
var urlFindById = '/kenpis/' + urlObjeto + '/find-complemento-detalles/';

$(document).ready(function () {
    var empresaId = $("#empresaId").val();
    cargarTabla();

    function cargarTabla() {
        $.ajax({
            type: 'GET',
            url: urlFindAll,
            contentType: 'application/json',
            success: function (response) {
                const tablaBody = $('#tablaBody');
                tablaBody.empty();
                response.forEach(dato => {
                    tablaBody.append(`
                    <tr id="row-${dato.proCompId}">
                        <td>${dato.empNombreComercial}</td>
                        <td>${dato.proCompNombre}</td>
                        <td>
                            <button type="button" data-id="${dato.proCompId}" class="btn btn-sm btn-warning editar" data-toggle="tooltip" data-placement="top" title="Editar">
                                <i class="fas fa-pencil-alt"></i>
                            </button>
                            <button type="button" data-id="${dato.proCompId}" class="btn btn-sm btn-danger eliminar" data-toggle="tooltip" data-placement="top" title="Eliminar">
                                <i class="fas fa-trash"></i>
                            </button>
                       </td>
                    </tr>
                `);
                });

                $('.editar').click(function (event) {
                    event.preventDefault();
                    var id = $(this).data('id');
                    editar(id);
                });

                $('.eliminar').click(function (event) {
                    event.preventDefault();
                    var id = $(this).data('id');
                    eliminarPadre(id);
                });
            },
            error: function () {
                toastr.error('Error al cargar los ' + nombreObjeto);
            }
        });
    }

    // Registrar complemento "Padre"
    $('#registrarComplemento').click(function (event) {
        event.preventDefault();
        var tipoComplemento = $('#complementoTipo').val();

        if (!tipoComplemento) {
            toastr.error('Por favor, ingresa el tipo de complemento.');
            return;
        }
        var detalles = [];
        $('#complementosTableBody tr').each(function () {
            var nombre = $(this).find('input[id^="complementoNombre"]').val();
            var precio = parseFloat($(this).find('input[id^="complementoPrecio"]').val());
            if (!nombre || isNaN(precio) || precio < 0) {
                toastr.error('Por favor, ingresa un nombre y un precio positivo para cada detalle.');
                return;
            }
            detalles.push({
                proCompNombre: nombre,
                proCompPrecio: precio

            });
        });
        if (detalles.length === 0) {
            toastr.error('Por favor, agrega al menos un detalle.');
            return;
        }

        var complementoData = {
            empId : empresaId,
            proCompNombre: tipoComplemento,
            detalles: detalles
        };
        $.ajax({
            url: urlCreateComplementoPadre,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(complementoData),
            success: function () {
                toastr.success('Registrado correctamente.');
                $('#createModal').modal('hide');
                cargarTabla();
            },
            error: function () {
                toastr.error('Error al registrar el ' + nombreObjeto + '. Intente nuevamente.');
            }
        });
    });


    // registrar complemento "Hijo"
    function guardarNuevaFila(fila) {
        const nombre = fila.find('.nombre-detalle').val();
        const precio = parseFloat(fila.find('.precio-detalle').val());
        const proId = $('#editProductId').val();

        if (!nombre || isNaN(precio) || precio < 0) {
            toastr.error('Por favor, ingresa un nombre válido y un precio positivo.');
            return;
        }

        $.ajax({
            url: urlCreate,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                proCompIdPadre: proId,
                proCompNombre: nombre,
                proCompPrecio: precio
            }),
            success: function (response) {
                toastr.success('Nuevo complemento agregado correctamente.');
                fila.removeClass('new-complemento');
                fila.find('.nombre-detalle').prop('disabled', true);
                fila.find('.precio-detalle').prop('disabled', true);
                fila.removeClass('new-complemento');
                fila.removeClass('modificado');
                fila.attr('data-id', response.proCompId);
                const botones = `
                <td class="text-center">
                    <button type="button" class="btn btn-sm btn-warning editar-detalle mr-1" data-toggle="tooltip" data-placement="top" title="Editar">
                        <i class="fas fa-pencil-alt"></i>
                    </button>
                    <button type="button" class="btn btn-sm btn-danger eliminar-detalle" data-toggle="tooltip" data-placement="top" title="Eliminar">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
                `;
                fila.append(botones);

                fila.find('.editar-detalle').click(function (event) {
                    event.preventDefault();
                    fila.find('.nombre-detalle').prop('disabled', false);
                    fila.find('.precio-detalle').prop('disabled', false);
                    fila.data('editing', true);
                });

                fila.find('.eliminar-detalle').click(function (event) {
                    event.preventDefault();
                    const id = fila.data('id');
                    eliminarDetalle(id);
                });
            },
            error: function () {
                toastr.error('Error al agregar el nuevo complemento. Intente nuevamente.');
            }
        });
    }

    // actualizar complemento "Hijo"
    function guardarFilaModificada(fila) {
        if (fila.hasClass('modificado')) {
            const proId = fila.data('id');
            const nombre = fila.find('.nombre-detalle').val();
            const precio = parseFloat(fila.find('.precio-detalle').val());

            // Realizar la solicitud de actualización solo de la fila modificada
            $.ajax({
                url: urlUpdate,
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({
                    proCompId: proId,
                    proCompNombre: nombre,
                    proCompPrecio: precio
                }),
                success: function () {
                    fila.find('.nombre-detalle').prop('disabled', true);
                    fila.find('.precio-detalle').prop('disabled', true);
                    fila.removeClass('modificado');
                    toastr.success('Detalle actualizado correctamente.');
                },
                error: function () {
                    toastr.error('Error al actualizar el detalle. Intente nuevamente.');
                }
            });
        }
    }

    //editar nombre complemento padre
        $('#complementoTipoEdit').on('focusout', function () {
            const nombrePadre = $(this).val().trim();
            const proCompId = $('#editProductId').val();

            if (nombrePadre === '') {
                toastr.error('Por favor, ingresa un nombre válido para el tipo de complemento.');
                return;
            }

            $.ajax({
                url: urlUpdatePadre,
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({
                    proCompId: proCompId,
                    proCompNombre: nombrePadre,
                }),
                success: function () {
                    toastr.success('Tipo de complemento actualizado correctamente.');
                    cargarTabla();
                },
                error: function () {
                    toastr.error('Error al actualizar el tipo de complemento. Intente nuevamente.');
                }
            });
        });


    // Función para editar
    function editar(id) {
        $.ajax({
            url: urlFindById.concat(id),
            method: 'GET',
            success: function (response) {
                if (response.status === 'success') {
                    const complementos = response.complemento;

                    $('#editProductId').val(complementos.proCompId);
                    $('#complementoTipoEdit').val(complementos.proCompNombre);

                    $('#complementosTableBodyEdit').empty();

                    if (Array.isArray(response.detalles)) {
                        response.detalles.forEach((detalle, index) => {
                            const row = `
                      <tr data-id="${detalle.proCompId}" data-index="${index}">
                            <td>
                                <input type="text" class="form-control nombre-detalle" value="${detalle.proCompNombre}" disabled required>
                            </td>
                            <td>
                                <input type="number" class="form-control precio-detalle" value="${detalle.proCompPrecio}" disabled required>
                            </td>
                            <td class="text-center">
                                <button type="button" class="btn btn-sm btn-warning editar-detalle mr-1" data-index="${index}" data-toggle="tooltip" data-placement="top" title="Editar">
                                    <i class="fas fa-pencil-alt"></i>
                                </button>
                                <button type="button" class="btn btn-sm btn-danger eliminar-detalle" data-id="${detalle.proCompId}" data-index="${index}" data-toggle="tooltip" data-placement="top" title="Eliminar">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </td>                          
                        </tr>
                        `;
                            $('#complementosTableBodyEdit').append(row);
                        });

                        $('.eliminar-detalle').click(function (event) {
                            event.preventDefault();
                            var id = $(this).data('id');
                            eliminarDetalle(id);
                        });

                        $('.editar-detalle').click(function (event) {
                            event.preventDefault();
                            const index = $(this).data('index');
                            const row = $(`#complementosTableBodyEdit tr[data-index="${index}"]`);

                            row.data('editing', true);
                            row.find('.nombre-detalle').prop('disabled', false);
                            row.find('.precio-detalle').prop('disabled', false);
                        });
                    } else {
                        toastr.error('complemento.detalles no es un array o está indefinido', complementos.detalles);
                    }

                    $('#editModal').modal('show');
                } else {
                    toastr.error('Error al obtener los detalles del complemento.');
                }
            },
            error: function () {
                toastr.error('Error al obtener los detalles del ' + nombreObjeto + ' :: ID :: ' + id);
            }
        });
    }

    // Función para habilitar la edición de un detalle específico
    $(document).on('click', '.editar-detalle', function () {
        const index = $(this).data('index');
        const row = $(`#complementosTableBodyEdit tr[data-index="${index}"]`);

        row.data('editing', true);
        row.find('.nombre-detalle').prop('disabled', false);
        row.find('.precio-detalle').prop('disabled', false);
    });

    // Agregar fila dinámica en el formulario de edición
    $(document).on('click', '.btn-add-row', function (event) {
        event.preventDefault();
        const newRow = `
            <tr class="new-complemento" data-id="0">
                <td>
                    <input type="text" class="form-control nombre-detalle" placeholder="Nombre" required>
                </td>
                <td>
                    <input type="number" class="form-control precio-detalle" placeholder="Precio" required>
                </td>
             
            </tr>
        `;
        $('#complementosTableBodyEdit').append(newRow);
    });

    // Eliminar fila dinámica
    $(document).on('click', '.btn-remove-row', function () {
        $(this).closest('tr').remove();
    });

    // Detectar cambios en los campos de nombre y precio para marcar la fila como modificada
    $(document).on('input', '.nombre-detalle, .precio-detalle', function () {
        $(this).closest('tr').addClass('modificado');
    });

    // Guardar cambios cuando se haga clic fuera de los campos de edición
    $(document).on('focusout', '.nombre-detalle, .precio-detalle', function () {
        const fila = $(this).closest('tr');

        setTimeout(function () {
            if (fila.data('editing')) {
                if (fila.find(':focus').length === 0) {
                    guardarFilaModificada(fila);
                    fila.data('editing', false);
                }
            } else if (fila.hasClass('new-complemento')) {
                if (fila.find(':focus').length === 0) {
                    guardarNuevaFila(fila);
                }
            }
        }, 200);
    });

    // Función para eliminar un complemento "Padre"
    function eliminarPadre(id) {
        if (confirm('¿Estás seguro de que deseas eliminar este ' + nombreObjeto + '?')) {
            $.ajax({
                url: urlDeletePadre.concat(id),
                method: 'DELETE',
                success: function () {
                    toastr.success(nombreObjeto + ' eliminado correctamente.');
                    cargarTabla();
                },
                error: function () {
                    toastr.error('Error al eliminar el ' + nombreObjeto + ' con ID :' + id + '. Intente nuevamente.');
                }
            });
        }
    }

    // Función para eliminar un complemento "Hijo"
    function eliminarDetalle(id) {
        if (confirm('¿Estás seguro de que deseas eliminar este ' + nombreObjeto + '?')) {
            $.ajax({
                url: urlDelete.concat(id),
                method: 'DELETE',
                success: function () {
                    toastr.success(nombreObjeto + ' eliminado correctamente.');
                    const complementoPadreId = $('#editProductId').val();
                    editar(complementoPadreId);
                },
                error: function () {
                    toastr.error('Error al eliminar el ' + nombreObjeto + ' con ID :' + id + '. Intente nuevamente.');
                }
            });
        }
    }
});
