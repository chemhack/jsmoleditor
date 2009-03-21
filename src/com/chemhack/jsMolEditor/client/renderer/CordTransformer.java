package com.chemhack.jsMolEditor.client.renderer;

import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Point2D;

public class CordTransformer {
    private static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_TRANSLATION = 1;
    public static final int TYPE_UNIFORM_SCALE = 2;
    public static final int TYPE_GENERAL_SCALE = 4;
    public static final int TYPE_MASK_SCALE = (TYPE_UNIFORM_SCALE |
            TYPE_GENERAL_SCALE);
    public static final int TYPE_FLIP = 64;
    public static final int TYPE_QUADRANT_ROTATION = 8;
    public static final int TYPE_GENERAL_ROTATION = 16;
    public static final int TYPE_MASK_ROTATION = (TYPE_QUADRANT_ROTATION |
            TYPE_GENERAL_ROTATION);
    public static final int TYPE_GENERAL_TRANSFORM = 32;
    static final int APPLY_IDENTITY = 0;
    static final int APPLY_TRANSLATE = 1;
    static final int APPLY_SCALE = 2;
    static final int APPLY_SHEAR = 4;
    private static final int HI_SHIFT = 3;
    private static final int HI_IDENTITY = APPLY_IDENTITY << HI_SHIFT;
    private static final int HI_TRANSLATE = APPLY_TRANSLATE << HI_SHIFT;
    private static final int HI_SCALE = APPLY_SCALE << HI_SHIFT;
    private static final int HI_SHEAR = APPLY_SHEAR << HI_SHIFT;
    double m00;
    double m10;
    double m01;
    double m11;
    double m02;
    double m12;
    transient int state;
    private transient int type;

    public CordTransformer() {
        m00 = m11 = 1.0;
    }

    public CordTransformer(double m00, double m10,
                           double m01, double m11,
                           double m02, double m12,
                           int state) {
        this.m00 = m00;
        this.m10 = m10;
        this.m01 = m01;
        this.m11 = m11;
        this.m02 = m02;
        this.m12 = m12;
        this.state = state;
        this.type = TYPE_UNKNOWN;
    }


    public void scale(double sx, double sy) {
        int state = this.state;
        switch (state) {
            default:
//	    stateError();
                /* NOTREACHED */
            case (APPLY_SHEAR | APPLY_SCALE | APPLY_TRANSLATE):
            case (APPLY_SHEAR | APPLY_SCALE):
                m00 *= sx;
                m11 *= sy;
                /* NOBREAK */
            case (APPLY_SHEAR | APPLY_TRANSLATE):
            case (APPLY_SHEAR):
                m01 *= sy;
                m10 *= sx;
                if (m01 == 0 && m10 == 0) {
                    this.state = state - APPLY_SHEAR;
                    // REMIND: Is it possible for m00 and m11 to be both 1.0?
                }
                this.type = TYPE_UNKNOWN;
                return;
            case (APPLY_SCALE | APPLY_TRANSLATE):
            case (APPLY_SCALE):
                m00 *= sx;
                m11 *= sy;
                if (m00 == 1.0 && m11 == 1.0) {
                    this.state = (state &= APPLY_TRANSLATE);
                    this.type = (state == APPLY_IDENTITY
                            ? TYPE_IDENTITY
                            : TYPE_TRANSLATION);
                } else {
                    this.type = TYPE_UNKNOWN;
                }
                return;
            case (APPLY_TRANSLATE):
            case (APPLY_IDENTITY):
                m00 = sx;
                m11 = sy;
                if (sx != 1.0 || sy != 1.0) {
                    this.state = state | APPLY_SCALE;
                    this.type = TYPE_UNKNOWN;
                }
                return;
        }
    }

    public void translate(double tx, double ty) {
        switch (state) {
            default:
//                stateError();
                /* NOTREACHED */
            case (APPLY_SHEAR | APPLY_SCALE | APPLY_TRANSLATE):
                m02 = tx * m00 + ty * m01 + m02;
                m12 = tx * m10 + ty * m11 + m12;
                if (m02 == 0.0 && m12 == 0.0) {
                    state = APPLY_SHEAR | APPLY_SCALE;
                    if (type != TYPE_UNKNOWN) {
                        type -= TYPE_TRANSLATION;
                    }
                }
                return;
            case (APPLY_SHEAR | APPLY_SCALE):
                m02 = tx * m00 + ty * m01;
                m12 = tx * m10 + ty * m11;
                if (m02 != 0.0 || m12 != 0.0) {
                    state = APPLY_SHEAR | APPLY_SCALE | APPLY_TRANSLATE;
                    type |= TYPE_TRANSLATION;
                }
                return;
            case (APPLY_SHEAR | APPLY_TRANSLATE):
                m02 = ty * m01 + m02;
                m12 = tx * m10 + m12;
                if (m02 == 0.0 && m12 == 0.0) {
                    state = APPLY_SHEAR;
                    if (type != TYPE_UNKNOWN) {
                        type -= TYPE_TRANSLATION;
                    }
                }
                return;
            case (APPLY_SHEAR):
                m02 = ty * m01;
                m12 = tx * m10;
                if (m02 != 0.0 || m12 != 0.0) {
                    state = APPLY_SHEAR | APPLY_TRANSLATE;
                    type |= TYPE_TRANSLATION;
                }
                return;
            case (APPLY_SCALE | APPLY_TRANSLATE):
                m02 = tx * m00 + m02;
                m12 = ty * m11 + m12;
                if (m02 == 0.0 && m12 == 0.0) {
                    state = APPLY_SCALE;
                    if (type != TYPE_UNKNOWN) {
                        type -= TYPE_TRANSLATION;
                    }
                }
                return;
            case (APPLY_SCALE):
                m02 = tx * m00;
                m12 = ty * m11;
                if (m02 != 0.0 || m12 != 0.0) {
                    state = APPLY_SCALE | APPLY_TRANSLATE;
                    type |= TYPE_TRANSLATION;
                }
                return;
            case (APPLY_TRANSLATE):
                m02 = tx + m02;
                m12 = ty + m12;
                if (m02 == 0.0 && m12 == 0.0) {
                    state = APPLY_IDENTITY;
                    type = TYPE_IDENTITY;
                }
                return;
            case (APPLY_IDENTITY):
                m02 = tx;
                m12 = ty;
                if (tx != 0.0 || ty != 0.0) {
                    state = APPLY_TRANSLATE;
                    type = TYPE_TRANSLATION;
                }
                return;
        }
    }

