import java.util.concurrent.Callable;


public class Task2 implements Callable<Result>{
	private double sum;
	private int from;
	private int to;
	private Result result;

	Task2(int from, int to, Result result){
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

		// Multiplay form (1.2)
		for (int i = from; i <= to; i++) {
			sum*= ((-1)^3*i) / (2*(i+1)+1);
		}

		
		result.mulNum(sum);
		return result;
	}

}


