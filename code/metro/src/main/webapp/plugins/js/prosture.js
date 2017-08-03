function Prosture(container){
    this._stage = new createjs.Stage(container); 
    this._container = container;
    this.x1 = this.x2 = 0;
    this.y1 = this.y2 = 0;
    this.update = init.bind(this);
    // 注册窗口变化参数
    $(window).resize(init.bind(this));
    init.call(this);

    // 取一个数值的中间值
    function mid(value){
        return value/2.0;
    };
    
    function drawRound(stage,args){
        var circles = new createjs.Container();
        args.forEach(function(arg){
            var circle = new createjs.Shape();
            if(arg.type === 1){
                circle.graphics.setStrokeDash([2,2],0);
            }
            circle.graphics.beginStroke('#000');
            circle.graphics.drawCircle(arg.center.x,arg.center.y,arg.radius);
            circles.addChild(circle);
        });
        stage.addChild(circles);
    }
    
    // 画线
    function drawLines(stage,points){
        var coordinate = new createjs.Container();
        points.forEach(function(point){
            var line = new createjs.Shape();
            if(point.type === 1){
                line.graphics.setStrokeDash([2,2],0);
            }
            line.graphics.beginStroke('#000');
            line.graphics.mt(point.start.x,point.start.y)
                .lt(point.end.x,point.end.y);
            // ToDo: draw tags here
            var tag = new createjs.Text(point.value,"10px Arial","#000");
            tag.x = point.tagPosition.x;
            tag.y = point.tagPosition.y;
            coordinate.addChild(line,tag);
        });
        stage.addChild(coordinate);
    }

    // 绘制两个十字和相连的虚线
    // x1,y1(前盾坐标)
    // x2,y2(后盾坐标)
    // s_axies 最小轴长
    function drawPosture(x1,y1,x2,y2,ox,oy,stage,s_axies){
        var unit = s_axies*0.0625; // 十字的半径，默认为十六分之一最短轴长
        var db_unit = unit*2; // 大十字半径，默认为八分之一轴长
        var data = [
            {
                value:'',
                type:0,
                start:{x:ox+(x1-unit),y:oy-y1},
                end:{x:ox+(x1+unit),y:oy-y1},
                tagPosition:{x:0,y:0}
            },
            {
                value:'',
                type:0,
                start:{x:ox+x1,y:oy-(y1-unit)},
                end:{x:ox+x1,y:oy-(y1+unit)},
                tagPosition:{x:0,y:0}
            },
            {
                value:'',
                type:0,
                start:{x:ox+(x2-db_unit),y:oy-y2},
                end:{x:ox+(x2+db_unit),y:oy-y2},
                tagPosition:{x:0,y:0}
            },
            {
                value:'',
                type:0,
                start:{x:ox+x2,y:oy-(y2-db_unit)},
                end:{x:ox+x2,y:oy-(y2+db_unit)},
                tagPosition:{x:0,y:0}
            },
            {
                value:'',
                type:1,
                start:{x:ox+x1,y:oy-y1},
                end:{x:ox+x2,y:oy-y2},
                tagPosition:{x:0,y:0}
            }
        ]
        drawLines(stage,data);
    }

    function init(){
        this._stage.canvas.width = $('#'+container).width();
        var sw= this._stage.canvas.width = $('#'+container).width(); // 屏幕宽度
        var sh= this._stage.canvas.height = $('#'+container).height(); // 屏幕高度
        var s_axies = sw < sh ? sw : sh; // 最短轴长
        var padding = s_axies * 0.02; // 坐标轴距离屏幕的距离
        var ox = sw/2.0; // 中心坐标X
        var oy = sh/2.0; // 中心坐标Y
        var marks_points = [ // 坐标轴刻度线,每个元素包含起始点和结束点,以及数值
            {
                value:"",
                type:0,
                start:{x:padding,y:oy},
                end:{x:sw-padding,y:oy},
                tagPosition:{x:sw-padding-5,y:oy+10}
            },
            {
                value:"",
                type:0,
                start:{x:ox,y:padding},
                end:{x:ox,y:sh-padding},
                tagPosition:{x:sw-padding-5,y:oy+10}
            },
            {
                value:"-100",
                type:0,
                start:{x:ox-s_axies/2+2*padding,y:oy-padding},
                end:{x:ox-s_axies/2+2*padding,y:oy},
                tagPosition:{x:ox-s_axies/2+2*padding-10,y:oy+10}
            },
            {
                value:"-50",
                type:0,
                start:{x:ox-s_axies/4+padding,y:oy-padding},
                end:{x:ox-s_axies/4+padding,y:oy},
                tagPosition:{x:ox-s_axies/4+padding-10,y:oy+10}
            },
            {
                value:"50",
                type:0,
                start:{x:ox+s_axies/4-padding,y:oy-padding},
                end:{x:ox+s_axies/4-padding,y:oy},
                tagPosition:{x:ox+s_axies/4-padding-10,y:oy+10}
            },
            {
                value:"100",
                type:0,
                start:{x:ox+s_axies/2-2*padding,y:oy-padding},
                end:{x:ox+s_axies/2-2*padding,y:oy},
                tagPosition:{x:ox+s_axies/2-2*padding-10,y:oy+10}
            },
            {
                value:"100",
                type:0,
                start:{x:ox+padding,y:oy-s_axies/2+2*padding},
                end:{x:ox,y:oy-s_axies/2+2*padding},
                tagPosition:{x:ox+padding+5,y:oy-s_axies/2+2*padding}
            },
            {
                value:"50",
                type:0,
                start:{x:ox+padding,y:oy-s_axies/4+padding},
                end:{x:ox,y:oy-s_axies/4+padding},
                tagPosition:{x:ox+padding+5,y:oy-s_axies/4+padding}
            },
            {
                value:"-50",
                type:0,
                start:{x:ox+padding,y:oy+s_axies/4-padding},
                end:{x:ox,y:oy+s_axies/4-padding},
                tagPosition:{x:ox+padding,y:oy+s_axies/4-padding}
            },
            {
                value:"-100",
                type:0,
                start:{x:ox+padding,y:oy+s_axies/2-2*padding},
                end:{x:ox,y:oy+s_axies/2-2*padding},
                tagPosition:{x:ox+padding,y:oy+s_axies/2-2*padding}
            },
        ]

        var round_args = [
            { 
                type:0,
                center:{x:ox,y:oy},
                radius:s_axies/4-padding
            },
            {
                type:1,
                center:{x:ox,y:oy},
                radius:s_axies/2-2*padding
            }
        ];

        this._stage.removeAllChildren();
        drawLines(this._stage,marks_points);
        drawRound(this._stage,round_args);
        drawPosture(this.x1,this.y1,this.x2,this.y2,ox,oy,this._stage,s_axies);
        this._stage.update();
    }

}
