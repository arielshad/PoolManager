
public class TaskObjectTester extends TaskObject{

	TaskObjectTester(int from, int to, Result result, ExpressionType type) {
		super(from, to, result, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	void expression() {
		for (int i = from; i <= to; i++) {
			sum *= ( Math.pow(-1, i) / ( 2*i+1 ) );
		}
	}

}
