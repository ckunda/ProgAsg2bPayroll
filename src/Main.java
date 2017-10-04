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
		
		// Create new Employees Object
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
		int i = 0;
		EmployeeRecord er = new EmployeeRecord();
		do {
			er = emps.iterate(i);
			if (er != null) {
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
			i++;
		} while (er != null);
	
		//   Print Report Totals
		erTotals.lastName = "Totals";
		report.printf("\n     %-20s %9.2f %9.2f %9.2f %9.2f %9.2f\n",
				erTotals.lastName, erTotals.payRate, erTotals.hours,
				erTotals.grossPay, erTotals.fedTax, erTotals.netPay);

		//   Print Report Averages
		i = totalRecords;
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
		Employee e1 = new Employee(2, "Kunda", "Chakra", 40f, 9.99f, 0f);
		System.out.println("\n2. Employee e1:\n" + e1.toString());
		e1.calcGross();
		System.out.println("\n3. Employee e1 Calculate Gross Pay:");
		System.out.println(e1.toString());
		e1.calcTax();
		System.out.println("\n4. Employee e1 Calculate Taxes:");
		System.out.println(e1.toString());
		e1.calcNet();
		System.out.println("\n5. Employee e1 Calculate Net Pay:");
		System.out.println(e1.toString());
		
		System.out.println("\nEmployees Class Examples");
		Employees emps = new Employees(101, "Kunda", "Chakra", 40f, 9.99f, 0f);
		System.out.println("\n1. Employees:");
		System.out.println("Added one employee to emps:");
		System.out.println(emps.toString());
		emps.add(e1);
		System.out.println("\n2. Employees:");
		System.out.println("Added one more employee to emps:");
		System.out.println(emps.toString());
		emps.add(103, "Five", "Jackson", 30f, 19.99f, 0f);
		emps.add(104, "Showers", "April", 41f, 29.99f, 0f);
		emps.add(105, "Forward", "March", 60f, 91.99f, 0f);
		emps.add(106, "Rider", "Knight", 80f, 1.00f, 0f);
		emps.add(107, "Moon", "Blue", 40f, 69.99f, 0f);
		emps.add(108, "Oriented", "Object", 1f, 0.01f, 0f);
		System.out.println("Added six more employee to emps:");
		System.out.println(emps.toString());
		
		System.out.println("\n1. Search Emp ID 1: " + emps.search(1) + " (null means not found)");
		System.out.println("2. Search Emp ID 2: " + emps.search(2));
		System.out.println("3. Search Emp 'Kunda': " + emps.search("Kunda"));
		System.out.println("4. Search Emp 'KUNDA': " + emps.search("KUNDA"));
		System.out.println("5. Search Emp 'oriented': " + emps.search("oriented"));
		System.out.println("6. Search Emp ID 106: " + emps.search(106));
		System.out.println("7. Search Emp 'titanic': " + emps.search("titanic"));
		
		emps.delete(106);
		System.out.println("\n8. Delete Emp ID 106");
		System.out.println(emps.toString());
		emps.delete(345);
		System.out.println("\n9. Delete Emp ID 345");
		System.out.println(emps.toString());
		emps.delete("kunda");
		System.out.println("\n10. Delete Emp 'kunda' (first occurance only)");
		System.out.println(emps.toString());
		emps.delete("kundax");
		System.out.println("\n11. Delete Emp 'kunda' (first occurance only)");
		System.out.println(emps.toString());

		emps.sort();
		System.out.println("\n12. Sorted");
		System.out.println(emps);
		
		System.out.println("\n13. Iterate");
		int i = 0;
		do {
			er = emps.iterate(i);
			if (er != null)
				System.out.println("Employee Record er (" + i + "):\n" + er);
			else
				System.out.println("Employee Record  er (" + i + "): null\n");		
			i++;
		} while (er != null);
		
		System.out.println("\nEnd of Tests.");
		System.out.close();
	}
}
