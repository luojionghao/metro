var gSVG = $("#svg")[0];
var mSVG = Snap("#svg");
var isCheckRange = false;
var isCheckCoordinate = false;
var RelativeX = 0;
var RelativeY = 0;
var T_background, T_text, T_text1,T_distance, T_line;
var rectR,rectG,textB,textG1,textG2;
var countL;
var countR;
var todayCountL;
var todayCountR;
var pathArrayL;
var pathArrayR;
var time,timeCount;

var conArray = [{ x: 38369.5, y: 662.2, z: 10 }, { x: 38391.5, y: 662.3, z: 2.64 }, { x: 38396.5, y: 662.23, z: 3.54 }, { x: 38406.5, y: 663.3, z: 2.67 }, { x: 38426.5, y: 664.1, z: 3.1 }, { x: 38440.5, y: 665.2, z: 1.5 }, { x: 38467.5, y: 668.2, z: 0.21 }, { x: 38509.5, y: 670.21, z: 2.1 }, { x: 38537.5, y: 672.22, z: 1.1 }, { x: 38546.5, y: 672.42, z: 1.5 }, { x: 38563.5, y: 673.22, z: 2.1 }, { x: 38574.5, y: 674.22, z: 1.9 }, { x: 38584.5, y: 675.22, z: 3.1 }, { x: 38593.5, y: 676.22, z: 2.4 }, { x: 38602.5, y: 677.22, z: 2.2 }, { x: 38611.5, y: 678.22, z: 1.2 }, { x: 38621.5, y: 679.22, z: 3.4 }, { x: 38637.5, y: 680.22, z: 2.1 }, { x: 38647.5, y: 681.22, z: 3.1 }, { x: 38657.5, y: 682.22, z: 1.4 }, { x: 38667.5, y: 682.62, z: 2.6}, { x: 38677.5, y: 683.22, z: 2.1 }, { x: 38687.5, y: 683.62, z: 1.1 }, { x: 38697.5, y: 684.22, z: 2.1 }, { x: 38707.5, y: 684.62, z: 3.1 }, { x: 38717.5, y: 685.22, z: 1.1 }, { x: 38727.5, y: 685.62, z: 2.1}, { x: 38737.5, y: 686.22, z: 2.5 }, { x: 38747.5, y: 686.62, z: 1.4 }, { x: 38757.5, y: 687.62, z: 1.1 }];
getLinedata();

if($("#sectionSvgUrl").val() == ""){
    loadingDismiss();
}
//加载SVG
Snap.load($("#sectionSvgUrl").val(), function(svg) {

    var line;
    var realPoint = gSVG.createSVGPoint();
    var hg = svg.select("g");
    mSVG.append(hg);
    mSVG.zpd();
    RelativeX = Number(document.getElementById("RelativeX").firstChild.nodeValue);
    RelativeY = Number(document.getElementById("RelativeY").firstChild.nodeValue);
    console.log("RelativeX:"+RelativeX);
    console.log("RelativeY:"+RelativeY);
    Snap(document.getElementById("RelativeX")).attr({ visibility: "hidden" });
    Snap(document.getElementById("RelativeY")).attr({ visibility: "hidden" });

    getTwoPoints(mSVG, realPoint, hg);
    getPoint(mSVG, realPoint, hg);
    getRisk(rpArray);
     var pathL = Snap(document.getElementById("lineL")).attr({ visibility: "hidden" });
     var pathR = Snap(document.getElementById("lineR")).attr({ visibility: "hidden" });
     pathArrayL = pathL.selectAll("path");
     pathArrayR = pathR.selectAll("path");

     countL = $("#lRingNum").val();
     countR = $("#rRingNum").val();
    todayCountL = $("#lCountRing").val();
    todayCountR = $("#rCountRing").val();

     getRingInfo(mSVG,pathL,pathArrayL, countL,todayCountL,lLinedata,$("#lvl").val(), "左线");
    // getRingInfo(mSVG,pathR,pathArrayR, countR,rLinedata,$("#rvr").val(), "右线");
     drawTbm(pathL,pathArrayL, countL);
    // drawTbm(pathR,pathArrayR, countR);
    //loadCoordinatesPoint(mSVG,hg,lCoordinates,rCoordinates);
    //移动放大到最新施工点
    if($("#goNumAll").val() != 0){
        moveToNum($("#goNumAll").val())
    }else {
        var linepoint = getlinepath(pathArrayL[countL * 4].node);
        moveToCenter(mSVG, linepoint);
    }

     loadingDismiss();

});

