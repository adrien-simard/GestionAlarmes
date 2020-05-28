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
import javax.swing.JButton;
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

public class MonitorWindow extends JFrame
                           implements ActionListener, ListSelectionListener {

  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

 
  

  private DefaultListModel<String> dlm = new DefaultListModel<String>();
  
  private ArrayList<String> listeInfo = new ArrayList<String>();
  
  private JList<String> liste = new JList<String>(this.dlm);

  private JButton archive = new JButton("Traitée");

  private JTextPane infosDisplayer = new JTextPane();

  public ArrayList<Moniteur> monitorList = new ArrayList<Moniteur>();
  
  private Font  f1  = new Font(Font.DIALOG, Font.PLAIN,  16);
  private Font  f2  = new Font(Font.DIALOG, Font.PLAIN,  10);
  
 
  public ArchiveWindow fenarch;
  private int nbIncendie = 0;
  private int nbGaz = 0;
  private int nbRad = 0;
  JLabel statsRad = new JLabel(" ");
  JLabel statsInc = new JLabel(" ");
  JLabel statsGaz = new JLabel(" ");
  

  

  public MonitorWindow(String title,int x, int y) throws Exception
  {
    super(title);
    
  //récuperer la dimension de l'écran
    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
    int longueur = tailleMoniteur.width * 1/4;
    int hauteur = tailleMoniteur.height * 1/8;
    //régler la taille de JFrame à 2/3 la taille de l'écran
    setSize(longueur,hauteur);
    
    setResizable(false);
    setLocation(x,y);
    setAlwaysOnTop(false);
    this.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    
	  // Barre du menu
	JMenuBar menuBar = new JMenuBar();
	
	
	this.setJMenuBar(menuBar);
	
	// Menu 
	JMenu menu = new JMenu("Utils");
	menu.setFont(f1);
	menuBar.setFont(f1);
	menuBar.add(menu);
	
	// sous menu
	JMenuItem quit = new JMenuItem("Quitter");
	JMenuItem archive = new JMenuItem("Archive");
	quit.setFont(f1);
	archive.setFont(f1);
	menu.add(quit);
	menu.add(archive);
	
	 quit.setActionCommand("quit");
     quit.addActionListener(this);
     
     archive.setActionCommand("archive");
     archive.addActionListener(this);
	    

    this.initContent();
    


    this.pack();
    setVisible(true);
  }


  /**
   * Cette fonction génère le contenu de la fenêtre  
   */
  public void initContent()
  {
	 
	  Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
	    int longueur = tailleMoniteur.width * 1/2;
	    int hauteur = tailleMoniteur.height * 1/5;
    // Sous panel
    JPanel left = new JPanel();
    left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
    JPanel right = new JPanel();
    this.statsGaz.setFont(f2);
    this.statsInc.setFont(f2);
    this.statsRad.setFont(f2);
    left.add(statsRad);
    left.add(statsInc);
    left.add(statsGaz);
    

    
    
    JPanel listPanel = new JPanel();
    liste.setPreferredSize(new Dimension(longueur/2,hauteur/2));
    liste.setFont(f1);
    // Evenements
    this.liste.addListSelectionListener(this);
    this.liste.setModel(dlm);
    JScrollPane scroller = new JScrollPane(liste);
    scroller.setPreferredSize(new Dimension(longueur/2,hauteur/2));
    listPanel.add(scroller);
    
   
    
    
    
    //Bouton Traitée qui archive l'alarme aves son action
    JPanel buttonPanel = new JPanel(new FlowLayout());
    archive.setPreferredSize(new Dimension(longueur/5,hauteur/5));
    archive.setFont(f1);
    buttonPanel.add(archive);
    this.archive.setEnabled(false);
    //ACTION TRAITEE
    this.archive.setActionCommand("Traitée");
    this.archive.addActionListener(this);


    //Panneau d'affichage
    JPanel displayPanel = new JPanel();
    this.infosDisplayer.setPreferredSize(new Dimension(longueur/2,hauteur/2));
    this.infosDisplayer.setFont(f1);
    this.infosDisplayer.setContentType("text/html");
    displayPanel.add(this.infosDisplayer);

    // Left Panel
    left.add(listPanel);
    left.add(buttonPanel);
    
    // Panneau droit
    right.add(displayPanel);

    // Conteneur principale 
    this.getContentPane().add(left);
    this.getContentPane().add(right);
  }


@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	if(e.getValueIsAdjusting() == false) 
    {
      //AUCUN INDEX SELECTIONNNE
      if(liste.getSelectedIndex() == -1 || this.dlm.size() == 0) 
      {
        this.infosDisplayer.setText("");   // nettoyage du panneau d'affichage
        this.archive.setEnabled(false);    // desactivation des boutons
      }
      //AFFICHAGE DE SES INFORMATIONS DANS LE PANNEAU
      else {
        String message = this.listeInfo.get(liste.getSelectedIndex()); // informations de l'evenement
        this.infosDisplayer.setText("");
        this.infosDisplayer.setText(message);   // affichage des informations       
        this.archive.setEnabled(true);          // activation des boutons
        
      }
    }
	
}


@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getActionCommand().equals("Traitée")) {
		statsGaz.setText("Alarmes Gaz  : " + this.nbGaz);
		statsRad.setText("Alarmes Radiation  : " + this.nbRad);
		statsInc.setText("Alarmes Incendie  : " + this.nbIncendie);
		this.fenarch.getDlmArch().addElement(this.dlm.get(liste.getSelectedIndex()));
		this.listeInfo.remove(liste.getSelectedIndex());
		this.dlm.remove(liste.getSelectedIndex());
		System.out.println(this.fenarch.getDlmArch());
		
	}
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
	if(e.getActionCommand().equals("archive")) {
		this.fenarch.setVisible(true);
		
		
		
	}
	
	
}

// Getters and setters

public int getNbIncendie() {
	return nbIncendie;
}



public void setNbIncendie(int nbIncendie) {
	this.nbIncendie = nbIncendie;
}



public int getNbGaz() {
	return nbGaz;
}



public void setNbGaz(int nbGaz) {
	this.nbGaz = nbGaz;
}



public int getNbRad() {
	return nbRad;
}



public void setNbRad(int nbRad) {
	this.nbRad = nbRad;
}



public DefaultListModel<String> getDlm() {
	return dlm;
}


public void setDlm(DefaultListModel<String> dlm) {
	this.dlm = dlm;
}


public ArrayList<String> getListeInfo() {
	return listeInfo;
}


}