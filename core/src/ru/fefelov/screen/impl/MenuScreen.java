package ru.fefelov.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.movement.MovementHelper;
import ru.fefelov.screen.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture img2;
    private Vector2 position;
    private Vector2 destination;
    private static final int UFO_WIDTH = 100;
    private static final  int UFO_HEIGHT = 50;

    @Override
    public void show() {
        super.show();
        img = new Texture("background.jpg");
        img2 = new Texture("ufo.png");
        position = new Vector2();
        position.x =  Gdx.graphics.getWidth()/2f;
        position.y = Gdx.graphics.getHeight()/2f;
        destination = new Vector2();
        destination.x = position.x;
        destination.y = position.y;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MovementHelper.calculateMovement(position,destination);
        batch.draw(img2, position.x-UFO_WIDTH/2f, position.y-UFO_HEIGHT/2f, UFO_WIDTH, UFO_HEIGHT);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        img2.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        destination.set(screenX, Gdx.graphics.getHeight()-screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
