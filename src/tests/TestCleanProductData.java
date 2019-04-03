package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import analytics.PurchaseAnalytics;

public class TestCleanProductData {
	
	@Test
	public void testCleanProductData() {
		PurchaseAnalytics analytics = new PurchaseAnalytics();
		String input = "\"abc,4367242Garlic vsf2334Powder,10gfhjd4,13\"";
		String output = analytics.cleanProductData(input);
		String expectedOutput = null;
		assertEquals(output, expectedOutput);
	}

}
