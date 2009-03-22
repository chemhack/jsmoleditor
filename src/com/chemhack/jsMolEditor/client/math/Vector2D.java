package com.chemhack.jsMolEditor.client.math;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D() {

    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public final double dot(Vector2D v1) {
        return (this.x * v1.x + this.y * v1.y);
    }


    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector
     */
    public final double length() {
        return (double) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Returns the squared length of this vector.
     *
     * @return the squared length of this vector
     */
    public final double lengthSquared() {
        return (this.x * this.x + this.y * this.y);
    }

    /**
     * Sets the value of this vector to the normalization of vector v1.
     *
     * @param v1 the un-normalized vector
     */
    public final void normalize(Vector2D v1) {
        double norm;

        norm = (double) (1.0 / Math.sqrt(v1.x * v1.x + v1.y * v1.y));
        this.x = v1.x * norm;
        this.y = v1.y * norm;
    }

    /**
     * Normalizes this vector in place.
     */
    public final void normalize() {
        double norm;

        norm = (double)
                (1.0 / Math.sqrt(this.x * this.x + this.y * this.y));
        this.x *= norm;
        this.y *= norm;
    }


    /**
     * Returns the angle in radians between this vector and the vector
     * parameter; the return value is constrained to the range [0,PI].
     *
     * @param v1 the other vector
     * @return the angle in radians in the range [0,PI]
     */
    public final double angle(Vector2D v1) {
        double vDot = this.dot(v1) / (this.length() * v1.length());
        if (vDot < -1.0) vDot = -1.0;
        if (vDot > 1.0) vDot = 1.0;
        return Math.acos(vDot);

    }

    /**
     * Return a vector with angle specified rotated from this vector and normalized.
     *
     * @param angle the angle in radians in the range [0,PI]
     * @return Vector2D with angle specified turned and normalized
     */

    public final Vector2D[] rotate(double angle) {
        Vector2D thisClone = new Vector2D(this.x, this.y);
        thisClone.normalize();
        double a=thisClone.x;
        double b=thisClone.y;
        double c=Math.cos(angle);
        double d=Math.sin(angle);
        double e=Math.cos(-angle);
        double f=Math.sin(-angle);

        double x1=b*d+a*c;
        double y1=b*c-a*d;
        double x2=b*f+a*e;
        double y2=b*e-a*f;

//        double x1=(-e*Math.pow(b,2)-Math.pow(Math.pow(a,2)+Math.pow(b*e,2)-Math.pow(e,2),0.5)*b+e)/a;
//        double y1=b*e+Math.pow(Math.pow(a,2)+Math.pow(b*e,2)-Math.pow(e,2),0.5);
//        double x2=(-e*Math.pow(b,2)+Math.pow(Math.pow(a,2)+Math.pow(b*e,2)-Math.pow(e,2),0.5)*b+e)/a;
//        double y2=b*e-Math.pow(Math.pow(a,2)+Math.pow(b*e,2)-Math.pow(e,2),0.5);

        return new Vector2D[]{new Vector2D(x1,y1),new Vector2D(x2,y2)};
    }

    public final void setLength(double length){
        normalize();
        x*=length;
        y*=length;
    }

    public String toString(){
        return "{"+x+","+y+"}";
    }


}
