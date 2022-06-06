package dictionary;

import java.util.TreeMap;

public class Dictionary {

	private TreeMap<String,Word> dictionaryData = new TreeMap<String,Word>();
	
    public void addWord(Word word) {
        dictionaryData.put(word.getWordTarget(), word);
    }
    
    public void removeWord(String key) {
        dictionaryData.remove(key);
    }

    public void replaceWord(String key, Word word) {
    	dictionaryData.replace(key, word);
    }
    
    public TreeMap<String,Word> getDictionaryData() { 
        return dictionaryData;
    }
    
    public Word getWord(String key) {
    	return dictionaryData.get(key);
    }
    
    public boolean isEmpty() { 
    	return dictionaryData.isEmpty();
    }
}

