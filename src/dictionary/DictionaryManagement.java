package dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class DictionaryManagement {
	private Dictionary dictionary = new Dictionary();
	
	protected TreeMap<String,Word> allWord = dictionary.getDictionaryData();
	protected Set<String> keySet = allWord.keySet();
	
	public void insertFromCommandline() {
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Nhập từ: ");
        String newTarget = scan.nextLine();

        System.out.print("\nNhập giải thích: ");
        String newExplain = scan.nextLine();
      
        dictionary.addWord(new Word(newTarget, newExplain));
    }
	
	public void insertFromFile(String url){
		FileReader fileReader = null;
		BufferedReader bufferIn = null;
		try {
			fileReader = new FileReader(url);
			bufferIn = new BufferedReader(fileReader);
			
			String newTarget = "";
			String newExplain = "";		
			String input = "";
			
			while ((input = bufferIn.readLine()) != null){
				int temp = input.indexOf('\t');
				newTarget = input.substring(0, temp);
				newExplain = input.substring(temp + 1);
				dictionary.addWord(new Word(newTarget, newExplain));
			}

			bufferIn.close();
		    fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Không tìm thấy file!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferIn != null)
					bufferIn.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeToFile(String url){
		BufferedWriter bufferW = null;
		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(url);
			bufferW = new BufferedWriter(fileWriter);

			for (String key : keySet) {
				bufferW.write(dictionary.getWord(key).toString());
				bufferW.write("\n");
	        }
			
			System.out.println("Xuất dữ liệu ra file thành công");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferW != null)
					bufferW.close();
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Dictionary dictionaryFilter(String startKey) {
		Dictionary subDictionary = new Dictionary();
		
		boolean stop = false;
		for (String key : keySet) {
			if (key.startsWith(startKey.toLowerCase())) {
				subDictionary.addWord(dictionary.getWord(key));
				stop = true;
				continue;
			}
			
			if (stop) {
				break;
			}
		}
		
		return subDictionary;
	}
	
	//
	public void dictionaryDelWord(String key) {
        dictionary.removeWord(key);
    }
	
	public String dictionaryLookup(String key) {
		Word word = dictionary.getWord(key.toLowerCase());
		
		if (word == null) {
			return "";
		}
		
        return word.getWordExplain();
    }
	
	public void dictionaryAddWord(String wordTarget, String wordExplain) {
		dictionary.addWord(new Word(wordTarget.toLowerCase(), wordExplain));
    }
	
	public Dictionary getDictionary() {
		return dictionary;
	}
}
