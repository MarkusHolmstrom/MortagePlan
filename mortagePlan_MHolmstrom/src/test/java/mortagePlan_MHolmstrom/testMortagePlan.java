package mortagePlan_MHolmstrom;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class testMortagePlan {
	MortagePlan mp = new MortagePlan();
	
	@Test
	void testStart() {
		mp.start();
	}
	
	@Test
	void testReadDocument() {
		
		mp.readDocumentInSrc();
		System.out.println("Test succeeded");
	}
	
	@Test 
	void testNewCustomer() {
		mp.newCustomer("testName", 20000, 4.4, 2);
	}

	@Test 
	void testManyNewCustomers() {
		mp.newCustomer("testName0", 24005.6, 2.4, 2);
		mp.newCustomer("testName1", 1600, 4.1, 5);
		mp.newCustomer("testName2", 26930, 7, 1);
		mp.newCustomer("testName3", 10000, 24, 12);
	}

	@Test 
	void testGetTotalCustomers() {
		assertEquals("error in getTotalCustomers()",  4, mp.getTotalCustomers());
	}

	@Test 
	void testAddMoreCustomersAndGetTotal() {
		mp.readDocumentInSrc();
		
		mp.newCustomer("testName0", 24005.6, 2.4, 2);
		mp.newCustomer("testName1", 1600, 4.1, 5);
		mp.newCustomer("testName2", 26930, 7, 1);
		mp.newCustomer("testName3", 10000, 24, 12);
		
		assertEquals("error in getTotalCustomers()",  8, mp.getTotalCustomers());
	}
}
