import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.sql.*;

public class Main {
    public static void populateDatabase(Iterable<CSVRecord> records, Connection con) throws SQLException {
        //grabbing all values in the scv file
        for (CSVRecord record : records) {
            String catName = record.get(0);
            String catBreed = record.get(1);
            String catRegistry = record.get(2);
            String ownerPhone = record.get(3);
            String ownerStreet = record.get(4);
            String ownerCity = record.get(5);
            String zipcode = record.get(6);

            //prepared MySQl INSERT statement
            PreparedStatement st = con.prepareStatement("INSERT INTO CatInfo(Name) VALUES (?);" , Statement.RETURN_GENERATED_KEYS );
            st.setString(1, catName);
            st.executeUpdate();
            //grabbing generated primary keys in the parent table
            ResultSet rs_key = st.getGeneratedKeys(); //foreign key for the rest of the tables

            int master_id = 0;
            if(rs_key.next()){
                master_id = rs_key.getInt(1);
            }
            //Inserting cat breed into CatBreed table
            PreparedStatement st2 = con.prepareStatement("INSERT INTO CatBreed(Breed, CatId) VALUES (?,?);");
            st2.setString(1, catBreed);
            st2.setString(2, String.valueOf(master_id));
            st2.executeUpdate();

            //Inserting registry into CatRegistry table
            PreparedStatement st3 = con.prepareStatement("INSERT INTO CatRegistry(Registry, CatId) VALUES (?,?);");
            st3.setString(1, catRegistry);
            st3.setString(2, String.valueOf(master_id));
            st3.executeUpdate();

            //Inserting phone into OwnerPhone table
            PreparedStatement st4 = con.prepareStatement("INSERT INTO OwnerPhone(ownerPhone, catId) VALUES (?,?);");
            st4.setString(1, ownerPhone);
            st4.setString(2, String.valueOf(master_id));
            st4.executeUpdate();

            //Inserting address items into OwnerPrimaryAddress table
            PreparedStatement st5 = con.prepareStatement("INSERT INTO OwnerPrimaryAddress(City, StreetAndApt, StateAndZip, CatId) VALUES (?,?,?,?);");
            st5.setString(1, ownerCity);
            st5.setString(2, ownerStreet);
            st5.setString(3, zipcode);
            st5.setString(4, String.valueOf(master_id));
            st5.executeUpdate();
        }
    }
    public static void main(String[] argv) throws IOException, SQLException {
        Connection con = DbConfig.getMySqlConnection(); //establishing connection to MySql server
        String fileName;
        System.out.println("This is an app that imports data from your csv file into a normalized database");
        System.out.println("Please enter the name of your local file");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            fileName = reader.readLine(); //gets file name from user
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in); //creates an iterable to read values in the csv file
            populateDatabase(records, con);
            System.out.println("The database is populated");
        }
        catch (ArrayIndexOutOfBoundsException | FileNotFoundException e) {
            System.out.println("The file you specified in empty or doesn't exist. Make a file and generate data first.");
        }
    }
}