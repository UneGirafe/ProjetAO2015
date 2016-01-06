package curves;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * @author casteran
 */

class CurveControls extends JPanel {

	private static final long serialVersionUID = 1L;

	protected final static Integer nStepsChoices[] = { 1, 2, 3, 4, 5, 10, 20,
			40, 80, 160, 320, 640 };

	private JComboBox<Integer> cb;
	private ZoomWidget zoom;
	private CurveTracer trace;

	CurveControls(final FunctionVariations var, final CurveFrame f) {
		super(new BorderLayout());
		
		JPanel precision = new JPanel();
		JLabel title = new JLabel("Precision");
		cb = new JComboBox<>(nStepsChoices);
		zoom = new ZoomWidget();
		
		precision.add(title);
		precision.add(cb);
		precision.add(ZoomWidget);
		add(precision);
		add(zoom.getWidget());
		
		cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					var.tabulate(CurveControls.this.currentPrecision());
					f.infos.update();
					f.repaint();
				}
			}

		});
		cb.setSelectedIndex(nStepsChoices.length / 2);
		
	}

	int currentPrecision() {
		return nStepsChoices[cb.getSelectedIndex()];
	}
}
