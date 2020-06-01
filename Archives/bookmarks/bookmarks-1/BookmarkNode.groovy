// @ExecutionModes({on_single_node="/main_menu/navigate/Bookmarks"})

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

storageKey = "MarksKeys"
anonymousIcon = "bookmark"
namedIcon = "bookmark-named"

def Map loadNamedBookmarks()
{
    // Create a HashMap where :
    // - the keys are the keyboard keys assigned to a bookmarked node;
    // - the values are the id of the corresponding bookmarked node.
    // This HashMap is read from the freeplane map storage area.

    // Read the datas from the map storage
    def marksString = new Convertible( '{}' )
    def stored = node.map.storage.getAt( storageKey )
    if( stored ) marksString = stored;

    // Convert these datas to an HashMap
    def namedBookmarks = new JsonSlurper().parseText( marksString.getText() )

    return namedBookmarks as Map
}

def saveNamedBookmarks( namedBookmarks )
{
    // Store the HashMap namedBookmarks in the freeplane map storage area.

    def builder = new JsonBuilder()
    builder( namedBookmarks )
    node.map.storage.putAt( storageKey, builder.toString() )
}

def putIconAtFirstPosition( node, icon )
{
    // Add an icon at the first position for this node

    // Do nothing else if there is already this icon as fisrt icon
    def num = icons.iterator().count( icon )
    if( num == 0 && icons.first == icon ) return

    def oldIcons = icons.iterator().toList()

    // Remove all the same icons if necessary
    if( num > 0 ) oldIcons.removeAll{ it == icon }

    def newIcons = [ icon ]
    newIcons.addAll( oldIcons )
    icons.clear()
    icons.addAll( newIcons )
}

def addNamedBookmarkIcon( node )
{
    // Add the named bookmark icon to this node, if needed.
    // Remove anonymous bookmarks icons.
    // Ensure the icon is the first icon of the node

    def icons = node.getIcons()

    // Remove anonymous bookmarks icons
    while( icons.contains( anonymousIcon ) ) icons.remove( anonymousIcon )

    putIconAtFirstPosition( node, namedIcon )
}

def removeNamedBookmarkIcon( node )
{
    // Remove the named bookmark icon from a node

    def icons = node.getIcons()
    while( icons.contains( namedIcon ) ) icons.remove( namedIcon )
}

def createNamedBookmark( node, keyCharCode, namedBookmarks )
{
    // Create a named bookmark for this node. The name of the
    // bookmark is a single keyboard key.

    String s = String.valueOf( keyCharCode )

    // If another node has this named bookmark, remove it
    if( namedBookmarks.containsKey( s ) )
    {
        def n = map.node( namedBookmarks[ s ] )
        if( n && n != node ) deleteNamedBookmark( n, namedBookmarks )
    }
    
    // Delete the existing named bookmarks assigned to the current node
    namedBookmarks.removeAll{ k, v -> v == node.id }

    // Assign the key pressed to the current node
    namedBookmarks[ s ] = node.id

    // Save the new namedBookmarks map
    saveNamedBookmarks( namedBookmarks )

    // Add the icon
    addNamedBookmarkIcon( node )
}

def deleteNamedBookmark( node, namedBookmarks )
{
    // Delete an existing named bookmark

    // Remove the icon
    removeNamedBookmarkIcon( node )

    // Nothing more to do if namedBookmarks doesn't reference this node
    if( ! namedBookmarks.containsValue( node.id ) ) return

    // Clear the map
    namedBookmarks.removeAll{ key, value -> value == node.id }

    // Save the new namedBookmarks map
    saveNamedBookmarks( namedBookmarks )
}

def createAnonymousBookmark( node )
{
    // Create an anonymous bookmark for this node.
    // Do nothing if the node has a named bookmark.

    def icons = node.getIcons()
    if( icons.contains( namedIcon ) ) return
    putIconAtFirstPosition( node, anonymousIcon )
}

def deleteAnonymousBookmark( node )
{
    def icons = node.getIcons()
    while( icons.contains( anonymousIcon ) ) icons.remove( anonymousIcon )
}


