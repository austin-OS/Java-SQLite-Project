import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/** 
 * @author Austin Greer
 * @date 11-22-2022
 */

public class DatabasesProject {
	
	/**
	 *  The database file name.
	 */
	private static String DATABASE = "final.db";
	
    /**
     * Connects to the database if it exists, creates it if it does not, and returns the connection object.
     * 
     * @param databaseFileName the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful.");
            } else {
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows
     * This query is written with the Statement class, typically 
     * used for static SQL SELECT statements
     */
    public static void sqlSelectQuery(Connection conn, String sql){
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sqlQuery(Connection conn, String sql){
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	stmt.execute();
        	System.out.println("Query Succesful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Displays the main menu
 	public static void displayMainMenu() {
 		System.out.println("--- Main Menu ---");
 		System.out.println("Choose an option below.");
 		System.out.println("[1] - Add");
 		System.out.println("[2] - Edit");
 		System.out.println("[3] - Delete");
 		System.out.println("[4] - Search");
 		System.out.println("[5] - View All");
 		System.out.println("[6] - Reports");
 		System.out.println("[0] - Quit");
 		System.out.print("Enter a number: ");
 	}
 	
 // Displays the menu for choosing a data type
 	public static void displayTypeMenu() {
 		System.out.println("[1] - Member");
 		System.out.println("[2] - Equipment");
 		System.out.println("[3] - Warehouse");
 		System.out.println("[4] - Drone");
 		System.out.println("[5] - Drone Custom Order");
 		System.out.println("[6] - Equipment Custom Order");
 		System.out.println("[7] - Other Custom Order");
 		System.out.println("[8] - New Element Order");
 		System.out.println("[9] - Review");
 		System.out.println("[10] - City");
 		System.out.println("[0] - Go Back");
 		System.out.print("Enter a number: ");
 	}
 	
 	public static void displayReportMenu() {
 		System.out.println("[1] - Bad Weather Impacts");
 		System.out.println("[2] - Member Count");
 		System.out.println("[3] - Active Drones Per Warehouse");
 		System.out.println("[0] - Go Back");
 		System.out.print("Enter a number: ");
 	}
 	
 	// Gets the user to enter a string
 	public static String getString(Scanner input) {
 		String name = input.nextLine();
 		return name;
 	}
 	
 	// Get the user to enter a number
 	public static int getNum(Scanner input) {
 		int num = Integer.parseInt(input.nextLine());
 		return num;
 	}
 	
 	// Get the user to enter a longer number (this works with phone numbers)
  	public static long getLongNum(Scanner input) {
  		long num = Long.parseLong(input.nextLine());
  		return num;
  	}

    
    public static void main(String[] args) {
    // Declaring user input
    Scanner userInput = new Scanner(System.in);
    //Connecting to database
    Connection conn = initializeDB(DATABASE);
    			
    // Displays the main menu to start off, then asks the user to choose an option
    displayMainMenu();
    String mainChoice = userInput.nextLine();
    			
    String typeChoice;
    String sql;
    			
    // While the user does NOT enter 0 to quit.
    while (mainChoice != "0") {
    // Declaring a variable used for the user's choice of data type
    // (Member = 1 /Equipment = 2/Warehouse = 3/ Go Back = 0)
    // Also declaring variables for the data name and data I
    	switch (mainChoice) {
    	// When the user enters 0 at the main menu
    		case "0":
    				mainChoice = "0";
    						System.out.println();
    						System.out.println("Qutting...");
    						System.out.println();
    						break;
    					
    					// When the user enters 1 to add
    					case "1":
    						System.out.println();
    						System.out.println("Choose what to add.");
    						displayTypeMenu();
    						typeChoice = userInput.nextLine();
    						System.out.println();
    						switch(typeChoice){
    						
    							// If the user enters 0 to go back to the main menu
    							// Switch cases for what type of data the user wants to add
    							case "0":
    								displayMainMenu();
    								mainChoice = userInput.nextLine();
    								break;
    							case "1":
    								System.out.println("--- Add Member ---");
    								sql = "INSERT INTO Member (Member_ID, First_Name, Last_Name, Address, Email, Start_Date, Warehouse_Distance, Warehouse_Number, PhoneNum) VALUES (?,?,?,?,?,?,?,?,?)";
    								System.out.print("Enter the new member's ID: ");
    								String newMemberID = getString(userInput);
    								System.out.print("Enter the new member's first name: ");
    								String newFirstName = getString(userInput);
    								System.out.print("Enter the new member's last name: ");
    								String newLastName = getString(userInput);
    								System.out.print("Enter the new member's address: ");
    								String newAddress = getString(userInput);
    								System.out.print("Enter the new member's email: ");
    								String newEmail = getString(userInput);
    								System.out.print("Enter the new member's start date: ");
    								String newStartDate = getString(userInput);
    								System.out.print("Enter the new member's distance from the warehouse: ");
    								int newWarehouseDistance = getNum(userInput);
    								System.out.print("Enter the new member's warehouse number: ");
    								int newWarehouseNumber = getNum(userInput);
    								System.out.print("Enter the new member's phone number: ");
    								long newPhoneNumber = getLongNum(userInput);
    								try {
									PreparedStatement newMember = conn.prepareStatement(sql);
									newMember.setString(1, newMemberID);
									newMember.setString(2, newFirstName);
									newMember.setString(3, newLastName);
									newMember.setString(4, newAddress);
									newMember.setString(5, newEmail);
									newMember.setString(6, newStartDate);
									newMember.setInt(7, newWarehouseDistance);
									newMember.setInt(8, newWarehouseNumber);
									newMember.setLong(9, newPhoneNumber);
									newMember.execute();
									System.out.println("New member added.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    								
    								break;
    							case "2":
    								System.out.println("--- Add Equipment ---");
    								sql = "INSERT INTO Equipment(Inv_ID, Warehouse_Number, Description, Active_Category, Model_Number, Type, Year, Serial_Number, Arrival_Date, Warranty_Exp, Manufacturer, Weight, Size) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    								System.out.print("Enter the new inventory ID: ");
    								String e01 = getString(userInput);
    								System.out.print("Enter the warehouse number: ");
    								int e02 = getNum(userInput);
    								System.out.print("Enter the description: ");
    								String e03 = getString(userInput);
    								System.out.print("Enter the active cateogry: ");
    								String e04 = getString(userInput);
    								System.out.print("Enter the model number: ");
    								String e05 = getString(userInput);
    								System.out.print("Enter the type: ");
    								String e06 = getString(userInput);
    								System.out.print("Enter the active year: ");
    								int e07 = getNum(userInput);
    								System.out.print("Enter the serial number: ");
    								String e08 = getString(userInput);
    								System.out.print("Enter the arrival date: ");
    								String e09 = getString(userInput);
    								System.out.print("Enter the warranty expiration: ");
    								String e10 = getString(userInput);
    								System.out.print("Enter the manufacturer's name: ");
    								String e11 = getString(userInput);
    								System.out.print("Enter the weight: ");
    								int e12 = getNum(userInput);
    								System.out.print("Enter the size: ");
    								int e13 = getNum(userInput);
    								try {
    									PreparedStatement newEquip = conn.prepareStatement(sql);
    									newEquip.setString(1, e01);
    									newEquip.setInt(2, e02);
    									newEquip.setString(3, e03);
    									newEquip.setString(4, e04);
    									newEquip.setString(5, e05);
    									newEquip.setString(6, e06);
    									newEquip.setInt(7, e07);
    									newEquip.setString(8, e08);
    									newEquip.setString(9, e09);
    									newEquip.setString(10, e10);
    									newEquip.setString(11, e11);
    									newEquip.setInt(12, e12);
    									newEquip.setInt(13, e13);
    									newEquip.execute();
    									System.out.println("New equipment added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
    								break;
    							case "3":
    								System.out.println("--- Add Warehouse ---");
    								sql = "INSERT INTO Warehouse(Warehouse_Number, Manager_Name, Address, Phone_Num, Storage_Capacity, Drone_Capacity) VALUES (?,?,?,?,?,?)";
    								System.out.print("Enter the new warehouse number: ");
    								int newWarehouseNum = getNum(userInput);
    								System.out.print("Enter the new warehouse manager name: ");
    								String newManagerName = getString(userInput);
    								System.out.print("Enter the new warehouse address: ");
    								String newWarehouseAddress = getString(userInput);
    								System.out.print("Enter the new warehouse phone number: ");
    								long newWarehousePhoneNum = getLongNum(userInput);
    								System.out.print("Enter the new warehouse capacity: ");
    								String newWarehouseCapacity = getString(userInput);
    								System.out.print("Enter the new warehouse drone capacity: ");
    								int newWarehouseDroneCapacity = getNum(userInput);
    								try {
    									PreparedStatement newWarehouse = conn.prepareStatement(sql);
    									newWarehouse.setInt(1, newWarehouseNum);
    									newWarehouse.setString(2, newManagerName);
    									newWarehouse.setString(3, newWarehouseAddress);
    									newWarehouse.setLong(4, newWarehousePhoneNum);
    									newWarehouse.setString(5, newWarehouseCapacity);
    									newWarehouse.setInt(6, newWarehouseDroneCapacity);
    									newWarehouse.execute();
    									System.out.println("New warehouse added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
    								break;		
    							case "4":
    								System.out.println("--- Add Drone ---");
    								sql = "INSERT INTO Drone (Serial_Number, Warehouse, Description, Model_Number, Fleet_ID, Manufacturer, Status, Warranty_Exp) VALUES (?,?,?,?,?,?,?,?)";
    								System.out.print("Enter the new serial number: ");
    								String d01 = getString(userInput);
    								System.out.print("Enter the new warehouse number: ");
    								int d02 = getNum(userInput);
    								System.out.print("Enter the new description: ");
    								String d03 = getString(userInput);
    								System.out.print("Enter the new model number: ");
    								String d04 = getString(userInput);
    								System.out.print("Enter the new fleet ID: ");
    								String d05 = getString(userInput);
    								System.out.print("Enter the new manufacturer: ");
    								String d06 = getString(userInput);
    								System.out.print("Enter the new drone's status (1 = Active / 0 = Inactive): ");
    								int d07 = getNum(userInput);
    								System.out.print("Enter the new drone's manufacturer warranty expiration date: ");
    								String d08 = getString(userInput);
    								try {
    									PreparedStatement newDroneEntry = conn.prepareStatement(sql);
    									newDroneEntry.setString(1, d01);
    									newDroneEntry.setInt(2, d02);
    									newDroneEntry.setString(3, d03);
    									newDroneEntry.setString(4, d04);
    									newDroneEntry.setString(5, d05);
    									newDroneEntry.setString(6, d06);
    									newDroneEntry.setInt(7, d07);
    									newDroneEntry.setString(8, d08);
    									newDroneEntry.execute();
    									System.out.println("New drone added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
    							break;
    							case "5":
    								System.out.println("--- Add Drone Custom Order ---");
    								sql = "INSERT INTO Custom_Order_Drones (Order_ID, Drone_Number) VALUES (?,?)";
    								System.out.print("Enter the new order ID: ");
    								String do01 = getString(userInput);
    								System.out.print("Enter the new drone serial number: ");
    								String do02 = getString(userInput);
    								try {
    									PreparedStatement newDroneOrder = conn.prepareStatement(sql);
    									newDroneOrder.setString(1, do01);
    									newDroneOrder.setString(2, do02);
    									newDroneOrder.execute();
    									System.out.println("New drone custom order added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
        						break;
    							case "6":
    								System.out.println("--- Add Equipment Custom Order ---");
    								sql = "INSERT INTO Custom_Order_Equipment (Order_ID, Inv_ID) VALUES (?,?)";
    								System.out.print("Enter the new order ID: ");
    								String eo01 = getString(userInput);
    								System.out.print("Enter the new inventory ID: ");
    								String eo02 = getString(userInput);
    								try {
    									PreparedStatement newEquipOrder = conn.prepareStatement(sql);
    									newEquipOrder.setString(1, eo01);
    									newEquipOrder.setString(2, eo02);
    									newEquipOrder.execute();
    									System.out.println("New equipment custom order added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
        						break;
    							case "7":
    								System.out.println("--- Add Other Custom Order ---");
    								sql = "INSERT INTO Custom_Order (Order_ID, Member_ID, Description, Value, ETA, Due_Date, Fee_Charged) VALUES (?,?,?,?,?,?,?)";
    								System.out.print("Enter the new order ID: ");
    								String co01 = getString(userInput);
    								System.out.print("Enter the new member ID: ");
    								String co02 = getString(userInput);
    								System.out.print("Enter the new description: ");
    								String co03 = getString(userInput);
    								System.out.print("Enter the new value: ");
    								int co04 = getNum(userInput);
    								System.out.print("Enter the new ETA: ");
    								String co05 = getString(userInput);
    								System.out.print("Enter the new due date: ");
    								String co06 = getString(userInput);
    								System.out.print("Enter the new fee charged: ");
    								int co07 = getNum(userInput);
    								try {
    									PreparedStatement newCustomOrder = conn.prepareStatement(sql);
    									newCustomOrder.setString(1, co01);
    									newCustomOrder.setString(2, co02);
    									newCustomOrder.setString(3, co03);
    									newCustomOrder.setInt(4, co04);
    									newCustomOrder.setString(5, co05);
    									newCustomOrder.setString(6, co06);
    									newCustomOrder.setInt(7, co07);
    									newCustomOrder.execute();
    									System.out.println("New other custom order added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
    								break;
    							case "8":
    								System.out.println("--- Add New Element Order ---");
    								sql = "INSERT INTO New_Elements_Ordered (Order_ID, Description, Quantity, Value, ETA, Arrival_Date, Warehouse_Number) VALUES (?,?,?,?,?,?,?)";
    								System.out.print("Enter the new order ID: ");
    								String el01 = getString(userInput);
    								System.out.print("Enter the new description: ");
    								String el02 = getString(userInput);
    								System.out.print("Enter the new quantity: ");
    								int el03 = getNum(userInput);
    								System.out.print("Enter the new value: ");
    								int el04 = getNum(userInput);
    								System.out.print("Enter the new ETA: ");
    								String el05 = getString(userInput);
    								System.out.print("Enter the new arrival date: ");
    								String el06 = getString(userInput);
    								System.out.print("Enter the new warehouse number: ");
    								int el07 = getNum(userInput);
    								try {
    									PreparedStatement newElementOrder = conn.prepareStatement(sql);
    									newElementOrder.setString(1, el01);
    									newElementOrder.setString(2, el02);
    									newElementOrder.setInt(3, el03);
    									newElementOrder.setInt(4, el04);
    									newElementOrder.setString(5, el05);
    									newElementOrder.setString(6, el06);
    									newElementOrder.setInt(7, el07);
    									newElementOrder.execute();
    									System.out.println("New element order added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
    								break;
    							case "9":
    								System.out.println("--- Add New Review ---");
    								sql = "INSERT INTO Review (Date, Title, Content, Rate, Order_ID) VALUES (?,?,?,?,?)";
    								System.out.print("Enter the new date: ");
    								String r01 = getString(userInput);
    								System.out.print("Enter the new title: ");
    								String r02 = getString(userInput);
    								System.out.print("Enter the new content: ");
    								String r03 = getString(userInput);
    								System.out.print("Enter the new rating: ");
    								int r04 = getNum(userInput);
    								System.out.print("Enter the new order ID: ");
    								String r05 = getString(userInput);
    								try {
    									PreparedStatement newReview = conn.prepareStatement(sql);
    									newReview.setString(1, r01);
    									newReview.setString(2, r02);
    									newReview.setString(3, r03);
    									newReview.setInt(4, r04);
    									newReview.setString(5, r05);
    									newReview.execute();
    									System.out.println("New review added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
    								break;
    							case "10":
    								System.out.println("--- Add New City ---");
    								sql = "INSERT INTO City (City_Name, State) VALUES (?,?)";
    								System.out.print("Enter the new city: ");
    								String c01 = getString(userInput);
    								System.out.print("Enter the new state: ");
    								String c02 = getString(userInput);
    								try {
    									PreparedStatement newCity = conn.prepareStatement(sql);
    									newCity.setString(1, c01);
    									newCity.setString(2, c02);
    									newCity.execute();
    									System.out.println("New city added.");
        								} catch (SQLException e) {
    									e.printStackTrace();
        								}
    								break;
    							default: 
    								System.out.println("Error: Invalid Input");
    								break;						
    						}
    						
    						break;
    					
    					// When the user enters 2 at the main menu
    					// Switch cases for what type of data the user wants to edit
    					case "2":
    						System.out.println();
    						System.out.println("Choose what to edit.");
    						displayTypeMenu();
    						typeChoice = userInput.nextLine();
    						System.out.println();
    						switch(typeChoice){
    						case "0":
    							displayMainMenu();
    							mainChoice = userInput.nextLine();
    							break;
    						case "1":
    							System.out.println("--- Edit Member ---");
    							System.out.print("Enter the member's ID to edit: ");
								String currentMemberID = getString(userInput);
								sql = "UPDATE Member SET Member_ID = ?, First_Name = ?, Last_Name = ?, Address = ?, Email = ?, Start_Date = ?, Warehouse_Distance = ?, Warehouse_Number = ?, PhoneNum = ? WHERE Member_ID = '"+currentMemberID+"';";
								System.out.print("Enter the new member's ID: ");
								String newMemberID = getString(userInput);
								System.out.print("Enter the new member's first name: ");
								String newFirstName = getString(userInput);
								System.out.print("Enter the new member's last name: ");
								String newLastName = getString(userInput);
								System.out.print("Enter the new member's address: ");
								String newAddress = getString(userInput);
								System.out.print("Enter the new member's email: ");
								String newEmail = getString(userInput);
								System.out.print("Enter the new member's start date: ");
								String newStartDate = getString(userInput);
								System.out.print("Enter the new member's distance from the warehouse: ");
								int newWarehouseDistance = getNum(userInput);
								System.out.print("Enter the new member's warehouse number: ");
								int newWarehouseNumber = getNum(userInput);
								System.out.print("Enter the new member's phone number: ");
								long newPhoneNumber = getLongNum(userInput);
								try {
								PreparedStatement newMember = conn.prepareStatement(sql);
								newMember.setString(1, newMemberID);
								newMember.setString(2, newFirstName);
								newMember.setString(3, newLastName);
								newMember.setString(4, newAddress);
								newMember.setString(5, newEmail);
								newMember.setString(6, newStartDate);
								newMember.setInt(7, newWarehouseDistance);
								newMember.setInt(8, newWarehouseNumber);
								newMember.setLong(9, newPhoneNumber);
								newMember.execute();
								System.out.println("Member info edited.");
								} catch (SQLException e) {
								e.printStackTrace();
								}
    							break;
    						case "2":
    							System.out.println("--- Edit Equipment ---");
    							System.out.print("Enter the inventory ID to edit: ");
								String e00 = getString(userInput);
    							sql = "UPDATE Equipment SET Inv_ID = ?, Warehouse_Number = ?, Description = ?, Active_Category = ?, Model_Number = ?, Type = ?, Year = ?, Serial_Number = ?, Arrival_Date = ?, Warranty_Exp = ?, Manufacturer = ?, Weight = ?, Size = ? WHERE Inv_ID = '"+e00+"';";
								System.out.print("Enter the new inventory ID: ");
								String e01 = getString(userInput);
								System.out.print("Enter the warehouse number: ");
								int e02 = getNum(userInput);
								System.out.print("Enter the description: ");
								String e03 = getString(userInput);
								System.out.print("Enter the active cateogry: ");
								String e04 = getString(userInput);
								System.out.print("Enter the model number: ");
								String e05 = getString(userInput);
								System.out.print("Enter the type: ");
								String e06 = getString(userInput);
								System.out.print("Enter the active year: ");
								int e07 = getNum(userInput);
								System.out.print("Enter the serial number: ");
								String e08 = getString(userInput);
								System.out.print("Enter the arrival date: ");
								String e09 = getString(userInput);
								System.out.print("Enter the warranty expiration: ");
								String e10 = getString(userInput);
								System.out.print("Enter the manufacturer's name: ");
								String e11 = getString(userInput);
								System.out.print("Enter the weight: ");
								int e12 = getNum(userInput);
								System.out.print("Enter the size: ");
								int e13 = getNum(userInput);
								try {
									PreparedStatement newEquip = conn.prepareStatement(sql);
									newEquip.setString(1, e01);
									newEquip.setInt(2, e02);
									newEquip.setString(3, e03);
									newEquip.setString(4, e04);
									newEquip.setString(5, e05);
									newEquip.setString(6, e06);
									newEquip.setInt(7, e07);
									newEquip.setString(8, e08);
									newEquip.setString(9, e09);
									newEquip.setString(10, e10);
									newEquip.setString(11, e11);
									newEquip.setInt(12, e12);
									newEquip.setInt(13, e13);
									newEquip.execute();
									System.out.println("Equipment info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "3":
    							System.out.println("--- Edit Warehouse ---");
    							System.out.print("Enter the warehouse number to edit: ");
								int currentWarehouseNum = getNum(userInput);
    							sql = "UPDATE Warehouse SET Warehouse_Number = ?, Manager_Name = ?, Address = ?, Phone_Num = ?, Storage_Capacity = ?, Drone_Capacity = ? WHERE Warehouse_Number = '"+currentWarehouseNum+"';";
								System.out.print("Enter the new warehouse number: ");
								int newWarehouseNum = getNum(userInput);
								System.out.print("Enter the new warehouse manager name: ");
								String newManagerName = getString(userInput);
								System.out.print("Enter the new warehouse address: ");
								String newWarehouseAddress = getString(userInput);
								System.out.print("Enter the new warehouse phone number: ");
								long newWarehousePhoneNum = getLongNum(userInput);
								System.out.print("Enter the new warehouse capacity: ");
								String newWarehouseCapacity = getString(userInput);
								System.out.print("Enter the new warehouse drone capacity: ");
								int newWarehouseDroneCapacity = getNum(userInput);
								try {
									PreparedStatement newWarehouse = conn.prepareStatement(sql);
									newWarehouse.setInt(1, newWarehouseNum);
									newWarehouse.setString(2, newManagerName);
									newWarehouse.setString(3, newWarehouseAddress);
									newWarehouse.setLong(4, newWarehousePhoneNum);
									newWarehouse.setString(5, newWarehouseCapacity);
									newWarehouse.setInt(6, newWarehouseDroneCapacity);
									newWarehouse.execute();
									System.out.println("Warehouse info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "4":
    							System.out.println("--- Edit Drone ---");
    							System.out.print("Enter the current drone serial number to edit: ");
								String d00 = getString(userInput);
    							sql = "UPDATE Drone SET Serial_Number = ?, Warehouse = ?, Description = ?, Model_Number = ?, Fleet_ID = ?, Manufacturer = ?, Status = ?, Warranty_Exp = ? WHERE Serial_Number = '"+d00+"';";
								System.out.print("Enter the new serial number: ");
								String d01 = getString(userInput);
								System.out.print("Enter the new warehouse number: ");
								int d02 = getNum(userInput);
								System.out.print("Enter the new description: ");
								String d03 = getString(userInput);
								System.out.print("Enter the new model number: ");
								String d04 = getString(userInput);
								System.out.print("Enter the new fleet ID: ");
								String d05 = getString(userInput);
								System.out.print("Enter the new manufacturer: ");
								String d06 = getString(userInput);
								System.out.print("Enter the new drone's status (1 = Active / 0 = Inactive): ");
								int d07 = getNum(userInput);
								System.out.print("Enter the new warranty expiration date: ");
								String d08 = getString(userInput);
								try {
									PreparedStatement newDroneEntry = conn.prepareStatement(sql);
									newDroneEntry.setString(1, d01);
									newDroneEntry.setInt(2, d02);
									newDroneEntry.setString(3, d03);
									newDroneEntry.setString(4, d04);
									newDroneEntry.setString(5, d05);
									newDroneEntry.setString(6, d06);
									newDroneEntry.setInt(7, d07);
									newDroneEntry.setString(8, d08);
									newDroneEntry.execute();
									System.out.println("Drone info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "5":
    							System.out.println("--- Edit Drone Custom Order ---");
    							System.out.print("Enter the current drone order ID: ");
								String do00 = getString(userInput);
    							sql = "UPDATE Custom_Order_Drones SET Order_ID = ?, Drone_Number = ? WHERE Order_ID = '"+do00+"';";
								System.out.print("Enter the new order ID: ");
								String do01 = getString(userInput);
								System.out.print("Enter the new drone serial number: ");
								String do02 = getString(userInput);
								try {
									PreparedStatement newDroneOrder = conn.prepareStatement(sql);
									newDroneOrder.setString(1, do01);
									newDroneOrder.setString(2, do02);
									newDroneOrder.execute();
									System.out.println("Drone custom order info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "6":
    							System.out.println("--- Edit Equipment Custom Order ---");
    							System.out.print("Enter the current equipment order ID: ");
								String eo00 = getString(userInput);
    							sql = "UPDATE Custom_Order_Equipment SET Order_ID = ?, Inv_ID = ? WHERE Order_ID = '"+eo00+"';";
								System.out.print("Enter the new order ID: ");
								String eo01 = getString(userInput);
								System.out.print("Enter the new inventory ID: ");
								String eo02 = getString(userInput);
								try {
									PreparedStatement newEquipOrder = conn.prepareStatement(sql);
									newEquipOrder.setString(1, eo01);
									newEquipOrder.setString(2, eo02);
									newEquipOrder.execute();
									System.out.println("Equipment custom order info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "7":
    							System.out.println("--- Edit Other Custom Order ---");
    							System.out.print("Enter the current other order ID: ");
								String co00 = getString(userInput);
    							sql = "UPDATE Custom_Order SET Order_ID = ?, Member_ID = ?, Description = ?, Value = ?, ETA = ?, Due_Date = ?, Fee_Charged = ? WHERE Order_ID = '"+co00+"';";
								System.out.print("Enter the new order ID: ");
								String co01 = getString(userInput);
								System.out.print("Enter the new member ID: ");
								String co02 = getString(userInput);
								System.out.print("Enter the new description: ");
								String co03 = getString(userInput);
								System.out.print("Enter the new value: ");
								int co04 = getNum(userInput);
								System.out.print("Enter the new ETA: ");
								String co05 = getString(userInput);
								System.out.print("Enter the new due date: ");
								String co06 = getString(userInput);
								System.out.print("Enter the new fee charged: ");
								int co07 = getNum(userInput);
								try {
									PreparedStatement newCustomOrder = conn.prepareStatement(sql);
									newCustomOrder.setString(1, co01);
									newCustomOrder.setString(2, co02);
									newCustomOrder.setString(3, co03);
									newCustomOrder.setInt(4, co04);
									newCustomOrder.setString(5, co05);
									newCustomOrder.setString(6, co06);
									newCustomOrder.setInt(7, co07);
									newCustomOrder.execute();
									System.out.println("Other custom order info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "8":
    							System.out.println("--- Edit New Element Order ---");
    							System.out.print("Enter the current element order ID: ");
								String el00 = getString(userInput);
    							sql = "UPDATE New_Elements_Ordered SET Order_ID = ?, Description = ?, Quantity = ?, Value = ?, ETA = ?, Arrival_Date = ?, Warehouse_Number = ? WHERE Order_ID = '"+el00+"';";
								System.out.print("Enter the new order ID: ");
								String el01 = getString(userInput);
								System.out.print("Enter the new description: ");
								String el02 = getString(userInput);
								System.out.print("Enter the new quantity: ");
								int el03 = getNum(userInput);
								System.out.print("Enter the new value: ");
								int el04 = getNum(userInput);
								System.out.print("Enter the new ETA: ");
								String el05 = getString(userInput);
								System.out.print("Enter the new arrival date: ");
								String el06 = getString(userInput);
								System.out.print("Enter the new warehouse number: ");
								int el07 = getNum(userInput);
								try {
									PreparedStatement newElementOrder = conn.prepareStatement(sql);
									newElementOrder.setString(1, el01);
									newElementOrder.setString(2, el02);
									newElementOrder.setInt(3, el03);
									newElementOrder.setInt(4, el04);
									newElementOrder.setString(5, el05);
									newElementOrder.setString(6, el06);
									newElementOrder.setInt(7, el07);
									newElementOrder.execute();
									System.out.println("Element order info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "9":
    							System.out.println("--- Edit Review ---");
    							System.out.print("Enter the current review order ID: ");
								String r00 = getString(userInput);
    							sql = "UPDATE Review SET Date = ?, Title = ?, Content = ?, Rate = ?, Order_ID = ? WHERE Order_ID = '"+r00+"';";
								System.out.print("Enter the new date: ");
								String r01 = getString(userInput);
								System.out.print("Enter the new title: ");
								String r02 = getString(userInput);
								System.out.print("Enter the new content: ");
								String r03 = getString(userInput);
								System.out.print("Enter the new rating: ");
								int r04 = getNum(userInput);
								System.out.print("Enter the new order ID: ");
								String r05 = getString(userInput);
								try {
									PreparedStatement newReview = conn.prepareStatement(sql);
									newReview.setString(1, r01);
									newReview.setString(2, r02);
									newReview.setString(3, r03);
									newReview.setInt(4, r04);
									newReview.setString(5, r05);
									newReview.execute();
									System.out.println("Review info edited.");
    								} catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						case "10":
    							System.out.println("--- Edit City ---");
    							System.out.print("Enter the current city to edit: ");
								String c00 = getString(userInput);
								System.out.print("Enter the current state to edit: ");
								String c000 = getString(userInput);
								sql = "UPDATE City SET City_Name = ?, State = ? WHERE City_Name = '"+c00+"' AND State = '"+c000+"';";
								System.out.print("Enter the new city: ");
								String c01 = getString(userInput);
								System.out.print("Enter the new state: ");
								String c02 = getString(userInput);
								try {
									PreparedStatement newCity = conn.prepareStatement(sql);
									newCity.setString(1, c01);
									newCity.setString(2, c02);
									newCity.execute();
									System.out.println("City info edited.");
    								} 
								catch (SQLException e) {
									e.printStackTrace();
    								}
    							break;
    						default: 
    							System.out.println("Error: Invalid Input");
    							break;
    						}
    						break;
    					
    					// When the user enters 3 at the main menu
    					// Switch cases for what type of data the user wants to delete
    					case "3":
    						System.out.println();
    						System.out.println("Choose what to delete.");
    						displayTypeMenu();
    						typeChoice = userInput.nextLine();
    						System.out.println();
    						switch(typeChoice){
    						case "0":
    							displayMainMenu();
    							mainChoice = userInput.nextLine();
    							break;
    						case "1":
    							System.out.println("--- Delete Member ---");
    							System.out.print("Enter a member ID to delete: ");
    							String enteredMemId = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Member WHERE Member_ID = '"+enteredMemId+"';");
    							break;
    						case "2":
    							System.out.println("--- Delete Equipment ---");
    							System.out.print("Enter an inventory ID to delete: ");
    							String enteredInvId = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Equipment WHERE Inv_ID = '"+enteredInvId+"';");
    							break;
    						case "3":
    							System.out.println("--- Delete Warehouse ---");
    							System.out.print("Enter a warehouse number to delete: ");
    							String enteredWarNum = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Warehouse WHERE Warehouse_Number = '"+enteredWarNum+"';");
    							break;
    						case "4":
    							System.out.println("--- Delete Drone ---");
    							System.out.print("Enter a drone serial number to delete: ");
    							String enteredDroneSerNum = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Drone WHERE Serial_Number = '"+enteredDroneSerNum+"';");
    							break;
    						case "5":
    							System.out.println("--- Delete Drone Custom Order ---");
    							System.out.print("Enter a drone order ID to delete: ");
    							String enteredDroneOrderId = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Custom_Order_Drones WHERE Order_ID = '"+enteredDroneOrderId+"';");
    							break;
    						case "6":
    							System.out.println("--- Delete Equipment Custom Order ---");
    							System.out.print("Enter an equipmdent order ID to delete: ");
    							String enteredEqOrdId = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Custom_Order_Equipment WHERE Order_ID = '"+enteredEqOrdId+"';");
    							break;
    						case "7":
    							System.out.println("--- Delete Other Custom Order ---");
    							System.out.print("Enter an other custom order ID to delete: ");
    							String enteredOtherCusOrdId = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Custom_Order WHERE Order_ID = '"+enteredOtherCusOrdId+"';");
    							break;
    						case "8":
    							System.out.println("--- Delete New Element Order ---");
    							System.out.print("Enter a new element order ID to delete: ");
    							String enteredElmOrdId = getString(userInput);
    							sqlQuery(conn, "DELETE FROM New_Elements_Ordered WHERE Order_ID = '"+enteredElmOrdId+"';");
    							break;
    						case "9":
    							System.out.println("--- Delete Review ---");
    							System.out.print("Enter a review order ID to delete: ");
    							String enteredReviewOrdId = getString(userInput);
    							sqlQuery(conn, "DELETE FROM Review WHERE Order_ID = '"+enteredReviewOrdId+"';");
    							break;
    						case "10":
    							System.out.println("--- Delete City ---");
    							System.out.print("Enter a city to delete: ");
    							String enteredCity = getString(userInput);
    							System.out.print("Enter a state to delete: ");
    							String enteredState = getString(userInput);
    							sqlQuery(conn, "DELETE FROM City WHERE City_Name = '"+enteredCity+"' AND State = '"+enteredState+"';");
    							break;
    						default: 
    							System.out.println("Error: Invalid Input");
    							break;
    						}
    						break;
    					
    					// When the user enters 4 at the main menu
    					// Switch cases for what type of data the user wants to search
    					case "4":
    						System.out.println();
    						System.out.println("Choose what to search.");
    						displayTypeMenu();
    						typeChoice = userInput.nextLine();
    						System.out.println();
    						String x;
    						switch(typeChoice){
    						case "0":
    							displayMainMenu();
    							mainChoice = userInput.nextLine();
    						break;
    						case "1":
    							System.out.println("--- Search Members ---");
    							System.out.print("Enter a member ID to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Member WHERE Member_ID = '"+x+"';");
    							break;
    						case "2":
    							System.out.println("--- Search Equipment ---");
    							System.out.print("Enter an inventory ID to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Equipment WHERE Inv_ID = '"+x+"';");
    							break;
    						case "3":
    							System.out.println("--- Searchs Warehouse ---");
    							System.out.print("Enter a warehouse number to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Warehouse WHERE Warehouse_Number = '"+x+"';");
    							break;
    						case "4":
    							System.out.println("--- Search Drones ---");
    							System.out.print("Enter a drone serial number to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Drone WHERE Serial_Number = '"+x+"';");
    							break;
    						case "5":
    							System.out.println("--- Search Drone Custom Orders ---");
    							System.out.print("Enter a drone order ID to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Custom_Order_Drones WHERE Order_ID = '"+x+"';");
    							break;
    						case "6":
    							System.out.println("--- Search Equipment Custom Orders ---");
    							System.out.print("Enter an equipment order ID to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Custom_Order_Equipment WHERE Order_ID = '"+x+"';");
    							break;
    						case "7":
    							System.out.println("--- Search Other Custom Orders ---");
    							System.out.print("Enter a custom order ID to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Custom_Order WHERE Order_ID = '"+x+"';");
    							break;
    						case "8":
    							System.out.println("--- Search New Element Orders ---");
    							System.out.print("Enter a new element order ID to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM New_Elements_Ordered WHERE Order_ID = '"+x+"';");
    							break;
    						case "9":
    							System.out.println("--- Search Reviews ---");
    							System.out.print("Enter a review order ID to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM Review WHERE Order_ID = '"+x+"';");
    							break;
    						case "10":
    							System.out.println("--- Search Cities ---");
    							System.out.print("Enter a state to search by: ");
    							x = getString(userInput);
    							sqlSelectQuery(conn, "SELECT * FROM City WHERE State = '"+x+"';");
    							break;
    						default: 
    							System.out.println("Error: Invalid Input");
    						break;
    						}
    						break;
    						
    					case "5":
    						System.out.println();
    						System.out.println("Choose what to view.");
    						displayTypeMenu();
    						typeChoice = userInput.nextLine();
    						System.out.println();
    						switch(typeChoice){
    						
    							// If the user enters 0 to go back to the main menu
    							// Switch cases for what type of data the user wants to view
    							case "0":
    								displayMainMenu();
    								mainChoice = userInput.nextLine();
    								break;
    							case "1":
    								System.out.println("--- Viewing All Members ---");
    								sqlSelectQuery(conn, "SELECT * FROM Member;");
    								break;
    							case "2":
    								System.out.println("--- Viewing All Equipment ---");
    								sqlSelectQuery(conn, "SELECT * FROM Equipment;");
    								break;
    							case "3":
    								System.out.println("--- Viewing All Warehouses ---");
    								sqlSelectQuery(conn, "SELECT * FROM Warehouse;");
    								break;
    							case "4":
    								System.out.println("--- Viewing All Drones---");
    								sqlSelectQuery(conn, "SELECT * FROM Drone;");
    								break;	
    							case "5":
    								System.out.println("--- Viewing All Drone Custom Orders---");
    								sqlSelectQuery(conn, "SELECT * FROM Custom_Order_Drones;");
    								break;
    							case "6":
    								System.out.println("--- Viewing All Equipment Custom Orders---");
    								sqlSelectQuery(conn, "SELECT * FROM Custom_Order_Equipment;");
    								break;
    							case "7":
    								System.out.println("--- Viewing All Other Custom Orders---");
    								sqlSelectQuery(conn, "SELECT * FROM Custom_Order;");
    								break;
    							case "8":
    								System.out.println("--- Viewing All New Element Orders---");
    								sqlSelectQuery(conn, "SELECT * FROM New_Elements_Ordered;");
    								break;
    							case "9":
    								System.out.println("--- Viewing All Reviews---");
    								sqlSelectQuery(conn, "SELECT * FROM Review;");
    								break;
    							case "10":
    								System.out.println("--- Viewing All Cities---");
    								sqlSelectQuery(conn, "SELECT * FROM City;");
    								break;
    							case "w":
    								System.out.println("--- Viewing All Weather---");
    								sqlSelectQuery(conn, "SELECT * FROM Date;");
    								break;
    							default: 
    								System.out.println("Error: Invalid Input");
    								break;						
    						}
    						break;
    					case "6": 
    						System.out.println();
    						System.out.println("Choose a report.");
    						displayReportMenu();
    						typeChoice = userInput.nextLine();
    						System.out.println();
    						switch(typeChoice){
    						case "0":
								displayMainMenu();
								mainChoice = userInput.nextLine();
								break;
    						case "1":
    							System.out.println("---Potential Bad Weather---");
    							System.out.println("This report shows custom orders which may have bad weather impacting their deliveries.");
								sqlSelectQuery(conn, "SELECT Order_ID FROM Custom_Order WHERE ETA IN (SELECT Arrival_Date From Date WHERE precip > .40);");
    							break;
    						case "2":
    							System.out.println("---Member Count---");
    							System.out.println("This report counts how many members are active per warehouse.");
								sqlSelectQuery(conn, "SELECT W.Warehouse_Number, COUNT(M.Member_ID) FROM Warehouse AS W, Member AS M WHERE W.Warehouse_Number = M.Warehouse_Number GROUP BY W.Warehouse_Number");
    							break;
    						case "3":
    							System.out.println("---Active Drones Per Warehouse---");
    							System.out.println("This report shows how many active drones there are per warehouse.");
    							System.out.println("(Not including warehouses with no active drones)");
								sqlSelectQuery(conn, "SELECT W.Warehouse_Number, W.Drone_Capacity, COUNT(D.Status) FROM Warehouse AS W, Drone AS D WHERE W.Warehouse_Number = D.Warehouse AND D.Status = 1 GROUP BY W.Warehouse_Number;");
    							break;
    						default:
    							System.out.println("Error: Invalid Input");
    							break;
    						}
    						break;
    						
    					// For when the user enters a value not accepted by the main menu			
    					default:
    						System.out.println("Error: Invalid Input");
    						System.out.println();
    						displayMainMenu();
    						mainChoice = userInput.nextLine();
    						break;
    				}
    			}
    			userInput.close();
    }
}
