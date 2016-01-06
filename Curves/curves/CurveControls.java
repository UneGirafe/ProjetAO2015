package curves;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import function.Functions;
import function.SyntaxErrorException;

/**
 * @author casteran
 */

class CurveControls extends JPanel {

	private static final long serialVersionUID = 1L;

	protected final static Integer nStepsChoices[] = { 1, 2, 3, 4, 5, 10, 20,
			40, 80, 160, 320, 640 };

	private JComboBox<Integer> cb;
	private ZoomWidget zoom;
	private DocumentWidget document;
	

	CurveControls(final FunctionVariations var, final CurveFrame f) {
		super();		
		JPanel precision = new JPanel();
		JLabel precisionL = new JLabel("Precision");
		
		cb = new JComboBox<>(nStepsChoices);
		zoom = new ZoomWidget();
		zoom.addObserver((Observer) var);
		document = new DocumentWidget();
		
		((Observable) var).addObserver(f); //permet a la fonction de notifier le tracer
		document.addObserver(f);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		precision.add(precisionL);
		precision.add(cb);

		cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					var.tabulate(CurveControls.this.currentPrecision());
					f.infos.update();
					f.repaint();
				}
			}

		});

		add(precision);
		add(zoom.getWidget());
		
		
		add(document.getWidget());
		
		cb.setSelectedIndex(nStepsChoices.length / 2);
		
	}
	
	public void readFile(File f){
		try (BufferedReader reader = new BufferedReader( new FileReader(f))) {//try-with-ressources
																			  //libere la ressource des que la vaiable n'est plus utilisee
			String line;
			while ((line = reader.readLine()) != null){
				System.out.println("ligne lue : " + line);
				/*comparaison avec fonctions deja dans le conteneur 
				 * if (line == conteneur.getXXX(line) */
				Functions.parse(line);
				/* conteneur.add(line);*/
			}
		}

		 catch (IOException | SyntaxErrorException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		 }

	}

	public void writeFile(File f, JTextField functionField){
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(f,true))){
			
			Functions.parse(functionField.getText()); //recupere la fonction dans le champ texte et verifie la syntaxe
			f.createNewFile();

			writer.write(functionField.getText());
			writer.newLine();
			
		} catch (IOException | SyntaxErrorException e) {
			System.out.println("You shall not parse !");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	int currentPrecision() {
		return nStepsChoices[cb.getSelectedIndex()];
	}
}
