
package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), this);
    private List<Brick> bricks = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        entities.add(bomberman);
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                if(bomberman.isRemove() == true) {
                    if(entities.size() > 0)
                        entities.remove(0);
                }
                if(entities.size() == 0) {
                    reset_player();
                }
            }
        };
        timer.start();
        bomberman.input(scene);
        createMap();

    }

    public void createMap() {
        try {
            FileInputStream map_file = new FileInputStream("res/levels/Level1.txt");
            Scanner scan = new Scanner(map_file);
            for(int i = 0; i < HEIGHT; i++) {
                String s = scan.nextLine();
                for(int j = 0; j < 31; j++) {
                    char x = s.charAt(j);
                    Entity object = null;
                    if(x == '#') {
                        object = new Wall(j, i);
                    }
                    else {
                        object = new Grass(j, i);
                    }
                    if(x == '*') {
                        Brick b = new Brick(j, i);
                        bricks.add(b);
                    }
                    stillObjects.add(object);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        bricks.forEach(Brick::update);
        if(bricks.size() > 0) {
            for(int i = 0; i < bricks.size(); i++) {
                if(bricks.get(i).isRemove() == true) {
                    bricks.remove(i);
                    break;
                }
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));

    }

    public Entity getObjectAt(int x, int y) {
        Iterator<Brick> br = bricks.iterator();
        Entity b;
        while(br.hasNext()) {
            b = br.next();
            if(b.getX() / Sprite.SCALED_SIZE == x && b.getY() / Sprite.SCALED_SIZE == y) {
                return b;
            }
        }

        Entity bomb_ = bomberman.getBombAt(x, y);
        if(bomb_ != null) return bomb_;

        Iterator<Entity> a =stillObjects.iterator();
        Entity barrier;
        while(a.hasNext()) {
            barrier = a.next();
            if(barrier.getX() / Sprite.SCALED_SIZE == x && barrier.getY() / Sprite.SCALED_SIZE == y) {
                return barrier;
            }
        }

        return null;
    }

    public Entity getCharacter(int x, int y) {
        Iterator<Entity> c = entities.iterator();
        Entity p;
        while(c.hasNext()) {
            p = c.next();
            if(p.getX() / Sprite.SCALED_SIZE == x && p.getY() / Sprite.SCALED_SIZE == y) {
                return p;
            }
            if(p.getX() / Sprite.SCALED_SIZE < x) {
                if((p.getX() + 32) / Sprite.SCALED_SIZE == x && p.getY() / Sprite.SCALED_SIZE == y) {
                    return p;
                }
            }
            if(p.getY() / Sprite.SCALED_SIZE < y ) {
                if(p.getX() / Sprite.SCALED_SIZE == x && (p.getY() + 32) / Sprite.SCALED_SIZE == y) {
                    return p;
                }
            }

        }

        return null;
    }

    public void move_map() {
        for(int i = 0; i < stillObjects.size(); i++) {
            int x = stillObjects.get(i).getX();
            stillObjects.get(i).setX(x - 32);
        }
        for(int i = 0; i < bricks.size(); i++) {
            int x = bricks.get(i).getX();
            bricks.get(i).setX(x - 32);
        }
    }

    public void reset_player() {
        bomberman.reset();
        entities.add(bomberman);
    }
}
