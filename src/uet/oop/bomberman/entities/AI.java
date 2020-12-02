package uet.oop.bomberman.entities;

import java.util.Random;

public abstract class AI {
    protected Random random = new Random();

    public abstract int calculate_move();
}
