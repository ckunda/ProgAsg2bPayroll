/**
 * Employee Record Class
 * @author Chakra Kunda
 *
 */
public class EmployeeRecord {
	
	public int empID;
	public String lastName;
	public String firstName;
	public float hours;
	public float payRate;
	public float grossPay;
	public float fedTax;
	public float stateTax;
	public float ssiTax;
	public float netPay;
	/**
	 * Default constructor
	 */
	public EmployeeRecord() {
		empID = 0;
		lastName = "";
		firstName = "";
		hours = 0;
		payRate = 0;
		grossPay = 0;
		fedTax = 0;
		stateTax = 0;
		ssiTax = 0;
		netPay = 0;
	}
	/**
	 * Parameterized constructor
	 * @param eID Employee ID
	 * @param lName Last Name
	 * @param fName First Name
	 * @param h Hours
	 * @param pr Pay Rate
	 * @param defr ??
	 */
	public EmployeeRecord(int eID, String lName, String fName,
			float h, float pr, float defr) {
		empID = eID;
		lastName = lName;
		firstName = fName;
		hours = h;
		payRate = pr;
	}
	/**
	 * Clone constructor
	 * @param er Employee Record to clone
	 */
	public EmployeeRecord(EmployeeRecord er) {
		empID = er.empID;
		lastName = er.lastName;
		firstName = er.firstName;
		hours = er.hours;
		payRate = er.payRate;
		grossPay = er.grossPay;
		fedTax = er.fedTax;
		stateTax = er.stateTax;
		ssiTax = er.ssiTax;
		netPay = er.netPay;		
	}
	/**
	 * Returns printable values
	 */
	public String toString() {
		return new String("Emp ID: " + empID +
				 "; Last Name: " + lastName +
				 "; First Name: " + firstName +
				 "; Hours: " + hours +
				 "; Pay Rate: = " + payRate +
				 "; Gross Pay: = " + grossPay +
				 "; Fed Tax: = " + fedTax +
				 "; State Tax: = " + stateTax +
				 "; SSI Tax: = " + ssiTax +
				 "; Net Pay: = " + netPay);
	}
}
