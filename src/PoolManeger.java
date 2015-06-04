import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//the singelton design pattern to prevent of creating more then 1 pool maneger
public class PoolManeger extends Thread{

	private static PoolManeger instance  = new PoolManeger();
	private static int numOfInstance = 0;
	private int numOfThread = 10;
	private int maxSizeOfTaskQueue = Integer.MAX_VALUE;
	private Object lock = new Object();
	private Lock reLock = new ReentrantLock();
	
	private BlockingQueue<PoolThread> threads_queue; //available threads
	private BlockingQueue<PoolThread> running_threads_queue; // not available threads
	private BlockingQueue<Callable<Result>> task_queue; // waiting tasks
	
	
	private PoolManeger() {
		// TODO Auto-generated constructor stub
	}
	
	public static PoolManeger getInstace(int maxNumOfTask, int numOfThreads){
		if (numOfInstance==0) {
			instance.setMaxSizeOfTaskQueue(maxNumOfTask);
			instance.setNumOfThread(numOfThreads);
			instance.init();
			numOfInstance++;
			
		}
		return instance;

	}
	
	//add task to queue
	public void addTask(Callable<Result> task){
		synchronized (lock) {
//			System.out.println("task queue size: "+task_queue.size());			
			try {
//				System.out.println("tying to add");
				if (task_queue != null && task != null){
					while(task_queue.size()==this.getMaxSizeOfTaskQueue()){
						lock.wait();
					}
					task_queue.add(task);
//					System.out.println("after add");
					

					lock.notifyAll();
					
						
				}					
				else
					System.out.println("---> fail task " + task + "task_q " +  task_queue);
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}

		
	}
	
	private void init(){
		synchronized (lock) {
			threads_queue = new BlockingQueue<PoolThread>(numOfThread);
//			System.out.println("print after init thread queue");
			
			running_threads_queue = new BlockingQueue<PoolThread>(numOfThread);
			task_queue = new BlockingQueue<Callable<Result>>(maxSizeOfTaskQueue);
			
			//create threads
			for (int i = 0; i < numOfThread; i++) {
				PoolThread pt = new PoolThread(threads_queue);
				pt.start();
				
				try {
					threads_queue.add(pt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}	
		}
		
	}
	
	@Override
	public void run() {

		//start working.
		synchronized (lock) {
			while (true) {
				//wait for task
				
				while (task_queue.size()==0) {					
					try {
//						System.out.println("im here waiting fot task");
						lock.wait();
//						System.out.println("I woke up!!");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
				
				//now we have task - pop thread and push task
				try {
//					System.out.println("im here tying to get avaliable thread");
					
					//pop thread
					PoolThread thread = (PoolThread) threads_queue.remove();
					lock.notifyAll();

					//insert thread to running queue
//					running_threads_queue.add(thread);
					
					//pop task	
//					System.out.println("--try to remove task from queue: "+task_queue.size());
					Callable<Result> task = (Callable<Result>) task_queue.remove();
//					System.out.println("--AFTER try to remove task from queue: "+task_queue.size());
										
					//push task to thread
					thread.addTask(task);
					

				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				
			}	
		}

	}


	public void setMaxSizeOfTaskQueue(int maxSizeOfQueue) {
		this.maxSizeOfTaskQueue = maxSizeOfQueue;
	}

	


	public int getMaxSizeOfTaskQueue() {
		return maxSizeOfTaskQueue;
	}


	public void setNumOfThread(int numOfThread) {
		this.numOfThread = numOfThread;
	}

}
