import java.util.concurrent.Callable;


public class Task1 implements Callable<Result>{
	private double sum;
	private int from;
	private int to;
	private Result result;
	private ExpressionType type = ExpressionType.MUL;
	
	Task1(int from, int to, Result result, ExpressionType type){
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
		// Multiplay form (1.1)
		
		
		for (int i = from; i <= to; i++) {
			sum *= ( Math.pow(-1, i) / ( 2*i+1 ) );
			
		}
		
//		System.out.println("from: "+from+" to: "+to+" result: "+sum);
		
		
		this.addAnswer(sum);
		return result;
	}

	private void addAnswer(double sum2) {
		if (type == ExpressionType.MUL) {
			result.mulNum(sum);
		}else{
			result.addNum(sum);
		}
		
		
	}

}
