;!function($){
    var defaults = {
        success : function(){},
        error : function(){},
        contexType : "application/x-www-form-urlencoded",
        dataType : "json"
    }

    $.valiAjax = function(options){
        options = $.extend({},defaults,options);
        $.ajax(options);
    }

}(jQuery)