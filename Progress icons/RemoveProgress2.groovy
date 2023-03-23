// @ExecutionModes({on_selected_node="/main_menu/insert/icons/progress"})

/*
Modified version of Insert > Icon > Progress icon (%) > Remove progress
- When there is only the checkmark icon, also delete it
*/

if( node.icons.contains( 'button_ok' ) ){
    node.icons.remove( 'button_ok' )
}

menuUtils.executeMenuItems( [ 'IconProgressRemoveAction' ] )
