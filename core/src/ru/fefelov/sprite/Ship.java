package ru.fefelov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.Gun;

public class Ship extends Sprite {

    protected int hp;
    protected Rect worldBounds;
    protected Gun gun;

    public Ship() {
    }

    public Ship (TextureRegion region) {
      super(region);
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public void shoot (){
        this.gun.shoot();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setWorldBounds(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }
}
