var nombreObjeto = "Complemento";
var urlObjeto = "complemento";
var urlCreate = '/kenpis/' + urlObjeto + '/create';
var urlUpdate = '/kenpis/' + urlObjeto + '/update';
var urlDelete = '/kenpis/' + urlObjeto + '/delete/';
var urlFindAll = '/kenpis/' + urlObjeto + '/find-all';
var urlFindById = '/kenpis/' + urlObjeto + '/find-by-id/';
var tbd = test;

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
                        <td>${dato.proCompNombre}</td>
                        <td>${dato.proCompPrecio}</td>
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
                    console.log("ID SELECCIONADO PARA :: EDITAR :: ", proId);
                    editar(id);
                });

                $('.eliminar').click(function (event) {
                    event.preventDefault();
                    var id = $(this).data('id');
                    console.log("ID SELECCIONADO PARA :: ELIMINAR :: ", proId);
                    eliminar(id);
                });

            },
            error: function () {
                toastr.error('Error al cargar los ' + nombreObjeto);
            }
        });
    }

    // Registrar
    $('#registrar').click(function (event) {
        event.preventDefault();
        var productoData = {
            proCategoria: $('#nombreProducto').val(),
            proPrecioCosto: $('#precioProductoCosto').val(),
            proPrecioVenta: $('#precioProductoVenta').val(),
            padreId: $('#categoria').val(),
            proDescripcion: $('#descripcionProducto').val(),
            proImagen: img_base64,
            proImagenLongitud: img_contenttype,
            empId: empresaId
        }

        $.ajax({
            url: urlCreate,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(productoData),
            success: function (response) {
                toastr.success('Registrado correctamente.');
                $('#createModal').modal('hide');
                cargarTabla();
            },
            error: function () {
                toastr.error('Error al registrar el ' + nombreObjeto + '. Intente nuevamente.');
            }
        });
    });

    //Funcion para editar
    function editar(id) {
        $.ajax({
            url: urlFindById.concat(id),
            method: 'GET',
            success: function (obj) {
                $('#editProductId').val(obj.proId);
                $('#editNombreProducto').val(obj.proCategoria);
                $('#editPrecioProductoCosto').val(obj.proPrecioCosto);
                $('#editPrecioProductoVenta').val(obj.proPrecioVenta);
                $('#editCategoria').val(obj.padreId);
                $('#editModal').modal('show');
            },
            error: function () {
                toastr.error('Error al obtener los detalles del ' + nombreObjeto + ' :: ID :: ' + id);
            }
        });
    }

    $('#editForm').submit(function (event) {
        event.preventDefault();
        var proId = $('#editProductId').val();
        var nombreProducto = $('#editNombreProducto').val();
        var precioProductoCosto = $('#editPrecioProductoCosto').val();
        var precioProductoVenta = $('#editPrecioProductoVenta').val();
        var padreCategoria = $('#editCategoria').val();
        var descripcionProducto = $('#editDescripcionProducto').val();
        var imagenProducto = $('#editImagenProducto').val();
        $.ajax({
            url: urlUpdate,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                proId: proId,
                proCategoria: nombreProducto,
                proPrecioCosto: precioProductoCosto,
                proPrecioVenta: precioProductoVenta,
                padreCategoria: padreCategoria,
                proDescripcion: descripcionProducto,
                proImagen: imagenProducto
            }),
            success: function () {
                $('#editModal').modal('hide');
                toastr.success(nombreObjeto + ' actualizado correctamente.');
                cargarTabla();
            },
            error: function () {
                toastr.error('Error al actualizar el ' + nombreObjeto + '. Intente nuevamente.');
            }
        });
    });


    // Función para eliminar un producto
    function eliminar(id) {
        if (confirm('¿Estás seguro de que deseas eliminar este ' + nombreObjeto + '?')) {
            $.ajax({
                url: urlDelete.concat(id),
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


});

