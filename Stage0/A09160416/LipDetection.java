import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import ij.plugin.Duplicator;

public class LipDetection implements PlugInFilter {
    ImagePlus imp;
    GaussianBlur gb = new GaussianBlur();
    RankFilters rf = new RankFilters();
    Duplicator dp = new Duplicator();

    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_ALL + NO_IMAGE_REQUIRED;
    }

    public double bottom(int x) {
        return -0.0013 * x * x + 1.2608 * x + 12.067;
    }

    public double top(int x) {
        return -0.0026 * x * x + 1.5713 * x + 14.8;
    }

    public ImagePlus getLips(ImagePlus imp) {
        ImagePlus innerImp = new Duplicator().run(imp);
        ImageProcessor innerIp = innerImp.getProcessor();
        ImageProcessor sourceIp = imp.getProcessor();

        int width = innerIp.getWidth(), height = innerIp.getHeight(), pixel, r, g, b;
        double rb;
        Color color;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                color = new Color(sourceIp.getPixel(col, row));
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                rb = (r + b) / 2.;
                if (b < g && g < r && rb >= bottom(g) && rb <= top(g))
                    innerIp.putPixel(col, row, 0); //BLACK
                else
                    innerIp.putPixel(col, row, 16777215); //WHITE
            }
        }

        rf.rank(innerIp, 4.0, rf.MIN);

        return innerImp;
    }

    public int[] getCentroid(ImageProcessor ip) {
        int width = ip.getWidth();
        int height = ip.getHeight();
        int R = 0;
        int sumX = 0;
        int sumY = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(ip.get(i, j) == 0) {
                    sumX += i;
                    sumY += j;
                    R++;
                }
            }
        }

        int[] centroid = {
                Math.round(sumX/R),
                Math.round(sumY/R)
        };

        return centroid;
    }

    public int[] getUpperLeft(ImageProcessor ip) {
        int width = ip.getWidth();
        int height = ip.getHeight();

        int upperX = width;
        int upperY = height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(ip.get(i,j) == 0) {
                    if(i < upperX)
                        upperX = i;
                    if(j < upperY)
                        upperY = j;
                }
            }
        }

        int[] upperLeft = { upperX, upperY };

        return upperLeft;
    }


    public void drawBoundingBox(ImagePlus layer, ImagePlus main) {
        ImageProcessor proc = layer.getProcessor();
        ImageProcessor mainProcessor = main.getProcessor();
        
        int[] centroid = getCentroid(proc);
        int[] upperLeft = getUpperLeft(proc);

        int midX = centroid[0] - upperLeft[0];
        int midY = centroid[1] - upperLeft[1];

        mainProcessor.set(centroid[0], centroid[1], 200);

        Roi mouthRoi = new Roi(centroid[0] - 2*midX, centroid[1] - 2*midY, 4*midX, 4*midY);
        mouthRoi.drawPixels(mainProcessor);

        main.show();
    }

    public void run(ImageProcessor ip) {
        ImagePlus mainImage = IJ.openImage();
        ImagePlus layerImage = dp.run(mainImage);


        gb.blurGaussian(layerImage.getProcessor(), 2.0);
        ImagePlus lips = getLips(layerImage);

        drawBoundingBox(lips, mainImage);
    }

}
