package uet.oop.bomberman.entities.Powerup;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Powerup{
    public Portal(int x, int y) {
        super(x, y, Sprite.portal.getFxImage());
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber) {

        }

        return false;
    }
}
