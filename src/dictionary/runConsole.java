package dictionary;

import java.util.Scanner;

public class runConsole {
	private static final String urlFile = "D:\\CodeJava\\DictionaryProject\\src\\data\\Test.txt";

	public static void main(String[] args) {
		new DictionaryCommandline().runByConsole(urlFile);
	}
}
