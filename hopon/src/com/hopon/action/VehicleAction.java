package com.hopon.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import com.hopon.dto.MatchedTripDTO;
import com.hopon.dto.MessageBoardDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.dto.VehicleMasterDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.ConfigurationException;
import com.hopon.utils.ListOfValuesManager;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.Messages;
import com.hopon.utils.ServerUtility;

public class VehicleAction extends BaseAction {
	/*protected VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();
	protected List<RidePreVehicle> ridePreVehicleList = new ArrayList<RidePreVehicle>();
	protected String ridePreVehicleDate;	
	protected VehicleMasterDataTable vehicleMasterDataModel = new VehicleMasterDataTable();
	protected VehicleMasterDTO[] vehicleMasterDtos;
	protected List<VehicleMasterDTO> vehicleMasterDTOList = new ArrayList<VehicleMasterDTO>();*/

	public void registerVehicleMaster(){

		try {
			vehicleMasterDTO.setUserID(userRegistrationDTO.getId());
			vehicleMasterDTO = ListOfValuesManager.getVehicleMaster("findByDTO", vehicleMasterDTO) ;
		} catch (ConfigurationException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
		}

		rollbackTest = false;
		vehicleList();
		vehicleMasterDTO = new VehicleMasterDTO();
		//return "vehicleegister";
	}
	public List<SelectItem> getAllVehicleList(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		VehicleMasterDTO tempVehicleList = new VehicleMasterDTO();
		for (int i =0 ; i< vehicleMasterDTOList.size();i++){
			if(vehicleMasterDTOList.get(i).isIs_default()) {
				tempVehicleList=vehicleMasterDTOList.get(0);
				vehicleMasterDTOList.set(0, vehicleMasterDTOList.get(i));    			
				vehicleMasterDTOList.set(i, tempVehicleList); 			
			}
		}
		for (int i =0 ; i< vehicleMasterDTOList.size();i++){
			list.add(new SelectItem(vehicleMasterDTOList.get(i).getVehicleID(),vehicleMasterDTOList.get(i).getReg_NO()));
		}
		return list;
	}


	public void vehicleDefaultUpdate(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		String vehicleID = (String)requestMap.get("vehicleID");
		vehicleMasterDTO.setVehicleID(vehicleID);
		vehicleMasterDTO.setUserID(userRegistrationDTO.getId());
		vehicleMasterDTO = ListOfValuesManager.getUpdateVehicleDefault(vehicleMasterDTO);
		
		rollbackTest = false;
		vehicleMasterDTO = new VehicleMasterDTO();
		vehicleList();
		getAllVehicleList();
	}

	public void updateVehicle(){
		clearScreenMessage();
		vehicleMasterDTO = ListOfValuesManager.getUpdateVehicle(vehicleMasterDTO);
		successMessage.add(Messages.getValue("success.update", new Object[]{"Vehicle"}));
		
		rollbackTest = false;
		//vehicleMasterDTO = new VehicleMasterDTO();
		vehicleList();
		//return "updateVehicle";
	}
	public void inActivateVehicle(){
		clearScreenMessage();
		for(int i=0;i<vehicleMasterDtos.length;i++){
			vehicleMasterDTO = vehicleMasterDtos[i];
			vehicleMasterDTO = ListOfValuesManager.getInActivateVehicle(vehicleMasterDTO);
			successMessage.add(Messages.getValue("success.delete", new Object[]{"Vehicle"}));
			rollbackTest = false;
		}
		vehicleMasterDTO = new VehicleMasterDTO();
		vehicleList();
		//return "inActivateVehicle";
	}
	public void showVehicleDetailsInpopup(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		String vehicleID = (String)requestMap.get("vehicleID");
		for(int i =0 ; i<vehicleMasterDTOList.size();i++){
			if(vehicleID.equalsIgnoreCase(vehicleMasterDTOList.get(i).getVehicleID())){
				vehicleMasterDTO = vehicleMasterDTOList.get(i);			
			}
		}
	}

