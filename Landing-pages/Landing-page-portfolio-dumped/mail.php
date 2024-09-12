<?php
  //Если форма отправлена
  if(isset($_POST['submit'])) {
	 //Проверка Поля ИМЯ
	  if(trim($_POST['name']) == '') {
	  	$hasError = true;
	  } else {
	  	$name = trim($_POST['name']);
	  }
	 //Проверка правильности ввода EMAIL
	  if(trim($_POST['email']) == '')  {
	  	$hasError = true;
	  } else {
	  	$email = trim($_POST['email']);
	  }
	 //Проверка наличия ТЕКСТА сообщения
	  if(trim($_POST['message']) == '') {
	  	$hasError = true;
	  } else {
		  	$comments = trim($_POST['message']);
		}
	 //Если ошибок нет, отправить email
	  if(!isset($hasError)) {
		  $emailTo = 'm.akeb805@gmail.com'; //Сюда введите Ваш email
		  $body = "Name: $name \n\nEmail: $email \n\nComments:\n $comments";
		  $headers = 'From: My Site <'.'animefangroup'.'>' . "\r\n" . 'Reply-To: ' . $email;
		 mail($emailTo, $body, $headers);
		  $emailSent = true;
	  }
  }

  header("Refresh:1; url=https://animefangroup.000webhostapp.com");
?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Accessed</title>
	<style>
		.block,.parent{position:absolute;top:0}.parent{overflow:auto;width:100%;height:100%;left:0}.block{width:250px;height:250px;left:50%;margin:0 0 0 -125px}p{color:#fff;font-family:Arial;font-weight:800;font-size:35px;margin-top:110px;text-align:center}
	</style>

</head>
<body style="background-image: url(img/header3_720-min.jpg); background-size: cover;">
	<div class="parent">
    <div class="block">
        <p>Message was sent!</p>
    </div>
</div>
</body>
</html>