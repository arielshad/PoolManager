
import java.util.ArrayList;
import java.util.concurrent.Callable;





public class User {

	public static void solutionAriel(ArrayList<Integer> n_vals,  ArrayList<Integer> l_vals, int task_limit, int threads_limit, int summon_limit, int multiplay_limit){
		// set pool maneger
		PoolManeger poolManeger = PoolManeger.getInstace(task_limit, threads_limit);
		// turn pool manger on!
		poolManeger.start();		
		
		try {
			poolManeger.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < n_vals.size(); i++) {
			//get n val for expression
			int temp_nVal = n_vals.get(i);
			
			//create array of tasks
			ArrayList<Callable<Result>> taskArray  =new ArrayList<Callable<Result>>();
			
			//create the result object
			Result result = new Result(1, (int)Math.ceil(temp_nVal/multiplay_limit), temp_nVal, "1.1");
			
//			taskArray = taskArrayCreator();
			
			
		}
		
		
		
		
	}
	
	@SuppressWarnings("unused")
	private static ArrayList<Callable<Result>> taskArrayCreator(TaskObject task, int nVal, int limiter, Result result, ExpressionType type) throws InstantiationException, IllegalAccessException {
		ArrayList<Callable<Result>> taskArray  =new ArrayList<Callable<Result>>();
		
		while(nVal-limiter>0){
			TaskObject temp = task.getClass().newInstance();
			
			temp.setFrom(nVal-limiter);
			temp.setFrom(nVal);
			temp.setResult(result);
			temp.setType(type);
			
			taskArray.add(temp);
			
			
			nVal-=limiter;
		}
		//after loop create the extra move
		TaskObject temp = task.getClass().newInstance();
		temp.setFrom(1);
		temp.setFrom(nVal);
		temp.setResult(result);
		temp.setType(type);
		taskArray.add(temp);
		
		
		return taskArray;
		
		
	}
	
	
	public static void runTask11(int k, ArrayList<Integer> n_vals, int m, PoolManeger poolManeger){
		for (int i = 0; i < k; i++) {
			//get current n_val;
			int temp_nVal = n_vals.get(i);
			ArrayList<Callable<Result>> taskArray  =new ArrayList<Callable<Result>>();
			
			int numOfTasks = (int)Math.ceil(temp_nVal/(double)m);
			Result result = new Result(1, numOfTasks, temp_nVal, "1.1");
			
			//create tasks
			while(temp_nVal-m>0){
				taskArray.add(new Task1(temp_nVal-m+1, temp_nVal, result, ExpressionType.MUL));
				temp_nVal-=m;				
			}
			if (temp_nVal!=0) {
				taskArray.add(new Task1(1, temp_nVal, result, ExpressionType.MUL));
			}			
			
			//now start feeder for each task
			for (int j = 0; j < taskArray.size(); j++) {
				Feeder feed = new Feeder(poolManeger, taskArray.get(j));
				feed.start();				
			}
		}
	}
	
	public static void runTask12(int r, ArrayList<Integer> l_vals, int m, int s, PoolManeger poolManeger){
		for (int i = 0; i < r; i++) {
			//get current n_val;
			int temp_nVal = l_vals.get(i);
			ArrayList<Callable<Result>> taskArray  =new ArrayList<Callable<Result>>();
			
			int numOfTasks = (int)Math.ceil(temp_nVal/(double)m);
			int numOfTasksSum = (int)Math.ceil(temp_nVal/(double)s);
			
			Result result = new Result(1, numOfTasks+numOfTasksSum, temp_nVal, "1.2");
			
			//create tasks
			while(temp_nVal-m>0){
				taskArray.add(new Task2(temp_nVal-m+1, temp_nVal, result, ExpressionType.MUL));
				temp_nVal-=m;				
			}
			if (temp_nVal!=0) {
				taskArray.add(new Task2(1, temp_nVal, result, ExpressionType.MUL));
			}			
			
			//now start feeder for each task
			for (int j = 0; j < taskArray.size(); j++) {
				Feeder feed = new Feeder(poolManeger, taskArray.get(j));
				feed.start();				
			}
			
			//this thread needs to wait before starting second part
			while (result.currentResult()>numOfTasksSum);
			
			//second part
			//create tasks
			taskArray.clear();
			
			temp_nVal = l_vals.get(i);
			while(temp_nVal-s>0){
				taskArray.add(new Task3(temp_nVal-s+1, temp_nVal, result, ExpressionType.SUM));
				temp_nVal-=s;				
			}
			if (temp_nVal!=0) {
				taskArray.add(new Task3(1, temp_nVal, result, ExpressionType.SUM));
			}			
			
			//now start feeder for each task
			for (int j = 0; j < taskArray.size(); j++) {
				Feeder feed = new Feeder(poolManeger, taskArray.get(j));
				feed.start();				
			}
			
			
			
			
			
		}
	}

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
		PoolManeger poolManeger = PoolManeger.getInstace(t, p);
		//turn pool manger on!
		poolManeger.start();
		
		//start feeding
		//expression 1.1
		//for each n_val
		runTask11(k,n_vals,m, poolManeger);
		runTask12(r, l_vals, m,s,poolManeger);
		
		
		

		
		
	}
	
	public static void main(String[] args) {
		//set pool maneger
		ArrayList<Integer> n_vals = new ArrayList<>();
		n_vals.add(20);
		n_vals.add(10);
		
		ArrayList<Integer> l_vals = new ArrayList<>();
		l_vals.add(2);
		l_vals.add(9);

		//7.625979004892141E-26
		solution(2, 2, n_vals, l_vals, l_vals, 4, 2, 6, 3);
	
		int task_limit=4;
		int threads_limit=5;
		int summon_limit=2;
		int multiplay_limit=3;
		
//		solutionAriel(n_vals, l_vals, task_limit, threads_limit, summon_limit, multiplay_limit);
	
		
		
	}

}




