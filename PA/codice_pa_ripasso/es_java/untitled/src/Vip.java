public class Vip extends  Spettatore{
    @Override
    public void accept(Visitor visitor) {
        visitor.printCredenziali(this);
    }
}
