package lab.basics.reports;

/**
 *
 * @author lucas
 */
public enum FileType {
    /**
     * Abrir com Evince (Linux), Adobe (Linux, Ruindows)
     */
    pdf(0, "application/pdf"),
    /**
     * Abrir com Browser (Firefox, Carniça Explorer)
     */
    html(1, "text/html"),
    /**
     * Abrir com Word (Ruindows)
     */
    docx(2, "application/msword"),
    /**
     * Abrir com Writer (Linux)
     */
    odt(3, "application/vnd.oasis.opendocument.text;application/x-vnd.oasis.opendocument.text"),
    /**
     * Abrir com Excel (Ruindows)
     */
    xlsx(4, "application/excel;application/vnd.ms-excel;application/x-excel;application/x-msexcel"),
    /**
     * Abrir com Calc (Linux)
     */
    ods(5, "application/vnd.oasis.opendocument.spreadsheet;application/x-vnd.oasis.opendocument.spreadsheet"),
    /**
     * Abrir com Editor de Texto
     */
    txt(6, "text/plain"),
    /**
     * Abrir com Editor de Texto ou Excel ou Calc
     */
    csv(7, "text/csv");

    private final int id;
    private final String mimeType;

    FileType(int id, String mimeType) {
        this.id = id;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getId() {
        return id;
    }

    public static FileType fromId(int id) {
        for (FileType value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        return pdf;
    }
}
