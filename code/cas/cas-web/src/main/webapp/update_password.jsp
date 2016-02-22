<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<!DOCTYPE>
<html lang="zh_CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<title>修改密码</title>
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
		<% 
	    request.setCharacterEncoding("UTF-8");
	    AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
	    String userName = principal.getName();
	    %>	
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
								<h1>修改密码</h1>
							</div>
							<div class="msg-wrap">
								<div class="msg-warn hide"><b></b></div>
								<div class="msg-error hide"><b></b></div>
							</div>
							<div class="mc">
								<div class="form">
									<form id="fm1" name="fm1">
										<div class="item item-fore1">											
											<label for="username" class="login-label name-label"></label>
											<input id="username" type="text" class="itxt" name="username" tabindex="1" 
												autocomplete="off" placeholder="用户名" value="<%=userName%>" readonly="readonly"/>
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore1">										
											<label for="password" class="login-label pwd-label"></label>
											<input id="password" type="password" class="itxt" name="password" tabindex="2" 
												autocomplete="off" placeholder="原密码" />
											<span class="clear-btn"></span>
										</div>
										<div id="entry" class="item item-fore1">
											<label class="login-label pwd-label" for="newpassword"></label>
											<input type="password" id="newpassword" name="newpassword" class="itxt itxt-error" 
												tabindex="3" autocomplete="off" placeholder="新密码" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore2">
											<label class="login-label pwd-label" for="repassword"></label>											
											<input type="password" id="repassword" name="repassword" class="itxt itxt-error" 
												tabindex="4" autocomplete="off" placeholder="确认密码" />
											<span class="clear-btn"></span>
										</div>
										<div class="item item-fore5">
											<div class="login-btn">
												<input type="button" class="btn-img btn-entry" id="loginsubmit" tabindex="5" 
													value="修&nbsp;&nbsp;&nbsp;&nbsp;改" />
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

		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="js/localization/messages_zh.min.js"></script>
		<script>
			$(document).ready(function() {
				$("div.item-fore1,div.item-fore2", "#fm1").hover(function(){
					$(this).addClass("item-focus");
				},function(){
					$(this).removeClass("item-focus");
				});
				
				$("#fm1").validate({
					rules: {
						username: {
							required: true,
							minlength: 3
						},
						password: {
							required: true,
							minlength: 3
						},
						newpassword: {
							required: true,
							minlength: 3
						},
						repassword: {
							required: true,
							minlength: 3,
							equalTo: "#newpassword"
						}
					},
					messages: {
						username: {
							required: "请输入用户名",
							minlength: "用户名至少3位"
						},
						password: {
							required: "请输入原密码",
							minlength: "原密码至少3位"
						},
						password: {
							required: "请输入新密码",
							minlength: "新密码至少3位"
						},
						repassword: {
							required: "请输入密码",
							minlength: "您的密码至少3位",
							equalTo: "密码输入不一致"
						}
					},
					validClass: "success",
					errorClass: "invalid"
				});
				
				$("#loginsubmit").click(function() {
					var password = $("#password").val();
					var newpassword = $("#newpassword").val();
					var repassword = $("#repassword").val();
					if(password == null  || password.length < 3){
						alert("原密码格式有误");
						$("#password").focus();
						return;
					}
					if(newpassword == null  || newpassword.length < 3){
						alert("新密码格式有误");
						$("#newpassword").focus();
						return;
					}
					if(repassword != newpassword){
						alert("密码输入不一致");
						$("#repassword").focus();
						return;
					}
					
					$.ajax({
						url: "/cas-web/update_password.do",
						type: "POST",
						data: $("#fm1").serialize(), 
						success: function(data){
							if(data.success == true){
					        	alert("密码修改成功");
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