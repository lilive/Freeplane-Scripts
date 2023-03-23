// @ExecutionModes({on_selected_node="/main_menu/insert/icons/progress"})

/*
Modified version of Insert > Icon > Progress icon (%) > Progress up
- When 100% if reach, only keep the checkmark icon and delete the progress icon
- If there is already a checkmark icon, remove any progress icon 
*/

if( node.icons.contains( 'button_ok' ) ){
    
    node.icons.remove( '0%' )
    node.icons.remove( '25%' )
    node.icons.remove( '50%' )
    node.icons.remove( '75%' )
    node.icons.remove( '100%' )
    
} else {

    menuUtils.executeMenuItems( [ 'IconProgressIconUpAction' ] )

    if( node.icons.contains( 'button_ok' ) ){
        node.icons.remove( '100%' )
    }

}

