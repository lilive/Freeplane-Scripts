// @ExecutionModes({on_single_node="/main_menu/navigate/mark"})

import groovy.swing.SwingBuilder
import javax.swing.JFrame
import javax.swing.BoxLayout
import javax.swing.Box
import org.freeplane.plugin.script.proxy.Convertible
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.event.*

// Read the marks datas from the map storage
def marksString = new Convertible( '{}' )
def stored = node.map.storage.getAt( 'MarksKeys' )
if( stored ) marksString = stored;
// Convert these datas to an HashMap
def marks = new JsonSlurper().parseText( marksString.getText() )

// Purge the marks for nodes that do not exist anymore
def change = false
marks.each
{
    key, id ->
    // For each node in the map marks, verify that the node still exists
    def r = c.find{ id.equals( it.getId() ) }
    if( r.isEmpty() )
    {
        marks.remove( key )
        change = true
    }
}
if( change )
{
    // Save the new marks map
    def builder = new JsonBuilder()
    builder( marks )
    node.map.storage.putAt( 'MarksKeys', builder.toString() )
}

// Quit the script if there is no marks
if( marks.size() == 0 )
{
    c.statusInfo = 'There is no marked nodes !'
    return
}

// Create the graphical user interface to display
def gui
groovy.swing.SwingBuilder.build
{
    gui = dialog(
        title: 'Marks',
        size: [ 300, 300 ],
        locationRelativeTo: ui.frame,
        owner: ui.frame,
        defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE,
        pack: true,
        show: true
    ){
        borderLayout()
        def blue = new Color( 0, 0, 255 )
        panel(
            border: emptyBorder( 10 ),
            constraints: BorderLayout.PAGE_START,
        ){
            boxLayout(axis: BoxLayout.Y_AXIS )
            label("Press one of these keys to jump to the corresponding node,")
            label("or press esc to cancel.")
            
            panel(
                border: emptyBorder( 10 ),
            )
            {
                boxLayout( axis: BoxLayout.Y_AXIS )
                marks.each
                {
                    key, id ->
                    // Display a line of text with the associated key
                    // and the 30 first caracters of the node
                    def target = node.map.node( id )
                    def text = target.text
                    if( text.length() > 30 ) text = text[0..27] + "..."
                    text =
                        "<html><font color='red'><b>" +
                        String.valueOf( (char) Integer.parseInt( key ) ) +
                        "</b></font> : " +
                        text +
                        "</html>"
                    if( id == node.id ) label( text: text, foreground: blue )
                    else label( text )
                }
            }
        }
    }
}

// Create the response to the user key press
gui.setFocusable(true)
gui.addKeyListener(
    new KeyAdapter()
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            // Get the key pressed
            char chr = e.getKeyChar()
            int idx = (int) chr

            if( idx == 27 )
            {
                gui.dispose()
                c.statusInfo = 'Mark jump aborded'
            }
            else if( idx > 32 && idx < 127 )
            {
                // Get the corresponding node id
                String s = String.valueOf( idx )
                def id = marks[ s ]
                if( id )
                {
                    def target = node.map.node( id )
                    
                    // It is possible to execute here the code to jump to the node.
                    // But this leave freeplane in a bad focus state.
                    // This is solved by putting this code in a listener executed
                    // after the gui destruction:
                    gui.addWindowListener(
                        new WindowAdapter()
                        {
                            @Override
                            public void windowClosed( WindowEvent event )
                            {
                                // Jump to this node
                                c.select(  target )
                                c.centerOnNode( target )
                                c.statusInfo = 'Jumped to mark ' + chr
                            }
                        }
                    )

                    // Now close the dialog, and the jump will be called
                    gui.dispose()
                }
                else
                {
                    c.statusInfo = 'There is no node marked with the key ' + chr
                }
            }
        }
    }
)
