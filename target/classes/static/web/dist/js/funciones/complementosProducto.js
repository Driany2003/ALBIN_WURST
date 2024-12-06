$(document).ready(function() {
    function cargarComplementos() {
        $.ajax({
            url: '/kenpis/productos/complementos',
            method: 'GET',
            success: function(response) {
                if (response.status === 'success') {
                    var complementos = response.data;
                    var tableBody = $('#complementosTableBody');
                    tableBody.empty();

                    complementos.forEach(function(complemento) {
                        var rowHtml = `
                            <tr>
                                <td>${complemento.proCompNombre}</td>
                                <td>${complemento.proCompPrecio}</td>
                                <td>
                                    <button class="btn btn-sm btn-info expandirHijos" data-id="${complemento.proCompId}">Ver Hijos</button>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-sm btn-warning editarComplemento" data-id="${complemento.proCompId}" data-toggle="tooltip" title="Editar Complemento">
                                        <i class="fas fa-pencil-alt"></i>
                                    </button>
                                    <button type="button" class="btn btn-sm btn-danger eliminarComplemento" data-id="${complemento.proCompId}" data-toggle="tooltip" title="Eliminar Complemento">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </td>
                            </tr>
                            <tr class="hijos-${complemento.proCompId}" style="display: none;">
                                <td colspan="4">
                                    <div class="hijos-container">
                                        <table class="table table-sm">
                                            <thead>
                                                <tr>
                                                    <th>Nombre Hijo</th>
                                                    <th>Precio Hijo</th>
                                                </tr>
                                            </thead>
                                            <tbody id="hijosTableBody-${complemento.proCompId}"></tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>`;
                        tableBody.append(rowHtml);
                    });

                    // Asignar funcionalidad de expansión
                    $('.expandirHijos').on('click', function() {
                        var complementoId = $(this).data('id');
                        var hijosRow = $(`.hijos-${complementoId}`);
                        var hijosTableBody = $(`#hijosTableBody-${complementoId}`);

                        if (hijosRow.is(':visible')) {
                            hijosRow.hide();
                        } else {
                            hijosRow.show();
                            cargarHijos(complementoId, hijosTableBody);
                        }
                    });
                } else {
                    toastr.error('Error al cargar los complementos');
                }
            },
            error: function() {
                toastr.error('Error en la solicitud de complementos');
            }
        });
    }

    // Función para cargar los hijos de un complemento específico
    function cargarHijos(complementoId, hijosTableBody) {
        $.ajax({
            url: `/kenpis/productos/complementos/hijos/${complementoId}`,
            method: 'GET',
            success: function(response) {
                if (response.status === 'success') {
                    var hijos = response.data;
                    hijosTableBody.empty();

                    hijos.forEach(function(hijo) {
                        var hijoRowHtml = `
                            <tr>
                                <td>${hijo.proCompNombre}</td>
                                <td>${hijo.proCompPrecio}</td>
                            </tr>`;
                        hijosTableBody.append(hijoRowHtml);
                    });
                } else {
                    toastr.error('Error al cargar los hijos del complemento');
                }
            },
            error: function() {
                toastr.error('Error en la solicitud para cargar los hijos');
            }
        });
    }
    $('#complementosModal').on('show.bs.modal', function() {
        cargarComplementos();
    });
});
