package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y, Sprite.wall.getFxImage());
    }

    @Override
    public void update() {

    }

    public boolean collide(Entity e) {
        return false;
    }
}
