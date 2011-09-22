package experiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * TrialHandler handles the list of trials for recording and ultimately the output 
 * into a JTable.
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */
public class TrialHandler {
	
	private ArrayList<Trial> trials = new ArrayList<Trial>();
	private Experiment experiment;
	private String comma = ",";
	private BufferedWriter bwriter;
	private PrintWriter writer;
	Thread t;
	                                         
	/** Creates a new TrialHandler
	 * @param experiment
	 */
	public TrialHandler(Experiment experiment) {
		this.experiment = experiment;
	}
	
	/** Adds the trial to this trialHandler
	 * @param trial
	 */
	public void add(Trial trial) {
		trials.add(trial);
	}
	
	
	/** Gets a trial from this trialHandler given a trial
	 * @param trial
	 * @return trial
	 */
	public Trial get(Trial trial) {
		return trials.get(trials.indexOf(trial));
	}
	
	/** Gets the trial from this trialHandler given a trialNumber
	 * @param trialNumber
	 * @return trial
	 */
	public Trial get(int trialNumber) {
		return trials.get(trialNumber);
	}

	/**
	 * Runs the trial handler, outputs the contents of each trial
	 * into a text file that can be loaded into excel in comma-delimited
	 * format
	 */
	public void run() { 
		String name = experiment.getSubject() 
		+ "_" + experiment.getTestLetter() + ".txt";
		boolean exists = false;
		if(new File(name).exists()) {
			exists = true;
		}
		try {
			bwriter = new BufferedWriter(new FileWriter(name,true));
			writer = new PrintWriter(bwriter);
			System.out.println("Created File " + name);
		} catch (IOException e) {
			System.out.println("exception when making writer");
			e.printStackTrace();
		}
		
		if(!exists)
			recordHeadings();
		recordTrials();
	}
	
	/**
	 * Records the headings for the text file that will be created 
	 */
	public void recordHeadings() {
		write("Subject");

		writer.write(comma);
		write("Trial");

		writer.write(comma);
		write("Category");

		writer.write(comma);
		write("Response");
		
		writer.write(comma);
		write("Target");

		writer.write(comma);
		write("R-Location");

		writer.write(comma);
		write("Correct?");

		writer.write(comma);
		write("RT");

		writer.write(comma);
		write("Delay");

		write("\n");
	}
	
	/** Writes a string to the file
	 * @param str
	 */
	public void write(String str) {
		writer.print(str);
	}
	
	/**
	 *  Records the trials into the text file in the following ordeR:
	 *  Subject, Trial Number, Category, Response, TargetWord, Location,
	 *  Correct?, Response Time, and Delay
	 */
	public void recordTrials() {
		Trial current;
		for(int index = 0; index < trials.size(); index++) {
			current = trials.get(index);
			write(experiment.getSubject());
			writer.write(comma);
			write(Integer.toString(current.getTrialNumber() + 1));
			writer.write(comma);
			write(current.getCategoryAsString());
			writer.write(comma);
			write(current.getResponse());
			writer.write(comma);
			write(current.getTargetWord().getWord());
			writer.write(comma);
			write(current.getLoc());
			writer.write(comma);
			write(current.correct());
			writer.write(comma);
			write(Integer.toString(current.getResponseTime()));
			writer.write(comma);
			write(Integer.toString(current.getDelay()));
			write("\n");
		}

	    writer.close();
	}

	
}
	

