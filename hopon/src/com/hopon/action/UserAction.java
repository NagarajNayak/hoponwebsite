package com.hopon.action;

/**
 * @author Kumar yogesh
 * this class is used as a action class for jsf 
 * communicate with xhtml pages 
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.json.JSONException;
import org.primefaces.model.UploadedFile;

import com.hopon.dto.ApproverDTO;
import com.hopon.dto.CircleAffiliationsDTO;
import com.hopon.dto.CircleDTO;
import com.hopon.dto.CircleMemberDTO;
import com.hopon.dto.CircleOwnerDTO;
import com.hopon.dto.CircleOwnerManagerDTO;
import com.hopon.dto.CityDTO;
import com.hopon.dto.CombineVehicleDataModel;
import com.hopon.dto.CompanyRegisterDTO;
import com.hopon.dto.ContactusDTO;
import com.hopon.dto.EmailDTO;
import com.hopon.dto.FavoritePlacesDTO;
import com.hopon.dto.FrequencyDTO;
import com.hopon.dto.HoponAccountDTO;
import com.hopon.dto.LoginPageDTO;
import com.hopon.dto.MatchedTripDTO;
import com.hopon.dto.MatchedTripDataModel;
import com.hopon.dto.MessageBoardDTO;
import com.hopon.dto.PageStoreDTO;
import com.hopon.dto.PaymentDTO;
import com.hopon.dto.PaymentTxnsDTO;
import com.hopon.dto.PoolRequestsDTO;
import com.hopon.dto.RideManagementDTO;
import com.hopon.dto.RideSeekerDTO;
import com.hopon.dto.SmsReplyDTO;
import com.hopon.dto.UserPreferencesDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.dto.VehicleMasterDTO;
import com.hopon.dto.VehicleMasterDataTable;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.ConfigurationException;
import com.hopon.utils.ControllerException;
import com.hopon.utils.ListOfValuesManager;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.MailService;
import com.hopon.utils.Messages;
import com.hopon.utils.ServerUtility;
import com.hopon.utils.SmsService;
import com.hopon.utils.Validator;

public class UserAction extends HPBaseAction {
	private List<SelectItem> allCompany;
	private List<SelectItem> companySector;
	private List<SelectItem> allCircleOption;

	public List<SelectItem> getCompanyOptions() {
		allCompany = ListOfValuesManager.getCompanyOptions(allCompany);
		return allCompany;
	}

	public List<SelectItem> getCompanySectorOptions() {
		companySector = ListOfValuesManager
				.getCompanySectorOption(companySector);
		return companySector;
	}

	public List<SelectItem> getCircleOption() {
		allCircleOption = ListOfValuesManager
				.getAllCircleListOptions(allCircleOption);
		return allCircleOption;
	}

	public List<String> cityAutoCompleateMethod(String finalValue) {
		List<String> cityValue = new ArrayList<String>();
		List<CityDTO> cityList = new ArrayList<CityDTO>();
		String city;
		cityList = ListOfValuesManager.getCityList(finalValue);
		for (int i = 0; i < cityList.size(); i++) {
			city = cityList.get(i).getCityName();
			cityValue.add(city);
		}
		return cityValue;
	}

	public List<String> userAutoCompleateMethod(String prefix) {
		List<String> userValue = new ArrayList<String>();
		List<UserRegistrationDTO> userList = new ArrayList<UserRegistrationDTO>();
		String user;
		userList = ListOfValuesManager.getAllUserList(prefix, 5);
		for (int i = 0; i < userList.size(); i++) {
			user = userList.get(i).getFirst_name() + "-"
					+ userList.get(i).getAddress() + ","
					+ userList.get(i).getId();
			userValue.add(user);
		}
		return userValue;
	}

	public List<CircleDTO> userAutoTaxiCircle(String prefix) {

		List<CircleDTO> circle = new ArrayList<CircleDTO>();
		List<CircleDTO> circleTemp = new ArrayList<CircleDTO>();
		// if(taxiCircle.getCircleID() > 0) {
		gatherDefaultcircleDTO();
		circle = ListOfValuesManager.getAllAffiliatedTaxiCircle(prefix);
		List<Integer> tempCircleId = new ArrayList<Integer>();
		for (CircleAffiliationsDTO dto : allCircleAffiliationsDTO) {
			tempCircleId.add(dto.getAffilicatedCircleId());
		}
		for (CircleAffiliationsDTO dto : allPendingCircleAffiliationsDTO) {
			tempCircleId.add(dto.getAffilicatedCircleId());
		}
		for (int i = 0; i < circle.size(); i++) {
			if (!tempCircleId.contains(circle.get(i).getCircleID())) {
				circleTemp.add(circle.get(i));
			}
		}
		circle.clear();
		circle.addAll(circleTemp);
		// }

		return circle;

	}

	public List<CircleDTO> autoTaxiCircle(String prefix) {
		List<CircleDTO> circle = new ArrayList<CircleDTO>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager
				.getallRegisteredTaxiCircleListed(prefix).iterator();
		while (circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO) circleTemp.next();
			if (dto.getName() != null) {
				circle.add(dto);
			}
		}
		return circle;
	}

	public List<CircleDTO> autoNonTaxiCircle(String prefix) {
		List<CircleDTO> circle = new ArrayList<CircleDTO>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager
				.getallRegisteredNonTaxiCircleListed(prefix).iterator();
		while (circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO) circleTemp.next();
			if (dto.getName() != null) {
				circle.add(dto);
			}
		}
		return circle;
	}

	public List<String> autoTaxiCircleStr(String prefix) {
		List<String> circle = new ArrayList<String>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager
				.getallRegisteredTaxiCircleListed(prefix).iterator();
		while (circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO) circleTemp.next();
			if (dto.getName() != null) {
				circle.add(dto.getName() + " - " + dto.getCircleID());
			}
		}
		return circle;
	}

	public List<String> autoNonTaxiCircleStr(String prefix) {
		List<String> circle = new ArrayList<String>();
		Iterator<CircleDTO> circleTemp = ListOfValuesManager
				.getallRegisteredNonTaxiCircleListed(prefix).iterator();
		while (circleTemp.hasNext()) {
			CircleDTO dto = (CircleDTO) circleTemp.next();
			if (dto.getName() != null) {
				circle.add(dto.getName() + " - " + dto.getCircleID());
			}
		}
		return circle;
	}

	public void gatherAffiliatedCircle(int circleId) {
		allCircleAffiliationsDTO.clear();
		allPendingCircleAffiliationsDTO.clear();
		allCircleAffiliationsDTO = ListOfValuesManager
				.getAllAffiliatedCircle("" + circleId);

		allPendingCircleAffiliationsDTO = ListOfValuesManager
				.getAllPendingCircle("" + circleId);

	}

	public void makeCircleAffiliated() {
		int parentCircleId = taxiCircle.getCircleID();
		// FacesContext context = FacesContext.getCurrentInstance();
		// Map<String,String> requestMap =
		// context.getExternalContext().getRequestParameterMap(); //In java
		// class, you can get back the parameter value with component
		// (submit-command buton) like this :
		int childCircleId = affiliateCircleId;
		gatherAffiliatedCircle(parentCircleId);
		clearScreenMessage();
		if (parentCircleId <= 0) {
			errorMessage.add("Please select a Circle first.");
		} else if (childCircleId <= 0) {
			errorMessage.add("Please select affiliated Taxi circle.");
		} else {
			ListOfValuesManager.makeTaxiCircleAffiliated(parentCircleId,
					childCircleId);
			successMessage.add(Messages.getValue("success.taxi.affiliation"));
		}
		gatherDefaultcircleDTO();
	}

	public String registerUser() {

		clearScreenMessage();
		forregistrationOnly.setMobile_no(forregistrationOnly.getMobile_no()
				.replaceFirst("^[0|+]*", ""));

		if (Validator.isNotNumber(forregistrationOnly.getMobile_no())
				|| forregistrationOnly.getMobile_no().length() != 10) {
			errorMessage.add("Mobile Number is not proper.");
		}
		if (ListOfValuesManager.testUniqueMobileNumber(forregistrationOnly
				.getMobile_no())) {
			errorMessage
					.add("Mobile Number is alrady registered. Please enter another mobile number.");
		}

		String emailTest = ListOfValuesManager
				.testEmailAllStatus(forregistrationOnly.getEmail_id());

		if (emailTest.equalsIgnoreCase("P")) {
			errorMessage.add("Email ID is pending for approval.");
			forregistrationOnly = new UserRegistrationDTO();
			return "clear";
		} else if (emailTest.equalsIgnoreCase("I")) {
			errorMessage.add("Email ID de activated. Please contact admin.");
			forregistrationOnly = new UserRegistrationDTO();
			return "clear";
		} else if (emailTest.equalsIgnoreCase("A")) {
			errorMessage.add("Email ID already registered.");
			forregistrationOnly = new UserRegistrationDTO();
			return "clear";
		} else if (emailTest.equalsIgnoreCase("N") && errorMessage.size() == 0) {

			Connection con = (Connection) ListOfValuesManager
					.getBroadConnection();

			try {
				forregistrationOnly.setIsVerified('0');
				forregistrationOnly.setVerificationCode(ServerUtility
						.randomString(15));
				forregistrationOnly.setStatus("P");
				forregistrationOnly.setSignupType(3);
				forregistrationOnly.setTotalCredit(50);
				forregistrationOnly.setTotalGreenMiles(0);

				CityDTO cityDto = ListOfValuesManager.getCity(
						forregistrationOnly.getCity(),
						forregistrationOnly.getState());
				forregistrationOnly.setCityId(cityDto.getCityId());

				forregistrationOnly = ListOfValuesManager.getUserRegistration(
						"findByDTO", forregistrationOnly,
						forregistrationOnly.getEmail_id(), con);
				userPreferences = new UserPreferencesDTO();
				userPreferences.setUserId(Integer.parseInt(forregistrationOnly
						.getId()));

				if (forregistrationOnly.getDefaultTimeSlice() != null) {
					String[] minuteSlice = forregistrationOnly
							.getDefaultTimeSlice().split(":");
					if (minuteSlice.length > 1) {
						int minuteCount = Integer.parseInt(minuteSlice[0]) * 60; // convert
																					// hour
																					// to
																					// minute
						minuteCount += Integer.parseInt(minuteSlice[1]);
						userPreferences.setMaxWaitTime(minuteCount);
					}
				} else {
					userPreferences.setMaxWaitTime(10);
				}
				userPreferences = ListOfValuesManager.getUserPreferencesSave(
						userPreferences, con);
				/*
				 * String parts1[] = autoNonTaxiCircleValue.trim().split("-");
				 * String parts2[] = autoTaxiCircleValue.trim().split("-");
				 * 
				 * try{ autoNonTaxiCircleValue = parts1[parts1.length - 1];
				 * autoTaxiCircleValue = parts2[parts2.length - 1]; } catch
				 * (ArrayIndexOutOfBoundsException e) {
				 * 
				 * }
				 */

				autoNonTaxiCircleValue = autoNonTaxiCircleValue.trim();
				autoTaxiCircleValue = autoTaxiCircleValue.trim();
				if (forregistrationOnly.getTravel().equalsIgnoreCase("T")
						&& Validator.isNumberZeroNotAlloed(autoTaxiCircleValue)) {
					userRegisterCircle = Integer.parseInt(autoTaxiCircleValue);
				} else if (!forregistrationOnly.getTravel().equalsIgnoreCase(
						"T")
						&& Validator
								.isNumberZeroNotAlloed(autoNonTaxiCircleValue)) {
					userRegisterCircle = Integer
							.parseInt(autoNonTaxiCircleValue);
				}

				if (userRegisterCircle > 0) {
					circleMemberDTO.setCircleid(String
							.valueOf(userRegisterCircle));
					circleMemberDTO.setUserid(forregistrationOnly.getId());
					circleMemberDTO.setRequestUserId(forregistrationOnly
							.getId());
					circleMemberDTO = ListOfValuesManager.getJoinCircle(
							circleMemberDTO, forregistrationOnly.getId(), con);
					circleMemberDTO = new CircleMemberDTO();
				}
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				rollbackTest = true;
			} finally {
				if (rollbackTest) {
					try {
						con.rollback();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
					forregistrationOnly.setStatus("Fault");
					rollbackTest = false;
					userMessageDTO = new MessageBoardDTO();
					errorMessage
							.add("There is some problem in registration of Email ID.");
					return null;
				} else {
					try {
						con.commit();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
					successMessage
							.add("Email ID registered successfully. You will get verification email on registered email ID. Please verify your account.");
				}
				autoTaxiCircleValue = null;
				autoNonTaxiCircleValue = null;
				userRegisterCircle = 0;
			}

			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setEmailSubject(Messages
					.getValue("subject.register"));
			userMessageDTO
					.setMessage(Messages.getValue("user.register.intro",
							new Object[] { forregistrationOnly.getFirst_name(),
									forregistrationOnly.getPassword(),
									forregistrationOnly.getEmail_id() })
							+ Messages
									.getValue(
											"user.register.verify",
											new Object[] { Messages
													.getValue(
															"link.user.verify",
															new Object[] {
																	forregistrationOnly
																			.getEmail_id(),
																	URLEncoder
																			.encode(forregistrationOnly
																					.getVerificationCode()) }) })
							+ "&nbsp;&nbsp;" + Messages.getValue("site.login"));
			userMessageDTO.setToMember(Integer.parseInt(forregistrationOnly
					.getId()));
			userMessageDTO.setMessageChannel("E");

			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);

			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setMessage(Messages.getValue("sms.register",
					new Object[] { forregistrationOnly.getFirst_name(),
							forregistrationOnly.getEmail_id() }));
			userMessageDTO.setToMember(Integer.parseInt(forregistrationOnly
					.getId()));
			userMessageDTO.setMessageChannel("S");

			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);

			rollbackTest = false;
			userMessageDTO = new MessageBoardDTO();
			// userRegistrationDTO = forregistrationOnly;
			forregistrationOnly = new UserRegistrationDTO();
		}
		// Uncomment when you want direct login.
		// validateUser();
		// return "userRegister";

		return "userPending";
	}

	public void sendMessage() {
		String[] part = circleAffiliationsDTO.getCircleAffilicatedCircleId()
				.split("-");
		int affiliatedCircleId = 0;
		clearScreenMessage();
		if (part.length > 1 && part[1] != null && Integer.parseInt(part[1]) > 0) {
			affiliatedCircleId = Integer.parseInt(part[1]);

			CircleDTO dto = new CircleDTO();
			dto = ListOfValuesManager
					.getCircleDtoByCircleId(affiliatedCircleId);
			// userMessageDTO.setReceiveUserId(""+dto.getCircleOwner_Member_Id_P());
			// userMessageDTO.setSentUserId(userRegistrationDTO.getId());
			// userMessageDTO.setEmailSent('N');
			// userMessageDTO.setSubject(Messages.getValue("taxi.circle.subject"));
			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setToMember(dto.getCircleOwner_Member_Id_P());
			userMessageDTO.setCreatedBy(Integer.parseInt(userRegistrationDTO
					.getId()));
			userMessageDTO.setSubmittedBy(Integer.parseInt(userRegistrationDTO
					.getId()));
			userMessageDTO.setEmailSubject(Messages
					.getValue("taxi.circle.subject"));
			userMessageDTO.setMessageChannel("E");
			userMessageDTO.setMessage(getMessage());
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);
			setMessage("");
			successMessage
					.add("Message sent successfully to the circle members.");
		} else {
			errorMessage
					.add("Please select a Circle and then affiliated taxi circle.");
		}
	}

	public void companyRegister() {
		clearScreenMessage();
		try {
			if (listofCompanyForLoginUser.size() == 0) {
				companyRegisterDTO.setUserID(userRegistrationDTO.getId());
				companyRegisterDTO = ListOfValuesManager
						.getCompanyRegistration("findByDTO",
								companyRegisterDTO, null);
				successMessage.add("Company added successfully.");
			} else {
				companyRegisterDTO.setUserID(userRegistrationDTO.getId());
				companyRegisterDTO = ListOfValuesManager
						.getCompanyUpdateByUserId(companyRegisterDTO);
				successMessage.add("Company updated successfully.");
			}
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		}
		if (rollbackTest) {
			errorMessage.add("There is some issue in add/update of company.");
		}
		rollbackTest = false;
		findListofCompanyForLoginUser();
		// companyRegisterDTO = new CompanyRegisterDTO();
	}

	public String registerCompany() {
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		try {
			companyRegisterDTO.setUserID(userRegistrationDTO.getId());
			companyRegisterDTO = ListOfValuesManager.getCompanyRegistration(
					"findByDTO", companyRegisterDTO, con);
			List<CircleDTO> circleList;
			circleList = ListOfValuesManager.getallRegisteredCircleListed();
			CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
			int check = 0;
			for (int i = 0; i < circleList.size(); i++) {
				if (circleList.get(i).getName()
						.equalsIgnoreCase(companyRegisterDTO.getCompanyName())) {
					check = 1;
					CircleDTO circleDTO = new CircleDTO();
					circleDTO.setCompanyID(companyRegisterDTO.getCompanyID());
					circleDTO.setCircleOwner_Member_Id_P(Integer
							.valueOf(userRegistrationDTO.getId()));
					circleDTO.setName("circle_"
							+ companyRegisterDTO.getCompanyName());
					circleDTO = ListOfValuesManager.getregisterCircle(
							circleDTO, con);
					circleOwnerDTO.setCircleID(String.valueOf(circleDTO
							.getCircleID()));
					circleOwnerDTO.setUserID(userRegistrationDTO.getId());
					circleOwnerDTO = ListOfValuesManager
							.getregisterCircleOwner(circleOwnerDTO,
									userRegistrationDTO.getId(), con);
				}
			}
			if (check == 0) {
				CircleDTO circleDTO = new CircleDTO();
				circleDTO.setCompanyID(companyRegisterDTO.getCompanyID());
				circleDTO.setCircleOwner_Member_Id_P(Integer
						.valueOf(userRegistrationDTO.getId()));
				circleDTO.setDate_of_creation(ListOfValuesManager
						.getcurrentDate());
				circleDTO.setName(companyRegisterDTO.getCompanyName());
				circleDTO = ListOfValuesManager.getregisterCircle(circleDTO,
						con);
				circleOwnerDTO.setCircleID(String.valueOf(circleDTO
						.getCircleID()));
				circleOwnerDTO.setUserID(userRegistrationDTO.getId());
				circleOwnerDTO = ListOfValuesManager.getregisterCircleOwner(
						circleOwnerDTO, userRegistrationDTO.getId(), con);
			}

		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			}
			rollbackTest = false;
		}
		findListofCompanyForLoginUser();
		// companyRegisterDTO = new CompanyRegisterDTO();
		// userRegistrationDTO.setCompanyName(companyRegisterDTO.getCompanyName());
		return "registerCompany";
	}

	public String editCompany() {
		String index = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		index = (String) requestMap.get("index");

		companyRegisterDTO = listofCompanyForLoginUser.get(Integer
				.valueOf(index));

		return "editCompany";
	}

	public void editCompanyValue() {
		companyRegisterDTO = ListOfValuesManager
				.getUpdateCompany(companyRegisterDTO);
		rollbackTest = false;
		findListofCompanyForLoginUser();
		// companyRegisterDTO = new CompanyRegisterDTO();
		// return "editValue";
	}

	public void userRatingUpdate() {
		userRegistrationDTO = ListOfValuesManager
				.getAverageRatingForUser(userRegistrationDTO);

	}

	public String validateUser() {
		userRegistrationDTO = ListOfValuesManager
				.getValidateUser(userRegistrationDTO);

		if (userRegistrationDTO.getId() != null)
			userPreferences = ListOfValuesManager.getUserPreferences(Integer
					.parseInt(userRegistrationDTO.getId()));
		clearScreenMessage();
		if (userRegistrationDTO.getEmail_id() == null
				&& userRegistrationDTO.getPassword() == null) {
			errorMessage.add(Messages.getValue("error.login.invalid"));
			// com.hopon.utils.ErrorMessages.getErrorMessage(ValidationManager.getBundle().getString("error.login.invalid"));
			userRegistrationDTO.setPassword("");
			return null;
		}
		if (userRegistrationDTO.getStatus() != null
				&& userRegistrationDTO.getStatus().equals("I")) {
			errorMessage.add(Messages
					.getValue("error.login.accountStatusClosed"));
			userRegistrationDTO.setPassword("");
			return null;
		}
		if (userRegistrationDTO.getStatus() != null
				&& userRegistrationDTO.getStatus().equals("P")) {
			errorMessage.add(Messages
					.getValue("error.login.accountStatusPending"));
			userRegistrationDTO.setPassword("");
			return null;
		}
		/*
		 * Here is code for non verified account.
		 * if(userRegistrationDTO.getStatus() != null &&
		 * userRegistrationDTO.getStatus().equals("2")) {
		 * errorMessage.add(Messages
		 * .getValue("error.login.accountStatusPending")); return null; }
		 */
		if (userRegistrationDTO.getTravel().equalsIgnoreCase("T")) {
			giveRide();
		}
		HttpSession currentSession = ServerUtility.getSession();
		// set session attribute e.g "LoggedIn='true' " if user is logged in

		currentSession.setAttribute("LoggedIn", "true");
		currentSession.setAttribute("user_role",
				userRegistrationDTO.getTravel());
		currentSession.setAttribute("userId", userRegistrationDTO.getId());
		currentSession.setAttribute("userName",
				userRegistrationDTO.getFirst_name());
		currentSession.setAttribute("emailId",
				userRegistrationDTO.getEmail_id());
		allListedCircle();
		rideManagementList();
		allCompleateRideList();
		allCircleForLoginUser();
		allCircleMemberForLoginUserList.clear();
		allPendingCircleMemberList.clear();
		messageForLoginUser();
		vehicleList();
		findListofCompanyForLoginUser();
		currentSession.setAttribute("unreadMessageRowNo", 1);
		allLoginUserMessageList();
		populatePopupMessage();
		populateApprover();
		vehicleMasterDTO = new VehicleMasterDTO();

		/* Here write down the code url redirection for post-Login page. */
		List<LoginPageDTO> dtos = new ArrayList<LoginPageDTO>();
		dtos.addAll(ListOfValuesManager.getLoginPagesByUserId(Integer
				.parseInt(userRegistrationDTO.getId())));
		for (LoginPageDTO dto : dtos) {
			if (dto.getPageCode().equalsIgnoreCase(
					Messages.getValue("page.code.change.password").trim())) {
				return Messages.getValue("page.code.change.password").trim();
			}
		}
		for (LoginPageDTO dto : dtos) {
			if (dto.getPageCode().equalsIgnoreCase(
					Messages.getValue("page.code.preference").trim())) {
				return Messages.getValue("page.code.preference").trim();
			}
		}
		validateUserPayment();
		paymentTransaction();
		populatePaymentPlan();
		return userRegistrationDTO.getTravel();
	}

	public void populatePaymentPlan() {
		userPaymentPlanList.clear();
		userPaymentPlanList.addAll(ListOfValuesManager
				.fetchPaymentPlanForLoginUser(userRegistrationDTO.getId()));
	}

	public String signOut() {
		userRegistrationDTO = new UserRegistrationDTO();

		HttpSession currentSession = ServerUtility.getSession();
		currentSession.setAttribute("LoggedIn", null);
		currentSession.setAttribute("user_role", null);
		currentSession.setAttribute("readMessageRowNo", null);
		currentSession.setAttribute("unreadMessageRowNo", null);
		listofCompanyForLoginUser.clear();
		allCircleListByName.clear();
		findListofCompanyForLoginUser();
		currentSession.invalidate();
		return "signOut";
	}

	public String userAcountClose() {
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		try {
			userRegistrationDTO = ListOfValuesManager.getCloseUserAcount(
					userRegistrationDTO, userRegistrationDTO.getId(), con);
			// circle table make circle inactive created by user.
			ListOfValuesManager.makeCircleInactiveForUser(userRegistrationDTO,
					con);
			// circle_members table make status Inactive for user.
			ListOfValuesManager.makeCircleMemberInactiveForUser(
					userRegistrationDTO, userRegistrationDTO.getId(), con);
			// circle_owner make status 2 for user.
			ListOfValuesManager.makeCircleOwnerForUser(userRegistrationDTO,
					userRegistrationDTO.getId(), con);
			// make "ride_seeker_details" table status 2 for user_id.
			ListOfValuesManager.makeRideSeekerCancelForUser(
					userRegistrationDTO, con);
			// need discussion In ride_management table.
			ListOfValuesManager.makeRideCancelForUser(userRegistrationDTO, con);

		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			}
			rollbackTest = false;
		}
		signOut();
		return "close";
	}

	public String rideManagementList() {

		rideManagementList = ListOfValuesManager
				.getRideManagementList(userRegistrationDTO.getId());
		List<RideManagementDTO> listNew = new ArrayList<RideManagementDTO>();
		for (RideManagementDTO dto : rideManagementList) {
			if (!dto.getStatus().equalsIgnoreCase("T")
					&& userRegistrationDTO.getTravel().equalsIgnoreCase("T")) {
				continue;
			}
			if (!dto.getStatus().equalsIgnoreCase("A")
					&& userRegistrationDTO.getTravel().equalsIgnoreCase("P")) {
				continue;
			}
			if (!dto.getStatus().equalsIgnoreCase("A")
					&& userRegistrationDTO.getTravel().equalsIgnoreCase("B")) {
				continue;
			}
			listNew.add(dto);
		}
		rideManagementList.clear();
		rideManagementList.addAll(listNew);
		listNew.clear();

		rideSeekerList = ListOfValuesManager
				.getAllRideSeekerList(userRegistrationDTO.getId());
		List<RideSeekerDTO> temp1 = new ArrayList<RideSeekerDTO>();
		recurringRideSeekerList.clear();
		for (RideSeekerDTO dto : rideSeekerList) {
			if (dto.getRecurring().equals("Y")) {
				recurringRideSeekerList.add(dto);
			} else {
				temp1.add(dto);
			}
		}
		rideSeekerList.clear();
		rideSeekerList.addAll(temp1);

		pendingRideTest = false;
		for (RideSeekerDTO dto : rideSeekerList) {
			if (dto.getStatus().equals("A") || dto.getStatus().equals("I")) {
				pendingRideTest = true;
				break;
			}
		}
		Collections.reverse(rideManagementList);
		Collections.reverse(rideSeekerList);

		return "rideManagementList";
	}

	public String allListedCircle() {
		allCircleList = ListOfValuesManager
				.getallCircleListed(userRegistrationDTO.getId());

		allMemberCircleList = ListOfValuesManager
				.getallMemberCircleListed(userRegistrationDTO.getId());

		return "circleList";
	}

	/*
	 * public List<String> getAllCompanyList(){ List<String> allCompanyList =
	 * new ArrayList<String>(); List<CompanyRegisterDTO> allCompany = null; try
	 * { allCompany = ListOfValuesManager.getCompanyList();
	 * allCompanyList.add("NEW"); for (int i = 0;i<allCompany.size() ; i++){
	 * allCompanyList.add(allCompany.get(i).getCompanyName()); }
	 * 
	 * } catch (ConnectionException e) { // TODO Auto-generated catch block }
	 * catch (ConfigurationException e) { // TODO Auto-generated catch block }
	 * 
	 * return allCompanyList;
	 * 
	 * }
	 */
	public void fetchCircleList() {
		taxiCircleList = new ArrayList<CircleDTO>();
		nonTaxiCircleList = new ArrayList<CircleDTO>();
		List<CircleDTO> circleList = null;
		circleList = ListOfValuesManager.getallRegisteredCircleListed();

		for (CircleDTO dto : circleList) {
			if (dto.isTaxiCircle()) {
				taxiCircleList.add(dto);
			} else {
				nonTaxiCircleList.add(dto);
			}
		}
	}

	public List<SelectItem> getAllTaxiCilcleDropDownList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		circleList = ListOfValuesManager.getallRegisteredCircleListed();
		for (int i = 0; i < circleList.size(); i++) {
			if (circleList.get(i).isTaxiCircle())
				list.add(new SelectItem(circleList.get(i).getCircleID(),
						circleList.get(i).getName()));
		}
		return list;
	}

	public List<SelectItem> getAllNonTaxiCilcleDropDownList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		circleList = ListOfValuesManager.getallRegisteredCircleListed();
		for (int i = 0; i < circleList.size(); i++) {
			if (!circleList.get(i).isTaxiCircle())
				list.add(new SelectItem(circleList.get(i).getCircleID(),
						circleList.get(i).getName()));
		}
		return list;
	}

	public void registerRide() {

		if (userCirclePaymentPending)
			return;
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		String ride = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		ride = (String) requestMap.get("ride");
		clearScreenMessage();

		if (this.recurring && rideRegistered.getEndDate1() == null) {
			errorMessage.add("Please select proper end date.");
		}
		if (rideRegistered.getFromAddress1() == null
				|| rideRegistered.getFromAddress1().trim().length() == 0) {
			errorMessage.add("Please enter source address.");
		}
		if (rideRegistered.getFullDay().equalsIgnoreCase("N")
				&& (rideRegistered.getToAddress1() == null || rideRegistered
						.getToAddress1().trim().length() == 0)) {
			errorMessage.add("Please enter destination address.");
		}
		if (rideManager.equals("giveRide")
				&& Integer.parseInt(rideRegistered.getVehicleID()) <= 0) {
			errorMessage.add("Please select Vehicle.");
		}

		if (ride != null) {
			rideManager = ride;
		}
		try {
			if (errorMessage.size() > 0)
				throw new ControllerException();
			rideRegistered.setUserID(userRegistrationDTO.getId());
			DateFormat dateFormat = new SimpleDateFormat(
					ApplicationUtil.datePattern3);

			Calendar cal = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();

			try {
				rideRegistered.setStartDate(new SimpleDateFormat(
						ApplicationUtil.datePattern4).parse(rideRegistered
						.getStartDate1()));
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				errorMessage.add("Please select proper start date.");
				throw new ControllerException();
			}
			cal.setTime(rideRegistered.getStartDate());

			try {
				if (rideRegistered.getEndDate1() != null
						&& rideRegistered.getEndDate1().length() > 0) {
					rideRegistered.setEndDate(new SimpleDateFormat(
							ApplicationUtil.datePattern4).parse(rideRegistered
							.getEndDate1()));
				}
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}
			long dateDiff = 0;
			if (this.recurring) {
				Calendar startDateCalendar = Calendar.getInstance();
				startDateCalendar.setTime(rideRegistered.getStartDate());
				Calendar endDateCalendar = Calendar.getInstance();
				endDateCalendar.setTime(rideRegistered.getEndDate());
				dateDiff = (endDateCalendar.getTimeInMillis() - startDateCalendar
						.getTimeInMillis()) / (1000 * 60 * 60 * 24);
			}
			if (this.recurring && dateDiff < 0) {
				errorMessage
						.add("Please select end date after the start date.");
				throw new ControllerException();
			}
			if (this.recurring && dateDiff > 60) {
				errorMessage
						.add("Recurring ride can be booked only for 2 months from the start date.");
				throw new ControllerException();
			}
			if (rideRegistered.getEndDate() != null) {
				cal1.setTime(rideRegistered.getEndDate());
				rideRegistered
						.setEnddateValue(dateFormat.format(cal1.getTime()));
			}
			rideRegistered.setStartdateValue(dateFormat.format(cal.getTime()));

			/*
			 * float startPointLat = rideRegistered.getStartPointLatitude();
			 * float startPointLng = rideRegistered.getStartPointLongitude();
			 * float endPointLat = rideRegistered.getEndPointLatitude(); float
			 * endPointLng = rideRegistered.getEndPointLongitude();
			 */

			clearScreenMessage();
			if (frequencyDTO.getFrequency() == null
					|| frequencyDTO.getFrequency().size() == 0) {
				List<String> value = new ArrayList<String>();
				String putValue = "Once";
				value.add(putValue);
				frequencyDTO.setFrequency(value);
			}
			if (rideManager.equals("takeRide")) {
				if (ListOfValuesManager
						.checkRideSeekerDuplicacy(rideRegistered)) {
					errorMessage.add("Same Ride already exist.");
					throw new ControllerException();
					// return "takeRide";
				} else {
					rideRegistered.setRideID(null);
					rideRegistered.setCreatedBy(userRegistrationDTO.getId());
					rideRegistered.setCreated_dt(ListOfValuesManager
							.getCurrentTimeStampDate());
					if (this.recurring)
						rideRegistered.setRecurring("Y");
					else
						rideRegistered.setRecurring("N");

					/*
					 * For full day request make start point and end point same.
					 */

					if (rideRegistered.getFullDay().equalsIgnoreCase("Y")) {
						rideRegistered.setEndPointLatitude(rideRegistered
								.getStartPointLatitude());
						rideRegistered.setEndPointLongitude(rideRegistered
								.getStartPointLongitude());
						rideRegistered.setToAddress1(rideRegistered
								.getFromAddress1());
						rideRegistered.setToAddressCity(rideRegistered
								.getFromAddressCity());
						rideRegistered.setToAddressPin(rideRegistered
								.getFromAddressPin());
						rideRegistered.setToAddressTag("");
					}

					List windowCalculation;
					try {
						windowCalculation = ApplicationUtil
								.calculateTimeWindowSettings(
										rideRegistered.getFromAddress1(), "",
										rideRegistered.getToAddress1(),
										userPreferences.getMaxWaitTime(),
										rideRegistered.getStartdateValue());

						if (windowCalculation.size() > 0) {
							rideRegistered.setStartdateValue(windowCalculation
									.get(1).toString());
							rideRegistered.setStartDateEarly(windowCalculation
									.get(1).toString());
							rideRegistered.setStartDateLate(windowCalculation
									.get(2).toString());
							rideRegistered.setEndDateEarly(windowCalculation
									.get(3).toString());
							rideRegistered.setEndDateLate(windowCalculation
									.get(4).toString());
							float distance = Integer.parseInt(windowCalculation
									.get(5).toString()) / 1000;
							rideRegistered.setRideDistance(distance);
							if(rideRegistered.isSharedTaxi()==true){
								System.out.println("printing five rupees :"+ distance);
								rideRegistered.setRideCost(distance
										* Float.parseFloat(Messages.getValue(
												"ride.perkm.charge").trim()) + "");
								System.out.println("this is for 5rupee: "+ Messages.getValue(
										"ride.perkm.charge"));
							}else{
								rideRegistered.setRideCost(distance
										* Float.parseFloat(Messages.getValue(
												"ride.perkm.sharecharge").trim()) + "");
								System.out.println("inside default amnt 12for carpool:"+ Messages.getValue(
										"ride.perkm.sharecharge"));
							}
						}
					} catch (IOException e) {
						errorMessage
								.add("There is some problem in calculating time for ride.");
						throw new ControllerException();
					} catch (JSONException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
						errorMessage
								.add("There is some problem in calculating time for ride.");
						throw new ControllerException();
					}

					// if no approver status = 1, if 1 approver status = O, if
					// approver = 2 status = T
					// check hoponid exist in db or not.
					// If exist send email, sms and messageboard
					// If does not exist send email only
					// in email make link to approve.
					ApproverDTO approverDtoTemp = ListOfValuesManager
							.findApproverById(rideRegistered.getApproverID());
					if (rideRegistered.getApproverID() > 0) {
						if (approverDtoTemp.getHoponId() != null
								&& approverDtoTemp.getHoponId().length() > 0) {
							rideRegistered.setStatus("O");
							if (approverDtoTemp.getHoponId2() != null
									&& approverDtoTemp.getHoponId2().length() > 0) {
								rideRegistered.setStatus("T");
							}
						}
					}

					if (rideRegistered.getCircleId() <= 0)
						rideRegistered.setCircleId(allMemberCircleList.get(0)
								.getCircleID());
					rideRegistered = ListOfValuesManager.getRideSeekerEntery(
							"findByDTO", rideRegistered, con);

					// Here code is to make entry in paymentTxn table.

					PaymentTxnsDTO paymentTxnsDTO = new PaymentTxnsDTO();
					paymentTxnsDTO.setCreatedBy(Integer
							.parseInt(userRegistrationDTO.getId()));
					paymentTxnsDTO.setFromPayer(Integer
							.parseInt(userRegistrationDTO.getId()));
					paymentTxnsDTO.setToPayee(100);
					paymentTxnsDTO.setTripDetails("");
					paymentTxnsDTO.setSeekerId(Integer.parseInt(rideRegistered
							.getRideID()));
					paymentTxnsDTO.setCreatedDate(ApplicationUtil
							.currentTimeStamp());
					paymentTxnsDTO.setDist(rideRegistered.getRideDistance());
					paymentTxnsDTO.setAmount(Float.parseFloat(rideRegistered
							.getRideCost()));
					ListOfValuesManager.paymentTxnInsert(paymentTxnsDTO, con);

					if (rideRegistered.getApproverID() > 0) {
						if (approverDtoTemp.getHoponId() != null
								&& approverDtoTemp.getHoponId().length() > 0) {
							String approveLink = Messages.getValue(
									"ride.approve",
									new Object[] {
											rideRegistered.getRideID(),
											URLEncoder.encode(approverDtoTemp
													.getVerificationCode()),
											approverDtoTemp.getId(),
											approverDtoTemp.getHoponId() });
							String rejectLink = Messages.getValue(
									"ride.reject",
									new Object[] {
											rideRegistered.getRideID(),
											URLEncoder.encode(approverDtoTemp
													.getVerificationCode()),
											approverDtoTemp.getId(),
											approverDtoTemp.getHoponId() });
							String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "
									+ approverDtoTemp.getbCode()
									+ "<br>Name: "
									+ userRegistrationDTO.getFirst_name()
									+ "<br>Request ID: "
									+ rideRegistered.getRideID()
									+ "<br>From: "
									+ rideRegistered.getFromAddress1()
									+ "<br>To: "
									+ rideRegistered.getToAddress1()
									+ "<br>Date Time: "
									+ rideRegistered.getStartdateValue()
									+ "<br>Frequency: "
									+ frequencyDTO.getFrequency().toString()
									+ "<br> "
									+ approveLink
									+ " &nbsp;&nbsp;"
									+ rejectLink + "";
							if (ListOfValuesManager.testEmail(approverDtoTemp
									.getHoponId())) {
								UserRegistrationDTO dtoTemp = null;
								dtoTemp = ListOfValuesManager
										.findUserByEmail(approverDtoTemp
												.getHoponId());
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO.setMessage(messageContent);
								userMessageDTO.setEmailSubject(Messages
										.getValue("subject.ride.approval"));
								userMessageDTO.setMessageChannel("E");
								userMessageDTO.setToMember(Integer
										.parseInt(dtoTemp.getId()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"ride.option.msgBoard",
														new Object[] {
																approverDtoTemp
																		.getbCode(),
																rideRegistered
																		.getRideID(),
																userRegistrationDTO
																		.getFirst_name(),
																rideRegistered
																		.getFromAddress1(),
																rideRegistered
																		.getToAddress1(),
																rideRegistered
																		.getStartdateValue() }));
								userMessageDTO.setMessageChannel("M");
								userMessageDTO.setToMember(Integer
										.parseInt(dtoTemp.getId()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"ride.option.sms",
														new Object[] {
																approverDtoTemp
																		.getbCode(),
																rideRegistered
																		.getRideID(),
																userRegistrationDTO
																		.getFirst_name(),
																rideRegistered
																		.getFromAddress1()
																		.substring(
																				0,
																				(rideRegistered
																						.getFromAddress1()
																						.length() >= 20) ? 20
																						: rideRegistered
																								.getFromAddress1()
																								.length()),
																rideRegistered
																		.getToAddress1()
																		.substring(
																				0,
																				(rideRegistered
																						.getToAddress1()
																						.length() >= 20) ? 20
																						: rideRegistered
																								.getToAddress1()
																								.length()),
																rideRegistered
																		.getStartdateValue() }));
								userMessageDTO.setMessageChannel("S");
								userMessageDTO.setToMember(Integer
										.parseInt(dtoTemp.getId()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
							} else {
								EmailDTO emaildto = new EmailDTO();
								emaildto.setReceiverEmailId(approverDtoTemp
										.getHoponId());
								emaildto.setSenderEmailId(userRegistrationDTO
										.getEmail_id());
								emaildto.setSubject(Messages
										.getValue("subject.ride.approval"));
								emaildto.setEmailTemplateBody(Messages
										.getValue(
												"email.template2",
												new Object[] {
														Messages.getValue("subject.ride.approval"),
														messageContent }));
								MailService.sendMail(emaildto);
							}
						}
					}

					frequencyDTO.setRideSeekerId(rideRegistered.getRideID());
				}
			}

			if (rideManager.equals("giveRide")) {
				if (ListOfValuesManager.checkRideDuplicacy(rideRegistered)) {
					errorMessage.add("Same Ride already exist.");
					// return "giveRide";
					throw new ControllerException();
				} else {
					rideRegistered.setRideID(null);
					rideRegistered.setCreatedBy(userRegistrationDTO.getId());
					rideRegistered.setCreated_dt(ListOfValuesManager
							.getCurrentTimeStampDate());

					List windoqCalculation;
					try {
						windoqCalculation = ApplicationUtil
								.calculateTimeWindowSettings(
										rideRegistered.getFromAddress1(), "",
										rideRegistered.getToAddress1(),
										userPreferences.getMaxWaitTime(),
										rideRegistered.getStartdateValue());

						if (windoqCalculation.size() > 0) {
							rideRegistered.setStartdateValue(windoqCalculation
									.get(1).toString());
							rideRegistered.setStartDateEarly(windoqCalculation
									.get(1).toString());
							rideRegistered.setStartDateLate(windoqCalculation
									.get(2).toString());
							rideRegistered.setEndDateEarly(windoqCalculation
									.get(3).toString());
							rideRegistered.setEndDateLate(windoqCalculation
									.get(4).toString());
							float distance = Integer.parseInt(windoqCalculation
									.get(5).toString()) / 1000;
							rideRegistered.setRideDistance(distance);
							rideRegistered.setRideCost((distance * 5) + "");
						}
					} catch (IOException e) {
						errorMessage
								.add("There is some problem in calculating time for ride.");
						throw new ControllerException();
					} catch (JSONException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
						errorMessage
								.add("There is some problem in calculating time for ride.");
						throw new ControllerException();
					}

					rideRegistered = ListOfValuesManager.getRideEntery(
							"findByDTO", rideRegistered, con);
					frequencyDTO
							.setRideManagementId(rideRegistered.getRideID());
				}
			}

			frequencyDTO.setTime(rideRegistered.getStartDate());
			frequencyDTO.setStartDate(rideRegistered.getStartdateValue());
			frequencyDTO.setEndDate(rideRegistered.getEnddateValue());

			frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO",
					frequencyDTO, con);

			if (rideRegistered.getFromAddressTag().equalsIgnoreCase("new")
					&& rideRegistered.getFromAddressTagLimit() != null
					&& rideRegistered.getFromAddressTagLimit() != "") {

				FavoritePlacesDTO dto = new FavoritePlacesDTO();

				dto.setAddress(rideRegistered.getFromAddress1());
				// dto.setRideID(rideRegistered.getRideID());
				dto.setUserID(userRegistrationDTO.getId());
				dto.setCity(rideRegistered.getFromAddressCity());
				dto.setPin(rideRegistered.getFromAddressPin());
				dto.setTagtype(rideRegistered.getFromAddressTagLimit());
				dto.setLatitude(rideRegistered.getStartPointLatitude());
				dto.setLongitude(rideRegistered.getStartPointLongitude());
				dto.setBoundtype("1");
				dto = ListOfValuesManager.getfavoritePlaces(dto, con);
				HttpSession currentSession = ServerUtility.getSession();
				currentSession.removeAttribute("allPlaces");
			}
			if (rideRegistered.getToAddressTag().equalsIgnoreCase("new")
					&& rideRegistered.getToAddressTagLimit() != null
					&& rideRegistered.getToAddressTagLimit() != "") {

				FavoritePlacesDTO dto = new FavoritePlacesDTO();

				dto.setAddress(rideRegistered.getToAddress1());
				// dto.setRideID(rideRegistered.getRideID());
				dto.setUserID(userRegistrationDTO.getId());
				dto.setCity(rideRegistered.getToAddressCity());
				dto.setPin(rideRegistered.getToAddressPin());
				dto.setTagtype(rideRegistered.getToAddressTagLimit());
				dto.setLatitude(rideRegistered.getEndPointLatitude());
				dto.setLongitude(rideRegistered.getEndPointLongitude());
				dto.setBoundtype("2");
				dto = ListOfValuesManager.getfavoritePlaces(dto, con);
				HttpSession currentSession = ServerUtility.getSession();
				currentSession.removeAttribute("allPlaces");
			}

		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} catch (ControllerException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			}
			rollbackTest = false;
		}
		rideRegistered.setSharedTaxi(false);

		rideRegistered = new RideManagementDTO();
		frequencyDTO = new FrequencyDTO();
		// rideManagementList();
		// return "rideRegistered";
	}

	public void clearNewRide() {
		rideRegistered = new RideManagementDTO();
		frequencyDTO = new FrequencyDTO();
		this.setRecurring(false);
		// return "clear";
	}

	public void registerVehicleMaster() {

		try {
			vehicleMasterDTO.setUserID(userRegistrationDTO.getId());
			vehicleMasterDTO = ListOfValuesManager.getVehicleMaster(
					"findByDTO", vehicleMasterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}

		rollbackTest = false;
		vehicleList();
		vehicleMasterDTO = new VehicleMasterDTO();
		// return "vehicleegister";
	}

	public String allCompleateRideList() {
		allCompleateRideList = ListOfValuesManager
				.getAllPoolRequest(userRegistrationDTO.getId());

		allCompleateRideSeekerList = ListOfValuesManager
				.getAllPoolRequestSeeker(userRegistrationDTO.getId());
		/*
		 * for ride seeker status = '1' , is_result = 'Y' for ride giver status
		 * in '1', pool_request table For taxi status = 'T',( should be in
		 * pool_request table)
		 */

		allMsgBoardCompleateRideList.clear();
		for (PoolRequestsDTO dto : allCompleateRideList) {
			if (dto.getRideGiverNotes() != null
					&& dto.getRideGiverNotes().length() > 0) {
				continue;
			}
			// if((dto.getRequestStatus().equalsIgnoreCase("T") &&
			// (userRegistrationDTO.getTravel().equalsIgnoreCase("P") ||
			// userRegistrationDTO.getTravel().equalsIgnoreCase("B"))) ||
			// ((userRegistrationDTO.getTravel().equalsIgnoreCase("T") &&
			// dto.getRequestStatus().equalsIgnoreCase("1")))) {
			if (!dto.getRequestStatus().equalsIgnoreCase("T")
					&& userRegistrationDTO.getTravel().equalsIgnoreCase("T")) {
				continue;
			}
			if (!dto.getRequestStatus().equalsIgnoreCase("1")
					&& userRegistrationDTO.getTravel().equalsIgnoreCase("P")) {
				continue;
			}
			if (!dto.getRequestStatus().equalsIgnoreCase("1")
					&& userRegistrationDTO.getTravel().equalsIgnoreCase("B")) {
				continue;
			}
			allMsgBoardCompleateRideList.add(dto);
		}
		allMsgBoardCompleateRideSeekerList.clear();
		for (PoolRequestsDTO dto : allCompleateRideSeekerList) {
			if (dto.getRideTakerNotes() != null
					&& dto.getRideTakerNotes().length() > 0) {
				continue;
			}
			allMsgBoardCompleateRideSeekerList.add(dto);
		}
		return "compleateList";
	}

	public String takeRide() {
		rideManager = "takeRide";
		ridePicker = 1;
		return "takeRide";
	}

	public String giveRide() {

		rideManager = "giveRide";
		ridePicker = 2;
		return "giveRide";
	}

	public String clearLoginData() {

		userRegistrationDTO = new UserRegistrationDTO();
		return "clear";
	}

	public String clearCompanyData() {
		companyRegisterDTO = new CompanyRegisterDTO();
		return "clear";
	}

	public void processValueChange(AjaxBehaviorEvent event)
			throws AbortProcessingException {

		FavoritePlacesDTO dto = new FavoritePlacesDTO();
		String value = (String) ((UIInput) event.getSource()).getValue();
		if (value.equalsIgnoreCase("new")) {
			rideRegistered = new RideManagementDTO();
		}
		if (value != null) {
			for (int i = 0; i < allPlace.size(); i++) {
				String place = allPlace.get(i).getTagtype();
				if (place.equals(value)) {
					dto = allPlace.get(i);
					rideRegistered.setFromAddress1(dto.getAddress());
					rideRegistered.setFromAddressPin(dto.getPin());
					rideRegistered.setFromAddressCity(dto.getCity());
					rideRegistered.setStartPointLatitude(dto.getLatitude());
					rideRegistered.setStartPointLongitude(dto.getLongitude());
				}
			}
		}
	}

	public void processValueChange1(AjaxBehaviorEvent event)
			throws AbortProcessingException {

		FavoritePlacesDTO dto = new FavoritePlacesDTO();
		String value = (String) ((UIInput) event.getSource()).getValue();
		if (value.equalsIgnoreCase("new")) {
			rideRegistered = new RideManagementDTO();
		}
		if (value != null) {
			for (int i = 0; i < allPlace.size(); i++) {
				String place = allPlace.get(i).getTagtype();
				if (place.equals(value)) {
					dto = allPlace.get(i);
					rideRegistered.setToAddress1(dto.getAddress());
					rideRegistered.setToAddressPin(dto.getPin());
					rideRegistered.setToAddressCity(dto.getCity());
					rideRegistered.setEndPointLatitude(dto.getLatitude());
					rideRegistered.setEndPointLongitude(dto.getLongitude());
				}
			}
		}
	}

	public void processValueChange2(AjaxBehaviorEvent event)
			throws AbortProcessingException {
		allCircleForLoginUser();
		String value = String.valueOf(((UIInput) event.getSource()).getValue());
		List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
		for (int i = 0; i < allCircleMemberForLoginUserList.size(); i++) {
			if (allCircleMemberForLoginUserList.get(i).getCircleID()
					.equals(value)) {
				CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
				dto = allCircleMemberForLoginUserList.get(i);
				allCircleMemberForLoginUserList1.add(dto);
			}
		}
		allCircleMemberForLoginUserList.clear();
		allCircleMemberForLoginUserList
				.addAll(allCircleMemberForLoginUserList1);
		allCircleMemberForLoginUserList1.clear();

		List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
		for (int i = 0; i < allPendingCircleMemberList.size(); i++) {
			if (allPendingCircleMemberList.get(i).getCircleID().equals(value)) {
				CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
				dto = allPendingCircleMemberList.get(i);
				allPendingCircleMemberList1.add(dto);
			}
		}
		allPendingCircleMemberList.clear();
		allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
		allPendingCircleMemberList1.clear();
	}

	public void processValueChange3(AjaxBehaviorEvent event)
			throws AbortProcessingException {
		String circleId = String.valueOf(((UIInput) event.getSource())
				.getValue());
		gatherAffiliatedCircle(Integer.parseInt(circleId));
	}

	public void processValueChange4(AjaxBehaviorEvent event)
			throws AbortProcessingException {
		gatherAffiliatedCircle(manageRideFormDTO.getMyCircleId());
	}

	public void gatherDefaultcircleDTO() {
		if (taxiCircle.getCircleID() >= 0) {
			gatherAffiliatedCircle(taxiCircle.getCircleID());
		}
	}

	public void clearCircleForLoginUser() {
		allCircleMemberForLoginUserList.clear();
		circleDTO = new CircleDTO();
		circleOwnerManagerDTO = new CircleOwnerManagerDTO();
	}

	public String copyRideManager() {

		Connection con = (Connection) ListOfValuesManager.getBroadConnection();

		String rideManager = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		rideManager = (String) requestMap.get("rideManagement");

		try {
			DateFormat dateFormat = new SimpleDateFormat(
					ApplicationUtil.datePattern3);
			Calendar cal = Calendar.getInstance();
			// Calendar cal1 = Calendar.getInstance();

			cal.setTime(rideRegistered.getStartDate());
			// cal1.setTime(rideRegistered.getEndDate());

			rideRegistered.setStartdateValue(dateFormat.format(cal.getTime()));
			// rideRegistered.setEnddateValue(dateFormat.format(cal1.getTime()));
			if (rideManager.equalsIgnoreCase("rideManagementFrompool")) {
				rideRegistered.setRideID(null);
				rideRegistered.setUserID(userRegistrationDTO.getId());
				rideRegistered.setFromAddress1(poolRequestsDTO
						.getRideManagementFrom());

				// Calendar cal1 = Calendar.getInstance();

				cal.setTime(rideRegistered.getStartDate());
				rideRegistered.setStartdateValue(dateFormat.format(cal
						.getTime()));
				String date = dateFormat.format(rideRegistered.getStartDate());
				DateFormat dateFormat1 = new SimpleDateFormat(
						ApplicationUtil.datePattern3);
				try {
					rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
					rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
					rideRegistered.setCreated_dt(ListOfValuesManager
							.getCurrentTimeStampDate());
				} catch (ParseException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}

				rideRegistered.setToAddress1(poolRequestsDTO
						.getRideManagementTO());
				rideRegistered = ListOfValuesManager.getRideEntery("findByDTO",
						rideRegistered, con);
				frequencyDTO.setTime(rideRegistered.getStartDate());
				List<String> list = new ArrayList<String>();
				list.add(poolRequestsDTO.getRideManagementFrequency());
				frequencyDTO.setFrequency(list);
				frequencyDTO.setRideManagementId(rideRegistered.getRideID());
				rideRegistered.setCreatedBy(rideRegistered.getCreatedBy());
				/*
				 * for(int i = 0;i< vehicleMasterDTOList.size();i++) {
				 * if(vehicleMasterDTOList
				 * .get(i).getReg_NO().equalsIgnoreCase(rideRegistered
				 * .getVehicleID()) ){
				 * rideRegistered.setVehicleID(vehicleMasterDTOList
				 * .get(i).getVehicleID()); break; } }
				 */

				frequencyDTO = ListOfValuesManager.getFrequencyEntery(
						"findByDTO", frequencyDTO, con);
				rideManagementList();
				frequencyDTO = new FrequencyDTO();
				rideRegistered = new RideManagementDTO();
				return "copyrideManager";
			}

			rideRegistered = ListOfValuesManager
					.getRideManagerPopupDataDirect(rideRegistered.getRideID());
			List<String> frequencyList = new ArrayList<String>();
			// list.add(rideRegistered.getFrequencyinweek());
			List<FrequencyDTO> freqDtos = new ArrayList<FrequencyDTO>();
			freqDtos.addAll(ListOfValuesManager
					.fetchFrequencyListForRideManager(rideRegistered
							.getRideID()));
			for (FrequencyDTO freqDto : freqDtos)
				frequencyList.addAll(freqDto.getFrequency());

			rideRegistered.setRideID(null);

			rideRegistered.setUserID(userRegistrationDTO.getId());

			// Calendar cal1 = Calendar.getInstance();

			cal.setTime(rideRegistered.getStartDate());
			String date = dateFormat.format(rideRegistered.getStartDate());
			DateFormat dateFormat1 = new SimpleDateFormat(
					ApplicationUtil.datePattern3);
			try {
				rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
				rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
				rideRegistered.setCreated_dt(ListOfValuesManager
						.getCurrentTimeStampDate());
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}

			List x1;
			try {
				x1 = ApplicationUtil.calculateTimeWindowSettings(
						rideRegistered.getFromAddress1(), "",
						rideRegistered.getToAddress1(),
						userPreferences.getMaxWaitTime(), date);

				/*
				 * TWstart_early = x1.get(1); TWstart_late = x1.get(2);
				 * TWend_early TWend_late ride_distance ride_cost =
				 * ride_distance * 5;
				 */
				if (x1.size() > 0) {
					rideRegistered.setStartdateValue(x1.get(1).toString());
					rideRegistered.setStartDateEarly(x1.get(1).toString());
					rideRegistered.setStartDateLate(x1.get(2).toString());
					rideRegistered.setEndDateEarly(x1.get(3).toString());
					rideRegistered.setEndDateLate(x1.get(4).toString());
					float distance = Integer.parseInt(x1.get(5).toString()) / 1000;
					rideRegistered.setRideDistance(distance);
					rideRegistered.setRideCost((distance * 5) + "");
				}

			} catch (IOException e) {
			} catch (JSONException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}

			rideRegistered.setStartPointLatitude(rideRegistered
					.getStartPointLatitude());
			rideRegistered.setStartPointLongitude(rideRegistered
					.getStartPointLongitude());
			rideRegistered.setEndPointLatitude(rideRegistered
					.getEndPointLatitude());
			rideRegistered.setEndPointLongitude(rideRegistered
					.getEndPointLongitude());
			rideRegistered.setViaPointLatitude(rideRegistered
					.getViaPointLatitude());
			rideRegistered.setViaPointLongitude(rideRegistered
					.getViaPointLongitude());

			for (int i = 0; i < vehicleMasterDTOList.size(); i++) {
				if (vehicleMasterDTOList.get(i).getReg_NO()
						.equalsIgnoreCase(rideRegistered.getVehicleID())) {
					rideRegistered.setVehicleID(vehicleMasterDTOList.get(i)
							.getVehicleID());
					break;
				}
			}

			rideRegistered = ListOfValuesManager.getRideEntery("findByDTO",
					rideRegistered, con);

			frequencyDTO.setTime(rideRegistered.getStartDate());

			frequencyDTO.setFrequency(frequencyList);
			frequencyDTO.setRideManagementId(rideRegistered.getRideID());
			try {
				frequencyDTO.setStartDate(freqDtos.get(0).getStartDate());
				frequencyDTO.setEndDate(freqDtos.get(0).getEndDate());
			} catch (NullPointerException e) {
			}
			frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO",
					frequencyDTO, con);
			rideManagementList();
			rideRegistered = new RideManagementDTO();
			frequencyDTO = new FrequencyDTO();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			}
			rollbackTest = false;
		}

		return "copyrideManager";
	}

	public String copyRideSeeker() {
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		String rideSeeker = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		rideSeeker = (String) requestMap.get("rideSeeker");

		try {
			DateFormat dateFormat = new SimpleDateFormat(
					ApplicationUtil.datePattern3);
			Calendar cal = Calendar.getInstance();
			// Calendar cal1 = Calendar.getInstance();
			cal.setTime(rideRegistered.getStartDate());
			// cal1.setTime(rideSeekerDTO.getEndDate());
			rideRegistered.setStartdateValue(dateFormat.format(cal.getTime()));
			// rideRegistered.setEnddateValue(
			// dateFormat.format(cal1.getTime()));
			rideRegistered.setRecurring(rideSeekerDTO.getRecurring());
			rideRegistered.setSubSeekers(rideSeekerDTO.getSubSeekers());
			if (rideSeeker.equalsIgnoreCase("rideSeekerFrompool")) {
				rideRegistered.setRideID(null);
				rideRegistered.setUserID(userRegistrationDTO.getId());
				rideRegistered.setFromAddress1(poolRequestsDTO
						.getRideSeekerFrom());

				// Calendar cal1 = Calendar.getInstance();

				cal.setTime(rideRegistered.getStartDate());
				rideRegistered.setStartdateValue(dateFormat.format(cal
						.getTime()));
				String date = dateFormat.format(rideRegistered.getStartDate());
				DateFormat dateFormat1 = new SimpleDateFormat(
						ApplicationUtil.datePattern3);
				try {
					rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
					rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
					rideRegistered.setCreated_dt(ListOfValuesManager
							.getCurrentTimeStampDate());
				} catch (ParseException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}

				rideRegistered.setToAddress1(poolRequestsDTO.getRideSeekerTo());
				rideRegistered = ListOfValuesManager.getRideSeekerEntery(
						"findByDTO", rideRegistered, con);
				frequencyDTO.setTime(rideRegistered.getStartDate());
				List<String> list = new ArrayList<String>();
				list.add(poolRequestsDTO.getRideSeekerFrequency());
				frequencyDTO.setFrequency(list);
				frequencyDTO.setRideSeekerId(rideRegistered.getRideID());
				rideRegistered.setCreatedBy(rideRegistered.getCreatedBy());
				/*
				 * for(int i = 0;i< vehicleMasterDTOList.size();i++) {
				 * if(vehicleMasterDTOList
				 * .get(i).getReg_NO().equalsIgnoreCase(rideRegistered
				 * .getVehicleID()) ){
				 * rideRegistered.setVehicleID(vehicleMasterDTOList
				 * .get(i).getVehicleID()); break; } }
				 */

				frequencyDTO = ListOfValuesManager.getFrequencyEntery(
						"findByDTO", frequencyDTO, con);
				rideManagementList();
				frequencyDTO = new FrequencyDTO();
				rideRegistered = new RideManagementDTO();
				return "copyrideSeeker";
			}

			rideSeekerDTO = ListOfValuesManager.getRideSeekerData(Integer
					.parseInt(rideSeekerDTO.getSeekerID()));
			List<String> frequencyList = new ArrayList<String>();
			// list.add(rideSeekerDTO.getFrequencyinweek());
			List<FrequencyDTO> freqDtos = new ArrayList<FrequencyDTO>();
			freqDtos.addAll(ListOfValuesManager
					.fetchFrequencyListForRideSeeker(rideSeekerDTO
							.getSeekerID()));
			for (FrequencyDTO freqDto : freqDtos)
				frequencyList.addAll(freqDto.getFrequency());

			rideRegistered.setRideID(null);
			/*
			 * rideRegistered.setStartDate(rideSeekerDTO.getStartDate());
			 * rideRegistered.setEndDate(rideSeekerDTO.getEndDate());
			 */
			rideRegistered.setUserID(userRegistrationDTO.getId());
			rideRegistered.setFromAddress1(rideSeekerDTO.getFromAddress1());

			rideRegistered.setToAddress1(rideSeekerDTO.getToAddress1());
			cal.setTime(rideRegistered.getStartDate());
			String date = dateFormat.format(rideRegistered.getStartDate());
			DateFormat dateFormat1 = new SimpleDateFormat(
					ApplicationUtil.datePattern3);
			try {
				rideRegistered.setFlexiTimeAfter(dateFormat1.parse(date));
				rideRegistered.setFlexiTimeBefore(dateFormat1.parse(date));
				rideRegistered.setCreated_dt(ListOfValuesManager
						.getCurrentTimeStampDate());
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}
			List x1;
			try {
				x1 = ApplicationUtil.calculateTimeWindowSettings(
						rideSeekerDTO.getFromAddress1(), "",
						rideSeekerDTO.getToAddress1(),
						userPreferences.getMaxWaitTime(),
						rideRegistered.getStartdateValue());

				/*
				 * TWstart_early = x1.get(1); TWstart_late = x1.get(2);
				 * TWend_early TWend_late ride_distance ride_cost =
				 * ride_distance * 5;
				 */
				if (x1.size() > 0) {
					rideRegistered.setStartdateValue(x1.get(1).toString());
					rideRegistered.setStartDateEarly(x1.get(1).toString());
					rideRegistered.setStartDateLate(x1.get(2).toString());
					rideRegistered.setEndDateEarly(x1.get(3).toString());
					rideRegistered.setEndDateLate(x1.get(4).toString());
					float distance = Integer.parseInt(x1.get(5).toString()) / 1000;
					rideRegistered.setRideDistance(distance);
					rideRegistered.setRideCost((distance * 5) + "");
				}

			} catch (IOException e) {
			} catch (JSONException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}
			// Calendar cal1 = Calendar.getInstance();

			rideRegistered.setCreatedBy(rideSeekerDTO.getCreatedBy());
			rideRegistered.setStartPointLatitude(rideSeekerDTO
					.getStartPointLatitude());
			rideRegistered.setStartPointLongitude(rideSeekerDTO
					.getStartPointLongitude());
			rideRegistered.setEndPointLatitude(rideSeekerDTO
					.getEndPointLatitude());
			rideRegistered.setEndPointLongitude(rideSeekerDTO
					.getEndPointLongitude());
			rideRegistered.setViaPointLatitude(rideSeekerDTO
					.getViaPointLatitude());
			rideRegistered.setViaPointLongitude(rideSeekerDTO
					.getViaPointLongitude());

			for (int i = 0; i < vehicleMasterDTOList.size(); i++) {
				if (vehicleMasterDTOList.get(i).getReg_NO()
						.equalsIgnoreCase(rideRegistered.getVehicleID())) {
					rideRegistered.setVehicleID(vehicleMasterDTOList.get(i)
							.getVehicleID());
					break;
				}
			}
			rideRegistered = ListOfValuesManager.getRideSeekerEntery(
					"findByDTO", rideRegistered, con);

			try {
				frequencyDTO.setStartDate(freqDtos.get(0).getStartDate());
				frequencyDTO.setEndDate(freqDtos.get(0).getEndDate());
			} catch (NullPointerException e) {
			}

			if (Validator.isNotEmpty(rideRegistered.getEndDate())) {
				frequencyDTO.setEndDate(ApplicationUtil.dateFormat3
						.format(rideRegistered.getEndDate()));
			}
			if (Validator.isNotEmpty(rideRegistered.getStartDate())) {
				frequencyDTO.setStartDate(ApplicationUtil.dateFormat3
						.format(rideRegistered.getStartDate()));
			}

			frequencyDTO.setTime(rideRegistered.getStartDate());

			frequencyDTO.setFrequency(frequencyList);
			frequencyDTO.setRideSeekerId(rideRegistered.getRideID());

			frequencyDTO = ListOfValuesManager.getFrequencyEntery("findByDTO",
					frequencyDTO, con);
			rideManagementList();
			rideRegistered = new RideManagementDTO();
			frequencyDTO = new FrequencyDTO();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			}
		}

		rollbackTest = false;
		return "copyrideSeeker";
	}

	public void registerCircle() {
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		clearScreenMessage();
		if (userRegistrationDTO.getTravel().equals("T")
				&& allCircleForLoginUserList.size() > 0) {
			errorMessage.add(Messages.getValue("circle.add.restriction",
					new Object[] { 1, "Circle" }));
			circleDTO = new CircleDTO();
			return;
		} else if ((userRegistrationDTO.getTravel().equals("T") == false)
				&& (circleDTO.getCircleType().equals("T"))) {
			errorMessage.add(Messages.getValue("circle.add.typerestriction",
					new Object[] { "Taxi" }));
			circleDTO = new CircleDTO();
			return;
		} else if ((userRegistrationDTO.getTravel().equals("T") == false)
				&& ((circleDTO.getCircleType().equals("C")) && (allCorpCircleForLoginUserList
						.size() > 0))) {
			errorMessage.add(Messages.getValue("circle.add.restriction",
					new Object[] { 1, "Corporate Circle" }));
			circleDTO = new CircleDTO();
			return;
		}

		circleDTO.setCircleOwner_Member_Id_P(Integer
				.valueOf(userRegistrationDTO.getId()));
		circleDTO.setDate_of_creation(ListOfValuesManager.getcurrentDate());
		CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
		try {
			circleDTO = ListOfValuesManager.getregisterCircle(circleDTO, con);
			circleOwnerDTO.setCircleID(String.valueOf(circleDTO.getCircleID()));
			circleOwnerDTO.setUserID(userRegistrationDTO.getId());
			circleOwnerDTO = ListOfValuesManager.getregisterCircleOwner(
					circleOwnerDTO, userRegistrationDTO.getId(), con);
			circleMemberDTO.setUserid(userRegistrationDTO.getId());
			circleMemberDTO.setCircleid(circleOwnerDTO.getCircleID());
			circleMemberDTO.setStatus("1");
			circleMemberDTO = ListOfValuesManager.getJoinCircle(
					circleMemberDTO, userRegistrationDTO.getId(), con);
			successMessage.add(Messages.getValue("success.add",
					new Object[] { "Circle" }));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
			errorMessage.add(Messages.getValue("error.db1",
					new Object[] { "Circle" }));
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
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
		// return "circleRegistered";
	}

	public List<String> getAllPlacesList() {

		List<String> list = new ArrayList<String>();
		/*
		 * HttpSession currentSession = ServerUtility.getSession();
		 * if(currentSession.getAttribute("allPlaces") != null) { allPlace =
		 * (List<FavoritePlacesDTO>) currentSession.getAttribute("allPlaces"); }
		 * else {
		 */
		allPlace = ListOfValuesManager
				.getAllPlaces(userRegistrationDTO.getId());
		/*
		 * currentSession.setAttribute("allPlaces", allPlace); }
		 */

		for (int i = 0; i < allPlace.size(); i++) {
			String place = allPlace.get(i).getTagtype();
			list.add(place);
		}
		return list;

	}

	public String allCircleForLoginUser() {

		allCircleForLoginUserList = ListOfValuesManager
				.getAllCircleForLoginUser(userRegistrationDTO.getId());

		allCircleMemberForLoginUserList = ListOfValuesManager
				.getAllCircleMemberForLoginUser(userRegistrationDTO.getId());

		allPendingCircleMemberList = ListOfValuesManager
				.getAllPendingCircleMemberForLoginUser(userRegistrationDTO
						.getId());

		allCorpCircleForLoginUserList = ListOfValuesManager
				.getAllCorpCircleForLoginUser(userRegistrationDTO.getId());

		allTaxiCircleForLoginUserList = ListOfValuesManager
				.getAllTaxiCircleForLoginUser(userRegistrationDTO.getId());

		memberForSelectedCircle();

		return "allCircleList";
	}

	public String memberForSelectedCircle() {
		allCircleMemberForLoginUserList = ListOfValuesManager
				.getAllCircleMemberForLoginUser(userRegistrationDTO.getId());

		allPendingCircleMemberList = ListOfValuesManager
				.getAllPendingCircleMemberForLoginUser(userRegistrationDTO
						.getId());

		return "selected";
	}

	public List<SelectItem> getAllVehicleList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		VehicleMasterDTO tempVehicleList = new VehicleMasterDTO();
		for (int i = 0; i < vehicleMasterDTOList.size(); i++) {
			if (vehicleMasterDTOList.get(i).isIs_default()) {
				tempVehicleList = vehicleMasterDTOList.get(0);
				vehicleMasterDTOList.set(0, vehicleMasterDTOList.get(i));
				vehicleMasterDTOList.set(i, tempVehicleList);
			}
		}
		for (int i = 0; i < vehicleMasterDTOList.size(); i++) {
			list.add(new SelectItem(vehicleMasterDTOList.get(i).getVehicleID(),
					vehicleMasterDTOList.get(i).getReg_NO()));
		}
		return list;
	}

	public List<SelectItem> getAllCilcleForLoginUserDropDownList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (int i = 0; i < allCircleForLoginUserList.size(); i++) {
			list.add(new SelectItem(allCircleForLoginUserList.get(i)
					.getCircleID(), allCircleForLoginUserList.get(i)
					.getCircleName()));
		}
		return list;
	}

	public List<SelectItem> getAllCilcleMemberForLoginUserDropDownList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (int i = 0; i < allCircleMemberForLoginUserList.size(); i++) {
			String listText = "";
			listText += allCircleMemberForLoginUserList.get(i).getMemberName();
			if (allCircleMemberForLoginUserList.get(i).getAge() != null
					&& allCircleMemberForLoginUserList.get(i).getAge().length() > 0) {
				listText += " of age: "
						+ allCircleMemberForLoginUserList.get(i).getAge();
			}
			if (allCircleMemberForLoginUserList.get(i).getAddress() != null
					&& allCircleMemberForLoginUserList.get(i).getAddress()
							.length() > 0) {
				listText += " lives in: "
						+ allCircleMemberForLoginUserList.get(i).getAddress();
			}
			if (allCircleMemberForLoginUserList.get(i).getAdminInfo() != null
					&& allCircleMemberForLoginUserList.get(i).getAdminInfo()
							.length() > 0) {
				listText += " - "
						+ allCircleMemberForLoginUserList.get(i).getAdminInfo();
			}

			list.add(new SelectItem(allCircleMemberForLoginUserList.get(i)
					.getUserid()
					+ ","
					+ allCircleMemberForLoginUserList.get(i).getCircleID()
					+ ","
					+ allCircleMemberForLoginUserList.get(i).getSuperAdmin(),
					listText));

		}
		return list;
	}

	public List<SelectItem> getAllCilcleMemberToSendMessageDropDownList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (int i = 0; i < allCircleMemberForLoginUserList.size(); i++) {
			list.add(new SelectItem(allCircleMemberForLoginUserList.get(i)
					.getCircleID(), allCircleMemberForLoginUserList.get(i)
					.getCircleName()));

		}
		return list;
	}

	public List<SelectItem> getAllAffiliatedCilcleMember() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		// allCircleAffiliationsDTO
		for (CircleAffiliationsDTO dto : allCircleAffiliationsDTO) {
			list.add(new SelectItem(dto.getCircleAffilicatedCircleId(), dto
					.getAffilicatedCircleName()));
		}
		return list;
	}

	public List<String> getAllPendingAffiliatedCilcleMember() {
		List<String> list = new ArrayList<String>();
		// allCircleAffiliationsDTO
		for (CircleAffiliationsDTO dto : allPendingCircleAffiliationsDTO) {
			list.add(dto.getAffilicatedCircleName());
		}
		return list;
	}

	public List<String> getAllPendingCilcleMemberForLoginUser() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < allPendingCircleMemberList.size(); i++) {
			String listText = "";
			listText += allPendingCircleMemberList.get(i).getMemberName();
			if (allPendingCircleMemberList.get(i).getAge() != null
					&& allPendingCircleMemberList.get(i).getAge().length() > 0) {
				listText += " of age: "
						+ allPendingCircleMemberList.get(i).getAge();
			}
			if (allPendingCircleMemberList.get(i).getAddress() != null
					&& allPendingCircleMemberList.get(i).getAddress().length() > 0) {
				listText += " lives in: "
						+ allPendingCircleMemberList.get(i).getAddress();
			}
			if (allPendingCircleMemberList.get(i).getAdminInfo().length() > 0) {
				listText += " - "
						+ allPendingCircleMemberList.get(i).getAdminInfo();
			}

			list.add(listText);

		}
		return list;
	}

	public List<SelectItem> getAllAffiliateCircleForTaxiUser() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<CircleDTO> dto = new ArrayList<CircleDTO>();
		try {
			dto = ListOfValuesManager.getAffiliateCircleForTaxiUser(Integer
					.parseInt(userRegistrationDTO.getId()));

		} catch (NumberFormatException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}
		for (CircleDTO dtoTemp : dto) {
			list.add(new SelectItem(dtoTemp.getCircleID(), dtoTemp.getName()));
		}
		return list;
	}

	public void makeRidePreMatchInactive() {
		ridePreMatchFormTest = false;
	}

	public void makeRideMatchInactive() {
		rideMatchFormTest = false;
	}

	public void clearMatchTripList() {
		rideSeekerDTO = new RideSeekerDTO();
		matchedTripByConditionList.clear();
		matchedTripDataModel = new MatchedTripDataModel();
	}

	public void clearCombineVehicleSearch() {
		rideManagementDTO = new RideManagementDTO();
		combineVehicleCondition.clear();
		combineVehicleDataModel = new CombineVehicleDataModel();
		circleDTO = new CircleDTO();
		this.setRideIdToDrop(0);
		this.setRideIdToTake(0);
	}

	public void combineVehicleSearch() {
		String date1 = "";
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern1);
		// SimpleDateFormat formatter1 = new
		// SimpleDateFormat(ApplicationUtil.datePattern2);
		// date = formatter1.parse(dto1.getStartDate());
		if (rideManagementDTO.getStartDate() != null) {
			date1 = dateFormat.format(rideManagementDTO.getStartDate());
		}
		combineVehicleCondition.clear();
		combineVehicleCondition = ListOfValuesManager.getAllCombineVehicleList(
				rideManagementDTO.getFromAddress1(),
				rideManagementDTO.getToAddress1(), date1,
				circleDTO.getCircleID());

		combineVehicleDataModel = new CombineVehicleDataModel(
				combineVehicleCondition);
	}

	public void combineVehicle() {
		clearScreenMessage();
		if (rideIdToDrop <= 0)
			errorMessage.add("Please select ride to drop.");
		if (rideIdToTake <= 0)
			errorMessage.add("Please select ride to Combine.");
		if (rideIdToTake == rideIdToDrop && rideIdToTake > 0)
			errorMessage
					.add("Please select different rides to drop and Combine.");

		if (errorMessage.size() == 0) {
			// populate drop and take ride.
			RideManagementDTO rideToDrop = ListOfValuesManager
					.getRideManagerPopupDataDirect(rideIdToDrop + "");
			RideManagementDTO rideToTake = ListOfValuesManager
					.getRideManagerPopupDataDirect(rideIdToTake + "");
			if (!Validator.isNumberZeroNotAlloed(rideToDrop.getRideID())) {
				errorMessage.add("Ride you want to drop does not exist.");
			}
			if (!Validator.isNumberZeroNotAlloed(rideToTake.getRideID())) {
				errorMessage.add("Ride you want to combine does not exist.");
			}
			if (!ApplicationUtil.dateFormat1.format(rideToDrop.getStartDate())
					.equals(ApplicationUtil.dateFormat1.format(rideToTake
							.getStartDate()))) {
				errorMessage
						.add("Select Ride of same date you want to combine and drop.");
			}

			// Make validation that both rides start and end point synchronized.
			// Make validation that both rides time synchronized.

			if (errorMessage.size() == 0) {
				Connection con = (Connection) ListOfValuesManager
						.getBroadConnection();
				try {
					rideToDrop.setStatus("I");
					ListOfValuesManager.getCancleRide(rideToDrop, con);
					// Update rideId in pool request and ride seeker table.
					ListOfValuesManager.updateRideIdDropTake(rideToDrop,
							rideToTake, con);
					// Send message to ride user that ride cancelled.

					UserRegistrationDTO userDto = new UserRegistrationDTO();
					userDto = ListOfValuesManager.findDriverDtoByRideId(
							rideToDrop.getRideID(), con);

					UserRegistrationDTO userDtoRide = new UserRegistrationDTO();
					userDtoRide = ListOfValuesManager.getUserById(Integer
							.parseInt(rideToDrop.getUserID()));

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages.getValue(
									"sms.cancel.driver.admin",
									new Object[] {
											userDto.getFirst_name(),
											rideToDrop.getRideID(),
											(rideToDrop.getFromAddress1()
													.length() > 25) ? rideToDrop
													.getFromAddress1()
													.substring(0, 25)
													: rideToDrop
															.getFromAddress1(),
											(rideToDrop.getToAddress1()
													.length() > 25) ? rideToDrop
													.getToAddress1().substring(
															0, 25) : rideToDrop
													.getToAddress1(),
											rideToDrop.getStartdateValue() }));
					userMessageDTO
							.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setRideId(Integer.parseInt(rideToDrop
							.getRideID()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setEmailSubject(Messages.getValue(
							"subject.cancel",
							new Object[] { rideToDrop.getStartdateValue() }));
					userMessageDTO.setMessage(Messages.getValue(
							"ridegiver.driver.cancel",
							new Object[] { userDto.getFirst_name(),
									rideToDrop.getRideID(),
									rideToDrop.getFromAddress1(),
									rideToDrop.getToAddress1(),
									rideToDrop.getStartdateValue(),
									userDtoRide.getMobile_no() }));
					userMessageDTO
							.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setRideId(Integer.parseInt(rideToDrop
							.getRideID()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					// Send message to ride user that new ride assigned.
					userDto = ListOfValuesManager.findDriverDtoByRideId(
							rideToTake.getRideID(), con);
					userDtoRide = ListOfValuesManager.getUserById(Integer
							.parseInt(rideToTake.getUserID()));

					allSeekerForGivenRide = ListOfValuesManager
							.getAllRideSeekerForAGivenRide(rideToDrop
									.getRideID());
					if (allSeekerForGivenRide.size() > 0) {
						for (int index = 0; index < allSeekerForGivenRide
								.size(); index++) {
							UserRegistrationDTO seekerUserDto = ListOfValuesManager
									.getUserById(Integer.parseInt(rideToTake
											.getUserID()));

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO
									.setEmailSubject(Messages.getValue(
											"subject.match",
											new Object[] { rideToTake
													.getStartDate() }));
							userMessageDTO.setMessage(Messages.getValue(
									"ridematched.driver",
									new Object[] {
											userDto.getFirst_name(),
											rideToDrop.getRideID(),
											rideToTake.getFromAddress1(),
											rideToTake.getToAddress1(),
											rideToTake.getStartDate(),
											seekerUserDto.getFirst_name()
													+ " - "
													+ seekerUserDto
															.getMobile_no() }));
							userMessageDTO.setToMember(Integer.parseInt(userDto
									.getId()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setMessage(Messages.getValue(
									"sms.match.driver",
									new Object[] {
											allSeekerForGivenRide.get(index)
													.getFromAddress1(),
											allSeekerForGivenRide.get(index)
													.getToAddress1(),
											seekerUserDto.getFirst_name(),
											allSeekerForGivenRide.get(index)
													.getStartdateValue(),
											seekerUserDto.getMobile_no(),
											rideToTake.getRideID(),
											userDto.getFirst_name() }));
							userMessageDTO.setToMember(Integer.parseInt(userDto
									.getId()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();

							userMessageDTO
									.setEmailSubject("Your ride has been changed");
							userMessageDTO.setMessage(Messages.getValue(
									"ridematched.seeker",
									new Object[] {
											allSeekerForGivenRide.get(index)
													.getUserName(),
											userDto.getFirst_name(),
											rideToTake.getRideID(),
											allSeekerForGivenRide.get(index)
													.getFromAddress1(),
											allSeekerForGivenRide.get(index)
													.getToAddress1(),
											allSeekerForGivenRide.get(index)
													.getStartdateValue(),
											rideToTake.getVehicleRegno(),
											userDto.getMobile_no() }));
							userMessageDTO.setToMember(Integer
									.parseInt(allSeekerForGivenRide.get(index)
											.getUserID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO
									.setMessage("Your ride has been changed."
											+ Messages
													.getValue(
															"sms.match",
															new Object[] {
																	allSeekerForGivenRide
																			.get(index)
																			.getFromAddress1(),
																	allSeekerForGivenRide
																			.get(index)
																			.getToAddress1(),
																	seekerUserDto
																			.getFirst_name(),
																	allSeekerForGivenRide
																			.get(index)
																			.getStartdateValue(),
																	userDto.getMobile_no(),
																	rideToTake
																			.getRideID(),
																	allSeekerForGivenRide
																			.get(index)
																			.getUserName(),
																	rideToTake
																			.getVehicleRegno(),
																	allSeekerForGivenRide
																			.get(index)
																			.getSeekerID() }));
							userMessageDTO.setToMember(Integer
									.parseInt(allSeekerForGivenRide.get(index)
											.getUserID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

						}
					}
				} catch (ConfigurationException e) {
					rollbackTest = true;
				} catch (NullPointerException e1) {
					rollbackTest = true;
				} finally {
					if (rollbackTest) {
						try {
							con.rollback();
						} catch (SQLException e) {
							LoggerSingleton.getInstance().error(
									e.getStackTrace()[0].getClassName()
											+ "->"
											+ e.getStackTrace()[0]
													.getMethodName()
											+ "() : "
											+ e.getStackTrace()[0]
													.getLineNumber() + " :: "
											+ e.getMessage());
						}
						ListOfValuesManager.releaseConnection(con);
						errorMessage.add("There is some problem in operation.");
						successMessage.clear();
					} else {
						try {
							con.commit();
						} catch (SQLException e) {
							LoggerSingleton.getInstance().error(
									e.getStackTrace()[0].getClassName()
											+ "->"
											+ e.getStackTrace()[0]
													.getMethodName()
											+ "() : "
											+ e.getStackTrace()[0]
													.getLineNumber() + " :: "
											+ e.getMessage());
						}
						ListOfValuesManager.releaseConnection(con);
						successMessage.add("Vehicle combined successfully.");
					}
					rollbackTest = false;
				}
			}

		}
		clearCombineVehicleSearch();
	}

	public String matchedTripListByCondition() {
		rideMatchFormTest = true;
		String date1 = "";
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern1);
		// SimpleDateFormat formatter1 = new
		// SimpleDateFormat(ApplicationUtil.datePattern2);
		// date = formatter1.parse(dto1.getStartDate());
		if (rideSeekerDTO.getStartDate() != null) {
			date1 = dateFormat.format(rideSeekerDTO.getStartDate());
		}
		matchedTripByConditionList.clear();
		matchedTripByConditionList = ListOfValuesManager
				.getAllMatchedListByCondition(rideSeekerDTO.getFromAddress1(),
						rideSeekerDTO.getToAddress1(), date1,
						circleDTO.getCircleID());

		Map<String, Integer> group = new HashMap<String, Integer>();
		for (MatchedTripDTO dto : matchedTripByConditionList) {
			if (group.size() > 0 && group.containsKey(dto.getGroupId())) {
				group.put(dto.getGroupId(), group.get(dto.getGroupId()) + 1);
			} else {
				group.put(dto.getGroupId(), 1);
			}
		}
		for (int i = 0; i < matchedTripByConditionList.size(); i++) {
			matchedTripByConditionList.get(i).setMemberCount(
					""
							+ group.get(matchedTripByConditionList.get(i)
									.getGroupId()));
		}

		matchedTripDataModel = new MatchedTripDataModel(
				matchedTripByConditionList);

		return "matchedTrip";
	}

	public String rideMatchedTripListByCondition() {
		ridePreMatchFormTest = true;
		String date1 = "";
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern1);
		// SimpleDateFormat formatter1 = new
		// SimpleDateFormat(ApplicationUtil.datePattern2);
		// date = formatter1.parse(dto1.getStartDate());
		if (rideSeekerDTO.getStartDate() != null) {
			date1 = dateFormat.format(rideSeekerDTO.getStartDate());
		}
		matchedTripByConditionList.clear();
		if (circleDTO.getCircleID() > 0) {
			matchedTripByConditionList = ListOfValuesManager
					.getAllMatchedListByCondition(
							rideSeekerDTO.getFromAddress1(),
							rideSeekerDTO.getToAddress1(), date1,
							circleDTO.getCircleID());

			List<MatchedTripDTO> dtoTemp = new ArrayList<MatchedTripDTO>();
			for (int i = 0; i < matchedTripByConditionList.size(); i++) {
				if (!matchedTripByConditionList.get(i).isFullDay()
						&& !matchedTripByConditionList.get(i).isRecurring()) {
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
		for (MatchedTripDTO dto : matchedTripByConditionList) {
			if (group.size() > 0 && group.containsKey(dto.getGroupId())) {
				group.put(dto.getGroupId(), group.get(dto.getGroupId()) + 1);
			} else {
				group.put(dto.getGroupId(), 1);
			}
		}
		for (int i = 0; i < matchedTripByConditionList.size(); i++) {
			matchedTripByConditionList.get(i).setMemberCount(
					""
							+ group.get(matchedTripByConditionList.get(i)
									.getGroupId()));
		}

		matchedTripDataModel = new MatchedTripDataModel(
				matchedTripByConditionList);

		return "matchedTrip";
	}

	public String matchRideForCompany() {
		if (manageRideFormDTO.getRideDate() != null
				&& !manageRideFormDTO.getRideDate().equals("")) {
			SimpleDateFormat df1 = new SimpleDateFormat(
					ApplicationUtil.datePattern12);
			SimpleDateFormat df2 = new SimpleDateFormat(
					ApplicationUtil.datePattern1);
			try {
				Date date = df1.parse(manageRideFormDTO.getRideDate());
				manageRideFormDTO.setRideDate(df2.format(date));
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}
		}

		String rideOption = manageRideFormDTO.getRideOption();
		clearMatchRideForCompany();
		clearScreenMessage();

		if (manageRideFormDTO.getMyCircleId() > 0) {
			if (rideOption.equals("pending")) {
				manageRideDTOs.addAll(ListOfValuesManager
						.findPendingManageRide(manageRideFormDTO));

			} else if (rideOption.equals("matchedPending")) {
				manageRideDTOs.addAll(ListOfValuesManager
						.findPendingMatchedManageRide(manageRideFormDTO));

			} else if (rideOption.equals("completed")) {
				manageRideDTOs.addAll(ListOfValuesManager
						.findCompletedManageRide(manageRideFormDTO));

			} else {
				errorMessage
						.add("Please select type of rides you want to search.");
			}
		} else {
			errorMessage.add("Please select your circle.");
		}

		// manageRideFormDTO = new ManageRideFormDTO();
		return "matchedTrip";
	}

	public void clearMatchRideForCompany() {
		if (manageRideDTOs.size() > 0)
			manageRideDTOs.clear();
	}

	public String matchRideCancel() {
		if (matchRideCancelParam != null && matchRideCancelParam.length() > 0) {
			String[] x1 = matchRideCancelParam.split("@");
			for (String temp1 : x1) {
				String x2[] = temp1.split("-");
				if (x2.length >= 2) {
					int rideId = Integer.parseInt(x2[0]);
					String role = x2[1];
					if (role.equalsIgnoreCase("giver")) {
						rideRegistered = ListOfValuesManager
								.getRideManagerPopupDataDirect("" + rideId);

						cancleRideManager();
					} else if (role.equalsIgnoreCase("taker")) {
						rideSeekerDTO = ListOfValuesManager
								.getRideSeekerData(rideId);

						cancleRideSeeker();
					}
				}
			}
		}
		matchRideCancelParam = "";

		matchRideForCompany();

		return "";
	}

	public String messageForLoginUser() {
		allCircleOwnerManagerUserList = ListOfValuesManager
				.getAllMessageForLoginUser(userRegistrationDTO.getId());
		for (int i = 0; i < allCircleOwnerManagerUserList.size(); i++) {
			UserRegistrationDTO dtoTemp = new UserRegistrationDTO();
			dtoTemp.setId(allCircleOwnerManagerUserList.get(i).getUserid());
			dtoTemp = ListOfValuesManager.getAverageRatingForUser(dtoTemp);
			allCircleOwnerManagerUserList.get(i).setUserRating(
					dtoTemp.getAverageRating());
		}
		allCircleMembershipInvitationList = ListOfValuesManager
				.getAllCircleMembershipInvitation(userRegistrationDTO.getId());
		allCircleAffiliationRequest = ListOfValuesManager
				.getAllPendingAffiliatedCircle(userRegistrationDTO.getId());
		allUnreadMessageList = ListOfValuesManager
				.getAllUnreadMessage(userRegistrationDTO.getId());
		allRideApprovalRequest = ListOfValuesManager
				.findRideSeekerDetailsForApprove(userRegistrationDTO
						.getEmail_id());
		return "message";
	}

	public void confirmOrDeclineAffiliatedCircle() {
		String confirm = null;
		String decline = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		confirm = (String) requestMap.get("confirm");
		decline = (String) requestMap.get("decline");
		int circleId = 0;
		String[] part = null;
		if (requestMap.containsKey("circleAffiliation")) {
			part = requestMap.get("circleAffiliation").split("-");
		} else {
			part = circleAffiliationsDTO.getCircleAffilicatedCircleId().split(
					"-");
		}

		int affiliatedCircleId = 0;
		if (part.length >= 2) {
			if (part[0] != null && part[0].length() > 0
					&& Integer.parseInt(part[0]) > 0) {
				circleId = Integer.parseInt(part[0]);
			}
			if (part[1] != null && part[1].length() > 0
					&& Integer.parseInt(part[1]) > 0) {
				affiliatedCircleId = Integer.parseInt(part[1]);
			}
		}

		clearScreenMessage();
		if (circleId <= 0 || affiliatedCircleId <= 0) {
			errorMessage
					.add("Please select a Circle first and then select affiliated taxi circle.");
		} else {
			if (confirm != null) {
				ListOfValuesManager.makeTaxiCircleAffiliatedActive(circleId,
						affiliatedCircleId);
				successMessage
						.add("Selected taxi affiliated successfully with circle.");
			} else if (decline != null) {
				ListOfValuesManager.makeTaxiCircleAffiliatedInactive(circleId,
						affiliatedCircleId);
				successMessage
						.add("Selected taxi un-affiliated successfully with circle.");
			}
			gatherDefaultcircleDTO();
			messageForLoginUser();
		}
	}

	public void confirmOrDeclineUser() {

		String confirmByUser = null;
		String confirmByAdmin = null;
		String declineByUser = null;
		String declineByAdmin = null;
		String requestByUser = null;
		String requestByAdmin = null;
		String removedByUser = null;
		String removedByAdmin = null;

		/*
		 * String confirm = null; String decline = null; String declineByOwner =
		 * null;
		 */
		String circleOwnerCircleID = null;
		String circleOwnerUserID = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :

		confirmByUser = (String) requestMap.get("confirmByUser");
		confirmByAdmin = (String) requestMap.get("confirmByAdmin");
		declineByUser = (String) requestMap.get("declineByUser");
		declineByAdmin = (String) requestMap.get("declineByAdmin");
		requestByUser = (String) requestMap.get("requestByUser");
		requestByAdmin = (String) requestMap.get("requestByAdmin");
		removedByUser = (String) requestMap.get("removedByUser");
		removedByAdmin = (String) requestMap.get("removedByAdmin");

		/*
		 * confirm = (String)requestMap.get("confirm"); decline =
		 * (String)requestMap.get("decline");
		 */
		circleOwnerCircleID = (String) requestMap.get("circleOwnerCircleID");
		circleOwnerUserID = (String) requestMap.get("circleOwnerUserID");
		/* declineByOwner = (String)requestMap.get("declinebyowner"); */
		if (circleOwnerCircleID != null)
			circleOwnerManagerDTO.setCircleID(circleOwnerCircleID);
		if (circleOwnerUserID != null)
			circleOwnerManagerDTO.setUserid(circleOwnerUserID);

		// String requestBy = (String)requestMap.get("requestBy");

		CircleMemberDTO dto = new CircleMemberDTO();

		if (confirmByUser != null || confirmByAdmin != null) {
			if (!Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO
					.getCircleID()))
				errorMessage.add("Please select Circle first.");
			else if (!Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO
					.getUserid()))
				errorMessage.add("Please select Circle Member.");
			else {
				dto.setUserid(circleOwnerManagerDTO.getUserid());
				dto.setCircleid(circleOwnerManagerDTO.getCircleID());
				dto.setStatus("1");
			}
		}
		if (declineByUser != null || declineByAdmin != null
				|| removedByUser != null) {
			if (!Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO
					.getCircleID()))
				errorMessage.add("Please select Circle first.");
			else if (!Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO
					.getUserid()))
				errorMessage.add("Please select Circle Member.");
			else {
				dto.setUserid(circleOwnerManagerDTO.getUserid());
				dto.setCircleid(circleOwnerManagerDTO.getCircleID());
				dto.setStatus("2");
			}
		}

		if (requestByUser != null) {
		}
		if (removedByAdmin != null) {
			String[] parts = circleOwnerManagerDTO.getCombineUserAndCircleID()
					.split(",");
			if (!Validator.isNumberZeroNotAlloed(parts[0]))
				errorMessage.add("Please select Circle then Circle Member.");
			else if (parts.length < 3 || parts[1] == null
					|| !Validator.isNumberZeroNotAlloed(parts[1]))
				errorMessage.add("Please select Circle then Circle Member.");
			else if (parts[0].equals(parts[2]))
				errorMessage
						.add("You are unauthorised to remove circle owner from circle.");
			else {
				String part1 = parts[0];
				String part2 = parts[1];
				dto.setUserid(part1);
				dto.setCircleid(part2);
				dto.setStatus("2");
			}
		}
		/*
		 * if(declineByOwner!=null){ String[] parts =
		 * circleOwnerManagerDTO.getCombineUserAndCircleID().split(",");
		 * if(!Validator.isNumberZeroNotAlloed(parts[0]))
		 * errorMessage.add("Please select Circle first."); if(parts.length < 3
		 * || parts[1] == null || !Validator.isNumberZeroNotAlloed(parts[1]))
		 * errorMessage.add("Please select Circle Member.");
		 * if(parts[0].equals(parts[2])) errorMessage.add(
		 * "You are unauthorised to remove circle owner from circle.");
		 * if(errorMessage.size() == 0) { String part1 = parts[0]; String part2
		 * = parts[1]; dto.setUserid(part1); dto.setCircleid(part2);
		 * dto.setStatus("2"); } }
		 * 
		 * if(confirm != null){
		 * if(!Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO
		 * .getCircleID())) errorMessage.add("Please select Circle first.");
		 * if(!
		 * Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO.getUserid()))
		 * errorMessage.add("Please select Circle Member.");
		 * dto.setUserid(circleOwnerManagerDTO.getUserid());
		 * dto.setCircleid(circleOwnerManagerDTO.getCircleID());
		 * dto.setStatus("1"); }
		 * 
		 * if(decline != null){
		 * if(!Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO
		 * .getCircleID())) errorMessage.add("Please select Circle first.");
		 * if(!
		 * Validator.isNumberZeroNotAlloed(circleOwnerManagerDTO.getUserid()))
		 * errorMessage.add("Please select Circle Member.");
		 * dto.setUserid(circleOwnerManagerDTO.getUserid());
		 * dto.setCircleid(circleOwnerManagerDTO.getCircleID());
		 * dto.setStatus("2"); }
		 */
		if (errorMessage.size() == 0) {
			Connection con = (Connection) ListOfValuesManager
					.getBroadConnection();
			try {
				CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
				circleOwnerDTO.setCircleID(String.valueOf(dto.getCircleid()));
				circleOwnerDTO.setUserID(dto.getUserid());
				/*
				 * if(declineByOwner != null ) { circleOwnerDTO =
				 * ListOfValuesManager.updateRegisterCircleOwner(circleOwnerDTO,
				 * userRegistrationDTO.getId(), con); dto =
				 * ListOfValuesManager.getConfirmOrDeclineUser(dto,
				 * userRegistrationDTO.getId(), con); }
				 * 
				 * if(confirm!= null || decline != null) dto =
				 * ListOfValuesManager.getConfirmOrDeclineUser(dto,
				 * userRegistrationDTO.getId(), con);
				 */
				if (confirmByUser != null || declineByUser != null
						|| requestByUser != null || requestByAdmin != null
						|| removedByUser != null || removedByAdmin != null) {
					dto = ListOfValuesManager.getConfirmOrDeclineUser(dto,
							userRegistrationDTO.getId(), con);
				} else if (confirmByAdmin != null || declineByAdmin != null) {
					circleOwnerDTO = ListOfValuesManager
							.updateRegisterCircleOwner(circleOwnerDTO,
									userRegistrationDTO.getId(), con);
					dto = ListOfValuesManager.getConfirmOrDeclineUser(dto,
							userRegistrationDTO.getId(), con);
				}

				userMessageDTO = new MessageBoardDTO();
				// fetch circle name from db.
				if (confirmByUser != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO
							.setMessage("User "
									+ userRegistrationDTO.getFirst_name()
									+ " has accepted your invitation to join your circle "
									+ dtoTemp.getName() + ".");
					userMessageDTO.setToMember(dtoTemp
							.getCircleOwner_Member_Id_P());
					successMessage.add("Invitation accepted successfully.");
				}
				if (confirmByAdmin != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO.setMessage("Admin of circle "
							+ dtoTemp.getName()
							+ " has accepted your request to join the circle.");
					userMessageDTO
							.setToMember(Integer.parseInt(dto.getUserid()));
					successMessage.add("User added to circle successfully.");
				}
				if (declineByUser != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO.setMessage("User "
							+ userRegistrationDTO.getFirst_name()
							+ " has declined to join your circle "
							+ dtoTemp.getName() + ".");
					userMessageDTO.setToMember(dtoTemp
							.getCircleOwner_Member_Id_P());
					successMessage.add("Invitation rejected successfully.");
				}
				if (declineByAdmin != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO.setMessage("Admin of circle "
							+ dtoTemp.getName()
							+ " has declined your request to join the circle.");
					userMessageDTO
							.setToMember(Integer.parseInt(dto.getUserid()));
					successMessage.add("Request rejected successfully.");
				}
				if (requestByUser != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO.setMessage("User "
							+ userRegistrationDTO.getFirst_name()
							+ " has sent a request to join "
							+ dtoTemp.getName() + " circle.");
					userMessageDTO
							.setToMember(Integer.parseInt(dto.getUserid()));
					successMessage
							.add("Your request has been sent to the admin successfully.");
				}
				if (requestByAdmin != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO.setMessage("Admin of circle "
							+ dtoTemp.getName()
							+ " has invited you to join the circle.");
					userMessageDTO
							.setToMember(Integer.parseInt(dto.getUserid()));
					successMessage
							.add("Your invitation has been sent to the user.");
				}
				if (removedByUser != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO.setMessage("User "
							+ userRegistrationDTO.getFirst_name()
							+ " has left " + dtoTemp.getName() + " circle.");
					userMessageDTO.setToMember(dtoTemp
							.getCircleOwner_Member_Id_P());
					successMessage
							.add("You have left the circle successfully.");
				}
				if (removedByAdmin != null) {
					CircleDTO dtoTemp = ListOfValuesManager
							.getCircleDtoByCircleId(Integer.parseInt(dto
									.getCircleid()));
					userMessageDTO.setMessage("Admin of circle "
							+ dtoTemp.getName()
							+ " has removed you from the circle.");
					userMessageDTO
							.setToMember(Integer.parseInt(dto.getUserid()));
					successMessage
							.add("User removed from the circle successfully.");
				}

				/*
				 * //check point for message if(declineByOwner != null ) {
				 * CircleDTO dtoTemp =
				 * ListOfValuesManager.getCircleDtoByCircleId
				 * (Integer.parseInt(dto.getCircleid()));
				 * userMessageDTO.setMessage("Admin of circle "+
				 * dtoTemp.getName()
				 * +" has declined your request to join the circle.");
				 * userMessageDTO
				 * .setToMember(Integer.parseInt(dto.getUserid())); }
				 * 
				 * if(confirm != null){ CircleDTO dtoTemp =
				 * ListOfValuesManager.getCircleDtoByCircleId
				 * (Integer.parseInt(dto.getCircleid()));
				 * userMessageDTO.setMessage
				 * ("User "+userRegistrationDTO.getFirst_name
				 * ()+" has accepted your invitation to join your circle "+
				 * dtoTemp.getName() +".");
				 * userMessageDTO.setToMember(dtoTemp.getCircleOwner_Member_Id_P
				 * ()); } if(decline != null){ CircleDTO dtoTemp =
				 * ListOfValuesManager
				 * .getCircleDtoByCircleId(Integer.parseInt(dto.getCircleid()));
				 * userMessageDTO
				 * .setMessage("User "+userRegistrationDTO.getFirst_name
				 * ()+" has declined to join your circle "+ dtoTemp.getName()
				 * +".");
				 * userMessageDTO.setToMember(dtoTemp.getCircleOwner_Member_Id_P
				 * ()); }
				 */
				userMessageDTO.setMessageChannel("M");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				rollbackTest = true;
			} finally {
				if (rollbackTest) {
					try {
						con.rollback();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
					errorMessage
							.add("There is some problem in adding user to circle.");
					successMessage.clear();
				} else {
					try {
						con.commit();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
				}
				rollbackTest = false;
			}
			messageForLoginUser();
			// allCircleForLoginUser();

			memberForSelectedCircle();

			List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
			for (int i = 0; i < allCircleMemberForLoginUserList.size(); i++) {
				if (allCircleMemberForLoginUserList.get(i).getCircleID()
						.equals(dto.getCircleid())) {
					CircleOwnerManagerDTO dto1 = new CircleOwnerManagerDTO();
					dto1 = allCircleMemberForLoginUserList.get(i);
					allCircleMemberForLoginUserList1.add(dto1);
				}
			}
			allCircleMemberForLoginUserList.clear();
			allCircleMemberForLoginUserList
					.addAll(allCircleMemberForLoginUserList1);
			allCircleMemberForLoginUserList1.clear();

			List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
			for (int i = 0; i < allPendingCircleMemberList.size(); i++) {
				if (allPendingCircleMemberList.get(i).getCircleID()
						.equals(dto.getCircleid())) {
					CircleOwnerManagerDTO dto1 = new CircleOwnerManagerDTO();
					dto1 = allPendingCircleMemberList.get(i);
					allPendingCircleMemberList1.add(dto1);
				}
			}
			allPendingCircleMemberList.clear();
			allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
			allPendingCircleMemberList1.clear();
			// circleDTO = new CircleDTO();
			forregistrationOnly = new UserRegistrationDTO();

		}
		// return "confirm";
	}

	public void approveOrRejectRide() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		String approve = (String) requestMap.get("approve");
		String decline = (String) requestMap.get("decline");
		String seekerId = (String) requestMap.get("seekerId");
		String newStatus = "";
		RideSeekerDTO dtoTemp = new RideSeekerDTO();
		if (approve != null) {
			for (RideSeekerDTO dto : allRideApprovalRequest) {
				if (dto.getSeekerID().equals(seekerId)
						&& dto.getFirstApproverEmailId().equals(
								userRegistrationDTO.getEmail_id())) {
					newStatus = "O";
					if (Validator.isEmpty(dto.getSecondApproverEmailId())) {
						newStatus = "A";
					}
					dtoTemp = dto;
					break;
				} else if (dto.getSeekerID().equals(seekerId)
						&& dto.getSecondApproverEmailId().equals(
								userRegistrationDTO.getEmail_id())) {
					newStatus = "A";
					dtoTemp = dto;
					break;
				}
			}
		} else if (decline != null) {
			for (RideSeekerDTO dto : allRideApprovalRequest) {
				if (dto.getSeekerID().equals(seekerId)) {
					newStatus = "I";
					dtoTemp = dto;
					break;
				}
			}
		}
		if (!Validator.isEmpty(newStatus)) {
			ListOfValuesManager.changeStatus(Integer.parseInt(seekerId),
					newStatus);
			ApproverDTO dto = ListOfValuesManager.findApproverById(dtoTemp
					.getApproverID());
			frequencyDTO = ListOfValuesManager.fetchFrequencyListForRideSeeker(
					dtoTemp.getSeekerID()).get(0);
			String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "
					+ dto.getbCode()
					+ "<br>Name: "
					+ dtoTemp.getUserName()
					+ "<br>Request ID: "
					+ dtoTemp.getSeekerID()
					+ "<br>From: "
					+ dtoTemp.getFromAddress1()
					+ "<br>To: "
					+ dtoTemp.getToAddress1()
					+ "<br>Date Time: "
					+ dtoTemp.getStartdateValue()
					+ "<br>Frequency: "
					+ frequencyDTO.getFrequency().toString();
			if (newStatus.equalsIgnoreCase("O")) {
				String approveLink = Messages.getValue(
						"ride.approve",
						new Object[] { dtoTemp.getSeekerID(),
								URLEncoder.encode(dto.getVerificationCode2()),
								dto.getId(), dto.getHoponId2() });
				String rejectLink = Messages.getValue(
						"ride.reject",
						new Object[] { dtoTemp.getSeekerID(),
								URLEncoder.encode(dto.getVerificationCode2()),
								dto.getId(), dto.getHoponId2() });

				if (!Validator.isEmpty(dtoTemp.getSecondApproverEmailId())) {
					messageContent += "<br> " + approveLink + " &nbsp;&nbsp;"
							+ rejectLink;
					if (ListOfValuesManager.testEmail(dtoTemp
							.getSecondApproverEmailId())) {
						UserRegistrationDTO userTemp = null;
						userTemp = ListOfValuesManager.findUserByEmail(dtoTemp
								.getSecondApproverEmailId());
						MessageBoardDTO userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(messageContent);
						userMessageDTO
								.setEmailSubject("Ride Request for Approval");
						userMessageDTO.setMessageChannel("E");
						userMessageDTO.setToMember(Integer.parseInt(userTemp
								.getId()));
						userMessageDTO = ListOfValuesManager
								.getInsertedMessage(userMessageDTO);
						userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(Messages.getValue(
								"ride.option.msgBoard",
								new Object[] { dto.getbCode(),
										dtoTemp.getSeekerID(),
										dtoTemp.getUserName(),
										dtoTemp.getFromAddress1(),
										dtoTemp.getToAddress1(),
										dtoTemp.getStartdateValue() }));
						userMessageDTO.setMessageChannel("M");
						userMessageDTO.setToMember(Integer.parseInt(userTemp
								.getId()));
						userMessageDTO = ListOfValuesManager
								.getInsertedMessage(userMessageDTO);
						userMessageDTO = new MessageBoardDTO();
						userMessageDTO
								.setMessage(Messages.getValue(
										"ride.option.sms",
										new Object[] {
												dto.getbCode(),
												dtoTemp.getSeekerID(),
												dtoTemp.getUserName(),
												dtoTemp.getFromAddress1()
														.substring(
																0,
																(dtoTemp.getFromAddress1()
																		.length() >= 20) ? 20
																		: dtoTemp
																				.getFromAddress1()
																				.length()),
												dtoTemp.getToAddress1()
														.substring(
																0,
																(dtoTemp.getToAddress1()
																		.length() >= 20) ? 20
																		: dtoTemp
																				.getToAddress1()
																				.length()),
												dtoTemp.getStartdateValue() }));
						userMessageDTO.setMessageChannel("S");
						userMessageDTO.setToMember(Integer.parseInt(userTemp
								.getId()));
						userMessageDTO = ListOfValuesManager
								.getInsertedMessage(userMessageDTO);
					} else {
						EmailDTO emaildto = new EmailDTO();
						emaildto.setReceiverEmailId(dto.getHoponId2());
						emaildto.setSubject("Ride Request for Approval");
						emaildto.setEmailTemplateBody(Messages.getValue(
								"email.template2", new Object[] { "", "",
										messageContent, "", "", "", "" }));
						MailService.sendMail(emaildto);
					}
				}
			} else if (newStatus.equalsIgnoreCase("A")) {
				MessageBoardDTO userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(messageContent);
				userMessageDTO.setEmailSubject("Ride request approved");
				userMessageDTO.setMessageChannel("E");
				userMessageDTO
						.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue(
						"ride.option.approved",
						new Object[] { dto.getbCode(), dtoTemp.getSeekerID(),
								dtoTemp.getUserName(),
								dtoTemp.getFromAddress1(),
								dtoTemp.getToAddress1(),
								dtoTemp.getStartdateValue() }));
				userMessageDTO.setMessageChannel("M");
				userMessageDTO
						.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue(
						"ride.option.approved",
						new Object[] { dto.getbCode(), dtoTemp.getSeekerID(),
								dtoTemp.getUserName(),
								dtoTemp.getFromAddress1(),
								dtoTemp.getToAddress1(),
								dtoTemp.getStartdateValue() }));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO
						.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
			} else if (newStatus.equalsIgnoreCase("I")) {
				MessageBoardDTO userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(messageContent);
				userMessageDTO.setEmailSubject("Ride request rejected");
				userMessageDTO.setMessageChannel("E");
				userMessageDTO
						.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue(
						"ride.option.rejected",
						new Object[] { dto.getbCode(), dtoTemp.getSeekerID(),
								dtoTemp.getUserName(),
								dtoTemp.getFromAddress1(),
								dtoTemp.getToAddress1(),
								dtoTemp.getStartdateValue() }));
				userMessageDTO.setMessageChannel("M");
				userMessageDTO
						.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue(
						"ride.option.rejected",
						new Object[] { dto.getbCode(), dtoTemp.getSeekerID(),
								dtoTemp.getUserName(),
								dtoTemp.getFromAddress1(),
								dtoTemp.getToAddress1(),
								dtoTemp.getStartdateValue() }));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO
						.setToMember(Integer.parseInt(dtoTemp.getUserID()));
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
			}
		}
		messageForLoginUser();
	}

	public String vehicleList() {
		vehicleMasterDTOList = ListOfValuesManager
				.getAllVehicleList(userRegistrationDTO.getId());

		vehicleMasterDataModel = new VehicleMasterDataTable(
				vehicleMasterDTOList);

		return "vehicle";
	}

	public void vehicleDefaultUpdate(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		String vehicleID = (String) requestMap.get("vehicleID");
		vehicleMasterDTO.setVehicleID(vehicleID);
		vehicleMasterDTO.setUserID(userRegistrationDTO.getId());
		vehicleMasterDTO = ListOfValuesManager
				.getUpdateVehicleDefault(vehicleMasterDTO);

		rollbackTest = false;
		vehicleMasterDTO = new VehicleMasterDTO();
		vehicleList();
		getAllVehicleList();
	}

	public String cancleRideManager() {
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		try {
			rideRegistered.setStatus("I");// rideRegistered.setStatus("0");rideRegistered.setStatus("I");
			rideRegistered = ListOfValuesManager.getCancleRide(rideRegistered,
					con);
			ListOfValuesManager.changePoolRequestStatusForRideGiver(con,
					Integer.parseInt(rideRegistered.getRideID()));
			rideRegistered = ListOfValuesManager
					.getRideManagerPopupDataDirect(rideRegistered.getRideID());

			UserRegistrationDTO userDto = new UserRegistrationDTO();
			userDto = ListOfValuesManager.findDriverDtoByRideId(
					rideRegistered.getRideID(), con);

			UserRegistrationDTO userDtoRide = new UserRegistrationDTO();
			userDtoRide = ListOfValuesManager.getUserById(Integer
					.parseInt(rideRegistered.getUserID()));

			userMessageDTO = new MessageBoardDTO();
			userMessageDTO
					.setMessage(Messages.getValue(
							"sms.cancel.driver.admin",
							new Object[] {
									userDto.getFirst_name(),
									rideRegistered.getRideID(),
									(rideRegistered.getFromAddress1().length() > 25) ? rideRegistered
											.getFromAddress1().substring(0, 25)
											: rideRegistered.getFromAddress1(),
									(rideRegistered.getToAddress1().length() > 25) ? rideRegistered
											.getToAddress1().substring(0, 25)
											: rideRegistered.getToAddress1(),
									rideRegistered.getStartdateValue() }));
			userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
			userMessageDTO.setRideId(Integer.parseInt(rideRegistered
					.getRideID()));
			userMessageDTO.setMessageChannel("S");
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);

			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setEmailSubject(Messages.getValue("subject.cancel",
					new Object[] { rideRegistered.getStartdateValue() }));
			userMessageDTO.setMessage(Messages.getValue(
					"ridegiver.driver.cancel",
					new Object[] { userDto.getFirst_name(),
							rideRegistered.getRideID(),
							rideRegistered.getFromAddress1(),
							rideRegistered.getToAddress1(),
							rideRegistered.getStartdateValue(),
							userDtoRide.getMobile_no() }));
			userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO
					.getId()));
			userMessageDTO.setRideId(Integer.parseInt(rideRegistered
					.getRideID()));
			userMessageDTO.setMessageChannel("E");
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);

			allSeekerForGivenRide = ListOfValuesManager
					.getAllRideSeekerForAGivenRide(rideRegistered.getRideID());
			if (allSeekerForGivenRide.size() > 0) {
				for (int index = 0; index < allSeekerForGivenRide.size(); index++) {
					String id = allSeekerForGivenRide.get(index).getSeekerID();
					rideSeekerDTO.setStatus("I");
					rideSeekerDTO.setSeekerID(id);
					rideSeekerDTO = ListOfValuesManager.getCancleRideSeeker(
							rideSeekerDTO, con);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages.getValue(
									"sms.cancel.passenger.admin",
									new Object[] {
											allSeekerForGivenRide.get(index)
													.getUserName(),
											rideRegistered.getRideID(),
											(allSeekerForGivenRide.get(index)
													.getFromAddress1().length() > 25) ? allSeekerForGivenRide
													.get(index)
													.getFromAddress1()
													.substring(0, 25)
													: allSeekerForGivenRide
															.get(index)
															.getFromAddress1(),
											(allSeekerForGivenRide.get(index)
													.getToAddress1().length() > 25) ? allSeekerForGivenRide
													.get(index).getToAddress1()
													.substring(0, 25)
													: allSeekerForGivenRide
															.get(index)
															.getToAddress1(),
											allSeekerForGivenRide.get(index)
													.getStartdateValue(),
											userRegistrationDTO.getMobile_no() }));
					userMessageDTO.setToMember(Integer
							.parseInt(allSeekerForGivenRide.get(index)
									.getUserID()));
					userMessageDTO.setRideId(Integer
							.parseInt(allSeekerForGivenRide.get(index)
									.getSeekerID()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setEmailSubject(Messages.getValue(
							"subject.cancel",
							new Object[] { allSeekerForGivenRide.get(index)
									.getStartdateValue() }));
					userMessageDTO.setMessage(Messages.getValue(
							"ridegiver.seeker.cancel",
							new Object[] {
									allSeekerForGivenRide.get(index)
											.getUserName(),
									rideRegistered.getRideID(),
									allSeekerForGivenRide.get(index)
											.getFromAddress1(),
									allSeekerForGivenRide.get(index)
											.getToAddress1(),
									allSeekerForGivenRide.get(index)
											.getStartdateValue(), }));
					userMessageDTO.setToMember(Integer
							.parseInt(allSeekerForGivenRide.get(index)
									.getUserID()));
					userMessageDTO.setRideId(Integer
							.parseInt(allSeekerForGivenRide.get(index)
									.getSeekerID()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

				}
			}
			rideManagementList();
			userMessageDTO = new MessageBoardDTO();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			}
		}

		rollbackTest = false;
		return "cancle";
	}

	public String cancleRideSeeker() {
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		try {
			rideSeekerDTO.setStatus("I");
			rideSeekerDTO = ListOfValuesManager.getCancleRideSeeker(
					rideSeekerDTO, con);
			ListOfValuesManager.changePoolRequestStatusForRideGiver(con,
					Integer.parseInt(rideSeekerDTO.getSeekerID()));
			rideSeekerDTO = ListOfValuesManager.getRideSeekerData(Integer
					.parseInt(rideSeekerDTO.getSeekerID()));

			try {
				Date d1 = ApplicationUtil.dateFormat1.parse(rideSeekerDTO
						.getStartdateValue());
				Date d2 = ApplicationUtil.dateFormat3.parse(ApplicationUtil
						.currentTimeStamp());
				long minuteDiff = ((d2.getTime() - d1.getTime()) / (60 * 60 * 1000));
				if (minuteDiff > 60) {
					PaymentTxnsDTO paymentTxnsDTO = new PaymentTxnsDTO();
					paymentTxnsDTO.setUpdatedBy(Integer
							.parseInt(userRegistrationDTO.getId()));
					paymentTxnsDTO.setSeekerId(Integer.parseInt(rideRegistered
							.getRideID()));
					paymentTxnsDTO.setFromPayer(100);
					paymentTxnsDTO.setToPayee(Integer
							.parseInt(userRegistrationDTO.getId()));

					ListOfValuesManager.paymentTxnCancel(paymentTxnsDTO, con);
				}
			} catch (ParseException ex) {
				LoggerSingleton.getInstance().error("Date parse exception");
			}

			if (rideSeekerDTO.getIsResult().equalsIgnoreCase("N")) {
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setEmailSubject(Messages.getValue(
						"subject.cancel",
						new Object[] { rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setMessage(Messages.getValue(
						"rideSeeker.unmatched.cancel",
						new Object[] { userRegistrationDTO.getFirst_name(),
								rideSeekerDTO.getSeekerID(),
								rideSeekerDTO.getFromAddress1(),
								rideSeekerDTO.getToAddress1(),
								rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setToMember(Integer.parseInt(rideSeekerDTO
						.getUserID()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO
						.getSeekerID()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);

				userMessageDTO = new MessageBoardDTO();
				userMessageDTO
						.setMessage(Messages
								.getValue(
										"sms.rideSeeker.unmatched.cancel",
										new Object[] {
												userRegistrationDTO
														.getFirst_name(),
												rideSeekerDTO.getSeekerID(),
												(rideSeekerDTO
														.getFromAddress1()
														.length() > 25) ? rideSeekerDTO
														.getFromAddress1()
														.substring(0, 25)
														: rideSeekerDTO
																.getFromAddress1(),
												(rideSeekerDTO.getToAddress1()
														.length() > 25) ? rideSeekerDTO
														.getToAddress1()
														.substring(0, 25)
														: rideSeekerDTO
																.getToAddress1(),
												rideSeekerDTO
														.getStartdateValue() }));
				userMessageDTO.setToMember(Integer.parseInt(rideSeekerDTO
						.getUserID()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
			} else {
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO
						.setMessage(Messages.getValue(
								"sms.cancel",
								new Object[] {
										userRegistrationDTO.getFirst_name(),
										(rideSeekerDTO.getFromAddress1()
												.length() > 25) ? rideSeekerDTO
												.getFromAddress1().substring(0,
														25) : rideSeekerDTO
												.getFromAddress1(),
										(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO
												.getToAddress1().substring(0,
														25) : rideSeekerDTO
												.getToAddress1(),
										ListOfValuesManager
												.getcurrentDate(ApplicationUtil.datePattern4),
										userRegistrationDTO.getMobile_no(),
										rideSeekerDTO.getRideMatchRideId() }));
				userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO
						.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO
						.getSeekerID()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);

				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setEmailSubject(Messages.getValue(
						"subject.cancel",
						new Object[] { rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setMessage(Messages.getValue(
						"rideSeeker.matched.cancel",
						new Object[] { userRegistrationDTO.getFirst_name(),
								rideSeekerDTO.getRideMatchRideId(),
								rideSeekerDTO.getFromAddress1(),
								rideSeekerDTO.getToAddress1(),
								rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO
						.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO
						.getSeekerID()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);

				/* Now message should go to driver. */
				UserRegistrationDTO userDto = new UserRegistrationDTO();
				userDto = ListOfValuesManager.findDriverDtoByRideId(
						rideSeekerDTO.getRideMatchRideId(), con);
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO
						.setMessage(Messages.getValue(
								"sms.cancel.driver",
								new Object[] {
										userDto.getFirst_name(),
										userRegistrationDTO.getFirst_name(),
										rideSeekerDTO.getRideMatchRideId(),
										(rideSeekerDTO.getFromAddress1()
												.length() > 25) ? rideSeekerDTO
												.getFromAddress1().substring(0,
														25) : rideSeekerDTO
												.getFromAddress1(),
										(rideSeekerDTO.getToAddress1().length() > 25) ? rideSeekerDTO
												.getToAddress1().substring(0,
														25) : rideSeekerDTO
												.getToAddress1(),
										ListOfValuesManager
												.getcurrentDate(ApplicationUtil.datePattern4),
										userRegistrationDTO.getMobile_no() }));
				userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO
						.getSeekerID()));
				userMessageDTO.setMessageChannel("S");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);

				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setEmailSubject(Messages.getValue(
						"subject.cancel",
						new Object[] { rideSeekerDTO.getStartdateValue() }));
				userMessageDTO.setMessage(Messages.getValue(
						"driver.match.cancel",
						new Object[] { userDto.getFirst_name(),
								userRegistrationDTO.getFirst_name(),
								rideSeekerDTO.getRideMatchRideId(),
								rideSeekerDTO.getFromAddress1(),
								rideSeekerDTO.getToAddress1(),
								rideSeekerDTO.getStartdateValue(),
								userDto.getMobile_no(),
								Messages.getValue("customer.support") }));
				userMessageDTO.setToMember(Integer.parseInt(userDto.getId()));
				userMessageDTO.setRideId(Integer.parseInt(rideSeekerDTO
						.getSeekerID()));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
			}
			userMessageDTO = new MessageBoardDTO();

			if (rideSeekerDTO.getRecurring().equalsIgnoreCase("Y")
					&& this.allowRecurringSubRideToCancel) {
				String[] rides = rideSeekerDTO.getSubSeekers().split(",");
				if (rides.length > 0) {
					rideSeekerDTO.setStatus("I");
					rideSeekerDTO = ListOfValuesManager.cancelSubSeekers(con,
							rideSeekerDTO);
					rides = rideSeekerDTO.getSubSeekers().split(",");
					for (int i = 0; i < rides.length; i++) {
						ListOfValuesManager
								.changePoolRequestStatusForRideGiver(con,
										Integer.parseInt(rides[i]));
						rideSeekerDTO = ListOfValuesManager
								.getRideSeekerData(Integer.parseInt(rides[i]));

						if (rideSeekerDTO.getIsResult().equalsIgnoreCase("N")) {
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages.getValue(
									"subject.cancel",
									new Object[] { rideSeekerDTO
											.getStartdateValue() }));
							userMessageDTO
									.setMessage(Messages
											.getValue(
													"rideSeeker.unmatched.cancel",
													new Object[] {
															userRegistrationDTO
																	.getFirst_name(),
															rideSeekerDTO
																	.getSeekerID(),
															rideSeekerDTO
																	.getFromAddress1(),
															rideSeekerDTO
																	.getToAddress1(),
															rideSeekerDTO
																	.getStartdateValue() }));
							userMessageDTO.setToMember(Integer
									.parseInt(rideSeekerDTO.getUserID()));
							userMessageDTO.setRideId(Integer
									.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO
									.setMessage(Messages
											.getValue(
													"sms.rideSeeker.unmatched.cancel",
													new Object[] {
															userRegistrationDTO
																	.getFirst_name(),
															rideSeekerDTO
																	.getSeekerID(),
															(rideSeekerDTO
																	.getFromAddress1()
																	.length() > 25) ? rideSeekerDTO
																	.getFromAddress1()
																	.substring(
																			0,
																			25)
																	: rideSeekerDTO
																			.getFromAddress1(),
															(rideSeekerDTO
																	.getToAddress1()
																	.length() > 25) ? rideSeekerDTO
																	.getToAddress1()
																	.substring(
																			0,
																			25)
																	: rideSeekerDTO
																			.getToAddress1(),
															rideSeekerDTO
																	.getStartdateValue() }));
							userMessageDTO.setToMember(Integer
									.parseInt(rideSeekerDTO.getUserID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);
						} else {
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO
									.setMessage(Messages
											.getValue(
													"sms.cancel",
													new Object[] {
															userRegistrationDTO
																	.getFirst_name(),
															(rideSeekerDTO
																	.getFromAddress1()
																	.length() > 25) ? rideSeekerDTO
																	.getFromAddress1()
																	.substring(
																			0,
																			25)
																	: rideSeekerDTO
																			.getFromAddress1(),
															(rideSeekerDTO
																	.getToAddress1()
																	.length() > 25) ? rideSeekerDTO
																	.getToAddress1()
																	.substring(
																			0,
																			25)
																	: rideSeekerDTO
																			.getToAddress1(),
															ListOfValuesManager
																	.getcurrentDate(ApplicationUtil.datePattern4),
															userRegistrationDTO
																	.getMobile_no(),
															rideSeekerDTO
																	.getRideMatchRideId() }));
							userMessageDTO.setToMember(Integer
									.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setRideId(Integer
									.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages.getValue(
									"subject.cancel",
									new Object[] { rideSeekerDTO
											.getStartdateValue() }));
							userMessageDTO
									.setMessage(Messages
											.getValue(
													"rideSeeker.matched.cancel",
													new Object[] {
															userRegistrationDTO
																	.getFirst_name(),
															rideSeekerDTO
																	.getRideMatchRideId(),
															rideSeekerDTO
																	.getFromAddress1(),
															rideSeekerDTO
																	.getToAddress1(),
															rideSeekerDTO
																	.getStartdateValue() }));
							userMessageDTO.setToMember(Integer
									.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setRideId(Integer
									.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							/* Now message should go to driver. */
							UserRegistrationDTO userDto = new UserRegistrationDTO();
							userDto = ListOfValuesManager
									.findDriverDtoByRideId(
											rideSeekerDTO.getRideMatchRideId(),
											con);
							userMessageDTO = new MessageBoardDTO();
							userMessageDTO
									.setMessage(Messages.getValue(
											"sms.cancel.driver",
											new Object[] {
													userDto.getFirst_name(),
													userRegistrationDTO
															.getFirst_name(),
													rideSeekerDTO
															.getRideMatchRideId(),
													(rideSeekerDTO
															.getFromAddress1()
															.length() > 25) ? rideSeekerDTO
															.getFromAddress1()
															.substring(0, 25)
															: rideSeekerDTO
																	.getFromAddress1(),
													(rideSeekerDTO
															.getToAddress1()
															.length() > 25) ? rideSeekerDTO
															.getToAddress1()
															.substring(0, 25)
															: rideSeekerDTO
																	.getToAddress1(),
													ListOfValuesManager
															.getcurrentDate(ApplicationUtil.datePattern4),
													userRegistrationDTO
															.getMobile_no() }));
							userMessageDTO.setToMember(Integer.parseInt(userDto
									.getId()));
							userMessageDTO.setRideId(Integer
									.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("S");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages.getValue(
									"subject.cancel",
									new Object[] { rideSeekerDTO
											.getStartdateValue() }));
							userMessageDTO
									.setMessage(Messages
											.getValue(
													"driver.match.cancel",
													new Object[] {
															userDto.getFirst_name(),
															userRegistrationDTO
																	.getFirst_name(),
															rideSeekerDTO
																	.getRideMatchRideId(),
															rideSeekerDTO
																	.getFromAddress1(),
															rideSeekerDTO
																	.getToAddress1(),
															rideSeekerDTO
																	.getStartdateValue(),
															userDto.getMobile_no(),
															Messages.getValue("customer.support") }));
							userMessageDTO.setToMember(Integer.parseInt(userDto
									.getId()));
							userMessageDTO.setRideId(Integer
									.parseInt(rideSeekerDTO.getSeekerID()));
							userMessageDTO.setMessageChannel("E");
							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);
						}
					}
				}
			}

			rideManagementList();
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			rollbackTest = true;
		} finally {
			if (rollbackTest) {
				try {
					con.rollback();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			} else {
				try {
					con.commit();
				} catch (SQLException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				ListOfValuesManager.releaseConnection(con);
			}
		}
		rollbackTest = false;
		return "cancle";
	}

	public void forgotPassword() {
		String emailTest = ListOfValuesManager.testEmailAllStatus(userName);

		clearScreenMessage();
		if (emailTest.equalsIgnoreCase("A")) {
			String tempPassword = ServerUtility.randomString(10);
			forregistrationOnly = ListOfValuesManager.getForgotPassword(
					userName, tempPassword, userRegistrationDTO.getId());
			successMessage
					.add("A new password has been sent to your email id.");
			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setEmailSubject(Messages
					.getValue("subject.forgot.password"));
			userMessageDTO.setMessage(Messages.getValue(
					"body.forgot.password",
					new Object[] { userName, tempPassword,
							forregistrationOnly.getFirst_name() }));
			userMessageDTO.setToMember(Integer.parseInt(forregistrationOnly
					.getId()));
			userMessageDTO.setMessageChannel("E");
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);

			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setMessage(Messages.getValue(
					"sms.forgot.password",
					new Object[] { userName, tempPassword,
							forregistrationOnly.getFirst_name() }));
			userMessageDTO.setToMember(Integer.parseInt(forregistrationOnly
					.getId()));
			userMessageDTO.setMessageChannel("S");
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);

			/*
			 * LoginPageDTO dto = new LoginPageDTO(); PageStoreDTO pageDto =
			 * ListOfValuesManager.getPageStoreByCode("changePassword");
			 * dto.setUserId
			 * (Integer.parseInt(forregistrationOnly.getId()));dto.setPageId
			 * (pageDto.getPageId()); dto.setStatus("N");
			 * ListOfValuesManager.insertLoginPage(dto);
			 */

		} else {
			if (emailTest.equalsIgnoreCase("P"))
				errorMessage.add("Email ID is pending for approval.");
			else if (emailTest.equalsIgnoreCase("I"))
				errorMessage
						.add("Email ID de activated. Please contact admin.");
			else
				errorMessage.add("Email ID does not exist in database.");
		}
		userMessageDTO = new MessageBoardDTO();
	}

	public void rateAndWriteNotes() {
		String taker = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		taker = (String) requestMap.get("taker");
		if (taker == null) {
			taker = "giver";
		}
		clearScreenMessage();
		poolRequestsDTO.setMasterControl(taker);
		poolRequestsDTO = ListOfValuesManager
				.getRateAndWriteNotes(poolRequestsDTO);
		successMessage.add("Ride successfully rated.");

		userMessageDTO = new MessageBoardDTO();
		userMessageDTO.setEmailSubject(Messages.getValue("subject.rate.ride"));
		userMessageDTO.setMessage(Messages.getValue("body.rate.ride",
				new Object[] { userRegistrationDTO.getFirst_name(),
						poolRequestsDTO.getRateRideGiver() }));
		userMessageDTO.setToMember(Integer.parseInt(poolRequestsDTO
				.getUser_id()));
		if (poolRequestsDTO.getRideSeekerID() != null)
			userMessageDTO.setRideId(Integer.parseInt(poolRequestsDTO
					.getRideSeekerID()));
		userMessageDTO.setMessageChannel("E");
		userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);

		rollbackTest = false;
		userMessageDTO = new MessageBoardDTO();
		allCompleateRideList();
		// return "notes";
	}

	public String getcurrentDate() {
		// DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern4);
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		return date;
	}

	public void declineCircle() {
		circleDTO = ListOfValuesManager.getDeclineCircle(circleDTO,
				userRegistrationDTO);

		rollbackTest = false;
		allListedCircle();
		allCircleForLoginUser();
		circleDTO = new CircleDTO();
		// return "decline";
	}

	public void findCircleByName() {
		allCircleListByName = ListOfValuesManager.getCircleByName(
				circleDTO.getName(), userRegistrationDTO.getId());

		forregistrationOnly.setMyCircle(circleDTO.getName());
		circleDTO = new CircleDTO();
	}

	public String findListofCompanyForLoginUser() {
		listofCompanyForLoginUser = ListOfValuesManager
				.getListofCompanyForLoginUser(userRegistrationDTO.getId());

		if (listofCompanyForLoginUser.size() > 0) {
			companyRegisterDTO = listofCompanyForLoginUser.get(0);
		} else {
			companyRegisterDTO = new CompanyRegisterDTO();
		}
		return "companyList";
	}

	public String showEditCompanyPage() {
		return "page";
	}

	public void changePassword() {
		clearScreenMessage();
		setRedirectUri("");
		if (userRegistrationDTO.getOldPassword() != null) {
			if (userRegistrationDTO.getPassword().equals(
					userRegistrationDTO.getOldPassword())) {
				userRegistrationDTO.setPassword(userRegistrationDTO
						.getRepassword1());
				CityDTO cityDto = ListOfValuesManager.getCity(
						userRegistrationDTO.getCity(),
						userRegistrationDTO.getState());
				userRegistrationDTO.setCityId(cityDto.getCityId());
				try {
					userRegistrationDTO = ListOfValuesManager
							.getUpdateUserPassword(userRegistrationDTO,
									userRegistrationDTO.getId(), null);
				} catch (ConfigurationException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
				userMessageDTO = new MessageBoardDTO();
				userMessageDTO.setMessage(Messages.getValue("password.changed",
						new Object[] { userRegistrationDTO.getPassword() }));
				userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO
						.getId()));
				userMessageDTO.setEmailSubject(Messages
						.getValue("subject.password.changed"));
				userMessageDTO.setMessageChannel("E");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
				successMessage.add(Messages.getValue("success.update",
						new Object[] { "Password" }));
				userMessageDTO = new MessageBoardDTO();

				List<LoginPageDTO> dtos = new ArrayList<LoginPageDTO>();
				dtos.addAll(ListOfValuesManager.getLoginPagesByUserId(Integer
						.parseInt(userRegistrationDTO.getId())));
				for (LoginPageDTO dto : dtos) {
					if (dto.getPageCode().equalsIgnoreCase(
							Messages.getValue("page.code.change.password")
									.trim())) {
						ListOfValuesManager.inactiveLoginPageByUserId(
								dto.getUserId(), dto.getPageId());
					}
				}
				for (LoginPageDTO dto : dtos) {
					if (dto.getPageCode().equalsIgnoreCase(
							Messages.getValue("page.code.preference").trim())) {
						redirectUri = dto.getPageURL();
					}
				}

			} else {
				userRegistrationDTO.setOldPassword("Fault");
				errorMessage.add(Messages.getValue("password.mismatch"));
			}
			userRegistrationDTO.setOldPassword(null);
		} else {
			errorMessage.add(Messages.getValue("error.required",
					new Object[] { "Password" }));
		}
	}

	public void editUserInformation() {
		clearScreenMessage();

		userRegistrationDTO.setMobile_no(userRegistrationDTO.getMobile_no()
				.replaceFirst("^[0|+]*", ""));

		if (Validator.isEmpty(userRegistrationDTO.getFirst_name())) {
			errorMessage.add("Please enter first name.");
		}
		if (Validator.isEmpty(userRegistrationDTO.getMobile_no())) {
			errorMessage.add("Please enter Mobile Number.");
		}
		if (Validator.isNotNumber(userRegistrationDTO.getMobile_no())
				|| userRegistrationDTO.getMobile_no().length() != 10) {
			errorMessage.add("Mobile Number is not proper.");
		}
		if (ListOfValuesManager.testOtherUniqueMobileNumber(
				userRegistrationDTO.getMobile_no(),
				Integer.parseInt(userRegistrationDTO.getId()))) {
			errorMessage
					.add("Mobile Number is alrady registered. Please enter another mobile number.");
		}
		if (Validator.isEmpty(userRegistrationDTO.getAddress())) {
			errorMessage.add("Please enter address.");
		}
		if (Validator.isEmpty(userRegistrationDTO.getTravel())) {
			errorMessage.add("Please select travel type.");
		}
		if (!(userRegistrationDTO.getTravel().equals("C")
				|| userRegistrationDTO.getTravel().equals("P")
				|| userRegistrationDTO.getTravel().equals("B") || userRegistrationDTO
				.getTravel().equals("T"))) {
			errorMessage.add("Please select proper travel type.");
		}

		if (errorMessage.size() == 0) {
			Connection con = (Connection) ListOfValuesManager
					.getBroadConnection();
			try {
				CityDTO cityDto = ListOfValuesManager.getCity(
						userRegistrationDTO.getCity(),
						userRegistrationDTO.getState());
				userRegistrationDTO.setCityId(cityDto.getCityId());
				userRegistrationDTO = ListOfValuesManager.getUpdateUser(
						userRegistrationDTO, userRegistrationDTO.getId(), con);
				successMessage.add(Messages.getValue("success.update",
						new Object[] { "User Preferences" }));

				userPreferences.setUserId(Integer.parseInt(userRegistrationDTO
						.getId()));
				String[] minuteSlice = userRegistrationDTO
						.getDefaultTimeSlice().split(":");
				if (minuteSlice.length > 1) {
					int minuteCount = Integer.parseInt(minuteSlice[0]) * 60; // convert
																				// hour
																				// to
																				// minute
					minuteCount += Integer.parseInt(minuteSlice[1]);
					userPreferences.setMaxWaitTime(minuteCount);
				}
				userPreferences = ListOfValuesManager.getUserPreferencesEdit(
						userPreferences, con);

				List<LoginPageDTO> dtos = new ArrayList<LoginPageDTO>();
				dtos.addAll(ListOfValuesManager.getLoginPagesByUserId(Integer
						.parseInt(userRegistrationDTO.getId())));

				for (LoginPageDTO dto : dtos) {
					if (dto.getPageCode().equalsIgnoreCase(
							Messages.getValue("page.code.preference").trim())) {
						ListOfValuesManager.inactiveLoginPageByUserId(
								dto.getUserId(), dto.getPageId());
					}
				}
				rollbackTest = false;
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				rollbackTest = true;
				errorMessage.add(Messages.getValue("error.db2",
						new Object[] { "User" }));
			} finally {
				if (rollbackTest) {
					try {
						con.rollback();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
				} else {
					try {
						con.commit();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
				}
				rollbackTest = false;
			}
		}

		if (errorMessage.size() > 0) {
			UserRegistrationDTO userTemp = ListOfValuesManager
					.getUserById(Integer.parseInt(userRegistrationDTO.getId()));
			userRegistrationDTO.setFirst_name(userTemp.getFirst_name());
			userRegistrationDTO.setLast_name(userTemp.getLast_name());
			userRegistrationDTO.setAddress(userTemp.getAddress());
			userRegistrationDTO.setCity(userTemp.getCity());
			userRegistrationDTO.setCityId(userTemp.getCityId());
			userRegistrationDTO.setState(userTemp.getState());
			userRegistrationDTO.setCountry(userTemp.getCountry());
			userRegistrationDTO.setLatitude(userTemp.getLatitude());
			userRegistrationDTO.setLongitude(userTemp.getLongitude());
			userRegistrationDTO.setPincode(userTemp.getPincode());
			userRegistrationDTO.setMobile_no(userTemp.getMobile_no());
			successMessage.clear();
		}
		userMessageDTO = new MessageBoardDTO();
		this.setRecurring(false);
		// return "editInformation";
	}

	public void joinCircle() {
		clearScreenMessage();
		circleMemberDTO.setCircleid(String.valueOf(circleDTO.getCircleID()));
		circleMemberDTO.setUserid(userRegistrationDTO.getId());
		circleMemberDTO.setRequestUserId(userRegistrationDTO.getId());
		String ctype = (String.valueOf(circleDTO.getCircleType()));
		String ttype = (String.valueOf(userRegistrationDTO.getTravel()));
		try {
			if (ListOfValuesManager.testSingleCircleMember(circleMemberDTO,
					userRegistrationDTO.getId(), ctype, ttype)) {
				circleMemberDTO = ListOfValuesManager.getJoinCircle(
						circleMemberDTO, userRegistrationDTO.getId(), null);
				// allCircleListByName =
				// ListOfValuesManager.getCircleByName("",userRegistrationDTO.getId());
				allCircleListByName = ListOfValuesManager.getCircleByName(
						forregistrationOnly.getMyCircle(),
						userRegistrationDTO.getId());
				CircleDTO dtoTemp = ListOfValuesManager
						.getCircleDtoByCircleId(circleDTO.getCircleID());
				userMessageDTO.setMessage("User "
						+ userRegistrationDTO.getFirst_name()
						+ " has sent a request to join " + dtoTemp.getName()
						+ " circle.");
				userMessageDTO
						.setToMember(dtoTemp.getCircleOwner_Member_Id_P());
				userMessageDTO.setMessageChannel("M");
				userMessageDTO = ListOfValuesManager
						.getInsertedMessage(userMessageDTO);
				userMessageDTO = new MessageBoardDTO();
				successMessage.add("You joined circle successfully.");
			} else {
				errorMessage
						.add("You do not have permission to join this circle.");
			}
		} catch (ConfigurationException e) {
			errorMessage
					.add("There is some problem in completing your request.");
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}

		circleDTO = new CircleDTO();
		// return "joinedCircle";
	}

	public void getCircleMemberInfo() {
		String x = circleOwnerManagerDTO.getCombineUserAndCircleID();
		String[] part = x.split(",");
		String userId = "0";
		try {
			userId = part[0];
		} catch (Exception e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}
		List<Integer> x2 = new ArrayList<Integer>();
		x2.add(Integer.parseInt(userId));
		List<UserRegistrationDTO> dtosTemp = ListOfValuesManager
				.getAllUserById(x2);

		if (dtosTemp.size() > 0) {
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
		if (circleDTO.getCircleID() > 0) {
			circleDTO = ListOfValuesManager.getDeactivateCircle(circleDTO);
			circleDTO = ListOfValuesManager.getCircleDtoByCircleId(circleDTO
					.getCircleID());
			userMessageDTO = new MessageBoardDTO();

			userMessageDTO.setMessage("User "
					+ userRegistrationDTO.getFirst_name()
					+ " De-Activated circle '" + circleDTO.getName() + "'.");
			userMessageDTO.setToMember(circleDTO.getCircleOwner_Member_Id_P());
			userMessageDTO.setCreatedBy(Integer.parseInt(userRegistrationDTO
					.getId()));
			userMessageDTO.setEmailSubject("Circle De-Activation");
			userMessageDTO.setMessageChannel("E");

			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);

			if (circleDTO.getStatus().equals("2")) {
				successMessage.add("circle De-Activated successfully.");
			} else {
				errorMessage
						.add("There is some issue in de activating circle.");
			}
			allListedCircle();
			allCircleForLoginUser();
			memberForSelectedCircle();
		} else {
			errorMessage.add("Please Select Circle first.");
		}
		circleDTO = new CircleDTO();
	}

	public void addMemberToCircle() {
		circleMemberDTO.setCircleid(String.valueOf(circleDTO.getCircleID()));
		String[] parts = forregistrationOnly.getFirst_name().split(",");
		String part2 = "";
		clearScreenMessage();
		if (!Validator.isNumberZeroNotAlloed(String.valueOf(circleDTO
				.getCircleID())))
			errorMessage.add("Please select Circle first.");

		if (parts.length < 2) {
			errorMessage.add("Please Select Member.");
		} else {
			part2 = parts[parts.length - 1];
		}

		if (errorMessage.size() == 0) {

			circleMemberDTO.setUserid(part2);
			circleMemberDTO.setStatus("0");
			if (errorMessage.size() == 0) {
				try {
					if (ListOfValuesManager.testSingleCircleMember(
							circleMemberDTO, userRegistrationDTO.getId(), "",
							"")) {
						circleMemberDTO = ListOfValuesManager.getJoinCircle(
								circleMemberDTO, userRegistrationDTO.getId(),
								null);
						successMessage
								.add("User added to circle. The request is pending for user approval.");
					} else {
						errorMessage
								.add("User is already part of another circle.");
					}
				} catch (ConfigurationException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
					errorMessage
							.add("There is some problem in adding member to circle..");
				}
			}
			memberForSelectedCircle();

			List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
			for (int i = 0; i < allCircleMemberForLoginUserList.size(); i++) {
				if (allCircleMemberForLoginUserList.get(i).getCircleID()
						.equals(circleMemberDTO.getCircleid())) {
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allCircleMemberForLoginUserList.get(i);
					allCircleMemberForLoginUserList1.add(dto);
				}
			}
			allCircleMemberForLoginUserList.clear();
			allCircleMemberForLoginUserList
					.addAll(allCircleMemberForLoginUserList1);
			allCircleMemberForLoginUserList1.clear();

			List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
			for (int i = 0; i < allPendingCircleMemberList.size(); i++) {
				if (allPendingCircleMemberList.get(i).getCircleID()
						.equals(circleMemberDTO.getCircleid())) {
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allPendingCircleMemberList.get(i);
					allPendingCircleMemberList1.add(dto);
				}
			}
			allPendingCircleMemberList.clear();
			allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
			allPendingCircleMemberList1.clear();

			// circleDTO = new CircleDTO();
			forregistrationOnly = new UserRegistrationDTO();
			// return "memberAdded";
		}
	}

	public void createMessage() {
		clearScreenMessage();
		List<Integer> userIds = null;
		String messageContent = getMessage();
		String messageTo = getMessagePlace();
		if (circleDTO.getCircleID() <= 0) {
			errorMessage.add("Please select circle first.");
		} else if (!(messageTo.equalsIgnoreCase("e")
				|| messageTo.equalsIgnoreCase("m") || messageTo
					.equalsIgnoreCase("b"))) {
			errorMessage.add("Please select where you want to send message.");
		} else if (Validator.isEmpty(messageContent.trim())) {
			errorMessage.add("Please wite message.");
		} else {
			userIds = ListOfValuesManager.findAllUsersInCircle(circleDTO
					.getCircleID());
			if (messageTo.equalsIgnoreCase("m")
					|| messageTo.equalsIgnoreCase("b")) {
				for (Integer user : userIds) {
					userMessageDTO = new MessageBoardDTO();

					userMessageDTO.setMessage(messageContent);
					userMessageDTO.setToMember(user);
					userMessageDTO.setMessageChannel("M");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
				}
			}
			if (messageTo.equalsIgnoreCase("e")
					|| messageTo.equalsIgnoreCase("b")) {
				for (Integer user : userIds) {
					userMessageDTO = new MessageBoardDTO();

					userMessageDTO.setEmailSubject("Message from "
							+ userRegistrationDTO.getFirst_name());
					userMessageDTO.setMessage(messageContent);
					userMessageDTO.setToMember(user);
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
				}
			}
			setMessage("");
			setMessagePlace("m");
			successMessage.add("Message sent successfully to circle members.");
		}
	}

	public void makeAdmin() {
		clearScreenMessage();
		// FacesContext context = FacesContext.getCurrentInstance();
		// Map<String,String> requestMap =
		// context.getExternalContext().getRequestParameterMap(); //In java
		// class, you can get back the parameter value with component
		// (submit-command buton) like this :
		// String userAndCircleId = (String)requestMap.get("userAndCircleId");
		// String circleId = (String)requestMap.get("circleId");
		String[] parts = circleOwnerManagerDTO.getCombineUserAndCircleID()
				.split(",");
		String part1 = parts[0];
		// String part2 = parts[1];
		if (!(circleDTO.getCircleID() > 0)) {
			errorMessage.add("Please select Circle.");
		} else if (!Validator.isNumberZeroNotAlloed(part1)) {
			errorMessage
					.add("Please select Circle Member you want to make admin.");
		} else {
			CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
			circleOwnerDTO.setCircleID(String.valueOf(circleDTO.getCircleID()));
			circleOwnerDTO.setUserID(part1);
			try {
				circleOwnerDTO = ListOfValuesManager.getregisterCircleOwner(
						circleOwnerDTO, userRegistrationDTO.getId(), null);
				successMessage.add("User is now admin of selected circle "
						+ circleDTO.getName() + ".");
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				rollbackTest = true;
				errorMessage
						.add("There is some problem in making user admin of circle "
								+ circleDTO.getName() + ".");
			}
			rollbackTest = false;
			memberForSelectedCircle();

			List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
			for (int i = 0; i < allCircleMemberForLoginUserList.size(); i++) {
				if (allCircleMemberForLoginUserList.get(i).getCircleID()
						.equals(circleOwnerDTO.getCircleID())) {
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allCircleMemberForLoginUserList.get(i);
					allCircleMemberForLoginUserList1.add(dto);
				}
			}
			allCircleMemberForLoginUserList.clear();
			allCircleMemberForLoginUserList
					.addAll(allCircleMemberForLoginUserList1);
			allCircleMemberForLoginUserList1.clear();

			List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
			for (int i = 0; i < allPendingCircleMemberList.size(); i++) {
				if (allPendingCircleMemberList.get(i).getCircleID()
						.equals(circleOwnerDTO.getCircleID())) {
					CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
					dto = allPendingCircleMemberList.get(i);
					allPendingCircleMemberList1.add(dto);
				}
			}
			allPendingCircleMemberList.clear();
			allPendingCircleMemberList.addAll(allPendingCircleMemberList1);
			allPendingCircleMemberList1.clear();
		}
		// return "admin";
	}

	public void removeAdmin() {
		String[] parts = circleOwnerManagerDTO.getCombineUserAndCircleID()
				.split(",");
		clearScreenMessage();
		if (!(circleDTO.getCircleID() > 0)) {
			errorMessage.add("Please select Circle.");
		} else if (!Validator.isNumberZeroNotAlloed(parts[0])) {
			errorMessage
					.add("Please select Circle Member you want to remove admin.");
		} else {

			String part1 = parts[0];
			String part2 = parts[2];
			CircleOwnerDTO circleOwnerDTO = new CircleOwnerDTO();
			circleOwnerDTO.setCircleID(String.valueOf(circleDTO.getCircleID()));
			circleOwnerDTO.setUserID(part1);
			if (part2.equalsIgnoreCase(part1)) {
				errorMessage.add("Circle can not remove admin authorisation.");
			} else {
				try {
					circleOwnerDTO = ListOfValuesManager
							.updateRegisterCircleOwner(circleOwnerDTO,
									userRegistrationDTO.getId(), null);
					successMessage
							.add("User is now not admin of selected circle "
									+ circleDTO.getName() + ".");
				} catch (ConfigurationException e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
					rollbackTest = true;
					errorMessage
							.add("There is some problem in removing admin authorisation of circle "
									+ circleDTO.getName() + ".");
				}
				rollbackTest = false;
				memberForSelectedCircle();

				List<CircleOwnerManagerDTO> allCircleMemberForLoginUserList1 = new ArrayList<CircleOwnerManagerDTO>();
				for (int i = 0; i < allCircleMemberForLoginUserList.size(); i++) {
					if (allCircleMemberForLoginUserList.get(i).getCircleID()
							.equals(circleOwnerDTO.getCircleID())) {
						CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
						dto = allCircleMemberForLoginUserList.get(i);
						allCircleMemberForLoginUserList1.add(dto);
					}
				}
				allCircleMemberForLoginUserList.clear();
				allCircleMemberForLoginUserList
						.addAll(allCircleMemberForLoginUserList1);
				allCircleMemberForLoginUserList1.clear();

				List<CircleOwnerManagerDTO> allPendingCircleMemberList1 = new ArrayList<CircleOwnerManagerDTO>();
				for (int i = 0; i < allPendingCircleMemberList.size(); i++) {
					if (allPendingCircleMemberList.get(i).getCircleID()
							.equals(circleOwnerDTO.getCircleID())) {
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
		// return"adminRemoved";
	}

	public void handleDateSelect(SelectEvent event) {
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern5);
		DateFormat dateFormat1 = new SimpleDateFormat(
				ApplicationUtil.datePattern6);
		// DateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy, HH.mm a");
		DateFormat dateFormat2 = new SimpleDateFormat(
				ApplicationUtil.datePattern4);
		Date date = null;
		try {
			date = dateFormat.parse(event.getObject().toString());

		} catch (ParseException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}
		String datevalue = dateFormat1.format(date);
		int days = Integer.valueOf(datevalue.substring(3, 5)) + 1;
		String tempValue = datevalue.substring(0, 3) + ""
				+ String.valueOf(days) + ""
				+ datevalue.substring(5, datevalue.length());
		try {
			date = dateFormat1.parse(tempValue);
			// date = dateFormat2.format(tempValue);
		} catch (ParseException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}
		minDate = dateFormat2.format(date);
	}

	public void recurringValue(AjaxBehaviorEvent event)
			throws AbortProcessingException {
		if (recurring == true) {
			recurring = false;
		}
		if (recurring == false) {
			recurring = true;
		}
	}

	public String matchTripLinking() {
		clearScreenMessage();
		if (ridePreMatchTest > 0) {
			List<String> groupMap = new ArrayList<String>();
			List<String> rideIdArr = new ArrayList<String>();
			for (MatchedTripDTO dto : matchedTripDTOs) {
				if (dto.getGroupId() != null && dto.getGroupId().length() > 0)
					groupMap.add(dto.getGroupId());
				if (!rideIdArr.contains(dto.getSeekerID())) {
					rideIdArr.add(dto.getSeekerID());
				}
			}

			List<MatchedTripDTO> temp1 = ListOfValuesManager
					.findMatchTripByGroupId(groupMap);
			int count1 = 0;
			for (MatchedTripDTO dto : temp1) {
				if (!rideIdArr.contains(dto.getSeekerID())) {
					count1++;
				}
			}
			int count2 = 0;
			MatchedTripDTO temp2[] = new MatchedTripDTO[count1
					+ matchedTripDTOs.length];
			for (int i = 0; i < temp1.size(); i++) {
				if (!rideIdArr.contains(temp1.get(i).getSeekerID())) {
					temp2[count2] = temp1.get(i);
					count2++;
				}
			}
			for (int i = 0; i < matchedTripDTOs.length; i++) {
				temp2[i + count2] = matchedTripDTOs[i];
			}
			matchedTripDTOs = temp2;
		}
		ridePreMatchTest = 0;

		Map<String, Integer> groupCountTemp = new HashMap<String, Integer>();
		Map<String, String> groupRideIdTemp = new HashMap<String, String>();
		for (int i = 0; i < matchedTripDTOs.length; i++) {
			if (matchedTripDTOs[i].getGroupId() != null
					&& matchedTripDTOs[i].getGroupId().length() > 0
					&& matchedTripDTOs[i].getCountTemp() != null) {
				groupCountTemp.put(matchedTripDTOs[i].getGroupId(),
						Integer.parseInt(matchedTripDTOs[i].getCountTemp()));
				groupRideIdTemp.put(matchedTripDTOs[i].getGroupId(),
						matchedTripDTOs[i].getRideId());
			}
		}
		for (int i = 0; i < matchedTripDTOs.length; i++) {
			if (groupCountTemp.containsKey(matchedTripDTOs[i].getGroupId())
					&& (matchedTripDTOs[i].getCountTemp() == null || matchedTripDTOs[i]
							.getCountTemp().length() == 0)) {
				groupCountTemp
						.put(matchedTripDTOs[i].getGroupId(), groupCountTemp
								.get(matchedTripDTOs[i].getGroupId()) - 1);
				matchedTripDTOs[i].setCountTemp(""
						+ groupCountTemp.get(matchedTripDTOs[i].getGroupId()));
			}
		}
		for (int i = 0; i < matchedTripDTOs.length; i++) {
			if (groupRideIdTemp.containsKey(matchedTripDTOs[i].getGroupId())) {
				matchedTripDTOs[i].setRideId(groupRideIdTemp
						.get(matchedTripDTOs[i].getGroupId()));
			}
		}

		// check concurrency here.
		List<Integer> seekerIdForConcurrency = new ArrayList<Integer>();
		// Map<String, String> seekerArrayForConcurrency = new HashMap<String,
		// String>();
		for (MatchedTripDTO dtoTemp1 : matchedTripDTOs) {
			seekerIdForConcurrency
					.add(Integer.parseInt(dtoTemp1.getSeekerID()));
			// seekerArrayForConcurrency.put(dtoTemp1.getSeekerID(), );
		}
		List<RideSeekerDTO> seekerDtoForConcurrency = ListOfValuesManager
				.findRideSeekerDataByIds(seekerIdForConcurrency);

		boolean test = true;
		if (seekerDtoForConcurrency == null
				|| seekerDtoForConcurrency.size() != matchedTripDTOs.length) {
			test = false;
		} else {
			for (RideSeekerDTO dtoTest : seekerDtoForConcurrency) {
				if ((!dtoTest.getStatus().equalsIgnoreCase("A") || !dtoTest
						.getIsResult().equalsIgnoreCase("N"))) {
					if (dtoTest.isNightRide()
							&& (dtoTest.getStatus().equalsIgnoreCase("O") || dtoTest
									.getStatus().equalsIgnoreCase("T"))) {
						continue;
					}
					test = false;
					break;
				}
			}
		}

		if (!test) {
			errorMessage
					.add("Some error has occured. Please try again after sometime.");
		} else {
			Connection con = (Connection) ListOfValuesManager
					.getBroadConnection();
			try {
				for (int i = 0; i < matchedTripDTOs.length; i++) {
					matchedTripDTOs[i].setCount(matchedTripDTOs[i]
							.getCountTemp());
					MatchedTripDTO dto = new MatchedTripDTO();
					RideManagementDTO rideManagementDTO = new RideManagementDTO();
					PoolRequestsDTO poolDTO = new PoolRequestsDTO();
					FrequencyDTO freqDTO = new FrequencyDTO();
					// FrequencyDTO frequencyDTO = new FrequencyDTO();
					dto = matchedTripDTOs[i];
					if (dto.getRideId().equalsIgnoreCase("NEW")) {
						rideManagementDTO.setFromAddress1(dto.getStartPoint());
						rideManagementDTO.setToAddress1(dto.getEndPoint());
						rideManagementDTO
								.setUserID(userRegistrationDTO.getId());
						rideManagementDTO.setVehicleID(dto.getVehicleID());
						rideManagementDTO.setStatus("T");// previously 2
						SimpleDateFormat formatter = new SimpleDateFormat(
								ApplicationUtil.datePattern3);
						DateFormat dateFormat = new SimpleDateFormat(
								ApplicationUtil.datePattern9);
						try {
							Date date = dateFormat.parse(dto.getStartDate());
							rideManagementDTO.setStartdateValue(formatter
									.format(date));
							date = formatter.parse(formatter.format(date));
							freqDTO.setTime(date);
						} catch (ParseException e1) {
							LoggerSingleton.getInstance().error(
									e1.getStackTrace()[0].getClassName()
											+ "->"
											+ e1.getStackTrace()[0]
													.getMethodName()
											+ "() : "
											+ e1.getStackTrace()[0]
													.getLineNumber() + " :: "
											+ e1.getMessage());
						}

						rideManagementDTO.setCreated_dt(ListOfValuesManager
								.getCurrentTimeStampDate());
						rideManagementDTO.setFlexiTimeAfter(ListOfValuesManager
								.getCurrentTimeStampDate());
						rideManagementDTO
								.setFlexiTimeBefore(ListOfValuesManager
										.getCurrentTimeStampDate());
						rideManagementDTO.setCreatedBy(userRegistrationDTO
								.getId());

						List<String> frequency = new ArrayList<String>();
						frequency.add(dto.getFrequency());
						freqDTO.setFrequency(frequency);

						rideManagementDTO = ListOfValuesManager.getRideEntery(
								"findByDTO", rideManagementDTO, con);
						freqDTO.setRideManagementId(rideManagementDTO
								.getRideID());
						freqDTO = ListOfValuesManager.getFrequencyEntery(
								"findByDTO", freqDTO, con);

					} else {

						if (Integer.valueOf(dto.getCount()) == 0) {
							return null;
						} else {
							rideManagementDTO.setRideID(dto.getRideId());
							rideManagementDTO.setStatus("I");// rideManagementDTO.setStatus("2");rideManagementDTO.setStatus("I");
							rideManagementDTO = ListOfValuesManager
									.getCancleRide(rideManagementDTO, con);
							rideManagementDTO = ListOfValuesManager
									.getRideManagerPopupDataDirect(dto
											.getRideId());

						}
					}

					/*
					 * ride_seeker_id is the passenger's ride id. ride_seeker_id
					 * -> ride_seeker_details -> seeker_id -> ride_seeker_id ->
					 * user_id ride_id is the ride id for driver. ride_id ->
					 * ride_management -> ride_id -> user_id
					 */
					RideSeekerDTO seekerDtoTemp = ListOfValuesManager
							.getRideSeekerData(Integer.parseInt(dto
									.getSeekerID()));
					// RideManagementDTO managementDtoTemp =
					// ListOfValuesManager.getRideManagerPopupData(rideManagementDTO.getRideID());
					// RideManagementDTO managementDtoTemp2 =
					// ListOfValuesManager.getRideManagerPopupData(dto.getSeekerID());

					poolDTO.setRidemanagerID(rideManagementDTO.getRideID());
					poolDTO.setRideSeekerID(dto.getSeekerID());
					poolDTO.setPoolRequestToId(Integer
							.parseInt(rideManagementDTO.getUserID()));
					poolDTO.setPoolRequestBy(Integer.parseInt(seekerDtoTemp
							.getUserID()));
					poolDTO = ListOfValuesManager.getInsertInPool(poolDTO, con);

					RideManagementDTO managementDtoTemp = new RideManagementDTO();
					managementDtoTemp.setRideID(rideManagementDTO.getRideID());
					managementDtoTemp.setUserID(rideManagementDTO.getUserID());
					managementDtoTemp.setVehicleID(rideManagementDTO
							.getVehicleID());
					managementDtoTemp.setFromAddress1(rideManagementDTO
							.getFromAddress1());
					managementDtoTemp.setToAddress1(rideManagementDTO
							.getToAddress1());
					managementDtoTemp.setStartdateValue(rideManagementDTO
							.getStartdateValue());
					managementDtoTemp.setCreatedBy(rideManagementDTO
							.getCreatedBy());
					managementDtoTemp.setStatus(rideManagementDTO.getStatus());

					List<Integer> x = new ArrayList<Integer>();
					x.add(Integer.parseInt(seekerDtoTemp.getUserID()));
					x.add(Integer.parseInt(managementDtoTemp.getUserID()));
					List<UserRegistrationDTO> dtosTemp = ListOfValuesManager
							.getAllUserById(x);

					UserRegistrationDTO seekerUserDto = null;
					UserRegistrationDTO managerUserDto = null;
					for (UserRegistrationDTO di : dtosTemp) {
						if (di.getId().equals(seekerDtoTemp.getUserID())) {
							seekerUserDto = di;
						} else if (di.getId().equals(
								managementDtoTemp.getUserID())) {
							managerUserDto = di;
						}
					}

					UserRegistrationDTO userDto = new UserRegistrationDTO();
					VehicleMasterDTO vehicleDto1 = new VehicleMasterDTO();
					if (Validator.isEmpty(rideManagementDTO.getVehicleID())
							|| rideManagementDTO.getVehicleID().equals("0")) {
						userDto = ListOfValuesManager.findUserDtoByVehicleId(
								Integer.parseInt(rideManagementDTO
										.getVehicleID()), con);
						vehicleDto1 = ListOfValuesManager
								.getVehicleDtoById(matchedTripDTOs[i]
										.getVehicleID());
					} else {
						userDto = ListOfValuesManager.findDriverDtoByRideId(
								rideManagementDTO.getRideID(), con);
						vehicleDto1 = ListOfValuesManager
								.getVehicleDtoById(rideManagementDTO
										.getVehicleID());
					}

					userMessageDTO = new MessageBoardDTO();

					userMessageDTO.setEmailSubject(Messages.getValue(
							"subject.match",
							new Object[] { dto.getStartDate() }));
					userMessageDTO.setMessage(Messages.getValue(
							"ridematched.seeker",
							new Object[] { seekerUserDto.getFirst_name(),
									userDto.getFirst_name(),
									rideManagementDTO.getRideID(),
									dto.getStartPoint(), dto.getEndPoint(),
									dto.getStartDate(),
									vehicleDto1.getReg_NO(),
									userDto.getMobile_no() }));
					userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp
							.getUserID()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setEmailSubject(Messages.getValue(
							"subject.match",
							new Object[] { dto.getStartDate() }));
					userMessageDTO.setMessage(Messages.getValue(
							"ridematched.driver",
							new Object[] {
									userDto.getFirst_name(),
									rideManagementDTO.getRideID(),
									dto.getStartPoint(),
									dto.getEndPoint(),
									dto.getStartDate(),
									seekerUserDto.getFirst_name() + " - "
											+ seekerUserDto.getMobile_no() }));
					userMessageDTO
							.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages
							.getValue(
									"sms.match",
									new Object[] { dto.getStartPoint(),
											dto.getEndPoint(),
											managerUserDto.getFirst_name(),
											dto.getStartDate(),
											managerUserDto.getMobile_no(),
											managementDtoTemp.getRideID(),
											seekerDtoTemp.getUserName(),
											vehicleDto1.getReg_NO(),
											dto.getSeekerID() }));
					userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp
							.getUserID()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages.getValue(
							"sms.match.driver",
							new Object[] { seekerDtoTemp.getFromAddress1(),
									seekerDtoTemp.getToAddress1(),
									seekerUserDto.getFirst_name(),
									seekerDtoTemp.getStartdateValue(),
									seekerUserDto.getMobile_no(),
									managementDtoTemp.getRideID(),
									userDto.getFirst_name() }));
					userMessageDTO
							.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					// vehicleMasterDTO=ListOfValuesManager.getUpdateSeat(vehicleMasterDTO);
					/*
					 * RideSeekerDTO dtoSeeker = new RideSeekerDTO();
					 * dtoSeeker.setSeekerID(dto.getSeekerID());
					 * dtoSeeker.setStatus("I"); dtoSeeker=
					 * ListOfValuesManager.getCancleRideSeeker(dtoSeeker,
					 * con);//use for update
					 */
					RideSeekerDTO dtoSeeker = new RideSeekerDTO();
					dtoSeeker.setSeekerID(dto.getSeekerID());
					dtoSeeker.setRideMatchRideId(rideManagementDTO.getRideID());
					dtoSeeker.setIsResult("Y");
					dtoSeeker = ListOfValuesManager.changeField(dtoSeeker, con);
				}
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				rollbackTest = true;
			} finally {
				if (rollbackTest) {
					try {
						con.rollback();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
				} else {
					try {
						con.commit();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
				}
				rollbackTest = false;
			}
		}

		rideManagementList();
		allCompleateRideList();
		vehicleList();
		// matchedTripListByCondition();

		matchedTripByConditionList.clear();
		matchedTripDataModel = new MatchedTripDataModel();
		vehicleMasterDTO = new VehicleMasterDTO();
		rideManagementListForPopupOne.clear();
		userMessageDTO = new MessageBoardDTO();
		return "matched";
	}

	public String ridePreMatchTripLinking() {
		clearScreenMessage();
		Map<String, List<Integer>> groupRideComb = new HashMap<String, List<Integer>>();
		Map<String, Integer> groupRideSingle = new HashMap<String, Integer>();
		Map<String, MatchedTripDTO> groupMatchTrip = new HashMap<String, MatchedTripDTO>();
		Map<String, Integer> groupMatchTest = new HashMap<String, Integer>();
		if (ridePreMatchTest > 0) {
			Map<String, String> groupVehicleMap = new HashMap<String, String>();
			Map<String, String> groupCountMap = new HashMap<String, String>();
			Map<String, String> groupCountTempMap = new HashMap<String, String>();
			Map<String, String> groupRideIdMap = new HashMap<String, String>();
			for (MatchedTripDTO dto : matchedTripDTOs) {
				if (dto.isSelectGroup()
						&& !Validator.isEmpty(dto.getVehicleID())) {
					groupVehicleMap.put(dto.getGroupId(), dto.getVehicleID());
				}
				if (dto.isSelectGroup() && !Validator.isEmpty(dto.getCount())) {
					groupCountMap.put(dto.getGroupId(), dto.getCount());
				}
				if (dto.isSelectGroup()
						&& !Validator.isEmpty(dto.getCountTemp())) {
					groupCountTempMap.put(dto.getGroupId(), dto.getCountTemp());
				}
				if (dto.isSelectGroup() && !Validator.isEmpty(dto.getRideId())) {
					groupRideIdMap.put(dto.getGroupId(), dto.getRideId());
				}
			}
			for (int i = 0; i < matchedTripDTOs.length; i++) {
				if (groupVehicleMap
						.containsKey(matchedTripDTOs[i].getGroupId())) {
					matchedTripDTOs[i].setVehicleID(groupVehicleMap
							.get(matchedTripDTOs[i].getGroupId()));
				}
				if (groupCountMap.containsKey(matchedTripDTOs[i].getGroupId())) {
					matchedTripDTOs[i].setCount(groupCountMap
							.get(matchedTripDTOs[i].getGroupId()));
				}
				if (groupCountTempMap.containsKey(matchedTripDTOs[i]
						.getGroupId())) {
					matchedTripDTOs[i].setCountTemp(groupCountTempMap
							.get(matchedTripDTOs[i].getGroupId()));
					groupCountTempMap.put(
							matchedTripDTOs[i].getGroupId(),
							""
									+ (Integer.parseInt(groupCountTempMap
											.get(matchedTripDTOs[i]
													.getGroupId())) - 1));
				}
				if (groupRideIdMap.containsKey(matchedTripDTOs[i].getGroupId())) {
					matchedTripDTOs[i].setRideId(groupRideIdMap
							.get(matchedTripDTOs[i].getGroupId()));
				}
			}
			/*
			 * List<String> groupMap = new ArrayList<String>(); List<String>
			 * rideIdArr = new ArrayList<String>(); //Here we are adding group
			 * to array. for(MatchedTripDTO dto:matchedTripDTOs) {
			 * if(dto.isSelectGroup() && dto.getGroupId() != null &&
			 * dto.getGroupId().length() > 0) groupMap.add(dto.getGroupId());
			 * if(!rideIdArr.contains(dto.getSeekerID())) {
			 * rideIdArr.add(dto.getSeekerID()); } } //Here we are fetching rest
			 * ride seeker data by group for matchTripDTOs.
			 * 
			 * List<MatchedTripDTO> temp1 =
			 * ListOfValuesManager.findMatchTripByGroupId(groupMap); int count1
			 * = 0; for(MatchedTripDTO dto:temp1) {
			 * if(!rideIdArr.contains(dto.getSeekerID())) { count1++; } } int
			 * count2 = 0; MatchedTripDTO temp2[] = new MatchedTripDTO[count1 +
			 * matchedTripDTOs.length]; for(int i =0;i<temp1.size();i++) {
			 * //if(!rideIdArr.contains(temp1.get(i).getSeekerID()))
			 * temp2[count2] = temp1.get(i); count2++; } for(int i
			 * =0;i<matchedTripDTOs.length;i++) {
			 * if(!matchedTripDTOs[i].isSelectGroup()) temp2[i+count2] =
			 * matchedTripDTOs[i]; } matchedTripDTOs = temp2;
			 */

			// Now matchedTripDTOs has all ride seeker data group wise.

			for (MatchedTripDTO dto : matchedTripDTOs) {
				if (groupRideComb.containsKey(dto.getGroupId())) {
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
			for (String group : groupRideComb.keySet()) {
				int rideForGroup = ListOfValuesManager
						.calculateSingleRide(groupRideComb.get(group));
				groupRideSingle.put(group, rideForGroup);
			}
			for (String group : groupRideSingle.keySet()) {
				String tempSeekerId = groupRideSingle.get(group) + "";
				for (MatchedTripDTO dto : matchedTripDTOs) {
					if (dto.getGroupId().equalsIgnoreCase(group)
							&& dto.getSeekerID().equalsIgnoreCase(tempSeekerId)) {
						groupMatchTrip.put(group, dto);
						break;
					}
				}
			}
		}
		ridePreMatchTest = 0;

		Map<String, Integer> groupCountTemp = new HashMap<String, Integer>();
		Map<String, String> groupRideIdTemp = new HashMap<String, String>();
		for (int i = 0; i < matchedTripDTOs.length; i++) {
			if (matchedTripDTOs[i].getGroupId() != null
					&& matchedTripDTOs[i].getGroupId().length() > 0
					&& matchedTripDTOs[i].getCountTemp() != null) {
				groupCountTemp.put(matchedTripDTOs[i].getGroupId(),
						Integer.parseInt(matchedTripDTOs[i].getCountTemp()));
				groupRideIdTemp.put(matchedTripDTOs[i].getGroupId(),
						matchedTripDTOs[i].getRideId());
			}
		}
		for (int i = 0; i < matchedTripDTOs.length; i++) {
			if (groupCountTemp.containsKey(matchedTripDTOs[i].getGroupId())
					&& (matchedTripDTOs[i].getCountTemp() == null || matchedTripDTOs[i]
							.getCountTemp().length() == 0)) {
				groupCountTemp
						.put(matchedTripDTOs[i].getGroupId(), groupCountTemp
								.get(matchedTripDTOs[i].getGroupId()) - 1);
				matchedTripDTOs[i].setCountTemp(""
						+ groupCountTemp.get(matchedTripDTOs[i].getGroupId()));
			}
		}
		for (int i = 0; i < matchedTripDTOs.length; i++) {
			if (groupRideIdTemp.containsKey(matchedTripDTOs[i].getGroupId())) {
				matchedTripDTOs[i].setRideId(groupRideIdTemp
						.get(matchedTripDTOs[i].getGroupId()));
			}
		}

		// check concurrency here.
		List<Integer> seekerIdForConcurrency = new ArrayList<Integer>();
		// Map<String, String> seekerArrayForConcurrency = new HashMap<String,
		// String>();
		for (MatchedTripDTO dtoTemp1 : matchedTripDTOs) {
			seekerIdForConcurrency
					.add(Integer.parseInt(dtoTemp1.getSeekerID()));
			// seekerArrayForConcurrency.put(dtoTemp1.getSeekerID(), );
		}
		List<RideSeekerDTO> seekerDtoForConcurrency = ListOfValuesManager
				.findRideSeekerDataByIds(seekerIdForConcurrency);

		boolean test = true;
		if (seekerDtoForConcurrency == null
				|| seekerDtoForConcurrency.size() != matchedTripDTOs.length) {
			test = false;
		} else {
			for (RideSeekerDTO dtoTest : seekerDtoForConcurrency) {
				if ((!dtoTest.getStatus().equalsIgnoreCase("A") || !dtoTest
						.getIsResult().equalsIgnoreCase("N"))) {
					if (dtoTest.isNightRide()
							&& (dtoTest.getStatus().equalsIgnoreCase("O") || dtoTest
									.getStatus().equalsIgnoreCase("T"))) {
						continue;
					}
					test = false;
					break;
				}
			}
		}

		if (!test) {
			errorMessage
					.add("Some error has occured. Please try again after sometime.");
		} else {
			// uncomment for sending mail in combined way.
			// Map<Integer, List<Integer>> driverMessageList = new
			// HashMap<Integer, List<Integer>>();
			Connection con = (Connection) ListOfValuesManager
					.getBroadConnection();
			try {
				for (int i = 0; i < matchedTripDTOs.length; i++) {
					matchedTripDTOs[i].setCount(matchedTripDTOs[i]
							.getCountTemp());
					MatchedTripDTO dto = new MatchedTripDTO();
					RideManagementDTO rideManagementDTO = new RideManagementDTO();
					PoolRequestsDTO poolDTO = new PoolRequestsDTO();
					FrequencyDTO freqDTO = new FrequencyDTO();
					// FrequencyDTO frequencyDTO = new FrequencyDTO();
					dto = matchedTripDTOs[i];
					MatchedTripDTO dtoNew = groupMatchTrip
							.get(dto.getGroupId());
					if (dto.getRideId().equalsIgnoreCase("NEW")) {

						rideManagementDTO.setFromAddress1(dtoNew
								.getStartPoint());
						rideManagementDTO.setToAddress1(dtoNew.getEndPoint());
						rideManagementDTO
								.setUserID(userRegistrationDTO.getId());
						rideManagementDTO.setVehicleID(dtoNew.getVehicleID());
						rideManagementDTO.setStatus("T");// previously 2
						SimpleDateFormat formatter = new SimpleDateFormat(
								ApplicationUtil.datePattern3);
						DateFormat dateFormat = new SimpleDateFormat(
								ApplicationUtil.datePattern9);
						try {
							Date date = dateFormat.parse(dtoNew.getStartDate());
							rideManagementDTO.setStartdateValue(formatter
									.format(date));
							date = formatter.parse(formatter.format(date));
							freqDTO.setTime(date);
						} catch (ParseException e1) {
							LoggerSingleton.getInstance().error(
									e1.getStackTrace()[0].getClassName()
											+ "->"
											+ e1.getStackTrace()[0]
													.getMethodName()
											+ "() : "
											+ e1.getStackTrace()[0]
													.getLineNumber() + " :: "
											+ e1.getMessage());
						}

						rideManagementDTO.setCreated_dt(ListOfValuesManager
								.getCurrentTimeStampDate());
						rideManagementDTO.setFlexiTimeAfter(ListOfValuesManager
								.getCurrentTimeStampDate());
						rideManagementDTO
								.setFlexiTimeBefore(ListOfValuesManager
										.getCurrentTimeStampDate());
						rideManagementDTO.setCreatedBy(userRegistrationDTO
								.getId());

						List<String> frequency = new ArrayList<String>();
						frequency.add(dtoNew.getFrequency());
						freqDTO.setFrequency(frequency);
						if (groupMatchTest.get(dto.getGroupId()) == 0) {
							rideManagementDTO = ListOfValuesManager
									.getRideEntery("findByDTO",
											rideManagementDTO, con);
							freqDTO.setRideManagementId(rideManagementDTO
									.getRideID());
							freqDTO = ListOfValuesManager.getFrequencyEntery(
									"findByDTO", freqDTO, con);
							groupMatchTest.put(dto.getGroupId(), Integer
									.parseInt(rideManagementDTO.getRideID()));
						} else {
							rideManagementDTO.setRideID(groupMatchTest.get(dto
									.getGroupId()) + "");
							freqDTO.setRideManagementId(groupMatchTest.get(dto
									.getGroupId()) + "");
						}

					} else {

						if (Integer.valueOf(dto.getCount()) == 0) {
							return null;
						} else {
							rideManagementDTO.setRideID(dto.getRideId());
							rideManagementDTO.setStatus("I");// rideManagementDTO.setStatus("2");rideManagementDTO.setStatus("I");
							rideManagementDTO = ListOfValuesManager
									.getCancleRide(rideManagementDTO, con);
							rideManagementDTO = ListOfValuesManager
									.getRideManagerPopupDataDirect(dto
											.getRideId());
						}
					}

					/*
					 * //uncomment for sending mail in combined way.
					 * if(driverMessageList
					 * .containsKey(poolDTO.getRidemanagerID())) {
					 * driverMessageList
					 * .get(poolDTO.getRidemanagerID()).add(Integer
					 * .parseInt(poolDTO.getRideSeekerID())); }
					 */

					/*
					 * ride_seeker_id is the passenger's ride id. ride_seeker_id
					 * -> ride_seeker_details -> seeker_id -> ride_seeker_id ->
					 * user_id ride_id is the ride id for driver. ride_id ->
					 * ride_management -> ride_id -> user_id
					 */
					RideSeekerDTO seekerDtoTemp = ListOfValuesManager
							.getRideSeekerData(Integer.parseInt(dto
									.getSeekerID()));
					// RideManagementDTO managementDtoTemp =
					// ListOfValuesManager.getRideManagerPopupData(rideManagementDTO.getRideID());
					// RideManagementDTO managementDtoTemp2 =
					// ListOfValuesManager.getRideManagerPopupData(dto.getSeekerID());

					poolDTO.setRidemanagerID(rideManagementDTO.getRideID());
					poolDTO.setRideSeekerID(dto.getSeekerID());
					poolDTO.setPoolRequestToId(Integer
							.parseInt(rideManagementDTO.getUserID()));
					poolDTO.setPoolRequestBy(Integer.parseInt(seekerDtoTemp
							.getUserID()));
					poolDTO = ListOfValuesManager.getInsertInPool(poolDTO, con);

					RideManagementDTO managementDtoTemp = new RideManagementDTO();
					managementDtoTemp.setRideID(rideManagementDTO.getRideID());
					managementDtoTemp.setUserID(rideManagementDTO.getUserID());
					managementDtoTemp.setVehicleID(rideManagementDTO
							.getVehicleID());
					managementDtoTemp.setFromAddress1(rideManagementDTO
							.getFromAddress1());
					managementDtoTemp.setToAddress1(rideManagementDTO
							.getToAddress1());
					managementDtoTemp.setStartdateValue(rideManagementDTO
							.getStartdateValue());
					managementDtoTemp.setCreatedBy(rideManagementDTO
							.getCreatedBy());
					managementDtoTemp.setStatus(rideManagementDTO.getStatus());

					List<Integer> x = new ArrayList<Integer>();
					x.add(Integer.parseInt(seekerDtoTemp.getUserID()));
					x.add(Integer.parseInt(managementDtoTemp.getUserID()));
					List<UserRegistrationDTO> dtosTemp = ListOfValuesManager
							.getAllUserById(x);

					UserRegistrationDTO seekerUserDto = null;
					UserRegistrationDTO managerUserDto = null;
					for (UserRegistrationDTO di : dtosTemp) {
						if (di.getId().equals(seekerDtoTemp.getUserID())) {
							seekerUserDto = di;
						} else if (di.getId().equals(
								managementDtoTemp.getUserID())) {
							managerUserDto = di;
						}
					}

					UserRegistrationDTO userDto = new UserRegistrationDTO();
					VehicleMasterDTO vehicleDto1 = new VehicleMasterDTO();
					if (Validator.isEmpty(rideManagementDTO.getVehicleID())
							|| rideManagementDTO.getVehicleID().equals("0")) {
						userDto = ListOfValuesManager.findDriverDtoByRideId(
								rideManagementDTO.getRideID(), con);
					} else {
						userDto = ListOfValuesManager.findUserDtoByVehicleId(
								Integer.parseInt(rideManagementDTO
										.getVehicleID()), con);
					}
					vehicleDto1 = ListOfValuesManager
							.getVehicleDtoById(rideManagementDTO.getVehicleID());

					userMessageDTO = new MessageBoardDTO();

					userMessageDTO.setEmailSubject(Messages.getValue(
							"subject.match",
							new Object[] { dto.getStartDate() }));
					userMessageDTO.setMessage(Messages.getValue(
							"ridematched.seeker",
							new Object[] { seekerUserDto.getFirst_name(),
									userDto.getFirst_name(),
									rideManagementDTO.getRideID(),
									dto.getStartPoint(), dto.getEndPoint(),
									dto.getStartDate(),
									vehicleDto1.getReg_NO(),
									userDto.getMobile_no() }));
					userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp
							.getUserID()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setEmailSubject(Messages.getValue(
							"subject.match",
							new Object[] { dto.getStartDate() }));
					userMessageDTO.setMessage(Messages.getValue(
							"ridematched.driver",
							new Object[] {
									userDto.getFirst_name(),
									rideManagementDTO.getRideID(),
									dto.getStartPoint(),
									dto.getEndPoint(),
									dto.getStartDate(),
									seekerUserDto.getFirst_name() + " - "
											+ seekerUserDto.getMobile_no() }));
					userMessageDTO
							.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setMessageChannel("E");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages
									.getValue(
											"sms.match",
											new Object[] {
													(dto.getStartPoint()
															.length() > 25) ? dto
															.getStartPoint()
															.substring(0, 25)
															: dto.getStartPoint(),
													(dto.getEndPoint().length() > 25) ? dto
															.getEndPoint()
															.substring(0, 25)
															: dto.getEndPoint(),
													managerUserDto
															.getFirst_name(),
													dto.getStartDate(),
													managerUserDto
															.getMobile_no(),
													managementDtoTemp
															.getRideID(),
													seekerDtoTemp.getUserName(),
													vehicleDto1.getReg_NO(),
													dto.getSeekerID() }));
					userMessageDTO.setToMember(Integer.parseInt(seekerDtoTemp
							.getUserID()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);

					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages
									.getValue(
											"sms.match.driver",
											new Object[] {
													(seekerDtoTemp
															.getFromAddress1()
															.length() > 25) ? seekerDtoTemp
															.getFromAddress1()
															.substring(0, 25)
															: seekerDtoTemp
																	.getFromAddress1(),
													(seekerDtoTemp
															.getToAddress1()
															.length() > 25) ? seekerDtoTemp
															.getToAddress1()
															.substring(0, 25)
															: seekerDtoTemp
																	.getToAddress1(),
													seekerUserDto
															.getFirst_name(),
													seekerDtoTemp
															.getStartdateValue(),
													seekerUserDto
															.getMobile_no(),
													managementDtoTemp
															.getRideID(),
													userDto.getFirst_name(),
													vehicleDto1.getReg_NO() }));
					userMessageDTO
							.setToMember(Integer.parseInt(userDto.getId()));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
					// vehicleMasterDTO=ListOfValuesManager.getUpdateSeat(vehicleMasterDTO);
					/*
					 * RideSeekerDTO dtoSeeker = new RideSeekerDTO();
					 * dtoSeeker.setSeekerID(dto.getSeekerID());
					 * dtoSeeker.setStatus("I"); dtoSeeker=
					 * ListOfValuesManager.getCancleRideSeeker(dtoSeeker,
					 * con);//use for update
					 */
					RideSeekerDTO dtoSeeker = new RideSeekerDTO();
					dtoSeeker.setSeekerID(dto.getSeekerID());
					dtoSeeker.setRideMatchRideId(rideManagementDTO.getRideID());
					dtoSeeker.setIsResult("Y");
					dtoSeeker = ListOfValuesManager.changeField(dtoSeeker, con);
				}
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				rollbackTest = true;
			} finally {
				if (rollbackTest) {
					try {
						con.rollback();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
				} else {
					try {
						con.commit();
					} catch (SQLException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					ListOfValuesManager.releaseConnection(con);
				}
				rollbackTest = false;
			}
		}
		rideManagementList();
		allCompleateRideList();
		vehicleList();
		// matchedTripListByCondition();

		matchedTripByConditionList.clear();
		matchedTripDataModel = new MatchedTripDataModel();
		vehicleMasterDTO = new VehicleMasterDTO();
		rideManagementListForPopupOne.clear();
		userMessageDTO = new MessageBoardDTO();
		return "matched";
	}

	public void processValue(AjaxBehaviorEvent event)
			throws AbortProcessingException {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		rowForTaxiLinking = (String) requestMap.get("row");
		String startDate = (String) requestMap.get("startDate");
		matchedTripDTO.setStartDate(startDate);
		String value = (String) ((UIInput) event.getSource()).getValue();
		if (value == "" || value == null) {
			matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking))
					.setRideId(null);
			matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking))
					.setCount(null);
			matchedTripDataModel = new MatchedTripDataModel(
					matchedTripByConditionList);
			rideManagementListForPopupOne.clear();
		} else {
			for (int i = 0; i < vehicleMasterDTOList.size(); i++) {
				if (vehicleMasterDTOList.get(i).getVehicleID().equals(value)) {
					MatchedTripDTO dto1 = new MatchedTripDTO();

					dto1 = matchedTripByConditionList.get(Integer
							.valueOf(rowForTaxiLinking));
					MatchedTripDTO matchedTripDTO = new MatchedTripDTO();
					Date date = null;
					try {
						// DateFormat dateFormat = new
						// SimpleDateFormat(ApplicationUtil.datePattern1);
						DateFormat dateFormat = new SimpleDateFormat(
								ApplicationUtil.datePattern3);
						SimpleDateFormat formatter1 = new SimpleDateFormat(
								ApplicationUtil.datePattern2);
						date = formatter1.parse(dto1.getStartDate());
						String date1 = dateFormat.format(date);
						matchedTripDTO = ListOfValuesManager.getCount(value,
								date1, "com");

						dto1 = matchedTripByConditionList.get(Integer
								.valueOf(rowForTaxiLinking));
						dateFormat = new SimpleDateFormat(
								ApplicationUtil.datePattern1);
						date = formatter1.parse(dto1.getStartDate());
						date1 = dateFormat.format(date);
						if (matchedTripDTO.getCount() != null) {
							if (matchedTripDTO.getCount().equalsIgnoreCase(
									vehicleMasterDTOList.get(i).getCapacity())
									|| Integer.valueOf(vehicleMasterDTOList
											.get(i).getCapacity()) <= Integer
											.valueOf(matchedTripDTO.getCount())) {
								dto1.setCount(ApplicationUtil.seatAbsent);
								rideManagementListForPopupOne.clear();
							} else {
								int co = Integer.valueOf(vehicleMasterDTOList
										.get(i).getCapacity())
										- Integer.valueOf(matchedTripDTO
												.getCount());
								dto1.setCount(String.valueOf(co));
							}
						} else {
							dto1.setCount(vehicleMasterDTOList.get(i)
									.getCapacity());
						}
						vehicleMasterDTO = vehicleMasterDTOList.get(i);
						matchedTripByConditionList.set(
								Integer.valueOf(rowForTaxiLinking), dto1);

						rideManagementListForPopupOne = ListOfValuesManager
								.getMethodForPopupOne(value, date1);

						if (rideManagementListForPopupOne.size() != 0) {
							if (matchedTripDTO.getCount() != null
									&& (matchedTripDTO.getCount()
											.equalsIgnoreCase(
													vehicleMasterDTOList.get(i)
															.getCapacity()) || Integer
											.valueOf(vehicleMasterDTOList
													.get(i).getCapacity()) <= Integer
											.valueOf(matchedTripDTO.getCount()))) {
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
							matchedTripByConditionList.get(
									Integer.valueOf(rowForTaxiLinking))
									.setRideId(ApplicationUtil.rideStatus);
						}
					} catch (ParseException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
					}
					matchedTripDataModel = new MatchedTripDataModel(
							matchedTripByConditionList);
				}
			}
		}
	}

	public void rideIdSetForLinking() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		String rideID = (String) requestMap.get("rideID");
		// String rowIndex = (String)requestMap.get("row");

		/*
		 * if(rowIndex==null){ vehicleMasterDTO =
		 * vehicleMasterDTOList.get(Integer.valueOf(rowIndex)); }
		 */
		matchedTripByConditionList.get(Integer.valueOf(rowForTaxiLinking))
				.setRideId(rideID);
		matchedTripDataModel = new MatchedTripDataModel(
				matchedTripByConditionList);
		// return "linking";
	}

	public String showAllRideSeekerForRideInPopup() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		String rideID = (String) requestMap.get("rideID");

		try {
			vehicleMasterDTO = ListOfValuesManager.getVehicleDtoByRideId(
					rideID, null);
		} catch (ConfigurationException e) {
			vehicleMasterDTO = new VehicleMasterDTO();
		}
		allSeekerForGivenRide = ListOfValuesManager
				.getAllRideSeekerForAGivenRide(rideID);

		return "allSeeker";
	}

	public String showRideSeekerPopup() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		String rideID = (String) requestMap.get("rideId");
		rideManagerInfoForRideSeeker = ListOfValuesManager
				.getRideManagerPopupData(rideID);

		vehicleMasterDTO = ListOfValuesManager
				.getVehicleDtoById(rideManagerInfoForRideSeeker.getVehicleID());
		try {
			vehicleMasterDTO = ListOfValuesManager.getVehicleDtoByRideId(
					rideID, null);
		} catch (ConfigurationException e) {
			vehicleMasterDTO = new VehicleMasterDTO();
		}

		try {
			UserRegistrationDTO userDto = ListOfValuesManager
					.findUserDtoByVehicleId(Integer
							.parseInt(rideManagerInfoForRideSeeker
									.getVehicleID()), null);
			rideManagerInfoForRideSeeker.setUserID(userDto.getId());
			rideManagerInfoForRideSeeker.setCreatedBy(userDto.getFirst_name());
		} catch (NumberFormatException e) {
		} catch (ConfigurationException e) {
		}

		/*
		 * userDto =
		 * ListOfValuesManager.findUserDtoByVehicleId(Integer.parseInt(
		 * rideManagementDTO.getVehicleID()), con);
		 */
		return "allSeeker";
	}

	public void updateVehicle() {
		clearScreenMessage();
		vehicleMasterDTO = ListOfValuesManager
				.getUpdateVehicle(vehicleMasterDTO);
		successMessage.add(Messages.getValue("success.update",
				new Object[] { "Vehicle" }));

		rollbackTest = false;
		// vehicleMasterDTO = new VehicleMasterDTO();
		vehicleList();
		// return "updateVehicle";
	}

	public void inActivateVehicle() {
		clearScreenMessage();
		for (int i = 0; i < vehicleMasterDtos.length; i++) {
			vehicleMasterDTO = vehicleMasterDtos[i];
			vehicleMasterDTO = ListOfValuesManager
					.getInActivateVehicle(vehicleMasterDTO);
			successMessage.add(Messages.getValue("success.delete",
					new Object[] { "Vehicle" }));
			rollbackTest = false;
		}
		vehicleMasterDTO = new VehicleMasterDTO();
		vehicleList();
		// return "inActivateVehicle";
	}

	public void showVehicleDetailsInpopup() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		String vehicleID = (String) requestMap.get("vehicleID");
		for (int i = 0; i < vehicleMasterDTOList.size(); i++) {
			if (vehicleID.equalsIgnoreCase(vehicleMasterDTOList.get(i)
					.getVehicleID())) {
				vehicleMasterDTO = vehicleMasterDTOList.get(i);
			}
		}
	}

	public void showLoginUserUnreadMessageList() {
		/*
		 * FacesContext context = FacesContext.getCurrentInstance();
		 * Map<String,String> requestMap =
		 * context.getExternalContext().getRequestParameterMap(); //In java
		 * class, you can get back the parameter value with component
		 * (submit-command buton) like this : int row =
		 * Integer.parseInt(requestMap.get("row")); HttpSession currentSession =
		 * ServerUtility.getSession();
		 * currentSession.setAttribute("unreadMessageRowNo", row);
		 */
		allUnreadMessageList.clear();
		allUnreadMessageList.addAll(ListOfValuesManager
				.getAllUnreadMessage(userRegistrationDTO.getId()));

	}

	public void populatePopupMessage() {
		allpopUpMessageList.clear();
		allpopUpMessageList.addAll(ListOfValuesManager
				.getAllPopupMessage(Integer.parseInt(userRegistrationDTO
						.getId())));
	}

	public void clearPopupMessage() {
		int[] row = new int[allpopUpMessageList.size()];
		int i = 0;
		for (MessageBoardDTO dto : allpopUpMessageList) {
			row[i++] = dto.getMessageId();
		}
		ListOfValuesManager.makePopupMessageRead(row);
		allpopUpMessageList.clear();
	}

	public void makeMessageReadAll() {
		int[] row = new int[allUnreadMessageList.size()];
		int i = 0;
		for (MessageBoardDTO dto : allUnreadMessageList) {
			row[i++] = dto.getMessageId();
		}

		ListOfValuesManager.makeBoardMessageRead(row);
	}

	public String allLoginUserMessageList() {
		allUnreadMessageList.clear();

		allUnreadMessageList.addAll(ListOfValuesManager
				.getAllUnreadMessage(userRegistrationDTO.getId()));

		return "messageList";
	}

	public void makeBoardMessageRead() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		int row = Integer.parseInt(requestMap.get("row"));
		int[] rowAray = { row };
		ListOfValuesManager.makeBoardMessageRead(rowAray);
		rollbackTest = false;
		allLoginUserMessageList();
	}

	public void makeBoardMessageDelete() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		int row = Integer.parseInt(requestMap.get("row"));
		int[] rowAray = { row };
		ListOfValuesManager.makeBoardMessageDelete(rowAray);
		rollbackTest = false;
	}

	public void handleVehicleUpload(FileUploadEvent event) {
		UploadedFile item = event.getFile();
		String tomcatDirectoryPath = ApplicationUtil.catalinaDirectoryPath;
		String extension = ServerUtility.getExtension(item);
		String path = null;
		File vehiclePath = null;
		if (tomcatDirectoryPath != null) {
			String vehicleDirPath = ApplicationUtil.vehicleDirectoryPath;
			path = ServerUtility.constructTargetFileName("vehicle_"
					+ userRegistrationDTO.getId(), extension, vehicleDirPath);
			vehiclePath = new File(vehicleDirPath + path);
			try {
				InputStream input = item.getInputstream();
				OutputStream output = new FileOutputStream(vehiclePath);
				try {
					IOUtils.copy(input, output);
				} catch (Exception e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				} finally {
					input.close();
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}

			Workbook workbook;
			try {
				workbook = Workbook.getWorkbook(vehiclePath);
				Sheet sheet = workbook.getSheet(0);
				clearScreenMessage();
				for (int i = 1;; i++) {
					Cell cell = sheet.getCell(0, i);
					String make = cell.getContents();
					cell = sheet.getCell(1, i);
					String model = cell.getContents();
					cell = sheet.getCell(2, i);
					String color = cell.getContents();
					cell = sheet.getCell(3, i);
					String registrationNo = cell.getContents();
					cell = sheet.getCell(4, i);
					String capacity = cell.getContents();
					if ((make.trim() == null || make.trim().isEmpty())
							&& (model.trim() == null || model.trim().isEmpty())
							&& (color.trim() == null || color.trim().isEmpty())
							&& (registrationNo.trim() == null || registrationNo
									.trim().isEmpty())
							&& (capacity.trim() == null || capacity.trim()
									.isEmpty())) {
						break;
					}
					String error = "";
					try {

						if (make.trim().isEmpty())
							error += Messages.getValue("error.required",
									new Object[] { "Make" });
						if (model.trim().isEmpty())
							error += Messages.getValue("error.required",
									new Object[] { "Model" });
						if (color.trim().isEmpty())
							error += Messages.getValue("error.required",
									new Object[] { "Color" });
						if (capacity.trim().isEmpty()
								|| Integer.parseInt(capacity) < 1
								|| Integer.parseInt(capacity) > 60)
							error += Messages.getValue("error.required",
									new Object[] { "Capacity" })
									+ " "
									+ Messages.getValue("error.between",
											new Object[] { "Capacity", "1",
													"60" });
						if (registrationNo.trim().isEmpty())
							error += Messages.getValue("error.required",
									new Object[] { "Registration Numbe" });
						if (ListOfValuesManager
								.testVehicleRegistrationNumber(registrationNo))
							error += Messages.getValue("error.alreadyexist",
									new Object[] { "Registration Number '"
											+ registrationNo + "'" });

						if (error.length() == 0) {
							vehicleMasterDTO.setUserID(userRegistrationDTO
									.getId());
							vehicleMasterDTO.setMake(make);
							vehicleMasterDTO.setModel(model);
							vehicleMasterDTO.setColor(color);
							vehicleMasterDTO.setReg_NO(registrationNo);
							vehicleMasterDTO.setCapacity(capacity);
							vehicleMasterDTO = ListOfValuesManager
									.getVehicleMaster("findByDTO",
											vehicleMasterDTO);
						}

					} catch (ConfigurationException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
						rollbackTest = true;
						error += Messages.getValue("error.db1",
								new Object[] { "Vehicle" });
						userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(Messages.getValue(
								"error.db1", new Object[] { "Vehicle" })
								+ e.getMessage());
						userMessageDTO.setToMember(Integer
								.parseInt(userRegistrationDTO.getId()));
						userMessageDTO.setMessageChannel("E");
						userMessageDTO = ListOfValuesManager
								.getInsertedMessage(userMessageDTO);
					} finally {
						rollbackTest = false;
						if (error.length() > 0)
							errorMessage.add("Row " + i + " : " + error);
						else
							successMessage.add(Messages.getValue(
									"success.uploading", new Object[] {
											"Vehicle", i }));
					}

					if (errorMessage.size() > 0) {
						errorMessage.add(Messages.getValue("error.uploading",
								new Object[] { "Vehicle" }));
						break;
					}
				}
			} catch (BiffException e1) {
				LoggerSingleton.getInstance().error(
						e1.getStackTrace()[0].getClassName() + "->"
								+ e1.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e1.getStackTrace()[0].getLineNumber()
								+ " :: " + e1.getMessage());
			} catch (IOException e1) {
				LoggerSingleton.getInstance().error(
						e1.getStackTrace()[0].getClassName() + "->"
								+ e1.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e1.getStackTrace()[0].getLineNumber()
								+ " :: " + e1.getMessage());
			} catch (ArrayIndexOutOfBoundsException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());

			}
			vehicleList();
			vehicleMasterDTO = new VehicleMasterDTO();
			userMessageDTO = new MessageBoardDTO();
		}
	}

	public void handleUserUpload(FileUploadEvent event) {
		UploadedFile item = event.getFile();
		String tomcatDirectoryPath = ApplicationUtil.catalinaDirectoryPath;
		String extension = ServerUtility.getExtension(item);
		String path = null;
		File userPath = null;
		if (tomcatDirectoryPath != null) {
			String userDirPath = ApplicationUtil.userDirectoryPath;
			path = ServerUtility.constructTargetFileName("user_"
					+ userRegistrationDTO.getId(), extension, userDirPath);
			userPath = new File(userDirPath + path);
			try {
				InputStream input = item.getInputstream();
				OutputStream output = new FileOutputStream(userPath);
				try {
					IOUtils.copy(input, output);
				} catch (Exception e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				} finally {
					input.close();
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}

			Workbook workbook;
			try {
				workbook = Workbook.getWorkbook(userPath);
				Sheet sheet = workbook.getSheet(0);
				clearScreenMessage();
				for (int i = 1;; i++) {
					String email = sheet.getCell(0, i).getContents();
					String mobileNo = sheet.getCell(1, i).getContents();
					String gender = sheet.getCell(2, i).getContents();
					/*
					 * String dob = sheet.getCell(3,i).getContents(); try { dob
					 * = new
					 * SimpleDateFormat(ApplicationUtil.datePattern1).format(new
					 * SimpleDateFormat
					 * (ApplicationUtil.datePattern6).parse(dob)); } catch
					 * (ParseException e2) { } String address =
					 * sheet.getCell(4,i).getContents(); String city =
					 * sheet.getCell(5,i).getContents(); String pin =
					 * sheet.getCell(6,i).getContents(); String firstName =
					 * sheet.getCell(7,i).getContents(); String lastName =
					 * sheet.getCell(8,i).getContents();
					 */
					String firstName = sheet.getCell(3, i).getContents();
					String lastName = sheet.getCell(4, i).getContents();
					// String travel = sheet.getCell(5,i).getContents();
					String travel = "B";
					if (userRegistrationDTO.getTravel().equalsIgnoreCase("T")) {
						travel = "T";
					}
					if (email.trim() == null || email.trim().isEmpty()) {
						break;
					}
					String error = "";
					if (Validator.isEmpty(email))
						error += Messages.getValue("error.required",
								new Object[] { "Email" });
					if (Validator.isNotEmail(email))
						error += Messages.getValue("error.format",
								new Object[] { "Email" });
					if (Validator.isEmpty(mobileNo))
						error += Messages.getValue("error.required",
								new Object[] { "Mobile Number" });
					if (Validator.isNotPhone(mobileNo))
						error += Messages.getValue("error.format",
								new Object[] { "Mobile Number" });
					if (Validator.isEmpty(gender))
						error += Messages.getValue("error.required",
								new Object[] { "Gender" });
					if (!gender.equalsIgnoreCase("f")
							&& !gender.equalsIgnoreCase("m"))
						error += "Gender must be M/F.";
					if (Validator.isEmpty(firstName))
						error += Messages.getValue("error.required",
								new Object[] { "First Name" });
					if (Validator.isEmpty(lastName))
						error += Messages.getValue("error.required",
								new Object[] { "Last Name" });
					if (Validator.isNotName(firstName))
						error += Messages.getValue("error.format",
								new Object[] { "First Name" });
					if (Validator.isNotName(lastName))
						error += Messages.getValue("error.format",
								new Object[] { "Last Name" });
					if (Validator.isEmpty(travel))
						error += Messages.getValue("error.format",
								new Object[] { "Travel" });
					if (!(travel.equalsIgnoreCase("B")
							|| travel.equalsIgnoreCase("C")
							|| travel.equalsIgnoreCase("T") || travel
								.equalsIgnoreCase("P")))
						error += "Mention role P/B/C/T.";
					String testUserExistense = ListOfValuesManager
							.testEmailAllStatus(email);
					if (testUserExistense.equalsIgnoreCase("P"))
						error += "Email ID is pending for approval.";
					else if (testUserExistense.equalsIgnoreCase("I"))
						error += "Email ID de activated. Please contact admin.";
					else if (testUserExistense.equalsIgnoreCase("A"))
						error += Messages.getValue("error.alreadyexist",
								new Object[] { "Email" });

					try {
						if (error.length() == 0) {
							forregistrationOnly = new UserRegistrationDTO();
							forregistrationOnly.setEmail_id(email);
							forregistrationOnly.setMobile_no(mobileNo);
							forregistrationOnly.setGender(gender);
							/*
							 * forregistrationOnly.setBirthdate(dob);
							 * forregistrationOnly.setAddress(address);
							 * forregistrationOnly.setCity(city);
							 * forregistrationOnly.setPincode(pin);
							 */
							forregistrationOnly.setFirst_name(firstName);
							forregistrationOnly.setLast_name(lastName);
							forregistrationOnly.setTravel(travel);
							String tempPassword = ServerUtility
									.randomString(10);
							forregistrationOnly.setPassword(tempPassword);

							forregistrationOnly.setIsVerified('1');
							forregistrationOnly.setVerificationCode("C");
							forregistrationOnly.setSignupType(3);
							forregistrationOnly.setTotalCredit(50);
							forregistrationOnly.setTotalGreenMiles(0);

							forregistrationOnly = ListOfValuesManager
									.getUserRegistration("findByDTO",
											forregistrationOnly,
											userRegistrationDTO.getId(), null);

							UserPreferencesDTO userPreferencesTemp = new UserPreferencesDTO();
							userPreferencesTemp.setUserId(Integer
									.parseInt(forregistrationOnly.getId()));

							if (forregistrationOnly.getDefaultTimeSlice() != null) {
								String[] minuteSlice = forregistrationOnly
										.getDefaultTimeSlice().split(":");
								if (minuteSlice.length > 1) {
									int minuteCount = Integer
											.parseInt(minuteSlice[0]) * 60; // convert
																			// hour
																			// to
																			// minute
									minuteCount += Integer
											.parseInt(minuteSlice[1]);
									userPreferencesTemp
											.setMaxWaitTime(minuteCount);
								}
							} else {
								userPreferencesTemp.setMaxWaitTime(10);
							}
							userPreferencesTemp = ListOfValuesManager
									.getUserPreferencesSave(
											userPreferencesTemp, null);

							LoginPageDTO dto = new LoginPageDTO();
							PageStoreDTO pageDto = ListOfValuesManager
									.getPageStoreByCode(Messages
											.getValue("page.code.change.password"));
							dto.setUserId(Integer.parseInt(forregistrationOnly
									.getId()));
							dto.setPageId(pageDto.getPageId());
							dto.setStatus("N");
							ListOfValuesManager.insertLoginPage(dto);
							dto = new LoginPageDTO();
							pageDto = ListOfValuesManager
									.getPageStoreByCode(Messages
											.getValue("page.code.preference"));
							dto.setUserId(Integer.parseInt(forregistrationOnly
									.getId()));
							dto.setPageId(pageDto.getPageId());
							dto.setStatus("N");
							ListOfValuesManager.insertLoginPage(dto);

							for (CircleDTO dto1 : allCircleList) {
								CircleMemberDTO circleMemberDTOTemp = new CircleMemberDTO();
								circleMemberDTOTemp.setCircleid(String
										.valueOf(dto1.getCircleID()));
								circleMemberDTOTemp
										.setUserid(forregistrationOnly.getId());
								circleMemberDTOTemp
										.setRequestUserId(forregistrationOnly
												.getId());
								circleMemberDTOTemp.setStatus("1");
								circleMemberDTOTemp = ListOfValuesManager
										.getJoinCircle(circleMemberDTOTemp,
												userRegistrationDTO.getId(),
												null);
								circleMemberDTOTemp = new CircleMemberDTO();
							}

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO.setEmailSubject(Messages
									.getValue("subject.register"));
							userMessageDTO
									.setMessage(Messages
											.getValue(
													"user.upload.register.intro",
													new Object[] {
															forregistrationOnly
																	.getFirst_name(),
															tempPassword,
															forregistrationOnly
																	.getEmail_id(),
															Messages.getValue("login.link") }));
							userMessageDTO.setToMember(Integer
									.parseInt(forregistrationOnly.getId()));
							userMessageDTO.setCreatedBy(Integer
									.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setSubmittedBy(Integer
									.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setMessageChannel("E");

							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);

							userMessageDTO = new MessageBoardDTO();
							userMessageDTO
									.setMessage(Messages.getValue(
											"sms.register",
											new Object[] {
													forregistrationOnly
															.getFirst_name(),
													forregistrationOnly
															.getEmail_id() }));
							userMessageDTO.setToMember(Integer
									.parseInt(forregistrationOnly.getId()));
							userMessageDTO.setCreatedBy(Integer
									.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setSubmittedBy(Integer
									.parseInt(userRegistrationDTO.getId()));
							userMessageDTO.setMessageChannel("S");

							userMessageDTO = ListOfValuesManager
									.getInsertedMessage(userMessageDTO);
						}
					} catch (ConfigurationException e) {
						LoggerSingleton.getInstance().error(
								e.getStackTrace()[0].getClassName() + "->"
										+ e.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e.getStackTrace()[0].getLineNumber()
										+ " :: " + e.getMessage());
						rollbackTest = true;
						error += Messages.getValue("error.db1",
								new Object[] { "User" });
						userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(Messages.getValue(
								"error.db1", new Object[] { "User" })
								+ e.getMessage());
						userMessageDTO.setToMember(Integer
								.parseInt(forregistrationOnly.getId()));
						userMessageDTO.setCreatedBy(Integer
								.parseInt(userRegistrationDTO.getId()));
						userMessageDTO.setSubmittedBy(Integer
								.parseInt(userRegistrationDTO.getId()));
						userMessageDTO.setMessageChannel("E");
						userMessageDTO = ListOfValuesManager
								.getInsertedMessage(userMessageDTO);
					} finally {
						rollbackTest = false;
						if (error.length() > 0)
							errorMessage.add("Row " + i + " : " + error);
						else
							successMessage.add(Messages.getValue(
									"success.uploading", new Object[] { "User",
											i })
									+ ", " + email);
					}
					if (errorMessage.size() > 0) {
						errorMessage.add(Messages.getValue("error.uploading",
								new Object[] { "User" }));
						break;
					}
				}
			} catch (BiffException e1) {
				LoggerSingleton.getInstance().error(
						e1.getStackTrace()[0].getClassName() + "->"
								+ e1.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e1.getStackTrace()[0].getLineNumber()
								+ " :: " + e1.getMessage());
				// TODO Auto-generated catch block
			} catch (IOException e1) {
				LoggerSingleton.getInstance().error(
						e1.getStackTrace()[0].getClassName() + "->"
								+ e1.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e1.getStackTrace()[0].getLineNumber()
								+ " :: " + e1.getMessage());
				// TODO Auto-generated catch block
			}
			vehicleList();
			vehicleMasterDTO = new VehicleMasterDTO();
		}
		userMessageDTO = new MessageBoardDTO();
	}

	public void cronCompletedRideMessageBoard() {
		List<RideSeekerDTO> rideSeekerTemp = new ArrayList<RideSeekerDTO>();
		List<RideManagementDTO> rideManagementTemp = new ArrayList<RideManagementDTO>();
		try {
			rideSeekerTemp = ListOfValuesManager.fetchCompletedRideSeekerList();
			rideManagementTemp = ListOfValuesManager
					.fetchCompletedRideManagementList();
			for (RideSeekerDTO dto : rideSeekerTemp) {
				if (dto.getUserID() != null && !dto.getUserID().equals("")
						&& Integer.parseInt(dto.getUserID()) > 0) {
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages.getValue(
									"ride.completed",
									new Object[] { dto.getStartDate(),
											dto.getEndDate(),
											dto.getSeekerID(),
											dto.getFromAddress1(),
											dto.getToAddress1(), }));
					userMessageDTO
							.setToMember(Integer.parseInt(dto.getUserID()));
					userMessageDTO.setEmailSubject("Ride Seeker completed");
					if (dto.getSeekerID() != null
							&& !dto.getSeekerID().equals("")
							&& Integer.parseInt(dto.getSeekerID()) > 0) {
						userMessageDTO.setRideId(Integer.parseInt(dto
								.getSeekerID()));
					}
					userMessageDTO.setMessageChannel("A");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
				}

			}
			for (RideManagementDTO dto : rideManagementTemp) {
				if (dto.getUserID() != null && !dto.getUserID().equals("")
						&& Integer.parseInt(dto.getUserID()) > 0) {
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages.getValue(
									"ride.completed",
									new Object[] { dto.getStartDate(),
											dto.getEndDate(), dto.getRideID(),
											dto.getFromAddress1(),
											dto.getToAddress1(), }));
					userMessageDTO
							.setToMember(Integer.parseInt(dto.getUserID()));
					userMessageDTO.setEmailSubject("Ride Seeker completed");
					userMessageDTO.setRideId(Integer.parseInt(dto.getRideID()));
					userMessageDTO.setMessageChannel("A");
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
				}
			}
		} finally {

		}

		rollbackTest = false;
		userMessageDTO = new MessageBoardDTO();
	}

	public void cronMessage() {
		List<Integer> messageId = new ArrayList<Integer>();
		try {
			List<MessageBoardDTO> messagedto = ListOfValuesManager
					.getAllEmailSendingMessage();
			for (MessageBoardDTO dto : messagedto) {
				EmailDTO emaildto = new EmailDTO();
				emaildto.setReceiverEmailId(dto.getToMemberEmail());
				emaildto.setSenderEmailId(dto.getCreatedByEmail());
				emaildto.setSubject(dto.getEmailSubject());
				if (emaildto.getSubject() == null)
					emaildto.setSubject(Messages.getValue("subject.default"));
				// emaildto.setEmailBody(dto.getMessage());
				emaildto.setEmailTemplateBody(Messages.getValue(
						"email.template2", new Object[] {
								emaildto.getSubject(), dto.getMessage() }));

				if (dto.getAttachements() != null
						&& dto.getAttachements().size() > 0) {
					emaildto.setAttachements(dto.getAttachements());
				}

				MailService.sendMail(emaildto);
				messageId.add(dto.getMessageId());
			}
		} finally {

		}

		int[] msgId = new int[messageId.size()];
		int i = 0;
		for (Iterator<Integer> it = messageId.iterator(); it.hasNext(); msgId[i++] = it
				.next())
			;

		if (msgId.length > 0) {
			ListOfValuesManager.makeEmailStatusSent(msgId);
			rollbackTest = false;
		}

		try {
			List<MessageBoardDTO> messagedto = ListOfValuesManager
					.getAllSmsSendingMessage();

			List<Integer> userId = new ArrayList<Integer>();
			for (MessageBoardDTO dto : messagedto) {
				userId.add(dto.getToMember());
			}
			List<UserRegistrationDTO> userDto = ListOfValuesManager
					.getAllUserById(userId);

			Map<String, String> userMap = new HashMap<String, String>();
			for (UserRegistrationDTO dtoTemp : userDto) {
				userMap.put(dtoTemp.getId(), dtoTemp.getMobile_no());
			}
			for (MessageBoardDTO dto : messagedto) {
				try {
					SmsService.sendSms(userMap.get("" + dto.getToMember()),
							dto.getMessage());
				} catch (Exception e) {
					LoggerSingleton.getInstance().error(
							e.getStackTrace()[0].getClassName() + "->"
									+ e.getStackTrace()[0].getMethodName()
									+ "() : "
									+ e.getStackTrace()[0].getLineNumber()
									+ " :: " + e.getMessage());
				}
			}
		} finally {

		}
		userMessageDTO = new MessageBoardDTO();
	}

	public void clearScreenMessage() {
		successMessage.clear();
		errorMessage.clear();
	}

	public void listOfRideForACircle() {
		matchedTripByConditionList = ListOfValuesManager
				.getRideSekerForCircle(String.valueOf(circleDTO.getCircleID()));

		matchedTripDataModel = new MatchedTripDataModel(
				matchedTripByConditionList);
	}

	public List<SelectItem> getVehicleListForDriver() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		if (vehicleMasterDTOList != null) {
			for (int i = 0; i < vehicleMasterDTOList.size(); i++) {
				if (vehicleMasterDTOList.get(i).getDrivername()
						.equalsIgnoreCase("NoDriver")) {
					list.add(new SelectItem(vehicleMasterDTOList.get(i)
							.getVehicleID(), vehicleMasterDTOList.get(i)
							.getModel()
							+ "-"
							+ vehicleMasterDTOList.get(i).getReg_NO()));
				}
			}
		}
		return list;
	}

	public List<SelectItem> getAllUserForACircle() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<UserRegistrationDTO> userList = new ArrayList<UserRegistrationDTO>();
		userList = ListOfValuesManager.getAllUsaerOfACircle(userRegistrationDTO
				.getId());

		for (int j = 0; j < vehicleMasterDTOList.size(); j++) {
			for (int i = 0; i < userList.size(); i++) {
				if (vehicleMasterDTOList.get(j).getDriverid()
						.equalsIgnoreCase(userList.get(i).getId())) {
					userList.remove(i);
				}
			}
		}
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				list.add(new SelectItem(userList.get(i).getFirst_name() + "-"
						+ userList.get(i).getAddress() + ","
						+ userList.get(i).getId(), userList.get(i)
						.getFirst_name()
						+ "-"
						+ userList.get(i).getAddress()
						+ "," + userList.get(i).getId()));
			}
		}
		return list;
	}

	public String addDriverForVehicle() {
		String link = null;
		String delink = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestMap = context.getExternalContext()
				.getRequestParameterMap(); // In java class, you can get back
											// the parameter value with
											// component (submit-command buton)
											// like this :
		link = (String) requestMap.get("link");
		delink = (String) requestMap.get("delink");
		if (link != null) {
			vehicleMasterDTO.setAvilable(link);
			String[] parts = forregistrationOnly.getFirst_name().split(",");
			String[] names = parts[0].split("-");
			String driverName = names[0];
			String part2 = "0";
			if (parts.length > 0)
				part2 = parts[parts.length - 1];
			vehicleMasterDTO.setUserID(part2);
			vehicleMasterDTO.setDrivername(driverName);
		}
		if (delink != null) {
			vehicleMasterDTO.setCapacity(null);
			vehicleMasterDTO.setAvilable(delink);
		}
		vehicleMasterDTO = ListOfValuesManager
				.getUpdateVehicle(vehicleMasterDTO);

		rollbackTest = false;
		vehicleList();
		forregistrationOnly.setFirst_name(null);
		vehicleMasterDTO = new VehicleMasterDTO();
		return "link";
	}

	public void smsReplyOperation(String smsSid, String fromNumber,
			String toNumber, String body, String exotelDate) {
		fromNumber = fromNumber.replaceFirst("^[0|+]*", "");
		body = body.trim();
		SmsReplyDTO dto = new SmsReplyDTO();
		dto.setBody(body);
		dto.setSmsSid(smsSid);
		dto.setFromNumber(fromNumber);
		dto.setToNumber(toNumber);
		dto.setExotelDate(exotelDate);
		dto = ListOfValuesManager.addSms(dto);
		String[] bodyPart = body.split(" ");
		if (bodyPart.length >= 2) {
			String msg = bodyPart[0];
			int rideId = 0;
			try {
				rideId = Integer.parseInt(bodyPart[1]);
				if (msg.equalsIgnoreCase("CAN")) {
					RideSeekerDTO seekerDto = new RideSeekerDTO();
					try {
						seekerDto = ListOfValuesManager
								.getRideSeekerData(rideId);
						List<Integer> a = new ArrayList<Integer>();
						a.add(Integer.parseInt(seekerDto.getUserID()));
						UserRegistrationDTO userTemp = ListOfValuesManager
								.getAllUserById(a).get(0);
						if (userTemp != null && userTemp.getMobile_no() != null
								&& userTemp.getMobile_no().equals(fromNumber)
								&& userTemp.getStatus().equalsIgnoreCase("A")) {
							seekerDto.setSeekerID(rideId + "");
							seekerDto.setStatus("I");
							ListOfValuesManager.getCancleRideSeeker(seekerDto,
									null);

							ListOfValuesManager
									.changePoolRequestStatusForRideGiver(null,
											Integer.parseInt(seekerDto
													.getSeekerID()));
							seekerDto = ListOfValuesManager
									.getRideSeekerData(Integer
											.parseInt(seekerDto.getSeekerID()));

							if (seekerDto.getIsResult().equalsIgnoreCase("N")) {
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setEmailSubject(Messages.getValue(
												"subject.cancel",
												new Object[] { seekerDto
														.getStartdateValue() }));
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"rideSeeker.unmatched.cancel",
														new Object[] {
																userTemp.getFirst_name(),
																seekerDto
																		.getSeekerID(),
																seekerDto
																		.getFromAddress1(),
																seekerDto
																		.getToAddress1(),
																seekerDto
																		.getStartdateValue() }));
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO.setRideId(Integer
										.parseInt(seekerDto.getSeekerID()));
								userMessageDTO.setMessageChannel("E");
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);

								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"sms.rideSeeker.unmatched.cancel",
														new Object[] {
																userTemp.getFirst_name(),
																seekerDto
																		.getSeekerID(),
																(seekerDto
																		.getFromAddress1()
																		.length() > 25) ? seekerDto
																		.getFromAddress1()
																		.substring(
																				0,
																				25)
																		: seekerDto
																				.getFromAddress1(),
																(seekerDto
																		.getToAddress1()
																		.length() > 25) ? seekerDto
																		.getToAddress1()
																		.substring(
																				0,
																				25)
																		: seekerDto
																				.getToAddress1(),
																seekerDto
																		.getStartdateValue() }));
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO.setMessageChannel("S");
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
							} else {
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages.getValue(
												"sms.cancel",
												new Object[] {
														userTemp.getFirst_name(),
														(seekerDto
																.getFromAddress1()
																.length() > 25) ? seekerDto
																.getFromAddress1()
																.substring(0,
																		25)
																: seekerDto
																		.getFromAddress1(),
														(seekerDto
																.getToAddress1()
																.length() > 25) ? seekerDto
																.getToAddress1()
																.substring(0,
																		25)
																: seekerDto
																		.getToAddress1(),
														ListOfValuesManager
																.getcurrentDate(ApplicationUtil.datePattern4),
														userTemp.getMobile_no(),
														seekerDto
																.getRideMatchRideId() }));
								userMessageDTO.setToMember(Integer
										.parseInt(userTemp.getId()));
								userMessageDTO.setRideId(Integer
										.parseInt(seekerDto.getSeekerID()));
								userMessageDTO.setMessageChannel("S");
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);

								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setEmailSubject(Messages.getValue(
												"subject.cancel",
												new Object[] { seekerDto
														.getStartdateValue() }));
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"rideSeeker.matched.cancel",
														new Object[] {
																userTemp.getFirst_name(),
																seekerDto
																		.getRideMatchRideId(),
																seekerDto
																		.getFromAddress1(),
																seekerDto
																		.getToAddress1(),
																seekerDto
																		.getStartdateValue() }));
								userMessageDTO.setToMember(Integer
										.parseInt(userTemp.getId()));
								userMessageDTO.setRideId(Integer
										.parseInt(seekerDto.getSeekerID()));
								userMessageDTO.setMessageChannel("E");
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);

								/* Now message should go to driver. */
								UserRegistrationDTO userDto = new UserRegistrationDTO();
								userDto = ListOfValuesManager
										.findDriverDtoByRideId(
												seekerDto.getRideMatchRideId(),
												null);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"sms.cancel.driver",
														new Object[] {
																userDto.getFirst_name(),
																userTemp.getFirst_name(),
																seekerDto
																		.getRideMatchRideId(),
																(seekerDto
																		.getFromAddress1()
																		.length() > 25) ? seekerDto
																		.getFromAddress1()
																		.substring(
																				0,
																				25)
																		: seekerDto
																				.getFromAddress1(),
																(seekerDto
																		.getToAddress1()
																		.length() > 25) ? seekerDto
																		.getToAddress1()
																		.substring(
																				0,
																				25)
																		: seekerDto
																				.getToAddress1(),
																ListOfValuesManager
																		.getcurrentDate(ApplicationUtil.datePattern4),
																userTemp.getMobile_no() }));
								userMessageDTO.setToMember(Integer
										.parseInt(userDto.getId()));
								userMessageDTO.setRideId(Integer
										.parseInt(seekerDto.getSeekerID()));
								userMessageDTO.setMessageChannel("S");
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);

								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setEmailSubject(Messages.getValue(
												"subject.cancel",
												new Object[] { seekerDto
														.getStartdateValue() }));
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"driver.match.cancel",
														new Object[] {
																userDto.getFirst_name(),
																userTemp.getFirst_name(),
																seekerDto
																		.getRideMatchRideId(),
																seekerDto
																		.getFromAddress1(),
																seekerDto
																		.getToAddress1(),
																seekerDto
																		.getStartdateValue(),
																userDto.getMobile_no(),
																Messages.getValue("customer.support") }));
								userMessageDTO.setToMember(Integer
										.parseInt(userDto.getId()));
								userMessageDTO.setRideId(Integer
										.parseInt(seekerDto.getSeekerID()));
								userMessageDTO.setMessageChannel("E");
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
							}

							if (seekerDto.getRecurring().equalsIgnoreCase("Y")) {
								String[] rides = seekerDto.getSubSeekers()
										.split(",");
								if (rides.length > 0) {
									seekerDto.setStatus("I");
									seekerDto = ListOfValuesManager
											.cancelSubSeekers(null, seekerDto);
									rides = seekerDto.getSubSeekers()
											.split(",");
									for (int i = 0; i < rides.length; i++) {
										ListOfValuesManager
												.changePoolRequestStatusForRideGiver(
														null,
														Integer.parseInt(rides[i]));
										seekerDto = ListOfValuesManager
												.getRideSeekerData(Integer
														.parseInt(rides[i]));

										if (seekerDto.getIsResult()
												.equalsIgnoreCase("N")) {
											userMessageDTO = new MessageBoardDTO();
											userMessageDTO
													.setEmailSubject(Messages
															.getValue(
																	"subject.cancel",
																	new Object[] { seekerDto
																			.getStartdateValue() }));
											userMessageDTO
													.setMessage(Messages
															.getValue(
																	"rideSeeker.unmatched.cancel",
																	new Object[] {
																			userRegistrationDTO
																					.getFirst_name(),
																			seekerDto
																					.getSeekerID(),
																			seekerDto
																					.getFromAddress1(),
																			seekerDto
																					.getToAddress1(),
																			seekerDto
																					.getStartdateValue() }));
											userMessageDTO.setToMember(Integer
													.parseInt(seekerDto
															.getUserID()));
											userMessageDTO.setRideId(Integer
													.parseInt(seekerDto
															.getSeekerID()));
											userMessageDTO
													.setMessageChannel("E");
											userMessageDTO = ListOfValuesManager
													.getInsertedMessage(userMessageDTO);

											userMessageDTO = new MessageBoardDTO();
											userMessageDTO
													.setMessage(Messages
															.getValue(
																	"sms.rideSeeker.unmatched.cancel",
																	new Object[] {
																			userRegistrationDTO
																					.getFirst_name(),
																			seekerDto
																					.getSeekerID(),
																			(seekerDto
																					.getFromAddress1()
																					.length() > 25) ? seekerDto
																					.getFromAddress1()
																					.substring(
																							0,
																							25)
																					: seekerDto
																							.getFromAddress1(),
																			(seekerDto
																					.getToAddress1()
																					.length() > 25) ? seekerDto
																					.getToAddress1()
																					.substring(
																							0,
																							25)
																					: seekerDto
																							.getToAddress1(),
																			seekerDto
																					.getStartdateValue() }));
											userMessageDTO.setToMember(Integer
													.parseInt(seekerDto
															.getUserID()));
											userMessageDTO
													.setMessageChannel("S");
											userMessageDTO = ListOfValuesManager
													.getInsertedMessage(userMessageDTO);
										} else {
											userMessageDTO = new MessageBoardDTO();
											userMessageDTO
													.setMessage(Messages
															.getValue(
																	"sms.cancel",
																	new Object[] {
																			userRegistrationDTO
																					.getFirst_name(),
																			(seekerDto
																					.getFromAddress1()
																					.length() > 25) ? seekerDto
																					.getFromAddress1()
																					.substring(
																							0,
																							25)
																					: seekerDto
																							.getFromAddress1(),
																			(seekerDto
																					.getToAddress1()
																					.length() > 25) ? seekerDto
																					.getToAddress1()
																					.substring(
																							0,
																							25)
																					: seekerDto
																							.getToAddress1(),
																			ListOfValuesManager
																					.getcurrentDate(ApplicationUtil.datePattern4),
																			userRegistrationDTO
																					.getMobile_no(),
																			seekerDto
																					.getRideMatchRideId() }));
											userMessageDTO
													.setToMember(Integer
															.parseInt(userRegistrationDTO
																	.getId()));
											userMessageDTO.setRideId(Integer
													.parseInt(seekerDto
															.getSeekerID()));
											userMessageDTO
													.setMessageChannel("S");
											userMessageDTO = ListOfValuesManager
													.getInsertedMessage(userMessageDTO);

											userMessageDTO = new MessageBoardDTO();
											userMessageDTO
													.setEmailSubject(Messages
															.getValue(
																	"subject.cancel",
																	new Object[] { seekerDto
																			.getStartdateValue() }));
											userMessageDTO
													.setMessage(Messages
															.getValue(
																	"rideSeeker.matched.cancel",
																	new Object[] {
																			userRegistrationDTO
																					.getFirst_name(),
																			seekerDto
																					.getRideMatchRideId(),
																			seekerDto
																					.getFromAddress1(),
																			seekerDto
																					.getToAddress1(),
																			seekerDto
																					.getStartdateValue() }));
											userMessageDTO
													.setToMember(Integer
															.parseInt(userRegistrationDTO
																	.getId()));
											userMessageDTO.setRideId(Integer
													.parseInt(seekerDto
															.getSeekerID()));
											userMessageDTO
													.setMessageChannel("E");
											userMessageDTO = ListOfValuesManager
													.getInsertedMessage(userMessageDTO);

											/* Now message should go to driver. */
											UserRegistrationDTO userDto = new UserRegistrationDTO();
											userDto = ListOfValuesManager
													.findDriverDtoByRideId(
															seekerDto
																	.getRideMatchRideId(),
															null);
											userMessageDTO = new MessageBoardDTO();
											userMessageDTO
													.setMessage(Messages
															.getValue(
																	"sms.cancel.driver",
																	new Object[] {
																			userDto.getFirst_name(),
																			userRegistrationDTO
																					.getFirst_name(),
																			seekerDto
																					.getRideMatchRideId(),
																			(seekerDto
																					.getFromAddress1()
																					.length() > 25) ? seekerDto
																					.getFromAddress1()
																					.substring(
																							0,
																							25)
																					: seekerDto
																							.getFromAddress1(),
																			(seekerDto
																					.getToAddress1()
																					.length() > 25) ? seekerDto
																					.getToAddress1()
																					.substring(
																							0,
																							25)
																					: seekerDto
																							.getToAddress1(),
																			ListOfValuesManager
																					.getcurrentDate(ApplicationUtil.datePattern4),
																			userRegistrationDTO
																					.getMobile_no() }));
											userMessageDTO.setToMember(Integer
													.parseInt(userDto.getId()));
											userMessageDTO.setRideId(Integer
													.parseInt(seekerDto
															.getSeekerID()));
											userMessageDTO
													.setMessageChannel("S");
											userMessageDTO = ListOfValuesManager
													.getInsertedMessage(userMessageDTO);

											userMessageDTO = new MessageBoardDTO();
											userMessageDTO
													.setEmailSubject(Messages
															.getValue(
																	"subject.cancel",
																	new Object[] { seekerDto
																			.getStartdateValue() }));
											userMessageDTO
													.setMessage(Messages
															.getValue(
																	"driver.match.cancel",
																	new Object[] {
																			userDto.getFirst_name(),
																			userRegistrationDTO
																					.getFirst_name(),
																			seekerDto
																					.getRideMatchRideId(),
																			seekerDto
																					.getFromAddress1(),
																			seekerDto
																					.getToAddress1(),
																			seekerDto
																					.getStartdateValue(),
																			userDto.getMobile_no(),
																			Messages.getValue("customer.support") }));
											userMessageDTO.setToMember(Integer
													.parseInt(userDto.getId()));
											userMessageDTO.setRideId(Integer
													.parseInt(seekerDto
															.getSeekerID()));
											userMessageDTO
													.setMessageChannel("E");
											userMessageDTO = ListOfValuesManager
													.getInsertedMessage(userMessageDTO);
										}
									}
								}
							}
						}
					} catch (ConfigurationException e1) {
						LoggerSingleton.getInstance().error(
								e1.getStackTrace()[0].getClassName() + "->"
										+ e1.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e1.getStackTrace()[0].getLineNumber()
										+ " :: " + e1.getMessage());
					}
				} else if (msg.equalsIgnoreCase("APR")) {
					RideSeekerDTO seekerDto = new RideSeekerDTO();
					try {
						seekerDto = ListOfValuesManager
								.getRideSeekerData(rideId);
						if (seekerDto.getStatus() != null
								&& seekerDto.getStatus().equalsIgnoreCase("T")) {
							ApproverDTO approverDtoTemp = ListOfValuesManager
									.findApproverById(seekerDto.getApproverID());
							UserRegistrationDTO userTemp = ListOfValuesManager
									.findUserByEmail(approverDtoTemp
											.getHoponId2());
							if (userTemp != null
									&& userTemp.getMobile_no() != null
									&& userTemp.getMobile_no().equals(
											fromNumber)
									&& userTemp.getStatus().equalsIgnoreCase(
											"A")) {
								seekerDto.setSeekerID(rideId + "");
								seekerDto.setStatus("O");
								ListOfValuesManager.getCancleRideSeeker(
										seekerDto, null);

								if (approverDtoTemp.getHoponId2() != null
										&& approverDtoTemp.getHoponId2()
												.length() > 0) {
									FrequencyDTO frequencyDto = ListOfValuesManager
											.fetchFrequencyListForRideSeeker(
													seekerDto.getSeekerID())
											.get(0);
									String approveLink = Messages
											.getValue(
													"ride.approve",
													new Object[] {
															seekerDto
																	.getSeekerID(),
															approverDtoTemp
																	.getVerificationCode2(),
															approverDtoTemp
																	.getId(),
															approverDtoTemp
																	.getHoponId2() });
									String rejectLink = Messages
											.getValue(
													"ride.reject",
													new Object[] {
															seekerDto
																	.getSeekerID(),
															approverDtoTemp
																	.getVerificationCode2(),
															approverDtoTemp
																	.getId(),
															approverDtoTemp
																	.getHoponId2() });
									String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "
											+ approverDtoTemp.getbCode()
											+ "<br>Name: "
											+ userTemp.getFirst_name()
											+ "<br>Request ID: "
											+ seekerDto.getSeekerID()
											+ "<br>From: "
											+ seekerDto.getFromAddress1()
											+ "<br>To: "
											+ seekerDto.getToAddress1()
											+ "<br>Date Time: "
											+ seekerDto.getStartdateValue()
											+ "<br>Frequency: "
											+ frequencyDto.getFrequency()
													.toString()
											+ "<br>"
											+ approveLink
											+ " &nbsp;&nbsp;"
											+ rejectLink;
									if (ListOfValuesManager
											.testEmail(approverDtoTemp
													.getHoponId2())) {
										UserRegistrationDTO dtoTemp = null;
										dtoTemp = ListOfValuesManager
												.findUserByEmail(approverDtoTemp
														.getHoponId2());
										userMessageDTO = new MessageBoardDTO();
										userMessageDTO
												.setMessage(messageContent);
										userMessageDTO
												.setEmailSubject(Messages
														.getValue("subject.ride.approval"));
										userMessageDTO.setMessageChannel("E");
										userMessageDTO.setToMember(Integer
												.parseInt(dtoTemp.getId()));
										userMessageDTO = ListOfValuesManager
												.getInsertedMessage(userMessageDTO);
										userMessageDTO = new MessageBoardDTO();
										userMessageDTO
												.setMessage(Messages
														.getValue(
																"ride.option.msgBoard",
																new Object[] {
																		approverDtoTemp
																				.getbCode(),
																		seekerDto
																				.getSeekerID(),
																		dtoTemp.getFirst_name(),
																		seekerDto
																				.getFromAddress1(),
																		seekerDto
																				.getToAddress1(),
																		seekerDto
																				.getStartdateValue() }));
										userMessageDTO.setMessageChannel("M");
										userMessageDTO.setToMember(Integer
												.parseInt(dtoTemp.getId()));
										userMessageDTO = ListOfValuesManager
												.getInsertedMessage(userMessageDTO);
										userMessageDTO = new MessageBoardDTO();
										userMessageDTO
												.setMessage(Messages
														.getValue(
																"ride.option.sms",
																new Object[] {
																		approverDtoTemp
																				.getbCode(),
																		seekerDto
																				.getSeekerID(),
																		dtoTemp.getFirst_name(),
																		seekerDto
																				.getFromAddress1()
																				.substring(
																						0,
																						(seekerDto
																								.getFromAddress1()
																								.length() >= 20) ? 20
																								: seekerDto
																										.getFromAddress1()
																										.length()),
																		seekerDto
																				.getToAddress1()
																				.substring(
																						0,
																						(seekerDto
																								.getToAddress1()
																								.length() >= 20) ? 20
																								: seekerDto
																										.getToAddress1()
																										.length()),
																		seekerDto
																				.getStartdateValue() }));
										userMessageDTO.setMessageChannel("S");
										userMessageDTO.setToMember(Integer
												.parseInt(dtoTemp.getId()));
										userMessageDTO = ListOfValuesManager
												.getInsertedMessage(userMessageDTO);
									}
								}
								if (seekerDto.getRecurring().equalsIgnoreCase(
										"Y")) {
									String[] rides = seekerDto.getSubSeekers()
											.split(",");
									if (rides.length > 0) {
										seekerDto.setStatus("O");
										seekerDto = ListOfValuesManager
												.cancelSubSeekers(null,
														seekerDto);
									}
								}
							}
						} else if (seekerDto.getStatus() != null
								&& seekerDto.getStatus().equalsIgnoreCase("O")) {
							ApproverDTO approverDto = ListOfValuesManager
									.findApproverById(seekerDto.getApproverID());
							UserRegistrationDTO userTemp = ListOfValuesManager
									.findUserByEmail(approverDto.getHoponId());
							if (userTemp != null
									&& userTemp.getMobile_no() != null
									&& userTemp.getMobile_no().equals(
											fromNumber)
									&& userTemp.getStatus().equalsIgnoreCase(
											"A")) {
								seekerDto.setSeekerID(rideId + "");
								seekerDto.setStatus("A");
								ListOfValuesManager.getCancleRideSeeker(
										seekerDto, null);
								frequencyDTO = ListOfValuesManager
										.fetchFrequencyListForRideSeeker(
												seekerDto.getSeekerID()).get(0);
								String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "
										+ approverDto.getbCode()
										+ "<br>Name: "
										+ seekerDto.getUserName()
										+ "<br>Request ID: "
										+ seekerDto.getSeekerID()
										+ "<br>From: "
										+ seekerDto.getFromAddress1()
										+ "<br>To: "
										+ seekerDto.getToAddress1()
										+ "<br>Date Time: "
										+ seekerDto.getStartdateValue()
										+ "<br>Frequency: "
										+ frequencyDTO.getFrequency()
												.toString();

								userMessageDTO.setMessage(messageContent);
								userMessageDTO
										.setEmailSubject("Ride request approved");
								userMessageDTO.setMessageChannel("E");
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"ride.option.approved",
														new Object[] {
																approverDto
																		.getbCode(),
																seekerDto
																		.getSeekerID(),
																seekerDto
																		.getUserName(),
																seekerDto
																		.getFromAddress1(),
																seekerDto
																		.getToAddress1(),
																seekerDto
																		.getStartdateValue() }));
								userMessageDTO.setMessageChannel("M");
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"ride.option.approved",
														new Object[] {
																approverDto
																		.getbCode(),
																seekerDto
																		.getSeekerID(),
																seekerDto
																		.getUserName(),
																seekerDto
																		.getFromAddress1(),
																seekerDto
																		.getToAddress1(),
																seekerDto
																		.getStartdateValue() }));
								userMessageDTO.setMessageChannel("S");
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);

								if (seekerDto.getRecurring().equalsIgnoreCase(
										"Y")) {
									String[] rides = seekerDto.getSubSeekers()
											.split(",");
									if (rides.length > 0) {
										seekerDto.setStatus("A");
										seekerDto = ListOfValuesManager
												.cancelSubSeekers(null,
														seekerDto);
									}
								}
							}
						}
					} catch (ConfigurationException e1) {
						LoggerSingleton.getInstance().error(
								e1.getStackTrace()[0].getClassName() + "->"
										+ e1.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e1.getStackTrace()[0].getLineNumber()
										+ " :: " + e1.getMessage());
					}
				} else if (msg.equalsIgnoreCase("REJ")) {
					RideSeekerDTO seekerDto = new RideSeekerDTO();
					try {
						seekerDto = ListOfValuesManager
								.getRideSeekerData(rideId);
						if (seekerDto.getStatus().equalsIgnoreCase("T")) {
							ApproverDTO approverDto = ListOfValuesManager
									.findApproverById(seekerDto.getApproverID());
							UserRegistrationDTO userTemp = ListOfValuesManager
									.findUserByEmail(approverDto.getHoponId2());
							if (userTemp != null
									&& userTemp.getMobile_no() != null
									&& userTemp.getMobile_no().equals(
											fromNumber)
									&& userTemp.getStatus().equalsIgnoreCase(
											"A")) {
								seekerDto.setSeekerID(rideId + "");
								seekerDto.setStatus("I");
								ListOfValuesManager.getCancleRideSeeker(
										seekerDto, null);

								String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "
										+ approverDto.getbCode()
										+ "<br>Name: "
										+ seekerDto.getUserName()
										+ "<br>Request ID: "
										+ seekerDto.getSeekerID()
										+ "<br>From: "
										+ seekerDto.getFromAddress1()
										+ "<br>To: "
										+ seekerDto.getToAddress1()
										+ "<br>Date Time: "
										+ seekerDto.getStartdateValue();
								MessageBoardDTO userMessageDTO = new MessageBoardDTO();
								userMessageDTO.setMessage(messageContent);
								userMessageDTO
										.setEmailSubject("Ride request rejected");
								userMessageDTO.setMessageChannel("E");
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"ride.option.rejected",
														new Object[] {
																approverDto
																		.getbCode(),
																seekerDto
																		.getSeekerID(),
																seekerDto
																		.getUserName(),
																seekerDto
																		.getFromAddress1(),
																seekerDto
																		.getToAddress1(),
																seekerDto
																		.getStartdateValue() }));
								userMessageDTO.setMessageChannel("M");
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);
								userMessageDTO = new MessageBoardDTO();
								userMessageDTO
										.setMessage(Messages
												.getValue(
														"ride.option.rejected",
														new Object[] {
																approverDto
																		.getbCode(),
																seekerDto
																		.getSeekerID(),
																seekerDto
																		.getUserName(),
																seekerDto
																		.getFromAddress1(),
																seekerDto
																		.getToAddress1(),
																seekerDto
																		.getStartdateValue() }));
								userMessageDTO.setMessageChannel("S");
								userMessageDTO.setToMember(Integer
										.parseInt(seekerDto.getUserID()));
								userMessageDTO = ListOfValuesManager
										.getInsertedMessage(userMessageDTO);

								if (seekerDto.getRecurring().equalsIgnoreCase(
										"Y")) {
									String[] rides = seekerDto.getSubSeekers()
											.split(",");
									if (rides.length > 0) {
										seekerDto.setStatus("I");
										seekerDto = ListOfValuesManager
												.cancelSubSeekers(null,
														seekerDto);
									}
								}
							}
						} else if (seekerDto.getStatus().equalsIgnoreCase("O")) {
							ApproverDTO approverDto = ListOfValuesManager
									.findApproverById(seekerDto.getApproverID());
							UserRegistrationDTO userTemp = ListOfValuesManager
									.findUserByEmail(approverDto.getHoponId());
							if (userTemp != null
									&& userTemp.getMobile_no() != null
									&& userTemp.getMobile_no().equals(
											fromNumber)
									&& userTemp.getStatus().equalsIgnoreCase(
											"A")) {
								seekerDto.setSeekerID(rideId + "");
								seekerDto.setStatus("I");
								ListOfValuesManager.getCancleRideSeeker(
										seekerDto, null);
								if (seekerDto.getRecurring().equalsIgnoreCase(
										"Y")) {
									String[] rides = seekerDto.getSubSeekers()
											.split(",");
									if (rides.length > 0) {
										seekerDto.setStatus("I");
										seekerDto = ListOfValuesManager
												.cancelSubSeekers(null,
														seekerDto);
									}
								}
							}
						}
					} catch (ConfigurationException e1) {
						LoggerSingleton.getInstance().error(
								e1.getStackTrace()[0].getClassName() + "->"
										+ e1.getStackTrace()[0].getMethodName()
										+ "() : "
										+ e1.getStackTrace()[0].getLineNumber()
										+ " :: " + e1.getMessage());
					}
				}

			} catch (NumberFormatException e) {

			}
		}
	}

	public void updatePoolForIsResult() {
		ListOfValuesManager.getUpdatePoolForIsResult();
		rollbackTest = false;

	}

	public void populateApprover() {
		approverdtoList.clear();
		/*
		 * HttpSession currentSession = ServerUtility.getSession();
		 * if(currentSession.getAttribute("approverdtoList") != null &&
		 * ((List<ApproverDTO>)
		 * currentSession.getAttribute("approverdtoList")).size() == 0) {
		 * approverdtoList.addAll((List<ApproverDTO>)
		 * currentSession.getAttribute("approverdtoList")); } else { try {
		 */
		approverdtoList.addAll(ListOfValuesManager.findApproverForUser(Integer
				.parseInt(userRegistrationDTO.getId())));
		/*
		 * currentSession.setAttribute("approverdtoList", approverdtoList); }
		 * catch (NumberFormatException e) {
		 * currentSession.removeAttribute("approverdtoList"); } }
		 */

	}

	public void addApprover() {
		clearScreenMessage();
		populateApprover();
		int circleId = 0;
		for (CircleDTO dto : allCircleList) {
			if (dto.getCircleType().equals("C"))
				circleId = dto.getCircleID();
		}
		if (circleId > 0) {
			if ((approverdto.getHoponId() == null || approverdto.getHoponId()
					.trim().length() == 0)) {
				errorMessage.add("Please enter first approver hopon iD.");
			}
			if (approverdto.getHoponId() != null
					&& approverdto.getHoponId2() != null
					&& approverdto.getHoponId().equalsIgnoreCase(
							approverdto.getHoponId2())) {
				errorMessage
						.add("Second approver HopOn ID should be different from First HopOn ID.");
			}
			if (errorMessage.size() == 0) {
				approverdto.setCircleId(circleId);
				approverdto.setCreatedBy(userRegistrationDTO.getId());
				approverdto.setVerificationCode(ServerUtility.randomString(15));
				if (approverdto.getHoponId2() != null
						&& approverdto.getHoponId2().length() > 0) {
					approverdto.setVerificationCode2(ServerUtility
							.randomString(15));
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

	public void handleVehiclePopUp(SelectEvent evt) {
		MatchedTripDTO dto = (MatchedTripDTO) evt.getObject();
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern9);
		clearRidePreVehicleList();
		try {
			String date = new SimpleDateFormat(ApplicationUtil.datePattern3)
					.format(dateFormat.parse(dto.getStartDate()));

			ridePreVehicleList = ListOfValuesManager.fetchRidePreVehicleList(
					date, Integer.parseInt(userRegistrationDTO.getId()));

		} catch (ParseException e) {
		} catch (NumberFormatException e) {
		}
		try {
			ridePreVehicleDate = new SimpleDateFormat(
					ApplicationUtil.datePattern7).format(dateFormat.parse(dto
					.getStartDate()));
		} catch (ParseException e) {
		}
	}

	public void clearRidePreVehicleList() {
		ridePreVehicleList.clear();
		ridePreVehicleDate = "";
	}

	public void populateVehicleByDate() {
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern12);
		// clearRidePreVehicleList();
		try {
			Date date1 = dateFormat.parse(ridePreVehicleDate);
			String date2 = new SimpleDateFormat(ApplicationUtil.datePattern3)
					.format(date1);
			ridePreVehicleList = ListOfValuesManager.fetchRidePreVehicleList(
					date2, Integer.parseInt(userRegistrationDTO.getId()));
		} catch (ParseException e) {
		}
	}

	public void inBoundMessageToApprover() {
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		dtos.addAll(ListOfValuesManager.fetchRideSeekerUnApproved());
		for (RideSeekerDTO dto : dtos) {
			ApproverDTO approverDtoTemp = ListOfValuesManager
					.findApproverById(dto.getApproverID());
			if (dto.getStatus().equalsIgnoreCase("O")
					&& approverDtoTemp.getHoponId() != null
					&& approverDtoTemp.getHoponId().length() > 0) {
				String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "
						+ approverDtoTemp.getbCode()
						+ "<br>Name: "
						+ dto.getUserName()
						+ "<br>Request ID: "
						+ dto.getSeekerID()
						+ "<br>From: "
						+ dto.getFromAddress1()
						+ "<br>To: "
						+ dto.getToAddress1()
						+ "<br>Date Time: "
						+ dto.getStartdateValue();

				String approveLink = Messages.getValue(
						"ride.approve",
						new Object[] {
								dto.getSeekerID(),
								URLEncoder.encode(approverDtoTemp
										.getVerificationCode()),
								approverDtoTemp.getId(),
								approverDtoTemp.getHoponId() });
				String rejectLink = Messages.getValue(
						"ride.reject",
						new Object[] {
								dto.getSeekerID(),
								URLEncoder.encode(approverDtoTemp
										.getVerificationCode()),
								approverDtoTemp.getId(),
								approverDtoTemp.getHoponId() });

				messageContent += "<br> " + approveLink + " &nbsp;&nbsp;"
						+ rejectLink;
				if (ListOfValuesManager.testEmail(approverDtoTemp.getHoponId())) {
					UserRegistrationDTO userTemp = null;
					userTemp = ListOfValuesManager
							.findUserByEmail(approverDtoTemp.getHoponId());
					MessageBoardDTO userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(messageContent);
					userMessageDTO.setEmailSubject("Ride Request for Approval");
					userMessageDTO.setMessageChannel("E");
					userMessageDTO.setToMember(Integer.parseInt(userTemp
							.getId()));
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages.getValue(
							"ride.option.msgBoard",
							new Object[] { approverDtoTemp.getbCode(),
									dto.getSeekerID(), dto.getUserName(),
									dto.getFromAddress1(), dto.getToAddress1(),
									dto.getStartdateValue() }));
					userMessageDTO.setMessageChannel("M");
					userMessageDTO.setToMember(Integer.parseInt(userTemp
							.getId()));
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages.getValue(
									"ride.option.sms",
									new Object[] {
											approverDtoTemp.getbCode(),
											dto.getSeekerID(),
											dto.getUserName(),
											dto.getFromAddress1()
													.substring(
															0,
															(dto.getFromAddress1()
																	.length() >= 20) ? 20
																	: dto.getFromAddress1()
																			.length()),
											dto.getToAddress1()
													.substring(
															0,
															(dto.getToAddress1()
																	.length() >= 20) ? 20
																	: dto.getToAddress1()
																			.length()),
											dto.getStartdateValue() }));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO.setToMember(Integer.parseInt(userTemp
							.getId()));
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
				} else {
					EmailDTO emaildto = new EmailDTO();
					emaildto.setReceiverEmailId(approverDtoTemp.getHoponId());
					emaildto.setSubject("Ride Request for Approval");
					emaildto.setEmailTemplateBody(Messages.getValue(
							"email.template2", new Object[] { "", "",
									messageContent, "", "", "", "" }));
					MailService.sendMail(emaildto);
				}

			} else if (dto.getStatus().equalsIgnoreCase("T")
					&& approverDtoTemp.getHoponId2() != null
					&& approverDtoTemp.getHoponId2().length() > 0) {
				String messageContent = "<span style='font-size: 17px;font-weight: bold;text-decoration: underline;'>Ride Details</span><br>B-Code: "
						+ approverDtoTemp.getbCode()
						+ "<br>Name: "
						+ dto.getUserName()
						+ "<br>Request ID: "
						+ dto.getSeekerID()
						+ "<br>From: "
						+ dto.getFromAddress1()
						+ "<br>To: "
						+ dto.getToAddress1()
						+ "<br>Date Time: "
						+ dto.getStartdateValue();

				String approveLink = Messages.getValue(
						"ride.approve",
						new Object[] {
								dto.getSeekerID(),
								URLEncoder.encode(approverDtoTemp
										.getVerificationCode2()),
								approverDtoTemp.getId(),
								approverDtoTemp.getHoponId2() });
				String rejectLink = Messages.getValue(
						"ride.reject",
						new Object[] {
								dto.getSeekerID(),
								URLEncoder.encode(approverDtoTemp
										.getVerificationCode2()),
								approverDtoTemp.getId(),
								approverDtoTemp.getHoponId2() });

				messageContent += "<br> " + approveLink + " &nbsp;&nbsp;"
						+ rejectLink;
				if (ListOfValuesManager
						.testEmail(approverDtoTemp.getHoponId2())) {
					UserRegistrationDTO userTemp = null;
					userTemp = ListOfValuesManager
							.findUserByEmail(approverDtoTemp.getHoponId2());
					MessageBoardDTO userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(messageContent);
					userMessageDTO.setEmailSubject("Ride Request for Approval");
					userMessageDTO.setMessageChannel("E");
					userMessageDTO.setToMember(Integer.parseInt(userTemp
							.getId()));
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO.setMessage(Messages.getValue(
							"ride.option.msgBoard",
							new Object[] { approverDtoTemp.getbCode(),
									dto.getSeekerID(), dto.getUserName(),
									dto.getFromAddress1(), dto.getToAddress1(),
									dto.getStartdateValue() }));
					userMessageDTO.setMessageChannel("M");
					userMessageDTO.setToMember(Integer.parseInt(userTemp
							.getId()));
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
					userMessageDTO = new MessageBoardDTO();
					userMessageDTO
							.setMessage(Messages.getValue(
									"ride.option.sms",
									new Object[] {
											approverDtoTemp.getbCode(),
											dto.getSeekerID(),
											dto.getUserName(),
											dto.getFromAddress1()
													.substring(
															0,
															(dto.getFromAddress1()
																	.length() >= 20) ? 20
																	: dto.getFromAddress1()
																			.length()),
											dto.getToAddress1()
													.substring(
															0,
															(dto.getToAddress1()
																	.length() >= 20) ? 20
																	: dto.getToAddress1()
																			.length()),
											dto.getStartdateValue() }));
					userMessageDTO.setMessageChannel("S");
					userMessageDTO.setToMember(Integer.parseInt(userTemp
							.getId()));
					userMessageDTO = ListOfValuesManager
							.getInsertedMessage(userMessageDTO);
				} else {
					EmailDTO emaildto = new EmailDTO();
					emaildto.setReceiverEmailId(approverDtoTemp.getHoponId2());
					emaildto.setSubject("Ride Request for Approval");
					emaildto.setEmailTemplateBody(Messages.getValue(
							"email.template2", new Object[] { "", "",
									messageContent, "", "", "", "" }));
					MailService.sendMail(emaildto);
				}
			}
		}
	}

	public void outBoundMessageToAssignTaxi() {
		Map<Integer, List<RideSeekerDTO>> map = new HashMap<Integer, List<RideSeekerDTO>>();
		map = ListOfValuesManager.getunAssignedTaxi();
		for (Map.Entry<Integer, List<RideSeekerDTO>> entry : map.entrySet()) {
			int userId = entry.getKey();
			List<RideSeekerDTO> dtos = entry.getValue();
			UserRegistrationDTO userDto = ListOfValuesManager
					.getUserById(userId);
			MessageBoardDTO userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setMessage(Messages.getValue(
					"ride.unassigned.email",
					new Object[] { userDto.getFirst_name(), dtos.size() }));
			userMessageDTO.setEmailSubject("Reminder to assign vehicle");
			userMessageDTO.setMessageChannel("E");
			userMessageDTO.setToMember(userId);
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);
			userMessageDTO = new MessageBoardDTO();
			userMessageDTO.setMessage(Messages.getValue("ride.unassigned.sms",
					new Object[] { userDto.getFirst_name(), dtos.size() }));

			userMessageDTO.setMessageChannel("S");
			userMessageDTO.setToMember(userId);
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);
		}
	}

	public void outBoundMessageLargeToAssignTaxi() {
		Map<Integer, List<RideSeekerDTO>> map = new HashMap<Integer, List<RideSeekerDTO>>();
		map = ListOfValuesManager.getunAssignedTaxiForOwner();
		for (Map.Entry<Integer, List<RideSeekerDTO>> entry : map.entrySet()) {
			int userId = entry.getKey();
			List<RideSeekerDTO> dtos = entry.getValue();
			UserRegistrationDTO userDto = ListOfValuesManager
					.getUserById(userId);
			MessageBoardDTO userMessageDTO = new MessageBoardDTO();

			try {
				String path = ApplicationUtil.demoDirectoryPath;
				int noOfFiles = 0;
				try {
					noOfFiles = new java.io.File(path).listFiles().length;
				} catch (NullPointerException e) {
				}
				String fileName = "attachement_" + (noOfFiles + 1) + ".xls";

				WritableWorkbook workbook = Workbook
						.createWorkbook(new java.io.File(path + fileName));
				WritableSheet writablesheet = workbook.createSheet("Sheet1", 0);
				writablesheet.addCell(new Label(1, 0, "Request ID"));
				writablesheet.addCell(new Label(2, 0, "Name"));
				writablesheet.addCell(new Label(3, 0, "From"));
				writablesheet.addCell(new Label(4, 0, "To"));
				writablesheet.addCell(new Label(5, 0, "Start Date"));
				int counter = 1;
				for (RideSeekerDTO dto : dtos) {
					writablesheet.addCell(new Label(0, counter, counter + ""));
					writablesheet.addCell(new Label(1, counter, dto
							.getSeekerID()));
					writablesheet.addCell(new Label(2, counter, dto
							.getUserName()));
					writablesheet.addCell(new Label(3, counter, dto
							.getFromAddress1()));
					writablesheet.addCell(new Label(4, counter, dto
							.getToAddress1()));
					writablesheet.addCell(new Label(5, counter, dto
							.getStartdateValue()));
					counter++;
				}
				workbook.write();
				workbook.close();
				Map<String, String> attachements = new HashMap<String, String>();
				attachements.put("Ride list.xls", path + fileName);
				userMessageDTO.setAttachements(attachements);
			} catch (WriteException e) {
			} catch (IOException e) {
			}

			userMessageDTO.setMessage(Messages.getValue("ride.pending.email",
					new Object[] { userDto.getFirst_name(), dtos.size() }));
			userMessageDTO.setEmailSubject("Pending Rides");
			userMessageDTO.setMessageChannel("E");
			userMessageDTO.setToMember(userId);
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);
		}
	}

	public void recurringRideCron() {
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		dtos.addAll(ListOfValuesManager.fetchRecurringRideList());
		Date post2Date = new Date(System.currentTimeMillis() + 86400 * 1000 * 2);
		for (RideSeekerDTO dto : dtos) {
			Date tempDate = dto.getStartDate();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(tempDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(post2Date);
			cal2.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY));
			cal2.set(Calendar.MINUTE, cal1.get(Calendar.MINUTE));
			cal2.set(Calendar.SECOND, cal1.get(Calendar.SECOND));
			post2Date = cal2.getTime();
			String frequency[] = dto.getFrequencyinweek().replace("[", "")
					.replace("]", "").split(",");
			// Testing if 2nd day present in trip frequency or not.
			for (int i = 0; i < frequency.length; i++) {
				if (ApplicationUtil.dateFormat18.format(post2Date)
						.equalsIgnoreCase(frequency[i].trim())) {
					RideManagementDTO dtoTemp = new RideManagementDTO();
					/*
					 * try { BeanUtils.copyProperties(dtoTemp, dto); } catch
					 * (IllegalAccessException e) { } catch
					 * (InvocationTargetException e) { }
					 * catch(ConversionException e) { }
					 */
					dtoTemp.setUserID(dto.getUserID());
					dtoTemp.setViaPoint(dto.getViaPoint());
					dtoTemp.setViaPointLatitude(dto.getViaPointLatitude());
					dtoTemp.setViaPointLongitude(dto.getViaPointLongitude());
					dtoTemp.setFromAddress1(dto.getFromAddress1());
					dtoTemp.setToAddress1(dto.getToAddress1());

					DateFormat dateFormat = new SimpleDateFormat(
							ApplicationUtil.datePattern3);
					dtoTemp.setStartdateValue(dateFormat.format(post2Date));
					dtoTemp.setRideID(null);
					dtoTemp.setStartDate(post2Date);
					dtoTemp.setFlexiTimeAfter(post2Date);
					dtoTemp.setFlexiTimeBefore(post2Date);
					dtoTemp.setStatus(dto.getStatus());
					dtoTemp.setCreated_dt(new Date());
					dtoTemp.setFromAddressCity(dto.getToAddressCity());
					dtoTemp.setFromAddressPin(dto.getFromAddressPin());
					dtoTemp.setFromAddressCity(dto.getToAddressCity());
					dtoTemp.setToAddressPin(dto.getToAddressPin());
					dtoTemp.setCreatedBy(dto.getCreatedBy());
					dtoTemp.setSharedTaxi(dto.isSharedTaxi());
					dtoTemp.setCustom(dto.getCustom());
					dtoTemp.setStartPointLatitude(dto.getStartPointLatitude());
					dtoTemp.setStartPointLongitude(dto.getStartPointLongitude());
					dtoTemp.setEndPointLatitude(dto.getEndPointLatitude());
					dtoTemp.setEndPointLongitude(dto.getEndPointLongitude());
					dtoTemp.setApproverID(dto.getApproverID());
					dtoTemp.setRecurring("F");
					UserPreferencesDTO userDto = ListOfValuesManager
							.getUserPreferences(Integer.parseInt(dtoTemp
									.getUserID()));
					List x1;
					try {
						x1 = ApplicationUtil.calculateTimeWindowSettings(
								dtoTemp.getFromAddress1(), "",
								dtoTemp.getToAddress1(),
								userDto.getMaxWaitTime(),
								dtoTemp.getStartdateValue());
						if (x1.size() > 0) {
							dtoTemp.setStartdateValue(x1.get(1).toString());
							dtoTemp.setStartDateEarly(x1.get(1).toString());
							dtoTemp.setStartDateLate(x1.get(2).toString());
							dtoTemp.setEndDateEarly(x1.get(3).toString());
							dtoTemp.setEndDateLate(x1.get(4).toString());
							float distance = Integer.parseInt(x1.get(5)
									.toString()) / 1000;
							dtoTemp.setRideDistance(distance);
							dtoTemp.setRideCost((distance * 5) + "");
						}
					} catch (IOException e) {
					} catch (JSONException e) {
					}
					if (!ListOfValuesManager.checkRideSeekerDuplicacy(dtoTemp)) {
						Connection con = ListOfValuesManager
								.getBroadConnection();
						try {
							dtoTemp = ListOfValuesManager.getRideSeekerEntery(
									"findByDTO", dtoTemp, con);

							try {
								frequencyDTO = new FrequencyDTO();
								frequencyDTO.setStartDate(dateFormat
										.format(post2Date));
								frequencyDTO.setEndDate(dateFormat
										.format(dtoTemp.getEndDate()));
							} catch (NullPointerException e) {
							}

							frequencyDTO.setTime(post2Date);
							List<String> freq = new ArrayList<String>();
							freq.add("Once");
							frequencyDTO.setFrequency(freq);
							frequencyDTO.setRideSeekerId(dtoTemp.getRideID());

							frequencyDTO = ListOfValuesManager
									.getFrequencyEntery("findByDTO",
											frequencyDTO, con);

							if (dto.getSubSeekers().length() == 0)
								dto.setSubSeekers(dtoTemp.getRideID());
							else
								dto.setSubSeekers(dto.getSubSeekers() + ","
										+ dtoTemp.getRideID());
							ListOfValuesManager.addSubSeekers(dto, con);

						} catch (ConfigurationException e) {
							LoggerSingleton.getInstance().error(
									e.getStackTrace()[0].getClassName()
											+ "->"
											+ e.getStackTrace()[0]
													.getMethodName()
											+ "() : "
											+ e.getStackTrace()[0]
													.getLineNumber() + " :: "
											+ e.getMessage());
							rollbackTest = true;
						} finally {
							if (rollbackTest) {
								try {
									con.rollback();
								} catch (SQLException e) {
									LoggerSingleton.getInstance().error(
											e.getStackTrace()[0].getClassName()
													+ "->"
													+ e.getStackTrace()[0]
															.getMethodName()
													+ "() : "
													+ e.getStackTrace()[0]
															.getLineNumber()
													+ " :: " + e.getMessage());
								}
								ListOfValuesManager.releaseConnection(con);
							} else {
								try {
									con.commit();
								} catch (SQLException e) {
									LoggerSingleton.getInstance().error(
											e.getStackTrace()[0].getClassName()
													+ "->"
													+ e.getStackTrace()[0]
															.getMethodName()
													+ "() : "
													+ e.getStackTrace()[0]
															.getLineNumber()
													+ " :: " + e.getMessage());
								}
								ListOfValuesManager.releaseConnection(con);
							}
						}
					}
					break;
				}
			}
		}
	}

	public void paymentTransaction() {
		paymentList.clear();
		try {
			paymentList.addAll(ListOfValuesManager.fetchPaymentList(Integer
					.parseInt(userRegistrationDTO.getId())));
		} catch (NumberFormatException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}
		duePaymentList.clear();
		for (PaymentDTO dto : paymentList) {
			if (dto.getStatus().equalsIgnoreCase("A")) {
				duePaymentList.add(dto);
			}
		}
	}

	public void validateUserPayment() {
		List<Integer> circleIds = new ArrayList<Integer>();
		for (CircleDTO dto : allMemberCircleList) {
			circleIds.add(dto.getCircleID());
		}
		List<PaymentDTO> circlePaymentDue = new ArrayList<PaymentDTO>();
		circlePaymentDue.addAll(ListOfValuesManager
				.fetchActivePaymentListForCircle(circleIds
						.toArray(new Integer[circleIds.size()])));

		if (circlePaymentDue.size() > 0) {
			userCirclePaymentPending = true;
		}
	}

	public void paymentTxnCronToSwapAmount() {
		// First fetch list of paymentTxns fro date before today.
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		List<PaymentTxnsDTO> dtos = new ArrayList<PaymentTxnsDTO>();
		dtos.addAll(ListOfValuesManager.fetchAllTxnByDate(cal.getTime()));

		for (PaymentTxnsDTO dto : dtos) {
			if (dto.getFromPayer() == 100) {
				// deduct amount from hopon account and add to user account.
				UserRegistrationDTO userDto = new UserRegistrationDTO();
				userDto.setId(String.valueOf(dto.getToPayee()));
				userDto.setTotalCredit(dto.getAmount());

				HoponAccountDTO hoponAccountDto = new HoponAccountDTO();
				hoponAccountDto.setBalance(dto.getAmount());
				hoponAccountDto.setIdHoponAccounts(100);
				hoponAccountDto.setUpdateDate(ApplicationUtil
						.currentTimeStamp());
				hoponAccountDto.setUpdatedBy(1);

				try {
					ListOfValuesManager.paymentTxnHoponToUser(hoponAccountDto,
							userDto);
				} catch (ConfigurationException e) {
				}
			}
			if (dto.getToPayee() == 100) {
				// deduct amount from user account and add to hopon account.
				UserRegistrationDTO userDto = new UserRegistrationDTO();
				userDto.setId(String.valueOf(dto.getToPayee()));
				userDto.setTotalCredit(dto.getAmount());

				HoponAccountDTO hoponAccountDto = new HoponAccountDTO();
				hoponAccountDto.setBalance(dto.getAmount());
				hoponAccountDto.setIdHoponAccounts(100);
				hoponAccountDto.setUpdateDate(ApplicationUtil
						.currentTimeStamp());
				hoponAccountDto.setUpdatedBy(1);

				try {
					ListOfValuesManager.paymentTxnUserToHopon(hoponAccountDto,
							userDto);
				} catch (ConfigurationException e) {
				}
			}
		}

	}
	
	/*
	 * This <code>ContactUs</code> Method is nothing but controller for the ContactUs in this we getting the data with the help of ListOfValueManager
	 * By using the ListOfvaluesManager Class invoke the getContactInfo with parameters
	 */

	public String contactUs() {
		Connection con = (Connection) ListOfValuesManager.getBroadConnection();
		boolean flag = ListOfValuesManager.getContactInfo(con, contactusDTO);
		System.out.println("values are getting from the controller is:"
				+ contactusDTO);
		if (flag == true) {
			System.out.println("added successfully from the userAction");
			contactusDTO = new ContactusDTO();
		
		} else {
			System.out.println("Failure");
		}

		return "contactUs";
	}
}
