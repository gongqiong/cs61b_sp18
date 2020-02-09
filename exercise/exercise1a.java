public class exercise1a{
	public static void main(String[] args){
		for (int i=1;i<=5;i++) {
			for (int j=1;j<i;j++) {
				System.out.print("*");
			}
			System.out.println("*");
		}
		int row = 0;
		int SIZE = 5;
		while (row<SIZE){
			int col = 0;
			while (col<row){
				System.out.print('*');
				col++;
			}
			System.out.println("*");
			row++;
		}
	}
}