package com.Agenda;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;


public class Agenda {
	

	static ArrayList<Persoana> entitati=new ArrayList<Persoana>();
	final static Logger logger = Logger.getLogger(Agenda.class);
	

	public static void inputConsole(){			
		
		// ----- Introducere persoane tastatura -----
		
		entitati.clear();
		System.out.println("Numar Persoane: ");
		String n;
		int nr;
		Scanner inputReader = new Scanner(System.in);
		n=inputReader.nextLine();
		nr=Integer.parseInt(n);		
		
		for (int i=0;i<nr;i++)	
	{
		String nume = null;
		String numarTelefon = null;
		String email = null;
		String oras = null;
		System.out.println("\n" + "Nume: ");
		nume = inputReader.nextLine();
		System.out.println("\n" + "Numar telefon: ");
		numarTelefon = inputReader.nextLine();
		System.out.println("\n" + "Email: ");
		email = inputReader.nextLine();
		System.out.println("\n" + "Oras: ");
		oras = inputReader.nextLine();
		entitati.add(new Persoana(nume,numarTelefon,email,oras));
	}	
		System.out.println("Numar Firme: ");
		
		n=inputReader.nextLine();
		nr=Integer.parseInt(n);		
		
		for (int i=0;i<nr;i++)	
	{
		String nume = null;
		String numarTelefon = null;
		String email = null;
		String oras = null;
		String str_numarAngajati = null;
		int numarAngajati;
		String numarFax = null;
		
		System.out.println("\n" + "Nume: ");
		nume = inputReader.nextLine();
		System.out.println("\n" + "Numar telefon: ");
		numarTelefon = inputReader.nextLine();
		System.out.println("\n" + "Email: ");
		email = inputReader.nextLine();
		System.out.println("\n" + "Oras: ");
		oras = inputReader.nextLine();
		System.out.println("\n" + "numarAngajati: ");
		str_numarAngajati = inputReader.nextLine();
		numarAngajati=Integer.parseInt(str_numarAngajati);
		System.out.println("\n" + "numarFax: ");
		numarFax = inputReader.nextLine();
		entitati.add(new Firma(nume,numarTelefon,email,oras,numarAngajati,numarFax));
	}
		
		inputReader.close();
		//Damaschin Alexandru-Mihai
}
	
	public static void printConsole()
	{
		
		for (Persoana line : entitati) {
			  System.out.println(line + "\n");
			}
	
	}
	
	public static void exportToXml(ArrayList<Persoana> entitati)
	{
	try {
		PrintWriter writer = new PrintWriter("agenda.xml", "UTF-8");
		writer.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' +"UTF-8" + '"' + "?>" + "\r\n");
		
		writer.println("<agenda>");
		for (Persoana line : entitati){
			writer.println(line.toXml());
		}
		
		writer.println("</agenda>");
		writer.close();
		
	} catch (FileNotFoundException | UnsupportedEncodingException e) {		
		e.printStackTrace();
	}
	}
	
	public static void exportToXml()
	{
	try {
		PrintWriter writer = new PrintWriter("agenda.xml", "UTF-8");
		writer.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' +"UTF-8" + '"' + "?>" + "\r\n");
		
		writer.println("<agenda>");
		for (Persoana line : entitati){
			writer.println(line.toXml());
		}
		
		
		writer.println("</agenda>");
		writer.close();
		
	} catch (FileNotFoundException | UnsupportedEncodingException e) {		
		e.printStackTrace();
	}
	}


	
	
	public static void sqlSELECT()
	{
		
		Connection c = null;
	    Statement stmt = null;
	    
	    if (!entitati.isEmpty())
	    {
	    	entitati.clear();
	    }
	    
	    try { 
	      
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
	      c.setAutoCommit(false);
	      //System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM PERSOANE;" );
	      while ( rs.next() ) {  
	    	 Persoana pers=new Persoana(); 
	    	 //long id=rs.getLong("ID");
	         String  nume = rs.getString("NUME");
	         String  numarTelefon = rs.getString("NUMAR_TELEFON");
	         String  email = rs.getString("EMAIL");
	         String  oras = rs.getString("ORAS");              	         
	         
	         pers.setNume(nume);
	         pers.setNumarTelefon(numarTelefon);
	         pers.setEmail(email);
	         pers.setOras(oras);
	         entitati.add(pers); 
	               
	      }   
	      
	      ResultSet rs1 = stmt.executeQuery( "SELECT * FROM FIRME;" );
	      while ( rs.next() ) {
	    	  
	    	 Firma firm = new Firma(); 
	        // long id=rs.getLong("ID");
	         String  nume = rs.getString("NUME");
	         String  numarTelefon = rs.getString("NUMAR_TELEFON");
	         String  email = rs.getString("EMAIL");
	         String  oras = rs.getString("ORAS");              	         
	         int numarAngajati = rs.getInt("NUMAR_ANGAJATI");
	         String numarFax = rs.getString("NUMAR_FAX"); 
	         
	         firm.setNume(nume);
	         firm.setNumarTelefon(numarTelefon);
	         firm.setEmail(email);
	         firm.setOras(oras);
	         firm.setNumarAngajati(numarAngajati);
	         firm.setNumarFax(numarFax);
	         
	        entitati.add(firm);                
	      }	      
	      
	      rs.close();
	      rs1.close();
	      stmt.close();
	      c.close();
	      
	     
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   // System.out.println("Reading from database done successfully");
}	
	
	public static void main(String[] args) {
		
	}
}
