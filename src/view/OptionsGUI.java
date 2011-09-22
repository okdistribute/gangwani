package view;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 
/*
 * A 1.4 application that uses SpringLayout to create a forms-type layout.
 * Other files required: SpringUtilities.java.
 */

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import experiment.Experiment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/** Creates a GUI that must be used to input test-specific
 * variables for important features of the test.
 * These variables include the name of the file containing the 
 * words to be loaded, the name of the Category A and Category B (should be one character)
 * The subject to be tested, and a letter that represents the test
 * The Administrator also has an option in the menu to turn on 
 * "Debug Mode" which is a shorter version of the test.
 * 
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 *
 */
public class OptionsGUI {
	
	private Experiment experiment;

    private JTextField fileName;
    private JTextField subject;
    private JTextField testLetter;
    private JMenuBar menuBar;
    private JMenu menu;
    private JCheckBoxMenuItem debugMode;
    private JMenuItem about;
	
	/** Creates an optionsGUI
	 * @param experiment
	 */
	public OptionsGUI(Experiment experiment) {
		this.experiment = experiment;
		createAndShowGUI();	
	}
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        String[] labels = {"Name of File: ", "Subject: ", "Test ID: "};
        int numPairs = labels.length;
        //Create and populate the panel.
        SpringLayout layout = new SpringLayout();
        final JPanel p = new JPanel(layout);
        p.setBackground(Color.WHITE);
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            JTextField field = new JTextField(10);
            p.add(field);
            l.setLabelFor(field);
            if(i == 0)
            	fileName = field;
            if (i == 1)
            	subject = field;
            if(i == 2)
            	testLetter = field;
        }
        
        JButton startButton = new JButton("Click to Start");
        startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(subject.getText().isEmpty() ||
				   fileName.getText().isEmpty() ||
				   testLetter.getText().isEmpty()) {
					JOptionPane.showMessageDialog(p, "All fields are required to start the experiment", 
							"Please Try Again",JOptionPane.ERROR_MESSAGE);
				}
					
				experiment.startExperiment(subject.getText(),
						new Scanner(fileName.getText()),
						testLetter.getText(),
						new Scanner("a,c,e,g,i,k,m,o,q,s,u,w,y"),
						new Scanner("b,d,f,h,j,l,n,p,r,t,v,x,z"));
			}
        	
        });
        
        JCheckBoxMenuItem interweaved = new JCheckBoxMenuItem("Interweaved", false);
        interweaved.addItemListener(new interweavedListener(experiment));
        
        debugMode = new JCheckBoxMenuItem("Debug Mode", false);
        debugMode.addItemListener(new debugListener(experiment));
        
        menuBar = new JMenuBar();
        menu = new JMenu("More...");
        JMenuItem howTo = new JMenuItem("Help");
        howTo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		aboutDialog ad = new aboutDialog(true);
        		ad.setVisible(true);
        	}
        });
        
        about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent event) {
        		        aboutDialog ad = new aboutDialog();
        		        ad.setVisible(true);
        			}
        });
        menu.add(interweaved);
        menu.add(debugMode);
        menu.add(about);
        menu.add(howTo);
        menu.setMnemonic(KeyEvent.VK_E);
        menuBar.setLayout(new BorderLayout());
        menuBar.add(menu, BorderLayout.EAST);
        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                 numPairs, 2, //rows, cols
                                 7, 30,        //initX, initY
                                 7, 7);       //xPad, yPad

        p.add(startButton);
        //Create and set up the window
        JFrame frame = new JFrame("Administrative Panel");
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        p.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(p);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    
    }
    
    /** This instantiates an ItemListener that checks to see if the 
     * administrator wants the experiment to begin in debugMode rather
     * than regularly.
     * DebugMode is a mode in which there are only 3 words displayed during the study 
     * section and 3 trials during the TestPhase.
     * @author krmckelv
     *
     */
    class debugListener implements ItemListener {
    	
    	private Experiment experiment;

		public debugListener(Experiment experiment) {
    		this.experiment = experiment;
    	}

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1)
					experiment.setDebugMode(true);
				else
					experiment.setDebugMode(false);
			}
        	
    }
    
    class interweavedListener implements ItemListener {
    	
    	private Experiment experiment;

		public interweavedListener(Experiment experiment) {
    		this.experiment = experiment;
    	}

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1)
					experiment.setInterweaved(true);
				else
					experiment.setInterweaved(false);
			}
        	
    }
    /** Creates a JDialog that represents author information
     * @author krmckelv
     *
     */
    @SuppressWarnings("serial")
	class aboutDialog extends JDialog {
    	
    	public aboutDialog() {
    	    setTitle("About the Author");
    	    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    	    setBackground(Color.WHITE);

    	    JTextPane name = new JTextPane();
    		StyledDocument doc = name.getStyledDocument();
    		MutableAttributeSet standard = new SimpleAttributeSet();
    		name.setFont(new Font("seriff",Font.PLAIN,14));
    		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
    	    StyleConstants.setFontSize(standard, 14);
    		StyleConstants.setBold(standard, true);
    		doc.setParagraphAttributes(0,0,standard,true);
    		name.setStyledDocument(doc);
    	    name.setText("Created by:\n\n Karissa McKelvey \nKMProjects, 2010\n\n" +
    	    		"For questions, comments, or your own customized program, go to http://mypage.iu.edu/~krmckelv");
    	    name.setEditable(false);
    	    name.setAlignmentX(0.5f);
    	    add(name);
    	    setResizable(false);

    	    //add(Box.createRigidArea(new Dimension(0, 100)));
    	    setModalityType(ModalityType.APPLICATION_MODAL);
    	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	    setSize(300, 200);
    	}

    	public aboutDialog(boolean isConfused) {
    	    setTitle("Things you Might Want to Know");
    	    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    	    setBackground(Color.WHITE);

    	    JTextPane name = new JTextPane();
    		StyledDocument doc = name.getStyledDocument();
    		MutableAttributeSet standard = new SimpleAttributeSet();
    		name.setFont(new Font("seriff",Font.PLAIN,14));
    		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
    	    StyleConstants.setFontSize(standard, 14);
    		doc.setParagraphAttributes(0,0,standard,true);
    		name.setStyledDocument(doc);
    	    name.setText("This program creates an experiment from a text file of words with each word on" +
    	    		" a different line. There is assumed to be such a 'word list' in the " +
    	    		"same directory as the program on your machine. You must give the full name of the " +
    	    		"file, \n(i.e., word_list.txt, second_word_list.txt). \n\nThe first half of the words will be labeled category 1 and " +
    	    		"the second half category 2; these categories are labeled alphabetically (i.e., a and b).\n" +
    	    		"\n\nWhen the program is finished, press 'spacebar' and a file will be created in the same directory" +
    	    		", named after the given subject_testid.txt, in comma-delimited format, which can be loaded" +
    	    		" into excel for further processing. \n\nAll fields are required to start the experiment.");
    	    name.setEditable(false);
    	    name.setAlignmentX(0.5f);
    	    add(name);
    	    setResizable(false);

    	    setModalityType(ModalityType.APPLICATION_MODAL);
    	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	    setSize(500, 370);
    	}
    }
}