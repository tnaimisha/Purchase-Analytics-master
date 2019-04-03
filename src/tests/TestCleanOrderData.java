package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import analytics.PurchaseAnalytics;

public class TestCleanOrderData {
	
	@Test
	public void testCleanOrderData() {
		PurchaseAnalytics analytics = new PurchaseAnalytics();
		String input = "\"2,\"93\"2-7,3,0\"";
		String output = analytics.cleanOrdersData(input);
		String expectedOutput = null;
		assertEquals(output, expectedOutput);
	}

}
