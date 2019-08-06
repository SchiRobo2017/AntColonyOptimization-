package antSystemForGraduateStudy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * 問題を生成するクラス
 * @author kouya
 *
 */
public class ProblemGenerator {
	public static UndirectedSparseGraph<Vertex,Edge> Graph = new UndirectedSparseGraph<Vertex,Edge>();
	/**
	 * 問題の都市集合
	 */
	private static ArrayList<Vertex> verticesSet;

	/**
	 * 引数で指定した数だけ、都市をランダムに生成する。都市の座標はx座標、y座標ともに(0,1)の範囲
	 * @param numCities 生成する都市数
	 */
	public static void generaterandomProblem(int numCities){
		generateCities(numCities);
		connectAllCities(numCities);
	}

	/**
	 * テキストファイルから問題を読み込む
	 * @param fileName ファイルのパス
	 * @param numCities 読み込む問題の都市数
	 */
	public static void generatePresetProblem(String fileName,int numCities){
		Result.numCities = numCities;
		loadCities(fileName);
		connectAllCities(numCities);
	}

	/**
	 * generaterandomProblemで呼び出されるメソッド。
	 * 指定した数だけランダムに都市を生成する
	 * @param numCities
	 */
	private static void generateCities(int numCities){
		verticesSet = new ArrayList<Vertex>();

		for(int i=1;i<=numCities;i++){
			double x = Math.random();
			double y = Math.random();

			Vertex vertex = new Vertex(i,x,y);

			verticesSet.add(vertex);
		}
	}

	/**
	 * generaterandomProblem,generatePresetProblemで呼び出されるメソッド。
	 * 全ての点を接続し完全グラフを作る
	 * @param numCities 都市数
	 */
	private static void connectAllCities(int numCities){
		Vertex sp;
		Vertex ep;
		String edgeLabel;
		double distance,x0,y0,x1,y1;

		for(int i=0;i<numCities-1;i++){

			sp = verticesSet.get(i);
			x0 = sp.getX();
			y0 = sp.getY();

			for(int j=i+1;j<numCities;j++){
				ep = verticesSet.get(j);
				x1 = ep.getX();
				y1 = ep.getY();

				double x1minusx0, y1minusy0;
				x1minusx0 = x1-x0;
				y1minusy0 = y1-y0;
				double powX,powY;
				powX = x1minusx0*x1minusx0;
				powY = y1minusy0*y1minusy0;
				distance = Math.sqrt( powX + powY );

				edgeLabel = sp.getIndex() + "-" + ep.getIndex();
				Graph.addEdge(new Edge(edgeLabel,distance), sp, ep);
			}
		}
	}

	/**
	 * 問題のデータが格納されたテキストファイルを読み込む
	 * @param fileName ファイルのパス
	 */
	public static void loadCities(String fileName){
		try{
			File myFile = new File(fileName);
			System.out.println(fileName);
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line;

			verticesSet = new ArrayList<Vertex>();

			while( (line =reader.readLine()) != null ){
				String [] oneline = line.split(",");

				for(int i=0;i<oneline.length;i+=3){
					int index = Integer.valueOf(oneline[i]);
					double x = Double.valueOf(oneline[i+1]);
					double y = Double.valueOf(oneline[i+2]);

					verticesSet.add(new Vertex(index,x,y));
					}
			}
			reader.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * コンソールに問題を出力する。一行ごとに[都市番号,x座標,y座標]と出力する
	 * @param numCities 都市数
	 */
	public static void printProblem(int numCities){ //プリセット問題の点座標生成用
		for(int i=1;i<=numCities;i++){
			double x = Math.random();
			double y = Math.random();

			System.out.println(i + ","  + x +  "," + y);
		}
	}
}