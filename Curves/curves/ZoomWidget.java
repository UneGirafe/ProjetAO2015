package curves;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;


public class ZoomWidget extends Observable {

	private JPanel widget;
	
	private JTextField xMinField;
	private JTextField xMaxField;
	
	private JLabel xLabel;
		
	private double xMin;
	private double xMax;
	

	public ZoomWidget(/*final FunctionVariations var, final CurveFrame f*/) {
		widget = new JPanel();
		widget.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(5,5,5,5);

		xMinField = new JTextField("-5", 5);
		xMaxField = new JTextField("5", 5);

		xLabel = new JLabel("X");
		
		JButton applyButton = new JButton("Apply");
		
		applyButton.addMouseListener(new MouseAdapter() { 
	       @Override
			public void mouseClicked(MouseEvent e)
	        {	
				try {
					double newXmin;
					double newXmax;
				    newXmin = Double.parseDouble(xMinField.getText());
				    newXmax = Double.parseDouble(xMaxField.getText());
					setXMin(newXmin);
					setXMax(newXmax);
				    setChanged();
					notifyObservers(this);
					
				} catch (NumberFormatException exception) {
					System.out.println("Please enter a double value");
				    exception.printStackTrace();
				    // handle the error
				}
				
	           /* System.out.println("Applying Zoom: X[" + xMinField.getText() + ";" + xMaxField.getText()
					+ "] Y[" + var.getYmin() + ";" + var.getYmax() + "]");*/
				
/*				var.setXmin(newXmin);
				var.setXmax(newXmax);
				
				var.tabulate(var.getStepNumber());*/
			/*	f.infos.update();
				f.repaint();*/	
	        }
	    });
		
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		widget.add(xLabel);
		
		c.gridx = 1;
		widget.add(xMinField);
		
		c.gridx = 2;
		widget.add(xMaxField);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		widget.add(applyButton);		
	}


	public double getXMin(){
		return xMin;
	}
	
	public double getXMax(){
		return xMax;
	}
	
	public void setXMin(double newXmin){
		xMin = newXmin;
	}
	
	public void setXMax(double newXmax){
		xMax = newXmax;
	}
	
	
	public JPanel getWidget() {
		return widget;
	}

}
