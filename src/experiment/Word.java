package experiment;

/**
 * This class Word handles the classification and organization of specific words.
 * Each word has a word type, either target or foil, and a category, either a or b, 
 * as well as a String that represents the word as it should appear to the user
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */
public class Word {
	
	private String word;
	private char category;
	private char wordType;
	private int studyPhaseNumber;
	
	/** Creates a word with the given attributes
	 * @param word a String representing a word
	 * @param category
	 * @param wordType
	 * 
	 */
	public Word(String word, char category, char wordType) {
		this.word = word;
		this.category = category;
		this.wordType = wordType;
	}//word(word, cat, wordtype)
	
	/**
	 * Creates a word with the given attributes
	 * @param word
	 * @param category
	 * 
	 */
	public Word(String word, char category) {
		this.word = word;
		this.category = category;
	}//word(word, cat)
	
	/** Creates a word with the given attribute
	 * @param word
	 * 
	 */
	public Word(String word) {
		this.word = word;
	}//word(word)
	
	/** Returns the category of this word, either 'a' or 'b'
	 * @return category the category of this word as a character
	 */
	public char getCategory() {
		return category;
	}//getcategory
	
	/** Sets the WordType of this word, either 't' or 'f' (target or foil)
	 * @param wordType the type of word this is
	 */
	public void setWordType(char wordType) {
		this.wordType = wordType;
	}//setwordtype
	
	/** Sets the category of this word, either 'a' or 'b'
	 * @param category the category of this word as a character
	 */
	public void setCategory(char category) {
		this.category = category;
	}//setcategory
	
	/** Returns the type of this word, either a foil or target, as a character, 'f' or 't'
	 * @return wordType the type of word this is
	 */
	public char getWordType() {
		return wordType;
	}//getwordtype
	
	/** Returns the word as a string
	 * @return the word as a string
	 */
	public String getWord() {
		return word;
	}//getword
	
	/** Prints this word as a string
	 * Prints this word as a string
	 */
	public void printWord() {
		System.out.print(getWord());
	}//printword
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getWord();
	}//printword
	
	/** Returns true if the given word equals this word
	 * @param word
	 * @return equals 
	 */
	public boolean equals(Word word){
		return this.word.equals(word.getWord());
	}//equals

	/** Sets the study phase this word appeared in
	 * @param studyPhaseNumber
	 */
	public void setStudyPhaseNumber(int studyPhaseNumber) {
		this.studyPhaseNumber = studyPhaseNumber;
	}//setStudyPhaseNumber

	/** Gets the study phase this word appread in
	 * @return studyPhasENumber the number as it appears in study trials
	 */
	public int getStudyPhaseNumber() {
		return studyPhaseNumber;
	}//getstudyPhaseNumber
	
	
}//word
