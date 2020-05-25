package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    int period;
    int state;
    
    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }
    
    @Override
    public double next() {
        state += 1;
        state %= period;
        return normalize();
    }
    
    double normalize() {
        return (float) state * 2 / period - 1;
    }
}