function moveToNum(goNum) {
    console.log(time);
    if(typeof(time)!="undefined"){
        clearInterval(time);
    }
    var linepoint1 = getlinepath(pathArrayL[goNum * 4].node);
    moveToCenter(mSVG, linepoint1);
    var lineL = Snap(document.getElementById("lineL"));
    var polygonArrayL = lineL.selectAll("polygon");
    var color = polygonArrayL[goNum-1].attr("fill");
    timeCount=0;
    time = setInterval(blink, 300,polygonArrayL,goNum,color);
    console.log(goNum);
}
function blink(array,num,color) {
    array[num - 1].animate({
        fill: "#00f"	// 蓝色
    }, 200, mina.easein, function () {
        array[num - 1].attr({
            fill: color
        });
    });
    timeCount++;
    if(timeCount==10){
        clearInterval(time);
    }
}

//获取每环的四个坐标点
function getlinepath(path) {
    var array = path.attributes.d.nodeValue.split(" ");
    return array;
}

//测量两点之间的距离
function getTwoPoints(svg, realPoint, hg) {

    var isclick = false;
    var x1, y1, x2, y2;
    svg.click(function(evt) {
        if (isCheckRange == true) {
            realPoint.x = evt.x || evt.clientX;
            realPoint.y = evt.y || evt.clientY;
            var matriPoint = realPoint.matrixTransform(gSVG.firstElementChild.getScreenCTM().inverse());

            var x = matriPoint.x;
            var y = matriPoint.y;

            if (!isclick) {
                x1 = x;
                y1 = y;
                isclick = true;

                if (typeof(rectG) != "undefined") {
                    rectG.setAttributeNS(null,"visibility","hidden");
                }
                if (typeof(textG1) != "undefined") {
                    textG1.setAttributeNS(null,"visibility","hidden");
                }
                if (typeof(textG2) != "undefined") {
                    textG2.setAttributeNS(null,"visibility","hidden");
                }
                if (typeof(rectR) != "undefined") {
                    rectR.setAttributeNS(null,"visibility","hidden");
                }
                if (typeof(textB) != "undefined") {
                    textB.setAttributeNS(null,"visibility","hidden");
                }
                // if (typeof(T_text) != "undefined") {
                //     T_text.attr({
                //         visibility: "hidden"
                //     });
                // }
                // if (typeof(T_text1) != "undefined") {
                //     T_text1.attr({
                //         visibility: "hidden"
                //     });
                // }
                // if (typeof(T_distance) != "undefined") {
                //     T_distance.attr({
                //         visibility: "hidden"
                //     });
                // }
                // if (typeof(T_background) != "undefined") {
                //     T_background.attr({
                //         visibility: "hidden"
                //     });
                // }
                if (typeof(T_line) != "undefined") {
                    T_line.attr({
                        visibility: "hidden"
                    });
                }
            } else {
                isclick = false;
                x2 = x;
                y2 = y;
                T_line = hg.line(x1, y1, x2, y2).attr({
                    stroke: "#000",
                    strokeWidth: 0.1,
                    visibility: "visible"
                });

                var w = $("#svg").width();
                var h = $("#svg").height();

                rectR = document.createElementNS('http://www.w3.org/2000/svg','rect');
                rectR.setAttributeNS(null,"x",w/2);
                rectR.setAttributeNS(null,"y",h/2);
                rectR.setAttributeNS(null,"width",210);
                rectR.setAttributeNS(null,"height",40);
                rectR.setAttributeNS(null,"fill",'#0000ff');
                rectR.setAttributeNS(null,"visibility","visible");
                svg.append(rectR);

                textB = document.createElementNS('http://www.w3.org/2000/svg','text');
                textB.setAttributeNS(null,"x",w/2+5);
                textB.setAttributeNS(null,"y",h/2+25);
                textB.setAttributeNS(null,"fill",'#bada55');
                textB.setAttributeNS(null,"stroke","#ffff00");
                textB.setAttributeNS(null,"strokeWidth",0.1);
                textB.setAttributeNS(null,"font-size","20px");
                textB.setAttributeNS(null,"visibility","visible");

                var textStringB = document.createTextNode("两点间距离:" + Math.sqrt(Math.pow(Math.abs((x1 - x2) / 4),2)+Math.pow(Math.abs((y1 - y2) / 4),2)).toFixed(2) + "m");
                textB.appendChild(textStringB);
                svg.append(textB);
            }
        }
    });
}

