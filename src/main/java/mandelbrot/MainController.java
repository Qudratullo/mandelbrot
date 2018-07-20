package mandelbrot;

/**
 * Created by QUDRAT on 7/2/2018.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller

public class MainController {

    @Autowired
    private MandelbrotRepository mandelbrotRepository;

    @RequestMapping("/")
    public void index(HttpServletResponse response, @RequestParam("zoom") int zoom, @RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("size") String size) throws IOException {
        zoom = Math.max(1, zoom);
        // байткод рисунка
        byte[] image = mandelbrotRepository.draw(zoom, x, y, size);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(image);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}