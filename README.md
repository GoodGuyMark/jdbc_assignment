# JDBC Assignment
A simple JDBC project with a GUI to operate CRUD actions and export data as a .csv file using a MySQL database. 

This project was an assignment from my Data Architecture & Database Systems module during my Masters degree. I was tasked with analysing a telecommunications problem and creating sample relational data to describe this problem. My project observes the signal loss of a Wire Mesh Network over time as rain storm passes over Ireland. There are nodes set up in major Irish cities and these nodes are given basic location details and specific operating components.

To install:
1. Download the code by cloning the repository or downloading the zip folder directly.
2. Create the database by running the database.sql file in a local MySQL server (You will need the MySQL Workbench to do this. You can download this here: https://dev.mysql.com/downloads/workbench/)
3. Import your project into a Java IDE (I use Eclipse but other IDEs should have no problem running this).
4. To start the program run the MainClass.java as a Java application.

To use:
1. When you start the program the GUI will open to displaying the tables and operations available to you.
2. Table 1 lists the nodes that are installed across the country containing the city they are located in, gps location, transmission rate, opearting frequency, and peak performance as a percentage. Using the Node details box you can perform CRUD actions on Table 1. Entering the details of a new node and clicking the INSERT button will enter a new node entry onto Table 1. You can alter the data of an existing node by entering the new details with the corresponding Node_ID and clicking UPDATE. Clicking the EXPORT button will create a .csv of the current data in Table 1 when can be copied into excel to undergo data analysis. If you wish to delete a node entry you only need to enter the Node_ID of the node you wish to delete and click the DELETE button to remove the node from Table 1. Clicking the CLEAR button removes the text from the Node details text boxes.
3. 
