// @ExecutionModes({on_single_node="/main_menu/insert/links"})

/*

Summary
-------
Create a link that open a pdf in SumatraPDF, at a specific page.
Only tested for Windows.
The script open a dialog to ask for the pdf and the page number.
Then it can create a link to this page in the currently selected node,
or an entire node to handle this link.

Note
----
With FP 1.9.0 alpha, the link do not works well if SumatraPDF
is not already started.

How to customize
----------------

1- Default file chooser dialog location
By default, the first time the script is called during a Freeplane
session, the file chooser open in the user home directory. You can change
this behavior and set a custom default path. You have to replace the line
static String dir = ""
by something like
static String dir = /C:\Users\xxx\Downloads/

2- Change the command line options passed to SumatraPDF, or use a
different pdf viewer
Read comments after the line
String getLink(){

*/

// Author: lilive
// Licence: WTFPL2


import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.JFrame
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import java.awt.Insets
import javax.swing.SwingUtilities
import java.awt.event.KeyEvent
import java.awt.event.KeyAdapter
import javax.swing.InputMap
import javax.swing.JComponent
import javax.swing.AbstractAction
import java.awt.event.ActionEvent
import javax.swing.KeyStroke


pageTF = null    // JtextField for the page
pdfPathTF = null // JtextField for the pdf path
gui = null       // JFrame for the whole dialog
pdf = null       // java.io.File that points to the pdf
page = null      // String that hold the page number

// A class with a static fields, to memorize the path
// between script calls (workaround)
class Path {
    // Open the file chooser in this directory
    static String dir = ""
    // This is the last choosen pdf file
    static String file = ""
}

// Open a file chooser dialog to select the pdf
fileFilter = new FileNameExtensionFilter("PDF document", "pdf")
String askPdf(){
    JFileChooser jfc = new JFileChooser( Path.dir )
    jfc.setFileFilter( fileFilter )
    int returnValue = jfc.showOpenDialog()
    if( returnValue == JFileChooser.APPROVE_OPTION ) return jfc.getSelectedFile()?.getAbsolutePath()
    else return null
}

// Create the Gui, assign it to the gui var,
// also define pageTF and pdfPathTF
def createGui(){ 
    groovy.swing.SwingBuilder.build{
        gui = dialog(
            title: 'Link to PDF page',
            modal:true,
            owner: ui.frame,
            defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE
        ){
            panel(
                constraints: BorderLayout.PAGE_START,
                border: emptyBorder( [ 15, 15, 15, 15 ] )
            ){
                gbl = gridBagLayout()
                label(
                    text: "PDF document",
                    constraints: gbc( gridx:0, gridy:0, insets: new Insets( 0, 0, 15, 15 ) )
                )
                pdfPathTF = textField(
                    text: Path.file,
                    columns: 40,
                    constraints: gbc( gridx:1, gridy:0, fill:GridBagConstraints.HORIZONTAL, weightx:1, insets: new Insets( 0, 0, 15, 15 ) )
                )
                button(
                    text: "Choose",
                    constraints: gbc( gridx:2, gridy:0, insets: new Insets( 0, 0, 15, 0 ) ),
                    actionPerformed: {
                        e ->
                        def path = askPdf()
                        if( path ){
                            pdfPathTF.text = path
                            gui.pack()
                            gui.setLocationRelativeTo( ui.frame )
                        }
                    }
                )
                label(
                    text: "Page number",
                    constraints: gbc( gridx:0, gridy:1, anchor:GridBagConstraints.LINE_END, insets: new Insets( 0, 0, 15, 15 ) )
                )
                pageTF = textField(
                    text: "",
                    columns: 5,
                    constraints: gbc( gridx:1, gridy:1, anchor:GridBagConstraints.LINE_START, gridwidth:2, insets: new Insets( 0, 0, 15, 0 ) )
                )
                pageTF.maximumSize = pageTF.preferredSize
                pageTF.minimumSize = pageTF.preferredSize
            }
            hbox(
                constraints: BorderLayout.PAGE_END,
                border: emptyBorder( [ 0, 15, 15, 15 ] )
            ){
                hglue()
                button(
                    text: "Cancel",
                    actionPerformed: { gui.dispose() }
                )
                hstrut(15)
                okBtn = button(
                    text: "Create link to PDF page",
                    actionPerformed: { createLink() }
                )
                hstrut(15)
                button(
                    text: "Create node and link",
                    actionPerformed: { createNode() }
                )
            }
        }
    }
    SwingUtilities.getRootPane( gui ).setDefaultButton( okBtn )
    SwingUtilities.invokeLater( new Runnable() { 
        public void run() { pageTF.requestFocus() } 
    } )
}

