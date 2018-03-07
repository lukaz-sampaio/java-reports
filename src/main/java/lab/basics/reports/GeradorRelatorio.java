package lab.basics.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleCsvMetadataExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

/**
 *
 * Exemplos:
 * <ul>
 * <li>http://www.dsc.ufcg.edu.br/~jacques/cursos/daca/html/documentviews/relatorios.htm</li>
 * <li>http://blog.triadworks.com.br/jasperreports-gerando-relatorios-pdf-na-web</li>
 * <li>https://davidbuzatto.com.br/2010/10/09/jasperreports-trabalhando-com-relatorios-em-java-parte-1/</li>
 * </ul>
 * @author lucas
 */
public abstract class GeradorRelatorio {

    private String fileName;
    private String outputFileName;
    private HashMap<String, Object> parameters;
    private FileType fileType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
    
    /**
     * Recebe os dados da query pra mandar pro JRDataSource e escrever os dados no arquivo.
     * 
     * @param rs
     * @return ByteArrayOutputStream
     * @throws JRException 
     */
    public ByteArrayOutputStream fromResultSet(ResultSet rs) throws JRException {
        try {
            JRResultSetDataSource source = new JRResultSetDataSource(rs);
            return createOutputStream(source);
        } catch (JRException e) {
            throw e;
        }
    }
    
    /**
     * Pega os dados de saída pra poder escrever o arquivo com a extensão escolhida.
     * @param source
     * @return ByteArrayOutputStream
     * @throws net.sf.jasperreports.engine.JRException
     */
    protected ByteArrayOutputStream createOutputStream(JRDataSource source) throws JRException {
        JasperPrint print = JasperFillManager.fillReport(this.fileName,
                this.parameters, source);
        Exporter exporter;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStreamExporterOutput output
                = new SimpleOutputStreamExporterOutput(os);
        switch (this.fileType) {
            case pdf:
                exporter = new JRPdfExporter();
                break;
            case ods:
                exporter = new JROdsExporter();
                break;
            case html:
                exporter = new HtmlExporter();
                exporter.setExporterOutput(new SimpleHtmlExporterOutput(os));
                output = null;
                break;
            case docx:
                exporter = new JRDocxExporter();
                break;
            case odt:
                exporter = new JROdtExporter();
                break;
            case xlsx:
                exporter = new JRXlsxExporter();
                break;
            case txt:
                exporter = new JRTextExporter();
                SimpleTextReportConfiguration txtConfig = new SimpleTextReportConfiguration();
                txtConfig.setCharWidth(6.55F);
                txtConfig.setCharHeight(11.9F);
                exporter.setConfiguration(txtConfig);
                exporter.setExporterOutput(new SimpleWriterExporterOutput(os));
                output = null;
                break;
            case csv:
                exporter = new JRCsvExporter();
                SimpleCsvMetadataExporterConfiguration config = new SimpleCsvMetadataExporterConfiguration();
                config.setFieldDelimiter("\t");
                exporter.setConfiguration(config);
                exporter.setExporterOutput(new SimpleWriterExporterOutput(os));
                output = null;
                break;
            default:
                exporter = new JRPdfExporter();
                break;
        }

        if (output != null) {
            exporter.setExporterOutput(output);
        }
        ExporterInput exporterInput = new SimpleExporterInput(print);
        exporter.setExporterInput(exporterInput);
        exporter.exportReport();
        return os;
    }
    
    /**
     * Monta o arquivo com os dados recebidos.
     * @param response
     * @param stream
     * @throws IOException 
     */
    public void abrirArquivoBrowser(HttpServletResponse response, ByteArrayOutputStream stream) throws IOException {
        byte[] bytes = stream.toByteArray();
        response.setContentType(this.fileType.getMimeType());
        response.setContentLength(bytes.length);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + this.outputFileName + "."
                + this.fileType.name());
        try(ServletOutputStream out = response.getOutputStream()){
            out.write(bytes);
            out.flush();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
