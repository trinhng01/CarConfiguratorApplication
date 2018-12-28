package util;
import java.io.*;
import model.*;
import exception.*;
import java.util.Properties;
import java.util.Scanner;

public class FileIO {
	public boolean DEBUG = false;
	
	//Read text file to database
	public Automotive readTextFile(String fileName) throws AutoException{
		Automotive a1 = new Automotive();
		Scanner scanner = new Scanner (System.in);
		try{
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			boolean eof = false;
			int counter = 0;
			int opsetSize = 0;
			if (DEBUG)
				System.out.println("\n======== READING FROM FILE ========\n");
			while(!eof)
			{
				String line = buff.readLine();
				if (DEBUG)
					System.out.println("--- [TEST - READING] " + line);

				//Check if file is empty, if yes then exit program
				//Next lab, have user reenter a different text file 
				if (line == null)
				{
					eof = true;
					if (counter == 0)
					{
						System.out.println("\n======== FILE IS EMPTY ========\n");
						System.exit(-1);
					}
				}
				//Read and set the name of the automotive
				if (counter == 0)
				{
					if (line == null || !isAlpha(line))
					{
						float basePrice = Integer.parseInt(line);
						a1.setBaseprice(basePrice);
						if (DEBUG)
							System.out.println("Base price: $" + a1.getBaseprice());
						System.out.printf("[ERROR] -- Automotive name is missing! Please enter the missing name --> ");
						line = scanner.nextLine();
						counter++;
					}
					a1.setMake(line);
					if (DEBUG)
						System.out.println("Name: " + a1.getMake());
				}
				else if (counter == 1)
				{
					a1.setModel(line);
					if (DEBUG)
						System.out.println("Name: " + a1.getModel());
				}
				//Read and set the base price of the automotive
				else if (counter == 2)
				{
					float basePrice = Integer.parseInt(line);
					a1.setBaseprice(basePrice);
					if (DEBUG)
						System.out.println("Base price: $" + a1.getBaseprice());
				}
				else if(counter == 3)
				{
					//Read in OptionSet array size
					opsetSize = Integer.parseInt(line); 
					if (opsetSize > 1000)
					{
						System.out.printf("[ERROR] -- OptionSet Size is too big! Please enter the size of OptionSet array --> ");
						line = scanner.nextLine();
						opsetSize = Integer.parseInt(line);
					}
					if (DEBUG)
						System.out.println("OptionSet Array Size: " + opsetSize);
					//Initialize OptionSet Array to avoid null pointer exception
					a1.initializeOptionSet(opsetSize);
				}
				else 
				{
					for (int k = 0; k < opsetSize && line != null; k++)
					{
						if (k > 0)
						{
							line = buff.readLine();
							if (DEBUG)
								System.out.println("--- [TEST - READING] " + line);
						}
						//Read in OptionSet Name
						String opsetname = line;
						//Set OptionSet Name
						a1.setOpsetName(opsetname, k);
						if (DEBUG)
							System.out.printf("\nOptionSet %s Name: %s\n", k+1, a1.getOpsetName(k));
						//Read in Option array size
						line = buff.readLine();
						if (DEBUG)
							System.out.println("--- [TEST - READING] " + line);
						int optionSize = 0;
						optionSize = Integer.parseInt(line);
						if (optionSize > 100)
						{
							System.out.printf("[ERROR] -- Option Size is too big! Please enter the size of Option array --> ");
							line = scanner.nextLine();
						}
						optionSize = Integer.parseInt(line);
						if (DEBUG)
							System.out.printf("   Option %s Array Size: %s\n", k+1, optionSize);
						//Initialize Option Array to avoid null pointer exception
						a1.initializeOption(k, optionSize);

						for(int i = 0; i < optionSize; i++)
						{
							//Read and set Option Name
							line = buff.readLine();
							if (DEBUG)
								System.out.println("--- [TEST - READING] " + line);
							if (line.equals("null"))
							{
								System.out.printf("[ERROR] -- Option name is missing! Please enter the name of option --> ");
								line = scanner.nextLine();
							}
							a1.setOptionName(line, k, i);
							if (DEBUG)
								System.out.printf("Option %s Name: %s\n", i+1, a1.getOptionName(k, i));

							//Read and set Option Price
							line = buff.readLine();
							if (DEBUG)
								System.out.println("--- [TEST - READING] " + line);
							if (line == null)
							{
								System.out.printf("[ERROR] -- Option price is missing! Please enter the price for the option --> ");
								line = scanner.nextLine();
							}
							float price = Float.parseFloat(line);
							a1.setOptionPrice(price, k, i);

							if (DEBUG)
								System.out.printf("   Price: $%.2f\n", a1.getOptionPrice(k, i));
						}

					}
				}
				counter++;
			}
			buff.close();
			if (DEBUG)
				System.out.printf("\n======== END OF FILE ========\n");
		}catch (NullPointerException e){
			System.err.println("Error--!" + e.toString());
		}catch (FileNotFoundException e){
			System.out.println("Error -- " + e.toString());
			throw new AutoException(1, "FileNotFoundException");
		}catch (NumberFormatException e){
			System.out.println("Error -- " + e.toString());
		}catch (Exception e){
			System.out.println("Error -- " + e.toString());
		}
		return a1;
	}
	
