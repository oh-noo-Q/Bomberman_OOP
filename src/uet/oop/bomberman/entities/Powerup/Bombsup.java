package uet.oop.bomberman.entities.Powerup;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bombsup extends Powerup{
    public Bombsup(int x, int y) {
        super(x, y, Sprite.powerup_bombs.getFxImage());
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber) {
            ((Bomber) e).up_bomb_size();
            removed();
            return true;
        }
        return false;
    }
}
