<%@page import="com.hopon.dto.FavoritePlacesDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.hopon.action.*, com.hopon.dto.VehicleMasterDTO, java.util.*, com.hopon.utils.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="/pages/common/headerScript.xhtml"/>
<script type="text/javascript">
Date.prototype.format=function(b){
	var c={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),S:this.getMilliseconds()};if(/(y+)/.test(b)){b=b.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length))}
	for(var a in c){
		if(new RegExp("("+a+")").test(b)){
			b=b.replace(RegExp.$1,RegExp.$1.length==1?c[a]:("00"+c[a]).substr((""+c[a]).length));
		}
	}
	return b;
};
jQuery(document).ready(function(){updatePart();googleLoc()});
function updatePart(){
	$("#curentDate").datetimepicker({
		dateFormat:"dd M y,",timeFormat:"hh.mm TT",minDate:new Date(),onSelect:function(){
			var d=$("#curentDate").datepicker("getDate");
			d.setDate(d.getDate()+1);
			var c=$("#curentDate").datepicker("getDate");
			c.setDate(c.getDate()+61);
			$("#curentDate2").datepicker("option","minDate",d);
			$("#curentDate2").datepicker("option","maxDate",c)
		}
	}); 
	$("#curentDate2").datetimepicker({
		dateFormat:"dd M y,",timeFormat:"hh.mm TT",minDate:Date.parseString("${UserAction.minDate}","dd NNN yy, HH.mm a")});
		var a=new Date();a.setDate(a.getDate()+1);
		var b=new Date();b.setDate(b.getDate()+60);
		$("#curentDate2").datepicker("option","minDate",a);
		$("#curentDate2").datepicker("option","maxDate",b);
		$("#fromcity, #frompin, #tocity, #topin").prop("readonly",true);
		if($("#recurring").is(":checked")){
			$("#ifYes").show()
		}else{
			$("#ifYes").hide()
		}
		$("#recurring").click(function(){
			if($(this).is(":checked")){
				var c=Date.parseString($("#curentDate").val(),"dd NNN yy, HH.mm a");
				if(c!=null){
					currentTime=new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate());
					currentTime.setDate(currentTime.getDate()+2);
					if(!(c>=currentTime)){
						$("#curentDate").val("")
					}
				}
				currentTime=new Date();currentTime.setDate(currentTime.getDate()+2);
				$("#curentDate").datepicker("option","minDate",currentTime);
				$("#ifYes").show()
			}else{
				$("#ifYes").hide();
				$("#curentDate").datepicker("option","minDate",new Date())
			}
		});
		$("#fullDay").val("N");$("#fullDayCheck").click(function(){
			if($(this).is(":checked")){
				$("#fullDay").val("Y");
				$("#toaddress").prop("readonly",true)
			}else{
				$("#fullDay").val("N");
				$("#toaddress").prop("readonly",false)
			}
		})
	}
	function check(){
		document.getElementById("form:takeride").className+=" btn btn-small btn-success";
	}
</script>

