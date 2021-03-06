package com.generic.views.scenes;

import java.util.HashMap;
import java.util.Map;

import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.models.WarehouseFactory;
import com.generic.models.WeightUnit;
import com.generic.views.utils.FileSaverIO;
import com.generic.views.utils.MessageBoxView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Creates a shipment scene
 * @author GENERIC TEAM
 *
 */
public final class ShipmentScene {

	private static WarehouseFactory warehouseTracker = WarehouseFactory.getInstance();
	private static TextField sIDTextField, sWeightTextField,
	sReceiptDateTextField; // textfields in the shipment class

	private static ComboBox<FreightType> shipmentOptions;

	private static ComboBox<WeightUnit> unitOptions;

	private static TableView<Shipment> shipmentTable; // holds the shipment table

	/**
	 * Creates a shipment scene with tables of shipments
	 * @param primaryStage reference to the primary stage
	 * @param selectedWarehouse the warehouse which
	 * 							shipments are contained in.
	 * @return a new based on the selected warehouse
	 */
	@SuppressWarnings("unchecked")
	public static Scene createShipmentTable(Stage primaryStage, Warehouse selectedWarehouse) {

		ObservableList<Shipment> shipmentList = FXCollections.observableArrayList(selectedWarehouse.getShipmentList());

		// Create Table
		shipmentTable = new TableView<Shipment>();
		shipmentTable.setItems(shipmentList);
		shipmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		shipmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create Table Columns
		// Shipment ID Column
		TableColumn<Shipment, String> sIDColumn = new TableColumn<>("ID");
		sIDColumn.setMinWidth(150);
		sIDColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("ShipmentID"));

		// Warehouse Shipments Size Column
		TableColumn<Shipment, FreightType> sFreightTypeColumn = new TableColumn<>("Shipping Method");
		sFreightTypeColumn.setMinWidth(200);
		sFreightTypeColumn.setCellValueFactory(new PropertyValueFactory<Shipment, FreightType>("Freight"));

