<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="monitordto.resourcedto"%>
<!DOCTYPE html>
<style>
.highcharts-figure .chart-container {
    width: 400px;
    height: 250px;
    float: left;
    margin-top: 200px;
    text-align: left;
}

.chart-container2 {
    width: 400px;
    height: 250px;
    float: left;
    margin-top: 200px;
    text-align: left;
}

.chart-container3 {
    width: 400px;
    height: 250px;
    float: left;
    margin-top: 200px;
    text-align: left;
}

.highcharts-figure,
.highcharts-data-table table {
    width: 60%;
    margin-left: 400px;
}

.highcharts-data-table table {
    font-family: Verdana, sans-serif;
    border-collapse: collapse;
    border: 1px solid #ebebeb;
    margin: 10px auto;
    text-align: center;
    width: 100%;
    max-width: 500px;
}

.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}

.highcharts-data-table th {
    font-weight: 600;
    padding: 0.5em;
}

.highcharts-data-table td,
.highcharts-data-table th,
.highcharts-data-table caption {
    padding: 0.5em;
}

.highcharts-data-table thead tr,
.highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}

.highcharts-data-table tr:hover {
    background: #f1f7ff;
}

@media (max-width: 600px) {
    .highcharts-figure,
    .highcharts-data-table table {
        width: 100%;
    }

    .highcharts-figure .chart-container {
        width: 300px;
        float: none;
        margin: 0 auto;
    }
}
</style>
<html>
<body class="d-flex flex-column min-vh-100">	
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-more.js"></script>
<script src="https://code.highcharts.com/modules/solid-gauge.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<%@ include file="header.jsp" %>
<figure class="highcharts-figure">
	<div class="row">
		<div class="col-4">
		    <div id="container-cpu" class="chart-container">
		    	<script>
		    	const cpuUsageOptions = {
		    		    chart: {
		    		        type: 'solidgauge'
		    		    },
		
		    		    title: '사용량',
		
		    		    pane: {
		    		        center: ['50%', '85%'],
		    		        size: '140%',
		    		        startAngle: -90,
		    		        endAngle: 90,
		    		        background: {
		    		            backgroundColor:
		    		                Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
		    		            innerRadius: '60%',
		    		            outerRadius: '100%',
		    		            shape: 'arc'
		    		        }
		    		    },
		
		    		    exporting: {
		    		        enabled: false
		    		    },
		
		    		    tooltip: {
		    		        enabled: false
		    		    },
		
		    		    // the value axis
		    		    yAxis: {
		    		        stops: [
		    		            [0.5, '#55BF3B'], // green
		    		            [0.7, '#DDDF0D'], // yellow
		    		            [0.9, '#DF5353'] // red
		    		        ],
		    		        lineWidth: 0,
		    		        tickWidth: 0,
		    		        minorTickInterval: null,
		    		        tickAmount: 2,
		    		        title: {
		    		            y: -70
		    		        },
		    		        labels: {
		    		            y: 16
		    		        }
		    		    },
		
		    		    plotOptions: {
		    		        solidgauge: {
		    		            dataLabels: {
		    		                y: 5,
		    		                borderWidth: 0,
		    		                useHTML: true
		    		            }
		    		        }
		    		    }
		    		};
		
		    		const cpuUsage = Highcharts.chart('container-cpu', Highcharts.merge(cpuUsageOptions, {
		    		    yAxis: {
		    		        min: 0,
		    		        max: 100,
		    		        title: {
		    		            text: 'CPU 사용률',
		    		            y: -100
		    		        }
		    		    },
		
		    		    credits: {
		    		        enabled: false
		    		    },
		
		    		    series: [{
		    		        name: 'usage',
		    		        data: [80],
		    		        dataLabels: {
		    		            format:
		    		                '<div style="text-align:center">' +
		    		                '<span style="font-size:25px">{y}</span><br/>' +
		    		                '<span style="font-size:16px;opacity:0.4">%</span>' +
		    		                '</div>'
		    		        },
		    		        tooltip: {
		    		            valueSuffix: ' %'
		    		        }
		    		    }]
		
		    		}));
		    		
		    		<%
		    		ArrayList<resourcedto> cpuresource = (ArrayList<resourcedto>)request.getAttribute("cpuUsage");
		    		%>

					function updateCPUUsage(){
						cpuUsage.series[0].points[0].update(
								parseInt(<%=cpuresource.get(0).getUsed() %>)
						);
					}
					
					updateCPUUsage();
		    	</script>
		    </div>
	    </div>
		<div class="col-4">
		    <div id="container-memory" class="chart-container2">
		    	<script>
		    	const memoryUsageOptions = {
		    		    chart: {
		    		        type: 'solidgauge'
		    		    },
		
		    		    title: '사용량',
		
		    		    pane: {
		    		        center: ['50%', '85%'],
		    		        size: '140%',
		    		        startAngle: -90,
		    		        endAngle: 90,
		    		        background: {
		    		            backgroundColor:
		    		                Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
		    		            innerRadius: '60%',
		    		            outerRadius: '100%',
		    		            shape: 'arc'
		    		        }
		    		    },
		
		    		    exporting: {
		    		        enabled: false
		    		    },
		
		    		    tooltip: {
		    		        enabled: false
		    		    },
		
		    		    // the value axis
		    		    yAxis: {
		    		        stops: [
		    		            [0.4, '#55BF3B'], // green
		    		            [0.6, '#DDDF0D'], // yellow
		    		            [0.8, '#DF5353'] // red
		    		        ],
		    		        lineWidth: 0,
		    		        tickWidth: 0,
		    		        minorTickInterval: null,
		    		        tickAmount: 2,
		    		        title: {
		    		            y: -70
		    		        },
		    		        labels: {
		    		            y: 16
		    		        }
		    		    },
		
		    		    plotOptions: {
		    		        solidgauge: {
		    		            dataLabels: {
		    		                y: 5,
		    		                borderWidth: 0,
		    		                useHTML: true
		    		            }
		    		        }
		    		    }
		    		};
		
		    		const memoryUsage = Highcharts.chart('container-memory', Highcharts.merge(memoryUsageOptions, {
		    		    yAxis: {
		    		        min: 0,
		    		        max: 100,
		    		        title: {
		    		            text: '메모리 사용률',
		    		            y: -100
		    		        }
		    		    },
		
		    		    credits: {
		    		        enabled: false
		    		    },
		
		    		    series: [{
		    		        name: 'usage',
		    		        data: [80],
		    		        dataLabels: {
		    		            format:
		    		                '<div style="text-align:center">' +
		    		                '<span style="font-size:25px">{y}</span><br/>' +
		    		                '<span style="font-size:16px;opacity:0.4">%</span>' +
		    		                '</div>'
		    		        },
		    		        tooltip: {
		    		            valueSuffix: ' %'
		    		        }
		    		    }]
		
		    		}));
		    		<%
		    		ArrayList<resourcedto> memoryresource = (ArrayList<resourcedto>)request.getAttribute("memoryUsage");
		    		%>

					function updateMemoryUsage(){
						memoryUsage.series[0].points[0].update(
								parseInt(<%=memoryresource.get(0).getUsed() %>)
						);
					}
					
					updateMemoryUsage();
		    	</script>
		    </div>
	    </div>
		<div class="col-4">
		    <div id="container-disk" class="chart-container3">
		    	<script>
		    	const diskUsageOptions = {
		    		    chart: {
		    		        type: 'solidgauge'
		    		    },
		
		    		    title: '사용량',
		
		    		    pane: {
		    		        center: ['50%', '85%'],
		    		        size: '140%',
		    		        startAngle: -90,
		    		        endAngle: 90,
		    		        background: {
		    		            backgroundColor:
		    		                Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
		    		            innerRadius: '60%',
		    		            outerRadius: '100%',
		    		            shape: 'arc'
		    		        }
		    		    },
		
		    		    exporting: {
		    		        enabled: false
		    		    },
		
		    		    tooltip: {
		    		        enabled: false
		    		    },
		
		    		    // the value axis
		    		    yAxis: {
		    		        stops: [
		    		            [0.4, '#55BF3B'], // green
		    		            [0.6, '#DDDF0D'], // yellow
		    		            [0.8, '#DF5353'] // red
		    		        ],
		    		        lineWidth: 0,
		    		        tickWidth: 0,
		    		        minorTickInterval: null,
		    		        tickAmount: 2,
		    		        title: {
		    		            y: -70
		    		        },
		    		        labels: {
		    		            y: 16
		    		        }
		    		    },
		
		    		    plotOptions: {
		    		        solidgauge: {
		    		            dataLabels: {
		    		                y: 5,
		    		                borderWidth: 0,
		    		                useHTML: true
		    		            }
		    		        }
		    		    }
		    		};
		
		    		const diskUsage = Highcharts.chart('container-disk', Highcharts.merge(diskUsageOptions, {
		    		    yAxis: {
		    		        min: 0,
		    		        max: 100,
		    		        title: {
		    		            text: '디스크 사용률',
		    		            y: -100
		    		        }
		    		    },
		
		    		    credits: {
		    		        enabled: false
		    		    },
		
		    		    series: [{
		    		        name: 'usage',
		    		        data: [80],
		    		        dataLabels: {
		    		            format:
		    		                '<div style="text-align:center">' +
		    		                '<span style="font-size:25px">{y}</span><br/>' +
		    		                '<span style="font-size:16px;opacity:0.4">%</span>' +
		    		                '</div>'
		    		        },
		    		        tooltip: {
		    		            valueSuffix: ' %'
		    		        }
		    		    }]
		    		}));
		    		<%
		    		ArrayList<resourcedto> diskresource = (ArrayList<resourcedto>)request.getAttribute("diskUsage");
		    		%>

					function updateDiskUsage(){
						diskUsage.series[0].points[0].update(
								parseInt(<%=diskresource.get(0).getUsed() %>)
						);
					}
					
					updateDiskUsage();
		    	</script>
		    </div>
	    </div>
    </div>
</figure>	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
<%@ include file="footer.jsp" %>
</html>
