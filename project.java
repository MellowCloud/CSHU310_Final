import java.io.*;
import java.sql.*;
import java.util.*;

import com.jcraft.jsch.*;

public class project{
	
	Session session;
	Statement stmt;
	Connection con;
	
	public static void main(String[] args) {
		
		Class.forName("com.mysql.jdbc.Driver");
		//Please dont access my account :)
		session = project.doSshTunnel("josephkitzhaber", "GlintC25", "onyx.boisestate.edu", 133, "localhost", 52956, 22);
		con = DriverManager.getConnection("jdbc:mysql://localhost:",db_user("msandbox"), db_password("Yveltal25"));
		stmt = con.createStatement();
		
		
		
		//END
		con.close();
		session.disconnect();
	}
	
	//ACESSING QUERIES:
	//ResultSet rs = stmt.executeQuery("[QUERY]");
	
	
	//EXAMPLE java Project CreateItem LSoda "Large Soda" 5.50
	private CreateItem(int itemCode, String itemDescription, double price) {
		
	}
	
	private CreatePurchase(int itemCode, int purchaseQuantity) {
		
	}
	
	private CreateShipment(int itemCode, int ShipmentQuantity, String shipmentDate) {
		
	}
	
	//EXAMPLE
	//java Project GetItems %    <- will return all items
	//java Project GetItems LSoda    <- will return one item
	private GetItems(int itemCode) {
		
	}
	
	private GetShipments(int itemCode) {
		
	}
	
	private GetPurchases(int itemCode) {
		
	}
	
	//MOST COMPLEX METHOD
	private ItemsAvailable(int itemCode) {
		
	}
	
	private UpdateItem(int itemCode, double price) {
		
	}
	
	private DeleteItem(int itemCode) {
		
	}
	
	private DeleteShipment(int itemCode) {
		
	}
	
	private DeletePurchase(int itemCode) {
		
	}
	
	private static Session doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	{
		/*This is one of the available choices to connect to mysql
		 * If you think you know another way, you can go ahead*/
		
		final JSch jsch = new JSch();
		java.util.Properties configuration = new java.util.Properties();
		configuration.put("StrictHostKeyChecking", "no");

		Session session = jsch.getSession( strSshUser, strSshHost, 22 );
		session.setPassword( strSshPassword );

		session.setConfig(configuration);
		session.connect();
		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
		return session;
	}
	
	
		private static Statement insertLocations(Connection con, String[] data) throws SQLException {
			  String sql;
			  java.sql.Statement stmt = null;
			  stmt = con.createStatement();
			  for(int i=0;i<data.length;i++){
				  sql = "INSERT INTO `Company`.`dept_locations`(`dnumber`,`dlocation`)VALUES(1,'"+data[i]+"')";
				  int res = stmt.executeUpdate(sql);
				  System.out.println(res);
			  }
			  return stmt;


	}

}

