package com.hopon.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.hopon.dto.ApproverDTO;
import com.hopon.dto.CircleAffiliationsDTO;
import com.hopon.dto.CircleDTO;
import com.hopon.dto.CircleMemberDTO;
import com.hopon.dto.CircleOwnerManagerDTO;
import com.hopon.dto.CombineRideDTO;
import com.hopon.dto.CombineVehicleDataModel;
import com.hopon.dto.CompanyRegisterDTO;
import com.hopon.dto.ContactusDTO;
import com.hopon.dto.FavoritePlacesDTO;
import com.hopon.dto.FrequencyDTO;
import com.hopon.dto.ManageRideDTO;
import com.hopon.dto.ManageRideFormDTO;
import com.hopon.dto.MatchedTripDTO;
import com.hopon.dto.MatchedTripDataModel;
import com.hopon.dto.MessageBoardDTO;
import com.hopon.dto.PaymentDTO;
import com.hopon.dto.PaymentPlanDTO;
import com.hopon.dto.PoolRequestsDTO;
import com.hopon.dto.RideManagementDTO;
import com.hopon.dto.RidePreVehicle;
import com.hopon.dto.RideSeekerDTO;
import com.hopon.dto.UserPreferencesDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.dto.VehicleMasterDTO;
import com.hopon.dto.VehicleMasterDataTable;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.LoggerSingleton;

public class HPBaseAction {
	protected static Logger log = Logger.getLogger(UserAction.class.getName());
	protected boolean rollbackTest = false;
	protected String redirectUri;
	protected List<CompanyRegisterDTO> listofCompanyForLoginUser = new ArrayList<CompanyRegisterDTO>();
	protected List<FavoritePlacesDTO> allPlace = new ArrayList<FavoritePlacesDTO>();
	protected String minDate;
	protected String rowForTaxiLinking;
	protected List<MessageBoardDTO> allUnreadMessageList = new ArrayList<MessageBoardDTO>();
	protected List<MessageBoardDTO> allpopUpMessageList = new ArrayList<MessageBoardDTO>();
	protected MessageBoardDTO userMessageDTO = new MessageBoardDTO();
	protected List<String> successMessage = new ArrayList<String>();
	protected List<String> errorMessage = new ArrayList<String>();
	protected String uri; 
	protected String message;
	protected String messagePlace;
	protected StreamedContent vehicleFile;  
	protected StreamedContent userFile;
	
	
	protected UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
	protected UserPreferencesDTO userPreferences = new UserPreferencesDTO();
	protected UserRegistrationDTO forregistrationOnly = new UserRegistrationDTO();
	protected CompanyRegisterDTO  companyRegisterDTO  = new CompanyRegisterDTO();
	protected String userName;
	
	protected boolean recurring = false;
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
	
	
	protected List<CircleDTO> allCircleList = new ArrayList<CircleDTO>();
	protected List<CircleDTO> allMemberCircleList = new ArrayList<CircleDTO>();
	protected List<CircleDTO> allCircleListByName = new ArrayList<CircleDTO>();
	protected List<CircleDTO> taxiCircleList = new ArrayList<CircleDTO>();
	protected List<CircleDTO> nonTaxiCircleList = new ArrayList<CircleDTO>();
	protected String autoNonTaxiCircleValue = null;
	protected String autoTaxiCircleValue = null;
	protected int userRegisterCircle = 0;
	protected CircleAffiliationsDTO circleAffiliationsDTO = new CircleAffiliationsDTO();
	protected List<CircleAffiliationsDTO> allCircleAffiliationsDTO = new ArrayList<CircleAffiliationsDTO>();
	protected List<CircleAffiliationsDTO> allPendingCircleAffiliationsDTO = new ArrayList<CircleAffiliationsDTO>();
	protected CircleDTO circleDTO = new CircleDTO();
	protected int taxiCircleId;
	protected CircleDTO taxiCircle = new CircleDTO();
	protected List<CircleOwnerManagerDTO> allCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();
	protected List<CircleOwnerManagerDTO> allCorpCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();
	protected List<CircleOwnerManagerDTO> allTaxiCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();
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
	
