// @ExecutionModes({on_selected_node="/main_menu/navigate"})

// Jump to the previous node memorized in GoUp script

// Get the road memorized by GoUp
def historyKey = "GoUpDownHistory"
def historyString = ""
def stored = node.map.storage.getAt( historyKey )
if( stored ) historyString = stored.string
def history = historyString.tokenize( "|" )
println ">> 1" + history

// Do nothing if there is no road
if( ! history ) return

// Get the last node reached by GoUp
def lastNode = node.map.node( history.pop() )

// If we are not in this location,
// or if this node doesn't exist anymore,
// or if there is no history left,
// clear the history and stop
if( lastNode != node || ! history ){
    node.map.storage.putAt( historyKey, null )
    return
}

// Get the previous node reached by GoUp
lastNode = node.map.node( history.first() )

// If this node doesn't exist anymore,
// clear the history and stop
if( ! lastNode ){
    node.map.storage.putAt( historyKey, null )
    return
}

// Jump to this node
c.centerOnNode( lastNode )
c.select( lastNode )

// Unfold it
lastNode.folded = false

// Fold each children of this node
lastNode.children.each{ it.folded = true }

// Update the road
if( history.size() == 1 ) historyString = null
else historyString = history.join( "|" )
node.map.storage.putAt( historyKey, historyString )

println "2>>" + historyString
