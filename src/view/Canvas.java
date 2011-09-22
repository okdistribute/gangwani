package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import experiment.*;
/**
 * Handles the look and feel of the study section and begins the test phase
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 *
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {
	
	private Experiment experiment;
	public JTextPane studyLabel;
	public JTextPane leftLabel = new JTextPane();
	public JTextArea rightLabel = new JTextArea("");
	private StudyList studyList;
	
	/** Creates a new canvas
	 * @param experiment
	 */
	public Canvas(Experiment experiment) {
		this.experiment = experiment;
		studyList = experiment.getSorter().getStudyList();
		setLayout(null);
		setBackground(Color.WHITE);
		Font font = new Font("monospaced", Font.BOLD, 28);
		setFont(font);
		studyLabel = new JTextPane();
		StyledDocument doc = studyLabel.getStyledDocument();
		MutableAttributeSet standard = new SimpleAttributeSet();
		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
	    StyleConstants.setFontSize(standard, 28);
		StyleConstants.setBold(standard, true); 
		doc.setParagraphAttributes(0,0,standard,true);
		studyLabel.setFont(font);
		leftLabel.setFont(font);
		rightLabel.setFont(font);
		studyLabel.setText("A series of words will be presented sequentially on the screen. \nTry to remember as many words as you can.\n"
				+"\nYou will be tested on your knowledge upon completion of the study section.\n" +
				"Click here and then press the spacebar when you are ready to start.");
		studyLabel.addKeyListener(new SpaceListener(this, studyLabel, false));
		studyLabel.setSize(900,350);
		studyLabel.setLocation(GUI.width()/2 - studyLabel.getSize().width/2, 
				GUI.height()/2 - studyLabel.getSize().height/2);
		studyLabel.setFocusable(true);
		studyLabel.setEditable(false);
		add(studyLabel);
		add(leftLabel);
		add(rightLabel);
		paintImmediately(0,0, GUI.width(), GUI.height());
		}
		
	
	/**
	 * Runs the study
	 */
	private void run() {
		paintImmediately(0,0, GUI.width(), GUI.height());
		paintStudy();
	}
	
	/**
	 * begins the test phase and paints the directions
	 */
	private void paintTrial() {
		studyLabel.setFocusable(true);
		studyLabel.setText("For each screen, press 's' if you think the word on the LEFT appeared during the study phase. Press" +
				" 'k' if you think the word on the RIGHT appeared during the study phase. Respond as quickly as possible without sacrificing accuracy. " + 
				"\nIf you are unsure, take your best guess.\n\n You may now take a break. Click here and then press the spacebar when you are ready to continue. ");
		studyLabel.addKeyListener(new SpaceListener(this, studyLabel, true));
		studyLabel.setSize(1000,400);
		studyLabel.setLocation(GUI.width()/2 - studyLabel.getSize().width/2, 
				GUI.height()/2 - studyLabel.getSize().height/2);
		paintImmediately(0,0, GUI.width(), GUI.height());
	}//printTrial
	
	/**
	 * Paints the study section by creating a timer
	 * Each word has a 1000 ms display time and 150ms
	 * blank in between each word
	 */
	public void paintStudy() {
		final Timer timer = new Timer(150, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//nothing
			}
		});
		studyListener showWords = new studyListener(timer, experiment);
		timer.addActionListener(showWords);
		timer.start();
		studyLabel.removeKeyListener(studyLabel.getKeyListeners()[0]);
	}

	/** Returns the left label
	 * @return the left label
	 */
	public JTextPane getLeftLabel() {
		return leftLabel;
	}


	/** Sets the left label to the given label
	 * @param leftLabel
	 */
	public void setLeftLabel(JTextPane leftLabel) {
		this.leftLabel = leftLabel;
	}


	/** Returns the right label
	 * @return rightLabel
	 */
	public JTextArea getRightLabel() {
		return rightLabel;
	}


	/** Sets the right label to the given label
	 * @param rightLabel
	 */
	public void setRightLabel(JTextArea rightLabel) {
		this.rightLabel = rightLabel;
	}

	/** Returns the experiment associated with this canvas
	 * @return the experiment
	 */
	public Experiment getExperiment() {
		return experiment;
	}

	/** This class instantiates the ActionListener that 
	 * the study phase uses to time and display each study word
	 * @author krmckelv
	 *
	 */
	private class studyListener implements ActionListener {

		private Timer timer;
		private int index = 0;
		int size = 0;
		
		public studyListener(Timer timer, Experiment experiment) {
			this.timer = timer;
			if(experiment.isDebugMode())
				size = 3;
			else
				size = experiment.getSorter().getStudyList().size();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			studyLabel.setSize(250,200);
			studyLabel.setLocation(GUI.width()/2 - studyLabel.getSize().width/2-25, 
					GUI.height()/2 - studyLabel.getSize().height/2);
			Word toDisplay = studyList.get(index);
			experiment.getSorter().getTestList().get(toDisplay).setStudyPhaseNumber(index);
			studyLabel.setText(toDisplay.getWord());
			paintImmediately(0,0, GUI.width(), GUI.height());
			Experiment.setDelay(1000);
			studyLabel.setText("");
			paintImmediately(0,0, GUI.width(), GUI.height());
			index++;
			if(index >= size) {
					timer.stop();
					paintTrial();
				}
					
		}		
	}
	
	/** Creates a KeyListener that waits for a spacebar.
	 * If the testPhaseNext is set to true, creates a test phase. If not,
	 * it begins the study phase. 
	 * @author krmckelv
	 *
	 */
	class SpaceListener implements KeyListener {

		private Canvas canvas;
		private JTextPane studyLabel;
		private boolean textPhaseNext = false;

		public SpaceListener(Canvas canvas, JTextPane studyLabel, boolean testPhaseNext) {
			this.textPhaseNext = testPhaseNext;
			this.canvas = canvas;
			this.studyLabel = studyLabel;
		}
		
		public void setTestPhaseNext(boolean testPhaseNext) {
			this.textPhaseNext = testPhaseNext;
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				studyLabel.setText("");
				if(this.textPhaseNext)
					new TestPhase(canvas);  
				else
					canvas.run();
			}
		}
	}	
}

