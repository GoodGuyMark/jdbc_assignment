package jdbc;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

@SuppressWarnings("serial")
public class JDBCMainWindowContent extends JInternalFrame implements ActionListener {
	// DB Connectivity Attributes
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private Container content;

	private JPanel detailsPanel;
	private JPanel performancePanel;
	private JPanel buttonPanel;
	private JScrollPane dbContentsPanel;
	private JScrollPane dbContentsPanel2;
	private JScrollPane dbContentsPanel3;
	private JScrollPane dbContentsPanel4;

	private Border lineBorder;
	
	/*///////////////////////////////////////////////////////
	 * 					Table 1 Entry Fields
	 //////////////////////////////////////////////////////*/

	private JLabel Node_ID = new JLabel("Node ID:                 ");
	private JLabel City = new JLabel("City:               ");
	private JLabel Longitude = new JLabel("Longitude:      ");
	private JLabel Latitude = new JLabel("Latitude:        ");
	private JLabel Transmission_Rate = new JLabel("Transmission Rate:                 ");
	private JLabel Operating_Frequency = new JLabel("Operating Frequency:               ");
	private JLabel Peak_Performance = new JLabel("Peak Performance:      ");

	private JTextField Node_ID_TF = new JTextField(10);
	private JTextField City_TF = new JTextField(10);
	private JTextField Longitude_TF = new JTextField(10);
	private JTextField Latitude_TF = new JTextField(10);
	private JTextField Transmission_Rate_TF = new JTextField(10);
	private JTextField Operating_Frequency_TF = new JTextField(10);
	private JTextField Peak_Performance_TF = new JTextField(10);
	
	private static QueryTableModel TableModel = new QueryTableModel();
	
	/*////////////////////////////////////////////////////////
	 * 					Table 2 Entry Fields
	 ///////////////////////////////////////////////////////*/
	
	private JLabel Node_Num = new JLabel("Node Number:                 ");
	private JLabel Time_1 = new JLabel("% Loss @ 1pm:               ");
	private JLabel Time_2 = new JLabel("% Loss @ 2pm:               ");
	private JLabel Time_3 = new JLabel("% Loss @ 3pm:               ");
	private JLabel Time_4 = new JLabel("% Loss @ 4pm:               ");
	private JLabel Time_5 = new JLabel("% Loss @ 5pm:               ");
	private JLabel Time_6 = new JLabel("% Loss @ 6pm:               ");
	private JLabel Time_7 = new JLabel("% Loss @ 7pm:               ");
	private JLabel Time_8 = new JLabel("% Loss @ 8pm:               ");

	private JTextField Node_Num_TF = new JTextField(10);
	private JTextField Time_1_TF = new JTextField(10);
	private JTextField Time_2_TF = new JTextField(10);
	private JTextField Time_3_TF = new JTextField(10);
	private JTextField Time_4_TF = new JTextField(10);
	private JTextField Time_5_TF = new JTextField(10);
	private JTextField Time_6_TF = new JTextField(10);
	private JTextField Time_7_TF = new JTextField(10);
	private JTextField Time_8_TF = new JTextField(10);

	private static QueryTableModel2 TableModel2 = new QueryTableModel2();
	
	/*///////////////////////////////////////////////////////
	 * 					Audit Tables
	 //////////////////////////////////////////////////////*/
	
	private static AuditTable1 AuditModel1 = new AuditTable1();
	private static AuditTable2 AuditModel2 = new AuditTable2();

	/*///////////////////////////////////////////////////////
	 * 						JTables
	 ///////////////////////////////////////////////////////*/
	private JTable TableofDBContents = new JTable(TableModel);
	private JTable TableofDBContents2 = new JTable(TableModel2);
	private JTable TableofDBContents3 = new JTable(AuditModel1);
	private JTable TableofDBContents4 = new JTable(AuditModel2);

	/*///////////////////////////////////////////////////////
	 * 						Buttons
	///////////////////////////////////////////////////////*/
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton = new JButton("Export");
	private JButton deleteButton = new JButton("Delete");
	private JButton clearButton = new JButton("Clear");
	
	private JButton updateButton2 = new JButton("Update");
	private JButton insertButton2 = new JButton("Insert");
	private JButton exportButton2 = new JButton("Export");
	private JButton deleteButton2 = new JButton("Delete");
	private JButton clearButton2 = new JButton("Clear");
	
	private JButton joinButton = new JButton("Sort Signal Loss by Location");
	private JButton averageButton = new JButton("Average % Signal Attenuation / Hour (not working)");
	
	/*///////////////////////////////////////////////////////
	 * 					Main GUI Window
	 //////////////////////////////////////////////////////*/

