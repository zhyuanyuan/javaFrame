<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE>
<html lang="zh_CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<title>添加用户</title>
		<link rel="stylesheet" type="text/css" href="css/casbase.css" media="all" />
		<link type="text/css" rel="stylesheet" href="css/changepassword.css" />
		<style>
			.form .itxt {
				height:38px;
			}
			.login-form .login-box .login-btn .btn-img{
				width:304px;
				height:33px;
				line-height: 33px;
			}
		</style>
	</head>
	<body>		
		<div class="w">
			<div id="logo">
				<a href="#">
					<img src="http://www.credithc.com/Image/l_home_logo.jpg" width="170" height="60">
				</a>
				<b></b>
			</div>
		</div>
		<div id="content">
			<div class="login-wrap">
				<div class="w">
					<div class="login-form">
						<div class="login-box">
							<div class="mt">
								<h1>添加用户</h1>
							</div>
							<div class="msg-wrap">
								<div class="msg-warn hide"><b></b></div>
								<div class="msg-error hide"><b></b></div>
							</div>
							<div class="mc">
								<div class="form">
									<form id="addUserForm" name="addUserForm">
										<div class="item item-fore1">											
											<label for="username" class="login-label name-label"></label>
											<input id="username" type="text" class="itxt" name="userName" tabindex="1" 
												autocomplete="off" placeholder="用户名" />
											<span class="clear-btn"></span>
										</div>
										<div id="entry" class="item item-fore1">
											<label for="password" class="login-label pwd-label"></label>
											<input type="password" id="password" name="password" class="itxt itxt-error" 
												tabindex="2" autocomplete="off" placeholder="密码" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore2">
											<label for="repassword" class="login-label pwd-label"></label>											
											<input type="password" id="repassword" name="repassword" class="itxt itxt-error" 
												tabindex="3" autocomplete="off" placeholder="确认密码" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore1">											
											<label for="mobile" class="login-label name-label"></label>
											<input id="mobile" type="text" class="itxt" name="mobile" tabindex="4" maxlength="11"
												autocomplete="off" placeholder="手机号" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore2">
											<label class="login-label pwd-label" for="email"></label>											
											<input type="text" id="email" name="email" class="itxt itxt-error" 
												tabindex="5" autocomplete="off" placeholder="邮箱" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore2">
											<label class="" for="isAdmin">角色：</label>
											<select id="isAdmin" name="isAdmin">
												<option value="0">非管理员</option>
												<option value="1">管理员</option>
											</select>										
										</div>
										<div class="item item-fore2">
											<label class="" for="male">性别：</label>	
											<select id="male" name="male">
												<option value="0">男</option>
												<option value="1">女</option>
											</select>										
										</div>
										<div class="item item-fore2">
											<label class="" for="enabled">账户状态：</label>	
											<select id="enabled" name="enabled">
												<option value="1">启用</option>
												<option value="0">禁用</option>
											</select>										
										</div>
										<div class="item item-fore2">
											<label class="login-label pwd-label" for="address"></label>											
											<input type="text" id="address" name="address" class="itxt itxt-error" 
												tabindex="5" autocomplete="off" placeholder="地址" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore2">
											<label class="login-label pwd-label" for="cname"></label>											
											<input type="text" id="cname" name="cname" class="itxt itxt-error" 
												tabindex="5" autocomplete="off" placeholder="中文名字" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore2">
											<label class="login-label pwd-label" for="ename"></label>											
											<input type="text" id="ename" name="ename" class="itxt itxt-error" 
												tabindex="5" autocomplete="off" placeholder="英文名字" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore5">
											<div class="login-btn">
												<input type="button" class="btn-img btn-entry" id="addSubmit" tabindex="6" 
													value="添&nbsp;&nbsp;&nbsp;&nbsp;加" />
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="w">
			<div id="footer-hc">
				<div class="links"></div>
				<div class="copyright">
				</div>
			</div>
		</div>

		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="js/localization/messages_zh.min.js"></script>
		<script>
			$(document).ready(function() {
				$("div.item-fore1,div.item-fore2", "#addUserForm").hover(function(){
					$(this).addClass("item-focus");
				},function(){
					$(this).removeClass("item-focus");
				});
				
				$("#addUserForm").validate({
					rules: {
						username: {
							required: true,
							minlength: 3
						},
						password: {
							required: true,
							minlength: 6
						},
						repassword: {
							required: true,
							minlength: 6,
							equalTo: "#password"
						}
					},
					messages: {
						username: {
							required: "请输入用户名",
							minlength: "您的用户名至少3位"
						},
						password: {
							required: "请输入密码",
							minlength: "您的密码至少6位"
						},
						repassword: {
							required: "请输入密码",
							minlength: "您的密码至少6位",
							equalTo: "密码输入不一致"
						}
					},
					validClass: "success",
					errorClass: "invalid"
				});
				
				$("#addSubmit").click(function(){
					var username = $("#username").val();
					var password = $("#password").val();
					var repassword = $("#repassword").val();
					
					if(username == null  || username.length < 3){
						alert("登录名称格式有误");
						$("#newpassword").focus();
						return;
					}
					if(password == null  || password.length < 3){
						alert("密码格式有误");
						$("#password").focus();
						return;
					}
					if(repassword != password){
						alert("密码输入不一致");
						$("#repassword").focus();
						return;
					}
					
					$.ajax({
						url: "/cas-web/add_user.do",
						type: "POST",
						data: $("#addUserForm").serialize(), 
						dataType: "json",
						success: function(data){
					        if(data.success == true){
					        	alert("用户添加成功");
					        } else {
					        	alert(data.msg);
					        }
						}
					});
				});
			});
		</script>
	</body>

</html>