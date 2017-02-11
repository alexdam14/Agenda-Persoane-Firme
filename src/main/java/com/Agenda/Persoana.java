package com.Agenda;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Persoana extends DefaultHandler{

	protected static int idP=new Integer(0);
	protected Integer idPf;
	
	private String nume;
	private String numarTelefon;
	private String email;
	private String oras;

	
	Persoana()
	{
		nume="Ion Popescu";
		numarTelefon="0268.345.234";
		email="ion.popescu@gmail.com";
		oras="Brasov";
		idP++;
		idPf=idP;
	}
	
	Persoana(String nume, String numarTelefon, String email, String oras)
	{
		this.nume=nume;
		this.numarTelefon=numarTelefon;
		this.email=email;
		this.oras=oras;
		idP++;
		idPf=idP;
	}

	
	public int getId()
	{
		return idPf;
	}
	
	
	public boolean equals(Object obj)
	{
		if(obj==null){
			return false;
		}
		
		if(this==obj){
			return true;
		}
		if(obj instanceof Persoana){
			Persoana other = (Persoana) obj;
			return this.getSQLId(this.idPf).equals(other.getId());
		}
		return false;
	}
	
	
	
	public Integer getSQLId(int selId)
	{
		Connection c = null;
	    Statement stmt = null;	   
	    int maxid = 0;
	    int count=0;
	   
	    try { 
	      
	    	Class.forName("org.sqlite.JDBC");
		    c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
		    c.setAutoCommit(false);

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) as ID FROM PERSOANE;" );
	      while ( rs.next() ) {  	    	
	    	 maxid=rs.getInt("ID");	               
	      }
	      
	      ResultSet rs2 = stmt.executeQuery( "SELECT COUNT(ID) as ID FROM PERSOANE;" );
	      while ( rs.next() ) {  	    	
	    	 count=rs.getInt("ID");	               
	      }      
	      
	      rs.close();
	      rs2.close();
	      stmt.close();
	      c.close();     
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return maxid-count+selId;
}	
	
	
	
	
	public String getNume()
	{
		return nume;
	}
	
	public String getNumarTelefon()
	{
		return numarTelefon;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getOras()
	{
		return oras;
	}
	
	public void setNume(String nume)
	{
		this.nume=nume;
	}
	
	public void setNumarTelefon(String numarTelefon)
	{
		this.numarTelefon=numarTelefon;
	}
	
	public void setEmail(String email)
	{
		this.email=email;
	}
	
	public void setOras(String oras)
	{
		this.oras=oras;
	}
	
	public String toString() { 
	    return nume;
	} 
	
	public String toStr() { 
	    return "Nume: " + this.nume + "\n" + "Numar telefon: " + this.numarTelefon + "\n" + "Email: " + this.email + "\n" + "Oras: " + this.oras;
	} 
	
	public String toXml()
	{
		return "	<persoana>" + "\r\n" + "		<nume>" + nume + "</nume>" + "\r\n" + "		<numarTelefon>" + numarTelefon 
				+ "</numarTelefon>" + "\r\n" + "		<email>" + email + "</email>"
				+ "\r\n" + "		<oras>" + oras + "</oras>" + "\r\n" + "	</persoana>";
	}

	
	public static void sqlDeleteAllRecords()
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      String sql = "DELETE from PERSOANE;";
	      stmt.executeUpdate(sql);
	      c.commit();
	      stmt.close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   // System.out.println("Operation done successfully");
	}
	
	public static int sqlNumberOf()
	{
		Connection c = null;
	    Statement stmt = null;
	    int nrPers=0;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT max(ID) AS ID FROM PERSOANE;" );
	      while ( rs.next() ) {  
	    	 
	    	nrPers=rs.getInt("ID");         
	               
	      }   
	      rs.close();
	      stmt.close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return nrPers;
	}
	
	public static void sqlDelete(int ID)
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      String sql = "DELETE from PERSOANE where ID="+ID+";";
	      stmt.executeUpdate(sql);
	      c.commit();
	      stmt = c.createStatement();
	      sql="update persoane set id=id-1 where id>"+ID+";";
	      stmt.executeUpdate(sql);
	      c.commit();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	  //  System.out.println("Operation done successfully");
	  //Damaschin Alexandru-Mihai
	}
	
	
		//  --- INNER CLASS --- 
	
