<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="com.woori.myhome.common.*" %>
<%@page import="com.woori.myhome.admin.noticeboard.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

</head>
<body>
<%
	String key = StringUtil.nullToValue(request.getParameter("key"), "1");
	String keyword = StringUtil.nullToValue(request.getParameter("keyword"), "");
	String pg = StringUtil.nullToValue(request.getParameter("pg"), "0");
	int totalCnt = (Integer)request.getAttribute("totalCnt");
%>

<%@include file="../include/nav.jsp" %>

			<form name="myform" method="get">
				<input type="hidden" name="key" id="key" value="<%=key%>"/>
				<input type="hidden" name="pg" id="pg" value="<%=pg%>"/>
				<input type="hidden" name="notice_id" id="notice_id" value=""/>
			
			    <div class="container" style="margin-top:30px ">
			        <h2 style="margin-bottom:40px">공지사항 게시판 (${totalCnt}건)</h2>   <!-- 배열은 $ (== < %=request.getAttribute("totalCnt")%>) 표현식 못씀 -->
					
			        <table class="table table-hover ">
			        	<colgroup>
			        		<col width="8%">
			        		<col width="*">
			        		<col width="12%">
			        		<col width="12%">
			        	</colgroup>
			            <thead class="table-secondary">
			              <tr>
			                <th>번호</th>
			                <th>제목</th>
			                <th>작성자</th>
			                <th style="text-align:center;">작성일</th>
			                <th style="text-align:center;">삭제</th>
			              </tr>
			            </thead>
			            <tbody>
			            <%
			            
			            List<AdminNoticeBoardDto> list = (List<AdminNoticeBoardDto>)request.getAttribute("noticeBoardList");
			           	for(AdminNoticeBoardDto tempDto : list){
			            %>
			              <tr>
			                <td><%=totalCnt - tempDto.getRnum()+1%></td>
			                <!-- 
			                < %
			                String url=request.getContextPath()+"/board/view?id="+tempDto.getId();
			                %>
			                 -->
			                <td><a href="#none" onclick="goView('<%=tempDto.getNotice_id() %>')"><%=tempDto.getNotice_title()%></a></td>
			                <td>관리자</td>
			                <td style="text-align:center;"><%=tempDto.getNotice_wdate()%></td>
			                <td style="text-align:center;"><button type="button" onClick="adminDelete('<%=tempDto.getNotice_id() %>')">삭제하기</button></td>

			              </tr>
			            <%}%>
			            </tbody>
			          </table>
			 
			          <div class="container mt-3" style="text-align:right;">
			          	<%=Pager.makeTag(request, 10, totalCnt) %>
			          </div>
			
			          <div class="container mt-3" style="text-align:right;">
			            <a href="<%=request.getContextPath()%>/noticeboard/write" 
			               class="btn btn-secondary">글쓰기</a>
			          </div>
			          
			    </div> <!-- container -->
			    
			</form>
   		</div><!-- //#content -->
	</div><!-- //#container -->
</div><!-- //#wrap -->
    
</body>
</html>

<script>

window.onload=function(){
	let key = '<%=key%>';
	var texts=["", "선택하세요", "제목", "내용", "제목+내용"];  //0을 비운거임
	document.getElementById("searchItem").innerHTML=texts[key];
	
}

function adminDelete(id){
		
	let frm = document.myform;
	frm.notice_id.value=id;
	alert(frm.notice_id.value);
	frm.action = "<%=request.getContextPath()%>/admin/noticeboard/delete";
	frm.method="get";
	frm.submit();

}

function changeSearch(id){
	
	var texts=["", "선택하세요", "제목", "내용", "제목+내용"];  //0을 비운거임
	document.getElementById("searchItem").innerHTML=texts[id]; //화면에 보이기 위해서
	document.getElementById("key").value=id; //컨트롤러로 넘기기 위해서
	document.getElementById("keyword").value="";
}

function goSearch(){
	
	let frm = document.myform;
	frm.pg.value=0;
	frm.action = "<%=request.getContextPath()%>/noticeboard/list";
	frm.method="get";
	frm.submit();
}

function goPage(pg){
	
	frm = document.myform;
	frm.pg.value=pg;
	frm.method="get";
	frm.action="${pageContext.request.contextPath}/noticeboard/list";
	frm.submit();
}

function goView(id){
	
	frm = document.myform;
	frm.notice_id.value=id;
	frm.method="get";
	frm.action="${pageContext.request.contextPath}/noticeboard/view";
	frm.submit();
}

</script>


