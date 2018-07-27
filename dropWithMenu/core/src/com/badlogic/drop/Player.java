package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player extends Sprite {

    private Array<TextureRegion> frames;
    private TextureRegion shell;
    private Animation walkAnimation;

    public enum State {WALKING, MOVING_SHELL, STANDING_SHELL}

    public State currentState;
    public State previousState;
    public Vector2 velocity;
    private float stateTime;

    public Player() {

        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("girl.png")), 0, 0, 23, 32));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("girl.png")), 24, 0, 24, 32));
        shell = new TextureRegion(new Texture(Gdx.files.internal("girl.png")), 47, 0, 26, 32);
        walkAnimation = new Animation(0.1f, frames);

        setRegion(shell);
        setPosition(50, 50);
        setBounds(getX(), getY(), 64, 64);

    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

        stateTime = stateTime + dt;
        //update previous state
        //return our final adjusted frame
        return region;
    }


    public void update(float dt) {
        setRegion(getFrame(dt));



    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
