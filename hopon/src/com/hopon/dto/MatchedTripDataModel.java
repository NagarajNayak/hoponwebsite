package com.hopon.dto;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class MatchedTripDataModel extends ListDataModel<MatchedTripDTO> implements SelectableDataModel<MatchedTripDTO>, Serializable{

	private static final long serialVersionUID = 1L;
	
	public MatchedTripDataModel(){
		
	}
	public MatchedTripDataModel(List<MatchedTripDTO> list){
		super(list);
	}
	@SuppressWarnings("unchecked")
	@Override
	public MatchedTripDTO getRowData(String arg0) {
		// TODO Auto-generated method stub
           List<MatchedTripDTO> matchedTripDTOs = (List<MatchedTripDTO>)getWrappedData();
		
		  for (MatchedTripDTO matchedTripDTO : matchedTripDTOs) {
			if(matchedTripDTO.getUserName().equals(arg0))
				return matchedTripDTO;
			
		}
		return null;
	}
	@Override
	public Object getRowKey(MatchedTripDTO matchedTripDTO) {
		// TODO Auto-generated method stub
		return matchedTripDTO.getUserName();
	}

}
