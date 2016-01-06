package curves;

public class FunVariations extends Variations {
	private  Function f;
	
	public  FunVariations (Function f, double xmin, double xmax){
		
	}

	@Override
	protected double fun(double x) {
		return f.value(x);
	}	
	
	
	
}
