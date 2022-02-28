package com.badlogic.greedydwarf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.greedydwarf.GreedyDwarf;

public class LoadScreen implements Screen {
    final GreedyDwarf game;
    TextButton startButton;
    OrthographicCamera camera;
    int width = 1600, length = 900;
    Skin skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    Stage stage;

    public LoadScreen(final GreedyDwarf game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, length);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();

        final TextButton backButton = new TextButton("Back", skin, "default");
        backButton.setWidth(100);
        backButton.setHeight(50);
        backButton.setPosition(Gdx.graphics.getWidth()/4-backButton.getWidth()/2,Gdx.graphics.getHeight()/3-backButton.getHeight()/3);
        stage.addActor(backButton);

        Label text = new Label("Load Previous Game", skin,"default");
        text.setPosition(Gdx.graphics.getWidth()/2-text.getWidth()/2,Gdx.graphics.getHeight()*3/5-text.getHeight()*3/5);
        stage.addActor(text);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
}
