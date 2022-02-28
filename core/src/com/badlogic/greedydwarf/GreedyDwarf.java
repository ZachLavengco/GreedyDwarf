package com.badlogic.greedydwarf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.greedydwarf.Screens.MainMenuScreen;


public class GreedyDwarf extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    private Stage stage;

    @Override
    public void create() {
        batch = new SpriteBatch();  // used to render objects onto a screen
        font = new BitmapFont(); // use libGDX's default Arial font

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

//        music = Gdx.audio.newMusic(Gdx.files.internal("gamemusic.wav"));
//        music.setLooping(true);
//        music.setVolume(0.2f);
//        music.play();

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); // important!
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

}