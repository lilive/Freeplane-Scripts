// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Fold or unfold this node. When unfolded, fold all its children
// Author: lilive
// Licence: WTFPL2

if( node.folded || node.isRoot() ){
    node.folded = false
    node.children.each{ it.folded = true }
} else {
    node.folded = true
}


