package speech;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
	public static void speech(String word) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		Voice voice;
		voice = VoiceManager.getInstance().getVoice("kevin");
		
		if (voice != null) {
	        voice.allocate();//Allocating Voice
	    }

	    try {
	        voice.setRate(190);//Setting the rate of the voice
	        voice.setPitch(150);//Setting the Pitch of the voice
	        voice.setVolume(3);//Setting the volume of the voice
	        voice.speak(word);// Calling speak() method
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}
}
