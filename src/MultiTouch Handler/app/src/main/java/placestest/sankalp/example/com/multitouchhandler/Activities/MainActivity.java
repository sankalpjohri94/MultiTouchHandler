package placestest.sankalp.example.com.multitouchhandler.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

import placestest.sankalp.example.com.multitouchhandler.Classes.Circle;
import placestest.sankalp.example.com.multitouchhandler.R;

public class MainActivity extends Activity {

    //UI Vaariables
    RelativeLayout layout;
    Paint paint;
    Bitmap bg;
    Canvas canvas;

    //System Variables
    Display display;
    Vibrator phoneVibrate;

    //Data Variables
    Random random;
    ArrayList<Circle> touchPoints;
    String[] color = {"#9e1c1c", "#461346", "#175d5a", "#104240", "#426edd"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.drawCircles);
        phoneVibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        init();
    }

    //Function to initialize variables.
    public void init(){
        random = new Random();
        touchPoints = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Circle temp = new Circle();
            touchPoints.add(temp);
        }
        paint = new Paint();
        paint.setColor(Color.parseColor(color[random.nextInt(5)]));
        display = getWindowManager().getDefaultDisplay();
        bg = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bg);
    }

    //Function to detect touch events.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        //Detect touch events of fingers touching the screen.
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN){
            //Make the phone vibrate for 200 ms.
            phoneVibrate.vibrate(200);
            if (action == MotionEvent.ACTION_DOWN){
                int[] curLoc = {0, 0};
                // Get the location of the pointer.
                curLoc[0] = (int) event.getX();
                curLoc[1] = (int) event.getY();
                int ptrIndex = event.getPointerId(event.getActionIndex());
                touchPoints.get(ptrIndex).setInitLocation(curLoc[0], curLoc[1]);
                //Select the colors for the circle and the text.
                int fillColor = random.nextInt(5);
                int textColor = random.nextInt(5);
                touchPoints.get(ptrIndex).setColor(fillColor, textColor);
            }else{
                int[] curLoc = {0, 0};
                int ptrIndex = event.getPointerId(event.getActionIndex());
                //Get the location of the pointer.
                curLoc[0] = (int) event.getX(ptrIndex);
                curLoc[1] = (int) event.getY(ptrIndex);
                touchPoints.get(ptrIndex).setInitLocation(curLoc[0], curLoc[1]);
                //Select the colors for the circle and the text.
                int fillColor = random.nextInt(5);
                int textColor = random.nextInt(5);
                touchPoints.get(ptrIndex).setColor(fillColor, textColor);
            }
            resetCanvas(layout, bg, canvas);
            drawCircles(canvas, touchPoints, event);
        }else if(action == MotionEvent.ACTION_MOVE){ //Detects finger movements on the screen.
            for (int i = 0; i < event.getPointerCount(); i++) {
                int[] curLoc = {0, 0};
                curLoc[0] = (int) event.getX(i);
                curLoc[1] = (int) event.getY(i);
                touchPoints.get(event.getPointerId(i)).updateLocation(curLoc[0], curLoc[1]);
            }
            resetCanvas(layout, bg, canvas);
            drawCircles(canvas, touchPoints, event);
        }else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP){ //Detects fingers leaving the screen.
            //Make the phone vibrate for 200ms.
            phoneVibrate.vibrate(200);
            int ptrIndex = event.getPointerId(event.getActionIndex());
            touchPoints.get(ptrIndex).disableCircle();
            if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) && event.getPointerCount() == 1) {
                init();
                resetCanvas(layout, bg, canvas);
            }else{
                resetCanvas(layout, bg, canvas);
                drawCircles(canvas, touchPoints, event);
            }
        }
        return true;
    }

    //Function to draw circle under fingers touching the screen and show their deviation on the Y-axis.
    public void drawCircles(Canvas canvas, ArrayList<Circle> touchPoints, MotionEvent event){
        //Create a new blank Bitmap.
        bg = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bg);
        int[] currentPos = {0, 0};
        for (int i = 0; i < event.getPointerCount(); i++) {
            currentPos = touchPoints.get(event.getPointerId(i)).getCurrentLocation();
            if(touchPoints.get(event.getPointerId(i)).getStatus()) {
                paint.setColor(Color.parseColor(color[touchPoints.get(event.getPointerId(i)).getColor()[0]]));
                canvas.drawCircle(currentPos[0], currentPos[1], 40, paint);
                paint.setColor(Color.parseColor(color[touchPoints.get(event.getPointerId(i)).getColor()[1]]));
                paint.setTextSize(25);
                canvas.drawText(Integer.toString(touchPoints.get(event.getPointerId(i)).getDeviation()), currentPos[0] - 15, currentPos[1] - 90, paint);
                layout.setBackgroundDrawable(new BitmapDrawable(bg));
            }
        }
    }

    //Delete everything from the screen
    public void resetCanvas(RelativeLayout layout, Bitmap bg, Canvas canvas){
        bg = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        layout.setBackgroundDrawable(new BitmapDrawable(bg));
    }
}