		// Warehouse Freight Status Column
		TableColumn<Shipment, String> sWeightColumn = new TableColumn<Shipment, String>("Weight");
		sWeightColumn.setMinWidth(200);
		sWeightColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("Weight"));

		// Warehouse Freight Status Column
		TableColumn<Shipment, String> sReceiptDateColumn = new TableColumn<Shipment, String>("Receipt Date");
		sReceiptDateColumn.setMinWidth(250);
		sReceiptDateColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("ReceiptDateString"));

		// Warehouse Freight Status Column
		TableColumn<Shipment, WeightUnit> sWeightUnitColumn = new TableColumn<Shipment, WeightUnit>("Weight Unit");
		sWeightUnitColumn.setMinWidth(100);
		sWeightUnitColumn.setCellValueFactory(new PropertyValueFactory<Shipment, WeightUnit>("WeightUnit"));

		// Add Columns to TableView
		shipmentTable.getColumns().addAll(sIDColumn, sFreightTypeColumn, sWeightColumn, sReceiptDateColumn,
				sWeightUnitColumn);

		// Create Table Header
		Label tableHeadingLabel = new Label("Warehouse " + selectedWarehouse.getId());
		tableHeadingLabel.setFont(new Font("Arial", 20));

		// Add table to center pane
		VBox centerPane = new VBox(10);
		centerPane.setPadding(new Insets(10));
		centerPane.getChildren().addAll(shipmentTable);

		// Top pane contents
		Button backButton = new Button("Warehouse Inventory");
		// Navigate to warehouse when Button is pressed
		Scene warehouseScene = WarehouseScene.createWarehouseTable(primaryStage);
		backButton.setOnAction(e -> { primaryStage.setScene(warehouseScene); });

		Button toggleFreight = new Button(
				(selectedWarehouse.getFreightReceiptEnabled() ? "Disable Freight" : "Enable Freight"));
		toggleFreight.setOnAction(e -> {
			toggleFreightClicked(primaryStage, selectedWarehouse);
		});

		Button exportWarehouseButton = new Button("Export Warehouse Metadata");
		exportWarehouseButton.setOnAction(e -> {
			FileSaverIO.exportWarehouse(primaryStage, selectedWarehouse);
		});

		HBox topPaneControls = new HBox(10);

		if(selectedWarehouse.getShipmentSize() == 0) {
			topPaneControls.getChildren().addAll(backButton, toggleFreight);
		}else {
			topPaneControls.getChildren().addAll(backButton, toggleFreight, exportWarehouseButton);
		}

		VBox topPane = new VBox(10);
		topPane.setPadding(new Insets(10));
		topPane.getChildren().addAll(tableHeadingLabel, topPaneControls);

		// Text Fields
		sIDTextField = new TextField();
		sIDTextField.setPromptText("ID");
		sIDTextField.setMinWidth(80);
		Region spacer1 = new Region();

		shipmentOptions = new ComboBox<>();
		shipmentOptions.getItems().addAll(FreightType.values());
		shipmentOptions.setPromptText("Shipment Method");
		shipmentOptions.setMinWidth(180);
		Region spacer2 = new Region();

		sWeightTextField = new TextField();
		sWeightTextField.setPromptText("Weight");
		sWeightTextField.setMinWidth(80);
		Region spacer3 = new Region();

		sReceiptDateTextField = new TextField();
		sReceiptDateTextField.setPromptText("Receipt Date");
		sReceiptDateTextField.setMinWidth(180);
		Region spacer4 = new Region();

		unitOptions = new ComboBox<>();
		unitOptions.getItems().addAll(WeightUnit.values());
		unitOptions.setPromptText("Weight Unit");
		unitOptions.setMinWidth(60);
		Region spacer5 = new Region();

		// Buttons
		Button addBtn = new Button("Add");
		addBtn.setMinWidth(60);
		Region spacer6 = new Region();
		spacer6.setMinWidth(60);
		addBtn.setOnAction(e -> addShipmentClicked(primaryStage, selectedWarehouse));

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);
		deleteBtn.setOnAction(e -> {
			deleteShipmentsClicked(selectedWarehouse.getId());
			Scene shipmentScene = createShipmentTable(primaryStage, selectedWarehouse);
			primaryStage.setScene(shipmentScene);
		});

		HBox bottomPane = new HBox();
		HBox.setHgrow(sIDTextField, Priority.ALWAYS);
		HBox.setHgrow(shipmentOptions, Priority.ALWAYS);
		HBox.setHgrow(sWeightTextField, Priority.ALWAYS);
		HBox.setHgrow(unitOptions, Priority.ALWAYS);
		HBox.setHgrow(sReceiptDateTextField, Priority.ALWAYS);

		// Spacers for layout consistency when
		// shipment adding controls are not available
		HBox.setHgrow(spacer1, Priority.ALWAYS);
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		HBox.setHgrow(spacer3, Priority.ALWAYS);
		HBox.setHgrow(spacer4, Priority.ALWAYS);
		HBox.setHgrow(spacer5, Priority.ALWAYS);
		HBox.setHgrow(spacer6, Priority.ALWAYS);

		bottomPane.setPadding(new Insets(10));
		bottomPane.setSpacing(8);

		if (warehouseTracker.freightIsEnabled(selectedWarehouse.getId())) {
			bottomPane.getChildren().addAll(sIDTextField, shipmentOptions, sWeightTextField, unitOptions,
					sReceiptDateTextField, addBtn, deleteBtn);
		} else {
			bottomPane.getChildren().addAll(spacer1, spacer2, spacer3, spacer4, spacer5, spacer6, deleteBtn);
		}

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(centerPane);
		// Only show adding controls if freight is enabled
		borderPane.setBottom(bottomPane);

		borderPane.setTop(topPane);

		Scene mShipmentScene = new Scene(borderPane);

		return mShipmentScene;
	}

	/*
	 * Toggles the receiving freight status of a warehouse
	 */
	private static void toggleFreightClicked(Stage primaryStage, Warehouse warehouse) {
		if (!warehouseTracker.freightIsEnabled(warehouse.getId())) {
			warehouseTracker.enableFreight(warehouse.getId());
		} else {
			warehouseTracker.endFreight(warehouse.getId());
		}
		Scene shipmentScene = createShipmentTable(primaryStage, warehouse);
		primaryStage.setScene(shipmentScene);
	}

	/**
	 * Deletes selected Shipment(s)
	 * @param warehouseID ID of warehouse to delete from
	 */
	private static void deleteShipmentsClicked (String warehouseID) {
		ObservableList<Shipment> selectedShipments, shipmentItems;
		shipmentItems = shipmentTable.getItems();
		selectedShipments = shipmentTable.getSelectionModel().getSelectedItems();
		warehouseTracker.removeAllShipments(warehouseID, selectedShipments);
		shipmentItems.removeAll(selectedShipments);
	}

	/**
	 * Adds a Shipment to a given warehouse
	 * @param warehouse the Warehouse to add shipment to
	 */
	private static void  addShipmentClicked(Stage primaryStage, Warehouse warehouse) {
		// Get the raw data and validate
		// Pass the data into local variables
		// Pass the local locals to the the builder to build object

		String shipmentID = sIDTextField.getText();
		FreightType fType = null;
		WeightUnit wUnit = null;
		double weight = 0;
		long receiptDate = 0;


		fType = shipmentOptions.getSelectionModel().getSelectedItem();

		wUnit = unitOptions.getSelectionModel().getSelectedItem();

		try {
			weight = Double.parseDouble(sWeightTextField.getText());
		} catch (NumberFormatException | NullPointerException e) {
			MessageBoxView.show("Please enter a valid weight(i.e 34.5, 213): " + sWeightTextField.getText() + " ",
					"Error");
		}

		try {
			receiptDate = Long.parseLong(sReceiptDateTextField.getText());
		} catch (NumberFormatException e) {
			MessageBoxView.show(
					"Please enter a valid receipt date(i.e 150001211129L): " + sReceiptDateTextField.getText() + " ",
					"Error");
		}

		Map<String, Object> textFieldInput = new HashMap<>();
		textFieldInput.put("shipmentID", shipmentID);
		textFieldInput.put("fType", fType);
		textFieldInput.put("weightUnit", wUnit);
		textFieldInput.put("weight", weight);
		textFieldInput.put("receiptDate", receiptDate);

		if (warehouseTracker.addShipment(warehouse.getId(), textFieldInput)) {
			shipmentOptions.setValue(null);
			sWeightTextField.clear();
			unitOptions.setValue(null);
			sReceiptDateTextField.clear();

			Scene shipmentScene = createShipmentTable(primaryStage, warehouse);
			primaryStage.setScene(shipmentScene);
		}

	}
}
