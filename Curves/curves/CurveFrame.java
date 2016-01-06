package curves;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import function.FunVariations;
import function.Function;

/**
 * A class  to represent the variations of some function  in some interval.
 * Allows some control on the accuracy of this representation
 */

/**
 * @author casteran
 */
public class CurveFrame extends JFrame  implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** the graphic part of the display */
	CurveTracer tracer;

	List<FunctionVariations> fVars;
	/** information bar */
	CurveInfos infos;

	/** various commands */
	CurveControls controls;

	/**
	 * Builds a top-level window from the variations of a function
	 * 
	 * @see FunctionVariations
	 */
	public CurveFrame(FunctionVariations fvar) {
		super("Curve");
		tracer = new CurveTracer(fvar);
		infos = new CurveInfos(fvar);
		controls = new CurveControls(fvar, this);

		JPanel mainPane = new JPanel(new BorderLayout());

		mainPane.add(tracer, BorderLayout.CENTER);
		mainPane.add(infos, BorderLayout.SOUTH);
		mainPane.add(controls, BorderLayout.EAST);

		tracer.addMouseListener(new MouseAdapter() {
			CurveFrame cf = CurveFrame.this;

			public void mouseEntered(MouseEvent e) {
				cf.infos.xmouse.setText("x = " + cf.tracer.realX(e.getX()));
				cf.infos.ymouse.setText("y = " + cf.tracer.realY(e.getY()));
				cf.infos.repaint();
			}

			public void mouseExited(MouseEvent e) {
				cf.infos.xmouse.setText("");
				cf.infos.ymouse.setText("");
				cf.infos.repaint();
			}
		});
		tracer.addMouseMotionListener(new MouseMotionAdapter() {
			CurveFrame cf = CurveFrame.this;

			public void mouseMoved(MouseEvent e) {
				cf.infos.xmouse.setText("x = " + cf.tracer.realX(e.getX()));
				cf.infos.ymouse.setText("y = " + cf.tracer.realY(e.getY()));
				//cf.infos.repaint();
			}

			public void mouseDragged(MouseEvent e) {
				cf.infos.xmouse.setText("x = " + cf.tracer.realX(e.getX()));
				cf.infos.ymouse.setText("y = " + cf.tracer.realY(e.getY()));
				cf.infos.repaint();
			}
		});
		
		fvar.tabulate(controls.currentPrecision());
		setContentPane(mainPane);
		pack();
		infos.update();
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof DocumentWidget){

		System.out.print("Mise � jour Curve Frame ");
		FunctionVariations newFvar = new FunVariations((Function) arg, -10, 10);
		fVars.add(newFvar);
/*		DocumentWidget dw = (DocumentWidget) arg ;


		tracers.add();
		dw.getDrawnFunctions();*/
		repaint();
		}

		if (o instanceof Variations){
			infos.update();
			repaint();
		}
	}
}
