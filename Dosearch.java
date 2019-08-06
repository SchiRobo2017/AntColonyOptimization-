package antSystemForGraduateStudy;

import java.util.Iterator;

/**
 * 探索を行うクラス。メインメソッドを持つ
 * @author kouya
 *
 */
public class Dosearch {
	Ant[] a;
	/**
	 * フェロモン蒸発率 ρ
	 */
	private double ro;

	public static void main(String[] args) {
		ProblemGenerator.generatePresetProblem("presetGraph_deg100.log", 100); //問題の生成

		Dosearch d = new Dosearch();
		d.doACO(0.0, 100.0, 1.0, 5, 100, 0.3);

		//Resultに問題ファイル名を保存
		Result.saveFile();

		Graphic g = new Graphic();
		g.show();
	}

	/**
	 * 蟻コロニー最適化を行う
	 * @param alpha α
	 * @param beta β
	 * @param q Q
	 * @param numAnts 蟻の個体数
	 * @param numIteration 反復回数
	 * @param ro フェロモン蒸発率
	 */
	void doACO(double alpha, double beta, double q, int numAnts, int numIteration, double ro){
		//Resultにパラメタの組み合わせを保存
		Result.alpha = alpha;
		Result.beta = beta;
		Result.q = q;
		Result.numAnts = numAnts;
		Result.numItr = numIteration;
		Result.evaporateRatio = ro;

		initAnt(alpha, beta, q, numAnts, ro);
		searchAnt(numIteration);
	}

	/**
	 * doACOで呼び出されるメソッド。
	 * 蟻のインスタンス生成と初期化
	 * 一回目の探索が行われる
	 * @param alpha
	 * @param beta
	 * @param q
	 * @param numAnts
	 * @param ro
	 */
	void initAnt(double alpha, double beta, double q, int numAnts, double ro){
		System.out.println(alpha + "," + beta + "," + q + "," + numAnts + "," + ro);
		Ant.setQ(q);
		this.a = new Ant[numAnts];
		for(int i=0;i<numAnts;i++){
			System.out.print(0 + ",");
			System.out.print(i + ",");
			a[i] = new Ant();
			a[i].tour(); //最初の一回
		}

		for(int i=0;i<numAnts;i++){
			a[i].addPheromone();
		}

		this.ro = ro;
		Ant.setAlpha(alpha);
		Ant.setBeta(beta);
	}

	/**
	 * doACOで呼び出されるメソッド。
	 * 全ての蟻が巡回路を求めたあと、フェロモンを付与する
	 * @param numIteration 反復回数
	 */
	void searchAnt(int numIteration){
		for(int i=1;i<numIteration;i++){
			for(int j=0;j<a.length;j++){
				System.out.print(i + ","); //反復回数
				System.out.print(j + ","); //蟻番号
				a[j].tour();
			}

			//AS
			for(int j=0;j<a.length;j++){
				a[j].addPheromone();
			}
		}

		for( Iterator<Edge> i = ProblemGenerator.Graph.getEdges().iterator(); i.hasNext();){
			(i.next()).pheromone *= (1-ro);
		}
	}

	public double getRo() {
		return ro;
	}

	public void setRo(double ro) {
		this.ro = ro;
	}
}