	//Read Properties file to database
	public Automotive readPropFile(Properties props) throws AutoException{
		Automotive a2 = new Automotive();

		String CarMake = props.getProperty("CarMake").trim(); //this is how you read a property. It is like getting a value from HashTable.
		if(DEBUG)
			System.out.println("Car Make: " + CarMake);
		if(!CarMake.equals(null)) {
			String CarModel = props.getProperty("CarModel").trim();
			float CarPrice = Float.parseFloat(props.getProperty("CarPrice").trim());
			int opsetSize =  Integer.parseInt(props.getProperty("OpSetSize").trim());
			if(DEBUG)
				System.out.println(CarModel + " -- " + CarPrice + " -- " + opsetSize);
			a2 = new Automotive(CarMake, CarModel, CarPrice, opsetSize);
			
			for (int i = 0; i < opsetSize; i++)
			{
				//Read in OptionSet Name
				String OptionSetName = props.getProperty("Option" + (i+1)).trim();
				a2.setOpsetName(OptionSetName, i);
				//Read in OptionSet Size
				int optionSize = Integer.parseInt(props.getProperty("Option"+ (i+1) +"Size").trim());
				if(DEBUG)
					System.out.println("\n+++ OptionSet: " + OptionSetName + " -- Size " + optionSize);
				a2.initializeOption(i, optionSize);
				for(int n = 0; n < optionSize; n++)
				{
					if(DEBUG)
						System.out.println("~~~ Option" + (i+1) + "Value" + (n+1));
					String OptionName = props.getProperty("Option" + (i+1) + "Value" + (n+1)).trim();
					float OptionPrice = Float.parseFloat(props.getProperty("Option" + (i+1) + "Price" + (n+1)).trim());
					if(DEBUG)
						System.out.println(OptionName + " -- $" + OptionPrice);
					a2.setOptionName(OptionName, i, n);
					a2.setOptionPrice(OptionPrice, i, n);
				}
			}
		}
		return a2;
	}
	//Source: http://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
	public boolean isAlpha(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	public void serializeAuto(Automotive a1)
	{
		try
		{  
			String outFileName = "auto.ser";
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFileName));
			out.writeObject(a1);
			if (DEBUG)
			{
				System.out.printf("\n========== SERIALIZING OBJECT TO FILE ==========\n");
			}
			out.close();
		}
		catch(Exception e)
		{  
			System.out.print("Error: " + e);
			System.exit(1);
		}
	}

	public Automotive deserializeAuto(String filename)
	{
		try
		{  
			ObjectInputStream in =  new ObjectInputStream(new FileInputStream(filename));
			Automotive newAuto = (Automotive) in.readObject();
			if (DEBUG)
			{
				System.out.printf("\n======== DESERIALIZING OBJECT FROM FILE ========\n");
			}
			in.close();
			return newAuto;
		}
		catch(Exception e)
		{  
			System.out.print("Error: " + e);
			System.exit(1);
			return null;
		}
	}
}
