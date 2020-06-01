// @ExecutionModes({on_single_node="/main_menu/navigate/Bookmarks"})

import groovy.swing.SwingBuilder
import javax.swing.JFrame
import javax.swing.BoxLayout
import javax.swing.SwingConstants
import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.*
import org.freeplane.plugin.script.proxy.Convertible
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

storageKey = "MarksKeys"
anonymousIcon = "bookmark"
namedIcon = "bookmark-named"
map = node.map
gui = null

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

def populateAnonymousBookmarksMap( node, Map bookmarks )
{
    if( node.icons.contains( anonymousIcon ) ){
        def text = node.text
        if( text.length() > 30 ) text = text[0..27] + "..."
        bookmarks[ node.id ] = text
    }
    node.children.each{ populateAnonymousBookmarksMap( it, bookmarks ) }
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

namedBookmarks = loadNamedBookmarks()

// Make sure namedBookmarks is up-to-date
def change = false
namedBookmarks.each
{
    // For each node in the map namedBookmark, verify that the node still exists
    key, id ->
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

anonymousBookmarks = [:]
populateAnonymousBookmarksMap( map.root, anonymousBookmarks )

// Quit the script if there is no bookmarks
if( namedBookmarks.size() + anonymousBookmarks.size() == 0 )
{
    c.statusInfo = 'There is no bookmarks !'
    return
}

// Jump to a node after the gui close
def jumpToNodeAfterGuiDispose( target, message )
{
    // If the code to jump to a node is executed before the gui close,
    // it leave freeplane in a bad focus state.
    // This is solved by putting this code in a listener executed
    // after the gui destruction:
    gui.addWindowListener(
        new WindowAdapter()
        {
            @Override
            public void windowClosed( WindowEvent event )
            {
                c.select(  target )
                c.centerOnNode( target )
                c.statusInfo = message
            }
        }
    )
}

isAnonymousBookmark = node.icons.contains( anonymousIcon )
isNamedBookmark = namedBookmarks.containsValue( node.id )

// Create the graphical user interface to display
groovy.swing.SwingBuilder.build
{
    gui = dialog(
        title: 'Jump to bookmark',
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
            constraints: BorderLayout.PAGE_START
        ){
            borderLayout()
            panel( constraints: BorderLayout.PAGE_START )
            {
                borderLayout()
                if( namedBookmarks.size() > 0 )
                {
                    // List all the named bookmarks
                    panel(
                        border: emptyBorder( [0,0,10,0] ),
                        constraints: BorderLayout.PAGE_START
                    )
                    {
                        gridLayout( rows: namedBookmarks.size() + 1 )
                        label("Press one of these keys to jump to a named bookmark:")
                        namedBookmarks.each
                        {
                            // Display a button for each bookmark.
                            // The label is the name of the bookmark, followed by the text
                            // of the node (truncated to 30 caracters)
                            key, id ->
                            def target = node.map.node( id )
                            def name = String.valueOf( (char) Integer.parseInt( key ) )
                            def text = target.text
                            if( text.length() > 30 ) text = text[0..27] + "..."
                            text = "<html><font color='red'><b>${name}</b></font> : ${text}</html>"
                            def action = {
                                jumpToNodeAfterGuiDispose( target, 'Jumped to bookmark named "' + name + '"' )
                                gui.dispose()
                            }
                            if( id == node.id ) button(
                                text: text, foreground: blue,
                                horizontalAlignment: SwingConstants.LEFT, actionPerformed: action
                            )
                            else button(
                                text: text, horizontalAlignment: SwingConstants.LEFT,
                                actionPerformed: action
                            )
                        }
                    }
                }
                if( anonymousBookmarks.size() > 0 )
                {
                    // List all the anonymous bookmarks
                    panel(
                        border: emptyBorder( [0,0,10,0] ),
                        constraints: BorderLayout.PAGE_END
                    )
                    {
                        gridLayout( rows: anonymousBookmarks.size() + 1 )
                        label( "Click the standard bookmark you want to jump to:" )
                        anonymousBookmarks.each
                        {
                            // Display a button for each bookmark
                            id, text ->
                            def target = node.map.node( id )
                            def action = {
                                jumpToNodeAfterGuiDispose( target, "Jump to bookmark" )
                                gui.dispose()
                            }
                            if( id == node.id ) button(
                                text: text, foreground: blue,
                                horizontalAlignment: SwingConstants.LEFT, actionPerformed: action
                            )
                            else button(
                                text: text, horizontalAlignment: SwingConstants.LEFT,
                                actionPerformed: action
                            )
                        }
                    }
                }
            }
            label( text: "Press esc to cancel.", constraints: BorderLayout.PAGE_END )
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
                def id = namedBookmarks[ s ]
                if( id )
                {
                    def target = node.map.node( id )
                    jumpToNodeAfterGuiDispose( target, 'Jumped to bookmark named "' + chr + '"' )
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
