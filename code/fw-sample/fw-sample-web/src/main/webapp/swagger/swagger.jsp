<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>恒昌财富</title>
  <base href="<%=basePath%>">
  <link href='swagger/css/font.css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'/>
  <link href='swagger/css/highlight.default.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='swagger/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <script type="text/javascript" src="lib/shred.bundle.js"></script>
  <script src='swagger/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='swagger/lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='swagger/lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='swagger/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='swagger/lib/handlebars-1.0.0.js' type='text/javascript'></script>
  <script src='swagger/lib/underscore-min.js' type='text/javascript'></script>
  <script src='swagger/lib/backbone-min.js' type='text/javascript'></script>
  <script src='swagger/lib/swagger.js' type='text/javascript'></script>
  <script src='swagger/lib/swagger-client.js' type='text/javascript'></script>
  <script src='swagger/swagger-ui.js' type='text/javascript'></script>
  <script src='swagger/lib/highlight.7.3.pack.js' type='text/javascript'></script>
  <!-- enabling this will enable oauth2 implicit scope support -->
  <script src='swagger/lib/swagger-oauth.js' type='text/javascript'></script>
  <script type="text/javascript">
    $(function () {
      // modify by xiyt
      // var url = window.location.search.match(/url=([^&]+)/);
      var href = window.location.href.split("/");
      var url = "<%=basePath%>doc/api-docs";//window.location.protocol + "//" + window.location.host + "/" + href[3] + "/backend/api-docs.json";
      window.swaggerUi = new SwaggerUi({
        url: url,
        dom_id: "swagger-ui-container",
        supportedSubmitMethods: ['get', 'post', 'put', 'delete'],
        onComplete: function(swaggerApi, swaggerUi){
        log("Loaded SwaggerUI");
         if(typeof initOAuth == "function") {
            
          }
          $('pre code').each(function(i, e) {
            hljs.highlightBlock(e)
          });
        },
        onFailure: function(data) {
          log("Unable to Load SwaggerUI");
        },
        docExpansion: "none",
        sorter : "alpha"
      });

      window.swaggerUi.load();
  });
  </script>
</head>

<body>
<<body class="swagger-section">
<div id='header'>
  <div class="swagger-ui-wrap">
     <a id="logo" href="#"></a>
    <form id='api_selector'>
      <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
      <div class='input'><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/></div>
      <div class='input'><a id="explore" href="#" data-sw-translate>Explore</a></div>
    </form>
  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div>
<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</body>
</body>
</html> 