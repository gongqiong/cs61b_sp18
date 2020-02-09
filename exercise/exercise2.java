public class exercise2{
	/** Return the maximum value of m. */
	public static int max(int[] m){
		int mValue=0;
		for (int i=0; i<m.length; i++){
			if (m[i]>mValue){
				mValue = m[i];
			}
		}
		return mValue;
	}
	public static void main(String[] args) {
       	int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
		System.out.print(max(numbers));      
      }
}