package uet.oop.bomberman.entities.Powerup;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flameup extends Powerup{

    public Flameup(int x, int y) {
        super(x, y, Sprite.powerup_flames.getFxImage());
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber) {
            ((Bomber) e).up_flame();
            removed();
            return true;
        }
        return false;
    }
}
