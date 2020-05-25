package lab14;

public class AcceleratingSawToothGenerator extends SawToothGenerator {
    private double rate;
    public AcceleratingSawToothGenerator(int period, double rate){
        super(period);
        this.rate = rate;
    }
    @Override
    public double next(){
        state += 1;
        if (state == period){
            state = 0;
            period *= rate;
        } else {
            state%=period;
        }
        return normalize();
    }
}
