package antSystemForGraduateStudy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 探索の結果を格納するクラス
 * @author kouya
 *
 */
public class Result {
	/**
	 * 得られた巡回路のすべてを格納するリスト
	 */
	public static ArrayList< ArrayList<Vertex> > trailList = new ArrayList< ArrayList<Vertex> >();
	/**
	 * 巡回路長を格納するリスト
	 */
	public static ArrayList<Double> length = new ArrayList<Double>();
	/**
	 * 都市数
	 */
	public static int numCities;
	/**
	 * α
	 */
	public static double alpha;
	/**
	 * β
	 */
	public static double beta;
	/**
	 * Q
	 */
	public static double q;
	/**
	 * 蟻の個体数
	 */
	public static int numAnts;
	/**
	 * 反復回数
	 */
	public static int numItr;
	/**
	 * フェロモンの蒸発率
	 */
	public static double evaporateRatio;
	/**
	 * 最良の巡回路を見つけた蟻のインデックス
	 */
	public static int indexBest;
	/**
	 * 最良の巡回路が見つかった反復
	 */
	public static double ItrBest;

	/**
	 *
	 * @return Result.lengthにおける最良の巡回路の格納箇所
	 */
	static int findAntIndexOfBestTour(){
		double best = Double.MAX_VALUE;
		int counter = 0;
		int indexBest = 0;
		double tmp;

		for(Iterator<Double> i = length.iterator(); i.hasNext(); ){
			tmp = i.next();
			if(tmp<best){
				best = tmp;
				indexBest = counter;
			}
			counter++;
		}

		return indexBest;
	}

	/**
	 * 探索の結果をCSV形式で保存する
	 */
	public static void saveFile() {
		String fileName = Double.toString(alpha) + "_" +
						  Double.toString(beta) + "_" +
						  Double.toString(q) + "_" +
						  Integer.toString(numAnts) + "_" +
						  Integer.toString(numItr) + "_" +
						  Double.toString(evaporateRatio) + "_" +
						  Integer.toString(numCities) +
						  ".CSV";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			int numAntsCtr =0;
			int numItrCtr = 0;

			//書き込み処理
			writer.write( fileName );
			writer.write("\n");
			writer.write("alpha,beta,q,numAnts,numItr,evaporateRatio");
			writer.write("\n");
			writer.write( alpha + "," + beta + "," + q + "," + numAnts + "," + numItr + "," + evaporateRatio);
			writer.write("\n");
			writer.write("min," + findBestLength() );
			writer.write("\n");
			writer.write("itrOfThisSolution," + (int)findAntIndexOfBestTour()/numAnts);
			writer.write("\n\n");
			writer.write("Iteration,Index of Ant,Length");
			writer.write("\n");

			for(Double l : length){
				writer.write(numItrCtr + ",");
				writer.write(numAntsCtr + ",");
				writer.write( String.valueOf(l) );
				writer.write("\n");

				numAntsCtr++;

				if(numAntsCtr == numAnts){
					numAntsCtr = 0;
					numItrCtr++;
				}
			}

			writer.close();

		} catch(IOException ex){
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * @return 最良の巡回路長
	 */
	private static double findBestLength() {
		int i = findAntIndexOfBestTour();
		return length.get(i);
	}
}
