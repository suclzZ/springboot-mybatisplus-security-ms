/*
    组件-field
*/
!(function($){

    $('.modal:not(.modal-sp)').on('show.bs.modal',function(e){
          //清空原始值
          $(e.currentTarget).find('form:first').valiForm().load(null);
          //初始化组件
          $.componentUtils.initModal($(e.currentTarget));
    });
    $('.modal:not(.modal-sp)').on('hide.bs.modal',function(e){
          console.info('before modal hide')
    })

    var Component = function($comp , options){
        this.element = $comp || {};
        this.options = options;
    }

    Component.prototype = {
        init : function(){
            var comp = this.element.data('comp'),
            args = this.options || Array.prototype.slice.call(arguments);
            $.isArray(args) ||(args = [args]);
            comp && $.componentUtils[comp]['init'].apply(this.element,args);
        },
        value : function(val){
            var comp = this.element.data('comp');
            comp && $.componentUtils[comp]['value'].call(this.element,val);
        }
    }

    $.fn.component = function(options){
        return new Component($(this),options);
    }

    $.componentUtils = {
        initModal : function($modal){
            $modal.find('.form-control').each(function(){//[data-comp]
                var comp = $(this).data('comp');
                comp && $.componentUtils[comp]['init'].call($(this));
            })
        },
        text : {
            value :function(val){
                if(val!=0[0]){//set
                    this.val(val);
                }else{//get
                    return this.val();
                }
            }
        },
        textarea:{
            value :function(val){
                if(val!=0[0]){//set
                    this.val(val);
                }else{//get
                    return this.val();
                }
            }
        },
        select : {
            init : function(options){
                options = options ||{};
                //options url code show
                var url = options.url || this.data('url'),code = options.code||this.data('code'),show = options.show||this.data('show');
                var defaultVal = this.data('default'),that = this;
                if(url && code && show){
                    this.empty();
                    if(options.require==true){
                        this.append('<option></option>');
                    }
                    $.vali.uitl.ajaxUtils.ajax({
                        url:url,
                        data:options.data,
                        dataType:'json',
                        async:false,
                        success:function(res){
                            if(res && $.type(res) == 'array'){
                                for(var i in res){
                                    var selected = '';
                                    if(defaultVal && res[i][code] == defaultVal){
                                        selected = 'selected';
                                    }
                                    that.append('<option title="'+(options.title?res[i][options.title]:'')+'" value="'+res[i][code]+'" '+selected+'>'+res[i][show]+'</option>');
                                }
                            }
                            $.isFunction( options.callback) && options.callback.call(this);
                        }
                    });
                }
            },
            value :function(val){
                if(val!=0[0]){//set
                    this.find('option[value="'+val+'"]').attr('selected','selected');
                }else{//get
                    return this.find('option:selected').prop('value') ;
                }
            }
        },
        /*
        * 通过jstree插件实现
        * url 对应{id:"",text:"",data:"",state:{},type:"",li_attr:"",a_attr:"",children:{}}
        */
        tree : {
            init:function(options){
                this.jstree({
                    'core' : {
                        'themes' : {'responsive': false},
                        'check_callback' : true,
                        'data' : { url:options.url}
                    },
                    'types' : {
                        'default' : {'icon' : 'fa fa-folder'},
                        'file' : {'icon' : 'fa fa-file'	}
                    },
                    'plugins': ['types']
                }).on('changed.jstree', function (e, data) {
                    var events = options && options.events;
                    if($.isArray(events)){

                    }else{
                        events && events.call(this,e,data);
                    }
                });
            }
        }
    }

})(jQuery);