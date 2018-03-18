<#include "/default/utils/ui.ftl"/>

<@layout "我的喜欢">
<div class="row users-show">
    <div class="col-xs-12 col-md-3 side-left">
		<#include "/default/user/left.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">
                我的喜欢
            </div>

            <div class="panel-body">
                <ul class="list-group">
					<#list page.content as row>
                        <li class="list-group-item" id="loop-${target.id}">
							<#if row.post??>
                                <a href="${base}/view/${row.post.id}" class="remove-padding-left">${row.post.title}</a>
							<#else>
                                <a href="javascript:;" class="remove-padding-left">文章已删除</a>
							</#if>
                            <span class="meta">
								<span class="timeago">${timeAgo(row.created)}</span>
      						</span>

                            <div class="pull-right hidden-xs">
                                <a class="act" href="javascript:void(0);" data-evt="unfavor" data-id="${target.id}">
                                    <i class="icon icon-close"></i>
								</a>
                            </div>

                            <div class="reply-body markdown-reply content-body">
                                <p>${row.content}</p>
                            </div>
                        </li>
					</#list>

					<#if page.content?size == 0>
                        <li class="list-group-item ">
                            <div class="infos">
                                <div class="media-heading">该目录下还没有内容!</div>
                            </div>
                        </li>
					</#if>
                </ul>
            </div>
            <div class="panel-footer">
				<@pager request.requestURI!"", page, 5/>
            </div>
        </div>
    </div>
</div>
<!-- /end -->

<script type="text/javascript">
$(function() {
	$('a[data-evt=unfavor]').click(function () {
		var id = $(this).attr('data-id');

		layer.confirm('确定删除此项吗?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
			jQuery.getJSON('${base}/user/unfavor', {'id': id}, function (ret) {
				layer.msg(ret.message, {icon: 1});
				if (ret.code >=0) {
					$('#loop-' + id).fadeOut();
					$('#loop-' + id).remove();
				}
			});

        }, function(){

        });
	});
})
</script>
</@layout>