!(function($){
    $.vali = $.vali||{};

    $.vali.uitl = {
        ajaxUtils :{
            ajax : function(options){
                var defaultOptions = {
                    dataType:'json',
                    type:'get',
                    beforeSend:function(){},
                    complete:function(){}
                };
                var oldSuccess = options.success || function(){};
                var oldError = options.error || function(){};
                options.success = function(){
                    oldSuccess && oldSuccess.apply(this,Array.prototype.slice.call(arguments,0));
                }
                options.error = function(jqXHR, textStatus, errorThrown){
                    console.error('ajax服务异常',jqXHR && jqXHR.responseJSON,textStatus,errorThrown)
                    oldError && oldError.apply(this,Array.prototype.slice.call(arguments,0));
                }
                $.ajax($.extend(defaultOptions, options));
            }
        },
        /*
        * 提示弹框
        */
        messageUtils:{
            show:function(title,message,callback){
                 var $msg = $('.msg-modal')
                 var html = $msg.html();
                 $msg.find('.msg-title').text(title);
                 $msg.find('.msg-info').text(message);
                 $msg.unbind().on('click','button.btn-primary',function(e){
                        callback && callback.apply(e);
                        $msg.modal('hide');
                 });
                 $msg.modal('show');
             },
             notify:function(title,message){
                $.notify({
                    title: title,
                    message: message,
                    icon: 'fa fa-check'
                },{
                    type: "info"
                });
            }
        },
        recordUtils:{
            recordToParams:function(record){
                var params = [];
                for(var prop in record){
                    var val = record[prop];
                    if($.type(val)=='array'){
                        for(var i in val){
                            params.push(prop+'['+i+']='+val[i]);// name[0]=zs&name[1]=ls
                        }
                    }else{
                        params.push(prop +'='+val);
                    }
                }
                return params.join('&');
            },
            convert:function(cv ,code){
                var converts = $.eui.convert[cv];
                if(converts && converts.length >0 &&code){
                    for(var i in converts){
                        if(converts[i]['code'] == code){
                            return converts[i]['show'];
                        }
                    }
                }
                return code;
            }
        },
    }
})(jQuery);