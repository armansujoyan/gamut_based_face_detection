import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import ij.plugin.Duplicator;

public class ExtractLayers implements PlugInFilter {

    GaussianBlur gb = new GaussianBlur();
    RankFilters rf = new RankFilters();

    public double bottom0 (int x) {
        return 0.9848 * x - 6.7474;
    }

    public double top0 (int x) {
        return -0.0009 * x * x + 1.1917 * x - 4.0146;
    }

    public int setup (String args, ImagePlus im) {
        return DOES_RGB + NO_IMAGE_REQUIRED;
    }

    public void extract0 (ImagePlus imp) {
        ImagePlus innerImp = new Duplicator().run(imp);
        ImageProcessor innerIp = innerImp.getProcessor();
        ImageProcessor sourceIp = imp.getProcessor();
        int width = innerIp.getWidth();
        int height = innerIp.getHeight();
        int pixel;
        int r;
        int g;
        int b;
        double rb;
        Color color;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                color = new Color(sourceIp.getPixel(col, row));
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                rb = (r + b) / 2;
                if (b < g && g < r && rb >= bottom0(g) && rb <= top0(g))
                    innerIp.putPixel(col, row, 0);
                else
                    innerIp.putPixel(col, row, 16777215);
            }
        }
        rf.rank(innerIp, 4.0, rf.MEDIAN);

        innerImp.setTitle("Layer 0");
        innerImp.setProcessor(innerIp);
        innerImp.show();
    }

    public double bottom1(int x) {
        return -0.0009 * x * x + 1.1917 * x - 4.0146;
    }

    public double top1(int x) {
        return -0.0011 * x * x + 1.2262 * x + 4.0264;
    }

    public void extract1(ImagePlus imp) {
        ImagePlus innerImp = new Duplicator().run(imp);
        ImageProcessor innerIp = innerImp.getProcessor();
        ImageProcessor sourceIp = imp.getProcessor();
        int width = innerIp.getWidth(), height = innerIp.getHeight(), pixel, r, g, b;
        double rb;
        Color color;

        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                color = new Color(sourceIp.getPixel(col, row));
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                rb = (r + b) / 2.;
                if (b < g && g < r && rb >= bottom1(g) && rb <= top1(g))
                    innerIp.putPixel(col, row, 0); //BLACK
                else
                    innerIp.putPixel(col, row, 16777215); //WHITE
            }
        rf.rank(innerIp, 4.0, rf.MEDIAN);

        innerImp.setTitle("Layer 1");
        innerImp.setProcessor(innerIp);
        innerImp.show();
    }

    public double bottom2(int x) {
        return -0.0011 * x * x + 1.2262 * x + 4.0264;
    }

    public double top2(int x) {
        return -0.0013 * x * x + 1.2608 * x + 12.067;
    }

    public void extract2(ImagePlus imp) {
        ImagePlus innerImp = new Duplicator().run(imp);
        ImageProcessor innerIp = innerImp.getProcessor();
        ImageProcessor sourceIp = imp.getProcessor();
        int width = innerIp.getWidth(), height = innerIp.getHeight(), pixel, r, g, b;
        double rb;
        Color color;
        for (int row = 0; row < height; row++)
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
        rf.rank(innerIp, 4.0, rf.MEDIAN);

        innerImp.setTitle("Layer 2");
        innerImp.setProcessor(innerIp);
        innerImp.show();
    }

    public double bottom(int x) {
        return -0.0013 * x * x + 1.2608 * x + 12.067;
    }

    public double top(int x) {
        return -0.0026 * x * x + 1.5713 * x + 14.8;
    }

    public void extract3(ImagePlus imp) {
        ImagePlus innerImp = new Duplicator().run(imp);
        ImageProcessor innerIp = innerImp.getProcessor();
        ImageProcessor sourceIp = imp.getProcessor();
        int width = innerIp.getWidth(), height = innerIp.getHeight(), pixel, r, g, b;
        double rb;
        Color color;
        for (int row = 0; row < height; row++)
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
        rf.rank(innerIp, 4.0, rf.MEDIAN);

        innerImp.setTitle("Layer 3");
        innerImp.setProcessor(innerIp);
        innerImp.show();
    }

    public void run(ImageProcessor ip) {
        ImagePlus mainImage = IJ.openImage();
        ImageProcessor mainIp = mainImage.getProcessor();

        rf.rank(mainIp, 4.0, rf.MEDIAN);
        gb.blurGaussian(mainIp, 4.0);

        extract0(mainImage);
        extract1(mainImage);
        extract2(mainImage);
        extract3(mainImage);
    }

}
