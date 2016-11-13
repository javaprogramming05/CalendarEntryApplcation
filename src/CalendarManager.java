
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class CalendarManager {

	//method to save the calendar entry
	public void save(String month, String day, String year, String event) 
	{
		//declaring the required variables
		String fileName = year+"-"+month+".txt";
		File file = new File(fileName);
		int days =0;

		try{
			//If file exists, read the data from the existing file  
			//and then modify the content and upload the file
			String[] files;
			PrintWriter writer;
			if(file.exists())
			{  
				FileInputStream fis = new FileInputStream(file);
				Scanner scanner = new Scanner(fis);
				int i = 0;
				days = getDays(month, year)+1; //Adding 1 as array starts from zero and there is no 0th day.
				files = new String[days];
				try
				{
					//read the file content
					while(scanner.hasNextLine())
					{
						files[i]=scanner.nextLine();                      
						i++;
					}
					//close the input file
					scanner.close();
					//write the data back into the file
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					writer = new PrintWriter(oos);

					//store the calendar entry at the given day
					files[Integer.parseInt(day)]+=", "+day+" - "+event;

					for(i = 0;i<getDays(month, year)+1;i++)
					{
						writer.println(files[i]);
					}
					writer.flush();
					writer.close();

				}catch(Exception e){}
			}
			//if file does not exist, create a new file
			//and write content to the file
			else
			{
				days = getDays(month, year)+1;
				files = new String[days];
				files[Integer.parseInt(day)]=day+" - "+event;              
				writer = new PrintWriter(file);

				for(int i = 0;i<days;i++)
				{
					if(i == Integer.parseInt(day))
						writer.println(files[Integer.parseInt(day)]);
					else
						writer.println();
				}

				writer.close();  
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}      


	//method to get the number of days for that particular month and year, 
	public int getDays(String month, String year)
	{
		Calendar calendar = Calendar.getInstance();
		int a = Integer.parseInt(month);
		int finalMonth = a-1;
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, finalMonth);
		int numDays = calendar.getActualMaximum(Calendar.DATE);
		return numDays;
	}


	//method to retrieve the calendar entry
	public String retrieve(String month, String day, String year)
	{
		String fileName = year+"-"+month+".txt";      
		String entry ="";
		File file = new File(fileName);      
		try{

			FileInputStream fis = new FileInputStream(fileName);      
			Scanner fileRead = new Scanner(fis);
			String[] files = new String[getDays(month, year) + 1];
			int i=0;
			//if file exists then read file content
			if(file.exists())
			{
				while(fileRead.hasNextLine())
				{                  
					files[i]=fileRead.nextLine();                  
					if(i == Integer.parseInt(day)) //if there exists entry for the given day
						entry =  files[i];
					i++;
					if(i == files.length){
						return entry;
					}
				}
			}
			else
			{
				return  "File not found";
			}
			fileRead.close();

		}catch(Exception e)
		{

			return  "File not found";

		}      
		return entry;
	}

	//method to validate calendar date(month, day, year)
	public  boolean isValidDate(String year, String month, String day) {


		String inDate= month+"-"+day+"-"+year;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

}