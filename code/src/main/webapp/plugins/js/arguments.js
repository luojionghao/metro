var unit = [
    { id:'bar',text:'压力(bar)' },
    { id:'duC',text:'温度(°C)' },
    { id:'KN',text:'推力(KN)' },
    { id:'KNm',text:'扭矩(kNm)' },
    { id:'m',text:'里程坐标(m)' },
    { id:'mm',text:'行程或偏差(mm)' },
    { id:'mFang',text:'体积(m³)' },
    { id:'mmPerR',text:'贯入度(mm/rpm)' },
    { id:'mmPerMin',text:'速度(mm/min)' },
    { id:'rpm',text:'转速(rpm)' },
    { id:'du',text:'角度(°)' }
]

var param = {
    "bar":[
        { text:"土压[上]", value:"A0004" },
        { text:"土压[右上]", value:"A0005" },
        { text:"土压[左上]", value:"A0011" },
        { text:"土压[右下]", value:"A0007" },
        { text:"土压[左下]", value:"A0009" },
        { text:"同步注浆压力[右上]", value:"D0016" },
        { text:"同步注浆压力[右下]", value:"D0018" },
        { text:"同步注浆压力[左下]", value:"D0020" },
        { text:"同步注浆压力[左上]", value:"D0022" },
        { text:"推进千斤顶油压[上]", value:"C0009" },
        { text:"推进千斤顶油压[右上]", value:"C0010" },
        { text:"推进千斤顶油压[右]", value:"C0011" },
        { text:"推进千斤顶油压[右下]", value:"C0012" },
        { text:"推进油压", value:"B0007" },
        { text:"螺旋输送机前端土压", value:"F0003" },
        { text:"螺旋输送机后端土压", value:"F0004" },
        { text:"铰接压力", value:"E0019" }
    ],
    "duC":[
        { text:"齿轮油温", value:"B0011" },
        { text:"液压邮箱油温", value:"B0012" }
    ],
    "KN":[
        { text:"总推力", value:"B0006" }
    ],
    "KNm":[
        { text:"刀盘扭矩", value:"B0004" },
        { text:"螺旋输送机扭矩", value:"F0002" }
    ],
    "m":[
        { text:"前盾X坐标", value:"J0002" },
        { text:"前盾Y坐标", value:"J0003" },
        { text:"前盾Z坐标", value:"J0004" },
        { text:"中盾X坐标", value:"J0005" },
        { text:"中盾Y坐标", value:"J0006" },
        { text:"中盾Z坐标", value:"J0007" },
        { text:"尾盾X坐标", value:"J0008" },
        { text:"尾盾Y坐标", value:"J0009" },
        { text:"尾盾Z坐标", value:"J0010" }
    ],
    "mm":[
        { text:"推进千斤顶行程[上]", value:"C0001" },
        { text:"推进千斤顶行程[右上]", value:"C0002" },
        { text:"推进千斤顶行程[右]", value:"C0003" },
        { text:"推进千斤顶行程[右下]", value:"C0004" },
        { text:"前盾水平偏差", value:"J0020" },
        { text:"前盾垂直偏差", value:"J0021" },
        { text:"中盾水平偏差", value:"J0022" },
        { text:"中盾垂直偏差", value:"J0023" },
        { text:"尾盾水平偏差", value:"J0024" },
        { text:"尾盾垂直偏差", value:"J0025" }
    ],
    "mFang":[
        { text:"同步注浆量[右上]", value:"D0008" },
        { text:"同步注浆量[右下]", value:"D0010" },
        { text:"同步注浆量[左下]", value:"D0012" },
        { text:"同步注浆量[左上]", value:"D0014" },
        { text:"同步注浆总量", value:"D0023" }
    ],
    "mmPerR":[
        { text:"贯入度", value:"B0003" }
    ],
    "mmPerMin":[
        { text:"推进速度", value:"B0001" }
    ],
    "rpm":[
        { text:"刀盘转速", value:"B0002" },
        { text:"螺旋输送机转速", value:"F0001" }
    ],
    "du":[
        { text:"滚动角", value:"J0011" },
        { text:"前盾体俯仰角", value:"J0012" },
        { text:"后盾体俯仰角", value:"J0013" },
        { text:"前盾体水平角", value:"J0014" },
        { text:"后盾体水平角", value:"J0015" },
        { text:"前盾体水平偏角", value:"J0016" },
        { text:"后盾体水平偏角", value:"J0017" },
        { text:"前盾体垂直偏角", value:"J0018" },
        { text:"后盾体垂直偏角", value:"J0019" }
    ]
}
