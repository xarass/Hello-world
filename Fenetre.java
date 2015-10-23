// auteurs: Maud El-Hachem
// 2015
package drugware_v20;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.IconUIResource;

public class Fenetre extends JFrame {
	private JMenuBar menuBar;
	private JMenu menuFic;
	private JMenu menuClients;
	private JMenu menuPresc;
	private JMenu menuMedic;

	private JMenuItem itemFic1;
	private JMenuItem itemFic2;
	private JMenuItem itemFic3;

	private JMenuItem itemClients1;
	private JMenuItem itemClients2;

	private JMenuItem itemPresc1;
	private JMenuItem itemPresc2;
	private JMenuItem itemPresc3;

	private JMenuItem itemMedic1;
	private JMenuItem itemMedic2;

	private Pharmacie pharma;
	
	private JPanel panel = new JPanel(new BorderLayout());
	private JLabel label = new JLabel();

	public Fenetre() {
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Pharmacie");
		label.setText("          Bienvenu dans\nl'application pharmacie!!");
		panel.add(label);
		this.add(panel, BorderLayout.CENTER);
		this.setIconImage(new ImageIcon(this.getClass().getResource("icon.png")).getImage());

		menuBar = new JMenuBar();
		menuFic = new JMenu("Fichier");
		menuClients = new JMenu("Clients");
		menuPresc = new JMenu("Prescriptions");
		menuMedic = new JMenu("M�dicaments");

		itemFic1 = new JMenuItem("Charger les fichiers");
		itemFic2 = new JMenuItem("Mettre � jour les fichiers");
		itemFic3 = new JMenuItem("Quitter");

		itemClients1 = new JMenuItem("Inscrire un nouveau client");
		itemClients2 = new JMenuItem("Afficher tous les clients");

		itemPresc1 = new JMenuItem("Afficher les prescriptions d'un client");
		itemPresc2 = new JMenuItem("Servir une prescription");
		itemPresc3 = new JMenuItem("Ajouter une prescription");

		itemMedic1 = new JMenuItem("Afficher tous les m�dicaments");
		itemMedic2 = new JMenuItem("Afficher si interaction");

		pharma = new Pharmacie();

	}

	public void initMenus() {

		// Menu fichier
		itemFic1.addActionListener(new BoutonFic1Listener());
		this.menuFic.add(itemFic1);
		itemFic2.addActionListener(new BoutonFic2Listener());
		this.menuFic.add(itemFic2);

		// Ajout d'un s�parateur
		this.menuFic.addSeparator();
		// si quitter
		itemFic3.addActionListener(new BoutonFic3Listener());
		this.menuFic.add(itemFic3);

		// Menu Clients
		itemClients1.addActionListener(new BoutonClient1Listener());
		this.menuClients.add(itemClients1);
		itemClients2.addActionListener(new BoutonClient2Listener());
		this.menuClients.add(itemClients2);

		// Menu Prescriptions
		itemPresc1.addActionListener(new BoutonPresc1Listener());
		this.menuPresc.add(itemPresc1);
		itemPresc2.addActionListener(new BoutonPresc2Listener());
		this.menuPresc.add(itemPresc2);
		itemPresc3.addActionListener(new BoutonPresc3Listener());
		this.menuPresc.add(itemPresc3);

		// Menu M�dicaments
		itemMedic1.addActionListener(new BoutonMedic1Listener());
		this.menuMedic.add(itemMedic1);
		itemMedic2.addActionListener(new BoutonMedic2Listener());
		this.menuMedic.add(itemMedic2);

		this.menuBar.add(menuFic);
		this.menuBar.add(menuClients);
		this.menuBar.add(menuPresc);
		this.menuBar.add(menuMedic);
		this.setJMenuBar(menuBar);
		this.setVisible(true);

	}

