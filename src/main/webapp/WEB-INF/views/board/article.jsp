<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="detail-wraper">
    <div class="container">
        <input type="hidden" class="boardId" value="${board.id}">
        <div class="header__line">
            <h1>홈 > 제품</h1>
            <c:choose>
                <c:when test="${principal.username eq board.writer}">
                    <div class="dropdown">
                        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                            <img src="/images/setting.png"></button>
                        <div class="dropdown-menu">
                            <a id="update-Post" class="dropdown-item" href="#">수정</a>
                            <a id="delete-Post" class="dropdown-item" href="#">삭제</a>
                        </div>
                    </div>
                </c:when>
            </c:choose>
            <hr/>
        </div>


        <div class="product_info">
            <div class="product">
                <div class="img-container">
                    <img id="imgBox" src="/upload/${board.files[0].tempName}">
                </div>

                <div class="product-small-img">
                    <c:forEach var="files" items="${board.files}">
                        <img class="grid-box" id="img-${files.fileid}" src="/upload/${files.tempName}">
                    </c:forEach>
                </div>
            </div>

            <div class="container">
                <div class="content_info">
                    <div class="item__title">${board.title}</div>
                    <div class="item_price_container">
                        <span class="item__price">${board.price}</span>
                        <span class="k__money">원</span>
                    </div>

                    <hr/>

                    <ul>
                        <li>
                            <div class="product__status">
                                <div class="index-info">상품상태
                                </div>
                                <div class="product__info__content">새상품</div>
                            </div>
                        </li>
                        <li>

                            <div class="product__status">
                                <div class="index-info">교환여부
                                </div>
                                <div class="product__info__content">새상품</div>
                            </div>
                        </li>
                        <li>

                            <div class="product__status">
                                <div class="index-info">판매자
                                </div>
                                <div class="product__info__content">
                                    <div class="item__writer">${board.writer}</div>
                                </div>
                            </div>
                        </li>
                        <li>

                            <div class="product__status">
                                <div class="index-info">거래지역
                                </div>
                                <div class="product__info__content">${board.location}</div>
                            </div>
                        </li>
                    </ul>

                    <hr/>
                    <ul>
                        <li>
                            <div class="product__descryption">
                                <div class="index-info">상품설명
                                </div>
                                <div class="product__descryption__content">${board.descryption}</div>
                            </div>
                        </li>
                    </ul>

                    <hr/>

                    <div class="product__info__count">
                        <span class="view__count">
                            <img src="/images/boardDetail/view.png">
                            <div class="count__number">32</div>
                        </span>
                        <span class="view__like">
                            <img src="/images/boardDetail/like.png">
                            <div class="count__number">3</div>
                        </span>
                        <span class="view__time">
                            <img src="/images/boardDetail/time.png">
                            <div class="count__number">${createTime}</div>
                        </span>
                    </div>

                    <div class="btn__container">
                        <div class="like__product_btn">
                            <button class="btn btn-secondary"><img src="/images/boardDetail/exchanges.png">장바구니 담기</button>
                        </div>
                        <div class="exchange__product_btn">
                            <button class="btn btn-danger"><img src="/images/boardDetail/exchange.png">교환하기</button>
                        </div>
                        <div class="chat__product_btn">
                            <button class="btn btn-primary"><img src="/images/boardDetail/chat.png">문의하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="new__reg__product">
            <div class="new__product__category">최근 등록물품</div>
                <c:forEach var="new_board" items="${topBoards.content}" varStatus="i">
                    <div class="new__product__box">
                        <a href="/board/article/${new_board.id}">
                        <div class="new__box">
                            <div class="new__product__img">
                                <img src="/upload/${new_board.files[0].tempName}">
                            </div>
                            <div class="new__product__title">${new_board.title}</div>
                        </div>
                        </a>
                    </div>
                </c:forEach>
        </div>

    </div>

<script>

$('.chat__product_btn').on("click", function(){

        var user = $('.user__name').text();
        var target = $('.item__writer').text();

        $.ajax({
            url: '/api/create/room',
            type: 'POST',
            data: 'user=' + user + '&target=' + target ,
            contentType: 'application/x-www-form-urlencoded',
            success: function(response){
                console.log(response);
                if(response.result.includes('exist') || response.result.includes('success')){
                    popupWindow(target, response.roomId);
                }
            }
        })

})

    function popupWindow(target, roomId, w = 360, h = 700) {
        var y = (screen.width - w) - 2500;
        var x = (screen.height - h) / 2; 
        var targetWin = window.open('/api/chat/target=' + target +'/room=' + roomId, '문의하기', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + w + ', height=' + h + ', top=' + x + ', left=' + y);
    }

</script>

</div></div><%@ include file="../common/footer.jsp" %>
