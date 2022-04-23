package com.badlogic.greedydwarf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.greedydwarf.GreedyDwarf;
import com.badlogic.greedydwarf.entities.Player;
import com.badlogic.greedydwarf.entities.Dragon;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

import java.util.Iterator;
import java.util.List;

public class Play implements Screen {

    private final GreedyDwarf game;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap Outside, StartRoom, Lair1, Lair2;
    private MapLayer objectLayer;
    private MapObjects objects;
    private ShapeRenderer sr;
    private OrthographicCamera camera;
    private Player player;

    // TODO: make array of dragons instead of individual objects
    private Dragon dragon;
    private Dragon dragon2;
    private Dragon dragon3;
    private Dragon dragon4;
    private Dragon dragon5;

    private Skin skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    private Stage stage;

    // weight counter variables
    BitmapFont font = new BitmapFont();
    private int weight = 0;
    private String knapsack = "Weight: " + String.valueOf(weight);
    private int score = 0;
    private boolean died = false;

    // Navigation variables
    private NavigationTiledMapLayer navLayer;
    List<GridCell> pathToEnd;

    private int tileSize = 32;

    public Play(final GreedyDwarf game) {
        this.game = game;
    }

    @Override
    public void show() {

        // load map
        Outside = new TmxMapLoader().load("maps/Outside.tmx");
        objectLayer = Outside.getLayers().get("ObjectLayer");
        objects = objectLayer.getObjects();
//        navLayer = (NavigationTiledMapLayer) map.getLayers().get("navigation"); // get walkable layer from Tiled map(duplicate of floor tiles)

        // create pathfinder with custom options:
//        GridFinderOptions opt = new GridFinderOptions();
//        AStarGridFinder<GridCell> finder = new AStarGridFinder(GridCell.class, opt);

        renderer = new OrthogonalTiledMapRenderer(Outside);
        sr = new ShapeRenderer();
        sr.setColor(Color.CYAN);

        camera = new OrthographicCamera();

        player = new Player(new Sprite(new Texture("Dwarf.png")), (TiledMapTileLayer) Outside.getLayers().get("Walls"));
        player.setPosition(20*tileSize, 38*tileSize);

        dragon = new Dragon(new Sprite(new Texture("Dragon.png"), 36, 0, 155, 122), (TiledMapTileLayer) Outside.getLayers().get("Walls"));
        dragon.setPosition(50*tileSize, 50*tileSize);  // puts dragon out of site for the outside tmx
        dragon.toggleMovement(false);

        dragon2 = new Dragon(new Sprite(new Texture("Dragon.png"), 36, 0, 155, 122), (TiledMapTileLayer) Outside.getLayers().get("Walls"));
        dragon2.setPosition(50*tileSize, 50*tileSize);  // puts dragon out of site for the outside tmx
        dragon2.toggleMovement(false);
        dragon2.moveType = 1;

        dragon3 = new Dragon(new Sprite(new Texture("Dragon.png"), 36, 0, 155, 122), (TiledMapTileLayer) Outside.getLayers().get("Walls"));
        dragon3.setPosition(50*tileSize, 50*tileSize);  // puts dragon out of site for the outside tmx
        dragon3.toggleMovement(false);
        dragon3.moveType = 2;

        dragon4 = new Dragon(new Sprite(new Texture("Dragon.png"), 36, 0, 155, 122), (TiledMapTileLayer) Outside.getLayers().get("Walls"));
        dragon4.setPosition(50*tileSize, 50*tileSize);  // puts dragon out of site for the outside tmx
        dragon4.toggleMovement(false);
        dragon4.moveType = 1;

        dragon5 = new Dragon(new Sprite(new Texture("Dragon.png"), 36, 0, 155, 122), (TiledMapTileLayer) Outside.getLayers().get("Walls"));
        dragon5.setPosition(50*tileSize, 50*tileSize);  // puts dragon out of site for the outside tmx
        dragon5.toggleMovement(false);
        dragon5.moveType = 2;

        Gdx.input.setInputProcessor(player);

        // tries to emplace a stage that will show a counter in the top left
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//        BitmapFont font = new BitmapFont();
//        Label title = new Label("Greedy Dwarf", skin,"default");
//        title.setPosition(Gdx.graphics.getWidth()/2-title.getWidth()/2,Gdx.graphics.getHeight()*3/5-title.getHeight()*3/5);
//        stage.addActor(title);

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

//        // draw rectangle over object (ex: dragon, dwarf) for hitbox debugging
//        Rectangle rec = player.getBoundingRectangle();
//        sr.begin(ShapeRenderer.ShapeType.Filled);
//        sr.rect(rec.x, rec.y, rec.width, rec.height);
//        sr.end();

        // begin batch operations
        renderer.getBatch().begin();

        player.move((SpriteBatch) renderer.getBatch());
        dragon.move((SpriteBatch) renderer.getBatch());
        dragon2.move((SpriteBatch) renderer.getBatch());
        dragon3.move((SpriteBatch) renderer.getBatch());
        dragon4.move((SpriteBatch) renderer.getBatch());
        dragon5.move((SpriteBatch) renderer.getBatch());
        font.draw(renderer.getBatch(), knapsack, player.getX()-165, player.getY()-75);

        endGame();
        renderExit();
        renderTreasure();
        score = weight*325;

        renderer.getBatch().end();

        // TODO: reduce logic operations in render method to decrease game lag
    }

