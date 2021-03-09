package superDetective;

import java.sql.*;

public class MetierJeu {
	
	DAOJeu monDao;
	
	public void init (String driverName, String pseudoURL, String user, String mdp)
	{
		// Création d'une instance de DAO
		monDao = new DAOJeu();
		// demande de connexion
		if (monDao.getConnect(driverName, pseudoURL, user, mdp) == true)
			System.out.println ("DAO initialisé");
		else
			System.err.println("Un probléme a été rencontré lors de l'initialisation");
	}
	
	public void listeJoueur()
	{
		
	}
	
	public int rechercheJoueur(String pseudoSaisi)
	{
		int nbJoueurTrouve = 0;
		String requeteSQL = "SELECT * FROM JOUEUR WHERE pseudo = '" + pseudoSaisi + " '";
		ResultSet  results = monDao.doSearch(requeteSQL);
		
		if (results == null)
		{
			System.out.println("Aucun joueur enregistré");
			return nbJoueurTrouve;
		}
		else
		{
			try 
			{
				while (results.next())
				{
					nbJoueurTrouve = nbJoueurTrouve + 1;
				}
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				return nbJoueurTrouve;
			}
		}
	}
	
	public void ajoutJoueur(String pseudo, String mdp, int progression, int score, String dateActuelle)
	{
		String requeteSQL = "INSERT INTO joueur VALUES ( '" + pseudo + "', '" + mdp + "', " + 0 + "," + 0 + ", '" + dateActuelle + "')";
		//System.out.println(requeteSQL);
		monDao.doUpdate(requeteSQL);
	}
	
	public boolean verificationIdentifiant(String pseudoSaisi, String mdpSaisi)
	{
		boolean statutVerification = false;
		
		String requeteSQL = "SELECT motDePasse FROM JOUEUR WHERE pseudo = '" + pseudoSaisi + "'";
		System.out.println(requeteSQL);
		ResultSet resultat = monDao.doSearch(requeteSQL);
		if (resultat == null)
			//System.out.println("Table vide ou problème dans la requete");
			statutVerification = false;
		
		else
		{
			try {
				if (resultat.next() == false)
				{
					System.out.println("Table vide");
				}
				else
				{
					System.out.println("Résultat :" + resultat.getString("motDePasse"));
					System.out.println("Argument :" + mdpSaisi);
					if (resultat.getString("motDePasse").equals(mdpSaisi))
					{
						System.out.println("OK ");
						statutVerification = true;
					}
					else
						System.out.println("Else");
					
					resultat.afterLast();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return statutVerification;
		
	}
	
	public void afficheTopScore()
	{
		String requeteSQL = "SELECT TOP 5 FROM joueur";
		
		// "SELECT * FROM joueur ORDER BY Score DESC LIMIT 5";
		
		ResultSet  results = monDao.doSearch(requeteSQL);
		
		// Parcourir le resultSet et afficher les résultats
		
		// Tester que le resultat de la requete n'est pas vide (null)
		
		if (results == null)
			System.out.println("Problème dans la requete");
		else
		{
			try {
				if (results.next() == false)
					System.out.println("Table vide");
				else
				{
					System.out.println("5 Meilleurs scores ");
					while (results.next())
					{
						System.out.println(results.getString("pseudo") + " : \t" + results.getInt("Score"));
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String[] recupereInfoJoueur(String pseudoSaisi)
	{
		String[] infoJoueur = new String[6];

		String requeteSQL = "SELECT * FROM JOUEUR WHERE pseudo = '" + pseudoSaisi + " '";
		ResultSet  results = monDao.doSearch(requeteSQL);
		
		if (results == null)
		{
			System.out.println("Problème avec la requete");
		}
		else
		{
			try 
			{
				if (results.next() == false)
				{
					System.out.println("Aucune donnée dans la table");
				}
				else
				{
					infoJoueur[0] = results.getString("pseudo");
					infoJoueur[1] = results.getString("motDePasse");
					infoJoueur[2] = results.getString("progressionJoueur");   //(String) results.getInt("progressionJoueur")
					infoJoueur[3] = results.getString("Score");
					infoJoueur[4] = results.getString("DateInscription");
					infoJoueur[5] = results.getString("TempsDeJeu");
					results.afterLast();
				}
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return infoJoueur;
	}
	
	public void modifInfosJoueur(String pseudo, String mdp)
	{
		int nbTuplesModifie = 0;
		
		String requeteSQL = "UPDATE joueur SET Pseudo = '" + pseudo + "', MotDePasse = '" + mdp + "' WHERE Pseudo = '" + pseudo +" ')";
		System.out.println(requeteSQL);
		monDao.doUpdate(requeteSQL);
			
	}
	
	public ResultSet renvoieNomPersonnage(String role)
	{
		String requeteSQL = "SELECT * FROM NomPersonnage WHERE role = '" + role + "'";
		ResultSet resultats = monDao.doSearch(requeteSQL);
		
		if (resultats == null)
		{
			System.out.println("Problème avec la requete");
		}
		else
		{
			try 
			{
				if (resultats.next() == false)
				{
					System.out.println("Aucune donnée dans la table");
				}
				else
				{
					return resultats;
				}
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return resultats;
	}
	

}
