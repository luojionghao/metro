var gSVG = $("#svg")[0];
var mSVG = Snap("#svg");
var isCheckRange = false;
var isCheckCoordinate = false;
var RelativeX = 0;
var RelativeY = 0;
var RelMultiple=0;
var CoordMultiple=0;
var T_background, T_text, T_text1,T_distance, T_line;
var rectR,rectG,textB,textG1,textG2;
var rectS,textS1,textS2,textS3,textS4;
var countL;
var countR;
var todayCountL;
var todayCountR;
var pathArrayL;
var pathArrayR;
var time,timeCount;

getLinedata();
console.log($("#ppSvgUrl"));
if($("#ppSvgUrl").val() == ""){
    loadingDismiss();
}
//加载SVG
Snap.load($("#ppSvgUrl").val(), function(svg) {
    var line;
    var realPoint = gSVG.createSVGPoint();
    console.log(realPoint);
    var hg = svg.select("g");
    mSVG.append(hg);
    mSVG.zpd();

    RelativeX = Number(document.getElementById("RelativeX").firstChild.nodeValue);
    RelativeY = Number(document.getElementById("RelativeY").firstChild.nodeValue);
    RelMultiple = Number(document.getElementById("RelMultiple").firstChild.nodeValue);
    CoordMultiple = Number(document.getElementById("CoordMultiple").firstChild.nodeValue);
    Snap(document.getElementById("RelativeX")).attr({ visibility: "hidden" });
    Snap(document.getElementById("RelativeY")).attr({ visibility: "hidden" });
    Snap(document.getElementById("RelMultiple")).attr({ visibility: "hidden" });
    Snap(document.getElementById("CoordMultiple")).attr({ visibility: "hidden" });

    getTwoPoints(mSVG, realPoint, hg);
    getPoint(mSVG, realPoint, hg);
    getRisk(rpArray);

    // var path = Snap(document.getElementById("line")).attr({ visibility: "hidden" });
    //
    // var pathArray = path.selectAll("path");
    // console.log(pathArray.length);
    //
    // var countL = $("#lRingNum").val();
    // var countR = $("#rRingNum").val();
    // getRingInfo(mSVG,path, pathArray,lLinedata,lLinedata, countL, countR);
    var pathL = Snap(document.getElementById("lineL")).attr({ visibility: "hidden" });
    var pathR = Snap(document.getElementById("lineR")).attr({ visibility: "hidden" });
    pathArrayL = pathL.selectAll("path");
    pathArrayR = pathR.selectAll("path");

    countL = $("#lRingNum").val();
    countR = $("#rRingNum").val();
    todayCountL = $("#lCountRing").val();
    //todayCountL = 5;
    todayCountR = $("#rCountRing").val();
    getRingInfo(mSVG,pathL,pathArrayL, countL,todayCountL,lLinedata,$("#lvl").val(), "左线");
    getRingInfo(mSVG,pathR,pathArrayR, countR,todayCountR,rLinedata,$("#rvr").val(), "右线");
    drawTbm(pathL,pathArrayL, countL);
    drawTbm(pathR,pathArrayR, countR);
    loadSettlePoint(mSVG,hg, settleArray);
    loadCoordinatesPoint(mSVG,hg,lCoordinates,rCoordinates);
    //移动放大到最新施工点
    if($("#goNumAll").val() != 0){
        if($("#pingLorR").val() == "l") {
            moveToNumL($("#goNumAll").val());
        }else if($("#pingLorR").val() == "r"){
            moveToNumR($("#goNumAll").val());
        }
    }else {
        var linepoint = getlinepath(pathArrayL[countL * 4].node);
        moveToCenter(mSVG, linepoint);
    }
    loadingDismiss();

});

function moveToNumL(goNum) {
    console.log(time);
    if(typeof(time)!="undefined"){
        clearInterval(time);
    }
    var linepointL = getlinepath(pathArrayL[goNum * 4].node);
    moveToCenter(mSVG, linepointL);
    var lineL = Snap(document.getElementById("lineL"));
    var polygonArrayL = lineL.selectAll("polygon");
    var color = polygonArrayL[goNum-1].attr("fill");
    timeCount=0;
    time = setInterval(blink, 300,polygonArrayL,goNum,color);
    console.log(goNum);
}
function moveToNumR(goNum) {
    console.log(time);
    var linepointR = getlinepath(pathArrayR[goNum * 4].node);
    moveToCenter(mSVG, linepointR);
    var lineR = Snap(document.getElementById("lineR"));
    var polygonArrayR = lineR.selectAll("polygon");
    var color = polygonArrayR[goNum-1].attr("fill");

    timeCount=0;
    time = setInterval(blink, 300,polygonArrayR,goNum,color);
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

            console.log(matriPoint);

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
                var textStringB = document.createTextNode( "两点间距离:" + Math.sqrt(Math.pow(Math.abs((x1 - x2) / RelMultiple),2)+Math.pow(Math.abs((y1 - y2) / RelMultiple),2)).toFixed(2) + "m");
                textB.appendChild(textStringB);
                svg.append(textB);
            }
        }
    });
}

