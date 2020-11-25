// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// If the node is folded, unfold it at level 1
// Else go to the first child of this node, folded
// Author: lilive
// Licence: WTFPL2

def fold( node ){
    node.children.each{ fold( it ) }
    node.folded = true
}

def folded = node.folded
fold( node )
node.folded = false

if( folded ) return

def children = node.children
if( ! children || children.size() == 0 ) return

def n = children[ 0 ]
c.select( n )
c.centerOnNode( n )


