var host = window.location.host;

function load(callback) {
    var data;
    $.ajax({
        type: 'GET',
        url: "//" + host + "/index.json",
        data: {},
        success: function (res) {
            if (res.success) {
                data = res.data;
            } else {
                return [];
            }
        },
        async: false,
        dataType: 'json',
        error: function (XMLHttpRequest) {
        }
    });
    return data;
}

// 渲染条形图
function renderBarChart(data) {
    const chart = new G2.Chart({
        container: 'container',
        theme: 'classic',
        title: '录制回放记录统计',
    });
    // 声明可视化
    chart
        .interval() // 创建一个 Interval 标记
        .data(data) // 绑定数据
        .encode('x', 'name') // 编码 x 通道
        .encode('y', 'total') // 编码 y 通道
        .encode('key', 'name') // 指定 key
        .animate('update', {duration: 300}) // 指定更新动画的时间
        .label({
            text: 'total', // 指定绑定的字段
            style: {
                fill: '#fff', // 指定样式
                dy: 5,
            },
        });
    // 渲染可视化
    chart.render();
    return chart;
}

// 更新条形图的数据
function updateBarChart(chart, data) {
    // 获得 Interval Mark
    const interval = chart.getNodesByType('interval')[0];
    interval.data(data);
    // 重新渲染
    chart.render();
}