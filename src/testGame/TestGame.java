package testGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGame implements ApplicationListener {
	private OrthographicCamera camera;
	
	private Bob b;
	private SpriteBatch BobBatch;
	
	private Texture bg;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		BobBatch = new SpriteBatch();
		
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2); 	//Moving the camera so (0,0) becomes lower left corner instead of center.
		camera.update();													//Updating the camera.
		
		b = new Bob("data/bob16x16.png");
		b.setSize(16f, true);
		b.setOrigin(b.getSize().x/2, b.getSize().y/2);
		b.setPosition(0, 0);
		
		
		bg = new Texture(Gdx.files.internal("data/testgamemap.png"));
	
	}
	
	
	
	@Override
	public void dispose() {
		BobBatch.dispose();
		b.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);	
		
		b.update();
		
		BobBatch.setProjectionMatrix(camera.combined);
		BobBatch.begin();
		BobBatch.draw(bg, 0, 0, camera.viewportWidth, camera.viewportHeight);
		b.draw(BobBatch);
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
