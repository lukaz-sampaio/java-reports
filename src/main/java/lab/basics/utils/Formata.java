package lab.basics.utils;

/**
 *
 * @author lucas
 */
public class Formata {

    public static Integer getInteger(String valor) {
        try {
            if (valor != null && !valor.isEmpty()) {
                return Integer.valueOf(valor.replaceAll("[\\D]", "").trim());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
