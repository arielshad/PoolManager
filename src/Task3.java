import java.util.concurrent.Callable;


public class Task3 implements Callable<Result>{
	private double sum;
	private int from;
	private int to;
	private Result result;

	Task3(int from, int to, Result result){
		this.from=from;
		this.to = to;
		this.sum=0;
		this.result = result;
	}

	public double getSum(){
		return sum;
	}



	@Override
	public Result call() throws Exception {

		// Sum form (1.2)		

		for (int i = from; i <= to; i++) {
			sum+=i/(2*i^2+1);
		}
		result.addNum(sum);
		
		return result;
	}

}


