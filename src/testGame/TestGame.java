package testGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TestGame implements ApplicationListener {
	private OrthographicCamera camera;
	
	static final int WIDTH  = 480;
    static final int HEIGHT = 320;
	
	private Texture tex;
	private Sprite bob;
	private SpriteBatch BobBatch;
	
	private float speed = 0.1f;
	private boolean moving = false;
	private Vector2 direction = new Vector2(0,0);
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1f, h/w);
		BobBatch = new SpriteBatch();
		
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
		camera.update();
		tex = new Texture(Gdx.files.internal("data/bob16x16.png"));
		//tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bob = new Sprite(tex);
		bob.setSize(0.1f, 0.1f * bob.getHeight() / bob.getWidth());
		bob.setOrigin(bob.getWidth()/2, bob.getHeight()/2);
		bob.setPosition(bob.getX() - bob.getOriginX(), bob.getY() - bob.getOriginY());
		
	
	}
	
	
	
	@Override
	public void dispose() {
		BobBatch.dispose();
		tex.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);		
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT))
		{
			float diffx = (camera.viewportWidth / Gdx.graphics.getWidth()) * Gdx.input.getX() - bob.getX() - bob.getOriginX();
			float diffy = (camera.viewportHeight / Gdx.graphics.getHeight()) * (Gdx.graphics.getHeight() - Gdx.input.getY()) - bob.getY() - bob.getOriginY();
			direction.set(diffx, diffy);
			if (direction.len2() > 1/100000f)
			{
				direction.nor();
				direction.mul(speed);
				bob.translate(direction.x * Gdx.graphics.getDeltaTime(),direction.y * Gdx.graphics.getDeltaTime());
			}
		}
		
		BobBatch.setProjectionMatrix(camera.combined);
		BobBatch.begin();
		bob.draw(BobBatch);
		
		BobBatch.end();
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
}
