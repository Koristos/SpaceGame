package ru.fefelov.sprite.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.fefelov.math.Rect;
import ru.fefelov.sprite.Sprite;

public class Bullet extends Sprite {

    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private int damage;
    private Sprite owner;

    public Bullet(){
    }

    public void set(
            Sprite owner,
            TextureRegion regions [],
            Vector2 pos,
            Vector2 v,
            float height,
            Rect worldBounds,
            int damage,
            int firstFrame
    ) {
        this.owner = owner;
        this.regions = regions;
        this.pos.set(pos);
        this.v.set(v);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
        this.frame = firstFrame;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }

}
