package states;

import gameObjects.MovingObject;
import gameObjects.Player;
import graphics.Assets;
import math.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class GameState {
    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();


    public GameState() {
        player = new Player(new Vector2D(100,500), new Vector2D(), 7, Assets.player, this);
        movingObjects.add(player);
    }

    public void update(){
        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).update();
        }
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
