package com.generic.model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class creates a model of a Warehouse.
 * 
 * @author GENERIC TEAM
 */

public class Warehouse {
	
	private static final String WAREHOUSE_DETAIL_FORMAT_STRING = "| WAREHOUSEID: %d| FREIGHT RECEIPT STATUS: %s| SHIPMENT AVALIABLE: %d|";
	
	private int warehouseID; // warehouse ID
	private boolean freightReceiptEnabled; // freight receipt
	private List<Shipment> shipments; // List of shipments

	/**
	 * Construct a new warehouse
	 * @param id warehouse identification number
	 * @param receipt freightReceipt status
	 */
	public Warehouse(int warehouseID) {
		this.shipments = new ArrayList<Shipment>();
		this.warehouseID = warehouseID;
		this.freightReceiptEnabled = true;
	}
	
	/**
	 * Enables freight receipt
	 */
	public void enableFreight() {
		freightReceiptEnabled = true;
	}
	
	/**
	 * Disables freight receipt
	 */
	public void disableFreight() {
		freightReceiptEnabled = false;
	}

	/**
	 * Gets the freightReceipt Status
	 * @return freightReceipt
	 */
	public boolean receivingFreight() {
		return freightReceiptEnabled;
	}
	
	/**
	 * Gets the warehouseID
	 * @return warehouseID
	 */
	public int getWarehouseID() {
		return warehouseID;
	}
	
	/**
	 * Adds a shipment to the warehouse if freightReceipt
	 * enabled, does not add if it isn't enabled
	 * @param mShipment shipment to be received
	 * @return true if add successful, false if not.
	 */
	public boolean addShipment(Shipment mShipment) {
		if (freightReceiptEnabled) {
			shipments.add(mShipment);
			return true;
		}
		return false;
	}

	/**
	 * Getter for number of shipments 
	 * in the warehouse
	 * @return
	 */
	public int getShipmentSize() {
		return shipments.size();
	}
	
	@Override
	public String toString() {
		String headerString = String.format(WAREHOUSE_DETAIL_FORMAT_STRING, warehouseID, (freightReceiptEnabled) ? "ENABLED" : "ENDED", getShipmentSize());
		String headerFormat = new String(new char[headerString.length()]).replace("\0", "-");
		StringBuilder warehouseInfo = new StringBuilder()
				.append(headerFormat).append("\n")
				.append(headerString).append("\n")
				.append(headerFormat).append("\n")
				.append("          *SHIPMENT RECEIVED*").append("\n")
				.append("****************************************").append("\n");
		if (!isEmpty()) {
			int count = 1;
			for (Shipment shipment : shipments) {
				String shipmentInfo = shipment.toString();
				warehouseInfo.append(count++).append(".").append(shipmentInfo).append("\n");
				warehouseInfo.append("****************************************").append("\n");
			}
		} else {
			warehouseInfo.append("        *NO SHIPMENTS RECEIVED YET*").append("\n");
		}
		return warehouseInfo.toString();
	}
	
	/**
	 * Checks if warehouse is empty.
	 * Obviously so we don't loop through an
	 * try to print an empty list.
	 * @return true if shipments size 
	 * 		   is 0 and false if not
	 */
	public boolean isEmpty() {
		return (shipments.size() == 0);
	}
	
	/**
	 * Exports a warehouse object to a JSON 
	 * file.
	 * 
	 * A NICE THING TO ADD WILL BE TO ALLOW
	 * USER SPECIFY A DESTINATION PATH FOR FILE.
	 */
	@SuppressWarnings("unchecked")
	public void exportToJSON() {
		String filePath = "output/warehouse_"+ warehouseID + ".json";
		File file = new File(filePath);

		JSONObject warehouseInfo = new JSONObject();
		JSONArray warehouseContents = new JSONArray();
		JSONObject shipmentContents;

		for (Shipment shipment : shipments) {
			shipmentContents = shipment.toJSON();
			warehouseContents.add(shipmentContents);
		}
		warehouseInfo.put("Warehouse_" + warehouseID, warehouseContents);
		
		// Check and create directory
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();

		//Write JSON file
		try (FileWriter fw = new FileWriter(filePath)) {
			PrintWriter printWriter = new PrintWriter(fw);
			printWriter.println(warehouseInfo.toJSONString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
