package experiment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import experiment.TrialHandler;

import view.*;

/**
 * Experiment<br><br>
 * Created for Tarun Gangwani in Fall, 2010 from his specifications and pseudocode<br><br>
 * 
 * Created for a research project that tests subjects on their memorization ability<br><br>
 * 
 * A dialog box will appear that asks for the file name that the list of words 
 * is located. This list of words should be in the same directory as the runnable jar file. 
 * You can enter multiple word lists separated by commas.
 * <br><br>
 * 
 * <ul>
 * 	<li> Provides instructions for the subject, must click in the middle and hit the spacebar to continue</li>
 *  <li> Reads in the given file name, a text file filled with words separated by 
 * 	     new lines, and id's them. The first half and second half are sorted into categories and 
 * 		 half of each are labeled "targets" and the other half labeled "foils"</li>
 * 	<li> Shows words from the study list one by one from the "targets" list
 *       for 1000ms each, and 150ms of a blank screen.</li>
 *  <li> Break; shows instructions for the Test Phase, must click in the middle and hit the spacebar to continue. </li>
 * 	<li> Performs a series of trials until finished, recording
 *       the user's answer for each trial along with other important information such as response time. </li>
 * 	<li> If the proctor had checked "interweaved" in the options panel,
 *    	 the individual trials are randomized rather than blocked by category.</li>
 *  <li> At the end of the test phase, the user can press 'spacebar' which 
 * 		 generates a text file in comma delimited format which
 * 		 compiles key information recorded during the study</li>
 *  </ul>
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */
public class Experiment {

	private ListSorter sorter;
	private String subject;
	private String testLetter;
	private Scanner files;
	private TrialHandler trials;
	private JFrame GUI;
	private Scanner catAsc;
	private Scanner catBsc;
	private String fileName;

	private String catA;

	private String catB;
	
	private char catAchar;
	private char catBchar;
	private boolean debugMode = false;
	private boolean interweaved = false;
	
	public boolean isInterweaved() {
		return interweaved;
	}

	/**
	 * Creates an experiment
	 */
	public Experiment() {
		setTrialHandler(new TrialHandler(this));
	}

	/** Returns the ListSorter for this experiment
	 * @return the list sorter associated with this experiment
	 */
	public ListSorter getSorter() {
		return sorter;
	}

	/** Sets the sorter to this experiment
	 * @param sorter to set to this experiment
	 */
	public void setSorter(ListSorter sorter) {
		this.sorter = sorter;
	}
	
	/**
	 * readWord creates a new word given a string and adds it to the ListSorter
	 * @param word
	 * @param index
	 */
	public void readWord(String word, int index) {
		Word toAdd = new Word(word); //create a new word from the string
		sorter.add(toAdd, index); //add the word to the sorter
	}//readWord
	
	/**
	 * Reads in a file of words, assuming they are separated by a newline
	 * @param fileName
	 */
	public void readFile(String fileName) {
		int index = 0; //to keep track of categories and word types
			try {
			    BufferedReader in = new BufferedReader(new FileReader(fileName));
			    String str;
			    while ((str = in.readLine()) != null) {
			    	//while the list of words still has a next line
			    	//read that next line into a string
			        readWord(str, index);//read the word into the sorter
			        index++; //+1 on index because we are going to the next word
			    }
			    in.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("File not found.");
			}
		sorter.setLength(index + 1);
	}//readFile
	
	/**
	 * clearScreen prints 30 new lines to the cmd line
	 */
	public static void clearScreen() {
		for(int i = 0; i < 50; i++) {
			System.out.println();
		}
	}//clearScreen
	
    
    /**
     * Delays or pauses the program given certain milliseconds
     * @param ms milliseconds to delay
     */
    public static void setDelay(int ms) {
	try 
	    {
		Thread.sleep(ms); // do nothing for ms 
	    } 
	catch(InterruptedException e)
	    {
		e.printStackTrace();
	    }

    }
    
	/** Begins the experiment
	 * @param args
	 */
	public static void main(String[] args) {
		Experiment experiment = new Experiment();
		experiment.setSorter(new ListSorter(experiment)); //create a new sorter 
		new OptionsGUI(experiment);
	}//main

	/**
	 * makes a GUI
	 */
	public void makeGUI() {
		readFile(getFileName()); //read the words in the file
		getSorter().sort(); //sort the words (randomly) into study and test lists
		GUI = new GUI(this);
	}
	
