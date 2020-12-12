package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class Bomber extends Animated {

    private List<Bomb> bombs= new ArrayList<>();
    private int max_bomb = 1;
    private int timeForDie = 30;
    private int direction = 1;
    private boolean moving = false;
    private BombermanGame game;
    private List<String> inputList = new ArrayList<>();

    public Bomber(int x, int y, Image img, BombermanGame game) {
        super( x, y, img);
        this.alive = true;
        this.game = game;
    }

    @Override
    public void update() {
        animate();
        if(bombs.size() > 0) {
            for(int i = 0; i < bombs.size(); i++) {
                if(bombs.get(i).isRemove() == true) {
                    bombs.remove(i);
                }
            }
        }
        controll();
        if(alive == false) {
            if(timeForDie > 0) timeForDie--;
            else {
                removed();
            }
        }
    }

    public void input(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            String keyName = event.getCode().toString();
            if(!inputList.contains(keyName)) {
                inputList.add(keyName);
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            String keyName = event.getCode().toString();
            inputList.remove(keyName);
            moving = false;
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if(key.getCode() == KeyCode.SPACE) {
                if (bombs.size() < max_bomb) {
                    int x = (this.x + 16) / Sprite.SCALED_SIZE;
                    int y = (this.y + 16) / Sprite.SCALED_SIZE;
                    Bomb b = new Bomb(x, y, this.game, level_bomb);
                    bombs.add(b);
                }
            }
        });
    }

    public boolean can_move(int x0, int y0, BombermanGame game) {
        for(int i = 0; i < 4; i++) {
            double x_ = (x0 * step + this.x + i % 2 * 20 + 8) / Sprite.SCALED_SIZE;
            double y_ = (y0 * step + this.y + i / 2 * 30 + 12) / Sprite.SCALED_SIZE;

            Entity x = game.getObjectAt((int)x_, (int)y_);
            if(x != null) {
                if (!x.collide(this)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void render(GraphicsContext gc) {
        detail_sprite();
        if (bombs.size() != 0) {
            for(int i = 0; i < bombs.size(); i++) {
                bombs.get(i).update();
                bombs.get(i).render(gc);
            }
        }
        super.render(gc);
    }

    public void killed() {
        this.setAlive(false);
        BombermanGame.bomberDead.stop();
        BombermanGame.bomberDead.play();
    }

    @Override
    public boolean collide(Entity e) {

        if (e instanceof DirectionBomb) {
            killed();
            return false;
        }
        if (e instanceof Enemy) {
            killed();
            return false;
        }

        return true;
    }

    public void controll() {
        if(this.alive == true) {
            if (inputList.contains("RIGHT")) {
                direction = 1;
                if (can_move(1, 0, game)) {
                    this.x += step;
                }
                moving = true;
            }
            if (inputList.contains("LEFT")) {
                direction = 3;
                if (can_move(-1, 0, game)) {
                    this.x -= step;
                }
                moving = true;
            }
            if (inputList.contains("UP")) {
                direction = 0;
                if (can_move(0, -1, game)) {
                    this.y -= step;
                }
                moving = true;
            }
            if (inputList.contains("DOWN")) {
                direction = 2;
                if (can_move(0, 1, game)) {
                    this.y += step;
                }
                moving = true;
            }

            if (inputList.contains("SPACE")) {
                BombermanGame.bomSet.stop();
                BombermanGame.bomSet.play();
            }

        }
    }
    public void detail_sprite() {
        if(alive == true) {
            switch (this.direction) {
                case 0:
                    this.img = Sprite.player_up.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20).getFxImage();
                    break;
                case 1:
                    this.img = Sprite.player_right.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20).getFxImage();
                    break;
                case 3:
                    this.img = Sprite.player_left.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20).getFxImage();
                    break;
                case 2:
                    this.img = Sprite.player_down.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20).getFxImage();
                    break;
            }
        }
        else {
            this.img = Sprite.player_dead2.getFxImage();
        }
    }

    public Entity getBombAt(int x0, int y0) {
        if(bombs.size() > 0) {
            for(int i = 0; i < bombs.size(); i++) {
                if(bombs.get(i).getX() / Sprite.SCALED_SIZE == x0 && bombs.get(i).getY() / Sprite.SCALED_SIZE == y0)
                    return bombs.get(i);
            }
        }
        return null;
    }

    public void reset() {
        this.setX(48);
        this.setY(48);
        this.alive = true;
        timeForDie = 30;
        this.setRemove(false);
    }

    public void up_speed() {
        this.step++;
    }

    public void up_bomb_size() {
        this.max_bomb = 2;
    }

    public void up_flame() {
        up_level_bomb();
    }
}
