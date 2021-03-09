package superDetective;

public class Lancer {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		// Une seule instruction qui va lancer le jeu
		Partie partie = new Partie();
		GUI gui = new GUI( partie);
		
		partie.setGUI( gui);
		
		Audio son = new Audio();
		boolean continueMusic = false;
		while (continueMusic == false)
		{
			son.start();
		}
		
		
	}

}
