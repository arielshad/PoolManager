
public class Result{
	private double sum;

	private int resultsCounter;
	private int default_n;
	private String exp_type;
	
	
	public Result(double sum, int resultsCounter, int default_n, String exp_type) {
		this.resultsCounter = resultsCounter;
		this.setSum(sum);
		this.default_n = default_n;
		this.exp_type = exp_type;
	}
	
	
	private synchronized int getResultCounter(){
		if (resultsCounter == 0) {
			resultsCounter = resultsCounter-1;
			return 0;
		}
		return this.resultsCounter;
	}
	
	public void addNum(double num){
		synchronized (this) {
			sum+=num;
			resultsCounter--;	
		}
		
	}
	

	public void mulNum(double num){
		synchronized (this) {
			sum*=num;
			resultsCounter--;
		}
	}


	public synchronized double getSum() {
		return sum;
	}

	public synchronized void setSum(double sum) {
		this.sum = sum;
	}
	
	public synchronized void report(){
		if (getResultCounter() == 0) {
			if (exp_type.contains("1.1")) {
				System.out.println("Expr. type " + exp_type +", n = " + default_n + ": "+sum);
			}else{
				System.out.println("Expr. type " + exp_type +", l=m = " + default_n + ": "+sum);
			}
									
		}
	}
	
	

}