function getPoint(svg, realPoint, hg) {

    var x1, y1;
    svg.click(function(evt) {
        if (isCheckCoordinate == true) {
            realPoint.x = evt.x || evt.clientX;
            realPoint.y = evt.y || evt.clientY;
            var matriPoint = realPoint.matrixTransform(gSVG.firstElementChild.getScreenCTM().inverse());

            var x = matriPoint.x;
            var y = matriPoint.y;


            x1 = x;
            y1 = y;

            if (typeof(rectG) != "undefined") {
                rectG.setAttributeNS(null,"visibility","hidden");
            }
            if (typeof(textG1) != "undefined") {
                textG1.setAttributeNS(null,"visibility","hidden");
            }
            if (typeof(textG2) != "undefined") {
                textG2.setAttributeNS(null,"visibility","hidden");
            }
            if (typeof(rectR) != "undefined") {
                rectR.setAttributeNS(null,"visibility","hidden");
            }
            if (typeof(textB) != "undefined") {
                textB.setAttributeNS(null,"visibility","hidden");
            }
            if (typeof(T_text) != "undefined") {
                T_text.attr({
                    visibility: "hidden"
                });
            }
            if (typeof(T_text1) != "undefined") {
                T_text1.attr({
                    visibility: "hidden"
                });
            }
            if (typeof(T_background) != "undefined") {
                T_background.attr({
                    visibility: "hidden"
                });
            }

            var w = $("#svg").width();
            var h = $("#svg").height();
            rectG = document.createElementNS('http://www.w3.org/2000/svg','rect');
            rectG.setAttributeNS(null,"x",w/2);
            rectG.setAttributeNS(null,"y",h/2);
            rectG.setAttributeNS(null,"width",210);
            rectG.setAttributeNS(null,"height",75);
            rectG.setAttributeNS(null,"fill",'#0000ff');
            rectG.setAttributeNS(null,"visibility","visible");
            svg.append(rectG);

            textG1 = document.createElementNS('http://www.w3.org/2000/svg','text');
            textG1.setAttributeNS(null,"x",w/2+5);
            textG1.setAttributeNS(null,"y",h/2+25);
            textG1.setAttributeNS(null,"fill",'#bada55');
            textG1.setAttributeNS(null,"stroke","#ffff00");
            textG1.setAttributeNS(null,"strokeWidth",0.1);
            textG1.setAttributeNS(null,"font-size","20px");
            textG1.setAttributeNS(null,"font-weight","bold");
            textG1.setAttributeNS(null,"visibility","visible");
            var textStringG1 = document.createTextNode( "    东西坐标:" + (RelativeY - (y1 / 4)).toFixed(2) + "m");
            textG1.appendChild(textStringG1);
            svg.append(textG1);

            textG2 = document.createElementNS('http://www.w3.org/2000/svg','text');
            textG2.setAttributeNS(null,"x",w/2+5);
            textG2.setAttributeNS(null,"y",h/2+60);
            textG2.setAttributeNS(null,"fill",'#bada55');
            textG2.setAttributeNS(null,"stroke","#ffff00");
            textG2.setAttributeNS(null,"strokeWidth",0.1);
            textG2.setAttributeNS(null,"font-size","20px");
            textG2.setAttributeNS(null,"visibility","visible");
            var textStringG2 = document.createTextNode( "    南北坐标:" + ((x1 / 4) + RelativeX).toFixed(2) + "m");
            textG2.appendChild(textStringG2);
            svg.append(textG2);

        }
    });
}

