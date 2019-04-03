package analytics;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class PurchaseAnalytics {
	
	//deptMap is the mapping for product_id and the corresponding department_id
	HashMap<Integer, Integer> prodDeptMap = new HashMap<>();
	
	//deptOrdersCount holds the number of orders and number of first orders for each department_id
	HashMap<Integer, int[]> deptOrdersCount = new HashMap<>();
	
	public void purchaseAnalytics() throws FileNotFoundException, IOException {
		
		//path setting for the parent directory holding input and output folders
		File file = new File("").getCanonicalFile();
		BufferedReader productsInfo = new BufferedReader(new FileReader(file.getParent() + "/input/products.csv"));
		
		//ignoring the header
		String cleanedProductData = productsInfo.readLine();
		
		//reading each line from the csv file and updating the prodDeptMap
		while ((cleanedProductData = cleanProductData(productsInfo.readLine())) != null) {
			if (cleanedProductData.equals("junk")) {
				continue;
			}
			String[] productFields = cleanedProductData.split(",");
			int numOfFields = productFields.length;
			prodDeptMap.put(Integer.parseInt(productFields[0]), Integer.parseInt(productFields[numOfFields-1]));
		}
		
		productsInfo.close();
		
		//setting up the path for second input file
		BufferedReader ordersInfo = new BufferedReader(new FileReader(file.getParent() + "/input/order_products.csv"));
		
		//ignoring the header
		String cleanedOrderDetails = ordersInfo.readLine();
		
		while((cleanedOrderDetails = cleanOrdersData(ordersInfo.readLine())) != null) {
			if (cleanedOrderDetails.equals("junk")){
				continue;
			}
			String[] orderFields = cleanedOrderDetails.split(",");
			
			//fetching the department_id corresponding to the product_id
			int deptId = prodDeptMap.getOrDefault(Integer.parseInt(orderFields[1]), -1);
			if (deptId == -1) {
				//error in the product_id, ignore the record
				continue;
			}
			
			//ordersCount is an array of size 2 with counts of total orders and first orders respectively
			int[] ordersCount = deptOrdersCount.getOrDefault(deptId,new int[2]);
			ordersCount[0]++;
			if (Integer.parseInt(orderFields[3])==0) {
				ordersCount[1]++;
			}
			
			deptOrdersCount.put(deptId, ordersCount);
		}
	
	
		ordersInfo.close();
		
		//sorting the OrdersCount based on the department_id and writing to a Queue 
		PriorityQueue<Map.Entry<Integer, int[]>> sortedOutput = new PriorityQueue<>((e1,e2) -> e1.getKey() - e2.getKey());
		for (Map.Entry<Integer, int[]> entry : deptOrdersCount.entrySet()) {
			sortedOutput.add(entry);
		}
		
		//setting up the output file 
		File filePath = new File(file.getParent() + "/output/report.csv");
		FileWriter writer = new FileWriter(filePath);
		
		//writing header to the output file
		writer.write("department_id,number_of_orders,number_of_first_orders,percentage\n");
		
		//creating a String object for the rest of the output file content
		StringBuilder deptOrder = new StringBuilder();
		
		while (!sortedOutput.isEmpty()) {
			Map.Entry<Integer, int[]> eachDept = sortedOutput.poll();
			//writing to output only if number of orders > 0
			if (eachDept.getValue()[0] > 0) {
				
				deptOrder.append(eachDept.getKey() + ",");
				deptOrder.append(eachDept.getValue()[0] + ",");
				deptOrder.append(eachDept.getValue()[1] + ",");
				
				double ratio = (double)eachDept.getValue()[1]/(double)eachDept.getValue()[0];
				DecimalFormat f = new DecimalFormat("0.00");
				deptOrder.append(f.format(ratio));
				deptOrder.append("\n");	
			}
		}
		
		//writing all the lines to output at once.
		writer.write(deptOrder.toString());
		
		writer.close();
	}
	
	//method to clean records in products.csv file
	public String cleanProductData(String line) {
		if (line == null) return line;
		String removedQuotes = line.replaceAll("\"", "");
		String[] fields = removedQuotes.split(",");
		int numFields = fields.length;
		try {
			//expects product_id and department_id to be integers. Aisle_id, product_name can be any alphanumeric strings 
			Integer.parseInt(fields[0]);
			Integer.parseInt(fields[numFields - 1]);
		}
		catch (NumberFormatException e) {
			System.out.println("Corrupt data in product_id/department_id fields. Record dropped.");
			return "junk";
		}
		
		return removedQuotes;
	}
	
	//method to clean records in order_products.csv file
	public String cleanOrdersData(String line) {
		if (line == null) return line;
		String cleanedQuotes = line.replaceAll("\"", "");
		String[] fields = cleanedQuotes.split(",");
		int numFields = fields.length;
		try {
			//checks that all the fields in the orders file are integers.
			for (int i=0; i< numFields; i++) {
				Integer.parseInt(fields[i]);
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Corrupt record in order_products.csv file. Record dropped.");
			return "junk";
		}
		
		return cleanedQuotes;
	}
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		PurchaseAnalytics obj = new PurchaseAnalytics();
		obj.purchaseAnalytics();
		System.out.println("Done");
	}

}
