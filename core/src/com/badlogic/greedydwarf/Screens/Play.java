package com.badlogic.greedydwarf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.greedydwarf.entities.Player;

import java.util.Iterator;

public class Play implements Screen {

    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private MapLayer objectLayer;
    private MapObjects objects;
    private ShapeRenderer sr;
    private OrthographicCamera camera;
    private Player player;

    private int tileSize = 32;

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/Lair1.tmx");
        objectLayer = map.getLayers().get("ObjectLayer");
        objects = objectLayer.getObjects();

        renderer = new OrthogonalTiledMapRenderer(map);
        sr = new ShapeRenderer();
        sr.setColor(Color.CYAN);

        camera = new OrthographicCamera();

        player = new Player(new Sprite(new Texture("Dwarf.png")), (TiledMapTileLayer) map.getLayers().get("Walls"));
        player.setPosition(0*tileSize, 34*tileSize);

        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void render(float delta) {
        // reset map
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // set camera to player
        camera.position.set(player.getX() + 16, player.getY() + 16, 0);
        camera.update();

        // create renderer
        renderer.setView(camera);
        renderer.render();

        // begin batch operations
        renderer.getBatch().begin();
        player.move((SpriteBatch) renderer.getBatch());
        renderTreasure();
        renderer.getBatch().end();

        // TODO: reduce logic operations in render method to decrease game lag
    }

    // helper method to render and update treasure objects
    private void renderTreasure() {
        // render objects
        sr.setProjectionMatrix(camera.combined);
        // loop through all objects in object layer
        for (MapObject object : objects) {
            /* if (object instanceof RectangleMapObject) */ // kept for checking all rectangle shaped objects

            // check if object is a treasure object
            if (object.getName().contains("treasure")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // draw object bounds for debugging
//                sr.begin(ShapeRenderer.ShapeType.Filled);
//                sr.rect(rect.x, rect.y, rect.width, rect.height);
//                sr.end();

                // draw texture over object
                Texture temp = new Texture("Coin.png");
                renderer.getBatch().draw(temp, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());


                // check if player boundary collides with treasure boundary
                if (rect.overlaps(player.getBoundingRectangle())) {
                    // remove object
                    objects.remove(object);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/2;
        camera.viewportHeight = height/2;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }
}
