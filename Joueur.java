package superDetective;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class Joueur {
	
	private String pseudo;
	private String mdp;
	private int score;
	private String dateInscription;
	private LinkedList<String> historiqueConnexion;
	private LinkedList<Objet> inventaire;
	private int progression;
	private long tempsDeJeu; // revoir le type. Stocker le temps de jeu du joueur
	final static long tempsDeJeuMax = 0; // temps de jeu max à ne pas dépasser
	
	/*
	 * Constructeur
	 * Méthode static connexion (boolean)
	 * Méthode inscription (void)
	 */
	public Joueur()
	{
		
	}
	
	public Joueur (String pseudo, String mdp, int score, String date) // Instanciation de l'objet, renseigner toutes les propriétés
	{
		
	}
	
	public void creationCompteJoueur()
	{
		try (Scanner scanner = new Scanner (System.in))
		{
			MetierJeu monMetier = new MetierJeu();
			monMetier.init("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3307/projetjeujava", "root", "");
			
			System.out.println("Entrez votre pseudo (il vous permettra de vous connecter et servira aussi de nom à votre personnage)");
			
			
			//String pseudo = gui.recupereDonneeSaisie();
			String pseudo = scanner.nextLine();
			int nb = monMetier.rechercheJoueur(pseudo);
			if (nb == 0)
			{
				System.out.println("Entrez votre mot de passe");
				Scanner scannerBis = new Scanner (System.in);
				String motDePasse = scannerBis.nextLine();
				
				// Enregistrer le nouveau joueur
				// Formater la date
				LocalDateTime myDateObj = LocalDateTime.now();
			    System.out.println("Before formatting: " + myDateObj);
			    DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			    String formattedDate = myDateObj.format(date);
			    // Ajout dans la base
				monMetier.ajoutJoueur(pseudo, motDePasse, 0, 0, formattedDate);
				// Faut -il instancier  un objet joueur ici ?
			}
			else
				System.err.println("Ce pseudo est déjà pris. Rééssayer");
		}
	}
	
	public boolean connexionCompteJoueur()
	{
		boolean statutConnection = false;
		try (Scanner scanner = new Scanner (System.in))
		{
			MetierJeu monMetier = new MetierJeu();
			monMetier.init("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3307/projetjeujava", "root", "");
			
			System.out.println("Entrez votre pseudo");
			Scanner scannerBis = new Scanner (System.in);
			pseudo = scanner.nextLine();

			int nb = monMetier.rechercheJoueur(pseudo);
			if (nb == 0)
			{
				System.err.println("ce pseudo n'est pas attribué. Rééssayer");
			}
			else
			{
				if (nb == 1)
				{
					System.out.println("Entrez votre mot de passe");
					Scanner scannerTer = new Scanner (System.in);
					String motDePasse = scannerTer.nextLine();
					
					// Comment comparer les informations saisies avec les identifoants enregistés?
					/* 
					 * Solution 1: mettre les joueurs dans un tableau de longeur count(nbjoueur)
					 * Faire comme en PHP avec un in_array
					 * Solution 2: 
					 * comme j'ai commencé mais connexion devrait etre static et c'est après la connexion qu'on créera l'objet Joueur
					 * Solution 3: Faire une requete pour trouver le mot de passe qui correspond au pseudo selectionne
					 * SELECT mdp FROM joueur where pseudo = pseudosaisi
					 * Si mdp trouve == mdp saisi, bon. ensuite créer l'objet avec une nouvelle date de connexion
					 */
					boolean ok = monMetier.verificationIdentifiant(pseudo, motDePasse);
					//System.out.println("Valeur retournée" + ok);
					//String[] donneeJoueur = monMetier.recupereInfoJoueur(pseudo);
					if (ok == true)
						statutConnection = true;
					else
						System.err.println("Mot de passe incorrect");
				}
				else
					System.err.println("Erreur inconnue normalement on devrait trouvé un seul joueur");
			}
		
			return statutConnection;
		}
	
	}
	
	public static void afficheMeilleursScores()
	{
		MetierJeu monMetier = new MetierJeu();
		monMetier.init("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3307/projetjeujava", "root", "");
		monMetier.afficheTopScore(); 
	}
	
	public void modificationIdentifiants()
	{
		int nbTuplesModifie;
		try (Scanner scanner = new Scanner (System.in))
		{
			MetierJeu monMetier = new MetierJeu();
			monMetier.init("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3307/projetjeujava", "root", "");
			
			System.out.println("Entrez votre nouveau pseudo (Retapez votre ancien pseudo si vous ne souhaitez pas le modifier)");
			
			
			//String pseudo = gui.recupereDonneeSaisie();
			String pseudo = scanner.nextLine();
			System.out.println("Entrez votre mot de passe (Retapez votre ancien mot de passe si vous ne souhaitez pas le modifier)");
			Scanner scannerBis = new Scanner (System.in);
			String motDePasse = scannerBis.nextLine();
				
			// Modifier les informations de connexion le nouveau joueur
				
			// Modofication dans la base
			monMetier.modifInfosJoueur(pseudo, motDePasse);
		}
		
	}
	
	public void demarreChrono()
	{
		
	}
	
	public void arreteChrono()
	{
		
	}
	
	public long getTempsDeJeu()
	{
		return this.tempsDeJeu;
	}

}