;!function($){

    var methods {}
    ,defaults = {
        ui : 'text'
    }
    ;

    methods.value = function(setting){
        if(setting){
            this.val(setting);
        }else{
            return this.val();
        }
    };
    methods.init = function(){

    }

    $.fn.valiText = function(method){
        if(this.data('ui') != defaults.ui){
            return;
        }
        this.each(function(){
            if(methods[method]){
                methods[method].apply(this,Array.slice.prototype.call(arguments,1));
            }
            if (typeof method === 'object' || ! method) {
                methods.init.apply(this, arguments);
            }
        });
        return this;
    }
}(jQuery)