//Che-Chi (Jack) Liu
//V00850558

//Reads a CVS file and performs one of the 3 aggregate functions(‘count’, ‘sum’, or ‘avg‘) on a column from the input CVS file.
//The worst case running time of the entire program is O(n log2 n) for an input with n rows.

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Aggregate {
	public static String[] data;
	
	public static void showUsage(){
		System.err.printf("Usage: java Aggregate <function> <aggregation column> <csv file> <group column 1> <group column 2> ...\n");
		System.err.printf("Where <function> is one of \"count\", \"sum\", \"avg\"\n");	
	}
	
	public static void input(BufferedReader br, String agg_column, String[] group_columns, String[] header_line, int[] group_count, String function) {
		Scanner input = new Scanner(br);
		String line = "";

		ArrayList<String> list = new ArrayList<String>();

		//to find which column is agg_cloumn
		int count = 0;		
		for(int i = 0; i < header_line.length; i++) {
			if(agg_column.equals(header_line[i])) {
				count= i;
				break;
			}
		}
		
		//print the name of agg_column and groups columns
		for(int i = 0; i < group_columns.length; i++) {
			System.out.print(group_columns[i]+",");
		}
		System.out.print(function+"("+agg_column+")"+ "\n");

		int x=0;
		while(input.hasNext()) {
			line = input.nextLine();
			if(line.trim().isEmpty()) {
          			continue;
		   	}
			
			list.add(line);
			
			x++; //count the columns
		}
		
		int z=0;
		String[] numbers = new String[x];
		String[] groupkey= new String[x];
		String group_key="";
		
		for(String w: list) {
			data = w.split(",");
			
			if(!data[count].matches("^-?\\d*\\.?\\d+$")) {   //Assume the agg_column's numerical data are only postitve and negative numbers
				System.err.printf("The aggregation column contains non-numerical data in this row"+"\n"); 
			}else {
				numbers [z] = data[count];	
			}
			
			for(int i = 0; i < group_columns.length; i++) {		
				group_key = group_key.concat((data[group_count[i]]));
			}
			
			groupkey[z] = group_key;
			group_key= "";

			String[] data;
			z++;
		}
		
		List<String> stringList = new ArrayList<String>(Arrays.asList(groupkey));
		HashMap<String, Integer> occurrences = new HashMap<String, Integer>();

		for(String s: stringList) {
			occurrences.put(s, Collections.frequency(stringList,s));
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put((groupkey[0]),(numbers[0]));
		double y;
		int yy;
		
		for(int i = 1; i < groupkey.length; i++) {
			String same= groupkey[i];
			if(function.equals("sum") || function.equals("avg")) {
				if(map.containsKey(same)) {	
					if(numbers[i].contains(".")) {
            					y = (Double.parseDouble(map.get(same))) + (Double.parseDouble(numbers[i]));
            					map.put(groupkey[i], (Double.toString(y)));
            					y=0;
        				}else {
      						yy = (Integer.parseInt((map.get(same)))) + (Integer.parseInt(numbers[i]));
            					map.put(groupkey[i], (Integer.toString(yy)));
            					yy=0;
      					}	
				}else {
					map.put((groupkey[i]),(numbers[i]));
				}
			}
			else{
				map.put((groupkey[i]),(numbers[i]));
			}
			same="";
		}
		
		if(function.equals("count")) {
    			System.out.println(occurrences.keySet());

			for(Integer value : occurrences.values()) {
    				System.out.print(value+",");
			}
		}

		if(function.equals("sum")) {
    			System.out.println(occurrences.keySet());
    			System.out.print(map.values()+",");
		}
	}
	
	public static void main(String[] args){
		//At least four arguments are needed
		if (args.length < 4){
			showUsage();
			return;
		}

		String agg_function = args[0];
		String agg_column = args[1];
		String csv_filename = args[2];
		
		int[] group_count = new int[args.length - 3];
		String[] group_columns = new String[args.length - 3];
		for(int i = 3; i < args.length; i++) {
			group_columns[i-3] = args[i];
			if(agg_column.equals(args[i])) {
				showUsage();
				System.err.printf("A column can not be used as both a grouping column and an aggregation column.");
				return;
			}
		}

		if(!agg_function.equals("count") && !agg_function.equals("sum") && !agg_function.equals("avg")) {
			showUsage();
			return;
		}
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(csv_filename));
		}catch( IOException e ) {
			System.err.printf("Error: Unable to open file %s\n", csv_filename);
			return;
		}
		
		String header_line;

		try {
			header_line = br.readLine(); //The readLine method returns either the next line of the file or null (if the end of the file has been reached)
		}catch (IOException e) {
			System.err.printf("Error reading file\n", csv_filename);
			return;
		}
		
		if(header_line == null) {
			System.err.printf("Error: CSV file %s has no header row\n", csv_filename);
			return;
		}
		
		int count=0;
		//Split the header_line string into an array of string values using a comma
		//as the separator.
		String[] column_names = header_line.split(",");
		//found out which group columns to print 
		for(int i = 0; i < column_names.length; i++) {
			for(int j = 0; j < group_columns.length; j++) {
				if(column_names[i].equals(group_columns[j])) {
					group_count[count] = i;
					count++;
				}
			}
		}
		
		boolean agg_found = false;
		for(String s: column_names) {
			if(agg_column.equals(s)) {  //assuming there is only one agg_column 
				agg_found = true;
			}	
		}
		
		if(!agg_found) {
			System.err.printf("Error: The aggregation "+ agg_column+ " column could not be found in the file");
			return;
		}

		for(int i = 0; i < group_columns.length; i++) {
			if(!Arrays.asList(column_names).contains(group_columns[i])) {
				System.err.printf("Error: Grouping column "+ group_columns[i]+ " can not be found in the file");
				return;
			}
		}
		
		input(br, agg_column, group_columns, column_names, group_count, agg_function);
	}
}