//获取环线信息
function getRingInfo(svg,path,pathArray, count,todayCount,lineData,ringNum,lineName) {

    for (var i = 0; i < count; i++) {
        var newPoints = [];
        var line = getlinepath(pathArray[i + i * 3].node);
        var line1 = getlinepath(pathArray[i + i * 3 + 2].node);
        newPoints.push(line[1]);
        newPoints.push(line[2]);
        newPoints.push(line[4]);
        newPoints.push(line[5]);
        newPoints.push(line1[1]);
        newPoints.push(line1[2]);
        newPoints.push(line1[4]);
        newPoints.push(line1[5]);
        pathArray[i + i * 3].attr({ visibility: "visible" });
        pathArray[i + i * 3 + 2].attr({ visibility: "visible" });
        var color = "#FFC0CB";
        if (i == count - 1) {
            //color = "#0F0";
            color="#00CD00";//Green3
        }else if(i>=count-todayCount && i != count - 1){
            color = "#C0FF3E";//OliveDrab1
        }
        var circle = path.polygon(newPoints).attr({
            fill: color,
            visibility: "visible"
        });
        circle.index = i;
        circle.mouseover(function() {

            var line2 = getlinepath(pathArray[this.index + this.index * 3].node);
            var x = parseFloat(line2[1]);
            var y = parseFloat(line2[2]);
            var id = this.index + 1;
            var w = $("#svg").width();
            var h = $("#svg").height();
            this.textR = document.createElementNS('http://www.w3.org/2000/svg','rect');
            this.textR.setAttributeNS(null,"id",id);
            this.textR.setAttributeNS(null,"x",w/2);
            this.textR.setAttributeNS(null,"y",h/2);
            this.textR.setAttributeNS(null,"width",180);
            this.textR.setAttributeNS(null,"height",95);
            this.textR.setAttributeNS(null,"fill",'#0000ff');
            this.textR.setAttributeNS(null,"visibility","visible");
            svg.append(this.textR);

            this.text = document.createElementNS('http://www.w3.org/2000/svg','text');
            this.text.setAttributeNS(null,"x",w/2+5);
            this.text.setAttributeNS(null,"y",h/2+25);
            this.text.setAttributeNS(null,"fill",'#bada55');
            this.text.setAttributeNS(null,"stroke","#ffff00");
            this.text.setAttributeNS(null,"strokeWidth",0.1);
            this.text.setAttributeNS(null,"font-size","20px");
            this.text.setAttributeNS(null,"visibility","visible");
            var textString = document.createTextNode(lineName + ": " + (this.index + 1) + "/" + ringNum + "环");
            this.text.appendChild(textString);
            svg.append(this.text);

            var mileage = lineData[this.index+1];
            if(mileage == null){
                mileage = "";
            }
            this.text1 = document.createElementNS('http://www.w3.org/2000/svg','text');
            this.text1.setAttributeNS(null,"x",w/2+5);
            this.text1.setAttributeNS(null,"y",h/2+55);
            this.text1.setAttributeNS(null,"fill",'#bada55');
            this.text1.setAttributeNS(null,"stroke","#ffff00");
            this.text1.setAttributeNS(null,"strokeWidth",0.1);
            this.text1.setAttributeNS(null,"font-size","20px");
            this.text1.setAttributeNS(null,"visibility","visible");
            var textString1 = document.createTextNode("里程：" + mileage  + "m");
            this.text1.appendChild(textString1);
            svg.append(this.text1);

            var state = "";
            if (this.index == count - 1) {
                state = "进行中"
            } else {
                state = "完成"
            }
            this.text2 = document.createElementNS('http://www.w3.org/2000/svg','text');
            this.text2.setAttributeNS(null,"x",w/2+5);
            this.text2.setAttributeNS(null,"y",h/2+85);
            this.text2.setAttributeNS(null,"fill",'#bada55');
            this.text2.setAttributeNS(null,"stroke","#ffff00");
            this.text2.setAttributeNS(null,"strokeWidth",0.1);
            this.text2.setAttributeNS(null,"font-size","20px");
            this.text2.setAttributeNS(null,"visibility","visible");
            var textString2 = document.createTextNode("状态：" + state);
            this.text2.appendChild(textString2);
            svg.append(this.text2);
        });
        circle.mouseout(function() {
            this.textR.setAttributeNS(null,"visibility","hidden");
            this.text.setAttributeNS(null,"visibility","hidden");
            this.text1.setAttributeNS(null,"visibility","hidden");
            this.text2.setAttributeNS(null,"visibility","hidden");
        });

        circle.dblclick(function () {
            window.parent.gotoTab2(this.index + 1,"左线");
        });

    }
}

