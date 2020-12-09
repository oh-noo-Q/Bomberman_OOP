package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    private BombermanGame game;
    private boolean moving = true;


    public Oneal(int x, int y, Image img, Image dead_image, BombermanGame game) {
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
                    this.img = Sprite.oneal_right1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60).getFxImage();
                    break;
                case 1:
                    this.img = Sprite.oneal_right1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60).getFxImage();
                    break;
                case 2:
                    this.img = Sprite.oneal_left1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60).getFxImage();
                    break;
                case 3:
                    this.img = Sprite.oneal_right1.getFxImage();
                    if(moving) this.img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60).getFxImage();
                    break;
            }
        }
        else {
            this.img = Sprite.oneal_dead.getFxImage();
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
         // direction = ai.calculate_move();
//            if (game.bomberman.x /32 < this.x / 32) {
//                this.direction = 3;
//            } else if (game.bomberman.x / 32 > this.x / 32) {
//                this.direction = 1;
//            } else if (this.game.bomberman.y / 32 < this.y / 32) {
//                this.direction = 0;
//            } else if (this.game.bomberman.y / 32 > this.y / 32) {
//                this.direction = 2;
//            }

//            System.out.println(direction);
            if (direction == 3 && can_move(-1, 0, game)) {
                this.x -= step;
                moving = true;
            }
            if (direction == 2 && can_move(0, 1, game)) {
                this.y += step;
                moving = true;
            }
            if (direction == 1 && can_move(1, 0, game)) {
                this.x += step;
                moving = true;
            }
            if (direction == 0 && can_move(0, -1, game)) {
                this.y -= step;
                moving = true;
            }
        }
    }
}
