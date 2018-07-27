package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {

    final Drop game;

    Texture bucketImageRight;
    Texture bucketImageLeft;
    Texture bucketImage;

    Texture key;
    Texture closet;
    Rectangle objectRect;
    Texture objectText;

    Texture prince;
    Rectangle princeRect;

    Music music;
    OrthographicCamera camera;
    Rectangle bucket;

    TiledMap mapTile;
    OrthogonalTiledMapRenderer rederer;

    boolean jump;
    boolean canJump;

    float base;

    final int bucketSize = 65;

    public GameScreen(final Drop game) {

        TmxMapLoader loader = new TmxMapLoader();

        mapTile = loader.load("level1.tmx");

        rederer = new OrthogonalTiledMapRenderer(mapTile);

        this.game = game;

        bucketImageRight = new Texture(Gdx.files.internal("girl_right.png"));
        bucketImageLeft = new Texture(Gdx.files.internal("girl_left.png"));
        bucketImage = bucketImageRight;

        key = new Texture(Gdx.files.internal("key.png"));
        closet = new Texture(Gdx.files.internal("closet.png"));
        objectText = key;

        prince = new Texture((Gdx.files.internal("prince.png")));

        // load the drop sound effect and the rain background "music"
        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Mika.mp3"));
        music.setLooping(true);

        camera = new OrthographicCamera(800, 480);
        camera.update();

        bucket = new Rectangle();
        bucket.x = 418;
        bucket.y = 50;

        bucket.width = bucketSize;
        bucket.height = bucketSize;

        objectRect = new Rectangle();
        objectRect.x = 2177;
        objectRect.y = 96;

        objectRect.width = bucketSize;
        objectRect.height = bucketSize;


        princeRect = new Rectangle();
        princeRect.x = 4350;
        princeRect.y = 16;

        princeRect.width = bucketSize;
        princeRect.height = bucketSize;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rederer.setView(camera);
        rederer.render();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        game.batch.draw(objectText, objectRect.x, objectRect.y, objectRect.width, objectRect.height);
        game.batch.draw(prince, princeRect.x, princeRect.y, princeRect.width, princeRect.height);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            bucket.x -= 500 * Gdx.graphics.getDeltaTime();
            bucketImage = bucketImageLeft;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            bucket.x += 500 * Gdx.graphics.getDeltaTime();
            bucketImage = bucketImageRight;
        }

        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && canJump) {
            jump = true;
            canJump = false;
        }

        if (jump) {
            bucket.y += 500 * delta;
        } else {
            bucket.y -= 500 * delta;
        }

        if (bucket.x < 418) {
            bucket.x = 418;
        }

        if (bucket.y < 0) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        camera.position.set(bucket.x, camera.viewportHeight / 2f, 0);
        camera.update();

        for (MapObject object : mapTile.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            if (bucket.overlaps(rect)) {
                bucket.y = rect.y + rect.height;
                base = bucket.y;
                canJump = true;
            }

            if (bucket.y >= base + 150) {
                bucket.y = base + 150;
                jump = false;
            }

        }

        if (bucket.overlaps(objectRect)) {
            objectRect.setX(3407);
            objectRect.setY(16);
            objectText = closet;
        }

        if (bucket.overlaps(princeRect)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        System.out.println(bucket.x + " " + bucket.y);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        music.play();
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
        bucketImage.dispose();
        music.dispose();
    }
}