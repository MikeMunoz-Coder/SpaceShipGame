package gameObjects;

import graphics.Assets;
import input.KeyBoard;
import math.Vector2D;
import gameObjects.Constants;
import states.GameState;

import javax.swing.text.Position;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends MovingObject{

    private  Vector2D heading;
    private Vector2D acceleration;
    private final double ACC = 0.2;
    private final double DELTAANGLE = 0.1;
    private  boolean accelerating = false;

    private GameState gameState;
    private  long time, lastTime;

    public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture);
        this.gameState = gameState;
        heading = new Vector2D(0,1);
        acceleration = new Vector2D();
        time = 0;
        lastTime = System.currentTimeMillis();
    }
    @Override
    public void update() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (KeyBoard.SHOOT && time > 100) {
            gameState.getMovingObjects().add(0,new Laser(
                    getCenter().add(heading.scale(width)),
                    heading,
                    10,
                    angle,
                    Assets.redLaser
            ));
            time = 0;
        }

        if (KeyBoard.RIGHT) {
            angle += DELTAANGLE;
        }
        if (KeyBoard.LEFT) {
            angle -= DELTAANGLE;
        }
        if (KeyBoard.UP) {
            acceleration = heading.scale(ACC);
            accelerating = true;
        }else {
            if (velocity.getMagnitude() != 0) {
                acceleration = (velocity.scale(-1).normalize().scale(ACC / 2));
                accelerating = false;
            }
        }
        velocity = velocity.add(acceleration);
        //velocity.limit(maxVel);
        velocity = velocity.limit(maxVel);
        heading = heading.setDirection(angle - Math.PI/2);
        position = position.add(velocity);

        /*
        if (position.getX() > Constants.WIDTH) {
            position.setX(0);
        }
        if (position.getY() > Constants.HEIGHT) {
            position.setY(0);
        }
        if (position.getX() < 0) {
            position.setX(Constants.WIDTH);
        }
        if (position.getY() < 0) {
            position.setY(Constants.HEIGHT);
        }

         */
        final int spriteW = texture.getWidth();
        final int spriteH = texture.getHeight();

        if (position.getX() < -spriteW) {
            position.setX(Constants.WIDTH);
        }else if (position.getX() > Constants.WIDTH){
            position.setX(-spriteW);
        }
        if (position.getY() < -spriteH) {
            position.setY(Constants.HEIGHT);
        } else if (position.getY() > Constants.HEIGHT) {
            position.setY(-spriteH);
        }


    }
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 5, position.getY() + height / 2 + 10);
        AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 5, position.getY() + height / 2 + 10);
        at1.rotate(angle, -5, -10);
        at2.rotate(angle, width / 2 - 5,-10);
        if (accelerating) {
            g2d.drawImage(Assets.speed, at1, null);
            g2d.drawImage(Assets.speed, at2, null);
        }
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);
        g2d.drawImage(Assets.player, at, null);
    }
    public Vector2D getCenter() {
        return new Vector2D(position.getX() + width/2, position.getY() + height/2);
    }
}
