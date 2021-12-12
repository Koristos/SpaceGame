package ru.fefelov.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.impl.PlasmGun;
import ru.fefelov.pools.impl.BulletPool;
import ru.fefelov.pools.impl.EnemyPool;
import ru.fefelov.screen.BaseScreen;
import ru.fefelov.sprite.impl.Background;
import ru.fefelov.sprite.impl.Bullet;
import ru.fefelov.sprite.impl.EnemyShip;
import ru.fefelov.sprite.impl.Label;
import ru.fefelov.sprite.impl.Star;
import ru.fefelov.sprite.impl.Ufo;
import ru.fefelov.utils.EnemyEmitter;


public class GameScreen extends BaseScreen {

    private Texture backgroundPict;
    private TextureRegion explosions;
    private Vector2 position;
    private Background background;
    private Ufo ufo;
    private Label gameOver;

    private TextureAtlas ufoAtlas;
    private TextureAtlas atlas;
    private TextureAtlas bulletAtlas;
    private TextureAtlas enemyAtlas;
    private TextureAtlas gameOverAtlas;

    private final String[] textureNameArray = new String[]{"Star2", "Star4", "Star6", "Star7", "Star8"};
    private Star[] stars;
    private Music music;
    private BulletPool bulletPool;
    private EnemyEmitter enemyEmitter;
    private EnemyPool enemyPool;

    @Override
    public void show() {
        super.show();
        backgroundPict = new Texture("background.jpg");
        explosions = new TextureRegion();
        explosions.setRegion(new Texture("explosion.png"));
        ufoAtlas = new TextureAtlas("ufo.pack");
        atlas = new TextureAtlas("menu.pack");
        bulletAtlas = new TextureAtlas("bullets.pack");
        enemyAtlas = new TextureAtlas("enemy.pack");
        gameOverAtlas = new TextureAtlas("gameover.pack");
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(getWorldBounds());
        enemyEmitter = new EnemyEmitter(enemyAtlas, getWorldBounds(), enemyPool, bulletPool, bulletAtlas, explosions);
        gameOver = new Label(gameOverAtlas.findRegion("gameover"), new Vector2(0,0.2f),
                Gdx.audio.newSound(Gdx.files.internal("music/gameover.mp3")));
        position = new Vector2();
        background = new Background(backgroundPict);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas, textureNameArray);
        }
        ufo = new Ufo(ufoAtlas,true, this);
        ufo.setExplosions(explosions);
        ufo.setGun(new PlasmGun(bulletPool, true, bulletAtlas, getWorldBounds(), this.ufo));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/game.mp3"));
        music.setLooping(true);
        music.play();
        music.setVolume(0.1f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();

        checkCollisions();

        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ufo.draw(batch);
        gameOver.draw(batch);
        enemyPool.drawActiveSprites(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundPict.dispose();
        atlas.dispose();
        ufoAtlas.dispose();
        music.dispose();
        bulletPool.dispose();
        bulletAtlas.dispose();
        enemyAtlas.dispose();
        gameOverAtlas.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        ufo.resize(worldBounds);
        gameOver.resize(worldBounds);
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
    private void checkCollisions(){
        for (EnemyShip enemy: enemyPool.getActiveObjects()) {
            if (enemy.isDestroyed() || enemy.isBlowing()){
                continue;
            }
            if (!enemy.isOutside(ufo) && !enemy.isBlowing()){
                ufo.makeDamage(enemy.getHp());
                enemy.blow();
            }
        }

        for (Bullet bullet: bulletPool.getActiveObjects()) {
            if (bullet.isDestroyed() || bullet.isBlowing()){
                continue;
            }
            for (EnemyShip enemy: enemyPool.getActiveObjects()) {
                if (enemy.isDestroyed() || enemy.isBlowing()) {
                    continue;
                }
                if (bullet.getOwner() == ufo && enemy.isMe(bullet.getPosition())){
                    enemy.makeDamage(bullet.getDamage());
                    bullet.blow();
                }

                if (bullet.getOwner() != ufo && ufo.isMe(bullet.getPosition()) && !ufo.isBlowing()){
                    ufo.makeDamage(bullet.getDamage());
                    bullet.blow();
                }
            }
        }
    }

    public void gameOver(){
        this.music.stop();
        this.enemyEmitter.stop();
        bulletPool.stop();
        enemyPool.stop();
        gameOver.activate();
    }


}