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
            <!-- ============================================================== -->
            <!-- DATOS GENERALES -->
            <!-- ============================================================== -->
            <div class="row">
                <div class="col-lg-3 col-md-4 col-sm-6 mb-2">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-account-check font-20 text-info"></i>
                                    <p class="font-16 m-b-5 text-truncate">Registrado</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${pedidosEstado.registrado}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 mb-2">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-currency-usd font-20 text-success"></i>
                                    <p class="font-16 m-b-5 text-truncate">Pagados</p>
                                </div>
                                <div class="col-5">
                                    <h1  class="font-light text-right mb-0">${pedidosEstado.pagado}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2 col-md-4 col-sm-6 mb-2">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-av-timer font-20 text-purple"></i>
                                    <p class="font-16 m-b-5 text-truncate">En proceso</p>
                                </div>
                                <div class="col-5">
                                    <h1  class="font-light text-right mb-0">${pedidosEstado.en_Proceso}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2 col-md-4 col-sm-6 mb-2">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-check-all font-20 text-danger"></i>
                                    <p class="font-16 m-b-5 text-truncate">Atendido</p>
                                </div>
                                <div class="col-5">
                                    <h1  class="font-light text-right mb-0">${pedidosEstado.atendido}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2 col-md-4 col-sm-6 mb-2">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-check-all font-20 text-danger"></i>
                                    <p class="font-16 m-b-5 text-truncate">Descartado</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${pedidosEstado.descartado}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ============================================================== -->
            <!-- SEGUIMIENTO -->
            <!-- ============================================================== -->
            <div class="container-fluid">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-3 col-sm-6 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Registrado</h4>
                                <div id="registrado" class="task-list">
                                    <th:block th:each="venta : ${Registrados}">
                                        <div class="card mb-2">
                                            <div class="card-body">
                                                <h5 class="card-title" th:text="${venta['clienteNombre']}">Comprador: </h5>
                                                <p class="card-text" th:text="'Tipo: ' + ${venta['proTipo']}"> Tipo: </p>
                                                <p class="card-text" th:text="'Cantidad: ' + ${venta['venDetCantidad']}">Cantidad: </p>
                                            </div>
                                        </div>
                                    </th:block>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">En Proceso</h4>
                                <div id="enProceso" class="task-list">
                                    <th:block th:each="venta : ${EnProcesos}">
                                        <div class="card mb-2">
                                            <div class="card-body">
                                                <h5 class="card-title" th:text="${venta.get('clienteNombre')}"></h5>
                                                <p class="card-text" th:text="'Tipo: ' + ${venta.get('proTipo')}"></p>
                                                <p class="card-text" th:text="'Cantidad: ' + ${venta.get('venDetCantidad')}"></p>
                                            </div>
                                        </div>
                                    </th:block>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Completado</h4>
                                <div id="pagado" class="task-list">
                                    <th:block th:each="venta : ${Pagados}">
                                        <div class="card mb-2">
                                            <div class="card-body">
                                                <h5 class="card-title" th:text="${venta.get('clienteNombre')}"></h5>
                                                <p class="card-text" th:text="'Tipo: ' + ${venta.get('proTipo')}"></p>
                                                <p class="card-text" th:text="'Cantidad: ' + ${venta.get('venDetCantidad')}"></p>
                                            </div>
                                        </div>
                                    </th:block>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Atendido</h4>
                                <div id="atendido" class="task-list">
                                    <th:block th:each="venta : ${Atendidos}">
                                        <div class="card mb-2">
                                            <div class="card-body">
                                                <h5 class="card-title" th:text="${venta.get('clienteNombre')}"></h5>
                                                <p class="card-text" th:text="'Tipo: ' + ${venta.get('proTipo')}"></p>
                                                <p class="card-text" th:text="'Cantidad: ' + ${venta.get('venDetCantidad')}"></p>
                                            </div>
                                        </div>
                                    </th:block>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
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


<script src="/static/web/dist/js/funciones/dashboard.js"></script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/dragula/3.7.3/dragula.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.14.0/Sortable.min.js"></script>

<script>

    // Inicializar SortableJS para cada lista
    ["registrado", "enProceso", "pagado", "atendido"].forEach(id => {
        new Sortable(document.getElementById(id), {
            group: 'shared',
            animation: 150
        });
    });

    // Event listener para detectar cuando se mueve una tarjeta
    document.addEventListener('DOMContentLoaded', () => {
        ['registrado', 'enProceso', 'pagado', 'atendido'].forEach(id => {
            const el = document.getElementById(id);
            new Sortable(el, {
                group: 'shared',
                animation: 150,
                onEnd: function (evt) {
                    const itemEl = evt.item;  // Tarjeta movida
                    const newParent = evt.to.id;  // Nuevo contenedor
                    console.log(`Moved ${itemEl.querySelector('.card-title').innerText} to ${newParent}`);
                }
            });
        });
    });


</script>
<%@ include file="includes/all-jquery.jspf" %>
<%@ include file="includes/footer.jspf" %>
</body>
</html>
