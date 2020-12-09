package uet.oop.bomberman.entities;

public class AILow extends AI {

    @Override
    public int calculate_move() {
        return random.nextInt(4);
    }

}
