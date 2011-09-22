package view;


import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import experiment.*;

/**
 * Handles the GUI and creates trials for the Test phase. Then adds each trial to
 * a trial handler.
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 */

public class TestPhase implements KeyListener {

	private TrialHandler trials;
	private Trial currentTrial;
	private Canvas canvas;
	private JTextPane leftLabel;
	private JTextArea rightLabel;
	private JTextArea reminderLabel;
	private Experiment experiment;
	private ListSorter sorter;
	private int currentTrialNumber = 0;
	private boolean finished = false;
	private JTextPane studyLabel;
	
	
	/** Creates a TestPhase given a Canvas
	 * @param canvas
	 */
	public TestPhase(Canvas canvas) {
		this.canvas = canvas;
		this.studyLabel = canvas.studyLabel;
		leftLabel = canvas.getLeftLabel();
		rightLabel = canvas.getRightLabel();
		reminderLabel = new JTextArea("Which word was on the list you just studied?");
		leftLabel.setEditable(false);rightLabel.setEditable(false);
		leftLabel.setFocusable(false);rightLabel.setFocusable(false);
		leftLabel.setSize(GUI.width()/2,130);
		rightLabel.setSize(GUI.width()/2,130);
		reminderLabel.setEditable(false);
		reminderLabel.setSize(600,100);
		Font directionFont = new Font("monospaced", Font.BOLD, 22);
		StyledDocument doc = leftLabel.getStyledDocument();
		MutableAttributeSet standard = new SimpleAttributeSet();
		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_RIGHT);
	    StyleConstants.setFontSize(standard, 28);
		StyleConstants.setBold(standard, true); 
		doc.setParagraphAttributes(0, 0, standard,true);
		reminderLabel.setFont(directionFont);
		studyLabel.setFont(directionFont);
		leftLabel.setLocation(0,
				GUI.height()/2 - leftLabel.getHeight()/2);
		rightLabel.setLocation(GUI.width()/2,
				GUI.height()/2 - rightLabel.getHeight()/2);
		studyLabel.setSize(GUI.width(),200);
		studyLabel.setLocation(GUI.width()/2-studyLabel.getSize().width/2, GUI.height()/7);
		reminderLabel.setLocation(GUI.width()/2 - reminderLabel.getSize().width/2 - 15, 
				GUI.height()/7 + studyLabel.getSize().height);
		canvas.add(reminderLabel);
		studyLabel.setFocusable(true);
		studyLabel.removeKeyListener(studyLabel.getKeyListeners()[0]);
		studyLabel.addKeyListener(this);
		experiment = canvas.getExperiment();
		sorter = experiment.getSorter();
		trials = experiment.getTrialHandler();
		startTestPhase();
	}
	
	/**
	 * Starts the testPhase. 
	 */
	private void startTestPhase() {
		Word[] trialWords = null;
		if(experiment.isDebugMode() && currentTrialNumber >= 3) {
			finishTestPhase();
		}
		else if(!sorter.getTestList().isEmpty()) {
			canvas.studyLabel.setText("");
			currentTrial = new Trial(sorter, currentTrialNumber);
			trialWords = currentTrial.getTrialWords();
			leftLabel.setText(trialWords[0].getWord() + "       ");
			rightLabel.setText("       " + trialWords[1].getWord());
			canvas.paintImmediately(0,0, GUI.width(), GUI.height());
			currentTrial.startTimer();
		}
		else {
			finishTestPhase();
		}
	}
	
	/**
	 * Finishes the test phase and displays the end instructions
	 */
	private void finishTestPhase() {
		this.finished = true;
		reminderLabel.setText("");
		canvas.studyLabel.setText("You have finished this section of the experiment. \n Press the spacebar to continue.");
		canvas.paintImmediately(0,0, GUI.width(), GUI.height());
		experiment.setTrialHandler(trials);
	}
	
	/**
	 * adds the finished trial to the trial handler for output and increments
	 * the current trial number by one
	 */
	private void nextTrial() {
		canvas.studyLabel.setText("");
		trials.add(currentTrial);
		leftLabel.setText("");
		rightLabel.setText("");
		canvas.paintImmediately(0,0, GUI.width(), GUI.height());
		currentTrialNumber++;
		startTestPhase();
	}
	

	@Override
	public void keyTyped(KeyEvent e) {

		if(!this.finished) {
		char c = e.getKeyChar();
			if(c == 's' || c == 'k') {
				currentTrial.recordAnswer(c);
				nextTrial();
			}
		
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
        if(this.finished && e.getKeyChar() == ' ')
        	experiment.stopExperiment();
        else if(!this.finished && !(c == 's' || c == 'k')) {
			studyLabel.setText("Please press 's' for the LEFT word or 'k' for RIGHT word");
			canvas.paintImmediately(0,0, GUI.width(), GUI.height());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * @param trials the trialhandler to set
	 */
	public void setTrialHandler(TrialHandler trials) {
		this.trials = trials;
	}

	/**
	 * @return the current trialhandler for this phase
	 */
	public TrialHandler getTrialHandler() {
		return trials;
	}

}
