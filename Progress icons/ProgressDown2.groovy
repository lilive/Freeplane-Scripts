// @ExecutionModes({on_selected_node="/main_menu/insert/icons/progress"})

/*
Modified version of Insert > Icon > Progress icon (%) > Progress down
- When there is a checkmark icon, delete it and set progress to 75%
- Do nothing when there is no checkmark icon and no progress icon
*/

if( node.icons.contains( 'button_ok' ) ){
    
    node.icons.remove( '0%' )
    node.icons.remove( '25%' )
    node.icons.remove( '50%' )
    node.icons.remove( '75%' )
    node.icons.remove( '100%' )
    node.icons.remove( 'button_ok' )
    node.icons.add( '75%' )
    
} else {

    if(
        node.icons.contains( '0%' )
        || node.icons.contains( '25%' )
        || node.icons.contains( '50%' )
        || node.icons.contains( '75%' )
        || node.icons.contains( '100%' )
    ){
        menuUtils.executeMenuItems( [ 'IconProgressIconDownAction' ] )
    }

}

