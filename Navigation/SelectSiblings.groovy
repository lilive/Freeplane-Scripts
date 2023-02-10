// @ExecutionModes({on_single_node="/menu_bar/navigate"})

// Select all the siblings of the currently selected nodes

nodes = []
c.selecteds.each{
    n ->
    if( n.parent ) nodes += n.parent.children
}
c.select( nodes.unique() )
