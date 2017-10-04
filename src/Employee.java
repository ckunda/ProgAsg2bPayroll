/**
 * Employee Class
 * @author Chakra Kunda
 *
 */
public class Employee {

	EmployeeRecord e;
	/**
	 * Default constructor
	 */
	public Employee() {
		e = new EmployeeRecord();
	}
	/**
	 * Parameterized constructor
	 * @param lName Last Name
	 * @param fName First Name
	 * @param h Hours
	 * @param pr Pay Rate
	 * @param defr ??
	 */
	public Employee(int eID, String lName, String fName,
			float h, float pr, float defr) {
		e = new EmployeeRecord(eID, lName, fName, h, pr, defr);
	}
	/**
	 * Parameterized constructor
	 * @param er Employee Record
	 */
	public Employee(EmployeeRecord er) {
		e = new EmployeeRecord(er);			
	}
	/**
	 * Calculate Gross Pay with overtime
	 */
	public void calcGross() {
		// Calculate Regular + Overtime Pay
		if (e.hours > 40.00f) {
			e.grossPay = e.payRate * 40.00f;
			e.grossPay += 1.50f * e.payRate * (e.hours - 40.00f);
		}
		// Calculate Regular Pay
		else {
			e.grossPay = e.payRate * e.hours;
		}
	}
	/**
	 * Calculate Taxes
	 */
	public void calcTax() {
		e.fedTax = e.grossPay * .15f;
		e.stateTax = e.grossPay * 0.0f;
		e.ssiTax = e.grossPay * 0.0f;
	}
	/**
	 * Calculate Net Pay = Gross Pay - all Taxes
	 */
	public void calcNet() {
		e.netPay = e.grossPay - e.fedTax - e.stateTax - e.ssiTax;
	}
	/**
	 * Returns Last Name
	 * @return
	 */
	public String getLastName() {
		return e.lastName;
	}
	/**
	 * Returns printable values
	 */
	public String toString() {
		return e.toString();
	}
}
