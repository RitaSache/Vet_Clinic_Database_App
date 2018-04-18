import com.github.javafaker.Faker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataGenerator {
    //generates one tuple with all specified values
    public String generateTuple(){
        Faker faker = new Faker();
        String catName = faker.cat().name();
        String catBreed = faker.cat().breed();
        String catRegistry = faker.cat().registry();
        String ownerPhone = faker.phoneNumber().cellPhone();
        String ownerAddress1 = faker.address().fullAddress();
        return catName + "," + catBreed + "," + catRegistry + "," + ownerPhone + "," + ownerAddress1;
        }
    //writes a tuple to a specified file
    public void writeToFile(String fileName, String string) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(string);
        writer.close();
    }
    //gets filename and number of tuples to generate from a user and generates random values into the csv file
    public static void main(String[] args) throws IOException {
        String filename;
        int numberOfTuples = 0;
        if (args.length == 2) {
            filename = args[0] + ".csv";
            DataGenerator data = new DataGenerator();
            try {
                numberOfTuples = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid second parameter");
            }
            for(int i = 0; i < numberOfTuples; i++ ){
                data.writeToFile(filename, data.generateTuple());
                data.writeToFile(filename, "\n");
            }
            System.out.println("Data successfully generated into the file");
        }
        else {
            System.out.println("Insufficient number of arguments");
        }
    }
}
