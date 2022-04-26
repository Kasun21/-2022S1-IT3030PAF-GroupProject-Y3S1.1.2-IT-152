package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer_management","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPay(String CustomerName, String Address, String AccountNo, String BillNo, String Amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment(`PaymentID`,`CustomerName`,`Address`,`AccountNo`,`BillNo`,`Amount`)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, CustomerName);
			preparedStmt.setString(3, Address);
			preparedStmt.setString(4, AccountNo);
			preparedStmt.setString(5, BillNo);
			preparedStmt.setString(6, Amount);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPay() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment ID</th><th>Customer Name</th><th>Address</th><th>Account No</th><th>Bill No</th><th>Total Amount</th></tr>";
			String query = "select * from payment";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String PaymentID = Integer.toString(rs.getInt("PaymentID"));
				String CustomerName = rs.getString("CustomerName");
				String Address = rs.getString("Address");
				String AccountNo = rs.getString("AccountNo");
				String BillNo = rs.getString("BillNo");
				String Amount = rs.getString("Amount");

				output += "<tr><td>" + PaymentID + "</td>";
				output += "<td>" + CustomerName + "</td>";
				output += "<td>" + Address + "</td>";
				output += "<td>" + AccountNo + "</td>";
				output += "<td>" + BillNo + "</td>";
				output += "<td>" + Amount + "</td>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePay(String PaymentID, String CustomerName, String Address, String AccountNo, String BillNo, String Amount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET CustomerName=?,Address=?,AccountNo=?,BillNo=?,Amount=? WHERE PaymentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, CustomerName);
			preparedStmt.setString(2, Address);
			preparedStmt.setString(3, AccountNo);
			preparedStmt.setString(4, BillNo);
			preparedStmt.setString(5, Amount);
			preparedStmt.setInt(6, Integer.parseInt(PaymentID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePay(String PaymentID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from payment where PaymentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PaymentID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
