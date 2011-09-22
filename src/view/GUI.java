package view;

import java.awt.*;

import javax.swing.JFrame;

import experiment.*;
/**
 * Creates a GUI
 * @author krmckelv, <a href = mailto:krmckelv@indiana.edu>Karissa McKelvey</a>
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	private static int WIDTH;
	private static int HEIGHT;
	private Experiment experiment;
	
	/**
	 * Creates a GUI from this experiment
	 * @param experiment
	 */
	public GUI(Experiment experiment) {
		this.setExperiment(experiment);
		setTitle("Interaction Window");
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = screenSize.width;
		HEIGHT = screenSize.height;
		setPreferredSize(screenSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Canvas c = new Canvas(experiment);
		setContentPane(c);
		pack();
		setVisible(true);
	}
	
	/**
	 * @return width as an int
	 */
	public static int width() {
		return WIDTH;
	}

	/**
	 * @return height as an int
	 */
	public static int height() {
		return HEIGHT;
	}

	/**
	 * @param experiment the experiment to set to this gui
	 */
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	/**
	 * @return the experiment associated with this gui
	 */
	public Experiment getExperiment() {
		return experiment;
	}
		
}
