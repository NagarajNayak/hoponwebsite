package com.hopon.utils;

import com.hopon.dao.*;

public class DAOProvider {
	private static UserRegistrationDAO userRegistrationDAO = new UserRegistrationDAO();
	private static CompanyRegisterDAO companyRegisterDAO = new CompanyRegisterDAO();
	private static CircleDAO circleDAO = new CircleDAO();
	private static RideManagementDAO rideManagementDAO = new RideManagementDAO();
	private static RideSeekerDAO rideSeekerDAO = new RideSeekerDAO();
	private static FrequencyDAO frequencyDAO = new FrequencyDAO();
	private static FavoritePlacesDAO favoritePlacesDAO = new FavoritePlacesDAO();
	private static PoolRequestsDAO poolRequestsDAO = new PoolRequestsDAO();
	private static VehicleMasterDAO vehicleMasterDAO = new VehicleMasterDAO();
	private static CityDAO cityDAO = new CityDAO();
	private static CircleOwnerDAO circleOwnerDAO = new CircleOwnerDAO();
	private static CircleOwnerManagerDAO circleOwnerManagerDAO = new CircleOwnerManagerDAO();
	private static MatchTripDAO matchTripDAO = new MatchTripDAO();
	private static CircleMemberDAO circleMemberDAO = new CircleMemberDAO();
	private static MessageBoardDAO messageBoardDAO = new MessageBoardDAO();
	private static UserPreferencesDAO userPreferencesDAO = new UserPreferencesDAO();
	private static CircleAffiliationsDAO circleAffiliationsDAO = new CircleAffiliationsDAO();
	private static ApproverDAO approverDAO = new ApproverDAO();
	private static PageStoreDAO pageStoreDAO = new PageStoreDAO();
	private static LoginPageDAO loginPageDAO = new LoginPageDAO();
	private static PaymentDAO paymentDAO = new PaymentDAO();
	private static PaymentPlanDAO paymentPlanDAO = new PaymentPlanDAO();
	private static PaymentTxnsDAO paymentTxnsDAO = new PaymentTxnsDAO();
	//Helpper class
	private static ContactUsDAO contactUsDAO = new ContactUsDAO();

	public static MessageBoardDAO getMessageBoardDAO() {
		return messageBoardDAO;
	}

	public static void setMessageBoardDAO(MessageBoardDAO messageBoardDAO) {
		DAOProvider.messageBoardDAO = messageBoardDAO;
	}

	public static FavoritePlacesDAO getFavoritePlacesDAO() {
		return favoritePlacesDAO;
	}

	public static void setFavoritePlacesDAO(FavoritePlacesDAO favoritePlacesDAO) {
		DAOProvider.favoritePlacesDAO = favoritePlacesDAO;
	}

	public static FrequencyDAO getFrequencyDAO() {
		return frequencyDAO;
	}

	public static void setFrequencyDAO(FrequencyDAO frequencyDAO) {
		DAOProvider.frequencyDAO = frequencyDAO;
	}

	public static RideSeekerDAO getRideSeekerDAO() {
		return rideSeekerDAO;
	}

	public static void setRideSeekerDAO(RideSeekerDAO rideSeekerDAO) {
		DAOProvider.rideSeekerDAO = rideSeekerDAO;
	}

	public static RideManagementDAO getRideManagementDAO() {
		return rideManagementDAO;
	}

	public static void setRideManagementDAO(RideManagementDAO rideManagementDAO) {
		DAOProvider.rideManagementDAO = rideManagementDAO;
	}

	public static CircleDAO getCircleDAO() {
		return circleDAO;
	}

	public static void setCircleDAO(CircleDAO circleDAO) {
		DAOProvider.circleDAO = circleDAO;
	}

	public static CompanyRegisterDAO getCompanyRegisterDAO() {
		return companyRegisterDAO;
	}

	public static void setCompanyRegisterDAO(
			CompanyRegisterDAO companyRegisterDAO) {
		DAOProvider.companyRegisterDAO = companyRegisterDAO;
	}

	public static UserRegistrationDAO getUserRegistrationDAO() {
		return userRegistrationDAO;
	}

	public static void setUserRegistrationDAO(
			UserRegistrationDAO userRegistrationDAO) {
		DAOProvider.userRegistrationDAO = userRegistrationDAO;
	}

