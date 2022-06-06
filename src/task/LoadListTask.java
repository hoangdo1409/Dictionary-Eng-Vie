package task;

import java.util.Set;
import java.util.TreeMap;

import dictionary.Dictionary;
import dictionary.Standardize;
import dictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;


public class LoadListTask extends Task<ObservableList<String>>{
	private Dictionary dictionary;
	private boolean getAll;
	
	public LoadListTask(Dictionary dictionary, boolean getAll) {
		this.dictionary = dictionary;
		this.getAll = getAll;
	}
	
	@Override
	protected ObservableList<String> call() throws Exception {
			
		TreeMap<String,Word> allWords = dictionary.getDictionaryData();
		Set<String> keySet = allWords.keySet();

		ObservableList<String> empData = FXCollections.observableArrayList();
		
		for (String key : keySet) {
			if (!getAll) {
				empData.add(key);
			} else {
				empData.add(allWords.get(key).toString());
			}
		}
		
		return empData;
	}

}