function getPoint(svg, realPoint, hg) {

    var x1, y1
    svg.click(function(evt) {
        if (isCheckCoordinate == true) {

             realPoint.x = evt.x || evt.clientX;
             realPoint.y = evt.y || evt.clientY;
            var matriPoint = realPoint.matrixTransform(gSVG.firstElementChild.getScreenCTM().inverse());

            var x = matriPoint.x;
            var y = matriPoint.y;

            console.log(matriPoint);

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
            textG1.setAttributeNS(null,"visibility","visible");
            var textStringG1 = document.createTextNode( "东西坐标:" + Math.abs((y1 / RelMultiple) - RelativeY).toFixed(2) + "m");
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
            var textStringG2 = document.createTextNode( "南北坐标:" + Math.abs((x1 / RelMultiple) + RelativeX).toFixed(2) + "m");
            textG2.appendChild(textStringG2);
            svg.append(textG2);
        }

    });
}

// //获取环线信息
// function getRingInfo(svg,path, pathArray,lLinedata,rLinedata, countL, countR) {
//     for (var i = 0; i <= countL; i++) {
//         var newPoints = [];
//         var line = getlinepath(pathArray[i + i * 3].node);
//         var line1 = getlinepath(pathArray[i + i * 3 + 2].node);
//         newPoints.push(line[1]);
//         newPoints.push(line[2]);
//         newPoints.push(line[4]);
//         newPoints.push(line[5]);
//         newPoints.push(line1[1]);
//         newPoints.push(line1[2]);
//         newPoints.push(line1[4]);
//         newPoints.push(line1[5]);
//         pathArray[i + i * 3].attr({ visibility: "visible" });
//         pathArray[i + i * 3 + 2].attr({ visibility: "visible" });
//         var color = "#FFC0CB";
//         if (i == countL) {
//             color = "#0F0";
//         }
//         var circle = path.polygon(newPoints).attr({
//             fill: color,
//             visibility: "visible"
//         });
//         circle.index = i;
//         circle.mouseover(function() {
//             var line2 = getlinepath(pathArray[this.index + this.index * 3].node);
//             var x = parseFloat(line2[1]);
//             var y = parseFloat(line2[2]);
//
//             var id = this.index;
//             var w = $("#svg").width();
//             var h = $("#svg").height();
//             this.textR = document.createElementNS('http://www.w3.org/2000/svg','rect');
//             this.textR.setAttributeNS(null,"id",id);
//             this.textR.setAttributeNS(null,"x",w/2);
//             this.textR.setAttributeNS(null,"y",h/2);
//             this.textR.setAttributeNS(null,"width",210);
//             this.textR.setAttributeNS(null,"height",95);
//             this.textR.setAttributeNS(null,"fill",'#0000ff');
//             this.textR.setAttributeNS(null,"visibility","visible");
//             svg.append(this.textR);
//
//             this.text = document.createElementNS('http://www.w3.org/2000/svg','text');
//             this.text.setAttributeNS(null,"x",w/2+5);
//             this.text.setAttributeNS(null,"y",h/2+25);
//             this.text.setAttributeNS(null,"fill",'#bada55');
//             this.text.setAttributeNS(null,"stroke","#ffff00");
//             this.text.setAttributeNS(null,"strokeWidth",0.1);
//             this.text.setAttributeNS(null,"font-size","20px");
//             this.text.setAttributeNS(null,"visibility","visible");
//             var textString = document.createTextNode( "左线: " + (this.index) + "/" + $("#lvl").val() + "环");
//             this.text.appendChild(textString);
//             svg.append(this.text);
//
//             var mileage = lLinedata[this.index];
//             if(mileage == null){
//                 mileage = "";
//             }
//             this.text1 = document.createElementNS('http://www.w3.org/2000/svg','text');
//             this.text1.setAttributeNS(null,"x",w/2+5);
//             this.text1.setAttributeNS(null,"y",h/2+55);
//             this.text1.setAttributeNS(null,"fill",'#bada55');
//             this.text1.setAttributeNS(null,"stroke","#ffff00");
//             this.text1.setAttributeNS(null,"strokeWidth",0.1);
//             this.text1.setAttributeNS(null,"font-size","20px");
//             this.text1.setAttributeNS(null,"visibility","visible");
//             var textString1 = document.createTextNode("里程：" + mileage  + "m");
//             this.text1.appendChild(textString1);
//             svg.append(this.text1);
//
//             var state = "";
//             if (this.index == countL) {
//                 state = "进行中"
//             } else {
//                 state = "完成"
//             }
//             this.text2 = document.createElementNS('http://www.w3.org/2000/svg','text');
//             this.text2.setAttributeNS(null,"x",w/2+5);
//             this.text2.setAttributeNS(null,"y",h/2+85);
//             this.text2.setAttributeNS(null,"fill",'#bada55');
//             this.text2.setAttributeNS(null,"stroke","#ffff00");
//             this.text2.setAttributeNS(null,"strokeWidth",0.1);
//             this.text2.setAttributeNS(null,"font-size","20px");
//             this.text2.setAttributeNS(null,"visibility","visible");
//             var textString2 = document.createTextNode("状态：" + state);
//             this.text2.appendChild(textString2);
//             svg.append(this.text2);
//         });
//         circle.mouseout(function() {
//
//             this.textR.setAttributeNS(null,"visibility","hidden");
//             this.text.setAttributeNS(null,"visibility","hidden");
//             this.text1.setAttributeNS(null,"visibility","hidden");
//             this.text2.setAttributeNS(null,"visibility","hidden");
//         });
//
//         circle.dblclick(function () {
//             alert(this.index);
//         });
//
//     }
//
//     for (var i = 1152; i < countR + 1152; i++) {
//         var newPoints = [];
//         var line = getlinepath(pathArray[i + i * 3].node);
//         var line1 = getlinepath(pathArray[i + i * 3 + 2].node);
//         newPoints.push(line[1]);
//         newPoints.push(line[2]);
//         newPoints.push(line[4]);
//         newPoints.push(line[5]);
//         newPoints.push(line1[1]);
//         newPoints.push(line1[2]);
//         newPoints.push(line1[4]);
//         newPoints.push(line1[5]);
//         pathArray[i + i * 3].attr({ visibility: "visible" });
//         pathArray[i + i * 3 + 2].attr({ visibility: "visible" });
//         var color = "#FFC0CB";
//         if (i == countR + 1152) {
//             color = "#0F0";
//         }
//         var circle = path.polygon(newPoints).attr({
//             fill: color,
//             visibility: "visible"
//         });
//         circle.index = i;
//         circle.mouseover(function() {
//             var line2 = getlinepath(pathArray[this.index + this.index * 3].node);
//             var x = parseFloat(line2[1]);
//             var y = parseFloat(line2[2]);
//
//             var id = this.index;
//             var w = document.getElementById('svg').offsetWidth;
//             var h = document.getElementById('svg').offsetHeight;
//             this.textR = document.createElementNS('http://www.w3.org/2000/svg','rect');
//             this.textR.setAttributeNS(null,"id",id);
//             this.textR.setAttributeNS(null,"x",w/2);
//             this.textR.setAttributeNS(null,"y",h/2);
//             this.textR.setAttributeNS(null,"width",210);
//             this.textR.setAttributeNS(null,"height",95);
//             this.textR.setAttributeNS(null,"fill",'#0000ff');
//             this.textR.setAttributeNS(null,"visibility","visible");
//             svg.append(this.textR);
//
//             this.text = document.createElementNS('http://www.w3.org/2000/svg','text');
//             this.text.setAttributeNS(null,"x",w/2+5);
//             this.text.setAttributeNS(null,"y",h/2+25);
//             this.text.setAttributeNS(null,"fill",'#bada55');
//             this.text.setAttributeNS(null,"stroke","#ffff00");
//             this.text.setAttributeNS(null,"strokeWidth",0.1);
//             this.text.setAttributeNS(null,"font-size","20px");
//             this.text.setAttributeNS(null,"visibility","visible");
//             var textString = document.createTextNode( "右线: " + (this.index - 1152) + "/" + $("#rvr").val() + "环");
//             this.text.appendChild(textString);
//             svg.append(this.text);
//
//             var mileage = rLinedata[this.index - 1152];
//             if(mileage == null){
//                 mileage = "";
//             }
//             this.text1 = document.createElementNS('http://www.w3.org/2000/svg','text');
//             this.text1.setAttributeNS(null,"x",w/2+5);
//             this.text1.setAttributeNS(null,"y",h/2+55);
//             this.text1.setAttributeNS(null,"fill",'#bada55');
//             this.text1.setAttributeNS(null,"stroke","#ffff00");
//             this.text1.setAttributeNS(null,"strokeWidth",0.1);
//             this.text1.setAttributeNS(null,"font-size","20px");
//             this.text1.setAttributeNS(null,"visibility","visible");
//             var textString1 = document.createTextNode("里程：" + mileage  + "m");
//             this.text1.appendChild(textString1);
//             svg.append(this.text1);
//
//             var state = "";
//             if (this.index == countR + 1152) {
//                 state = "进行中"
//             } else {
//                 state = "完成"
//             }
//             this.text2 = document.createElementNS('http://www.w3.org/2000/svg','text');
//             this.text2.setAttributeNS(null,"x",w/2+5);
//             this.text2.setAttributeNS(null,"y",h/2+85);
//             this.text2.setAttributeNS(null,"fill",'#bada55');
//             this.text2.setAttributeNS(null,"stroke","#ffff00");
//             this.text2.setAttributeNS(null,"strokeWidth",0.1);
//             this.text2.setAttributeNS(null,"font-size","20px");
//             this.text2.setAttributeNS(null,"visibility","visible");
//             var textString2 = document.createTextNode("状态：" + state);
//             this.text2.appendChild(textString2);
//             svg.append(this.text2);
//         });
//         circle.mouseout(function() {
//             if (typeof(rectG) != "undefined") {
//                 rectG.setAttributeNS(null,"visibility","hidden");
//             }
//             if (typeof(textG1) != "undefined") {
//                 textG1.setAttributeNS(null,"visibility","hidden");
//             }
//             if (typeof(textG2) != "undefined") {
//                 textG2.setAttributeNS(null,"visibility","hidden");
//             }
//             if (typeof(rectR) != "undefined") {
//                 rectR.setAttributeNS(null,"visibility","hidden");
//             }
//             if (typeof(textB) != "undefined") {
//                 textB.setAttributeNS(null,"visibility","hidden");
//             }
//             if (typeof(T_line) != "undefined") {
//                 T_line.attr({
//                     visibility: "hidden"
//                 });
//             }
//             this.textR.setAttributeNS(null,"visibility","hidden");
//             this.text.setAttributeNS(null,"visibility","hidden");
//             this.text1.setAttributeNS(null,"visibility","hidden");
//             this.text2.setAttributeNS(null,"visibility","hidden");
//         });
//         circle.dblclick(function () {
//             alert(this.index);
//         });
//
//     }
// }

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

        if(lineName == "左线") {
            circle.dblclick(function () {
                window.parent.gotoTab3L(this.index + 1);
            });
        }else{
            circle.dblclick(function () {
                window.parent.gotoTab3R(this.index + 1);
            });
        }

    }
}

