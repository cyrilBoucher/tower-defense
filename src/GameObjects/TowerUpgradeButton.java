package GameObjects;

import Primitives.Sprite;

public class TowerUpgradeButton extends Button{

	//_Debug : if no texture handling, uncomment
	//private Square background;
	//private Arrow upgradeArrow;
	
	private Sprite sUpgrade;
	
	private Tower towerToUpgrade;
	
	public TowerUpgradeButton(float x, float y, float width, float height, Tower t2u) {
		super(x, y, width, height);
		
		//_Debug : if no texture handling, uncomment
		//background = new Square(x,y,width,height,new float[]{0.8f,0.79f,0.79f,1.0f});
		//upgradeArrow = new Arrow(x,y,width-0.05f,height-0.05f);
		
		sUpgrade = new Sprite(x,y,width,height,new String[]{"upgrade_button"});
		
		towerToUpgrade = t2u;
		
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		//_Debug : if no texture handling, uncomment
		//background.draw(mvpMatrix);
		//upgradeArrow.draw(mvpMatrix);
		
		sUpgrade.draw(mvpMatrix);
		
		//_Debug : uncomment for collision debugging
		//touchBox.draw(mvpMatrix);
	}
	
	@Override
	public void click(float x, float y) {

		towerToUpgrade.askForClosing();
		
		towerToUpgrade.askForUpgrade();
		
	}

	
}
