// @ExecutionModes({on_selected_node="/main_menu/navigate/fold"})

// Center on root node, fold all branches

def root = node.map.root
root.children.each{ it.folded = true }
c.select( root  )
c.centerOnNode( root )


