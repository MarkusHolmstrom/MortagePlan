package mortagePlan_MHolmstrom;


public class Customer {
	private String name;
	private double totalLoan;
	private double interest;
	private int years;
	
	private double fixedMonthlyPayment;
	
	public Customer(String name, double totalLoan, double interest, int years) {
		this.name = name;
		this.totalLoan = totalLoan;
		this.interest = interest;
		this.years = years;
	}
	
	public String getProspect() {
		return name + " wants to borrow " + totalLoan + " € for a period of " + years + " years and pay " + fixedMonthlyPayment + " € each month"; 
	}
	public void setFixedPayment(double payment) {
		fixedMonthlyPayment = payment;
	}
}

