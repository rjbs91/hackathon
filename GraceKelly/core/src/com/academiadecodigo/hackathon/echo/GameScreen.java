package com.academiadecodigo.hackathon.echo;

import com.academiadecodigo.hackathon.echo.assets.GameProperties;
import com.academiadecodigo.hackathon.echo.assets.Levels;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

    final GKGame game;

    Texture graceImageRight;
    Texture graceImageLeft;
    Texture graceImage;

    Texture key;
    Texture closet;
    Rectangle objectRect;
    Texture objectText;

    Texture npc;
    Rectangle npcRect;

    Music music;
    OrthographicCamera camera;
    Rectangle grace;

    TiledMap mapTile;
    OrthogonalTiledMapRenderer renderer;

    boolean jump;
    boolean canJump;

    float base;

    final int imageSize = 65;

    final Levels level;

    public GameScreen(GKGame game, Levels level) {
        this.game = game;
        this.level = level;

        TmxMapLoader loader = new TmxMapLoader();

        mapTile = loader.load(level.path);

        renderer = new OrthogonalTiledMapRenderer(mapTile);

        camera = new OrthographicCamera(GameProperties.WIDTH, GameProperties.HEIGHT);
        camera.update();

        createTextures();
        createMusic();
        createObjects();

    }

    private void createObjects() {
        grace = GameObjectsFactory.makeObject(420, 150, imageSize);
        objectRect = GameObjectsFactory.makeObject(level.keyX, level.keyY, imageSize);
        npcRect = GameObjectsFactory.makeObject(4350, 16, imageSize);
    }

    private void createMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal(GameProperties.MUSIC));
        music.setLooping(true);
    }

    private void createTextures() {
        graceImageRight = new Texture(Gdx.files.internal(GameProperties.GK_RIGHT));
        graceImageLeft = new Texture(Gdx.files.internal(GameProperties.GK_LEFT));
        graceImage = graceImageRight;

        key = new Texture(Gdx.files.internal(GameProperties.KEY));
        closet = new Texture(Gdx.files.internal(GameProperties.CLOSET));
        objectText = key;

        npc = new Texture(Gdx.files.internal(GameProperties.NPC));

    }

    @Override
    public void show() {

        music.play();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        setUpBash();

        checkInputs();

        movement(delta);

        camera.position.set(grace.x, camera.viewportHeight / 2f, 0);
        camera.update();

        checkCollision();

    }

    private void checkCollision() {
        for (MapObject object : mapTile.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            if (grace.overlaps(rect)) {
                grace.y = rect.y + rect.height;
                base = grace.y;
                canJump = true;
            }

            if (grace.y >= base + 150) {
                grace.y = base + 150;
                jump = false;
            }

        }

        if (grace.overlaps(objectRect)) {
            objectRect.setX(level.closetX);
            objectRect.setY(level.closetY);
            objectText = closet;
        }

        if (grace.overlaps(npcRect)) {

            switch (level) {
                case LEVEL_1:
                    game.setScreen(new GameScreen(game, Levels.LEVEL_2));
                    dispose();
                    break;
                default:
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                    break;
            }
        }
    }

    private void movement(float delta) {
        grace.y = jump ?
                grace.y + GameProperties.MOVEMENT_SPEED * delta :
                grace.y - GameProperties.MOVEMENT_SPEED * delta;

        if (grace.x < 420) {
            grace.x = 420;
        }

        if (grace.y < 0) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && canJump) {
            jump = true;
            canJump = false;
        }
    }

    private void checkInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            grace.x -= GameProperties.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
            graceImage = graceImageLeft;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            grace.x += GameProperties.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
            graceImage = graceImageRight;
        }
    }

    private void setUpBash() {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(graceImage, grace.x, grace.y, grace.width, grace.height);
        game.batch.draw(objectText, objectRect.x, objectRect.y, objectRect.width, objectRect.height);
        game.batch.draw(npc, npcRect.x, npcRect.y, npcRect.width, npcRect.height);
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
        graceImage.dispose();
        graceImageLeft.dispose();
        graceImageRight.dispose();
        key.dispose();
        closet.dispose();
        objectText.dispose();
        npc.dispose();
        music.dispose();
    }
}
