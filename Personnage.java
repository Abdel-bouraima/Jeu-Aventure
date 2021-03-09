package superDetective;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

public class Personnage {
	
	private String nomPersonnage;
	private String role; // Epouse, soeur, preteur sur gage, ami, victime, médecin, gérant
	private boolean culpabilite; // Désigne false s'il est innocent et true dans le cas contraire
	private String description; // Age, groupe sangain, alibi, Se met à jour au fur et à mesure de l'enquete
	private static Vector<Stack<Personnage>> allName = null;
	
	// Les personnages recoivent des noms aléatoires en fonctions de leur role
	/*
	 * Il existe une table NomPersonnage dans la base de données
	 */
	
	private Personnage(String nom, String role, boolean coupable, String description)
	{
		this.nomPersonnage = nom;
		this.role = role;
		this.culpabilite = culpabilite;
		this.description = description;
	}
	
	private Personnage(String nom, String role)
	{
		this.nomPersonnage = nom;
		this.role = role;
	}
	
	private static void remblissageFileDeNom()
	{
		//String requeteSQL = "SELECT * FROM NomPersonnage";
		/*
		 * Qaund je fais ca je peux tirer des hommes pu des femmes
		 * Pour résoudre cette solution, je peux fare un vector (un tableau de deux cases: une pile des hommes et une secondes avec les femmes ou faire des piles en fonction des roles
		 * Tant que resultSet n'est pas vide
		 * Instancier un nouvel objet avec les informations saisies et l'ajouter
		 * Personnage perso = new Personnage (results.getString(nom), ...)
		 * allName.add(perso);
		 * ajouter un personnage dans la file
		 */
		// Attribution du nombre de cases du tableau 
		// Une case = un role
		Personnage.allName = new Vector<Stack<Personnage>>(7);
		int i = 0;
		while (i < 7)
		{
			Personnage.allName.add(new Stack<Personnage>());
			i = i + 1;
		}
		
		MetierJeu monMetier = new MetierJeu();
		monMetier.init("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3307/projetjeujava", "root", "");
		String[] tabRole = new String[7];
		tabRole[0] = "Victime";
		tabRole[1] = "Epouse";
		tabRole[2] = "Soeur";
		tabRole[3] = "Gérant casino";
		tabRole[4] = "Ami";
		tabRole[5] = "Preteur sur gage";
		tabRole[6] = "Médecin";
		
		i = 0;
		while (i < 7)
		{
			ResultSet resultats = monMetier.renvoieNomPersonnage(tabRole[i]);
			try {
				do
				{
					Personnage perso = new Personnage (resultats.getString("Nom"), resultats.getString("Role"));
					Personnage.allName.get(i).push(perso);
				}while (resultats.next() != false);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			i = i + 1;
		}
	}
	
	private static String attributionNom()
	{
		Personnage personnageTiré = null;
		String nomTiré = null;
		/*
		 * Selectionne tout les noms de la base de données et les mettre dans une file ou pile 
		 * ??? Faut-il faire plusieurs files/piles 
		 * rand = radom (1, allName.size)
		 * Personnage newPerso = allName.get(rand);
		 * Enregistrer newPerso dans la base de données et la dépiler
		 * Tire aléatoirement un nom dans la pile/file (retirer le nom tiré) et l'enregistrer le personnage
		 * SE
		 * 
		 * une fois l'ajout fait, remove cet élément
		 */
		
		int i = 0;
		while (i < 7) // tant qu'on n'a pas trouvé un nom à chacun personnage (chaque personnage ayant un role doit avoir un nom)
		{
			Random rand = new Random();
			int nombreAleatoire = rand.nextInt(Personnage.allName.get(i).size());
			System.out.println("Nombre tiré: " + nombreAleatoire + "Intervalle : 0 à " + Personnage.allName.get(i).size());
			personnageTiré = Personnage.allName.get(i).get(nombreAleatoire);
			System.out.println("Personnage tiré : " + personnageTiré);
			nomTiré = personnageTiré.nomPersonnage;
			String roleTiré = personnageTiré.role;
			System.out.println("Nom : " + nomTiré + " a pour role : " + roleTiré);
			// Une fois les noms tirés, les enregistrés dans la base de données
			// INSERT INTO Personnage VALUES ('nomTiré', 'role', 'Non coupable', 'description') 
			i = i + 1;
		}
		
		return nomTiré;
	}
	
	/*
	 * Exemples de noms
	 * Nom, role
	 * Meghan     role
	 * Georges
	 * Peter
	 * James
	 * Carla
	 * Gomez
	 *      aaron.
    andy.
    cameron.
    charlie.
    clyde.
    connor.
    damon.
    darren.
	 */
	public String toString()
	{
		return "Nom : " + this.nomPersonnage + " Role: " + this.role;
	}
	
	public static void main (String[] args)
	{
		Personnage.remblissageFileDeNom();
		int i = 0;
		while (i < 7)
		{
			System.out.println(i + "\n");
			System.out.println(Personnage.allName.get(i));
			i = i + 1;
		}
		System.out.println(Personnage.allName);
		
		Personnage.attributionNom();
	}

}