	public class BoutonFic1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			pharma.lireClients();
			pharma.lirePrescriptions();
			pharma.lireMedicaments();
		}
	}

	public class BoutonFic2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			pharma.ecrireClients();
			pharma.ecrirePrescriptions();
		}
	}

	public class BoutonFic3Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	public class BoutonClient1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String nom, prenom;
			String NAM = JOptionPane.showInputDialog(null,
					"Entre le num�ro d'assurance maladie",
					"Num�ro d'assurance maladie", JOptionPane.QUESTION_MESSAGE);
			if (NAM != null && NAM.length() > 0)
				if (pharma.siClientExiste(NAM))
					JOptionPane.showMessageDialog(null,
							"Ce num�ro d'assurance maladie existe d�j�",
							"Probl�me", JOptionPane.INFORMATION_MESSAGE);
				else {
					nom = JOptionPane.showInputDialog(null, "Entre le nom",
							"Nom", JOptionPane.QUESTION_MESSAGE);
					prenom = JOptionPane.showInputDialog(null,
							"Entre le prenom", "Pr�nom",
							JOptionPane.QUESTION_MESSAGE);
					if (nom != null && nom.length() > 0 && prenom != null
							&& prenom.length() > 0)
						pharma.ajouterClient(NAM, nom, prenom);
				}
		}
	}

	public class BoutonClient2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JFrame nouvelle = new JFrame("Clients");
			int nbClients = pharma.getLesClients().size();
			JPanel panneau = new JPanel();
			panneau.setLayout(new GridLayout(nbClients, 1));
			panneau.setBackground(Color.white);
			for (Iterator<Client> it = pharma.getLesClients().iterator(); it.hasNext();) {
				Client courant = it.next();
				JLabel label = new JLabel();
				label.setText("<html><p style='font-size:14px'>"
						+ courant.afficherClient() + "</p></html>");
				panneau.add(label);
			}
			nouvelle.setSize(400, nbClients*100);
			nouvelle.add(panneau);
			nouvelle.setVisible(true);

		}
	}

	public class BoutonPresc1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String NAM = JOptionPane.showInputDialog(null,
					"Entre le num�ro d'assurance maladie",
					"Num�ro d'assurance maladie", JOptionPane.QUESTION_MESSAGE);
			if (NAM != null && NAM.length() > 0) {
				List<Prescription> liste = pharma.getPrescriptionsClient(NAM);
				if (liste != null) {
					JFrame nouvelle = new JFrame("Prescription du client");
					int nbPresc = liste.size();
					JPanel panneau = new JPanel();
					panneau.setLayout(new GridLayout(nbPresc, 1));
					panneau.setBackground(Color.white);
					for (Iterator<Prescription> it = liste.iterator(); it
							.hasNext();) {
						Prescription courant = it.next();
						JLabel label = new JLabel();
						label.setText("<html><p style='font-size:14px'>"
								+ courant.afficherPrescription() + "</p></html>");
						panneau.add(label);
					}
					nouvelle.add(panneau);
					nouvelle.setSize(400, nbPresc*100);
					nouvelle.setVisible(true);
				}

			}
		}
	}

	public class BoutonPresc2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String NAM = JOptionPane.showInputDialog(null,
					"Entre le num�ro d'assurance maladie",
					"Prescription", JOptionPane.QUESTION_MESSAGE);
			String medicament = JOptionPane.showInputDialog(null,
					"Entre le nom du m�dicament",
					"Prescription", JOptionPane.QUESTION_MESSAGE);
			if (pharma.servirPrescription(NAM, medicament))
				JOptionPane.showMessageDialog(null,
						"Prescription servie!",
						"Prescription", JOptionPane.INFORMATION_MESSAGE);
			else 
				JOptionPane.showMessageDialog(null,
						"Il n'est pas possible de servir la prescription",
						"Prescription", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public class BoutonPresc3Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String NAM = JOptionPane.showInputDialog(null,
					"Entre le num�ro d'assurance maladie",
					"Prescription", JOptionPane.QUESTION_MESSAGE);
			String medicament = JOptionPane.showInputDialog(null,
					"Entre le nom du m�dicament",
					"Prescription", JOptionPane.QUESTION_MESSAGE);
			String dose = JOptionPane.showInputDialog(null, "Entre la dose du medicament", "Prescription", JOptionPane.QUESTION_MESSAGE);
			String renouvellement = JOptionPane.showInputDialog(null,"Entre le nombre de renouvellement du medicament", "Prescription", JOptionPane.QUESTION_MESSAGE);
			if (pharma.ajoutPrescriptionClient(NAM, medicament,dose,renouvellement))
				JOptionPane.showMessageDialog(null,
						"Prescription ajoute!",
						"Prescription", JOptionPane.INFORMATION_MESSAGE);
			else 
				JOptionPane.showMessageDialog(null,
						"Il n'est pas possible d'ajouter la prescription",
						"Prescription", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public class BoutonMedic1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JFrame nouvelle = new JFrame("M�dicaments");
			int nbMedic = pharma.getLesMedicaments().size();
			JPanel panneau = new JPanel();
			panneau.setLayout(new GridLayout(nbMedic, 1));
			panneau.setBackground(Color.white);
			for (Iterator<Medicament> it = pharma.getLesMedicaments()
					.iterator(); it.hasNext();) {
				Medicament courant = it.next();
				JLabel label = new JLabel();
				label.setText("<html><p style='font-size:14px'>"
						+ courant.getNomMolecule() + " "
						+ courant.getNomMarque() + "</p></html>");
				panneau.add(label);
			}
			nouvelle.add(panneau);
			nouvelle.setSize(400, nbMedic*100);
			nouvelle.setVisible(true);

		}
	}

	public class BoutonMedic2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String medicament1 = JOptionPane.showInputDialog(null,
					"Entre le nom de la mol�cule ou du m�dicament no 1",
					"Interactions", JOptionPane.QUESTION_MESSAGE);
			String medicament2 = JOptionPane.showInputDialog(null,
					"Entre le nom de la mol�cule ou du m�dicament no 2",
					"Interactions", JOptionPane.QUESTION_MESSAGE);
			if (pharma.trouverInteraction(medicament1, medicament2))
				JOptionPane.showMessageDialog(null,
						"Interaction trouv�e! Faites attention!",
						"Interactions", JOptionPane.INFORMATION_MESSAGE);
			else 
				JOptionPane.showMessageDialog(null,
						"Aucune interaction trouv�e!",
						"Interactions", JOptionPane.INFORMATION_MESSAGE);
		}
	}



}