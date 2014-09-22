package com.hopon.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.json.JSONException;

import com.hopon.dto.ApproverDTO;
import com.hopon.dto.CircleDTO;
import com.hopon.dto.CombineVehicleDataModel;
import com.hopon.dto.EmailDTO;
import com.hopon.dto.FavoritePlacesDTO;
import com.hopon.dto.FrequencyDTO;
import com.hopon.dto.MatchedTripDTO;
import com.hopon.dto.MatchedTripDataModel;
import com.hopon.dto.MessageBoardDTO;
import com.hopon.dto.PoolRequestsDTO;
import com.hopon.dto.RideManagementDTO;
import com.hopon.dto.RideSeekerDTO;
import com.hopon.dto.UserPreferencesDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.dto.VehicleMasterDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.ConfigurationException;
import com.hopon.utils.ControllerException;
import com.hopon.utils.ListOfValuesManager;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.MailService;
import com.hopon.utils.Messages;
import com.hopon.utils.ServerUtility;
import com.hopon.utils.Validator;

public class RideAction extends BaseAction {
	/*protected boolean recurring = false;
	protected boolean allowRecurringSubRideToCancel = true;
	protected RideManagementDTO rideRegistered = new RideManagementDTO();
	protected List<RideSeekerDTO> allSeekerForGivenRide = new ArrayList<RideSeekerDTO>();
	protected FrequencyDTO frequencyDTO = new FrequencyDTO();
	protected List<RideManagementDTO> rideManagementList = new ArrayList<RideManagementDTO>();
	protected List<RideSeekerDTO> rideSeekerList = new ArrayList<RideSeekerDTO>();
	protected List<RideSeekerDTO> recurringRideSeekerList = new ArrayList<RideSeekerDTO>();
	protected List<PoolRequestsDTO> allCompleateRideList = new ArrayList<PoolRequestsDTO>();
	protected List<PoolRequestsDTO> allCompleateRideSeekerList = new ArrayList<PoolRequestsDTO>();
	protected List<PoolRequestsDTO> allMsgBoardCompleateRideList = new ArrayList<PoolRequestsDTO>();
	protected List<PoolRequestsDTO> allMsgBoardCompleateRideSeekerList = new ArrayList<PoolRequestsDTO>();
	protected ApproverDTO approverdto = new ApproverDTO();
	protected List<ApproverDTO> approverdtoList = new ArrayList<ApproverDTO>();
	protected PoolRequestsDTO poolRequestsDTO = new PoolRequestsDTO();
	protected List<MatchedTripDTO> matchedTripByConditionList = new ArrayList<MatchedTripDTO>();
	protected List<CombineRideDTO> combineVehicleCondition = new ArrayList<CombineRideDTO>();
	protected List<RideSeekerDTO> allRideApprovalRequest = new ArrayList<RideSeekerDTO>();
	protected MatchedTripDataModel matchedTripDataModel = new MatchedTripDataModel();
	protected CombineVehicleDataModel combineVehicleDataModel = new CombineVehicleDataModel();
	protected MatchedTripDTO[] matchedTripDTOs ;
	protected CombineRideDTO[] combineVehicleDtos;
	protected RideSeekerDTO rideSeekerDTO = new RideSeekerDTO();
	protected RideManagementDTO rideManagementDTO = new RideManagementDTO();
	protected int rideIdToDrop;
	protected int rideIdToTake;
	protected String rideManager = "takeRide";
	protected int ridePicker = 1;
	protected List<RideManagementDTO> rideManagementListForPopupOne = new ArrayList<RideManagementDTO>();
	protected MatchedTripDTO matchedTripDTO = new MatchedTripDTO();
	protected boolean pendingRideTest;
	protected boolean ridePreMatchFormTest = false;
	protected boolean rideMatchFormTest = false;
	protected ManageRideFormDTO manageRideFormDTO = new ManageRideFormDTO();
	protected List<ManageRideDTO> manageRideDTOs = new ArrayList<ManageRideDTO>();
	protected String matchRideCancelParam;
	protected int ridePreMatchTest;
	protected RideManagementDTO rideManagerInfoForRideSeeker = new RideManagementDTO();
	*/
	public String rideManagementList(){

		rideManagementList = ListOfValuesManager.getRideManagementList(userRegistrationDTO.getId());
		List<RideManagementDTO> listNew = new ArrayList<RideManagementDTO>();
		for(RideManagementDTO dto:rideManagementList) {
			if(!dto.getStatus().equalsIgnoreCase("T") && userRegistrationDTO.getTravel().equalsIgnoreCase("T")) {
				continue;
			}
			if(!dto.getStatus().equalsIgnoreCase("A") && userRegistrationDTO.getTravel().equalsIgnoreCase("P")) {
				continue;
			}
			if(!dto.getStatus().equalsIgnoreCase("A") && userRegistrationDTO.getTravel().equalsIgnoreCase("B")) {
				continue;
			}
			listNew.add(dto);				
		}
		rideManagementList.clear();
		rideManagementList.addAll(listNew);
		listNew.clear();
		
		
		rideSeekerList = ListOfValuesManager.getAllRideSeekerList(userRegistrationDTO.getId());
		List<RideSeekerDTO> temp1 = new ArrayList<RideSeekerDTO>();
		recurringRideSeekerList.clear();
		for(RideSeekerDTO dto : rideSeekerList) {
			if(dto.getRecurring().equals("Y")) {
				recurringRideSeekerList.add(dto);
			} else {
				temp1.add(dto);
			}
		}
		rideSeekerList.clear();rideSeekerList.addAll(temp1);
		
		pendingRideTest = false;
		for(RideSeekerDTO dto : rideSeekerList) {
			if(dto.getStatus().equals("A") || dto.getStatus().equals("I")) {
				pendingRideTest = true;
				break;
			}
		}
		Collections.reverse(rideManagementList);
		Collections.reverse(rideSeekerList);

		return "rideManagementList";
	}

