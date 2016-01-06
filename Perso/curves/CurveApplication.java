package curves;

import javax.swing.JFrame;

public class CurveApplication {

	private CurveApplication() {
	}
	
	public static void start(FunctionVariations fv) {
		final FunctionVariations fvar = fv;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				CurveFrame cv = new CurveFrame(fvar);
				cv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

}
