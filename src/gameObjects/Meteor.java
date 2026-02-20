package gameObjects;

import graphics.Assets;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Meteor extends MovingObject{

    private Size size;

    public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
        super(position, velocity, maxVel, texture, gameState);
        this.size = size;
    }

    @Override
    public void update() {
        position = position.add(velocity);

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
        angle += Constants.DELTAANGLE/2;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width/2, height/2);
        g2d.drawImage(texture, at, null);
    }
    public Size getSize() {
        return size;
    }
}
