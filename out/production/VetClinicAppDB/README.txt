
The two applications generate and populate data for the vet clinic database.
MySQL statements to create the database are in the DDL.txt.

The fake data generator app is DataGenerator.java file and the app that imports the data in the csv file into
a 3NF database is the Main.java file. Run the data generator app first. In order to run it, specify two command
line arguments, the first one is the filename you want to create and the second is how many tuples you would like to
generate.
The library that I use to generate data is com.github.javafaker:javafaker:0.15

The data importer app asks for your filename that you just created in order to read values from it and populate the
database.
The library I use for reading values from the csv file is org.apache.commons:commons-csv:1.0
To populate the database I use the mysql:mysql-connector-java:5.1.45 library