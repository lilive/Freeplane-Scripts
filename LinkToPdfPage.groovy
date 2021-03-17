// @ExecutionModes({on_single_node="/main_menu/insert/links"})

// Create a link that open a pdf in SumatraPDF, at a specific page.
// Only tested for Windows.
// The script open a dialog to ask for the pdf and the page number.
// Then it can create a link to this page in the currently selected node,
// or an entire node to handle this link.

// Note: with FP 1.9.0 alpha, the link do not works well if SumatraPDF
// is not already started.


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


// The 3 main fields of the script
pageTF = null    // JtextField for the page
pdfPathTF = null // JtextField for the pdf path
gui = null       // JFrame for the whole dialog


// Class with a static field, to memorize the pdf path
// between script calls (workaround)
class LastFile {
    static String path = ""
}

// Open a file chooser dialog to select the pdf
fileFilter = new FileNameExtensionFilter("PDF document", "pdf")
String askPdf(){
    JFileChooser jfc = new JFileChooser( LastFile.path )
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
                border: emptyBorder( [ 5, 5, 5, 5 ] )
            ){
                gbl = gridBagLayout()
                gbl.defaultConstraints.insets = new Insets( 5, 5, 5, 5 )
                label(
                    text: "PDF document",
                    constraints: gbc( gridx:0, gridy:0, insets: new Insets( 0, 0, 5, 5 ) )
                )
                pdfPathTF = textField(
                    text: LastFile.path,
                    columns: 40,
                    constraints: gbc( gridx:1, gridy:0, fill:GridBagConstraints.HORIZONTAL, weightx:1, insets: new Insets( 0, 0, 5, 5 ) )
                )
                button(
                    text: "Choose",
                    constraints: gbc( gridx:2, gridy:0, insets: new Insets( 0, 0, 5, 0 ) ),
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
                    constraints: gbc( gridx:0, gridy:1, anchor:GridBagConstraints.LINE_END, insets: new Insets( 0, 0, 0, 5 ) )
                )
                pageTF = textField(
                    text: "",
                    columns: 5,
                    constraints: gbc( gridx:1, gridy:1, anchor:GridBagConstraints.LINE_START, gridwidth:2 )
                )
                pageTF.maximumSize = pageTF.preferredSize
                pageTF.minimumSize = pageTF.preferredSize
            }
            hbox(
                constraints: BorderLayout.PAGE_END,
                border: emptyBorder( [ 0, 5, 5, 5 ] )
            ){
                hglue()
                button(
                    text: "Cancel",
                    actionPerformed: { gui.dispose() }
                )
                hstrut()
                okBtn = button(
                    text: "Create link to PDF page",
                    actionPerformed: { createLink() }
                )
                hstrut()
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
        @Override public void keyReleased( KeyEvent e ){
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

// Return true if we have got a valid pdf path and a valid page number
boolean checkDatas(){
    if( ! ( pageTF.text ==~ /\d+/ ) ) return false
    if( pdfPathTF.text == "" ) return false
    def file = new File( pdfPathTF.text )
    if( ! file.exists() ) return false
    return fileFilter.accept( file )
}

// Create the string for the link to create
String getLink(){
    def pdfReaderPath = /C:\Program Files\SumatraPDF\SumatraPDF.exe/
    def args = /-reuse-instance -page / + pageTF.text
    def filePath = pdfPathTF.text
    def cmd = /execute:_"${pdfReaderPath}" ${args} "${filePath}"/
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
    if( ! checkDatas() ) return
    LastFile.path = pdfPathTF.text
    node.link.text = getLink()
}

// Close the dialog and create a node that link the pdf page
def createNode(){
    gui.dispose()
    if( ! checkDatas() ) return
    LastFile.path = pdfPathTF.text
    def pdfName = new File( pdfPathTF.text ).getName()
    n = node.createChild( "${pdfName} - Page ${pageTF.text}" )
    n.link.text = getLink()
}

createGui()
addEnterKeyListener()
addEscKeyListener()
gui.pack()
gui.setLocationRelativeTo( ui.frame )
gui.visible = true


