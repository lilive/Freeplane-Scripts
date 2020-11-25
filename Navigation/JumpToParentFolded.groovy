// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Jump to the parent of this node, and fold it.

if( ! node.parent ) return
def parent = node.parent

parent.folded = true
c.select( parent )
c.centerOnNode( parent )


