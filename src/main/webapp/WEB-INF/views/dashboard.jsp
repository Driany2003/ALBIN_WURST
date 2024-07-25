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
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-account-check font-20 text-info"></i>
                                    <p class="font-16 m-b-5">Registrado</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${registrados}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-currency-usd font-20 text-success"></i>
                                    <p class="font-16 m-b-5">Pagados</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${pagados}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-av-timer font-20 text-purple"></i>
                                    <p class="font-16 m-b-5">En proceso</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${enProceso}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-check-all font-20 text-danger"></i>
                                    <p class="font-16 m-b-5">Atendido</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${atendidos}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- SEGUIMIENTO -->
            <!-- ============================================================== -->
            <div class="row">
                <!-- column -->
                <div class="col-lg-12 col-md-12">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex align-items-center">
                                <div>
                                    <h4 class="card-title mb-0">Seguimiento por Areas</h4>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th class="border-top-0">Area</th>
                                    <th class="border-top-0">Lider</th>
                                    <th class="border-top-0">Objetivos</th>
                                    <th class="border-top-0">Key Results</th>
                                    <th class="border-top-0">Estado</th>
                                    <th class="border-top-0">Ver</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="txt-oflo">Risk</td>
                                    <td class="txt-oflo">Natalia</td>
                                    <td class="txt-oflo">2</td>
                                    <td class="txt-oflo">5</td>
                                    <td><span class="label label-purple label-rounded">En proceso</span></td>
                                    <td class="txt-oflo"><a href="/okr/detalle"><img height="20%" width="20%" src="/static/web/assets/images/quote.png"></a></td>
                                </tr>
                                <tr>
                                    <td class="txt-oflo">Construye Experto</td>
                                    <td class="txt-oflo">Patricia</td>
                                    <td class="txt-oflo">2</td>
                                    <td class="txt-oflo">4</td>
                                    <td><span class="label label-purple label-rounded">En proceso</span></td>
                                    <td class="txt-oflo"><a href="/okr/detalle"><img height="20%" width="20%" src="/static/web/assets/images/quote.png"></a></td>
                                </tr>
                                <tr>
                                    <td class="txt-oflo">Pentagono</td>
                                    <td class="txt-oflo">Luis</td>
                                    <td class="txt-oflo">3</td>
                                    <td class="txt-oflo">4</td>
                                    <td><span class="label label-red label-rounded">Sin registro</span></td>
                                    <td class="txt-oflo"><a href="/okr/detalle"><img height="20%" width="20%" src="/static/web/assets/images/quote.png"></a></td>
                                </tr>
                                <tr>
                                    <td class="txt-oflo">Oculus</td>
                                    <td class="txt-oflo">Walter</td>
                                    <td class="txt-oflo">1</td>
                                    <td class="txt-oflo">3</td>
                                    <td><span class="label label-success label-rounded">Pagado</span></td>
                                    <td class="txt-oflo"><a href="/okr/detalle"><img height="20%" width="20%" src="/static/web/assets/images/quote.png"></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- column -->
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- End Container fluid  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- footer -->
        <!-- ============================================================== -->
        <%@ include file="includes/footer.jspf" %>
        <!-- ============================================================== -->
        <!-- End footer -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- End Page wrapper  -->
    <!-- ============================================================== -->
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!--  modal: NUEVA VENTA -->
<script src="/static/web/dist/js/funciones/dashboard.js"></script>
<!-- ============================================================== -->
<!-- End Wrapper -->
<!-- ============================================================== -->
<!-- ============================================================== -->
<!-- customizer Panel - Chat -->
<!-- ============================================================== -->
<!-- ============================================================== -->
<!-- All Jquery -->
<!-- ============================================================== -->
<%@ include file="includes/all-jquery.jspf" %>
</body>
</html>