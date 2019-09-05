<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    application.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Excel数据分析</title>
    <link rel="stylesheet" href="static/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/fileinput.min.css">
    <script src="static/js/jquery-2.2.3.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/echarts.min.js"></script>
</head>
<body>
<div class="container" style="margin-top: 30px;">
    <div class="row">
        <div class="col-md-12">
            <!--用来装填Echarts图表的div-->
            <div id="main" style="height: 600px;"></div>
        </div>
    </div>
</div>
</body>
<script>
    var myChart = echarts.init(document.getElementById('main'));

    $(function () {
        init();
    });

    setInterval('loadChart()', 500);
    setInterval('test()', 500);

    // 初始化图表
    function init() {
        myChart.setOption({
            title: {
                text: '实时监控'
            },
            xAxis: {
                name: '时间',
                type: 'time',
                splitLine: {
                    show: false
                }
            },
            yAxis: {
                name: '垃圾桶占用量',
                type: 'value',
                min: 0,
                max: 100,
                splitLine: {
                    show: false
                }
            },
            series: [{
                name: '模拟数据',
                type: 'line',
                showSymbol: false,
                hoverAnimation: false,
                data: []
            }]
        });
    }

    function loadChart() {
        $.ajax({
            url: "${APP_PATH}/AnalyseServlet?method=getData",
            type: "POST",
            cache: false,
            processData: false,  // 不处理数据
            contentType: false,   // 不设置内容类型
            success: function (result) {
                var data = [];
                $.each(JSON.parse(result), function (i, ele) {
                    data.push({
                        value: [ele.time, ele.state]
                    });
                });
                myChart.setOption({
                    series: [
                        {
                            type: 'line',
                            data: data
                        }
                    ]
                });
            }
        });
    }

    function test() {
        $.ajax({
            url: "${APP_PATH}/AnalyseServlet?method=storeState&state=" + Math.random() * 101,
            type: "POST",
            cache: false,
            processData: false, // 不处理数据
            contentType: false, // 不设置内容类型
            success: function (result) {
                console.log("success");
            }
        });
    }
</script>


</html>