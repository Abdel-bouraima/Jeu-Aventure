package superDetective;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;

public class Partie {

	private GUI gui; 
	private Zone zoneCourante;
	private boolean connected = false;
	private Joueur player = new Joueur();
    
    public Partie() 
    {
        creerCarte();
        gui = null;
    }

    public void setGUI( GUI g) 
    { 
    	gui = g; 
    	afficherMessageDeBienvenue();
    }
    
    public GUI getGui()
    {
    	GUI guiGet = this.gui;
    	return guiGet;
    }
    
    private void afficheMenuConnexion()
    {
    	gui.afficher();
    	gui.afficher("Tapez connexion pour créer un compte");
    	gui.afficher();
    	gui.afficher("Tapez inscription pour vous connectez à votre compte");
    	gui.afficher();
    	gui.afficher("Tapez info pour afficher la rubrique A propos");
    	gui.afficher();
    	gui.afficher("Aide");
    }
    
    private void creerCarte() {
        Zone [] zones = new Zone [4];
        zones[0] = new Zone("le couloir", "Couloir.jpg" );
        zones[1] = new Zone("l'escalier", "Escalier.jpg" );
        zones[2] = new Zone("la grande salle", "GrandeSalle.jpg" );
        zones[3] = new Zone("la salle à manger", "SalleAManger.jpg" );
        zones[0].ajouteSortie(Sortie.EST, zones[1]);
        zones[1].ajouteSortie(Sortie.OUEST, zones[0]);
        zones[1].ajouteSortie(Sortie.EST, zones[2]);
        zones[2].ajouteSortie(Sortie.OUEST, zones[1]);
        zones[3].ajouteSortie(Sortie.NORD, zones[1]);
        zones[1].ajouteSortie(Sortie.SUD, zones[3]);
        zoneCourante = zones[1]; 
    }

    private void afficherLocalisation() {
            gui.afficher( zoneCourante.descriptionLongue());
            gui.afficher();
    }
    
    

    private void afficherMessageDeBienvenue() {
    	gui.afficheImage("logo.png");
    	gui.afficher();
    	gui.afficher();
    	gui.afficher("\t -----------------------------------");
    	gui.afficher();
    	gui.afficher(" \t Bienvenue dans Super Détective"); // Essayer de centrer
    	gui.afficher();
    	gui.afficher("\t -----------------------------------");
    	gui.afficher();
    	gui.afficher();
    	gui.afficher("Dans ce jeu d'aventure, vous incarnez un jeune détective qui doit résoudre un affaire de meurtre à l'aide d'indices et d'interrogatoire");
    	gui.afficher();
    	//gui.afficherBtn("Commencer l'avanture");
    	gui.afficher("Commencer l'aventure");
    	
    	// Lui demander de tapez commencer et ensuite afficher le menu de connexion
    	//this.attendQuelquesSecondes(10);
    	
    	this.afficheMenuConnexion();
    	
    	
    }
    
    private void afficherChoixPartie()
    {
    	gui.afficher();
        gui.afficher("Tapez Start pour commencer une nouvelle partie");
        gui.afficher();
        gui.afficher("Tapez continue pour reprendre une partie");
        gui.afficher();
        gui.afficher("Tapez top score pour afficher meilleurs scores");
        gui.afficher();
        gui.afficher("Tapez modification pour modifier vos informations de connexion");
    	
    	
    }
    
