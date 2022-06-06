package dictionary;

public class Word {
    private String wordTarget;
    private String wordExplain;

    public Word() {
    	wordTarget = "";
    	wordExplain = "";
    }
    
    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }
    
    public Word(Word word){
        this.wordTarget = word.getWordTarget();
        this.wordExplain = word.getWordExplain();
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }
    
    @Override
    public String toString() {
        return wordTarget + "\t" + wordExplain;
    }
}
