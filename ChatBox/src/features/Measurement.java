

package features;

import java.io.PrintWriter;
import java.util.Scanner;

import configuration.Response;

/**
 *  a class to convert the measurement such as weight, length and temperature
 * @author fadelalshammasi
 *
 */

public class Measurement implements Feature  {
	
	private static final double FROM_POUNDS_TO_KG=0.453592;
	private static final double FROM_KG_TO_POUNDS=2.20462;
	private double weight;
	
	private static final double M_TO_FT=3.28084;
	private static final double FT_TO_M=0.3048;
	private double length;

	

	private double temperature;

	
	
	/**
	 * a constructor that calls the helper functions to show the options of conversion 
	 * @param query
	 */
	public Measurement(String query)
	{
		int option = options();
		
		while(option !=4) {
			option=options();
		switch(option) {
			case 1:
				weight();
				break;
			case 2:
				length();
				break;
			case 3:
				temperature();
				break;
				
			case 4: 
				break;
				
		}
	}
				
		parseQuery(query);
	}

	/**
	 *  Convert from  Fahrenheit to Celsius or(and) from  Celsius to Fahrenheit
	 */
	private void temperature() {
		Scanner scan=new Scanner(System.in);
		//PrintWriter pw=new PrintWriter(fw);
		
		System.out.println("1. Convert from  Fahrenheit to Celsius : ");
		System.out.println("2. Convert from  Celsius to Fahrenheit : ");
		int num=scan.nextInt();
		//System.out.println();
		//System.out.print("Please select an option: ");
		
		if(num==1) {
		 System.out.println("Enter the Temperature : ");
		   double num1=scan.nextDouble();
		   temperature=(num1-32) * 5.0/9.0;
		}
		else if(num==2) {
			 System.out.print("Enter the Temperature : ");
			double num1=scan.nextDouble();
			temperature=(num1 * 9.0/5)+32;
		}
		System.out.println("The Temperature is: "+ temperature);

	}


	/**
	 *  Convert from  meters to feet or(and) from  feet to meters
	 */
	private void length() {
		
		Scanner scan=new Scanner(System.in);
		
		System.out.println("1. Convert from meters to feet  : ");
		System.out.println("2. Convert from feet to meter : ");
		int num=scan.nextInt();
		
		if(num==1) {
		 System.out.print("Enter the height : ");
		   double num1=scan.nextDouble();
		   length=M_TO_FT * num1;
		}
		else if(num==2) {
			 System.out.print("Enter the height : ");
			double num1=scan.nextDouble();
			length=FT_TO_M * num1;
		}
			System.out.println("The length is: "+length);

	}

	/**
	 * Convert from  pounds to kg or(and) from  kg to pounds
	 */
	private void weight() {
		Scanner scan=new Scanner(System.in);
		//PrintWriter pw=new PrintWriter(fw);
		
		System.out.println("1. Convert from pounds to kg : ");
		System.out.println("2. Convert from kg to pounds : ");
		int num=scan.nextInt();
		//System.out.println();
		//System.out.print("Please select an option: ");
		
		if(num==1) {
		 System.out.print("Enter the weight : ");
		   double num1=scan.nextDouble();
		   weight=FROM_POUNDS_TO_KG * num1;
		}
		else if(num==2) {
			 System.out.print("Enter the weight : ");
			double num1=scan.nextDouble();
			weight=FROM_KG_TO_POUNDS * num1;
		}
			System.out.println("The weight is: "+weight);

	}


	/**
	 * A method to display all of the options that the user can choose from 
	 * @return the choice that the user entered
	 */
	private int options() {
		Scanner scan=new Scanner(System.in);
		System.out.println("\n Options to choose from \n");
		System.out.println("1. Weight Conversion ");
		System.out.println("2. Length Conversion");
		System.out.println("3. Temperature Conversion ");
		System.out.println("4. Exit ");
		System.out.println();
		System.out.print("Please select an option: ");
		int option= scan.nextInt();
		
		return option;
	}

	@Override
	public Response setResponse() {
		return new Response("");

	}

	@Override
	public void parseQuery(String query) {
		
	}

	
	
}
