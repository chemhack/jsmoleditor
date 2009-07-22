package com.chemhack.jsMolEditor.client.renderer;

import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.Bond;
import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Rectangle2D;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Point2D;
import com.chemhack.jsMolEditor.client.widget.ExtendedCanvas;
import com.chemhack.jsMolEditor.client.controller.EditorController;


public class CanvasRenderer {
    EditorController controller;
    RendererModel rendererModel;
    ExtendedCanvas canvas;
    CordTransformer transformer;

    public CanvasRenderer(ExtendedCanvas canvas) {
        this(null,canvas);
    }
    public CanvasRenderer(EditorController controller, ExtendedCanvas canvas) {
        this.controller = controller;
        this.canvas = canvas;
        rendererModel = new RendererModel();
        transformer=new CordTransformer(54,0,0,-54,-12660,8196,3); //Hardcoded default transform.   `
    }


    public void paintNewMolecule(Molecule molecule) {
        createScaleTransform(createRectangle2D(molecule), new Rectangle2D(0, 0, canvas.getWidth(), canvas.getHeight()));
        paintMolecule(molecule);
    }

    public void paintMolecule(Molecule molecule) {
        canvas.setBackgroundColor(rendererModel.getBackColor());
        canvas.clear();
        canvas.save();
        this.paintBonds(molecule);
        this.paintAtoms(molecule);
        canvas.restore();
    }


    private void paintBonds(Molecule molecule) {
        canvas.save();
        canvas.setLineWidth(rendererModel.getBondWidth());
        for (int i = 0; i < molecule.countBonds(); i++) {
            Bond currentBond = molecule.getBond(i);
            if(rendererModel.getHighlightedBonds().contains(currentBond)){
              canvas.setStrokeStyle("red");
            }else{
                canvas.setStrokeStyle(rendererModel.getForeColor());                
            }

            Point2D pointSource = transformer.transform(new Point2D(currentBond.getSource().getX(), currentBond.getSource().getY()), null);
            Point2D pointTarget = transformer.transform(new Point2D(currentBond.getTarget().getX(), currentBond.getTarget().getY()), null);
            double x1 = pointSource.getX();
            double y1 = pointSource.getY();
            double x2 = pointTarget.getX();
            double y2 = pointTarget.getY();


            switch (currentBond.getType()) {
                case 1:
                    canvas.beginPath();
                    canvas.moveTo(x1, y1);
                    canvas.lineTo(x2, y2);
                    canvas.stroke();
                    break;
                case 2:
                    double[] coords2 = distanceCalculator(new double[]{x1, y1, x2, y2}, rendererModel.getBondDistance());

                    canvas.beginPath();
                    canvas.moveTo(coords2[0], coords2[1]);
                    canvas.lineTo(coords2[6], coords2[7]);
                    canvas.stroke();

                    canvas.beginPath();
                    canvas.moveTo(coords2[2], coords2[3]);
                    canvas.lineTo(coords2[4], coords2[5]);
                    canvas.stroke();

                    break;
                case 3:
                    double[] coords3 = distanceCalculator(new double[]{x1, y1, x2, y2}, 4);

                    canvas.beginPath();
                    canvas.moveTo(x1, y1);
                    canvas.lineTo(x2, y2);
                    canvas.stroke();

                    canvas.beginPath();
                    canvas.moveTo(coords3[0], coords3[1]);
                    canvas.lineTo(coords3[6], coords3[7]);
                    canvas.stroke();

                    canvas.beginPath();
                    canvas.moveTo(coords3[2], coords3[3]);
                    canvas.lineTo(coords3[4], coords3[5]);
                    canvas.stroke();

                    break;
            }
        }

        canvas.restore();

    }

    private void paintAtoms(Molecule molecule) {
        for (int i = 0; i < molecule.countAtoms(); i++) {

            Atom currentAtom = molecule.getAtom(i);
            paintAtom(currentAtom);
        }
    }

