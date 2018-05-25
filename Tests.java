package com.jambalayasystems.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Tests {

	public static void main(String[] args) {
		generateUsersCSV(100);

	}
	
	public static void generateUsersCSV(int count) {
		Random randy = new Random();
		try {
			PrintWriter pw = new PrintWriter(new File("testData.csv"));
			
			for (int i = 1; i < count+1; i++) {
				String entry = "";
				
				String firstName = "";
				String lastName = "";
				String position = "";
				
				firstName += Character.toString((char) (randy.nextInt(26) + 65));//Returns a random upper case letter.
				firstName += Character.toString((char) (randy.nextInt(26) + 97));//Returns a random lower case letter.
				firstName += Character.toString((char) (randy.nextInt(26) + 97));
				firstName += Character.toString((char) (randy.nextInt(26) + 97));
				firstName += Character.toString((char) (randy.nextInt(26) + 97));
				
				lastName += Character.toString((char) (randy.nextInt(26) + 65));//Returns a random upper case letter.
				lastName += Character.toString((char) (randy.nextInt(26) + 97));//Returns a random lower case letter.
				lastName += Character.toString((char) (randy.nextInt(26) + 97));
				
				position += Character.toString((char) (randy.nextInt(26) + 65));//Returns a random upper case letter.
				position += Character.toString((char) (randy.nextInt(26) + 97));//Returns a random lower case letter.
				position += Character.toString((char) (randy.nextInt(26) + 97));
				position += Character.toString((char) (randy.nextInt(26) + 97));
				position += Character.toString((char) (randy.nextInt(26) + 97));
				position += Character.toString((char) (randy.nextInt(26) + 97));
				position += Character.toString((char) (randy.nextInt(26) + 97));
				
				int isManagerInt = randy.nextInt(2);
				
				String isManager = Boolean.toString((isManagerInt == 1) ? true:false);
				
				entry += firstName;
				entry +=",";
				entry +=lastName;
				entry +=",";
				entry +=position;
				entry +=",";
				entry +=isManager;
				entry +="\n";
				
				pw.write(entry);
				
				System.out.println(entry);
			}
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(count);
		
	}

}
