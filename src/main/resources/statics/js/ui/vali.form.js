!(function($){
    $.vali = $.vali||{};

    var valiForm = function($form,options){
        this.element = $form;
        this.options = options;
    }

    valiForm.prototype = {
        submit : function(){
            var record = this.getRecord(),that = this;
            if(!record) return;
            $.vali.uitl.ajaxUtils.ajax({
                url:this.element.attr('action'),
                type:'post',
                data:$.vali.uitl.recordUtils.recordToParams(record),
                contentType:'application/x-www-form-urlencoded;charset=utf-8 ',
                success:function(res){
                    that.element.parents('.modal').modal('hide');
                    $.vali.uitl.messageUtils.notify('','数据保存成功');
                    var afterSubmit = that.element.prop('id')+'_aftersubmit';
                    if(window[afterSubmit] !=null && $.isFunction(window[afterSubmit])){
                        window[afterSubmit].call(that,res);
                    }
                }
            });
        },
        getRecord : function(){
            var record = {};
            var checkinfo = [];
            this.element.find('.form-control').each(function(){
                var comp = $(this).data('comp');
                var prop = $(this).attr('name'),val;
                if(comp){
                    val = $.componentUtils[comp]['value'].call($form,$(this));
                }else{
                    val =  $(this).val();
                }
                if($(this).parents('.form-group').hasClass('require') && !val){
                    checkinfo.push($(this).parent().prev().text());
                }
                val && (record[prop] = val);
            });
            if(checkinfo.length>0){
                $.vali.uitl.messageUtils.show('提示','【'+checkinfo.join('、')+'】不能为空！');
                return null;
            }
            return record;
        },
        load : function(record){//record位null则为指 通过fixed可以忽略重置
            this.element.find('.form-control').each(function(){
                var comp = $(this).data('comp') || $(this).attr('type')||$(this)[0].tagName.toLowerCase()||'text';
                var prop = $(this).attr('name'),val = record?record[prop]:'';
                if(!$(this).hasClass('fixed'))
                    $.componentUtils[comp]['value'].call($(this),val);
            })
        }
    }

    $.fn.valiForm = function(options){
        var _valiForm = new valiForm($(this),options);
        return _valiForm;
    };

//    $.fn.valiForm = function(method,options){
//        var _valiForm = $(this).data('form');
//        if(!_valiForm){
//            _valiForm = new valiForm($(this),options);
//            $(this).data('form',_valiForm);
//        }
//        ValiForm.prototype[method].call(_valiForm,option);
//    }
})(jQuery);