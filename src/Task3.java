import java.util.concurrent.Callable;


public class Task3 implements Callable<Result>{
	private double sum;
	private int from;
	private int to;
	private Result result;
	private ExpressionType type = ExpressionType.SUM;

	Task3(int from, int to, Result result, ExpressionType type){
		this.from=from;
		this.to = to;
		this.sum=0;
		this.result = result;
		this.type = type;
	}

	public double getSum(){
		return sum;
	}



	@Override
	public Result call() throws Exception {

		// Sum form (1.2)		

		for (int i = from; i <= to; i++) {
			sum+=i/(2*Math.pow(i, 2)+1);
		}
		
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


