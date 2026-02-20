package states;

import gameObjects.*;
import graphics.Assets;
import math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameState {
    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
    private int meteore;


    public GameState() {
        player = new Player(new Vector2D(100,500), new Vector2D(), 7, Assets.player, this);
        movingObjects.add(player);
        meteore = 1;
        startWave();
    }
    private void startWave() {
        double x, y;
        for (int i = 0; i < meteore; i++) {
          x = i % 2 == 0 ? Math.random()* Constants.WIDTH : 0;
          y = i % 2 == 0 ? 0 : Math.random()* Constants.HEIGHT;

          BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
          movingObjects.add(new Meteor(
                  new Vector2D(x,y),
                  new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
                  Constants.METEOR_VEL*Math.random() + 1,
                  texture,
                  this,
                  Size.BIG
          ));
        }
        meteore ++;
    }

    public void update(){
        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).update();
        }
        for (int i = 0; i < movingObjects.size(); i++) {
            if (movingObjects.get(i)instanceof Meteor) {
                return;
            }
        }
        startWave();
    }
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).draw(g);
        }
    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }
}
