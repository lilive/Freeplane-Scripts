// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Fold or unfold this node, with its children folded.
// Do the same for all its siblings.
// Author: lilive
// Licence: WTFPL2

def unfold( n ){
    n.folded = false
    n.children.each{  it.folded = true }
}

if( node.folded ){
    if( node.parent ) node.parent.children.each{ unfold( it ) }
    else unfold( node )
} else {
    if( node.parent ) node.parent.children.each{ it.folded = true }
    else node.folded = true
}


