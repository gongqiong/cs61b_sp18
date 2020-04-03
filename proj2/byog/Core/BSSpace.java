package byog.Core;

import java.io.Serializable;
import java.util.Random;

public class BSSpace implements Serializable {
    private static final long serialVersionUID = 123123123123123L;
    
    private static final int MIN_SIZE = 6;
    private int width;
    private int height;
    private Position pos;
    BSSpace leftSpace;
    BSSpace rightSpace;
    Room room;
    
    public BSSpace(int width, int height, Position pos) {
        this.width = width;
        this.height = height;
        this.pos = pos;
        leftSpace = null;
        rightSpace = null;
        room = null;
    }
    
    boolean split(Random random) {
        if (leftSpace != null) {
            return false;
        }
        if (width < 2 * MIN_SIZE && height < 2 * MIN_SIZE) {
            return false;
        }
        if (width > height) {
            int leftWidth = Math.max(MIN_SIZE, RandomUtils.uniform(random, width - MIN_SIZE + 1));
            leftSpace = new BSSpace(leftWidth, height, pos);
            Position rightPos = new Position(pos.getX() + leftWidth - 1, pos.getY());
            rightSpace = new BSSpace(width - leftWidth, height, rightPos);
        } else {
            int bottomHeight = Math.max(MIN_SIZE,
                    RandomUtils.uniform(random, height - MIN_SIZE + 1));
            leftSpace = new BSSpace(width, bottomHeight, pos);
            Position topPos = new Position(pos.getX(), pos.getY() + bottomHeight - 1);
            rightSpace = new BSSpace(width, height - bottomHeight, topPos);
        }
        return true;
    }
    
    void buildRoom(Random random) {
        if (leftSpace != null) {
            leftSpace.buildRoom(random);
            rightSpace.buildRoom(random);
        } else {
            int offsetX = RandomUtils.uniform(random, 0, width - MIN_SIZE + 1);
            int offsetY = RandomUtils.uniform(random, 0, height - MIN_SIZE + 1);
            Position start = new Position(pos.getX() + offsetX, pos.getY() + offsetY);
            int roomWidth = RandomUtils.uniform(random, width - offsetX - 1);
            int roomHeight = RandomUtils.uniform(random, height - offsetY - 1);
            room = new Room(start,
                    Math.max(MIN_SIZE - 2, roomWidth), Math.max(MIN_SIZE - 2, roomHeight));
        }
    }
}
