<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome Main</title>
<link rel="stylesheet" href="resources/css/main.css">
</head>
<body>

<div class="wrapper">
	<div class="wrap">
		<div class="top_gnb_area">
			<ul class= "list">
			<c:if test = "${member == null }">
			<li>
			<a href="/member/login">로그인</a>
			</li>
			<li>
			<a href="/member/join">회원가입</a>
			</li>
			</c:if>
			<c:if test="${member.adminCk == 1 }">
                        <li><a href="/admin/main">관리자 페이지</a></li>
                    </c:if>
			 <c:if test="${member != null }">                    
                    <li>
                        로그아웃
                    </li>
                    <li>
                        마이룸
                    </li>
                    <li>
                        <a href="/cart/${member.memberId}">장바구니</a>
                    </li>
                </c:if>    
			<li>
			  고객센터
			</li>
			</ul>
		</div>
		<div class="top_area">
			<div class="logo_area">
				<a href="/main"><img src="resources/img/logo.png"></a>
			</div>
			<div class="search_area">
				                	<div class="search_wrap">
                		<form id="searchForm" action="/search" method="get">
                			<div class="search_input">
                				<select name="type">
                					<option value="T">책 제목</option>
                					<option value="A">작가</option>
                				</select>
                				<input type="text" name="keyword">
                    			<button class='btn search_btn'>검 색</button>                				
                			</div>
                		</form>
                	</div>

			</div>
			<div class="login_area">
				    <!-- 로그인 하지 않은 상태 -->
                <c:if test = "${member == null }">
                    <div class="login_button"><a href="/member/login">로그인</a></div>
                    <span><a href="/member/join">회원가입</a></span>                
                </c:if>    
                
                   <!-- 로그인한 상태 -->
          
                <c:if test="${ member != null }">
                    <div class="login_success_area">
                        <span>회원 : ${member.memberName}</span>
                        <span>충전금액 : <fmt:formatNumber value="${member.money }" pattern="\#,###.##"/></span>
                        <span>포인트 :  <fmt:formatNumber value="${member.point }" pattern="#,###" /></span>
                    	<a href="/member/logout.do">로그아웃</a>
                    </div>
                </c:if>
 			</div>
			<div class="clearfix"></div>			
		</div>
		<div class="navi_bar_area">
		<div class="dropdown">
			    <button class="dropbtn">국내 
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			        <c:forEach items="${cate1}" var="cate"> 
		    		<a href="search?type=C&cateCode=${cate.cateCode}">${cate.cateName}</a>
		    	</c:forEach>		      		      
			    </div>			
			</div>
			<div class="dropdown">
			    <button class="dropbtn">국외 
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			         <c:forEach items="${cate2}" var="cate"> 
		    		<a href="search?type=C&cateCode=${cate.cateCode}">${cate.cateName}</a>
		    	</c:forEach>      		      
			    </div>			
			</div>
			</div>
		</div>
		<div class="content_area">
		</div>
		
        <!-- Footer 영역 -->
        <div class="footer_nav">
            <div class="footer_nav_container">
                <ul>
                    <li>회사소개</li>
                    <span class="line"></span>
                    <li>이용약관</li>
                    <span class="line"></span>
                    <li>고객센터</li>
                    <span class="line"></span>
                    <li>광고문의</li>
                    <span class="line"></span>
                    <li>채용정보</li>
                    <span class="line"></span>
                </ul>
            </div>
        </div> <!-- class="footer_nav" -->
        
        <div class="footer">
            <div class="footer_container">
                
                <div class="footer_left">
                    <img src="resources/img/logo.png">
                </div>
                <div class="footer_right">
                    (주) 교보문고    대표이사 : 이진수,이하민
                    <br>
                    사업자등록번호 : 010-3901-3517
                    <br>
                    대표전화 : 1111-2222(발신자 부담전화)
                    <br>
                    <br>
                    COPYRIGHT(C) <strong>kimvampa.tistory.com</strong>    ALL RIGHTS RESERVED.
                </div>
                <div class="clearfix"></div>
            </div>
        </div> <!-- class="footer" -->    
	</div>

</body>
</html>