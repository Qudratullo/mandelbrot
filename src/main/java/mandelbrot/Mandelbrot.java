package mandelbrot;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.jcache.JCacheCacheManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by QUDRAT on 7/4/2018.
 */

public class Mandelbrot {
    private int zoom;
    private int x;
    private int y;
    private String sizeName;
    private static final HashMap<String, Integer> sizes;

    static {
        HashMap<String, Integer> temp = new HashMap<>();
        temp.put("small", 256);
        temp.put("medium", 512);
        temp.put("big", 1024);
        temp.put("large", 2048);
        sizes = temp;
    }

    public Mandelbrot(int zoom, int x, int y, String sizeName) {
        this.zoom = zoom;
        this.x = x;
        this.y = y;
        this.sizeName = sizeName;
    }

    public byte[] draw() throws IOException {
        int size = sizes.get(this.sizeName);
        Paint paint = new Color(0x000000);
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = image.createGraphics();
        graphic.setPaint(paint);
        graphic.fillRect(0, 0, size, size);
        for(int d_x = 0; d_x < size; d_x ++) {
            for(int d_y = 0; d_y < size; d_y ++) {
                double m_x = (Coordinates.rightX - Coordinates.leftX) * (d_x + this.x) / (size * this.zoom) + Coordinates.leftX / this.zoom;
                double m_y = (Coordinates.topY - Coordinates.bottomY) * (d_y + this.y) / (size * this.zoom) + Coordinates.bottomY / this.zoom;
                double tm_x = m_x, tm_y = m_y;
                double t_x, t_y;
                int lastIteration = -1;
                double f = Math.sqrt(0.001+2.0 * Math.min((Coordinates.rightX - Coordinates.leftX) / this.zoom, (Coordinates.topY - Coordinates.bottomY) / this.zoom));
                int countMaxIterations = (int)(210.0/f);
                for(int it = 0; it <= countMaxIterations; it ++) {
                    lastIteration = it;
                    if(m_x * m_x + m_y * m_y > 4)
                        break;
                    t_x = m_x * m_x - m_y * m_y + tm_x;
                    t_y = 2 * m_x * m_y + tm_y;
                    m_x = t_x;
                    m_y = t_y;
                }
                if(lastIteration < countMaxIterations) {
                    graphic.setColor(new Color((int)(255.0 * lastIteration / countMaxIterations), (int)(255.0 * lastIteration / countMaxIterations), (int)(255.0 * lastIteration / countMaxIterations)));
                    graphic.drawRect(d_x, d_y, 1, 1);
                }
            }
        }

        graphic.setColor(Color.RED);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
}
