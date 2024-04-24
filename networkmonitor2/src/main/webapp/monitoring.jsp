<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<style>
#container1 {
    max-width: 900px;
    min-width: 400px;
    margin: 0 auto;
    height: 700px;
    padding-top: 50px;
}

.highcharts-figure,
.highcharts-data-table table {
    min-width: 360px;
    max-width: 800px;
    margin: 1em auto;
    padding-top: 50px;
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

</style>
<html>
<body>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/treemap.js"></script>
<script src="https://code.highcharts.com/modules/treegraph.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<%@ include file="header.jsp" %>
	<div class="row">
		<div class="col-6">
			<figure class="highcharts-figure">
    			<div id="container2">
    				<script>
    				(async() => {
    					async function updateTrafficChart(){
    						try{
    							const response = await fetch('trafficmonitor');
    							const data = await response.text();
    							
    							//const jsonData = JSON.parse(data);
    							
    							const series = trafficChart.get('trafficSeries');
    							series.setData(eval(data), true);
    						} catch (error) {
    				            console.error('Error fetching traffic data:', error);
    				        }
    					}
    					const trafficChart = Highcharts.chart('container2', {
    				        chart: {
    				            zoomType: 'x'
    				        },
    				        title: {
    				            text: '네트워크 트래픽',
    				            align: 'left'
    				        },
    				        xAxis: {
    				            type: 'datetime',
    				 						labels: {
    				                format: '{value:%Y-%m-%d %H:%M:%S}', // 시간 형식
    				            }
    				        },
    				        yAxis: {
    				            title: {
    				                text: '트래픽(pps)'
    				            }
    				        },
    				        legend: {
    				            enabled: false
    				        },
    				        plotOptions: {
    				            area: {
    				                fillColor: {
    				                    linearGradient: {
    				                        x1: 0,
    				                        y1: 0,
    				                        x2: 0,
    				                        y2: 1
    				                    },
    				                    stops: [
    				                        [0, Highcharts.getOptions().colors[0]],
    				                        [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
    				                    ]
    				                },
    				                marker: {
    				                    radius: 2
    				                },
    				                lineWidth: 1,
    				                states: {
    				                    hover: {
    				                        lineWidth: 1
    				                    }
    				                },
    				                threshold: null
    				            }
    				        },
    				
    				        series: [{
    				        	id: 'trafficSeries',
    				            type: 'area',
    				            name: 'traffic',
    				            data: [
    				              [ Date.parse("2024-04-11 16:28:49"), 2 ], 
    				              [ Date.parse("2024-04-11 16:28:50"), 5 ],
    				              [ Date.parse("2024-04-11 16:28:51"), 3 ],
    				              [ Date.parse("2024-04-11 16:28:52"), 1 ],
    				              [ Date.parse("2024-04-11 16:28:53"), 3 ],
    				              [ Date.parse("2024-04-11 16:28:55"), 2 ],
    				              [ Date.parse("2024-04-11 16:28:57"), 25 ]
    				            ]
    				        }],
    					  	credits: {enabled: false},
    				    });
    					
    					setInterval(updateTrafficChart, 1000);
    				})();
    				</script>
    			</div>
			</figure>
		</div>
		<div class="col-6">
			<div id="container1">
			<%
			String networkstatus = (String)request.getAttribute("networkstatus");
			%>
				<script>
				(async() => {
					async function updateNetworkStatus(){
						try{
							const response = await fetch('connectionmonitor');
							const data = await response.text();
							console.log(data);
							//netstatChart.setData(data);
							const series = netstatChart.get('netstatSeries');
							series.setData(eval(data), true);
						} catch (error){
				            console.error('Error updating network status:', error);
						}
					}
			
					const netstatChart = Highcharts.chart('container1', {
					    title: {
					        text: '네트워크 연결 상태 정보'
					    },
					    series: [
					        {
    				        	id: 'netstatSeries',
					            type: 'treegraph',
					            data:[],
					            tooltip: {
					                pointFormat: '{point.name}'
					            },
					            marker: {
					                symbol: 'rect',
					                width: '25%'
					            },
					            borderRadius: 10,
					            dataLabels: {
					                pointFormat: '{point.name}',
					                style: {
					                    whiteSpace: 'nowrap'
					                }
					            },
					            levels: [
					                {
					                    level: 1,
					                    levelIsConstant: false
					                },
					                {
					                    level: 2,
					                    colorByPoint: true
					                },
					                {
					                    level: 3,
					                    colorVariation: {
					                        key: 'brightness',
					                        to: -0.5
					                    }
					                },
					                {
					                    level: 4,
					                    colorVariation: {
					                        key: 'brightness',
					                        to: 0.5
					                    }
					                },
					            ]
					        }
					    ],
					  	credits: {enabled: false},
					});
					
					setInterval(updateNetworkStatus, 10000);
				})();
				</script>
			</div>
		</div>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
<%@ include file="footer.jsp" %>
</html>
