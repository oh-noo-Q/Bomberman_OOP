package uet.oop.bomberman.entities;

import org.omg.IOP.ENCODING_CDR_ENCAPS;

public class AIMedium extends AI {
    Bomber bomber;
    Enemy e;

    public AIMedium(Bomber bomber, Enemy enemy) {
        this.bomber = bomber;
        this.e = enemy;
    }

    @Override
    public int calculate_move() {
        if(bomber == null)
            return random.nextInt(4);

        if (this.bomber.x < this.e.x) {
            this.e.direction = 3;
        } else if (this.bomber.x > this.e.x) {
            this.e.direction = 1;
        } else if (this.bomber.y < this.e.y) {
            this.e.direction = 0;
        } else if (this.bomber.y > this.e.y) {
            this.e.direction = 2;
        }
        return 0;
    }
}
