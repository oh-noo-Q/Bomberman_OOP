package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity{
    private int max_animate = 7500;
    private int animate = 0;
    private boolean destroyed;
    private int timeToDestroy = 30;

    public Brick(int x, int y) {
        super(x, y, Sprite.brick.getFxImage());
    }

    @Override
    public void update() {
        if(destroyed) {
            if(animate < max_animate) animate++;
            else animate = 0;
            if(timeToDestroy > 0) timeToDestroy--;
            else removed();
        }
    }
    public void render(GraphicsContext gc) {
        if(destroyed) {
            this.img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 20).getFxImage();
        }
        else {
            this.img = Sprite.brick.getFxImage();
        }
        gc.drawImage(img, x, y);
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof DirectionBomb) {
            destroy();
        }
        return false;
    }

    public void destroy() {
        this.destroyed = true;
    }
}
