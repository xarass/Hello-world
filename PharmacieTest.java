package drugware_v20;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class PharmacieTest {
	
	private List<Client> lesClients, lesClientsTest;
	private List<Medicament> lesMedicaments, lesMedicamentsTest;
	private Pharmacie pharmacie;
	private Client client;
	private Client client2;
	private Medicament medicament, medicament2, medicament3;
	private Prescription prescription;
		
	@Rule
    public TestName name = new TestName();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		client = new Client("HASB12345678", "Hashten", "Boby");
		medicament = new Medicament();
		medicament.setNomMarque("Advil");
		medicament.setNomMolecule("Ibuprofène");
		medicament.setInteractions(new String[]{"NACL"});
		pharmacie = new Pharmacie();
		this.lesMedicaments = new ArrayList<>();
		this.lesMedicamentsTest = new ArrayList<>();
		this.lesMedicamentsTest.add(medicament);
		this.lesClients = new ArrayList<>();
		this.lesClientsTest = new ArrayList<>();
		this.lesClientsTest.add(client);
	}
	
	@Before
	public void textBefore(){
		System.out.println("\nDébut du test - Pharmacie " + name.getMethodName());
	}
	@After
	public void textAfter(){
		System.out.println("Fin du test - Pharmacie " + name.getMethodName());
	}

	@After
	public void tearDown() throws Exception {
		client = null;
		medicament = null;
		pharmacie = null;
		lesMedicaments = null;
		lesMedicamentsTest = null;
		lesClients = null;
		lesClientsTest = null;
	}

	@Test
	public void testPharmacie() {		
		System.out.println("Teste la construcion de l'objet pharmacie");
		assertNotNull("Erreur", pharmacie);
		assertNotNull("Erreur", this.lesMedicaments);
		assertNotNull("Erreur", this.lesClients);
	}

	@Test
	public void testGetLesClients() {
		System.out.println("Test l'accesseur de lesClients");
		assertEquals("Erreur", lesClients, pharmacie.getLesClients());
	}

	@Test
	public void testSetLesClients() {		
		System.out.println("Test le mutateur de lesClients");
		pharmacie.setLesClients(lesClientsTest);
		assertEquals("Erreur", lesClientsTest, pharmacie.getLesClients());
	}

	@Test
	public void testGetLesMedicaments() {
		System.out.println("Test l'accesseur de lesMedicaments");
		assertEquals("Erreur", lesMedicaments, pharmacie.getLesMedicaments());
	}

	@Test
	public void testSetLesMedicaments() {
		System.out.println("Test le mutateur de lesMedicaments");
		pharmacie.setLesMedicaments(lesMedicamentsTest);
		assertEquals("Erreur", lesMedicamentsTest, pharmacie.getLesMedicaments());
	}	

	@Test
	public void testSiClientExiste() {		
		System.out.println("Teste si le client existe");
		pharmacie.setLesClients(lesClientsTest);
		assertTrue("Erreur", pharmacie.siClientExiste(client.getNAM()));
		assertFalse("Erreur", pharmacie.siClientExiste(";oiawjefio;jwae"));
	}

	@Test
	public void testAjouterClient() {
		System.out.println("Teste si on peut ajouter un client dans la pharmacie correctment");
		pharmacie.ajouterClient("FRAS12345678", "Francoeur", "Simon");
		assertEquals("Erreur", "FRAS12345678", pharmacie.getLesClients().get(0).getNAM());
		assertEquals("Erreur", "Francoeur", pharmacie.getLesClients().get(0).getNom());
		assertEquals("Erreur", "Simon", pharmacie.getLesClients().get(0).getPrenom());
	}

	@Test
	public void testGetPrescriptionsClient() {
		System.out.println("Teste l'accesseur de PrescriptionClient");
		lireClientsBouchon();
		lireMedicamentsBouchon();
		lirePrescriptionsBouchon();
		
		assertEquals("Erreur", client2.getPrescriptions(), pharmacie.getPrescriptionsClient("SEGT12345678"));
		assertNull("Erreur", pharmacie.getPrescriptionsClient("SEGT123dfggthsthrestrhetrhe45678"));
	}

	@Test
	public void testServirPrescription() {
		System.out.println("Teste si on peut servir un prescription correctement");
		lireClientsBouchon();
		lireMedicamentsBouchon();
		lirePrescriptionsBouchon();
	
		pharmacie.servirPrescription("SEGT12345678", "NSA");
		assertEquals("Erreur", 1, prescription.getRenouvellements());
		assertTrue("Erreur", pharmacie.servirPrescription("SEGT12345678", "NSA"));
		assertFalse("Erreur", pharmacie.servirPrescription("SEGT123sfdgdsfghsfjgh45678", "NSA"));
		assertFalse("Erreur", pharmacie.servirPrescription("SEGT12345678", "NSA"));
		assertFalse("Erreur", pharmacie.servirPrescription("SEGT12345678", "NasdfdasftghsfrthsSA"));
		
	}	

	@Test
	public void testTrouverInteraction() {
		System.out.println("Teste si on peut trouver un interaction entre deux médicaments");
		assertFalse("Erreur", pharmacie.trouverInteraction("Ibuprofène", "NACL"));
		
		lireClientsBouchon();
		lireMedicamentsBouchon();
		lirePrescriptionsBouchon();
		
		assertTrue("Erreur", pharmacie.trouverInteraction("Ibuprofène", "NACL"));
		assertTrue("Erreur", pharmacie.trouverInteraction("NACL", "Ibuprofène"));
		assertFalse("Erreur", pharmacie.trouverInteraction("H2O", "Ibuprofène"));
		assertFalse("Erreur", pharmacie.trouverInteraction("Ibuprofène", "H2O"));
		
	}
	
	@Test
	public void testAjoutPrescriptionClient(){
		System.out.println("Teste si on peut ajouter une prescription a un client");
		lireClientsBouchon();
		lireMedicamentsBouchon();
		lirePrescriptionsBouchon();
		
		assertTrue("Erreur", pharmacie.ajoutPrescriptionClient("SEGT12345678", "Advil", "100.0", "2"));
		assertFalse("Erreur", pharmacie.ajoutPrescriptionClient("SEGT12345678awef", "Advil", "100.0", "2"));
		pharmacie.ajoutPrescriptionClient("SEGT12345678", "NSA", "100.0", "3");
		assertEquals("Erreur", 100, client2.getPrescriptions().get(0).getDose(), 0);
	}
	
