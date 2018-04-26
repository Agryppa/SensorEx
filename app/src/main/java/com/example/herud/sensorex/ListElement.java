package com.example.herud.sensorex;



/**
 * Created by Herud on 2018-04-25.
 */

public class ListElement
{
    private double expectedX;
    private double resultX;
    private Integer pic;

    public Integer getPic() {
        return pic;
    }

    public ListElement(double expectedX, double resultX,Integer pic)
    {
        this.expectedX=expectedX;
        this.resultX=resultX;
        this.pic=pic;
    }

    public double getResultX() {
        return resultX;
    }

    public double getExpectedX() {
        return expectedX;

    }
}
