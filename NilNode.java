package RBTree;
class NilNode extends Node{
    static final boolean BLACK = true;

    public NilNode() {
        super(0);
        this.color = BLACK;
    }
}
