package experiment;
import java.util.Random;

/**
 * A trial is one trial during the test phase. 
 * Trials handle the recording of certain important parts of the trial such as
 * which words were displayed, the response time, the subject's answer,
 * the category of the words, and the trial number.
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */
public class Trial {
	
	private ListSorter sorter;
	private TestList testList;
	private StudyList studyList;
	private int trialNumber;
	private String subject;
	private long beginningMs;
	private long responseTime;
	private Word[] trialWords;
	private boolean trialFirst = false;
	private String userAnswer;
	private String category;
	
	/** Creates a new Trial
	 * @param sorter
	 * @param trial
	 */
	public Trial(ListSorter sorter, int trial) {
		this.setSorter(sorter);
		this.testList = sorter.getTestList();
		this.setStudyList(sorter.getStudyList());
		this.trialNumber = trial;
		startTrial();
	}//Trial
	
	/**Sets the subject to the given string
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}//setSubject
	
	/** Returns the subject
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}//getSubject

	/**
	 * Checks the length of the category in the testList to see if there are words left
	 * @param category
	 * @return true if the given category has words left
	 */
	public boolean hasWordsLeft(char category) {
		if(category == 'a') 
			if (testList.getAfoils().size() == 0)
				return false;
		if(category == 'b')
			if(testList.getBfoils().size() == 0)
				return false;
		return true;
	}
	
	/**
	 * Decides upon a trial which consists of two words from the same category
	 * these words are selected randomly and displayed in random order
	 */
	public void startTrial() {
		Random gen = new Random();
		boolean a;
		if(!sorter.getExperiment().isInterweaved()) //blocked by category
			a = (getTrialNumber() < sorter.getNumberOfTotalWords()/4);
		else {//Interweaved
			a = gen.nextBoolean();
			if(a){ //randomly chose a
				if(sorter.getTestList().getAfoils().size() < 1)
					a = false; //if the cat A test list has no more items, get b
			}else //randomly chose b
				if(sorter.getTestList().getBfoils().size() < 1)
					a = true; //if the cat B test list has no more items, get a
		}
		
		boolean t = gen.nextBoolean(); //find out if trial goes first or not
		char category = 'b'; //initialize as 'b' category
		this.category = "b";
		if(t) 
			setTrialFirst(true); //randomly set to either set the trial first or second
		if(a) { //a category
			category = 'a';
			this.category = "a";
		}
		setTrialWords(category);
	}//startTrial()
	
	/** Sets the trial words to the given category
	 * @param category
	 */
	private void setTrialWords(char category) {
		Word[] ans = new Word[2];
		if(isTrialFirst()) {
			ans[0] = testList.get(category,'t'); //trial first
			ans[1] = testList.get(category,'f');
		}
		else{
			ans[0] = testList.get(category,'f'); //foil first
			ans[1] = testList.get(category,'t');
		}
		this.trialWords = ans;
	}


	/** Sets the first word as the trial word
	 * @param true if the left word is a trial word
	 */
	private void setTrialFirst(boolean b) {
		trialFirst = b;
	}
	
	/** Returns a boolean indicating if the left word was a trial word
	 * @return true if the left word is a trial word
	 */
	private boolean isTrialFirst() {
		return trialFirst;
	}

	/**Sets the ListSorter to this trial
	 * @param sorter
	 */
	public void setSorter(ListSorter sorter) {
		this.sorter = sorter;
	}//setSorter

	/** Returns the ListSorter associated with this trial
	 * @return the ListSorter
	 */
	public ListSorter getSorter() {
		return sorter;
	}//getSorter

	/** Sets the trial number for this trial
	 * @param trialNumber
	 */
	public void setTrialNumber(int trialNumber) {
		this.trialNumber = trialNumber;
	}//setTrialnumber

	/** Returns the trial number as an int for this trial
	 * @return trialNumber an int indicating the trial number
	 */
	public int getTrialNumber() {
		return trialNumber;
	}//getTrialNumber
	
	/**
	 *  Starts the timer for this trial that will be used to calculate
	 *  the response time of the subject
	 */
	public void startTimer() {
		beginningMs = System.currentTimeMillis();
	}//startTimer
	
	/**
	 * Ends the timer for this trial and records the response time
	 * for this subject
	 */
	public void endTimer() {
		setResponseTime(System.currentTimeMillis() - beginningMs);
	}//endTimer
	
	/** Records the answer given by the subject
	 * @param userAnswer a character either 's' or 'k'
	 */
	public void recordAnswer(char userAnswer) {
		endTimer();
		if(userAnswer == 's')
			setUserAnswer("LEFT");
		else
			setUserAnswer("RIGHT");
	}
	
	/** Returns the target word of this trial
	 * @return the target word
	 */
	public Word getTargetWord() {
		Word targetWord;
		if(isTrialFirst())
			targetWord = trialWords[0];
		else
			targetWord = trialWords[1];
		return targetWord;
	}
	
	/** Returns the foil word of this trial
	 * @return the foil word
	 */
	public Word getFoilWord() {
		Word foilWord;
		if(isTrialFirst())
			foilWord = trialWords[1];
		else
			foilWord = trialWords[0];
		return foilWord;
	}
	
	/** Returns an array representation of the trial words
	 * @return an array containing the trial words
	 */
	public Word[] getTrialWords() {
		return trialWords;
	}
	
	/** Returns the delay as an int. 
	 * The delay is (where the target word appeared in the study phase) -
	 *  (the current trial number)
	 * @return delay the of this trial
	 */
	public int getDelay() {
		Word targetWord = getTargetWord();
		return targetWord.getStudyPhaseNumber() - getTrialNumber();
	}

	/** Sets the user's answer to the given answer, (the location of the answer)
	 * @param userAnswer the userAnswer to set
	 */
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	/** Returns the location of the user's answer
	 * @return location the location of the user's answer
	 */
	public String getLoc() {
		return userAnswer;
	}
	
	/** Get the word that the user answered as a string
	 * @return repsonseWord the word that the user decided upon
	 */
	public String getResponse() {
		if (userAnswer.equals("LEFT")) {
			return getTrialWords()[0].getWord();
		}
		else return getTrialWords()[1].getWord();
	}

	/** Sets the response time 
	 * @param responseTime the responseTime to set
	 */
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	/** Returns the response time for this trial
	 * @return the responseTime
	 */
	public int getResponseTime() {
		return new Long(responseTime).intValue();
	}

	/** Sets the studyList for this trial
	 * @param studyList the studyList to set
	 */
	public void setStudyList(StudyList studyList) {
		this.studyList = studyList;
	}

	/** Returns the studyList associated with this trial
	 * @return the studyList
	 */
	public StudyList getStudyList() {
		return studyList;
	}
	
	/** Returns a string representing the category of the words
	 * @return category the category of the words in this trial
	 */
	public String getCategoryAsString() {
		String c = this.category;
		if(c.equals("a"))
			return sorter.getExperiment().getCatA();
		else
			return sorter.getExperiment().getCatB();
	}
	
	/** Returns a string indicating if the subject was correct or not in 
	 * his or her answer
	 * @return correct "1" if the subject was correct, "0" if not
	 */
	public String correct() {
		if(getTargetWord().getWord().equals(getResponse())) {
			return "1";
		}
		return "0";
	}

}
