package GameObjects;

public class GridPoint {

	private int row;
	private int column;
	
	public GridPoint(int _r, int _c) {
		row = _r;
		column = _c;
	}
	
	public GridPoint(GridPoint pos)
	{
		row = pos.getRow();
		column = pos.getColumn();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumn() {
		return column;
	}
}
