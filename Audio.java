package superDetective;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import  java.io.*;
import javax.sound.sampled.*;

public class Audio extends Thread {
	
	// Est-il possible de faire une pile/file de string. ajouter le fichier et l'exécuter puis le rajouter. Tant que la file/pile n'est pas vide, on fait un run 
	
	final static String nomFichier=  "ressources/Son/musique.wav";
	
	AudioInputStream audioInputStream = null;
    SourceDataLine line;
     
    public void run(){
        File fichier = new File(nomFichier);
        try {
        AudioFileFormat format = AudioSystem.getAudioFileFormat(fichier);
        } catch (UnsupportedAudioFileException | IOException e1) {
            e1.printStackTrace();
        } 
         
        try {
            audioInputStream = AudioSystem.getAudioInputStream(fichier);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
         
         AudioFormat audioFormat = audioInputStream.getFormat();
         DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
          
         try {
             line = (SourceDataLine) AudioSystem.getLine(info);
                        
             } catch (LineUnavailableException e) {
               e.printStackTrace();
               return;
             }
          
        try {
                line.open(audioFormat);
        } catch (LineUnavailableException e) {
                    e.printStackTrace();
                    return;
        }
        line.start();
        try {
            byte bytes[] = new byte[1024];
            int bytesRead=0;
            while ((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
                 line.write(bytes, 0, bytesRead);
                }
        } catch (IOException io) {
            io.printStackTrace();
            return;
        }
    }
	/*public static void main (String[] args)
	{
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			String filename = "ressources/Son/Kalimba.mp3";
			try {
				clip.open(AudioSystem.getAudioInputStream(new URL(filename)));
			} catch (IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/


}
