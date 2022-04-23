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

public class EndGameScreen implements Screen {

    final GreedyDwarf game;
    TextButton startButton;
    OrthographicCamera camera;
    int width = 1600, length = 900;
    Skin skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    Stage stage;

    public EndGameScreen(final GreedyDwarf game, int score, boolean died) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, length);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();

        // displays the play again button
        final TextButton playButton = new TextButton("Play Again", skin, "default");
        playButton.setWidth(200);
        playButton.setHeight(50);
        playButton.setPosition(Gdx.graphics.getWidth()*2/5-15-playButton.getWidth()/2,Gdx.graphics.getHeight()/3-playButton.getHeight()/3);
        stage.addActor(playButton);

        // displays the quit button
        final TextButton quitButton = new TextButton("Quit", skin, "default");
        quitButton.setWidth(100);
        quitButton.setHeight(50);
        quitButton.setPosition(Gdx.graphics.getWidth()*3/5+25-quitButton.getWidth()/2,Gdx.graphics.getHeight()/3-quitButton.getHeight()/3);
        stage.addActor(quitButton);

        // displays text depending on whether they died or not on screen
        if (died == true) {
            Label endGame = new Label("You Died", skin,"title");
            endGame.setPosition(Gdx.graphics.getWidth()/2-endGame.getWidth()/2,Gdx.graphics.getHeight()*4/5-endGame.getHeight());
            stage.addActor(endGame);
        } else {
            Label endGame = new Label("You Lived!", skin,"title");
            endGame.setPosition(Gdx.graphics.getWidth()/2-endGame.getWidth()/2,Gdx.graphics.getHeight()*4/5-endGame.getHeight());
            stage.addActor(endGame);
        }


        // displays final score on screen
        Label showScore = new Label("Score: " + score, skin,"default");
        showScore.setPosition(Gdx.graphics.getWidth()/2-showScore.getWidth()/2,Gdx.graphics.getHeight()*53/100-showScore.getHeight());
        stage.addActor(showScore);

        // when play again button is pressed we start a new game
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new Play(game));
//                game.setScreen(new GameScreen(game));
            }
        });

        // when quit button is pressed we close the program
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