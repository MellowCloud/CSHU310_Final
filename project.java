import java.io.*;
import java.sql.*;
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
		
	}
	
	private static void CreatePurchase(String itemCode, String purchaseQuantity) throws SQLException {
		
	}
	
	private static void CreateShipment(String itemCode, String ShipmentQuantity, String shipmentDate) throws SQLException {
		
	}
	
	//EXAMPLE
	//java Project GetItems %    <- will return all items
	//java Project GetItems LSoda    <- will return one item
	private static void GetItems(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if(itemCode == "%") {
			rs = stmt.executeQuery("SELECT * FROM Item;");
		}else {
			rs = stmt.executeQuery("SELECT * FROM Item WHERE itemCode = "+itemCode+";");
		}
		System.out.println(rs.getRow());
	}
	
	private static void GetShipments(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if(itemCode == "%") {
			rs = stmt.executeQuery("SELECT * FROM Shipment;");
		}else {
			rs = stmt.executeQuery("SELECT * FROM Shipment s "
					+ "WHERE "+itemCode
					+ "=(SELECT itemCode FROM Item i WHERE i.ID = s.ItemID);");
		}
		System.out.println(rs.getRow());
	}
	
	private static void GetPurchases(String itemCode) throws SQLException {
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if(itemCode =="%") {
			rs = stmt.executeQuery("SELECT * FROM Purchase;");
		}else {
			rs = stmt.executeQuery("SELECT * FROM Purchase p "
					+ "WHERE "+itemCode
					+ "=(SELECT itemCode FROM Item i WHERE i.ID = p.ItemID);");		
		}
		System.out.println(rs.getRow());
	}
	
	//MOST COMPLEX METHOD
	private static void ItemsAvailable(String itemCode) throws SQLException {
		
	}
	
	private static void UpdateItem(String itemCode, double price) throws SQLException {
		
	}
	
	private static void DeleteItem(String itemCode) throws SQLException {
		
	}
	
	private static void DeleteShipment(String itemCode) throws SQLException {
		
	}
	
	private static void DeletePurchase(String itemCode) throws SQLException {
		
	}

//	private static Session doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
//	{
//		/*This is one of the available choices to connect to mysql
//		 * If you think you know another way, you can go ahead*/
//		
//		final JSch jsch = new JSch();
//		java.util.Properties configuration = new java.util.Properties();
//		configuration.put("StrictHostKeyChecking", "no");
//
//		Session session = jsch.getSession( strSshUser, strSshHost, 22 );
//		session.setPassword( strSshPassword );
//
//		session.setConfig(configuration);
//		session.connect();
//		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
//		return session;
//	}
}