	public static VehicleMasterDAO getVehicleMasterDAO() {
		return vehicleMasterDAO;
	}

	public static void setVehicleMasterDAO(VehicleMasterDAO vehicleMasterDAO) {
		DAOProvider.vehicleMasterDAO = vehicleMasterDAO;
	}

	public static CircleOwnerDAO getCircleOwnerDAO() {
		return circleOwnerDAO;
	}

	public static void setCircleOwnerDAO(CircleOwnerDAO circleOwnerDAO) {
		DAOProvider.circleOwnerDAO = circleOwnerDAO;
	}

	public static PoolRequestsDAO getPoolRequestsDAO() {
		return poolRequestsDAO;
	}

	public static void setPoolRequestsDAO(PoolRequestsDAO poolRequestsDAO) {
		DAOProvider.poolRequestsDAO = poolRequestsDAO;
	}

	public static CityDAO getCityDAO() {
		return cityDAO;
	}

	public static void setCityDAO(CityDAO cityDAO) {
		DAOProvider.cityDAO = cityDAO;
	}

	public static CircleOwnerManagerDAO getCircleOwnerManagerDAO() {
		return circleOwnerManagerDAO;
	}

	public static void setCircleOwnerManagerDAO(
			CircleOwnerManagerDAO circleOwnerManagerDAO) {
		DAOProvider.circleOwnerManagerDAO = circleOwnerManagerDAO;
	}

	public static MatchTripDAO getMatchTripDAO() {
		return matchTripDAO;
	}

	public static void setMatchTripDAO(MatchTripDAO matchTripDAO) {
		DAOProvider.matchTripDAO = matchTripDAO;
	}

	public static CircleMemberDAO getCircleMemberDAO() {
		return circleMemberDAO;
	}

	public static void setCircleMemberDAO(CircleMemberDAO circleMemberDAO) {
		DAOProvider.circleMemberDAO = circleMemberDAO;
	}

	public static CircleAffiliationsDAO getCircleAffiliationsDAO() {
		return circleAffiliationsDAO;
	}

	public static void setCircleAffiliationsDAO(
			CircleAffiliationsDAO circleAffiliationsDAO) {
		DAOProvider.circleAffiliationsDAO = circleAffiliationsDAO;
	}

	public static UserPreferencesDAO getUserPreferencesDAO() {
		return userPreferencesDAO;
	}

	public static void setUserPreferencesDAO(
			UserPreferencesDAO userPreferencesDAO) {
		DAOProvider.userPreferencesDAO = userPreferencesDAO;
	}

	public static ApproverDAO getApproverDAO() {
		return approverDAO;
	}

	public static void setApproverDAO(ApproverDAO approverDAO) {
		DAOProvider.approverDAO = approverDAO;
	}

	public static PageStoreDAO getPageStoreDAO() {
		return pageStoreDAO;
	}

	public static void setPageStoreDAO(PageStoreDAO pageStoreDAO) {
		DAOProvider.pageStoreDAO = pageStoreDAO;
	}

	public static LoginPageDAO getLoginPageDAO() {
		return loginPageDAO;
	}

	public static void setLoginPageDAO(LoginPageDAO loginPageDAO) {
		DAOProvider.loginPageDAO = loginPageDAO;
	}

	public static PaymentDAO getPaymentDAO() {
		return paymentDAO;
	}

	public static void setPaymentDAO(PaymentDAO paymentDAO) {
		DAOProvider.paymentDAO = paymentDAO;
	}

	public static PaymentPlanDAO getPaymentPlanDAO() {
		return paymentPlanDAO;
	}

	public static void setPaymentPlanDAO(PaymentPlanDAO paymentPlanDAO) {
		DAOProvider.paymentPlanDAO = paymentPlanDAO;
	}

	public static PaymentTxnsDAO getPaymentTxnsDAO() {
		return paymentTxnsDAO;
	}

	public static void setPaymentTxnsDAO(PaymentTxnsDAO paymentTxnsDAO) {
		DAOProvider.paymentTxnsDAO = paymentTxnsDAO;
	}

	public static ContactUsDAO getContactUsDAO() {
		return contactUsDAO;
	}

	public static void setContactUsDAO(ContactUsDAO contactUsDAO) {
		DAOProvider.contactUsDAO = contactUsDAO;
	}

}
