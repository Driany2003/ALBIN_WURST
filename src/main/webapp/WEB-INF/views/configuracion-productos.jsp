<%@ page contentType="text/html; charset=UTF-8" %>
<html dir="ltr" lang="en">
<%@ include file="includes/header.jspf" %>
<body>
<!-- ============================================================== -->
<!-- Preloader - style you can find in spinners.css -->
<!-- ============================================================== -->
<%@ include file="includes/preloader.jspf" %>
<!-- ============================================================== -->
<!-- Main wrapper - style you can find in pages.scss -->
<!-- ============================================================== -->
<div id="main-wrapper">
    <!-- ============================================================== -->
    <!-- Topbar header - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <%@ include file="includes/topbar.jspf" %>
    <!-- ============================================================== -->
    <!-- End Topbar header -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <%@ include file="includes/left-sidebar.jspf" %>
    <!-- ============================================================== -->
    <!-- End Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Page wrapper  -->
    <!-- ============================================================== -->
    <!-- =========================MAMASHAROOO===================================== -->
    <div class="page-wrapper">
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">PRODUCTOS <span class="label label-rounded label-info">Listado</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-2">
                            <div class="d-flex justify-content-end mb-2">
                                <button class="btn btn-outline-primary mr-2" data-toggle="modal" data-target="#createProductModal">Ingresar Producto</button>
                                <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                                <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                            </div>
                        </div>
                        <div class="col-md-10">
                            <div class="d-flex justify-content-end mb-2">
                                <div class="input-group" style="width: 300px;">
                                    <input type="text" class="form-control" id="tableFilter" placeholder="Filtra Productos...">
                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="fas fa-search"></i>
                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="card-body p-2">
                        <div class="card-body p-2">
                            <div class="table-responsive">
                                <table id="productoTable" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Precio</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Accion</th>
                                    </tr>
                                    </thead>
                                    <tbody id="productoBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL PARA PODER CREAR UN PRODCUCTO -->
        <div class="modal fade" id="createProductModal" tabindex="-1" role="dialog" aria-labelledby="createProductModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createProductModalLabel">Agregar Producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <form id="createProductForm" role="form" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="nombreProducto">Nombre del Producto</label>
                                <input type="text" class="form-control" id="nombreProducto" placeholder="Ingresar Nombre del Producto" required>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-5">
                                    <label for="precioProducto">Precio</label>
                                    <input type="number" class="form-control" id="precioProducto" placeholder="Ingresar Precio" required>
                                </div>
                                <div class="form-group col-md-7">
                                    <label for="categoria">Categoría</label>
                                    <select class="form-control" id="categoria" required>
                                        <option value="" disabled selected>Seleccionar Una Categoria</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="descripcionProducto">Descripcion del Producto</label>
                                <textarea class="form-control" id="descripcionProducto" rows="3" placeholder="Ingresar la Descripcion del Producto" required></textarea>
                            </div>
                            <div class="form-group text-center">
                                <label for="imagenProducto" class="btn btn-outline-secondary">Agregar Imagen</label>
                                <input type="file" id="imagenProducto" style="display: none;">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary" id="registrarProducto">Agregar Producto</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal de editar producto -->
        <div class="modal fade" id="editProductModal" tabindex="-1" role="dialog" aria-labelledby="editProductModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editProductModalLabel">Editar Producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="editarProductoForm">
                            <div class="form-group">
                                <label for="editNombreProducto">Nombre</label>
                                <input type="text" class="form-control" id="editNombreProducto" required>
                            </div>
                            <div class="form-group">
                                <label for="editPrecioProducto">Precio</label>
                                <input type="number" class="form-control" id="editPrecioProducto" required>
                            </div>
                            <div class="form-group">
                                <label for="editCategoria">Categoría</label>
                                <select class="form-control" id="editCategoria" required></select>
                            </div>
                            <div class="form-group">
                                <label for="editDescripcionProducto">Descripción</label>
                                <textarea class="form-control" id="editDescripcionProducto" rows="3"></textarea>
                            </div>
                            <div id="editImgContainer">
                            </div>
                            <br>
                            <div class="form-group">
                                <label for="editImagenProductoNuevo" class="btn btn-outline-secondary">Editar Imagen</label>
                                <input type="file" id="editImagenProductoNuevo" style="display: none;">
                            </div>
                            <input type="hidden" id="editProductId">
                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <style>
            .switch {
                position: relative;
                display: inline-block;
                width: 40px;
                height: 20px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
                border-radius: 20px;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 16px;
                width: 16px;
                left: 2px;
                bottom: 2px;
                background-color: white;
                transition: .4s;
                border-radius: 50%;
            }

            input:checked + .slider {
                background-color: #673ab7;
            }

            input:focus + .slider {
                box-shadow: 0 0 1px #673ab7;
            }

            input:checked + .slider:before {
                transform: translateX(20px);
            }

            .table {
                background-color: white;
            }

            .modal-header {
                border-bottom: none;
            }

            .modal-title {
                font-weight: bold;
            }

            .form-group label {
                font-weight: bold;
            }

            #productImage {
                margin-top: 10px;
            }

            .modal-footer {
                border-top: none;
            }

            /* Estilo para los campos de entrada del modal */
            /* From Uiverse.io by aunis1616 */
            .coolinput {
                display: flex;
                flex-direction: column;
                width: fit-content;
                position: static;
                max-width: 240px;
            }

            .coolinput label.text {
                font-size: 0.75rem;
                color: #000000;
                font-weight: 700;
                position: relative;
                top: 0.5rem;
                margin: 0 0 0 7px;
                padding: 0 3px;
                background: #e8e8e8;
                width: fit-content;
            }

            .coolinput input[type="text"].input {
                padding: 11px 10px;
                font-size: 0.75rem;
                border: 2px #000000 solid;
                border-radius: 5px;
                background: #e8e8e8;
            }

            .coolinput input[type="text"].input:focus {
                outline: none;
            }


        </style>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!--  modal: PRODUCTOS -->
        <script src="/static/web/dist/js/funciones/producto.js"></script>
        <%@ include file="includes/all-jquery.jspf" %>
        <!-- ============================================================== -->
        <!-- footer -->
        <!-- ============================================================== -->
        <%@ include file="includes/footer.jspf" %>
        <!-- ============================================================== -->
        <!-- End footer -->
        <!-- ============================================================== -->
</body>
</html>