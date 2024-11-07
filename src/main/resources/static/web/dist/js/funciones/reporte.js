$(document).ready(function () {
    window.ventasChart = null;

    $('#generarReporteBtn').click(function () {
        const fechaInicio = $('#fechaInicio').val();
        const fechaFin = $('#fechaFin').val();

        if (!fechaInicio || !fechaFin) {
            alert("Por favor, selecciona ambas fechas para generar el reporte.");
            return;
        }

        $('#fechaInicioText').text(fechaInicio);
        $('#fechaFinText').text(fechaFin);

        $.ajax({
            url: '/kenpis/venta/reporte/filtroXfecha',
            type: 'GET',
            data: {
                fechaInicio: fechaInicio,
                fechaFin: fechaFin
            },
            success: function (data) {
                $('#totalVenta').text(`S/ ${data.totalVenta.toFixed(2)}`);
                $('#totalCosto').text(`S/ ${data.totalCosto.toFixed(2)}`);
                $('#gananciaTotal').text(`S/ ${data.gananciaTotal.toFixed(2)}`);
                $('#numeroVentas').text(data.numeroVentas);
                $('#totalYape').text(`S/ ${data.totalYape.toFixed(2)}`);
                $('#totalPlin').text(`S/ ${data.totalPlin.toFixed(2)}`);
                $('#totalEfectivo').text(`S/ ${data.totalEfectivo.toFixed(2)}`);

                const productosTableBody = $('#productosMasVendidos');
                productosTableBody.empty();

                const nombresProductos = [];
                const popularidades = [];
                const colores = ['#4EA8DE', '#4CD62B', '#FF6B6B', '#FFC107', '#A3D4FF', '#E6C8FF', '#FFD1C1'];

                data.productosMasVendidos.forEach(function (producto, index) {
                    nombresProductos.push(producto.productoNombre);
                    popularidades.push(producto.popularidad);

                    const row = `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${producto.productoNombre}</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" style="width: ${producto.popularidad}%; background-color: ${colores[index % colores.length]};"></div>
                            </div>
                        </td>
                        <td>${producto.popularidad}%</td>
                    </tr>
                `;
                    productosTableBody.append(row);
                });

                const ctx = document.getElementById('ventasChart').getContext('2d');

                if (window.ventasChart) {
                    window.ventasChart.destroy();
                }

                window.ventasChart = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: nombresProductos,
                        datasets: [{
                            data: popularidades,
                            backgroundColor: colores,
                            borderWidth: 2,
                            borderColor: '#ffffff',
                            hoverBorderColor: '#000000'
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            },
                            tooltip: {
                                callbacks: {
                                    label: function (context) {
                                        return `${context.label}: ${context.raw}%`;
                                    }
                                }
                            },
                            datalabels: {
                                display: true,
                                color: '#fff',
                                formatter: (value) => `${value}%`,
                                font: {
                                    weight: 'bold',
                                    size: 12
                                }
                            }
                        },
                        animation: {
                            animateScale: true,
                            animateRotate: true
                        },
                        cutout: '70%' // Makes it a doughnut with a larger center hole for aesthetics
                    }
                });
            },
            error: function () {
                alert("Ocurrió un error al generar el reporte. Intenta nuevamente.");
            }
        });
    });

    // Botón de imprimir
    $('.btn-group-custom button:contains("Imprimir")').click(function () {
        // Selecciona el contenido del modal que deseas imprimir
        var printContent = document.getElementById('reporteModal').innerHTML;

        // Crea una nueva ventana para la impresión
        var printWindow = window.open('', '', 'height=800,width=1000');

        // Inserta el contenido del modal en la nueva ventana
        printWindow.document.write('<html><head><title>Reporte de Ventas</title>');
        printWindow.document.write('<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">');
        printWindow.document.write('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">');

        // Añadir estilos específicos para impresión
        printWindow.document.write(`
            <style>
                /* Asegura el diseño en cuadrícula */
                .summary-cards {
                    display: grid !important;
                    grid-template-columns: repeat(4, 1fr) !important;
                    gap: 10px;
                }
                .summary-card {
                    display: flex !important;
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
                /* Ocultar los botones de exportar e imprimir */
                .btn-group-custom {
                    display: none !important;
                }
                /* Ajuste para que el gráfico mantenga su tamaño */
                #ventasChart {
                    max-width: 100% !important;
                    max-height: 100% !important;
                }
            </style>
        `);

        printWindow.document.write('</head><body>');
        printWindow.document.write(printContent);
        printWindow.document.write('</body></html>');

        // Espera a que el contenido se cargue en la ventana de impresión y luego imprime
        printWindow.document.close();
        printWindow.focus();

        setTimeout(function () {
            printWindow.print();
            printWindow.close();
        }, 500);
    });

    // Botón de exportar a PDF
    $('.btn-group-custom button:contains("Exportar")').click(function () {
        // Abre el modal si está cerrado


        // Espera unos milisegundos para asegurar que esté completamente abierto antes de exportar
        setTimeout(function () {
            var element = document.getElementById('reporteModal');

            var opt = {
                margin: 0.2,
                filename: 'Reporte_Ventas.pdf',
                image: {type: 'jpeg', quality: 0.98},
                html2canvas: {scale: 1.2, useCORS: true},
                jsPDF: {unit: 'in', format: 'letter', orientation: 'portrait'}
            };

            html2pdf().set(opt).from(element).save();
        }, 500); // Espera medio segundo para que el modal se muestre completamente

    });
});