<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script>
var x=jQuery.noConflict();function googleLoc(){x(function(){x("#fromaddress").autocomplete({source:function(b,a){$("#fromcity").val("");$("#frompin").val("");$("#startPoinLat").val("");$("#startPoinLng").val("");x.ajax({url:"http://maps.googleapis.com/maps/api/geocode/json",data:{sensor:false,address:b.term,components:"country:IN"},success:function(f){var l=new Array();for(var e in f.results){var h="";var g="";for(var d in f.results[e]["address_components"]){for(var c in f.results[e]["address_components"][d]["types"]){if(f.results[e]["address_components"][d]["types"][c]=="locality"){h=f.results[e]["address_components"][d]["long_name"];break}if(f.results[e]["address_components"][d]["types"][c]=="postal_code"){g=f.results[e]["address_components"][d]["long_name"];break}}}l.push({formatted_address:f.results[e]["formatted_address"],lat:f.results[e]["geometry"]["location"]["lat"],lng:f.results[e]["geometry"]["location"]["lng"],city:h,postal_code:g})}a($.map(l,function(i){return{label:i.formatted_address,value:i.formatted_address,lat:i.lat,lng:i.lng,city:i.city,postal_code:i.postal_code}}))}})},minLength:2,select:function(a,b){$("#fromcity").val(b.item.city);$("#frompin").val(b.item.postal_code);$("#startPoinLat").val(b.item.lat);$("#startPoinLng").val(b.item.lng)}});x("#toaddress").autocomplete({source:function(b,a){$("#tocity").val("");$("#topin").val("");$("#endPoinLat").val("");$("#endPoinLng").val("");x.ajax({url:"http://maps.googleapis.com/maps/api/geocode/json",data:{sensor:false,address:b.term,components:"country:IN"},success:function(f){var l=new Array();for(var e in f.results){var h="";var g="";for(var d in f.results[e]["address_components"]){for(var c in f.results[e]["address_components"][d]["types"]){if(f.results[e]["address_components"][d]["types"][c]=="locality"){h=f.results[e]["address_components"][d]["long_name"];break}if(f.results[e]["address_components"][d]["types"][c]=="postal_code"){g=f.results[e]["address_components"][d]["long_name"];break}}}l.push({formatted_address:f.results[e]["formatted_address"],lat:f.results[e]["geometry"]["location"]["lat"],lng:f.results[e]["geometry"]["location"]["lng"],city:h,postal_code:g})}a($.map(l,function(i){return{label:i.formatted_address,value:i.formatted_address,lat:i.lat,lng:i.lng,city:i.city,postal_code:i.postal_code}}))}})},minLength:2,select:function(a,b){$("#tocity").val(b.item.city);$("#topin").val(b.item.postal_code);$("#endPoinLat").val(b.item.lat);$("#endPoinLng").val(b.item.lng)}})})};
</script>
<script type="text/javascript">
function validate_rideSeeker(){var a=Date.parseString($("#curentDate").val(),"dd NNN yy, HH.mm a");var f=Date.parseString($("#curentDate2").val(),"dd NNN yy, HH.mm a");var e=$("#fromaddress").val();var d=$("#toaddress").val();var g=$("#recurring").is(":checked");var b=new Array();if(a==null){b.push("Please select Start Date.")}if(g){if(f==null){b.push("Please select End Date.")}}if($.trim(e).length==0){b.push("Please enter source address.")}if($("#fullDay").val()=="N"){if($.trim(d).length==0){b.push("Please enter destination address.")}}if($("#vehicleId")){if(parseInt($("#vehicleId").val())==0){b.push("Please select Vehicle.")}}if(g){if(a>f){b.push("End date should be after start date.")}}if(g){if((f-a)>1000*60*60*24*60){b.push("Recurring ride can be booked only for 2 months from the start date.")}}if(b.length>0){$("#errorMessage").show();$("#errorMessage").find(".form-message").html("");for(var c in b){$("#errorMessage").find(".form-message").append(b[c]+"&lt;br>")}}else{$("#errorMessage").hide();$("#errorMessage").find(".form-message").html("");return true}return false};
</script>  
</head>
<body>
<jsp:include page="/pages/common/headerContent.xhtml"/>
<div class="content"><div class="container">
<div class="row">
<div class="span2"><div class="smv-leftnav"><jsp:include page="/pages/common/leftPanel.xhtml"/></div></div>
<div class="span10">
	<div class="page-header"><h1>New Ride</h1></div>
	<form enctype="application/x-www-form-urlencoded" class="form-horizontal" action="/" method="post" name="tab1" id="tab1">
	<div class="row">
		<div class="span4">
			<div class="well changer" style="height: 125px;">
				<div class="control-group">
					<label class="control-label" for="inputPassword"><b>Trip date/time</b></label>
					<div class="controls"><input type="text" name="curentDate" id="curentDate" class="hasDatepicker"></div>
				</div>
				<c:if test="${sessionScope.user_role == 'C' || sessionScope.user_role == 'B'}">
				<div class="control-group">
                   	<label class="control-label" for="custom"><b>Custom</b></label>
                   	<div class="controls"><input type="text" name="custom" id="custom"/></div>
				</div>
				</c:if>
				<c:if test="${(userAction.ridePicker ==2 and sessionScope.user_role eq 'B') or (sessionScope.user_role eq 'P')}">
				<div class="control-group">
                   	<label class="control-label" for="inputPassword"><b>My Vehicles</b></label>
                   	<div class="controls" style="display: inline-block;margin-left: 8px;"> &nbsp;
                       	<select name="vehicleId" id="vehicleId">
                       	<%
				VehicleMasterDTO tempVehicleList = new VehicleMasterDTO();
				List<VehicleMasterDTO> vehicleMasterDTOList = ListOfValuesManager.getAllVehicleList(session.getAttribute("userId")+"");
				for (int i =0 ; i< vehicleMasterDTOList.size();i++){
					if(vehicleMasterDTOList.get(i).isIs_default()) {
						tempVehicleList=vehicleMasterDTOList.get(0);
						vehicleMasterDTOList.set(0, vehicleMasterDTOList.get(i));    			
						vehicleMasterDTOList.set(i, tempVehicleList); 			
					}
				}
				for (int i =0 ; i< vehicleMasterDTOList.size();i++){
					out.print("<option value='"+vehicleMasterDTOList.get(i).getVehicleID()+"'>"+ vehicleMasterDTOList.get(i).getReg_NO()+"</option>");
				}
				%>
                       	</select>
					</div>
				</div>
				</c:if>
			</div>
			<div class="well changer">
				<p><strong>Address From</strong></p>
				<div class="control-group">
					<label class="control-label" for="inputEmail">Address Tag</label>
					<div class="controls">
						<select size="1" name="from" id="from">
							<option value="new">New</option>
							<% 
							List<FavoritePlacesDTO> allPlace = ListOfValuesManager.getAllPlaces(session.getAttribute("userId")+"");
							for(int i = 0;i<allPlace.size();i++){
								if(allPlace.get(i).getTagtype().length() > 0) out.print("<option value='"+allPlace.get(i).getTagtype()+"'>"+allPlace.get(i).getTagtype()+"</option>");
							}
							%>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inputPassword">Address1</label>
					<div class="controls"><input type="text" name="fromaddress" id="fromaddress" class="ui-autocomplete-input" autocomplete="off"></div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inputEmail">City</label>
					<div class="controls"><input type="text" name="fromcity" id="fromcity" readonly=""></div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inputPassword">Pin</label>
					<div class="controls"><input type="text" name="frompin" id="frompin" readonly=""></div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inputPassword">Tag (20 limit)</label>
					<div class="controls"><input type="text" name="tag"></div>
				</div>
			</div>
			<div class="well changer" style="height:auto">
            	<p><strong>Recurring</strong> <input type="checkbox" class="input-xlarge" name="recurring" id="recurring"/></p>
   	         	<div id="ifYes" style="display:none;">
                	<div class="control-group">
                    	<label class="control-label" for="inputEmail">Frequency</label>
                      	<div class="controls">
							Mon <input type="checkbox" value="Mon" name="frequenct">
							Tue <input type="checkbox" value="Tue" name="frequenct">
							Wed <input type="checkbox" value="Wed" name="frequenct">
							Thu <input type="checkbox" value="Thu" name="frequenct">
							Fri <input type="checkbox" value="Fri" name="frequenct">
							Sat <input type="checkbox" value="Sat" name="frequenct">
							Sun <input type="checkbox" value="Sun" name="frequenct">
                     	</div>
                    </div>
                  	<div class="control-group">
                    	<label class="control-label" for="inputPassword">End Date</label>
						<div class="controls"><input type="text" name="curentDate2" id="curentDate2" class="hasDatepicker"></div>
                    </div>                    
            	</div>
        	</div>
		</div>
		<div class="span4">
			<c:if test="${(sessionScope.user_role == 'B') or sessionScope.user_role == 'C'}">
			<div class="well changer" style="height: 125px;">
				<div class="control-group">
					<div style="margin-bottom: 22px;padding: 7px 1px;">
						<c:if test="${(sessionScope.user_role == 'B' and userAction.ridePicker == 1) or sessionScope.user_role == 'C'}">
						<span style="margin-right:1px;"><input type="checkbox" name="carpool" id="carpool"/> Carpool&nbsp;</span>
						<span style="margin-right:2px;"><input type="checkbox" name="fullDayCheck" id="fullDayCheck"/> Full Day&nbsp;</span>
						<input type="hidden" name="fullDay" id="fullDay" value="N">
						</c:if>
						<input type="checkbox" checked="checked" name="checkboxes-0" id="checkboxes-0">  Auto Match-In circle only
					</div>
					<c:if test="${(sessionScope.user_role == 'B' and userAction.ridePicker == 1) or sessionScope.user_role == 'C'}">
					<label class="control-label" style="width: 86px;">Exp. Code / Mgr</label>
					<div class="controls">
						<select style="margin-left: 9px;width: 71%" size="1" name="approver">	
							<option selected="selected" value="">Select Approver</option>
							<c:forEach var="approver" items="#{userAction.approverdtoList}">
							<option value="{approver.id}">${approver.bCode} - ${approver.name}</option>
							</c:forEach>
						</select>
					</div>
					</c:if>
				</div>
			</div>
			</c:if>
			<div class="well changer">
				<p><strong>Address To</strong> &nbsp;</p>
				<div class="control-group">
					<label class="control-label" for="inputEmail">Address Tag</label>
					<div class="controls">
						<select size="1" name="from" id="from">
							<option value="new">New</option>
							<% 
							for(int i = 0;i<allPlace.size();i++){
								if(allPlace.get(i).getTagtype().length() > 0) out.print("<option value='"+allPlace.get(i).getTagtype()+"'>"+allPlace.get(i).getTagtype()+"</option>");
							}
							%>
						</select>
					</div>
				</div>
				<div class="control-group">
                      <label for="inputEmail" class="control-label">Address1</label>
                      <div class="controls"><input type="text" name="toaddress" id="toaddress" class="ui-autocomplete-input" autocomplete="off"></div>
                    </div>
                 <div class="control-group">
                      <label for="inputEmail" class="control-label">City</label>
                      <div class="controls"><input type="text" name="tocity" id="tocity" readonly=""></div>
                    </div>
                    <div class="control-group">
                      <label for="inputPassword" class="control-label">Pin</label>
                      <div class="controls">
                      	<input type="text" name="topin" id="topin" readonly="">
                      	<input type="hidden" value="0.0" name="endPoinLat" id="endPoinLat">
                      	<input type="hidden" value="0.0" name="endPoinLng" id="endPoinLng">
                      </div>
                    </div>
                    <div class="control-group">
                      <label for="inputPassword" class="control-label">Tag (20 limit)</label>
                      <div class="controls"><input type="text" name="toTag">
                      </div>
                    </div>
			</div>
			 <div class="well changer" style="height:150px;">
             	<p><strong>Flexibility (Time) </strong></p>
              	${userAction.userRatingUpdate()}
				<script type="text/javascript">jQuery(document).ready(function(){var a="${userAction.userRegistrationDTO.defaultTimeSlice}";if(a.length>5){a=a.substring(0,5)}$("#before_input").val(a);$("#after_input").val(a)});</script>
		        <div class="control-group">
               		<label class="control-label" for="inputPassword">Time before</label>
               		<div class="controls">
                        <input type="text" id="before" name="before"/>
                    </div>
      			</div>
                <div class="control-group">
               		<label class="control-label" for="inputPassword">Time After</label>
                	<div class="controls">
                      	<input type="text" id="after" name="after"/>
                   	</div>
               	</div>
           	</div>
		</div>
	</div>
	</form>
</div>
</div>
</div></div>
<jsp:include page="/pages/common/footer-content.xhtml" />
</body>
</html>