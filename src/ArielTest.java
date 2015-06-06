public class ArielTest {
	public static void main(String[] args) {
		double sum = 1;
		
		for (int i = 1; i <= 20; i++) {
			sum *= ( Math.pow(-1, i) / ( 2*i+1 ) );
			
		}
		System.out.println("sum n=20 1.1: "+sum);
		
sum=1;
		for (int i = 1; i <= 10; i++) {
			sum *= ( Math.pow(-1, i) / ( 2*i+1 ) );
			
		}
		
		System.out.println("sum n=10 1.1: "+sum);

		
		
		sum=1;
		for (int i = 1; i <= 20; i++) {
			sum*= (Math.pow(-1, 3*i) / (2*(i+1)+1));
		}
		
		for (int i = 1; i <= 20; i++) {
			sum+=i/(2*Math.pow(i, 2)+1);
		}
/*
 * Expr. type 1.1, n = 10: -7.313315179042153E-15
 * Expr. type 1.1, n = 20: 4.124942058622729E-37
 * */
		System.out.println(sum);
	}
}
