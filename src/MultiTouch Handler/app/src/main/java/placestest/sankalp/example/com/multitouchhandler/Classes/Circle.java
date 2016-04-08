package placestest.sankalp.example.com.multitouchhandler.Classes;

public class Circle {

    //Variables to store position.
    int initPosX;
    int initPosY;
    int curPosX;
    int curPosY;

    //Variables to store the colors of the circle.
    int fillColor;
    int textColor;

    //Variable to store the state of the circle.
    boolean enabled;

    public Circle() {
        initPosX = 0;
        initPosY = 0;
        curPosX = 0;
        curPosY = 0;
        enabled = false;
    }

    public void setInitLocation(int initPosX, int initPosY){
        this.initPosX = initPosX;
        this.curPosX = initPosX;
        this.initPosY = initPosY;
        this.curPosY = initPosY;
        this.enabled = true;
    }

    public void setColor(int fillColor, int textColor){
        this.fillColor = fillColor;
        this.textColor = textColor;
    }

    public int[] getColor(){
        int[] color = {0, 0};
        color[0] = this.fillColor;
        color[1] = this.textColor;
        return color;
    }

    public void updateLocation(int posX, int posY){
        this.curPosX = posX;
        this.curPosY = posY;
    }

    public int getDeviation(){
        int deviation = 0;
        deviation = this.initPosY - this.curPosY;
        return deviation;
    }

    public int[] getCurrentLocation(){
        int[] pos = {0, 0};
        pos[0] = this.curPosX;
        pos[1] = this.curPosY;
        return pos;
    }

    public void disableCircle(){
        this.enabled = false;
    }

    public boolean getStatus(){
        return this.enabled;
    }
}
