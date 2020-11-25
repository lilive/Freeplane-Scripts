// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Fold all siblings of this node and
// jump to the parent of this node

if( ! node.parent ) return

// Fold each sibling of this node
node.parent.children.each{ it.folded = true }

// Jump to parent
c.select( node.parent  )
c.centerOnNode( node.parent )
