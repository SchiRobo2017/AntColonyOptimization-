package antSystemForGraduateStudy;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 個々の蟻の振る舞いに関するクラス
 * @author Kouya
 */
public class Ant {
	/**
	 * α
	 */
	private static double alpha = 0.0;
	/**
	 * β
	 */
	private static double beta  = 0.0;
	/**
	 * Q
	 */
	private static double q     = 1.0;
	/**
	 * 未訪問点集合
	 */
	private ArrayList<Vertex> unvisited;
	/**
	 * 現在地
	 */
	private Vertex presentCity = null;
	/**
	 * 巡回路
	 */
	private ArrayList<Vertex> trail;
	/**
	 * 巡回路長
	 */
	private double length;
	/**
	 * 次の都市を選択する確率を格納するリスト
	 */
	private ArrayList<Double> p;
	/**
	 * 確率リストに対応する都市のリスト
	 */
	private ArrayList<Vertex> p_cities;

	/**
	 * コンストラクタ
	 */
 	Ant(){
		unvisited = new ArrayList<Vertex>();
		trail= new ArrayList<Vertex>();
		p = new ArrayList<Double>();
		p_cities = new ArrayList<Vertex>();
		length = Integer.MAX_VALUE;
	}

 	/**
 	 * 巡回路を求める
 	 */
	public void tour(){
		tourInitializer();

		while(!unvisited.isEmpty()){
			try{
				calcProbability();
			}catch(Exception ex){
				System.out.println("error");
			}

			chooseVertex();

			p.clear();
			p_cities.clear();
		}

		calcAllLength();
	}

	/**
	 * tour()の初期化
	 */
	private void tourInitializer() {
		unvisited.clear();
		unvisited.addAll( ProblemGenerator.Graph.getVertices() );

		trail.clear();

		chooseVertex();
	}

	/**
	 * 現在地から次の都市に移動する
	 */
	private void chooseVertex(){
		if(presentCity==null)
		{
			presentCity = unvisited.get((int)(Math.random()*unvisited.size()));
		}else{
			double temp = Math.random();
			for(int i=0; i<p.size();i++){
				double ptemp = (double)p.get(i);
				temp -= ptemp;
				if( temp<ptemp ){
					presentCity = p_cities.get(i);
					break;
				}
			}
		}

		trail.add(presentCity);

		unvisited.clear();
		unvisited.addAll( ProblemGenerator.Graph.getNeighbors(presentCity) );
		unvisited.removeAll(trail);
	}

	/**
	 * 次の都市を選ぶ確率を計算する
	 * @throws Exception
	 */
	private void calcProbability() throws Exception{
		if(presentCity == null){
			System.out.println("calcProbability: presentloc=null ");
			throw new Exception();
		}

		double pheromonePrs;
		double etaPrs;
		double pheromone;
		double eta;
		double bunbo = 0;
		double bunshi;

		p.clear();
		p_cities.clear();

		for( Iterator<Vertex> j = unvisited.iterator(); j.hasNext();){
			Vertex buf_Loc = j.next();
			pheromone = (ProblemGenerator.Graph.findEdge(presentCity,buf_Loc)).pheromone;
			eta = (ProblemGenerator.Graph.findEdge(presentCity,buf_Loc)).getDistance();
			eta = 1/eta;
			bunbo += Math.pow(pheromone, alpha)*Math.pow(eta, beta);
		}

		for( Iterator<Vertex> i = unvisited.iterator(); i.hasNext();){
			Vertex buf_nextLoc = i.next();
			pheromonePrs = (ProblemGenerator.Graph.findEdge(presentCity,buf_nextLoc)).pheromone;
			etaPrs = (ProblemGenerator.Graph.findEdge(presentCity,buf_nextLoc)).getDistance();
			etaPrs = 1/etaPrs;
			bunshi = Math.pow(pheromonePrs, alpha)*Math.pow(etaPrs, beta);

			p.add(bunshi/bunbo);
			p_cities.add(buf_nextLoc);
		}
	}

	private void calcAllLength(){
		length = 0.0;
		Vertex sp;
		Vertex ep;

		if(trail.size()==ProblemGenerator.Graph.getVertexCount()) //����H������ꂽ
		{
			for(int i=0;i<trail.size();i++){
				if(i==trail.size()-1){
					sp = trail.get(i);
					ep = trail.get(0);
				}else{
					sp = trail.get(i);
					ep = trail.get(i+1);
				}

				double d,x0,y0,x1,y1;
				x0 = sp.getX();
				y0 = sp.getY();
				x1 = ep.getX();
				y1 = ep.getY();
				d = Math.sqrt( Math.pow(x1-x0, 2.0) + Math.pow(y1-y0, 2.0) ); //sqrt( (x1-x0)^2+(y1-y0)^2 )
				length += d;
			}

			Result.trailList.add(trail);
			Result.length.add(length);
			System.out.println(length);
		}
	}

	/**
	 * 得られた巡回路長に応じてフェロモンを付与する
	 */
	public void addPheromone(){
		if(trail.size()<ProblemGenerator.Graph.getVertexCount()){
			System.out.println("addPheromone：巡回路が完成していない");
		}

		Edge e;

		for(int i=0; i<(trail.size()-1);i++){
			Vertex v1,v2;
			v1 = trail.get(i);
			v2 = trail.get(i+1);
			e = ProblemGenerator.Graph.findEdge(v1, v2);
			e.pheromone += q/length;
		}

		e = ProblemGenerator.Graph.findEdge(trail.get( trail.size()-1 ), trail.get(0));
		e.pheromone += q/length;

	}

	public static void setAlpha(double alpha) {
		Ant.alpha = alpha;
	}

	public static void setBeta(double beta) {
		Ant.beta = beta;
	}

	public static void setQ(double q) {
		Ant.q = q;
	}

	/**
	 *
	 * @return length この蟻が得た巡回路の長さ
	 */
	public double getLength() {
		return length;
	}

}