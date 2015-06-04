import java.util.concurrent.Callable;


public class Feeder extends Thread{
	private PoolManeger poolManger;
	private Callable<Result> task;
	public Feeder(PoolManeger poolManger, Callable<Result> task) {
		this.poolManger = poolManger;
		this.task = task;
	}
	
	@Override
	public void run() {
		
		poolManger.addTask(task);
		
	}
}
