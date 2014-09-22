package com.hopon.dto;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class CombineVehicleDataModel extends ListDataModel<CombineRideDTO> implements SelectableDataModel<CombineRideDTO>, Serializable {
private static final long serialVersionUID = 1L;
	
	public CombineVehicleDataModel(){
		
	}
	public CombineVehicleDataModel(List<CombineRideDTO> list){
		super(list);
	}
	@SuppressWarnings("unchecked")
	@Override
	public CombineRideDTO getRowData(String arg0) {
		// TODO Auto-generated method stub
           List<CombineRideDTO> combineRideDTOs = (List<CombineRideDTO>)getWrappedData();
		
		  for (CombineRideDTO combineRideDTO : combineRideDTOs) {
			if(combineRideDTO.getUserName().equals(arg0))
				return combineRideDTO;
			
		}
		return null;
	}
	@Override
	public Object getRowKey(CombineRideDTO matchedTripDTO) {
		// TODO Auto-generated method stub
		return matchedTripDTO.getUserName();
	}
}
