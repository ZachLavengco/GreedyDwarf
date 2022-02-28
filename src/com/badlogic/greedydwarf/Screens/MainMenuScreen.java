package com.badlogic.greedydwarf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.greedydwarf.GreedyDwarf;

public class MainMenuScreen implements Screen {

    final GreedyDwarf game;
    TextButton startButton;
    OrthographicCamera camera;
    int width = 1600, length = 900;
    Skin skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    Stage stage;

    public MainMenuScreen(final GreedyDwarf game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, length);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();

        final TextButton playButton = new TextButton("Play", skin, "default");
        playButton.setWidth(100);
        playButton.setHeight(50);
        playButton.setPosition(Gdx.graphics.getWidth()/4-playButton.getWidth()/2,Gdx.graphics.getHeight()/3-playButton.getHeight()/3);
        stage.addActor(playButton);

        final TextButton loadButton = new TextButton("Load", skin, "default");
        loadButton.setWidth(100);
        loadButton.setHeight(50);
        loadButton.setPosition(Gdx.graphics.getWidth()*2/5-loadButton.getWidth()/2,Gdx.graphics.getHeight()/3-loadButton.getHeight()/3);
        stage.addActor(loadButton);

        final TextButton createButton = new TextButton("Create", skin, "default");
        createButton.setWidth(140);
        createButton.setHeight(50);
        createButton.setPosition(Gdx.graphics.getWidth()*4/7-createButton.getWidth()/2,Gdx.graphics.getHeight()/3-createButton.getHeight()/3);
        stage.addActor(createButton);

        final TextButton quitButton = new TextButton("Quit", skin, "default");
        quitButton.setWidth(100);
        quitButton.setHeight(50);
        quitButton.setPosition(Gdx.graphics.getWidth()*74/100-quitButton.getWidth()/2,Gdx.graphics.getHeight()/3-quitButton.getHeight()/3);
        stage.addActor(quitButton);

        Label title = new Label("Greedy Dwarf", skin,"title");
        title.setPosition(Gdx.graphics.getWidth()/2-title.getWidth()/2,Gdx.graphics.getHeight()*3/5-title.getHeight()*3/5);
        stage.addActor(title);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new Play());
//                game.setScreen(new GameScreen(game));
            }
        });
        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new LoadScreen(game));
            }
        });
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new CreateScreen(game));
            }
        });
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        stage.draw();
        //game.font.draw(game.batch, "Tap anywhere to begin!", width/2, 300);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


    //...Rest of class omitted for succinctness.

}