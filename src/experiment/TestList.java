package experiment;
import java.util.*;

/**
 * TestList contains an array of Words that can be manipulated
 * Assumes the word list is of a size divisible by 4
 * 
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */
public class TestList {

	private ArrayList<Word> afoils;
	private ArrayList<Word> atargets;
	private ArrayList<Word> bfoils;
	private ArrayList<Word> btargets;
	                                    
	/**
	 * Creates a testList from the given length
	 * @param length
	 */
	public TestList(int length) {
		int newlength = length/4;
		afoils = new ArrayList<Word>(newlength);
		bfoils = new ArrayList<Word>(newlength);
		atargets = new ArrayList<Word>(newlength);
		btargets = new ArrayList<Word>(newlength);
	}//testlist
	
	/** Returns a word with the given attributes
	 * @param category
	 * @param wordType
	 * @return word a word associated with this cat and wordtype
	 */
	public Word get(char category, char wordType) {
		Word toReturn = new Word("couldn't find one at index 0");
		
		if(category == 'a' && wordType == 'f')
			toReturn = afoils.remove(0);
		if(category == 'b' && wordType == 't')
			toReturn = btargets.remove(0);
		if(category == 'a' && wordType == 't')
			toReturn = atargets.remove(0);
		if(category == 'b' && wordType == 'f')
			toReturn = bfoils.remove(0);

		return toReturn;
	}//get(cat, wordtype, index)
	
	/**
	 * Adds this word to the testlist
	 * @param word
	 */
	public void add(Word word){
		char category = word.getCategory();
		char wordType = word.getWordType();

		if(category == 'a') { //cat is a
			if(wordType == 'f') //is a foil
				afoils.add(word); 
			else
				atargets.add(word);
		} else { //cat is b
			if(wordType == 'f') //is a foil
				bfoils.add(word);
			else
				btargets.add(word);
		}
	}//add		

	/** Gets the given word from the test list
	 * @param word
	 * @return Word the word in the testList
	 */
	public Word get(Word word) {
		char category = word.getCategory();
		char wordType = word.getWordType();
		Word toReturn = null;
		if(category == 'a') { //cat is a
			if(wordType == 'f') //is a foil
				toReturn = afoils.get(afoils.indexOf(word)); 
			else //is a target
				toReturn = atargets.get(atargets.indexOf(word)); 
		} else { //cat is b
			if(wordType == 'f') //is a foil
				toReturn = bfoils.get(bfoils.indexOf(word));
			else //is a target
				toReturn = btargets.get(btargets.indexOf(word)); 
		}
		return toReturn;
	}//get(word)
		
	/** Returns the size of this testlist
	 * @return size the size of this testList as an int
	 */
	public int size() {
		return afoils.size() *4;
	}//size()
	
	/**Returns true of this test list is empty
	 * @return isEmpty a boolean indicating if there are words left in this testlist
	 */
	public boolean isEmpty() {
		if(afoils.size() == 0 &&
		   bfoils.size() == 0 &&
		   atargets.size() == 0 &&
		   afoils.size() == 0)
			return true;
		return false;
	}
	
	/** Returns the index of this word in the test list
	 * @param word
	 * @return index the index of this word in the testList
	 */
	public int indexOf(Word word) {
		char category = word.getCategory();
		char wordType = word.getWordType();
		int toReturn;
		if(category == 'a') { //cat is a
			if(wordType == 'f') //is a foil
				toReturn =afoils.indexOf(word); 
			else //is a target
				toReturn =atargets.indexOf(word); 
		} else { //cat is b
			if(wordType == 'f') //is a foil
				toReturn =bfoils.indexOf(word);
			else //is a target
				toReturn =btargets.indexOf(word); 
		}//indexOf(word)
		return toReturn;
	}

	/** Returns an array representation of the category A foils in this TestList
	 * @return aFoils 
	 */
	public ArrayList<Word> getAfoils() {
		return afoils;
	}

	/**Returns an array representation of the category A targets in this TestList
	 * @return aTargets
	 */
	public ArrayList<Word> getAtargets() {
		return atargets;
	}

	/**Returns an array representation of the category B foils in this TestList
	 * @return bFoils
	 */
	public ArrayList<Word> getBfoils() {
		return bfoils;
	}

	/**Returns an array representation of the category B targets in this TestList
	 * @return bTargets
	 */
	public ArrayList<Word> getBtargets() {
		return btargets;
	}
}
