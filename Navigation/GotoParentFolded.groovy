// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Jump to the parent of this node, fold it and fold its children
// Author: lilive
// Licence: WTFPL2

def fold( node ){
    node.children.each{ fold( it ) }
    node.folded = true
}

if( ! node.folded && node.children && node.children.size() ){
    fold( node )
} else {
    def parent = node.parent
    if( ! parent ) return
    c.select( parent )
    c.centerOnNode( parent )
}




