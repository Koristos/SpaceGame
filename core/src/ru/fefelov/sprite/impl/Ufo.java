package ru.fefelov.sprite.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.sprite.Sprite;

public class Ufo extends Sprite {

    private Vector2 destination = new Vector2();
    static Vector2 move = new Vector2();
    static final float SPEED = 0.005f;

    public Ufo(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight()*0.1f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.destination.set(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public void draw(SpriteBatch batch) {
        calculateMovement(this.pos, this.destination);
        super.draw(batch);
    }


    private void calculateMovement (Vector2 position, Vector2 destination){
        move.set(destination);
        move.sub(position);
        if (move.len()>SPEED){
            move.nor().scl(SPEED);
        }
        position.add(move);
    }
}