	/**
	 * Stops the experiment and prints the trials into a file
	 */
	public void stopExperiment() {
		GUI.dispose();
		getTrialHandler().run();
		if(files.hasNext())
			runMultipleTests();
	}
	
	/** Returns the subject associated with this experiment
	 * @return the subject associated with this experiment
	 */
	public String getSubject() {
		return subject;
	}

	/** Sets the testLetter to a given String
	 * @param testLetter the testLetter to set to this experiment
	 */
	public void setTestLetter(String testLetter) {
		this.testLetter = testLetter;
	}

	/** Returns the testLetter as a String
	 * @return testLetter the testLetter associated with this experiment
	 */
	public String getTestLetter() {
		return testLetter;
	}

	/** Sets the trialHandler to the given one
	 * @param trials the trials to set
	 */
	public void setTrialHandler(TrialHandler trials) {
		this.trials = trials;
	}

	/** Returns the trialHandler for this experiment
	 * @return the trial handler associated with this experiment
	 */
	public TrialHandler getTrialHandler() {
		return trials;
	}

	/** Sets the filename for the word list
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/** Returns the filename for the word list
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/** Sets the subject given a string
	 * @param subject a String representing the subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/** Sets the name for the category B, given to the program
	 * by the administrator of the study.
	 * @param catB the catB to set
	 */
	public void setCatB(String catB) {
		this.catB = catB;
		this.catBchar = catB.charAt(0);
	}

	/** Returns the category B identifier as a String
	 * @return catB the identifier for category B
	 */
	public String getCatB() {
		return catB;
	}

	/** Sets the name for the category A, given to the program
	 * by the administrator of the study.
	 * @param catA the catA to set
	 */
	public void setCatA(String catA) {
		this.catA = catA;
		this.catAchar = catA.charAt(0);
	}

	/** Returns the Category B identifier as a String
	 * @return catA the category A identifier as a String
	 */
	public String getCatA() {
		return catA;
	}


	/** Returns the category A identifier as a char
	 * @return catAchar the identifier as a char
	 */
	public char getCatAchar() {
		return catAchar;
	}


	/**Returns the category B identifier as a char
	 * @return catBchar the identifier as a char
	 */
	public char getCatBchar() {
		return catBchar;
	}

	/** Sets the debugMode to the given boolean,
	 * true if the experiment is in debug mode
	 * @param debugMode a boolean that indicates if the experiment in debug mode or not
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode  = debugMode;
	}
	
	/**Returns true if the experiment is in debug mode
	 * @return debugMode a boolean indicating if the experiment is in debug mode
	 */
	public boolean isDebugMode() {
		return debugMode;
	}

	/** Starts the experiment
	 * @param subj the subject taking this experiment
	 * @param files the file names or file name as a scanner
	 * @param testL the test letter for this experiment
	 * @param catAsc category A names
	 * @param catBsc category B names
	 * @param multiple if this is a multiple test or not
	 */
	public void startExperiment(String subj, Scanner files, String testL,
			Scanner catAsc, Scanner catBsc) {
		if(!subj.isEmpty() && !testL.isEmpty()) {
			setTestLetter(testL.trim());
			setSubject(subj.trim());
		}//set constants
		
		files.useDelimiter(",");
		catAsc.useDelimiter(",");
		catBsc.useDelimiter(",");
		this.files = files;
		this.catAsc = catAsc;
		this.catBsc = catBsc;
		
		if(files.hasNext()) {
			setFileName(files.next().trim());
			setCatA(catAsc.next().trim());
			setCatB(catBsc.next().trim());
			makeGUI();
		}//just one test
		
	}

	private void runMultipleTests() {
		setFileName(files.next().trim());
		setTrialHandler(new TrialHandler(this));
		setSorter(new ListSorter(this));
		if(catAsc.hasNext())
			setCatA(catAsc.next().trim());
		if(catBsc.hasNext())
			setCatB(catBsc.next().trim());
		makeGUI();
	}

	/** Sets this experiment to be interweaved (normally blocked by category)
	 * @param b if this experiment will be interweaved during the testphase
	 */
	public void setInterweaved(boolean b) {
		this.interweaved = b;
		
	}
		
		
}//experiment
