// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Center on root node, fold all branches

def fold( node ){
    node.children.each{ fold( it ) }
    node.folded = true
}

def root = node.map.root
root.children.each{ fold( it ) }
c.select( root  )
c.centerOnNode( root )


