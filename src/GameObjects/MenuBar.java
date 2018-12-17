package GameObjects;

import android.opengl.Matrix;
import Primitives.Sprite;
import Text.Text;
import Utilities.WorldPoint;

public class MenuBar extends GameObject implements IDrawable, IMovable{

	private Sprite sMenuBar;
	private Sprite sResources;
	private Text tPlayerResources;
	
	private int playerResources;
	
	private float[] transMatrix;
	private float[] MVPMatrix;
	
	public MenuBar(float x, float y, float width, float height) 
	{
		worldPosition = new WorldPoint(x,y);
		
		sMenuBar = new Sprite(worldPosition.getX(),worldPosition.getY(),width,height,new String[]{"ingame_menu_bar"});
		sResources = new Sprite(worldPosition.getX()-width/4.0f,worldPosition.getY(),0.5f,0.5f,new String[]{"resources"});
		
		tPlayerResources = new Text();
		
		transMatrix = new float[16];
		MVPMatrix = new float[16];
		Matrix.setIdentityM(transMatrix, 0);
		Matrix.setIdentityM(MVPMatrix, 0);
	}
	
	@Override
	public void move(double deltaT) {
		// TODO Auto-generated method stub
		
	}
	
	public void move(float dx, float dy) {

		float[] newT = new float[16];
		
		Matrix.setIdentityM(newT, 0);
		Matrix.setIdentityM(transMatrix, 0);
		
		Matrix.translateM(newT , 0, dx, dy, 0.0f);
		Matrix.multiplyMM(transMatrix, 0, transMatrix, 0, newT, 0);
		
	}

	@Override
	public WorldPoint getPosition() {
		
		float[] v_pos = worldPosition.getVector();
		float[] t_pos = new float[4];
		
		Matrix.multiplyMV(t_pos, 0, transMatrix, 0, v_pos, 0);
		
		return new WorldPoint(t_pos[0], t_pos[1]);
	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(int pr, float cam_pos_x, float cam_pos_y) {
		playerResources = pr;
		
		float dx, dy;
		
		dx = cam_pos_x-worldPosition.getX();
		dy = cam_pos_y-worldPosition.getY();
		
		move(dx,dy);
		
	}

	@Override
	public void draw(float[] mvpMatrix) {
		Matrix.multiplyMM(MVPMatrix, 0, mvpMatrix, 0, transMatrix, 0);
	
		sMenuBar.draw(MVPMatrix);
		
		sResources.draw(MVPMatrix);
		
		tPlayerResources.drawText(Integer.toString(playerResources), getPosition().getX()-3.0f/4.0f+0.8f, getPosition().getY(), MVPMatrix);
	}

}