	public void handleVehicleUpload(FileUploadEvent event)  {  
		UploadedFile item = event.getFile();
		String tomcatDirectoryPath = ApplicationUtil.catalinaDirectoryPath;
		String extension = ServerUtility.getExtension(item);
		String path = null;
		File vehiclePath = null;
		if(tomcatDirectoryPath!=null){
			String vehicleDirPath = ApplicationUtil.vehicleDirectoryPath;
			path = ServerUtility.constructTargetFileName("vehicle_"+userRegistrationDTO.getId(), extension,  vehicleDirPath);
			vehiclePath = new File(vehicleDirPath + path);
			try {
				InputStream input = item.getInputstream();
				OutputStream output = new FileOutputStream(vehiclePath);
				try { 
					IOUtils.copy(input, output);
				} 
				catch (Exception e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
				}
				finally {
					input.close();
					output.flush();
					output.close();
				}
			} catch(IOException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}

			Workbook workbook;
			try {
				workbook = Workbook.getWorkbook(vehiclePath);
				Sheet sheet = workbook.getSheet(0); 
				clearScreenMessage();
				for(int i=1;;i++) {
					Cell cell = sheet.getCell(0,i);
					String make = cell.getContents();
					cell = sheet.getCell(1,i);
					String model = cell.getContents();
					cell = sheet.getCell(2,i);
					String color = cell.getContents();
					cell = sheet.getCell(3,i);
					String registrationNo = cell.getContents();
					cell = sheet.getCell(4,i);
					String capacity = cell.getContents();
					if((make.trim() == null || make.trim().isEmpty()) && (model.trim() == null || model.trim().isEmpty()) && (color.trim() == null || color.trim().isEmpty()) && (registrationNo.trim() == null || registrationNo.trim().isEmpty()) && (capacity.trim() == null || capacity.trim().isEmpty())) {
						break;
					}
					String error = "";
					try {	    			

						if(make.trim().isEmpty()) error += Messages.getValue("error.required",  new Object[]{"Make"});
						if(model.trim().isEmpty()) error += Messages.getValue("error.required",  new Object[]{"Model"});
						if(color.trim().isEmpty()) error += Messages.getValue("error.required",  new Object[]{"Color"});
						if(capacity.trim().isEmpty() || Integer.parseInt(capacity) < 1 || Integer.parseInt(capacity) > 60 ) error += Messages.getValue("error.required",  new Object[]{"Capacity"}) + " " + Messages.getValue("error.between", new Object[]{"Capacity", "1", "60"});
						if(registrationNo.trim().isEmpty()) error += Messages.getValue("error.required",  new Object[]{"Registration Numbe"});
						if(ListOfValuesManager.testVehicleRegistrationNumber(registrationNo)) error += Messages.getValue("error.alreadyexist",  new Object[]{"Registration Number '"+ registrationNo +"'"});
						
						if(error.length() == 0) {	
							vehicleMasterDTO.setUserID(userRegistrationDTO.getId());
							vehicleMasterDTO.setMake(make);
							vehicleMasterDTO.setModel(model);
							vehicleMasterDTO.setColor(color);
							vehicleMasterDTO.setReg_NO(registrationNo);
							vehicleMasterDTO.setCapacity(capacity);    					
							vehicleMasterDTO = ListOfValuesManager.getVehicleMaster("findByDTO", vehicleMasterDTO) ;
						}

					} catch (ConfigurationException e) {
						LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
						rollbackTest = true;
						error += Messages.getValue("error.db1", new Object[]{"Vehicle"});
						userMessageDTO = new MessageBoardDTO();
						userMessageDTO.setMessage(Messages.getValue("error.db1", new Object[]{"Vehicle"}) + e.getMessage());
						userMessageDTO.setToMember(Integer.parseInt(userRegistrationDTO.getId()));
						userMessageDTO.setMessageChannel("E");
						userMessageDTO = ListOfValuesManager.getInsertedMessage(userMessageDTO);
					} finally {
						rollbackTest = false;
						if(error.length() > 0)
							errorMessage.add("Row "+ i +" : " + error);
						else	    					
							successMessage.add(Messages.getValue("success.uploading", new Object[]{"Vehicle", i}));
					}

					if(errorMessage.size() > 0) {
						errorMessage.add(Messages.getValue("error.uploading", new Object[]{"Vehicle"}));
						break;
					}
				}
			} catch (BiffException e1) {
				LoggerSingleton.getInstance().error(e1.getStackTrace()[0].getClassName()+"->"+e1.getStackTrace()[0].getMethodName()+"() : "+e1.getStackTrace()[0].getLineNumber()+" :: "+e1.getMessage());
			} catch (IOException e1) {
				LoggerSingleton.getInstance().error(e1.getStackTrace()[0].getClassName()+"->"+e1.getStackTrace()[0].getMethodName()+"() : "+e1.getStackTrace()[0].getLineNumber()+" :: "+e1.getMessage());
			} catch(ArrayIndexOutOfBoundsException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());

			}  
			vehicleList();
			vehicleMasterDTO = new VehicleMasterDTO();
			userMessageDTO = new MessageBoardDTO();
		}
	}

	public List<SelectItem> getVehicleListForDriver() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		if(vehicleMasterDTOList != null) {
			for (int i =0 ; i< vehicleMasterDTOList.size();i++){
				if(vehicleMasterDTOList.get(i).getDrivername().equalsIgnoreCase("NoDriver")){
					list.add(new SelectItem(vehicleMasterDTOList.get(i).getVehicleID(),vehicleMasterDTOList.get(i).getModel()+"-" +vehicleMasterDTOList.get(i).getReg_NO()));
				}
			}
		}
		return list;
	}
	public List<SelectItem> getAllUserForACircle() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<UserRegistrationDTO> userList = new ArrayList<UserRegistrationDTO>();
		userList = ListOfValuesManager.getAllUsaerOfACircle(userRegistrationDTO.getId());
		
		for (int j =0 ; j< vehicleMasterDTOList.size();j++){
			for(int i = 0;i<userList.size();i++){
				if(vehicleMasterDTOList.get(j).getDriverid().equalsIgnoreCase(userList.get(i).getId())){
					userList.remove(i);
				}
			}
		}
		if(userList != null) {
			for(int i = 0;i<userList.size();i++){
				list.add(new SelectItem(userList.get(i).getFirst_name()+"-"+userList.get(i).getAddress()+","+userList.get(i).getId(),userList.get(i).getFirst_name()+"-"+userList.get(i).getAddress()+","+userList.get(i).getId()));
			}
		}
		return list;
	}

	public String addDriverForVehicle(){
		String link = null;
		String delink = null;
		FacesContext context = FacesContext.getCurrentInstance();  
		Map<String,String> requestMap = context.getExternalContext().getRequestParameterMap();  //In java class, you can get back the parameter value with component (submit-command buton) like this :
		link = (String)requestMap.get("link");
		delink = (String)requestMap.get("delink");
		if(link!=null){
			vehicleMasterDTO.setAvilable(link) ;
			String[] parts = forregistrationOnly.getFirst_name().split(",");
			String[] names= parts[0].split("-");
			String driverName = names[0];
			String part2 = "0";
			if(parts.length > 0) part2 = parts[parts.length - 1];
			vehicleMasterDTO.setUserID(part2);
			vehicleMasterDTO.setDrivername(driverName);
		}
		if(delink!=null){
			vehicleMasterDTO.setCapacity(null);
			vehicleMasterDTO.setAvilable(delink) ;
		}
		vehicleMasterDTO = ListOfValuesManager.getUpdateVehicle(vehicleMasterDTO); 
		
		rollbackTest = false;
		vehicleList();
		forregistrationOnly.setFirst_name(null);
		vehicleMasterDTO = new VehicleMasterDTO();
		return "link";
	}


	public void handleVehiclePopUp(SelectEvent evt) {
		MatchedTripDTO dto = (MatchedTripDTO)evt.getObject();
		DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern9);
		clearRidePreVehicleList();
		try {
			String date = new SimpleDateFormat(ApplicationUtil.datePattern3).format(dateFormat.parse(dto.getStartDate()));

			ridePreVehicleList = ListOfValuesManager.fetchRidePreVehicleList(date, Integer.parseInt(userRegistrationDTO.getId()));

		} catch (ParseException e) { } catch (NumberFormatException e) { }
		try {
			ridePreVehicleDate = new SimpleDateFormat(ApplicationUtil.datePattern7).format(dateFormat.parse(dto.getStartDate()));
		} catch (ParseException e) { }
	}
	public void clearRidePreVehicleList() {
		ridePreVehicleList.clear();
		ridePreVehicleDate = "";
	}
	public void populateVehicleByDate() {
		DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern12);
		//clearRidePreVehicleList();
		try {
			Date date1 = dateFormat.parse(ridePreVehicleDate);
			String date2 = new SimpleDateFormat(ApplicationUtil.datePattern3).format(date1);
			ridePreVehicleList = ListOfValuesManager.fetchRidePreVehicleList(date2, Integer.parseInt(userRegistrationDTO.getId()));
		} catch (ParseException e) { }
	}
}
