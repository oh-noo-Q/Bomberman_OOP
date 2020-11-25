package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;

public class DirectionBomb extends Entity{

    private boolean last = true;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public DirectionBomb(int x, int y, int direction, boolean last) {
        super(x, y, Sprite.explosion_vertical2.getFxImage());
        this.last = last;

        if(direction == 0) {
            if(last == true) {
                this.img = Sprite.explosion_vertical_top_last2.getFxImage();
            }
            else this.img = Sprite.explosion_vertical2.getFxImage();
        }

        if(direction == 1) {
            if(last == true) {
                this.img = Sprite.explosion_horizontal_right_last2.getFxImage();
            }
            else this.img = Sprite.explosion_horizontal2.getFxImage();
        }
        if(direction == 2) {
            if(last == true) {
                this.img = Sprite.explosion_vertical_down_last2.getFxImage();
            }
            else this.img = Sprite.explosion_vertical2.getFxImage();
        }
        if(direction == 3) {
            if(last == true) {
                this.img = Sprite.explosion_horizontal_left_last2.getFxImage();
            }
            else this.img = Sprite.explosion_horizontal2.getFxImage();
        }
    }

    @Override
    public void update() {

    }

    public boolean collide(Entity e) {
        if(e instanceof Bomber) {
            ((Bomber) e).killed();
        }
        return true;
    }
}
