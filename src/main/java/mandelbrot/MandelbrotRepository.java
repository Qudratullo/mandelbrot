package mandelbrot;

import java.io.IOException;

/**
 * Created by QUDRAT on 7/10/2018.
 */
public interface MandelbrotRepository {
    byte[] draw(int zoom, int x, int y, String sizeName) throws IOException;
}
