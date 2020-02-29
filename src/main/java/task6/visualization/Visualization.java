package task6.visualization;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.keyboard.camera.CameraKeyController;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import task6.optimization.OptimizationResult;

public class Visualization
{
    private Chart chart;
    private static final String CANVAS_TYPE = "awt";
    
    private final OptimizationResult result;
    
    public Visualization(OptimizationResult result)
    {
    	this.result = result;
    }

    public void init() 
    {
        Mapper mapper = new Mapper() 
        {
            public double f(double x, double y) 
            {
                return result.getEquation().compute(x, y);
            }
        };

        Range range = new Range(result.getRanges()[0][0], result.getRanges()[0][1]);
        int steps = 100;

        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, 0.3f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        
        Coord3d[] coords = buildPoints(result.getPopulations());
        
        Scatter scatter = new Scatter(coords, Color.RED);
        scatter.setWidth(3f);
        chart = new Chart(Quality.Nicest, CANVAS_TYPE);
        chart.getScene().getGraph().add(surface);
        chart.getScene().getGraph().add(scatter);
        chart.addController(new CameraKeyController());
    }

    private Coord3d[] buildPoints(double[] data)
    {
        Coord3d[] coords = new Coord3d[data.length/2];
        double min = Double.MAX_VALUE;
        
        for(int i = 0; i < coords.length; i++)
        {
            double x = data[i*2];
            double y = data[i*2+1];
            
            double z = result.getEquation().compute(x, y);
            if(z < min) min = z;
            
            coords[i] = new Coord3d(x, y, z);
        }
        
        return coords;
    }
    
	public Chart getChart()
	{
        return chart;
    }
}
