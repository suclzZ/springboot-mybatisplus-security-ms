/*
下拉框组件
 data-ui = 'select'

*/
;!function($){

    var methods {}
    ,defaults = {
        ui : 'select'
    }
    ;

    methods.value = function(setting){
        if(setting){
            this.val(setting);
        }else{
            return this.val();
        }
    };
    methods.init = function(setting){
        setting = $.extend({},setting,defaults);


    }

    $.fn.valiSelect = function(method){
        if(this.data('ui') != defaults.ui){
            return;
        }
        this.each(function(){
            if(methods[method]){
                methods[method].apply(this,Array.slice.prototype.call(arguments,1));
            }
            if(typeof method === object  || !method){
                methods.init.apply(this,Array.prototype.slice(arguments,0));
            }
        });
        return this;
    }
}(jQuery)