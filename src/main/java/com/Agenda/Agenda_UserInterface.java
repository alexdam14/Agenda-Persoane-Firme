package com.Agenda;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
public class Agenda_UserInterface extends JFrame {

	static DefaultListModel<Persoana> elementeList = new DefaultListModel<Persoana>();
	static DefaultListModel<Persoana> elementeListTemp = new DefaultListModel<Persoana>();
	
	private JPanel contentPane;
	private JTextField textID;
	private JTextField textNume;
	private JTextField textTelefon;
	private JTextField textEmail;
	private JTextField textOras;
	private JTextField textNrAngajati;
	private JTextField textFax;
	
	Persoana a =new Persoana();
	Firma b=new Firma();
	private int indexBeforeDelete;
	int updateid=0;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		Persoana.createTable();
		try{
		Firma.createTable();
		Agenda.sqlSELECT();}
		catch(Exception e){			
		}
	
		for(Persoana line : Agenda.entitati)
		{
			elementeList.addElement(line);			
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Agenda_UserInterface frame = new Agenda_UserInterface();
					frame.setVisible(true);					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	public Agenda_UserInterface() {
		setIconImage(
			    new ImageIcon(getClass().getResource("/icon.png")).getImage()
			);
		
		//frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		
		setTitle("Agenda");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 675, 370);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);		
		
		
		
		JMenu mnSql = new JMenu("SQL");
		menuBar.add(mnSql);
		
