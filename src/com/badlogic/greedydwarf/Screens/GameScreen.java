package com.badlogic.greedydwarf.Screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.greedydwarf.GreedyDwarf;
import com.badlogic.greedydwarf.RenderWithSprites;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    final GreedyDwarf game;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private RenderWithSprites tiledMapRenderer;
    private Texture texture;
    private Sprite sprite;
    private Stage stage;
    int width = 1920, length = 1080;
    int tileLength = 128;

    // Box2D flowchart: world -> bodies -> mass -> velocity
    // -> location -> angles -> fixtures (shape, density, friction, restitution)
    private World world;
    private Box2DDebugRenderer b2dr;

    public GameScreen(final GreedyDwarf game) {
        this.game = game;
        stage = new Stage();

        // camera variables
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, length);

        // player variables
        texture = new Texture("Textures/Placeholder_pictos-sprite.png");
        sprite = new Sprite(texture,tileLength, tileLength); // TODO: add boundaries
        sprite.setPosition(tileLength*8,tileLength/2); // set starting position

        // map variables
        tiledMap = new TmxMapLoader().load("Textures/DragonLair.tmx");
        tiledMapRenderer = new RenderWithSprites(tiledMap);
        tiledMapRenderer.addSprite(sprite);

        // Box2D variables
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // create bodies for wall tiles
        for(MapObject object : tiledMap.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        }

    }

    public void update(float dt) {
        camera.update();
    }

    @Override
    public void render(float delta) {

        // Clear the game screen with black by default
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // render map
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        // render Box2D debug
        b2dr.render(world, camera.combined);

        // temporary movement code
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            sprite.translate(-32, 0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            sprite.translate(32, 0);
        if(Gdx.input.isKeyPressed(Keys.UP))
            sprite.translate(0, 32);
        if(Gdx.input.isKeyPressed(Keys.DOWN))
            sprite.translate(0, -32);

        // update camera to point to sprite
        camera.position.x = sprite.getX();
        camera.position.y = sprite.getY();

        // update game
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
//        port.update(width,height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}