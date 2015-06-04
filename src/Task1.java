import java.util.concurrent.Callable;


public class Task1 implements Callable<Result>{
	private double sum;
	private int from;
	private int to;
	private Result result;
	
	Task1(int from, int to, Result result){
		this.from=from;
		this.to = to;
		this.sum=1;
		this.result = result;
	}

	public double getSum(){
		return sum;
	}
	
	

	@Override
	public Result call() throws Exception {
		// Multiplay form (1.1)
		for (int i = from; i <= to; i++) {
			sum *= ( Math.pow(-1, i) / ( 2*i+1 ) );
		}
				
		result.mulNum(sum);
		return result;
	}

}
