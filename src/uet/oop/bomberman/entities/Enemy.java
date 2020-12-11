package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.CacheDataLoader;

import java.util.Random;


public  abstract class Enemy extends Animated{
    protected int timeFordie = 30;
    protected int timeAfterDie = 15;
    private Image dead_image;
    protected int direction = -1;
    protected AI ai;
    private BombermanGame game;
    private boolean moving = false;
    private int timeToChange = 500;

    public Enemy(int x, int y, Image img, Image dead_image, BombermanGame game) {
        super(x,y, img);
        this.dead_image = dead_image;
        this.game = game;
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
                else {
                    game.removeEnemy(this);
                    removed();
                }
            }
        }

        if(alive == true) {
//            direction = 3;
//            if (direction == 3 && can_move(-1, 0, game)) {
//                this.x -= step;
//                moving = true;
//            }
//            if (direction == 2 && can_move(0, 1, game)) {
//                this.y += step;
//                moving = true;
//            }
//            if (direction == 1 && can_move(1, 0, game)) {
//                this.x += step;
//                moving = true;
//            }
//            if (direction == 0 && can_move(0, -1, game)) {
//                this.y -= step;
//                moving = true;
//            }
        }
    }

    public void changeDir() {
        int dir = new Random().nextInt(4);
        direction = dir;
    }

    public boolean can_move(int x0, int y0, BombermanGame game) {
        int dX = 0, dY = 0;
        if(x0 > 0) dX = 40;
        if(y0 > 0) dY = 40;
            double x_ = (x0 * step + this.x + dX) / Sprite.SCALED_SIZE;
            double y_ = (y0 * step + this.y + dY) / Sprite.SCALED_SIZE;

            Entity x = game.getObjectAt((int)x_, (int)y_);
            timeToChange--;
            if(x != null) {
                if (!x.collide(this) || timeToChange < 0) {
                    changeDir();
                    timeToChange = 500;
                    return false;
                }
            }
            x = game.getCharacter((int)x_, (int)y_);
            if (x != null) {
                x.collide(this);
            }
        return true;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
        if(alive == true) {
            detail_sprite();
        }
        else {
            if(timeAfterDie > 0) {
                this.img = dead_image;
            }
            else {
                this.img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60).getFxImage();

            }

        }
    }


    @Override
    public void killed() {
        this.alive = false;
        BombermanGame.enemyDead.stop();
        BombermanGame.enemyDead.play();
    }

    public abstract void detail_sprite();

    @Override
    public boolean collide(Entity e) {
        if (e instanceof DirectionBomb) {
            killed();
            return false;
        }

        if (e instanceof Bomber) {
            ((Bomber)e).killed();
            return false;
        }

        return true;
    }
}
