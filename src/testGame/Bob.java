package testGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bob {
	
	private Texture tex;
	private Sprite spr;
	
	private float scale = 1;
	private float speed = 15f;
	private float ratio = 0;
	private Vector2 size = new Vector2(0,0);
	private Vector2 origin = new Vector2(0,0);
	private Vector2 position = new Vector2(0,0);
	private Vector2 direction = new Vector2(0,0);
	
	public Bob(String path)
	{
		tex = new Texture(Gdx.files.internal(path));
		spr = new Sprite(tex);
		this.ratio = spr.getHeight()/spr.getWidth();
		this.size.set(spr.getWidth(), spr.getHeight());
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
		spr.setScale(scale, scale);
	}

	public Vector2 getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2 origin) {
		this.origin.set(origin);
		spr.setOrigin(origin.x, origin.y);
	}
	
	public void setOrigin(float x, float y) {
		this.origin.set(x, y);
		spr.setOrigin(origin.x, origin.y);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
		spr.setPosition(position.x - spr.getOriginX(), position.y - spr.getOriginY());
	}
	
	public void setPosition(float x, float y)
	{
		this.position.set(x,y);
		spr.setPosition(position.x - origin.x, position.y - origin.y);
	}
	
	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size.set(size);
		spr.setSize(size.x, size.y);
	}
	
	public void setSize(float x, float y) {
		this.size.set(x, y);
		spr.setSize(x, y);
	}
	
	public void setSize(float size, boolean ratio)
	{
		if (ratio)
			this.size.set(size, size * this.ratio);
		else
			this.size.set(size,size);
		spr.setSize(this.size.x, this.size.y);
	}

	public void update()
	{
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) //If LEFT mouse button is pressed.
		{
			float diffx = Gdx.input.getX() - this.position.x;
			float diffy = (Gdx.graphics.getHeight() - Gdx.input.getY()) - this.position.y;
			direction.set(diffx, diffy);
			if (direction.len2() > 1)
			{
				direction.nor();
				direction.mul(speed * Gdx.graphics.getDeltaTime());
				spr.translate(direction.x,direction.y);
			}
		}
		
		direction.set(0, 0);
		
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			direction.add(0,1);
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			direction.add(-1,0);
		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			direction.add(0,-1);
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			direction.add(1,0);
		}
		if(direction.len2() >= 1)
		{
			direction.nor();
			direction.mul(speed * Gdx.graphics.getDeltaTime());
			spr.translate(direction.x,direction.y);
		}
		
		this.position.add(direction.x, direction.y);
	}
	
	public void draw(SpriteBatch batch)
	{
		spr.draw(batch);
	}
	
	public void dispose()
	{
		tex.dispose();
	}
	
	

}
