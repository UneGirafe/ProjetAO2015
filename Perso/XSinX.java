import curves.CurveApplication;
import curves.FunctionVariations;
import curves.Variations;

/**
 * @author casteran
 */
/**
 * A simple function to check the computation of integral.
 */

public class XSinX extends Variations implements FunctionVariations {

	/** contructs a representation of fun in the interval [xmin,xmax] */
	public XSinX(double xmin, double xmax) {
		setXmin(xmin);
		setXmax(xmax);
	}

	public double fun(double x) {
		return x * Math.sin(x);
		// (x - Math.floor(x)) * Math.sin(50 * x);
	}

	public static void main(String[] args) {
		CurveApplication.start(new XSinX(-2 * Math.PI, 2 * Math.PI));
	}

}
