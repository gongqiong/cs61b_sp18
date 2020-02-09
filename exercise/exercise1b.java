public class exercise1b {
   public static void drawTriangle(int N){
         for (int row=0; row<N; row++){
            for (int col=0; col<row; col++){
               System.out.print('*');
            }
            System.out.println('*');
         }
   }
   public static void main(String[] args) {
      drawTriangle(10);
   }
}