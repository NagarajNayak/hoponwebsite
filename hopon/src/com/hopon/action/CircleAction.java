package com.hopon.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import com.hopon.dto.CircleAffiliationsDTO;
import com.hopon.dto.CircleDTO;
import com.hopon.dto.CircleMemberDTO;
import com.hopon.dto.CircleOwnerDTO;
import com.hopon.dto.CircleOwnerManagerDTO;
import com.hopon.dto.MessageBoardDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.utils.ConfigurationException;
import com.hopon.utils.ListOfValuesManager;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.Messages;
import com.hopon.utils.Validator;

public class CircleAction extends BaseAction {
	/*protected List<CircleDTO> allCircleList = new ArrayList<CircleDTO>();
	protected List<CircleDTO> allMemberCircleList = new ArrayList<CircleDTO>();
	protected List<CircleDTO> allCircleListByName = new ArrayList<CircleDTO>();
	protected List<CircleDTO> taxiCircleList = new ArrayList<CircleDTO>();
	protected List<CircleDTO> nonTaxiCircleList = new ArrayList<CircleDTO>();
	protected String autoNonTaxiCircleValue;
	protected String autoTaxiCircleValue;
	protected int userRegisterCircle = 0;
	protected CircleAffiliationsDTO circleAffiliationsDTO = new CircleAffiliationsDTO();
	protected List<CircleAffiliationsDTO> allCircleAffiliationsDTO = new ArrayList<CircleAffiliationsDTO>();
	protected List<CircleAffiliationsDTO> allPendingCircleAffiliationsDTO = new ArrayList<CircleAffiliationsDTO>();
	protected CircleDTO circleDTO = new CircleDTO();
	protected int taxiCircleId;
	protected CircleDTO taxiCircle = new CircleDTO();
	protected List<CircleOwnerManagerDTO> allCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();
	protected List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();
	protected List<CircleOwnerManagerDTO> allPendingCircleMemberList = new ArrayList<CircleOwnerManagerDTO>();
	protected List<CircleDTO> allCircleMembershipInvitationList = new ArrayList<CircleDTO>();
	protected List<CircleAffiliationsDTO> allCircleAffiliationRequest = new ArrayList<CircleAffiliationsDTO>();
	protected CircleOwnerManagerDTO circleOwnerManagerDTO = new CircleOwnerManagerDTO();
	protected CircleMemberDTO circleMemberDTO = new CircleMemberDTO();
	protected List<CircleDTO> allTaxiCircle = new ArrayList<CircleDTO>();
	protected int affiliateCircleId;
	protected UserRegistrationDTO circleMember;
	protected List<CircleOwnerManagerDTO> allCircleOwnerManagerUserList = new ArrayList<CircleOwnerManagerDTO>();
	private List<SelectItem> allCircleOption;*/
	
