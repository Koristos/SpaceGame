package ru.fefelov.screen.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.screen.BaseScreen;
import ru.fefelov.sprite.impl.Background;
import ru.fefelov.sprite.impl.Star;
import ru.fefelov.sprite.impl.Ufo;


public class GameScreen extends BaseScreen {

    private Texture backgroundPict;
    private Vector2 position;
    private Background background;
    private Ufo ufo;

    private TextureAtlas ufoAtlas;
    private TextureAtlas atlas;

    private final String[] textureNameArray = new String[]{"Star2", "Star4", "Star6", "Star7", "Star8"};
    private Star[] stars;

    @Override
    public void show() {
        super.show();
        backgroundPict = new Texture("background.jpg");
        ufoAtlas = new TextureAtlas("ufo.pack");
        atlas = new TextureAtlas("menu.pack");
        position = new Vector2();
        background = new Background(backgroundPict);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas, textureNameArray);
        }
        ufo = new Ufo(ufoAtlas,true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ufo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundPict.dispose();
        atlas.dispose();
        ufoAtlas.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        ufo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        position.set(touch);
        ufo.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        position.set(touch);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        ufo.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ufo.keyUp(keycode);
        return false;
    }
}