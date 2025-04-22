package pik;

import pik.DB_Parser.CsvParser;

import java.util.List;

import pik.DB.*;
import pik.DB.Entities.*;

public class Main {
    public static void main(String[] args) {
        CsvParser parser = new CsvParser("src/main/resources/Interns_2025_SWIFT_CODES - Sheet1.csv");
        Handler handler = new Handler();
        handler.start();
        List<BankBranch> list = parser.parseToBankBranch();
        Storable storable = list.get(0);
        // handler.addObjects((List<Storable>)(List<?>)parser.parseToBankBranch()); //to Storable for adding objects in database
        System.out.println(storable.toString());
        // handler.addObject(storable); 
        BankBranch branch = new BankBranch("1", "wg", "iso", "ada", "9990999");
        handler.addObject(branch);


        handler.close();
    }
}