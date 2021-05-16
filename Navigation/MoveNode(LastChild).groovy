// @ExecutionModes({on_single_node="/main_menu/edit/menu_moveNode"})

// Make the selected node(s) the last child(ren) of its(their) parent node

parent = node.parent
nodes = c.getSortedSelection( true )
if( nodes.find{ it.parent != parent } ){
    ui.informationMessage(
        ui.frame, "All nodes must have the same parent to use this fonction.",
        "Freeplane", javax.swing.JOptionPane.ERROR_MESSAGE
    )
} else {
    nodes.each{ it.moveTo( parent, -1 ) }
    c.select( nodes )
}