function drawTbm(path,pathArray, count) {
    if(count==0){
        return;
    }
    var color = "#ffff00";
    var strokecolor = "#ED7D40";
    for (var i = count*1; i < count*1 + 5; i++) {

        var newPoints = [];
        var line = getlinepath(pathArray[i + i * 3].node);
        var line1 = getlinepath(pathArray[i + i * 3 + 2].node);
        newPoints.push(line[1]);
        newPoints.push(line[2]);
        newPoints.push(line[4]);
        newPoints.push(line[5]);
        newPoints.push(line1[1]);
        newPoints.push(line1[2]);
        newPoints.push(line1[4]);
        newPoints.push(line1[5]);

        var circle = path.polygon(newPoints).attr({
            stroke: color,
            fill: color,
            visibility: "visible"
        });
        var newPoints1 = [];
        var newPoints2 = [];
        var line = getlinepath(pathArray[i + i * 3+1].node);
        var line1 = getlinepath(pathArray[i + i * 3 + 3].node);
        newPoints1.push(line[1]);
        newPoints1.push(line[2]);
        newPoints1.push(line[4]);
        newPoints1.push(line[5]);

        path.polyline(newPoints1).attr({
            stroke: strokecolor,
            visibility: "visible"
        });

        newPoints2.push(line1[1]);
        newPoints2.push(line1[2]);
        newPoints2.push(line1[4]);
        newPoints2.push(line1[5]);

        path.polyline(newPoints2).attr({
            stroke: strokecolor,
            visibility: "visible"
        });

        if(i == count) {
            var line = getlinepath(pathArray[i + i * 3-4].node);
            var startPoints =[];
            startPoints.push(line[1]);
            startPoints.push(line[2]);
            startPoints.push(line[4]);
            startPoints.push(line[5]);
            path.polyline(startPoints).attr({
                stroke: strokecolor,
                visibility: "visible"
            });
        }
        if(i==count*1 + 4) {
            var line = getlinepath(pathArray[i + i * 3 + 2+4].node);
            var endPoints =[];
            endPoints.push(line[1]);
            endPoints.push(line[2]);
            endPoints.push(line[4]);
            endPoints.push(line[5]);
            path.polyline(endPoints).attr({
                stroke: strokecolor,
                visibility: "visible"
            });
        }
    }
}

function clickRange() {
    isCheckRange = !isCheckRange;
    isCheckCoordinate = false;
    if(isCheckCoordinate){
        $('#coordbtn').attr('class','btn btn-success');
    }else{
        $('#coordbtn').attr('class','btn btn-primary');
    }
    if(isCheckRange){
        $('#rangbtn').attr('class','btn btn-success');
    }else{
        $('#rangbtn').attr('class','btn btn-primary');
    }
    if (typeof(rectG) != "undefined") {
        rectG.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(textG1) != "undefined") {
        textG1.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(textG2) != "undefined") {
        textG2.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(rectR) != "undefined") {
        rectR.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(textB) != "undefined") {
        textB.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(T_text) != "undefined") {
        T_text.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_text1) != "undefined") {
        T_text1.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_distance) != "undefined") {
        T_distance.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_background) != "undefined") {
        T_background.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_line) != "undefined") {
        T_line.attr({
            visibility: "hidden"
        });
    }
}

