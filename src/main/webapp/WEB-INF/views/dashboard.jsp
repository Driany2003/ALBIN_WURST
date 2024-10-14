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
        <!-- ============================================================== -->
        <!-- Bread crumb and right sidebar toggle -->
        <!-- ============================================================== -->
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">DASHBOARD <span class="label label-rounded label-info">Pedidos</span></h4>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- End Bread crumb and right sidebar toggle -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Container fluid  -->
        <!-- ============================================================== -->

        <div class="container-fluid">
            <div class="card p-1">
                <!-- ============================================================== -->
                <!-- DATOS GENERALES -->
                <!-- ============================================================== -->
                <div class="card-body p-1">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="card registrado-superior-card">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-7">
                                            <i class="mdi mdi-account-check font-24 text-info"></i>
                                            <p class="font-18 font-bold m-b-5 text-truncate">Registrado</p>
                                        </div>
                                        <div class="col-5">
                                            <h1 class="font-light text-right mb-0" id="registradoCount">${pedidosEstado.registrado}</h1>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class=" card pagado-superior-card">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-7">
                                            <i class="mdi mdi-currency-usd font-24 text-success"></i>
                                            <p class="font-18 font-bold m-b-5 text-truncate">Pagado</p>
                                        </div>
                                        <div class="col-5">
                                            <h1 class="font-light text-right mb-0" id="pagadoCount">${pedidosEstado.pagado}</h1>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class=" card en-proceso-superior-card">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-7">
                                            <i class="mdi mdi-av-timer font-24 text-purple"></i>
                                            <p class="font-18 font-bold m-b-5 text-truncate">En proceso</p>
                                        </div>
                                        <div class="col-5">
                                            <h1 class="font-light text-right mb-0" id="enProcesoCount">${pedidosEstado.en_Proceso}</h1>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class=" card atendido-card-superior">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-7">
                                            <i class="mdi mdi-check-all font-24 text-danger"></i>
                                            <p class="font-18 font-bold m-b-5 text-truncate">Atendido</p>
                                        </div>
                                        <div class="col-5">
                                            <h1 class="font-light text-right mb-0" id="atendidoCount">${pedidosEstado.atendido}</h1>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- ============================================================== -->
                <!-- SEGUIMIENTO -->
                <!-- ============================================================== -->

                <div class="card-body p-1">
                    <div class="row" id="CARD_SELECTOR">
                        <div class="col-lg-3">
                            <div class="card-registrado">
                                <div class="card-body p-3 ">
                                    <div id="REGISTRADO" class="task-list">
                                        <c:forEach var="entry" items="${REGISTRADO}">
                                            <div class="card mb-2 shadow-sm" data-venEstadoId="${entry.venEstadoId}">
                                                <div class="card-body p-3">
                                                    <div class="d-flex justify-content-between align-items-start">
                                                        <div>
                                                            <h5 class="card-title mb-2">
                                                                <i class="fas fa-user"> </i> ${entry.clienteNombre}
                                                            </h5>
                                                        </div>
                                                        <div class="text-right" style="font-size: 12px;">
                                                            <p class="mb-0"><strong>Total: S/ ${entry.venTotal}</strong></p>
                                                            <p class="mb-0" style="font-size: 13px;">(${entry.venTipoPago})</p>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="productos-section mt-2">
                                                        <c:forEach var="producto" items="${entry.productos}">
                                                            <div class="producto-item">
                                                                <p class="mb-0">
                                                                        ${producto.proDescripcion}
                                                                </p>
                                                                <p><strong>${producto.venDetCantidad} x S/${producto.proPrecio}</strong></p>
                                                            </div>
                                                            <hr>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-3">
                            <div class="card-pagado">
                                <div class="card-body p-3">
                                    <div id="PAGADO" class="task-list">
                                        <c:forEach var="entry" items="${PAGADO}">
                                            <div class="card mb-2 shadow-sm" data-venEstadoId="${entry.venEstadoId}">
                                                <div class="card-body p-3">
                                                    <div class="d-flex justify-content-between align-items-start">
                                                        <div>
                                                            <h5 class="card-title mb-2">
                                                                <i class="fas fa-user"> </i> ${entry.clienteNombre}
                                                            </h5>
                                                        </div>
                                                        <div class="text-right" style="font-size: 12px;">
                                                            <p class="mb-0"><strong>Total: S/ ${entry.venTotal}</strong></p>
                                                            <p class="mb-0" style="font-size: 13px;">(${entry.venTipoPago})</p>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="productos-section mt-2">
                                                        <c:forEach var="producto" items="${entry.productos}">
                                                            <div class="producto-item">
                                                                <p class="mb-0">
                                                                        ${producto.proDescripcion}
                                                                </p>
                                                                <p><strong>${producto.venDetCantidad} x S/${producto.proPrecio}</strong></p>
                                                            </div>
                                                            <hr>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="en-proceso-card">
                                <div class="card-body p-3">
                                    <div id="EN_PROCESO" class="task-list">
                                        <c:forEach var="entry" items="${EN_PROCESO}">
                                            <div class="card mb-2 shadow-sm " data-venEstadoId="${entry.venEstadoId}">
                                                <div class="card-body p-3">
                                                    <div class="d-flex justify-content-between align-items-start">
                                                        <div>
                                                            <h5 class="card-title mb-2">
                                                                <i class="fas fa-user"> </i> ${entry.clienteNombre}
                                                            </h5>
                                                        </div>
                                                        <div class="text-right" style="font-size: 12px;">
                                                            <p class="mb-0"><strong>Total: S/ ${entry.venTotal}</strong></p>
                                                            <p class="mb-0" style="font-size: 13px;">(${entry.venTipoPago})</p>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="productos-section mt-2">
                                                        <c:forEach var="producto" items="${entry.productos}">
                                                            <div class="producto-item">
                                                                <p class="mb-0">
                                                                        ${producto.proDescripcion}
                                                                </p>
                                                                <p><strong>${producto.venDetCantidad} x S/${producto.proPrecio}</strong></p>
                                                            </div>
                                                            <hr>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="atendido-card">
                                <div class="card-body p-3">
                                    <div id="ATENDIDO" class="task-list ">
                                        <c:forEach var="entry" items="${ATENDIDO}">
                                            <div class="card mb-2 shadow-sm" data-venEstadoId="${entry.venEstadoId}">
                                                <div class="card-body p-3">
                                                    <div class="d-flex justify-content-between align-items-start">
                                                        <div>
                                                            <h5 class="card-title mb-2">
                                                                <i class="fas fa-user"> </i> ${entry.clienteNombre}
                                                            </h5>
                                                        </div>
                                                        <div class="text-right" style="font-size: 12px;">
                                                            <p class="mb-0"><strong>Total: S/ ${entry.venTotal}</strong></p>
                                                            <p class="mb-0" style="font-size: 13px;">(${entry.venTipoPago})</p>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="productos-section mt-2">
                                                        <c:forEach var="producto" items="${entry.productos}">
                                                            <div class="producto-item">
                                                                <p class="mb-0">
                                                                        ${producto.proDescripcion}
                                                                </p>
                                                                <p><strong>${producto.venDetCantidad} x S/${producto.proPrecio}</strong></p>
                                                            </div>
                                                            <hr>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="includes/footer.jspf" %>
    </div>

    <style>

        .card {
            border: none;
            margin-bottom: 1rem;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
        }

        .registrado-superior-card .card-body {
            background-color: #add8e6;
        }

        .pagado-superior-card .card-body {
            background-color: #ffb6c1;
        }

        .en-proceso-superior-card .card-body {
            background-color: #ffa07a;
        }

        .atendido-card-superior .card-body {
            background-color: #90ee90;

        }

        .atendido-card {
            border: 2px solid #90ee90;
        }

        .en-proceso-card {
            border: 2px solid #ffa07a;
        }

        .card-pagado {
            border: 2px solid #ffb6c1;
        }

        .card-registrado {
            border: 2px solid #add8e6;
        }

        .card:hover {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: bold;
            color: #333;
        }

        .productos-section {
            padding: 0;
            list-style: none;
        }

        .producto-item {
            margin-bottom: 10px;
        }

        .total-section {
            background-color: #f8f9fa;
            padding: 10px;
            border-radius: 8px;
        }

        .total-section p {
            margin: 0;
            font-size: 1.1rem;
            font-weight: bold;
            color: #333;
        }

        hr {
            margin: 10px 0;
            border: 0;
            border-top: 1px solid #eee;
        }

        .card-text {
            font-size: 14px;
        }

        .task-list {
            max-height: 300px;
            overflow-y: auto;
        }

    </style>

    <!-- This page plugin CSS -->
    <link href="/static/web/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css" rel="stylesheet">
    <link href="https://cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/web/assets/extra-libs/taskboard/css/lobilist.css">
    <link rel="stylesheet" href="/static/web/assets/extra-libs/taskboard/css/jquery-ui.min.css">
    <link rel="stylesheet" href="/static/web/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
    <link href="/static/web/dist/css/style.min.css" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/dragula/3.7.3/dragula.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.14.0/Sortable.min.js"></script>

    <script src="/static/web/dist/js/funciones/dashboard.js"></script>
    <script src="/static/web/dist/js/funciones/taskboard.js"></script>

    <%@ include file="includes/all-jquery.jspf" %>
</body>
</html>
