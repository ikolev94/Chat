<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Create an account</title>

<script src="jquery-2.1.0.min.js"></script>
<link href="chat.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<div class="container">
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<form id="logoutForm" method="POST" action="${contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

			<h2>
				Welcome ${pageContext.request.userPrincipal.name} | <a
					onclick="document.forms['logoutForm'].submit()">Logout</a>
			</h2>

		</c:if>
	</div>
	<div>
		<div id="userList"></div>
	</div>
	<script type="text/javascript">
                var stompClient = null;
                var socket = null;
                var whoami = null;

                function connect() {
                    socket = new SockJS('/chat');
                    stompClient = Stomp.over(socket);
                    stompClient.connect('', '', function(frame) {
                        whoami = frame.headers['user-name'];
                        console.log('Connected: ' + frame);
                        stompClient.subscribe('/user/queue/messages', function(message) {
                            showMessage(JSON.parse(message.body));
                        });
                        stompClient.subscribe('/topic/active', function(activeMembers) {
                            showActive(activeMembers);
                        });
                    });
                }

                function showActive(activeMembers) {
                    renderActive(activeMembers.body);
                    stompClient.send('/app/activeUsers', {}, '');
                }

                function renderActive(activeMembers) {
                    var previouslySelected = $('.user-selected').text();
                    var usersWithPendingMessages = new Object();
                    $.each($('.pending-messages'), function(index, value) {
                        usersWithPendingMessages[value.id.substring(5)] = true; // strip the user-
                    });
                    var members = $.parseJSON(activeMembers);
                    var userDiv = $('<div>', {
                        id: 'users'
                    });
                    $.each(members, function(index, value) {
                        if (value === whoami) {
                            return true;
                        }
                        var userLine = $('<div>', {
                            id: 'user-' + value
                        });
                        userLine.addClass('user-entry');
                        if (previouslySelected === value) {
                            userLine.addClass('user-selected');
                        } else {
                            userLine.addClass('user-unselected');
                        }
                        var userNameDisplay = $('<span>');
                        userNameDisplay.html(value);
                        userLine.append(userNameDisplay);
                        userLine.click(function() {
                            var foo = this;
                            $('.chat-container').hide();
                            $('.user-entry').removeClass('user-selected');
                            $('.user-entry').addClass('user-unselected');
                            userLine.removeClass('user-unselected');
                            userLine.removeClass('pending-messages');
                            userLine.addClass('user-selected');
                            userLine.children('.newmessage').remove();
                            var chatWindow = getChatWindow(value);
                            chatWindow.show();
                        });
                        if (value in usersWithPendingMessages) {
                            userLine.append(newMessageIcon());
                            userLine.addClass('pending-messages');
                        }
                        userDiv.append(userLine);
                    });
                    $('#userList').html(userDiv);
                }

                function disconnect() {
                    stompClient.disconnect();
                    console.log("Disconnected");
                }

                function sendMessageTo(user) {
                    var chatInput = '#input-chat-' + user;
                    var message = $(chatInput).val();
                    if (!message.length) {
                        return;
                    }
                    stompClient.send("/app/chat", {}, JSON.stringify({
                        'recipient': user,
                        'message': message
                    }));
                    $(chatInput).val('');
                    $(chatInput).focus();
                }

                function getChatWindow(userName) {
                    var existingChats = $('.chat-container');
                    var elementId = 'chat-' + userName;
                    var containerId = elementId + '-container';
                    var selector = '#' + containerId;
                    var inputId = 'input-' + elementId;
                    if (!$(selector).length) {
                        var chatContainer = $('<div>', {
                            id: containerId,
                            class: 'chat-container'
                        });
                        var chatWindow = $('<div>', {
                            id: elementId,
                            class: 'chat'
                        });
                        var chatInput = $('<textarea>', {
                            id: inputId,
                            type: 'text',
                            class: 'chat-input',
                            rows: '2',
                            cols: '75',
                            placeholder: 'Enter a message.  Something deep and meaningful.  Something you can be proud of.'
                        });
                        var chatSubmit = $('<button>', {
                            id: 'submit-' + elementId,
                            type: 'submit',
                            class: 'chat-submit'
                        })
                        chatSubmit.html('Send');

                        $.get("messagesBetween/" + whoami + "/" + userName, function(data) {
                            data.forEach(function(msg) {
                                showMessage(msg)
                            })
                        })

                        chatInput.keypress(function(event) {
                            if (event.which == 13) {
                                var user = event.currentTarget.id.substring(11); // gets rid of 'input-chat-'
                                event.preventDefault();
                                sendMessageTo(user);
                            }
                        });

                        chatSubmit.click(function(event) {
                            var user = event.currentTarget.id.substring(12); // gets rid of 'submit-chat-'
                            sendMessageTo(user);
                        });

                        chatContainer.append(chatWindow);
                        chatContainer.append(chatInput);
                        chatContainer.append(chatSubmit);

                        if (existingChats.length) {
                            chatContainer.hide();
                        }

                        $('body').append(chatContainer);
                    }
                    return $(selector);
                }

                function showMessage(message) {
                    var chatWindowTarget = (message.recipient === whoami) ? message.sender : message.recipient;
                    var chatContainer = getChatWindow(chatWindowTarget);
                    var chatWindow = chatContainer.children('.chat');
                    var userDisplay = $('<span>', {
                        class: (message.sender === whoami ? 'chat-sender' : 'chat-recipient')
                    });
                    userDisplay.html(message.sender + ' says: ');
                    var messageDisplay = $('<span>');
                    messageDisplay.html(message.message);
                    chatWindow.append(userDisplay).append(messageDisplay).append('<br/>');
                    chatWindow.animate({
                        scrollTop: chatWindow[0].scrollHeight
                    }, 1);
                    if (message.sender !== whoami) {
                        var sendingUser = $('#user-' + message.sender);
                        if (!sendingUser.hasClass('user-selected') && !sendingUser.hasClass('pending-messages')) {
                            sendingUser.append(newMessageIcon());
                            sendingUser.addClass('pending-messages');
                        }
                    }
                }

                function newMessageIcon() {
                    var newMessage = $('<span>', {
                        class: 'newmessage'
                    });
                    newMessage.html('&#x2709;');
                    return newMessage;
                }

                $(document).ready(function() {
                    connect();
                });
            </script>
	<!-- /container -->
	<script src="sockjs-0.3.4.js"></script>
	<script src="stomp.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>

</html>