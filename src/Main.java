import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
	// Process Payroll
		processPayroll();
		
	// Test all class methods
		classTests();
		System.out.println("Done.");
	}
	
	public static void processPayroll() throws FileNotFoundException {
		
		/* Pseudocode
		 * Create new Employees object
		   Read employee record from input file
		   Do While not end of file
		      Create new Employee object and populate it
		      Calculate Gross Pay, Taxes, and net Pay
		      Add Employee object to Employees object
		      Print input record to log file as confirmation
		      Read next employee record
		   End of file
		   
		   Sort Employees object by Last Name
		   Print Report Headings
		   Loop through Employees object
		      Print Employee Detail 
		      Accumulate Totals
		   End Loop
		   
		   Print Report Totals
		   Print Report Averages
		   
		*/
		
		// input and output file names
		final String INPUT_FILE = "inputfile.txt";
		final String OUTPUT_LOG = "inputlogfile.txt";
		final String REPORT_FILE = "reportfile.txt";
		
		// Construct the Scanner and PrintWriter objects for reading and writing
		File inputFile = new File(INPUT_FILE);
		Scanner in = new Scanner(inputFile);
		PrintWriter out = new PrintWriter(OUTPUT_LOG);
		
		out.printf("The following records were processed: \n\n");
		
		// Create new empty Employees Object
		Employees emps = new Employees();
				
		// Read the input and write the output log
		int totalRecords = 0;
		while (in.hasNextInt()) {
			
			// Read one employee data a time
			int empID = in.nextInt();
			String lastName = in.next();
			String firstName = in.next();
			float payRate = in.nextFloat();
			float hours = in.nextFloat();
			
			// Create and populate Employee object
			Employee e = new Employee(empID, lastName, firstName, hours, payRate, 0.0f);
			
			// Calculate Gross Pay, Taxes, and net Pay
			e.calcGross();
			e.calcTax();
			e.calcNet();
			
			// Add Employee object to Employees object
			emps.add(e);

			// Print to input log
			out.printf("%d %s %s %.2f %4.2f\n", empID, lastName, firstName, hours, payRate);
			out.printf("%s\n\n", e);

			totalRecords++;
		}
		out.printf("Total records processed: " + totalRecords + " \n");
		in.close();
		out.close();
		
		// Sort Employees object by Last Name
		emps.sort();
		
		// Print Report Headings
		PrintWriter report = new PrintWriter(REPORT_FILE);
		report.print("Employee Payroll Report\n\n");
		String dl = "     Employee                   Pay    Hours      Gross       Tax      Net";
		report.println(dl);
		       dl = "     Name                      Rate    Worked       Pay    Amount      Pay";
		report.println(dl);
		       dl = "     ========                  ====    ======   =======    ======     =====";
		report.println(dl);
		
		// Create a dummy employee record to hold totals	
		EmployeeRecord erTotals = new EmployeeRecord();
		
		// Loop through Employees object, print detail, and accumulate totals	
		emps.start();
		while(emps.hasNext()) {
			EmployeeRecord er = emps.getNext();
			// Print Employee Detail 
			String fullName = er.lastName + ", " + er.firstName;
			report.printf("     %-20s %9.2f %9.2f %9.2f %9.2f %9.2f\n",
					fullName, er.payRate, er.hours, er.grossPay, er.fedTax, er.netPay);

			// Accumulate Totals
			erTotals.hours += er.hours;
			erTotals.payRate += er.payRate;
			erTotals.grossPay += er.grossPay;
			erTotals.fedTax += er.fedTax;
			erTotals.netPay += er.netPay;
		}
	
		//   Print Report Totals
		erTotals.lastName = "Totals";
		report.printf("\n     %-20s %9.2f %9.2f %9.2f %9.2f %9.2f\n",
				erTotals.lastName, erTotals.payRate, erTotals.hours,
				erTotals.grossPay, erTotals.fedTax, erTotals.netPay);

		//   Print Report Averages
		int i = totalRecords;
		erTotals.lastName = "Averages";
		report.printf("     %-20s %9.2f %9.2f %9.2f %9.2f %9.2f\n",
				erTotals.lastName, erTotals.payRate/i, erTotals.hours/i,
				erTotals.grossPay/i, erTotals.fedTax/i, erTotals.netPay/i);

		report.println("\nEnd of Report.");
		report.close();
	}
	
	public static void classTests() throws FileNotFoundException {
		
		// The following code tests all the methods of the
		// EmployeeRecord, Employee, and Employees Classes
		
		// Redirect all console output to file (with time stamp)
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//		long timeStamp = System.currentTimeMillis();
		System.setOut(new PrintStream(
				new FileOutputStream("console_" + timeStamp + ".txt")));
		System.out.println("Time Stamp of output: " + timeStamp + "\n");

		/** Pseudocode (Test Script)
		   Test all constructors of the Employee Record Class
		      Create Employee Record object with default constructor (er)
		      Print the Employee Record object er - Should all have blanks/zeros
		      Create Employee Record object with parameterized constructor (er1)
		      Print the Employee Record object er1 - Should have all the values
		      Create Employee Record object with clone constructor (er1Dup)
		      Print the cloned Employee Record object er1Dup
		      Print the cloned object er1Dup - Should be same as object er1
		      
		   Test all constructors of the Employee Class
		      Create Employee object with default constructor (e)
		      Print the Employee object e - Should all have blanks/zeros
		      Create Employee object with parameterized constructor (e1)
		      Print the Employee object e1 - Should have all the values
		      Create Employee object with clone constructor (e1Dup)
		      Print the cloned Employee object er1Dup

		   Test all other methods of the Employee Class
		      Call Employee e1's calculate gross pay method
		      Print the Employee object e1 - Should have the gross pay calculated
		      Call Employee e1's calculate taxes method
		      Print the Employee object e1 - Should have the taxes calculated
		      Call Employee e1's calculate net pay method
		      Print the Employee object e1 - Should have the net pay calculated
		   
		   Test all constructors of the Employees Class
		      Create Employee object with default constructor (emps)
		      Print the Employees object emps - Should be null
		      Create Employee object with parameterized constructor (emps1)
		      Print the Employees object emps1 - Should have all the values
		      Create Employee object with parameterized constructor (emps2)
		      Print the Employees object emps2 - Should have all the values
		      Create Employee object with parameterized constructor (emps3)
		      Print the Employees object emps3 - Should have all the values
		   
		   Test all other methods of the Employees Class
		      Test 3 Add overloaded methods to emps Employee object
		      Add 1 employee with all individual parameters (emp id 102)
		      Print emps Employees Object - Should have 1 employee
		      Add 1 employee with er Employee Record object
		      Print emps Employees Object - Should have 2 employees
		      Add 1 employee with e1 Employee object
		      Print emps Employees Object - Should have 3 employees
		      
		      Test 2 delete overloaded methods
		      Delete employee id 102
		      Print emps Employees object - Should not have emp id 102
		      Delete employee whose last name is 'Kunda'
		      Print emps Employees object - Should not have first occurance
		         of last name 'Kunda'
		         
		      Add six more employees to test additional methods
		      Print emps Employees object - Should have 7 employees
		      
		      Test search method
		      Search for emp id 101 - Should find it
		      Search for emp id 102 - Should not find it
		      Search for last name 'Kunda' - Should find first occurance
		      Search for last name 'KUNDA' - Should find first occurance
		      Search for last name 'titanic' - Should not find it
		      
		      Test sort method - Sort by Last Name
		      Print emps Employees object - Should be in Last Name order
		      
		      Test Iterate methods: start(), hasNext(), and getNext()
		      
		 */
		
		System.out.println("EmployeeRecord Class Examples");
		EmployeeRecord er = new EmployeeRecord();
		System.out.println("\n1. Employee Record (default) er:\n" + er);
		EmployeeRecord er1 = new EmployeeRecord(2, "Kunda", "Chakra", 40f, 9.99f, 0f);
		System.out.println("\n2. Employee Record (parameterized) er1:\n" + er1.toString());
		EmployeeRecord er1Dup = new EmployeeRecord(er1);
		System.out.println("\n3. Employee Record (clone) er1Dup:\n" + er1Dup.toString());
		
		System.out.println("\nEmployee Class Examples");
		Employee e = new Employee();
		System.out.println("\n1. Employee (default) e:\n" + e.toString());
		Employee e1 = new Employee(1, "Kunda", "Chakra", 40f, 9.99f, 0f);
		System.out.println("\n2. Employee (parameterized) e1:\n" + e1.toString());
		Employee e1Dup = new Employee(e1.e);
		System.out.println("\n3. Employee (cloned) e1Dup:\n" + e1Dup.toString());
		e1.calcGross();
		System.out.println("\n4. Employee e1 Calculate Gross Pay:");
		System.out.println(e1.toString());
		e1.calcTax();
		System.out.println("\n5. Employee e1 Calculate Taxes:");
		System.out.println(e1.toString());
		e1.calcNet();
		System.out.println("\n6. Employee e1 Calculate Net Pay:");
		System.out.println(e1.toString());
		
		System.out.println("\nEmployees Class Examples");
		Employees emps = new Employees();
		System.out.println("\n1. Employees emps (empty object):");
		System.out.println(emps.toString());
		Employees emps2 = new Employees(101, "Kunda", "Chakra", 40f, 9.99f, 0f);
		System.out.println("\n2. Employees emps2:");
		System.out.println(emps2.toString());
		Employees emps3 = new Employees(er1);
		System.out.println("\n3. Employees emps3 (created with er1 Employee Record object):");
		System.out.println(emps3.toString());
		Employees emps4 = new Employees(e1);
		System.out.println("\n4. Employees emps4 (created with e1 Employee object):");
		System.out.println(emps4.toString());		
		
		System.out.println("\nTest three Add overloaded methods to emps employees object:");
		System.out.println("\nemps object before adding:");
		System.out.println(emps.toString());
		
		System.out.println("\n5. emps object after adding individual values:");
		emps.add(102, "Five", "Jackson", 30f, 19.99f, 0f);
		System.out.println(emps.toString());
		
		System.out.println("\n6. emps object after adding employee record er1:");
		emps.add(er1);
		System.out.println(emps.toString());
		
		System.out.println("\n7. emps object after adding employee e1:");
		emps.add(e1);
		System.out.println(emps.toString());
		
		System.out.println("\nTest two Delete overloaded methods from emps employees object:");

		System.out.println("\n8. emps object after deleting employee ID 102:");
		emps.delete(102);
		System.out.println(emps.toString());

		System.out.println("\n9. Delete Emp 'Kunda' (first occurance only)");
		emps.delete("Kunda");
		System.out.println(emps.toString());
		
		emps.add(103, "Five", "Jackson", 30f, 19.99f, 0f);
		emps.add(104, "Showers", "April", 41f, 29.99f, 0f);
		emps.add(105, "Forward", "March", 60f, 91.99f, 0f);
		emps.add(106, "Rider", "Knight", 80f, 1.00f, 0f);
		emps.add(107, "Moon", "Blue", 40f, 69.99f, 0f);
		emps.add(108, "Oriented", "Object", 1f, 0.01f, 0f);
		System.out.println("\n10. Added six more employees to emps:");
		System.out.println(emps.toString());
		
		System.out.println("\n11. 1.Search Emp ID 1: " + emps.search(1));
		System.out.println("  2. Search Emp ID 2: " + emps.search(2) + " (null means not found)");
		System.out.println("  3. Search Emp 'Kunda': " + emps.search("Kunda"));
		System.out.println("  4. Search Emp 'KUNDA': " + emps.search("KUNDA"));
		System.out.println("  5. Search Emp 'oriented': " + emps.search("oriented"));
		System.out.println("  7. Search Emp 'titanic': " + emps.search("titanic"));

		emps.sort();
		System.out.println("\n12. Sorted (see above number 10 - before sort)");
		System.out.println(emps);
		
		System.out.println("\n13. Iterate methods: start(), hasNext(), and getNext()");
		emps.start();
		while(emps.hasNext()) {
			System.out.println(emps.getNext().toString());
		}
		
		System.out.println("\nEnd of Tests.");
		System.out.close();
	}
}
