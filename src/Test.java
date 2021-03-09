package superDetective;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Test {
	
	public static void main (String[] args)
	{
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			String filename = "musique.wav";
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
		
	}

}
