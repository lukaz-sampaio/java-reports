package lab.basics.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lucas
 */
public class ServletUtil {

    private static final Logger LOG = Logger.getLogger(ServletUtil.class.getName());

    private ServletUtil() {}

    public static void render(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!response.isCommitted()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                dispatcher.forward(request, response);
            }
        } catch (ServletException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
