package uet.oop.bomberman.entities.Powerup;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Speed extends Powerup{
    public Speed (int x, int y) {
        super(x, y, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber) {
            ((Bomber) e).up_speed();
            removed();
            return true;
        }
        return false;
    }
}
