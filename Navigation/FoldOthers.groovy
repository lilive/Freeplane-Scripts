// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Fold all nodes and keep the current node visible

def fold( node ){
    node.children.each{ fold( it ) }
    node.folded = true
}

def root = node.map.root
root.children.each{ fold( it ) }
c.select( node  )
c.centerOnNode( node )


