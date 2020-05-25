package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;
    
    public Clorus(double e) {
        super("clorus");
        energy = e;
        r = 34;
        g = 0;
        b = 231;
    }
    
    public Color color() {
        return color(r, g, b);
    }
    
    public void attack(Creature c) {
        energy += c.energy();
    }
    
    public void move() {
        energy -= 0.03;
    }
    
    public void stay() {
        energy += 0.01;
    }
    
    public Clorus replicate() {
        energy *= 0.5;
        return new Clorus(energy);
    }
    
    /**
     * Cloruses take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if any Plips, attack one of them randomly.
     * 3. Otherwise, if any energy >= 1, REPLICATE.
     * 4. Otherwise,  MOVE to a random empty square.
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (plips.size() > 0) {
            Direction attackDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, attackDir);
        }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        return new Action(Action.ActionType.MOVE, moveDir);
    }
    
}
