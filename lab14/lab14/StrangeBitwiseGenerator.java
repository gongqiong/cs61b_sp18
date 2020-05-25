package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    int period;
    int state;
    
    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }
    
    @Override
    public double next() {
        state += 1;
        int weirdState = state & (state >> 7) % period;
        return normalize(weirdState);
    }
    
    double normalize(int weirdState) {
        return (float) weirdState * 2 / period - 1;
    }
}
