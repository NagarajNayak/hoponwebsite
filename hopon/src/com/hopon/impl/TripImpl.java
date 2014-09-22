package com.hopon.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hopon.dao.CircleAffiliationsDAO;
import com.hopon.dao.CircleDAO;
import com.hopon.dao.CircleMemberDAO;
import com.hopon.dao.CircleOwnerDAO;
import com.hopon.dao.CircleOwnerManagerDAO;
import com.hopon.dao.CityDAO;
import com.hopon.dao.CompanyRegisterDAO;
import com.hopon.dao.ContactUsDAO;
import com.hopon.dao.FavoritePlacesDAO;
import com.hopon.dao.FrequencyDAO;
import com.hopon.dao.LoginPageDAO;
import com.hopon.dao.MatchTripDAO;
import com.hopon.dao.MessageBoardDAO;
import com.hopon.dao.PageStoreDAO;
import com.hopon.dao.PaymentDAO;
import com.hopon.dao.PaymentPlanDAO;
import com.hopon.dao.PaymentTxnsDAO;
import com.hopon.dao.PoolRequestsDAO;
import com.hopon.dao.RideManagementDAO;
import com.hopon.dao.RideSeekerDAO;
import com.hopon.dao.UserPreferencesDAO;
import com.hopon.dao.UserRegistrationDAO;
import com.hopon.dao.VehicleMasterDAO;
import com.hopon.dao.ApproverDAO;
import com.hopon.dto.ApproverDTO;
import com.hopon.dto.CircleAffiliationsDTO;
import com.hopon.dto.CircleDTO;
import com.hopon.dto.CircleMemberDTO;
import com.hopon.dto.CircleOwnerDTO;
import com.hopon.dto.CircleOwnerManagerDTO;
import com.hopon.dto.CityDTO;
import com.hopon.dto.CombineRideDTO;
import com.hopon.dto.CompanyRegisterDTO;
import com.hopon.dto.ContactusDTO;
import com.hopon.dto.FavoritePlacesDTO;
import com.hopon.dto.FrequencyDTO;
import com.hopon.dto.HoponAccountDTO;
import com.hopon.dto.LoginPageDTO;
import com.hopon.dto.ManageRideDTO;
import com.hopon.dto.ManageRideFormDTO;
import com.hopon.dto.MatchedTripDTO;
import com.hopon.dto.MessageBoardDTO;
import com.hopon.dto.PageStoreDTO;
import com.hopon.dto.PaymentDTO;
import com.hopon.dto.PaymentPlanDTO;
import com.hopon.dto.PaymentTxnsDTO;
import com.hopon.dto.PoolRequestsDTO;
import com.hopon.dto.RideManagementDTO;
import com.hopon.dto.RidePreVehicle;
import com.hopon.dto.RideSeekerDTO;
import com.hopon.dto.SmsReplyDTO;
import com.hopon.dto.UserPreferencesDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.dto.VehicleMasterDTO;
import com.hopon.interf.Trip;
import com.hopon.utils.ConfigurationException;
import com.hopon.utils.DAOProvider;
import com.hopon.utils.LoggerSingleton;

public class TripImpl implements Trip {
	Logger log = Logger.getLogger(TripImpl.class.getName());

