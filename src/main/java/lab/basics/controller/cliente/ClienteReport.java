package lab.basics.controller.cliente;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab.basics.connection.DatabaseConnection;
import lab.basics.reports.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author lucas
 */
public class ClienteReport extends GeradorRelatorio {

    private static final Logger LOG = Logger.getLogger(ClienteReport.class.getName());

    public ByteArrayOutputStream reportCliente() {
        Connection conn = DatabaseConnection.getInstance();
        try (PreparedStatement ps = conn.prepareStatement("SELECT idpessoa, nome, email FROM pgd.tb_pessoas")) {
            try (ResultSet rs = ps.executeQuery()) {
                return fromResultSet(rs);
            } catch (JRException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