function clickCoordinate() {
    isCheckCoordinate = !isCheckCoordinate;
    isCheckRange = false;
    if(isCheckCoordinate){
        $('#coordbtn').attr('class','btn btn-success');
    }else{
        $('#coordbtn').attr('class','btn btn-primary');
    }
    if(isCheckRange){
        $('#rangbtn').attr('class','btn btn-success');
    }else{
        $('#rangbtn').attr('class','btn btn-primary');
    }
    if (typeof(rectG) != "undefined") {
        rectG.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(textG1) != "undefined") {
        textG1.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(textG2) != "undefined") {
        textG2.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(rectR) != "undefined") {
        rectR.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(textB) != "undefined") {
        textB.setAttributeNS(null,"visibility","hidden");
    }
    if (typeof(T_text) != "undefined") {
        T_text.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_text1) != "undefined") {
        T_text1.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_distance) != "undefined") {
        T_distance.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_background) != "undefined") {
        T_background.attr({
            visibility: "hidden"
        });
    }
    if (typeof(T_line) != "undefined") {
        T_line.attr({
            visibility: "hidden"
        });
    }
}

function panZoom(svg, xPan, yPan, scale, xZoom, yZoom) {
    var me = this;
    var tfStr = 'translate(' + xPan + ',' + yPan + ')';
    tfStr += ' translate(' + xZoom + ',' + yZoom + ')';
    tfStr += ' scale(' + scale + ' ' + scale + ')';
    tfStr += ' translate(-' + xZoom + ',-' + yZoom + ')';
    svg.transform(tfStr);
}

function moveToCenter(svg, points) {

    var svgBox = svg.getBBox();
    console.log(svgBox);
    var divWidth =$("#svg").outerWidth(false);
    console.log(divWidth);
    var divHeight = $("#svg").outerHeight(false);
    panZoom(svg.select("g"), -points[1] + divWidth / 2, -points[2] + divHeight / 2, 10, points[1], points[2]);

}

//移动放大到左线最新施工点
function clickLeft() {

    var pathL = Snap(document.getElementById("lineL")).attr({ visibility: "hidden" });
    var pathArrayL = pathL.selectAll("path");
    var countL = $("#lRingNum").val();
    if(!parseInt(countL)){
        alert("左线暂时未开始施工");
        return;
    }
    //移动放大到最新施工点
    var linepoint = getlinepath(pathArrayL[countL * 4].node);
    moveToCenter(mSVG, linepoint);
}

//移动放大到右线最新施工点
function clickRight() {
    var pathR = Snap(document.getElementById("lineR")).attr({ visibility: "hidden" });
    var pathArrayR = pathR.selectAll("path");
    var countR = $("#rRingNum").val();

    if(!parseInt(countR)){
        alert("右线暂时未开始施工");
        return;
    }
    //移动放大到最新施工点
    var linepoint = getlinepath(pathArrayR[countR * 4].node);
    moveToCenter(mSVG, linepoint);
}


function hidecoord(){
    var nv = document.getElementById("hidecoord");
    nv.innerHTML="显示线路";
    nv.onclick =function(){showcoord()};
    var lineList = $("*[name='coordinates']");
    for(var i=0;i<lineList.length;i++){
        Snap(lineList[i]).attr({ visibility: "hidden" });
    }
}

function showcoord(){
    var nv = document.getElementById("hidecoord");
    nv.innerHTML="隐藏线路";
    nv.onclick = function(){hidecoord()};
    var lineList = $("*[name='coordinates']");
    for(var i=0;i<lineList.length;i++){
        Snap(lineList[i]).attr({ visibility: "visible" });
    }
}