//加载沉降点信息
function loadSettlePoint(msvg,svg,conPoints) {
    for (var i = 0; i < conPoints.length; i++) {
        var circle1 = svg.circle((conPoints[i].x - RelativeX) * CoordMultiple, Math.abs((conPoints[i].y - RelativeY) * CoordMultiple), 2).attr({
            fill: "#C51414",
            visibility: "visible"
        });
        circle1.index = i;
        var spid;
        var id3 = 'circletext3_'+i;
        var id4 = 'circletext4_'+i;
        circle1.mouseover(function() {

            var w = $("#svg").width();
            var h = $("#svg").height();
            rectS = document.createElementNS('http://www.w3.org/2000/svg','rect');
            rectS.setAttributeNS(null,"x",w/2);
            rectS.setAttributeNS(null,"y",h/2);
            rectS.setAttributeNS(null,"width",190);
            rectS.setAttributeNS(null,"height",125);
            rectS.setAttributeNS(null,"fill",'#0000ff');
            rectS.setAttributeNS(null,"visibility","visible");
            msvg.append(rectS);

            textS1 = document.createElementNS('http://www.w3.org/2000/svg','text');
            textS1.setAttributeNS(null,"x",w/2+10);
            textS1.setAttributeNS(null,"y",h/2+25);
            textS1.setAttributeNS(null,"fill",'#bada55');
            textS1.setAttributeNS(null,"stroke","#ffff00");
            textS1.setAttributeNS(null,"strokeWidth",0.1);
            textS1.setAttributeNS(null,"font-size","15px");
            textS1.setAttributeNS(null,"visibility","visible");
            var textString1 = document.createTextNode( "监测点:" + conPoints[this.index].name);
            textS1.appendChild(textString1);
            msvg.append(textS1);

            textS2 = document.createElementNS('http://www.w3.org/2000/svg','text');
            textS2.setAttributeNS(null,"x",w/2+10);
            textS2.setAttributeNS(null,"y",h/2+55);
            textS2.setAttributeNS(null,"fill",'#bada55');
            textS2.setAttributeNS(null,"stroke","#ffff00");
            textS2.setAttributeNS(null,"strokeWidth",0.1);
            textS2.setAttributeNS(null,"font-size","15px");
            textS2.setAttributeNS(null,"visibility","visible");
            var textString2 = document.createTextNode( "时间:" + conPoints[this.index].date);
            textS2.appendChild(textString2);
            msvg.append(textS2);



            // this.background = svg.rect((conPoints[this.index].x - 73339) * 1.6, Math.abs((conPoints[this.index].y - 47180) * 1.6) - 15, 40, 15)
            //     .attr({
            //         fill: "#0000ff",
            //         visibility: "visible"
            //     });
            // this.text = svg.text((conPoints[this.index].x - 73339) * 1.6, Math.abs((conPoints[this.index].y - 47180) * 1.6) - 10, "监测点:" + conPoints[this.index].name)
            //     .attr({
            //         fill: "#bada55",
            //         stroke: '#ffff00',
            //         strokeWidth: 0.1,
            //         "font-size": "3px",
            //         visibility: "visible"
            //     });
            // this.text1 = svg.text((conPoints[this.index].x - 73339) * 1.6, Math.abs((conPoints[this.index].y - 47180) * 1.6) - 7, "时间:" + conPoints[this.index].date)
            //     .attr({
            //         fill: "#bada55",
            //         stroke: '#ffff00',
            //         strokeWidth: 0.1,
            //         "font-size": "3px",
            //         visibility: "visible"
            //     });

            var ax_request = axios.create({
                baseURL:'/monitor/info',
                timeout:6000
            });
            var index_x = conPoints[this.index].x;
            var index_y = conPoints[this.index].y;
            spid = conPoints[this.index].SpId;
            // 发起请求
            ax_request.get('/find/interval/monitor/datas?intervalId='+conPoints[this.index].intervalId+'&intervalSpId='+conPoints[this.index].SpId)
                .then(function(res){
                    var intervalSpId = res.data.intervalSpId;
                    var amount  = res.data.grandSettlement;
                    var rate = res.data.speedSettlement;
                    if(spid != parseInt(intervalSpId)){
                        return;
                    }
                    if( Array.isArray(amount)){
                        amount = amount[0][1];
                    }else{
                        amount = 0;
                    }
                    if( Array.isArray(rate)){
                        rate = rate[0][1];
                    }else{
                        rate = 0;
                    }
                    // this.text2 = svg.text((index_x - 73339) * 1.6, Math.abs(( index_y- 47180) * 1.6) - 4, "最新累计沉降量:" + amount + "mm")
                    //     .attr({
                    //         fill: "#bada55",
                    //         stroke: '#ffff00',
                    //         strokeWidth: 0.1,
                    //         "font-size": "3px",
                    //         visibility: "visible"
                    //     });
                    //
                    // this.text3 = svg.text((index_x - 73339) * 1.6, Math.abs((index_y - 47180) * 1.6) - 1, "最新沉降率:" + rate)
                    //     .attr({
                    //         fill: "#bada55",
                    //         stroke: '#ffff00',
                    //         strokeWidth: 0.1,
                    //         "font-size": "3px",
                    //         visibility: "visible"
                    //     });
                    if(document.getElementById(id3) && document.getElementById(id4)){
                         // msvg.remove("#"+id3);
                         // msvg.remove("#"+id4);
                        var text_3 = document.getElementById(id3);
                        text_3.removeChild(text_3.childNodes[0]);
                        textS3.setAttributeNS(null,"x",w/2+10);
                        textS3.setAttributeNS(null,"y",h/2+85);
                        textS3.setAttributeNS(null,"fill",'#bada55');
                        textS3.setAttributeNS(null,"stroke","#ffff00");
                        textS3.setAttributeNS(null,"strokeWidth",0.1);
                        textS3.setAttributeNS(null,"font-size","15px");
                        textS3.setAttributeNS(null,"visibility","visible");
                        var textString3 = document.createTextNode( "最新累计沉降量:" + amount + "mm");
                        textS3.appendChild(textString3);
                        msvg.append(textS3);

                        var text_4 = document.getElementById(id4);
                        text_4.removeChild(text_4.childNodes[0]);
                        textS4.setAttributeNS(null,"x",w/2+10);
                        textS4.setAttributeNS(null,"y",h/2+115);
                        textS4.setAttributeNS(null,"fill",'#bada55');
                        textS4.setAttributeNS(null,"stroke","#ffff00");
                        textS4.setAttributeNS(null,"strokeWidth",0.1);
                        textS4.setAttributeNS(null,"font-size","15px");
                        textS4.setAttributeNS(null,"visibility","visible");
                        var textString4 = document.createTextNode("最新沉降率:" + rate);
                        textS4.appendChild(textString4);
                        msvg.append(textS4);
                        return;
                    }
                    textS3 = document.createElementNS('http://www.w3.org/2000/svg','text');
                    textS3.setAttributeNS(null,"id",id3);
                    textS3.setAttributeNS(null,"x",w/2+10);
                    textS3.setAttributeNS(null,"y",h/2+85);
                    textS3.setAttributeNS(null,"fill",'#bada55');
                    textS3.setAttributeNS(null,"stroke","#ffff00");
                    textS3.setAttributeNS(null,"strokeWidth",0.1);
                    textS3.setAttributeNS(null,"font-size","15px");
                    textS3.setAttributeNS(null,"visibility","visible");
                    var textString3 = document.createTextNode( "最新累计沉降量:" + amount + "mm");
                    textS3.appendChild(textString3);
                    msvg.append(textS3);

                    textS4 = document.createElementNS('http://www.w3.org/2000/svg','text');
                    textS4.setAttributeNS(null,"id",id4);
                    textS4.setAttributeNS(null,"x",w/2+10);
                    textS4.setAttributeNS(null,"y",h/2+115);
                    textS4.setAttributeNS(null,"fill",'#bada55');
                    textS4.setAttributeNS(null,"stroke","#ffff00");
                    textS4.setAttributeNS(null,"strokeWidth",0.1);
                    textS4.setAttributeNS(null,"font-size","15px");
                    textS4.setAttributeNS(null,"visibility","visible");
                    var textString4 = document.createTextNode("最新沉降率:" + rate);
                    textS4.appendChild(textString4);
                    msvg.append(textS4);

                })
                .catch(function(err){
                    console.log(err);

                });


            // this.text2 = svg.text((conPoints[this.index].x - 73339) * 1.6, Math.abs((conPoints[this.index].y - 47180) * 1.6) - 4, "最新累计沉降量:" + conPoints[this.index].amount + "mm")
            //     .attr({
            //         fill: "#bada55",
            //         stroke: '#ffff00',
            //         strokeWidth: 0.1,
            //         "font-size": "3px",
            //         visibility: "visible"
            //     });
            // this.text3 = svg.text((conPoints[this.index].x - 73339) * 1.6, Math.abs((conPoints[this.index].y - 47180) * 1.6) - 1, "最新沉降率:" + conPoints[this.index].rate)
            //     .attr({
            //         fill: "#bada55",
            //         stroke: '#ffff00',
            //         strokeWidth: 0.1,
            //         "font-size": "3px",
            //         visibility: "visible"
            //     });
        });
        circle1.mouseout(function() {
            // this.background.attr({
            //     visibility: "hidden"
            // });
            // this.text.attr({
            //     visibility: "hidden"
            // });
            // this.text1.attr({
            //     visibility: "hidden"
            // });
            // this.text2.attr({
            //     visibility: "hidden"
            // });
            // this.text3.attr({
            //     visibility: "hidden"
            // });
            spid = null;
            rectS.setAttributeNS(null,"visibility","hidden");
            textS1.setAttributeNS(null,"visibility","hidden");
            textS2.setAttributeNS(null,"visibility","hidden");
            textS3.setAttributeNS(null,"visibility","hidden");
            textS4.setAttributeNS(null,"visibility","hidden");
        });

    }
}

