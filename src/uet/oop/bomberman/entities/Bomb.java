package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Animated{

    private int timeToExplode = 120;
    private int timeForExplode = 30;
    private boolean _exploded = false;
    private int lvlbomb = 1;
    private DirectionBomb[] Explosion = null;
    private BombermanGame game;

    public Bomb(int x, int y, BombermanGame game) {
        super(x, y, Sprite.bomb.getFxImage());
        this.game = game;
    }

    public int getLvlbomb() {
        return lvlbomb;
    }

    public void setLvlbomb(int lvlbomb) {
        this.lvlbomb = lvlbomb;
    }

    public void killed() {}
    @Override
    public void update() {
        animate();
        if(timeToExplode > 0) {
            timeToExplode--;
        }
        else {
            if(!_exploded) {
                explosion();
            }

            if(timeForExplode > 0) {
                timeForExplode--;
            }
            else removed();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if(_exploded) {
            img = Sprite.bomb_exploded2.getFxImage();
            if(Explosion.length > 0) {
                for(int i = 0; i < Explosion.length; i++) {
                    if(Explosion[i] != null) {
                        Explosion[i].render(gc);
                        int x0 = Explosion[i].getX() / Sprite.SCALED_SIZE;
                        int y0 = Explosion[i].getY() / Sprite.SCALED_SIZE;
                        Entity x = game.getCharacter(x0, y0);
                        if (x != null) {
                            x.collide(Explosion[i]);
                        }
                        x = game.getEnemy(x0, y0);
                        if (x != null) {
                            x.collide(Explosion[i]);
                        }
                    }
                }
            }
        }
        else {
            this.img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60).getFxImage();
        }
        gc.drawImage(this.img, (double)this.x, (double)this.y);
    }

    public void explosion() {
        _exploded = true;

        Animated b = (Animated) game.getCharacter(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE);
        if(b != null) {
            b.killed();
        }

        if(Explosion == null) {
            Explosion = new DirectionBomb[lvlbomb * 4];
            for (int i = 0; i < Explosion.length; i++) {
                boolean last = true;
                int x_ = this.x / Sprite.SCALED_SIZE;
                int y_ = this.y / Sprite.SCALED_SIZE;
                switch (i) {
                    case 0:
                        y_--;
                        break;
                    case 1:
                        x_++;
                        break;
                    case 2:
                        y_++;
                        break;
                    case 3:
                        x_--;
                        break;
                }
                DirectionBomb direction_ = new DirectionBomb(x_, y_, i, last);
                Entity q = game.getObjectAt(x_, y_);
                if(q != null) {
                    if(q.collide(direction_)) {
                        Explosion[i] = direction_;
                    }
                }
            }
        }
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber) {
            double dX = (e.getX() - this.getX()) ;
            double dY = (e.getY() - this.getY()) ;

            if(!(dX >= -24 && dX <= 40 && dY >= -42 && dY <= 30)) {
                return false;
            }
            return true;
        }

        if(e instanceof DirectionBomb) {
            timeToExplode = 0;
            return true;
        }

        return false;
    }

}
