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
	
	private float speed = 0.1f; 					//We want it to move this amount every second.
	private Vector2 direction = new Vector2(0,0); 	//Direction which we will move.
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1f, h/w);
		BobBatch = new SpriteBatch();
		
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2); 	//Moving the camera so (0,0) becomes lower left corner instead of center.
		camera.update();													//Updating the camera.
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
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) //If LEFT mouse button is pressed.
		{
			/*
				Gdx.input.getY() returns the y coordinate of pointers location based on pixels but it is out of our boundary, which is between 0-1
				Also it takes top-left corner as (0,0) so we subtract it from height to revert it and make bottom-left the (0,0) point.
				We also multiply it with the ratio of our current camera boundaries and our windows's boundaries [camera.viewportHeight / Gdx.graphics.getHeight()]
				We set the direction vector with these differences.
				Then we check if te difference is greater than some value so it doesn't jump back and forth when it reaches the current mouse location
				Then we normalize the vector, making its length 1
				After that we multiplay it with our desired speed per second and deltaTime (time passed between last and current frame)
				Finally, we move our guy.
			*/
			float diffx = (camera.viewportWidth / Gdx.graphics.getWidth()) * Gdx.input.getX() - bob.getX() - bob.getOriginX();
			float diffy = (camera.viewportHeight / Gdx.graphics.getHeight()) * (Gdx.graphics.getHeight() - Gdx.input.getY()) - bob.getY() - bob.getOriginY();
			direction.set(diffx, diffy);
			if (direction.len2() > 1/100000f)
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
