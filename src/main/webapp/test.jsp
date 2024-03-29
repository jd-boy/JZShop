<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="test" pagination="p-new" pagenumber="5" totalnumber="15" paginationmax="10">
		<ul class="pagination">
	    <li><a href="#">«</a></li>
	    <li><a href="#">1</a></li>
	    <li><a href="#">2</a></li>
	    <li class="active"><a href="#">3</a></li>
	    <li class="disabled"><a href="#">»</a></li>
</ul>
	</div>
</body>

<script language="javascript">

	function initPagination() {
		
		//当前页数
        var pagenumber;
        //总页数
        var totalnumber;
        //分页栏显示的页数
        var paginationmax;
        paginationInit();
        function initPagination(element){
            pagenumber = Number(element.attr('pagenumber'));
            totalnumber = Number(element.attr('totalnumber'));
            paginationmax = Number(element.attr('paginationmax'));
            if(totalnumber >= 1 && pagenumber <= totalnumber && paginationmax <= totalnumber){
                var content =
                    "<ul class='pagination'>" +
                        "<li value='pre'>" +
                            "<a href='javascript:void(0);'>«</a>" +
                        "</li>";
                for (var i = 0; i < totalnumber; i++) {
                    content +=
                        "<li value='"+ (i + 1) +"'>" +
                            "<a href='javascript:void(0);'>" + (i + 1) +
                            "</a>" +
                        "</li>"
                }
                content +=
                        "<li value='next'>" +
                            "<a href='javascript:void(0);'>»</a>" +
                        "</li>" +
                    "</ul>";
                element.append(content);
				//为设置为当前页的页签添加样式
                element.children('ul').children('li[value = '+ pagenumber +']').addClass('active');
                element.children('ul').children('li').click(clickChange);
                element.children('ul').children('li').click(processData);
				//显示那几个页签 传入任意li元素即可
                pageShow(element.children('ul').children('li[value = '+ pagenumber +']'))
            }else{
                console.log('分页自定义属性不合理')
            }
        };
		
	}
	
	//凡是带有pagination = p-new属性的元素，都会生成分页，这样设计方便一个页面中有多个不同的分页
    function paginationInit(){
        $('[pagination = p-new]').each(function(){
            initPagination($(this))
        })
    };
    
//  点击页签时候样式的变化
    function clickChange(ev){
        ev = event || window.event;
        pageShow($(ev.target).parent());

        $(ev.target).parent().parent().children('li').each(function(index,item){
            if($(item).hasClass('active')){
                $(item).removeClass('active');
            }
        });
//            点击页码页签
        if($(ev.target).parent().attr('value') != 'pre' && $(ev.target).parent().attr('value') != 'next'){
            pagenumber = Number($(ev.target).parent().attr('value'))
            $(ev.target).parent().addClass('active');
            $(ev.target).parent().parent().children('li[value = pre]').removeClass('disabled');
            $(ev.target).parent().parent().children('li[value = next]').removeClass('disabled');
//            点击上一页页签
        }else if($(ev.target).parent().attr('value') == 'pre'){
            pagenumber -= 1;
            if(pagenumber <= 1){
                pagenumber = 1;
                $(ev.target).parent().parent().children('li[value = 1]').addClass('active');
                $(ev.target).parent().parent().children('li[value = pre]').addClass('disabled');
            }else{
                $(ev.target).parent().parent().children('li[value = '+ pagenumber.toString() +']').addClass('active');
                $(ev.target).parent().parent().children('li[value = pre]').removeClass('disabled');
                $(ev.target).parent().parent().children('li[value = next]').removeClass('disabled');
            }
//            点击下一页页签
        }else if($(ev.target).parent().attr('value') == 'next'){
            pagenumber += 1;
            if(pagenumber >= totalnumber){
                pagenumber = totalnumber;
                $(ev.target).parent().parent().children('li[value = '+ totalnumber +']').addClass('active');
                $(ev.target).parent().parent().children('li[value = next]').addClass('disabled');
            }else{
                $(ev.target).parent().parent().children('li[value = '+ pagenumber.toString() +']').addClass('active');
                $(ev.target).parent().parent().children('li[value = next]').removeClass('disabled');
                $(ev.target).parent().parent().children('li[value = pre]').removeClass('disabled');
            }
        }
    }
    
//  展示哪些页码 要用一个实际的分页找规律
    function pageShow(element){
        if(Number(pagenumber) >= 1 && Number(pagenumber) <= parseInt(.5 * Number(paginationmax))){
            element.parent().children('li').each(function(index,item){
                if(Number($(item).attr('value')) >= 1 + Number(paginationmax) && Number($(item).attr('value')) <= Number(totalnumber)){
                    $(item).css('display','none')
                }else{
                    $(item).css('display','inline-block')
                }
            });
        }else if(Number(pagenumber) > parseInt(.5 * Number(paginationmax)) && Number(pagenumber) <= Number(totalnumber) - parseInt(.5 * Number(paginationmax))){
            element.parent().children('li').each(function(index,item){
                if((Number($(item).attr('value')) >= 1 && Number($(item).attr('value')) <= Number(pagenumber) - parseInt(.5 * Number(paginationmax))) || (Number($(item).attr('value')) > Number(pagenumber) + parseInt(.5 * Number(paginationmax)) && Number($(item).attr('value')) <= Number(totalnumber))){
                    $(item).css('display','none')
                }else{
                    $(item).css('display','inline-block')
                }
            });
        }else if(Number(pagenumber) > Number(totalnumber) - parseInt(.5 * Number(paginationmax))){
            element.parent().children('li').each(function(index,item){
                if(Number($(item).attr('value')) >= 1 && Number($(item).attr('value')) <= Number(totalnumber) - Number(paginationmax)){
                    $(item).css('display','none')
                }else{
                    $(item).css('display','inline-block')
                }
            });
        }
    }
    
//  页面切换时候的处理函数。比如发ajax根据不同页码获取不同数据展示数据等，用户自行配置。
    function processData(){
        console.log('当前页码',pagenumber);
//        用户在这里写页码切换时候的逻辑
    }
</script>

</html>