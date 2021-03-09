package superDetective;

import java.sql.*;

public class DAOJeu {
	
public Connection cnx;
	
	public boolean getConnect (String leDriver, String pseudoURL, String user, String mdp)
	{
		boolean testExecution = false;
		try 
		{
			Class.forName(leDriver).newInstance();
			this.cnx = DriverManager.getConnection(pseudoURL, user, mdp);
			testExecution = true;		
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Affiche la pile qui contient les traces fonctions qui ont conduit � l'erreur
			System.err.println("Probl�me au chargement du Driver ou dans la demande de connexion");
		}
		finally // On y passe quoi qu'il arrive. Utiliser quand on veut faire un truc en fin de fonction
		{
			return testExecution;
		}
		
	}
	
	public ResultSet rechercheExistencePseudo (String requete)
	{
		ResultSet resultats = null; // La ou on va stocker les r�sultats de la requete
		try 
		{
			Statement stmt = cnx.createStatement();
			resultats = stmt.executeQuery(requete);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.err.println("Probl�me au niveau de la requete");
			e.printStackTrace();
		}// Phrase qui va contenir la requete SQL. Les classes JDBC n'ont pas de new

		
		return resultats;
	}
	
	public ResultSet doSearch (String requete)
	{
		ResultSet resultats = null; // La ou on va stocker les r�sultats de la requete
		try 
		{
			Statement stmt = cnx.createStatement();
			resultats = stmt.executeQuery(requete);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.err.println("Problème au niveau de la requete");
			e.printStackTrace();
		}// Phrase qui va contenir la requete SQL. Les classes JDBC n'ont pas de new

		
		return resultats;
	}
	
	public void doUpdate (String requete)
	{
		int statutRequete; // La ou on va stocker le nombre d'enregistrement modifié par la requete
		try 
		{
			Statement stmt = cnx.createStatement();
			statutRequete = stmt.executeUpdate(requete);
			if (statutRequete == 0)
				System.out.println ("Aucun ajout effectué");
			else
				System.out.println (statutRequete + " ligne ajoutée");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.err.println("Problème au niveau de la requete");
			e.printStackTrace();
		}// Phrase qui va contenir la requete SQL. Les classes JDBC n'ont pas de new
	}	

}
