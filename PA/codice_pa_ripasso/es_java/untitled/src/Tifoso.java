public class Tifoso extends  Spettatore{

    @Override
    public void accept(Visitor visitor) {
        visitor.printCredenziali(this);
    }
}