		JMenuItem btnDeletepersoaneRecords = new JMenuItem("Delete \"Persoane\" Records");
		btnDeletepersoaneRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Persoana.sqlDeleteAllRecords();
				Agenda.sqlSELECT();
				elementeList.clear();
				for(Persoana line : Agenda.entitati)
				{
					elementeList.addElement(line);			
				}
				
			}
		});
		mnSql.add(btnDeletepersoaneRecords);
		
		JMenuItem btnDeletefirmeRecords = new JMenuItem("Delete \"Firme\" Records");
		btnDeletefirmeRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Firma.sqlDeleteAllRecords();
				Agenda.sqlSELECT();
				elementeList.clear();
				for(Persoana line : Agenda.entitati)
				{
					elementeList.addElement(line);			
				}
				
			}
		});
		mnSql.add(btnDeletefirmeRecords);
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		
		// --------  Jlist Pannel --------
		final JList<Persoana> entitatiJlist;
		
		entitatiJlist=new JList<Persoana>(elementeList);	
		entitatiJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		contentPane.add(new JScrollPane(entitatiJlist), gbc); 
		
		entitatiJlist.addListSelectionListener(new ListSelectionListener()
		{
		  public void valueChanged(ListSelectionEvent ev)
		  {
			// int s = entitatiJlist.getSelectedIndex();
			// textEmail.setCaretPosition(0);
			 if(entitatiJlist.getSelectedIndex()== -1){	
				 int x=elementeList.getSize();
					if(x>indexBeforeDelete){
					 entitatiJlist.setSelectedIndex(indexBeforeDelete);}
					
					else{
						entitatiJlist.setSelectedIndex(indexBeforeDelete-1);
						
					}
				 		
			 }
			 else 
			 {
			//System.out.println(elementeList.get(entitatiJlist.getSelectedIndex()).getId());
			 textID.setText(String.valueOf(elementeList.get(entitatiJlist.getSelectedIndex()).getSQLId(entitatiJlist.getSelectedIndex()+1))); 			 
			 textNume.setText(elementeList.get(entitatiJlist.getSelectedIndex()).getNume());
			 textTelefon.setText(elementeList.get(entitatiJlist.getSelectedIndex()).getNumarTelefon());
			 textEmail.setText(elementeList.get(entitatiJlist.getSelectedIndex()).getEmail());
			 textOras.setText(elementeList.get(entitatiJlist.getSelectedIndex()).getOras());
			 try{
				
				 textNrAngajati.setText(String.valueOf(((Firma) elementeList.get(entitatiJlist.getSelectedIndex())).getNumarAngajati()));
				 }			 
				 catch(Exception e)
				 {
					
					 textNrAngajati.setText("");					 
				 }
			 
			 try{
				
			 textFax.setText(((Firma)elementeList.get(entitatiJlist.getSelectedIndex())).getNumarFax());
			 }			 
			 catch(Exception e)
			 {
				 textFax.setText("");
				
			 }
			
			 
		  }
			 if(elementeList.getSize() == 0)
			 {
				 textID.setText("");
				 textNume.setText("");
				 textTelefon.setText("");
				 textEmail.setText("");
				 textOras.setText(""); 
				 textNrAngajati.setText(""); 
				 textFax.setText("");
				 
			 }
		  }
		});
	
		
		JMenu mnParse = new JMenu("Parse");
		menuBar.add(mnParse);
		final Persoana handler=new Persoana();
		final Firma handlerF=new Firma();
		JMenuItem btnpersoaneXmlTo = new JMenuItem("\"Persoane\" from XML to SQL");
		btnpersoaneXmlTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Persoana.createTable();
				
				Persoana.xmlToSQL(handler);
				
				
				Agenda.sqlSELECT();
				
				elementeList.clear();
				for(Persoana line : Agenda.entitati)
				{
					elementeList.addElement(line);			
				}			
				
				Persoana.empList.clear();
				
			}
		});
		mnParse.add(btnpersoaneXmlTo);
		
		JMenuItem btnfirmeXmlTo = new JMenuItem("\"Firme\" from XML to SQL");
		btnfirmeXmlTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Firma.createTable();				
				Firma.xmlToSQL(handlerF);
				
				Agenda.sqlSELECT();
				elementeList.clear();
				for(Persoana line : Agenda.entitati)
				{
					elementeList.addElement(line);			
				}
				
				
				Persoana.empList.clear();
				
			}
		});
		mnParse.add(btnfirmeXmlTo);
		
		JMenuItem mntmAgendaFromSql = new JMenuItem("Agenda from SQL to XML");
		mntmAgendaFromSql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agenda.sqlSELECT();
				Agenda.exportToXml(Agenda.entitati);   // export Array Lists to XML
				//entitatiJlist.setModel(elementeList);
				//Damaschin Alexandru-Mihai
			}
		});
		
		
		mnParse.add(mntmAgendaFromSql);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.weighty = 1.0;
		gbc_panel.weightx = 1.0;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblId = new JLabel("ID");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.WEST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		panel.add(lblId, gbc_lblId);
		
		//textField = new JTextField(Agenda.entitati.get(3).getNume());
		textID = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		//gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(textID, gbc_textField);
		textID.setColumns(10);
		textID.setEditable(false);
		JLabel lblNume = new JLabel("Nume");
		GridBagConstraints gbc_lblNume = new GridBagConstraints();
		gbc_lblNume.anchor = GridBagConstraints.WEST;
		gbc_lblNume.insets = new Insets(0, 0, 5, 5);
		gbc_lblNume.gridx = 0;
		gbc_lblNume.gridy = 1;
		panel.add(lblNume, gbc_lblNume);
		
		textNume = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
	
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel.add(textNume, gbc_textField_1);
		textNume.setColumns(10);
		
		JLabel lblTelefon = new JLabel("Telefon");
		GridBagConstraints gbc_lblTelefon = new GridBagConstraints();
		gbc_lblTelefon.anchor = GridBagConstraints.WEST;
		gbc_lblTelefon.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefon.gridx = 0;
		gbc_lblTelefon.gridy = 2;
		panel.add(lblTelefon, gbc_lblTelefon);
		
		textTelefon = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		panel.add(textTelefon, gbc_textField_2);
		textTelefon.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 3;
		panel.add(lblEmail, gbc_lblEmail);
		
		textEmail = new JTextField();
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
	
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 3;
		panel.add(textEmail, gbc_textField_3);
		textEmail.setColumns(10);
		
		JLabel lblOras = new JLabel("Oras");
		GridBagConstraints gbc_lblOras = new GridBagConstraints();
		gbc_lblOras.anchor = GridBagConstraints.WEST;
		gbc_lblOras.insets = new Insets(0, 0, 5, 5);
		gbc_lblOras.gridx = 0;
		gbc_lblOras.gridy = 4;
		panel.add(lblOras, gbc_lblOras);
		
		textOras = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();

		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 4;
		panel.add(textOras, gbc_textField_4);
		textOras.setColumns(10);
		
		JLabel lblNrangajati = new JLabel("Nr.Angajati");
		GridBagConstraints gbc_lblNrangajati = new GridBagConstraints();
		gbc_lblNrangajati.anchor = GridBagConstraints.WEST;
		gbc_lblNrangajati.insets = new Insets(0, 0, 5, 5);
		gbc_lblNrangajati.gridx = 0;
		gbc_lblNrangajati.gridy = 5;
		panel.add(lblNrangajati, gbc_lblNrangajati);
		
		textNrAngajati = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();

		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 5;
		panel.add(textNrAngajati, gbc_textField_5);
		textNrAngajati.setColumns(10);
		
		JLabel lblFax = new JLabel("Fax");
		GridBagConstraints gbc_lblFax = new GridBagConstraints();
		gbc_lblFax.anchor = GridBagConstraints.WEST;
		gbc_lblFax.insets = new Insets(0, 0, 0, 5);
		gbc_lblFax.gridx = 0;
		gbc_lblFax.gridy = 6;
		panel.add(lblFax, gbc_lblFax);
		
		textFax = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
	
		gbc_textField_6.insets = new Insets(0, 0, 0, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 6;
		panel.add(textFax, gbc_textField_6);
		textFax.setColumns(10);
		
		ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("delete.png"));	
		java.awt.Image image = icon.getImage();
		image = image.getScaledInstance(image.getWidth(null)/3, image.getHeight(null)/3, java.awt.Image.SCALE_SMOOTH);
	    icon.setImage(image);
	    
		JButton btnDelete = new JButton(icon);
		
		
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.weightx = 1.0;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 1;
		contentPane.add(btnDelete, gbc_btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			  
            public void actionPerformed(ActionEvent e)
            {    
            	
            	try{
            	if(Agenda.entitati.get(entitatiJlist.getSelectedIndex()).getClass() == a.getClass())
            			{
            				Persoana.sqlDelete((elementeList.get(entitatiJlist.getSelectedIndex()).getSQLId(entitatiJlist.getSelectedIndex()+1)));
            				
            			}
            	else if(Agenda.entitati.get(entitatiJlist.getSelectedIndex()).getClass() == b.getClass())
            			{
            				Firma.sqlDelete((elementeList.get(entitatiJlist.getSelectedIndex()).getSQLId(entitatiJlist.getSelectedIndex()+1)));
            				
            			}}
            	catch(Exception noEl){}
            	
            	Agenda.sqlSELECT();
				elementeList.clear();
				for(Persoana line : Agenda.entitati)
				{
					elementeList.addElement(line);			
				}
				
            	
            }
        });
		ImageIcon iconU=new ImageIcon(getClass().getClassLoader().getResource("update.png"));	
		java.awt.Image imageU = iconU.getImage();
		imageU = imageU.getScaledInstance(imageU.getWidth(null)/3, imageU.getHeight(null)/3, java.awt.Image.SCALE_SMOOTH);
	    iconU.setImage(imageU);
	    
		JButton btnUpdate = new JButton(iconU);
		
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.weightx = 1.0;
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 2;
		gbc_btnUpdate.gridy = 1;
		contentPane.add(btnUpdate, gbc_btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			  
	            public void actionPerformed(ActionEvent e)
	            {
	            	try{
	                	if(Agenda.entitati.get(entitatiJlist.getSelectedIndex()).getClass() == a.getClass())
	                			{
	                				updateid=elementeList.get(entitatiJlist.getSelectedIndex()).getSQLId(entitatiJlist.getSelectedIndex()+1);
	                				String selNume=new String();
	                				String selTelefon = new String();
	                				String selEmail= new String();
	                				String selOras= new String();
	                				
	                				try{if(textNume.getText().equals("")) {selNume="no_name";} else {selNume=textNume.getText();}}catch(Exception noNume){selNume="";}
	                				selTelefon=textTelefon.getText();
	                				selEmail=textEmail.getText();
	                				selOras=textOras.getText();
	                				
	                				Persoana.updateSQL(updateid,selNume,selTelefon,selEmail,selOras);
	                			}
	                	else if(Agenda.entitati.get(entitatiJlist.getSelectedIndex()).getClass() == b.getClass())
	                			{
	                		updateid=elementeList.get(entitatiJlist.getSelectedIndex()).getSQLId(entitatiJlist.getSelectedIndex()+1);
            				String selNume=new String();
            				String selTelefon = new String();
            				String selEmail= new String();
            				String selOras= new String();
            				int selAng=0;
            				String selFax= new String();
            				
            				try{if(textNume.getText().equals("")) {selNume="no_name";} else {selNume=textNume.getText();}}catch(Exception noNume){selNume="";}
            				selTelefon=textTelefon.getText();
            				selEmail=textEmail.getText();
            				selOras=textOras.getText();
            				selAng=Integer.parseInt(textNrAngajati.getText());
            				selFax=textFax.getText();       
            				
            				
            				Firma.updateSQL(updateid,selNume,selTelefon,selEmail,selOras,selAng, selFax);
            				
            				
	                			}}
	                	catch(Exception noEl){}
	                	
	                	Agenda.sqlSELECT();
	    				elementeList.clear();
	    				for(Persoana line : Agenda.entitati)
	    				{
	    					elementeList.addElement(line);			
	    				}
	            	
	            }
	        });
		
		
		ImageIcon iconD=new ImageIcon(getClass().getClassLoader().getResource("insert.png"));	
		java.awt.Image imageD = iconD.getImage();
		imageD = imageD.getScaledInstance(imageD.getWidth(null)/3, imageD.getHeight(null)/3, java.awt.Image.SCALE_SMOOTH);
	    iconD.setImage(imageD);
		JButton btnInsert = new JButton(iconD);
		GridBagConstraints gbc_btnInsert = new GridBagConstraints();
		gbc_btnInsert.weightx = 1.0;
		gbc_btnInsert.gridx = 3;
		gbc_btnInsert.gridy = 1;
		
		btnInsert.addActionListener(new ActionListener() {
			  
            public void actionPerformed(ActionEvent e)
            {   
            	
            	String selNume=new String();
				String selTelefon = new String();
				String selEmail= new String();
				String selOras= new String();
				int selAng=0;
				String selFax= new String();
				try{if(textNume.getText().equals("")) {selNume="no_name";} else {selNume=textNume.getText();}}catch(Exception noNume){selNume="";}
				try{selTelefon=textTelefon.getText();}catch(Exception noTelefon){selTelefon="";}
				selEmail=textEmail.getText();
				selOras=textOras.getText();
				try{selAng=Integer.parseInt(textNrAngajati.getText());}catch(Exception noAng){selAng=0;}
				try{selFax=textFax.getText();}catch(Exception noFax){selFax="";}
				
				
            	Persoana.createTable();
            	Firma.createTable();
            	
            	if(!textNrAngajati.getText().equals("") || !textFax.getText().equals("")){
            		//System.out.println(textNrAngajati.getText());
            		//System.out.println(textFax.getText());
            		Firma insertedF=new Firma(selNume,selTelefon,selEmail,selOras,selAng,selFax);
            		Firma.empList.clear();            		
            		Firma.empList.add(insertedF);
            		Firma.insertSQL(Firma.empList);
            		Firma.empList.clear();   
            	}else if(textNrAngajati.getText().equals("") && textFax.getText().equals(""))
            	{
            		Persoana insertedP = new Persoana(selNume, selTelefon, selEmail, selOras);
            		Persoana.empList.clear();            		
            		Persoana.empList.add(insertedP);
            		Persoana.insertSQL(Firma.empList);
            		Persoana.empList.clear();  
            	}
            	
            	           	
            	elementeList.clear();
            	try{
            		Firma.createTable();
            		Agenda.sqlSELECT();}
            		catch(Exception eIns){			
            		}
            	for(Persoana line : Agenda.entitati)
        		{
        			elementeList.addElement(line);			
        		}
            	//entitatiJlist.setSelectedIndex(elementeList.getSize()-1);
            	Persoana.empList.clear();
            	Firma.empList.clear();
            }
        });
		
		contentPane.add(btnInsert, gbc_btnInsert);
		
	}
}
