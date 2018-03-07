package lab.basics.model.cliente;

import lab.basics.model.pessoa.Pessoa;

/**
 *
 * @author lucas
 */
public class Cliente extends Pessoa{
    
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
