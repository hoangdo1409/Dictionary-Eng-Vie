package dictionary;

public class Standardize {

	public static String replaceSpace(String str) {
		return str.trim().replaceAll("[\t ]"," ");
	}

	public static String capitalize(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	public static boolean isalnum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				continue;
			}
			
			if (str.charAt(i) < 'a' && str.charAt(i) > 'Z' || str.charAt(i) < 'A' || str.charAt(i) > 'z') {
				return false;
			}
		}
		return true;
	}
}
