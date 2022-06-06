package application;

import apiTranslator.Languages;
import apiTranslator.Translator;
import javafx.concurrent.Task;

public class APITrans extends Task<String> {

	String chuoidich;
	Languages langTo;
	Languages langFrom;
	
	public APITrans(String s, Languages langTo, Languages langFrom) {
		chuoidich = s;
		this.langTo = langTo;
		this.langFrom = langFrom;
	}
	
    protected String call() throws Exception {
    	String text = "";
		text = Translator.translate(chuoidich, 
									langTo, 
									langFrom);
		return text;
    }
}
