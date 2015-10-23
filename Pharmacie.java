// auteurs: Maud El-Hachem
// 2015
package drugware_v20;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

public class Pharmacie {
	private List<Client> lesClients;
	private List<Medicament> lesMedicaments;

	public Pharmacie() {
		this.lesMedicaments = new ArrayList<>();
		this.lesClients = new ArrayList<>();
	}

	/**
	 * @return the lesClients
	 */
	public List<Client> getLesClients() {
		return lesClients;
	}

	/**
	 * @param lesClients
	 *            the lesClients to set
	 */
	public void setLesClients(List<Client> lesClients) {
		this.lesClients = lesClients;
	}

	/**
	 * @return the lesMedicaments
	 */
	public List<Medicament> getLesMedicaments() {
		return lesMedicaments;
	}

	/**
	 * @param lesMedicaments
	 *            the lesMedicaments to set
	 */
	public void setLesMedicaments(List<Medicament> lesMedicaments) {
		this.lesMedicaments = lesMedicaments;
	}

	public void lireClients() {
		Fichiers fichier = new Fichiers();
		fichier.lireClients(lesClients);
	}

	public void lireMedicaments() {
		Fichiers fichier = new Fichiers();
		fichier.lireMedicaments(lesMedicaments);
	}

	public void lirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.lirePrescriptions(lesClients);
	}

	public boolean siClientExiste(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				return true;
			}
		}
		return false;
	}

	public void ajouterClient(String NAM, String nom, String prenom) {
		this.lesClients.add(new Client(NAM, nom, prenom));
	}

	public List<Prescription> getPrescriptionsClient(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client client = it.next();
			if (client.getNAM().equals(NAM)) {
				return client.getPrescriptions();
			}
		}
		return null;
	}

	public boolean servirPrescription(String NAM, String medicament) {
		boolean delivree = false;
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				for (Iterator<Prescription> it2 = itClient.getPrescriptions().iterator(); it2.hasNext();) {
					Prescription courante = it2.next();
					if (courante.getMedicamentAPrendre().equalsIgnoreCase(medicament))
						if (courante.getRenouvellements() >= 1) {
							courante.setRenouvellements(courante.getRenouvellements() - 1);
							delivree = true;
							detailPrescription(courante);
						}
				}
			}
		}

		return delivree;
	}

	public boolean trouverInteraction(String medicament1, String medicament2) {
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament courant = it.next();
			if (courant.getNomMolecule().equalsIgnoreCase(medicament1))
				for (int i = 0; i < courant.getInteractions().length; i++)
					if (courant.getInteractions()[i].equalsIgnoreCase(medicament2))
						return true;
			if (courant.getNomMolecule().equalsIgnoreCase(medicament2))
				for (int i = 0; i < courant.getInteractions().length; i++)
					if (courant.getInteractions()[i].equalsIgnoreCase(medicament1))
						return true;
		}
		return false;
	}

	public boolean ajoutPrescriptionClient(String NAM, String medicament, String dose, String renouvellement) {
		boolean ajoute = false, remplace = false;
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				for (Iterator<Prescription> it2 = itClient.getPrescriptions().iterator(); it2.hasNext();) {
					Prescription courante = it2.next();
					if (courante.getMedicamentAPrendre().equalsIgnoreCase(medicament)) {
						courante.setDose(Double.parseDouble(dose));
						courante.setRenouvellements(Integer.parseInt(renouvellement));
						remplace = true;
						ajoute = true;
					}
				}
				if (remplace == false) {
					Prescription ajout = new Prescription(medicament, Double.parseDouble(dose),
							Integer.parseInt(renouvellement));
					itClient.getPrescriptions().add(ajout);
					ajoute = true;
				}
			}
		}

		return ajoute;
	}

	public void detailPrescription(Prescription prescription) {
		Medicament med = new Medicament();
		String texte = "Les doses suggérées sont : ";
		double tmp = prescription.getDose();
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			med = it.next();
			if (med.getNomMarque().equalsIgnoreCase(prescription.getMedicamentAPrendre())) {

				for (int i = med.getDosesPossibles().length - 1; i >= 0; i--) {
					for (int j = 0; tmp >= med.getDosesPossibles()[i]; j++) {
						//if (tmp >= med.getDosesPossibles()[i]) {

							tmp -= med.getDosesPossibles()[i];
							if (tmp < med.getDosesPossibles()[i]) {
								texte += (j + 1) + " dose(s) de " + med.getDosesPossibles()[i] + med.getUnite() + ", ";
							}

						//}
					}

				}

			}
		}

		JOptionPane.showMessageDialog(null, texte, "Prescription", JOptionPane.INFORMATION_MESSAGE);

	}

	public void ecrireClients() {
		Fichiers fichier = new Fichiers();
		fichier.ecrireClients(lesClients);
	}

	public void ecrirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.ecrirePrescriptions(lesClients);
	}

}
