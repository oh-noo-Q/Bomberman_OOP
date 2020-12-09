package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    private BombermanGame game;
    private boolean moving = false;

    public Balloom(int x, int y, Image img, Image dead_image, BombermanGame game) {
        super(x, y, img, dead_image, game);
        this.alive = true;
        ai = new AILow();
        direction = ai.calculate_move();
        this.game = game;

    }


    @Override
    public void detail_sprite() {
        if(alive == true) {
            switch (this.direction) {
                case 0:
                    this.img = Sprite.balloom_right1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60).getFxImage();
                    break;
                case 1:
                    this.img = Sprite.balloom_right1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60).getFxImage();
                    break;
                case 2:
                    this.img = Sprite.balloom_left1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60).getFxImage();
                    break;
                case 3:
                    this.img = Sprite.balloom_left1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60).getFxImage();
                    break;
            }
        }
        else {
            this.img = Sprite.balloom_dead.getFxImage();
        }
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
            //direction = ai.calculate_move();
            if (direction == 3 && can_move(-1, 0, game)) {
                this.x -= step - 1;
                moving = true;
            }
            if (direction == 2 && can_move(0, 1, game)) {
                this.y += step - 1;
                moving = true;
            }
            if (direction == 1 && can_move(1, 0, game)) {
                this.x += step - 1;
                moving = true;
            }
            if (direction == 0 && can_move(0, -1, game)) {
                this.y -= step - 1;
                moving = true;
            }
        }
    }
}
