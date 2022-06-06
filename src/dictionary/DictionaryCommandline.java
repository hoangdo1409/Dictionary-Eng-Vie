package dictionary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class DictionaryCommandline extends DictionaryManagement {
	Scanner scan = new Scanner(System.in);
	
	public void showAllWords() {
		System.out.printf("%-5s | %-20s | %s \n", "No", "English", "Vietnamses");
		int i = 0;
		for (String key : keySet) {
			String wordTarget = allWord.get(key).getWordTarget();
			String wordExplain = allWord.get(key).getWordExplain();
			System.out.printf("%-5d | %-20s | %s \n", ++i, wordTarget, wordExplain);
		}
	}

	public void dictionaryBasic(String url) {
		while (true) {
			System.out.println("\n1: Hiện danh sách từ. " 
					+ "\n2: Thêm hoặc sửa từ."
					+ "\n3: Để quay lại."
					+ "\nBất kì để dừng chương trình.");
			System.out.print("\nNhập lựa chọn: ");
			String temp = scan.nextLine();
			
			switch (temp.trim()) {
				case "1":
					showAllWords();
					dictionaryBasic(url);
					break;
				case "2":
					insertFromCommandline();
					dictionaryBasic(url);
					break;
				case "3":
					runByConsole(url);
					break;
				default:
					System.out.print("\nĐã dừng chương trình!");
					System.exit(0);
			}
		}
	}

	public void dictionaryAdvanced(String url) {
		insertFromFile(url);
		while (true) {
			System.out.println("\n1: Hiện danh sách từ. " 
					+ "\n2: Tìm kiếm." 
					+ "\n3: Thêm hoặc sửa từ."
					+ "\n4: Xóa từ." 
					+ "\n5: Lưu dữ liệu." 
					+ "\n6: Để quay lại."
					+ "\nBất kì để dừng chương trình");
			System.out.print("\nNhập lựa chọn: ");
			String temp = scan.nextLine();
			switch (temp.trim()) {
				case "1":
					showAllWords();
					break;
				case "2":
					dictionarySearcher();
					break;
				case "3":
					insertFromCommandline();
					break;
				case "4":
					System.out.print("Nhập từ cần xóa: ");
					String word = scan.nextLine();
					dictionaryDelWord(word);
					break;
				case "5":
					writeToFile(url);
					break;
				case "6":
					runByConsole(url);
					break;
				default:
					System.out.print("\nĐã dừng chương trình!");
					System.exit(0);
			}
		}
	}

	// *****
	public void dictionarySearcher() {
		System.out.print("Nhập từ cần tìm kiếm: ");
		String word = scan.nextLine();
		
		System.out.printf("%-5s | %-20s | %s \n", "No", "English", "Vietnamses");
		int i = 0;
		boolean stop = false;
		for (String key : keySet) {
			if (key.startsWith(word)) {
				System.out.printf("%-5d | %-20s | %s \n", ++i, key, allWord.get(key));
				stop= true;
				continue;
			}
			
			if (stop) {
				break;
			}
		}
		
		if (i == 0) {
			System.out.printf("%-5s | %-20s | %s \n", "...", "...", "...");
		}
	}

	public void runByConsole(String url) {
		System.out.println("\n1: Chế độ cơ bản." 
					+ "\n2: Chế độ nâng cao." 
					+ "\nBất kì để dừng chương trình.");
		System.out.print("\nNhập lựa chọn: ");

		String temp = scan.nextLine();
		switch (temp.trim()) {
			case "1":
				dictionaryBasic(url);
				break;
			case "2":
				dictionaryAdvanced(url);
				break;
			default:
				System.out.print("\nĐã dừng chương trình!");
				System.exit(0);
		}
	}
}
