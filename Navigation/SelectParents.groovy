// @ExecutionModes({on_single_node="/menu_bar/navigate"})

// Select the parents of the currently selected nodes

nodes = []
c.selecteds.each{
    n ->
    if( n.parent ) nodes += n.parent }
c.select( nodes.unique() )
