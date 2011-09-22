package experiment;
import java.util.*;

/**
 * 
 */

/** ListSorter takes a  Word object for each word,
 * randomly deciding between tagging as either a target or foil.
 * It then randomly assigns each target to a study list and 
 * test list, and then each foil to that same testlist.
 * Assumes the list length is divisible by 2 and by 4.
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */
public class ListSorter {

	private Experiment experiment;
	private int numberOfTotalWords = 0;
	private TestList testList;
	private StudyList studyList;
	private ArrayList<Word> listOfWords;
	
	/**
	 * Creates a listSorter given an experiment
	 * @param experiment
	 */
	public ListSorter(Experiment experiment) {
		listOfWords = new ArrayList<Word>();
		this.setExperiment(experiment);
	}//ListSorter
	
	/**
	 * Sets the total number of words in this listSorter
	 * @param length an int representing the number of words that will be added
	 */
	public void setLength(int length) {
		numberOfTotalWords = length;
		testList = new TestList(length);
		studyList = new StudyList(length/2);
	}
	
	/**
	 * Chooses the word type, either target or foil, for the given word and index
	 * @param word a word to set the wordtype for
	 * @param index an int representing the index
	 */
	public void chooseWordType(Word word, int index) {
		char ans;
		if((index < 75) || (index > 149 && index < 225))
			ans = 't';
		else 
			ans = 'f';
		word.setWordType(ans);
	}//chooseWordType
	
	/**
	 * Chooses a category for this word, either 'a' or 'b'
	 * @param word
	 * @param index
	 */
	public void chooseCategory(Word word, int index) {
		char ans;
		if (index < 150)
			ans = 'a';
		else
			ans = 'b';
		word.setCategory(ans);
	}//choseCategory
	
	/**
	 * Adds this word to the listsorter
	 * @param word the word to add
	 * @param index the index it came from
	 */
	public void add(Word word, int index) {
		chooseWordType(word, index);
		chooseCategory(word, index);
		listOfWords.add(word);
	} //add
	
	/**
	 * Sorts the words in the listSorter into two lists, testList and studyList
	 * the testList contains foils and targets, while the studyList contains just targets
	 */
	public void sort() {
		Random gen = new Random();
		int size = listOfWords.size();
		int index;
		Word word;
		char wordType = 'z';
		do {
			index = gen.nextInt(size); //get a random index
			word = listOfWords.remove(index); //get & remove a word at that index
			//also shifts all items to the left!!!!
			size--; //reduces the size variable by one to accommodate the now smaller list
			wordType = word.getWordType();
			if(wordType == 'f') { //is a foil
				testList.add(word);
			} else { //is a target
				studyList.add(word);
				testList.add(word);
			}
		} while(size != 0); //when size hits 0, we are done sorting
	}//sort
		
	
	/**
	 * @return the studyList associated with this listSorter
	 */
	public StudyList getStudyList() {
		return studyList;
	}//getStudyList
	
	/**
	 * @return the testList
	 */
	public TestList getTestList() {
		return testList;
	}//getTestList
	
	/**
	 * @return the total number of words in this sorter
	 */
	public int getNumberOfTotalWords() {
		return numberOfTotalWords - 1;
	}

	/**
	 * @param experiment the experiment to set
	 */
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	/**
	 * @return the experiment
	 */
	public Experiment getExperiment() {
		return experiment;
	}

}
