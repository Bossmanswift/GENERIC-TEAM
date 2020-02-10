package com.generic.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;
import com.sun.xml.internal.ws.api.server.LazyMOMProvider.Scope;

/**
 * Entry Point
 * 
 * To import run the command:
 * import example.json
 * 
 * @author GENERIC TEAM
 *
 */
public class Main {
	
	public static WarehouseTracker warehouseTracker;
	
	public Main() {
		warehouseTracker = WarehouseTracker.getInstance();
		
		System.out.println("Available Commands:");
		System.out.println("1. import <json_file>");
		System.out.println("2. export <warehouse_id>");
		System.out.println("3. print <warehouse_id>");
		System.out.println("4. print*");
		System.out.println("5. enablef <warehouse_id>");
		System.out.println("6. disablef <warehouse_id>");
		System.out.println("7. add <warehouse_id>");
		System.out.println("8. exit");

		System.out.println();

	}

	public static void main(String[] args) {
		Main app = new Main();
		Scanner in = new Scanner(System.in);
		
		loop:
		while(true) {
			System.out.print(">> ");
			if (!in.hasNextLine())
				break;
				
			String[] arg = in.nextLine().split(" ");
			String command = arg[0];
			
			
			
			switch(command.toLowerCase()) {		
				case "import":
					
					String file = arg[1];
					try {
						System.out.println("Importing "+ file +"...");
						app.parseJson(new File("resource/"+file).getAbsolutePath());
						System.out.println("Importing complete!");
					} catch (IOException | ParseException | ArrayIndexOutOfBoundsException e) {
						System.out.println("System can not read the file!");
					}
					break;
				
				case "export":					
					
					try {
						int mWarehouseID = Integer.parseInt(arg[1]);
						if (!warehouseTracker.warehouseExists(mWarehouseID)) {
							System.out.println("** Warehouse with ID " + mWarehouseID + " does not exist");
							break;
						}
						warehouseTracker.exportWarehouseToJSON(mWarehouseID);
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						System.out.println("** Please input an integer");
					}
					break;
				
				case "print":
					
					try {
						int warehouseID = Integer.parseInt(arg[1]);
						warehouseTracker.printWarehouseDetails(warehouseID);
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						System.out.println("** Please input a warehouse_id");
					}
					break;
				
				case "print*":
					if (!warehouseTracker.isEmpty()) {
						warehouseTracker.printAll();
					}else {
						System.out.println("** No warehouses avaliable, please import data first");
					}
					break;
					
				case "enablef":
					// TODO: write to enable freight on a specified warehouse id
				case "disablef":
					// TODO: write to disable freight on a specified warehouse id
					
				case "add":
					try {
						int warehouseID = Integer.parseInt(arg[1]);
						if (!warehouseTracker.isEmpty()) {
							
							if (!warehouseTracker.warehouseExists(warehouseID)){
								System.out.println("** Warehouse with ID #" + warehouseID + " does not exist");	
							}else {
								if (!app.addShipmentOp(warehouseID)) {
									System.out.println("** Shipment could not be added because"
										     		   + " freight has ended for this warehouse");
								}	
							}
						}else {
							System.out.println("** No warehouses with avaliable, please import data first");
						}
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						System.out.println("** Please input a warehouse_id");
					}
					
					
					
					
					
					break;
					
				case "exit":
					break loop;
				default:
					System.out.println("** Invalid Command!");
					continue;
			}
		}
		System.out.println("Goodbye!");
	}
	
	public boolean addShipmentOp(int warehouseID) {
		
        Scanner sc = new Scanner(System.in);
        
        boolean added = false;
		
        String option = "";
        
		String shipmentID;
		FreightType freight;
		long receiptDate;
		double weight;
		
		
		
		do {
		
	        System.out.print("Enter shipmentID(i.e. iafr4, 15566) -> ");
	        while (!sc.hasNext()) {
	        	System.out.print("Please enter a valid shipmentID(i.e. iafr4, 85545) -> ");
	        	sc.next();
	        }
	        shipmentID = sc.next();
	       
	        System.out.print("Enter shipment_method(i.e air, truck, ship, rail) -> ");
	        
	        //TODO: Validate input for freight type


	        System.out.print("Enter weight(i.e. 84.71, 321) -> ");
	        while (!sc.hasNextDouble()) {
	        	System.out.print("Please enter a valid weight(i.e. 84.71, 321) -> ");
	        	sc.next();
	        }
	        weight = sc.nextDouble();
	        
	        
	        System.out.print("Enter receipt_date(i.e 1515354694451) -> ");
	        while (!sc.hasNextLong()) {
	        	System.out.print("Please enter a valid receipt_date(i.e 1515354694451) -> ");
	        	sc.next();
	        }
	        receiptDate = sc.nextLong();
	   
	        // build a shipment
	     	Shipment shipment = new Shipment.Builder()
	     				    				.id(shipmentID)
	     				    				//.type(FreightType.valueOf(frei))
	     				    				.weight(weight)
	     				    				.date(receiptDate)
	     				    				.build();
	        
			// add the shipment to the warehouse
	        added = warehouseTracker.addShipment(warehouseID, shipment);
	        
	        if (added) {
	        	System.out.println("Incoming shipment has been sucessfully added");
	        	System.out.print("Would you like to add another shipment to warehouse_" + warehouseID + "?(Y/N)");
		        while (!sc.hasNext() || !sc.next().equalsIgnoreCase("Y") || !sc.next().equalsIgnoreCase("N")) {
		        	System.out.print("Please enter ('Y/N')");
		        	sc.next();
		        }
		        option = sc.next();
			}
	        
	        sc.close();
			
		} while (option.equalsIgnoreCase("Y") && added);
		return added; 
	}
	
	
	/**
	 * Reads a file that is in JSON format containing
	 * various shipment information.
	 * @param filepath the path of JSON file 
	 */
	@SuppressWarnings("unchecked")
	public void parseJson(String filepath) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filepath);

		JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
		JSONArray warehouseContents = (JSONArray) jsonFile.get("warehouse_contents");
		warehouseContents.forEach(shipmentObject -> parseWarehouseContentsToObjects((JSONObject) shipmentObject));
		
		reader.close();
	}

	/**
	 * Parses and assigns shipment 
	 * object for each warehouse
	 * @param shipmentObject shipment object in json
	 */
	private void parseWarehouseContentsToObjects(JSONObject shipmentObject) {

		String warehouseString = (String)shipmentObject.get("warehouse_id");
		int warehouseID = Integer.parseInt(warehouseString);
		
		// create warehouse
		Warehouse warehouse = new Warehouse(warehouseID);

		String shipmentID = (String) shipmentObject.get("shipment_id");
		FreightType freight = FreightType.valueOf((String)shipmentObject.get("shipment_method").toString().toUpperCase());
		Number weight = (Number) shipmentObject.get("weight");
		Number receiptDate = (Number) shipmentObject.get("receipt_date");
		
		// build a shipment
		Shipment shipment = new Shipment.Builder()
				.id(shipmentID)
				.type(freight)
				.weight(weight.doubleValue())
				.date(receiptDate.longValue())
				.build();

		// add the warehouse
		warehouseTracker.addWarehouse(warehouse);
				
		// add the shipment to the warehouse
		warehouseTracker.addShipment(warehouseID, shipment);
	}
}
