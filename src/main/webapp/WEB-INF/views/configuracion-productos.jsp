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
        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-9">
                            <h1 class="card-title">${empresaSession.empNombreComercial}</h1>
                            <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                        </div>
                        <div class="form-group col-md-3 text-md-right">
                            <label class="font-10">${empresaSession.empDocumentoTipo} : ${empresaSession.empDocumentoNumero}</label><br>
                            <label class="font-10">Vendedor: ${usuSessionNombre}</label>
                            <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                        </div>
                    </div>
                </div>
                <div class="container-fluid">
                    <br>
                    <div class="d-flex justify-content-end mb-3">
                        <button class="btn btn-outline-primary mr-2">Ingresar Producto</button>
                        <!--
                        <div class="dropdown">
                            <button class="btn btn-custom dropdown-toggle" type="button" id="createProductMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Crear Producto
                            </button>
                            <div class="dropdown-menu" aria-labelledby="createProductMenuButton">
                                <a class="item-Producto">Product</a>
                            </div>
                        </div>
                        -->
                    </div>

                    <div class="card-body p-2">
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

                        <div class="card-body p-2">
                            <div class="table-responsive">
                                <table id="productoTable" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Imagen</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Precio</th>
                                        <th scope="col">Disponible</th>
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
            .quantity-container {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px;
                margin: 10px 0;
            }
            .quantity-button {
                background-color: #007bff;
                border: none;
                color: white;
                font-size: 1.5rem;
                width: 30px;
                height: 30px;
                border-radius: 5px;
                cursor: pointer;
            }
            .quantity-display {
                font-size: 1.5rem;
                width: 40px;
                text-align: center;
            }

        </style>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

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