    // check if player and dragon objects overlap (end game)
    private void endGame() {
        Rectangle rect = dragon.getBoundingRectangle();
        rect.set(rect.getX() + rect.width/4, rect.getY() + rect.height/4, rect.width/2, rect.height/2);
        if(rect.overlaps(player.getBoundingRectangle())) {
            died = true;
            game.setScreen(new EndGameScreen(game,score, died));
            //Gdx.app.exit();
        }
        Rectangle rect2 = dragon2.getBoundingRectangle();
        rect2.set(rect2.getX() + rect2.width/4, rect2.getY() + rect2.height/4, rect2.width/2, rect2.height/2);
        if(rect2.overlaps(player.getBoundingRectangle())) {
            died = true;
            game.setScreen(new EndGameScreen(game,score, died));
            //Gdx.app.exit();
        }
        Rectangle rect3 = dragon3.getBoundingRectangle();
        rect3.set(rect3.getX() + rect3.width/4, rect3.getY() + rect3.height/4, rect3.width/2, rect3.height/2);
        if(rect3.overlaps(player.getBoundingRectangle())) {
            died = true;
            game.setScreen(new EndGameScreen(game,score, died));
            //Gdx.app.exit();
        }
        Rectangle rect4 = dragon4.getBoundingRectangle();
        rect4.set(rect4.getX() + rect4.width/4, rect4.getY() + rect4.height/4, rect4.width/2, rect4.height/2);
        if(rect4.overlaps(player.getBoundingRectangle())) {
            died = true;
            game.setScreen(new EndGameScreen(game,score, died));
            //Gdx.app.exit();
        }
        Rectangle rect5 = dragon5.getBoundingRectangle();
        rect5.set(rect5.getX() + rect5.width/4, rect5.getY() + rect5.height/4, rect5.width/2, rect5.height/2);
        if(rect5.overlaps(player.getBoundingRectangle())) {
            died = true;
            game.setScreen(new EndGameScreen(game,score, died));
            //Gdx.app.exit();
        }
    }

