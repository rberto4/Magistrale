public class App {
    public void main(String[] args) {

        int giorni = 30;
        double centesimi = 100;
        int contatore = 1;
        calcolo(giorni, centesimi, contatore);

    }

    private void calcolo (int g, double c, int contatore){

        c = c*2;
        System.err.println(c/100 + " euro dopo " + (contatore) + " giorni");
        g--;
        contatore++;
        if (g > 0){
            calcolo(g, c, contatore);
        }
    }

}
