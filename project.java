import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class project {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		dao db = new dao();
		if (args.length != 0) {
			if (args[0].equals("CreateItem") && args.length == 4) {
				db.CreateItem(args[1], args[2], Double.parseDouble(args[3]));
			} else if (args[0].equals("CreatePurchase") && args.length == 3) {
				db.CreatePurchase(args[1], args[2]);
			} else if (args[0].equals("CreateShipment") && args.length == 4) {
				db.CreateShipment(args[1], args[2], args[3]);
			} else if (args[0].equals("GetItems") && args.length == 2) {
				db.GetItems(args[1]);
			} else if (args[0].equals("GetShipments") && args.length == 2) {
				db.GetShipments(args[1]);
			} else if (args[0].equals("GetPurchases") && args.length == 2) {
				db.GetPurchases(args[1]);
			} else if (args[0].equals("ItemsAvailable") && args.length == 2) {
				db.ItemsAvailable(args[1]);
			} else if (args[0].equals("UpdateItem") && args.length == 3) {
				db.UpdateItem(args[1], Double.parseDouble(args[2]));
			} else if (args[0].equals("DeleteItem") && args.length == 2) {
				db.DeleteItem(args[1]);
			} else if (args[0].equals("DeleteShipment") && args.length == 2) {
				db.DeleteShipment(args[1]);
			} else if (args[0].equals("DeletePurchase") && args.length == 2) {
				db.DeletePurchase(args[1]);
			} else {
				printUsage();
			}
		}
	}

	public static void printUsage() {
		System.out.println("Please use a one of the following (without brackets):\n"+
		"java project /?\n"+
		"java project CreateItem <itemCode> <itemDescription> <price>\n"+
		"java project CreatePurchase <itemCode> <PurchaseQuantity> \n"+
		"java project CreateShipment <itemCode> <ShipmentQuantity>  <shipmentDate>\n"+
		"java project GetItems <itemCode>\n"+
		"java project GetShipments <itemCode>\n"+
		"java project GetPurchases <itemCode>\n"+
		"java project ItemsAvailable <itemCode>\n"+
		"java project UpdateItem <itemCode> <price>\n"+
		"java project DeleteItem <itemCode>\n"+
		"java project DeleteShipment <itemCode>\n"+
		"java project DeletePurchase <itemCode>");
	}
}

class dao {
	private Connection getConn() {
		Connection con = null;
		String db_host = "127.0.0.1";
		String db_port = "52956";
		String db_name = "concessions";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String databaseURL = String.format("jdbc:mysql://%s:%s/%s?verifyServerCertificate=false&useSSL=true", db_host,
				db_port, db_name);
		try {
			con = DriverManager.getConnection(databaseURL, "msandbox", "Yveltal25");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	private void closeConn(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DONE
	public void CreateItem(String itemCode, String itemDescription, double price) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		try {
			stmt.executeUpdate("INSERT INTO Item(ItemCode, ItemDescription, Price)"+
			"VALUES('" + itemCode + "', '" + itemDescription + "', " + price + ");");
		}catch(Exception e) {
			e.printStackTrace();
		}
		closeConn(con);

	}

	public void CreatePurchase(String itemCode, String purchaseQuantity) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		try{
			stmt.executeUpdate("INSERT INTO Purchase(Quantity, ItemID)" + "VALUES('" + purchaseQuantity + "',(SELECT ID from Item WHERE itemCode = '" + itemCode + "'));");
		}catch(Exception e){
			e.printStackTrace();
		}
		closeConn(con);
	}

	public void CreateShipment(String itemCode, String shipmentQuantity, String shipmentDate) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		try{
			stmt.executeUpdate("INSERT INTO Shipment(ItemID, Quantity, ShipmentDate) VALUES ((SELECT ID from Item WHERE itemCode = '"+itemCode+"'), '"+shipmentQuantity+"', '"+shipmentDate+"');");
		}catch(Exception e){
			e.printStackTrace();
		}
		closeConn(con);
	}

	// DONE
	public void GetItems(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if (itemCode.equals("%")) {
			rs = stmt.executeQuery("SELECT * FROM Item;");
		} else {
			String query = "SELECT * FROM Item WHERE ItemCode = " + "'" + itemCode + "';";
			System.out.println("Executing query: " + query);
			rs = stmt.executeQuery(query);
		}
		PrintItemResults("%", rs);
		closeConn(con);
	}

	// DONE
	public void GetShipments(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if (itemCode.equals("%")) {
			rs = stmt.executeQuery("SELECT * FROM Shipment;");
		} else {
			String query = "SELECT s.* FROM Shipment s JOIN Item i on s.ItemID = i.ID WHERE i.ItemCode = " + "'"
					+ itemCode + "';";
			System.out.println("Executing query: " + query);
			rs = stmt.executeQuery(query);
		}
		PrintShipmentResults("%", rs);
		closeConn(con);
	}

