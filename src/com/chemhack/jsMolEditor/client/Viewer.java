package com.chemhack.jsMolEditor.client;

import com.google.gwt.core.client.EntryPoint;
import com.chemhack.jsMolEditor.client.controller.ViewerController;

import java.util.HashMap;

public class Viewer implements EntryPoint {
    static HashMap<String, ViewerController> controllers =new HashMap<String,ViewerController>();
    static Editor editor;

    public void onModuleLoad() {
        injectJSMethods();
    }

    public static void initEditor(String divID, int width, int height) {
//        if(controllers.get(divID)==null){
            controllers.put(divID,new ViewerController(divID, width, height));
//        }else{
//            controllers.get(divID)=new EditorController(divID, width, height);
//        }
    }


    public static void importMolFile(String divID,String fileContent) {
        controllers.get(divID).importMolFile(fileContent);
    }

    private static native void injectJSMethods()/*-{
    $wnd.__importMolFile =function(divID,fileContent){
    @com.chemhack.jsMolEditor.client.Viewer::importMolFile(Ljava/lang/String;Ljava/lang/String;)(divID,fileContent);
    };

    $wnd.__initEditor =function(divID, width, height){
    @com.chemhack.jsMolEditor.client.Viewer::initEditor(Ljava/lang/String;II)(divID, width, height);
    };

    }-*/;
}
