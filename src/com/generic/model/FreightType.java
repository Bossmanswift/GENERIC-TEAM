package com.generic.model;

/**
 * Enum for FreightType
 * @author GENERIC TEAM
 */

public enum FreightType {
	AIR, TRUCK, SHIP, RAIL;
	
	public static boolean getFreightFromString(String str) {
		FreightType fType = FreightType.valueOf(str);
		
		boolean exists = true;
		 
		 if (fType == null) {
			 exists = false;
		 }
		
		return exists;
	}

}
