// @ExecutionModes({on_single_node="/menu_bar/navigate"})

// Select all the children of the currently selected nodes

nodes = []
c.selecteds.each{ n -> nodes += n.children }
c.select( nodes )
