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
                    <h4 class="page-title">DASHBOARD <span class="label label-rounded label-info">OKR</span></h4>
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
            <div class="card-group">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="d-flex no-block align-items-center">
                                    <div>
                                        <i class="mdi mdi-emoticon font-20 text-muted"></i>
                                        <p class="font-16 m-b-5">Registrados</p>
                                    </div>
                                    <div class="ml-auto">
                                        <h1 class="font-light text-right">20</h1><small>de 30</small>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="progress">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: 75%; height: 6px;" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Column -->
                <!-- Column -->
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="d-flex no-block align-items-center">
                                    <div>
                                        <i class="mdi mdi-image font-20  text-muted"></i>
                                        <p class="font-16 m-b-5">Completados</p>
                                    </div>
                                    <div class="ml-auto">
                                        <h1 class="font-light text-right">0</h1><small>de 20</small>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="progress">
                                    <div class="progress-bar bg-success" role="progressbar" style="width: 60%; height: 6px;" aria-valuenow="2" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Column -->
                <!-- Column -->
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="d-flex no-block align-items-center">
                                    <div>
                                        <i class="mdi mdi-currency-eur font-20 text-muted"></i>
                                        <p class="font-16 m-b-5">En proceso</p>
                                    </div>
                                    <div class="ml-auto">
                                        <h1 class="font-light text-right">20</h1><small>de 20</small>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="progress">
                                    <div class="progress-bar bg-purple" role="progressbar" style="width: 65%; height: 6px;" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Column -->
                <!-- Column -->
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="d-flex no-block align-items-center">
                                    <div>
                                        <i class="mdi mdi-poll font-20 text-muted"></i>
                                        <p class="font-16 m-b-5">Sin registrar</p>
                                    </div>
                                    <div class="ml-auto">
                                        <h1 class="font-light text-right">10</h1><small>de 30</small>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style="width: 70%; height: 6px;" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- SEGUIMIENTO DE CLIENTES -->
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

                                    <td class="txt-oflo">Gestiona</td>
                                    <td class="txt-oflo">Sabrina</td>
                                    <td class="txt-oflo">3</td>
                                    <td class="txt-oflo">6</td>
                                    <td><span class="label label-red label-rounded">Sin registro</span></td>
                                    <td class="txt-oflo"><a href="/okr/detalle"><img height="20%" width="20%" src="/static/web/assets/images/quote.png"></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
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