    private void attendQuelquesSecondes(int nombreSecondes)
    {
    	try {
			TimeUnit.SECONDS.sleep(nombreSecondes);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void traiterCommande(String commandeLue) {
    	gui.afficher( " > "+ commandeLue + "\n");
    	//Joueur player = new Joueur();
        switch (commandeLue.toUpperCase()) {
        
        case "CONNEXION":
        	boolean statut = player.connexionCompteJoueur();
        	if (statut == true)
        	{
        		this.afficherChoixPartie();
        		// Instanciation de l'objet joueur
        		this.connected = true;
        		// Vu que je mets toutes les commandes dans une meme fonction (parce que j'arrive pas à faire autrement vu que ectionPerformed se lance à n'importe quel événement
        		/*
        		 * Je crée une variable connected qui passe à true lorsque le joueur se connecte
        		 * S'il clique sur start meme sans etre connecté, faire une vérification sur connected. Si connected == true alors commencer partie sinon afficher le menu de connexion
        		 */
        		this.player = new Joueur();// Mettre en parametre les données
        	}	
        	else
        		System.err.println("Echec de la connexion");
        	break;
        case "INSCRIPTION": 
        	this.player.creationCompteJoueur();
        	this.afficherChoixPartie();
        	this.connected = true;
        	// Instanciation de l'objet joueur
        	this.player = new Joueur();// Mettre en parametre les données
        	break;
        case "INFO":
        	break;
        case "MODIFICATION":
        	if (this.connected == true)
        	{
        		gui.afficher("Modifier votre pseudo, mot de passe, ou nom du personnage");
        		// Lancer le code 
        		player.modificationIdentifiants();
        	}
        	else
        	{
        		gui.afficher("Vous devez etre connecté");
        		this.afficheMenuConnexion();	
        	}
        	break;	
        		
        case "HELP" : case "?":
        	this.afficherAide();
        	break;
        case "Q" : case "QUITTER" :
        	terminer();
        	break;
        case "START":
        	if (connected == true)
        	{
        		System.out.println("Partie commencée");
        		// Affiche description, présentation et autre
        		this.commencerNouvellePartie();
        		// Démarrer le chrono et le comparer au chrono max
        		// While (tempsde jeu <
        		
        	}
        	else
        	{
        		gui.afficher("Vous devez etre connecté");
        		this.afficheMenuConnexion();	
        	}
        	break;
        case "CONTINUE":
        	if (this.connected == true)
        	{
        		//
        	}
        	else
        	{
        		gui.afficher("Vous devez etre connecté");
        		this.afficheMenuConnexion();	
        	}
        	break;
        case "TOP BOARD":
        	Joueur.afficheMeilleursScores();
        	break;
        default:
            gui.afficher("Commande inconnue");
            break;
        }
    }
    
    private void commencerNouvellePartie()
    {
    	this.afficherPresentationJeu(); // Affiche la présentation du jeu
    	this.attendQuelquesSecondes(8);
    	this.afficherMenuPartie();
    }
    
    private void afficherMenuPartie()
    {
    	/*
    	 * Se déplacer
    	 * consulter résultat analyse
    	 * interroger
    	 * Demander de l'aide
    	 * Consulter inventaire
    	 * Sauvegarder partie
    	 * Arreter partie
    	 */
    }

    private void afficherPresentationJeu()
    {
    	// Petit résumé
    	// Dans ce jeu, vous incarnez Marcel, un jeune détective qui tient une agence à Las Vegas. + tirer nom d'une personne au hasard 
    	
    	//Voulez-vous choisir le nom de votre personnage ou vous souhaitez continuer avec Marcel? 
    	
    	//Créer un attribut nomdetective. s'il choisit un nouveau nom, enregistrer le nom dans cet attribut.
    	//si laissez marcel
    	/*
    	 * que vous souhaitez vous faire 
    	 * 
    	 */
    	
    	gui.afficher();
    	gui.afficher();
    	gui.afficher("Nous sommes en 2045, une année apocalyphique. L'humanité a sombré dans l'excès, la violence");
    	gui.afficher("Le taux de criminalité n'a jamais été aussi haut. Les magasins sont pillés, incendiés, des innocents assassinés, kidnappés.");
    	gui.afficher("Les forces de l'ordre sont dépassés par tant de violence. Il y a beaucoup trop d'affaire à régler");
    	gui.afficher("Les révolutions d'enquete sont baclés et prennent énormément de temps");
    	gui.afficher("Face à cette situation, les citoyens ont perdu confiance en l'autorité et préfèrent faire appel aux détectives privés");
    	gui.afficher("Vous incarnez un jeune détective privé qui doit résoudre une afaire de meurtre");
    	
    	this.attendQuelquesSecondes(4);
    	
    	gui.afficher();
    	/*
    	 * getNomPersonnge(String role) : renvoie le nom du personage en fonction du role passé en paramètre
    	 * Sera une requete sur la table Personnage
    	 * A la fin de la présentation, demandez à l'utilisateur de saisir le nom de son personnage
    	 * Est ce que je peux faire un tableau dans lequel mettre les informations et le renvoyer dans un return ?
    	 */
    	gui.afficher("");

    }
    
    private void afficherAPropos()
    {
    	gui.afficher();
    	gui.afficher("Ce jeu a été réalisé par deux étudiants en troisième année de licence gestion parcours MIAGE");
    	gui.afficher("dans le cadre d'un projet d'année");
    	// il s'agit d'une première version sans graphique. de nouvelles versions plus avancées (avec graphisme seront bientot disponibles)
    	// bon jeu
    }
    
    private void continuerPartie(int progression)
    {
        switch (progression)
        {
        case 0:
        	this.commencerNouvellePartie();
        	break;
        }
    		
    }
    
    private void afficherAide() {
        gui.afficher("Etes-vous perdu ?");
        gui.afficher();
        gui.afficher("Les commandes autorisées sont :");
        gui.afficher();
        gui.afficher("");
        //gui.afficher(Commande.toutesLesDescriptions().toString());
        gui.afficher();
    }

    private void allerEn(String direction) {
    	Zone nouvelle = zoneCourante.obtientSortie( direction);
    	if ( nouvelle == null ) {
        	gui.afficher( "Pas de sortie " + direction);
    		gui.afficher();
    	}
        else {
        	zoneCourante = nouvelle;
        	gui.afficher(zoneCourante.descriptionLongue());
        	gui.afficher();
        	gui.afficheImage(zoneCourante.nomImage());
        }
    }
    
    private void terminer() {
    	gui.afficher( "Au revoir...");
    	gui.enable( false);
    }
}
