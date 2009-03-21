package com.chemhack.jsMolEditor.client;

import com.google.gwt.core.client.EntryPoint;
import com.chemhack.jsMolEditor.client.controller.EditorController;

import java.util.HashMap;

public class Editor implements EntryPoint {
    static HashMap<String,EditorController> controllers =new HashMap<String,EditorController>();
    static Editor editor;

    public void onModuleLoad() {
        injectJSMethods();
    }

    public static void initEditor(String divID, int width, int height) {
//        if(controllers.get(divID)==null){
            controllers.put(divID,new EditorController(divID, width, height));
//        }else{
//            controllers.get(divID)=new EditorController(divID, width, height);
//        }
    }


    public static void importMolFile(String divID,String fileContent) {
        controllers.get(divID).importMolFile(fileContent);
    }

    public static String exportMolFile(String divID) {
        return controllers.get(divID).exportMolFile();
    }

    private static native void injectJSMethods()/*-{
    $wnd.__importMolFile =function(divID,fileContent){
    @com.chemhack.jsMolEditor.client.Editor::importMolFile(Ljava/lang/String;Ljava/lang/String;)(divID,fileContent);
    };
    
    $wnd.__initEditor =function(divID, width, height){
    @com.chemhack.jsMolEditor.client.Editor::initEditor(Ljava/lang/String;II)(divID, width, height);
    };

    $wnd.__exportMolFile =function(divID){
    return @com.chemhack.jsMolEditor.client.Editor::exportMolFile(Ljava/lang/String;)(divID);
    };

    }-*/;
}
