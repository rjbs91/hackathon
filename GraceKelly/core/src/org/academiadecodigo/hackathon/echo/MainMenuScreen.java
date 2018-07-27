package org.academiadecodigo.hackathon.echo;

import org.academiadecodigo.hackathon.echo.assets.GameProperties;
import org.academiadecodigo.hackathon.echo.assets.Levels;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    final GKGame game;
    OrthographicCamera camera;

    public MainMenuScreen(GKGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameProperties.WIDTH, GameProperties.HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(new Texture(Gdx.files.internal("MainScreen.png")), 0, 0 );
        //game.font.draw(game.batch, "Welcome!!! ", 100, 150);
        //game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game, Levels.LEVEL_1));
            dispose();
        }

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

    }
}
