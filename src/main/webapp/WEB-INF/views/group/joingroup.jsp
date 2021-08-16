<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>

<div id="Group__wrapper" class="container" style="margin-top: 155px; min-height: 800px;">

        <div class="fast__sale_products">
            <span class="fast__category">그룹 목록</span>
            <span style="float: right;">
            <span>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">그룹 등록</button>
            </span>
            </span>

            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                    <div class="modal-content" style="height: 100%;">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">물품 공동구매 채팅방 생성</h5> <button type="button" class="close" data-dismiss="modal" aria-label="Close"> <span aria-hidden="true"></span> </button>
                        </div>

                        <div class="modal-body">
                            <div style="padding: 10px; margin-top: 10px;">
                                <div id="step-1">
                                    <div style="display: flex; flex-direction: row;">
                                        <img id="img__upload" src="/images/upload.png">
                                        <input id="input__Group__img" type="file" accept="image/jpg, image/jpeg, image/png" class="custom-file-input" name="upload_file" style="display: none">
                                        <div id="preview__img__container"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="article__item__box">
                                <label class="item__value">그룹 이름</label>
                                <input type="text" name="title" id="Group__title" class="inputbox" placeholder="그룹 이름을 설정해주세요."/>
                            </div>

                            <div class="article__item__box">
                                <label class="item__value">그룹 설명</label>
                                <textarea class="inputbox" id="Group__desciption" rows="3"></textarea>
                            </div>

                            <div class="article__item__box">
                                <label class="item__value">그룹 태그</label>
                                <input type="text" name="tag" id="tags" class="inputbox" placeholder="그룹과 관련된 물품들을 입력해주세요"/>
                            </div>

                            <div class="article__item__box">
                                <label class="item__value">그룹 설정</label>
                                <div class="chk _group" style="display: inline-flex; width: 100%">
                                    <div class="checkbox _openChk" onclick="checkboxOnLoad(this);"><input
                                            type="checkbox" id="chk_1" checked="checked" value="1"/>전체 공개</div>
                                    <div class="checkbox _partChk" onclick="checkboxOnLoad(this);"><input
                                            type="checkbox" id="chk_2" value="0"/>일부 공개</div>
                                    <div class="checkbox _secretChk" onclick="checkboxOnLoad(this);"><input
                                            type="checkbox" id="chk_3" value="0"/>비공개</div>
                                </div>
                            </div>

                            <div>
                                <div style="display: flex; justify-content: flex-end; margin-right: 10px;">
                                    <button id="room__create" class="btn btn-primary">생성</button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

    <hr style="border: none; height: 1px; background-color: #ccc;" />
        <div class="Group__room__list">

        </div>
        <div class="page__container">
            <div class="page__box" style="display: inline-flex; max-width: 395px;">
                <div class="previous"><img src="/images/back.png"></div>
                    <input type="hidden" class="curpage" data-value="1">
                    <input type="hidden" class="lastpage" data-value="${endPages}">
                    <div class="page__number__box">

                    </div>
                <div class="next"><img src="/images/next.png"></div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

    <div id="my_modal" style="overflow:auto; margin-top: 25px; width: 850px;">

        <input type="hidden" class="groupId" value="0"/>
        <input type="hidden" class="roomTitle" value=""/>

        <div class="enter__title">그룹 입장</div>
        <hr />

        <div class="enter__img">그룹 이미지</div>
        <div class="product__img__container" style="min-height: 170px;">

        </div>
        <hr />

        <div class="enter__desc">그룹 설명</div>
        <div class="product__img__desc" style="margin: 5px 5px; min-height: 100px;">

        </div>
        <hr />

        <div class="modal_close_btn"><button id="close__btn" type="button" class="float-right btn btn-danger">닫기</button></div>
        <div class="delete_btn"><button id="deletebtn" type="button" class="float-right btn btn-secondary">삭제</button></div>
        <button id="enter__btn" type="button" class="float-right btn btn-primary">입장</button>
        
    </div>
<!-- modal -->

<script src="/js/pagination.js"></script>
<script src="/js/modal.js"></script>
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script> <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<link href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/smart_wizard.min.css" rel="stylesheet" type="text/css" />
<link href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/smart_wizard_theme_dots.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/jquery.smartWizard.min.js"></script>
<link rel="stylesheet" href="/css/joinbuying.css">
<script type="text/javascript" src="/js/joinbuying.js"></script>
<%@ include file="../common/footer.jsp"%>