	protected VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();
	protected List<RidePreVehicle> ridePreVehicleList = new ArrayList<RidePreVehicle>();
	protected String ridePreVehicleDate;	
	protected VehicleMasterDataTable vehicleMasterDataModel = new VehicleMasterDataTable();
	protected VehicleMasterDTO[] vehicleMasterDtos;
	protected List<VehicleMasterDTO> vehicleMasterDTOList = new ArrayList<VehicleMasterDTO>();
	protected List<PaymentDTO> paymentList = new ArrayList<PaymentDTO>();
	protected List<PaymentDTO> duePaymentList = new ArrayList<PaymentDTO>();
	public boolean userCirclePaymentPending = false;
	protected List<PaymentPlanDTO> userPaymentPlanList = new ArrayList<PaymentPlanDTO>();
	protected List<SelectItem> allCompany;
	protected List<SelectItem> companySector;
	protected List<SelectItem> allCircleOption;
	
	
	//Start ContactUsDTO class
	protected ContactusDTO contactusDTO=new ContactusDTO();
	
	
	public ContactusDTO getContactusDTO() {
		return contactusDTO;
	}
	public void setContactusDTO(ContactusDTO contactusDTO) {
		this.contactusDTO = contactusDTO;
	}
	public UserRegistrationDTO getUserRegistrationDTO() {
		return userRegistrationDTO;
	}
	public void setUserRegistrationDTO(UserRegistrationDTO userRegistrationDTO) {
		this.userRegistrationDTO = userRegistrationDTO;
	}
	public UserPreferencesDTO getUserPreferences() {
		return userPreferences;
	}
	public void setUserPreferences(UserPreferencesDTO userPreferences) {
		this.userPreferences = userPreferences;
	}
	public UserRegistrationDTO getForregistrationOnly() {
		return forregistrationOnly;
	}
	public void setForregistrationOnly(UserRegistrationDTO forregistrationOnly) {
		this.forregistrationOnly = forregistrationOnly;
	}
	public CompanyRegisterDTO getCompanyRegisterDTO() {
		return companyRegisterDTO;
	}
	public void setCompanyRegisterDTO(CompanyRegisterDTO companyRegisterDTO) {
		this.companyRegisterDTO = companyRegisterDTO;
	}
	public RideManagementDTO getRideRegistered() {
		return rideRegistered;
	}
	public void setRideRegistered(RideManagementDTO rideRegistered) {
		this.rideRegistered = rideRegistered;
	}
	public List<CircleDTO> getAllCircleList() {
		return allCircleList;
	}
	public void setAllCircleList(List<CircleDTO> allCircleList) {
		this.allCircleList = allCircleList;
	}
	public List<CircleDTO> getAllMemberCircleList() {
		return allMemberCircleList;
	}
	public void setAllMemberCircleList(List<CircleDTO> allMemberCircleList) {
		this.allMemberCircleList = allMemberCircleList;
	}
	public List<CircleDTO> getAllCircleListByName() {
		return allCircleListByName;
	}
	public void setAllCircleListByName(List<CircleDTO> allCircleListByName) {
		this.allCircleListByName = allCircleListByName;
	}
	public List<CircleDTO> getAllTaxiCircle() {
		return allTaxiCircle;
	}
	public void setAllTaxiCircle(List<CircleDTO> allTaxiCircle) {
		this.allTaxiCircle = allTaxiCircle;
	}
	public boolean isRollbackTest() {
		return rollbackTest;
	}
	public void setRollbackTest(boolean rollbackTest) {
		this.rollbackTest = rollbackTest;
	}
	public List<RideSeekerDTO> getAllSeekerForGivenRide() {
		return allSeekerForGivenRide;
	}
	public void setAllSeekerForGivenRide(List<RideSeekerDTO> allSeekerForGivenRide) {
		this.allSeekerForGivenRide = allSeekerForGivenRide;
	}
	public FrequencyDTO getFrequencyDTO() {
		return frequencyDTO;
	}
	public void setFrequencyDTO(FrequencyDTO frequencyDTO) {
		this.frequencyDTO = frequencyDTO;
	}
	public VehicleMasterDTO getVehicleMasterDTO() {
		return vehicleMasterDTO;
	}
	public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
		this.vehicleMasterDTO = vehicleMasterDTO;
	}
	public List<RideManagementDTO> getRideManagementList() {
		return rideManagementList;
	}
	public void setRideManagementList(List<RideManagementDTO> rideManagementList) {
		this.rideManagementList = rideManagementList;
	}
	public List<RideSeekerDTO> getRideSeekerList() {
		return rideSeekerList;
	}
	public void setRideSeekerList(List<RideSeekerDTO> rideSeekerList) {
		this.rideSeekerList = rideSeekerList;
	}
	public List<RideSeekerDTO> getRecurringRideSeekerList() {
		return recurringRideSeekerList;
	}
	public void setRecurringRideSeekerList(
			List<RideSeekerDTO> recurringRideSeekerList) {
		this.recurringRideSeekerList = recurringRideSeekerList;
	}
	public List<PoolRequestsDTO> getAllCompleateRideList() {
		return allCompleateRideList;
	}
	public void setAllCompleateRideList(List<PoolRequestsDTO> allCompleateRideList) {
		this.allCompleateRideList = allCompleateRideList;
	}
	public List<PoolRequestsDTO> getAllCompleateRideSeekerList() {
		return allCompleateRideSeekerList;
	}
	public void setAllCompleateRideSeekerList(
			List<PoolRequestsDTO> allCompleateRideSeekerList) {
		this.allCompleateRideSeekerList = allCompleateRideSeekerList;
	}
	public List<PoolRequestsDTO> getAllMsgBoardCompleateRideList() {
		return allMsgBoardCompleateRideList;
	}
	public void setAllMsgBoardCompleateRideList(
			List<PoolRequestsDTO> allMsgBoardCompleateRideList) {
		this.allMsgBoardCompleateRideList = allMsgBoardCompleateRideList;
	}
	public List<PoolRequestsDTO> getAllMsgBoardCompleateRideSeekerList() {
		return allMsgBoardCompleateRideSeekerList;
	}
	public void setAllMsgBoardCompleateRideSeekerList(
			List<PoolRequestsDTO> allMsgBoardCompleateRideSeekerList) {
		this.allMsgBoardCompleateRideSeekerList = allMsgBoardCompleateRideSeekerList;
	}
	public List<CircleDTO> getTaxiCircleList() {
		return taxiCircleList;
	}
	public void setTaxiCircleList(List<CircleDTO> taxiCircleList) {
		this.taxiCircleList = taxiCircleList;
	}
	public List<CircleDTO> getNonTaxiCircleList() {
		return nonTaxiCircleList;
	}
	public void setNonTaxiCircleList(List<CircleDTO> nonTaxiCircleList) {
		this.nonTaxiCircleList = nonTaxiCircleList;
	}
	public ApproverDTO getApproverdto() {
		return approverdto;
	}
	public void setApproverdto(ApproverDTO approverdto) {
		this.approverdto = approverdto;
	}
	public List<ApproverDTO> getApproverdtoList() {
		return approverdtoList;
	}
	public void setApproverdtoList(List<ApproverDTO> approverdtoList) {
		this.approverdtoList = approverdtoList;
	}
	public List<RidePreVehicle> getRidePreVehicleList() {
		return ridePreVehicleList;
	}
	public void setRidePreVehicleList(List<RidePreVehicle> ridePreVehicleList) {
		this.ridePreVehicleList = ridePreVehicleList;
	}
	public String getRidePreVehicleDate() {
		return ridePreVehicleDate;
	}
	public void setRidePreVehicleDate(String ridePreVehicleDate) {
		this.ridePreVehicleDate = ridePreVehicleDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public boolean isAllowRecurringSubRideToCancel() {
		return allowRecurringSubRideToCancel;
	}
	public void setAllowRecurringSubRideToCancel(
			boolean allowRecurringSubRideToCancel) {
		this.allowRecurringSubRideToCancel = allowRecurringSubRideToCancel;
	}
	public String getAutoNonTaxiCircleValue() {
		return autoNonTaxiCircleValue;
	}
	public void setAutoNonTaxiCircleValue(String autoNonTaxiCircleValue) {
		this.autoNonTaxiCircleValue = autoNonTaxiCircleValue;
	}
	public String getAutoTaxiCircleValue() {
		return autoTaxiCircleValue;
	}
	public void setAutoTaxiCircleValue(String autoTaxiCircleValue) {
		this.autoTaxiCircleValue = autoTaxiCircleValue;
	}
	public int getUserRegisterCircle() {
		return userRegisterCircle;
	}
	public void setUserRegisterCircle(int userRegisterCircle) {
		this.userRegisterCircle = userRegisterCircle;
	}
	public CircleAffiliationsDTO getCircleAffiliationsDTO() {
		return circleAffiliationsDTO;
	}
	public void setCircleAffiliationsDTO(CircleAffiliationsDTO circleAffiliationsDTO) {
		this.circleAffiliationsDTO = circleAffiliationsDTO;
	}
	public List<CircleAffiliationsDTO> getAllCircleAffiliationsDTO() {
		return allCircleAffiliationsDTO;
	}
	public void setAllCircleAffiliationsDTO(
			List<CircleAffiliationsDTO> allCircleAffiliationsDTO) {
		this.allCircleAffiliationsDTO = allCircleAffiliationsDTO;
	}
	public List<CircleAffiliationsDTO> getAllPendingCircleAffiliationsDTO() {
		return allPendingCircleAffiliationsDTO;
	}
	public void setAllPendingCircleAffiliationsDTO(
			List<CircleAffiliationsDTO> allPendingCircleAffiliationsDTO) {
		this.allPendingCircleAffiliationsDTO = allPendingCircleAffiliationsDTO;
	}
	public PoolRequestsDTO getPoolRequestsDTO() {
		return poolRequestsDTO;
	}
	public void setPoolRequestsDTO(PoolRequestsDTO poolRequestsDTO) {
		this.poolRequestsDTO = poolRequestsDTO;
	}
	public CircleDTO getCircleDTO() {
		return circleDTO;
	}
	public void setCircleDTO(CircleDTO circleDTO) {
		this.circleDTO = circleDTO;
	}
	public RideSeekerDTO getRideSeekerDTO() {
		return rideSeekerDTO;
	}
	public void setRideSeekerDTO(RideSeekerDTO rideSeekerDTO) {
		this.rideSeekerDTO = rideSeekerDTO;
	}
	public RideManagementDTO getRideManagementDTO() {
		return rideManagementDTO;
	}
	public void setRideManagementDTO(RideManagementDTO rideManagementDTO) {
		this.rideManagementDTO = rideManagementDTO;
	}
	public int getRideIdToDrop() {
		return rideIdToDrop;
	}
	public void setRideIdToDrop(int rideIdToDrop) {
		this.rideIdToDrop = rideIdToDrop;
	}
	public int getRideIdToTake() {
		return rideIdToTake;
	}
	public void setRideIdToTake(int rideIdToTake) {
		this.rideIdToTake = rideIdToTake;
	}
	public String getRideManager() {
		return rideManager;
	}
	public void setRideManager(String rideManager) {
		this.rideManager = rideManager;
	}
	public int getRidePicker() {
		return ridePicker;
	}
	public void setRidePicker(int ridePicker) {
		this.ridePicker = ridePicker;
	}
	public int getTaxiCircleId() {
		return taxiCircleId;
	}
	public void setTaxiCircleId(int taxiCircleId) {
		this.taxiCircleId = taxiCircleId;
	}
	public CircleDTO getTaxiCircle() {
		return taxiCircle;
	}
	public void setTaxiCircle(CircleDTO taxiCircle) {
		this.taxiCircle = taxiCircle;
	}
	public List<CircleOwnerManagerDTO> getAllCircleForLoginUserList() {
		return allCircleForLoginUserList;
	}
	public void setAllCircleForLoginUserList(
			List<CircleOwnerManagerDTO> allCircleForLoginUserList) {
		this.allCircleForLoginUserList = allCircleForLoginUserList;
	}
	public List<CircleOwnerManagerDTO> getAllCorpCircleForLoginUserList() {
		return allCorpCircleForLoginUserList;
	}
	public void setAllCorpCircleForLoginUserList(
			List<CircleOwnerManagerDTO> allCorpCircleForLoginUserList) {
		this.allCorpCircleForLoginUserList = allCorpCircleForLoginUserList;
	}
	public List<CircleOwnerManagerDTO> getAllTaxiCircleForLoginUserList() {
		return allTaxiCircleForLoginUserList;
	}
	public void setAllTaxiCircleForLoginUserList(
			List<CircleOwnerManagerDTO> allTaxiCircleForLoginUserList) {
		this.allTaxiCircleForLoginUserList = allTaxiCircleForLoginUserList;
	}
	public List<CircleOwnerManagerDTO> getAllCircleMemberForLoginUserList() {
		return allCircleMemberForLoginUserList;
	}
	public void setAllCircleMemberForLoginUserList(
			List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList) {
		this.allCircleMemberForLoginUserList = allCircleMemberForLoginUserList;
	}
	public List<CircleOwnerManagerDTO> getAllPendingCircleMemberList() {
		return allPendingCircleMemberList;
	}
	public void setAllPendingCircleMemberList(
			List<CircleOwnerManagerDTO> allPendingCircleMemberList) {
		this.allPendingCircleMemberList = allPendingCircleMemberList;
	}
	public List<CircleOwnerManagerDTO> getAllCircleOwnerManagerUserList() {
		return allCircleOwnerManagerUserList;
	}
	public void setAllCircleOwnerManagerUserList(
			List<CircleOwnerManagerDTO> allCircleOwnerManagerUserList) {
		this.allCircleOwnerManagerUserList = allCircleOwnerManagerUserList;
	}
	public List<CircleDTO> getAllCircleMembershipInvitationList() {
		return allCircleMembershipInvitationList;
	}
	public void setAllCircleMembershipInvitationList(
			List<CircleDTO> allCircleMembershipInvitationList) {
		this.allCircleMembershipInvitationList = allCircleMembershipInvitationList;
	}
	public List<MatchedTripDTO> getMatchedTripByConditionList() {
		return matchedTripByConditionList;
	}
	public void setMatchedTripByConditionList(
			List<MatchedTripDTO> matchedTripByConditionList) {
		this.matchedTripByConditionList = matchedTripByConditionList;
	}
	public List<CombineRideDTO> getCombineVehicleCondition() {
		return combineVehicleCondition;
	}
	public void setCombineVehicleCondition(
			List<CombineRideDTO> combineVehicleCondition) {
		this.combineVehicleCondition = combineVehicleCondition;
	}
	public List<CircleAffiliationsDTO> getAllCircleAffiliationRequest() {
		return allCircleAffiliationRequest;
	}
	public void setAllCircleAffiliationRequest(
			List<CircleAffiliationsDTO> allCircleAffiliationRequest) {
		this.allCircleAffiliationRequest = allCircleAffiliationRequest;
	}
	public List<RideSeekerDTO> getAllRideApprovalRequest() {
		return allRideApprovalRequest;
	}
	public void setAllRideApprovalRequest(List<RideSeekerDTO> allRideApprovalRequest) {
		this.allRideApprovalRequest = allRideApprovalRequest;
	}
	public MatchedTripDataModel getMatchedTripDataModel() {
		return matchedTripDataModel;
	}
	public void setMatchedTripDataModel(MatchedTripDataModel matchedTripDataModel) {
		this.matchedTripDataModel = matchedTripDataModel;
	}
	public CombineVehicleDataModel getCombineVehicleDataModel() {
		return combineVehicleDataModel;
	}
	public void setCombineVehicleDataModel(
			CombineVehicleDataModel combineVehicleDataModel) {
		this.combineVehicleDataModel = combineVehicleDataModel;
	}
	public VehicleMasterDataTable getVehicleMasterDataModel() {
		return vehicleMasterDataModel;
	}
	public void setVehicleMasterDataModel(
			VehicleMasterDataTable vehicleMasterDataModel) {
		this.vehicleMasterDataModel = vehicleMasterDataModel;
	}
	public MatchedTripDTO[] getMatchedTripDTOs() {
		return matchedTripDTOs;
	}
	public void setMatchedTripDTOs(MatchedTripDTO[] matchedTripDTOs) {
		this.matchedTripDTOs = matchedTripDTOs;
	}
	public CombineRideDTO[] getCombineVehicleDtos() {
		return combineVehicleDtos;
	}
	public void setCombineVehicleDtos(CombineRideDTO[] combineVehicleDtos) {
		this.combineVehicleDtos = combineVehicleDtos;
	}
	public VehicleMasterDTO[] getVehicleMasterDtos() {
		return vehicleMasterDtos;
	}
	public void setVehicleMasterDtos(VehicleMasterDTO[] vehicleMasterDtos) {
		this.vehicleMasterDtos = vehicleMasterDtos;
	}
	public CircleOwnerManagerDTO getCircleOwnerManagerDTO() {
		return circleOwnerManagerDTO;
	}
	public void setCircleOwnerManagerDTO(CircleOwnerManagerDTO circleOwnerManagerDTO) {
		this.circleOwnerManagerDTO = circleOwnerManagerDTO;
	}
	public List<VehicleMasterDTO> getVehicleMasterDTOList() {
		return vehicleMasterDTOList;
	}
	public void setVehicleMasterDTOList(List<VehicleMasterDTO> vehicleMasterDTOList) {
		this.vehicleMasterDTOList = vehicleMasterDTOList;
	}
	public List<CompanyRegisterDTO> getListofCompanyForLoginUser() {
		return listofCompanyForLoginUser;
	}
	public void setListofCompanyForLoginUser(
			List<CompanyRegisterDTO> listofCompanyForLoginUser) {
		this.listofCompanyForLoginUser = listofCompanyForLoginUser;
	}
	public List<FavoritePlacesDTO> getAllPlace() {
		return allPlace;
	}
	public void setAllPlace(List<FavoritePlacesDTO> allPlace) {
		this.allPlace = allPlace;
	}
	public List<RideManagementDTO> getRideManagementListForPopupOne() {
		return rideManagementListForPopupOne;
	}
	public void setRideManagementListForPopupOne(
			List<RideManagementDTO> rideManagementListForPopupOne) {
		this.rideManagementListForPopupOne = rideManagementListForPopupOne;
	}
	public CircleMemberDTO getCircleMemberDTO() {
		return circleMemberDTO;
	}
	public void setCircleMemberDTO(CircleMemberDTO circleMemberDTO) {
		this.circleMemberDTO = circleMemberDTO;
	}
	public boolean isRecurring() {
		return recurring;
	}
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getRowForTaxiLinking() {
		return rowForTaxiLinking;
	}
	public void setRowForTaxiLinking(String rowForTaxiLinking) {
		this.rowForTaxiLinking = rowForTaxiLinking;
	}
	public List<MessageBoardDTO> getAllUnreadMessageList() {
		return allUnreadMessageList;
	}
	public void setAllUnreadMessageList(List<MessageBoardDTO> allUnreadMessageList) {
		this.allUnreadMessageList = allUnreadMessageList;
	}
	public List<MessageBoardDTO> getAllpopUpMessageList() {
		return allpopUpMessageList;
	}
	public void setAllpopUpMessageList(List<MessageBoardDTO> allpopUpMessageList) {
		this.allpopUpMessageList = allpopUpMessageList;
	}
	public MessageBoardDTO getUserMessageDTO() {
		return userMessageDTO;
	}
	public void setUserMessageDTO(MessageBoardDTO userMessageDTO) {
		this.userMessageDTO = userMessageDTO;
	}
	public List<String> getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(List<String> successMessage) {
		this.successMessage = successMessage;
	}
	public List<String> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(List<String> errorMessage) {
		this.errorMessage = errorMessage;
	}
	public MatchedTripDTO getMatchedTripDTO() {
		return matchedTripDTO;
	}
	public void setMatchedTripDTO(MatchedTripDTO matchedTripDTO) {
		this.matchedTripDTO = matchedTripDTO;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getAffiliateCircleId() {
		return affiliateCircleId;
	}
	public void setAffiliateCircleId(int affiliateCircleId) {
		this.affiliateCircleId = affiliateCircleId;
	}
	public boolean isPendingRideTest() {
		return pendingRideTest;
	}
	public void setPendingRideTest(boolean pendingRideTest) {
		this.pendingRideTest = pendingRideTest;
	}
	public boolean isRidePreMatchFormTest() {
		return ridePreMatchFormTest;
	}
	public void setRidePreMatchFormTest(boolean ridePreMatchFormTest) {
		this.ridePreMatchFormTest = ridePreMatchFormTest;
	}
	public boolean isRideMatchFormTest() {
		return rideMatchFormTest;
	}
	public void setRideMatchFormTest(boolean rideMatchFormTest) {
		this.rideMatchFormTest = rideMatchFormTest;
	}
	public ManageRideFormDTO getManageRideFormDTO() {
		return manageRideFormDTO;
	}
	public void setManageRideFormDTO(ManageRideFormDTO manageRideFormDTO) {
		this.manageRideFormDTO = manageRideFormDTO;
	}
	public List<ManageRideDTO> getManageRideDTOs() {
		return manageRideDTOs;
	}
	public void setManageRideDTOs(List<ManageRideDTO> manageRideDTOs) {
		this.manageRideDTOs = manageRideDTOs;
	}
	public String getMatchRideCancelParam() {
		return matchRideCancelParam;
	}
	public void setMatchRideCancelParam(String matchRideCancelParam) {
		this.matchRideCancelParam = matchRideCancelParam;
	}
	public UserRegistrationDTO getCircleMember() {
		return circleMember;
	}
	public void setCircleMember(UserRegistrationDTO circleMember) {
		this.circleMember = circleMember;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessagePlace() {
		return messagePlace;
	}
	public void setMessagePlace(String messagePlace) {
		this.messagePlace = messagePlace;
	}
	public int getRidePreMatchTest() {
		return ridePreMatchTest;
	}
	public void setRidePreMatchTest(int ridePreMatchTest) {
		this.ridePreMatchTest = ridePreMatchTest;
	}
	public RideManagementDTO getRideManagerInfoForRideSeeker() {
		return rideManagerInfoForRideSeeker;
	}
	public void setRideManagerInfoForRideSeeker(
			RideManagementDTO rideManagerInfoForRideSeeker) {
		this.rideManagerInfoForRideSeeker = rideManagerInfoForRideSeeker;
	}  
	public List<PaymentDTO> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<PaymentDTO> paymentList) {
		this.paymentList = paymentList;
	}
	public List<PaymentDTO> getDuePaymentList() {
		return duePaymentList;
	}
	public void setDuePaymentList(List<PaymentDTO> duePaymentList) {
		this.duePaymentList = duePaymentList;
	}
	public boolean isUserCirclePaymentPending() {
		return userCirclePaymentPending;
	}
	public void setUserCirclePaymentPending(boolean userCirclePaymentPending) {
		this.userCirclePaymentPending = userCirclePaymentPending;
	}
	public List<PaymentPlanDTO> getUserPaymentPlanList() {
		return userPaymentPlanList;
	}
	public void setUserPaymentPlanList(List<PaymentPlanDTO> userPaymentPlanList) {
		this.userPaymentPlanList = userPaymentPlanList;
	}
	public StreamedContent getVehicleFile() {  
		String tomcatDirectoryPath = ApplicationUtil.catalinaDirectoryPath;
		File vehiclePath = null;
		if(tomcatDirectoryPath!=null){
			String vehicleDirPath = ApplicationUtil.demoDirectoryPath;
			vehiclePath = new File(vehicleDirPath + ApplicationUtil.vehicleDemoFile);
			String mimeType = new MimetypesFileTypeMap().getContentType(vehiclePath);
			InputStream stream = null;
			//stream = ((javax.servlet.ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(vehicleDirPath + "VehicleBulkUploadFormat.xls");
			try {
				stream = new FileInputStream(vehiclePath);
			} catch (FileNotFoundException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
			vehicleFile = new DefaultStreamedContent(stream, mimeType, ApplicationUtil.vehicleDownloadFile); 						
		}
		return vehicleFile;
	}    
	public StreamedContent getUserFile() {

		String tomcatDirectoryPath = ApplicationUtil.catalinaDirectoryPath;
		File userPath = null;
		if(tomcatDirectoryPath!=null){
			String userDirPath = ApplicationUtil.demoDirectoryPath;
			userPath = new File(userDirPath + ApplicationUtil.userDemoFile);
			String mimeType = new MimetypesFileTypeMap().getContentType(userPath);
			InputStream stream = null;
			//stream = ((javax.servlet.ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(vehicleDirPath + "VehicleBulkUploadFormat.xls");
			try {
				stream = new FileInputStream(userPath);
			} catch (FileNotFoundException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
			userFile = new DefaultStreamedContent(stream, mimeType, ApplicationUtil.userDownloadFile); 						
		}
		return userFile; 
	}
	
	
	
}
