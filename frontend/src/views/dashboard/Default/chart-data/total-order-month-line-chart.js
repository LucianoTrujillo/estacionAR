// ===========================|| DASHBOARD - TOTAL ORDER MONTH CHART ||=========================== //

const radialChart = {
    optionsRadial: {
        plotOptions: {
            radialBar: {
                hollow: {
                    margin: 0,
                    size: '85%',
                    background: '#fff',
                    position: 'front'
                },
                track: {
                    background: '#fff',
                    strokeWidth: '67%',
                    margin: 0 // margin is in pixels
                },

                dataLabels: {
                    showOn: 'always',
                    name: {
                        offsetY: -20,
                        show: true,
                        color: '#888',
                        fontSize: '13px'
                    },
                    value: {
                        formatter: function (val) {
                            return val;
                        },
                        color: '#231',
                        fontSize: '30px',
                        show: true
                    }
                }
            }
        },
        fill: {
            type: 'gradient',
            gradient: {
                shade: 'dark',
                type: 'horizontal',
                shadeIntensity: 0.5,
                gradientToColors: ['#ABE5A1'],
                inverseColors: false,
                opacityFrom: 0.5,
                opacityTo: 1,
                stops: [0, 100]
            }
        },
        labels: ['Tiempo']
    },
    seriesRadial: [100]
};
const chartData = {
    type: 'gauge',
    height: 90,
    options: {
        chart: {
            sparkline: {
                enabled: true
            }
        },
        dataLabels: {
            enabled: false
        },
        colors: ['#fff'],
        fill: {
            type: 'solid',
            opacity: 1
        },
        stroke: {
            curve: 'smooth',
            width: 3
        },
        yaxis: {
            min: 0,
            max: 100
        },
        tooltip: {
            theme: 'dark',
            fixed: {
                enabled: false
            },
            x: {
                show: false
            },
            y: {
                title: 'Total Order'
            },
            marker: {
                show: false
            }
        }
    },
    series: [
        {
            name: 'series1',
            data: [45, 66, 41, 89, 25, 44, 9, 54]
        }
    ]
};

export default radialChart;
