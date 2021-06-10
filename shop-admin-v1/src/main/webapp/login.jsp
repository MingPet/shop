<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>登录!</h2>
<a href='brand/toAdd.jhtml'>添加页面</a>
<a href='brand/toList.jhtml'>查询页面</a>
<a href='log/index.jhtml'>log查询页面</a>
<br/><br/><br/><br/>


用户名:<input type="text" id="userName"/>
密码:<input type="password" id="password"/>
<input type="button" value="登录" onclick="login()"/>
<script src="/js/jquery.min.js"></script>
<script>
    function login() {
        var v_userName = $("#userName").val();
        var v_password = $("#password").val();
        // 前台，js验证

        $.ajax({
            type:"post",
            url:"/user/login.jhtml",
            data:{"userName":v_userName,"password":v_password},
            success:function (result) {
                if (result.code == 200) {

                    window.location.href="/brand/toList.jhtml";



                } else {
                    alert(result.message);
                }
            }
        })
    }
</script>

</body>
</html>
