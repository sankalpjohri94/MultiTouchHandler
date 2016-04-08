package placestest.sankalp.example.com.multitouchhandler.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import placestest.sankalp.example.com.multitouchhandler.Classes.Circle;

/**
 * Created by Scarecrow on 4/7/2016.
 */
public class TrackPointer extends View {

    //System Variables
    Display display;
    Context mContext;

    //Layout Variables
    RelativeLayout layout;
    Bitmap bg;
    Paint paint;

    //Data Variables
    ArrayList<Circle> touchPoints;
    String[] color = {"#9e1c1c", "#461346", "#175d5a", "#104240", "#426edd"};

    public TrackPointer(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TrackPointer(Context context, AttributeSet attrs, Context mContext) {
        super(context, attrs);
        this.mContext = mContext;
        init();
    }

    public TrackPointer(Context context, AttributeSet attrs, int defStyleAttr, Context mContext) {
        super(context, attrs, defStyleAttr);
        this.mContext = mContext;
        init();
    }

    void init(){
        touchPoints = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Circle temp = new Circle();
            touchPoints.add(temp);
        }
        paint = new Paint();
        paint.setColor(Color.parseColor(color[0]));
        display = ((Activity)mContext).getWindowManager().getDefaultDisplay();
        bg = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        //canvas = new Canvas(bg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    void resetCanvas(){
        bg = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        //canvas = new Canvas(bg);
        layout.setBackgroundDrawable(new BitmapDrawable(bg));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        resetCanvas();
        for (int i = 0; i < touchPoints.size(); i++){
            if (touchPoints.get(i).getStatus()) {
                int[] currentPos = touchPoints.get(i).getCurrentLocation();
                canvas.drawCircle(currentPos[0], currentPos[1], 30, paint);
                paint.setColor(Color.parseColor(color[i % 5]));
                paint.setTextSize(25);
                canvas.drawText(Integer.toString((int) touchPoints.get(i).getDeviation()), currentPos[0] - 50, currentPos[1] - 90, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int maskedAction = event.getActionMasked();
        int[] tempPos = {0, 0};
        int ptrNum = 0;
        if (action == MotionEvent.ACTION_DOWN || maskedAction == MotionEvent.ACTION_POINTER_DOWN){
            if (action == MotionEvent.ACTION_DOWN){
                ptrNum = event.getActionIndex();
                touchPoints.get(ptrNum).setInitLocation((int)event.getX(), (int)event.getY());
            }else{
                ptrNum = event.getActionIndex();
                touchPoints.get(ptrNum).setInitLocation((int)event.getX(ptrNum), (int)event.getY(ptrNum));
            }
        }else if(action == MotionEvent.ACTION_MOVE){

        }else if(action == MotionEvent.ACTION_UP || maskedAction == MotionEvent.ACTION_POINTER_UP){
            if (action == MotionEvent.ACTION_UP){

            }else{

            }
        }
        return true;
    }
}
