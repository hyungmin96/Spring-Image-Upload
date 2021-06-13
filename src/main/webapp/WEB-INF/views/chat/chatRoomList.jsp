<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>



<link rel="stylesheet" type="text/css" href="/css/chatRoomList.css">

<div class="container" style="margin-top: 185px;">

    <div class="user__chat" style="display: inline-flex;">
        <h2><span class="user__name__info">${principal.username}</span></h2>
        <span style="margin-left: 5px; margin-top: 15px; color: rgb(160, 160, 160);">님의 채팅내역 입니다.</span>
    </div>

    <hr style="margin-top: 10px; margin-bottom: 10px;"/>
        <div class="roomsContainer">
            
        </div>

</div>

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="/js/notificationStomp.js"></script>
<script>

    // 채팅방 목록
    $(document).ready(function(){

        $.ajax({

            url: '/api/chat/rooms',
            type: 'GET',
            dataType: 'json',
            success: function(response){
                
                $.each(response, function(key, value){

                    var currentChatMessage = value.roomEntity.chats;
                    var content;

                    if(currentChatMessage.length > 0)
                        content = currentChatMessage[currentChatMessage.length-1].message;
                    else
                        content = '채팅방이 개설되었습니다.'

                    var currentChatMessageTime = new Date(currentChatMessage[currentChatMessage.length - 1].createTime).toLocaleTimeString([], {'month': '2-digit', 'day': '2-digit', 'hour': '2-digit', 'minute' : '2-digit'});

                        $('.roomsContainer').append(
                            "<div class='room__box'" + 
                            "onclick='popupWindow(" + value.target.username + "," + value.roomEntity.id + ")'>" +
                            "<div class='room__title'>" + 
                            value.target.username + "님과의 채팅" + 
                            "</div>" +
                            "<hr style='margin: 10px;'/>" + 
                            "<div class='room_chatting'>" + 
                            currentChatMessageTime + ' : ' + content + 
                            "</div>" + 
                            "</div>"
                        );

                })

            }

        })

    })


    // 채팅방 개설
    $('.createBtn').on("click", function(){
        var user = $('.user__name__info').text();
        var target = $('#target__id').val();

        $.ajax({
            url: '/api/create/room',
            type: 'POST',
            data: 'user=' + user + '&target=' + target ,
            contentType: 'application/x-www-form-urlencoded',
            success: function(response){
                
            }
        })
    })

    function popupWindow(target, roomId, w = 360, h = 700) {
        var y = (screen.width - w) - 2500;
        var x = (screen.height - h) / 2; 
        var targetWin = window.open('/api/chat/target=' + target +'/room=' + roomId, '문의하기', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + w + ', height=' + h + ', top=' + x + ', left=' + y);
    }

</script>
<link rel="stylesheet" href="/css/chatCreateList.css">