//	@Test
//	public void testDetailPrescription(){
//		System.out.println("Teste pour l'affichage des détails d'une prescription");
//		lireClientsBouchon();
//		lireMedicamentsBouchon();
//		lirePrescriptionsBouchon();
//		
//		assertTrue(pharmacie.detailPrescription(prescription);
//		
//	}
	
	

	public void lireClientsBouchon() {
		client2 = new Client("SEGT12345678","Séguin-Chouinard","Tristan");
		lesClients.add(client2);
		pharmacie.setLesClients(lesClients);		
	}

	public void lireMedicamentsBouchon() {
		medicament2 = new Medicament();

		medicament2.setNomMarque("NSA");
		medicament2.setNomMolecule("NACL");
		medicament2.setDosesPossibles(new double[] {50, 3});
		medicament2.setInteractions(new String[] {"Ibuprofène", "AU"});
		medicament2.setUnite("mg");
		medicament2.setUsages(new String[] {"Permet de protéger les pors de la peau","Tue les microbes"});
		
		medicament3 = new Medicament();
		medicament3.setNomMarque("Puff");
		medicament3.setNomMolecule("H20");
		medicament3.setDosesPossibles(new double[] {30, 1});
		medicament3.setInteractions(new String[] {"O", "K"});
		medicament3.setUnite("mg");
		medicament3.setUsages(new String[] {"Fait fondre les graisses!","Perte de poid instantanée!!"});
		
		
		pharmacie.getLesMedicaments().add(medicament);
		pharmacie.getLesMedicaments().add(medicament2);
		pharmacie.getLesMedicaments().add(medicament3);
	}

	public void lirePrescriptionsBouchon() {
		prescription = new Prescription("NSA", 30.0, 2);		
		client2.getPrescriptions().add(prescription);
	}

}
