package es_gpt;

public class ContoBancario {
    private double saldo;
    private String intestatario;

    public ContoBancario(String intestatario, double saldo) {
        this.saldo = saldo;
        this.intestatario = intestatario;
    }

    public void preleva(double importo) throws SaldoInsufficienteException {
        if (saldo < importo) {
            throw new SaldoInsufficienteException("Saldo insufficiente");
        }
        saldo -= importo;
    }

    public double getSaldo() {
        return saldo;
    }
    public String getIntestatario() {
        return intestatario;
    }
}
