package experiment;
import java.util.ArrayList;

/**
 * Studylist creates a list of words that handles all the words in the "study list", i.e
 * chosen randomly
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */
public class StudyList{

	private ArrayList<Word> studyList;
	
	/** Creates a study list given a number representing the length
	 * @param length the length as an int
	 */
	public StudyList(int length) {
		studyList = new ArrayList<Word>(length);
	}//StudyList

	/** Adds the given word to the StudyList
	 * @param word
	 */
	public void add(Word word) {
		studyList.add(word);
	}//add

	/** Returns the index of the given word
	 * @param word
	 * @return index the index of the word
	 */
	public int indexOf(Word word) {
		return studyList.indexOf(word);
	}
	
	/** Returns the word at the given index
	 * @param index
	 * @return word the word at this index
	 */
	public Word get(int index) {
		return studyList.get(index);
	}//get
	
	/** Returns an int representing the size of the list
	 * @return size the size of the list
	 */
	public int size() {
		return studyList.size();
	}//size

}