    // helper method to render and update exit tiles
    private void renderExit() {
        // render objects
        sr.setProjectionMatrix(camera.combined);
        // loop through all objects in object layer
        for (MapObject object : objects) {
            /* if (object instanceof RectangleMapObject) */ // kept for checking all rectangle shaped objects

            // check if object is exit tile
            if (object.getName().contains("ExitOutsideToStartRoom")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // check if player boundary collides with treasure boundary
                if (rect.overlaps(player.getBoundingRectangle())) {
                    renderExitHelper(Lair1,"StartRoom");
                    player.setPosition(7*tileSize, 35*tileSize);
                    dragon.setPosition(50*tileSize, 50*tileSize);  // puts dragon out of site
                    dragon.toggleMovement(false);

                    dragon2.setPosition(50*tileSize, 50*tileSize);
                    dragon2.toggleMovement(false);

                    dragon3.setPosition(50*tileSize, 50*tileSize);
                    dragon3.toggleMovement(false);

                    dragon4.setPosition(50*tileSize, 50*tileSize);
                    dragon4.toggleMovement(false);

                    dragon5.setPosition(50*tileSize, 50*tileSize);
                    dragon5.toggleMovement(false);
                }
            }
            if (object.getName().contains("ExitStartRoomToOutside")) {

                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // check if player boundary collides with ExitTile
                if (rect.overlaps(player.getBoundingRectangle())) {
                    score *= 2;  // doubles score if takes treasure home
                    game.setScreen(new EndGameScreen(game,score,died));
                }
            }
            if (object.getName().contains("ExitStartRoomToLair1")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // check if player boundary collides with treasure boundary
                if (rect.overlaps(player.getBoundingRectangle())) {
                    renderExitHelper(Lair1,"Lair1");
                    player.setPosition(1*tileSize, 34*tileSize);
                    dragon.setPosition(23*tileSize, 26*tileSize);  // puts dragon in object layer spot
                    dragon.toggleMovement(true);

                    dragon2.setPosition(23*tileSize, 19*tileSize);
                    dragon2.toggleMovement(true);

                    dragon3.setPosition(12*tileSize, 28*tileSize);
                    dragon3.toggleMovement(true);

                    dragon4.setPosition(12*tileSize, 1*tileSize);
                    dragon4.toggleMovement(true);

                    dragon4.setPosition(12*tileSize, 1*tileSize);
                    dragon4.toggleMovement(true);

                    dragon5.setPosition(2.4f*tileSize, 19*tileSize);
                    dragon5.toggleMovement(true);
                }
            }
            if (object.getName().contains("ExitLair1ToStartRoom")) {

                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // check if player boundary collides with ExitTile
                if (rect.overlaps(player.getBoundingRectangle())) {
                    renderExitHelper(StartRoom,"StartRoom");
                    player.setPosition(13*tileSize, 38*tileSize);
                    dragon.setPosition(50*tileSize, 50*tileSize);  // puts dragon out of site
                    dragon.toggleMovement(false);

                    dragon2.setPosition(50*tileSize, 50*tileSize);
                    dragon2.toggleMovement(false);

                    dragon3.setPosition(50*tileSize, 50*tileSize);
                    dragon3.toggleMovement(false);

                    dragon4.setPosition(50*tileSize, 50*tileSize);
                    dragon4.toggleMovement(false);

                    dragon5.setPosition(50*tileSize, 50*tileSize);
                    dragon5.toggleMovement(true);
                }
            }
            if (object.getName().contains("ExitLair1ToLair2")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // check if player boundary collides with ExitTile
                if (rect.overlaps(player.getBoundingRectangle())) {
                    renderExitHelper(Lair2,"Lair2");
                    player.setPosition(34*tileSize, 54*tileSize);
                    dragon.setPosition(3*tileSize, 58*tileSize); // puts dragon in object layer spot
                    dragon.toggleMovement(true);
                    dragon.speed = 125;

                    dragon2.setPosition(28*tileSize, 28.5f*tileSize);
                    dragon2.toggleMovement(true);
                    dragon2.speed = 125;

                    dragon3.setPosition(36.5f*tileSize, 43*tileSize);
                    dragon3.toggleMovement(true);
                    dragon3.speed = 125;

                    dragon4.setPosition(13*tileSize, 15*tileSize);
                    dragon4.toggleMovement(true);
                    dragon4.speed = 125;

                    dragon5.setPosition(7.5f*tileSize, 29*tileSize);
                    dragon5.toggleMovement(true);
                    dragon5.speed = 125;
                }
            }
            if (object.getName().contains("ExitLair2ToLair1")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // check if player boundary collides with ExitTile
                if (rect.overlaps(player.getBoundingRectangle())) {
                    renderExitHelper(Lair1,"Lair1");
                    player.setPosition(32*tileSize, 3*tileSize);
                    dragon.setPosition(23*tileSize, 26*tileSize);  // puts dragon in object layer spot
                    dragon.toggleMovement(true);
                    dragon.speed = 75;

                    dragon2.setPosition(23*tileSize, 19*tileSize);
                    dragon2.toggleMovement(true);
                    dragon2.speed = 75;

                    dragon3.setPosition(12*tileSize, 28*tileSize);
                    dragon3.toggleMovement(true);
                    dragon3.speed = 75;

                    dragon4.setPosition(12*tileSize, 1*tileSize);
                    dragon4.toggleMovement(true);
                    dragon4.speed = 75;

                    dragon5.setPosition(2.4f*tileSize, 19*tileSize);
                    dragon5.toggleMovement(true);
                    dragon5.speed = 75;
                }
            }
        }
    }

