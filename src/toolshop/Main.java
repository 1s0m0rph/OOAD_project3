package toolshop;

import java.util.ArrayList;

public class Main
{
    public static void main(String args[]) {
        Store s = Store.getInstance();
        Customer c = new CasualCustomer("Jerry");
        c.rentTools();
        ArrayList<RentalRecord> rr = s.getRentalRecords();
        for (RentalRecord r : rr) {
            System.out.println(r.toolsRented);
        }
    }
}
