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
	
	private Texture tex;
	private Sprite bob;
	private SpriteBatch BobBatch;
	
	private float speed = 5f; 					//We want it to move this amount every second.
	private Vector2 direction = new Vector2(0,0); 	//Direction which we will move.
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		BobBatch = new SpriteBatch();
		
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2); 	//Moving the camera so (0,0) becomes lower left corner instead of center.
		camera.update();													//Updating the camera.
		tex = new Texture(Gdx.files.internal("data/bob16x16.png"));
		//tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bob = new Sprite(tex);
		bob.setSize(16f, 16f * bob.getHeight() / bob.getWidth());
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
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) //If LEFT mouse button is pressed.
		{
			float diffx = Gdx.input.getX() - bob.getX() - bob.getOriginX();
			float diffy = (Gdx.graphics.getHeight() - Gdx.input.getY()) - bob.getY() - bob.getOriginY();
			direction.set(diffx, diffy);
			if (direction.len2() > 1)
			{
				direction.nor();
				direction.mul(speed * Gdx.graphics.getDeltaTime());
				bob.translate(direction.x,direction.y);
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
