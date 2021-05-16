// @ExecutionModes({on_single_node="/main_menu/edit/menu_moveNode"})

// Make the selected node(s) the first child(ren) of its(their) parent node

parent = node.parent
nodes = c.getSortedSelection( true )
if( nodes.find{ it.parent != parent } ){
    ui.informationMessage(
        ui.frame, "All nodes must have the same parent to use this fonction.",
        "Freeplane", javax.swing.JOptionPane.ERROR_MESSAGE
    )
} else {
    nodes.reverse().each{ it.moveTo( parent, 0 ) }
    c.select( nodes )
}
