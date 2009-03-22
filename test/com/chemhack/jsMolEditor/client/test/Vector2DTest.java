/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007, 2008 Metamolecular, LLC
 * 
 * http://metamolecular.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.chemhack.jsMolEditor.client.test;

import com.chemhack.jsMolEditor.client.math.Vector2D;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author Duan Lian
 */
public class Vector2DTest extends GWTTestCase {
    public String getModuleName() {
        return "com.chemhack.jsMolEditor.Editor";
    }
    public void testTurn(){
        Vector2D vector1=new Vector2D(6,13);
        Vector2D[] vectors=vector1.rotate(Math.toRadians(60));
       assertEquals(vector1.angle(vectors[0]),Math.toRadians(60),0.0001);
        assertEquals(vector1.angle(vectors[1]),Math.toRadians(60),0.0001);

    }
}