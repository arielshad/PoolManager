import java.util.concurrent.Callable;


public class Task2 implements Callable<Result>{
	private double sum;
	private int from;
	private int to;
	private Result result;
	private ExpressionType type = ExpressionType.MUL;

	Task2(int from, int to, Result result, ExpressionType type){
		this.from=from;
		this.to = to;
		this.sum=1;
		this.result = result;
		this.type = type;
	}

	public double getSum(){
		return sum;
	}



	@Override
	public Result call() throws Exception {

		// Multiplay form (1.2)
		for (int i = from; i <= to; i++) {
			sum*= (Math.pow(-1, 3*i) / (2*(i+1)+1));
		}
//		System.out.println(sum);

		
		this.addAnswer();
		return result;
	}
	
	private void addAnswer() {
		if (type == ExpressionType.MUL) {
			result.mulNum(sum);
		}else{
			result.addNum(sum);
		}
		
		
	}

}


