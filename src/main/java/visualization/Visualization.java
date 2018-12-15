package visualization;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.keyboard.camera.CameraKeyController;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import equation.Equation;
import equation.EquationParser;

public class Demo
{
    protected Chart chart;
    private final String CANVAS_TYPE = "awt";
	
    public static void main(String[] args) throws Exception
    {
        DemoLauncher.openDemo(new Demo());
    }
    
    public void init() 
    {
    	EquationParser parser = new EquationParser();
    	Equation eq = parser.parse("-20*exp(-0.2*(0.5*(par0^2+par1^2))^0.5)-exp(0.5*(cos(2*pi*par0)+cos(2*pi*par1)))+e+20");
    	
        Mapper mapper = new Mapper() 
        {
            public double f(double x, double y) 
            {
                return eq.compute(x, y);
            }
        };

        Range range = new Range(-3, 3);
        int steps = 100;

        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        chart = new Chart(Quality.Nicest, CANVAS_TYPE);
        chart.getScene().getGraph().add(surface);
        chart.addController(new CameraKeyController());
    }

	public String getPitch()
	{
		return "";
	}
	
	public boolean isInitialized()
	{
	    return chart != null;
	}
	
	public Chart getChart()
	{
        return chart;
    }

    public boolean hasOwnChartControllers()
	{
	    return false;
	}
}
