// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Fold or unfold this node. When unfolded, fold all its children

if( node.folded || node.isRoot() ){
    node.folded = false
    node.children.each{ it.folded = true }
} else {
    node.folded = true
}