	// DONE
	public void GetPurchases(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if (itemCode.equals("%")) {
			rs = stmt.executeQuery("SELECT * FROM Purchase;");
		} else {
			String query = "SELECT p.* FROM Purchase p JOIN Item i on p.ItemID = i.ID WHERE i.ItemCode = " + "'"
					+ itemCode + "';";
			System.out.println("Executing query: " + query);
			rs = stmt.executeQuery(query);
		}
		PrintPurchaseResults("%", rs);
		closeConn(con);
	}

	// MOST COMPLEX METHOD
	public void ItemsAvailable(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if (itemCode.equals("%")) {
			String itemSummary = "SELECT i.*, sum(p.Quantity) as PurchaseSum, sum(s.Quantity) as ShipmentSum FROM Item i LEFT JOIN Purchase p ON i.ID = p.ItemID LEFT JOIN Shipment s ON i.ID = s.ItemID GROUP BY i.ID";
			stmt = con.createStatement();
			rs = stmt.executeQuery(itemSummary);
			while(rs.next()) {
				int purchaseSum = rs.getInt("PurchaseSum");
				int shipmentSum = rs.getInt("ShipmentSum");
				int numberAvailable = shipmentSum-purchaseSum;
				System.out.println("ItemCode: " + rs.getString("ItemCode") + "\nItemDescription: " + rs.getString("ItemDescription") + "\nNumber Items Available(Shipment-Purchase): " + numberAvailable);
			}
		} else {
			String itemSummary = "SELECT i.*, sum(p.Quantity) as PurchaseSum, sum(s.Quantity) as ShipmentSum FROM Item i LEFT JOIN Purchase p ON i.ID = p.ItemID LEFT JOIN Shipment s ON i.ID = s.ItemID GROUP BY i.ID HAVING i.ItemCode = " + "'" + itemCode + "'" + ";";
			stmt = con.createStatement();
			rs = stmt.executeQuery(itemSummary);
			if(rs.getFetchSize() == 0) {
				System.out.println("That item does not exist.");
			}
			while(rs.next()) {
				int purchaseSum = rs.getInt("PurchaseSum");
				int shipmentSum = rs.getInt("ShipmentSum");
				int numberAvailable = shipmentSum-purchaseSum;
				System.out.println("ItemCode: " + rs.getString("ItemCode") + "\nItemDescription: " + rs.getString("ItemDescription") + "\nNumber Items Available(Shipment-Purchase): " + numberAvailable);
			}
		}
		closeConn(con);
	}

	public void UpdateItem(String itemCode, double price) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		stmt.executeUpdate("UPDATE Item SET Price = '"+price+"' WHERE ItemCode = '"+itemCode+"';");
		closeConn(con);
	}

	// DONE
	public void DeleteItem(String itemCode){
		Connection con = getConn();
		try {
			Statement stmt = con.createStatement();
			int r = stmt.executeUpdate("DELETE FROM Item WHERE ItemCode = " + "'" + itemCode + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConn(con);
	}

	// DONE
	public void DeleteShipment(String itemCode) {
		Connection con = getConn();
		try {
			String query = "DELETE FROM Shipment s WHERE s.ItemID = "
					+ "(SELECT ID from Item WHERE ItemCode = " + "'" + itemCode + "'" + ") "
						+ "ORDER BY ShipmentDate DESC LIMIT 1";
			Statement stmt = con.createStatement();
			int r = stmt.executeUpdate(query);
			System.out.println("Executed query: " + query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConn(con);
	}

	// DONE
	public void DeletePurchase(String itemCode) throws SQLException {
		Connection con = getConn();
		try {
			String query = "DELETE FROM Purchase p WHERE p.ItemID = "
					+ "(SELECT ID from Item WHERE ItemCode = " + "'" + itemCode + "'" + ") "
						+ "ORDER BY PurchaseDate DESC LIMIT 1";
			
			Statement stmt = con.createStatement();
			int r = stmt.executeUpdate(query);
			System.out.println("Executed query: " + query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConn(con);
	}

	public void PrintPurchaseResults(String columns, ResultSet rs) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt("ID");
			String itemID = rs.getString("ItemID");
			int quantity = rs.getInt("Quantity");
			Date date = rs.getDate("PurchaseDate");
			
			System.out.print("ID " + id);
			System.out.print(", ItemID " + itemID);
			System.out.print(", Quantity " + quantity);
			System.out.print(", PurchaseDate " + date+"\n");

		}
	}

	public void PrintShipmentResults(String columns, ResultSet rs) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt("ID");
			int itemID = rs.getInt("ItemID");
			int quantity = rs.getInt("Quantity");
			Date date = rs.getDate("ShipmentDate");

			System.out.print("ID " + id);
			System.out.print(", ItemID " + itemID);
			System.out.print(", Quantity " + quantity);
			System.out.println(", ShipmentDate " + date);

		}
	}

	public void PrintItemResults(String columns, ResultSet rs) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt("ID");
			String itemCode = rs.getString("ItemCode");
			String description = rs.getString("ItemDescription");
			Double price = rs.getDouble("Price");

			System.out.print("ID " + id);
			System.out.print(", ItemCode " + itemCode);
			System.out.print(", ItemDescription " + description);
			System.out.println(", Price " + price);

		}
	}
}