//加载平面坐标点信息
function loadCoordinatesPoint(msvg,svg,lCoordinates,rCoordinates) {
    var larr = new Array();
    $.each(lCoordinates,function (name, value){
        if(value.X && value.Y){
            larr.push([value.Y, value.X,value.SP,value.CZ]);
        }

    });

    for (var i = 0; i < larr.length; i++) {

        var circle1 = svg.circle((larr[i][0] - RelativeX) * CoordMultiple, Math.abs((larr[i][1] - RelativeY) * CoordMultiple), 1).attr({
            fill: "#0000ff",
            visibility: "visible",
            name:"coordinates"
        });
        if (i < larr.length - 1) {
            svg.line((larr[i][0] - RelativeX) * CoordMultiple, Math.abs((larr[i][1] - RelativeY) * CoordMultiple), (larr[i + 1][0] - RelativeX) * CoordMultiple, Math.abs((larr[i + 1][1] - RelativeY) * CoordMultiple)).attr({
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
            var textStringC = document.createTextNode("盾尾水平偏差:" + larr[this.index][2] + "mm");
            this.textCL.appendChild(textStringC);
            msvg.append(this.textCL);

        });
        circle1.mouseout(function() {
            this.rectCL.setAttributeNS(null,"visibility","hidden");
            this.textCL.setAttributeNS(null,"visibility","hidden");
        });
    }
    var rarr = new Array();
    $.each(rCoordinates,function (name, value){
        if(value.X && value.Y){
            rarr.push([value.Y, value.X,value.SP,value.CZ]);
        }

    });
    for (var i = 0; i < rarr.length; i++) {

        var circle1 = svg.circle((rarr[i][0] - RelativeX) * CoordMultiple, Math.abs((rarr[i][1] - RelativeY) * CoordMultiple), 1).attr({
            fill: "#0000ff",
            visibility: "visible",
            name:"coordinates"
        });
        if (i < rarr.length - 1) {
            svg.line((rarr[i][0] - RelativeX) * CoordMultiple, Math.abs((rarr[i][1] - RelativeY) * CoordMultiple), (rarr[i + 1][0] - RelativeX) * CoordMultiple, Math.abs((rarr[i + 1][1] - RelativeY) * CoordMultiple)).attr({
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

            this.rectCR = document.createElementNS('http://www.w3.org/2000/svg','rect');
            this.rectCR.setAttributeNS(null,"x",w/2);
            this.rectCR.setAttributeNS(null,"y",h/2);
            this.rectCR.setAttributeNS(null,"width",200);
            this.rectCR.setAttributeNS(null,"height",35);
            this.rectCR.setAttributeNS(null,"fill",'#0000ff');
            this.rectCR.setAttributeNS(null,"visibility","visible");
            msvg.append(this.rectCR);

            this.textCR = document.createElementNS('http://www.w3.org/2000/svg','text');
            this.textCR.setAttributeNS(null,"x",w/2+5);
            this.textCR.setAttributeNS(null,"y",h/2+25);
            this.textCR.setAttributeNS(null,"fill",'#bada55');
            this.textCR.setAttributeNS(null,"stroke","#ffff00");
            this.textCR.setAttributeNS(null,"strokeWidth",0.1);
            this.textCR.setAttributeNS(null,"font-size","20px");
            this.textCR.setAttributeNS(null,"visibility","visible");
            var textStringC = document.createTextNode("盾尾水平偏差:" + rarr[this.index][2] + "mm");
            this.textCR.appendChild(textStringC);
            msvg.append(this.textCR);

        });
        circle1.mouseout(function() {
            this.rectCR.setAttributeNS(null,"visibility","hidden");
            this.textCR.setAttributeNS(null,"visibility","hidden");
        });

    }
}

// function drawTbm(path,pathArray, countL,countR) {
//     if(countL==0){
//         return;
//     }
//     var color = "#ffff00";
//     var strokecolor = "#ED7D40";
//     for (var i = countL*1; i < countL*1 + 5; i++) {
//
//         var newPoints = [];
//         var line = getlinepath(pathArray[i + i * 3+4].node);
//         var line1 = getlinepath(pathArray[i + i * 3 + 6].node);
//         newPoints.push(line[1]);
//         newPoints.push(line[2]);
//         newPoints.push(line[4]);
//         newPoints.push(line[5]);
//         newPoints.push(line1[1]);
//         newPoints.push(line1[2]);
//         newPoints.push(line1[4]);
//         newPoints.push(line1[5]);
//         var circle = path.polygon(newPoints).attr({
//             stroke: color,
//             fill: color,
//             visibility: "visible"
//         });
//         var newPoints1 = [];
//         var newPoints2 = [];
//         var line = getlinepath(pathArray[i + i * 3+5].node);
//         var line1 = getlinepath(pathArray[i + i * 3 + 3].node);
//         newPoints1.push(line[1]);
//         newPoints1.push(line[2]);
//         newPoints1.push(line[4]);
//         newPoints1.push(line[5]);
//
//         path.polyline(newPoints1).attr({
//             stroke: strokecolor,
//             visibility: "visible"
//         });
//
//         newPoints2.push(line1[1]);
//         newPoints2.push(line1[2]);
//         newPoints2.push(line1[4]);
//         newPoints2.push(line1[5]);
//
//         path.polyline(newPoints2).attr({
//             stroke: strokecolor,
//             visibility: "visible"
//         });
//
//         if(i == countL) {
//             var line = getlinepath(pathArray[i + i * 3].node);
//             var startPoints =[];
//             startPoints.push(line[1]);
//             startPoints.push(line[2]);
//             startPoints.push(line[4]);
//             startPoints.push(line[5]);
//             path.polyline(startPoints).attr({
//                 stroke: strokecolor,
//                 visibility: "visible"
//             });
//         }
//         if(i==countL*1 + 4) {
//             var line = getlinepath(pathArray[i + i * 3 + 2+8].node);
//             var endPoints =[];
//             endPoints.push(line[1]);
//             endPoints.push(line[2]);
//             endPoints.push(line[4]);
//             endPoints.push(line[5]);
//             path.polyline(endPoints).attr({
//                 stroke: strokecolor,
//                 visibility: "visible"
//             });
//         }
//     }
//
//     for (var i = countR*1+1152; i < countR*1 + 5+1152; i++) {
//         if(countR==0){
//             return;
//         }
//         var newPoints = [];
//         var line = getlinepath(pathArray[i + i * 3+4].node);
//         var line1 = getlinepath(pathArray[i + i * 3 + 6].node);
//         newPoints.push(line[1]);
//         newPoints.push(line[2]);
//         newPoints.push(line[4]);
//         newPoints.push(line[5]);
//         newPoints.push(line1[1]);
//         newPoints.push(line1[2]);
//         newPoints.push(line1[4]);
//         newPoints.push(line1[5]);
//         var circle = path.polygon(newPoints).attr({
//             stroke: color,
//             fill: color,
//             visibility: "visible"
//         });
//         var newPoints1 = [];
//         var newPoints2 = [];
//         var line = getlinepath(pathArray[i + i * 3+5].node);
//         var line1 = getlinepath(pathArray[i + i * 3 + 3].node);
//         newPoints1.push(line[1]);
//         newPoints1.push(line[2]);
//         newPoints1.push(line[4]);
//         newPoints1.push(line[5]);
//
//         path.polyline(newPoints1).attr({
//             stroke: strokecolor,
//             visibility: "visible"
//         });
//
//         newPoints2.push(line1[1]);
//         newPoints2.push(line1[2]);
//         newPoints2.push(line1[4]);
//         newPoints2.push(line1[5]);
//
//         path.polyline(newPoints2).attr({
//             stroke: strokecolor,
//             visibility: "visible"
//         });
//
//         if(i == countL) {
//             var line = getlinepath(pathArray[i + i * 3].node);
//             var startPoints =[];
//             startPoints.push(line[1]);
//             startPoints.push(line[2]);
//             startPoints.push(line[4]);
//             startPoints.push(line[5]);
//             path.polyline(startPoints).attr({
//                 stroke: strokecolor,
//                 visibility: "visible"
//             });
//         }
//         if(i==countL*1 + 4) {
//             var line = getlinepath(pathArray[i + i * 3 + 2+8].node);
//             var endPoints =[];
//             endPoints.push(line[1]);
//             endPoints.push(line[2]);
//             endPoints.push(line[4]);
//             endPoints.push(line[5]);
//             path.polyline(endPoints).attr({
//                 stroke: strokecolor,
//                 visibility: "visible"
//             });
//         }
//     }
// }
function drawTbm(path,pathArray, count) {
    if(count==0){
        return;
    }
    var color = "#ffff00";
    var strokecolor = "#ED7D40";
    for (var i = count*1; i < count*1 + 5; i++) {

        var newPoints = [];
        var line = getlinepath(pathArray[i + i * 3+1].node);
        var line1 = getlinepath(pathArray[i + i * 3 + 3].node);
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
        var line = getlinepath(pathArray[i + i * 3].node);
        var line1 = getlinepath(pathArray[i + i * 3 + 2].node);
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
            var line = getlinepath(pathArray[i + i * 3+1-4].node);
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
            var line = getlinepath(pathArray[i + i * 3 + 1].node);
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

function hidecoord(){
    var nv = document.getElementById("hidecoord");
    nv.innerHTML="显示线路";
    nv.onclick =function(){showcoord()};
    var lineList = $("*[name='coordinates']");
    console.log(lineList);
    for(var i=0;i<lineList.length;i++){
        Snap(lineList[i]).attr({ visibility: "hidden" });
    }
}

function showcoord(){
    var nv = document.getElementById("hidecoord");
    nv.innerHTML="隐藏线路";
    nv.onclick = function(){hidecoord()};
    var lineList = $("*[name='coordinates']");
    console.log(lineList);
    for(var i=0;i<lineList.length;i++){
        Snap(lineList[i]).attr({ visibility: "visible" });
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

// //移动放大到左线最新施工点
// function clickLeft() {
//     var path = Snap(document.getElementById("line")).attr({ visibility: "hidden" });
//     var pathArray = path.selectAll("path");
//     var countL = $("#lRingNum").val();
//     if(!parseInt(countL)){
//         alert("左线暂时未开始施工");
//         return;
//     }
//     //移动放大到最新施工点
//     var linepoint = getlinepath(pathArray[countL * 4].node);
//     moveToCenter(mSVG, linepoint);
// }
//
// //移动放大到右线最新施工点
// function clickRight() {
//     var path = Snap(document.getElementById("line")).attr({ visibility: "hidden" });
//     var pathArray = path.selectAll("path");
//     var countR = $("#rRingNum").val();
//     console.log(parseInt(countR));
//     if(!parseInt(countR)){
//         alert("右线暂时未开始施工");
//         return;
//     }
//     //移动放大到最新施工点
//     var linepoint = getlinepath(pathArray[countR * 4].node);
//     moveToCenter(mSVG, linepoint);
// }

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


function getRisk(riskData) {
    for (var i = 0; i < riskData.length; i++){
        if(riskData[i].gType == 1) {
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