//class PersoanaSQL  {
		
		static ArrayList<Persoana> empList = new ArrayList<>();	
		Persoana pers=null;
		  
		  public List<Persoana> getList()
		  {
			  return empList;
		  }
		  
		  boolean bNume = false;
		  boolean bNumarT = false;
		  boolean bEmail = false;
		  boolean bOras = false;
		
		  @Override		  
		  public void startElement(String uri, String localName, String qName, Attributes attributes)
		            throws SAXException {
		 
		        if (qName.equalsIgnoreCase("persoana")) {
		           // String id = attributes.getValue("id");   
		           //initialize Employee object and set id attribute
		            pers = new Persoana();
		         
		        } else if (qName.equalsIgnoreCase("nume")) {
		            bNume = true;
		        } else if (qName.equalsIgnoreCase("numarTelefon")) {
		            bNumarT = true;
		        } else if (qName.equalsIgnoreCase("email")) {
		            bEmail = true;
		        } else if (qName.equalsIgnoreCase("oras")) {
		            bOras = true;
		        }
		    }
		  
		  @Override
		    public void endElement(String uri, String localName, String qName) throws SAXException {
		        if (qName.equalsIgnoreCase("persoana")) {
		            empList.add(pers);
		            pers = null;
		        }
		    }
		  
		  @Override
		    public void characters(char ch[], int start, int length) throws SAXException {
		 
			  if (pers != null) {
		        if (bNume) {
		            //set Persoana nume
		            pers.setNume(new String(ch, start, length));
		            bNume = false;
		        } else if (bNumarT) {
		        	pers.setNumarTelefon(new String(ch, start, length));
		            bNumarT = false;
		        } else if (bEmail) {
		            pers.setEmail(new String(ch, start, length));
		            bEmail = false;
		        } else if (bOras) {
		            pers.setOras(new String(ch, start, length));
		            bOras = false;
		        }		        
			  }
		    }
		  
		  public String toSql(){
			  return "INSERT INTO PERSOANE (NUME,NUMAR_TELEFON,EMAIL,ORAS) " +
	                   "VALUES ('" + getNume() +
               "','"+getNumarTelefon()+"','"+getEmail()+
               "','"+getOras()+"');";
		  }
		  
		  public static void updateSQL(int id, String nume, String telefon, String email, String oras)
		  {
			  Connection c = null;
	 		    Statement stmt = null;
	 		    try {
	 		      Class.forName("org.sqlite.JDBC");
	 		      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
	 		      c.setAutoCommit(false);
	 		      stmt = c.createStatement();
		    	  stmt.executeUpdate("UPDATE SQLITE_SEQUENCE SET seq = (select max(id) as id from persoane) WHERE name = 'PERSOANE'");
		    	  stmt = c.createStatement();
		    	  stmt.executeUpdate("update persoane set nume='" + nume + "',numar_telefon='" + telefon + "',email='"+ email +"',oras='"+ oras +"' where id="+ id +";");
	 		    
	 		      stmt.close();
	 		      c.commit();
	 		      c.close();
	 		    } catch ( Exception e ) {
	 		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	 		      System.exit(0);
	 		    }	 		  
		  }
		  
		  public static void insertSQL(List<Persoana> empList)
		  {
			  
	        	Connection c = null;
	 		    Statement stmt = null;
	 		    try {
	 		      Class.forName("org.sqlite.JDBC");
	 		      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
	 		      c.setAutoCommit(false);
	 		      stmt = c.createStatement();
		    	  stmt.executeUpdate("UPDATE SQLITE_SEQUENCE SET seq = (select max(id) as id from persoane) WHERE name = 'PERSOANE'");
		    	  stmt = c.createStatement();
		    	  stmt.executeUpdate("UPDATE SQLITE_SEQUENCE SET seq = (select max(id) as id from firme) WHERE name = 'FIRME'");
	 		     for(Persoana emp : empList)
		            {
	 		    	 	stmt = c.createStatement();
	 		    	    stmt.executeUpdate(emp.toSql());
		            }

	 		      stmt.close();
	 		      c.commit();
	 		      c.close();
	 		    } catch ( Exception e ) {
	 		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	 		      System.exit(0);
	 		    }	 		    	           
	           			  
			//  System.out.println("Records created successfully");
		  } 		  
		  
		 

public static void xmlToSQL(Persoana x)
{
	  SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	    	
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        saxParser.parse(new File("agenda.xml"), x);
	        //Get Persoana list
	        empList = (ArrayList<Persoana>) x.getList();	        
	        Persoana.insertSQL(empList); // insert data in database
	        
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }	    
}

public static void createTable()
{
	
	Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
    //  System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sqlPers = "CREATE TABLE IF NOT EXISTS PERSOANE" +
                   "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                   " NUME           CHAR(50)    NOT NULL, " + 
                   " NUMAR_TELEFON  CHAR(15)     NOT NULL, " + 
                   " EMAIL          CHAR(50), " + 
                   " ORAS           CHAR(20)	NOT NULL)"; 
      
           
      stmt.executeUpdate(sqlPers);
     
      
      stmt.close();
      c.close();
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
   // System.out.println("Table created successfully");	
}

public int getIdFromDb()
{
	Connection c = null;
    Statement stmt = null;
   
    try { 
      
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT max(ID) as ID FROM PERSOANE;" );
      while ( rs.next() ) {      	 
    	 int id=rs.getInt("ID");
    	 idP=id;               
      }
      
      rs.close();
      stmt.close();
      c.close();    
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
    return idP;
   
}



}

