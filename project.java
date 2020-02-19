import java.io.*;
import java.sql.*;
import java.util.*;


public class project {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		// if(args[0].equals("/?")){
		// 	printUsage();
		// } else if(args <) {

		// } else {
		// 	printUsage();
		// }
		Connection con = getConn();
		Statement stmt = con.createStatement();
		ResultSet result_set = stmt.executeQuery("Select * from Item;");
		System.out.println(result_set);
		
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
	
	private void closeConn(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void printUsage() {
		System.out.println("Usage");
	}
	//ACESSING QUERIES:
	//ResultSet rs = stmt.executeQuery("[QUERY]");
	
	
	//EXAMPLE java Project CreateItem LSoda "Large Soda" 5.50
//	private CreateItem(int itemCode, String itemDescription, double price) {
//		
//	}
//	
//	private CreatePurchase(int itemCode, int purchaseQuantity) {
//		
//	}
//	
//	private CreateShipment(int itemCode, int ShipmentQuantity, String shipmentDate) {
//		
//	}
	
	//EXAMPLE
	//java Project GetItems %    <- will return all items
	//java Project GetItems LSoda    <- will return one item
//	private GetItems(int itemCode) {
//		if(itemCode == "%") {
//			itemCode = "*";
//		}
//		ResultSet rs = stmt.executeQuery("")
//	}
//	
//	private GetShipments(int itemCode) {
//		
//	}
//	
//	private GetPurchases(int itemCode) {
//		
//	}
//	
//	//MOST COMPLEX METHOD
//	private ItemsAvailable(int itemCode) {
//		
//	}
//	
//	private UpdateItem(int itemCode, double price) {
//		
//	}
//	
//	private DeleteItem(int itemCode) {
//		
//	}
//	
//	private DeleteShipment(int itemCode) {
//		
//	}
//	
//	private DeletePurchase(int itemCode) {
//		
//	}
	
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

