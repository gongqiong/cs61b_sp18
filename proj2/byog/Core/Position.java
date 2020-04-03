package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = 123123123123123L;
    
    private int X;
    private int Y;
    
    public Position(int x, int y) {
        X = x;
        Y = y;
    }
    
    public int getX() {
        return X;
    }
    
    public int getY() {
        return Y;
    }
}
