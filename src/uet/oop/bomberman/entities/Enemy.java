package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public  abstract class Enemy extends Animated{
    private int timeFordie = 30;
    private int timeAfterDie = 15;
    private Image dead_image;
    protected int direction = -1;

    public Enemy(int x, int y, Image img, Image dead_image) {
        super(x,y, img);
        this.dead_image = dead_image;
    }

    @Override
    public void update() {
        animate();

        if(alive == false) {
            if(timeAfterDie > 0) timeAfterDie--;
            else {
                if (timeFordie > 0) {
                    timeFordie--;
                }
                else removed();
            }
        }

        if(alive == true) {

        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if(alive == true) {
            detail_sprite();
        }
        else {
            if(timeAfterDie > 0) this.img = dead_image;
            else
                this.img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60).getFxImage();
        }
    }


    @Override
    public void killed() {
        this.alive = false;
    }

    public abstract void detail_sprite();

    @Override
    public boolean collide(Entity e) {
        if(e instanceof DirectionBomb) {
            killed();
            return false;
        }

        if(e instanceof Bomber) {
            ((Bomber)e).killed();
            return false;
        }
        return true;
    }
}
