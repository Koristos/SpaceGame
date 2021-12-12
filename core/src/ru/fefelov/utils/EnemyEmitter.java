package ru.fefelov.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.impl.BigFireGun;
import ru.fefelov.mech.impl.BossFireGun;
import ru.fefelov.mech.impl.FireGun;
import ru.fefelov.pools.impl.BulletPool;
import ru.fefelov.pools.impl.EnemyPool;
import ru.fefelov.sprite.impl.EnemyShip;

public class EnemyEmitter {
    private final Rect worldBounds;
    private final EnemyPool enemyPool;
    private TextureAtlas enemyAtlas;
    private BulletPool bulletPool;
    private TextureAtlas bulletAtlas;
    private TextureRegion [] explosions;

    private static final float GENERATE_INTERVAL = 4f;
    private float generateTimer;
    private boolean working;

    private final Sound BOSS_SHOOT = Gdx.audio.newSound(Gdx.files.internal("music/fire2.mp3"));
    private final Sound ENEMY_SHOOT = Gdx.audio.newSound(Gdx.files.internal("music/fire1.mp3"));
    private final Sound ENEMY_HIT = Gdx.audio.newSound(Gdx.files.internal("music/fireDamage.mp3"));

    public EnemyEmitter(TextureAtlas enemyAtlas, Rect worldBounds, EnemyPool enemyPool, BulletPool bulletPool, TextureAtlas bulletAtlas, TextureRegion explosion) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        this.bulletPool = bulletPool;
        this.enemyAtlas = enemyAtlas;
        this.bulletAtlas = bulletAtlas;
        this.explosions = Regions.split(explosion, 6, 8, 48);
        this.working = true;
    }

    public void generate(float delta) {
        if (!working){
            return;
        }
        generateTimer += delta;
        if (generateTimer > GENERATE_INTERVAL) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type = (float) Math.random();

            if (type < 0.2f) {
                enemyShip.setProp(
                        new Vector2(0, -0.09f),
                        1,
                        enemyAtlas.findRegion("enemy1"),
                        0.08f,
                        1,
                        new FireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT),
                        explosions
                );
            } else if (type < 0.4f) {
                enemyShip.setProp(
                        new Vector2(0.06f, -0.06f),
                        1,
                        enemyAtlas.findRegion("enemy2"),
                        0.08f,
                        2,
                        new FireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT),
                        explosions
                );
            } else if (type < 0.6f){
                enemyShip.setProp(
                        new Vector2(0.1f, -0.025f),
                        2,
                        enemyAtlas.findRegion("enemy4"),
                        0.08f,
                        3,
                        new FireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT),
                        explosions
                );
            }else if (type < 0.75f) {
                enemyShip.setProp(
                        new Vector2(0, -0.04f),
                        1,
                        enemyAtlas.findRegion("enemy5"),
                        0.11f,
                        5,
                        new BigFireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT),
                        explosions
                );
            } else if (type < 0.9f) {
                enemyShip.setProp(
                        new Vector2(0.05f, -0.02f),
                        1,
                        enemyAtlas.findRegion("enemy6"),
                        0.11f,
                        5,
                        new BigFireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT),
                        explosions
                );
            } else {
                enemyShip.setProp(
                        new Vector2(0.03f, -0.001f),
                        2,
                        enemyAtlas.findRegion("enemy7"),
                        0.2f,
                        10,
                        new BossFireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                BOSS_SHOOT,
                                ENEMY_HIT),
                        explosions
                );
            }
            enemyShip.setBottom(worldBounds.getTop());
            enemyShip.pos.x = MathUtils.random(
                    worldBounds.getLeft() + enemyShip.getWidth(),
                    worldBounds.getRight() - enemyShip.getWidth()
            );
        }
    }

    public void stop(){
        this.working = false;
    }

    public void start(){
        generateTimer = 0f;
        this.working = true;
    }
}
