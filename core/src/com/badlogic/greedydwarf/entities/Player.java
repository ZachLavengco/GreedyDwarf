package com.badlogic.greedydwarf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

    private Vector2 velocity = new Vector2(); // stores players current movement vectors

    public float speed = 175; // max speed allowed to a player when they havent picked up any treasure

    private TiledMapTileLayer collision; // a layer to be checked for tiles with the blocked property to prevent movement

    public boolean drop = false;

    private Texture tLeft;
    private Texture tRight;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collision = collisionLayer;

        tLeft = new Texture("DwarfR.png");
        tRight = new Texture("Dwarf.png");

    }

    // update the character
    public void move(SpriteBatch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public void setPlayerCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collision = collisionLayer;
    }

    public void update(float delta) {
        // stores original pos before making changes
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        // update position in the x direction
        setX(getX() + velocity.x * delta);

        // check if new position collides with a tile it shouldn't be allowed in
        if(velocity.x < 0) // going left;
            collisionX = collidesLeft();
        else if (velocity.x > 0)
            collisionX = collidesRight();

        // reverts to old position if above is true
        if(collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        // updates position in the y direction
        setY(getY() + velocity.y * delta);

        // check if new position collides with a tile it shouldn't be allowed in
        if(velocity.y < 0)  // going down
            collisionY = collidesDown();
        else if (velocity.y > 0)
            collisionY = collidesUp();

        // revert to old y position if above is true
        if(collisionY) {
            setY(oldY);
            velocity.y = 0;
        }
    }

    // Checks to see if the given location is in the region of a tile with the blocked property
    private boolean isBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collision.getCell((int) (x / collision.getTileWidth()), (int) (y / collision.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("Blocked"); // access wall tile property
    }

    // checks multiple points to the right of the player to ensure that the player sprite isnt overlapping blocked tiles
    private boolean collidesRight(){
        boolean collides = false;
        for (float step = 0; step < getHeight(); step += collision.getTileHeight() / 2) {
            if (collides = isBlocked(getX() + getWidth(), getY() + step))
                break;
        }
        return collides;
    }

    // checks multiple points to the left of the player to ensure that the player sprite isn't overlapping blocked tiles
    private boolean collidesLeft() {
        boolean collides = false;
        for (float step = 0; step < getHeight();step += collision.getTileHeight() / 2) {
            if (collides = isBlocked(getX(), getY() + step))
                break;
        }
        return collides;
    }

    // checks multiple points above the player to ensure that the player sprite isn't overlapping blocked tiles
    private boolean collidesUp() {
        boolean collides = false;
        for (float step = 0; step < getWidth(); step += collision.getTileWidth() / 2) {
            if (collides = isBlocked(getX() + step, getY() + getHeight()))
                break;
        }
        return  collides;
    }

    // checks multiple points below the player to ensure that the player sprite isn't overlapping blocked tiles
    private boolean collidesDown() {
        boolean collides = false;
        for (float step = 0; step < getWidth(); step += collision.getTileWidth() / 2) {
               if(collides = isBlocked(getX() + step, getY()))
                   break;
        }
        return collides;
    }

    // processes player input when a key is pressed down.
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.Q:
                drop = true;
                break;
            case Input.Keys.W:
                velocity.y += speed;
                break;
            case Input.Keys.A:
                velocity.x -= speed;
                this.setTexture(tLeft);
                break;
            case Input.Keys.S:
                velocity.y -= speed;
                break;
            case Input.Keys.D:
                velocity.x += speed;
                this.setTexture(tRight);

        }
        return true;
    }

    // processes player input when a key is released.
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.Q:
                drop = false;
                break;
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

