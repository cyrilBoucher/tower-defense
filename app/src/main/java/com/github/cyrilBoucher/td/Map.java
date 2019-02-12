package com.github.cyrilBoucher.td;

import android.content.res.AssetManager;

import com.github.cyrilBoucher.td.GameObjects.Base;
import com.github.cyrilBoucher.td.GameObjects.Enemy;
import com.github.cyrilBoucher.td.GameObjects.UnusableBlock;
import com.github.cyrilBoucher.td.GameObjects.UsableBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.github.cyrilBoucher.td.Primitives.Sprite;
import com.github.cyrilBoucher.td.Utilities.GridPoint;

public class Map {
	private Sprite sMap;
	private int sizeX;
	private int sizeY;
	private int camPosX;
	private int camPosY;
	private int basePosX;
	private int basePosY;
	private GridPoint startPos;
	private GridPoint endPos;
	private int numberOfWaves;

	private List<UnusableBlock> mUnusableBlockList;
	private List<UsableBlock> mUsableBlockList;
	private Base base;
	private short[] spots;
	private short[] path;
	private LinkedList<GridPoint> mapList;

	public Map(AssetManager am, String file_name) {
		mapList = new LinkedList<GridPoint>();

		try {
			getInfoFromFile(am, file_name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sMap = new Sprite(sizeX/2.0f, sizeY/2.0f, sizeX, sizeY, new String[]{"map1"});

		createBlocks();
	}

	public void draw(float[] mvpMatrix) {
		sMap.draw(mvpMatrix);
	}

	private boolean getInfoFromFile(AssetManager am, String file_name) throws IOException {
		InputStream is = am.open(file_name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String[] parts;

		// Reading first line : Map Size
		if ((line = reader.readLine()) != null) {
			parts = line.split("\\s");

			if (parts.length != 3)
				return false;

			if (parts[0].equals("sm")) {
				sizeX = Integer.valueOf(parts[1]);
				sizeY = Integer.valueOf(parts[2]);

				spots = new short[sizeX * sizeY];
				path = new short[sizeX * sizeY];
			}
		}

		// Reading second line : Camera Position
		if ((line = reader.readLine()) != null) {
			parts = line.split("\\s");

			if (parts.length != 3)
				return false;

			if (parts[0].equals("cp")) {
				camPosX = Integer.valueOf(parts[1]);
				camPosY = Integer.valueOf(parts[2]);
			}
		}

		// Reading third line : Base Position
		if ((line = reader.readLine()) != null) {
			parts = line.split("\\s");

			if (parts.length != 3)
				return false;

			if (parts[0].equals("bp")) {
				basePosX = Integer.valueOf(parts[1]);
				basePosY = Integer.valueOf(parts[2]);
				base = new Base(new GridPoint(basePosY, basePosX));
			}
		}

		// Reading fourth line : Start Position
		if ((line = reader.readLine()) != null) {
			parts = line.split("\\s");

			if (parts.length != 3)
				return false;

			if (parts[0].equals("sp")) {
				startPos = new GridPoint(Integer.valueOf(parts[2]),
						Integer.valueOf(parts[1]));
			}
		}

		// Reading fifth line : End Position
		if ((line = reader.readLine()) != null) {
			parts = line.split("\\s");

			if (parts.length != 3)
				return false;

			if (parts[0].equals("ep")) {
				endPos = new GridPoint(Integer.valueOf(parts[2]),
						Integer.valueOf(parts[1]));
				mapList.add(endPos);
			}
		}
		
		// Reading sixth line : Number Of Waves
		if ((line = reader.readLine()) != null) {
			parts = line.split("\\s");

			if (parts.length != 2)
				return false;
			
			if (parts[0].equals("nw")) {
				numberOfWaves =	Integer.valueOf(parts[1]);
			}
		}

		int j = 0;

		// Reading last lines : Blocks position
		while ((line = reader.readLine()) != null) {
			parts = line.split("\\s");
			
			if (parts.length != sizeX + 1)
				return false;

			if (parts[0].equals("l")) {
				for (int i = 0; i < sizeX; i++) {
					spots[i + j * sizeX] = Short.valueOf(parts[i + 1]);

					if (Short.valueOf(parts[i + 1]) == 0) {
						path[i + j * sizeX] = -1;
					} else {
						path[i + j * sizeX] = Short.MAX_VALUE;
					}
				}
				j++;
			}
		}
		
		reader.close();

		makePath();

		return true;
	}

	private void createBlocks() {
		mUnusableBlockList = new LinkedList<UnusableBlock>();
		mUsableBlockList = new LinkedList<UsableBlock>();

		for (int i = 0; i < sizeY; i++) 
		{
			for (int j = 0; j < sizeX; j++) 
			{
				if (spots[j + i * sizeX] == 2) 
				{
					mUnusableBlockList.add(new UnusableBlock(j + 1, i + 1));
				}
				else if(spots[j + i * sizeX] == 1)
				{
					mUsableBlockList.add(new UsableBlock(j + 1, i + 1));
				}
			}
		}
	}

	public GridPoint getDestination(Enemy e) 
	{
		int[] dx = new int[] { 0, 1, 0, -1 };
		int[] dy = new int[] { -1, 0, 1, 0 };
		
		GridPoint pos = e.getGridPosition();

		int smaller = path[(pos.getColumn()-1) + (pos.getRow()-1) * sizeX];
		
		GridPoint result = null;
		
		for (int i = 0; i < 4; i++) 
		{
			int x = (pos.getColumn()-1) + dx[i];
			int y = (pos.getRow()-1) + dy[i];
			
			if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) 
			{
				if (path[x + y * sizeX] < smaller) 
				{
					smaller = path[x + y * sizeX];
					result = new GridPoint(y+1, x+1);
				}
			}
		}
		return result;
	}

	private void makePath() {
		int valueMapPath = -1;

		while (!mapList.isEmpty()) {
			GridPoint pos = mapList.removeFirst();
			path[(pos.getColumn() - 1) + (pos.getRow() - 1) * sizeX] = (short) ++valueMapPath;

			int index = (int) ((pos.getColumn() - 1) + sizeX * (pos.getRow() - 1));

			if (index < path.length - sizeX && path[index + sizeX] == -1) 
			{
				mapList.add(new GridPoint(pos.getRow() + 1, pos.getColumn()));
			} 
			else if (index > sizeX && path[index - sizeX] == -1) 
			{
				mapList.add(new GridPoint(pos.getRow() - 1, pos.getColumn()));
			} 
			else if (index != (pos.getRow() - 1) * sizeX && path[index - 1] == -1) 
			{
				mapList.add(new GridPoint(pos.getRow(), pos.getColumn() - 1));
			}
			else if (index != (pos.getRow() * sizeX) - 1 && path[index + 1] == -1) 
			{
				mapList.add(new GridPoint(pos.getRow(), pos.getColumn() + 1));
			}
		}

		++valueMapPath;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getCamPosX() {
		return camPosX;
	}

	public int getCamPosY() {
		return camPosY;
	}

	public short[] getSpots() {
		return spots;
	}

	public short[] getPath() {
		return path;
	}

	public void setSpots(int x, int y, short value) {
		spots[x + y * sizeX] = value;
	}
	
	public GridPoint getStartPos() {
		return startPos;
	}
	
	public GridPoint getEndPos() {
		return endPos;
	}

	public int getNumberOfWaves() {
		return numberOfWaves;
	}

	public List<UnusableBlock> getUnusableBlockLists() {
		return mUnusableBlockList;
	}
	
	public List<UsableBlock> getUsableBlockLists() {
		return mUsableBlockList;
	}
	
	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
}
