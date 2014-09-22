package com.hopon.dto;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class VehicleMasterDataTable extends ListDataModel<VehicleMasterDTO> implements SelectableDataModel<VehicleMasterDTO>, Serializable{

	private static final long serialVersionUID = 1L;
	
	public VehicleMasterDataTable(){
		
	}
	public VehicleMasterDataTable(List<VehicleMasterDTO> list){
		super(list);
	}
	@SuppressWarnings("unchecked")
	@Override
	public VehicleMasterDTO getRowData(String arg0) {
		// TODO Auto-generated method stub
           List<VehicleMasterDTO> vehiclemasterTripDTOs = (List<VehicleMasterDTO>)getWrappedData();
		
		  for (VehicleMasterDTO vehicleMasterDTO : vehiclemasterTripDTOs) {
			if(vehicleMasterDTO.getVehicleID().equals(arg0))
				return vehicleMasterDTO;
			
		}
		return null;
	}
	@Override
	public Object getRowKey(VehicleMasterDTO vehicleMasterDTO) {
		// TODO Auto-generated method stub
		return vehicleMasterDTO.getVehicleID();
	}
}