	public JDBCMainWindowContent(String aTitle) {
		// setting up the GUI
		super(aTitle, false, false, false, false);
		setEnabled(true);

		initiate_db_conn();
		// add the 'main' panel to the Internal Frame
		content = getContentPane();
		content.setLayout(null);
		content.setBackground(Color.lightGray);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.green, Color.black);

		/*//////////////////////////////////////////////////////
		 * 				Details Panel Setup
		 /////////////////////////////////////////////////////*/
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridLayout(7, 2));
		detailsPanel.setBackground(Color.lightGray);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Node Details"));

		detailsPanel.add(Node_ID);
		detailsPanel.add(Node_ID_TF);
		detailsPanel.add(City);
		detailsPanel.add(City_TF);
		detailsPanel.add(Longitude);
		detailsPanel.add(Longitude_TF);
		detailsPanel.add(Latitude);
		detailsPanel.add(Latitude_TF);
		detailsPanel.add(Transmission_Rate);
		detailsPanel.add(Transmission_Rate_TF);
		detailsPanel.add(Operating_Frequency);
		detailsPanel.add(Operating_Frequency_TF);
		detailsPanel.add(Peak_Performance);
		detailsPanel.add(Peak_Performance_TF);

		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize(100, 30);
		deleteButton.setSize(100, 30);
		clearButton.setSize(100, 30);

		insertButton.setLocation(415, 25);
		updateButton.setLocation(415, 80);
		exportButton.setLocation(415, 135);
		deleteButton.setLocation(415, 190);
		clearButton.setLocation(415, 245);
		
		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);

		content.add(insertButton);
		content.add(updateButton);
		content.add(exportButton);
		content.add(deleteButton);
		content.add(clearButton);
		
		/*/////////////////////////////////////////////////
		 * 				Performance Panel Setup
		 ////////////////////////////////////////////////*/
		
		performancePanel = new JPanel();
		performancePanel.setLayout(new GridLayout(9, 2));
		performancePanel.setBackground(Color.lightGray);
		performancePanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Performance Details"));

		performancePanel.add(Node_Num);
		performancePanel.add(Node_Num_TF);
		performancePanel.add(Time_1);
		performancePanel.add(Time_1_TF);
		performancePanel.add(Time_2);
		performancePanel.add(Time_2_TF);
		performancePanel.add(Time_3);
		performancePanel.add(Time_3_TF);
		performancePanel.add(Time_4);
		performancePanel.add(Time_4_TF);
		performancePanel.add(Time_5);
		performancePanel.add(Time_5_TF);
		performancePanel.add(Time_6);
		performancePanel.add(Time_6_TF);
		performancePanel.add(Time_7);
		performancePanel.add(Time_7_TF);
		performancePanel.add(Time_8);
		performancePanel.add(Time_8_TF);

		insertButton2.setSize(100, 30);
		updateButton2.setSize(100, 30);
		exportButton2.setSize(100, 30);
		deleteButton2.setSize(100, 30);
		clearButton2.setSize(100, 30);

		insertButton2.setLocation(415, 375);
		updateButton2.setLocation(415, 430);
		exportButton2.setLocation(415, 485);
		deleteButton2.setLocation(415, 540);
		clearButton2.setLocation(415, 595);

		insertButton2.addActionListener(this);
		updateButton2.addActionListener(this);
		exportButton2.addActionListener(this);
		deleteButton2.addActionListener(this);
		clearButton2.addActionListener(this);

		content.add(insertButton2);
		content.add(updateButton2);
		content.add(exportButton2);
		content.add(deleteButton2);
		content.add(clearButton2);
		
		/*//////////////////////////////////////////////////
		 * 				Button Panel Setup
		 /////////////////////////////////////////////////*/
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Extra Functions"));
		
		buttonPanel.add(joinButton);
		buttonPanel.add(averageButton);
		
		joinButton.setSize(290, 90);
		joinButton.setLocation(8, 690);
		averageButton.setSize(290, 90);
		averageButton.setLocation(8, 780);
		
		joinButton.addActionListener(this);
		averageButton.addActionListener(this);
		
		content.add(joinButton);
		content.add(averageButton);
		content.add(buttonPanel);
		
		/*////////////////////////////////////////////////
		 * 					Table Setup
		 ///////////////////////////////////////////////*/

		dbContentsPanel = new JScrollPane(TableofDBContents, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.lightGray);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Table 1"));
		
		dbContentsPanel2 = new JScrollPane(TableofDBContents2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel2.setBackground(Color.lightGray);
		dbContentsPanel2.setBorder(BorderFactory.createTitledBorder(lineBorder, "Table 2"));

		detailsPanel.setSize(360, 300);
		detailsPanel.setLocation(3, 0);
		performancePanel.setSize(360, 300);
		performancePanel.setLocation(3, 350);
		buttonPanel.setSize(300, 200);
		buttonPanel.setLocation(3, 675);
		dbContentsPanel.setSize(900, 300);
		dbContentsPanel.setLocation(550, 0);
		dbContentsPanel2.setSize(750, 300);
		dbContentsPanel2.setLocation(550, 350);

		content.add(detailsPanel);
		content.add(performancePanel);
		content.add(dbContentsPanel);
		content.add(dbContentsPanel2);

		setSize(982, 645);
		setVisible(true);

		TableModel.refreshFromDB(stmt);
		TableModel2.refreshFromDB(stmt);
		
		/*/////////////////////////////////////////////////
		 * 				Audit Table Setup
		 ////////////////////////////////////////////////*/
		
		dbContentsPanel3 = new JScrollPane(TableofDBContents3, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel3.setBackground(Color.lightGray);
		dbContentsPanel3.setBorder(BorderFactory.createTitledBorder(lineBorder, "Audit Table 1"));
		
		dbContentsPanel4 = new JScrollPane(TableofDBContents4, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel4.setBackground(Color.lightGray);
		dbContentsPanel4.setBorder(BorderFactory.createTitledBorder(lineBorder, "Audit Table 2"));

		dbContentsPanel3.setSize(450, 200);
		dbContentsPanel3.setLocation(550, 675);
		dbContentsPanel4.setSize(450, 200);
		dbContentsPanel4.setLocation(1010, 675);

		content.add(dbContentsPanel3);
		content.add(dbContentsPanel4);

		setSize(982, 645);
		setVisible(true);

		AuditModel1.refreshFromDB(stmt);
		AuditModel2.refreshFromDB(stmt);
	}
	
	/*/////////////////////////////////////////////////////
	 * 				Connect to Database
	 ////////////////////////////////////////////////////*/

	public void initiate_db_conn() {
		try {
			// Load the JConnector Driver
			Class.forName("com.mysql.jdbc.Driver");
			// Specify the DB Name
			String url = "jdbc:mysql://localhost:3306/jdbc_assignment1";
			// Connect to DB using DB URL, Username and password
			con = DriverManager.getConnection(url, "root", "admin");
			// Create a generic statement which is passed to the TestInternalFrame1
			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	/*//////////////////////////////////////////////////////
	 * 				Button Handling
	 /////////////////////////////////////////////////////*/
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		
		/*////////////////////////////////////////////////
		 * 				Clear Button
		 ///////////////////////////////////////////////*/
	
		if (target == clearButton) {
			Node_ID_TF.setText("");
			City_TF.setText("");
			Longitude_TF.setText("");
			Latitude_TF.setText("");
			Transmission_Rate_TF.setText("");
			Operating_Frequency_TF.setText("");
			Peak_Performance_TF.setText("");

		}
		
		if (target == clearButton2) {
			Node_Num_TF.setText("");
			Time_1_TF.setText("");
			Time_2_TF.setText("");
			Time_3_TF.setText("");
			Time_4_TF.setText("");
			Time_5_TF.setText("");
			Time_6_TF.setText("");
			Time_7_TF.setText("");
			Time_8_TF.setText("");
			
		}

		
		/*///////////////////////////////////////////////
		 * 				Insert Button
		 //////////////////////////////////////////////*/
		
		if (target == insertButton) {
			try {
				String updateTemp = "INSERT INTO Nodes VALUES " 
						+ "(" + Node_ID_TF.getText() + ", " 
						+ "'" + City_TF.getText() + "'" + ", " 
						+ "'" + Longitude_TF.getText() + "'" + ", " 
						+ "'" + Latitude_TF.getText() + "'" + ", " 
						+ "'" + Transmission_Rate_TF.getText() + "'" + ", " 
						+ "'" + Operating_Frequency_TF.getText() + "'" + ", " 
						+ "'" + Peak_Performance_TF.getText() + "'" + ")";
				
				stmt.executeUpdate(updateTemp);

			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				TableModel.refreshFromDB(stmt);
				AuditModel1.refreshFromDB(stmt);
			}
		}
		
		if (target == insertButton2) {
			try {
				String updateTemp = "INSERT INTO Performance VALUES " 
						+ "(" + Node_Num_TF.getText() + ", "
						+ Time_1_TF.getText() + ", "
						+ Time_2_TF.getText() + ", "
						+ Time_3_TF.getText() + ", "
						+ Time_4_TF.getText() + ", "
						+ Time_5_TF.getText() + ", "
						+ Time_6_TF.getText() + ", "
						+ Time_7_TF.getText() + ", "
						+ Time_8_TF.getText() + ")";
			
				stmt.executeUpdate(updateTemp);

			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				TableModel2.refreshFromDB(stmt);
				AuditModel2.refreshFromDB(stmt);
			}
		}
		
		/*///////////////////////////////////////////////
		 * 				Delete Button
		 //////////////////////////////////////////////*/
		if (target == deleteButton) {

			try {
				String updateTemp = "DELETE FROM Nodes WHERE Node_ID = " + Node_ID_TF.getText() + ";";
				
				stmt.executeUpdate(updateTemp);

			} catch (SQLException sqle) {
				System.err.println("Error with delete:\n" + sqle.toString());
			} finally {
				TableModel.refreshFromDB(stmt);
				AuditModel1.refreshFromDB(stmt);
			}
		}
		
		if (target == deleteButton2) {

			try {
				String updateTemp = "DELETE FROM Performance WHERE Node_Num = " + Node_Num_TF.getText() + ";";
			
				stmt.executeUpdate(updateTemp);

			} catch (SQLException sqle) {
				System.err.println("Error with delete:\n" + sqle.toString());
			} finally {
				TableModel2.refreshFromDB(stmt);
				AuditModel2.refreshFromDB(stmt);
			}
		}
		
		/*///////////////////////////////////////////////
		 * 				Update Button
		 //////////////////////////////////////////////*/
		if (target == updateButton) {
			try {
				String updateTemp = "UPDATE Nodes SET " 
						+ "City = " + "'" + City_TF.getText() + "', " 
						+ "Longitude = " + "'" + Longitude_TF.getText() + "', " 
						+ "Latitude = " + "'" + Latitude_TF.getText() + "', "
						+ "Transmission_Rate = " + "'" + Transmission_Rate_TF.getText() + "', "
						+ "Operating_Frequency = " + "'" + Operating_Frequency_TF.getText() + "', "
						+ "Peak_Performance = " + "'" + Peak_Performance_TF.getText() + "' " 
						+ "WHERE Node_ID = " + Node_ID_TF.getText();

				System.out.println(updateTemp);
				stmt.executeUpdate(updateTemp);
				// these lines do nothing but the table updates when we access the db.
				rs = stmt.executeQuery("SELECT * from Nodes ");
				rs.next();
				rs.close();
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				TableModel.refreshFromDB(stmt);
				AuditModel1.refreshFromDB(stmt);
			}
		}
		
		if (target == updateButton2) {
			try {
				String updateTemp = "UPDATE Performance SET " 
						+ "Time_1 = " + Time_1_TF.getText() + ", "
						+ "Time_2 = " + Time_2_TF.getText() + ", "
						+ "Time_3 = " + Time_3_TF.getText() + ", "
						+ "Time_4 = " + Time_4_TF.getText() + ", "
						+ "Time_5 = " + Time_5_TF.getText() + ", "
						+ "Time_6 = " + Time_6_TF.getText() + ", "
						+ "Time_7 = " + Time_7_TF.getText() + ", "
						+ "Time_8 = " + Time_8_TF.getText() + " "
						+ "WHERE Node_Num = " + Node_Num_TF.getText();
		
				System.out.println(updateTemp);
				stmt.executeUpdate(updateTemp);
				// these lines do nothing but the table updates when we access the db.
				rs = stmt.executeQuery("SELECT * from Performance ");
				rs.next();
				rs.close();
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				TableModel2.refreshFromDB(stmt);
				AuditModel2.refreshFromDB(stmt);
			}
		}
		
		/*/////////////////////////////////////////////////////////
		 * 					Export Button
		 ////////////////////////////////////////////////////////*/
		if (target == exportButton) {
			String filename = "Nodes.csv";
			try {
				FileWriter fw = new FileWriter(filename);
				Class.forName("com.mysql.jdbc.Driver").newInstance();
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_assignment1", "root", "admin");
	            String query = "select * from Nodes";
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                fw.append(rs.getString(1));
	                fw.append(',');
	                fw.append(rs.getString(2));
	                fw.append(',');
	                fw.append(rs.getString(3));
	                fw.append(',');
	                fw.append(rs.getString(4));
	                fw.append(',');
	                fw.append(rs.getString(5));
	                fw.append(',');
	                fw.append(rs.getString(6));
	                fw.append(',');
	                fw.append(rs.getString(7));
	                fw.append('\n');
	               }
	            fw.flush();
	            fw.close();
	            con.close();
	            System.out.println("CSV File is created successfully.");
			} catch (Exception f) {
	            f.printStackTrace();
	        }
		}
		if (target == exportButton2) {
			String filename = "Performance.csv";
			try {
				FileWriter fw = new FileWriter(filename);
				Class.forName("com.mysql.jdbc.Driver").newInstance();
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_assignment1", "root", "admin");
	            String query = "select * from Performance";
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                fw.append(rs.getString(1));
	                fw.append(',');
	                fw.append(rs.getString(2));
	                fw.append(',');
	                fw.append(rs.getString(3));
	                fw.append(',');
	                fw.append(rs.getString(4));
	                fw.append(',');
	                fw.append(rs.getString(5));
	                fw.append(',');
	                fw.append(rs.getString(6));
	                fw.append(',');
	                fw.append(rs.getString(7));
	                fw.append('.');
	                fw.append(rs.getString(8));
	                fw.append(',');
	                fw.append(rs.getString(9));
	                fw.append('\n');
	               }
	            fw.flush();
	            fw.close();
	            con.close();
	            System.out.println("CSV File is created successfully.");
			} catch (Exception f) {
	            f.printStackTrace();
	        }
		}
		
		if (target == joinButton) {
			String filename = "JOIN.csv";
			try {
				FileWriter fw = new FileWriter(filename);
				Class.forName("com.mysql.jdbc.Driver").newInstance();
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_assignment1", "root", "admin");
				String query = 
						"SELECT Nodes.City, Performance.Time_1, Performance.Time_2, Performance.Time_3, Performance.Time_4, Performance.Time_5, "
						+ "Performance.time_6, Performance.Time_7, Performance.Time_8 "
						+ "FROM Nodes "
						+ "RIGHT JOIN Performance "
						+ "ON Nodes.Node_ID = Performance.Node_Num";
				stmt = con.createStatement();
	            rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                fw.append(rs.getString(1));
	                fw.append(',');
	                fw.append(rs.getString(2));
	                fw.append(',');
	                fw.append(rs.getString(3));
	                fw.append(',');
	                fw.append(rs.getString(4));
	                fw.append(',');
	                fw.append(rs.getString(5));
	                fw.append(',');
	                fw.append(rs.getString(6));
	                fw.append(',');
	                fw.append(rs.getString(7));
	                fw.append('.');
	                fw.append(rs.getString(8));
	                fw.append(',');
	                fw.append(rs.getString(9));
	                fw.append('\n');
	               }
				System.out.println("JOIN Successful");
				
				fw.flush();
				fw.close();
				con.close();
				System.out.println("File created Successfully");
				
			} catch (Exception f) {	
				System.err.println("Error with members insert:\n" + f.toString());
			}
		}
		
		if (target == averageButton) {
			String filename = "Average2.csv";
			try {
				FileWriter fw = new FileWriter(filename);
				Class.forName("com.mysql.jdbc.Driver").newInstance();
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_assignment1", "root", "admin");
	            CallableStatement cstmt = con.prepareCall("{CALL avg_loss_1()}");
	            CallableStatement cstmt2 = con.prepareCall("{CALL avg_loss_2()}");
	            CallableStatement cstmt3 = con.prepareCall("{CALL avg_loss_3()}");
	            CallableStatement cstmt4 = con.prepareCall("{CALL avg_loss_4()}");
	            CallableStatement cstmt5 = con.prepareCall("{CALL avg_loss_5()}");
	            CallableStatement cstmt6 = con.prepareCall("{CALL avg_loss_6()}");
	            CallableStatement cstmt7 = con.prepareCall("{CALL avg_loss_7()}");
	            CallableStatement cstmt8 = con.prepareCall("{CALL avg_loss_8()}");
	            String query  = "SELECT * FROM Average";
				stmt = con.createStatement();
	            rs = cstmt.executeQuery(query);
	            while (rs.next()) {
	                fw.append(rs.getString(1));
	                fw.append(',');
	                fw.append(rs.getString(2));
	                fw.append(',');
	                fw.append(rs.getString(3));
	                fw.append(',');
	                fw.append(rs.getString(4));
	                fw.append(',');
	                fw.append(rs.getString(5));
	                fw.append(',');
	                fw.append(rs.getString(6));
	                fw.append(',');
	                fw.append(rs.getString(7));
	                fw.append('.');
	                fw.append(rs.getString(8));
	                fw.append(',');
	                fw.append(rs.getString(9));
	                fw.append('\n');
	            }
				fw.flush();
				fw.close();
				con.close();
				System.out.println("File created Successfully");
				
			} catch (Exception f) {	
				System.err.println("Error with members insert:\n" + f.toString());
			}
		}
	}
}
