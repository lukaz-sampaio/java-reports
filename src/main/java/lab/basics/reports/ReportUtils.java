package lab.basics.reports;

import java.util.HashMap;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author lucas
 */
public class ReportUtils {
    
    private ReportUtils(){}
    
    public static HashMap tipoArquivo(){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "PDF - Adobe Reader");
        map.put(1, "HTML");
        map.put(2, "Microsoft Office Word");
        map.put(3, "LibreOffice Writer");
        map.put(4, "Microsoft Office Excel");
        map.put(5, "LibreOffice Calc");
        map.put(6, "Texto");
        map.put(7, "CSV (Excel/Calc)");
        
        return map;
    }
    
    public static String contextPath(ServletContext context) {
        final String SLASH = "/";
        final String USER_DIR_WEBCONTENT = System.getProperty("user.dir") + "/WebContent/";
        final String realPath = context.getRealPath(SLASH);
        if (realPath != null) {
            return StringUtils.appendIfMissing(realPath, SLASH);
        }
        return USER_DIR_WEBCONTENT;
    }
}
