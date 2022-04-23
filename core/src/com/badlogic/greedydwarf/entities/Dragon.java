package com.badlogic.greedydwarf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Dragon extends Sprite {

    private Vector2 velocity = new Vector2(); // stores players current movement vectors

    public float speed; // max speed allowed to the dragon

    private final int tileSize = 32;

    private TiledMapTileLayer collision; // a layer to be checked for tiles with the blocked property to prevent movement

    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("dragon/dragon.atlas"));
    private TextureRegion textureRegion = textureAtlas.findRegion("tile000");

    private float startX;
    private float startY;
    private boolean set = false;
    private int condition;

    public int moveType;

    private Texture tLeft;
    private Texture tUp;
    private Texture tDown;
    private Texture tRight;

    public Dragon(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        speed = 75;
        this.collision = collisionLayer;
        velocity.x = 0;
        velocity.y = 0;
        set = false;
        condition = 0;
        moveType = 0;
        setScale(0.5f);
        tLeft = new Texture("Dragon.png");
        tUp = new Texture("DragonU.png");
        tDown = new Texture("DragonD.png");
        tRight = new Texture("DragonR.png");
    }

    public void setDragonCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collision = collisionLayer;
    }

    // set initial coordinates of dragon
    public void toggleMovement(boolean toggle) {
        startX = getX();
        startY = getY();
        velocity.y = speed;
        set = toggle;
    }

    // update the entity
    public void move(SpriteBatch spriteBatch) {
        if (set) {
            update(Gdx.graphics.getDeltaTime());
            super.draw(spriteBatch);
        }
    }

    public void update(float delta) {

        // TODO: change hardcoded movement with AI pathfinding
        if (moveType == 0) {
            // go up
            if (condition == 0) {
                this.setTexture(tUp);
                velocity.x = 0;
                velocity.y = speed;
                if (getY() >= startY + tileSize*2) {
                    condition++;
                }
            }
            // go left
            else if (condition == 1) {
                this.setTexture(tLeft);
                velocity.x = -speed;
                velocity.y = 0;
                if (getX() <= startX - tileSize*2) {
                    condition++;
                }
            }
            // go down
            else if (condition == 2) {
                this.setTexture(tDown);
                velocity.x = 0;
                velocity.y = -speed;
                if (getY() <= startY - tileSize*2) {
                    condition++;
                }
            }
            // go right
            else if (condition == 3) {
                this.setTexture(tRight);
                velocity.x = speed;
                velocity.y = 0;
                if (getX() >= startX + tileSize*2) {
                    condition = 0;
                }
            } else {
                velocity.x = 0;
                velocity.y = 0;
            }
        } else if ( moveType == 1) {
            // go left
            if (condition == 0) {
                this.setTexture(tLeft);
                velocity.x = -speed;
                velocity.y = 0;
                if (getX() <= startX - tileSize* 8) {
                    condition++;
                }
                // go right
            } else if (condition == 1) {
                this.setTexture(tRight);
                velocity.x = speed;
                velocity.y = 0;
                if (getX() >= startX + tileSize * 8) {
                    condition = 0;
                }
            }
        } else if (moveType == 2) {
            // go up
            if (condition == 0) {
                this.setTexture(tUp);
                velocity.x = 0;
                velocity.y = speed;
                if (getY() >= startY + tileSize * 6) {
                    condition++;
                }
                // go down
            } else if (condition == 1) {
                this.setTexture(tDown);
                velocity.x = 0;
                velocity.y = -speed;
                if (getY() <= startY - tileSize * 6) {
                    condition = 0;
                }
            }
        }

        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
    }
}

