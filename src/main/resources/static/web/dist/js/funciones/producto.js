$(document).ready(function () {
    listarCategoria();
    cargarProductos();

    // Listar las categorías para el registro de productos
    function listarCategoria() {
        $.ajax({
            url: '/kenpis/producto/categorias',
            method: 'GET',
            success: function (data) {
                var seleccionarCategoria = $('#categoria');
                var editCategoria = $('#editCategoria');
                seleccionarCategoria.empty();
                editCategoria.empty();
                seleccionarCategoria.append('<option value="" disabled selected>Seleccionar Una Categoria</option>');
                editCategoria.append('<option value="" disabled selected> Seleccionar Nueva Categoria</option>');

                function construirOpciones(categorias, nivel = 0) {
                    categorias.forEach(function (categoria) {
                        var opcion = '<option value="' + categoria.proId + '">' + categoria.proCategoria + '</option>';
                        seleccionarCategoria.append(opcion);
                        editCategoria.append(opcion);
                        if (categoria.categorias && categoria.categorias.length > 0) {
                            construirOpciones(categoria.categorias, nivel + 1);
                        }
                    });
                }

                construirOpciones(data);
            },
            error: function (error) {
                console.error('Error al obtener las categorías:', error);
            }
        });
    }

    function cargarProductos() {
        $.ajax({
            type: 'GET',
            url: '/kenpis/producto/find-all',
            contentType: 'application/json',
            success: function (response) {
                const productoBody = $('#productoBody');
                productoBody.empty();
                response.forEach(producto => {
                    productoBody.append(`
                    <tr id="product-row-${producto.proId}">
                        <td><img src="data:image/jpeg;base64,${producto.proImagen}" alt="${producto.proDescripcion}" width="50"> ${producto.proDescripcion}</td>
                        <td> S/. ${producto.proPrecio.toFixed(2)}</td>
                       <td>
                            <label class="switch">
                                <input type="checkbox" class="estado-checkbox"  data-id="${producto.proId}"  ${producto.proIsActive ? 'checked' : ''}>
                                <span class="slider"></span>
                            </label>
                       </td>
                       <td>
                              <button type="button" data-id="${producto.proId}" class="btn btn-sm btn-warning editarProducto" data-toggle="tooltip" data-placement="top" title="Editar Producto">
                                <i class="fas fa-pencil-alt"></i>
                             </button>
                              <button type="button" data-id="${producto.proId}" class="btn btn-sm btn-danger eliminarProducto" data-toggle="tooltip" data-placement="top" title="Eliminar Producto">
                                <i class="fas fa-trash"></i>
                              </button>
                       </td>
                    </tr>
                `);
                });

                $('.eliminarProducto').click(function (event) {
                    event.preventDefault();
                    var proId = $(this).data('id');
                    eliminarProducto(proId);
                });
                $('.editarProducto').click(function (event) {
                    event.preventDefault();
                    var proId = $(this).data('id');
                    editarProducto(proId);
                });

            },
            error: function () {
                toastr.error('Error al cargar los productos.');
            }
        });
    }

    // Registrar Producto
    $('#registrarProducto').click(function () {
        var nombreProducto = $('#nombreProducto').val();
        var precioProducto = $('#precioProducto').val();
        var categoriaProducto = $('#categoria').val();
        var descripcionProducto = $('#descripcionProducto').val();
        var imagenProducto = $('#imagenProducto').val();
        $.ajax({
            url: '/kenpis/producto/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                proCategoria: nombreProducto,
                proPrecio: precioProducto,
                padreId: categoriaProducto,
                proDescripcion: descripcionProducto,
                proImagen: imagenProducto,
                proIsActive: true,
                empId: 1
            }),
            success: function (response) {
                $('#nombreProducto').val('');
                $('#precioProducto').val('');
                $('#categoria').val('');
                $('#descripcionProducto').val('');
                $('#imagenProducto').val('');
                toastr.success('Producto registrado correctamente.');
                cargarProductos();
            },
            error: function () {
                toastr.error('Error al registrar el producto. Intente nuevamente.');
            }
        });
    });

    //Funcion para editar un Producto
    function editarProducto(proId) {
        $.ajax({
            url: `/kenpis/producto/find-by-id/${proId}`,
            method: 'GET',
            success: function (producto) {
                $('#editProductId').val(producto.proId);
                $('#editNombreProducto').val(producto.proCategoria);
                $('#editPrecioProducto').val(producto.proPrecio);
                $('#editCategoria').val(producto.padreId);
                $('#editDescripcionProducto').val(producto.proDescripcion);
                $('#editImagenProducto').val(producto.proImagen);
                $('#editProductModal').modal('show');
            },
            error: function () {
                toastr.error('Error al obtener los detalles del producto.');
            }
        });
    }

    $('#editarProductoForm').submit(function (event) {
        event.preventDefault();
        var proId = $('#editProductId').val();
        var nombreProducto = $('#editNombreProducto').val();
        var precioProducto = $('#editPrecioProducto').val();
        var padreCategoria = $('#editCategoria').val();
        var descripcionProducto = $('#editDescripcionProducto').val();
        var imagenProducto = $('#editImagenProducto').val();
        $.ajax({
            url: `/kenpis/producto/update`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                proId: proId,
                proCategoria: nombreProducto,
                proPrecio: precioProducto,
                padreCategoria: padreCategoria,
                proDescripcion: descripcionProducto,
                proImagen: imagenProducto
            }),
            success: function () {
                $('#editProductModal').modal('hide');
                toastr.success('Producto actualizado correctamente.');
                cargarProductos();
            },
            error: function () {
                toastr.error('Error al actualizar el producto. Intente nuevamente.');
            }
        });
    });


    // Función para eliminar un producto
    function eliminarProducto(proId) {
        if (confirm('¿Estás seguro de que deseas eliminar este producto?')) {
            $.ajax({
                url: `/kenpis/producto/delete/${proId}`,
                method: 'DELETE',
                success: function () {
                    toastr.success('Producto eliminado correctamente.');
                    cargarProductos();
                    // $(`#product-row-${proId}`).remove(); para no recargar la pagina.
                },
                error: function () {
                    toastr.error('Error al eliminar el producto. Intente nuevamente.');
                }
            });
        }
    }

    //Change de estado
    $(document).on('change', '.estado-checkbox', function () {
        var checkbox = $(this);
        var productId = checkbox.data('id');
        var isActive = checkbox.is(':checked');
        console.log('Product ID:', productId);
        console.log('Is Active:', isActive);
        $.ajax({
            url: '/kenpis/producto/update/status',
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                proId: productId,
                proIsActive: isActive
            }),
            success: function () {
                toastr.success('Estado del producto actualizado correctamente.');
            },
            error: function () {
                toastr.error('Error al actualizar el estado del producto. Intente nuevamente.');
            }
        });
    });


});