	public void registerRide(){
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		String ride = null;
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		ride = (String)requestMap.get("ride");
		clearScreenMessage();
		
		if(this.recurring && rideRegistered.getEndDate1() == null) {
			errorMessage.add("Please select proper end date.");
		}
		if(rideRegistered.getFromAddress1() == null || rideRegistered.getFromAddress1().trim().length() == 0) {
			errorMessage.add("Please enter source address.");
		}
		if(rideRegistered.getFullDay().equalsIgnoreCase("N") && (rideRegistered.getToAddress1() == null || rideRegistered.getToAddress1().trim().length() == 0)) {
			errorMessage.add("Please enter destination address.");
		}
		if(rideManager.equals("giveRide") && Integer.parseInt(rideRegistered.getVehicleID()) <= 0){
			errorMessage.add("Please select Vehicle.");
		}
		
		if(ride != null){
			rideManager = ride;
		}
		try {
			if(errorMessage.size() > 0) throw new ControllerException();
			rideRegistered.setUserID(userRegistrationDTO.getId());
			DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern3);
			
			Calendar cal = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();

			try {
				rideRegistered.setStartDate(new SimpleDateFormat(ApplicationUtil.datePattern4).parse(rideRegistered.getStartDate1()));
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
				errorMessage.add("Please select proper start date.");
				throw new ControllerException();
			}
			cal.setTime(rideRegistered.getStartDate());

			try {
				if(rideRegistered.getEndDate1() != null && rideRegistered.getEndDate1().length() > 0) {
					rideRegistered.setEndDate(new SimpleDateFormat(ApplicationUtil.datePattern4).parse(rideRegistered.getEndDate1()));
				}
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			} 
			long dateDiff = 0;
			if(this.recurring) {
				Calendar startDateCalendar = Calendar.getInstance(); startDateCalendar.setTime(rideRegistered.getStartDate());
				Calendar endDateCalendar = Calendar.getInstance(); endDateCalendar.setTime(rideRegistered.getEndDate());
				dateDiff = (endDateCalendar.getTimeInMillis() - startDateCalendar.getTimeInMillis()) / (1000*60*60*24);
			}
			if(this.recurring && dateDiff < 0) {
				errorMessage.add("Please select end date after the start date.");
				throw new ControllerException();
			}
			if(this.recurring && dateDiff > 60) {
				errorMessage.add("Recurring ride can be booked only for 2 months from the start date.");
				throw new ControllerException();
			}
			if(rideRegistered.getEndDate()!=null){
				cal1.setTime(rideRegistered.getEndDate());
				rideRegistered.setEnddateValue(dateFormat.format(cal1.getTime()));
			}
			rideRegistered.setStartdateValue( dateFormat.format(cal.getTime()));

			/*float startPointLat = rideRegistered.getStartPointLatitude();
			float startPointLng = rideRegistered.getStartPointLongitude();
			float endPointLat = rideRegistered.getEndPointLatitude();
			float endPointLng = rideRegistered.getEndPointLongitude();*/


			clearScreenMessage();
			if(frequencyDTO.getFrequency()==null || frequencyDTO.getFrequency().size() == 0){
				List<String> value= new ArrayList<String>();
				String putValue = "Once";
				value.add(putValue);
				frequencyDTO.setFrequency(value);
			}
			if(rideManager.equals("takeRide")){
				if(ListOfValuesManager.checkRideSeekerDuplicacy(rideRegistered)) {
					errorMessage.add("Same Ride already exist.");
					throw new ControllerException();
					//return "takeRide";
				} else {
					rideRegistered.setRideID(null);
					rideRegistered.setCreatedBy(userRegistrationDTO.getId());
					rideRegistered.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
					if(this.recurring) rideRegistered.setRecurring("Y"); else rideRegistered.setRecurring("N");
					
					/*
					 * For full day request make start point and end point same.
					 * */
					
					if(rideRegistered.getFullDay().equalsIgnoreCase("Y")) {
						rideRegistered.setEndPointLatitude(rideRegistered.getStartPointLatitude());
						rideRegistered.setEndPointLongitude(rideRegistered.getStartPointLongitude());
						rideRegistered.setToAddress1(rideRegistered.getFromAddress1());
						rideRegistered.setToAddressCity(rideRegistered.getFromAddressCity());
						rideRegistered.setToAddressPin(rideRegistered.getFromAddressPin());
						rideRegistered.setToAddressTag("");
					}
					
					List windowCalculation;
					try {
						windowCalculation = ApplicationUtil.calculateTimeWindowSettings(rideRegistered.getFromAddress1(), "", rideRegistered.getToAddress1(), userPreferences.getMaxWaitTime(), rideRegistered.getStartdateValue());

						if(windowCalculation.size() > 0) {
							rideRegistered.setStartdateValue(windowCalculation.get(1).toString());
							rideRegistered.setStartDateEarly(windowCalculation.get(1).toString());
							rideRegistered.setStartDateLate(windowCalculation.get(2).toString());
							rideRegistered.setEndDateEarly(windowCalculation.get(3).toString());
							rideRegistered.setEndDateLate(windowCalculation.get(4).toString());
							float distance = Integer.parseInt(windowCalculation.get(5).toString()) / 1000;
							rideRegistered.setRideDistance(distance);
							if(rideRegistered.isSharedTaxi()==true){
								System.out.println("inside the Rideaction Class:"+distance);
							rideRegistered.setRideCost(distance * Float.parseFloat(Messages.getValue("ride.perkm.charge").trim()) + "");
							System.out.println("value is printing in the RideAction class:"+Messages.getValue("ride.perkm.charge").trim() + "");
							}else{
								rideRegistered.setRideCost(distance
										* Float.parseFloat(Messages.getValue(
												"ride.perkm.sharecharge").trim()) + "");
								System.out.println("inside Rieaction classs amount 12:"+ Messages.getValue(
										"ride.perkm.sharecharge"));
							}
						}
					} catch (IOException e) { errorMessage.add("There is some problem in calculating time for ride."); throw new ControllerException(); } catch (JSONException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); errorMessage.add("There is some problem in calculating time for ride."); throw new ControllerException(); }

					//if no approver status = 1, if 1 approver status = O, if approver = 2 status = T
					//check hoponid exist in db or not.
					//If exist send email, sms and messageboard
					//If does not exist send email only
					//in email make link to approve.
					ApproverDTO approverDtoTemp = ListOfValuesManager.findApproverById(rideRegistered.getApproverID());
					if(rideRegistered.getApproverID() > 0) {
						if(approverDtoTemp.getHoponId() != null && approverDtoTemp.getHoponId().length() > 0) {
							rideRegistered.setStatus("O");
							if(approverDtoTemp.getHoponId2() != null && approverDtoTemp.getHoponId2().length() > 0) {
								rideRegistered.setStatus("T");
							}
						} 
					}				

					rideRegistered = ListOfValuesManager.getRideSeekerEntery("findByDTO", rideRegistered, con);

					if(rideRegistered.getApproverID() > 0) {						
						if(approverDtoTemp.getHoponId() != null && approverDtoTemp.getHoponId().length() > 0) {
							String approveLink = Messages.getValue("ride.approve", new Object[]{ rideRegistered.getRideID(), URLEncoder.encode(approverDtoTemp.getVerificationCode()), approverDtoTemp.getId(), approverDtoTemp.getHoponId()});
							String rejectLink = Messages.getValue("ride.reject", new Object[]{ rideRegistered.getRideID(), URLEncoder.encode(approverDtoTemp.getVerificationCode()), approverDtoTemp.getId(), approverDtoTemp.getHoponId()});
							String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "+approverDtoTemp.getbCode()+"<br>Name: "+userRegistrationDTO.getFirst_name() +"<br>Request ID: "+rideRegistered.getRideID()+"<br>From: "+rideRegistered.getFromAddress1()+"<br>To: "+rideRegistered.getToAddress1()+"<br>Date Time: "+rideRegistered.getStartdateValue()+"<br>Frequency: "+ frequencyDTO.getFrequency().toString()+"<br> "+approveLink+" &nbsp;&nbsp;"+rejectLink+"";
							if(ListOfValuesManager.testEmail(approverDtoTemp.getHoponId())) {
								UserRegistrationDTO dtoTemp = null;
								dtoTemp = ListOfValuesManager.findUserByEmail(approverDtoTemp.getHoponId());
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO.setMessage(messageContent); userMessageDTO.setEmailSubject(Messages.getValue("subject.ride.approval")); userMessageDTO.setMessageChannel("E"); userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getId()));
								userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO.setMessage(Messages.getValue("ride.option.msgBoard", new Object[]{approverDtoTemp.getbCode(),rideRegistered.getRideID(),userRegistrationDTO.getFirst_name(),rideRegistered.getFromAddress1(), rideRegistered.getToAddress1(), rideRegistered.getStartdateValue()})); userMessageDTO.setMessageChannel("M"); userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getId()));
								userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO.setMessage(Messages.getValue("ride.option.sms", new Object[]{approverDtoTemp.getbCode(),rideRegistered.getRideID(),userRegistrationDTO.getFirst_name(),rideRegistered.getFromAddress1().substring(0, ( rideRegistered.getFromAddress1().length() >= 20) ? 20 : rideRegistered.getFromAddress1().length()), rideRegistered.getToAddress1().substring(0, ( rideRegistered.getToAddress1().length() >= 20) ? 20 : rideRegistered.getToAddress1().length()), rideRegistered.getStartdateValue()})); userMessageDTO.setMessageChannel("S"); userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getId()));
								userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
							} else {
								EmailDTO emaildto = new EmailDTO();
								emaildto.setReceiverEmailId(approverDtoTemp.getHoponId());
								emaildto.setSenderEmailId(userRegistrationDTO.getEmail_id());
								emaildto.setSubject(Messages.getValue("subject.ride.approval"));
								emaildto.setEmailTemplateBody(Messages.getValue("email.template2", new Object[]{Messages.getValue("subject.ride.approval"), messageContent}));
								MailService.sendMail(emaildto);
							}
						} 
					}				

					frequencyDTO.setRideSeekerId(rideRegistered.getRideID());					
				}
			}

			if(rideManager.equals("giveRide")){
				if(ListOfValuesManager.checkRideDuplicacy(rideRegistered)) {
					errorMessage.add("Same Ride already exist.");
					//return "giveRide";
					throw new ControllerException();
				} else {
					rideRegistered.setRideID(null);
					rideRegistered.setCreatedBy(userRegistrationDTO.getId());
					rideRegistered.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
	
					List windoqCalculation;
					try {
						windoqCalculation = ApplicationUtil.calculateTimeWindowSettings(rideRegistered.getFromAddress1(), "", rideRegistered.getToAddress1(), userPreferences.getMaxWaitTime(), rideRegistered.getStartdateValue());
						System.out.println("window calculation is:"+windoqCalculation);
						if(windoqCalculation.size() > 0) {
							rideRegistered.setStartdateValue(windoqCalculation.get(1).toString());
							rideRegistered.setStartDateEarly(windoqCalculation.get(1).toString());
							rideRegistered.setStartDateLate(windoqCalculation.get(2).toString());
							rideRegistered.setEndDateEarly(windoqCalculation.get(3).toString());
							rideRegistered.setEndDateLate(windoqCalculation.get(4).toString());
							float distance = Integer.parseInt(windoqCalculation.get(5).toString()) / 1000;
							rideRegistered.setRideDistance(distance);
							rideRegistered.setRideCost((distance *5) + "");
						}
					} catch (IOException e) { errorMessage.add("There is some problem in calculating time for ride."); throw new ControllerException(); } catch (JSONException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); errorMessage.add("There is some problem in calculating time for ride."); throw new ControllerException(); }

					rideRegistered = ListOfValuesManager.getRideEntery("findByDTO", rideRegistered, con);
					frequencyDTO.setRideManagementId(rideRegistered.getRideID());
				}
			}

			frequencyDTO.setTime(rideRegistered.getStartDate());
			frequencyDTO.setStartDate(rideRegistered.getStartdateValue());
			frequencyDTO.setEndDate(rideRegistered.getEnddateValue());
			
			frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO", frequencyDTO, con);

			if(rideRegistered.getFromAddressTag().equalsIgnoreCase("new") && rideRegistered.getFromAddressTagLimit()!=null && rideRegistered.getFromAddressTagLimit()!=""){

				FavoritePlacesDTO dto = new FavoritePlacesDTO();

				dto.setAddress(rideRegistered.getFromAddress1());
				//dto.setRideID(rideRegistered.getRideID());
				dto.setUserID(userRegistrationDTO.getId());
				dto.setCity(rideRegistered.getFromAddressCity());
				dto.setPin(rideRegistered.getFromAddressPin());
				dto.setTagtype(rideRegistered.getFromAddressTagLimit());
				dto.setLatitude(rideRegistered.getStartPointLatitude());
				dto.setLongitude(rideRegistered.getStartPointLongitude());
				dto.setBoundtype("1");
				dto= ListOfValuesManager.getfavoritePlaces(dto, con);
				HttpSession currentSession = ServerUtility.getSession();
				currentSession.removeAttribute("allPlaces");
			}
			if(rideRegistered.getToAddressTag().equalsIgnoreCase("new") && rideRegistered.getToAddressTagLimit() !=null && rideRegistered.getToAddressTagLimit()!="" ){

				FavoritePlacesDTO dto = new FavoritePlacesDTO();

				dto.setAddress(rideRegistered.getToAddress1());
				//dto.setRideID(rideRegistered.getRideID());
				dto.setUserID(userRegistrationDTO.getId());
				dto.setCity(rideRegistered.getToAddressCity());
				dto.setPin(rideRegistered.getToAddressPin());
				dto.setTagtype(rideRegistered.getToAddressTagLimit());
				dto.setLatitude(rideRegistered.getEndPointLatitude());
				dto.setLongitude(rideRegistered.getEndPointLongitude());
				dto.setBoundtype("2");
				dto= ListOfValuesManager.getfavoritePlaces(dto, con);
				HttpSession currentSession = ServerUtility.getSession();
				currentSession.removeAttribute("allPlaces");
			}

		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
		} catch (ControllerException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
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
		rideRegistered.setSharedTaxi(false);
		
		rideRegistered = new RideManagementDTO();
		frequencyDTO = new FrequencyDTO();
		//rideManagementList();
		//return "rideRegistered";
	}
	public void clearNewRide(){
		rideRegistered = new RideManagementDTO();
		frequencyDTO = new FrequencyDTO();
		this.setRecurring(false);
		//return "clear"; 
	}
	public String allCompleateRideList(){
		allCompleateRideList = ListOfValuesManager.getAllPoolRequest(userRegistrationDTO.getId());
		
		allCompleateRideSeekerList = ListOfValuesManager.getAllPoolRequestSeeker(userRegistrationDTO.getId());
		/*
		 * for ride seeker status = '1' , is_result = 'Y'
		 * for ride giver status in '1', pool_request table 
		 * For taxi status = 'T',( should be in pool_request table)
		 * */
		

		allMsgBoardCompleateRideList.clear();
		for(PoolRequestsDTO dto:allCompleateRideList) {
			if(dto.getRideGiverNotes() != null && dto.getRideGiverNotes().length() > 0) {
				continue;
			}
			//if((dto.getRequestStatus().equalsIgnoreCase("T") && (userRegistrationDTO.getTravel().equalsIgnoreCase("P") || userRegistrationDTO.getTravel().equalsIgnoreCase("B"))) || ((userRegistrationDTO.getTravel().equalsIgnoreCase("T") && dto.getRequestStatus().equalsIgnoreCase("1")))) {
			if(!dto.getRequestStatus().equalsIgnoreCase("T") && userRegistrationDTO.getTravel().equalsIgnoreCase("T")) {
				continue;
			}
			if(!dto.getRequestStatus().equalsIgnoreCase("1") && userRegistrationDTO.getTravel().equalsIgnoreCase("P")) {
				continue;
			}
			if(!dto.getRequestStatus().equalsIgnoreCase("1") && userRegistrationDTO.getTravel().equalsIgnoreCase("B")) {
				continue;
			}
			allMsgBoardCompleateRideList.add(dto);
		}
		allMsgBoardCompleateRideSeekerList.clear();
		for(PoolRequestsDTO dto:allCompleateRideSeekerList) {
			if(dto.getRideTakerNotes() != null && dto.getRideTakerNotes().length() > 0) {
				continue;
			}
			allMsgBoardCompleateRideSeekerList.add(dto);
		}
		return "compleateList";
	}
	public String takeRide(){
		rideManager="takeRide";
		ridePicker = 1;
		return "takeRide";
	}
	public String giveRide(){

		rideManager="giveRide";
		ridePicker = 2;
		return "giveRide";
	}
	public String copyRideManager(){

		Connection con = (Connection) ListOfValuesManager.getBroadConnection();

		String rideManager = null;
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		rideManager = (String)requestMap.get("rideManagement");


		try {
			DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern3);
			Calendar cal = Calendar.getInstance();
			// Calendar cal1 = Calendar.getInstance();

			cal.setTime(rideRegistered.getStartDate());
			// cal1.setTime(rideRegistered.getEndDate());

			rideRegistered.setStartdateValue( dateFormat.format(cal.getTime()));
			// rideRegistered.setEnddateValue(dateFormat.format(cal1.getTime()));
			if(rideManager.equalsIgnoreCase("rideManagementFrompool")){
				rideRegistered.setRideID(null);
				rideRegistered.setUserID(userRegistrationDTO.getId());
				rideRegistered.setFromAddress1(poolRequestsDTO.getRideManagementFrom() );


				// Calendar cal1 = Calendar.getInstance();

				cal.setTime(rideRegistered.getStartDate());
				rideRegistered.setStartdateValue( dateFormat.format(cal.getTime()));
				String date =dateFormat.format(rideRegistered.getStartDate()) ;
				DateFormat dateFormat1 = new SimpleDateFormat(ApplicationUtil.datePattern3);
				try {
					rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
					rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
					rideRegistered.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
				} catch (ParseException e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
				}

				rideRegistered.setToAddress1(poolRequestsDTO.getRideManagementTO());
				rideRegistered = ListOfValuesManager.getRideEntery("findByDTO", rideRegistered, con);
				frequencyDTO.setTime(rideRegistered.getStartDate());
				List<String> list = new ArrayList<String>();
				list.add(poolRequestsDTO.getRideManagementFrequency());
				frequencyDTO.setFrequency(list);
				frequencyDTO.setRideManagementId(rideRegistered.getRideID());
				rideRegistered.setCreatedBy(rideRegistered.getCreatedBy() );
				/*for(int i = 0;i< vehicleMasterDTOList.size();i++) {
					if(vehicleMasterDTOList.get(i).getReg_NO().equalsIgnoreCase(rideRegistered.getVehicleID()) ){
						rideRegistered.setVehicleID(vehicleMasterDTOList.get(i).getVehicleID());
						break;
					}
				}*/

				
				frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO", frequencyDTO, con);
				rideManagementList();
				frequencyDTO = new FrequencyDTO();
				rideRegistered =  new RideManagementDTO();
				return "copyrideManager";
			}
			
			rideRegistered = ListOfValuesManager.getRideManagerPopupDataDirect(rideRegistered.getRideID());
			List<String> frequencyList = new ArrayList<String>();
			//list.add(rideRegistered.getFrequencyinweek());
			List<FrequencyDTO> freqDtos = new ArrayList<FrequencyDTO>();
			freqDtos.addAll(ListOfValuesManager.fetchFrequencyListForRideManager(rideRegistered.getRideID()));
			for(FrequencyDTO freqDto:freqDtos) frequencyList.addAll(freqDto.getFrequency());
			
			rideRegistered.setRideID(null);
			
			rideRegistered.setUserID(userRegistrationDTO.getId());


			// Calendar cal1 = Calendar.getInstance();

			cal.setTime(rideRegistered.getStartDate());
			String date =dateFormat.format(rideRegistered.getStartDate()) ;
			DateFormat dateFormat1 = new SimpleDateFormat(ApplicationUtil.datePattern3);
			try {
				rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
				rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
				rideRegistered.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
			
			List x1;
			try {
				x1 = ApplicationUtil.calculateTimeWindowSettings(rideRegistered.getFromAddress1(), "", rideRegistered.getToAddress1(), userPreferences.getMaxWaitTime(), date);

				/*TWstart_early = x1.get(1);
				TWstart_late = x1.get(2);
				TWend_early
				TWend_late
				ride_distance
				ride_cost = ride_distance * 5;
				 */
				if(x1.size() > 0) {
					rideRegistered.setStartdateValue(x1.get(1).toString());
					rideRegistered.setStartDateEarly(x1.get(1).toString());
					rideRegistered.setStartDateLate(x1.get(2).toString());
					rideRegistered.setEndDateEarly(x1.get(3).toString());
					rideRegistered.setEndDateLate(x1.get(4).toString());
					float distance = Integer.parseInt(x1.get(5).toString()) / 1000;
					rideRegistered.setRideDistance(distance);
					rideRegistered.setRideCost((distance * 5) + "");
				}

			} catch (IOException e) { } catch (JSONException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }

			rideRegistered.setStartPointLatitude(rideRegistered.getStartPointLatitude());
			rideRegistered.setStartPointLongitude(rideRegistered.getStartPointLongitude());
			rideRegistered.setEndPointLatitude(rideRegistered.getEndPointLatitude());
			rideRegistered.setEndPointLongitude(rideRegistered.getEndPointLongitude());
			rideRegistered.setViaPointLatitude(rideRegistered.getViaPointLatitude());
			rideRegistered.setViaPointLongitude(rideRegistered.getViaPointLongitude());
			
			
			for(int i = 0;i< vehicleMasterDTOList.size();i++) {
				if(vehicleMasterDTOList.get(i).getReg_NO().equalsIgnoreCase(rideRegistered.getVehicleID()) ){
					rideRegistered.setVehicleID(vehicleMasterDTOList.get(i).getVehicleID());
					break;
				}
			}

			rideRegistered = ListOfValuesManager.getRideEntery("findByDTO", rideRegistered, con);

			frequencyDTO.setTime(rideRegistered.getStartDate());
			
			frequencyDTO.setFrequency(frequencyList);
			frequencyDTO.setRideManagementId(rideRegistered.getRideID());
			try {
				frequencyDTO.setStartDate(freqDtos.get(0).getStartDate());
				frequencyDTO.setEndDate(freqDtos.get(0).getEndDate());
			} catch(NullPointerException e) {}
			frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO", frequencyDTO, con);
			rideManagementList();
			rideRegistered =  new RideManagementDTO();
			frequencyDTO = new FrequencyDTO();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
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

		return "copyrideManager";
	}

	public String copyRideSeeker(){
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		String rideSeeker = null;
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		rideSeeker = (String)requestMap.get("rideSeeker");


		try {
			DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern3);
			Calendar cal = Calendar.getInstance();
			// Calendar cal1 = Calendar.getInstance();
			cal.setTime(rideRegistered.getStartDate());
			//cal1.setTime(rideSeekerDTO.getEndDate());
			rideRegistered.setStartdateValue( dateFormat.format(cal.getTime()));
			// rideRegistered.setEnddateValue( dateFormat.format(cal1.getTime()));
			rideRegistered.setRecurring(rideSeekerDTO.getRecurring());
			rideRegistered.setSubSeekers(rideSeekerDTO.getSubSeekers());
			if(rideSeeker.equalsIgnoreCase("rideSeekerFrompool")){
				rideRegistered.setRideID(null);
				rideRegistered.setUserID(userRegistrationDTO.getId());
				rideRegistered.setFromAddress1(poolRequestsDTO.getRideSeekerFrom() );


				// Calendar cal1 = Calendar.getInstance();

				cal.setTime(rideRegistered.getStartDate());
				rideRegistered.setStartdateValue( dateFormat.format(cal.getTime()));
				String date =dateFormat.format(rideRegistered.getStartDate()) ;
				DateFormat dateFormat1 = new SimpleDateFormat(ApplicationUtil.datePattern3);
				try {
					rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
					rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
					rideRegistered.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
				} catch (ParseException e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
				}

				rideRegistered.setToAddress1(poolRequestsDTO.getRideSeekerTo());
				rideRegistered = ListOfValuesManager.getRideSeekerEntery("findByDTO", rideRegistered, con);
				frequencyDTO.setTime(rideRegistered.getStartDate());
				List<String> list = new ArrayList<String>();
				list.add(poolRequestsDTO.getRideSeekerFrequency());
				frequencyDTO.setFrequency(list);
				frequencyDTO.setRideSeekerId(rideRegistered.getRideID());
				rideRegistered.setCreatedBy(rideRegistered.getCreatedBy() );
				/*for(int i = 0;i< vehicleMasterDTOList.size();i++) {
					if(vehicleMasterDTOList.get(i).getReg_NO().equalsIgnoreCase(rideRegistered.getVehicleID()) ){
						rideRegistered.setVehicleID(vehicleMasterDTOList.get(i).getVehicleID());
						break;
					}
				}*/

				frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO", frequencyDTO, con);
				rideManagementList();
				frequencyDTO = new FrequencyDTO();
				rideRegistered =  new RideManagementDTO();
				return "copyrideSeeker";
			}

			rideSeekerDTO = ListOfValuesManager.getRideSeekerData(Integer.parseInt(rideSeekerDTO.getSeekerID()));
			List<String> frequencyList = new ArrayList<String>();
			//list.add(rideSeekerDTO.getFrequencyinweek());
			List<FrequencyDTO> freqDtos = new ArrayList<FrequencyDTO>();
			freqDtos.addAll(ListOfValuesManager.fetchFrequencyListForRideSeeker(rideSeekerDTO.getSeekerID()));
			for(FrequencyDTO freqDto:freqDtos) frequencyList.addAll(freqDto.getFrequency());
			
			rideRegistered.setRideID(null);
			/*rideRegistered.setStartDate(rideSeekerDTO.getStartDate());
		rideRegistered.setEndDate(rideSeekerDTO.getEndDate());*/
			rideRegistered.setUserID(userRegistrationDTO.getId());
			rideRegistered.setFromAddress1( rideSeekerDTO.getFromAddress1() );

			rideRegistered.setToAddress1( rideSeekerDTO.getToAddress1() );			
			cal.setTime(rideRegistered.getStartDate());
			String date =dateFormat.format(rideRegistered.getStartDate()) ;
			DateFormat dateFormat1 = new SimpleDateFormat(ApplicationUtil.datePattern3);
			try {
				rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
				rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
				rideRegistered.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
			List x1;
			try {
				x1 = ApplicationUtil.calculateTimeWindowSettings(rideSeekerDTO.getFromAddress1(), "", rideSeekerDTO.getToAddress1(), userPreferences.getMaxWaitTime(), rideRegistered.getStartdateValue());

				/*TWstart_early = x1.get(1);
				TWstart_late = x1.get(2);
				TWend_early
				TWend_late
				ride_distance
				ride_cost = ride_distance * 5;
				 */
				if(x1.size() > 0) {
					rideRegistered.setStartdateValue(x1.get(1).toString());
					rideRegistered.setStartDateEarly(x1.get(1).toString());
					rideRegistered.setStartDateLate(x1.get(2).toString());
					rideRegistered.setEndDateEarly(x1.get(3).toString());
					rideRegistered.setEndDateLate(x1.get(4).toString());
					float distance = Integer.parseInt(x1.get(5).toString()) / 1000;
					rideRegistered.setRideDistance(distance);
					rideRegistered.setRideCost((distance * 5) + "");
				}

			} catch (IOException e) { } catch (JSONException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
			// Calendar cal1 = Calendar.getInstance();

			

			rideRegistered.setCreatedBy(rideSeekerDTO.getCreatedBy() );
			rideRegistered.setStartPointLatitude(rideSeekerDTO.getStartPointLatitude());
			rideRegistered.setStartPointLongitude(rideSeekerDTO.getStartPointLongitude());
			rideRegistered.setEndPointLatitude(rideSeekerDTO.getEndPointLatitude());
			rideRegistered.setEndPointLongitude(rideSeekerDTO.getEndPointLongitude());
			rideRegistered.setViaPointLatitude(rideSeekerDTO.getViaPointLatitude());
			rideRegistered.setViaPointLongitude(rideSeekerDTO.getViaPointLongitude());
			
			for(int i = 0;i< vehicleMasterDTOList.size();i++) {
				if(vehicleMasterDTOList.get(i).getReg_NO().equalsIgnoreCase(rideRegistered.getVehicleID()) ){
					rideRegistered.setVehicleID(vehicleMasterDTOList.get(i).getVehicleID());
					break;
				}
			}
			rideRegistered = ListOfValuesManager.getRideSeekerEntery("findByDTO", rideRegistered, con);
			
			try {
				frequencyDTO.setStartDate(freqDtos.get(0).getStartDate());
				frequencyDTO.setEndDate(freqDtos.get(0).getEndDate());
			} catch(NullPointerException e) {}

			if(Validator.isNotEmpty(rideRegistered.getEndDate())) {
				frequencyDTO.setEndDate(ApplicationUtil.dateFormat3.format(rideRegistered.getEndDate()));
			}
			if(Validator.isNotEmpty(rideRegistered.getStartDate())) {
				frequencyDTO.setStartDate(ApplicationUtil.dateFormat3.format(rideRegistered.getStartDate()));
			}
			
			frequencyDTO.setTime(rideRegistered.getStartDate());
			
			frequencyDTO.setFrequency(frequencyList);
			frequencyDTO.setRideSeekerId(rideRegistered.getRideID());

			frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO", frequencyDTO, con);
			rideManagementList();
			rideRegistered =  new RideManagementDTO();
			frequencyDTO = new FrequencyDTO();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
		} finally {
			if(rollbackTest) {
				try { con.rollback(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			} else {
				try { con.commit(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			}
		}

		rollbackTest = false;
		return "copyrideSeeker";
	}

	public void makeRidePreMatchInactive() { ridePreMatchFormTest = false; }
	public void makeRideMatchInactive() { rideMatchFormTest = false; }
	public void clearMatchTripList() {
		rideSeekerDTO = new RideSeekerDTO();
		matchedTripByConditionList.clear();
		matchedTripDataModel= new MatchedTripDataModel();
	}
	public void clearCombineVehicleSearch() {
		rideManagementDTO = new RideManagementDTO();
		combineVehicleCondition.clear();
		combineVehicleDataModel = new CombineVehicleDataModel();
		circleDTO = new CircleDTO();
		this.setRideIdToDrop(0); this.setRideIdToTake(0);
	}
	public void combineVehicleSearch() {
		String date1 ="";
		DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern1);
		//SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern2);
		//date = formatter1.parse(dto1.getStartDate());
		if(rideManagementDTO.getStartDate()!=null){
			date1 = dateFormat.format(rideManagementDTO.getStartDate());
		}
		combineVehicleCondition.clear();
		combineVehicleCondition = ListOfValuesManager.getAllCombineVehicleList(rideManagementDTO.getFromAddress1(), rideManagementDTO.getToAddress1(),date1, circleDTO.getCircleID());
		
		combineVehicleDataModel = new CombineVehicleDataModel(combineVehicleCondition);
	}
	
	public void combineVehicle() {
		clearScreenMessage();
		if(rideIdToDrop <= 0) errorMessage.add("Please select ride to drop.");
		if(rideIdToTake <= 0) errorMessage.add("Please select ride to Combine.");
		if(rideIdToTake == rideIdToDrop && rideIdToTake > 0) errorMessage.add("Please select different rides to drop and Combine.");
		
		if(errorMessage.size() == 0) {
			//populate drop and take ride.
			RideManagementDTO rideToDrop = ListOfValuesManager.getRideManagerPopupDataDirect(rideIdToDrop+"");
			RideManagementDTO rideToTake = ListOfValuesManager.getRideManagerPopupDataDirect(rideIdToTake+"");
			if(!Validator.isNumberZeroNotAlloed(rideToDrop.getRideID())) {
				errorMessage.add("Ride you want to drop does not exist.");
			}
			if(!Validator.isNumberZeroNotAlloed(rideToTake.getRideID())) {
				errorMessage.add("Ride you want to combine does not exist.");
			}
			if(!ApplicationUtil.dateFormat1.format(rideToDrop.getStartDate()).equals(ApplicationUtil.dateFormat1.format(rideToTake.getStartDate()))) {
				errorMessage.add("Select Ride of same date you want to combine and drop.");
			}

			//Make validation that both rides start and end point synchronized.
			//Make validation that both rides time synchronized.
			
			if(errorMessage.size() == 0) {
				Connection con = (Connection)ListOfValuesManager.getBroadConnection();
				try {
					rideToDrop.setStatus("I");
					ListOfValuesManager.getCancleRide(rideToDrop, con);
					//Update rideId in pool request and ride seeker table.
					ListOfValuesManager.updateRideIdDropTake(rideToDrop, rideToTake, con);
					//Send message to ride user that ride cancelled.
					
					
					UserRegistrationDTO userDto = new UserRegistrationDTO();
					userDto = ListOfValuesManager.findDriverDtoByRideId(rideToDrop.getRideID(), con);
					
					UserRegistrationDTO userDtoRide = new UserRegistrationDTO();
					userDtoRide = ListOfValuesManager.getUserById(Integer.parseInt(rideToDrop.getUserID()));
					
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages.getValue("sms.cancel.driver.admin", new Object[]{ userDto.getFirst_name(), rideToDrop.getRideID(),(rideToDrop.getFromAddress1().length() > 25) ? rideToDrop.getFromAddress1().substring(0, 25) : rideToDrop.getFromAddress1(),(rideToDrop.getToAddress1().length() > 25) ? rideToDrop.getToAddress1().substring(0, 25) : rideToDrop.getToAddress1(), rideToDrop.getStartdateValue()}));
					userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setRideId(Integer.parseInt(rideToDrop.getRideID()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideToDrop.getStartdateValue() }));
					userMessageDTO.setMessage(Messages.getValue("ridegiver.driver.cancel", new Object[]{ userDto.getFirst_name(),rideToDrop.getRideID(),rideToDrop.getFromAddress1(),rideToDrop.getToAddress1(),rideToDrop.getStartdateValue(),userDtoRide.getMobile_no()}));
					userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setRideId(Integer.parseInt(rideToDrop.getRideID()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);


					//Send message to ride user that new ride assigned.
					userDto = ListOfValuesManager.findDriverDtoByRideId(rideToTake.getRideID(), con);
					userDtoRide = ListOfValuesManager.getUserById(Integer.parseInt(rideToTake.getUserID()));
					
					
					
					allSeekerForGivenRide = ListOfValuesManager.getAllRideSeekerForAGivenRide(rideToDrop.getRideID());
					if(allSeekerForGivenRide.size()>0){
						for(int index=0;index<allSeekerForGivenRide.size();index++){
							UserRegistrationDTO seekerUserDto = ListOfValuesManager.getUserById(Integer.parseInt(rideToTake.getUserID()));
							
							
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages.getValue("subject.match", new Object[]{ rideToTake.getStartDate() }));
							userMessageDTO.setMessage(Messages.getValue("ridematched.driver", new Object[]{
									userDto.getFirst_name(),rideToDrop.getRideID(),rideToTake.getFromAddress1(),rideToTake.getToAddress1(),
									rideToTake.getStartDate(),seekerUserDto.getFirst_name() + " - " + seekerUserDto.getMobile_no()}));
							userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

							
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setMessage(Messages.getValue("sms.match.driver", new Object[]{ 
								allSeekerForGivenRide.get(index).getFromAddress1(),
								allSeekerForGivenRide.get(index).getToAddress1(),
								seekerUserDto.getFirst_name(),
								allSeekerForGivenRide.get(index).getStartdateValue(),
								seekerUserDto.getMobile_no(),
								rideToTake.getRideID(),
								userDto.getFirst_name()
							}));
							userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
							
							userMessageDTO = new MessageBoardDTO();

							userMessageDTO.setEmailSubject("Your ride has been changed");
							userMessageDTO.setMessage(Messages.getValue("ridematched.seeker", new Object[]{
									allSeekerForGivenRide.get(index).getUserName(), 
									userDto.getFirst_name(),
									rideToTake.getRideID(),
									allSeekerForGivenRide.get(index).getFromAddress1(),
									allSeekerForGivenRide.get(index).getToAddress1(),
									allSeekerForGivenRide.get(index).getStartdateValue(), 
									rideToTake.getVehicleRegno(),
									userDto.getMobile_no()}));
							userMessageDTO.setToMember(Integer.parseInt(allSeekerForGivenRide.get(index).getUserID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
							
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setMessage("Your ride has been changed."+Messages.getValue("sms.match", new Object[]{ 
								allSeekerForGivenRide.get(index).getFromAddress1(),
								allSeekerForGivenRide.get(index).getToAddress1(),
								seekerUserDto.getFirst_name(),
								allSeekerForGivenRide.get(index).getStartdateValue(),
								userDto.getMobile_no(),
								rideToTake.getRideID(),
								allSeekerForGivenRide.get(index).getUserName(), 
								rideToTake.getVehicleRegno(), 
								allSeekerForGivenRide.get(index).getSeekerID()
							}));
							userMessageDTO.setToMember(Integer.parseInt(allSeekerForGivenRide.get(index).getUserID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

						}
					}
				} catch (ConfigurationException e) { rollbackTest = true; } catch(NullPointerException e1) { rollbackTest = true; } finally {
					if(rollbackTest) {
						try { con.rollback(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
						ListOfValuesManager.releaseConnection(con);
						errorMessage.add("There is some problem in operation.");
						successMessage.clear();
					} else {
						try { con.commit(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
						ListOfValuesManager.releaseConnection(con);
						successMessage.add("Vehicle combined successfully.");
					}
					rollbackTest = false;
				}
			}
			
		}
		clearCombineVehicleSearch();
	}
	
	public String matchedTripListByCondition(){
		rideMatchFormTest = true;
		String date1 ="";
		DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern1);
		//SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern2);
		//date = formatter1.parse(dto1.getStartDate());
		if(rideSeekerDTO.getStartDate()!=null){
			date1 = dateFormat.format(rideSeekerDTO.getStartDate());
		}
		matchedTripByConditionList.clear();
		matchedTripByConditionList = ListOfValuesManager.getAllMatchedListByCondition(rideSeekerDTO.getFromAddress1(), rideSeekerDTO.getToAddress1(),date1, circleDTO.getCircleID());
		
		

		Map<String, Integer> group = new HashMap<String, Integer>();
		for(MatchedTripDTO dto:matchedTripByConditionList) {
			if(group.size() > 0 && group.containsKey(dto.getGroupId())) {
				group.put(dto.getGroupId(), group.get(dto.getGroupId()) + 1);
			} else {
				group.put(dto.getGroupId(), 1);
			}
		}
		for(int i=0;i<matchedTripByConditionList.size();i++) {
			matchedTripByConditionList.get(i).setMemberCount(""+group.get(matchedTripByConditionList.get(i).getGroupId()));
		}

		matchedTripDataModel = new MatchedTripDataModel(matchedTripByConditionList);

		return "matchedTrip";
	}
	public String rideMatchedTripListByCondition(){
		ridePreMatchFormTest = true;
		String date1 ="";
		DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern1);
		//SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern2);
		//date = formatter1.parse(dto1.getStartDate());
		if(rideSeekerDTO.getStartDate()!=null){
			date1 = dateFormat.format(rideSeekerDTO.getStartDate());
		}
		matchedTripByConditionList.clear();
		if(circleDTO.getCircleID() > 0) {
			matchedTripByConditionList = ListOfValuesManager.getAllMatchedListByCondition(rideSeekerDTO.getFromAddress1(), rideSeekerDTO.getToAddress1(),date1, circleDTO.getCircleID());
						
			List<MatchedTripDTO> dtoTemp = new ArrayList<MatchedTripDTO>();
			for(int i=0;i<matchedTripByConditionList.size();i++) {
				if(!matchedTripByConditionList.get(i).isFullDay() && !matchedTripByConditionList.get(i).isRecurring()) {
					dtoTemp.add(matchedTripByConditionList.get(i));
				}
			}
			matchedTripByConditionList.clear();
			matchedTripByConditionList.addAll(dtoTemp);
			
		} else {
			clearScreenMessage();
			errorMessage.add("Please Select affiliated circle.");
		}
		

		Map<String, Integer> group = new HashMap<String, Integer>();
		for(MatchedTripDTO dto:matchedTripByConditionList) {
			if(group.size() > 0 && group.containsKey(dto.getGroupId())) {
				group.put(dto.getGroupId(), group.get(dto.getGroupId()) + 1);
			} else {
				group.put(dto.getGroupId(), 1);
			}
		}
		for(int i=0;i<matchedTripByConditionList.size();i++) {
			matchedTripByConditionList.get(i).setMemberCount(""+group.get(matchedTripByConditionList.get(i).getGroupId()));
		}

		matchedTripDataModel = new MatchedTripDataModel(matchedTripByConditionList);

		return "matchedTrip";
	}
	
	public String matchRideForCompany() {
		if(manageRideFormDTO.getRideDate()!=null && !manageRideFormDTO.getRideDate().equals("")){
			SimpleDateFormat df1 = new SimpleDateFormat(ApplicationUtil.datePattern12);
			SimpleDateFormat df2 = new SimpleDateFormat(ApplicationUtil.datePattern1);
			try {
				Date date = df1.parse(manageRideFormDTO.getRideDate());
				manageRideFormDTO.setRideDate(df2.format(date));
			}
			catch (ParseException e) { 
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());}
		}

		String rideOption = manageRideFormDTO.getRideOption();
		clearMatchRideForCompany();
		clearScreenMessage();
		
		if(manageRideFormDTO.getMyCircleId() > 0) {
			if(rideOption.equals("pending")) {
				manageRideDTOs.addAll(ListOfValuesManager.findPendingManageRide(manageRideFormDTO));
				
			} else if(rideOption.equals("matchedPending")) {
				manageRideDTOs.addAll(ListOfValuesManager.findPendingMatchedManageRide(manageRideFormDTO));
				
			} else if(rideOption.equals("completed")) {
				manageRideDTOs.addAll(ListOfValuesManager.findCompletedManageRide(manageRideFormDTO));
				
			} else {
				errorMessage.add("Please select type of rides you want to search.");
			}
		} else {
			errorMessage.add("Please select your circle.");
		}

		//manageRideFormDTO = new ManageRideFormDTO();
		return "matchedTrip";
	}
	public void clearMatchRideForCompany() {
		if(manageRideDTOs.size() > 0) manageRideDTOs.clear();
	}
	public String matchRideCancel() {
		if(matchRideCancelParam != null && matchRideCancelParam.length() > 0) {
			String[] x1 = matchRideCancelParam.split("@");
			for(String temp1:x1) {
				String x2[] = temp1.split("-");
				if(x2.length >= 2) {
					int rideId = Integer.parseInt(x2[0]);
					String role = x2[1];
					if(role.equalsIgnoreCase("giver")) {
						rideRegistered = ListOfValuesManager.getRideManagerPopupDataDirect(""+rideId);
						
						cancleRideManager();
					} else if(role.equalsIgnoreCase("taker")) {
						rideSeekerDTO = ListOfValuesManager.getRideSeekerData(rideId);
						
						cancleRideSeeker();
					}
				}
			}
		}
		matchRideCancelParam = "";

		matchRideForCompany();

		return "";
	}

	public void approveOrRejectRide() {
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		String approve = (String)requestMap.get("approve");
		String decline = (String)requestMap.get("decline");
		String seekerId = (String)requestMap.get("seekerId");
		String newStatus = "";
		RideSeekerDTO dtoTemp = new RideSeekerDTO();
		if(approve != null) {
			for(RideSeekerDTO dto:allRideApprovalRequest) {
				if(dto.getSeekerID().equals(seekerId) && dto.getFirstApproverEmailId().equals(userRegistrationDTO.getEmail_id())) {
					newStatus = "O";
					if(Validator.isEmpty(dto.getSecondApproverEmailId())) {
						newStatus = "A";
					}
					dtoTemp = dto;
					break;
				} else if(dto.getSeekerID().equals(seekerId) && dto.getSecondApproverEmailId().equals(userRegistrationDTO.getEmail_id())) {
					newStatus = "A";
					dtoTemp = dto;
					break;
				}
			}
		} else if(decline != null) {
			for(RideSeekerDTO dto:allRideApprovalRequest) {
				if(dto.getSeekerID().equals(seekerId)) {
					newStatus = "I";
					dtoTemp = dto;
					break;
				}
			}
		}
		if(!Validator.isEmpty(newStatus)) {
			ListOfValuesManager.changeStatus(Integer.parseInt(seekerId), newStatus);
			ApproverDTO dto = ListOfValuesManager.findApproverById(dtoTemp.getApproverID());
			frequencyDTO = ListOfValuesManager.fetchFrequencyListForRideSeeker(dtoTemp.getSeekerID()).get(0);
			String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "+dto.getbCode()+"<br>Name: "+dtoTemp.getUserName() +"<br>Request ID: "+dtoTemp.getSeekerID()+"<br>From: "+dtoTemp.getFromAddress1()+"<br>To: "+dtoTemp.getToAddress1()+"<br>Date Time: "+dtoTemp.getStartdateValue()+"<br>Frequency: "+ frequencyDTO.getFrequency().toString();
			if(newStatus.equalsIgnoreCase("O")) {	
				String approveLink = Messages.getValue("ride.approve", new Object[]{ dtoTemp.getSeekerID(), URLEncoder.encode(dto.getVerificationCode2()), dto.getId(), dto.getHoponId2()});
				String rejectLink = Messages.getValue("ride.reject", new Object[]{ dtoTemp.getSeekerID(), URLEncoder.encode(dto.getVerificationCode2()), dto.getId(), dto.getHoponId2()});
				
				if(!Validator.isEmpty(dtoTemp.getSecondApproverEmailId())) {
					messageContent += "<br> "+approveLink+" &nbsp;&nbsp;"+rejectLink;
					if(ListOfValuesManager.testEmail(dtoTemp.getSecondApproverEmailId())) {
						UserRegistrationDTO userTemp = null;
						userTemp = ListOfValuesManager.findUserByEmail(dtoTemp.getSecondApproverEmailId());
						MessageBoardDTO userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(messageContent); userMessageDTO.setEmailSubject("Ride Request for Approval"); userMessageDTO.setMessageChannel("E"); userMessageDTO.setToMember(Integer.parseInt(userTemp.getId()));
						userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
						userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(Messages.getValue("ride.option.msgBoard", new Object[]{dto.getbCode(),dtoTemp.getSeekerID(),dtoTemp.getUserName(),dtoTemp.getFromAddress1(), dtoTemp.getToAddress1(), dtoTemp.getStartdateValue()})); userMessageDTO.setMessageChannel("M"); userMessageDTO.setToMember(Integer.parseInt(userTemp.getId()));
						userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
						userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(Messages.getValue("ride.option.sms", new Object[]{dto.getbCode(),dtoTemp.getSeekerID(),dtoTemp.getUserName(),dtoTemp.getFromAddress1().substring(0, ( dtoTemp.getFromAddress1().length() >= 20) ? 20 : dtoTemp.getFromAddress1().length()), dtoTemp.getToAddress1().substring(0, ( dtoTemp.getToAddress1().length() >= 20) ? 20 : dtoTemp.getToAddress1().length()), dtoTemp.getStartdateValue()})); userMessageDTO.setMessageChannel("S"); userMessageDTO.setToMember(Integer.parseInt(userTemp.getId()));
						userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
					} else {
						EmailDTO emaildto = new EmailDTO();
						emaildto.setReceiverEmailId(dto.getHoponId2());
						emaildto.setSubject("Ride Request for Approval"); 
						emaildto.setEmailTemplateBody(Messages.getValue("email.template2", new Object[]{"", "", messageContent, "", "", "", ""}));
						MailService.sendMail(emaildto);
					}
				}
			} else if(newStatus.equalsIgnoreCase("A")) {
				MessageBoardDTO userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(messageContent); userMessageDTO.setEmailSubject("Ride request approved"); 
				userMessageDTO.setMessageChannel("E"); 
				userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("ride.option.approved", new Object[]{dto.getbCode(),dtoTemp.getSeekerID(),dtoTemp.getUserName(),dtoTemp.getFromAddress1(), dtoTemp.getToAddress1(), dtoTemp.getStartdateValue()})); userMessageDTO.setMessageChannel("M"); userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("ride.option.approved", new Object[]{dto.getbCode(),dtoTemp.getSeekerID(),dtoTemp.getUserName(),dtoTemp.getFromAddress1(), dtoTemp.getToAddress1(), dtoTemp.getStartdateValue()})); userMessageDTO.setMessageChannel("S"); userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
			} else if(newStatus.equalsIgnoreCase("I")) {
				MessageBoardDTO userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(messageContent); userMessageDTO.setEmailSubject("Ride request rejected"); 
				userMessageDTO.setMessageChannel("E"); 
				userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("ride.option.rejected", new Object[]{dto.getbCode(),dtoTemp.getSeekerID(),dtoTemp.getUserName(),dtoTemp.getFromAddress1(), dtoTemp.getToAddress1(), dtoTemp.getStartdateValue()})); userMessageDTO.setMessageChannel("M"); userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("ride.option.rejected", new Object[]{dto.getbCode(),dtoTemp.getSeekerID(),dtoTemp.getUserName(),dtoTemp.getFromAddress1(), dtoTemp.getToAddress1(), dtoTemp.getStartdateValue()})); userMessageDTO.setMessageChannel("S"); userMessageDTO.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
			}
		}
		messageForLoginUser();
	}

	public String cancleRideManager(){
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		try {
			rideRegistered.setStatus("I");//rideRegistered.setStatus("0");rideRegistered.setStatus("I");
			rideRegistered = ListOfValuesManager.getCancleRide(rideRegistered, con);
			ListOfValuesManager.changePoolRequestStatusForRideGiver(con, Integer.parseInt(rideRegistered.getRideID()));
			rideRegistered = ListOfValuesManager.getRideManagerPopupDataDirect(rideRegistered.getRideID());

			UserRegistrationDTO userDto = new UserRegistrationDTO();
			userDto = ListOfValuesManager.findDriverDtoByRideId(rideRegistered.getRideID(), con);
			
			UserRegistrationDTO userDtoRide = new UserRegistrationDTO();
			userDtoRide = ListOfValuesManager.getUserById(Integer.parseInt(rideRegistered.getUserID()));
			
			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setMessage(Messages.getValue("sms.cancel.driver.admin", new Object[]{ userDto.getFirst_name(), rideRegistered.getRideID(),(rideRegistered.getFromAddress1().length() > 25) ? rideRegistered.getFromAddress1().substring(0, 25) : rideRegistered.getFromAddress1(),(rideRegistered.getToAddress1().length() > 25) ? rideRegistered.getToAddress1().substring(0, 25) : rideRegistered.getToAddress1(), rideRegistered.getStartdateValue()}));
			userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
			userMessageDTO.setRideId(Integer.parseInt(rideRegistered.getRideID()));
			userMessageDTO.setMessageChannel("S");
			userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideRegistered.getStartdateValue() }));
			userMessageDTO.setMessage(Messages.getValue("ridegiver.driver.cancel", new Object[]{ userDto.getFirst_name(),rideRegistered.getRideID(),rideRegistered.getFromAddress1(),rideRegistered.getToAddress1(),rideRegistered.getStartdateValue(),userDtoRide.getMobile_no()}));
			userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO.getId()));
			userMessageDTO.setRideId(Integer.parseInt(rideRegistered.getRideID()));
			userMessageDTO.setMessageChannel("E");
			userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

			allSeekerForGivenRide = ListOfValuesManager.getAllRideSeekerForAGivenRide(rideRegistered.getRideID());
			if(allSeekerForGivenRide.size()>0){
				for(int index=0;index<allSeekerForGivenRide.size();index++){
					String id =	allSeekerForGivenRide.get(index).getSeekerID();
					rideSeekerDTO.setStatus("I");
					rideSeekerDTO.setSeekerID(id);
					rideSeekerDTO = ListOfValuesManager.getCancleRideSeeker(rideSeekerDTO, con);


					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages.getValue("sms.cancel.passenger.admin", new Object[]{allSeekerForGivenRide.get(index).getUserName(),rideRegistered.getRideID(),(allSeekerForGivenRide.get(index).getFromAddress1().length() > 25) ? allSeekerForGivenRide.get(index).getFromAddress1().substring(0, 25) : allSeekerForGivenRide.get(index).getFromAddress1(),(allSeekerForGivenRide.get(index).getToAddress1().length() > 25) ? allSeekerForGivenRide.get(index).getToAddress1().substring(0, 25) : allSeekerForGivenRide.get(index).getToAddress1(),		allSeekerForGivenRide.get(index).getStartdateValue(),userRegistrationDTO.getMobile_no()	}));
					userMessageDTO.setToMember(Integer.parseInt(allSeekerForGivenRide.get(index).getUserID()));
					userMessageDTO.setRideId(Integer.parseInt(allSeekerForGivenRide.get(index).getSeekerID()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ allSeekerForGivenRide.get(index).getStartdateValue() }));
					userMessageDTO.setMessage(Messages.getValue("ridegiver.seeker.cancel", new Object[]{ allSeekerForGivenRide.get(index).getUserName(),rideRegistered.getRideID(),allSeekerForGivenRide.get(index).getFromAddress1(),allSeekerForGivenRide.get(index).getToAddress1(),allSeekerForGivenRide.get(index).getStartdateValue(),}));
					userMessageDTO.setToMember(Integer.parseInt(allSeekerForGivenRide.get(index).getUserID()));
					userMessageDTO.setRideId(Integer.parseInt(allSeekerForGivenRide.get(index).getSeekerID()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

				}
			}
			rideManagementList();
			userMessageDTO = new MessageBoardDTO();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
		} finally {
			if(rollbackTest) {
				try { con.rollback(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			} else {
				try { con.commit(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			}
		}

		rollbackTest = false;
		return "cancle";
	}

	public String cancleRideSeeker(){
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		try {
			rideSeekerDTO.setStatus("I");
			rideSeekerDTO = ListOfValuesManager.getCancleRideSeeker(rideSeekerDTO, con);
			ListOfValuesManager.changePoolRequestStatusForRideGiver(con, Integer.parseInt(rideSeekerDTO.getSeekerID()));
			rideSeekerDTO = ListOfValuesManager.getRideSeekerData(Integer.parseInt(rideSeekerDTO.getSeekerID()));

			if(rideSeekerDTO.getIsResult().equalsIgnoreCase("N")) {
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setMessage(Messages.getValue("rideSeeker.unmatched.cancel", new Object[]{ userRegistrationDTO.getFirst_name(),rideSeekerDTO.getSeekerID(),rideSeekerDTO.getFromAddress1(),rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue()}));
				userMessageDTO.setToMember(Integer.parseInt(rideSeekerDTO.getUserID()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
				
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("sms.rideSeeker.unmatched.cancel", new Object[]{ userRegistrationDTO.getFirst_name(),rideSeekerDTO.getSeekerID(),(rideSeekerDTO.getFromAddress1().length() > 25) ? rideSeekerDTO.getFromAddress1().substring(0, 25) : rideSeekerDTO.getFromAddress1(),(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO.getToAddress1().substring(0, 25) : rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue()}));
				userMessageDTO.setToMember(Integer.parseInt(rideSeekerDTO.getUserID()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
			} else {
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("sms.cancel", new Object[]{userRegistrationDTO.getFirst_name(),(rideSeekerDTO.getFromAddress1().length() > 25) ? rideSeekerDTO.getFromAddress1().substring(0, 25) : rideSeekerDTO.getFromAddress1(),(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO.getToAddress1().substring(0, 25) : rideSeekerDTO.getToAddress1(),ListOfValuesManager.getcurrentDate(ApplicationUtil.datePattern4),userRegistrationDTO.getMobile_no(),rideSeekerDTO.getRideMatchRideId()}));
				userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setMessage(Messages.getValue("rideSeeker.matched.cancel", new Object[]{ userRegistrationDTO.getFirst_name(),rideSeekerDTO.getRideMatchRideId(),rideSeekerDTO.getFromAddress1(),rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue()}));
				userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

				/* Now message should go to driver. */
				UserRegistrationDTO userDto = new UserRegistrationDTO();
				userDto = ListOfValuesManager.findDriverDtoByRideId(rideSeekerDTO.getRideMatchRideId(), con);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("sms.cancel.driver", new Object[]{userDto.getFirst_name(),userRegistrationDTO.getFirst_name(),rideSeekerDTO.getRideMatchRideId(),(rideSeekerDTO.getFromAddress1().length() > 25) ? rideSeekerDTO.getFromAddress1().substring(0, 25) : rideSeekerDTO.getFromAddress1(),(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO.getToAddress1().substring(0, 25) : rideSeekerDTO.getToAddress1(),ListOfValuesManager.getcurrentDate(ApplicationUtil.datePattern4),userRegistrationDTO.getMobile_no()}));
				userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setMessage(Messages.getValue("driver.match.cancel", new Object[]{ userDto.getFirst_name(),userRegistrationDTO.getFirst_name(),rideSeekerDTO.getRideMatchRideId(),rideSeekerDTO.getFromAddress1(),rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue(),userDto.getMobile_no(),Messages.getValue("customer.support")}));
				userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
			}
			userMessageDTO = new MessageBoardDTO();
			
			if(rideSeekerDTO.getRecurring().equalsIgnoreCase("Y") && this.allowRecurringSubRideToCancel) {
				String[] rides = rideSeekerDTO.getSubSeekers().split(",");
				if(rides.length > 0) {
					rideSeekerDTO.setStatus("I");
					rideSeekerDTO = ListOfValuesManager.cancelSubSeekers(con, rideSeekerDTO);
					rides = rideSeekerDTO.getSubSeekers().split(",");
					for(int i = 0; i < rides.length; i++) {
						ListOfValuesManager.changePoolRequestStatusForRideGiver(con, Integer.parseInt(rides[i]));
						rideSeekerDTO = ListOfValuesManager.getRideSeekerData(Integer.parseInt(rides[i]));

						if(rideSeekerDTO.getIsResult().equalsIgnoreCase("N")) {
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideSeekerDTO.getStartdateValue() }));
							userMessageDTO.setMessage(Messages.getValue("rideSeeker.unmatched.cancel", new Object[]{ userRegistrationDTO.getFirst_name(),rideSeekerDTO.getSeekerID(),rideSeekerDTO.getFromAddress1(),rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue()}));
							userMessageDTO.setToMember(Integer.parseInt(rideSeekerDTO.getUserID()));
							userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
							
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setMessage(Messages.getValue("sms.rideSeeker.unmatched.cancel", new Object[]{ userRegistrationDTO.getFirst_name(),rideSeekerDTO.getSeekerID(),(rideSeekerDTO.getFromAddress1().length() > 25) ? rideSeekerDTO.getFromAddress1().substring(0, 25) : rideSeekerDTO.getFromAddress1(),(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO.getToAddress1().substring(0, 25) : rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue()}));
							userMessageDTO.setToMember(Integer.parseInt(rideSeekerDTO.getUserID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
						} else {
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setMessage(Messages.getValue("sms.cancel", new Object[]{userRegistrationDTO.getFirst_name(),(rideSeekerDTO.getFromAddress1().length() > 25) ? rideSeekerDTO.getFromAddress1().substring(0, 25) : rideSeekerDTO.getFromAddress1(),(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO.getToAddress1().substring(0, 25) : rideSeekerDTO.getToAddress1(),ListOfValuesManager.getcurrentDate(ApplicationUtil.datePattern4),userRegistrationDTO.getMobile_no(),rideSeekerDTO.getRideMatchRideId()}));
							userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideSeekerDTO.getStartdateValue() }));
							userMessageDTO.setMessage(Messages.getValue("rideSeeker.matched.cancel", new Object[]{ userRegistrationDTO.getFirst_name(),rideSeekerDTO.getRideMatchRideId(),rideSeekerDTO.getFromAddress1(),rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue()}));
							userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

							/* Now message should go to driver. */
							UserRegistrationDTO userDto = new UserRegistrationDTO();
							userDto = ListOfValuesManager.findDriverDtoByRideId(rideSeekerDTO.getRideMatchRideId(), con);
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setMessage(Messages.getValue("sms.cancel.driver", new Object[]{userDto.getFirst_name(),userRegistrationDTO.getFirst_name(),rideSeekerDTO.getRideMatchRideId(),(rideSeekerDTO.getFromAddress1().length() > 25) ? rideSeekerDTO.getFromAddress1().substring(0, 25) : rideSeekerDTO.getFromAddress1(),(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO.getToAddress1().substring(0, 25) : rideSeekerDTO.getToAddress1(),ListOfValuesManager.getcurrentDate(ApplicationUtil.datePattern4),userRegistrationDTO.getMobile_no()}));
							userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
							userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel", new Object[]{ rideSeekerDTO.getStartdateValue() }));
							userMessageDTO.setMessage(Messages.getValue("driver.match.cancel", new Object[]{ userDto.getFirst_name(),userRegistrationDTO.getFirst_name(),rideSeekerDTO.getRideMatchRideId(),rideSeekerDTO.getFromAddress1(),rideSeekerDTO.getToAddress1(),rideSeekerDTO.getStartdateValue(),userDto.getMobile_no(),Messages.getValue("customer.support")}));
							userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
							userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
						}
					}
				}
			}
			
			rideManagementList();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
		} finally {
			if(rollbackTest) {
				try { con.rollback(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			} else {
				try { con.commit(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
				ListOfValuesManager.releaseConnection(con);
			}
		}
		rollbackTest = false;
		return "cancle";
	}

	public void rateAndWriteNotes(){
		String taker = null;
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		taker = (String)requestMap.get("taker");
		if(taker==null){
			taker = "giver";
		}
		clearScreenMessage();
		poolRequestsDTO.setMasterControl(taker);
		poolRequestsDTO = ListOfValuesManager.getRateAndWriteNotes(poolRequestsDTO);
		successMessage.add("Ride successfully rated.");

		userMessageDTO = new MessageBoardDTO();
		userMessageDTO.setEmailSubject(Messages.getValue("subject.rate.ride"));
		userMessageDTO.setMessage(Messages.getValue("body.rate.ride", new Object[]{ 
				userRegistrationDTO.getFirst_name(),
				poolRequestsDTO.getRateRideGiver()
		}));
		userMessageDTO.setToMember(Integer.parseInt(poolRequestsDTO.getUser_id()));
		if(poolRequestsDTO.getRideSeekerID() != null) userMessageDTO.setRideId(Integer.parseInt(poolRequestsDTO.getRideSeekerID()));
		userMessageDTO.setMessageChannel("E");
		userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
		
		rollbackTest = false;
		userMessageDTO = new MessageBoardDTO();
		allCompleateRideList();
		//return "notes";
	}

	public void recurringValue(AjaxBehaviorEvent event)throws AbortProcessingException {
		if(recurring==true){
			recurring=false;
		}
		if(recurring==false){
			recurring=true;
		}
	}

	public String matchTripLinking(){
		clearScreenMessage();
		if(ridePreMatchTest > 0) {
			List<String> groupMap = new ArrayList<String>();
			List<String> rideIdArr = new ArrayList<String>();
			for(MatchedTripDTO dto:matchedTripDTOs) {
				if(dto.getGroupId() != null && dto.getGroupId().length() > 0) groupMap.add(dto.getGroupId());
				if(!rideIdArr.contains(dto.getSeekerID())) {
					rideIdArr.add(dto.getSeekerID());
				}
			}

			List<MatchedTripDTO> temp1 = ListOfValuesManager.findMatchTripByGroupId(groupMap);
			int count1 = 0;
			for(MatchedTripDTO dto:temp1) {
				if(!rideIdArr.contains(dto.getSeekerID())) {
					count1++;
				}
			}
			int count2 = 0;
			MatchedTripDTO temp2[] = new MatchedTripDTO[count1 + matchedTripDTOs.length];
			for(int i =0;i<temp1.size();i++) {
				if(!rideIdArr.contains(temp1.get(i).getSeekerID())) {
					temp2[count2] = temp1.get(i);
					count2++;
				}
			}
			for(int i =0;i<matchedTripDTOs.length;i++) {
				temp2[i+count2] = matchedTripDTOs[i];
			}
			matchedTripDTOs = temp2;
		}
		ridePreMatchTest = 0;

		Map<String, Integer> groupCountTemp = new HashMap<String, Integer>();
		Map<String, String> groupRideIdTemp = new HashMap<String, String>();
		for(int i=0;i<matchedTripDTOs.length;i++){
			if(matchedTripDTOs[i].getGroupId() != null && matchedTripDTOs[i].getGroupId().length() > 0 && matchedTripDTOs[i].getCountTemp() != null) {
				groupCountTemp.put(matchedTripDTOs[i].getGroupId(), Integer.parseInt(matchedTripDTOs[i].getCountTemp()));
				groupRideIdTemp.put(matchedTripDTOs[i].getGroupId(), matchedTripDTOs[i].getRideId());
			}
		}
		for(int i=0;i<matchedTripDTOs.length;i++){
			if(groupCountTemp.containsKey(matchedTripDTOs[i].getGroupId()) && (matchedTripDTOs[i].getCountTemp() == null || matchedTripDTOs[i].getCountTemp().length() == 0)) {
				groupCountTemp.put(matchedTripDTOs[i].getGroupId(), groupCountTemp.get(matchedTripDTOs[i].getGroupId()) - 1);
				matchedTripDTOs[i].setCountTemp(""+groupCountTemp.get(matchedTripDTOs[i].getGroupId()));
			}
		}
		for(int i=0;i<matchedTripDTOs.length;i++){
			if(groupRideIdTemp.containsKey(matchedTripDTOs[i].getGroupId())) {
				matchedTripDTOs[i].setRideId(groupRideIdTemp.get(matchedTripDTOs[i].getGroupId()));
			}
		}

		//check concurrency here.
		List<Integer> seekerIdForConcurrency = new ArrayList<Integer>();
		//Map<String, String> seekerArrayForConcurrency = new HashMap<String, String>();
		for(MatchedTripDTO dtoTemp1:matchedTripDTOs) {
			seekerIdForConcurrency.add(Integer.parseInt(dtoTemp1.getSeekerID()));
			//seekerArrayForConcurrency.put(dtoTemp1.getSeekerID(), );
		}
		List<RideSeekerDTO> seekerDtoForConcurrency = ListOfValuesManager.findRideSeekerDataByIds(seekerIdForConcurrency);

		boolean test = true;
		if(seekerDtoForConcurrency == null || seekerDtoForConcurrency.size() != matchedTripDTOs.length) {
			test = false;			
		} else {
			for(RideSeekerDTO dtoTest:seekerDtoForConcurrency) {
				if((!dtoTest.getStatus().equalsIgnoreCase("A") || !dtoTest.getIsResult().equalsIgnoreCase("N"))) {
					if(dtoTest.isNightRide() && (dtoTest.getStatus().equalsIgnoreCase("O") || dtoTest.getStatus().equalsIgnoreCase("T"))) {
						continue;
					} 
					test = false;
					break;
				}
			}
		}

		if(!test) {
			errorMessage.add("Some error has occured. Please try again after sometime.");
		} else {
			Connection con = (Connection) ListOfValuesManager.getBroadConnection();
			try {
			for(int i=0;i<matchedTripDTOs.length;i++){
				matchedTripDTOs[i].setCount(matchedTripDTOs[i].getCountTemp());
				MatchedTripDTO dto = new MatchedTripDTO();
				RideManagementDTO rideManagementDTO = new RideManagementDTO();
				PoolRequestsDTO poolDTO = new PoolRequestsDTO();
				FrequencyDTO freqDTO = new FrequencyDTO();
				//FrequencyDTO frequencyDTO = new FrequencyDTO();
				dto=matchedTripDTOs[i];
				if(dto.getRideId().equalsIgnoreCase("NEW")){
					rideManagementDTO.setFromAddress1(dto.getStartPoint());
					rideManagementDTO.setToAddress1(dto.getEndPoint());
					rideManagementDTO.setUserID(userRegistrationDTO.getId());
					rideManagementDTO.setVehicleID(dto.getVehicleID());
					rideManagementDTO.setStatus("T");//previously 2
					SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
					DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern9);
					try {
						Date date =dateFormat.parse(dto.getStartDate());
						rideManagementDTO.setStartdateValue(formatter.format(date));
						date = formatter.parse(formatter.format(date));
						freqDTO.setTime(date);
					} catch (ParseException e1) {
						LoggerSingleton.getInstance().error(e1.getStackTrace()[0].getClassName()+"->"+e1.getStackTrace()[0].getMethodName()+"() : "+e1.getStackTrace()[0].getLineNumber()+" :: "+e1.getMessage());
					}

					rideManagementDTO.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
					rideManagementDTO.setFlexiTimeAfter(ListOfValuesManager.getCurrentTimeStampDate());
					rideManagementDTO.setFlexiTimeBefore(ListOfValuesManager.getCurrentTimeStampDate());
					rideManagementDTO.setCreatedBy(userRegistrationDTO.getId());

					List<String> frequency = new ArrayList<String>();
					frequency.add(dto.getFrequency());
					freqDTO.setFrequency(frequency);

					rideManagementDTO = ListOfValuesManager.getRideEntery("findByDTO", rideManagementDTO, con);
					freqDTO.setRideManagementId(rideManagementDTO.getRideID());
					freqDTO=ListOfValuesManager.getFrequencyEntery("findByDTO", freqDTO, con);

				} else {

					if(Integer.valueOf(dto.getCount()) == 0){
						return null;
					} else {
						rideManagementDTO.setRideID(dto.getRideId());
						rideManagementDTO.setStatus("I");//rideManagementDTO.setStatus("2");rideManagementDTO.setStatus("I");
						rideManagementDTO = ListOfValuesManager.getCancleRide(rideManagementDTO, con);
						rideManagementDTO = ListOfValuesManager.getRideManagerPopupDataDirect(dto.getRideId());

					}
				}
				

				/*
				 * ride_seeker_id is the passenger's ride id. ride_seeker_id -> ride_seeker_details -> seeker_id -> ride_seeker_id -> user_id
				 * ride_id is the ride id for driver. ride_id -> ride_management -> ride_id -> user_id
				 * */
				RideSeekerDTO seekerDtoTemp = ListOfValuesManager.getRideSeekerData(Integer.parseInt(dto.getSeekerID()));
				//RideManagementDTO managementDtoTemp = ListOfValuesManager.getRideManagerPopupData(rideManagementDTO.getRideID());
				//RideManagementDTO managementDtoTemp2 = ListOfValuesManager.getRideManagerPopupData(dto.getSeekerID());

				poolDTO.setRidemanagerID(rideManagementDTO.getRideID());
				poolDTO.setRideSeekerID(dto.getSeekerID());
				poolDTO.setPoolRequestToId(Integer.parseInt(rideManagementDTO.getUserID()));
				poolDTO.setPoolRequestBy(Integer.parseInt(seekerDtoTemp.getUserID()));
				poolDTO = ListOfValuesManager.getInsertInPool(poolDTO, con);
				
				
				RideManagementDTO managementDtoTemp = new RideManagementDTO();
				managementDtoTemp.setRideID(rideManagementDTO.getRideID());
				managementDtoTemp.setUserID(rideManagementDTO.getUserID());
				managementDtoTemp.setVehicleID(rideManagementDTO.getVehicleID());
				managementDtoTemp.setFromAddress1(rideManagementDTO.getFromAddress1());
				managementDtoTemp.setToAddress1(rideManagementDTO.getToAddress1());
				managementDtoTemp.setStartdateValue(rideManagementDTO.getStartdateValue());
				managementDtoTemp.setCreatedBy(rideManagementDTO.getCreatedBy());
				managementDtoTemp.setStatus(rideManagementDTO.getStatus());

				List<Integer> x = new ArrayList<Integer>();
				x.add(Integer.parseInt(seekerDtoTemp.getUserID()));
				x.add(Integer.parseInt(managementDtoTemp.getUserID()));
				List<UserRegistrationDTO> dtosTemp = ListOfValuesManager.getAllUserById(x);

				UserRegistrationDTO seekerUserDto = null;
				UserRegistrationDTO managerUserDto = null;
				for(UserRegistrationDTO di:dtosTemp) {
					if(di.getId().equals(seekerDtoTemp.getUserID())) {
						seekerUserDto = di;
					} else if(di.getId().equals(managementDtoTemp.getUserID())) {
						managerUserDto = di;
					}					
				}

				
				UserRegistrationDTO userDto = new UserRegistrationDTO();
				VehicleMasterDTO vehicleDto1 = new VehicleMasterDTO();
				if(Validator.isEmpty(rideManagementDTO.getVehicleID()) || rideManagementDTO.getVehicleID().equals("0")) {
					userDto = ListOfValuesManager.findUserDtoByVehicleId(Integer.parseInt(rideManagementDTO.getVehicleID()), con);
					vehicleDto1 = ListOfValuesManager.getVehicleDtoById(matchedTripDTOs[i].getVehicleID());
				} else {
					userDto = ListOfValuesManager.findDriverDtoByRideId(rideManagementDTO.getRideID(), con);
					vehicleDto1 = ListOfValuesManager.getVehicleDtoById(rideManagementDTO.getVehicleID());
				}
				
				
				userMessageDTO = new MessageBoardDTO();

				userMessageDTO.setEmailSubject(Messages.getValue("subject.match", new Object[]{ dto.getStartDate() }));
				userMessageDTO.setMessage(Messages.getValue("ridematched.seeker", new Object[]{seekerUserDto.getFirst_name(), userDto.getFirst_name(),rideManagementDTO.getRideID(),dto.getStartPoint(),dto.getEndPoint(),dto.getStartDate(), vehicleDto1.getReg_NO(),userDto.getMobile_no()}));
				userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp.getUserID()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setEmailSubject(Messages.getValue("subject.match", new Object[]{ dto.getStartDate() }));
				userMessageDTO.setMessage(Messages.getValue("ridematched.driver", new Object[]{
						userDto.getFirst_name(),rideManagementDTO.getRideID(),dto.getStartPoint(),dto.getEndPoint(),
						dto.getStartDate(),seekerUserDto.getFirst_name() + " - " + seekerUserDto.getMobile_no()}));
				userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);



				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("sms.match", new Object[]{ 
				dto.getStartPoint(),
				dto.getEndPoint(),
				managerUserDto.getFirst_name(),
				dto.getStartDate(),
				managerUserDto.getMobile_no(),
				managementDtoTemp.getRideID(),
				seekerDtoTemp.getUserName(), vehicleDto1.getReg_NO(), dto.getSeekerID()
				}));
				userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp.getUserID()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("sms.match.driver", new Object[]{ 
				seekerDtoTemp.getFromAddress1(),
				seekerDtoTemp.getToAddress1(),
				seekerUserDto.getFirst_name(),
				seekerDtoTemp.getStartdateValue(),
				seekerUserDto.getMobile_no(),
				managementDtoTemp.getRideID(),
				userDto.getFirst_name()
				}));
				userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
				
				

				//vehicleMasterDTO=ListOfValuesManager.getUpdateSeat(vehicleMasterDTO);
				/*RideSeekerDTO dtoSeeker = new RideSeekerDTO();
				dtoSeeker.setSeekerID(dto.getSeekerID());
				dtoSeeker.setStatus("I");
				dtoSeeker= ListOfValuesManager.getCancleRideSeeker(dtoSeeker, con);//use for update 
				 */
				RideSeekerDTO dtoSeeker = new RideSeekerDTO();
				dtoSeeker.setSeekerID(dto.getSeekerID());
				dtoSeeker.setRideMatchRideId(rideManagementDTO.getRideID());
				dtoSeeker.setIsResult("Y");
				dtoSeeker = ListOfValuesManager.changeField(dtoSeeker, con);
			}
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			rollbackTest = true;
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
		}
		
		rideManagementList();
		allCompleateRideList();
		vehicleList();
		//matchedTripListByCondition();

		matchedTripByConditionList.clear();
		matchedTripDataModel= new MatchedTripDataModel();
		vehicleMasterDTO = new VehicleMasterDTO();
		rideManagementListForPopupOne.clear();
		userMessageDTO = new MessageBoardDTO();
		return "matched";
	}

	public String ridePreMatchTripLinking(){
		clearScreenMessage();
		Map<String, List<Integer>> groupRideComb = new HashMap<String, List<Integer>>();
		Map<String, Integer> groupRideSingle = new HashMap<String, Integer>();
		Map<String, MatchedTripDTO> groupMatchTrip = new HashMap<String, MatchedTripDTO>();
		Map<String, Integer> groupMatchTest = new HashMap<String, Integer>();
		if(ridePreMatchTest > 0) {
			Map<String, String> groupVehicleMap = new HashMap<String, String>();
			Map<String, String> groupCountMap = new HashMap<String, String>();
			Map<String, String> groupCountTempMap = new HashMap<String, String>();
			Map<String, String> groupRideIdMap = new HashMap<String, String>();
			for(MatchedTripDTO dto:matchedTripDTOs) {
				if(dto.isSelectGroup() && !Validator.isEmpty(dto.getVehicleID())) {
					groupVehicleMap.put(dto.getGroupId(), dto.getVehicleID());
				}
				if(dto.isSelectGroup() && !Validator.isEmpty(dto.getCount())) {
					groupCountMap.put(dto.getGroupId(), dto.getCount());
				}
				if(dto.isSelectGroup() && !Validator.isEmpty(dto.getCountTemp())) {
					groupCountTempMap.put(dto.getGroupId(), dto.getCountTemp());
				}
				if(dto.isSelectGroup() && !Validator.isEmpty(dto.getRideId())) {
					groupRideIdMap.put(dto.getGroupId(), dto.getRideId());
				}
			}
			for(int i = 0; i < matchedTripDTOs.length; i++) {
				if(groupVehicleMap.containsKey(matchedTripDTOs[i].getGroupId())) {
					matchedTripDTOs[i].setVehicleID(groupVehicleMap.get(matchedTripDTOs[i].getGroupId()));
				}
				if(groupCountMap.containsKey(matchedTripDTOs[i].getGroupId())) {
					matchedTripDTOs[i].setCount(groupCountMap.get(matchedTripDTOs[i].getGroupId()));
				}
				if(groupCountTempMap.containsKey(matchedTripDTOs[i].getGroupId())) {
					matchedTripDTOs[i].setCountTemp(groupCountTempMap.get(matchedTripDTOs[i].getGroupId()));
					groupCountTempMap.put(matchedTripDTOs[i].getGroupId(), "" + (Integer.parseInt(groupCountTempMap.get(matchedTripDTOs[i].getGroupId())) - 1));
				}
				if(groupRideIdMap.containsKey(matchedTripDTOs[i].getGroupId())) {
					matchedTripDTOs[i].setRideId(groupRideIdMap.get(matchedTripDTOs[i].getGroupId()));
				}
			}
			/*List<String> groupMap = new ArrayList<String>();
			List<String> rideIdArr = new ArrayList<String>();
			//Here we are adding group to array.
			for(MatchedTripDTO dto:matchedTripDTOs) {
				if(dto.isSelectGroup() && dto.getGroupId() != null && dto.getGroupId().length() > 0) groupMap.add(dto.getGroupId());
				if(!rideIdArr.contains(dto.getSeekerID())) {
					rideIdArr.add(dto.getSeekerID());
				}
			}
			//Here we are fetching rest ride seeker data by group for matchTripDTOs.
			
			List<MatchedTripDTO> temp1 = ListOfValuesManager.findMatchTripByGroupId(groupMap);
			int count1 = 0;
			for(MatchedTripDTO dto:temp1) {
				if(!rideIdArr.contains(dto.getSeekerID())) {
					count1++;
				}
			}
			int count2 = 0;
			MatchedTripDTO temp2[] = new MatchedTripDTO[count1 + matchedTripDTOs.length];
			for(int i =0;i<temp1.size();i++) {
				//if(!rideIdArr.contains(temp1.get(i).getSeekerID())) 
					temp2[count2] = temp1.get(i);
					count2++;
			}
			for(int i =0;i<matchedTripDTOs.length;i++) {
				if(!matchedTripDTOs[i].isSelectGroup()) temp2[i+count2] = matchedTripDTOs[i];
			}
			matchedTripDTOs = temp2;*/
			
			//Now matchedTripDTOs has all ride seeker data group wise.

			for(MatchedTripDTO dto:matchedTripDTOs) {
				if(groupRideComb.containsKey(dto.getGroupId())) {
					List<Integer> rideIdTemp = new ArrayList<Integer>();
					rideIdTemp.add(Integer.parseInt(dto.getSeekerID()));
					rideIdTemp.addAll(groupRideComb.get(dto.getGroupId()));
					groupRideComb.put(dto.getGroupId(), rideIdTemp);
				} else {
					List<Integer> rideIdTemp = new ArrayList<Integer>();
					rideIdTemp.add(Integer.parseInt(dto.getSeekerID()));
					groupRideComb.put(dto.getGroupId(), rideIdTemp);
				}
				groupMatchTest.put(dto.getGroupId(), 0);
			}
			for(String group:groupRideComb.keySet()) {
				int rideForGroup = ListOfValuesManager.calculateSingleRide(groupRideComb.get(group));
				groupRideSingle.put(group, rideForGroup);
			}
			for(String group:groupRideSingle.keySet()) {
				String tempSeekerId = groupRideSingle.get(group) + "";
				for(MatchedTripDTO dto:matchedTripDTOs) {
					if(dto.getGroupId().equalsIgnoreCase(group) && dto.getSeekerID().equalsIgnoreCase(tempSeekerId)) {
						groupMatchTrip.put(group, dto);
						break;
					}
				}
			}
		}
		ridePreMatchTest = 0;

		Map<String, Integer> groupCountTemp = new HashMap<String, Integer>();
		Map<String, String> groupRideIdTemp = new HashMap<String, String>();
		for(int i=0;i<matchedTripDTOs.length;i++){
			if(matchedTripDTOs[i].getGroupId() != null && matchedTripDTOs[i].getGroupId().length() > 0 && matchedTripDTOs[i].getCountTemp() != null) {
				groupCountTemp.put(matchedTripDTOs[i].getGroupId(), Integer.parseInt(matchedTripDTOs[i].getCountTemp()));
				groupRideIdTemp.put(matchedTripDTOs[i].getGroupId(), matchedTripDTOs[i].getRideId());
			}
		}
		for(int i=0;i<matchedTripDTOs.length;i++){
			if(groupCountTemp.containsKey(matchedTripDTOs[i].getGroupId()) && (matchedTripDTOs[i].getCountTemp() == null || matchedTripDTOs[i].getCountTemp().length() == 0)) {
				groupCountTemp.put(matchedTripDTOs[i].getGroupId(), groupCountTemp.get(matchedTripDTOs[i].getGroupId()) - 1);
				matchedTripDTOs[i].setCountTemp(""+groupCountTemp.get(matchedTripDTOs[i].getGroupId()));
			}
		}
		for(int i=0;i<matchedTripDTOs.length;i++){
			if(groupRideIdTemp.containsKey(matchedTripDTOs[i].getGroupId())) {
				matchedTripDTOs[i].setRideId(groupRideIdTemp.get(matchedTripDTOs[i].getGroupId()));
			}
		}

		//check concurrency here.
		List<Integer> seekerIdForConcurrency = new ArrayList<Integer>();
		//Map<String, String> seekerArrayForConcurrency = new HashMap<String, String>();
		for(MatchedTripDTO dtoTemp1:matchedTripDTOs) {
			seekerIdForConcurrency.add(Integer.parseInt(dtoTemp1.getSeekerID()));
			//seekerArrayForConcurrency.put(dtoTemp1.getSeekerID(), );
		}
		List<RideSeekerDTO> seekerDtoForConcurrency = ListOfValuesManager.findRideSeekerDataByIds(seekerIdForConcurrency);

		boolean test = true;
		if(seekerDtoForConcurrency == null || seekerDtoForConcurrency.size() != matchedTripDTOs.length) {
			test = false;			
		} else {
			for(RideSeekerDTO dtoTest:seekerDtoForConcurrency) {
				if((!dtoTest.getStatus().equalsIgnoreCase("A") || !dtoTest.getIsResult().equalsIgnoreCase("N"))) {
					if(dtoTest.isNightRide() && (dtoTest.getStatus().equalsIgnoreCase("O") || dtoTest.getStatus().equalsIgnoreCase("T"))) {
						continue;
					} 
					test = false;
					break;
				}
			}
		}
				
		if(!test) {
			errorMessage.add("Some error has occured. Please try again after sometime.");
		} else {
			//uncomment for sending mail in combined way.
			//Map<Integer, List<Integer>> driverMessageList = new HashMap<Integer, List<Integer>>();
			Connection con = (Connection) ListOfValuesManager.getBroadConnection();
			try {
				for(int i=0;i<matchedTripDTOs.length;i++){
					matchedTripDTOs[i].setCount(matchedTripDTOs[i].getCountTemp());
					MatchedTripDTO dto = new MatchedTripDTO();
					RideManagementDTO rideManagementDTO = new RideManagementDTO();
					PoolRequestsDTO poolDTO = new PoolRequestsDTO();
					FrequencyDTO freqDTO = new FrequencyDTO();
					//FrequencyDTO frequencyDTO = new FrequencyDTO();
					dto=matchedTripDTOs[i];
					MatchedTripDTO dtoNew = groupMatchTrip.get(dto.getGroupId());
					if(dto.getRideId().equalsIgnoreCase("NEW")){
	
						rideManagementDTO.setFromAddress1(dtoNew.getStartPoint());
						rideManagementDTO.setToAddress1(dtoNew.getEndPoint());
						rideManagementDTO.setUserID(userRegistrationDTO.getId());
						rideManagementDTO.setVehicleID(dtoNew.getVehicleID());
						rideManagementDTO.setStatus("T");//previously 2
						SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
						DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern9);
						try {
							Date date =dateFormat.parse(dtoNew.getStartDate());
							rideManagementDTO.setStartdateValue(formatter.format(date));
							date = formatter.parse(formatter.format(date));
							freqDTO.setTime(date);
						} catch (ParseException e1) {
							LoggerSingleton.getInstance().error(e1.getStackTrace()[0].getClassName()+"->"+e1.getStackTrace()[0].getMethodName()+"() : "+e1.getStackTrace()[0].getLineNumber()+" :: "+e1.getMessage());
						}
	
						rideManagementDTO.setCreated_dt(ListOfValuesManager.getCurrentTimeStampDate());
						rideManagementDTO.setFlexiTimeAfter(ListOfValuesManager.getCurrentTimeStampDate());
						rideManagementDTO.setFlexiTimeBefore(ListOfValuesManager.getCurrentTimeStampDate());
						rideManagementDTO.setCreatedBy(userRegistrationDTO.getId());
	
						List<String> frequency = new ArrayList<String>();
						frequency.add(dtoNew.getFrequency());
						freqDTO.setFrequency(frequency);
						if(groupMatchTest.get(dto.getGroupId()) == 0) {
							rideManagementDTO = ListOfValuesManager.getRideEntery("findByDTO", rideManagementDTO, con);
							freqDTO.setRideManagementId(rideManagementDTO.getRideID());
							freqDTO=ListOfValuesManager.getFrequencyEntery("findByDTO", freqDTO, con);
							groupMatchTest.put(dto.getGroupId(), Integer.parseInt(rideManagementDTO.getRideID()));
						} else {
							rideManagementDTO.setRideID(groupMatchTest.get(dto.getGroupId()) + "");
							freqDTO.setRideManagementId(groupMatchTest.get(dto.getGroupId()) + "");
						}
	
					} else {
	
						if(Integer.valueOf(dto.getCount()) == 0){
							return null;
						} else {
							rideManagementDTO.setRideID(dto.getRideId());
							rideManagementDTO.setStatus("I");//rideManagementDTO.setStatus("2");rideManagementDTO.setStatus("I");
							rideManagementDTO = ListOfValuesManager.getCancleRide(rideManagementDTO, con);
							rideManagementDTO = ListOfValuesManager.getRideManagerPopupDataDirect(dto.getRideId());
						}
					}
					
					
					/*//uncomment for sending mail in combined way.
					 * if(driverMessageList.containsKey(poolDTO.getRidemanagerID())) {
						driverMessageList.get(poolDTO.getRidemanagerID()).add(Integer.parseInt(poolDTO.getRideSeekerID()));
					}*/
	
					/*
					 * ride_seeker_id is the passenger's ride id. ride_seeker_id -> ride_seeker_details -> seeker_id -> ride_seeker_id -> user_id
					 * ride_id is the ride id for driver. ride_id -> ride_management -> ride_id -> user_id
					 * */
					RideSeekerDTO seekerDtoTemp = ListOfValuesManager.getRideSeekerData(Integer.parseInt(dto.getSeekerID()));
					//RideManagementDTO managementDtoTemp = ListOfValuesManager.getRideManagerPopupData(rideManagementDTO.getRideID());
					//RideManagementDTO managementDtoTemp2 = ListOfValuesManager.getRideManagerPopupData(dto.getSeekerID());
	
					poolDTO.setRidemanagerID(rideManagementDTO.getRideID());
					poolDTO.setRideSeekerID(dto.getSeekerID());
					poolDTO.setPoolRequestToId(Integer.parseInt(rideManagementDTO.getUserID()));
					poolDTO.setPoolRequestBy(Integer.parseInt(seekerDtoTemp.getUserID()));
					poolDTO = ListOfValuesManager.getInsertInPool(poolDTO, con);
					
					
					RideManagementDTO managementDtoTemp = new RideManagementDTO();
					managementDtoTemp.setRideID(rideManagementDTO.getRideID());
					managementDtoTemp.setUserID(rideManagementDTO.getUserID());
					managementDtoTemp.setVehicleID(rideManagementDTO.getVehicleID());
					managementDtoTemp.setFromAddress1(rideManagementDTO.getFromAddress1());
					managementDtoTemp.setToAddress1(rideManagementDTO.getToAddress1());
					managementDtoTemp.setStartdateValue(rideManagementDTO.getStartdateValue());
					managementDtoTemp.setCreatedBy(rideManagementDTO.getCreatedBy());
					managementDtoTemp.setStatus(rideManagementDTO.getStatus());
	
					List<Integer> x = new ArrayList<Integer>();
					x.add(Integer.parseInt(seekerDtoTemp.getUserID()));
					x.add(Integer.parseInt(managementDtoTemp.getUserID()));
					List<UserRegistrationDTO> dtosTemp = ListOfValuesManager.getAllUserById(x);
	
					UserRegistrationDTO seekerUserDto = null;
					UserRegistrationDTO managerUserDto = null;
					for(UserRegistrationDTO di:dtosTemp) {
						if(di.getId().equals(seekerDtoTemp.getUserID())) {
							seekerUserDto = di;
						} else if(di.getId().equals(managementDtoTemp.getUserID())) {
							managerUserDto = di;
						}					
					}
					
					UserRegistrationDTO userDto = new UserRegistrationDTO();
					VehicleMasterDTO vehicleDto1 = new VehicleMasterDTO();
					if(Validator.isEmpty(rideManagementDTO.getVehicleID()) || rideManagementDTO.getVehicleID().equals("0")) {
						userDto = ListOfValuesManager.findDriverDtoByRideId(rideManagementDTO.getRideID(), con);
					} else {
						userDto = ListOfValuesManager.findUserDtoByVehicleId(Integer.parseInt(rideManagementDTO.getVehicleID()), con);
					}
					vehicleDto1 = ListOfValuesManager.getVehicleDtoById(rideManagementDTO.getVehicleID());
					
					userMessageDTO = new MessageBoardDTO();
	
					userMessageDTO.setEmailSubject(Messages.getValue("subject.match", new Object[]{ dto.getStartDate() }));
					userMessageDTO.setMessage(Messages.getValue("ridematched.seeker", new Object[]{
							seekerUserDto.getFirst_name(), userDto.getFirst_name(),
							rideManagementDTO.getRideID(),dto.getStartPoint(),dto.getEndPoint(),dto.getStartDate(), 
							vehicleDto1.getReg_NO(),userDto.getMobile_no()}));
					userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp.getUserID()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
	
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setEmailSubject(Messages.getValue("subject.match", new Object[]{ dto.getStartDate() }));
					userMessageDTO.setMessage(Messages.getValue("ridematched.driver", new Object[]{
							userDto.getFirst_name(),rideManagementDTO.getRideID(),
							dto.getStartPoint(),dto.getEndPoint(),dto.getStartDate(),
							seekerUserDto.getFirst_name() + " - " + seekerUserDto.getMobile_no()}));
					userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
	
	
	
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages.getValue("sms.match", new Object[]{ 
						(dto.getStartPoint().length() > 25) ? dto.getStartPoint().substring(0, 25) : dto.getStartPoint(),
						(dto.getEndPoint().length() > 25) ? dto.getEndPoint().substring(0, 25) : dto.getEndPoint(),
						managerUserDto.getFirst_name(),
						dto.getStartDate(),
						managerUserDto.getMobile_no(),
						managementDtoTemp.getRideID(),
						seekerDtoTemp.getUserName(),vehicleDto1.getReg_NO(), dto.getSeekerID()
					}));
					userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp.getUserID()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
	
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages.getValue("sms.match.driver", new Object[]{ 
						(seekerDtoTemp.getFromAddress1().length() > 25) ? seekerDtoTemp.getFromAddress1().substring(0, 25) : seekerDtoTemp.getFromAddress1(),
						(seekerDtoTemp.getToAddress1().length() > 25) ? seekerDtoTemp.getToAddress1().substring(0, 25) : seekerDtoTemp.getToAddress1(),
						seekerUserDto.getFirst_name(),
						seekerDtoTemp.getStartdateValue(),
						seekerUserDto.getMobile_no(),
						managementDtoTemp.getRideID(),
						userDto.getFirst_name(),vehicleDto1.getReg_NO()
					}));
					userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
					//vehicleMasterDTO=ListOfValuesManager.getUpdateSeat(vehicleMasterDTO);
					/*RideSeekerDTO dtoSeeker = new RideSeekerDTO();
					dtoSeeker.setSeekerID(dto.getSeekerID());
					dtoSeeker.setStatus("I");
					dtoSeeker= ListOfValuesManager.getCancleRideSeeker(dtoSeeker, con);//use for update 
					*/	
					RideSeekerDTO dtoSeeker = new RideSeekerDTO();
					dtoSeeker.setSeekerID(dto.getSeekerID());
					dtoSeeker.setRideMatchRideId(rideManagementDTO.getRideID());
					dtoSeeker.setIsResult("Y");
					dtoSeeker = ListOfValuesManager.changeField(dtoSeeker, con);
				} 
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
				rollbackTest = true;
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
		}
		rideManagementList();
		allCompleateRideList();
		vehicleList();
		//matchedTripListByCondition();

		matchedTripByConditionList.clear();
		matchedTripDataModel= new MatchedTripDataModel();
		vehicleMasterDTO = new VehicleMasterDTO();
		rideManagementListForPopupOne.clear();
		userMessageDTO = new MessageBoardDTO();
		return "matched";
	}

	public void processValue(AjaxBehaviorEvent event) throws AbortProcessingException {
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		rowForTaxiLinking = (String)requestMap.get("row");
		String startDate = (String)requestMap.get("startDate");
		matchedTripDTO.setStartDate(startDate);
		String value = (String) ((UIInput) event.getSource()).getValue();
		if(value=="" || value==null){
			matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking)).setRideId(null);
			matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking)).setCount(null);
			matchedTripDataModel = new MatchedTripDataModel(matchedTripByConditionList);
			rideManagementListForPopupOne.clear();
		} else{
			for(int i=0;i<vehicleMasterDTOList.size();i++){
				if(vehicleMasterDTOList.get(i).getVehicleID().equals(value)){
					MatchedTripDTO dto1 = new MatchedTripDTO();

					dto1=matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking));
					MatchedTripDTO matchedTripDTO = new MatchedTripDTO();
					Date date = null;
					try {
						//DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern1);
						DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern3);
						SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern2);
						date = formatter1.parse(dto1.getStartDate());
						String date1 = dateFormat.format(date);
						matchedTripDTO = ListOfValuesManager.getCount(value, date1, "com");
						
						dto1=matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking));
						dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern1);
						date = formatter1.parse(dto1.getStartDate());
						date1 = dateFormat.format(date);
						if(matchedTripDTO.getCount()!=null){
							if(matchedTripDTO.getCount().equalsIgnoreCase(vehicleMasterDTOList.get(i).getCapacity()) || Integer.valueOf(vehicleMasterDTOList.get(i).getCapacity())<=Integer.valueOf(matchedTripDTO.getCount())){
								dto1.setCount(ApplicationUtil.seatAbsent);
								rideManagementListForPopupOne.clear();
							} else{
								int co = Integer.valueOf(vehicleMasterDTOList.get(i).getCapacity())-Integer.valueOf(matchedTripDTO.getCount());
								dto1.setCount(String.valueOf(co));
							}
						} else{
							dto1.setCount(vehicleMasterDTOList.get(i).getCapacity());
						}
						vehicleMasterDTO = vehicleMasterDTOList.get(i);
						matchedTripByConditionList.set(Integer.valueOf(rowForTaxiLinking), dto1);

						rideManagementListForPopupOne = ListOfValuesManager.getMethodForPopupOne(value, date1);
						
						if(rideManagementListForPopupOne.size()!=0){
							if(matchedTripDTO.getCount() != null && (matchedTripDTO.getCount().equalsIgnoreCase(vehicleMasterDTOList.get(i).getCapacity()) || Integer.valueOf(vehicleMasterDTOList.get(i).getCapacity())<=Integer.valueOf(matchedTripDTO.getCount()))){
								RideManagementDTO dto = new RideManagementDTO();
								dto.setRideID(ApplicationUtil.rideStatus);
								rideManagementListForPopupOne.add(dto);
							} else {
								RideManagementDTO dto = new RideManagementDTO();
								dto.setRideID(ApplicationUtil.rideStatus);
								rideManagementListForPopupOne.add(dto);
							}
							Collections.reverse(rideManagementListForPopupOne);
						} else {
							matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking)).setRideId(ApplicationUtil.rideStatus);
						}
					} catch (ParseException e) {
						LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
					}
					matchedTripDataModel = new MatchedTripDataModel(matchedTripByConditionList);
				}
			}
		}
	}
	public void rideIdSetForLinking(){

		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		String rideID = (String)requestMap.get("rideID");
		//String rowIndex = (String)requestMap.get("row");

		/*if(rowIndex==null){
		vehicleMasterDTO = vehicleMasterDTOList.get(Integer.valueOf(rowIndex));
		}*/
		matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking)).setRideId(rideID);
		matchedTripDataModel = new MatchedTripDataModel(matchedTripByConditionList);
		//return "linking";
	}
	public String showAllRideSeekerForRideInPopup(){
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		String rideID = (String)requestMap.get("rideID");
		
		try {
			vehicleMasterDTO = ListOfValuesManager.getVehicleDtoByRideId(rideID, null);
		} catch (ConfigurationException e) {
			vehicleMasterDTO = new VehicleMasterDTO();
		}
		allSeekerForGivenRide = ListOfValuesManager.getAllRideSeekerForAGivenRide(rideID);
		
		return "allSeeker";
	}
	

	public String showRideSeekerPopup() {
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		String rideID = (String)requestMap.get("rideId");
		rideManagerInfoForRideSeeker = ListOfValuesManager.getRideManagerPopupData(rideID);
		
		vehicleMasterDTO = ListOfValuesManager.getVehicleDtoById(rideManagerInfoForRideSeeker.getVehicleID());
		try {
			vehicleMasterDTO = ListOfValuesManager.getVehicleDtoByRideId(rideID, null);
		} catch (ConfigurationException e) {
			vehicleMasterDTO = new VehicleMasterDTO();
		}
		
		try {
			UserRegistrationDTO userDto = ListOfValuesManager.findUserDtoByVehicleId(Integer.parseInt(rideManagerInfoForRideSeeker.getVehicleID()), null);
			rideManagerInfoForRideSeeker.setUserID(userDto.getId());
			rideManagerInfoForRideSeeker.setCreatedBy(userDto.getFirst_name());
		} catch (NumberFormatException e) { } catch (ConfigurationException e) {}
		
		/*userDto = ListOfValuesManager.findUserDtoByVehicleId(Integer.parseInt(rideManagementDTO.getVehicleID()), con);
			
				*/
		return "allSeeker";
	}
	public void listOfRideForACircle(){
		matchedTripByConditionList = ListOfValuesManager.getRideSekerForCircle(String.valueOf(circleDTO.getCircleID()));
		
		matchedTripDataModel = new MatchedTripDataModel(matchedTripByConditionList);
	}
	public void updatePoolForIsResult() {
		ListOfValuesManager.getUpdatePoolForIsResult();
		rollbackTest = false;
		
	}

	public void populateApprover() {
		approverdtoList.clear();
		HttpSession currentSession = ServerUtility.getSession();
		if(currentSession.getAttribute("approverdtoList") != null) {
			approverdtoList.addAll((List<ApproverDTO>) currentSession.getAttribute("approverdtoList"));
		} else {
			try {
				approverdtoList.addAll(ListOfValuesManager.findApproverForUser(Integer.parseInt(userRegistrationDTO.getId())));
				currentSession.setAttribute("approverdtoList", approverdtoList);
			} catch (NumberFormatException e) { currentSession.removeAttribute("approverdtoList"); }
		}
		
	}
	public void addApprover() {
		clearScreenMessage();
		populateApprover();
		int circleId = 0;
		for(CircleDTO dto:allCircleList) {
			circleId = dto.getCircleID();
		}
		if(circleId > 0) {
			if((approverdto.getHoponId() == null || approverdto.getHoponId().trim().length() == 0)){
				errorMessage.add("Please enter first approver hopon iD.");
			} 
			if(approverdto.getHoponId() != null && approverdto.getHoponId2() != null && approverdto.getHoponId().equalsIgnoreCase(approverdto.getHoponId2())) {
				errorMessage.add("Second approver HopOn ID should be different from First HopOn ID.");
			}
			if(errorMessage.size() == 0){
				approverdto.setCircleId(circleId);
				approverdto.setCreatedBy(userRegistrationDTO.getId());
				approverdto.setVerificationCode(ServerUtility.randomString(15));
				if(approverdto.getHoponId2() != null && approverdto.getHoponId2().length() > 0) {
					approverdto.setVerificationCode2(ServerUtility.randomString(15));
				}
				approverdto = ListOfValuesManager.addApprover(approverdto);
				HttpSession currentSession = ServerUtility.getSession();
				currentSession.removeAttribute("approverdtoList");
				successMessage.add("Approver added successfully!");
			}
		} else {
			errorMessage.add("Please register circle First.");
		}
		approverdto = new ApproverDTO();
		populateApprover();
	}

	public void processValueChange4(AjaxBehaviorEvent event) throws AbortProcessingException {
		gatherAffiliatedCircle(manageRideFormDTO.getMyCircleId());
	}

	public void recurringRideCron() {
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		dtos.addAll(ListOfValuesManager.fetchRecurringRideList());
		Date post2Date = new Date(System.currentTimeMillis() + 86400 * 1000 * 2);
		for(RideSeekerDTO dto:dtos) {
			Date tempDate = dto.getStartDate();
			Calendar cal1 = Calendar.getInstance(); cal1.setTime(tempDate); 
			Calendar cal2 = Calendar.getInstance(); cal2.setTime(post2Date); 
			cal2.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY));
			cal2.set(Calendar.MINUTE, cal1.get(Calendar.MINUTE));
			cal2.set(Calendar.SECOND, cal1.get(Calendar.SECOND));
			post2Date = cal2.getTime();
			String frequency[] = dto.getFrequencyinweek().replace("[", "").replace("]", "").split(",");
			//Testing if 2nd day present in trip frequency or not.
			for(int i=0;i<frequency.length;i++) {
				if(ApplicationUtil.dateFormat18.format(post2Date).equalsIgnoreCase(frequency[i].trim())) {
					RideManagementDTO dtoTemp = new RideManagementDTO();
					/*
					try {
						BeanUtils.copyProperties(dtoTemp, dto);
					} catch (IllegalAccessException e) { } catch (InvocationTargetException e) { } catch(ConversionException e) { }*/
					dtoTemp.setUserID(dto.getUserID()); dtoTemp.setViaPoint(dto.getViaPoint()); 
					dtoTemp.setViaPointLatitude(dto.getViaPointLatitude()); dtoTemp.setViaPointLongitude(dto.getViaPointLongitude());
					dtoTemp.setFromAddress1(dto.getFromAddress1()); dtoTemp.setToAddress1(dto.getToAddress1());
					
					DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern3);
					dtoTemp.setStartdateValue(dateFormat.format(post2Date));
					dtoTemp.setRideID(null);dtoTemp.setStartDate(post2Date);
					dtoTemp.setFlexiTimeAfter(post2Date); dtoTemp.setFlexiTimeBefore(post2Date);
					dtoTemp.setStatus(dto.getStatus()); dtoTemp.setCreated_dt(new Date());
					dtoTemp.setFromAddressCity(dto.getToAddressCity()); dtoTemp.setFromAddressPin(dto.getFromAddressPin());
					dtoTemp.setFromAddressCity(dto.getToAddressCity()); dtoTemp.setToAddressPin(dto.getToAddressPin());
					dtoTemp.setCreatedBy(dto.getCreatedBy()); dtoTemp.setSharedTaxi(dto.isSharedTaxi());
					dtoTemp.setCustom(dto.getCustom()); dtoTemp.setStartPointLatitude(dto.getStartPointLatitude()); 
					dtoTemp.setStartPointLongitude(dto.getStartPointLongitude()); dtoTemp.setEndPointLatitude(dto.getEndPointLatitude());
					dtoTemp.setEndPointLongitude(dto.getEndPointLongitude()); dtoTemp.setApproverID(dto.getApproverID());
					dtoTemp.setRecurring("F");
					UserPreferencesDTO userDto = ListOfValuesManager.getUserPreferences(Integer.parseInt(dtoTemp.getUserID()));
					List x1;
					try {
						x1 = ApplicationUtil.calculateTimeWindowSettings(dtoTemp.getFromAddress1(), "", dtoTemp.getToAddress1(), userDto.getMaxWaitTime(), dtoTemp.getStartdateValue());
						if(x1.size() > 0) {
							dtoTemp.setStartdateValue(x1.get(1).toString());
							dtoTemp.setStartDateEarly(x1.get(1).toString());
							dtoTemp.setStartDateLate(x1.get(2).toString());
							dtoTemp.setEndDateEarly(x1.get(3).toString());
							dtoTemp.setEndDateLate(x1.get(4).toString());
							float distance = Integer.parseInt(x1.get(5).toString()) / 1000;
							dtoTemp.setRideDistance(distance);
							dtoTemp.setRideCost((distance * 5) + "");
						}
					} catch (IOException e) { } catch (JSONException e) { }
					if(!ListOfValuesManager.checkRideSeekerDuplicacy(dtoTemp)) {
						Connection con = ListOfValuesManager.getBroadConnection();
						try {
							dtoTemp = ListOfValuesManager.getRideSeekerEntery("findByDTO", dtoTemp, con);
						
							try {
								frequencyDTO = new FrequencyDTO();
								frequencyDTO.setStartDate(dateFormat.format(post2Date));
								frequencyDTO.setEndDate(dateFormat.format(dtoTemp.getEndDate()));
							} catch(NullPointerException e) {}
	
							frequencyDTO.setTime(post2Date);
							List<String> freq = new ArrayList<String>(); freq.add("Once");
							frequencyDTO.setFrequency(freq);
							frequencyDTO.setRideSeekerId(dtoTemp.getRideID());
	
							frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO", frequencyDTO, con);
							
							if(dto.getSubSeekers().length() == 0) dto.setSubSeekers(dtoTemp.getRideID());
							else dto.setSubSeekers(dto.getSubSeekers()+","+dtoTemp.getRideID());
							ListOfValuesManager.addSubSeekers(dto, con);
							
							
						} catch (ConfigurationException e) { 
							LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); rollbackTest = true;
						} finally {
							if(rollbackTest) {
								try { con.rollback(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
								ListOfValuesManager.releaseConnection(con);
							} else {
								try { con.commit(); } catch (SQLException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage()); }
								ListOfValuesManager.releaseConnection(con);
							}
						}
					}
					break;
				}
			}
		}
	}
	
}
