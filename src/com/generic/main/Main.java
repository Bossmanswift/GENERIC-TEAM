package com.generic.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;

/**
 * Entry Point
 * 
 * To import run the command: import example.json
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

		loop: while (true) {
			Scanner in = new Scanner(System.in);
			System.out.print(">> ");
			if (!in.hasNextLine())
				break;

			String[] arg = in.nextLine().split(" ");
			String command = arg[0];

			switch (command.toLowerCase()) {
			case "import":

				String file;
				try {
					file = arg[1];
					System.out.println("Importing " + file + "...");
					app.parseJson(new File("resource/" + file).getAbsolutePath());
					System.out.println("Importing complete!");
				} catch (IOException | ParseException | ArrayIndexOutOfBoundsException e) {
					System.out.println("**System can not read the file!");
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

				int warehouseID;
				try {
					warehouseID = Integer.parseInt(arg[1]);
					warehouseTracker.printWarehouseDetails(warehouseID);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input a warehouseID");
				}
				break;

			case "print*":
				if (!warehouseTracker.isEmpty()) {
					warehouseTracker.printAll();
				} else {
					System.out.println("** No warehouses avaliable, please import data first");
				}
				break;

			case "enablef":
				int warehouseID1;
				try {
					warehouseID1 = Integer.parseInt(arg[1]);
					if (!warehouseTracker.isEmpty()) {
						warehouseTracker.enableFreight(warehouseID1);
						System.out.println("** Freight enabled for warehouse #" + warehouseID1);

					} else {
						System.out.println("** No warehouse avaliable, please import data first");
					}
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input an integer");
				}
				break;
				
			case "disablef":
				int warehouseID2;
				try {
					warehouseID2 = Integer.parseInt(arg[1]);
					if (!warehouseTracker.isEmpty()) {
						warehouseTracker.endFreight(warehouseID2);
						System.out.println("** Freight ended for warehouse #" + warehouseID2);

					} else {
						System.out.println("** No warehouses avaliable, please import data first");
					}
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input an integer");
				}
				break;

			case "add":
				try {
					int mWarehouseID = Integer.parseInt(arg[1]);
					if (!warehouseTracker.isEmpty()) {

						if (!warehouseTracker.warehouseExists(mWarehouseID)) {
							System.out.println("** Warehouse with ID #" + mWarehouseID + " does not exist");
						} else {
							boolean added = app.addShipmentOp(mWarehouseID , in);
							if (!added) {
								System.out.println("** Shipment could not be added because"
										         + " freight has ended for this warehouse");
							}
						}
					} else {
						System.out.println("** No warehouses with avaliable, please import data first");
					}
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input a warehouse_id");
				}
				break;
			case "exit":
				in.close();
				break loop;
			default:
				System.out.println("** Invalid Command!");
				continue;
			}
		}
		
		System.out.println("Goodbye!");
		
	}

	/**
	 * Adds shipments to the warehouse using the command line interface
	 * 
	 * @param warehouseID
	 * @return
	 */
	public boolean addShipmentOp(int warehouseID , Scanner sc) {

		
		//Scanner sc = new Scanner(System.in);
		boolean added = false;
		String option = "";
		

		do {
			
			String shipmentID;
			String shipmentString;
			long receiptDate = 0;
			double weight = 0;

			
			System.out.print("Enter shipmentID(i.e. iafr4, 15566) -> ");
			shipmentID = sc.next();
			
			// TODO: Validate input for freight type
			System.out.print("Enter shipment_method(i.e air, truck, ship, rail) -> ");
			
			ArrayList<String> shipmentMethodList = new ArrayList<String>(Arrays.asList(new String [] {FreightType.AIR.toString()
																									, FreightType.RAIL.toString()
																									, FreightType.TRUCK.toString()
																									, FreightType.SHIP.toString()}));
			shipmentString = sc.next().toUpperCase();
			while(!shipmentMethodList.contains(shipmentString))
			{
				System.out.print("Please enter a valid shipment_method(i.e air, truck, ship, rail) -> ");
				shipmentString = sc.next().toUpperCase();
			}
			
			System.out.print("Enter weight(i.e. 84.71, 321) -> ");
			while (!sc.hasNextDouble()) {
				System.out.print("Please enter a valid weight(i.e. 84.71, 321) -> ");
				sc.next();
			}
			weight = sc.nextDouble();
			
			
			System.out.print("Enter receipt_date(i.e 1515354694451) -> ");
			while (!sc.hasNextDouble()) {
				System.out.print("Please enter a valid receipt_date(i.e 1515354694451) -> ");
				sc.next();
			}
			receiptDate = sc.nextLong();
			
			// build a shipment
			Shipment shipment = new Shipment.Builder()
											.id(shipmentID)
											.type(FreightType.valueOf(shipmentString))
											.weight(weight)
											.date(receiptDate)
											.build();

			// add the shipment to the warehouse
			added = warehouseTracker.addShipment(warehouseID, shipment);

			if (added) {
				System.out.println("Incoming shipment has been sucessfully added");
				System.out.print("Would you like to add another shipment to warehouse_" + warehouseID + "?(Y/N) -> ");
				//ArrayList<String> options = new ArrayList<>(Arrays.asList(new String [] {"Y","y"})); 
				while (!sc.hasNext()) {
					System.out.print("Please enter ('Y/N') ->");
				}
				option = sc.next();
			}

		} while (option.equalsIgnoreCase("Y") && added);
		//sc.close();
		

		return added;
	}

	/**
	 * Reads a file that is in JSON format containing various shipment information.
	 * 
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
	 * Parses and assigns shipment object for each warehouse
	 * 
	 * @param shipmentObject shipment object in json
	 */
	private void parseWarehouseContentsToObjects(JSONObject shipmentObject) {
		String warehouseString = (String) shipmentObject.get("warehouse_id");
		int warehouseID = Integer.parseInt(warehouseString);
		// create warehouse
		Warehouse warehouse = new Warehouse(warehouseID);
		// build a shipment
		Shipment shipment = new Shipment.Builder()
				.id((String) shipmentObject.get("shipment_id"))
				.type(FreightType.valueOf((String) shipmentObject.get("shipment_method").toString().toUpperCase()))
				.weight(((Number) shipmentObject.get("weight")).doubleValue())
				.date(((Number)shipmentObject.get("receipt_date")).longValue()).build();
		// add the warehouse
		warehouseTracker.addWarehouse(warehouse);
		// add the shipment to the warehouse
		warehouseTracker.addShipment(warehouseID, shipment);
	}
}
