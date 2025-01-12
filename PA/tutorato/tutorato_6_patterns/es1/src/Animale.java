public class Animale implements Visitable {

    @Override
    public <E> E accept(Visitor<E> v) {
        return v.visit(this);
    }

}