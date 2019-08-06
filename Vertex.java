package antSystemForGraduateStudy;

/**
 * 都市クラス
 * @author kouya
 *
 */
public class Vertex {
	/**
	 * 都市番号
	 */
	private int index;
	/**
	 * x座標
	 */
	private double x;
	/**
	 * y座標
	 */
	private double y;

	Vertex(int index,double x,double y){
		this.index = index;
		this.x = x;
		this.y = y;
	}

	@Override
    public String toString() {
		String iStr = String.valueOf(index);
		String xStr = String.valueOf(x);
		String yStr = String.valueOf(y);
		String s = "index:" + iStr + " x:" + xStr + " y:" + yStr + "\n";
        return s;
    }

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
