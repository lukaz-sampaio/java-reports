/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.basics.controller.cliente;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lab.basics.reports.FileType;
import lab.basics.reports.ReportUtils;
import lab.basics.utils.Formata;
import lab.basics.utils.ServletUtil;

/**
 *
 * @author lucas
 */
@WebServlet(name = "ServletCliente", urlPatterns = {"/cliente"})
public class ServletCliente extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ServletCliente.class.getName());

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        exportar(request, response);
    }

    private void exportar(HttpServletRequest request, HttpServletResponse response) {
        try {
            String acao = request.getParameter("botao");
            if ("export".equals(acao)) {
                String realPath = ReportUtils.contextPath(getServletContext());
                ClienteReport report = new ClienteReport();
                report.setFileName(realPath + "cliente/report/pessoas.jasper");
                report.setOutputFileName("generated_report");
                report.setFileType(FileType.fromId(Formata.getInteger(request.getParameter("tipoArquivo"))));
                ByteArrayOutputStream reportCliente = report.reportCliente();
                report.abrirArquivoBrowser(response, reportCliente);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        HashMap tipoArquivo = ReportUtils.tipoArquivo();
        request.setAttribute("tipoArquivo", tipoArquivo);

        ServletUtil.render("cliente/view.cliente.report.jsp", request, response);
    }
}
