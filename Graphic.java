package antSystemForGraduateStudy;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 探索結果をGUIで表示するクラス
 * @author kouya
 *
 */
public class Graphic extends JPanel{

	public void show(){
		JFrame frame = new JFrame();

		Graphic app = new Graphic();
		frame.getContentPane().add(app);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 550);
		String title = Double.toString(Result.alpha) + "_" +
				Double.toString(Result.beta) + "_" +
				Double.toString(Result.q) + "_" +
				Integer.toString(Result.numAnts) + "_" +
				Integer.toString(Result.numItr) + "_" +
				Double.toString(Result.evaporateRatio) + "_" +
				Integer.toString(Result.numCities);
		frame.setTitle(title);

		frame.setVisible(true);
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		dispParameters(g2);
		drawFrame(g2);
		drawVertices(g2);
		drawTour(g2);
	}

	/**
	 * 都市(点)を描画
	 * @param g2
	 */
	private void drawVertices(Graphics2D g2) {
		BasicStroke wideStroke;
		Vertex v;
		double x;
		double y;
		wideStroke = new BasicStroke(3.0f);
		for( Iterator<Vertex> i = ProblemGenerator.Graph.getVertices().iterator(); i.hasNext();){

			v = i.next();
			x = v.getX()*500;
			y = v.getY()*500;

			g2.setStroke(wideStroke);
			g2.draw(new Line2D.Double(x, y, x, y));
		}
	}

	private void drawFrame(Graphics2D g2) {
		BasicStroke wideStroke = new BasicStroke(1.2f);
		g2.setStroke(wideStroke);
		g2.draw(new Line2D.Double(0.0, 0.0, 500, 0.0));
		g2.draw(new Line2D.Double(500, 0.0, 500, 500));
		g2.draw(new Line2D.Double(500, 500, 0.0, 500));
		g2.draw(new Line2D.Double(0.0, 500, 0.0, 0.0));
	}

	/**
	 * ACOのパラメータの表示
	 * @param g2
	 */
	private void dispParameters(Graphics2D g2) {
		g2.drawString("都市数=" + Result.numCities ,520,50);
		g2.drawString("α=" + Result.alpha ,520,80);
		g2.drawString("β=" + Result.beta ,520,110);
		g2.drawString("q=" + Result.q ,520,140);
		g2.drawString("numAnts=" + Result.numAnts ,520,170);
		g2.drawString("numItr=" + Result.numItr ,520,200);
		g2.drawString("evporateRatio=" + Result.evaporateRatio ,520,230);
		g2.drawString("itrOfThisSolution=" + (int)Result.findAntIndexOfBestTour()/Result.numAnts,520,260);
		g2.drawString("idOfBestAnt=" + (int)Result.findAntIndexOfBestTour()%Result.numAnts,520,290);
		g2.drawString("総距離=" + String.valueOf(
				String.valueOf(
						Result.length.get(
								Result.findAntIndexOfBestTour()
						)
				)
		),520,320);
	}

	/**
	 * 最良の巡回路を描画
	 * @param g2
	 */
	private void drawTour(Graphics2D g2){
		Vertex sp;
		Vertex ep;
		ArrayList<Vertex> trail;

		int indexBest = Result.findAntIndexOfBestTour();

		trail = Result.trailList.get(indexBest);

		for(int i=0;i<trail.size();i++){
			if(i==trail.size()-1){
				sp = trail.get(i);
				ep = trail.get(0);
			}else{
				sp = trail.get(i);
				ep = trail.get(i+1);
			}

			double x0,y0,x1,y1;
			x0 = sp.getX()*500;
			y0 = sp.getY()*500;
			x1 = ep.getX()*500;
			y1 = ep.getY()*500;

			BasicStroke wideStroke = new BasicStroke(1.0f);
			g2.setStroke(wideStroke);
			g2.draw(new Line2D.Double(x0, y0, x1, y1));
		}
	}
}