	@Override
	public UserRegistrationDTO loadUserRegistration(Connection con,
			String category, UserRegistrationDTO userRegistrationDTO,
			String senderId) throws ConfigurationException {
		// TODO Auto-generated method stub
		UserRegistrationDAO userRegistrationDAO = DAOProvider
				.getUserRegistrationDAO();

		try {
			if (category.equals("all")) {

				userRegistrationDTO = userRegistrationDAO.registerUser(con,
						userRegistrationDTO, senderId);
			} else if (category.equals("findByDTO")) {
				userRegistrationDTO = userRegistrationDAO.registerUser(con,
						userRegistrationDTO, senderId);
			}
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return userRegistrationDTO;
	}

	@Override
	public CompanyRegisterDTO loadCompanyRegistration(Connection con,
			String category, CompanyRegisterDTO companyRegisterDTO)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		CompanyRegisterDAO companyRegisterDAO = DAOProvider
				.getCompanyRegisterDAO();
		try {
			if (category.equals("all")) {

				companyRegisterDTO = companyRegisterDAO.registerCompany(con,
						companyRegisterDTO);
			} else if (category.equals("findByDTO")) {
				companyRegisterDTO = companyRegisterDAO.registerCompany(con,
						companyRegisterDTO);
			}
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return companyRegisterDTO;
	}

	@Override
	public CompanyRegisterDTO getCompanyUpdateByUserId(Connection con,
			CompanyRegisterDTO companyRegisterDTO)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		CompanyRegisterDAO companyRegisterDAO = DAOProvider
				.getCompanyRegisterDAO();
		try {
			companyRegisterDTO = companyRegisterDAO.updateCompanyByUserId(con,
					companyRegisterDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return companyRegisterDTO;
	}

	@Override
	public UserRegistrationDTO validateUser(Connection con,
			UserRegistrationDTO userDTO) throws ConfigurationException {
		// TODO Auto-generated method stub
		UserRegistrationDAO userRegistrationDAO = DAOProvider
				.getUserRegistrationDAO();
		try {

			userDTO = userRegistrationDAO.validateUser(con, userDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return userDTO;
	}

	@Override
	public UserRegistrationDTO closeUserAcount(Connection con,
			UserRegistrationDTO userDTO, String senderId)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		UserRegistrationDAO userRegistrationDAO = DAOProvider
				.getUserRegistrationDAO();
		try {

			userDTO = userRegistrationDAO.closeUserAcount(con, userDTO,
					senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return userDTO;
	}

	@Override
	public List<CircleDTO> getallCircles(Connection con, String userid)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();

		try {
			circleList = circleDAO.findAllCircle(con, userid);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleList;
	}

	@Override
	public List<CircleDTO> getallMemberCircles(Connection con, String userid)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();

		try {
			circleList = circleDAO.findAllMemberCircle(con, userid);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleList;
	}

	@Override
	public List<CompanyRegisterDTO> allCompanyList(Connection con)
			throws ConfigurationException {
		CompanyRegisterDAO companyDAO = DAOProvider.getCompanyRegisterDAO();
		List<CompanyRegisterDTO> companyList = new ArrayList<CompanyRegisterDTO>();
		try {
			companyList = companyDAO.allRegisterCompanyList(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return companyList;
	}

	@Override
	public RideManagementDTO loadRide(Connection con, String category,
			RideManagementDTO rideManagementDTO) throws ConfigurationException {
		RideManagementDAO rideManagementDAO = DAOProvider
				.getRideManagementDAO();
		try {
			if (category.equals("all")) {

				rideManagementDTO = rideManagementDAO.registerRide(con,
						rideManagementDTO);
			} else if (category.equals("findByDTO")) {
				rideManagementDTO = rideManagementDAO.registerRide(con,
						rideManagementDTO);
			}
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return rideManagementDTO;
	}

	@Override
	public RideManagementDTO loadRideSeeker(Connection con, String category,
			RideManagementDTO rideSeekerDTO) throws ConfigurationException {
		// TODO Auto-generated method stub

		RideSeekerDAO rideSeekerDAO = DAOProvider.getRideSeekerDAO();
		try {
			if (category.equals("all")) {

				rideSeekerDTO = rideSeekerDAO.registerRideSeeker(con,
						rideSeekerDTO);
			} else if (category.equals("findByDTO")) {
				rideSeekerDTO = rideSeekerDAO.registerRideSeeker(con,
						rideSeekerDTO);
			}
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return rideSeekerDTO;
	}

	@Override
	public boolean checkRideSeekerDuplicacy(Connection con,
			RideManagementDTO rideSeekerDTO) throws ConfigurationException {
		RideSeekerDAO rideSeekerDAO = DAOProvider.getRideSeekerDAO();
		try {
			return rideSeekerDAO.checkRideSeekerDuplicacy(con, rideSeekerDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean checkDuplicacy(Connection con,
			RideManagementDTO rideSeekerDTO) throws ConfigurationException {
		RideManagementDAO rideManagementDAO = DAOProvider
				.getRideManagementDAO();
		try {
			return rideManagementDAO.checkDuplicacy(con, rideSeekerDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			return false;
		}
	}

	@Override
	public FrequencyDTO loadFrequency(Connection con, String category,
			FrequencyDTO frequencyDTO) throws ConfigurationException {
		// TODO Auto-generated method stub
		FrequencyDAO frequencyDAO = DAOProvider.getFrequencyDAO();
		try {
			if (category.equals("all")) {

				frequencyDTO = frequencyDAO.insertFrequency(con, frequencyDTO);
			} else if (category.equals("findByDTO")) {
				frequencyDTO = frequencyDAO.insertFrequency(con, frequencyDTO);
			}
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return frequencyDTO;
	}

	@Override
	public List<RideManagementDTO> loadAllRidemanagement(Connection con,
			String userName) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<RideManagementDTO> rideList = new ArrayList<RideManagementDTO>();
		RideManagementDAO rideManagementDAO = DAOProvider
				.getRideManagementDAO();
		try {
			rideList = rideManagementDAO.findAllRideManagerList(con, userName);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return rideList;
	}

	@Override
	public FavoritePlacesDTO loadFavoritePlaces(Connection con,
			FavoritePlacesDTO favoritePlacesDTO) throws ConfigurationException {
		FavoritePlacesDAO favoritePlacesDAO = DAOProvider
				.getFavoritePlacesDAO();

		try {
			favoritePlacesDTO = favoritePlacesDAO.enterFavoritePlace(con,
					favoritePlacesDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return favoritePlacesDTO;
	}

	@Override
	public List<CircleDTO> getallRegisteredCircles(Connection con)
			throws ConfigurationException {
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();

		try {
			circleList = circleDAO.findAllRegisteredCircle(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleList;
	}

	@Override
	public List<CircleDTO> getallRegisteredTaxiCircles(Connection con,
			String prefix) throws ConfigurationException {
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();

		try {
			circleList = circleDAO.findAllRegisteredTaxiCircle(con, prefix);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleList;
	}

	@Override
	public List<CircleDTO> getallRegisteredNonTaxiCircles(Connection con,
			String prefix) throws ConfigurationException {
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();

		try {
			circleList = circleDAO.findAllRegisteredNonTaxiCircle(con, prefix);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleList;
	}

	@Override
	public CircleDTO loadCircle(Connection con, CircleDTO circleDTO)
			throws ConfigurationException {
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		try {
			circleDTO = circleDAO.registerCircle(con, circleDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return circleDTO;
	}

	@Override
	public VehicleMasterDTO loadVehicle(Connection con, String category,
			VehicleMasterDTO vehicleMasterDTO) throws ConfigurationException {
		VehicleMasterDAO vehicleMasterDAO = DAOProvider.getVehicleMasterDAO();
		try {
			if (category.equals("all")) {

				vehicleMasterDTO = vehicleMasterDAO.registerVechile(con,
						vehicleMasterDTO);
			} else if (category.equals("findByDTO")) {
				vehicleMasterDTO = vehicleMasterDAO.registerVechile(con,
						vehicleMasterDTO);
			}
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return vehicleMasterDTO;
	}

	@Override
	public List<RideSeekerDTO> loadAllRideSeeker(Connection con, String userName)
			throws ConfigurationException {
		List<RideSeekerDTO> rideSeekerList = new ArrayList<RideSeekerDTO>();
		RideSeekerDAO rideSeekerDAO = DAOProvider.getRideSeekerDAO();
		try {
			rideSeekerList = rideSeekerDAO.findAllRideSeeker(con, userName);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return rideSeekerList;
	}

	@Override
	public List<PoolRequestsDTO> loadAllPoolRequest(Connection con,
			String userName) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<PoolRequestsDTO> poolList = new ArrayList<PoolRequestsDTO>();
		PoolRequestsDAO poolDAO = DAOProvider.getPoolRequestsDAO();

		try {
			poolList = poolDAO.findAllPoolRequests(con, userName);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return poolList;
	}

	@Override
	public List<CityDTO> loadAllCity(Connection con, String finalValue)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<CityDTO> cityList = new ArrayList<CityDTO>();
		CityDAO cityDAO = DAOProvider.getCityDAO();

		try {
			cityList = cityDAO.findCity(con, finalValue);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return cityList;
	}

	@Override
	public CityDTO loadCity(Connection con, String cityName, String stateName)
			throws ConfigurationException {
		CityDTO dto = new CityDTO();
		CityDAO cityDAO = DAOProvider.getCityDAO();

		try {
			dto = cityDAO.findCityByName(con, cityName, stateName);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<FavoritePlacesDTO> loadAllPlaces(Connection con, String userName)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<FavoritePlacesDTO> list = new ArrayList<FavoritePlacesDTO>();
		FavoritePlacesDAO favoritePlacesDAO = DAOProvider
				.getFavoritePlacesDAO();

		try {
			list = favoritePlacesDAO.findAllPlace(con, userName);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return list;
	}

	@Override
	public CircleOwnerDTO loadCircleOwner(Connection con,
			CircleOwnerDTO circleOwnerDTO, String senderId)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		CircleOwnerDAO circleOwnerDAO = DAOProvider.getCircleOwnerDAO();
		try {
			circleOwnerDTO = circleOwnerDAO.registerCircleOwner(con,
					circleOwnerDTO, senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return circleOwnerDTO;
	}

	@Override
	public CircleOwnerDTO updateCircleOwner(Connection con,
			CircleOwnerDTO circleOwnerDTO, String senderId)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		CircleOwnerDAO circleOwnerDAO = DAOProvider.getCircleOwnerDAO();
		try {
			circleOwnerDTO = circleOwnerDAO.updateCircleOwner(con,
					circleOwnerDTO, senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return circleOwnerDTO;
	}

	@Override
	public List<CircleOwnerManagerDTO> loadallCircleForLoginUser(
			Connection con, String userID) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<CircleOwnerManagerDTO> allCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();

		CircleOwnerManagerDAO circleOwnerManagerDAO = DAOProvider
				.getCircleOwnerManagerDAO();

		try {
			allCircleForLoginUserList = circleOwnerManagerDAO
					.findAllCircleForLoginUser(con, userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allCircleForLoginUserList;
	}

	@Override
	public List<CircleOwnerManagerDTO> loadallCorpCircleForLoginUser(
			Connection con, String userID) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<CircleOwnerManagerDTO> allCorpCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();

		CircleOwnerManagerDAO circleOwnerManagerDAO = DAOProvider
				.getCircleOwnerManagerDAO();

		try {
			allCorpCircleForLoginUserList.addAll(circleOwnerManagerDAO
					.findAllCorpCircleForLoginUser(con, userID));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allCorpCircleForLoginUserList;
	}

	@Override
	public List<CircleOwnerManagerDTO> loadallTaxiCircleForLoginUser(
			Connection con, String userID) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<CircleOwnerManagerDTO> allCorpCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();

		CircleOwnerManagerDAO circleOwnerManagerDAO = DAOProvider
				.getCircleOwnerManagerDAO();

		try {
			allCorpCircleForLoginUserList.addAll(circleOwnerManagerDAO
					.findAllTaxiCircleForLoginUser(con, userID));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allCorpCircleForLoginUserList;
	}

	@Override
	public List<CircleOwnerManagerDTO> loadallCircleMemberForLoginUser(
			Connection con, String userID) throws ConfigurationException {
		List<CircleOwnerManagerDTO> allCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();

		CircleOwnerManagerDAO circleOwnerManagerDAO = DAOProvider
				.getCircleOwnerManagerDAO();

		try {
			allCircleForLoginUserList = circleOwnerManagerDAO
					.findAllCircleMemberForLoginUser(con, userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allCircleForLoginUserList;
	}

	@Override
	public List<CircleOwnerManagerDTO> loadallPendingCircleMemberForLoginUser(
			Connection con, String userID) throws ConfigurationException {
		List<CircleOwnerManagerDTO> allPendingCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();

		CircleOwnerManagerDAO circleOwnerManagerDAO = DAOProvider
				.getCircleOwnerManagerDAO();

		try {
			allPendingCircleForLoginUserList = circleOwnerManagerDAO
					.findAllPendingCircleMemberForLoginUser(con, userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allPendingCircleForLoginUserList;
	}

	@Override
	public List<MatchedTripDTO> loasAllMatchTripByCondition(Connection con,
			String startPoint, String endPoint, String companyName, int circleId)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<MatchedTripDTO> tripList = new ArrayList<MatchedTripDTO>();
		MatchTripDAO matchTripDAO = DAOProvider.getMatchTripDAO();
		try {
			tripList = matchTripDAO.findAllMatchedTripByCondition(con,
					startPoint, endPoint, companyName, circleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return tripList;
	}

	@Override
	public List<CombineRideDTO> getAllCombineVehicleList(Connection con,
			String startPoint, String endPoint, String companyName, int circleId)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<CombineRideDTO> tripList = new ArrayList<CombineRideDTO>();
		MatchTripDAO matchTripDAO = DAOProvider.getMatchTripDAO();
		try {
			tripList = matchTripDAO.getAllCombineVehicleList(con, startPoint,
					endPoint, companyName, circleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return tripList;
	}

	@Override
	public List<MatchedTripDTO> findMatchTripByGroupId(Connection con,
			List<String> groupId) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<MatchedTripDTO> tripList = new ArrayList<MatchedTripDTO>();
		MatchTripDAO matchTripDAO = DAOProvider.getMatchTripDAO();
		try {
			tripList = matchTripDAO.findMatchTripByGroupId(con, groupId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return tripList;
	}

	@Override
	public int calculateSingleRide(Connection con, List<Integer> seekerId)
			throws ConfigurationException {
		int rideId = 0;
		RideSeekerDAO matchTripDAO = DAOProvider.getRideSeekerDAO();
		try {
			rideId = matchTripDAO.calculateSingleRide(con, seekerId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return rideId;
	}

	@Override
	public List<CircleOwnerManagerDTO> loadallLoginUserMessage(Connection con,
			String userID) throws ConfigurationException {
		List<CircleOwnerManagerDTO> allUserList = new ArrayList<CircleOwnerManagerDTO>();

		CircleOwnerManagerDAO circleOwnerManagerDAO = DAOProvider
				.getCircleOwnerManagerDAO();

		try {
			allUserList = circleOwnerManagerDAO.findAllLoginUserMessage(con,
					userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allUserList;
	}

	@Override
	public List<CircleDTO> loadallCircleInvitationUserList(Connection con,
			String userID) throws ConfigurationException {
		List<CircleDTO> allCircleInvitationUserList = new ArrayList<CircleDTO>();

		CircleDAO circleDAO = DAOProvider.getCircleDAO();

		try {
			allCircleInvitationUserList = circleDAO.findAllPendingMemberCircle(
					con, userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allCircleInvitationUserList;
	}

	@Override
	public CircleMemberDTO loadConfirmOrDeclineUser(Connection con,
			CircleMemberDTO userdto, String senderId)
			throws ConfigurationException {
		CircleMemberDAO userdao = DAOProvider.getCircleMemberDAO();
		try {
			userdto = userdao.confirmOrDeclineUser(con, userdto, senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return userdto;

	}

	@Override
	public List<VehicleMasterDTO> loadAllVehicle(Connection con, String userID)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<VehicleMasterDTO> vehicleList = new ArrayList<VehicleMasterDTO>();
		VehicleMasterDAO vehicleMasterDAO = DAOProvider.getVehicleMasterDAO();
		try {
			vehicleList = vehicleMasterDAO.findAllVehicle(con, userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return vehicleList;
	}

	@Override
	public RideManagementDTO loadCancleRide(Connection con,
			RideManagementDTO rideManagementDTO) throws ConfigurationException {
		// TODO Auto-generated method stub
		RideManagementDAO rideManagementDAO = DAOProvider
				.getRideManagementDAO();

		try {
			rideManagementDTO = rideManagementDAO.cancleRide(con,
					rideManagementDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return rideManagementDTO;
	}

	@Override
	public RideSeekerDTO loadCancleRideSeeker(Connection con,
			RideSeekerDTO rideSeekerDTO) throws ConfigurationException {
		// TODO Auto-generated method stub
		RideSeekerDAO rideSeekerDAO = DAOProvider.getRideSeekerDAO();
		try {
			rideSeekerDTO = rideSeekerDAO.cancleRideSeeker(con, rideSeekerDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return rideSeekerDTO;
	}

	@Override
	public RideSeekerDTO changeField(Connection con, RideSeekerDTO rideSeekerDTO)
			throws ConfigurationException {
		RideSeekerDAO rideSeekerDAO = DAOProvider.getRideSeekerDAO();
		try {
			rideSeekerDTO = rideSeekerDAO.changeField(con, rideSeekerDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return rideSeekerDTO;
	}

	@Override
	public PoolRequestsDTO loadRateAndWriteNotes(Connection con,
			PoolRequestsDTO pooldto) throws ConfigurationException {
		// TODO Auto-generated method stub
		PoolRequestsDAO pooldao = DAOProvider.getPoolRequestsDAO();

		try {
			pooldto = pooldao.rateAndWriteNotes(con, pooldto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return pooldto;
	}

	@Override
	public UserRegistrationDTO loadAverageRatingForUser(Connection con,
			UserRegistrationDTO dto) throws ConfigurationException {
		PoolRequestsDAO dao = DAOProvider.getPoolRequestsDAO();
		try {
			dto = dao.getAverageRatingForUser(con, dto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<PoolRequestsDTO> loadAllPoolRequestSeeker(Connection con,
			String userName) throws ConfigurationException {
		List<PoolRequestsDTO> poolList = new ArrayList<PoolRequestsDTO>();
		PoolRequestsDAO poolDAO = DAOProvider.getPoolRequestsDAO();

		try {
			poolList = poolDAO.findAllPoolRequestsSeekers(con, userName);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return poolList;

	}

	@Override
	public CircleDTO loadDeclineCircle(Connection con, CircleDTO circleDTO,
			String userId) throws ConfigurationException {
		CircleDAO circleDAO = DAOProvider.getCircleDAO();

		try {
			circleDTO = circleDAO.declineCircle(con, circleDTO, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return circleDTO;
	}

	@Override
	public CircleDTO deactivateCircle(Connection con, CircleDTO circleDTO)
			throws ConfigurationException {
		CircleDAO circleDAO = DAOProvider.getCircleDAO();

		try {
			circleDTO = circleDAO.deactivateCircle(con, circleDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}

		return circleDTO;
	}

	@Override
	public List<CircleDTO> loadCircleByName(Connection con, String circleName,
			String userId) throws ConfigurationException {
		List<CircleDTO> circleList = null;
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		try {
			circleList = circleDAO.findAllRegisteredCircleByName(con,
					circleName, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleList;
	}

	@Override
	public List<CompanyRegisterDTO> loadCompanyForLoginUser(Connection con,
			String userID) throws ConfigurationException {
		List<CompanyRegisterDTO> listOfCompany = null;
		CompanyRegisterDAO companyDAO = DAOProvider.getCompanyRegisterDAO();
		try {
			listOfCompany = companyDAO.allRegisterCompanyListByLoginUser(con,
					userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return listOfCompany;
	}

	@Override
	public UserRegistrationDTO loadupdateUser(Connection con,
			UserRegistrationDTO userRegistrationDTO, String senderId)
			throws ConfigurationException {
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		try {
			userRegistrationDTO = dao.updateUser(con, userRegistrationDTO,
					senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return userRegistrationDTO;
	}

	@Override
	public UserRegistrationDTO loadupdateUserPassword(Connection con,
			UserRegistrationDTO userRegistrationDTO, String senderId)
			throws ConfigurationException {
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		try {
			userRegistrationDTO = dao.updateUserPassword(con,
					userRegistrationDTO, senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return userRegistrationDTO;
	}

	@Override
	public CircleMemberDTO loadCirclemember(Connection con,
			CircleMemberDTO circleMemberDTO, String senderId)
			throws ConfigurationException {
		CircleMemberDAO dao = DAOProvider.getCircleMemberDAO();
		try {
			circleMemberDTO = dao.createCircleMember(con, circleMemberDTO,
					senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleMemberDTO;
	}

	@Override
	public List<UserRegistrationDTO> loadAllUser(Connection con, String prifex,
			int limit) throws ConfigurationException {
		List<UserRegistrationDTO> allUserList = new ArrayList<UserRegistrationDTO>();
		UserRegistrationDAO userRegistrationDAO = DAOProvider
				.getUserRegistrationDAO();
		try {
			allUserList = userRegistrationDAO.getAllUser(con, prifex, limit);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allUserList;
	}

	@Override
	public CompanyRegisterDTO loadUpdateCompany(Connection con,
			CompanyRegisterDTO companyRegisterDTO)
			throws ConfigurationException {
		CompanyRegisterDAO companyRegisterDAO = DAOProvider
				.getCompanyRegisterDAO();
		try {
			companyRegisterDTO = companyRegisterDAO.updateComapny(con,
					companyRegisterDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return companyRegisterDTO;
	}

	@Override
	public PoolRequestsDTO loadInsertinPool(Connection con,
			PoolRequestsDTO poolRequestsDTO) throws ConfigurationException {
		PoolRequestsDAO poolRequestsDAO = DAOProvider.getPoolRequestsDAO();
		try {
			poolRequestsDTO = poolRequestsDAO
					.insertInPool(con, poolRequestsDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return poolRequestsDTO;
	}

	@Override
	public VehicleMasterDTO loadUpdateSeat(Connection con,
			VehicleMasterDTO vehicleMasterDTO) throws ConfigurationException {
		VehicleMasterDAO dao = DAOProvider.getVehicleMasterDAO();
		try {
			vehicleMasterDTO = dao.updateSeat(con, vehicleMasterDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return vehicleMasterDTO;
	}

	@Override
	public MatchedTripDTO loasAllMatchTripByCondition1(Connection con,
			String vehicleID, String startTime, String company)
			throws ConfigurationException {
		MatchedTripDTO dto = new MatchedTripDTO();
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			dto = dao.findAllMatchedTrip(con, vehicleID, startTime, company);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<RideManagementDTO> loadMethodForPopupOne(Connection con,
			String vehicleID, String startTime) throws ConfigurationException {
		List<RideManagementDTO> listRide = null;
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			listRide = dao.methodForPopupOne(con, vehicleID, startTime);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return listRide;
	}

	@Override
	public List<RideSeekerDTO> loadAllSeekerForRideID(Connection con,
			String rideID) throws ConfigurationException {
		List<RideSeekerDTO> seekerList = null;
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			seekerList = dao.allRideSeekerForAGivenRide(con, rideID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return seekerList;
	}

	@Override
	public VehicleMasterDTO loadupdateVehicle(Connection con,
			VehicleMasterDTO vehicleMasterDTO) throws ConfigurationException {
		VehicleMasterDAO dao = DAOProvider.getVehicleMasterDAO();
		try {
			vehicleMasterDTO = dao.updateVehicle(con, vehicleMasterDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return vehicleMasterDTO;
	}

	@Override
	public VehicleMasterDTO loadupdateDefaultVehicle(Connection con,
			VehicleMasterDTO vehicleMasterDTO) throws ConfigurationException {
		VehicleMasterDAO dao = DAOProvider.getVehicleMasterDAO();
		try {
			vehicleMasterDTO = dao.updateDefaultStatus(con, vehicleMasterDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return vehicleMasterDTO;
	}

	@Override
	public VehicleMasterDTO loadInActivateVehicle(Connection con,
			VehicleMasterDTO vehicleMasterDTO) throws ConfigurationException {
		VehicleMasterDAO dao = DAOProvider.getVehicleMasterDAO();
		try {
			vehicleMasterDTO = dao.inActivateVehicle(con, vehicleMasterDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return vehicleMasterDTO;
	}

	@Override
	public List<MessageBoardDTO> loadUnreadMessage(Connection con,
			String userid, String status) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<MessageBoardDTO> message = new ArrayList<MessageBoardDTO>();
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		try {
			// message = dao.findMessage(con, userid, status, rowNumber, limit);
			message = dao.fetchBoardMesage(con, userid, status);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return message;
	}

	@Override
	public MessageBoardDTO loadInsertedMessage(Connection con,
			MessageBoardDTO messagedto) throws ConfigurationException {
		// TODO Auto-generated method stub
		MessageBoardDTO message = null;
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		try {
			message = dao.insertMessage(con, messagedto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return message;
	}

	@Override
	public void updateMessageStatusRead(Connection con, int[] messageId)
			throws ConfigurationException {
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		List<Integer> messageIdList = new ArrayList<Integer>();
		for (int i : messageId) {
			messageIdList.add(i);
		}
		try {
			dao.makeMessaeRead(con, messageIdList);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void makePopupMessageRead(Connection con, int[] messageId)
			throws ConfigurationException {
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		List<Integer> messageIdList = new ArrayList<Integer>();
		for (int i : messageId) {
			messageIdList.add(i);
		}
		try {
			dao.makePopupMessageRead(con, messageIdList);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void updateMessageStatusDelete(Connection con, int[] messageId)
			throws ConfigurationException {
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		List<Integer> messageIdList = new ArrayList<Integer>();
		for (int i : messageId) {
			messageIdList.add(i);
		}
		try {
			dao.makeMessaeDelete(con, messageIdList);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public List<MessageBoardDTO> loadEmailSendingMessage(Connection con)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<MessageBoardDTO> message = new ArrayList<MessageBoardDTO>();
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		try {
			message = dao.fetchEmailMesage(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return message;

	}

	@Override
	public List<MessageBoardDTO> loadSmsSendingMessage(Connection con)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<MessageBoardDTO> message = new ArrayList<MessageBoardDTO>();
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		try {
			message = dao.fetchSmsMesage(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return message;

	}

	@Override
	public List<MessageBoardDTO> loadPopupMessage(Connection con, int userId)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		List<MessageBoardDTO> message = new ArrayList<MessageBoardDTO>();
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		try {
			message = dao.fetchPopupMesage(con, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return message;

	}

	@Override
	public void loadMakeEmailStatusSent(Connection con, int[] messageId)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		try {
			dao.updateMessageSentStatus(con, messageId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public boolean testVehicleRegistrationNumber(Connection con,
			String registrationNo) throws ConfigurationException {
		VehicleMasterDAO vehicleMasterDAO = DAOProvider.getVehicleMasterDAO();
		boolean test = true;
		try {
			test = vehicleMasterDAO.testVehicleRegistrationNumber(con,
					registrationNo);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return test;
	}

	@Override
	public boolean testEmail(Connection con, String email)
			throws ConfigurationException {
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		boolean test = true;
		try {
			test = dao.testEmail(con, email);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return test;
	}

	@Override
	public boolean testUniqueMobileNumber(Connection con, String mobileNo,
			int excludedUserId) throws ConfigurationException {
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		boolean test = true;
		try {
			test = dao.testUniqueMobileNumber(con, mobileNo, excludedUserId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return test;
	}

	@Override
	public String testEmailAllStatus(Connection con, String email)
			throws ConfigurationException {
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		String test = "N";
		try {
			test = dao.testEmailAllStatus(con, email);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return test;
	}

	@Override
	public RideManagementDTO getRideManagerPopupData(Connection con,
			String rideID) throws ConfigurationException {
		RideManagementDTO dto = new RideManagementDTO();
		RideManagementDAO dao = DAOProvider.getRideManagementDAO();
		try {
			dto = dao.getRideManagerData(con, rideID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public RideManagementDTO getRideManagerPopupDataDirect(Connection con,
			String rideID) throws ConfigurationException {
		RideManagementDTO dto = new RideManagementDTO();
		RideManagementDAO dao = DAOProvider.getRideManagementDAO();
		try {
			dto = dao.getRideManagerDataDirect(con, rideID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public VehicleMasterDTO getVehicleDtoById(Connection con, String vehicleID)
			throws ConfigurationException {
		VehicleMasterDTO dto = new VehicleMasterDTO();
		VehicleMasterDAO dao = DAOProvider.getVehicleMasterDAO();
		try {
			dto = dao.getVehicleDtoById(con, vehicleID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<CircleDTO> getAllAffiliatedTaxiCircle(Connection con,
			String prefix) throws ConfigurationException {
		CircleDAO circleDAO = DAOProvider.getCircleDAO();
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();

		try {
			circleList = circleDAO.findAllAffiliatedTaxiCircle(con, prefix);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return circleList;
	}

	@Override
	public void getMakeTaxiCircleAffiliated(Connection con, int parentCircleId,
			int childCircleId) throws ConfigurationException {
		CircleAffiliationsDTO dto = new CircleAffiliationsDTO();
		CircleAffiliationsDAO dao = DAOProvider.getCircleAffiliationsDAO();
		try {
			dao.makeTaxiCircleAffiliated(con, parentCircleId, childCircleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public List<CircleAffiliationsDTO> getAllAffiliatedCircle(Connection con,
			String circleId) throws ConfigurationException {
		List<CircleAffiliationsDTO> dto = new ArrayList<CircleAffiliationsDTO>();
		CircleAffiliationsDAO dao = DAOProvider.getCircleAffiliationsDAO();
		try {
			dto = dao.findActiveAffiliatedCircle(con,
					Integer.parseInt(circleId));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<CircleAffiliationsDTO> getAllPendingCircle(Connection con,
			String circleId) throws ConfigurationException {
		List<CircleAffiliationsDTO> dto = new ArrayList<CircleAffiliationsDTO>();
		CircleAffiliationsDAO dao = DAOProvider.getCircleAffiliationsDAO();
		try {
			dto = dao.findPendingAffiliatedCircle(con,
					Integer.parseInt(circleId));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public void getMakeTaxiCircleAffiliatedActive(Connection con,
			int parentCircleId, int childCircleId)
			throws ConfigurationException {
		CircleAffiliationsDTO dto = new CircleAffiliationsDTO();
		CircleAffiliationsDAO dao = DAOProvider.getCircleAffiliationsDAO();
		try {
			dao.makeTaxiCircleAffiliatedActive(con, parentCircleId,
					childCircleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void getMakeTaxiCircleAffiliatedInactive(Connection con,
			int parentCircleId, int childCircleId)
			throws ConfigurationException {
		CircleAffiliationsDTO dto = new CircleAffiliationsDTO();
		CircleAffiliationsDAO dao = DAOProvider.getCircleAffiliationsDAO();
		try {
			dao.makeTaxiCircleAffiliatedInactive(con, parentCircleId,
					childCircleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public CircleDTO getCircleDtoByCircleId(Connection con, int circleId)
			throws ConfigurationException {
		CircleDTO dto = new CircleDTO();
		CircleDAO dao = DAOProvider.getCircleDAO();
		try {
			dto = dao.findCircleByCircleId(con, circleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<CircleAffiliationsDTO> getAllPendingAffiliatedCircle(
			Connection con, int userId) throws ConfigurationException {
		List<CircleAffiliationsDTO> dto = new ArrayList<CircleAffiliationsDTO>();
		CircleAffiliationsDAO dao = DAOProvider.getCircleAffiliationsDAO();
		try {
			dto = dao.findPendingAffiliatedCircleByUserId(con, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<CircleDTO> getAffiliateCircleForTaxiUser(Connection con,
			int userId) throws ConfigurationException {
		List<CircleDTO> dto = new ArrayList<CircleDTO>();
		CircleAffiliationsDAO dao = new CircleAffiliationsDAO();
		try {
			dto = dao.getAffiliateCircleForTaxiUser(con, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<MatchedTripDTO> loadRidesByCircle(Connection con,
			String circleId) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<MatchedTripDTO> listOfRideByCircle = null;
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			listOfRideByCircle = dao.findAllMatchedTripForCircle(con, circleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return listOfRideByCircle;
	}

	@Override
	public List<RideSeekerDTO> loadCompletedRideSeekerList(Connection con)
			throws ConfigurationException {
		List<RideSeekerDTO> dto = new ArrayList<RideSeekerDTO>();
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		try {
			dto = dao.fetchCompletedRideSeekerList(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<RideManagementDTO> loadCompletedRideManagementList(
			Connection con) throws ConfigurationException {
		List<RideManagementDTO> dto = new ArrayList<RideManagementDTO>();
		RideManagementDAO dao = DAOProvider.getRideManagementDAO();
		try {
			dto = dao.fetchCompletedRideManagementList(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public UserRegistrationDTO loadForgotPasswordUser(Connection con,
			String userName, String randomPassword, String senderId)
			throws ConfigurationException {
		UserRegistrationDTO dto = new UserRegistrationDTO();
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		try {
			dto = dao.UpdateRandomPassword(con, userName, randomPassword,
					senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<UserRegistrationDTO> loadForgotPasswordUser(Connection con,
			List<Integer> userId) throws ConfigurationException {
		List<UserRegistrationDTO> dto = new ArrayList<UserRegistrationDTO>();
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		try {
			dto = dao.getAllUserById(con, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public UserRegistrationDTO getUserById(Connection con, int userId)
			throws ConfigurationException {
		UserRegistrationDTO dto = new UserRegistrationDTO();
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		try {
			dto = dao.getUserById(con, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<UserRegistrationDTO> loadAlluserForACircle(Connection con,
			String userID) throws ConfigurationException {
		// TODO Auto-generated method stub
		List<UserRegistrationDTO> allUser = new ArrayList<UserRegistrationDTO>();
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		try {
			allUser = dao.allUserOfAcircle(con, userID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return allUser;
	}

	@Override
	public RideSeekerDTO loadRideSeekerData(Connection con, int rideSeekerId)
			throws ConfigurationException {
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		RideSeekerDTO dto = new RideSeekerDTO();
		try {
			dto = dao.getRideSeekerData(con, rideSeekerId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<Integer> findAllUsersInCircle(Connection con, int circleID)
			throws ConfigurationException {
		CircleDAO dao = DAOProvider.getCircleDAO();
		try {
			return dao.findAllUsersInCircle(con, circleID);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void makeCircleInactiveForUser(Connection con,
			UserRegistrationDTO userRegistrationDto)
			throws ConfigurationException {
		CircleDAO dao = DAOProvider.getCircleDAO();
		try {
			dao.makeCircleInactiveForUser(con, userRegistrationDto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void makeCircleMemberInactiveForUser(Connection con,
			UserRegistrationDTO userRegistrationDto, String senderId)
			throws ConfigurationException {
		CircleMemberDAO dao = DAOProvider.getCircleMemberDAO();
		try {
			dao.makeCircleMemberInactiveForUser(con, userRegistrationDto,
					senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void makeCircleOwnerForUser(Connection con,
			UserRegistrationDTO userRegistrationDto, String senderId)
			throws ConfigurationException {
		CircleOwnerDAO dao = DAOProvider.getCircleOwnerDAO();
		try {
			dao.makeCircleOwnerForUser(con, userRegistrationDto, senderId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public UserRegistrationDTO findUserDtoByVehicleId(Connection con,
			int vehicleId) throws ConfigurationException {
		UserRegistrationDTO dto = null;
		VehicleMasterDAO dao = DAOProvider.getVehicleMasterDAO();
		try {
			dto = dao.findUserDtoByVehicleId(con, vehicleId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public VehicleMasterDTO getVehicleDtoByRideId(Connection con, int rideId)
			throws ConfigurationException {
		VehicleMasterDTO dto = null;
		VehicleMasterDAO dao = DAOProvider.getVehicleMasterDAO();
		try {
			dto = dao.getVehicleDtoByRideId(con, rideId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<RideSeekerDTO> findRideSeekerDetailsForApprove(Connection con,
			String email_id) throws ConfigurationException {
		List<RideSeekerDTO> list = new ArrayList<RideSeekerDTO>();
		ApproverDAO dao = DAOProvider.getApproverDAO();
		try {
			list = dao.findRideSeekerDetailsForApprove(con, email_id);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return list;
	}

	@Override
	public void makeRideSeekerCancelForUser(Connection con,
			UserRegistrationDTO userRegistrationDto)
			throws ConfigurationException {
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		try {
			dao.makeRideSeekerCancelForUser(con, userRegistrationDto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void makeRideCancelForUser(Connection con,
			UserRegistrationDTO userRegistrationDto)
			throws ConfigurationException {
		RideManagementDAO dao = DAOProvider.getRideManagementDAO();
		try {
			dao.makeRideCancelForUser(con, userRegistrationDto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public UserPreferencesDTO getUserPreferencesSave(Connection con,
			UserPreferencesDTO userPreferencesDTO)
			throws ConfigurationException {
		UserPreferencesDAO dao = DAOProvider.getUserPreferencesDAO();

		try {
			userPreferencesDTO = dao.insert(con, userPreferencesDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in Adding User Prefereces", e);
		}
		return userPreferencesDTO;
	}

	@Override
	public UserPreferencesDTO getUserPreferencesEdit(Connection con,
			UserPreferencesDTO userPreferencesDTO)
			throws ConfigurationException {
		UserPreferencesDAO dao = DAOProvider.getUserPreferencesDAO();
		try {
			userPreferencesDTO = dao.update(con, userPreferencesDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return userPreferencesDTO;
	}

	@Override
	public UserPreferencesDTO getUserPreferences(Connection con, int userId)
			throws ConfigurationException {
		UserPreferencesDAO dao = DAOProvider.getUserPreferencesDAO();
		UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO();
		try {
			userPreferencesDTO = dao.fetchUserPreference(con, userId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return userPreferencesDTO;
	}

	@Override
	public List<ManageRideDTO> findCompletedManageRide(Connection con,
			ManageRideFormDTO manageRideFormDTO) throws ConfigurationException {
		List<ManageRideDTO> dtos = new ArrayList<ManageRideDTO>();
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			dtos = dao.findCompletedManageRide(con, manageRideFormDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public List<ManageRideDTO> findPendingManageRide(Connection con,
			ManageRideFormDTO manageRideFormDTO) throws ConfigurationException {
		List<ManageRideDTO> dtos = new ArrayList<ManageRideDTO>();
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			dtos = dao.findPendingManageRide(con, manageRideFormDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public List<ManageRideDTO> findPendingMatchedManageRide(Connection con,
			ManageRideFormDTO manageRideFormDTO) throws ConfigurationException {
		List<ManageRideDTO> dtos = new ArrayList<ManageRideDTO>();
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			dtos = dao.findPendingMatchedManageRide(con, manageRideFormDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public void getUpdatePoolForIsResult(Connection con)
			throws ConfigurationException {
		PoolRequestsDAO dao = DAOProvider.getPoolRequestsDAO();
		try {
			dao.UpdatePoolForIsResult(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public ApproverDTO addApprover(Connection con, ApproverDTO approberdto)
			throws ConfigurationException {
		ApproverDTO dto = null;
		ApproverDAO dao = DAOProvider.getApproverDAO();
		try {
			dto = dao.addApprover(con, approberdto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public ApproverDTO editApprover(Connection con, ApproverDTO approberdto)
			throws ConfigurationException {
		ApproverDTO dto = null;
		ApproverDAO dao = DAOProvider.getApproverDAO();
		try {
			dto = dao.editApprover(con, approberdto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<ApproverDTO> findApproverForUser(Connection con, int userId)
			throws ConfigurationException {
		List<ApproverDTO> dtos = new ArrayList<ApproverDTO>();
		ApproverDAO dao = DAOProvider.getApproverDAO();
		try {
			dtos.addAll(dao.findApproverForUser(con, userId));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public ApproverDTO findApproverById(Connection con, int approverId)
			throws ConfigurationException {
		ApproverDTO dto = new ApproverDTO();
		ApproverDAO dao = DAOProvider.getApproverDAO();
		try {
			dto = dao.findApproverById(con, approverId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<RidePreVehicle> fetchRidePreVehicleList(Connection con,
			String date, int userId) throws ConfigurationException {
		List<RidePreVehicle> list = new ArrayList<RidePreVehicle>();
		RideManagementDAO dao = DAOProvider.getRideManagementDAO();
		try {
			list.addAll(dao.fetchRidePreVehicleList(con, date, userId));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return list;
	}

	@Override
	public UserRegistrationDTO findUserByEmail(Connection con, String emailId)
			throws ConfigurationException {
		UserRegistrationDTO dto = null;
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		try {
			dto = dao.findUserByEmail(con, emailId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public SmsReplyDTO addSms(Connection con, SmsReplyDTO dto)
			throws ConfigurationException {
		SmsReplyDTO dto1 = null;
		MessageBoardDAO dao = DAOProvider.getMessageBoardDAO();
		try {
			dto1 = dao.addSms(con, dto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto1;
	}

	@Override
	public List<RideSeekerDTO> findRideSeekerDataByIds(Connection con,
			List<Integer> seekerIds) throws ConfigurationException {
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		try {
			dtos = dao.findRideSeekerDataByIds(con, seekerIds);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public void changeStatus(Connection con, int seekerId, String status)
			throws ConfigurationException {
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		try {
			dao.changeStatus(con, seekerId, status);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void changePoolRequestStatusForRideGiver(Connection con, int rideId)
			throws ConfigurationException {
		PoolRequestsDAO dao = DAOProvider.getPoolRequestsDAO();
		try {
			dao.changePoolRequestStatusForRideGiver(con, rideId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void changePoolRequestStatusForRideTaker(Connection con,
			int rideSeekerId) throws ConfigurationException {
		PoolRequestsDAO dao = DAOProvider.getPoolRequestsDAO();
		try {
			dao.changePoolRequestStatusForRideGiver(con, rideSeekerId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public UserRegistrationDTO findDriverDtoByRideId(Connection con,
			String rideId) throws ConfigurationException {
		UserRegistrationDAO dao = DAOProvider.getUserRegistrationDAO();
		UserRegistrationDTO dto = null;
		try {
			dto = dao.findDriverDtoByRideId(con, rideId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public PageStoreDTO getPageStoreByCode(Connection con, String code)
			throws ConfigurationException {
		PageStoreDTO dto = new PageStoreDTO();
		PageStoreDAO dao = DAOProvider.getPageStoreDAO();
		try {
			dto = dao.getPageStoreByCode(con, code);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public void insertLoginPage(Connection con, LoginPageDTO dto)
			throws ConfigurationException {
		LoginPageDAO dao = DAOProvider.getLoginPageDAO();
		try {
			dao.insertLoginPage(con, dto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public List<LoginPageDTO> getLoginPagesByUserId(Connection con, int userId)
			throws ConfigurationException {
		LoginPageDAO dao = DAOProvider.getLoginPageDAO();
		List<LoginPageDTO> dtos = new ArrayList<LoginPageDTO>();
		try {
			dtos.addAll(dao.getLoginPagesByUserId(con, userId));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public void updateLoginPageByUserId(Connection con, LoginPageDTO dto)
			throws ConfigurationException {
		LoginPageDAO dao = DAOProvider.getLoginPageDAO();
		try {
			dao.updateLoginPageByUserId(con, dto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void inactiveLoginPageByUserId(Connection con, int userId, int pageId)
			throws ConfigurationException {
		LoginPageDAO dao = DAOProvider.getLoginPageDAO();
		try {
			dao.inactiveLoginPageByUserId(con, userId, pageId);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public List<RideSeekerDTO> fetchRideSeekerUnApproved(Connection con)
			throws ConfigurationException {
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		try {
			dtos.addAll(dao.fetchRideSeekerUnApproved(con));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public Map<Integer, List<RideSeekerDTO>> getunAssignedTaxi(Connection con)
			throws ConfigurationException {
		Map<Integer, List<RideSeekerDTO>> map = new HashMap<Integer, List<RideSeekerDTO>>();
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			map = dao.getunAssignedTaxi(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return map;
	}

	@Override
	public Map<Integer, List<RideSeekerDTO>> getunAssignedTaxiForOwner(
			Connection con) throws ConfigurationException {
		Map<Integer, List<RideSeekerDTO>> map = new HashMap<Integer, List<RideSeekerDTO>>();
		MatchTripDAO dao = DAOProvider.getMatchTripDAO();
		try {
			map = dao.getunAssignedTaxiForOwner(con);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return map;
	}

	@Override
	public List<FrequencyDTO> fetchFrequencyListForRideSeeker(Connection con,
			String rideSeekerID) throws ConfigurationException {
		List<FrequencyDTO> dtos = new ArrayList<FrequencyDTO>();
		FrequencyDAO dao = DAOProvider.getFrequencyDAO();
		try {
			dtos.addAll(dao.fetchFrequencyListForRideSeeker(con, rideSeekerID));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public List<FrequencyDTO> fetchFrequencyListForRideManager(Connection con,
			String rideID) throws ConfigurationException {
		List<FrequencyDTO> dtos = new ArrayList<FrequencyDTO>();
		FrequencyDAO dao = DAOProvider.getFrequencyDAO();
		try {
			dtos.addAll(dao.fetchFrequencyListForRideManager(con, rideID));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public List<RideSeekerDTO> fetchRecurringRideList(Connection con)
			throws ConfigurationException {
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		try {
			dtos.addAll(dao.fetchRecurringRideList(con));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public void addSubSeekers(Connection con, RideSeekerDTO ride)
			throws ConfigurationException {
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		try {
			dao.addSubSeekers(con, ride);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public RideSeekerDTO cancelSubSeekers(Connection con,
			RideSeekerDTO rideSeekerDTO) throws ConfigurationException {
		RideSeekerDTO dto = null;
		RideSeekerDAO dao = DAOProvider.getRideSeekerDAO();
		try {
			dto = dao.cancelSubSeekers(con, rideSeekerDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public void updateRideIdDropTake(Connection con,
			RideManagementDTO rideToDrop, RideManagementDTO rideToTake)
			throws ConfigurationException {
		PoolRequestsDAO dao = DAOProvider.getPoolRequestsDAO();
		try {
			dao.updateRideIdDropTake(con, rideToDrop, rideToTake);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public boolean testSingleCircleMember(Connection con,
			CircleMemberDTO circleMemberDTO, String id, String ctype,
			String ttype) throws ConfigurationException {
		CircleMemberDAO dao = DAOProvider.getCircleMemberDAO();
		boolean test = false;
		try {
			test = dao.testSingleCircleMember(con, circleMemberDTO, id, ctype,
					ttype);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return test;
	}

	@Override
	public List<PaymentDTO> fetchPaymentList(Connection con, int id)
			throws ConfigurationException {
		PaymentDAO dao = DAOProvider.getPaymentDAO();
		List<PaymentDTO> dtos = new ArrayList<PaymentDTO>();
		try {
			dtos.addAll(dao.fetchPaymentList(con, id));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public List<PaymentDTO> fetchActivePaymentListForCircle(Connection con,
			Integer[] circleIds) throws ConfigurationException {
		PaymentDAO dao = DAOProvider.getPaymentDAO();
		List<PaymentDTO> dtos = new ArrayList<PaymentDTO>();
		try {
			dtos.addAll(dao.fetchActivePaymentListForCircle(con, circleIds));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public List<PaymentPlanDTO> fetchPaymentPlanForLoginUser(Connection con,
			String userID) throws ConfigurationException {
		PaymentPlanDAO dao = DAOProvider.getPaymentPlanDAO();
		List<PaymentPlanDTO> dtos = new ArrayList<PaymentPlanDTO>();
		try {
			dtos.addAll(dao.fetchAllPlanListForUser(con, userID));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public PaymentTxnsDTO paymentTxnInsert(Connection con,
			PaymentTxnsDTO paymentTxnsDTO) throws ConfigurationException {
		PaymentTxnsDAO dao = DAOProvider.getPaymentTxnsDAO();
		PaymentTxnsDTO dto = new PaymentTxnsDTO();
		try {
			dto = dao.add(con, paymentTxnsDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public PaymentTxnsDTO paymentTxnCancel(Connection con,
			PaymentTxnsDTO paymentTxnsDTO) throws ConfigurationException {
		PaymentTxnsDAO dao = DAOProvider.getPaymentTxnsDAO();
		PaymentTxnsDTO dto = new PaymentTxnsDTO();
		try {
			dto = dao.paymentTxnCancel(con, paymentTxnsDTO);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dto;
	}

	@Override
	public List<PaymentTxnsDTO> fetchAllTxnByDate(Connection con, Date date)
			throws ConfigurationException {
		List<PaymentTxnsDTO> dtos = new ArrayList<PaymentTxnsDTO>();
		try {
			dtos.addAll(DAOProvider.getPaymentTxnsDAO().fetchAllByDate(con,
					date));
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
		return dtos;
	}

	@Override
	public void paymentTxnHoponToUser(Connection con,
			HoponAccountDTO hoponAccountDto, UserRegistrationDTO userDto)
			throws ConfigurationException {
		try {
			DAOProvider.getPaymentTxnsDAO().paymentTxnHoponToUser(con,
					hoponAccountDto, userDto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}

	@Override
	public void paymentTxnUserToHopon(Connection con,
			HoponAccountDTO hoponAccountDto, UserRegistrationDTO userDto)
			throws ConfigurationException {
		try {
			DAOProvider.getPaymentTxnsDAO().paymentTxnUserToHopon(con,
					hoponAccountDto, userDto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ "Problem in db operation. " + e.getMessage());
			throw new ConfigurationException(
					"Exception in retriving providers", e);
		}
	}


	@Override
	public void contactUs(Connection con, ContactusDTO dto)
			throws ConfigurationException {
		System.out.println("contactus dto is:"+dto);
		ContactUsDAO dao = DAOProvider.getContactUsDAO();
		System.out.println("from the tripimplementation:"+dao);
		try {
			dao.insertContactInfo(con, dto);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClass() + "->"
							+ e.getStackTrace()[0].getMethodName() + "():"
							+ e.getStackTrace()[0].getLineNumber() + " :; "
							+ "Problem in db operation." + e.getMessage());
			throw new ConfigurationException("Exception in retriving provider",
					e);
		}

	}
}