    private void paintAtom(Atom currentAtom) {
        Point2D point = transformer.transform(new Point2D(currentAtom.getX(), currentAtom.getY()), null);
        double x = point.getX();
        double y = point.getY();

        boolean drawAtomLabel =false;
        String symbolText = currentAtom.getSymbol();
        if(!symbolText.equals("C")){
           drawAtomLabel=true; 
        }else if(currentAtom.countNeighbors()==0){
           drawAtomLabel=true;
        }else if(currentAtom.getCharge()!=0){
           drawAtomLabel=true;
        }


        String fontColor = rendererModel.getForeColor();
        if (symbolText.equals("N")) {
            fontColor = "blue";
        } else if (symbolText.equals("O")) {
            fontColor = "red";
        }                                                  

        boolean highlighted = rendererModel.getHighlightedAtoms().contains(currentAtom);
        if(drawAtomLabel|| highlighted){
            canvas.save();
            if (highlighted) {
                canvas.setFillStyle(rendererModel.getSelectedAtomBackColor());
            } else {
                canvas.setFillStyle(rendererModel.getBackColor());
            }

            double symbolFontSize=12;
            double chargeFontSize=symbolFontSize*0.6;

            double radius=symbolFontSize/2*1.414;

//            currentAtom.setCharge(1);
            String chargeText="";
            if(currentAtom.getCharge()!=0){
                if(currentAtom.getCharge()==1)chargeText="+";
                else if(currentAtom.getCharge()==-1)chargeText="-";
                else if(currentAtom.getCharge()>0)chargeText=currentAtom.getCharge()+"+";
                else if(currentAtom.getCharge()<0)chargeText=Math.abs(currentAtom.getCharge())+"-";
                radius+=5;
            }

            double textWidth = canvas.measure(symbolText, symbolFontSize);
            
            canvas.beginPath();
            canvas.rect(x - textWidth / 2,y - symbolFontSize / 2,textWidth,symbolFontSize);
//          canvas.arc(x, y, radius, 0, Math.PI * 2, true);
            canvas.fill();


            canvas.setStrokeStyle(rendererModel.getForeColor());
            canvas.setStrokeStyle(fontColor);


            canvas.drawText(symbolText, x - textWidth / 2, y - symbolFontSize / 2, symbolFontSize);

            if(!chargeText.equals("")){
                canvas.drawText(chargeText, x + textWidth / 2, y - symbolFontSize, chargeFontSize);
            }
            
//          canvas.drawTextCenter(currentAtom.getSymbol(), x, y, fontSize);
            

            canvas.restore();
        }
    }

    public void createScaleTransform(final Rectangle2D contextBounds, final Rectangle2D rendererBounds) {
        double factor = rendererModel.getZoomFactor() * (1.0 - rendererModel.getMargin() * 2.0);
        double scaleX = factor * rendererBounds.getWidth() / contextBounds.getWidth();
        double scaleY = factor * rendererBounds.getHeight() / contextBounds.getHeight();
        double scale;
        transformer = new CordTransformer();
        if (scaleX > scaleY) {
//            System.out.println("Scaled by Y: " + scaleY);
//             FIXME: should be -X: to put the origin in the lower left corner
            transformer.scale(scaleY, -scaleY);
            scale = scaleY;
        } else {
//            System.out.println("Scaled by X: " + scaleX);
//             FIXME: should be -X: to put the origin in the lower left corner
            transformer.scale(scaleX, -scaleX);
            scale = scaleX;
        }
        //translate
//        System.out.println("scale: " + scale);
        double dx = -contextBounds.getX() * scale + 0.5 * (rendererBounds.getWidth() - contextBounds.getWidth() * scale);
        double dy = -contextBounds.getY() * scale - 0.5 * (rendererBounds.getHeight() + contextBounds.getHeight() * scale);
//        System.out.println("dx/scaleX: " + dx / scaleX + " dy/scaleY:" + dy / scaleY);
        transformer.translate(dx / scale, dy / scale);

    }

    public Rectangle2D createRectangle2D(Molecule molecule) {
        if (molecule == null || molecule.countAtoms() == 0) {
            return null;
        }

        double xmin, xmax = molecule.getAtom(0).getX();
        xmin = xmax;
        double ymin, ymax = molecule.getAtom(0).getY();
        ymin = ymax;
        double y, x;
        for (int i = 1; i < molecule.countAtoms(); i++) {
            y = molecule.getAtom(i).getY();
            x = molecule.getAtom(i).getX();
            if (x < xmin) {
                xmin = x;
            } else if (x > xmax) {
                xmax = x;
            }
            if (y < ymin) {
                ymin = y;
            } else if (y > ymax) {
                ymax = y;
            }
        }
        double margin = 1; //1 is ~enough margin to make symbols + text appear on screen
        return new Rectangle2D(xmin - margin, ymin - margin, (xmax - xmin) + 2 * margin, (ymax - ymin) + 2 * margin);
    }

    public static double[] distanceCalculator(double[] coords, double dist) {
        double angle;
        if ((coords[2] - coords[0]) == 0) {
            angle = Math.PI / 2;
        } else {
            angle = Math.atan((coords[3] - coords[1]) / (coords[2] - coords[0]));
        }
        double begin1X = (Math.cos(angle + Math.PI / 2) * dist + coords[0]);
        double begin1Y = (Math.sin(angle + Math.PI / 2) * dist + coords[1]);
        double begin2X = (Math.cos(angle - Math.PI / 2) * dist + coords[0]);
        double begin2Y = (Math.sin(angle - Math.PI / 2) * dist + coords[1]);
        double end1X = (Math.cos(angle - Math.PI / 2) * dist + coords[2]);
        double end1Y = (Math.sin(angle - Math.PI / 2) * dist + coords[3]);
        double end2X = (Math.cos(angle + Math.PI / 2) * dist + coords[2]);
        double end2Y = (Math.sin(angle + Math.PI / 2) * dist + coords[3]);

        return new double[]{begin1X, begin1Y, begin2X, begin2Y, end1X, end1Y, end2X, end2Y};
    }

    public CordTransformer getTransformer() {
        return transformer;
    }

    public ExtendedCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(ExtendedCanvas canvas) {
        this.canvas = canvas;
    }

    public RendererModel getRendererModel() {
        return rendererModel;
    }
}
