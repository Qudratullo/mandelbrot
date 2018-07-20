package mandelbrot;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by QUDRAT on 7/10/2018.
 */
@Component
public class SimpleMandelbrotRepository implements MandelbrotRepository {

    @Override
    @Cacheable("images")
    public byte[] draw(int zoom, int x, int y, String sizeName) throws IOException {
        return new Mandelbrot(zoom, x, y, sizeName).draw();
    }

}