// Make ENTER trigger the link creation
def addEnterKeyListener(){
    def tfEnterAdapter = new java.awt.event.KeyAdapter(){
        @Override public void keyTyped( KeyEvent e ){
            int key = e.getKeyCode()
            if( key == KeyEvent.VK_ENTER ) createLink()
        }
    }
    pdfPathTF.addKeyListener( tfEnterAdapter )
    pageTF.addKeyListener( tfEnterAdapter )
}

// Make ESC close the dialog
def addEscKeyListener(){
    String onEscPressID = "onEscPress"
    InputMap inputMap = gui.getRootPane().getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT )
    inputMap.put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), onEscPressID )
    gui.getRootPane().getActionMap().put(
        onEscPressID,
        new AbstractAction(){
            @Override public void actionPerformed( ActionEvent e ){
                gui.dispose()
            }
        }
    )
}

// Return true if we have got a valid pdf path and a valid page number.
// Set the pdf and the page fields, update the class Path.
boolean parseDatas(){
    
    pdf = pdfPathTF.text ? new File( pdfPathTF.text ) : null
    Path.file = ""
    if( pdf && pdf.exists() && fileFilter.accept( pdf ) ){
        Path.file = pdf.path
        Path.dir = pdf.parent
    }

    if( pageTF.text ==~ /\d+/ ) page = pageTF.text
    else page = null
    
    return ( pdf && page ) 
}

// Create the string for the link to create
String getLink(){
    
    // This is where you can customize the program you want to use
    // to open the pdf file, or the options that you want to pass
    // to the program when it starts.

    // Two strings controls the generation of the link that will
    // be added to the map:
    
    // Path to the executable that will display the pdf
    // (SumatraPDF, Evince, Adobe Reader, etc)
    def pdfViewerPath = /C:\Program Files\SumatraPDF\SumatraPDF.exe/
    
    // Arguments to add to the command
    def args = /-reuse-instance -page $page/
    
    // The above values are set to use SumatraPDF. You may use the
    // values below to use Acrobat Reader
    // def pdfViewerPath = /C:\Program Files (x86)\Adobe\Acrobat Reader DC\Reader\AcroRd32.exe/
    // def args = "/N /S /O /A \"page=$page\""

    def cmd = /execute:_"${pdfViewerPath}" ${args} "${pdf.path}"/
    return encode( cmd )
}

// Replace some characters with their percent-encoded value
String encode( String str ){
    // This is experimental, I do not know exactly which characters need to be replaced,
    // I'm just trying to mimic what I've seen when using the built-in menu entry
    // Insert > Link > OS Command 
    def check = ~/[\d\w-_\.:]/
    String output = ""
    for( c in str ){
        if( c =~ check ) output += c
        else output += "%" + Integer.toHexString( (int)c.charAt(0) ).toUpperCase()
    }
    return output
}

// Close the dialog and create a link to thze pdf page in the selected node
def createLink(){
    gui.dispose()
    if( ! parseDatas() ) return
    node.link.text = getLink()
}

// Close the dialog and create a node that link the pdf page
def createNode(){
    gui.dispose()
    if( ! parseDatas() ) return
    n = node.createChild( "${pdf.name} - Page ${page}" )
    n.link.text = getLink()
}

createGui()
addEnterKeyListener()
addEscKeyListener()
gui.pack()
gui.setLocationRelativeTo( ui.frame )
gui.visible = true


