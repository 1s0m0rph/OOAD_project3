package toolshop;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import toolshop.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerPoolTest {

    @Test
    @Order(1)
    void getInstance()
    {
        CustomerPool customers = CustomerPool.getInstance();
    }

    @Test
    @Order(2)
    void getNamed() {
        CustomerPool customers = CustomerPool.getInstance();
        String name = "Alice";
        Customer alice = (Customer) customers.get("Alice");
        assert(alice.getName().equals("Alice"));
        assert(customers.get("Alice") == null);
        customers.release(alice);
    }

    @Test
    @Order(3)
    void getNext() {
        CustomerPool customers = CustomerPool.getInstance();
        ArrayList<Customer> inStore = new ArrayList<>();
        Customer next = (Customer) customers.get();
        while (next != null)
        {
            inStore.add(next);
            next = (Customer) customers.get();
        }
        assert(inStore.size() == 12);
        for (Customer c : inStore)
        {
            customers.release(c);
        }
    }

    @Test
    @Order(4)
    void release()
    {
        CustomerPool customers = CustomerPool.getInstance();
        Customer bob = (Customer) customers.get("Bob");
        assert(customers.get("Bob") == null);
        customers.release(bob);
        bob = null;
        bob = (Customer) customers.get("Bob");
        assert(bob.getName().equals("Bob"));
        customers.release(bob);
    }

    void shutdown()
    {
        CustomerPool customers = CustomerPool.getInstance();
        boolean caughtOnGetNamed = false;
        boolean caughtOnGetNext = false;
        boolean caughtOnRelease = false;
        customers.shutdown();

        try {
            customers.get("Alice");
        } catch (IllegalArgumentException e) {
            caughtOnGetNamed = true;
        }
        try {
            customers.get();
        } catch (IllegalArgumentException e) {
            caughtOnGetNext = true;
        }
        try {
            customers.release(null);
        } catch (IllegalArgumentException e) {
            caughtOnRelease = true;
        }

        assert(caughtOnGetNamed);
        assert(caughtOnGetNext);
        assert(caughtOnRelease);
    }
}