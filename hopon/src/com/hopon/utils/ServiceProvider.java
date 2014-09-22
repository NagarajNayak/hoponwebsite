package com.hopon.utils;



import com.hopon.impl.TripImpl;


import com.hopon.interf.Trip;

public class ServiceProvider {
	private static Trip tripService=null;
	
	public static Trip getTripService(){ 
		if(tripService==null){
			tripService= new TripImpl();
		}
		return tripService;
		
	}
	
	
}
