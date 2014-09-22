/*
 * This class call through our Action class hear we call interface methods 
 * and this class also use for some dropdown values 
 * in this class only we class the connection and passing it to other classes 
 * 
 * @author Anand Kumar
 */

package com.hopon.utils;

import static com.hopon.utils.ServiceProvider.*;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.hopon.utils.DataSource;
import com.hopon.dto.*;
import com.jolbox.bonecp.BoneCP;

public class ListOfValuesManager {
	static Logger log = Logger.getLogger(ListOfValuesManager.class.getName());
	static BoneCP connectionPool = null;

	/*
	 * method used for showing adult no. option called by action class
	 */
	private static MessageBoardDTO userMessageDTO = new MessageBoardDTO();

	public static List<SelectItem> getCompanySectorOption(
			List<SelectItem> options) {
		options = new ArrayList<SelectItem>();
		options.add(new SelectItem("Software", "Software"));
		options.add(new SelectItem("Hardware", "Hardware"));
		options.add(new SelectItem("Marketing", "Marketing"));
		options.add(new SelectItem("Other", "Other"));

		return options;
	}

	public static String getcurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat(
				ApplicationUtil.datePattern11);
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		return date;
	}

	public static String getcurrentDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		return date;
	}

	public static Date getCurrentTimeStampDate() {
		Date date = new Date();
		return date;
	}

	public static UserRegistrationDTO findDriverDtoByRideId(String rideId,
			Connection con) throws ConfigurationException {
		UserRegistrationDTO dto = null;
		if (con == null) {
			con = getLocalConnection();
			try {
				dto = getTripService().findDriverDtoByRideId(con, rideId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				dto = getTripService().findDriverDtoByRideId(con, rideId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return dto;
	}

	public static UserRegistrationDTO findUserDtoByVehicleId(int vehicleId,
			Connection con) throws ConfigurationException {
		UserRegistrationDTO dto = null;
		if (con == null) {
			con = getLocalConnection();
			try {
				dto = getTripService().findUserDtoByVehicleId(con, vehicleId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				dto = getTripService().findUserDtoByVehicleId(con, vehicleId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return dto;
	}

	public static VehicleMasterDTO getVehicleDtoByRideId(String rideID,
			Connection con) throws ConfigurationException {
		VehicleMasterDTO dto = null;
		if (con == null) {
			con = getLocalConnection();
			try {
				dto = getTripService().getVehicleDtoByRideId(con,
						Integer.parseInt(rideID));
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				dto = getTripService().getVehicleDtoByRideId(con,
						Integer.parseInt(rideID));
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return dto;
	}

	public static void makeCircleOwnerForUser(
			UserRegistrationDTO userRegistrationDto, String senderId,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().makeCircleOwnerForUser(con,
						userRegistrationDto, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().makeCircleOwnerForUser(con,
						userRegistrationDto, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}

	}

	public static void makeRideSeekerCancelForUser(
			UserRegistrationDTO userRegistrationDto, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().makeRideSeekerCancelForUser(con,
						userRegistrationDto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().makeRideSeekerCancelForUser(con,
						userRegistrationDto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static void makeRideCancelForUser(
			UserRegistrationDTO userRegistrationDto, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService()
						.makeRideCancelForUser(con, userRegistrationDto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService()
						.makeRideCancelForUser(con, userRegistrationDto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static RideManagementDTO getRideSeekerEntery(String category,
			RideManagementDTO rideSeekerDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				rideSeekerDTO = getTripService().loadRideSeeker(con, category,
						rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				rideSeekerDTO = getTripService().loadRideSeeker(con, category,
						rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return rideSeekerDTO;
	}

	public static RideManagementDTO getRideEntery(String category,
			RideManagementDTO rideManagementDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				rideManagementDTO = getTripService().loadRide(con, category,
						rideManagementDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				rideManagementDTO = getTripService().loadRide(con, category,
						rideManagementDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return rideManagementDTO;
	}

	public static FrequencyDTO getFrequencyEntery(String category,
			FrequencyDTO frequencyDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				frequencyDTO = getTripService().loadFrequency(con, category,
						frequencyDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				frequencyDTO = getTripService().loadFrequency(con, category,
						frequencyDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return frequencyDTO;
	}

	public static FavoritePlacesDTO getfavoritePlaces(
			FavoritePlacesDTO favoritePlacesDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				favoritePlacesDTO = getTripService().loadFavoritePlaces(con,
						favoritePlacesDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				favoritePlacesDTO = getTripService().loadFavoritePlaces(con,
						favoritePlacesDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}

		return favoritePlacesDTO;
	}

	public static CircleDTO getregisterCircle(CircleDTO circleDTO,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				circleDTO = getTripService().loadCircle(con, circleDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				circleDTO = getTripService().loadCircle(con, circleDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}

		return circleDTO;
	}

	public static CircleMemberDTO getJoinCircle(
			CircleMemberDTO circleMemberDTO, String senderId, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				circleMemberDTO = getTripService().loadCirclemember(con,
						circleMemberDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				circleMemberDTO = getTripService().loadCirclemember(con,
						circleMemberDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return circleMemberDTO;
	}

	public static CircleOwnerDTO getregisterCircleOwner(
			CircleOwnerDTO circleOwnerDTO, String senderId, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				circleOwnerDTO = getTripService().loadCircleOwner(con,
						circleOwnerDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				circleOwnerDTO = getTripService().loadCircleOwner(con,
						circleOwnerDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return circleOwnerDTO;

	}

	public static UserRegistrationDTO getUpdateUser(
			UserRegistrationDTO userRegistrationDTO, String senderId,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				userRegistrationDTO = getTripService().loadupdateUser(con,
						userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				userRegistrationDTO = getTripService().loadupdateUser(con,
						userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return userRegistrationDTO;
	}

	public static UserRegistrationDTO getUpdateUserPassword(
			UserRegistrationDTO userRegistrationDTO, String senderId,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				userRegistrationDTO = getTripService().loadupdateUserPassword(
						con, userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				userRegistrationDTO = getTripService().loadupdateUser(con,
						userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return userRegistrationDTO;
	}

	public static UserPreferencesDTO getUserPreferencesEdit(
			UserPreferencesDTO userPreferencesDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			UserPreferencesDTO dto = null;
			try {
				dto = getTripService().getUserPreferencesEdit(con,
						userPreferencesDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
			return dto;
		} else {
			UserPreferencesDTO dto = null;
			try {
				dto = getTripService().getUserPreferencesEdit(con,
						userPreferencesDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
			return dto;
		}

	}

	public static RideSeekerDTO getCancleRideSeeker(
			RideSeekerDTO rideSeekerDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				rideSeekerDTO = getTripService().loadCancleRideSeeker(con,
						rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				rideSeekerDTO = getTripService().loadCancleRideSeeker(con,
						rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return rideSeekerDTO;
	}

	public static RideSeekerDTO changeField(RideSeekerDTO rideSeekerDTO,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				rideSeekerDTO = getTripService()
						.changeField(con, rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				rideSeekerDTO = getTripService()
						.changeField(con, rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return rideSeekerDTO;
	}

	public static MessageBoardDTO getInsertedMessage(MessageBoardDTO messagedto) {
		Connection con = getLocalConnection();
		MessageBoardDTO dto = null;
		try {
			dto = getTripService().loadInsertedMessage(con, messagedto);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static Connection getLocalConnection() {

		Connection con = null;
		try {
			con = DataSource.getInstance().getConnection();
		} catch (SQLException e) {
		} catch (IOException e) {
		} catch (PropertyVetoException e) {
		}
		return con;
	}

	public static Connection getBroadConnection() {
		Connection con = null;
		try {
			con = DataSource.getInstance().getConnection();
		} catch (SQLException e) {
		} catch (IOException e) {
		} catch (PropertyVetoException e) {
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		}
		return con;
	}

	public static void releaseConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			}
		}
	}

	public static UserRegistrationDTO getUserRegistration(String category,
			UserRegistrationDTO userRegistrationDTO, String senderId,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				userRegistrationDTO = getTripService().loadUserRegistration(
						con, category, userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
			return userRegistrationDTO;
		} else {
			try {
				userRegistrationDTO = getTripService().loadUserRegistration(
						con, category, userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
			return userRegistrationDTO;
		}
	}

	public static UserPreferencesDTO getUserPreferencesSave(
			UserPreferencesDTO userPreferencesDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			UserPreferencesDTO dto = null;
			try {
				dto = getTripService().getUserPreferencesSave(con,
						userPreferencesDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
			return dto;
		} else {
			UserPreferencesDTO dto = null;
			try {
				dto = getTripService().getUserPreferencesSave(con,
						userPreferencesDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
			return dto;
		}
	}

	public static CompanyRegisterDTO getCompanyRegistration(String category,
			CompanyRegisterDTO companyRegisterDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				companyRegisterDTO = getTripService().loadCompanyRegistration(
						con, category, companyRegisterDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
			return companyRegisterDTO;
		} else {
			try {
				companyRegisterDTO = getTripService().loadCompanyRegistration(
						con, category, companyRegisterDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
			return companyRegisterDTO;
		}
	}

	public static UserRegistrationDTO getCloseUserAcount(
			UserRegistrationDTO userRegistrationDTO, String senderId,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				userRegistrationDTO = getTripService().closeUserAcount(con,
						userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				userRegistrationDTO = getTripService().closeUserAcount(con,
						userRegistrationDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return userRegistrationDTO;
	}

	public static void makeCircleInactiveForUser(
			UserRegistrationDTO userRegistrationDto, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().makeCircleInactiveForUser(con,
						userRegistrationDto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().makeCircleInactiveForUser(con,
						userRegistrationDto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static void makeCircleMemberInactiveForUser(
			UserRegistrationDTO userRegistrationDto, String senderId,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().makeCircleMemberInactiveForUser(con,
						userRegistrationDto, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().makeCircleMemberInactiveForUser(con,
						userRegistrationDto, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static CircleOwnerDTO updateRegisterCircleOwner(
			CircleOwnerDTO circleOwnerDTO, String senderId, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				circleOwnerDTO = getTripService().updateCircleOwner(con,
						circleOwnerDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				circleOwnerDTO = getTripService().updateCircleOwner(con,
						circleOwnerDTO, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}

		return circleOwnerDTO;
	}

	public static CircleMemberDTO getConfirmOrDeclineUser(
			CircleMemberDTO userdto, String senderId, Connection con)
			throws ConfigurationException {

		if (con == null) {
			con = getLocalConnection();
			try {
				userdto = getTripService().loadConfirmOrDeclineUser(con,
						userdto, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				userdto = getTripService().loadConfirmOrDeclineUser(con,
						userdto, senderId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return userdto;
	}

	public static RideManagementDTO getCancleRide(
			RideManagementDTO rideManagementDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				rideManagementDTO = getTripService().loadCancleRide(con,
						rideManagementDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				rideManagementDTO = getTripService().loadCancleRide(con,
						rideManagementDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return rideManagementDTO;
	}

	public static void changePoolRequestStatusForRideGiver(Connection con,
			int rideId) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().changePoolRequestStatusForRideGiver(con,
						rideId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().changePoolRequestStatusForRideGiver(con,
						rideId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static void changePoolRequestStatusForRideTaker(Connection con,
			int rideSeekerId) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().changePoolRequestStatusForRideTaker(con,
						rideSeekerId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().changePoolRequestStatusForRideTaker(con,
						rideSeekerId);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static PoolRequestsDTO getInsertInPool(
			PoolRequestsDTO poolRequestsDTO, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				poolRequestsDTO = getTripService().loadInsertinPool(con,
						poolRequestsDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				poolRequestsDTO = getTripService().loadInsertinPool(con,
						poolRequestsDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return poolRequestsDTO;
	}

	public static PaymentTxnsDTO paymentTxnInsert(PaymentTxnsDTO dto,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				dto = getTripService().paymentTxnInsert(con, dto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				dto = getTripService().paymentTxnInsert(con, dto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return dto;
	}

	public static PaymentTxnsDTO paymentTxnCancel(PaymentTxnsDTO dto,
			Connection con) throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				dto = getTripService().paymentTxnCancel(con, dto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				dto = getTripService().paymentTxnCancel(con, dto);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return dto;
	}

	public static List<SelectItem> getCompanyOptions(List<SelectItem> options) {
		Connection con = getLocalConnection();
		options = new ArrayList<SelectItem>();
		options.add(new SelectItem("new", "New"));
		List<CompanyRegisterDTO> allCompany;
		try {
			allCompany = getTripService().allCompanyList(con);
			for (int i = 0; i < allCompany.size(); i++) {
				options.add(new SelectItem(allCompany.get(i).getCompanyName(),
						allCompany.get(i).getCompanyName()));
			}
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return options;
	}

	public static List<SelectItem> getAllCircleListOptions(
			List<SelectItem> options) {

		options = new ArrayList<SelectItem>();
		options.add(new SelectItem("Default", "Default"));
		List<CircleDTO> allCircle = getallRegisteredCircleListed();
		for (int i = 0; i < allCircle.size(); i++) {
			options.add(new SelectItem(allCircle.get(i).getName(), allCircle
					.get(i).getName()));
		}
		return options;
	}

	public static List<CompanyRegisterDTO> getCompanyList() {
		List<CompanyRegisterDTO> allCompany = new ArrayList<CompanyRegisterDTO>();
		Connection con = getLocalConnection();
		try {
			allCompany = getTripService().allCompanyList(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return allCompany;

	}

	public static CompanyRegisterDTO getCompanyUpdateByUserId(
			CompanyRegisterDTO companyRegisterDTO) {
		Connection con = getLocalConnection();
		try {
			companyRegisterDTO = getTripService().getCompanyUpdateByUserId(con,
					companyRegisterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return companyRegisterDTO;
	}

	public static UserRegistrationDTO getValidateUser(
			UserRegistrationDTO userRegistrationDTO) {
		System.out.println("user registration :"+userRegistrationDTO.getEmail_id());
		Connection con = getLocalConnection();
		// Connection con= ConnectionClass.dataConnect();
		try {
			userRegistrationDTO = getTripService().validateUser(con,
					userRegistrationDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return userRegistrationDTO;
	}

	public static List<CircleDTO> getallCircleListed(String userid) {
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		Connection con = getLocalConnection();
		// Connection con= ConnectionClass.dataConnect();
		try {
			circleList = getTripService().getallCircles(con, userid);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}

		return circleList;

	}

	public static List<CircleDTO> getallMemberCircleListed(String userid) {
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		Connection con = getLocalConnection();
		// Connection con= ConnectionClass.dataConnect();
		try {
			circleList = getTripService().getallMemberCircles(con, userid);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}

		return circleList;

	}

	public static List<CircleDTO> getallRegisteredCircleListed() {
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		Connection con = getLocalConnection();
		// Connection con= ConnectionClass.dataConnect();
		try {
			circleList = getTripService().getallRegisteredCircles(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return circleList;

	}

	public static List<CircleDTO> getallRegisteredTaxiCircleListed(String prefix) {
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		Connection con = getLocalConnection();
		// Connection con= ConnectionClass.dataConnect();
		try {
			circleList = getTripService().getallRegisteredTaxiCircles(con,
					prefix);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return circleList;

	}

	public static List<CircleDTO> getallRegisteredNonTaxiCircleListed(
			String prefix) {
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		Connection con = getLocalConnection();
		// Connection con= ConnectionClass.dataConnect();
		try {
			circleList = getTripService().getallRegisteredNonTaxiCircles(con,
					prefix);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return circleList;

	}

	public static boolean checkRideSeekerDuplicacy(
			RideManagementDTO rideSeekerDTO) {
		Connection con = getLocalConnection();
		boolean c = false;
		try {
			c = getTripService().checkRideSeekerDuplicacy(con, rideSeekerDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return c;
	}

	public static boolean checkRideDuplicacy(RideManagementDTO rideSeekerDTO) {
		Connection con = getLocalConnection();
		boolean x = false;
		try {
			x = getTripService().checkDuplicacy(con, rideSeekerDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static List<RideManagementDTO> getRideManagementList(String userID) {
		Connection con = getLocalConnection();
		List<RideManagementDTO> rideList = new ArrayList<RideManagementDTO>();
		try {
			rideList = getTripService().loadAllRidemanagement(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return rideList;
	}

	public static VehicleMasterDTO getVehicleMaster(String category,
			VehicleMasterDTO vehicleMasterDTO) throws ConfigurationException {
		Connection con = getLocalConnection();
		try {
			vehicleMasterDTO = getTripService().loadVehicle(con, category,
					vehicleMasterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			throw new ConfigurationException();
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return vehicleMasterDTO;
	}

	public static List<RideSeekerDTO> getAllRideSeekerList(String category) {

		List<RideSeekerDTO> rideSeekerList = new ArrayList<RideSeekerDTO>();
		Connection con = getLocalConnection();
		try {
			rideSeekerList = getTripService().loadAllRideSeeker(con, category);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return rideSeekerList;

	}

	public static List<PoolRequestsDTO> getAllPoolRequest(String userid) {

		List<PoolRequestsDTO> poolList = new ArrayList<PoolRequestsDTO>();
		Connection con = getLocalConnection();
		try {
			poolList = getTripService().loadAllPoolRequest(con, userid);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return poolList;
	}

	public static List<CityDTO> getCityList(String finalValue) {

		List<CityDTO> cityList = new ArrayList<CityDTO>();
		Connection con = getLocalConnection();
		try {
			cityList = getTripService().loadAllCity(con, finalValue);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return cityList;
	}

	public static CityDTO getCity(String cityName, String stateName) {
		CityDTO dto = new CityDTO();
		Connection con = getLocalConnection();
		try {
			dto = getTripService().loadCity(con, cityName, stateName);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<FavoritePlacesDTO> getAllPlaces(String userName) {

		List<FavoritePlacesDTO> placesList = new ArrayList<FavoritePlacesDTO>();
		Connection con = getLocalConnection();
		try {
			placesList = getTripService().loadAllPlaces(con, userName);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return placesList;
	}

	public static List<CircleOwnerManagerDTO> getAllCircleForLoginUser(
			String userID) {
		List<CircleOwnerManagerDTO> allCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();
		Connection con = getLocalConnection();
		// Connection con = ConnectionClass.dataConnect();
		try {
			allCircleForLoginUserList = getTripService()
					.loadallCircleForLoginUser(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return allCircleForLoginUserList;
	}

	public static List<CircleOwnerManagerDTO> getAllCircleMemberForLoginUser(
			String userID) {
		List<CircleOwnerManagerDTO> allCircleForLoginUserList = new ArrayList<CircleOwnerManagerDTO>();
		Connection con = getLocalConnection();
		// Connection con = ConnectionClass.dataConnect();
		try {
			allCircleForLoginUserList = getTripService()
					.loadallCircleMemberForLoginUser(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return allCircleForLoginUserList;
	}

	public static List<CircleOwnerManagerDTO> getAllPendingCircleMemberForLoginUser(
			String userID) {
		List<CircleOwnerManagerDTO> pendingUser = new ArrayList<CircleOwnerManagerDTO>();
		Connection con = getLocalConnection();
		// Connection con = ConnectionClass.dataConnect();
		try {
			pendingUser = getTripService()
					.loadallPendingCircleMemberForLoginUser(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return pendingUser;
	}

	public static List<CircleOwnerManagerDTO> getAllMessageForLoginUser(
			String userID) {
		List<CircleOwnerManagerDTO> allUserList = new ArrayList<CircleOwnerManagerDTO>();
		Connection con = getLocalConnection();
		// Connection con = ConnectionClass.dataConnect();
		try {
			allUserList = getTripService().loadallLoginUserMessage(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return allUserList;
	}

	public static List<CircleDTO> getAllCircleMembershipInvitation(String userID) {
		List<CircleDTO> allCircleInvitationUserList = new ArrayList<CircleDTO>();
		Connection con = getLocalConnection();
		// Connection con = ConnectionClass.dataConnect();
		try {
			allCircleInvitationUserList = getTripService()
					.loadallCircleInvitationUserList(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return allCircleInvitationUserList;
	}

	public static List<MatchedTripDTO> getAllMatchedListByCondition(
			String startPoint, String endPoint, String rideDate, int circleId) {
		List<MatchedTripDTO> matchedList = new ArrayList<MatchedTripDTO>();
		Connection con = getLocalConnection();
		try {
			matchedList = getTripService().loasAllMatchTripByCondition(con,
					startPoint, endPoint, rideDate, circleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return matchedList;
	}

	public static List<MatchedTripDTO> findMatchTripByGroupId(
			List<String> groupId) {
		List<MatchedTripDTO> matchedList = new ArrayList<MatchedTripDTO>();
		Connection con = getLocalConnection();
		try {
			matchedList = getTripService().findMatchTripByGroupId(con, groupId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return matchedList;
	}

	public static int calculateSingleRide(List<Integer> seekerId) {
		int rideId = 0;
		Connection con = getLocalConnection();
		try {
			rideId = getTripService().calculateSingleRide(con, seekerId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return rideId;
	}

	public static List<VehicleMasterDTO> getAllVehicleList(String userID) {
		List<VehicleMasterDTO> vehicleList = new ArrayList<VehicleMasterDTO>();
		Connection con = getLocalConnection();
		try {
			vehicleList = getTripService().loadAllVehicle(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return vehicleList;
	}

	public static PoolRequestsDTO getRateAndWriteNotes(PoolRequestsDTO pooldto) {
		Connection con = getLocalConnection();
		try {
			pooldto = getTripService().loadRateAndWriteNotes(con, pooldto);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return pooldto;
	}

	public static UserRegistrationDTO getAverageRatingForUser(
			UserRegistrationDTO dto) {
		Connection con = getLocalConnection();
		try {
			dto = getTripService().loadAverageRatingForUser(con, dto);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<PoolRequestsDTO> getAllPoolRequestSeeker(String userid) {

		List<PoolRequestsDTO> poolList = new ArrayList<PoolRequestsDTO>();
		Connection con = getLocalConnection();
		try {
			poolList = getTripService().loadAllPoolRequestSeeker(con, userid);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return poolList;
	}

	public static CircleDTO getDeclineCircle(CircleDTO circleDTO,
			UserRegistrationDTO user) {
		Connection con = getLocalConnection();
		try {
			circleDTO = getTripService().loadDeclineCircle(con, circleDTO,
					user.getId());
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}

		userMessageDTO = new MessageBoardDTO();

		userMessageDTO.setMessage("User " + user.getFirst_name()
				+ " leave circle '" + circleDTO.getOwnerName() + "'.");
		userMessageDTO.setToMember(circleDTO.getCircleOwner_Member_Id_P());
		userMessageDTO.setCreatedBy(Integer.parseInt(user.getId()));
		userMessageDTO.setEmailSubject("Leave Circle");
		userMessageDTO.setMessageChannel("E");

		try {
			userMessageDTO = ListOfValuesManager
					.getInsertedMessage(userMessageDTO);
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return circleDTO;
	}

	public static CircleDTO getDeactivateCircle(CircleDTO circleDTO) {
		Connection con = getLocalConnection();
		try {
			circleDTO = getTripService().deactivateCircle(con, circleDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return circleDTO;
	}

	public static List<CircleDTO> getCircleByName(String circleName,
			String userId) {
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();
		Connection con = getLocalConnection();
		try {
			circleList = getTripService().loadCircleByName(con, circleName,
					userId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return circleList;
	}

	public static List<CompanyRegisterDTO> getListofCompanyForLoginUser(
			String userID) {
		List<CompanyRegisterDTO> companyList = new ArrayList<CompanyRegisterDTO>();
		Connection con = getLocalConnection();
		try {
			companyList = getTripService().loadCompanyForLoginUser(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return companyList;
	}

	public static List<UserRegistrationDTO> getAllUserList(String prefix,
			int limit) {
		List<UserRegistrationDTO> alluserList = new ArrayList<UserRegistrationDTO>();
		Connection con = getLocalConnection();
		try {
			alluserList = getTripService().loadAllUser(con, prefix, limit);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return alluserList;
	}

	public static CompanyRegisterDTO getUpdateCompany(
			CompanyRegisterDTO companyRegisterDTO) {
		Connection con = getLocalConnection();
		try {
			companyRegisterDTO = getTripService().loadUpdateCompany(con,
					companyRegisterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return companyRegisterDTO;

	}

	public static VehicleMasterDTO getUpdateSeat(
			VehicleMasterDTO vehicleMasterDTO) {
		Connection con = getLocalConnection();
		try {
			vehicleMasterDTO = getTripService().loadUpdateSeat(con,
					vehicleMasterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return vehicleMasterDTO;
	}

	public static MatchedTripDTO getCount(String vehicleID, String startTime,
			String company) {
		Connection con = getLocalConnection();
		MatchedTripDTO list = null;
		try {
			list = getTripService().loasAllMatchTripByCondition1(con,
					vehicleID, startTime, company);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return list;

	}

	public static List<RideManagementDTO> getMethodForPopupOne(
			String vehicleID, String startTime) {
		Connection con = getLocalConnection();
		List<RideManagementDTO> list = new ArrayList<RideManagementDTO>();
		try {
			list = getTripService().loadMethodForPopupOne(con, vehicleID,
					startTime);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return list;
	}

	public static List<RideSeekerDTO> getAllRideSeekerForAGivenRide(
			String rideID) {
		List<RideSeekerDTO> seekerList = new ArrayList<RideSeekerDTO>();
		Connection con = getLocalConnection();
		try {
			seekerList = getTripService().loadAllSeekerForRideID(con, rideID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return seekerList;
	}

	public static VehicleMasterDTO getUpdateVehicle(
			VehicleMasterDTO vehicleMasterDTO) {
		Connection con = getLocalConnection();
		try {
			vehicleMasterDTO = getTripService().loadupdateVehicle(con,
					vehicleMasterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return vehicleMasterDTO;
	}

	public static VehicleMasterDTO getUpdateVehicleDefault(
			VehicleMasterDTO vehicleMasterDTO) {
		Connection con = getLocalConnection();
		try {
			vehicleMasterDTO = getTripService().loadupdateDefaultVehicle(con,
					vehicleMasterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return vehicleMasterDTO;
	}

	public static VehicleMasterDTO getInActivateVehicle(
			VehicleMasterDTO vehicleMasterDTO) {
		Connection con = getLocalConnection();
		try {
			vehicleMasterDTO = getTripService().loadInActivateVehicle(con,
					vehicleMasterDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return vehicleMasterDTO;
	}

	public static List<MessageBoardDTO> getAllUnreadMessage(String userid) {
		Connection con = getLocalConnection();
		List<MessageBoardDTO> dto = new ArrayList<MessageBoardDTO>();
		try {
			dto = getTripService().loadUnreadMessage(con, userid, "U");
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		// Here we are using 10 to show 10 message.
		return dto;
	}

	public static List<MessageBoardDTO> getAllReadMessage(String userid) {
		Connection con = getLocalConnection();
		List<MessageBoardDTO> dto = new ArrayList<MessageBoardDTO>();
		try {
			dto = getTripService().loadUnreadMessage(con, userid, "R");
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
		// Here we are using 10 to show 10 message.
	}

	public static List<MessageBoardDTO> getAllDeletedMessage(String userid) {
		Connection con = getLocalConnection();
		List<MessageBoardDTO> dto = new ArrayList<MessageBoardDTO>();
		try {
			dto = getTripService().loadUnreadMessage(con, userid, "D");
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
		// Here we are using 10 to show 10 message.
	}

	public static List<MessageBoardDTO> getAllMessage(String userid) {
		Connection con = getLocalConnection();
		List<MessageBoardDTO> dto = new ArrayList<MessageBoardDTO>();
		try {
			dto = getTripService().loadUnreadMessage(con, userid, null);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
		// Here we are using 10 to show 10 message.
	}

	public static List<MessageBoardDTO> getAllEmailSendingMessage() {
		Connection con = getLocalConnection();
		List<MessageBoardDTO> dto = new ArrayList<MessageBoardDTO>();
		try {
			dto = getTripService().loadEmailSendingMessage(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
		// Here we are using 10 to show 10 message.
	}

	public static List<MessageBoardDTO> getAllSmsSendingMessage() {
		Connection con = getLocalConnection();
		List<MessageBoardDTO> dto = new ArrayList<MessageBoardDTO>();
		try {
			dto = getTripService().loadSmsSendingMessage(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
		// Here we are using 10 to show 10 message.
	}

	public static List<MessageBoardDTO> getAllPopupMessage(int userId) {
		Connection con = getLocalConnection();
		List<MessageBoardDTO> dto = new ArrayList<MessageBoardDTO>();
		try {
			dto = getTripService().loadPopupMessage(con, userId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
		// Here we are using 10 to show 10 message.
	}

	public static void makeEmailStatusSent(int[] messageId) {
		Connection con = getLocalConnection();
		try {
			getTripService().loadMakeEmailStatusSent(con, messageId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static void makeBoardMessageRead(int[] messageId) {
		Connection con = getLocalConnection();
		try {
			getTripService().updateMessageStatusRead(con, messageId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static void makePopupMessageRead(int[] messageId) {
		Connection con = getLocalConnection();
		try {
			getTripService().makePopupMessageRead(con, messageId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static void makeBoardMessageDelete(int[] messageId) {
		Connection con = getLocalConnection();
		try {
			getTripService().updateMessageStatusDelete(con, messageId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static boolean testVehicleRegistrationNumber(String registrationNo) {
		Connection con = getLocalConnection();
		boolean x = false;
		try {
			x = getTripService().testVehicleRegistrationNumber(con,
					registrationNo);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static boolean testEmail(String email) {
		Connection con = getLocalConnection();
		boolean x = false;
		try {
			x = getTripService().testEmail(con, email);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static String testEmailAllStatus(String email) {
		Connection con = getLocalConnection();
		String x = "N";
		try {
			x = getTripService().testEmailAllStatus(con, email);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static RideManagementDTO getRideManagerPopupData(String rideID) {
		Connection con = getLocalConnection();
		RideManagementDTO dto = null;
		try {
			dto = getTripService().getRideManagerPopupData(con, rideID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static RideManagementDTO getRideManagerPopupDataDirect(String rideID) {
		Connection con = getLocalConnection();
		RideManagementDTO dto = null;
		try {
			dto = getTripService().getRideManagerPopupDataDirect(con, rideID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static VehicleMasterDTO getVehicleDtoById(String vehicleID) {
		Connection con = getLocalConnection();
		VehicleMasterDTO dto = null;
		try {
			dto = getTripService().getVehicleDtoById(con, vehicleID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<CircleDTO> getAllAffiliatedTaxiCircle(String prefix) {
		Connection con = getLocalConnection();
		List<CircleDTO> x = new ArrayList<CircleDTO>();
		try {
			x = getTripService().getAllAffiliatedTaxiCircle(con, prefix);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static void makeTaxiCircleAffiliated(int parentCircleId,
			int childCircleId) {
		Connection con = getLocalConnection();
		try {
			getTripService().getMakeTaxiCircleAffiliated(con, parentCircleId,
					childCircleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static List<CircleAffiliationsDTO> getAllAffiliatedCircle(
			String circleId) {
		Connection con = getLocalConnection();
		List<CircleAffiliationsDTO> dto = new ArrayList<CircleAffiliationsDTO>();
		try {
			dto = getTripService().getAllAffiliatedCircle(con, circleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<CircleAffiliationsDTO> getAllPendingCircle(
			String circleId) {
		Connection con = getLocalConnection();
		List<CircleAffiliationsDTO> x = new ArrayList<CircleAffiliationsDTO>();
		try {
			x = getTripService().getAllPendingCircle(con, circleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static void makeTaxiCircleAffiliatedActive(int parentCircleId,
			int childCircleId) {
		Connection con = getLocalConnection();
		try {
			getTripService().getMakeTaxiCircleAffiliatedActive(con,
					parentCircleId, childCircleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static void makeTaxiCircleAffiliatedInactive(int parentCircleId,
			int childCircleId) {
		Connection con = getLocalConnection();
		try {
			getTripService().getMakeTaxiCircleAffiliatedInactive(con,
					parentCircleId, childCircleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static CircleDTO getCircleDtoByCircleId(int circleId) {
		Connection con = getLocalConnection();
		CircleDTO dto = null;
		try {
			dto = getTripService().getCircleDtoByCircleId(con, circleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<CircleAffiliationsDTO> getAllPendingAffiliatedCircle(
			String userId) {
		Connection con = getLocalConnection();
		List<CircleAffiliationsDTO> dto = new ArrayList<CircleAffiliationsDTO>();
		try {
			dto = getTripService().getAllPendingAffiliatedCircle(con,
					Integer.parseInt(userId));
		} catch (NumberFormatException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<CircleDTO> getAffiliateCircleForTaxiUser(int userId) {
		Connection con = getLocalConnection();
		List<CircleDTO> dto = new ArrayList<CircleDTO>();
		try {
			dto = getTripService().getAffiliateCircleForTaxiUser(con, userId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<MatchedTripDTO> getRideSekerForCircle(String circleId) {
		Connection con = getLocalConnection();
		List<MatchedTripDTO> dto = new ArrayList<MatchedTripDTO>();
		try {
			dto = getTripService().loadRidesByCircle(con, circleId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<RideSeekerDTO> fetchCompletedRideSeekerList() {
		Connection con = getLocalConnection();
		List<RideSeekerDTO> dto = new ArrayList<RideSeekerDTO>();
		try {
			dto = getTripService().loadCompletedRideSeekerList(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<RideManagementDTO> fetchCompletedRideManagementList() {
		Connection con = getLocalConnection();
		List<RideManagementDTO> dto = new ArrayList<RideManagementDTO>();
		try {
			dto = getTripService().loadCompletedRideManagementList(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static UserRegistrationDTO getForgotPassword(String userName,
			String randomPassword, String senderId) {
		Connection con = getLocalConnection();
		UserRegistrationDTO dto = null;
		try {
			dto = getTripService().loadForgotPasswordUser(con, userName,
					randomPassword, senderId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<UserRegistrationDTO> getAllUserById(List<Integer> userId) {
		Connection con = getLocalConnection();
		List<UserRegistrationDTO> dto = new ArrayList<UserRegistrationDTO>();
		try {
			dto = getTripService().loadForgotPasswordUser(con, userId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static UserRegistrationDTO getUserById(int userId) {
		Connection con = getLocalConnection();
		UserRegistrationDTO dto = null;
		try {
			dto = getTripService().getUserById(con, userId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<UserRegistrationDTO> getAllUsaerOfACircle(String userID) {
		Connection con = getLocalConnection();
		List<UserRegistrationDTO> dto = new ArrayList<UserRegistrationDTO>();
		try {
			dto = getTripService().loadAlluserForACircle(con, userID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static RideSeekerDTO getRideSeekerData(int rideSeekerId) {
		Connection con = getLocalConnection();
		RideSeekerDTO dto = null;
		try {
			dto = getTripService().loadRideSeekerData(con, rideSeekerId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<Integer> findAllUsersInCircle(int circleID) {
		Connection con = getLocalConnection();
		List<Integer> x = new ArrayList<Integer>();
		try {
			x = getTripService().findAllUsersInCircle(con, circleID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static UserPreferencesDTO getUserPreferences(int UserId) {
		Connection con = getLocalConnection();
		UserPreferencesDTO dto = new UserPreferencesDTO();
		try {
			dto = getTripService().getUserPreferences(con, UserId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<ManageRideDTO> findCompletedManageRide(
			ManageRideFormDTO manageRideFormDTO) {
		Connection con = getLocalConnection();
		List<ManageRideDTO> dto = new ArrayList<ManageRideDTO>();
		try {
			dto = getTripService().findCompletedManageRide(con,
					manageRideFormDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<ManageRideDTO> findPendingManageRide(
			ManageRideFormDTO manageRideFormDTO) {
		Connection con = getLocalConnection();
		List<ManageRideDTO> dto = new ArrayList<ManageRideDTO>();
		try {
			dto = getTripService()
					.findPendingManageRide(con, manageRideFormDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<ManageRideDTO> findPendingMatchedManageRide(
			ManageRideFormDTO manageRideFormDTO) {
		Connection con = getLocalConnection();
		List<ManageRideDTO> dto = new ArrayList<ManageRideDTO>();
		try {
			dto = getTripService().findPendingMatchedManageRide(con,
					manageRideFormDTO);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	// UpdatePoolForIsResult
	public static void getUpdatePoolForIsResult() {
		Connection con = getLocalConnection();
		try {
			getTripService().getUpdatePoolForIsResult(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static ApproverDTO addApprover(ApproverDTO approberdto) {
		Connection con = getLocalConnection();
		ApproverDTO dto = null;
		try {
			dto = getTripService().addApprover(con, approberdto);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static ApproverDTO editApprover(ApproverDTO approberdto) {
		Connection con = getLocalConnection();
		ApproverDTO dto = null;
		try {
			dto = getTripService().editApprover(con, approberdto);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<ApproverDTO> findApproverForUser(int userId) {
		Connection con = getLocalConnection();
		List<ApproverDTO> dtos = new ArrayList<ApproverDTO>();
		try {
			dtos.addAll(getTripService().findApproverForUser(con, userId));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static ApproverDTO findApproverById(int approverId) {
		Connection con = getLocalConnection();
		ApproverDTO dto = new ApproverDTO();
		try {
			dto = getTripService().findApproverById(con, approverId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static List<RidePreVehicle> fetchRidePreVehicleList(String date,
			int userId) {
		Connection con = getLocalConnection();
		List<RidePreVehicle> list = new ArrayList<RidePreVehicle>();
		try {
			list.addAll(getTripService().fetchRidePreVehicleList(con, date,
					userId));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return list;
	}

	public static UserRegistrationDTO findUserByEmail(String emailId) {
		Connection con = getLocalConnection();
		UserRegistrationDTO dto = null;
		try {
			dto = getTripService().findUserByEmail(con, emailId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static SmsReplyDTO addSms(SmsReplyDTO dto) {
		Connection con = getLocalConnection();
		SmsReplyDTO dto1 = null;
		try {
			dto1 = getTripService().addSms(con, dto);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto1;
	}

	public static List<RideSeekerDTO> findRideSeekerDataByIds(
			List<Integer> seekerIds) {
		Connection con = getLocalConnection();
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		try {
			dtos = getTripService().findRideSeekerDataByIds(con, seekerIds);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static void changeStatus(int seekerId, String status) {
		Connection con = getLocalConnection();
		try {
			getTripService().changeStatus(con, seekerId, status);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
	}

	public static List<RideSeekerDTO> findRideSeekerDetailsForApprove(
			String email_id) {
		Connection con = getLocalConnection();
		List<RideSeekerDTO> list = new ArrayList<RideSeekerDTO>();
		try {
			list = getTripService().findRideSeekerDetailsForApprove(con,
					email_id);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return list;
	}

	public static PageStoreDTO getPageStoreByCode(String code) {
		Connection con = getLocalConnection();
		PageStoreDTO dto = new PageStoreDTO();
		try {
			dto = getTripService().getPageStoreByCode(con, code);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dto;
	}

	public static void insertLoginPage(LoginPageDTO dto) {
		Connection con = getLocalConnection();
		try {
			getTripService().insertLoginPage(con, dto);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}

	}

	public static List<LoginPageDTO> getLoginPagesByUserId(int id) {
		Connection con = getLocalConnection();
		List<LoginPageDTO> dtos = new ArrayList<LoginPageDTO>();
		try {
			dtos.addAll(getTripService().getLoginPagesByUserId(con, id));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static void inactiveLoginPageByUserId(int userId, int pageId) {
		Connection con = getLocalConnection();
		try {
			getTripService().inactiveLoginPageByUserId(con, userId, pageId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}

	}

	public static List<RideSeekerDTO> fetchRideSeekerUnApproved() {
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		Connection con = getLocalConnection();
		try {
			dtos.addAll(getTripService().fetchRideSeekerUnApproved(con));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static Map<Integer, List<RideSeekerDTO>> getunAssignedTaxi() {
		Connection con = getLocalConnection();
		Map<Integer, List<RideSeekerDTO>> map = new HashMap<Integer, List<RideSeekerDTO>>();
		try {
			map = getTripService().getunAssignedTaxi(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return map;
	}

	public static List<FrequencyDTO> fetchFrequencyListForRideSeeker(
			String rideSeekerID) {
		List<FrequencyDTO> dtos = new ArrayList<FrequencyDTO>();
		Connection con = getLocalConnection();
		try {
			dtos.addAll(getTripService().fetchFrequencyListForRideSeeker(con,
					rideSeekerID));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static List<FrequencyDTO> fetchFrequencyListForRideManager(
			String rideID) {
		List<FrequencyDTO> dtos = new ArrayList<FrequencyDTO>();
		Connection con = getLocalConnection();
		try {
			dtos.addAll(getTripService().fetchFrequencyListForRideManager(con,
					rideID));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static boolean testUniqueMobileNumber(String mobile_no) {
		Connection con = getLocalConnection();
		boolean x = false;
		try {
			x = getTripService().testUniqueMobileNumber(con, mobile_no, 0);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static boolean testOtherUniqueMobileNumber(String mobile_no,
			int excludedUserId) {
		Connection con = getLocalConnection();
		boolean x = false;
		try {
			x = getTripService().testUniqueMobileNumber(con, mobile_no,
					excludedUserId);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return x;
	}

	public static Map<Integer, List<RideSeekerDTO>> getunAssignedTaxiForOwner() {
		Connection con = getLocalConnection();
		Map<Integer, List<RideSeekerDTO>> map = new HashMap<Integer, List<RideSeekerDTO>>();
		try {
			map = getTripService().getunAssignedTaxiForOwner(con);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return map;
	}

	public static List<RideSeekerDTO> fetchRecurringRideList() {
		Connection con = getLocalConnection();
		List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
		try {
			dtos.addAll(getTripService().fetchRecurringRideList(con));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static void addSubSeekers(RideSeekerDTO ride, Connection con)
			throws ConfigurationException {
		UserRegistrationDTO dto = null;
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().addSubSeekers(con, ride);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().addSubSeekers(con, ride);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static RideSeekerDTO cancelSubSeekers(Connection con,
			RideSeekerDTO rideSeekerDTO) throws ConfigurationException {
		RideSeekerDTO dto = null;
		if (con == null) {
			con = getLocalConnection();
			try {
				dto = getTripService().cancelSubSeekers(con, rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				dto = getTripService().cancelSubSeekers(con, rideSeekerDTO);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
		return dto;
	}

	public static List<CombineRideDTO> getAllCombineVehicleList(
			String fromAddress, String toAddress, String rideDate, int circleID) {
		List<CombineRideDTO> matchedList = new ArrayList<CombineRideDTO>();
		Connection con = getLocalConnection();
		try {
			matchedList = getTripService().getAllCombineVehicleList(con,
					fromAddress, toAddress, rideDate, circleID);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return matchedList;
	}

	public static void updateRideIdDropTake(RideManagementDTO rideToDrop,
			RideManagementDTO rideToTake, Connection con)
			throws ConfigurationException {
		if (con == null) {
			con = getLocalConnection();
			try {
				getTripService().updateRideIdDropTake(con, rideToDrop,
						rideToTake);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
			} finally {
				ListOfValuesManager.releaseConnection(con);
			}
		} else {
			try {
				getTripService().updateRideIdDropTake(con, rideToDrop,
						rideToTake);
			} catch (ConfigurationException e) {
				LoggerSingleton.getInstance().error(
						e.getStackTrace()[0].getClassName() + "->"
								+ e.getStackTrace()[0].getMethodName()
								+ "() : "
								+ e.getStackTrace()[0].getLineNumber() + " :: "
								+ e.getMessage());
				throw new ConfigurationException();
			}
		}
	}

	public static boolean testSingleCircleMember(
			CircleMemberDTO circleMemberDTO, String id, String ctype,
			String ttype) {
		Connection con = getLocalConnection();
		boolean test = false;
		try {
			test = getTripService().testSingleCircleMember(con,
					circleMemberDTO, id, ctype, ttype);
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return test;
	}

	public static List<PaymentDTO> fetchPaymentList(int id) {
		Connection con = getLocalConnection();
		List<PaymentDTO> dtos = new ArrayList<PaymentDTO>();
		try {
			dtos.addAll(getTripService().fetchPaymentList(con, id));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static List<PaymentDTO> fetchActivePaymentListForCircle(
			Integer[] integers) {
		Connection con = getLocalConnection();
		List<PaymentDTO> dtos = new ArrayList<PaymentDTO>();
		try {
			dtos.addAll(getTripService().fetchActivePaymentListForCircle(con,
					integers));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static List<CircleOwnerManagerDTO> getAllCorpCircleForLoginUser(
			String id) {
		Connection con = getLocalConnection();
		List<CircleOwnerManagerDTO> dtos = new ArrayList<CircleOwnerManagerDTO>();
		try {
			dtos.addAll(getTripService().loadallCorpCircleForLoginUser(con, id));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static List<CircleOwnerManagerDTO> getAllTaxiCircleForLoginUser(
			String id) {
		Connection con = getLocalConnection();
		List<CircleOwnerManagerDTO> dtos = new ArrayList<CircleOwnerManagerDTO>();
		try {
			dtos.addAll(getTripService().loadallTaxiCircleForLoginUser(con, id));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static List<PaymentPlanDTO> fetchPaymentPlanForLoginUser(
			String userId) {
		Connection con = getLocalConnection();
		List<PaymentPlanDTO> dtos = new ArrayList<PaymentPlanDTO>();
		try {
			dtos.addAll(getTripService().fetchPaymentPlanForLoginUser(con,
					userId));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static List<PaymentTxnsDTO> fetchAllTxnByDate(Date date) {
		Connection con = getLocalConnection();
		List<PaymentTxnsDTO> dtos = new ArrayList<PaymentTxnsDTO>();
		try {
			dtos.addAll(getTripService().fetchAllTxnByDate(con, date));
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		return dtos;
	}

	public static void paymentTxnHoponToUser(HoponAccountDTO hoponAccountDto,
			UserRegistrationDTO userDto) throws ConfigurationException {
		Connection con = getLocalConnection();
		getTripService().paymentTxnHoponToUser(con, hoponAccountDto, userDto);
	}

	public static void paymentTxnUserToHopon(HoponAccountDTO hoponAccountDto,
			UserRegistrationDTO userDto) throws ConfigurationException {
		Connection con = getLocalConnection();
		getTripService().paymentTxnUserToHopon(con, hoponAccountDto, userDto);
	}
	


//contactus method 
	public static boolean getContactInfo(Connection con,ContactusDTO contactusDTO) {
		 con = getLocalConnection();
		try {
			getTripService().contactUs(con, contactusDTO);
			System.out.println("");
			return true;
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(
					e.getStackTrace()[0].getClassName() + "->"
							+ e.getStackTrace()[0].getMethodName() + "() : "
							+ e.getStackTrace()[0].getLineNumber() + " :: "
							+ e.getMessage());
			return false;
		} finally {
			ListOfValuesManager.releaseConnection(con);
		}
		
	}
	}

