package antSystemForGraduateStudy;

/**
 * 辺クラス
 * @author kouya
 *
 */
public class Edge {
	/**
	 * 辺の名前。都市1-都市2
	 */
    String label;
    //この辺の長さ
    private double distance;
    //この辺のフェロモン値
    double pheromone;

    /**
     * コンストラクタ
     * @param s 辺の名前
     * @param d 辺の長さ
     */
    Edge(String s, double d) {
        label = s;
        distance = d;
        pheromone = 0.01;
    }

    @Override
    public String toString() {
        return label;
    }

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}