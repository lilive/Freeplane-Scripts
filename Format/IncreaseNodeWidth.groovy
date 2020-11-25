// @ExecutionModes({on_single_node="/menu_bar/format/menu_coreFormat/NodeWidth"})

/*
Increase the minimal node width of each selected node by a "step" amount.
If more than one node is selected, the minimum with for each node is set
to the minimum width of the larger one (after increment).
Ensure that the new minimal width is a multiple of the step.

Based on zipizap script for same purpose,
and nnako "toggle block view for all siblings" script.
*/

import org.freeplane.view.swing.map.NodeView

// Increase the width by this amount (you can change it)
int step = 20

// Get all selected nodes
def nodes = c.getSelecteds()

// Find the width of the larger node
float maxDisplayWidth = -1
nodes.each{
    nodeView = it.delegate.viewers.find() {it instanceof NodeView}
    width = nodeView?.mainView?.width
    if( width > maxDisplayWidth ) maxDisplayWidth = width
}

// Compute the new width to apply
int newWidth = maxDisplayWidth / c.zoom
newWidth = (int)( newWidth / step ) * step + step

// Change all nodes minimum width
nodes.each{
    it.style.minNodeWidth = newWidth
    it.style.maxNodeWidth = newWidth
}
