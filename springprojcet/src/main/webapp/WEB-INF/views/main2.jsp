<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome Main</title>
<link rel="stylesheet" href="resources/css/main2.css">
<link href="resources/jquery-ui/jquery-ui.css" rel="stylesheet"
	type="text/css" />
<link href="resources/jqGrid/css/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<link rel="short icon" href="#">
<script src="resources/jqGrid/js/jquery-1.11.0.min.js"></script>
<script src="resources/jquery-ui/jquery-ui.js"></script>
<%-- <script src="<c:url value="/resources/js/jquery.layout.js"/>"></script> --%>
<script src="resources/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script src="resources/jqGrid/js/jquery.jqGrid.min.js"></script>




<script>
 function startSetting() {
	 let today = new Date();   

	 let year = today.getFullYear(); // 년도
	 let month = today.getMonth() + 1;  // 월
	 let date = today.getDate();  // 날짜
	 //let day = today.getDay();  // 요일
	
	 var todayspan = document.getElementById("_today");
	 todayspan.innerHTML = year + '-' + month + '-' + date;
 }
 
 function dateChange() {
	 var Myelement = document.getElementById("selectDate");
	 var key = Myelement.options[Myelement.selectedIndex].value;
	 
	 let today = new Date();   

	 let year = today.getFullYear(); // 년도
	 let month = today.getMonth() + 1;  // 월
	 let date = today.getDate();  // 날짜
	 
	 var todayspan = document.getElementById("_today");
	 
	 if(key=="yyyy-MM-dd") {
		 todayspan.innerHTML = year + '-' + month + '-' + date;
	 }else if(key=="dd-MM-yyyy") {
		 todayspan.innerHTML = date + '-' + month + '-' + year;
	 }else if(key=="MM-dd-yyyy") {
		 todayspan.innerHTML = month + '-' + date + '-' + year;
	 }
 }
 function countrychange(){
	 var countrychan = document.getElementById("countryadd");
	 alert(countrychan.value)
 }
</script>
</head>
<body onload="startSetting()">

<div class="wrapper">
	
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
 			</div>
			<div class="clearfix"></div>			
		</div>
		<div class="navi_bar_area">
		<div class="dropdown">
			<select id="selectDate" onchange="dateChange()">
				<option>yyyy-MM-dd</option>
				<option>dd-MM-yyyy</option>
				<option>MM-dd-yyyy</option>			
			</select><br>  
			 <span id="_today"></span>    		      
			    </div>			
			    <img src="resources/img/logo.png"style="width:260px;">
	<select name="country" id="countryadd" onchange="countrychange()">
    <option value="none">국가</option>
    <option value="korea">한국</option>
    <option value="USA">미국</option>
    <option value="Japan">일본</option>
    <option value="Thailand">태국</option>
     <option value="Canada">캐나다</option>
    <option value="Germany">독일</option>
    <option value="Ukraine">우크라이나</option>
  </select>
  
  	<select name="language" id="countrylang" >
    <option value="none">언어</option>
    <option value="korean">한국어</option>
    <option value="english">미국어</option>
    <option value="Japanese">일본어</option>
    <option value="Thai">태국어</option>
     <option value="Canadian">캐나다어</option>
    <option value="German">독일어</option>
    <option value="Ukrainian">우크라이나어</option>
  </select>
  <div class="modal">
      <div class="modal_body">Modal
      <p>모델 창 띄운 내용들</p>
      
      </div>
    </div>
    <button class="btn-open-popup">Modal 띄우기</button>
    
	</div>
		<div class="content_area">
			<nav>
    <ul>
        <li><a href=”#”>첫번째</a></li>
        <li><a href=”#”>2</a></li>
        <li><a href=”#”>3</a></li>
        <li><a href=”#”>4</a></li>
        <li><a href=”#”>5</a></li>
    </ul>
</nav>

