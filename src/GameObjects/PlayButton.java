package GameObjects;

import Primitives.Sprite;

import com.example.td.MyGame;

public class PlayButton extends Button{

	private Sprite sPlayButton;
	
	public PlayButton(float x, float y, float width, float height) {
		super(x, y, width, height);

		String[] file_names = new String[]{"play_button"};
		sPlayButton = new Sprite(x,y,width,height,file_names);
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		sPlayButton.draw(mvpMatrix);
		
		//_Debug : uncomment for collision debugging
		//touchBox.draw(mvpMatrix);
	}

	@Override
	public void click(float x, float y) {
		MyGame.changeState(MyGame.GameState.LOADING_GAME);
	}

}
