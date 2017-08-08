$("form").validate({
    debug:true,
    errorClass:'has-error',
    highlight: function(element,errorClass){
        $(element).parents('.form-group').addClass('has-error');
    },
    unhighlight:function(element,errorClass){
        $(element).parents('.form-group').removeClass(errorClass);
    }
});
