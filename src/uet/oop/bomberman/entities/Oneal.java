package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    private BombermanGame game;
    private boolean moving = false;


    public Oneal(int x, int y, Image img, Image dead_image, BombermanGame game) {
        super(x, y, img, dead_image, game);
        this.alive = true;
        ai = new AIMedium(game.bomberman, this);
//        ai = new AILow();
        direction = ai.calculate_move();
        this.game = game;
    }

    @Override
    public void detail_sprite() {
        if(alive == true) {
            switch (this.direction) {
                case 0:
                case 1:
                    if(moving) {
                        this.img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60).getFxImage();
                    } else {
                        this.img =Sprite.oneal_left1.getFxImage();
                    }
                    break;
                case 2:
                case 3:
                    if(moving) {
                        this.img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60).getFxImage();
                    } else {
                        this.img = Sprite.oneal_right1.getFxImage();
                    }
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
//            direction = ai.calculate_move();
            if (game.bomberman.x / 16 < this.x / 16 && can_move(-1, 0, game)) {
                this.direction = 3;
            } else if (this.game.bomberman.y / 16 < this.y / 16 && can_move(0, -1, game)) {
                this.direction = 0;
            } else if (this.game.bomberman.y / 16 > this.y / 16 && can_move(0, 1, game)) {
                this.direction = 2;
            } else if (game.bomberman.x / 16 > this.x / 16 && can_move(1, 0, game)) {
                this.direction = 1;
            }

//            System.out.println(direction);
//            System.out.println(moving);
            if (direction == 3 && can_move(-1, 0, game)) {
                this.x -= step + 1;
                moving = true;
            }
            else if (direction == 2 && can_move(0, 1, game)) {
                this.y += step + 1;
                moving = true;
            }
            else if (direction == 1 && can_move(1, 0, game)) {
                this.x += step + 1;
                moving = true;
            }
            else if (direction == 0 && can_move(0, -1, game)) {
                this.y -= step + 1;
                moving = true;
            } else {
                moving = false;
            }
        }
    }
}
