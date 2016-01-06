package curves;

import java.awt.GridLayout;
import java.util.Observable;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class ZoomWidget extends Observable {

	private JPanel widget;
	private JSlider xSlider;
	private JSlider ySlider;

	
	public ZoomWidget() {
		widget = new JPanel(new GridLayout(2,4));
		
		xSlider = new JSlider(JSlider.HORIZONTAL, -300000, 300000,-300000);
		ySlider = new JSlider(JSlider.VERTICAL, -300000, 300000,-300000);
		
		widget.add(ySlider);
		widget.add(xSlider);
	
	}
	

	
	public JPanel getWidget() {
		return widget;
	}
}
