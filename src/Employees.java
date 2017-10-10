import java.util.ArrayList;
import java.util.Comparator;
/**
 * Employees Class
 * @author Chakra Kunda
 *
 */
public class Employees {

	ArrayList<Employee> emps;
	final int START = 0;
	int empIter = 0;
	/**
	 * Parameterized constructor
	 * @param eID Employee ID
	 * @param lName Last Name
	 * @param fName First Name
	 * @param h Hours
	 * @param pr Pay Rate
	 * @param defr ???
	 */
	/**
	 * Default constructor
	 */
	public Employees() {
		emps = new ArrayList<Employee>();
	}
	public Employees(int eID, String lName, String fName,
			float h, float pr, float defr) {
		emps = new ArrayList<Employee>();
		Employee e = new Employee(eID, lName, fName, h, pr, defr);
		emps.add(e);
	}
	/**
	 * Parameterized constructor
	 * @param er Employee Record
	 */
	public Employees(EmployeeRecord er) {
		emps = new ArrayList<Employee>();
		Employee e = new Employee(er);
		emps.add(e);	
	}
	/**
	 * Parameterized constructor
	 * @param e Employee
	 */
	public Employees(Employee e) {
		emps = new ArrayList<Employee>();
		emps.add(e);
	}
	/**
	 * Overloaded add method adds an employee
	 * @param eID Employee ID
	 * @param lName Last Name
	 * @param fName First Name
	 * @param h Hours
	 * @param pr Pay Rate 
	 * @param defr ???
	 */
	public void add(int eID, String lName, String fName,
			float h, float pr, float defr) {
		Employee e = new Employee(eID, lName, fName, h, pr, defr);
		emps.add(e);
	}
	/**
	 * Overloaded add method adds an employee
	 * @param er Employee Record
	 */
	public void add(EmployeeRecord er) {
		Employee e = new Employee(er);
		emps.add(e);	
	}
	/**
	 * Overloaded add method adds an employee
	 * @param e Employee
	 */
	public void add(Employee e) {
		emps.add(e);
	}
	/**
	 * Overloaded delete method removes an employee
	 * @param lName Last Name
	 */
	public void delete(String lName) {
		
		// Return if list is empty
		if (emps.isEmpty()) return;

		// Loop thru the list to find the employee and remove it
		int i = 0;
		while (i < emps.size()) {
			if (emps.get(i).e.lastName.toUpperCase().equals(lName.toUpperCase())) {
				emps.remove(i);
			}
			i++;
		}
	}
	/**
	 * Overloaded delete method removes an employee
	 * @param eID Employee ID
	 */
	public void delete(int eID) {
		
		// Return if list is empty
		if (emps.isEmpty()) return;
		
		// Loop thru the list to find the employee and remove it
		int i = 0;
		while (i < emps.size()) {
			if (emps.get(i).e.empID == eID) {
				emps.remove(i);
			}
			i++;
		}		
	}
	/** 
	 * Overloaded search method
	 * @param lName Last Name
	 * @return Index
	 */
	public EmployeeRecord search(String lName) {
		
		// Return null if list is empty
		if (emps.isEmpty()) return null;

		// Loop thru the list to find the employee and return it
		int i = 0;
		while (i < emps.size()) {
			if (emps.get(i).e.lastName.compareToIgnoreCase(lName) == 0) {
				return emps.get(i).e;
			}
			i++;
		}
		return null;
	}
	/**
	 * Overloaded search method
	 * @param eID Employee ID
	 * @return Index
	 */
	public EmployeeRecord search(int eID) {
		
		// Return null if list is empty
		if (emps.isEmpty()) return null;

		// Loop thru the list to find the employee and return it
		int i = 0;
		while (i < emps.size()) {
			if (emps.get(i).e.empID == eID) {
				return emps.get(i).e;
			}
			i++;
		}		
		return null;
	}
	/**
	 * Sorts employees on last name
	 */
	public void sort() {
		emps.sort(Comparator.comparing(Employee::getLastName));
	}
	/**
	 * Start iteration by resetting the cursor to starting position
	 */
	public void start() {
		// Set the cursor to the first employee record
		empIter = START;
	}
	/**
	 * Are there more Employees?
	 * @return boolean Yes/No 
	 */
	public boolean hasNext() {
		if (empIter < emps.size()) return true;
		return false;
	}
	/**
	 * Get next employee record from the cursor position
	 * @return Employee Record
	 */
	public EmployeeRecord getNext() {
		// Return employee if not at end of list
		if (empIter < emps.size())
			return emps.get(empIter++).e;		
		else
			return null;
	}
	/**
	 * Returns a printable state of the Employees
	 */
	public String toString() {
		int i = 0;
		String str = null;
		if (emps.isEmpty()) return str;
		str = new String("Number of entries: " + emps.size());
		while (i < emps.size()) {
			str += new String("\nIndex " + i + ": " + emps.get(i).e.toString());
			i++;
		}	
		return str;
	}
}
