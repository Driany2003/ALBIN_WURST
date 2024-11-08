<%@ page contentType="text/html; charset=UTF-8" %>
<html dir="ltr" lang="en">
<%@ include file="includes/header.jspf" %>
<body>
<%@ include file="includes/preloader.jspf" %>
<div id="main-wrapper">
    <%@ include file="includes/topbar.jspf" %>
    <%@ include file="includes/left-sidebar.jspf" %>

    <div class="page-wrapper">
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 d-flex align-items-center justify-content-between">
                    <h4 class="page-title">REPORTE <span class="label label-rounded label-info">Ventas</span></h4>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="row mb-3">
                        <div class="col-md-3">
                            <label for="fechaInicio">Fecha Inicio:</label>
                            <input type="date" id="fechaInicio" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label for="fechaFin">Fecha Fin:</label>
                            <input type="date" id="fechaFin" class="form-control">
                        </div>
                        <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                        <div class="col-md-3 align-self-end">
                            <button type="button" id="generarReporteBtn" class="btn btn-primary">Generar Reporte</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="reporteModal" tabindex="-1" role="dialog" aria-labelledby="reporteModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="reporteModalLabel">Reporte de Ventas
                        <span><p class="report-date-range">Del <span id="fechaInicioText"></span> al <span id="fechaFinText"></span></p></span>
                    </h5>
                    <div class="btn-group-custom">
                        <button><i class="fas fa-print"></i> Imprimir</button>
                        <button><i class="fas fa-file-export"></i> Exportar</button>
                        <button data-dismiss="modal"><i class="fas fa-arrow-left"></i> Regresar</button>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="summary-cards">
                        <!-- Tarjetas de resumen financiero -->
                        <div class="summary-card" style="background-color: #FF6B6B;">
                            <i class="fas fa-money-bill-wave"></i>
                            <div class="card-content">
                                <h4 id="totalVenta">S/ 0.0</h4>
                                <p>Precio Venta Total</p>
                            </div>
                        </div>
                        <div class="summary-card" style="background-color: #4EA8DE;">
                            <i class="fas fa-dollar-sign"></i>
                            <div class="card-content">
                                <h4 id="totalCosto">S/ 0.0</h4>
                                <p>Precio Costo Total</p>
                            </div>
                        </div>
                        <div class="summary-card" style="background-color: #4CD62B;">
                            <i class="fas fa-chart-line"></i>
                            <div class="card-content">
                                <h4 id="gananciaTotal">S/ 0.0</h4>
                                <p>Ganancia</p>
                            </div>
                        </div>
                        <div class="summary-card" style="background-color: #FFC107;">
                            <i class="fas fa-receipt"></i>
                            <div class="card-content">
                                <h4 id="numeroVentas">&nspar;0</h4>
                                <p>Total Ventas</p>
                            </div>
                        </div>
                    </div>

                    <!-- Línea de separación de Métodos de Pago -->
                    <div class="section-divider">
                        <span>Métodos de Pago</span>
                    </div>

                    <!-- Contenedor de las tarjetas de métodos de pago -->
                    <div class="summary-cards">
                        <div class="summary-card mini-card" style="background-color: #A3D4FF;">
                            <i class="fas fa-mobile-alt"></i>
                            <div class="card-content">
                                <h5>Yape</h5>
                                <p>S/ <span id="totalYape">0.0</span></p>
                            </div>
                        </div>
                        <div class="summary-card mini-card" style="background-color: #E6C8FF;">
                            <i class="fas fa-wallet"></i>
                            <div class="card-content">
                                <h5>Plin</h5>
                                <p>S/ <span id="totalPlin">0.0</span></p>
                            </div>
                        </div>
                        <div class="summary-card mini-card" style="background-color: #FFD1C1;">
                            <i class="fas fa-money-bill-wave"></i>
                            <div class="card-content">
                                <h5>Efectivo</h5>
                                <p>S/ <span id="totalEfectivo">0.0</span></p>
                            </div>
                        </div>
                        <div class="summary-card mini-card" style="background-color: #ffc1dd;">
                            <i class="fas fa-money-bill-wave"></i>
                            <div class="card-content">
                                <h5>Tarjeta</h5>
                                <p>S/ <span id="totalTarjeta">0.0</span></p>
                            </div>
                        </div>
                    </div>

                    <!-- Productos más vendidos y gráfico -->
                    <div class="row mt-4 justify-content-center" id="productosMasVendidosSection">
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-header">
                                    <h5>Productos Más Vendidos</h5>
                                </div>
                                <div class="card-body">
                                    <table class="table table-bordered products-table compact-table">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Nombre</th>
                                            <th>Popularidad</th>
                                            <th>% Ventas</th>
                                        </tr>
                                        </thead>
                                        <tbody id="productosMasVendidos"></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-header">
                                    <h5>% Ventas por Producto</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="ventasChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <style>
        .section-divider {
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 20px 0;
            text-align: center;
        }

        .section-divider::before,
        .section-divider::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid #dee2e6;
            margin: 0 10px;
        }

        .section-divider span {
            font-weight: bold;
            color: #495057;
            background-color: #fff;
            padding: 0 10px;
            font-size: 14px;
        }

        /* Estilos del modal */
        .modal-dialog {
            max-width: 1000px;
            margin: 1.75rem auto;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-header .btn-group-custom button {
            background-color: #eef5ff;
            color: #1f1f1f;
            border: none;
            border-radius: 8px;
            padding: 8px 12px;
            cursor: pointer;
            margin-right: 10px;
        }

        /* Tarjetas de resumen financiero */
        .summary-cards {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 10px;
            margin-bottom: 20px;
        }

        .summary-card {
            display: flex;
            align-items: center;
            padding: 15px;
            border-radius: 12px;
            color: #ffffff;
            font-size: 16px;
        }

        .summary-card i {
            font-size: 24px;
            margin-right: 10px;
        }

        .card-content {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }

        /* Ajustes para las mini tarjetas (Yape, Plin, Efectivo) */
        .mini-card {
            padding: 10px;
            font-size: 14px;
        }

        /* Bordes compactos para la tabla */
        .compact-card {
            padding: 10px;
            margin: 0 auto;
        }

        .compact-table th, .compact-table td {
            padding: 5px 10px;
            font-size: 12px;
        }


        /* Estilos específicos para impresión */
        @media print {

            #productosMasVendidosSection {
                transform: scale(0.8);
                transform-origin: center;
            }

            /* Ajusta el gráfico para que mantenga su tamaño */
            #ventasChart {
                width: 200px !important; /* Ajusta este valor según el tamaño deseado */
                height: 200px !important;
                margin: 0 auto;
            }

            /* Posicionar el gráfico debajo de la tabla */
            .row.mt-4 {
                display: block !important;
            }

            .col-md-8 {
                width: 100% !important;
            }

            .col-md-4 {
                width: 100% !important;
                margin-top: 20px;
            }

        }

        #reporteModal {
            position: relative;
        }

    </style>


    <link href="/static/css/custom.css" rel="stylesheet">
    <!-- customs -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/dragula/3.7.3/dragula.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.14.0/Sortable.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <link href="/static/web/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css" rel="stylesheet">
    <link href="/static/web/assets/extra-libs/taskboard/css/lobilist.css" rel="stylesheet">
    <link href="/static/web/assets/extra-libs/taskboard/css/jquery-ui.min.css" rel="stylesheet">
    <link href="/static/web/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


    <script src="/static/web/dist/js/funciones/reporte.js"></script>

    <%@ include file="includes/all-jquery.jspf" %>
    <!-- footer -->
    <%@ include file="includes/footer.jspf" %>
</body>
</html>
