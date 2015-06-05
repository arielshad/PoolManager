import java.util.concurrent.Callable;


public abstract class TaskObject implements Callable<Result>{
	protected double sum;
	protected int from;
	protected int to;
	protected Result result;
	protected ExpressionType type;
	
	public TaskObject(int from, int to, Result result, ExpressionType type){
		this.from=from;
		this.to = to;
		this.sum=1;
		this.result = result;
		this.type = type;
	}
	
	abstract void expression();

	public double getSum(){
		return sum;
	}
	
	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public ExpressionType getType() {
		return type;
	}

	public void setType(ExpressionType type) {
		this.type = type;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}	

	@Override
	public Result call() throws Exception {
		//do expression
		expression();
		
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