    private void renderExitHelper(TiledMap mapSelected, String mapSelectedString) {
        mapSelected = new TmxMapLoader().load("maps/" + mapSelectedString + ".tmx"); //load the new map
        renderer.getMap().dispose(); //dispose the old map
        renderer.setMap(mapSelected); //set the map in your renderer

        objectLayer = mapSelected.getLayers().get("ObjectLayer");
        objects = objectLayer.getObjects();
        player.setPlayerCollisionLayer((TiledMapTileLayer) mapSelected.getLayers().get("Walls"));
        dragon.setDragonCollisionLayer((TiledMapTileLayer) mapSelected.getLayers().get("Walls"));
        dragon2.setDragonCollisionLayer((TiledMapTileLayer) mapSelected.getLayers().get("Walls"));
        dragon3.setDragonCollisionLayer((TiledMapTileLayer) mapSelected.getLayers().get("Walls"));
        dragon4.setDragonCollisionLayer((TiledMapTileLayer) mapSelected.getLayers().get("Walls"));
        dragon5.setDragonCollisionLayer((TiledMapTileLayer) mapSelected.getLayers().get("Walls"));
    }

    // helper method to render and update treasure objects
    private void renderTreasure() {
        // render objects
        sr.setProjectionMatrix(camera.combined);
        // loop through all objects in object layer
        for (MapObject object : objects) {
            /* if (object instanceof RectangleMapObject) */ // kept for checking all rectangle shaped objects

            // check if object is a treasure object
            if (object.getName().contains("coin")) {

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
                    if (player.speed >= 50) {
                        player.speed = player.speed - 1;
                        weight++;
                        knapsack = "Weight: " + String.valueOf(weight);
                    }
                    objects.remove(object);
                }
            }
            // check if object is a treasure object
            if (object.getName().contains("chest")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // draw texture over object
                Texture temp = new Texture("Chest.png");
                renderer.getBatch().draw(temp, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

                // check if player boundary collides with treasure boundary
                if (rect.overlaps(player.getBoundingRectangle())) {
                    // remove object
                    if (player.speed >= 50) {
                        player.speed = player.speed - 9;
                        weight+=9;
                        knapsack = "Weight: " + String.valueOf(weight);
                    }
                    objects.remove(object);
                }
            }
            // check if object is a treasure object
            if (object.getName().contains("triple")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // draw texture over object
                Texture temp = new Texture("Coins.png");
                renderer.getBatch().draw(temp, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

                // check if player boundary collides with treasure boundary
                if (rect.overlaps(player.getBoundingRectangle())) {
                    // remove object
                    if (player.speed >= 50) {
                        player.speed = player.speed - 3;
                        weight+=3;
                        knapsack = "Weight: " + String.valueOf(weight);
                    }
                    objects.remove(object);
                }
            }
            // check if object is a treasure object
            if (object.getName().contains("treasure")) {

                // Create rectangle object boundary
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // draw texture over object
                Texture temp = new Texture("Coin.png");
                renderer.getBatch().draw(temp, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

                // check if player boundary collides with treasure boundary
                if (rect.overlaps(player.getBoundingRectangle())) {
                    // remove object
                    if (player.speed >= 50) {
                        player.speed = player.speed - 1;
                        weight+=1;
                        knapsack = "Weight: " + String.valueOf(weight);
                    }
                    objects.remove(object);
                }
            }
        }

        // drop coins condition
        if (player.drop == true && weight > 0) {
            weight--;
            player.speed++;
            knapsack = "Weight: " + String.valueOf(weight);
            player.drop = false;
        }
    }

    // updates playable screen to match window size
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
        //dispose();
    }

    @Override
    public void dispose() {
        Outside.dispose();
        Lair1.dispose();
        Lair2.dispose();
        StartRoom.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }
}
