$(document).ready(function () {
    var empresaId = $("#empresaId").val();
    console.log ("hola", empresaId);
    listarCategoria();
    cargarProductos();

    // Listar las categorías para el registro de productos
    function listarCategoria() {
        $.ajax({
            url: '/kenpis/producto/categorias',
            method: 'GET',
            data: {empId: empresaId},
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
            url: '/kenpis/producto/find-all-is-active',
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
                    console.log("ID DEL PRODUCTO A ELEIMINAR SELECCIONADO",proId);
                    eliminarProducto(proId);
                });
                $('.editarProducto').click(function (event) {
                    event.preventDefault();
                    var proId = $(this).data('id');
                    console.log("ID  DEL PRODUCTO SELECCIONADO",proId);
                    editarProducto(proId);
                });

            },
            error: function () {
                toastr.error('Error al cargar los productos.');
            }
        });
    }

    var isJpg = function (name) {
        return name.match(/jpg$/i)
    };
    var isJpeg = function (name) {
        return name.match(/jpeg$/i)
    };

    var isPng = function (name) {
        return name.match(/png$/i)
    };

    // Registrar Producto
    $('#registrarProducto').click(function (event) {
        event.preventDefault();
        var file = $('#imagenProducto');
        var filename = $.trim(file.val());

        alert(filename);

        if (!(isJpg(filename) || isJpeg(filename) || isPng(filename))) {
            alert('Please browse a JPG/PNG file to upload ...');
            return;
        }

        var img_base64 = "";
        var img_contenttype = "";
        form_data = new FormData(); // added
        image = $('#imagenProducto').prop('files')[0]; // modified
        form_data.append('file', image); // added
        console.log(image);

        $.ajax({
            type: "POST",
            url: '/kenpis/producto/echofile',
            data: form_data,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false
        }).done(function (data) {
            //imgContainer.html('');
            //var img = '<img src="data:' + data.contenttype + ';base64,' + data.base64 + '"/>';
            //imgContainer.append(img);
            img_base64 = data.base64;
            img_contenttype = data.contenttype;
            crearProducto(img_base64, img_contenttype);
        }).fail(function (jqXHR, textStatus) {
            //alert(jqXHR.responseText);
            alert('File upload failed ...');
        });
    });

    function crearProducto(img_base64, img_contenttype) {

        var productoData = {
            proCategoria: $('#nombreProducto').val(),
            proPrecio: $('#precioProducto').val(),
            padreId: $('#categoria').val(),
            proDescripcion: $('#descripcionProducto').val(),
            proImagen: img_base64,
            proImagenLongitud: img_contenttype,
            empId: empresaId
        }
        $.ajax({
            url: '/kenpis/producto/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(productoData),
            success: function (response) {
                toastr.success('Producto registrado correctamente.');
                $('#createProductModal').modal('hide');
                cargarProductos();
            },
            error: function () {
                toastr.error('Error al registrar el producto. Intente nuevamente.');
            }
        });
    }

    //Funcion para editar un Producto
    function editarProducto(proId) {
        var imgContainer = $('#editImgContainer');
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
                imgContainer.html('');
                var img = '<img src="data:' + producto.proImagenLongitud + ';base64,' + producto.proImagen + '" height="40%" width="50%"/>';
                imgContainer.append(img);
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

