package uet.oop.bomberman.entities;

import org.omg.IOP.ENCODING_CDR_ENCAPS;
import uet.oop.bomberman.graphics.Sprite;

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

        int vertical = random.nextInt(2);

        if (vertical == 1) {
            int v = caculateRow();
            if (v == -1) {
                return caculateCol();
            } else {
                return v;
            }
        } else {
            int h =caculateCol();
            if (h == -1) {
                return caculateRow();
            } else {
                return h;
            }
        }
    }
    protected int caculateCol() {
        if (this.bomber.getX() < e.getX()) {
            return 3;
        } else if (this.bomber.getX() > e.getX()) {
            return 1;
        }
        return -1;
    }
    protected int caculateRow() {
        if (this.bomber.getY() < e.getY()) {
            return 0;
        } else if (this.bomber.getY() > e.getY()) {
            return 2;
        }
        return -1;
    }

}
