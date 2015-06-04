import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T>{

	
	private List<T> queue = new LinkedList<T>();
	private int limit;
	private Object lock = new Object();

	public BlockingQueue(int limit){
		this.limit = limit;
	}


	public int size(){
		return this.queue.size();
	}
	
	@SuppressWarnings("unchecked")
	public void add(Object item) throws InterruptedException  {
		synchronized (lock) {			
			while(this.queue.size() == this.limit) {				
				lock.wait();
			}
			this.queue.add((T) item);
			lock.notifyAll();
			
			
		
		}


	}


	public Object remove() throws InterruptedException{
		
		synchronized (lock) {
			while(this.queue.size() == 0){
				lock.wait();
			}
			
			lock.notifyAll();
			
			
			return this.queue.remove(0);	
		}    
	}
	
	
	/*
	public Object removeByID(long id){
		for (int i = 0; i < queue.size(); i++) {
			if ( ( (PoolThread) queue.get(i) ).localId == id){
				return this.queue.remove(i);
			}
		}
		
		return null;
		
		
	}*/

}