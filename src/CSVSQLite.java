import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVSQLite {

	private static int recordsReceived = 0;
	private static int recordsSuccessful = 0;
	private static int recordsFailed = 0;
	private static Connection connect = null;

	private static String csvPath = "C:\\Users\\Erica\\Desktop\\ms3Interview.csv";
	private static String csvBadPath = "C:\\Users\\Erica\\eclipse-workspace\\MS3CodingChallenge\\csvBadRecords.csv";

	// function that connects to the database and insert good records
	public static void insertDB(String[] record) {
		try {
			connect = DriverManager
					.getConnection("jdbc:sqlite:C:\\Users\\Erica\\eclipse-workspace\\MS3CodingChallenge\\records.db");

			connect.setAutoCommit(true); // set to false when rerunning the code
			Statement statement = connect.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS records" + "(A       TEXT," + " B       TEXT,"
					+ " C       TEXT," + " D       TEXT," + " E       TEXT," + " F       TEXT," + " G       TEXT,"
					+ " H       TEXT," + " I       TEXT," + " J       TEXT);");

			PreparedStatement insert = connect
					.prepareStatement("INSERT INTO records(A,B,C,D,E,F,G,H,I,J) " + "VALUES(?,?,?,?,?,?,?,?,?,?);");
			insert.setString(1, record[0]);
			insert.setString(2, record[1]);
			insert.setString(3, record[2]);
			insert.setString(4, record[3]);
			insert.setString(5, record[4]);
			insert.setString(6, record[5]);
			insert.setString(7, record[6]);
			insert.setString(8, record[7]);
			insert.setString(9, record[8]);
			insert.setString(10, record[9]);
			insert.executeUpdate();

			insert.close();
			statement.close();
			connect.close();

		} catch (SQLException e) {
			e.getMessage();
		}
	}

	// function that creates a log file
	public static void logFile(int r, int s, int f) {
		Logger logger = Logger.getLogger(CSVSQLite.class.getName());
		FileHandler fh;

		try {

			fh = new FileHandler("C:\\Users\\Erica\\eclipse-workspace\\MS3CodingChallenge\\statistics.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			logger.info("# of records received: " + r);
			logger.info("# of records successful: " + s);
			logger.info("# of records failed: " + f);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Read and Write csv file
		try {

			CSVReader reader = new CSVReader(new FileReader(csvPath));
			Writer writer = Files.newBufferedWriter(Paths.get(csvBadPath));

			CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			String[] headerBadRecord = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
			csvWriter.writeNext(headerBadRecord);
			String[] nextRecord;

			while ((nextRecord = reader.readNext()) != null) {
				if (nextRecord != null) {
					System.out.println(Arrays.toString(nextRecord));
					recordsReceived++;
				}
				if (!Arrays.asList(nextRecord).contains("")) {
					insertDB(nextRecord);
					recordsSuccessful++;
				} else {
					csvWriter.writeNext(nextRecord);
					recordsFailed++;
				}

			}
			reader.close();
			csvWriter.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("# of records recieved: " + recordsReceived);
		System.out.println("# of records successful: " + recordsSuccessful);
		System.out.println("# of records failed: " + recordsFailed);

		logFile(recordsReceived, recordsSuccessful, recordsFailed);

	}

}
