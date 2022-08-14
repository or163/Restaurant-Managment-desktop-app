package Audio;

import java.io.File;

import javafx.scene.media.AudioClip;

public class sounds {
	
	public static void clickSound()
	{
		String path = new File("src/Audio/MouseClickShort2.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	public static void bellSound()
	{
		String path = new File("src/Audio/Big Bell.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	public static void flashBackSound()
	{
		String path = new File("src/Audio/Flashback.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	public static void byebyeSound()
	{
		String path = new File("src/Audio/byebye.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	public static void welcomeMSound()
	{
		String path = new File("src/Audio/WelcomeManager.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	public static void welcomeSound()
	{
		String path = new File("src/Audio/welcome.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	public static void bonapatiteSound()
	{
		String path = new File("src/Audio/bonapatite.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	public static void cashSound()
	{
		String path = new File("src/Audio/Cash Register.mp3").getAbsolutePath();
		AudioClip click = new AudioClip(new File(path).toURI().toString());
		click.play();
	}
	
	static String path = new File("src/Audio/Walt's American Restaurant complete music loop (1_4).mp3").getAbsolutePath();
	static AudioClip bgs = new AudioClip(new File(path).toURI().toString());
	public static void backgroundMusic()
	{
		bgs.setVolume(0.2);
		if(bgs.isPlaying())
			bgs.stop();
		else {
			bgs.play();
			bgs.setCycleCount(100);
		}
	}
	
	public static void backgroundMusicMute()
	{
		bgs.stop();
	}
}
	

