public class Combine {
    public static int combine(ComFunc f, int[] x){
        if (x.length == 0){
            return 0;
        }
        if (x.length == 1){
            return x[0];
        }
        int res = x[0];
        for (int i =1;i<x.length;i+=1){
            res = f.apply(res,x[i]);
        }
        return res;
    }
    
    public static void main(String[] args) {
        int[] x = {};
        Add f = new Add();
        System.out.println(combine(f, x));
    }
}
