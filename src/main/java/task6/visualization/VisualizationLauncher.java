package visualization;

import java.awt.Rectangle;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.global.Settings;

public class VisualizationLauncher
{	
	private static final Rectangle DEFAULT_WINDOW = new Rectangle(0, 0, 600, 600);
	private static final String WINDOW_NAME = "PAI O3";
	
	public static void show(Visualization visualization) throws Exception
	{	
		Settings.getInstance().setHardwareAccelerated(true);
		visualization.init();
		Chart chart = visualization.getChart();
		
		ChartLauncher.instructions();
		ChartLauncher.openChart(chart, DEFAULT_WINDOW, WINDOW_NAME);
	}
}