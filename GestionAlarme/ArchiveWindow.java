package GestionAlarme;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ArchiveWindow extends JFrame implements ActionListener, ListSelectionListener{
	

	private DefaultListModel<String> dlmArch = new DefaultListModel<String>();
	
	private JList<String> listeArch = new JList<String>(this.dlmArch);
	public ArrayList<Moniteur> monitorList = new ArrayList<Moniteur>();
	private Font  f1  = new Font(Font.DIALOG, Font.PLAIN,  16);
	private int x ;
	private int y;
	private JTextPane infosDisplayer = new JTextPane();
	
	
	
	
	public ArchiveWindow(String title,int x, int y) throws Exception
	  {
	    super(title);
	    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
	    int longueur = tailleMoniteur.width * 1/4;
	    int hauteur = tailleMoniteur.height * 1/8;
	    //régler la taille de JFrame à 2/3 la taille de l'écran
	    setSize(longueur,hauteur);
	    setResizable(false);
	    setLocation(x,y);
	    setAlwaysOnTop(false);
	    this.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
	    
		    // Barre de menu
		JMenuBar menuBar = new JMenuBar();
		
		
		this.setJMenuBar(menuBar);
		
		// menu
		JMenu menu = new JMenu("Utils");
		menu.setFont(f1);
		menuBar.setFont(f1);
		menuBar.add(menu);
		
		// sous menu déroulant
		JMenuItem quit = new JMenuItem("Quitter");
		
		quit.setFont(f1);
		
		menu.add(quit);
		
		// action des boutons du menu
		 quit.setActionCommand("quit");
	     quit.addActionListener(this);
	     
	    this.initContent();// fonction qui initialise 
	    
	    this.pack();
	    
	  }

	/**
     * Cette fonction génère le contenu de la fenêtre  
     */
	 
	  public void initContent()
	  {
	   // sous panels comme dans les frome du moniteur
	    JPanel left = new JPanel();
	    left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
	    JLabel nb = new JLabel("Archive des alarmes ");
	    nb.setFont(f1);
	    left.add(nb);
	   
	    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
	    int longueur = tailleMoniteur.width * 1/2;
	    int hauteur = tailleMoniteur.height * 1/6;
	    
	    JPanel listPanel = new JPanel();
	    listeArch.setPreferredSize(new Dimension(longueur,hauteur));
	    listeArch.setFont(f1);
	    
	    // Ajout du scroller et de l'affichage de la liste
	    this.listeArch.setModel(dlmArch);
	    this.listeArch.addListSelectionListener(this);
	    JScrollPane scroller = new JScrollPane(listeArch);
	    scroller.setPreferredSize(new Dimension(longueur,hauteur));
	    listPanel.add(scroller);
	    left.add(listPanel);
	   
	    //Ajout au conteneur principal
	    this.getContentPane().add(left);
	    
	  }



	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		if ( e.getActionCommand().equals("quit") )                     // si on sélectionne "Quitter"
	    {
	        JOptionPane pane = new JOptionPane();
	        if ( pane.showConfirmDialog(this,                              // on demande une confirmation en rapport
	                    "Voulez vous vraiment quitter ?",                  // avec la fenêtre qui a déclenché l'action (this)
	                    "Attention",
	                    pane.YES_NO_OPTION,
	                    pane.WARNING_MESSAGE) == JOptionPane.YES_OPTION )  // si on a confirmé
	            System.exit(0);                                            // on quitte le programme
	    }
	}



	public DefaultListModel<String> getDlmArch() {
		return dlmArch;
	}



	public void setDlmArch(DefaultListModel<String> dlmArch) {
		this.dlmArch = dlmArch;
	}



}
