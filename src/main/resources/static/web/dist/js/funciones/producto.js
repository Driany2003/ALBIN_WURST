$(document).ready(function () {
    var empresaId = $("#empresaId").val();
    let complementosSeleccionados = [];
    listarCategoria();
    cargarProductos();

    function listarCategoria() {
        $.ajax({
            url: '/kenpis/producto/categorias',
            method: 'GET',
            data: {empId: empresaId},
            success: function (data) {
                var seleccionarCategoria = $('#categoria');
                var editCategoria = $('#editCategoria');
                var complementosContainer = $('#complementos-container');

                seleccionarCategoria.empty();
                editCategoria.empty();
                complementosContainer.empty();

                seleccionarCategoria.append('<option value="" disabled selected>Seleccionar Categoria</option>');
                editCategoria.append('<option value="" disabled selected> Seleccionar Nueva Categoria</option>');

                // Función para construir las opciones de categoría
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

                // Construimos las opciones de categorías
                construirOpciones(data.categorias);

                // Función para construir los complementos
                function construirComplementos(complementos) {

                    complementos.forEach(function (complemento) {
                        var complementoContainer = $('<div class="complemento-section"></div>');

                        var complementoNombre = $('<h6></h6>').text(complemento.proCompNombre);

                        var switchLabel = $('<label>').addClass('switch-complemento');
                        var inputSwitch = $('<input>')
                            .attr({
                                type: 'checkbox',
                                class: 'complemento-checkbox',
                                'data-id': complemento.proCompId
                            })
                            .on('change', function () {
                                const complementoId = $(this).data('id');


                                if ($(this).is(':checked')) {
                                    // Agregar el ID al arreglo de complementos seleccionados si está marcado
                                    if (!complementosSeleccionados.includes(complementoId)) {
                                        complementosSeleccionados.push(complementoId);
                                    }
                                } else {
                                    // Eliminar el ID del arreglo si está desmarcado
                                    complementosSeleccionados = complementosSeleccionados.filter(id => id !== complementoId);
                                }

                                console.log("Complementos seleccionados:", complementosSeleccionados);
                            });

                        if (complemento.proCompIsActive) {
                            inputSwitch.attr('checked', 'checked');
                            complementosSeleccionados.push(complemento.proCompId);
                        }

                        var sliderSpan = $('<span>').addClass('slider-complemento');

                        switchLabel.append(inputSwitch);
                        switchLabel.append(sliderSpan);

                        var subcomplementosList = $('<small></small>');
                        if (complemento.subComplementos) {
                            var subcomplementos = complemento.subComplementos.split(', ');
                            subcomplementosList.text(subcomplementos.join(', '));
                        }

                        var headerContainer = $('<div class="complemento-header d-flex justify-content-between align-items-center"></div>');
                        headerContainer.append(complementoNombre);
                        headerContainer.append(switchLabel);

                        complementoContainer.append(headerContainer);
                        complementoContainer.append(subcomplementosList);

                        $('#complementos-container').append(complementoContainer);
                    });

                    return complementosSeleccionados;
                }



                // Construimos los complementos con los datos recibidos
                construirComplementos(data.complementos);
            },
            error: function (error) {
                console.error('Error al obtener las categorías y complementos:', error);
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
                    <td>
                      <img src="${producto.proImagen}" alt="Logo" style="width: 50px; height: 50px; vertical-align: middle;">
                      <span>${producto.proDescripcion}</span>
                    <td>
                        <td>S/. ${producto.proPrecioCosto.toFixed(2)}</td>
                        <td>S/. ${producto.proPrecioVenta.toFixed(2)}</td>
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
                    console.log("ID DEL PRODUCTO A ELEIMINAR SELECCIONADO", proId);
                    eliminarProducto(proId);
                });
                $('.editarProducto').click(function (event) {
                    event.preventDefault();
                    var proId = $(this).data('id');
                    console.log("ID  DEL PRODUCTO SELECCIONADO", proId);
                    editarProducto(proId);
                });

            },
            error: function () {
                toastr.error('Error al cargar los productos.');
            }
        });
    }

    $('#imagenProducto').on('change', function () {
        var file = this.files[0];
        if (file) {
            if (file.type === 'image/png' || file.type === 'image/jpeg') {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#imagenPreview').attr('src', e.target.result).css({width: '100px', height: '100px'}).show();
                };
                reader.readAsDataURL(file);
            } else {
                toastr.error('Solo se permiten imágenes en formato PNG o JPG.');
                $('#imagenPreview').val('');
                $('#imagenPreview').hide();
            }
        } else {
            $('#imagenPreview').hide();
        }
    });

    // Registrar Producto
    $('#registrarProducto').click(function (event) {
        let complementosSeleccionadosString = complementosSeleccionados.join(',');
        var productoData = {
            proCategoria: $('#nombreProducto').val(),
            proPrecioCosto: $('#precioProductoCosto').val(),
            proPrecioVenta: $('#precioProductoVenta').val(),
            padreId: $('#categoria').val(),
            proComplementos :complementosSeleccionadosString,
            proDescripcion: $('#descripcionProducto').val(),
            proImagen: $('#imagenPreview').attr('src'),
            proImagenLongitud: "campo vacio",
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
    });


    //Funcion para editar un Producto
    function editarProducto(proId) {
        var imgContainer = $('#editImgContainer');
        $.ajax({
            url: `/kenpis/producto/find-by-id/${proId}`,
            method: 'GET',
            success: function (producto) {
                $('#editProductId').val(producto.proId);
                $('#editNombreProducto').val(producto.proCategoria);
                $('#editPrecioProductoCosto').val(producto.proPrecioCosto);
                $('#editPrecioProductoVenta').val(producto.proPrecioVenta);
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
        var precioProductoCosto = $('#editPrecioProductoCosto').val();
        var precioProductoVenta = $('#editPrecioProductoVenta').val();
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
                proPrecioCosto: precioProductoCosto,
                proPrecioVenta: precioProductoVenta,
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

