import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class project {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		if (args.length != 0) {
			if (args[0].equals("CreateItem") && args.length==4) {
				CreateItem(args[1],args[2], Double.parseDouble(args[3]));
			} else if (args[0].equals("CreatePurchase")&&args.length==3) {
				CreatePurchase(args[1],args[2]);
			} else if (args[0].equals("CreateShipment")&&args.length==4) {
				CreateShipment(args[1],args[2], args[3]);
			}  else if (args[0].equals("GetItems") && args.length==2) {
				GetItems(args[1]);
			} else if (args[0].equals("GetShipments") && args.length==2) {
				GetShipments(args[1]);
			} else if (args[0].equals("GetPurchases") && args.length==2) {
				GetPurchases(args[1]);
			} else if (args[0].equals("ItemsAvailable")&&args.length==2) {
				ItemsAvailable(args[1]);
			} else if (args[0].equals("UpdateItem")&&args.length==3) {
				UpdateItem(args[1],Double.parseDouble(args[2]));
			} else if (args[0].equals("DeleteItem")&&args.length==2) {
				DeleteItem(args[1]);
			} else if (args[0].equals("DeleteShipment")&&args.length==2) {
				DeleteShipment(args[1]);
			} else if (args[0].equals("DeletePurchase")&&args.length==2) {
				DeletePurchase(args[1]);
			} else {
				printUsage();
			}
		}
		//TEST
		 Connection con = getConn();
		 Statement stmt = con.createStatement();
		 ResultSet result_set = stmt.executeQuery("Select * from Item;");
		 System.out.println(result_set.getRow());
		
		 closeConn(con);
		//END TEST
	}
	
	private static Connection getConn() {
		Connection con = null;
		String db_host = "127.0.0.1";
		String db_port = "52956";
		String db_name = "concessions";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String databaseURL = String.format("jdbc:mysql://%s:%s/%s?verifyServerCertificate=false&useSSL=true", db_host, db_port, db_name);
		try {
			con = DriverManager.getConnection(databaseURL,"msandbox", "Yveltal25");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	private static void closeConn(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void printUsage() {
		System.out.println("Usage");
	}
	//ACESSING QUERIES:
	//ResultSet rs = stmt.executeQuery("[QUERY]");
	
	
	//EXAMPLE java Project CreateItem LSoda "Large Soda" 5.50
	private static void CreateItem(String itemCode, String itemDescription, double price) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		rs = stmt.executeQuery("INSERT INTO Item(itemCode, itemDescription, price)" + 
				"VALUES('"+itemCode+"', '"+itemDescription+"', '"+price+"');");
		//TODO
		//PRINT
	}
	
	private static void CreatePurchase(String itemCode, String purchaseQuantity) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		rs =stmt.executeQuery("INSERT INTO Purchase(Quantity, ItemID)"+
				"VALUES('"+purchaseQuantity+"',"+
						"(SELECT ID from Item"+
						"WHERE itemCode = '"+itemCode+"'));");
		//TODO PRINT
	}
	
	private static void CreateShipment(String itemCode, String ShipmentQuantity, String shipmentDate) throws SQLException {
		
	}
	
	// DONE
	private static void GetItems(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if(itemCode.equals("%")) {
			rs = stmt.executeQuery("SELECT * FROM Item;");
		}else {
			String query = "SELECT * FROM Item WHERE ItemCode = "+"'"+itemCode+"';";
			System.out.println("Executing query: " + query);
			rs = stmt.executeQuery(query);
		}
		PrintItemResults("%", rs);
	}
	
	// DONE
	private static void GetShipments(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if(itemCode.equals("%")) {
			rs = stmt.executeQuery("SELECT * FROM Shipment;");
		}else {
			String query = "SELECT s.* FROM Shipment s JOIN Item i on s.ItemID = i.ID WHERE i.ItemCode = "+"'"+itemCode+"';";
			System.out.println("Executing query: " + query);
			rs = stmt.executeQuery(query);
		}
		PrintShipmentResults("%", rs);
	}
	
	// DONE
	private static void GetPurchases(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if(itemCode.equals("%")) {
			rs = stmt.executeQuery("SELECT * FROM Purchase;");
		}else {
			String query = "SELECT p.* FROM Purchase p JOIN Item i on p.ItemID = i.ID WHERE i.ItemCode = "+"'"+itemCode+"';";
			System.out.println("Executing query: " + query);
			rs = stmt.executeQuery(query);
		}
		PrintPurchaseResults("%", rs);
	}
	
	//MOST COMPLEX METHOD
	private static void ItemsAvailable(String itemCode) throws SQLException {
		
	}
	
	private static void UpdateItem(String itemCode, double price) throws SQLException {
		
	}
	
	private static void DeleteItem(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		rs = stmt.executeQuery("DELETE FROM Item WHERE itemCode = '"+itemCode+"';");
		//TODO print
		
	}
	
	private static void DeleteShipment(String itemCode) throws SQLException {
		
	}
	
	private static void DeletePurchase(String itemCode) throws SQLException {
		
	}
	
	private static void PrintPurchaseResults(String columns, ResultSet rs) throws SQLException {
		while(rs.next()){
			int id = rs.getInt("ID");
			int itemID = rs.getInt("ItemID");
			int quantity = rs.getInt("Quantity");
			Date date = rs.getDate("PurchaseDate");
			
			System.out.println("ID " + id);
			System.out.println("ItemID " + itemID);
			System.out.println("Quantity " + quantity);
			System.out.println("PurchaseDate " + date);

		}
	}
	
	private static void PrintShipmentResults(String columns, ResultSet rs) throws SQLException {
		while(rs.next()){
			int id = rs.getInt("ID");
			int itemID = rs.getInt("ItemID");
			int quantity = rs.getInt("Quantity");
			Date date = rs.getDate("ShipmentDate");
			
			System.out.println("ID " + id);
			System.out.println("ItemID " + itemID);
			System.out.println("Quantity " + quantity);
			System.out.println("ShipmentDate " + date);

		}
	}
	
	private static void PrintItemResults(String columns, ResultSet rs) throws SQLException {
		while(rs.next()){
			int id = rs.getInt("ID");
			String itemCode = rs.getString("ItemCode");
			String description = rs.getString("ItemDescription");
			Double price = rs.getDouble("Price");
			
			System.out.println("ID " + id);
			System.out.println("ItemCode " + itemCode);
			System.out.println("ItemDescription " + description);
			System.out.println("Price " + price);

		}
	}
}

