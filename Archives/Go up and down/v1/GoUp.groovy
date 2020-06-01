// @ExecutionModes({on_selected_node="/main_menu/navigate"})

// Fold all siblings of this node,
// jump to the parent of this node,
// memorize the node ID to be able to jump to it later

if( ! node.parent ) return
def parent = node.parent

// Get the previously memorized road
def historyKey = "GoUpDownHistory"
def historyString = ""
def stored = node.map.storage.getAt( historyKey )
if( stored ) historyString = stored.string
def history = historyString.tokenize( "|" )

// Get the last node reached by GoUp
def currentId = null
if( history ) currentId = history.first()
if( currentId ){
    // If we are not in this location, clear the road
    def n = node.map.node( currentId )
    if( !n || n != node ) history = [ node.id ]
} else {
    history = [ node.id ]
}

// Fold each sibling of this node
parent.children.each{ it.folded = true }

// Jump to parent
c.select( parent )
c.centerOnNode( parent )

// Update and memorize the road
history.push( parent.id )
println history
historyString = history.join( "|" )
node.map.storage.putAt( historyKey, historyString )