namedBookmarks = loadNamedBookmarks()

// Make sure namedBookmarks is up-to-date
def change = false
def map = node.map
namedBookmarks.each
{
    // For each node in the map namedBookmarks...
    key, id ->

    // ... verify that the node still exists
    def n = map.node( id )
    if( n == null )
    {
        // If not clean namedBookmarks
        namedBookmarks.remove( key )
        change = true
    }
    else
    {
        if( id == node.id ) addNamedBookmarkIcon( n )
    }
}
if( change ) saveNamedBookmarks( namedBookmarks )

isAnonymousBookmark = node.icons.contains( anonymousIcon )
isNamedBookmark = namedBookmarks.containsValue( node.id )

// Create the graphical user interface to display
def gui
groovy.swing.SwingBuilder.build
{
    gui = dialog(
        title: 'Bookmarks',
        modal: true,
        owner: ui.frame,
        defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE,
        pack: true
    ){
        borderLayout()
        def blue = new Color( 0, 0, 255 )

        // Fist panel : instructions
        panel(
            border: emptyBorder( 10 ),
            constraints: BorderLayout.PAGE_START,
        ){
            boxLayout(axis: BoxLayout.Y_AXIS )
            if( isAnonymousBookmark )
            {
                label(
                    text: "This node is already bookmarked.",
                    foreground: blue
                )
                label("- You can delete this bookmark with the backspace key")
                label("- You can create instead a named bookmark by pressing a key")
            }
            else if( isNamedBookmark )
            {
                label(
                    text: "This node already has a named bookmark.",
                    foreground: blue
                )
                label("- You can delete this bookmark with the backspace key")
                label("- You can create instead an anonymous bookmark with the space key")
                label("- You can change the name of this bookmark by pressing another key")
            }
            else
            {
                label("- Press space to bookmark this node")
                label("- Or press another key to create a name bookmark for it")
            }
            label("Press esc to cancel")
        }

        // Second panel : only if named bookmarks exists
        if( namedBookmarks.size() > 0 )
        {
            panel(
                border:titledBorder( "Already named bookmarks are" ),
                constraints: BorderLayout.CENTER
            ){
                boxLayout( axis: BoxLayout.Y_AXIS )
                namedBookmarks.each
                {
                    key, id ->
                    // Display a line of text with the associated key
                    // and the 30 first caracters of the node
                    def target = map.node( id )
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
    new java.awt.event.KeyAdapter()
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            // Get the key pressed
            char chr = e.getKeyChar()
            int keyCharCode = (int) chr

            if( keyCharCode == 8 )
            {
                if( isNamedBookmark )
                {
                    deleteNamedBookmark( node, namedBookmarks )
                    gui.dispose()
                    c.statusInfo = 'This node has no bookmark anymore'
                }
                else if( isAnonymousBookmark )
                {
                    deleteAnonymousBookmark( node )
                    gui.dispose()
                    c.statusInfo = 'This node has no bookmark anymore'
                }
            }
            else if( keyCharCode == 32 )
            {
                if( isNamedBookmark )
                {
                    deleteNamedBookmark( node, namedBookmarks )
                    createAnonymousBookmark( node )
                    gui.dispose()
                    c.statusInfo = 'This node now has a standard bookmark'
                }
                else if( ! isAnonymousBookmark )
                {
                    createAnonymousBookmark( node )
                    gui.dispose()
                    c.statusInfo = 'This node now has a standard bookmark'
                }
            }
            else if( keyCharCode > 32 && keyCharCode != 127 && keyCharCode < 256 )
            {
                if( isAnonymousBookmark ) deleteAnonymousBookmark( node )
                createNamedBookmark( node, keyCharCode, namedBookmarks )
                gui.dispose()
                c.statusInfo = 'This node now has a bookmark named "' + chr + "'"
            }
            else if( keyCharCode == 27 )
            {
                c.statusInfo = 'Bookmark operation aborded'
                gui.dispose()
            }
        }
    }
)

// Center the gui over the freeplane window
gui.setLocationRelativeTo( ui.frame )
gui.visible = true

