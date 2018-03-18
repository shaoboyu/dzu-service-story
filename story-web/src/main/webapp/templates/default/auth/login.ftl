<#include "/default/utils/ui.ftl"/>
<@layout>

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">请登录</h3>
            </div>
            <div class="panel-body">
                <div id="message"><#include "/default/inc/action_message.ftl"/></div>
                <form method="POST" action="login" accept-charset="UTF-8">
                    <div class="form-group ">
                        <label class="control-label" for="username">账号</label>
                        <input class="form-control" name="username" type="text" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="password">密码</label>
                        <input class="form-control" name="password" type="password" required>
                    </div>
                    <div class="form-group ">
                        <label>
                            <input type="checkbox" name="rememberMe" value="1"> 记住登录？
                        </label>
                    </div>
                    <button type="submit" class="btn btn-success btn-block btn-sm">
                        登录 Use it
                    </button>

                    <hr>
                </form>
            </div>
        </div>
    </div>
</div>

</@layout>

