package com.chemhack.jsMolEditor.client.widget;

import com.google.gwt.animation.client.Animation;

public class SplashAnimation extends Animation {
    ExtendedCanvas canvas;

    public SplashAnimation(ExtendedCanvas canvas){
        super();
        this.canvas=canvas;
    }

    
    @Override
    protected void onComplete() {
        super.onComplete();
        canvas.setGlobalAlpha(1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        canvas.drawText("jsMolEditor",0,0,24);
    }

    @Override
    protected void onUpdate(double progress) {
        canvas.setGlobalAlpha(1-progress);
    }


}
