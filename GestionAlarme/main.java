package GestionAlarme;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class main {
	
	public static void main(String[] args) throws Exception{
		// Apply a new look'n feel
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		UIManager.put("JOptionPane.font", new Font("yourFontName", Font.BOLD, 30));
		Moniteur pompier  = new Moniteur("A");
	    Moniteur ecologie = new Moniteur("B");
		//Start the window
		SimulationFrame fen = new SimulationFrame("Simulation des alarmes");
		 //récuperer la dimension de l'écran
	    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
	    int longueur1 = tailleMoniteur.width * 1/3;
	    int hauteur1 = tailleMoniteur.height * 1/10;
	    int hauteur2 = tailleMoniteur.height * 1/9;
	    int hauteur = tailleMoniteur.height * 1/5;
	    
		MonitorWindow mon = new MonitorWindow("Caserne",longueur1,hauteur1);
		MonitorWindow mon2 = new MonitorWindow("Ecologie",longueur1,hauteur2 + hauteur + 100 );
		fen.monitors1 = mon;
		fen.monitors2 = mon2;
		mon.monitorList.add(pompier);
		mon2.monitorList.add(ecologie);
		ArchiveWindow arch = new ArchiveWindow("Archive",longueur1,hauteur*3+50);
		mon.fenarch = arch;
		mon2.fenarch = arch;
		
	  }       
}

