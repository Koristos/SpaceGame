package ru.fefelov.mech;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import ru.fefelov.math.Rect;
import ru.fefelov.pools.impl.BulletPool;
import ru.fefelov.sprite.Sprite;
import ru.fefelov.sprite.impl.Bullet;
import ru.fefelov.utils.Regions;

public abstract class Gun {

    private Sprite owner;
    private BulletPool pool;
    private TextureRegion textureRegion;
    private int damage;
    private Vector2 speed;
    private float bulletHeight;
    private Rect worldBounds;
    private int rows;
    private int cols;
    private int frames;
    private int firstFrame;
    private Sound sound;
    private Timer timer;


    public void shoot(){
        Bullet bullet = pool.obtain();
        bullet.set(owner, Regions.split(textureRegion, rows, cols, frames), owner.getPosition(),
                speed, bulletHeight, worldBounds, damage, firstFrame);
        sound.play();
    };

    public void setOwner(Sprite owner) {
        this.owner = owner;
    }

    public void setPool(BulletPool pool) {
        this.pool = pool;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public void setBulletHeight(float bulletHeight) {
        this.bulletHeight = bulletHeight;
    }

    public void setWorldBounds(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setFrames(int frame) {
        this.frames = frame;
    }

    public void setFirstFrame(int firstFrame) {
        this.firstFrame = firstFrame;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }


    public void setTimer(Timer timer) {
        this.timer = timer;
    }

}
