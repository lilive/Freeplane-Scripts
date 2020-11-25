// @ExecutionModes({on_single_node="/main_menu/format/cloud_shapes"})

// Author: lilive
// Licence: WTFPL2

if( node.cloud.enabled ){
    node.cloud.enabled = false
} else {
    node.cloud.enabled = true
    node.cloud.shape = "RECT"
    node.cloud.colorCode = "#FFFFFF22"
}
