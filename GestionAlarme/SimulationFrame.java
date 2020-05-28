package GestionAlarme;

	
	
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SimulationFrame extends JFrame
                             implements ActionListener {


    
	private static final long serialVersionUID = 1L;

	private JButton launcher = new JButton("Declencher");

    private JTextField building = new JTextField("Localisation");

    private JComboBox<Integer> critLevel = new JComboBox<>();

    private JComboBox<String> alarmType = new JComboBox<>();

    JPanel moreData  = new JPanel();

    private JTextField gazType = new JTextField("C02, Azote..");

    private JComboBox<Integer>  radiationLevel = new JComboBox<Integer>(); 
    
    private Font  f1  = new Font(Font.DIALOG, Font.PLAIN,  12);
    
    public MonitorWindow monitors1;
    public MonitorWindow monitors2;


    
    public SimulationFrame(String title)
    {
        super(title);
      //récuperer la dimension de l'écran
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width * 1/4;
        int hauteur = tailleMoniteur.height * 1/2;
        //régler la taille de JFrame à 2/3 la taille de l'écran
        this.setSize(longueur, hauteur);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(50, 50);;

        this.initMenu();
        this.initForm();
        this.setVisible(true);
    }

    /**
     * Cette fonction génère le contenu du menu
     */
    
    public void initMenu()
    {
       
        JMenuBar menuBar = new JMenuBar(); //barre de menu 
        
        
        this.setJMenuBar(menuBar);

        /*----------------------------------
            MENU
        ----------------------------------*/
        JMenu menu = new JMenu("Utils");
        menuBar.setFont(f1);
        menu.setFont(f1);
        menuBar.add(menu);

        /*----------------------------------
            SOUS-MENU
        ----------------------------------*/
        JMenuItem quit = new JMenuItem("Quitter");// bouton quitter qui quitte l'appli
        quit.setFont(f1);
        menu.add(quit);

        /*----------------------------------
            ACTIONS
        ----------------------------------*/
        quit.setActionCommand("quit");
        quit.addActionListener(this);
    }

    /**
     * Cette fonction génère le contenu principale de la page, c'est a dire le formulaire d'alarme
     */
    
    public void initForm()
    {
    	 Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
         int longueur = tailleMoniteur.width * 1/4;
         int hauteur = tailleMoniteur.height * 1/2;
       //Bouton localisation, ou l'on rentre un texte 
        JPanel locPanel = new JPanel();
        JLabel locLabel = new JLabel("Location :");
        building.setBounds(128, 28, 86, 20);
        building.setColumns(10);
        locLabel.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/16));
        locLabel.setFont(f1);
        building.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/12));
        building.setFont(f1);
        
        locPanel.add(locLabel);
        locPanel.add(building);

        //Niveau d'importance
        JPanel critPanel = new JPanel();
        JLabel critLabel = new JLabel("Critical level :");
        Integer[] lvls = {1,2,3};

        for(int i = 0; i < 3; i++)
            critLevel.addItem(lvls[i]);
        
        critLabel.setPreferredSize(new Dimension(new Dimension(longueur*1/4,hauteur*1/12)));
        critLabel.setFont(f1);
        critLevel.setPreferredSize(new Dimension(longueur*1/3,hauteur*1/18));
        critLevel.setFont(f1);

        critPanel.add(critLabel);
        critPanel.add(critLevel);

        // Selection des 3 types d'alarmes possible
        JPanel alarmPanel = new JPanel();
        JLabel alarmLabel = new JLabel("Alarm Type :");
        String[] types = {"Fire", "Gaz", "Radiation"};     

        for(int i = 0; i < 3; i++)
            alarmType.addItem(types[i]);
        alarmLabel.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/20));
        alarmLabel.setFont(f1);
        alarmType.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/18));
        alarmType.setFont(f1);
        alarmType.addActionListener(this);
        

        alarmPanel.add(alarmLabel);
        alarmPanel.add(alarmType);
        
       // Bouton déclecher qui envoie l'alarme sur les moniteurs associés
        JPanel buttonPanel = new JPanel();
        launcher.setPreferredSize(new Dimension(longueur*1/3,hauteur*1/18));
        launcher.setFont(f1);
        buttonPanel.add(launcher);
        
        
        
    
        
        this.getContentPane().add(locPanel);
        this.getContentPane().add(critPanel);
        this.getContentPane().add(alarmPanel);
        this.getContentPane().add(moreData);
        this.getContentPane().add(buttonPanel);

      //Evenements 
        launcher.setActionCommand("alarm");             // clic sur le bouton de declenchement de l'alarme
        launcher.addActionListener(this);
        alarmType.setActionCommand("typeSelected");     // selection d'un type d'alarme
        alarmType.addActionListener(this);
    }
    
    /**
     * Cette fonction génère les actions provoqué par le formulaire 
     */

	@Override
	public void actionPerformed(ActionEvent event) {
		Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width * 1/4;
        int hauteur = tailleMoniteur.height * 1/2;
		String typeSelected = "";
		int lvl             = 0;                                                // niveau critique
        String loc          = "";                                               // localisation
       
		// TODO Auto-generated method stub
		if(event.getActionCommand().equals("typeSelected"))
        {
            typeSelected = this.alarmType.getSelectedItem().toString();

            // INCENDIE
            if(typeSelected == "Fire")
            {
            	// si le type est feu on n'affiche pas les infos compémentaires à remplir
                this.setVisible(false);
                moreData.removeAll();
                this.setVisible(true);
            }
            
            // GAZ
            if(typeSelected == "Gaz")
            {
            	// si le type est gaz on affiche les infos compémentaires à remplir
                this.setVisible(false);
                moreData.removeAll();       

                gazType.setBounds(128, 28, 86, 20);
                gazType.setColumns(10);
                JLabel gazLabel = new JLabel("Gaz type");
                gazLabel.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/16));
                gazLabel.setFont(f1);
                gazType.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/16));
                gazType.setFont(f1);
                
                moreData.add(gazLabel);
                moreData.add(gazType);

                this.setVisible(true);
            }

            // RADIATION
            if(typeSelected == "Radiation")
            {
            	// si le type est radiation on affiche les infos compémentaires à remplir
                this.setVisible(false);
                moreData.removeAll();

                radiationLevel.setBounds(128, 28, 86, 20);
                JLabel radLabel = new JLabel("Radiation level");
                radLabel.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/18));
                radLabel.setFont(f1);
                radiationLevel.setPreferredSize(new Dimension(longueur*1/4,hauteur*1/18));
                radiationLevel.setFont(f1);
                
                ArrayList<Integer> test = new ArrayList<Integer>();
            
                for(int i = 0; i <=100; i++) {
                	radiationLevel.addItem(i);
                }
                
                moreData.add(radiationLevel);

                this.setVisible(true);
            }
        }
	
	if (event.getActionCommand().equals("alarm"))
    {
        typeSelected = this.alarmType.getSelectedItem().toString();
        lvl          = this.critLevel.getSelectedIndex()+1;
        loc          = this.building.getText();
        
        
        //date de declenchement
        Date current = new Date();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);

        //FIRE ALARM
        if(typeSelected == "Fire") 
        {
        	SourceIncendie f = new SourceIncendie();
            IncendieEvent feu = new IncendieEvent(event.getSource(),loc,lvl,true);
            Moniteur ecologie = new Moniteur("B");
            f.addIncendieListener(ecologie);
            
            JOptionPane jop2 = new JOptionPane();
            jop2.setPreferredSize(new Dimension(500, 200));
            jop2.setFont(f1);
            jop2.setPreferredSize(new Dimension(480,500));
            String s = "Alerte de type "+ typeSelected +" " + shortDateFormat.format(current);
            // On fournit les informations au deux moniteurs associés
            this.monitors1.getDlm().addElement(s);
            this.monitors1.getListeInfo().add(ecologie.attentionFume(feu));
            
            this.monitors1.setNbIncendie(this.monitors1.getNbIncendie()+1);
            //generation du message d'alerte
            jop2.showMessageDialog(this, "<html><span style='font-size:18'>"+ecologie.attentionFume(feu), "Attention", JOptionPane.WARNING_MESSAGE);
            
                    }
            
        //GAZ ALARM
        else if(typeSelected == "Gaz") 
        {
        	String gType = this.gazType.getText();
        	SourceGaz g = new SourceGaz();
            GazEvent gaz  = new GazEvent(event.getSource(),loc,lvl,gType);
            
            Moniteur ecologie = new Moniteur("B");
            g.addGazListener(ecologie);
            
          
            
            JOptionPane jop2 = new JOptionPane();
            jop2.setPreferredSize(new Dimension(500, 200));
            jop2.setFont(f1);
            jop2.setPreferredSize(new Dimension(480,500));
            
            String s = "Alerte de type " +typeSelected +" " + shortDateFormat.format(current);
            
            // On fournit les informations au deux moniteurs associés
            
         // On ajoute le string de l'alarme a la liste en attribut du moniteur
            this.monitors1.getDlm().addElement(s);
         
            this.monitors1.getListeInfo().add(ecologie.attentionGaz(gaz));
            this.monitors1.setNbGaz(this.monitors1.getNbGaz()+1);
            this.monitors2.setNbGaz(this.monitors2.getNbGaz()+1);
            this.monitors2.getDlm().addElement(s);
            this.monitors2.getListeInfo().add(ecologie.attentionGaz(gaz));
            
          //generation du message d'alerte
            jop2.showMessageDialog(this, "<html><span style='font-size:18'> "+ecologie.attentionGaz(gaz) ,"Attention", JOptionPane.WARNING_MESSAGE);
            
            
        }

        //RADIATION ALARM
        else if(typeSelected == "Radiation") 
        {
        	
            int radLvl = this.radiationLevel.getSelectedIndex()+1;
            if(radLvl < 0 || radLvl > 100) {
                int option = JOptionPane.showConfirmDialog(this,
                                                           "Le niveau de radiation doit être compris entre 0 et 100",
                                                           "Attention !",
                                                           JOptionPane.WARNING_MESSAGE);
            }
            else {
            	int radLvl1 = this.radiationLevel.getSelectedIndex()+1;
            	
            	SourceGaz r = new SourceGaz();
                RadiationEvent rad  = new RadiationEvent(event.getSource(),loc,lvl,radLvl1);
                
                Moniteur ecologie = new Moniteur("B");
                r.addGazListener(ecologie);
                
                JOptionPane jop2 = new JOptionPane();
                jop2.setPreferredSize(new Dimension(500, 200));
                jop2.setFont(f1);
                jop2.setPreferredSize(new Dimension(480,500));
                String s ="Alerte de type "+ typeSelected +" " + shortDateFormat.format(current);
                // On ajoute le string de l'alarme a la liste en attribut du moniteur 
                this.monitors2.getDlm().addElement(s);
                //// On ajoute le string  qui s'affichera dans le tableau d'affichage du moniteur quand on selectionnera l'élémentS
                this.monitors2.getListeInfo().add(ecologie.attentionRadiation(rad));
                //On incrémente le nombre d'alarme 
                this.monitors1.setNbRad(this.monitors1.getNbRad()+1);
                this.monitors2.setNbRad(this.monitors2.getNbRad()+1);
              //generation du message d'alerte
                jop2.showMessageDialog(this, "<html><span style='font-size:18'> "+ecologie.attentionRadiation(rad) ,"Attention", JOptionPane.WARNING_MESSAGE);
                
            }
            
        }
    }
	if ( event.getActionCommand().equals("quit") )                     // si on sélectionne "Quitter"
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
}



















