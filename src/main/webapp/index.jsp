<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    const myChart = echarts.init(document.getElementById('main'));

    $(function () {
        init();
    });

    // setInterval('loadChart()', 500);
    // setInterval('test()', 500);

    // 初始化图表
    function init() {
        myChart.setOption(option);
    }

    option = {
        tooltip: {
            formatter: "{b} : {c}%"
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '',
                type: 'gauge',
                detail: {formatter: '{value}%'},
                data: [{value: 0, name: '1号垃圾桶'}]
            }
        ]
    };

    let websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/ws/websocketServer/1");

        //连接成功建立的回调方法
        websocket.onopen = function () {
        };

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            console.log("接受到消息:" + event.data);
            option.series[0].data[0].value = event.data;
            myChart.setOption(option)
        };

        //连接发生错误的回调方法
        websocket.onerror = function () {
            alert("WebSocket连接发生错误");
        };

        //连接关闭的回调方法
        websocket.onclose = function () {
            alert("WebSocket连接关闭");
        };

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }

    } else {
        alert('当前浏览器 Not support websocket')
    }

    /*let websocket2 = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/ws/websocketServer/2");

        //连接成功建立的回调方法
        websocket.onopen = function () {
        };

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            console.log("接受到消息2:" + event.data);
        };

        //连接发生错误的回调方法
        websocket.onerror = function () {
            alert("WebSocket连接发生错误");
        };

        //连接关闭的回调方法
        websocket.onclose = function () {
            alert("WebSocket连接关闭");
        };

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }

    } else {
        alert('当前浏览器 Not support websocket')
    }*/

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }
</script>


</html>