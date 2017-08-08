function initSlurry(){
    var mainRect = $('#slurry').get(0);
    var img = $('.slurry_bg').get(0);
    var container = $('.slurry_container').get(0);

    new ResizeSensor(mainRect,function(){
        ajustRectSize(img,mainRect);
        $(container).width(img.clientWidth);
        $(container).height(img.clientHeight);
    });

    setTimeout(function(){
        ajustRectSize(img,mainRect);
        $(container).width(img.clientWidth);
        $(container).height(img.clientHeight);
    },300);
}


/**
 * 根据父元素的width & height 调整子元素的尺寸，使子元素总在父元素中可见
 */
function ajustRectSize(child,parent){
    var _pWidth = parent.clientWidth;
    var _pHeight = parent.clientHeight;
    var _scale = 1366/768;
    var _pScale = _pWidth/_pHeight;
    if(_pScale >= _pScale){
        child.style.height = _pHeight+"px";
        child.style.width = (_pHeight*_scale)+"px";
    }else{
        child.style.width = _pWidth+"px";
        child.style.height = (_pWidth * 768 / 1366)+"px";
    }
}

