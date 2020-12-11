package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class Animated extends Entity{
    protected int animate = 0;
    protected int max_animate = 7500;
    protected int step = 3;
    protected boolean alive;
    protected int level_bomb = 1;

    public Animated(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected void animate() {
        if(animate < max_animate) animate++;
        else animate = 0;
    }
    public void up_level_bomb() {
        level_bomb = 2;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void update() {}

    public boolean collide(Entity e) {
        return true;
    }

    public abstract void killed();
}
