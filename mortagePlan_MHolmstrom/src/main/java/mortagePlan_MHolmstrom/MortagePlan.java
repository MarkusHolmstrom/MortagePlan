package mortagePlan_MHolmstrom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MortagePlan {
	
	private static final String START_COMMAND = "Command?";
	private static final String READ_DOC_COMMAND = "read doc";
	private static final String PRINT_ALL_COMMAND = "print all";
	private static final String GET_TOTAL_CUSTOMERS_COMMAND = "get total";
	private static final String EXIT_COMMAND = "exit";
	
	private String command = "";
	private Scanner scanner = new Scanner(System.in);
	
	private String file = "prospects.txt";
	
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	
	private boolean firstLine = true;
	
	public static void main(String[] args) {
		
		MortagePlan mp = new MortagePlan();
		mp.start();
	}
	
	public void start() {
		runCommandLoop();
	}
	
	/***************Commands:********************************************************/
	
	private void runCommandLoop() {
		do {
			command = readCommand(START_COMMAND);
			handleCommand(command.toLowerCase());
		} while (!command.equals(EXIT_COMMAND));
	}

	private String readCommand(String s) {
		System.out.println(s);
		String com = scanner.nextLine();
		return com;
	}
	
	private void handleCommand(String command) {
		switch (command) {
		case "rdc":
		case READ_DOC_COMMAND:
			readDocumentInSrc();
			break;
		case "pad":
		case PRINT_ALL_COMMAND:
			printProspects(customers);
			break;
		case "gtc":
		case GET_TOTAL_CUSTOMERS_COMMAND:
			getTotalCustomers();
			break;
		case "exi":
		case EXIT_COMMAND:
			closeDown();
			break;
		default:
			printErrorMessage();
		}
	}

	private void printErrorMessage() {
		System.out.println("Error: command not found");
		System.out.println();
		command = "";
	}
	
	private void closeDown() {
		System.out.println("Exiting program");
		scanner.close();
	}
	
	/***********************************************************/
	
	public void readDocumentInSrc() {
		try {
			Scanner myReader = new Scanner(new File(file));
			while (myReader.hasNextLine()) {
				String textLine = myReader.nextLine();
				// Only read customer information if its not the first line and ignore if it contains fewer than two characters:
				if (!firstLine && textLine.length() > 1) {
					createCustomerInfo(textLine);
				}
				else {
					firstLine = false;
				}
			}
		firstLine = true;
		myReader.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occurred: file not found");
	    	e.printStackTrace();
	    }
		
		printProspects(customers);
	}
	
	private void createCustomerInfo(String line) {
		
		ArrayList<Integer> commas = new ArrayList<Integer>();
		// Search for commas to get their positions and number of frequency:
		for(int i = 0; i < line.length(); i++){
		    if(line.charAt(i) == ','){
			   commas.add(i);
		    }
		}
		
		// Sorts the commas backwards (based on the lists length), in case there is a comma in the customers name:
		if (commas.size() >= 3) {
			String name = line.substring(0, commas.get(commas.size() - 3));
			double totalLoan = Double.parseDouble(line.substring(commas.get(commas.size() - 3) + 1, commas.get(commas.size() - 2)));
			double interest = Double.parseDouble(line.substring(commas.get(commas.size() - 2) + 1, commas.get(commas.size() - 1)));
			int years = Integer.parseInt(line.substring(commas.get(commas.size() - 1) + 1, line.length()));
			
			newCustomer(name, totalLoan, interest, years);
		}
	}
	
	public void newCustomer(String name, double totalLoan, double interest, int years) {
		Customer customer = new Customer(name, totalLoan, interest, years);
		customer.setFixedPayment(CalculateMonthlyPayment(interest, totalLoan, years));
		if (!customers.contains(customer)) {
			addCustomer(customer);
		}
	}
	
	private void addCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public int getTotalCustomers() {
		System.out.println("Nr of customers registrered: " + customers.size());
		return customers.size();
	}

	private static double CalculateMonthlyPayment(double interest, double totalLoan, int numberPayments) {
		
		return totalLoan * ((interest/1200) * (power(1 + (interest/1200), numberPayments))) /
				(power(1 + (interest/1200), numberPayments) - 1);
	}
	
	private static double power(double base, int exponent) {
        double result = 1;
        while (exponent != 0)
        {
            result *= base;
            --exponent;
        }
        return result;
	}
	
	private void printProspects(ArrayList<Customer> customers) {
		if(customers.size() > 0) {
			for (int i = 0; i < customers.size(); i++) {
				// Unsure if it was meant that these dotted lines should be in the print, but this way they are easy to remove if not necessary:
				System.out.println("\n****************************************************************************************************\n");
									
				System.out.println("Prospect " + (i + 1) + ": " + customers.get(i).getProspect());
				
				System.out.println("\n****************************************************************************************************\n");
			}
		}
		else {
			System.out.println("There is no customers registrered");
		}
	}
}

