package curves;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import javax.swing.JComponent;

/**
 * 
 * @author casteran
 * 
 */

class CurveTracer extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	FunctionVariations fvar;

	/**
	 * part of the height of the canvas devoted to the drawing of the curve
	 */

	static double croppingFactor = 0.9;

	CurveTracer(FunctionVariations var) {
		this.fvar = var;
		setPreferredSize(new Dimension(400, 300));
		setOpaque(true);
		setBackground(Color.GREEN);
		setForeground(Color.red);
	}

	/**
	 * converts the mouse's x into some value of the considered interval.
	 * correction 29/03/1999
	 */

	protected double realX(int mouseX) {
		double xmin = fvar.getXmin();
		return xmin + (mouseX * (fvar.getXmax() - xmin) / getWidth());

	}

	/**
	 * converts the mouse's y into the corresponding value in the function's
	 * range
	 */

	protected double realY(int mouseY) {
		if (fvar.isConstant())
			return fvar.getYmax();
		int h = getHeight();
		double ymax = fvar.getYmax();
		return ymax + (fvar.getYmin() - ymax)
				* (mouseY - h * (1 - croppingFactor) * 0.5) / h
				/ croppingFactor;
	}

	/** converts some y in the fun's range into a mouse y coordinate */

	protected int getY(double y) {
		int h = getHeight();
		if (fvar.isConstant())
			return (int) h / 2;
		else {
			double ymax = fvar.getYmax();
			return (int) ((h * (1 - croppingFactor) / 2.0) + h * croppingFactor
					* (y - ymax) / (fvar.getYmin() - ymax));
		}
	}

	protected void paintComponent(Graphics g) {
		int width = getWidth();

		// Paint background if we're opaque.
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, width, getHeight());
		}

		g.setColor(getForeground());
		double step = ((double) width) / fvar.getStepNumber();
		double lastX = 0;
		double newX;

		int lastY = getY(fvar.getStepValue(0));
		int newy;
		for (int i = 0; i < fvar.getStepNumber(); i++) {
			newy = getY(fvar.getStepValue(i + 1));
			newX = lastX + step;
			g.drawLine((int) lastX, lastY, (int) newX, newy);
			lastY = newy;
			lastX = newX;
		}
		if (lastX < (double) width) {
			g.drawLine((int) lastX, lastY, width, lastY);
		}
	}
}