<h1>1. jsp상으로 jqgid 적용시켜보기(출력할 데이터는 javascript에서 json파일로 만들어 줌)</h1>
<h2>객체에 더 많은 조건을 가진, 더 많은 데이터를 넣고 출력 확인</h2>

	<table id="list1"></table>
	<script type="text/javascript">
		$(function() {

			// 변수 선언

			var i, max, myData, grid = $("#list1");

			// grid 설정

			grid.jqGrid({

				datatype : "local",
				height : 'auto',
				jsonReader: {  //시험삼아 추가한것들 
			            root: "rows", //arry containing actual data  
			            page: "page", //current page  
			            total: "total", //total pages for the query  
			            records: "records", //total number of records  
			            repeatitems: false,  
			            id: "ID" //index of the column with the PK in it  
				},
				pager: '#pager',
			    rowNum: 5,
			    rowList: [10,20,30,40],
				colNames : [ 'Inv No', 'Date', 'Client', 'Amount', 'Tax',
						'Total', 'Notes' ],

				colModel : [
					
				{
					name : 'id',
					index : 'id',
					width : 60,
					sorttype : "int"
				},

				{
					name : 'invdate',
					index : 'invdate',
					width : 90,
					sorttype : "date"
				},

				{
					name : 'name',
					index : 'name',
					width : 100
				},

				{
					name : 'amount',
					index : 'amount',
					width : 80,
					align : "right",
					sorttype : "float"
				},

				{
					name : 'tax',
					index : 'tax',
					width : 80,
					align : "right",
					sorttype : "float"
				},

				{
					name : 'total',
					index : 'total',
					width : 80,
					align : "right",
					sorttype : "float"
				},

				{
					name : 'note',
					index : 'note',
					width : 150,
					sortable : false
				}

				],

				multiselect : true,

				caption : "배열 데이터"

			});

			// 로컬 데이터

			myData = [

			{
				id : "1",
				invdate : "2007-10-01",
				name : "test",
				note : "note",
				amount : "200.00",
				tax : "10.00",
				total : "210.00"
			},

			{
				id : "2",
				invdate : "2007-10-02",
				name : "test2",
				note : "note2",
				amount : "300.00",
				tax : "20.00",
				total : "320.00"
			},

			{
				id : "3",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},

			{
				id : "4",
				invdate : "2007-10-04",
				name : "test",
				note : "note",
				amount : "200.00",
				tax : "10.00",
				total : "210.00"
			},

			{
				id : "5",
				invdate : "2007-10-05",
				name : "test2",
				note : "note2",
				amount : "300.00",
				tax : "20.00",
				total : "320.00"
			},

			{
				id : "6",
				invdate : "2007-09-06",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},

			{
				id : "7",
				invdate : "2007-10-04",
				name : "test",
				note : "note",
				amount : "200.00",
				tax : "10.00",
				total : "210.00"
			},

			{
				id : "8",
				invdate : "2007-10-03",
				name : "test2",
				note : "note2",
				amount : "300.00",
				tax : "20.00",
				total : "320.00"
			},

			{
				id : "9",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "10",
				invdate : "2007-10-01",
				name : "test4",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "11",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "12",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "13",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "14",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "15",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "16",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "17",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "18",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "19",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "20",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "21",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "22",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "23",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "24",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			},
			{
				id : "25",
				invdate : "2007-09-01",
				name : "test3",
				note : "note3",
				amount : "400.00",
				tax : "30.00",
				total : "430.00"
			}

			];
		
			// 데이터 추가

			for (i = 0, max = myData.length; i <= max; i++) {

				grid.jqGrid('addRowData', i + 1, myData[i]);

			}

		});
	</script>
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
                <canvas id="canvas" width="180" height="180" style="background-color:#333"></canvas>               
                </div>
                <div class="clearfix"></div>
            </div>
        </div> <!-- class="footer" -->    
	

<script>
var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
var radius = canvas.height / 2;
ctx.translate(radius, radius);
radius = radius * 0.90
//drawClock();

setInterval(drawClock, 1000);

function drawClock() {
  drawFace(ctx, radius);
  drawNumbers(ctx, radius);
  drawTime(ctx, radius);
}

function drawFace(ctx, radius) {
  var grad;
  ctx.beginPath();
  ctx.arc(0, 0, radius, 0, 2*Math.PI);
  ctx.fillStyle = 'white';
  ctx.fill();
  grad = ctx.createRadialGradient(0,0,radius*0.95, 0,0,radius*1.05);
  grad.addColorStop(0, '#333');
  grad.addColorStop(0.5, 'white');
  grad.addColorStop(1, '#333');
  ctx.strokeStyle = grad;
  ctx.lineWidth = radius*0.1;
  ctx.stroke();
  ctx.beginPath();
  ctx.arc(0, 0, radius*0.1, 0, 2*Math.PI);
  ctx.fillStyle = '#333';
  ctx.fill();
}

function drawNumbers(ctx, radius) {
  var ang;
  var num;
  ctx.font = radius*0.15 + "px arial";
  ctx.textBaseline="middle";
  ctx.textAlign="center";
  for(num = 1; num < 13; num++){
    ang = num * Math.PI / 6;
    ctx.rotate(ang);
    ctx.translate(0, -radius*0.85);
    ctx.rotate(-ang);
    ctx.fillText(num.toString(), 0, 0);
    ctx.rotate(ang);
    ctx.translate(0, radius*0.85);
    ctx.rotate(-ang);
  }
}

function drawTime(ctx, radius){
    var now = new Date();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    //hour
    hour=hour%12;
    hour=(hour*Math.PI/6)+
    (minute*Math.PI/(6*60))+
    (second*Math.PI/(360*60));
    drawHand(ctx, hour, radius*0.5, radius*0.07);
    //minute
    minute=(minute*Math.PI/30)+(second*Math.PI/(30*60));
    drawHand(ctx, minute, radius*0.8, radius*0.07);
    // second
    second=(second*Math.PI/30);
    drawHand(ctx, second, radius*0.9, radius*0.02);
}

function drawHand(ctx, pos, length, width) {
    ctx.beginPath();
    ctx.lineWidth = width;
    ctx.lineCap = "round";
    ctx.moveTo(0,0);
    ctx.rotate(pos);
    ctx.lineTo(0, -length);
    ctx.stroke();
    ctx.rotate(-pos);
}
const body = document.querySelector('body');
const modal = document.querySelector('.modal');
const btnOpenPopup = document.querySelector('.btn-open-popup');

btnOpenPopup.addEventListener('click', () => {
  modal.classList.toggle('show');

  if (modal.classList.contains('show')) {
    body.style.overflow = 'hidden';
  }
});

modal.addEventListener('click', (event) => {
  if (event.target === modal) {
    modal.classList.toggle('show');

    if (!modal.classList.contains('show')) {
      body.style.overflow = 'auto';
    }
  }
});
</script>
            
</body>
</html>