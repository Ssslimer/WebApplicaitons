package visualization;

import java.awt.Rectangle;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.global.Settings;

public class DemoLauncher
{	
	protected static String DEFAULT_CANVAS_TYPE = "awt";
	protected static Rectangle DEFAULT_WINDOW = new Rectangle(0,0,600,600);
	
    /** Opens a demo with mouse/key/thread controllers for viewpoint change. */
	public static void openDemo(Demo demo) throws Exception
	{	
		Settings.getInstance().setHardwareAccelerated(true);
		demo.init();
		Chart chart = demo.getChart();
		
		ChartLauncher.instructions();
		ChartLauncher.openChart(chart, DEFAULT_WINDOW, "PAI O3");
	}
}