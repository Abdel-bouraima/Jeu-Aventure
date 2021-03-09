package superDetective;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GUI implements ActionListener
{
    private Partie partie;
    private Joueur player;
    private JFrame fenetre;
    private JTextField entree;
    private JTextArea texte;
    private JLabel image;

    public GUI(Partie j) {
    	partie = j;
        creerGUI();
    }
    
    public GUI (Joueur player)
    {
    	this.player = player;
    }

    public void afficher(String s) {
        texte.append(s);
        texte.setCaretPosition(texte.getDocument().getLength());
    }
    
    public void afficher() {
        afficher("\n");
    }
    
    public void afficherBtn (String nomBouton)
    {
    	JButton button = new JButton(nomBouton);
        fenetre.getContentPane().add(button); // Adds Button to content pane of frame
        fenetre.setVisible(true);
    }

   public void afficheImage( String nomImage) {
	   	URL imageURL = this.getClass().getClassLoader().getResource("images/" + nomImage);
	   	if( imageURL != null ) {
        	image.setIcon( new ImageIcon( imageURL ));
            fenetre.pack();
        }
   }
   
   public void afficheMenuPrincipal()
	{
		this.afficher("1 : Lancer une nouvelle partie");
		this.afficher("2: Reprendre une partie");
		this.afficher("3: Afficher score");
		this.afficher("4: Afficher historique de connexion");
		this.afficher("?: Obtenir de l'aide");
	}

    public void enable(boolean ok) {
        entree.setEditable(ok);
        if ( ! ok )
            entree.getCaret().setBlinkRate(0);
    }

    private void creerGUI() {
        fenetre = new JFrame("Super détective");
        
        entree = new JTextField(80);

        texte = new JTextArea();
        texte.setEditable(false);
        JScrollPane listScroller = new JScrollPane(texte);
        listScroller.setPreferredSize(new Dimension(300, 300));
        listScroller.setMinimumSize(new Dimension(200,200));

        JPanel panel = new JPanel();
        image = new JLabel();

        panel.setLayout(new BorderLayout());
        panel.add(image, BorderLayout.NORTH);
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(entree, BorderLayout.SOUTH);

        fenetre.getContentPane().add(panel, BorderLayout.CENTER);
        
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        entree.addActionListener(this);

        fenetre.pack();
        fenetre.setVisible(true);
        entree.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        executerCommande();
    }

    private void executerCommande() {
        String commandeLue = entree.getText();
        entree.setText("");
        partie.traiterCommande( commandeLue);
    }
    
    public String getDonneeSaisie() { // Alternative à Scanner puisque scanner s'éxécute dans la console
        String donneeSaisie = entree.getText();
        entree.setText("");
        return entree.getText();
    }
}
