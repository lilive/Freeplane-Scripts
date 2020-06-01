// @ExecutionModes({on_single_node="/main_menu/navigate/mark"})

import org.freeplane.plugin.script.proxy.Convertible
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.swing.SwingBuilder
import javax.swing.JFrame
import javax.swing.BoxLayout
import java.awt.event.*
import java.awt.Color
import java.awt.Component
import java.awt.BorderLayout

// Read the marks datas from the map storage
def marksString = new Convertible( '{}' )
def stored = node.map.storage.getAt( 'MarksKeys' )
if( stored ) marksString = stored;
// Convert these datas to an HashMap
def marks = new JsonSlurper().parseText( marksString.getText() )

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

        // Fist panel : instructions
        panel(
            border: emptyBorder( 10 ),
            constraints: BorderLayout.PAGE_START,
        ){
            boxLayout(axis: BoxLayout.Y_AXIS )
            def alreadyMarked = marks.containsValue( node.id )
            if( alreadyMarked )
            {
                label(
                    text: "This node is already marked with a key.",
                    foreground: blue
                )
                label("- You can delete this mark with the backspace key")
                label("- You can assign another key to this node by pressing it")
            }
            else
            {
                label("Press the key to assign to this node")
            }
            label("Press esc to cancel")
        }

        // Second panel : existing marks
        if( marks.size() > 0 )
        {
            panel(
                border:titledBorder( "Already assigned keys are" ),
                constraints: BorderLayout.CENTER
            ){
                boxLayout( axis: BoxLayout.Y_AXIS )
                marks.each
                {
                    key, id ->
                    // For each node in the map marks, verify the node still exists
                    def r = c.find{ id.equals( it.getId() ) }
                    if( !r.isEmpty() )
                    {
                        // If the node exists display a line of text with the associated key
                        // and the 30 first caracters of the node
                        def target = r[0]
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
}

// Create the response to the user key press
gui.setFocusable(true)
gui.addKeyListener(
    new java.awt.event.KeyAdapter()
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            // Get the key pressed
            char chr = e.getKeyChar()
            int idx = (int) chr

            // Check if the current node is already marked
            def alreadyMarked = marks.containsValue( node.id )
            
            if( alreadyMarked && idx == 8 )
            {
                // Delete the existing mark for the current node when backspace is pressed
                marks.removeAll{ key, value -> value == node.id }
                
                // Save the new marks map
                def builder = new JsonBuilder()
                builder( marks )
                node.map.storage.putAt( 'MarksKeys', builder.toString() )
                c.statusInfo = 'Node is not marked anymore'
            }
            else if( idx > 32 && idx < 127 )
            {
                // Delete the existing mark for the current node if a new mark is set
                if( alreadyMarked ) marks.removeAll{ key, value -> value == node.id }

                // Assign the key pressed to the current node
                String s = String.valueOf( idx )
                marks[ s ] = node.id

                // Save the new marks map
                def builder = new JsonBuilder()
                builder( marks )
                node.map.storage.putAt( 'MarksKeys', builder.toString() )
                c.statusInfo = 'Node is marked with key ' + chr
            }
            else
            {
                c.statusInfo = 'Mark node aborded'
            }
            gui.dispose()
        }
    }
)