//加载剖面坐标点信息
function loadCoordinatesPoint(msvg,svg,lCoordinates,rCoordinates) {
    var larr = new Array();
    $.each(lCoordinates,function (name, value){
        if(value.x && value.y){
            larr.push([value.x, value.y,value.z]);
        }
    });

    for (var i = 0; i < larr.length; i++) {
        var circle1 = svg.circle((larr[i][0] - RelativeX), Math.abs((larr[i][1] - RelativeY)), 1).attr({
            fill: "#0000ff",
            visibility: "visible",
            name:"coordinates"
        });
        if (i < larr.length - 1) {
            svg.line((larr[i][0] - RelativeX), Math.abs((larr[i][1] - RelativeY)), (larr[i + 1][0] - RelativeX), Math.abs((larr[i + 1][1] - RelativeY))).attr({
                stroke: "#000",
                visibility: "visible",
                strokeWidth: 0.1,
                name:"coordinates"
            });

        }
        circle1.index = i;
        circle1.mouseover(function() {

            var w = $("#svg").width();
            var h = $("#svg").height();

            this.rectCL = document.createElementNS('http://www.w3.org/2000/svg','rect');
            this.rectCL.setAttributeNS(null,"x",w/2);
            this.rectCL.setAttributeNS(null,"y",h/2);
            this.rectCL.setAttributeNS(null,"width",200);
            this.rectCL.setAttributeNS(null,"height",35);
            this.rectCL.setAttributeNS(null,"fill",'#0000ff');
            this.rectCL.setAttributeNS(null,"visibility","visible");
            msvg.append(this.rectCL);

            this.textCL = document.createElementNS('http://www.w3.org/2000/svg','text');
            this.textCL.setAttributeNS(null,"x",w/2+5);
            this.textCL.setAttributeNS(null,"y",h/2+25);
            this.textCL.setAttributeNS(null,"fill",'#bada55');
            this.textCL.setAttributeNS(null,"stroke","#ffff00");
            this.textCL.setAttributeNS(null,"strokeWidth",0.1);
            this.textCL.setAttributeNS(null,"font-size","20px");
            this.textCL.setAttributeNS(null,"visibility","visible");
            var textStringC = document.createTextNode("盾尾垂直偏差:" + larr[this.index][2] + "mm");
            this.textCL.appendChild(textStringC);
            msvg.append(this.textCL);

        });
        circle1.mouseout(function() {
            this.rectCL.setAttributeNS(null,"visibility","hidden");
            this.textCL.setAttributeNS(null,"visibility","hidden");
        });
    }

}


function getRisk(riskData) {
    for (var i = 0; i < riskData.length; i++){
        if(riskData[i].leftOrRight == "l" && riskData[i].gType == 2) {
            var risk = Snap(document.getElementById(riskData[i].positionNo));
            risk.index = i;
            risk.dblclick(function (evt) {

                $("#riskModal").css('display','block');
                $("#riskTextMsg").val(riskData[this.index].textMsg);
                if(riskData[this.index].riskPdf1Url != "" &&  riskData[this.index].riskPdf1Url != null){
                    $("#riskPdf1Url").html("风险分析报告1.pdf <a href='"+riskData[this.index].riskPdf1Url + "' download>下载</a>");
                }else{
                    $("#riskPdf1Url").html("");
                }
                if(riskData[this.index].riskPdf2Url != "" &&  riskData[this.index].riskPdf2Url != null){
                    $("#riskPdf2Url").html("风险分析报告2.pdf <a href='"+riskData[this.index].riskPdf2Url + "' download>下载</a>");
                }else {
                    $("#riskPdf2Url").html("")
                }
                if(riskData[this.index].riskPdf3Url != "" &&  riskData[this.index].riskPdf3Url != null){
                    $("#riskPdf3Url").html("风险分析报告3.pdf <a href='"+riskData[this.index].riskPdf3Url + "' download>下载</a>");
                }else {
                    $("#riskPdf3Url").html("");
                }

                if(riskData[this.index].riskImgUrl != "" &&  riskData[this.index].riskImgUrl != null){
                    $("#risImgUrl").html("<img style=\"border: 1px solid; height:245px; width:245px;\" src='"+riskData[this.index].riskImgUrl+"' />");
                }else{
                    $("#risImgUrl").html("");
                }

            })
        }
    }
}

function closeRisk() {
    $("#riskModal").css('display','none');
}



