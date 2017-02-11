package com.Agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;



public class Firma extends Persoana{
	
	protected static int idF=new Integer(0);
	protected Integer idFf;
	private int numarAngajati;
	private String numarFax;

	
Firma()
{
	this.setNume("SC Impex SRL");
	this.setNumarTelefon("0268.112.231");
	this.setEmail("impex@gmail.com");
	this.setOras("Brasov");
	numarAngajati=15;
	numarFax="0268.456.456";
	
	idF++;
	idFf=idF;
}
	
Firma(String nume, String numarTelefon, String email, String oras, int numarAngajati, String numarFax )
{

	this.setNume(nume);
	this.setNumarTelefon(numarTelefon);
	this.setEmail(email);
	this.setOras(oras);
	this.numarAngajati=numarAngajati;
	this.numarFax=numarFax;
	idF++;
	idFf=idF;
}

public int getId()
{
	return idFf;
}

public Integer getSQLId(int id)
{
	Connection c = null;
    Statement stmt = null;	   
    int maxid = 0;
    int count=0;
    int countP=0;
   
    try { 
      
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) as ID FROM FIRME;" );
      while ( rs.next() ) {  	    	
    	 maxid=rs.getInt("ID");	               
      }
      
      ResultSet rs2 = stmt.executeQuery( "SELECT COUNT(ID) as ID FROM FIRME;" );
      while ( rs.next() ) {  	    	
    	 count=rs.getInt("ID");	               
      }      
      
      ResultSet rs3 = stmt.executeQuery( "SELECT COUNT(ID) as ID FROM PERSOANE;" );
      while ( rs.next() ) {  	    	
    	 countP=rs.getInt("ID");	               
      }      
      
      rs.close();
      rs2.close();
      rs3.close();
      stmt.close();
      c.close();     
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    return maxid-count-countP+id;
}	


public static void updateSQL(int id, String nume, String telefon, String email, String oras, int nrAng, String nrFax)
{
	  Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:agenda.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
  	  stmt.executeUpdate("UPDATE SQLITE_SEQUENCE SET seq = (select max(id) as id from persoane) WHERE name = 'FIRME'");
  	  stmt = c.createStatement();
  	  stmt.executeUpdate("update firme set nume='" + nume + "',numar_telefon='" + telefon + "',email='"+ email +"',oras='"+ oras + "',numar_angajati=" + nrAng + ",numar_fax='" + nrFax +"' where id="+ id +";");
	    
	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }	 		  
}


public int getNumarAngajati()
{
	return numarAngajati;
}

public String getNumarFax()
{
	return numarFax;
}

public void setNumarAngajati(int numarAngajati)
{
	this.numarAngajati=numarAngajati;
}

public void setNumarFax(String numarFax)
{
	this.numarFax=numarFax;
}


public String toStr()
{
	return super.toString() + "\n" + "Numar angajati: " + this.numarAngajati + "\n" + "Numar fax: " + this.numarFax;		
}

public String toXml()
{
	return "	<firma>" + "\r\n" + "		<nume>" + super.getNume() + "</nume>" + "\r\n" + "		<numarTelefon>" + super.getNumarTelefon() 
			+ "</numarTelefon>" + "\r\n" + "		<email>" + super.getEmail() + "</email>"
			+ "\r\n" + "		<oras>" + super.getOras() + "</oras>" + "\r\n" + "		<numarAngajati>" + numarAngajati + "</numarAngajati>"
			+ "\r\n" + "		<numarFax>" + numarFax + "</numarFax>" + "\r\n" + "	</firma>";
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
      String sql = "DELETE from FIRME;";
      stmt.executeUpdate(sql);
      c.commit();

      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   // System.out.println("Operation done successfully");
   //Damaschin Alexandru-Mihai
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

      
      String sqlFirm = "CREATE TABLE IF NOT EXISTS FIRME" +
              "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
              " NUME           CHAR(50)    NOT NULL, " + 
              " NUMAR_TELEFON  CHAR(15)     NOT NULL, " + 
              " EMAIL          CHAR(50), " + 
              " ORAS           CHAR(20)	NOT NULL, "  + 
              " NUMAR_ANGAJATI    INT(5)	NOT NULL, " + 
              " NUMAR_FAX          CHAR(15))" ; 
      
      
     
      stmt.executeUpdate(sqlFirm);
      
      stmt.close();
      c.close();
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
  //  System.out.println("Table created successfully");

	
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
      ResultSet rs = stmt.executeQuery( "SELECT ID FROM FIRME;" );
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
      ResultSet rs = stmt.executeQuery( "SELECT max(ID) AS ID FROM FIRME;" );
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
      String sql = "DELETE from FIRME where ID="+ID+";";
      stmt.executeUpdate(sql);
      c.commit();
      stmt = c.createStatement();
      sql="update firme set id=id-1 where id>"+ID+";";
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


//class FirmaSQL extends DefaultHandler{
	Firma firm=null;
	 
	  
	  boolean bNume = false;
	  boolean bNumarT = false;
	  boolean bEmail = false;
	  boolean bOras = false;
	  boolean bNrAngajati = false;
	  boolean bNrFax = false;
	  
	  @Override		  
	  public void startElement(String uri, String localName, String qName, Attributes attributes)
	            throws SAXException {
	 
	        if (qName.equalsIgnoreCase("firma")) {
	           // String id = attributes.getValue("id");   
	           //initialize Employee object and set id attribute
	           	firm = new Firma();
	         
	        } else if (qName.equalsIgnoreCase("nume")) {
	            bNume = true;
	        } else if (qName.equalsIgnoreCase("numarTelefon")) {
	            bNumarT = true;
	        } else if (qName.equalsIgnoreCase("email")) {
	            bEmail = true;
	        } else if (qName.equalsIgnoreCase("oras")) {
	            bOras = true;
	        }
	        else if (qName.equalsIgnoreCase("numarAngajati")) {
	            bNrAngajati = true;
	        }
	        else if (qName.equalsIgnoreCase("numarFax")) {
	            bNrFax = true;
	        }
	    }
	  
	  @Override
	    public void endElement(String uri, String localName, String qName) throws SAXException {
	        if (qName.equalsIgnoreCase("firma")) {
	            empList.add(firm);
	            firm = null;
	        }
	    }
	  
	  @Override
	    public void characters(char ch[], int start, int length) throws SAXException {
	 
		  if (firm != null) {
	        if (bNume) {
	            //set Persoana nume
	            firm.setNume(new String(ch, start, length));
	            bNume = false;
	        } else if (bNumarT) {
	            firm.setNumarTelefon(new String(ch, start, length));
	            bNumarT = false;
	        } else if (bEmail) {
	            firm.setEmail(new String(ch, start, length));
	            bEmail = false;
	        } else if (bOras) {
	            firm.setOras(new String(ch, start, length));
	            bOras = false;
	        }	
	        else if (bNrAngajati) {
	            firm.setNumarAngajati(Integer.parseInt(new String(ch, start, length)));
	            bNrAngajati = false;
	        }
	        else if (bNrFax) {
	            firm.setNumarFax(new String(ch, start, length));
	            bNrFax = false;
	        }
		  }
	    }
	  
	  public String toSql(){
		  return "INSERT INTO FIRME (NUME,NUMAR_TELEFON,EMAIL,ORAS,NUMAR_ANGAJATI,NUMAR_FAX) " +
                   "VALUES ('" + getNume() +
           "','"+getNumarTelefon()+"','"+getEmail()+
           "','"+getOras()+ "','"+getNumarAngajati()+ "','"+
           getNumarFax()+"');";
	  }




}


