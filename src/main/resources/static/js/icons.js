        $(function(){
          $("#menu").on("click","li",function(){
            var obj = $("#"+$(this).attr("role")),
                st = obj.offset().top;
            jQuery('body,html').animate({
              scrollTop: st
            }, 1000);
          });
          $(".icons-list span").on("click",function(){
            selectText(this);
          });
        });
        function selectText(t) {
          if (document.selection) {
            var range = document.body.createTextRange();
            range.moveToElementText(t);
            range.select();
          } else if (window.getSelection) {
            var range = document.createRange();
            range.selectNode(t);
            window.getSelection().addRange(range);
          }
        }