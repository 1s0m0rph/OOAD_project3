package toolshop.test;

import org.junit.jupiter.api.Test;
import toolshop.*;

class IntegrationTest {

    @Test
    void returns()
    {
        Store s = Store.getInstance();
        Customer alice = new RegularCustomer("Alice");
        Customer bob = new BusinessCustomer("Bob");
        Customer chuck = new CasualCustomer("Chuck");
        alice.rentTools();
        bob.rentTools();
        chuck.rentTools();
        assert(s.getRentalRecords().size() == 3);
        assert(s.getCurrentInventoryCount() < 24);
        for (int i=0; i<8; i++) { s.incrementDay(); }
        assert(s.getCurrentInventoryCount() == 24);
        assert(s.getRentalRecords().size() == 0);
    }
}
