import static function.Functions.SIN;
import static function.Functions.X;
import static function.Functions.compose;
import static function.Functions.constant;
import static function.Functions.times;

import java.io.IOException;

import curves.CurveApplication;
import function.FunVariations;
import function.Function;
import function.SyntaxErrorException;

public class TestFunction {
	public static void main(String[] args) throws SyntaxErrorException, IOException {
		Function f = 
		times(X, compose(SIN, times(constant(2.), X)));
		//Function f = Functions.parse("/ 1 * x x");
		CurveApplication.start(new FunVariations(f, -10, 10));
	}
}