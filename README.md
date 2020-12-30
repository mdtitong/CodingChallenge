># Summary of the purpose of this repo
 

This is a programming challenge that requires a csv file to parse the data and insert to SQLite database. Each record (rows) must contain the right number of data to match the columns and consider as good record to be inserted to the database. Records with missing data elements will be regarded as bad data and will be written to a csv file. At the end of the processing, a log file is created showing the statistics of the received records, successful records, and failed records. 

---
># Steps
1. Download IDE (IntelliJ, NetBeans, Eclipse: Own preference). I use eclipse as my IDE. 
1. Download DB Browser for SQLite
1.	Download jar files
1.	Install IDE and DB Browser
1.	In Eclipse, import jar files to libraries
1.	Run the program
## *Below are the downloadable links:*
[Eclipse IDE](https://www.eclipse.org/downloads/)

[DB Browser](https://sqlitebrowser.org/dl/)

## *Jar Files*
[SQLite JDBC](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.34.0)

[openCSV](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.3)

[commons-lang3](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.11)

---   
># Overview


Reading the records line by line that returns an Array of Strings is the first step, then checks every column if it contains data or not. Next is to create a function that connects to the database through SQLite JDBC Driver and insert the records with complete data elements. By using openCSV library I was able to use a method that creates a csv file that writes the records with missing data elements. Lastly, I created a function that creates a log file using logging API for statistics viewing .     

Running the code for the first time will generate the requirements( database, csv, log). However when running the code the second time will duplicate the records in the database. Solution to this is to use a method setAutoCommit(Boolean expression) and set it to false when rerunning the code.  



