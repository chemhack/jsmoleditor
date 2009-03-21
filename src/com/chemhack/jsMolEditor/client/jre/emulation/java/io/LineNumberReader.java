package com.chemhack.jsMolEditor.client.jre.emulation.java.io;


/**
 * A simple LineNumberReader to emulate java.io.LineNumberReader
 *
 * @author	Duan Lian
 */

public class LineNumberReader {

    /** The current line number */
    private int lineNumber = 0;

    /** String array of lines **/
    String[] lines;




    public LineNumberReader(String content) {
        this.lines=content.replaceAll("\r\n","\n").split("\n");
    }



    /**
     * Set the current line number.
     *
     * @param lineNumber  an int specifying the line number.
     * @see #getLineNumber
     */
    public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
    }

    /**
     * Get the current line number.
     *
     * @return The current line number.
     * @see #setLineNumber
     */
    public int getLineNumber() {
	return lineNumber;
    }


    /**
     * Read a line of text.  A line is considered to be terminated by any one
     * of a line feed ('\n'), a carriage return ('\r'), or a carriage return
     * followed immediately by a linefeed.
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public String readLine() throws IOException {
//	synchronized (lock) {
//	    String l = super.readLine(skipLF);
//            skipLF = false;
//	    if (l != null)
//		lineNumber++;
//	    return l;
//	}
        if(lineNumber<lines.length){
            return lines[lineNumber++];
        }
        else return null;
    }


    /**
     * ready()
     *
     * @return  true if not end of file
     *          false if end of file
     */
    
    public boolean ready(){
        return lineNumber < lines.length;
    }



}
