package ru.fefelov.screen.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.screen.BaseScreen;
import ru.fefelov.sprite.impl.Background;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture img2;
    private Vector2 position;
    private Background background;

    @Override
    public void show() {
        super.show();
        img = new Texture("background.jpg");
        img2 = new Texture("ufo.png");
        position = new Vector2();
        background = new Background(img);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        img2.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        position.set(touch);
        return super.touchDown(touch, pointer, button);
    }
}