	public List<CircleDTO> autoTaxiCircle(String prefix) {
		List<CircleDTO> circle = new ArrayList<CircleDTO>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager.getallRegisteredTaxiCircleListed(prefix).iterator();
		while(circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO)circleTemp.next();
			if(dto.getName() != null) {
				circle.add(dto);
			}
		}
		return circle;
	}
	
	public List<CircleDTO> autoNonTaxiCircle(String prefix) {
		List<CircleDTO> circle = new ArrayList<CircleDTO>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager.getallRegisteredNonTaxiCircleListed(prefix).iterator();
		while(circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO)circleTemp.next();
			if(dto.getName() != null) {
				circle.add(dto);
			}
		}
		return circle;
	}
	public List<String> autoTaxiCircleStr(String prefix) {
		List<String> circle = new ArrayList<String>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager.getallRegisteredTaxiCircleListed(prefix).iterator();
		while(circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO)circleTemp.next();
			if(dto.getName() != null) {
				circle.add(dto.getName() + " - " + dto.getCircleID());
			}
		}
		return circle;
	}
	
	public List<String> autoNonTaxiCircleStr(String prefix) {
		List<String> circle = new ArrayList<String>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager.getallRegisteredNonTaxiCircleListed(prefix).iterator();
		while(circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO)circleTemp.next();
			if(dto.getName() != null) {
				circle.add(dto.getName() + " - " + dto.getCircleID());
			}
		}
		return circle;
	}
	
	
	public void makeCircleAffiliated() {
		int parentCircleId = taxiCircle.getCircleID();
		//FacesContext context = FacesContext.getCurrentInstance();  
		//Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		int childCircleId = affiliateCircleId;
		gatherAffiliatedCircle(parentCircleId);
		clearScreenMessage();
		if(parentCircleId <= 0) {
			errorMessage.add("Please select a Circle first.");
		} else if(childCircleId <= 0) {
			errorMessage.add("Please select affiliated Taxi circle.");
		} else {			
			ListOfValuesManager.makeTaxiCircleAffiliated(parentCircleId, childCircleId);
			successMessage.add(Messages.getValue("success.taxi.affiliation"));			
		}
		gatherDefaultcircleDTO();
	}
	public String allListedCircle(){
		allCircleList = ListOfValuesManager.getallCircleListed(userRegistrationDTO.getId());
		
		allMemberCircleList = ListOfValuesManager.getallMemberCircleListed(userRegistrationDTO.getId());
		

		return "circleList";
	}
	public void fetchCircleList() {
		taxiCircleList = new ArrayList<CircleDTO>();
		nonTaxiCircleList = new ArrayList<CircleDTO>();
		List<CircleDTO> circleList = null;
		circleList = ListOfValuesManager.getallRegisteredCircleListed();
		
		for(CircleDTO dto:circleList) {
			if(dto.isTaxiCircle()) {
				taxiCircleList.add(dto);
			} else {
				nonTaxiCircleList.add(dto);
			}
		}
	}

	public List<SelectItem> getAllTaxiCilcleDropDownList(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		circleList = ListOfValuesManager.getallRegisteredCircleListed();
		for (int i =0 ; i< circleList.size();i++){
			if(circleList.get(i).isTaxiCircle()) list.add(new SelectItem(circleList.get(i).getCircleID(),circleList.get(i).getName()));
		}
		return list;
	}

	public List<SelectItem> getAllNonTaxiCilcleDropDownList(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		circleList = ListOfValuesManager.getallRegisteredCircleListed();
		for (int i =0 ; i< circleList.size();i++){
			if(!circleList.get(i).isTaxiCircle()) list.add(new SelectItem(circleList.get(i).getCircleID(),circleList.get(i).getName()));
		}
		return list;
	}
	public void processValueChange2(AjaxBehaviorEvent event) throws AbortProcessingException {
		allCircleForLoginUser();
		String value = String.valueOf(((UIInput) event.getSource()).getValue());
		List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
		for(int i =0;i<allCircleMemberForLoginUserList.size();i++){
			if(allCircleMemberForLoginUserList.get(i).getCircleID().equals(value)){
				CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
				dto = allCircleMemberForLoginUserList.get(i);
				allCircleMemberForLoginUserList1.add(dto);
			}
		}
		allCircleMemberForLoginUserList.clear();
		allCircleMemberForLoginUserList.addAll(allCircleMemberForLoginUserList1);
		allCircleMemberForLoginUserList1.clear();

		List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
		for(int i=0;i<allPendingCircleMemberList.size();i++) {
			if(allPendingCircleMemberList.get(i).getCircleID().equals(value)){
				CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
				dto = allPendingCircleMemberList.get(i);
				allPendingCircleMemberList1.add(dto);
			}
		}
		allPendingCircleMemberList.clear();
		allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
		allPendingCircleMemberList1.clear();
	}
	public void processValueChange3(AjaxBehaviorEvent event) throws AbortProcessingException {
		String circleId = String.valueOf(((UIInput) event.getSource()).getValue());
		gatherAffiliatedCircle(Integer.parseInt(circleId));
	}
	public void gatherDefaultcircleDTO() {		
		if(taxiCircle.getCircleID() >= 0) {
			gatherAffiliatedCircle(taxiCircle.getCircleID());
		}
	}
	public void clearCircleForLoginUser() {
		allCircleMemberForLoginUserList.clear();
		circleDTO = new CircleDTO();
		circleOwnerManagerDTO = new CircleOwnerManagerDTO();
	}

	public void registerCircle(){
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		clearScreenMessage();
		if(userRegistrationDTO.getTravel().equals("T") && allCircleForLoginUserList.size() > 0) {
			errorMessage.add(Messages.getValue("circle.add.restriction", new Object[]{1, "Circle"}));
			circleDTO = new CircleDTO();
			return;
		}

		circleDTO.setCircleOwner_Member_Id_P(Integer.valueOf(userRegistrationDTO.getId()));
		circleDTO.setDate_of_creation(ListOfValuesManager.getcurrentDate());
		CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
		try {
			circleDTO = ListOfValuesManager.getregisterCircle(circleDTO, con);
			circleOwnerDTO.setCircleID(String.valueOf(circleDTO.getCircleID()));
			circleOwnerDTO.setUserID(userRegistrationDTO.getId());
			circleOwnerDTO = ListOfValuesManager.getregisterCircleOwner(circleOwnerDTO, userRegistrationDTO.getId(), con);
			circleMemberDTO.setUserid(userRegistrationDTO.getId());
			circleMemberDTO.setCircleid(circleOwnerDTO.getCircleID());
			circleMemberDTO.setStatus("1");
			circleMemberDTO = ListOfValuesManager.getJoinCircle(circleMemberDTO, userRegistrationDTO.getId(), con);
			successMessage.add(Messages.getValue("success.add", new Object[]{"Circle"}));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
			errorMessage.add(Messages.getValue("error.db1", new Object[]{"Circle"}));
		} finally {
			if(rollbackTest) {
				try { con.rollback(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			} else {
				try { con.commit(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			}
			rollbackTest = false;
		}
		allListedCircle();
		allCircleForLoginUser();
		allCircleMemberForLoginUserList.clear();
		allPendingCircleMemberList.clear();
		getAllCilcleForLoginUserDropDownList();

		circleDTO = new CircleDTO();
		circleMemberDTO = new CircleMemberDTO();
		//return "circleRegistered";
	}

	public String allCircleForLoginUser(){

		allCircleForLoginUserList = ListOfValuesManager.getAllCircleForLoginUser(userRegistrationDTO.getId());
		
		allCircleMemberForLoginUserList = ListOfValuesManager.getAllCircleMemberForLoginUser(userRegistrationDTO.getId());
		
		allPendingCircleMemberList = ListOfValuesManager.getAllPendingCircleMemberForLoginUser(userRegistrationDTO.getId());
		

		memberForSelectedCircle();

		return "allCircleList";
	}

	public String memberForSelectedCircle() {
		allCircleMemberForLoginUserList = ListOfValuesManager.getAllCircleMemberForLoginUser(userRegistrationDTO.getId());
		
		allPendingCircleMemberList = ListOfValuesManager.getAllPendingCircleMemberForLoginUser(userRegistrationDTO.getId());
		
		return "selected";
	}
	public List<SelectItem> getAllCilcleForLoginUserDropDownList(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (int i =0 ; i< allCircleForLoginUserList.size();i++){
			list.add(new SelectItem(allCircleForLoginUserList.get(i).getCircleID(),allCircleForLoginUserList.get(i).getCircleName()));
		}
		return list;
	}
	public List<SelectItem> getAllCilcleMemberForLoginUserDropDownList(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (int i =0 ; i< allCircleMemberForLoginUserList.size();i++){
			String listText = "";
			listText += allCircleMemberForLoginUserList.get(i).getMemberName();
			if(allCircleMemberForLoginUserList.get(i).getAge() != null && allCircleMemberForLoginUserList.get(i).getAge().length() > 0) {
				listText += " of age: " + allCircleMemberForLoginUserList.get(i).getAge();
			}
			if(allCircleMemberForLoginUserList.get(i).getAddress() != null && allCircleMemberForLoginUserList.get(i).getAddress().length() > 0) {
				listText += " lives in: " + allCircleMemberForLoginUserList.get(i).getAddress();
			}
			if(allCircleMemberForLoginUserList.get(i).getAdminInfo() != null && allCircleMemberForLoginUserList.get(i).getAdminInfo().length() > 0) {
				listText += " - " + allCircleMemberForLoginUserList.get(i).getAdminInfo();
			}

			list.add(new SelectItem(allCircleMemberForLoginUserList.get(i).getUserid()+","+allCircleMemberForLoginUserList.get(i).getCircleID()+","+allCircleMemberForLoginUserList.get(i).getSuperAdmin() , listText));

		}
		return list;
	}
	
	public List<SelectItem> getAllCilcleMemberToSendMessageDropDownList(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (int i =0 ; i< allCircleMemberForLoginUserList.size();i++){
			list.add(new SelectItem(allCircleMemberForLoginUserList.get(i).getCircleID() , allCircleMemberForLoginUserList.get(i).getCircleName()));

		}
		return list;
	} 
	
	public List<SelectItem> getAllAffiliatedCilcleMember(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		//allCircleAffiliationsDTO
		for(CircleAffiliationsDTO dto:allCircleAffiliationsDTO) {			
			list.add(new SelectItem(dto.getCircleAffilicatedCircleId(), dto.getAffilicatedCircleName()));
		}
		return list;
	}
	public List<String> getAllPendingAffiliatedCilcleMember(){
		List<String> list = new ArrayList<String>();
		//allCircleAffiliationsDTO
		for(CircleAffiliationsDTO dto:allPendingCircleAffiliationsDTO) {			
			list.add(dto.getAffilicatedCircleName());
		}
		return list;
	}

	public List<String> getAllPendingCilcleMemberForLoginUser(){
		List<String> list = new ArrayList<String>();
		for (int i =0 ; i< allPendingCircleMemberList.size();i++){
			String listText = "";
			listText += allPendingCircleMemberList.get(i).getMemberName();
			if(allPendingCircleMemberList.get(i).getAge() != null && allPendingCircleMemberList.get(i).getAge().length() > 0) {
				listText += " of age: " + allPendingCircleMemberList.get(i).getAge();
			}
			if(allPendingCircleMemberList.get(i).getAddress() != null && allPendingCircleMemberList.get(i).getAddress().length() > 0) {
				listText += " lives in: " + allPendingCircleMemberList.get(i).getAddress();
			}
			if(allPendingCircleMemberList.get(i).getAdminInfo().length() > 0) {
				listText += " - " + allPendingCircleMemberList.get(i).getAdminInfo();
			}

			list.add(listText);

		}
		return list;
	}

	public List<SelectItem> getAllAffiliateCircleForTaxiUser() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<CircleDTO> dto = new ArrayList<CircleDTO>();
		try {
			dto = ListOfValuesManager.getAffiliateCircleForTaxiUser(Integer.parseInt(userRegistrationDTO.getId()));
			
		} catch (NumberFormatException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
		}
		for(CircleDTO dtoTemp : dto) {
			list.add(new SelectItem(dtoTemp.getCircleID(), dtoTemp.getName()));
		}
		return list;
	}

	public void confirmOrDeclineAffiliatedCircle() {
		String confirm = null;
		String decline = null;
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		confirm = (String)requestMap.get("confirm");
		decline = (String)requestMap.get("decline");
		int circleId = 0;
		String[] part = null;
		if(requestMap.containsKey("circleAffiliation")) {
			part = requestMap.get("circleAffiliation").split("-");
		} else {
			part = circleAffiliationsDTO.getCircleAffilicatedCircleId().split("-");
		}


		int affiliatedCircleId = 0;
		if(part.length >= 2) {
			if(part[0] != null && part[0].length() > 0 && Integer.parseInt(part[0]) > 0) {
				circleId = Integer.parseInt(part[0]);
			}
			if(part[1] != null && part[1].length() > 0 && Integer.parseInt(part[1]) > 0) {
				affiliatedCircleId = Integer.parseInt(part[1]);
			}
		}
		
		clearScreenMessage();
		if(circleId <= 0 || affiliatedCircleId <= 0) {
			errorMessage.add("Please select a Circle first and then select affiliated taxi circle.");			
		} else {
			if(confirm != null) {
				ListOfValuesManager.makeTaxiCircleAffiliatedActive(circleId, affiliatedCircleId);
				successMessage.add("Selected taxi affiliated successfully with circle.");
			} else if(decline != null){
				ListOfValuesManager.makeTaxiCircleAffiliatedInactive(circleId, affiliatedCircleId);
				successMessage.add("Selected taxi un-affiliated successfully with circle.");
			}
			gatherDefaultcircleDTO();
			messageForLoginUser();
		}
	}
	public void declineCircle(){
		circleDTO=ListOfValuesManager.getDeclineCircle(circleDTO, userRegistrationDTO);
		
		rollbackTest = false;
		allListedCircle();
		allCircleForLoginUser();
		circleDTO=new CircleDTO();
		//return "decline";
	}
	public void findCircleByName(){
		allCircleListByName = ListOfValuesManager.getCircleByName(circleDTO.getName(),userRegistrationDTO.getId());
		
		forregistrationOnly.setMyCircle(circleDTO.getName());
		circleDTO = new CircleDTO();
	}

	public void joinCircle(){
		circleMemberDTO.setCircleid(String.valueOf(circleDTO.getCircleID()));
		circleMemberDTO.setUserid(userRegistrationDTO.getId());
		circleMemberDTO.setRequestUserId(userRegistrationDTO.getId());
		try {
			circleMemberDTO = ListOfValuesManager.getJoinCircle(circleMemberDTO, userRegistrationDTO.getId(), null);
			//allCircleListByName = ListOfValuesManager.getCircleByName("",userRegistrationDTO.getId());
			allCircleListByName = ListOfValuesManager.getCircleByName(forregistrationOnly.getMyCircle(),userRegistrationDTO.getId());
			CircleDTO dtoTemp = ListOfValuesManager.getCircleDtoByCircleId(circleDTO.getCircleID());
			userMessageDTO.setMessage("User "+userRegistrationDTO.getFirst_name()+" has sent a request to join "+dtoTemp.getName()+" circle.");
			userMessageDTO.setToMember(dtoTemp.getCircleOwner_Member_Id_P());
			userMessageDTO.setMessageChannel("M");
			userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
			userMessageDTO = new MessageBoardDTO();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
		}
		
		rollbackTest = false;
		circleDTO = new CircleDTO();
		//return "joinedCircle";
	}

	
	public void getCircleMemberInfo() {
		String x = circleOwnerManagerDTO.getCombineUserAndCircleID();
		String[] part = x.split(",");
		String userId = "0";
		try {
			userId = part[0];
		} catch(Exception e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());}
		List<Integer> x2 = new ArrayList<Integer>();
		x2.add(Integer.parseInt(userId));
		List<UserRegistrationDTO> dtosTemp = ListOfValuesManager.getAllUserById(x2);
		
		if(dtosTemp.size() > 0) {
			circleMember = dtosTemp.get(0);
		} else {
			circleMember = new UserRegistrationDTO();
		}		
	}

	public String clearCirclrMemberInfo() {
		circleMember = new UserRegistrationDTO();
		return "";
	}

	public void deActivateCircle() {
		clearScreenMessage();
		if(circleDTO.getCircleID() > 0) {
			circleDTO=ListOfValuesManager.getDeactivateCircle(circleDTO);
			circleDTO = ListOfValuesManager.getCircleDtoByCircleId(circleDTO.getCircleID());
			userMessageDTO = new MessageBoardDTO();
	
			userMessageDTO.setMessage("User "+ userRegistrationDTO.getFirst_name() +" De-Activated circle '"+ circleDTO.getName() +"'.");
			userMessageDTO.setToMember(circleDTO.getCircleOwner_Member_Id_P());
			userMessageDTO.setCreatedBy(Integer.parseInt(userRegistrationDTO.getId()));
			userMessageDTO.setEmailSubject("Circle De-Activation");
			userMessageDTO.setMessageChannel("E");
	
			userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
	
			if(circleDTO.getStatus().equals("2")) {
				successMessage.add("circle De-Activated successfully.");
			} else {
				errorMessage.add("There is some issue in de activating circle.");
			}
			allListedCircle();
			allCircleForLoginUser();
			memberForSelectedCircle();
		} else {
			errorMessage.add("Please Select Circle first.");
		}
		circleDTO=new CircleDTO();
	}

	public void addMemberToCircle(){
		circleMemberDTO.setCircleid(String.valueOf(circleDTO.getCircleID()));
		String[] parts = forregistrationOnly.getFirst_name().split(",");
		String part2 = "";
		clearScreenMessage();
		if(!Validator.isNumberZeroNotAlloed(String.valueOf(circleDTO.getCircleID()))) errorMessage.add("Please select Circle first.");
		
		if(parts.length < 2) {
			errorMessage.add("Please Select Member.");
		} else {
			part2 = parts[parts.length - 1];
		}
		
		if(errorMessage.size() == 0) {
			
			circleMemberDTO.setUserid(part2);
			circleMemberDTO.setStatus("0");
			if(errorMessage.size() == 0){ 
				try {
					circleMemberDTO = ListOfValuesManager.getJoinCircle(circleMemberDTO, userRegistrationDTO.getId(), null);
					successMessage.add("User added to circle. The request is pending for user approval.");
				} catch (ConfigurationException e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
					errorMessage.add("There is some problem in adding member to circle..");
				}
			}
			memberForSelectedCircle();
	
			List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
			for(int i =0;i<allCircleMemberForLoginUserList.size();i++){
				if(allCircleMemberForLoginUserList.get(i).getCircleID().equals(circleMemberDTO.getCircleid())){
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allCircleMemberForLoginUserList.get(i);
					allCircleMemberForLoginUserList1.add(dto);
				}
			}
			allCircleMemberForLoginUserList.clear();
			allCircleMemberForLoginUserList.addAll(allCircleMemberForLoginUserList1);
			allCircleMemberForLoginUserList1.clear();
	
			List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
			for(int i=0;i<allPendingCircleMemberList.size();i++) {
				if(allPendingCircleMemberList.get(i).getCircleID().equals(circleMemberDTO.getCircleid())){
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allPendingCircleMemberList.get(i);
					allPendingCircleMemberList1.add(dto);
				}
			}
			allPendingCircleMemberList.clear();
			allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
			allPendingCircleMemberList1.clear();
	
			//circleDTO = new CircleDTO();
			forregistrationOnly = new UserRegistrationDTO();
			//return "memberAdded";
		}
	}


	public void makeAdmin() {
		clearScreenMessage();
		//FacesContext context = FacesContext.getCurrentInstance();  
		//Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		//String userAndCircleId = (String)requestMap.get("userAndCircleId");
		//String circleId = (String)requestMap.get("circleId");
		String[] parts = circleOwnerManagerDTO.getCombineUserAndCircleID().split(",");
		String part1 = parts[0]; 
		//String part2 = parts[1];
		if(!(circleDTO.getCircleID() > 0)) {
			errorMessage.add("Please select Circle.");
		} else if(!Validator.isNumberZeroNotAlloed(part1)) {
			errorMessage.add("Please select Circle Member you want to make admin.");
		} else {
			CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
			circleOwnerDTO.setCircleID(String.valueOf(circleDTO.getCircleID()));
			circleOwnerDTO.setUserID(part1);
			try {
				circleOwnerDTO = ListOfValuesManager.getregisterCircleOwner(circleOwnerDTO, userRegistrationDTO.getId(), null);
				successMessage.add("User is now admin of selected circle "+circleDTO.getName()+".");
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
				rollbackTest = true;
				errorMessage.add("There is some problem in making user admin of circle "+circleDTO.getName()+".");
			}
			rollbackTest = false;
			memberForSelectedCircle();
	
			List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
			for(int i =0;i<allCircleMemberForLoginUserList.size();i++){
				if(allCircleMemberForLoginUserList.get(i).getCircleID().equals(circleOwnerDTO.getCircleID())){
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allCircleMemberForLoginUserList.get(i);
					allCircleMemberForLoginUserList1.add(dto);
				}
			}
			allCircleMemberForLoginUserList.clear();
			allCircleMemberForLoginUserList.addAll(allCircleMemberForLoginUserList1);
			allCircleMemberForLoginUserList1.clear();
	
			List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
			for(int i=0;i<allPendingCircleMemberList.size();i++) {
				if(allPendingCircleMemberList.get(i).getCircleID().equals(circleOwnerDTO.getCircleID())){
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allPendingCircleMemberList.get(i);
					allPendingCircleMemberList1.add(dto);
				}
			}
			allPendingCircleMemberList.clear();
			allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
			allPendingCircleMemberList1.clear();
		} 
		//return "admin";
	}
	public void removeAdmin(){
		String[] parts = circleOwnerManagerDTO.getCombineUserAndCircleID().split(",");
		clearScreenMessage();
		if(!(circleDTO.getCircleID() > 0)) {
			errorMessage.add("Please select Circle.");
		} else if(!Validator.isNumberZeroNotAlloed(parts[0])) {
			errorMessage.add("Please select Circle Member you want to remove admin.");
		} else {
			
		
		String part1 = parts[0];
		String part2 = parts[2];
		CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
		circleOwnerDTO.setCircleID(String.valueOf(circleDTO.getCircleID()));
		circleOwnerDTO.setUserID(part1);
		if(part2.equalsIgnoreCase(part1) ){
			errorMessage.add("Circle can not remove admin authorisation.");
		} else {
			try {
				circleOwnerDTO = ListOfValuesManager.updateRegisterCircleOwner(circleOwnerDTO, userRegistrationDTO.getId(), null);
				successMessage.add("User is now not admin of selected circle "+circleDTO.getName()+".");
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
				rollbackTest = true;
				errorMessage.add("There is some problem in removing admin authorisation of circle "+circleDTO.getName()+".");
			}
			rollbackTest = false;
			memberForSelectedCircle();

			List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
			for(int i =0;i<allCircleMemberForLoginUserList.size();i++){
				if(allCircleMemberForLoginUserList.get(i).getCircleID().equals(circleOwnerDTO.getCircleID())){
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allCircleMemberForLoginUserList.get(i);
					allCircleMemberForLoginUserList1.add(dto);
				}
			}
			allCircleMemberForLoginUserList.clear();
			allCircleMemberForLoginUserList.addAll(allCircleMemberForLoginUserList1);
			allCircleMemberForLoginUserList1.clear();

			List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
			for(int i=0;i<allPendingCircleMemberList.size();i++) {
				if(allPendingCircleMemberList.get(i).getCircleID().equals(circleOwnerDTO.getCircleID())){
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allPendingCircleMemberList.get(i);
					allPendingCircleMemberList1.add(dto);
				}
			}
			allPendingCircleMemberList.clear();
			allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
			allPendingCircleMemberList1.clear();
			}
		}
		//return"adminRemoved";
	}
	public void processValueChange4(AjaxBehaviorEvent event) throws AbortProcessingException {
		gatherAffiliatedCircle(manageRideFormDTO.getMyCircleId());
	}
}
