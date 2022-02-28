package com.badlogic.greedydwarf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

    // the movement velocity
    private Vector2 velocity = new Vector2();

    private final float speed = 100;

    private TiledMapTileLayer collision;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collision = collisionLayer;
    }

    // update the character
    public void move(SpriteBatch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public void update(float delta) {

        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        setX(getX() + velocity.x * delta);

        if(velocity.x < 0) // going left;
            collisionX = collidesLeft();
        else if (velocity.x > 0)
            collisionX = collidesRight();

        if(collisionX) {
            setX(oldX);
            velocity.x = 0;
            System.out.println("Colliding X");
        }

        setY(getY() + velocity.y * delta);

        if(velocity.y < 0)  // going down
            collisionY = collidesDown();
        else if (velocity.y > 0)
            collisionY = collidesUp();

        if(collisionY) {
            setY(oldY);
            velocity.y = 0;
            System.out.println("colliding Y");
        }
    }

    private boolean isBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collision.getCell((int) (x / collision.getTileWidth()), (int) (y / collision.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("Blocked"); // access wall tile property
    }

        private boolean collidesRight(){
            boolean collides = false;
            for (float step = 0; step < getHeight(); step += collision.getTileHeight() / 2) {
                if (collides = isBlocked(getX() + getWidth(), getY() + step))
                    break;
            }
            return collides;
        }

        private boolean collidesLeft() {
            boolean collides = false;
            for (float step = 0; step < getHeight();step += collision.getTileHeight() / 2) {
                if (collides = isBlocked(getX(), getY() + step))
                    break;
            }
            return collides;
        }

        private boolean collidesUp() {
            boolean collides = false;
            for (float step = 0; step < getWidth(); step += collision.getTileWidth() / 2) {
                if (collides = isBlocked(getX() + step, getY() + getHeight()))
                    break;
            }
            return  collides;
        }

        private boolean collidesDown() {
            boolean collides = false;
            for (float step = 0; step < getWidth(); step += collision.getTileWidth() / 2) {
                   if(collides = isBlocked(getX() + step, getY()))
                       break;
            }
            return collides;
        }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                velocity.y += speed;
                break;
            case Input.Keys.A:
                velocity.x -= speed;
                break;
            case Input.Keys.S:
                velocity.y -= speed;
                break;
            case Input.Keys.D:
                velocity.x += speed;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

