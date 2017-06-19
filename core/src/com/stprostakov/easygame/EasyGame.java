package com.stprostakov.easygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class EasyGame implements Screen {

    final Zachet game;

    Texture zachetImage;
    Texture studentImage;
    SpriteBatch batch;
    OrthographicCamera camera;
    Rectangle student;
    Array<Rectangle> zachet;
    long lastDropTime;


    public EasyGame(final Zachet game1) {
        this.game =game1;
        zachetImage = new Texture(Gdx.files.internal("core/assets/zachet.png"));
        studentImage = new Texture(Gdx.files.internal("core/assets/student.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        student = new Rectangle();
        student.x = 800 / 2 - 64 / 2;
        student.y = 20;
        student.width = 64;
        student.height = 64;

        zachet = new Array<Rectangle>();
        spawnZachet();
    }

    private void spawnZachet() {
        Rectangle zachet = new Rectangle();
        zachet.x = MathUtils.random(0, 800 - 64);
        zachet.y = 416;
        zachet.width = 64;
        zachet.height = 64;
        this.zachet.add(zachet);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.8f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(studentImage, student.x, student.y);
        for (Rectangle zachets : zachet) {
            batch.draw(zachetImage, zachets.x, zachets.y);
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            student.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) student.x -= 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) student.x += 400 * Gdx.graphics.getDeltaTime();

        if (student.x < 0) student.x = 0;
        if (student.x > 800 - 64) student.x = 800 - 64;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnZachet();

        Iterator<Rectangle> iter = zachet.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) iter.remove();
            if (raindrop.overlaps(student)) {
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        zachetImage.dispose();
        studentImage.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }
}