    public Point2D transform(Point2D ptSrc, Point2D ptDst) {
        if (ptDst == null) {
            ptDst = new Point2D();
        }
        // Copy source coords into local variables in case src == dst
        double x = ptSrc.getX();
        double y = ptSrc.getY();
        switch (state) {
            default:
//	    stateError();
                /* NOTREACHED */
            case (APPLY_SHEAR | APPLY_SCALE | APPLY_TRANSLATE):
                ptDst.setLocation(x * m00 + y * m01 + m02,
                        x * m10 + y * m11 + m12);
                return ptDst;
            case (APPLY_SHEAR | APPLY_SCALE):
                ptDst.setLocation(x * m00 + y * m01, x * m10 + y * m11);
                return ptDst;
            case (APPLY_SHEAR | APPLY_TRANSLATE):
                ptDst.setLocation(y * m01 + m02, x * m10 + m12);
                return ptDst;
            case (APPLY_SHEAR):
                ptDst.setLocation(y * m01, x * m10);
                return ptDst;
            case (APPLY_SCALE | APPLY_TRANSLATE):
                ptDst.setLocation(x * m00 + m02, y * m11 + m12);
                return ptDst;
            case (APPLY_SCALE):
                ptDst.setLocation(x * m00, y * m11);
                return ptDst;
            case (APPLY_TRANSLATE):
                ptDst.setLocation(x + m02, y + m12);
                return ptDst;
            case (APPLY_IDENTITY):
                ptDst.setLocation(x, y);
                return ptDst;
        }

        /* NOTREACHED */
    }

    public double getScaleX() {
        return m00;
    }

    public double getScaleY() {
        return m11;
    }

    public Point2D inverseTransform(Point2D ptSrc, Point2D ptDst)
            throws RuntimeException {
        if (ptDst == null) {
            ptDst = new Point2D();
        }
        // Copy source coords into local variables in case src == dst
        double x = ptSrc.getX();
        double y = ptSrc.getY();
        switch (state) {
            default:
                throw new RuntimeException("state error");
                /* NOTREACHED */
            case (APPLY_SHEAR | APPLY_SCALE | APPLY_TRANSLATE):
                x -= m02;
                y -= m12;
                /* NOBREAK */
            case (APPLY_SHEAR | APPLY_SCALE):
                double det = m00 * m11 - m01 * m10;
                if (Math.abs(det) <= Double.MIN_VALUE) {
                    throw new RuntimeException("Determinant is " +
                            det);
                }
                ptDst.setLocation((x * m11 - y * m01) / det,
                        (y * m00 - x * m10) / det);
                return ptDst;
            case (APPLY_SHEAR | APPLY_TRANSLATE):
                x -= m02;
                y -= m12;
                /* NOBREAK */
            case (APPLY_SHEAR):
                if (m01 == 0.0 || m10 == 0.0) {
                    throw new RuntimeException("Determinant is 0");
                }
                ptDst.setLocation(y / m10, x / m01);
                return ptDst;
            case (APPLY_SCALE | APPLY_TRANSLATE):
                x -= m02;
                y -= m12;
                /* NOBREAK */
            case (APPLY_SCALE):
                if (m00 == 0.0 || m11 == 0.0) {
                    throw new RuntimeException("Determinant is 0");
                }
                ptDst.setLocation(x / m00, y / m11);
                return ptDst;
            case (APPLY_TRANSLATE):
                ptDst.setLocation(x - m02, y - m12);
                return ptDst;
            case (APPLY_IDENTITY):
                ptDst.setLocation(x, y);
                return ptDst;
        }

        /* NOTREACHED */
    }

    public void dumpMatrix() {
        System.out.println(m00);
        System.out.println(m10);
        System.out.println(m01);
        System.out.println(m11);
        System.out.println(m02);
        System.out.println(m12);
        System.out.println(state);
    }
}
