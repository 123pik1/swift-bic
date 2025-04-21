package pik;

import pik.DB_Parser.CsvParser;

import java.util.List;

import pik.DB.*;
import pik.DB.Entities.*;

public class Main {
    public static void main(String[] args) {
        CsvParser parser = new CsvParser("src/main/resources/Interns_2025_SWIFT_CODES - Sheet1.csv");
        parser.parseToBankBranch();
        // Handler handler = new Handler();
        // handler.addObjects((List<Storable>)(List<?>)parser.parseToBankBranch()); //to Storable for adding objects in database



        // handler.close();
    }
}