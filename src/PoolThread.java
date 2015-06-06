import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class PoolThread extends Thread{
		
	private Object lock = new Object();
	private BlockingQueue<Callable<Double>> taskQueue;
	private boolean finished = true;
	public long localId;
	private BlockingQueue<PoolThread> threads_queue;
	
	public PoolThread(BlockingQueue<PoolThread> threads_queue) {
		this.threads_queue = threads_queue;
		taskQueue = new BlockingQueue<>(1);
	}
	
	@SuppressWarnings("rawtypes")
	public void addTask(Callable task){
		synchronized (lock) {
			try {
				taskQueue.add(task);
				lock.notifyAll();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}			
		}

	}
	
	@Override
	public void run() {
		localId = Thread.currentThread().getId();
		while(true){
			synchronized (lock) {
				while (taskQueue.size()==0) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				try {
					//now we have a task, lets inesrt it into futureTask and w8 for result
					@SuppressWarnings("unchecked")
					FutureTask<Result> futureTask = new FutureTask<Result>((Callable<Result>) taskQueue.remove());
					futureTask.run();
					Result temp = futureTask.get();
					temp.report();
					//finished running now add the thread back to the mangers pool
					
					threads_queue.add(this);
					
//					System.out.println("Thread "+localId+" Finshed Running");
					
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}

			}			
		}		
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
