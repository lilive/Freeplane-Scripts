// @ExecutionModes({on_single_node="/menu_bar/format/menu_coreFormat/NodeWidth"})

/*
Decrease the minimal node width of each selected node by a "step" amount.
If the minimal width of a node go above 0, unset it (equivalent to uncheck
the minimal width checkbox in the format panel).
Ensure that the new minimal width is a multiple of the step.

Based on zipizap script for same purpose.
*/

// Decrease the width by this amount (you can change it)
int step = 20

// Get all selected nodes
def nodes = c.getSelecteds()

// Change nodes minimum width
nodes.each{

    // Discard node that do not have a minimum width
    if( it.style.minNodeWidth < 0 ) return
    
    // Compute the new width to apply
    int newWidth = (int)( it.style.minNodeWidth / step ) * step - step
    if( newWidth <= 0 ) newWidth = -1
    
    it.style.minNodeWidth = newWidth
    it.style.maxNodeWidth = newWidth
}
