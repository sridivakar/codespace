package durs.puzzle;

public class Cube {

    private Color top;
    private Color front;
    private Color bottom;
    private Color back;
    private Color right;
    private Color left;

    private int xRotationNumber;
    private int yRotationNumber;
    private int zRotationNumber;

    public Cube(Color top, Color front, Color bottom, Color back, Color right, Color left) {
        super();
        this.top = top;
        this.front = front;
        this.bottom = bottom;
        this.back = back;
        this.right = right;
        this.left = left;

        this.xRotationNumber = 1;
        this.yRotationNumber = 1;
        this.zRotationNumber = 1;
    }

    public Cube(Cube that) {
        super();
        this.top = that.top;
        this.front = that.front;
        this.bottom = that.bottom;
        this.back = that.back;
        this.right = that.right;
        this.left = that.left;

        this.xRotationNumber = that.xRotationNumber;
        this.yRotationNumber = that.yRotationNumber;
        this.zRotationNumber = that.zRotationNumber;
    }

    public boolean rotate() {
        if (this.xRotationNumber < 4) {
            rotateXaxis();
            return true;
        }

        if (this.yRotationNumber < 4) {
            this.xRotationNumber = 1;
            rotateYaxis();
            return true;
        }

        if (this.zRotationNumber < 4) {
            this.xRotationNumber = 1;
            this.yRotationNumber = 1;
            rotateZaxis();
            return true;
        }

        return false;
    }

    private boolean rotateXaxis() {
        if (this.xRotationNumber == 4) {
            return false;
        }
        this.xRotationNumber++;

        Color temp = front;

        this.front = this.bottom;
        this.bottom = this.back;
        this.back = this.top;
        this.top = temp;

        return true;
    }

    private boolean rotateYaxis() {
        if (this.yRotationNumber == 4) {
            return false;
        }
        this.yRotationNumber++;

        Color temp = front;

        this.front = this.right;
        this.right = this.back;
        this.back = this.left;
        this.left = temp;

        return true;
    }

    private boolean rotateZaxis() {
        if (this.zRotationNumber == 4) {
            return false;
        }
        this.zRotationNumber++;

        Color temp = top;

        this.top = this.left;
        this.left = this.bottom;
        this.bottom = this.right;
        this.right = temp;

        return true;
    }

    public Color getTop() {
        return top;
    }

    public Color getFront() {
        return front;
    }

    public Color getBottom() {
        return bottom;
    }

    public Color getBack() {
        return back;
    }

    public Color getRight() {
        return right;
    }

    public Color getLeft() {
        return left;
    }

    @Override
    public String toString() {
        return "Cube [top=" + top + ", front=" + front + ", bottom=" + bottom + ", back=" + back + ", right=" + right
                + ", left=" + left + "]";
    }

}
