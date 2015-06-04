import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.omg.CORBA.NVList;



public class User {

	
	/**
	 * Solution.
	 *
	 * @param k the number of 1.1 expressions
	 * @param r the number of 1.2 expressions
	 * @param n_vals the n_vals array of k values for the for expression  1.1 
	 * @param l_vals the l_vals array of r values for the multiplay part of 1.2
	 * @param m_vals the m_vals array of r values for the summary part of 1.2
	 * @param t the t size of the tasks-queue of the PoolManeger
	 * @param s the s maximum number of summands that pool-thread can handle
	 * @param m the m maximum number of multiplicands the pool-thread can handle
	 * @param p the the number of PoolThreads that the PoolMangers creates
	 */
	public static void solution(int k, int r, ArrayList<Integer> n_vals,  ArrayList<Integer> l_vals, ArrayList<Integer> m_vals, int t, int s, int m, int p){
		//set pool maneger
		int need_task = 0;
		PoolManeger poolManeger = PoolManeger.getInstace(t, p);
		//turn pool manger on!
		poolManeger.start();
		
		//start feeding
		//expression 1.1
		//for each n_val
		for (int i = 0; i < k; i++) {
			//get current n_val;
			int temp_nVal = n_vals.get(i);
			ArrayList<Callable<Result>> taskArray  =new ArrayList<Callable<Result>>();
			
			Result result = new Result(1, (int)Math.ceil(temp_nVal/m), temp_nVal, "1.1");
			//create tasks
			while(temp_nVal-m>0){
				taskArray.add(new Task1(temp_nVal-m, temp_nVal-m, result));
				temp_nVal-=m;
			}
			taskArray.add(new Task1(1, temp_nVal, result));
			
			//now start feeder for each task
			for (int j = 0; j < taskArray.size(); j++) {
				Feeder feed = new Feeder(poolManeger, taskArray.get(j));
				feed.start();				
			}
		}
		
		
		for (int i = 0; i < r; i++) {
			int temp_l_val = l_vals.get(i);
			int temp_m_val = m_vals.get(i);
			int numOfNeededThreads = (int)Math.ceil(temp_l_val/m);
			
			numOfNeededThreads+=(int)Math.ceil(temp_m_val/s);
			
			ArrayList<Callable<Result>> taskArray  =new ArrayList<Callable<Result>>();
			Result result = new Result(1, numOfNeededThreads, temp_l_val, "1.2");
			
			while(temp_l_val-m>0){
				taskArray.add(new Task2(temp_l_val-m, temp_l_val-m, result));
				temp_l_val-=m;
			}
			taskArray.add(new Task2(1, temp_l_val, result));
			
			
			while(temp_m_val-s>0){
				taskArray.add(new Task3(temp_m_val-s, temp_m_val-s, result));
				temp_m_val-=s;
			}
			taskArray.add(new Task3(1, temp_m_val, result));
			
			//now start feeder for each task
			for (int j = 0; j < taskArray.size(); j++) {
				Feeder feed = new Feeder(poolManeger, taskArray.get(j));
				feed.start();				
			}
			
		}

		
		
	}
	
	public static void main(String[] args) {
		//set pool maneger
		ArrayList<Integer> nvals = new ArrayList<>();
		nvals.add(20);
		nvals.add(10);
		
		ArrayList<Integer> lvals = new ArrayList<>();
		lvals.add(2);
		lvals.add(2);
		
		
		ArrayList<Integer> mvals = new ArrayList<>();
		mvals.add(2);
		mvals.add(2);
		
		solution(2, 2, nvals, lvals, mvals, 4, 1, 2, 10);
		
		/*
		PoolManeger poolManeger = PoolManeger.getInstace(1, 1);
		
		poolManeger.start();


		for (int i = 0; i < 4; i++) {
//			poolManeger.addTask(task);
			
		}
		
		try {
			poolManeger.join();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
	}

}

//tester
class Ex1 implements Callable<Double>{
	private int from;
	private int to;

	@Override
	public Double call() throws Exception {
		
		Thread.sleep(1000);
		System.out.println("finished thread!!");
		return 0.0;
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
	
}



